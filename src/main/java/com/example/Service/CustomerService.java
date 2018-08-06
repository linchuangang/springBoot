package com.example.Service;


import com.example.Dao.customer.Customer3Dao;
import com.example.Entity.CustomerCoupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.Date;
import java.util.List;

/**
 * Created by lincg on 2017/4/20.
 */
@Service
public class CustomerService {

    @Autowired
    Customer3Dao customer3Dao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<CustomerCoupon> getList() {
        String sql = "select id,coupon_name,p_customer_id as customerId from p_customer_coupon order by id limit 0,2";
        List<CustomerCoupon> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper(CustomerCoupon.class));

        return list;
    }

    public Page<CustomerCoupon> getListByCondition(String id) {

        return customer3Dao.findAll(Condition(id), new PageRequest(0, 5));
    }

    private Specification<CustomerCoupon> Condition(final String id) {
        return new Specification<CustomerCoupon>() {
            @Override
            public Predicate toPredicate(Root<CustomerCoupon> r, CriteriaQuery<?> q, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                Path<Long> path = r.get("customerId");
                predicate.getExpressions().add(cb.equal(path, "13773"));
                if (id != null) {
                    predicate.getExpressions().add(cb.equal(r.<Long>get("id"), id));
                }
                predicate.getExpressions().add(cb.like(r.<String>get("couponName"), "%蛋%"));
                q.orderBy(cb.desc(r.<Date> get("createdAt").as(Date.class)));
                return predicate;
            }
        };
    }
}
