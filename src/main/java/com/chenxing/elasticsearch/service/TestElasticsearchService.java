package com.chenxing.elasticsearch.service;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Lists;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
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

	public List<User> findAll() {
		Page<User> res = null;
		List<User> arrlst = new ArrayList<User>();
		arrlst = Lists.newArrayList(userRepository.findAll());
		return arrlst;
	}

	public Page<User> search(User user) {
		// 创建builder
		BoolQueryBuilder builder = QueryBuilders.boolQuery();
		// builder下有must、should以及mustNot 相当于sql中的and、or以及not
		// 设置模糊搜索,真实姓名中包含张的用户
		builder.must(QueryBuilders.fuzzyQuery("userName", user.getUserName()));
		// 设置sysUserId的查询条件恒等于18111314514100021
		builder.must(new QueryStringQueryBuilder("18111314514100021").field("sysUserId"));
		// 排序
		FieldSortBuilder sort = SortBuilders.fieldSort("sysUserId").order(SortOrder.DESC);

		// 构建查询
		NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
		// 将搜索条件设置到构建中
		nativeSearchQueryBuilder.withQuery(builder);
		// 将分页设置到构建中
		nativeSearchQueryBuilder.withPageable(PageRequest.of(0, 2));
		// 将排序设置到构建中
		nativeSearchQueryBuilder.withSort(sort);
		// 生产NativeSearchQuery
		NativeSearchQuery query = nativeSearchQueryBuilder.build();
		// 执行,返回包装结果的分页
		Page<User> resutlList = userRepository.search(query);
		return resutlList;
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
