package com.yutong.axxc.parents.common.error;

import java.util.HashMap;
import java.util.Map;

/**
 * 业务错误工具类
 * @author zhangzhia 2013-8-25 下午2:14:41
 *
 */
public class ErrorInfoUtil
{

    private Map<String, String> map = new HashMap<String, String>();

    private static ErrorInfoUtil mInstance = null;

    public static ErrorInfoUtil getInstance()
    {
        if(mInstance == null)
        {
            mInstance = new ErrorInfoUtil();
        }
        return mInstance;
    }

    private ErrorInfoUtil()
    {
        initData();
    }

    private void initData()
    {
        map.put("10001", "请求信息缺少必要参数信息");
        map.put("10002", "请求参数不符合规范");
        map.put("10003", "终端请求参数开始时间大于结束时间");
        map.put("10004", "终端请求统计时间还未生成报表");
        map.put("10010", "未找到需要的信息");
        map.put("10011", "天气信息获取异常");
        map.put("10102", "用户名或密码错误");
        map.put("10103", "数据的版本信息错误");
        map.put("10104", "家长推送规则为空");
        map.put("10105", "终端持有的信息为最新，不需更新");
        map.put("20000", "请求信息中无access_token信息");
        map.put("20001", "家长令牌异常");
        map.put("90000", " 服务器内部异常");
        map.put("99999", "服务器内部异常，原因未知");

        
        map.put("10023", "邮箱错误");
        map.put("10020", "学生已绑定");
        map.put("10021", "手机已注册");
        map.put("10022", "密码修改失败");
        map.put("10024", "保存信息失败");
        map.put("10025", "手机号错误");
        map.put("10026", "用户已授权");
        map.put("10027", "用户不存在");
        map.put("10028", "用户名已经注册");
        map.put("10029", "资源不存在");
    }

    @SuppressWarnings("unused")
    public String get(String errorCode)
    {
        if(errorCode!= null && map.containsKey(errorCode))
        {
            return map.get(errorCode);
        }
        else
        {
            return "未知错误";
        }
       
    }
}
