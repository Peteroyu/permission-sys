package com.xmj.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xmj.entity.Department;
import com.xmj.entity.Employee;
import com.xmj.service.DepartmentService;
import com.xmj.util.AjaxMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dept")
@Api(value = "部门的API", description = "部门相关的CRUD功能")
public class DepartmentController {
    @Autowired
    DepartmentService service;

    @GetMapping("/{id}")
    @ApiOperation(value = "通过ID查询部门")
    public AjaxMessage queryOne(@PathVariable("id") Long id) {
        try {
            Department department = service.queryOne(id);
            return AjaxMessage.success().addData("department", department).addMessage("查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxMessage.error().addMessage("查询失败");
        }
    }

    @GetMapping
    @ApiOperation(value = "查询所有部门")
    public AjaxMessage queryAll(Integer pageNum, Integer pageSize, String keyword) {
        if (keyword == null || (keyword = keyword.trim()).equals("")) {
            keyword = "";
        }
        try {
            if (pageNum == null) {
                List<Department> departments = service.queryAll(keyword);
                return AjaxMessage.success().addData("departments", departments).addMessage("查询所有成功");
            } else {
                if (pageSize == null || pageSize < 10) {
                    pageSize = 10;
                }
                PageHelper.startPage(pageNum, pageSize);
                List<Department> departments = service.queryAll(keyword);
                PageInfo<Department> pageInfo = new PageInfo<>(departments, 5);
                return AjaxMessage.success().addData("pageInfo", pageInfo).addMessage("分页查询成功");
            }


        } catch (Exception e) {
            e.printStackTrace();
            return AjaxMessage.error().addMessage("查询失败");
        }
    }

    @PostMapping
    @ApiOperation(value = "新增部门")
    public AjaxMessage addOne(@RequestBody Department department) {
        System.out.println(department);
        try {
            service.addDept(department);
            return AjaxMessage.success().addMessage("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxMessage.error().addMessage("添加失败");
        }
    }


    @PutMapping
    @ApiOperation(value = "通过ID更新部门")
    public AjaxMessage update(@RequestBody Department department) {
        System.out.println(department);
        try {
            service.updateDept(department);
            return AjaxMessage.success().addMessage("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxMessage.error().addMessage("修改失败");
        }
    }

    @DeleteMapping
    @ApiOperation(value = "通过ID删除部门")
    public AjaxMessage delete(@RequestParam("id") Long[] ids) {
        try {
            service.deleteOne(ids);
            return AjaxMessage.success().addMessage("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxMessage.error().addMessage("删除失败");
        }
    }

}
