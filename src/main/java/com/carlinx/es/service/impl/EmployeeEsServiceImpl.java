package com.carlinx.es.service.impl;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.carlinx.es.entity.Employee;
import com.carlinx.es.repository.EmployeeRepository;
import com.carlinx.es.service.EmployeeEsService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

import static org.elasticsearch.index.query.QueryBuilders.*;


@Service
public class EmployeeEsServiceImpl implements EmployeeEsService {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeEsServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;




    /************************************spring data 简单的增删改查功能***************************************/

    /**
     * 新增或者修改数据
     * @param employee
     * @return
     */
    @Override
    public Boolean insertEmployee(Employee employee){
        try {
            employeeRepository.save(employee);
            return true;
        }catch (Exception e){
            Throwable throwable = e.getCause();
            logger.info("添加{}失败  失败原因:{}",JSONUtil.toJsonStr(employee),throwable.getMessage());
            return false;
        }
    }


    /**
     * 批量添加
     * @param employees
     * @return
     */
    @Override
    public Boolean insertEmployeeList(Collection<Employee> employees){
        try {
            employeeRepository.saveAll(employees);
            return true;
        }catch (Exception e){
            Throwable throwable = e.getCause();
            logger.info("批量添加失败  失败原因:{}",throwable.getMessage());
            return false;
        }
    }


    /**
     * 查询所有
     * @return
     */
    @Override
    public List<Employee> selectAll(){
        try {
            Iterable<Employee> employees = employeeRepository.findAll();
            List<Employee> employeeList = CollUtil.newArrayList(employees);
            return employeeList;
        }catch (Exception e){
            Throwable throwable = e.getCause();
            logger.info("查询全部失败   失败原因{}",throwable.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }


    /**
     * 查询所有（根据字段排序）
     * @param sort
     * @param property
     * @return
     */
    @Override
    public List<Employee> selectAllOrderByProperty(Sort.Direction sort,String property){
        try {
            Iterable<Employee> employees = employeeRepository.findAll(Sort.by(sort, property));
            List<Employee> employeeList = CollUtil.newArrayList(employees);
            return employeeList;
        }catch (Exception e){
            Throwable throwable = e.getCause();
            logger.info("排序查询全部失败  失败原因{}",throwable.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }


    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Override
    public Employee selectyId(Long id){
        try {
            Optional<Employee> employee = employeeRepository.findById(id);
            return employee.get();
        }catch (Exception e){
            Throwable throwable = e.getCause();
            logger.info("根据id查询失败  失败原因:{}",throwable.getMessage());
            throw new RuntimeException(throwable.getMessage());
        }
    }


    /**
     * 根据id集合批量查询
     * @param ids
     * @return
     */
    @Override
    public List<Employee> selectByIdList(List<Long> ids){
        try {
            Iterable<Employee> employees = employeeRepository.findAllById(ids);
            List<Employee> employeeList = CollUtil.newArrayList(employees);
            return employeeList;
        }catch (Exception e){
            Throwable throwable = e.getCause();
            logger.info("根据id集合批量查询失败  失败原因:{}",throwable.getMessage());
            throw  new RuntimeException(throwable.getMessage());
        }
    }


    /**
     * 单个删除（主要针对id删除   主键值不能为空）
     * @param employee
     * @return
     */
    @Override
    public Boolean remove(Employee employee){
        try {
            employeeRepository.delete(employee);
            return true;
        }catch (Exception e){
            Throwable throwable = e.getCause();
            logger.info("单个删除失败  失败原因:{}",throwable.getMessage());
            throw new RuntimeException(throwable.getMessage());
        }
    }


    /**
     * 删除全部
     * @return
     */
    @Override
    public Boolean removeAll(){
        try {
            employeeRepository.deleteAll();
            return true;
        }catch (Exception e){
            Throwable throwable = e.getCause();
            logger.info("删除全部失败  失败原因:{}",throwable.getMessage());
            throw new RuntimeException(throwable.getMessage());
        }
    }


    /**
     * 批量删除
     * @param employees
     * @return
     */
    @Override
    public Boolean removeBatch(Collection<Employee> employees){
        try {
            employeeRepository.deleteAll(employees);
            return true;
        }catch (Exception e){
            Throwable throwable = e.getCause();
            logger.info("批量删除失败  失败原因{}",throwable.getMessage());
            throw  new RuntimeException(throwable.getMessage());
        }
    }


    /**
     * 根据id删除
     * @param id
     * @return
     */
    @Override
    public Boolean removeById(Long id){
        try {
            employeeRepository.deleteById(id);
            return true;
        }catch (Exception e){
            Throwable throwable = e.getCause();
            logger.info("根据id删除失败  失败原因:{}",throwable.getMessage());
            throw new RuntimeException(throwable.getMessage());
        }
    }




    /************************************名字符合一定规则 spring data 自动生成实现类***************************************/


    /**
     * 根据年龄区间查询并倒序
     * @return
     */
    @Override
    public List<Employee> findByAgeBetweenOrOrderByAgeDesc(Integer startAge,Integer endAge){
        try {
            List<Employee> employees = employeeRepository.findByAgeBetweenOrderByAgeDesc(startAge, endAge);
            return employees;
        }catch (Exception e){
            Throwable throwable = e.getCause();
            logger.info("根据年龄区间查询并倒序查询失败  失败原因:{}",throwable.getMessage());
            throw new RuntimeException(throwable.getMessage());
        }
    }


    /**
     * 姓名以*开头查询
     * @param startStr
     * @return
     */
    @Override
    public List<Employee> findByNameStartingWith(String startStr){
        try {
            List<Employee> employees = employeeRepository.findByNameStartingWith(startStr);
            return employees;
        }catch (Exception e){
            Throwable throwable = e.getCause();
            logger.info("姓名以{}开头查询失败  失败原因:{}",startStr,throwable.getMessage());
            throw new RuntimeException(throwable.getMessage());
        }
    }



    /************************************自定义实现类***************************************/


    /**
     * 自定义分页查询
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public List<Employee> findByPageable(Integer pageIndex, Integer pageSize) {
       try {
           NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                   .withQuery(matchAllQuery())
                   .withPageable(new PageRequest((pageIndex - 1) * pageSize, pageSize))
                   .build();
           return elasticsearchTemplate.queryForList(searchQuery,Employee.class);
       }catch (Exception e){
           Throwable throwable = e.getCause();
           logger.info("自定义分页查询失败  失败原因:{}",throwable.getMessage());
           throw new RuntimeException(throwable.getMessage());
       }
    }


    /**
     * 自定义属性排序查询
     * @param property  (不支持文本类型排序)
     * @return
     */
    @Override
    public List<Employee> findByPropertyDesc(String property) {
       try {
           NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                   .withQuery(matchAllQuery())
                   .withSort(SortBuilders.fieldSort(property).order(SortOrder.DESC))
                   .build();

           return elasticsearchTemplate.queryForList(searchQuery,Employee.class);
       }catch (Exception e){
           Throwable throwable = e.getCause();
           logger.info("自定义属性排序查询失败  失败原因{}",throwable.getMessage());
           throw new RuntimeException(throwable.getMessage());
       }
    }


    /**
     * 自定义姓名查询并高亮
     * @param name
     * @return
     */
    @Override
    public List<Employee> findByNameLikeAndHighLight(String name) {
       try {
           NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                   .withQuery(matchQuery("name", name))
                   .withHighlightFields(new HighlightBuilder.Field("name"))
                   .build();

           AggregatedPage<Employee> employees = elasticsearchTemplate.queryForPage(searchQuery, Employee.class, new SearchResultMapper() {
               @Override
               public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                   List<Employee> employees = new ArrayList<Employee>();
                   SearchHits searchHits = searchResponse.getHits();
                   if (searchHits.getHits().length <= 0) {
                       return null;
                   }
                   for (SearchHit searchHit : searchHits) {
                       Employee employee = new Employee();
                       //主键
                       employee.setId(Long.parseLong(searchHit.getId()));
                       //Integer 类型
                       employee.setAge(Integer.parseInt(searchHit.getSourceAsMap().get("age").toString()));
                       //String类型
                       employee.setAddress(searchHit.getSourceAsMap().get("address").toString());
                       employee.setSex(searchHit.getSourceAsMap().get("sex").toString());
                       //日期类型
                       employee.setBirthday(new Date(Long.parseLong(searchHit.getSourceAsMap().get("birthday").toString())));
                       employee.setDepartment(searchHit.getSourceAsMap().get("department").toString());
                       employee.setPosition(searchHit.getSourceAsMap().get("position").toString());
                       //Bigdecimal类型
                       employee.setExpense(new BigDecimal(Double.valueOf(searchHit.getSourceAsMap().get("expense").toString())));

                       //获取高亮字段
                       String name = searchHit.getHighlightFields().get("name").fragments()[0].toString();
                       employee.setName(name);
                       //将从es中查询到的对象添加到集合中
                       employees.add(employee);
                   }
                   return new AggregatedPageImpl<T>((List<T>) employees);
               }
           });
           return employees.getContent();

       }catch (Exception e){
           Throwable throwable = e.getCause();
           logger.info("自定义姓名查询并高亮查询失败  失败原因{}",throwable.getMessage());
           throw new RuntimeException(throwable.getMessage());
       }
    }


    /**
     * 自定义姓名过滤查询
     * @param names
     * @return
     */
    @Override
    public List<Employee> findByNameWithTermFilter(Collection<String> names) {
        try {
            NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                    .withQuery(matchAllQuery())
                    .withFilter(termsQuery("name", names))
                    .build();
            return elasticsearchTemplate.queryForList(searchQuery,Employee.class);
        }catch (Exception e){
            Throwable throwable = e.getCause();
            logger.info("自定义姓名过滤查询失败  失败原因:{}",throwable.getMessage());
            throw new RuntimeException(throwable.getMessage());
        }
    }


    /**
     * 自定义年龄区间查询
     * @param startAge
     * @param endAge
     * @return
     */
    @Override
    public List<Employee> findByAgeWithRangeFilter(Integer startAge, Integer endAge) {
        try {
            NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                    .withFilter(rangeQuery("age").gte(startAge).lte(endAge))
                    .build();
            return elasticsearchTemplate.queryForList(searchQuery,Employee.class);
        }catch (Exception e){
            Throwable throwable = e.getCause();
            logger.info("自定义年龄区间查询失败  失败原因:{}",throwable.getMessage());
            throw new RuntimeException(throwable.getMessage());
        }
    }


    /**
     *  名字以prefixName开头的数据  年龄平均值聚合
     * @param prefixName
     * @return
     */
    @Override
    public Map findByNameStartingWithAndAggregations(String prefixName) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(prefixQuery("name", prefixName))
                //result 为度量聚合的结果名   度量 平均值
                .addAggregation(AggregationBuilders.avg("ageAvg").field("age"))
                .build();
        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {

            @Override
            public Aggregations extract(SearchResponse searchResponse) {
                Aggregations aggregations = searchResponse.getAggregations();
                return aggregations;
            }
        });
        Map<String, Aggregation> map = aggregations.getAsMap();
        return map;
    }


    /**
     * 消费金额  年龄 直方图聚合
     * @return
     */
    @Override
    public Map aggregationsWithHistogramAndMax() {

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .addAggregation(AggregationBuilders.histogram("result").field("age").interval(5)
                        .subAggregation(AggregationBuilders.max("max_expense").field("expense")))
                .build();
        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {
            @Override
            public Aggregations extract(SearchResponse searchResponse) {
                return searchResponse.getAggregations();
            }
        });
        return aggregations.getAsMap();
    }


    /**
     * 时间直方图聚合
     * @return
     */
    @Override
    public Map aggregationsWithDateHistogram() {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .addAggregation(AggregationBuilders.dateHistogram("result").field("birthday").format("yyyy-MM-dd")
                        .dateHistogramInterval(DateHistogramInterval.YEAR))
                .build();
        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {
            @Override
            public Aggregations extract(SearchResponse searchResponse) {
                return searchResponse.getAggregations();
            }
        });
        Map<String, Aggregation> map = aggregations.getAsMap();
        return map;
    }












}
