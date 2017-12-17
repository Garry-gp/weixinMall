package com.weixin.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.jpa.internal.EntityManagerFactoryRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.weixin.model.Cart;
import com.weixin.model.Order;
import com.weixin.model.Product;
import com.weixin.repository.CartRepository;
import com.weixin.repository.OrderRepository;
import com.weixin.repository.ProductRepository;

@Service
public class OrderService {
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	OrderRepository orderRepository;
	@PersistenceContext
	private EntityManager entityManager;
	
	public List<Order> findAll(){
		return orderRepository.findAll();
	}

	/**
	 * 根据订单ID，修改订单状态
	 * @return
	 */
	public Order updateOrderById(Long id,Long status){
		Order order = orderRepository.findOne(id);
		order.setStatus(status);
		return orderRepository.save(order);
	}
	//更新product表的库存
	public boolean update(String productId,String count){
		long id = Integer.parseInt(productId);
		long inventory = Integer.parseInt(count);
		Product list = productRepository.findOne(id);
		if(inventory>0||inventory==0){
			list.setInventory(list.getInventory()-inventory);
			list.setSell(list.getSell()+inventory);
			productRepository.save(list);
		}else{
			return false;
		}
		return true;
	}
	public void updateCart(String cartId){
		long id =(long) Integer.parseInt(cartId);
		cartRepository.delete(id);
	}
	public void save(String product_id,String count, String open_id,String order_price){
		double price=Double.parseDouble(order_price);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmm");
		String order_no = sdf.format(new Date());
		Order order = new Order();
		order.setCount(count);
		order.setCreateData(new Date());
		order.setUpdateDate(new Date());
		order.setOpenId(open_id);
		order.setOrderNo(order_no);
		order.setOrderPrice(price);
		order.setProductId(product_id);
		order.setStatus(1);
		orderRepository.save(order);
	}
	/**
	 * 根据model属性查询
	 * @param model
	 * @return
	 */
	public List<Order> findSearch(final Order model){
		
		if(model==null){
			return null;
		}
		List<Order> result = orderRepository.findAll(new Specification<Order>(){
			@Override
			public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				//根据用户ID查询
				if (model.getOpenId() != null) {
	                list.add(cb.equal(root.get("openId").as(String.class), ""+model.getOpenId()));
	            }
				//支持根据订单编号查询
				if (model.getOrderNo()!=null) {
		            list.add(cb.equal(root.get("orderNo").as(String.class), model.getOrderNo()));
		        }
				if (model.getStatus() != 0) {
	                list.add(cb.equal(root.get("status").as(String.class), ""+model.getStatus()));
	            }
				//TODO 根据价格查询
				Predicate[] p = new Predicate[list.size()];
		        return cb.and(list.toArray(p));
			}
		});
		return result;
	}
}
