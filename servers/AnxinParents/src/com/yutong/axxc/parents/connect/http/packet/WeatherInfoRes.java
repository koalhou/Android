
package com.yutong.axxc.parents.connect.http.packet;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.connect.http.packet.subres.WeatherDetailRes;

/**
 * 天气信息响应类
 * @author zhangzhia 2013-8-22 上午9:19:24
 *
 */
public class WeatherInfoRes extends AbstractRes {

    /** 更新时间 */
    private String time;
    /** 城市 */
    private String city;
    
    private List<WeatherDetailRes> weatherDetailResList;

    /**
     * （非 Javadoc）
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractRes#parseCorrectMsg(java.lang.String)
     */
    @Override
    boolean parseCorrectMsg(String jsonString) {
        JSONObject loginRes;
        try {
            loginRes = new JSONObject(jsonString);
            time = loginRes.optString("time");
            city = loginRes.optString("city");
            JSONArray infoArray = loginRes.optJSONArray("infos");
            if (infoArray != null) {
                weatherDetailResList = new ArrayList<WeatherDetailRes>();
                int length = infoArray.length();
                for (int i = 0; i < length; i++) {
                    WeatherDetailRes weatherDetailRes = new WeatherDetailRes();
                    if (weatherDetailRes.parse(infoArray.getJSONObject(i))) {
                        weatherDetailResList.add(weatherDetailRes);
                    }
                }
            }
            return true;
        } catch (JSONException e) {
            Logger.e(this.getClass(), "[天气信息响应类]:解析响应消息出错，详细信息：", e);
            return false;
        }
    }

    /**
     * @return Returns the time.
     */
    public String getTime() {
        return time;
    }

    /**
     * @return Returns the weatherDetailResList.
     */
    public List<WeatherDetailRes> getWeatherDetailResList() {
        return weatherDetailResList;
    }

    public String getCity()
    {
        return city;
    }
    
    

}
