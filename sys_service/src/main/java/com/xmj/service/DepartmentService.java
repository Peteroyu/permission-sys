package com.xmj.service;


import com.xmj.entity.Department;

import java.util.List;

public interface DepartmentService {
    Department queryOne(Long id);
    List<Department> queryAll(String keyword);
    List<Department> queryDeptTree();

    void deleteOne(Long[] ids);

    void addDept(Department department);

    void updateDept(Department department);

}
