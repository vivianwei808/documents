package com.itmayiedu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 功能说明:
 * 功能作者:
 * 创建日期:
 *
 */

/**
 * 功能说明: <br>
 * 创建作者:每特教育-余胜军<br>
 * 创建时间:2018年7月30日 下午2:17:15<br>

 */
@MapperScan(basePackages = { "com.itmayiedu.mapper" })
@EnableCaching
@SpringBootApplication
public class App {
	// @EnableCaching 开启缓存
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}
