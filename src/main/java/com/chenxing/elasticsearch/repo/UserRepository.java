package com.chenxing.elasticsearch.repo;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.chenxing.elasticsearch.entity.User;

public interface UserRepository extends ElasticsearchRepository<User, Long> {

}
