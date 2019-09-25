/**
 * 功能说明:
 * 功能作者:
 * 创建日期:
 *
 */
package com.itmayiedu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Component;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * 功能说明: <br>
 * 创建时间:2018年8月4日 下午3:31:42<br>
 */
@Component
public class EhCacheUtils {

	// @Autowired
	// private CacheManager cacheManager;
	@Autowired
	private EhCacheCacheManager ehCacheCacheManager;

	// 添加本地缓存 (相同的key 会直接覆盖)
	public void put(String cacheName, String key, Object value) {
		Cache cache = ehCacheCacheManager.getCacheManager().getCache(cacheName);
		Element element = new Element(key, value);
		cache.put(element);
	}

	// 获取本地缓存
	public Object get(String cacheName, String key) {
		Cache cache = ehCacheCacheManager.getCacheManager().getCache(cacheName);
		Element element = cache.get(key);
		return element == null ? null : element.getObjectValue();
	}

	public void remove(String cacheName, String key) {
		Cache cache = ehCacheCacheManager.getCacheManager().getCache(cacheName);
		cache.remove(key);
	}

}
