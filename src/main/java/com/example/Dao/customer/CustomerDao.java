package com.example.Dao.customer;

import com.example.Entity.CustomerCoupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by lincg on 2017/4/19.
 */

public interface CustomerDao extends JpaRepository<CustomerCoupon, Long>{
    @Transactional
    List<CustomerCoupon> findByCustomerId(Long id);

    @Query("select c from CustomerCoupon c where c.id=:id")
    @Transactional
    CustomerCoupon get(@Param("id") long id);



}
