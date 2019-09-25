/**
 * 功能说明:
 * 功能作者:
 * 创建日期:
 *
 */
package com.itmayiedu.api.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itmayiedu.entity.Users;
import com.itmayiedu.service.RedisService;
import com.itmayiedu.service.UserService;

/**
 * 功能说明: <br>

 * 创建时间:2018年8月1日 下午4:20:33<br>

 */
@RestController
public class IndexControler {

	@Autowired
	private RedisService redisService;
	@Autowired
	private UserService userService;

	@RequestMapping("/setString")
	public String setString(String key, String value) {
		// redisService.set(key, value, 60l);
		redisService.setString(key, value);
		return "success";
	}

	@RequestMapping("/getString")
	public String getString(String key) {
		return redisService.getString(key);
	}

	@RequestMapping("/setSet")
	public String setSet() {
		Set<String> set = new HashSet<String>();
		set.add("yushengjun");
		set.add("lisi");
		redisService.setSet("setTest", set);
		return "success";
	}

	@RequestMapping("/getUser")
	public Users getUser(Long id) {
		Users user = userService.getUser(id);
		return user;
	}
}
