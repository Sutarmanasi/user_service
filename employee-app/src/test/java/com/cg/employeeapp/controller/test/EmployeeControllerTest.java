package com.cg.employeeapp.controller.test;

import com.cg.employeeapp.EmployeeAppApplication;
import com.cg.employeeapp.bean.Employee;
import com.cg.employeeapp.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EmployeeAppApplication.class)
@WebAppConfiguration
public class EmployeeControllerTest {
    @Mock
    private EmployeeService service;

    @Test
    public void getAllEmployeeList() {
        service.getAllEmployee();
    }

    @Test
    public void getEmployeeDetails() {
        service.getEmployeeDetailsById(1);
    }

    @Test
    public void saveEmployeeDetails(){
        Employee employee = new Employee();
        employee.setEmployeeId(1);
        employee.setEmployeeName("qwerty");
        employee.setCompany("cg");
        service.saveEmployeeDetails(employee);
    }

    @Test
    public void deleteEmployee(){
        service.deleteEmployeeDetails(2);
    }
}
