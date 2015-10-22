
package com.yutong.axxc.parents.business.login;

import android.content.Context;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.dao.login.LoginInfoDao;

/**
 *  清除用户登录业务逻辑类
 * @author zhangzhia 2013-8-22 上午9:52:20
 *
 */
public class UserInfoClearBiz extends YtAsyncTask {

    /** Context对象 */
    private Context context;

    /**
     * 构造函数
     * @param context Context对象
     * @param handler Handler对象
     * @param bizLoginInfoBean 登录信息对象
     */
    public UserInfoClearBiz(Context context) {
        this.context = context;
        logTypeName = "[清除用户登录业务逻辑类]:";
    }

    /**
     * （非 Javadoc）
     * @see com.yutong.axxc.parents.common.YtAsyncTask#doInBackground()
     */
    @Override
    protected void doInBackground() {
        new LoginInfoDao(context).delLoginInfo();
    }

}
