package com.cg.employeeapp.repository;

import com.cg.employeeapp.bean.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
    @Transactional
    @Query(value = "select * from employee emp where emp.company=?", nativeQuery = true)
    Employee findByCompany(String company);
}
