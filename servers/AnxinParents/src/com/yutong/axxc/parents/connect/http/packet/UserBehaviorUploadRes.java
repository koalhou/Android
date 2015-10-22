
package com.yutong.axxc.parents.connect.http.packet;

/**
 * 用户行为上传响应
 * @author zhangzhia 2013-8-22 上午9:19:24
 *
 */
public class UserBehaviorUploadRes extends AbstractRes {

   /**
    * （非 Javadoc）
    * @see com.yutong.axxc.parents.connect.http.packet.AbstractRes#parseCorrectMsg(java.lang.String)
    */
    @Override
    boolean parseCorrectMsg(String jsonString) {
        return true;
    }

}
