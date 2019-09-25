/**
 * 功能说明:
 * 功能作者:
 * 创建日期:
 *
 */
package com.itmayiedu.ehcache;

import java.util.Map;

import org.springframework.stereotype.Component;

import net.sf.ehcache.util.concurrent.ConcurrentHashMap;

/**
 * 功能说明: <br>

 * 创建时间:2018年7月30日 下午9:12:19<br>

 */
@Component
public class MapCaChe<K, V> {

	// 存放缓存容器
	public Map<K, V> concurrentHashMap = new ConcurrentHashMap<K, V>();

	// 纯手写单个JVM缓存框架 缓存概念偏向于临时
	// 白话文代码分析 容器Map集合 如何设计时间有效期 开两个两个线程 主线程存放 定时job每隔一秒钟时间

	// 存储
	public void put(K k, V v) {
		concurrentHashMap.put(k, v);
	}

	// 查询
	public V get(K k) {
		return concurrentHashMap.get(k);
	}

	public void remove(K k) {
		concurrentHashMap.remove(k);
	}
}
