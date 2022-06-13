package com.cg.employeeapp.service.test;

import com.cg.employeeapp.EmployeeAppApplication;
import com.cg.employeeapp.bean.Employee;
import com.cg.employeeapp.repository.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EmployeeAppApplication.class)
@WebAppConfiguration
public class EmployeeServiceTest {
    @Mock
    private EmployeeRepository repository;

    @Before
    @Test
    public void saveEmployeeDetails(){
        Employee employee = new Employee();
        employee.setEmployeeId(1);
        employee.setEmployeeName("qwerty");
        employee.setCompany("cg");
        repository.save(employee);
    }

    @Test
    public void getAllEmployee(){
        repository.findAll();
    }

    @Test
    public void getEmployeeDetailsByValidId(){
        repository.findById(1);
    }

    @Test
    public void getEmployeeDetailsByInvalidId(){
        repository.findById(-1);
    }
}
