package com.weixin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.weixin.model.Users;
import com.weixin.service.UserService;

@RestController
@RequestMapping("/userInfo")
//@SpringApplicationConfiguration(Application.class)
public class UserController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value="/testByName",method=RequestMethod.DELETE)
	public Users testByName(@Param("name") String name){
		return userService.findUser(name);
	}	
	
	@RequestMapping("/findAll")
	public List<Users> findAll(){
		return userService.findAll();
	}
	
	@RequestMapping("/findAllById")
	public Users findAllById(@Param("id") long id){
		return userService.findUserById(id);
	}
	
	@RequestMapping("/findUserPage")
	public Page findUserPage(@Param("code") String id){		
		Long userId = Long.valueOf(id);
		Page pageResult = userService.findUserAndPage(userId);
		return pageResult;
	}
	/**
	 * 用户授权验证
	 * 检查用户是否存在，
	 * 不存在则保存用户到数据库
	 * @param rquest
	 * @return
	 */
	@RequestMapping("/check")
	public Users checkUser(HttpServletRequest rquest){
		String name = rquest.getParameter("name");
		return userService.checkUesr(name);
	}
	
	/**
	 * 判断用户是否在线
	 * @param userName	登录用户名
	 * @return
     */
	public boolean isUserConnected(String userName){
		
		return false;
	}

	/**
	 * 用户登录信息简单验证
	 * @return
	 */
	@RequestMapping(value="/getserInfo",method =RequestMethod.POST)
	public Users saveUser(HttpServletRequest request,HttpServletResponse respons,Users user){		
		String openId = user.getOpenId();
		if(openId != null){
			Users u = new Users();
			u.setOpenId(openId);
			Users result = userService.findUser(u);
			if(result == null){
				user = userService.saveUser(user);
			}
		}
		return user;
	}
	
	public Users updataUserToAdderrss(HttpServletRequest request,HttpServletResponse respons,Users user){
		
		return null;
	}
	
}
