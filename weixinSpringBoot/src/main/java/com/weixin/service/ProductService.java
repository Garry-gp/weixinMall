package com.weixin.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.weixin.model.Product;
import com.weixin.model.ProductType;
import com.weixin.repository.ProductRepository;
import com.weixin.repository.ProductTypeRepository;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


@Service
public class ProductService {
	
	@Autowired
	ProductRepository productRepository;
	@Autowired
	ProductTypeRepository productTypeRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("restriction")
	static BASE64Decoder decoder = new BASE64Decoder();
	@SuppressWarnings("restriction")
	static BASE64Encoder encoder = new BASE64Encoder();


	public void setEntityManeger(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	//获取产品列表
	public List<Product> findAll(){
		return productRepository.findAll();
	}
	
	/**
	 * 获取所有产品类型列表
	 * @return
	 */
	public List<ProductType> findAllType(){
		return productTypeRepository.findAll();
	}

	/**
	 * 根据产品ID获取产品信息
	 * @param id
	 * @return
	 */
	@SuppressWarnings("restriction")
	public Product findAllProductById(long id) {
		Product prod = productRepository.findOne(id);
		byte[] bytes = prod.getImage().clone();
		String img = encoder.encodeBuffer(bytes).trim();
		String imgName = prod.getName();
		base64StringToImage(img,imgName);
		return prod;
	}


	/**
	 * 根据model属性查询
	 * @param model
	 * @return
	 */
	public List<Product> findSearch(final Product model){
		
		if(model == null){
			return null;
		}
		List<Product> result = productRepository.findAll(new Specification<Product>(){
			@Override
			public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				//根据类型ID查询
				if (model.getTypeId() != 0) {
	                list.add(cb.equal(root.get("typeId").as(String.class), ""+model.getTypeId()));
	            }
				Predicate[] p = new Predicate[list.size()];
		        return cb.and(list.toArray(p));
			}
		});
		return result;
	}

	/**
	 * 将从数据库获取到的二进制图片转换成图片文件
	 * @param base64String
	 */
	static String base64StringToImage(String base64String,String imgName) { 
		
		String path=null;
        try {  
            @SuppressWarnings("restriction")
			byte[] bytes = decoder.decodeBuffer(base64String);  
  
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);  
            BufferedImage bi = ImageIO.read(bais);  
            path = "D:\\wxapp\\imagers\\"+imgName+".jpg";// 可以是jpg,png,gif格式  
            File file = new File(path);// 可以是jpg,png,gif格式  
            if(!file.exists()){
            	file.mkdirs();          
            }
            ImageIO.write(bi, "jpg", file);// 不管输出什么格式图片，此处不需改动 
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return path;
    }  

}
