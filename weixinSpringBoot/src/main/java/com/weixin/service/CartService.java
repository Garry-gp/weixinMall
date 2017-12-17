package com.weixin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weixin.model.Cart;
import com.weixin.model.Product;
import com.weixin.repository.CartRepository;
import com.weixin.repository.ProductRepository;

@Service
public class CartService {
	
	@Autowired
	CartRepository cartRepository;
	@Autowired
	ProductRepository productRepository;
	@PersistenceContext
	private EntityManager entityManager;

	
	//获取用客户下购物车中的所有产品
	public List<Cart> findAll(final Cart model){
		List<Cart> result = cartRepository.findAll(new Specification<Cart>(){
			@Override
			public Predicate toPredicate(Root<Cart> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				//根据类型ID查询
				if (model.getOpenId() != null) {
	                list.add(cb.equal(root.get("openId").as(String.class), ""+model.getOpenId()));
	            }
				//根据类型ID查询
				if (model.getProduct() != null) {
					list.add(cb.equal(root.get("product_id").as(String.class), ""+model.getProduct().getId()));
				}
				if (model.getStatus() ==1) {
					list.add(cb.equal(root.get("status").as(String.class), ""+model.getStatus()));
				}
				Predicate[] p = new Predicate[list.size()];
		        return cb.and(list.toArray(p));
			}
		});
		return result;
	}
	
	
	/**
	 * 计算总金额
	 * 返回总金额
	 * 
	 */
	public Double computeTotal(Cart ct){
		StringBuffer sql = null;
		double count = 0;
		ct.setPriceCount(ct.getProductCount() * ct.getProductPrice());
		Cart cart = cartRepository.saveAndFlush(ct);
		sql = new StringBuffer("SELECT c.PRICE_COUNT  " +  
	               "FROM wx_cart_vw c  " +  
	                "WHERE 1 = 1  " );  
	    sql.append("  AND c.USER_ID = "+ cart.getUserId());
	    sql.append("  AND c.STATUS = 1");	    
	    Query query = entityManager.createNativeQuery(sql.toString());
		
		List list = query.getResultList();
		if(list!=null){
			for (Object obj: list) {
				count += Double.parseDouble(obj.toString());
			}
		}
		return count;
	}


	/**
	 * 根据ID删除购物车里的商品
	 * @param ct
	 */
	@Transactional   //添加事务管理
	public void removeItemFromCart(Cart ct) {
		cartRepository.delete(ct);
	}

	/**
	 * 插入一条新的记录
	 * @param model
	 */
	
	public void saveModel(Cart model) {
		Product pd = productRepository.findOne(model.getProduct().getId());
		model.setProduct_id(pd.getId());
		model.setPriceCount(pd.getPrice());
		model.setProductName(pd.getName());
		model.setProductPrice(pd.getPrice());
		model.setProductCount(1);
		model.setStatus(1);
		model.setPriceCount(pd.getPrice()*1);
		model.setCreateDate(new Date());
		if(pd.getInventory()>0){
			cartRepository.save(model);
		}
		
	}

	//更新数据库表中的数据
	public void addCart(String temp,Cart ct) {
		long num = ct.getProductCount();
		if(temp == "add"){
			ct.setProductCount(++num);
		}
		ct.setPriceCount(num * ct.getProductPrice());
		cartRepository.saveAndFlush(ct);
	}


	/**
	 * 根据ID获取商品
	 * @param id
	 * @return
	 */
	public Cart findCartByOne(long id) {
		return cartRepository.findOne(id);
	}
	
	public void update(String openId, int status){
		Cart model = new Cart();
		model.setOpenId(openId);
		List<Cart> list = this.findAll(model);
		for(Cart i : list){
			i.setStatus(status);
			cartRepository.save(i);
		}
	}
}
