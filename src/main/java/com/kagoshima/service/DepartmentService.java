package com.kagoshima.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kagoshima.entity.Department;
import com.kagoshima.entity.Employee;
import com.kagoshima.repository.DepartmentRepository;
import com.kagoshima.repository.EmployeeRepository;

@Service
public class DepartmentService {

    private final EmployeeRepository employeeRepository;
    private final ReportService reportService;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository, ReportService reportService) {
        this.employeeRepository = employeeRepository;
        this.reportService = reportService;
        this.departmentRepository = departmentRepository;
    }

    // 所属保存
    @Transactional
    public void save(Employee employee) {

        
    }

    // 所属名更新
    @Transactional
    public void update(Employee employee) {

    }

    // 所属削除
    @Transactional
    public void delete(String code, UserDetail userDetail) {

    }

    // 所属一覧表示処理
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    


}
