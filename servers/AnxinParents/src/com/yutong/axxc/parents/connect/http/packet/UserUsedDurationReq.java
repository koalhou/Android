
package com.yutong.axxc.parents.connect.http.packet;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.beans.UserBehaviorBean;
import com.yutong.axxc.parents.common.beans.UserUsedDurationBean;

/**
 * 用户使用时长上传请求
 * @author zhangzhia 2013-8-22 上午9:19:24
 *
 */
public class UserUsedDurationReq extends AbstractReq{
    
    private List<UserUsedDurationBean> userUsedDurationBeans;

    public void setUserUsedDurationBeans(List<UserUsedDurationBean> userUsedDurationBeans)
    {
        this.userUsedDurationBeans = userUsedDurationBeans;
    }

    /**
     * （非 Javadoc）
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#packetMsgBody()
     */
    @Override
    public String packetMsgBody() {
        try {
            JSONObject req = new JSONObject();

            JSONArray jsonArray = new JSONArray();
            
            if(userUsedDurationBeans != null && userUsedDurationBeans.size() > 0)
            for(UserUsedDurationBean item: userUsedDurationBeans)
            {
                JSONObject usr_behavior_info = new JSONObject();

                usr_behavior_info.put("start_time", item.getStart_time());
                usr_behavior_info.put("end_time", item.getEnd_time());
                
                jsonArray.put(usr_behavior_info);
            }
            
            req.put("used_duration_info", jsonArray);

            return req.toString();

            
        } catch (JSONException e) {
            Logger.e(this.getClass(), "[用户使用时长请求类]：组包请求时失败，详细信息：", e);
            return null;
        }
    }

    /**
     * （非 Javadoc）
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#setReuqestUri()
     */
    @Override
    public void setReuqestUri() {
        reuqestUri = reuqestUri + "/log/usertime";
    }

}
