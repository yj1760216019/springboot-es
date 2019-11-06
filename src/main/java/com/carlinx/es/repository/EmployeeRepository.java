package com.carlinx.es.repository;

import com.carlinx.es.entity.Employee;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;


/**
 * 基础的es repository操作接口（通用的增删改查方法）
 */
public interface EmployeeRepository extends ElasticsearchRepository<Employee,Long> {

    /**
     *方法名符合一定的规范  spring data 自动生成实现类
     */

    //年龄区间查询 倒序排序
    public List<Employee> findByAgeBetweenOrderByAgeDesc(Integer start,Integer end);

    //查询姓名以*开头的数据
    public List<Employee> findByNameStartingWith(String startStr);

    //通过Query注解自定义查询表达式

}
