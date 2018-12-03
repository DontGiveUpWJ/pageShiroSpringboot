package com.sitech.paas.core;

/**
 * 响应码枚举，参考HTTP状态码的语义
 */
public enum ResultCode {
	
    /** 成功 */
	SUCCESS("200", "成功"),
	
	/** 失败 没有登录 */
	NOT_LOGIN("400", "没有登录"),
	
	/** 未认证（签名错误） 发生异常 */
	UNAUTHORIZED("401", "发生异常"),
	
	/** 系统错误 */
	SYS_ERROR("402", "系统错误"),
	
	/** 参数错误 */
	PARAMS_ERROR("403", "参数错误 "),
	
	/** 接口不存在 */
	NOT_FOUND("404","接口不存在"),
	
	/** 接口不存在 不支持或已经废弃 */
	NOT_SUPPORTED("410", "不支持或已经废弃"),
	
	/** AuthCode错误 */
	INVALID_AUTHCODE("444", "无效的AuthCode"),
 
	/** 太频繁的调用 */
	TOO_FREQUENT("445", "太频繁的调用"),
	
	/** 未知的错误 */
	UNKNOWN_ERROR("499", "未知错误"),
	
	/** 服务器内部错误 */
	INTERNAL_SERVER_ERROR("500","服务器内部错误");
	
	private ResultCode(String value, String msg){
		this.val = value;
		this.msg = msg;
	}
	
	public String val() {
		return val;
	}
 
	public String msg() {
		return msg;
	}
	
	private String val;
	private String msg;

}
