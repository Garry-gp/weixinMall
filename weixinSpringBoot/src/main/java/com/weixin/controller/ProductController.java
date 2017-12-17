package com.weixin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.weixin.model.Product;
import com.weixin.model.ProductType;
import com.weixin.service.ProductService;

@RestController
@RequestMapping("/productInfo")
public class ProductController {
	
	@Autowired
	ProductService productService;

	
	//获取产品列表
	@RequestMapping("/findAll")
	public List<Product> findAll(){
		return productService.findAll();
	}
	
	//获取所有产品类型列表
	@RequestMapping("/findAllType")
	public List<ProductType> findAllType(){
		return productService.findAllType();
	}
	/**
	 * 根据商品ID查询
	 * @param id
	 * @return
	 */
	@RequestMapping("/findAllById")
	public Product findAllProductById(@Param("id") long id){
		return productService.findAllProductById(id);
	}
	
	/**
	 * 根据类型ID查询商品列表
	 * @param typeId
	 * @return
	 */
	@RequestMapping("/findByType")
	public List<Product> findByType(@Param("typeId") String typeId){
		Long tId = Long.valueOf(typeId);
		Product model = new Product();
		model.setTypeId(tId);
		return productService.findSearch(model);		
	}

}
