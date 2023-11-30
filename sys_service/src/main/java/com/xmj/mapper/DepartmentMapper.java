package com.xmj.mapper;

import com.xmj.entity.Department;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Repository
public interface DepartmentMapper {
    @Transactional
    void deleteById(Serializable id);

    @Transactional
    void update(Department department);

    @Transactional
    void insertOne(Department department);

    Department queryById(Serializable id);

    List<Department> queryAll(@Param("keyword") String keyword);

    List<Department> queryDeptTree();

}
