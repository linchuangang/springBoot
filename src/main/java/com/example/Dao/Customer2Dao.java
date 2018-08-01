package com.example.Dao;

import com.example.Entity.CustomerCoupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by lincg on 2017/4/20.
 */
public interface Customer2Dao extends PagingAndSortingRepository<CustomerCoupon,Long>{
}
