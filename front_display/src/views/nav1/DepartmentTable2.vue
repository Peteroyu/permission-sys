<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="keyword">
        <el-form-item>
          <el-input v-model="keyword" placeholder="查询条件" @change="getDepartments"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getDepartments">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleAdd">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table :data="departments" highlight-current-row v-loading="listLoading" @selection-change="selsChange"
              style="width: 100%;">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="编号" width="80" sortable></el-table-column>
      <el-table-column prop="name" label="部门名" width="120" sortable></el-table-column>
      <el-table-column prop="intro" label="简介" width="200" sortable></el-table-column>
      <el-table-column prop="manager.username" label="主管" width="120" sortable></el-table-column>
      <el-table-column prop="parent.name" label="上级部门" width="120" sortable></el-table-column>
      <el-table-column prop="path" label="路径" width="150" sortable></el-table-column>
      <el-table-column prop="state" label="状态" width="80" sortable>
        <template slot-scope="scope">
          <el-tag :type="scope.row.state == 1 ? 'primary' : 'danger'" disable-transitions>
            {{ scope.row.state === '1' ? '正常' : '销毁' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="updateDate" label="修改时间" width="100" sortable></el-table-column>
      <el-table-column prop="createDate" label="创建时间" width="100" sortable></el-table-column>
      <el-table-column label="操作" width="150">
        <template scope="scope">
          <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button type="danger" size="small" @click="handleDel(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--工具条-->
    <el-col :span="24" class="toolbar">
      <el-button type="danger" @click="batchRemove" :disabled="this.sels.length===0">批量删除</el-button>
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page.sync="pageNum"
          :page-sizes="[10, 20, 30, 50]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          style="float:right;">
      </el-pagination>
    </el-col>

    <!--编辑界面-->
    <el-dialog :title="title"
               :visible.sync="editFormVisible"
               :close-on-click-modal="false">
      <el-form :model="editForm" label-width="80px" :rules="editFormRules" ref="editForm">
        <el-form-item label="部门名">
          <el-input v-model="editForm.name" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="简介">
          <el-input v-model="editForm.intro" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="主管">
          <el-select v-model="editForm.manager" clearable value-key="id" placeholder="请选择">
            <!--下拉选项
              :key=""  //唯一标识
              :label 选择之后展示到选择框中的值
              :value 选中之后绑定给模型层的值  如果要绑定对象给模型层 有一个大坑
                 必须要写  value-key="id"
            -->
            <el-option v-for="item in managers" :key="item.id" :label="item.username" :value="item">
              <span style="float: left">{{ item.username }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="上级部门">
          <el-select v-model="editForm.parent" clearable value-key="id" placeholder="请选择">
            <!--下拉选项
              :key=""  //唯一标识
              :label 选择之后展示到选择框中的值
              :value 选中之后绑定给模型层的值  如果要绑定对象给模型层 有一个大坑
                 必须要写  value-key="id"
            -->
            <el-option v-for="item in all_departments" :key="item.id" :label="item.name" :value="item">
              <span style="float: left">{{ item.name }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="路径">
          <el-input v-model="editForm.path" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-input v-model="editForm.state" auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="editFormVisible = false">取消</el-button>
        <el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
      </div>
    </el-dialog>
  </section>
</template>

<script>
// import util from '../../common/js/util'
//import NProgress from 'nprogress'
// import { getUserListPage, removeUser, batchRemoveUser, editUser, addUser } from '../../api/api';

import {removeUser} from "../../api/api";

export default {
  data() {
    return {
      title: '',
      keyword: '',
      employees: [],
      departments: [],
      all_departments: [],
      managers: [],
      users: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      listLoading: false,
      sels: [],//列表选中列

      editFormVisible: false,//编辑界面是否显示
      editLoading: false,
      editFormRules: {
        username: [
          {required: true, message: '请输入姓名', trigger: 'blur'}
        ]
      },
      //编辑界面数据
      editForm: {
        id: null,
        name: '',
        intro: '',
        manager: {
          id: null,
          name: ''
        },
        parent: {
          id: null,
          name: ''
        },
        path: '',
        state: null,
        createDate: '',
        updateDate: ''
      },
    }
  },
  methods: {

    handleCurrentChange(val) {
      this.pageNum = val;
      this.getDepartments();
    },
    handleSizeChange(val){
      this.pageSize = val;
      this.getDepartments();
    },
    //获取用户列表
    getDepartments() {
      let para = {
        pageNum: this.pageNum,
        pageSize: this.pageSize,
        keyword: this.keyword
      };
      this.listLoading = true;
      //NProgress.start();
      this.$http.get("/dept", {params: para}).then((result) => {
        console.log(result.data.data.pageInfo.list);
        this.departments = result.data.data.pageInfo.list;
        this.total = result.data.data.pageInfo.total;
        this.listLoading = false;
      });
    },
    handleDel: function (row) {
      this.$confirm('确认删除该记录吗?', '提示', {type: 'warning'}).then(() => {
        this.listLoading = true;
        //NProgress.start();
        let id = row.id;
        this.$http.delete("/dept", {params: {id: id}}).then((result) => {
          this.listLoading = false;
          if (result.data.code == 200) {
            this.$message({
              message: result.data.message,
              type: 'success'
            });
            this.getDepartments();
          } else {
            this.$message({
              message: result.data.message,
              type: 'error'
            });
          }
        });

      }).catch(() => {

      });
    },
    selsChange: function (sels) {
      this.sels = sels;
    },
    //批量删除
    batchRemove: function () {
      let ids = this.sels.map(item => item.id).toString();
      console.log(ids);
      this.$confirm('确认删除选中记录吗？', '提示', {
        type: 'warning'
      }).then(() => {
        this.listLoading = true;
        //NProgress.start();
        let para = {ids: ids};
        this.$http.delete("/dept", {params: {id: ids}}).then((result) => {
          this.listLoading = false;
          if (result.data.code == 200) {
            this.$message({
              message: result.data.message,
              type: 'success'
            });
            this.getDepartments();
          } else {
            this.$message({
              message: result.data.message,
              type: 'error'
            });
          }
        });
      }).catch(() => {

      });
    },


    //显示编辑界面
    handleEdit: function (row) {
      this.title = '编辑';
      this.editFormVisible = true;
      this.getAllEmployees();
      this.getAllDepartments();
      this.editForm = Object.assign({}, row);
      if (!this.editForm.parent) {
        this.editForm.parent = {id: null, name: ''};
      }
    },
    //显示新增界面
    handleAdd: function () {
      this.title = '新增';
      this.editFormVisible = true;
      this.editForm = {
        id: null,
        name: '',
        intro: '',
        manager: {
          id: null,
          name: ''
        },
        parent: {
          id: null,
          name: ''
        },
        path: '',
        state: null
      };
      this.getAllEmployees();
      this.getAllDepartments();
    },
    //保存提交
    editSubmit: function () {
      this.$refs.editForm.validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.editLoading = true;
            //NProgress.start();
            let para = Object.assign({}, this.editForm);
            if (para.id != null) {
              this.$http.put("/dept", para).then((result) => {
                console.log("====================")
                console.log(result.data)
                this.editLoading = false;
                if (result.data.code == 200) {
                  this.$message({
                    message: result.data.message,
                    type: 'success'
                  });
                  this.$refs['editForm'].resetFields();
                  this.editFormVisible = false;
                  this.getDepartments();
                } else {
                  this.$message({
                    message: result.data.message,
                    type: 'error'
                  });
                }
              });
            } else {
              this.$http.post("/dept", para).then((result) => {
                this.editLoading = false;
                if (result.data.code == 200) {
                  this.$message({
                    message: result.data.message,
                    type: 'success'
                  });
                  this.$refs['editForm'].resetFields();
                  this.editFormVisible = false;
                  this.pageNum = this.total + 1;
                  this.getDepartments();
                } else {
                  this.$message({
                    message: result.data.message,
                    type: 'error'
                  });
                }
              });
            }
          });
        }
      });
    },
    getAllEmployees: function () {
      this.$http.get("/emp").then(result => {
        console.log(result);
        this.managers = result.data.data.employees;
      }).catch(result => {
        this.$message({message: '查询员工失败', type: 'error'});
      })
    },
    getAllDepartments: function () {
      this.$http.get("/dept").then(result => {
        console.log(result);
        this.all_departments = result.data.data.departments;
      }).catch(result => {
        this.$message({message: '查询部门失败', type: 'error'});
      })
    }

  },
  mounted() {
    this.getDepartments();
  }
}

</script>

<style scoped>

</style>