package com.weixin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.weixin.model.Users;

@Service
public interface UserRepository extends JpaRepository<Users, Long>, CrudRepository<Users, Long>,
JpaSpecificationExecutor<Users>{
	//添加查詢方法
	List<Users> findAll();

    Users findByName(String name);
}
