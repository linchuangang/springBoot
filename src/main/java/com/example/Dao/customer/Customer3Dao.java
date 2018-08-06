package com.example.Dao.customer;

import com.example.Entity.CustomerCoupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


/**
 * Created by lincg on 2017/5/2.
 */
public interface Customer3Dao extends JpaRepository<CustomerCoupon,Long>,JpaSpecificationExecutor<CustomerCoupon> {
    @Override
    Page<CustomerCoupon> findAll(Specification<CustomerCoupon> spec,Pageable page);

    List<CustomerCoupon> findByCustomerIdAndIdNot(long CustomerId,long id);
}
