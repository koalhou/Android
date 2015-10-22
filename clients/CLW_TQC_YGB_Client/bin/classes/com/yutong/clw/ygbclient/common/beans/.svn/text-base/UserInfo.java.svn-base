/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-22 下午3:08:36
 * @功能描述：
 */
package com.yutong.clw.ygbclient.common.beans;

import java.io.Serializable;

import com.yutong.clw.ygbclient.common.enums.AreaType;
import com.yutong.clw.ygbclient.common.enums.SexType;

/**
 * 用户信息实体类
 * 
 * @author zhangzhia 2013-8-22 上午9:52:20
 * 
 */
public class UserInfo implements Serializable
{
	private static final long serialVersionUID = 3670826550506029100L;

	/** 要获取的Access Token */
	public String AccessToken;
	/** Access Token的有效期，单位：秒 */
	public long ExpiresIn;
	/** 用于刷新Access Token的Refresh Token */
	public String RefreshToken;

	/** 用户ID */
	public String Id;
	/** 员工号 */
	public String Code;
	/** 用户姓名 */
	public String Name;
	/** 员工姓名拼音缩写 */
	public String NameShort;
	/** 员工所在厂区 1:宇通工业园，2:：新能源工厂 */
	public AreaType BelongArea = AreaType.FirstFactory;
	/** 性别.-1:未知；0:男 1:女 */
	public SexType Sex = SexType.Unknown;
	/** 手机号码 */
	public String Phone;
	/** 用户照片，资源resid */
	public String Photo;
	/** 所在部门 */
	public String Department;
	/** eip绑定手机号 */
	public String EipPhone;
	/** 手机是否绑定，0：否，1：是 */
	public boolean BindPhone;
	/** 是否未修改默认密码，0：否，1：是 */
	public boolean DefaultPassword;


}
