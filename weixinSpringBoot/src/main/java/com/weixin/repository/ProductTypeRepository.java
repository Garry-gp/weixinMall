package com.weixin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import com.weixin.model.Order;
import com.weixin.model.ProductType;

@Service
public interface ProductTypeRepository extends JpaRepository<ProductType, Long>,JpaSpecificationExecutor<Order> {	
	//获取所有的类型
	public List<ProductType> findAll();

}
