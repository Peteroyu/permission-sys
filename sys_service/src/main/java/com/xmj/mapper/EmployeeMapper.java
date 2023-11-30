package com.xmj.mapper;

import com.xmj.entity.Employee;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Repository
public interface EmployeeMapper {
    @Transactional
    void deleteById(Serializable[] ids);

    @Transactional
    void update(Employee employee);

    @Transactional
    void insertOne(Employee employee);

    Employee queryById(Serializable id);

    List<Employee> queryAll(@Param("keyword") String keyword);
}
