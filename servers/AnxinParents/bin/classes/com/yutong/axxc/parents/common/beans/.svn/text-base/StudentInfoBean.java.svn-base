package com.yutong.axxc.parents.common.beans;

import java.io.Serializable;

import com.yutong.axxc.parents.common.context.StringUtil;

/**
 * 学生信息实体类
 * 
 * @author zhangzhia 2013-8-22 上午9:52:20
 * 
 */
public class StudentInfoBean implements Serializable {
	public final static String SEX_MAIL = "0";
	public final static String SEX_FEMAIL = "1";
	/**
	 * 
	 */
	private static final long serialVersionUID = 3670826550506029100L;
	/** 学生id */
	private String cld_id;
	/** 学生姓名 */
	private String cld_name;
	/** 学生昵称 */
	private String cld_alias;
	/** 学生性别 ,0:男，1：女 */
	private String cld_sex;
	/** 学生卡号 */
	private String cld_code;
	/** 学生地址 */
	private String cld_addr;
	/** 学生学校 */
	private String cld_school;
	/** 学生班级 */
	private String cld_class;
	/** 学号 */
	private String cld_no;
	/** 班主任 */
	private String cld_class_adviser;
	/** 班主任电话 */
	private String cld_class_adviser_phone;
	/** 对家长的称呼 */
	private String usr_salutation;
	/** 是否为主账号.0：否，1：是 */
	private String usr_main;

	private String cld_photo;

	private String cld_bg;

	private String cld_audio;

	private String cld_color;

	/**
	 * 取姓名和昵称的组合，格式: name（alias）。
	 * 
	 * @return
	 */
	public String getFullName() {
		if (StringUtil.isNull(cld_alias))
			return cld_name;
		return cld_name + "(" + cld_alias + ")";
	}

	public String getCld_id() {
		return cld_id;
	}

	public void setCld_id(String cld_id) {
		this.cld_id = cld_id;
	}

	public String getCld_name() {
		return cld_name;
	}

	public void setCld_name(String cld_name) {
		this.cld_name = cld_name;
	}

	public String getCld_alias() {
		return cld_alias;
	}

	public void setCld_alias(String cld_alias) {
		this.cld_alias = cld_alias;
	}

	public String getCld_sex() {
		return cld_sex;
	}

	public void setCld_sex(String cld_sex) {
		this.cld_sex = cld_sex;
	}

	public String getCld_code() {
		return cld_code;
	}

	public void setCld_code(String cld_code) {
		this.cld_code = cld_code;
	}

	public String getCld_addr() {
		return cld_addr;
	}

	public void setCld_addr(String cld_addr) {
		this.cld_addr = cld_addr;
	}

	public String getCld_school() {
		return cld_school;
	}

	public void setCld_school(String cld_school) {
		this.cld_school = cld_school;
	}

	public String getCld_class() {
		return cld_class;
	}

	public void setCld_class(String cld_class) {
		this.cld_class = cld_class;
	}

	public String getCld_no() {
		return cld_no;
	}

	public void setCld_no(String cld_no) {
		this.cld_no = cld_no;
	}

	public String getCld_class_adviser() {
		return cld_class_adviser;
	}

	public void setCld_class_adviser(String cld_class_adviser) {
		this.cld_class_adviser = cld_class_adviser;
	}

	public String getCld_class_adviser_phone() {
		return cld_class_adviser_phone;
	}

	public void setCld_class_adviser_phone(String cld_class_adviser_phone) {
		this.cld_class_adviser_phone = cld_class_adviser_phone;
	}

	public String getUsr_main() {
		return usr_main;
	}

	public void setUsr_main(String usr_main) {
		this.usr_main = usr_main;
	}

	public String getCld_photo() {
		return cld_photo;
	}

	public void setCld_photo(String cld_photo) {
		this.cld_photo = cld_photo;
	}

	public String getCld_bg() {
		return cld_bg;
	}

	public void setCld_bg(String cld_bg) {
		this.cld_bg = cld_bg;
	}

	public String getCld_audio() {
		return cld_audio;
	}

	public void setCld_audio(String cld_audio) {
		this.cld_audio = cld_audio;
	}

	public String getCld_color() {
		return cld_color;
	}

	public void setCld_color(String cld_color) {
		this.cld_color = cld_color;
	}

	public String getUsr_salutation() {
		return usr_salutation;
	}

	public void setUsr_salutation(String usr_salutation) {
		this.usr_salutation = usr_salutation;
	}

	public StudentCustomInfoBean getCustonInfo() {
		StudentCustomInfoBean cumton = new StudentCustomInfoBean();
		cumton.setCld_id(this.cld_id);
		cumton.setCld_alias(this.cld_alias);
		cumton.setCld_color(this.cld_color);
		cumton.setCld_bg(this.cld_bg);
		cumton.setCld_photo(this.cld_photo);
		cumton.setCld_audio(this.cld_audio);

		return cumton;
	}

}
