/**
 * 功能说明:
 * 功能作者:
 * 创建日期:
 *
 */
package com.itmayiedu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import com.itmayiedu.entity.Users;

/**
 * 功能说明: <br>

 * 创建时间:2018年7月30日 下午2:11:28<br>

 */

@CacheConfig(cacheNames = { "userCache" })
public interface UserMapper {
	@Select("SELECT ID ,NAME,AGE FROM users where id=#{id}")
	Users getUser(@Param("id") Long id);
	// @CacheConfig 配置缓存基本信息cacheNames缓存名称
	// @Cacheable 该方法查询数据库完毕之后，存入到缓存
}
