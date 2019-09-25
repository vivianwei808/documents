/**
 * 功能说明:
 * 功能作者:
 * 创建日期:
 *
 */
package com.itmayiedu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 功能说明: <br>

 * 创建时间:2018年8月1日 下午4:22:32<br>

 */
@SpringBootApplication
@MapperScan("com.itmayiedu.mapper")
@EnableCaching
public class AppRedis {

	public static void main(String[] args) {
		SpringApplication.run(AppRedis.class, args);
	}

}
