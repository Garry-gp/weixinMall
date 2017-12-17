package com.weixin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="wx_picture_tbl")
public class Picture {
	
	//图片ID
	@Id
	@GeneratedValue
	private long id;
	//产品ID
	@Column(name="PRODUCT_ID")
	private long productId;
	//图片名称
	@Column(name="NAME")
	private String name;
	//图片
	@Column(name="IMAGE")
	private long image;
	//图片大小
	@Column(name="SIZE")
	private long size;
	
	public Picture(){}

	public Picture(long id, long productId, String name, long image, long size) {
		this.id = id;
		this.productId = productId;
		this.name = name;
		this.image = image;
		this.size = size;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getImage() {
		return image;
	}

	public void setImage(long image) {
		this.image = image;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
	

	
}
