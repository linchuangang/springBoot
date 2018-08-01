package com.example.Dao;

import com.example.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/7/31.
 */
public interface UserDao extends JpaRepository<User,Long>,JpaSpecificationExecutor<User>,Serializable{

}
