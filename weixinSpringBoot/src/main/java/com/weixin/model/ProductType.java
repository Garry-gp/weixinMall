package com.weixin.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="wx_product_type_tbl")
public class ProductType {
	
	@Id
	@GeneratedValue
	private long id;
	@Column(name="NAME")
	private String name;
	@Column(name="MENO")
	private String meno;
	
	//配置一对多关系表，mappedBy表示有对方维护，cascade执行联级操作，fatch采用延时加载
//	@OneToMany(mappedBy="productType",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
//	private Set<Product> product = new HashSet<Product>();

	public ProductType(){}
	
	
	public ProductType(long id, String name, String meno) {
		super();
		this.id = id;
		this.name = name;
		this.meno = meno;
	}


	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMeno() {
		return meno;
	}
	public void setMeno(String meno) {
		this.meno = meno;
	}
	
	
	
	
}
