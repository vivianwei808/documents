/**
 * 功能说明:
 * 功能作者:
 * 创建日期:
 *
 */
package com.itmayiedu.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itmayiedu.ehcache.MapCaChe;
import com.itmayiedu.entity.Users;
import com.itmayiedu.service.UserService;

/**
 * 功能说明: <br>

 * 创建时间:2018年7月30日 下午2:14:38<br>

 */
@RestController
public class IndexControoller {
	@Autowired
	private MapCaChe<String, Object> mapCaChe;
	@Autowired
	private UserService userService;
	@Autowired
	private CacheManager cacheManager;

	@RequestMapping("/remoKey")
	@Transactional
	public void remoKey() {
		cacheManager.getCache("userCache").clear();
	}

	@RequestMapping("/getUser")
	public List<Users> getUser(Long id) {
		return userService.getUser(id);
	}

	@RequestMapping("/get")
	public String get(String key) {
		return (String) mapCaChe.get(key);
	}

	@RequestMapping("/put")
	public String put(String key, String value) {
		mapCaChe.put(key, value);
		return "success";
	}

	@RequestMapping("/remove")
	public String remove(String key) {
		mapCaChe.remove(key);
		return "success";
	}

}
