package com.yutong.axxc.parents.common.beans;

import java.io.Serializable;

import com.yutong.axxc.parents.common.context.StringUtil;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 用户信息实体类
 * 
 * @author zhangzhia 2013-8-22 上午9:52:20
 * 
 */
public class UserInfoBean implements Serializable {
	public static final String PHONEBIND = "1";
	public static final String PHONENOTBIND = "0";

	private static final long serialVersionUID = 3670826550506029100L;
	/** 用户ID */
	private String usr_id;
	/** 安芯号 */
	private String usr_no;
	/** 用户姓名 */
	private String usr_name;
	/** 用户密码 */
	private String usr_pwd;
	/** 用户昵称 */
	private String usr_login_name;
	/** 性别.-1:未知；0:男 1:女 */
	private String usr_sex;

	/** 手机号码 */
	private String usr_phone;
	/** 头像图片名称 资源id */
	private String usr_photo;
	/** 家庭住址 */
	private String usr_addr;
	/** 邮箱 */
	private String usr_email;
	/** 用户信息ETag值 */
	private String eTag;

	/** 是否绑定协议手机 */
	private String phone_bind;

	/** 授权人安芯号 */
	private String p_usr_no;
	/** 授权人登陆用户名 */
	private String p_usr_login_name;

	/** 针对学生是否主账号；0:否 1:是 */
	private String usr_main;

	public String getFullName() {
		if (StringUtil.isNull(getUsr_name()))
			return getUsr_login_name();
		return getUsr_name();

	}

	public String getUsr_id() {
		return usr_id;
	}

	public void setUsr_id(String usr_id) {
		this.usr_id = usr_id;
	}

	public String getUsr_name() {
		return usr_name;
	}

	public String getUsr_no() {
		return usr_no;
	}

	public void setUsr_no(String usr_no) {
		this.usr_no = usr_no;
	}

	public void setUsr_name(String usr_name) {
		this.usr_name = usr_name;
	}

	public String getUsr_pwd() {
		return usr_pwd;
	}

	public void setUsr_pwd(String usr_pwd) {
		this.usr_pwd = usr_pwd;
	}

	public String getUsr_login_name() {
		return usr_login_name;
	}

	public void setUsr_login_name(String usr_login_name) {
		this.usr_login_name = usr_login_name;
	}

	public String getUsr_sex() {
		return usr_sex;
	}

	public void setUsr_sex(String usr_sex) {
		this.usr_sex = usr_sex;
	}

	public String getUsr_phone() {
		return usr_phone;
	}

	public void setUsr_phone(String usr_phone) {
		this.usr_phone = usr_phone;
	}

	public String getUsr_photo() {
		return usr_photo;
	}

	public void setUsr_photo(String usr_photo) {
		this.usr_photo = usr_photo;
	}

	public String getUsr_addr() {
		return usr_addr;
	}

	public void setUsr_addr(String usr_addr) {
		this.usr_addr = usr_addr;
	}

	public String getUsr_email() {
		return usr_email;
	}

	public void setUsr_email(String usr_email) {
		this.usr_email = usr_email;
	}

	public String geteTag() {
		return eTag;
	}

	public void seteTag(String eTag) {
		this.eTag = eTag;
	}

	public String getPhone_bind() {
		return phone_bind;
	}

	public void setPhone_bind(String phone_bind) {
		this.phone_bind = phone_bind;
	}

	public String getP_usr_no() {
		return p_usr_no;
	}

	public void setP_usr_no(String p_usr_no) {
		this.p_usr_no = p_usr_no;
	}

	public String getP_usr_login_name() {
		return p_usr_login_name;
	}

	public void set(String p_usr_login_name) {
		this.p_usr_login_name = p_usr_login_name;
	}

	public String getUsr_main() {
		return usr_main;
	}

	public void setUsr_main(String usr_main) {
		this.usr_main = usr_main;
	}

}
