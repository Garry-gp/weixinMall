package com.weixin.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.weixin.model.Users;
import com.weixin.repository.UserRepository;


@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	public Users findUser(String name){
		return userRepository.findByName(name);
	}
	public Users findUserById(long id){
		return userRepository.findOne(id);
	}

	public List<Users> findAll() {
		return userRepository.findAll();
	}
	public List<Users> findAll(final Users model) {
		
		if(model==null){
			return null;
		}
		List<Users> result = userRepository.findAll(new Specification<Users>() {

			@Override
			public Predicate toPredicate(Root<Users> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				
				if(model.getOpenId() !=null || !model.getOpenId().equals("")){
					list.add(cb.equal(root.get("openId").as(String.class), model.getOpenId()));
				}
				Predicate[] p = new Predicate[list.size()];
		        return cb.and(list.toArray(p));
			}
		}); 
		
		return result;
	}

	/**
	 * 描述：排序和分页操作
	 */
	public Page<Users> findUserAndPage(final long id){
		Sort sort = new Sort(Direction.DESC, "id");
		return userRepository.findAll(new Specification<Users>() {
			@Override
			public Predicate toPredicate(Root<Users> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.gt(root.get("id").as(Long.class), id);
			}
		}, new PageRequest(0, 5, sort));
	}

	/**
	 * 用户验证
	 * @param name
	 * @return
	 */
	public Users checkUesr(String name) {
		Users user = userRepository.findByName(name);
		if(user == null){
			user = new Users();
			user.setName(name);
			user = userRepository.save(user);
		}
		return user;
	}
	
	/**
	 * 新增一条用户信息
	 * @param user
	 */
	public Users saveUser(Users user) {
		return userRepository.save(user);
	}
	public Users findUser(Users u) {
		List<Users> userList = findAll(u);
		if(userList.size()>0 ){
			return userList.get(0);	
		}
		return null;
	}
}
