package com.kagoshima;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kagoshima.entity.Employee;
import com.kagoshima.entity.Report;
import com.kagoshima.service.EmailService;
import com.kagoshima.service.EmployeeService;
import com.kagoshima.service.ReportService;

@Component
public class ScheduledTasks {

    private final EmailService emailService;
    private final ReportService reportService;
    private final EmployeeService employeeService;

    private String tempSubject = "【月報管理システム】%month%月報告書 リマインド";
    private String tempText = "%month%月分の報告書が未提出となっております。%month%月中の提出をお願いいたします。\r\n\r\n※このメールは「月報管理システム」からの自動配信メールとなっております。ご返信はお受けできかねますのでご了承ください。";

    @Autowired
    public ScheduledTasks(EmailService emailService, ReportService reportService, EmployeeService employeeService) {
        this.emailService = emailService;
        this.reportService = reportService;
        this.employeeService = employeeService;
    }

    @Scheduled(cron = "0 * * * * ?")
    public void performTask() {
        ArrayList<String> toList = new ArrayList<>();

        List<Employee> generalEmployees = employeeService.findByRole(Employee.Role.GENERAL);
        for (Employee employee : generalEmployees) {
            List<Report> reports = reportService.findByEmployee(employee);
            Report currentReport = null;
            if(reports != null && !reports.isEmpty()) {
                currentReport = reports.stream().max((r1, r2) -> r1.getReportMonth().compareTo(r2.getReportMonth())).get();
            }

            if(currentReport != null && !checkCurrentReportCompleted(currentReport)) {
                toList.add(employee.getEmail());
            }
        }
        sendEmail(toList);
    }

    private void sendEmail(ArrayList<String> toList) {
        String month = String.valueOf(YearMonth.now().getMonthValue());
        String subject = tempSubject.replace("%month%", month);
        String text = tempText.replace("%month%", month);

        for (String to : toList) {
            emailService.sendSimpleEmail(to, subject, text);
        }
    }

    // 当月分の報告書が作成済みか
    private boolean checkCurrentReportCompleted(Report report) {
        boolean isCurrent = false;
        boolean isCompleted = false;

        YearMonth currentMonth = YearMonth.now();
        isCurrent = currentMonth.equals(report.getReportMonth());

        isCompleted = report.isCompleteFlg();

        return isCurrent && isCompleted;
    }

}
