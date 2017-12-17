package com.weixin.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="wx_product_tbl")
public class Product {
	
	@Id
	@GeneratedValue
	private long id;
	//产品类型ID
	@Column(name="TYPE_ID")
	private long typeId;
	//产品名称
	@Column(name="NAME")
	private String name;
	//产品价格
	@Column(name="PRICE")
	private double price;
	//产品描述
	@Column(name="meno")
	private String meno;
	//商品图片
	@Column(name="image")
	private byte image[];	
	
	@Column(name="INVENTORY")
	private long Inventory;
	@Column(name="sell")
	private long sell;
	public long getSell() {
		return sell;
	}


	public void setSell(long sell) {
		this.sell = sell;
	}

	//多对一关联关系，对应商品类型表
//	@ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH})
//	@JoinColumn(name="TYPE_ID", referencedColumnName="ID", insertable=false, updatable=false)
//	private ProductType productType;
	
//	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
//	private Set<Cart> product = new HashSet<Cart>();


	public Product(){}


	public Product(long id, long typeId, String name, double price, String meno, byte[] image, long inventory) {
		super();
		this.id = id;
		this.typeId = typeId;
		this.name = name;
		this.price = price;
		this.meno = meno;
		this.image = image;
		Inventory = inventory;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getTypeId() {
		return typeId;
	}

	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getMeno() {
		return meno;
	}

	public void setMeno(String meno) {
		this.meno = meno;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte image[]) {
		this.image = image;
	}

	public long getInventory() {
		return Inventory;
	}

	public void setInventory(long inventory) {
		Inventory = inventory;
	}

}
