package com.chenxing.elasticsearch.repo;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.chenxing.elasticsearch.entity.User;

public interface UserRepository extends ElasticsearchRepository<User, Long> {
	/**
	 * 查询用户名为username的用户
	 * 
	 * @param username
	 * @return
	 */
	List<User> findByUserName(String username);
}
