package com.kagoshima.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.sql.init.DatabaseInitializationSettings;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kagoshima.constants.ErrorKinds;
import com.kagoshima.constants.ErrorMessage;
import com.kagoshima.entity.Report;
import com.kagoshima.entity.Employee;
import com.kagoshima.entity.Employee.Role;
import com.kagoshima.service.EmployeeService;
import com.kagoshima.service.ReportService;
import com.kagoshima.service.UserDetail;

@Controller
@RequestMapping("reports")
public class ReportController {

    private final ReportService reportService;
    private final EmployeeService employeeService;

    @Autowired
    public ReportController(ReportService reportService, EmployeeService employeeService) {
        this.reportService = reportService;
        this.employeeService = employeeService;
    }

    // 月報一覧画面
    @GetMapping
    public String list(Model model, @AuthenticationPrincipal UserDetail userDetail) {
        List<Report> reportList = new ArrayList<>();
        if(userDetail.getEmployee().getRole().equals(Role.ADMIN)) {
            // 管理者権限の場合、すべてのレポートを取得
            reportList.addAll(reportService.findAll());
        } else {
            // 一般権限の場合、ログインユーザと同じ所属のレポートを取得
            List<Employee> employeeList = employeeService.findByAffiliaton(userDetail.getEmployee().getAffiliation());
            for(Employee employee : employeeList) {
                reportList.addAll(reportService.findByEmployee(employee));
            }
        }

        // 表示月選択用
        TreeSet<LocalDate> dateSet = new TreeSet<>();
        for(Report rep : reportList) {
            dateSet.add(rep.getReportDate());
        }

        // 直近の報告書引き継ぎ用
        boolean isPastCheck = reportService.findByEmployee(userDetail.getEmployee()).size() > 0;

        model.addAttribute("listSize", reportList.size());
        model.addAttribute("reportList", reportList);
        model.addAttribute("dateSet", dateSet);
        model.addAttribute("isPastCheck", isPastCheck);

        return "reports/list";
    }

    // 月報新規登録画面
    @PostMapping(value = "/create")
    public String create(@ModelAttribute Report report, @AuthenticationPrincipal UserDetail userDetail, Model model, @RequestParam(name="pastCheck", required = false) String pastCheck) {
        model.addAttribute("fullName", userDetail.getEmployee().getFullName());
        model.addAttribute("affiliation", userDetail.getEmployee().getAffiliation());

        if(pastCheck != null ) {
            List<Report> reports = reportService.findByEmployee(userDetail.getEmployee());
            if(reports.size() > 0) {
                Collections.sort(reports, Comparator.comparing(Report::getReportDate));
                Report rep = reports.get(reports.size() - 1);
                model.addAttribute("report", rep);
            }
        }

        return "reports/new";
    }

    // 月報新規登録処理
    @PostMapping(value="/add")
    public String add(@Validated Report report, BindingResult res, @AuthenticationPrincipal UserDetail userDetail, Model model) {

        // 入力チェック
        if (res.hasErrors()) {
            return create(report, userDetail, model, null);
        }

        ErrorKinds result = reportService.save(report, userDetail);

        if (ErrorMessage.contains(result)) {
            model.addAttribute(ErrorMessage.getErrorName(ErrorKinds.DATECHECK_ERROR),
                    ErrorMessage.getErrorValue(ErrorKinds.DATECHECK_ERROR));
            return create(report, userDetail, model, null);
        }

        return "redirect:/reports";
    }

    // 月報詳細画面
    @GetMapping(value="/{id}/")
    public String detail(@PathVariable String id, @AuthenticationPrincipal UserDetail userDetail, Model model) {
        model.addAttribute("report", reportService.findById(id));
        model.addAttribute("employee", userDetail.getEmployee());
        return "reports/detail";
    }

    // 月報更新画面
    @GetMapping(value = "/update/{id}/")
    public String edit(@PathVariable String id, Model model, Report report) {
        if(report.getCreatedAt()==null) {
            // 更新画面を最初に開くときはこっち
            model.addAttribute("report", reportService.findById(id));
        } else {
            // 更新処理に失敗した場合はこっち
            model.addAttribute("report", report);
        }

        return "reports/update";
    }

    // 月報更新処理
    @PostMapping(value = "/update")
    public String update(@Validated Report report, BindingResult res, Model model) {

        // 入力チェック
        if (res.hasErrors()) {
            return edit(report.getId().toString(), model, report);
        }
        ErrorKinds result = reportService.update(report);

        if (ErrorMessage.contains(result)) {
            model.addAttribute(ErrorMessage.getErrorName(result), ErrorMessage.getErrorValue(result));
            return edit(report.getId().toString(), model, report);
        }

        return "redirect:/reports";

    }


    // 従業員削除処理
    @PostMapping(value = "/{id}/delete")
    public String delete(@PathVariable String id,  Model model) {

        reportService.delete(id);

        return "redirect:/reports";
    }

    // コメント追加
    @PostMapping(value = "/comment")
    public String comment(Report report,  Model model) {
        String id = report.getId().toString();
        String comment = report.getComment();
        reportService.comment(id, comment);

        return "redirect:/reports";
    }

}
