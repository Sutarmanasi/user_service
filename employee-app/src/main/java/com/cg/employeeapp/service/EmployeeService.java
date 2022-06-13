package com.cg.employeeapp.service;

import com.cg.employeeapp.bean.Employee;
import com.cg.employeeapp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    public EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployee() {
        List<Employee> employees = new ArrayList<>();
        employeeRepository.findAll().forEach(employees::add);
        return employees;
    }

    public Employee getEmployeeDetailsById(int employeeId) {
        return employeeRepository.findById(employeeId).get();
    }

    public void saveEmployeeDetails(Employee employee) {
        employeeRepository.save(employee);
    }

    public void deleteEmployeeDetails(int employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    public Employee getEmployeeDetailsByCompany(String company) {
        return employeeRepository.findByCompany(company);
    }
}
