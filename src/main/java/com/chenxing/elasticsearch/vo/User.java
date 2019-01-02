package com.chenxing.elasticsearch.vo;

import io.swagger.annotations.ApiModelProperty;

public class User {
	@ApiModelProperty(value = "用户ID")
	private long sysUserId;
	@ApiModelProperty(value = "用户名称")
	private String userName;
	@ApiModelProperty(value = "用户密码")
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
