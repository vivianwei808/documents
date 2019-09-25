/**
 * 功能说明:
 * 功能作者:
 * 创建日期:
 *
 */
package com.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itmayiedu.service.RedisService;


@RestController
public class IndexController {
	@Autowired
	private RedisService redisService;

	@RequestMapping("/setString")
	public String setString(String key, String object) {
		redisService.set(key, object, 60l);
		return "success";
	}

	@RequestMapping("/get")
	public String get(String key) {
		return redisService.getString(key);
	}

}
