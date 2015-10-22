
package com.yutong.axxc.parents.connect.http.packet;

/**
 * 用户使用时长响应
 * @author zhangzhia 2013-8-22 上午9:19:24
 *
 */
public class UserUsedDurationRes extends AbstractRes {

   /**
    * （非 Javadoc）
    * @see com.yutong.axxc.parents.connect.http.packet.AbstractRes#parseCorrectMsg(java.lang.String)
    */
    @Override
    boolean parseCorrectMsg(String jsonString) {
        return true;
    }

}
