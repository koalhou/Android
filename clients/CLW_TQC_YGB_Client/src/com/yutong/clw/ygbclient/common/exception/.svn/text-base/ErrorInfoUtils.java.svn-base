package com.yutong.clw.ygbclient.common.exception;

import java.util.HashMap;
import java.util.Map;

import com.yutong.clw.ygbclient.common.beans.UriAction;
import com.yutong.clw.ygbclient.common.enums.ActionURI;
import com.yutong.clw.ygbclient.common.enums.HttpAction;

/**
 * 业务错误工具类
 * 
 * @author zhangzhia 2013-8-25 下午2:14:41
 */
public class ErrorInfoUtils
{

    @SuppressWarnings("serial")
    private Map<String, String> map = new HashMap<String, String>()
    {
        {
            put("10001", "请求信息缺少必要参数信息");
            put("10002", "请求参数不符合规范");
            put("10003", "终端请求参数开始时间大于结束时间");
            put("10004", "终端请求统计时间还未生成报表");
            put("10010", "未找到需要的信息");
            put("10011", "天气信息获取异常");
            put("10102", "用户名或密码错误");
            put("10103", "数据的版本信息错误");
            put("10104", "家长推送规则为空");
            put("10105", "终端持有的信息为最新，不需更新");
            put("20000", "登陆已失效，请退出后重新登录");
            put("20001", "家长令牌异常");
            put("90000", " 服务器内部异常");
            put("99999", "服务器内部异常，原因未知");

            put("10023", "邮箱错误");
            put("10020", "学生已绑定");
            put("10021", "手机已注册");
            put("10022", "密码修改失败");
            put("10024", "保存信息失败");
            put("10025", "手机号错误");
            put("10026", "用户已授权");
            put("10027", "用户不存在");
            put("10028", "用户名已经注册");
            put("10029", "资源不存在");
        }
    };
    /*put("20000", "请求信息中无access_token信息");*/
    @SuppressWarnings("unused")
    public String get(String errorCode)
    {
        if (errorCode != null && map.containsKey(errorCode))
        {
            return map.get(errorCode);
        }
        else
        {
            return "未知错误";
        }

    }
}
