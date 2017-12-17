package com.weixin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.weixin.model.Cart;
import com.weixin.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {
	@Autowired
	CartService cartService;	
	/**
	 * 加载购物车中的所有商品
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<Cart> findAll(HttpServletRequest request, HttpServletResponse response){
		//获取web容器中存在的数据
		//request.getAttribute("userId");
		//获取当前请求参数
		String oId = request.getParameter("openId");
		if(oId == null){
			return null;
		}
		Cart model = new Cart();
		model.setOpenId(oId);
		return cartService.findAll(model);
	}
	
	/**
	 * 查询购物车是否存在该商品，存在则点击一次数量+1
	 * 不存在则插入一条商品信息
	 */
	@RequestMapping("/add")
	public boolean addProductToCart(HttpServletRequest request, Cart model){
		List<Cart> list = cartService.findAll(model);
		if(list.size()==0){
			//插入一条新的数据
			cartService.saveModel(model);
		}else{
			//更新购物车中商品的数量
			Cart ct = list.get(0);
			cartService.addCart("add",ct);
		}
		return true;
	}
	
	/**
	 * 根据id删除购物车中的商品
	 * @param
	 */
	@RequestMapping("/remove")
	public boolean moveItemFromCart(HttpServletRequest request,
			HttpServletResponse response, Cart ct){
		if(request.getParameter("id").isEmpty()){
			return false;
		}	
		cartService.removeItemFromCart(ct);
		return true;
	}
	
	/**
	 * 统计购物车中，被选中的商品的中价格
	 * @param request
	 * @param response
	 * @param ct
	 * @return
	 */
	@RequestMapping("/countPrice")
	public String countPriceFromCart(HttpServletRequest request,
			HttpServletResponse response, Cart ct){
		return cartService.computeTotal(ct).toString();
	}
	
	/**
	 * 更新购物车中的商品
	 * @param request
	 * @param response
	 * @param ct
	 * @return
	 */
	@RequestMapping("/update")
	public boolean updateCart(HttpServletRequest request,
			HttpServletResponse response, Cart ct){
		Cart cart = cartService.findCartByOne(ct.getId());
		if(ct.getStatus() != cart.getStatus() && ct.getStatus()!=0){
			cart.setStatus(ct.getStatus());
		}
		if(ct.getProductCount() != cart.getProductCount() && ct.getProductCount() !=0){
			cart.setProductCount(ct.getProductCount());
		}
		cartService.addCart("edit", cart);
		return true;
	}
	@RequestMapping("/statusAll")
	public void statusAll(HttpServletRequest request, HttpServletResponse response){
		String oId = request.getParameter("openId");
		String st = request.getParameter("status");
		int status =Integer.parseInt(st);
		if(status!=0){
			cartService.update(oId,status);
		}
	}
}
