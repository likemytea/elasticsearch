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
			this.save();
			// this.findAllUser();
			// this.search();

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
		u.setUserName("李海" + PrimarykeyGenerated.generateId(false));
		u.setPassWord("li123456hai");
		testESService.save(u);
	}

	/** 自定义搜索 */
	private void searchBySelfDefine() {
		User user = new User();
		user.setSysUserId(Long.parseLong(PrimarykeyGenerated.generateId(false)));
		user.setUserName("中");
		user.setPassWord("pwd");
		Page<User> p = testESService.searchBySelfDefine(user, 0, 30);
		log.info(JSON.toJSONString(p));
	}

	/** 搜索全部 */
	private void searchAllUser() {
		List<User> lst = testESService.searchAll();
		for (User user : lst) {
			log.info(JSON.toJSONString(user));
		}
	}

	/** 全文搜索 */
	private void search() {
		User user = new User();
		user.setSysUserId(Long.parseLong(PrimarykeyGenerated.generateId(false)));
		user.setUserName("和国");
		user.setPassWord("pwd");
		List<User> lst = testESService.search(user, 0, 10);
		for (User u : lst) {
			log.info(JSON.toJSONString(u));
		}
	}
}
