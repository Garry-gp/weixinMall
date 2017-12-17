package com.weixin.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="wx_cart_tbl")
public class Cart {
	
	@Id
	@GeneratedValue
	private long id;
	@Column(name="USER_ID")
	private long userId;
	@Column(name="PRICE_COUNT")
	private double priceCount;
	@Column(name="PRODUCT_COUNT")
	private long productCount;
	@Column(name="STATUS")
	private long status;
	@Column(name="CREATE_DATETIME")
	private Date createDate;
	@Column(name="PRODUCT_NAME")
	private String productName;
	@Column(name="OPEN_ID")
	private String openId;
	@Column(name="PRODUCT_PRICE")
	private double productPrice;
	@Column(name="PRODUCT_ID")
	private double product_id;
	


	@ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH})
	@JoinColumn(name="PRODUCT_ID", referencedColumnName="ID", insertable=false, updatable=false)
	private Product product;

	public Cart() {
	}
	
	public Cart(long id, long userId, double priceCount, long productCount, long status, Date createDate,
			String productName, String openId, double productPrice, Product product) {
		super();
		this.id = id;
		this.userId = userId;
		this.priceCount = priceCount;
		this.productCount = productCount;
		this.status = status;
		this.createDate = createDate;
		this.productName = productName;
		this.openId = openId;
		this.productPrice = productPrice;
		this.product = product;
	}





	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}


	public Product getProduct() {
		return product;
	}


	public void setProduct(Product product) {
		this.product = product;
	}


	public double getPriceCount() {
		return priceCount;
	}

	public void setPriceCount(double priceCount) {
		this.priceCount = priceCount;
	}

	public long getProductCount() {
		return productCount;
	}

	public void setProductCount(long productCount) {
		this.productCount = productCount;
	}

	public long getStatus() {
		return status;
	}

	public void setStatus(long status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public double getProduct_id() {
		return product_id;
	}

	public void setProduct_id(double product_id) {
		this.product_id = product_id;
	}
	
	
}
