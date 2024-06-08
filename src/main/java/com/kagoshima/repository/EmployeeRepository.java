package com.kagoshima.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kagoshima.entity.Employee;
import com.kagoshima.entity.Employee.Affiliation;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
    List<Employee> findByAffiliation(Affiliation affiliation);
}