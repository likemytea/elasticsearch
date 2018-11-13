package com.chenxing.elasticsearch.service;

import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import com.chenxing.elasticsearch.entity.User;
import com.chenxing.elasticsearch.repo.UserRepository;

/**
 * https://blog.csdn.net/tianyaleixiaowu/article/details/76149547
 * 
 */
@Service(value = "testESService")
public class TestElasticsearchService {
	@Autowired
	private UserRepository userRepository;

	public User save(User user) {
		return userRepository.save(user);
	}

	public Page<User> search(User user) {
		Page<User> x = userRepository.search(buildQuery(user.getUserName()));
		return x;
	}

	private SearchQuery buildQuery(String userName) {
		FuzzyQueryBuilder queryBuilders = QueryBuilders.fuzzyQuery("userName", userName);
		FieldSortBuilder sortBuilders = SortBuilders.fieldSort("sysUserId");
		NativeSearchQueryBuilder builder1 = new NativeSearchQueryBuilder().withFilter(queryBuilders)
				.withSort(sortBuilders)
				.withPageable(PageRequest.of(0, 20));
		SearchQuery searchQuery = builder1.build();
		return searchQuery;
	}

}
