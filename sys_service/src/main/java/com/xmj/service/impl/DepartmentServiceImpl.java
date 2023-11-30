package com.xmj.service.impl;

import com.xmj.entity.Department;
import com.xmj.mapper.DepartmentMapper;
import com.xmj.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    DepartmentMapper departmentMapper;
    @Override
    public Department queryOne(Long id) {
        return departmentMapper.queryById(id);
    }

    @Override
    public List<Department> queryAll(String keyword) {
        return departmentMapper.queryAll(keyword);
    }

    @Override
    public List<Department> queryDeptTree() {
        return departmentMapper.queryDeptTree();
    }

    @Override
    public void deleteOne(Long[] ids) {
        departmentMapper.deleteById(ids);
    }

    @Override
    public void addDept(Department department) {
        departmentMapper.insertOne(department);
    }

    @Override
    public void updateDept(Department department) {
        departmentMapper.update(department);
    }
}
