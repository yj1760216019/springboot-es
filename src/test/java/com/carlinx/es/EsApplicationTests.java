package com.carlinx.es;

import cn.hutool.json.JSONUtil;
import com.carlinx.es.entity.Employee;
import com.carlinx.es.service.EmployeeEsService;
import net.bytebuddy.asm.Advice;
import org.elasticsearch.client.ElasticsearchClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EsApplicationTests {
    @Autowired
    private EmployeeEsService employeeEsService;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private ElasticsearchClient elasticsearchClient;


    @Test
    public void testInsert(){
        Employee employee1 = new Employee(5L, "雷二", "男", 1, "董事长", "董事", "湖北省仙桃市", new Date(), new BigDecimal(250000));
        employeeEsService.insertEmployee(employee1);
    }


    @Test
    public void testInsertList(){
        List<Employee> employees = new ArrayList<>();
        Employee employee1 = new Employee(1L, "雷军", "男", 53, "董事长", "董事", "湖北省仙桃市", new Date(), new BigDecimal(250000));
        employees.add(employee1);
        Employee employee2 = new Employee(2L, "林斌", "男", 49, "总裁", "董事", "广东省广州市", new Date(), new BigDecimal(15000));
        employees.add(employee2);
        Employee employee3 = new Employee(3L, "王川", "男", 48, "电视部总经理", "电视部", "辽宁省沈阳市", new Date(), new BigDecimal(14000));
        employees.add(employee3);
        Employee employee4 = new Employee(4L, "魏思琪", "女", 39, "手机部产品经理", "手机部", "陕西省西安市", new Date(), new BigDecimal(1200));
        employees.add(employee4);
        employeeEsService.insertEmployeeList(employees);
    }



    @Test
    public void testSelectAll(){
        List<Employee> employees = employeeEsService.selectAll();
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }


    @Test
    public void testSelectAllOrder(){
        List<Employee> employees = employeeEsService.selectAllOrderByProperty(Sort.Direction.ASC, "id");
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }


    @Test
    public void testSelectById(){
        Employee employee = employeeEsService.selectyId(1L);
        System.out.println(employee);
    }



    @Test
    public void testSelectByIdList(){
        List<Employee> employees = employeeEsService.selectByIdList(Arrays.asList(1L, 2L));
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }



    @Test
    public void testRemove(){
        Employee employee = new Employee();
        employee.setId(6L);
        employeeEsService.remove(employee);
    }


    @Test
    public void testSelectByAgeBetweenOrOrderByAgeDesc(){
        List<Employee> employees = employeeEsService.findByAgeBetweenOrOrderByAgeDesc(49, 55);
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }


    @Test
    public void testSelectByNameStartingWith(){
        List<Employee> employees = employeeEsService.findByNameStartingWith("雷");
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }


    @Test
    public void testSelectByPageable(){
        List<Employee> employees = employeeEsService.findByPageable(1, 2);
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }



    @Test
    public void testSelectByPropertyDesc(){
        List<Employee> idEmployees = employeeEsService.findByPropertyDesc("id");
        for (Employee employee : idEmployees) {
            System.out.println(employee);
        }
    }



    @Test
    public void testSelectByNameLikeAndHighLight(){
        List<Employee> employees = employeeEsService.findByNameLikeAndHighLight("魏");
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }



    @Test
    public void testFindByNameWithTermFilter(){
        List<Employee> employees = employeeEsService.findByNameWithTermFilter(Arrays.asList("雷", "林"));
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }


    @Test
    public void testSelectByAgeWithRangeFilter(){
        List<Employee> employees = employeeEsService.findByAgeWithRangeFilter(47, 55);
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }


    @Test
    public void testSelectByNameStartingWithAndAggregations(){
        Map result = employeeEsService.findByNameStartingWithAndAggregations("雷");
        System.out.println(JSONUtil.toJsonStr(result));
    }


    @Test
    public void testAggregationsWithHistogramAndMax(){
        Map map = employeeEsService.aggregationsWithHistogramAndMax();
        System.out.println(JSONUtil.toJsonStr(map));
    }


    @Test
    public void testAggregationsWithDateHistogram(){
        Map map = employeeEsService.aggregationsWithDateHistogram();
        System.out.println(JSONUtil.toJsonStr(map));
    }


    @Test
    public void testUpdateEmployee(){
        Employee employee = employeeEsService.selectyId(2L);
        Calendar instance = GregorianCalendar.getInstance();
        instance.add(GregorianCalendar.YEAR,-1);
        employee.setBirthday(instance.getTime());
        employeeEsService.insertEmployee(employee);
    }


    @Test
    public void testUpdate(){

    }



}
