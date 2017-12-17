package com.weixin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.weixin.model.Product;

@Service
public interface ProductRepository extends JpaRepository<Product, Long>,JpaSpecificationExecutor<Product>{
}
