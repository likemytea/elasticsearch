package com.chenxing.elasticsearch;

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
		this.save();
		// this.searchUserList();
	}

	private void save() {
		User u = new User();
		u.setSysUserId(Long.parseLong(PrimarykeyGenerated.generateId(false)));
		u.setUserName("名字" + PrimarykeyGenerated.generateId(false));
		u.setPassWord("pwd");
		testESService.save(u);
	}

	private void searchUserList() {
		User u = new User();
		u.setSysUserId(Long.parseLong(PrimarykeyGenerated.generateId(false)));
		u.setUserName("名字" + PrimarykeyGenerated.generateId(false));
		u.setPassWord("pwd");
		Page<User> p = testESService.search(u);
		log.info(JSON.toJSONString(p));
	}

}
