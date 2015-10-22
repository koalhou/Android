
package com.yutong.axxc.parents.connect.http.packet;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.beans.UserBehaviorBean;

/**
 * 用户行为上传请求
 * @author zhangzhia 2013-8-22 上午9:19:24
 *
 */
public class UserBehaviorUploadReq extends AbstractReq{
    
    /** 行为日志上报实体类 */
    private List<UserBehaviorBean> userBehaviorBeans;

    public void setUserBehaviorBeans(List<UserBehaviorBean> userBehaviorBeans)
    {
        this.userBehaviorBeans = userBehaviorBeans;
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
            
            if(userBehaviorBeans != null && userBehaviorBeans.size() > 0)
            for(UserBehaviorBean item: userBehaviorBeans)
            {
                JSONObject usr_behavior_info = new JSONObject();
                usr_behavior_info.put("module_id", item.getModule_id());
                usr_behavior_info.put("log_date", item.getLog_date());
                
                jsonArray.put(usr_behavior_info);
            }
            
            req.put("usr_behavior_infos", jsonArray);

            return req.toString();

            
        } catch (JSONException e) {
            Logger.e(this.getClass(), "[用户行为上报请求类]：组包生成用户行为上报请求时失败，详细信息：", e);
            return null;
        }
    }

    /**
     * （非 Javadoc）
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#setReuqestUri()
     */
    @Override
    public void setReuqestUri() {
        reuqestUri = reuqestUri + "/log/behaviour";
    }

}
