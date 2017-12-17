package com.weixin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weixin.model.Cart;
import com.weixin.model.Order;
import com.weixin.service.CartService;
import com.weixin.service.OrderService;

@RestController
@RequestMapping("/orderInfo")
//@SpringApplicationConfiguration(Application.class)
public class OrderController {

	@Autowired
	CartService cartService;
	
	@Autowired
	OrderService orderService;
	
	/**
	 * 根据当前的用户ID，查询用户下的全部订单
	 * @param
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<Order> findAll(HttpServletRequest request, HttpServletResponse response){
		String oId = request.getParameter("openId");
		if(oId==null){
			return null;
		}
		Order model =new Order();
		model.setOpenId(oId);;
		return orderService.findSearch(model);			
	}
	@RequestMapping(value="/findByStatus")
	public List<Order> findByStatus(HttpServletRequest request, HttpServletResponse response){
		String openId = request.getParameter("openId");
		long status = Integer.parseInt(request.getParameter("status"));
		Order model = new Order();
		model.setStatus(status);
		model.setOpenId(openId);
		List<Order> list = orderService.findSearch(model);
		for(Order i:list){
			System.out.println(i.getCreateData());
		}
		return list;
	}
	//提交保存订单
	@RequestMapping(value="/save", method = RequestMethod.POST)
	public boolean saveOrder(@Param("orderId") String orderId,@Param("status") String status){
		Long id = Long.valueOf(orderId);
		Long sta = Long.valueOf(status);
		Order order = orderService.updateOrderById(id, sta);
		if(order!=null){
			return true;
		}
		return false;
	}
	//提取数字
	static List<String> resolve(String str){
		String [] c = str.split("-");
		
		List<String> cls = new ArrayList<String>();
		for(int i=0; i<c.length;i++){
			cls.add(c[i]);
		}
		return cls;
	}
	
	@RequestMapping(value="/save1")
	public boolean save(HttpServletRequest request,HttpServletResponse response){
		//更新库存
		String productId = request.getParameter("productId");
		String productCount = request.getParameter("count");
		String openId = request.getParameter("openId");
		String price = request.getParameter("total");
		String cartId = request.getParameter("id");
		List<String> list = this.resolve(productId);
		List<String> count = this.resolve(productCount);
		List<String> id = this.resolve(cartId);
		for(int i=0;i<list.size();i++){
			productId = list.get(i);
			productCount = count.get(i);
			cartId = id.get(i);
			boolean f = orderService.update(productId, productCount);
			if(!f){
				return false;
			}else{
				orderService.updateCart(cartId);
			}
		}
		//订单保存
		orderService.save(request.getParameter("productId"), request.getParameter("count"), openId, price);
		return true;
	}
	//订单编辑
	@RequestMapping(value="/edit", method = RequestMethod.POST)
	public boolean editOrder(){
		//TODO 
		return true;
	}
	
}
