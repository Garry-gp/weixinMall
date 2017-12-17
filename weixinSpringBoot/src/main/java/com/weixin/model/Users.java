package com.weixin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="wx_users_tbl")
public class Users {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column(name = "OPENID")
	private String openId;
	//用户名称
	@Column(name = "NICK_NAME")
	private String name;
	//城市
	@Column(name = "CITY")
	private String city;
	//性别
	@Column(name="GENDER")
	private long gender;
	//省份
	@Column(name="PROVINCE")
	private String province;
	//国家地区
	@Column(name="COUNTRY")
	private String country;
	//头像URL
	@Column(name="AVATAR_URL")
	private String avatarUrl;
	//用户名称
	@Column(name="USER_NAME")
	private String uName;
	//电话号码
	@Column(name="PHONE")
	private String phone;
	//用户地址
	@Column(name="ADDRESS")
	private String address;
	
	public Users() {}

	public Users(long id, String openId, String name, String city, long gender, String province, String country,
			String avatarUrl, String uName, String phone, String address) {
		super();
		this.id = id;
		this.openId = openId;
		this.name = name;
		this.city = city;
		this.gender = gender;
		this.province = province;
		this.country = country;
		this.avatarUrl = avatarUrl;
		this.uName = uName;
		this.phone = phone;
		this.address = address;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public long getGender() {
		return gender;
	}

	public void setGender(long gender) {
		this.gender = gender;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getuName() {
		return uName;
	}

	public void setuName(String uName) {
		this.uName = uName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	

}
