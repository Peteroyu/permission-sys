package com.xmj.service.impl;

import com.xmj.entity.Employee;
import com.xmj.mapper.EmployeeMapper;
import com.xmj.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeMapper employeeMapper;
    @Override
    public Employee queryOne(Long id) {
        return employeeMapper.queryById(id);
    }

    @Override
    public List<Employee> queryAll(String keyword) {
        return employeeMapper.queryAll(keyword);
    }

    @Override
    public void deleteEmp(Long[] ids) {
        employeeMapper.deleteById(ids);
    }

    @Override
    public void addEmp(Employee employee) {
        employeeMapper.insertOne(employee);
    }

    @Override
    public void updateEmp(Employee employee) {
        employeeMapper.update(employee);
    }
}
