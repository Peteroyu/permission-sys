package com.xmj.service;

import com.xmj.entity.Employee;

import java.io.Serializable;
import java.util.List;

public interface EmployeeService {
    Employee queryOne(Long id);
    List<Employee> queryAll(String keyword);

    void deleteEmp(Long[] ids);

    void addEmp(Employee employee);

    void updateEmp(Employee employee);
}
