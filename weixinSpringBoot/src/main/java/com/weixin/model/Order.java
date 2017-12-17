package com.weixin.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="wx_order_tbl")
public class Order {

	@Id
	@GeneratedValue
	private long id;
	
	@Column(name="PRODUCT_ID")
	private String productId;
	@Column(name="STATUS")
	private long status;
	@Column(name="CREATE_DATE")
	private Date createData;
	@Column(name="UPDATE_DATE")
	private Date updateDate;
	//订单的价格
	@Column(name="ORDER_PRICE")
	private double orderPrice;
	//订单的产品数量
	@Column(name="COUNT")
	private String count;
	@Column(name="USER_ID")
	private long userId;
	@Column(name="ORDER_NO")
	private String orderNo;
	@Column(name="OPEN_ID")
	private String openId;
	
	public Order(){}



	public Order(long id, String productId, long status, Date createData, Date updateDate, double orderPrice, String count,
			long userId, String orderNo) {
		this.id = id;
		this.productId = productId;
		this.status = status;
		this.createData = createData;
		this.updateDate = updateDate;
		this.orderPrice = orderPrice;
		this.count = count;
		this.userId = userId;
		this.orderNo = orderNo;
	}



	public String getOpenId() {
		return openId;
	}



	public void setOpenId(String openId) {
		this.openId = openId;
	}



	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public long getStatus() {
		return status;
	}

	public void setStatus(long status) {
		this.status = status;
	}
	public Date getCreateData() {
		return createData;
	}
	public void setCreateData(Date createData) {
		this.createData = createData;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public double getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(double orderPrice) {
		this.orderPrice = orderPrice;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}
	
	
	
}
