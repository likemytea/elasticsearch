package com.chenxing.elasticsearch;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.chenxing.common.distributedKey.PrimarykeyGenerated;
import com.chenxing.elasticsearch.entity.User;
import com.chenxing.elasticsearch.service.TestElasticsearchService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticsearchApplicationTests {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private TestElasticsearchService testESService;
	@Test
	public void contextLoads() {
		long start = System.currentTimeMillis();
		try {
			// this.save();
			// this.findAllUser();
			this.searchUserList();

		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		log.info("costtime:" + (end - start));
	}

	private void save() {
		User u = new User();
		u.setSysUserId(Long.parseLong(PrimarykeyGenerated.generateId(false)));
		u.setUserName("名11字" + PrimarykeyGenerated.generateId(false));
		u.setPassWord("pwd");
		testESService.save(u);
	}

	private void searchUserList() {
		User u = new User();
		u.setSysUserId(Long.parseLong(PrimarykeyGenerated.generateId(false)));
		u.setUserName("中");
		u.setPassWord("pwd");
		Page<User> p = testESService.search(u);
		log.info(JSON.toJSONString(p));
	}

	private void findAllUser() {
		List<User> lst = testESService.findAll();
		for (User user : lst) {
			log.info(JSON.toJSONString(user));
		}
	}
}
