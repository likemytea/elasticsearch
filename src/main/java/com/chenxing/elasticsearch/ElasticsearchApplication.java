package com.chenxing.elasticsearch;

/**
 * elasticsearch demo
 * <p>
 * Created by liuxing on 13/11/18.
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.chenxing.common.distributedKey.PrimarykeyGenerated;
import com.chenxing.elasticsearch.entity.User;
import com.chenxing.elasticsearch.service.TestElasticsearchService;

@RestController
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class ElasticsearchApplication {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	public static void main(String[] args) {
		SpringApplication.run(ElasticsearchApplication.class, args);
	}

	@Autowired
	private TestElasticsearchService elasticService;

	@RequestMapping(value = "/save", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String save(@RequestParam String username) {
		User u = new User();
		u.setSysUserId(Long.parseLong(PrimarykeyGenerated.generateId(false)));
		u.setUserName(username);
		u.setPassWord("pwd");
		User res = elasticService.save(u);
		return JSON.toJSONString(res);
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String search(@RequestParam String username) {
		User user = new User();
		user.setSysUserId(Long.parseLong(PrimarykeyGenerated.generateId(false)));
		user.setUserName(username);
		user.setPassWord("pwd");
		Page<User> res = elasticService.search(user);
		return JSON.toJSONString(res);
	}
}
