/**
 * 功能说明:
 * 功能作者:
 * 创建日期:
 *
 */
package com.itmayiedu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itmayiedu.entity.Users;
import com.itmayiedu.mapper.UserMapper;

/**
 * 功能说明: <br>

 * 创建时间:2018年7月30日 下午2:13:34<br>

 */
@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;

	public List<Users> getUser(Long id) {
		return userMapper.getUser(id);
	}

}
