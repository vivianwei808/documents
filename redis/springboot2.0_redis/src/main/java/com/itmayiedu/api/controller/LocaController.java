/**
 * 功能说明:
 * 功能作者:
 * 创建日期:
 *
 */
package com.itmayiedu.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itmayiedu.service.EhCacheUtils;

/**
 * 功能说明: <br>

 * 创建时间:2018年8月4日 下午4:02:00<br>

 */
@RestController
public class LocaController {
	@Autowired
	private EhCacheUtils ehRedisService;

	@RequestMapping("/addLoca")
	public String addLoca(String key, String value) {
		ehRedisService.put("userCache", key, value);
		return "success";
	}

	@RequestMapping("/getEh")
	public String getEh(String key) {
		return (String) ehRedisService.get("userCache", key);
	}

}
