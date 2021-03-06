package com.chenxing.elasticsearch.service;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Lists;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.chenxing.elasticsearch.entity.User;
import com.chenxing.elasticsearch.repo.UserRepository;

/**
 * https://blog.csdn.net/tianyaleixiaowu/article/details/76149547
 * 
 */
@Service(value = "testESService")
public class TestElasticsearchService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserRepository userRepository;

	public User save(User user) {
		return userRepository.save(user);
	}

	public List<User> searchAll() {
		Page<User> res = null;
		List<User> arrlst = new ArrayList<User>();
		arrlst = Lists.newArrayList(userRepository.findAll());
		return arrlst;
	}

	public List<User> search(User u, int pageindex, int pagesize) {

		Pageable pageable = PageRequest.of(pageindex, pagesize);
		List<User> arrlst = new ArrayList<User>();
		arrlst = userRepository.findByUserNameLike(u.getUserName(), pageable);
		return arrlst;
	}

	public Page<User> searchBySelfDefine(User user, int pageindex, int pagesize) {
		// 创建builder
		BoolQueryBuilder builder = QueryBuilders.boolQuery();
		// builder下有must、should以及mustNot 相当于sql中的and、or以及not
		// // 设置模糊搜索,真实姓名中包含张的用户
		// builder.must(QueryBuilders.fuzzyQuery("userName", user.getUserName()));

		// 设置查询条件之全文搜索
		builder.must(QueryBuilders.matchQuery("userName", user.getUserName()));

		// 设置查询条件之精确匹配
		if (!StringUtils.isEmpty(user.getPassWord())) {
			// termquery不支持中文
			builder.must(QueryBuilders.termsQuery("passWord", user.getPassWord(), "li123456hai"));

			// builder.must(new
			// QueryStringQueryBuilder(String.valueOf(user.getPassWord())).field("passWord"));
		}
		// 设置查询条件之精确匹配
		if ((!StringUtils.isEmpty(user.getSysUserId()) && (user.getSysUserId() != 0L))) {
			builder.must(QueryBuilders.termQuery("sysUserId", String.valueOf(user.getSysUserId())));

		}

		// 排序
		FieldSortBuilder sort = SortBuilders.fieldSort("sysUserId").order(SortOrder.ASC);

		// 构建查询
		NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
		// 将搜索条件设置到构建中
		nativeSearchQueryBuilder.withQuery(builder);
		// 将分页设置到构建中
		nativeSearchQueryBuilder.withPageable(PageRequest.of(pageindex, pagesize));
		// 将排序设置到构建中
		nativeSearchQueryBuilder.withSort(sort);
		// 生产NativeSearchQuery
		NativeSearchQuery query = nativeSearchQueryBuilder.build();
		// 执行,返回包装结果的分页
		Page<User> resutlList = userRepository.search(query);
		log.info(JSON.toJSONString(resutlList));

		return resutlList;
	}

}
