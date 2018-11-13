package com.chenxing.elasticsearch.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "rbac", type = "user", refreshInterval = "0s")
public class User {
	@Id
	private long sysUserId;
	private String userName;
	private String passWord;

	public long getSysUserId() {
		return sysUserId;
	}

	public void setSysUserId(long sysUserId) {
		this.sysUserId = sysUserId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

}
