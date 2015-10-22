/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-22 下午3:08:36
 * @功能描述：
 */
package com.yutong.clw.ygbclient.common.beans;

import org.json.JSONObject;

import com.yutong.clw.ygbclient.common.enums.AreaType;
import com.yutong.clw.ygbclient.common.enums.LineRange;
import com.yutong.clw.ygbclient.common.enums.StatusRange;
import com.yutong.clw.ygbclient.common.enums.remind.RemindRange;
import com.yutong.clw.ygbclient.common.enums.remind.RemindStatus;
import com.yutong.clw.ygbclient.common.enums.remind.RemindType;
import com.yutong.clw.ygbclient.common.utils.DataTypeConvertUtils;
import com.yutong.clw.ygbclient.common.utils.DateUtils;
import com.yutong.clw.ygbclient.common.utils.StringUtil;

/**
 * 版本信息实体
 * 
 * @author zhangzhia 2013-8-22 上午9:52:20
 */
public class VersionInfo
{

    /** 软件地址信息 */
    public String uri;

    /** 是否更新 */
    public boolean need_update = true;

    /** 是否强制性更新 false：否，true：是 */
    public boolean force;

    /** 新版本名 */
    public String version_name;

    /** 新版本号 */
    public int version_code;

    /** 软件升级描述 */
    public String change_desc;

    public String getUri()
    {
        return uri;
    }

    public void setUri(String uri)
    {
        this.uri = uri;
    }

    public boolean isNeed_update()
    {
        return need_update;
    }

    public void setNeed_update(boolean need_update)
    {
        this.need_update = need_update;
    }

    public boolean isForce()
    {
        return force;
    }

    public void setForce(boolean force)
    {
        this.force = force;
    }

    public String getVersion_name()
    {
        return version_name;
    }

    public void setVersion_name(String version_name)
    {
        this.version_name = version_name;
    }

    public int getVersion_code()
    {
        return version_code;
    }

    public void setVersion_code(int version_code)
    {
        this.version_code = version_code;
    }

    public String getChange_desc()
    {
        return change_desc;
    }

    public void setChange_desc(String change_desc)
    {
        this.change_desc = change_desc;
    }

    public void parse(JSONObject optJSONObject)
    {
        if (StringUtil.isNotBlank(optJSONObject.optString("uri")))
        {
            uri = optJSONObject.optString("uri");
        }
        if (StringUtil.isNotBlank(optJSONObject.optString("force")))
        {
            force = DataTypeConvertUtils.int2Boolean(optJSONObject.optInt("force"));
        }
        if (StringUtil.isNotBlank(optJSONObject.optString("version_name")))
        {
            version_name = optJSONObject.optString("version_name");
        }
        if (StringUtil.isNotBlank(optJSONObject.optString("version_code")))
        {
            version_code = optJSONObject.optInt("version_code");
        }
        if (StringUtil.isNotBlank(optJSONObject.optString("change_desc")))
        {
            change_desc = optJSONObject.optString("change_desc");
        }

    }

}
