package com.cg.employeeapp.controller;

import com.cg.employeeapp.bean.Employee;
import com.cg.employeeapp.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EmployeeController {
    @Autowired
    public EmployeeService employeeService;
    Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/employeeDetails")
    public List<Employee> getAllEmployee(){
        logger.info("Received all employee details");
        return employeeService.getAllEmployee();
    }

    @GetMapping("/employee/{employeeId}")
    public Employee getEmployeeDetailsById(@PathVariable("employeeId") int employeeId){
        logger.info("Received employee details by id");
        return employeeService.getEmployeeDetailsById(employeeId);
    }

    @PostMapping("/saveEmployee")
    public ResponseEntity<Optional> saveEmployeeDetails(@RequestBody Employee employee){
        employeeService.saveEmployeeDetails(employee);
        logger.info("Employee details are saved");
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/deleteEmployee/{employeeId}")
    public void deleteEmployeeDetails(@PathVariable("employeeId") int employeeId){
        logger.debug("Deleted employee details");
        employeeService.deleteEmployeeDetails(employeeId);
    }

    @GetMapping("/employee/company/{company}")
    public Employee getEmployeeDetailsByCompany(@PathVariable("company") String company){
        return employeeService.getEmployeeDetailsByCompany(company);
    }
}
