package com.chenxing.elasticsearch.rest;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.chenxing.elasticsearch.entity.User;
import com.chenxing.elasticsearch.service.TestElasticsearchService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("TestController-demo代码")
@RestController
public class TestController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private TestElasticsearchService elasticService;

	@RequestMapping(value = "/save", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String save(@RequestParam String username) {
		User u = new User();
		u.setSysUserId(System.currentTimeMillis());
		u.setUserName(username);
		u.setPassWord("pwd");
		User res = elasticService.save(u);
		return JSON.toJSONString(res);
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String search(@RequestParam String username, @RequestParam int pageindex, @RequestParam int pagesize) {
		User u = new User();
		u.setSysUserId(0);
		u.setUserName(username);
		u.setPassWord("pwd");
		List<User> lst = elasticService.search(u, pageindex, pagesize);
		return JSON.toJSONString(lst);
	}

	/**
	 * 查询例子： username作为全文搜索查询条件 。userid和password作为精确查询条件
	 * 
	 */
	@ApiOperation(value = "测试swagger-value", notes = "elasticsearch的自定义搜索例子")
	@RequestMapping(value = "/searchBySelfDefine", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public String searchBySelfDefine(@RequestParam String username, @RequestParam(defaultValue = "") String userid,
			@RequestParam(defaultValue = "") String password, @RequestParam int pageindex, @RequestParam int pagesize) {
		User u = new User();
		if (!StringUtils.isEmpty(userid)) {
			u.setSysUserId(Long.parseLong(userid));
		}
		u.setUserName(username);
		u.setPassWord(password);
		Page<User> page = elasticService.searchBySelfDefine(u, pageindex, pagesize);
		List<User> userEntityArray = page.getContent();
		List<com.chenxing.elasticsearch.vo.User> userVoLst = new ArrayList<com.chenxing.elasticsearch.vo.User>();
		com.chenxing.elasticsearch.vo.User uservo = null;
		for (User user : userEntityArray) {
			uservo = new com.chenxing.elasticsearch.vo.User();
			BeanUtils.copyProperties(user, uservo);
			userVoLst.add(uservo);
		}
		return JSON.toJSONString(userVoLst);
	}
}
