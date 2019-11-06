package com.carlinx.es.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 准备映射实体类
 */


//文档直接  用来描述索引及其相关信息
@Document(indexName = "mi",type = "employee")
public class Employee implements Serializable {

    //主键
    @Id
    private Long id;
    //姓名
    @Field(type = FieldType.Text)
    private String name;
    //性别
    @Field(type = FieldType.Keyword)
    private String sex;
    //年龄
    @Field(type = FieldType.Integer)
    private Integer age;
    //职位
    @Field(type = FieldType.Text)
    private String position;
    //部门
    @Field(type = FieldType.Text)
    private String department;
    //地址
    @Field(type = FieldType.Text)
    private String address;
    //年龄
    @Field(type = FieldType.Date)
    private Date birthday;
    //花销
    @Field(type = FieldType.Double)
    private BigDecimal expense;


    public Employee() {
    }


    //从es中恢复数据时使用的构造方法
    @PersistenceConstructor
    public Employee(Long id, String name, String sex, Integer age, String position, String department, String address, Date birthday, BigDecimal expense) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.position = position;
        this.department = department;
        this.address = address;
        this.birthday = birthday;
        this.expense = expense;
    }

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public BigDecimal getExpense() {
        return expense;
    }

    public void setExpense(BigDecimal expense) {
        this.expense = expense;
    }


    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", position='" + position + '\'' +
                ", department='" + department + '\'' +
                ", address='" + address + '\'' +
                ", birthday=" + birthday +
                ", expense=" + expense +
                '}';
    }
}
