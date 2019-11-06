package com.carlinx.es.service;

import com.carlinx.es.entity.Employee;
import org.springframework.data.domain.Sort;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @Author yj
 * @Create 2019/11/5 10:25
 *
 * 自定义es接口
 */

public interface EmployeeEsService {
    //新增或修改数据
    Boolean insertEmployee(Employee employee);

    //批量添加或修改
    Boolean insertEmployeeList(Collection<Employee> employees);

    //查询所有
    List<Employee> selectAll();

    //查询所有根据字段排序
    List<Employee> selectAllOrderByProperty(Sort.Direction sort, String property);

    //根据id查询
    Employee selectyId(Long id);

    //根据id批量查询
    List<Employee> selectByIdList(List<Long> ids);

    //单个删除
    Boolean remove(Employee employee);

    //删除全部
    Boolean removeAll();

    //批量删除
    Boolean removeBatch(Collection<Employee> employees);

    //根据id查询
    Boolean removeById(Long id);

    //根据年龄区间查询并倒序
    List<Employee> findByAgeBetweenOrOrderByAgeDesc(Integer startAge,Integer endAge);

    //姓名以*开头
    List<Employee> findByNameStartingWith(String startStr);

    //分页查询
    public List<Employee> findByPageable(Integer pageIndex,Integer pageSize);

    //根据属性值倒序查询
    public List<Employee> findByPropertyDesc(String property);

    //名字模糊查询并高亮
    public List<Employee> findByNameLikeAndHighLight(String name);

    //名字过滤查询
    public List<Employee> findByNameWithTermFilter(Collection<String> names);

    //年龄区间查询
    public List<Employee> findByAgeWithRangeFilter(Integer startAge,Integer endAge);

    //查询名字起始  桶聚合
    public Map findByNameStartingWithAndAggregations(String prefixName);

    /*** 嵌套查询： ** 先按年龄直方图（桶聚合）统计 * 然后再统计区间内员工的最高工资（度量聚合） */
    public Map aggregationsWithHistogramAndMax();

    /*** 日期直方图（桶聚合） */
    public Map aggregationsWithDateHistogram();



}
