package com.xmj.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xmj.entity.Employee;
import com.xmj.service.EmployeeService;
import com.xmj.util.AjaxMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emp")
@Api(value = "员工的API",description="员工相关的CRUD功能")
public class EmployeeController{
    @Autowired
    EmployeeService service;

    @GetMapping("/{id}")
    @ApiOperation(value = "通过ID查询员工" )
    public AjaxMessage queryOne(@PathVariable("id") Long id){
        try {
            Employee employee = service.queryOne(id);
            return AjaxMessage.success().addData("employee",employee).addMessage("查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            return  AjaxMessage.error().addMessage("查询失败");
        }
    }
    @GetMapping
    @ApiOperation(value = "查询所有员工" )
    public AjaxMessage queryAll(Integer pageNum,Integer pageSize,String keyword){
        if (keyword == null || (keyword = keyword.trim()).equals("")) {
            keyword = "";
        }
        try {
            if (pageNum == null){
                List<Employee> employees = service.queryAll(keyword);
                return AjaxMessage.success().addData("employees",employees).addMessage("查询成功");
            }else{
                if (pageSize == null || pageSize < 10){
                    pageSize = 10;
                }
                PageHelper.startPage(pageNum,pageSize);
                List<Employee> employees = service.queryAll(keyword);
                PageInfo<Employee> pageInfo = new PageInfo<>(employees, 5);
                return AjaxMessage.success().addData("pageInfo",pageInfo).addMessage("分页查询成功");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return AjaxMessage.error().addMessage("查询失败");
        }

    }

    @PostMapping
    @ApiOperation(value = "新增员工" )
    public AjaxMessage addOne( @RequestBody Employee employee){
        System.out.println(employee);
        try {
            service.addEmp(employee);
            return AjaxMessage.success().addMessage("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxMessage.error().addMessage("添加失败");
        }
    }


    @PutMapping
    @ApiOperation(value = "根据ID更新员工" )
    public AjaxMessage update(@RequestBody Employee employee){
        System.out.println(employee);
        try {
            if (employee.getId() == null || service.queryOne(employee.getId()) == null){
                return AjaxMessage.error().addMessage("修改失败");
            }
            service.updateEmp(employee);
            return AjaxMessage.success().addMessage("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxMessage.error().addMessage("修改失败");
        }
    }

    @DeleteMapping
    @ApiOperation(value = "根据ID删除员工" )
    public AjaxMessage delete(@RequestParam(value = "id") Long[] ids){
        try {
            service.deleteEmp(ids);
            return AjaxMessage.success().addMessage("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxMessage.error().addMessage("删除失败");
        }
    }
}
