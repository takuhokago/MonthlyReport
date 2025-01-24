package com.kagoshima;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
    private final MessageSource messageSource;

    @Autowired
    public ScheduledTasks(EmailService emailService, ReportService reportService, EmployeeService employeeService, MessageSource messageSource) {
        this.emailService = emailService;
        this.reportService = reportService;
        this.employeeService = employeeService;
        this.messageSource = messageSource;
    }

    @Scheduled(cron = "0 * * * * ?")
    public void performTask() {
        ArrayList<Employee> toList = new ArrayList<>();

        List<Employee> generalEmployees = employeeService.findByRole(Employee.Role.GENERAL);
        for (Employee employee : generalEmployees) {
            List<Report> reports = reportService.findByEmployee(employee);
            Report currentReport = null;
            if(reports != null && !reports.isEmpty()) {
                currentReport = reports.stream().max((r1, r2) -> r1.getReportMonth().compareTo(r2.getReportMonth())).get();
            }

            if(currentReport != null && !checkCurrentReportCompleted(currentReport)) {
                toList.add(employee);
            }
        }
        sendEmail(toList);
    }

    private void sendEmail(ArrayList<Employee> toList) {
        String month = String.valueOf(YearMonth.now().getMonthValue());
        String subject = messageSource.getMessage("subject.remind", new Object[] {month}, Locale.JAPAN);

        for (Employee emp : toList) {
        	String body = messageSource.getMessage("body.remind", new Object[] {emp.getFullName(),month}, Locale.JAPAN);
            emailService.sendSimpleEmail(emp.getEmail(), subject, body);
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
