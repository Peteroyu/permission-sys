package com.xmj.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class Department {
    private Long id;
    private String name;
    private String intro;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateDate;
    private Employee manager;
    private Department parent;

    public List<Department> getChild() {
        return child;
    }

    public void setChild(List<Department> child) {
        this.child = child;
    }

    private List<Department> child;
    private String path;
    private Integer state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public Department getParent() {
        return parent;
    }

    public void setParent(Department parent) {
        this.parent = parent;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", intro='" + intro + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", manager=" + manager +
                ", parent=" + parent +
                ", child=" + child +
                ", path='" + path + '\'' +
                ", state=" + state +
                '}';
    }

    public Department() {
    }

    public Department(Long id, String name, String intro, Date createDate, Date updateDate, Employee manager, Department parent, List<Department> child, String path, Integer state) {
        this.id = id;
        this.name = name;
        this.intro = intro;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.manager = manager;
        this.parent = parent;
        this.child = child;
        this.path = path;
        this.state = state;
    }
}
