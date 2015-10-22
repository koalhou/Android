package com.yutong.axxc.parents.connect.http.packet;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.beans.StudentInfoBean;
import com.yutong.axxc.parents.common.beans.UserInfoBean;

/**
 * 获取学生信息响应类
 * 
 * @author zhangzhia 2013-8-22 上午9:19:24
 */
public class GetStudentInfoRes extends AbstractRes
{

    /** ETag */
    private String ETag;

    /** 学生信息 */
    private List<StudentInfoBean> studentInfoBeans;

    /**
     * （非 Javadoc）
     * 
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractRes#parseCorrectMsg(java.lang.String)
     */
    @Override
    boolean parseCorrectMsg(String jsonString)
    {
        JSONObject res;
        try
        {
            res = new JSONObject(jsonString);

            JSONArray jsonArray = res.getJSONArray("child_infos");// 获取JSONArray

            if (jsonArray != null)
            {
                studentInfoBeans = new ArrayList<StudentInfoBean>();
                for (int i = 0, length = jsonArray.length(); i < length; i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    if (jsonObject != null)
                    {
                        StudentInfoBean studentInfoBean = new StudentInfoBean();
  
                        studentInfoBean.setCld_id(jsonObject.optString("cld_id"));

                        studentInfoBean.setCld_name(jsonObject.optString("cld_name"));
                        studentInfoBean.setCld_alias(jsonObject.optString("cld_alias"));
                        studentInfoBean.setCld_sex(jsonObject.optString("cld_sex"));
                        studentInfoBean.setCld_code(jsonObject.optString("cld_code"));
                        studentInfoBean.setCld_addr(jsonObject.optString("cld_addr"));
                        studentInfoBean.setCld_school(jsonObject.optString("cld_school"));
                        studentInfoBean.setCld_class(jsonObject.optString("cld_class"));
                        studentInfoBean.setCld_no(jsonObject.optString("cld_no"));
                        studentInfoBean.setCld_class_adviser(jsonObject.optString("cld_class_adviser"));
                        studentInfoBean.setCld_class_adviser_phone(jsonObject.optString("cld_class_adviser_phone"));
                        studentInfoBean.setUsr_salutation(jsonObject.optString("usr_salutation"));
                        studentInfoBean.setUsr_main(jsonObject.optString("usr_main"));

                        
                        studentInfoBean.setCld_photo(jsonObject.optString("cld_photo"));
                        studentInfoBean.setCld_bg(jsonObject.optString("cld_bg"));
                        studentInfoBean.setCld_audio(jsonObject.optString("cld_audio"));
                        studentInfoBean.setCld_color(jsonObject.optString("cld_color"));

                        studentInfoBeans.add(studentInfoBean);
                    }
                }
            }

            return true;
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "[获取学生信息响应类]:解析 响应消息出错，详细信息：", e);
            return false;
        }

    }

    public String getETag()
    {
        return ETag;
    }

    public List<StudentInfoBean> getStudentInfoBeans()
    {
        return studentInfoBeans;
    }

    
}
