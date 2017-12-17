package com.weixin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import com.weixin.model.Cart;

@Service
public interface CartRepository extends JpaRepository<Cart, Long>,JpaSpecificationExecutor<Cart>{

}
