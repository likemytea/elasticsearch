package com.chenxing.elasticsearch.repo;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.chenxing.elasticsearch.entity.User;

public interface UserRepository extends ElasticsearchRepository<User, Long> {
	/**
	 * 恒等于 <br>
	 * 注意：方法名要和属性名一致，保持驼峰法，否则运行报错，找不到属性名。
	 */
	List<User> findByUserName(String username);

	/**
	 * 全文搜索 <br>
	 * 注意：方法名要和属性名一致，保持驼峰法，否则运行报错，找不到属性名。
	 */
	List<User> findByUserNameLike(String username, Pageable pageable);
}
