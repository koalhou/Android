
package com.yutong.axxc.parents.business.login;

import android.content.Context;
import android.os.Handler;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.ThreadCommUtil;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.beans.LoginInfoBean;
import com.yutong.axxc.parents.dao.beans.DaoLoginInfoBean;
import com.yutong.axxc.parents.dao.login.LoginInfoDao;


/**
 * 自动登录检查业务逻辑类
 * @author zhangzhia 2013-8-22 上午9:52:20
 *
 */
public class AutoLoginChkBiz extends YtAsyncTask {

    /** Context对象 */
    private Context context;

    /** Handler对象 */
    private Handler handler;

    /**
     * 构造函数
     * @param context Context对象
     * @param handler Handler对象
     */
    public AutoLoginChkBiz(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
        logTypeName = "[自动登录业务类]:";
    }

    /**
     * （非 Javadoc）
     * @see com.yutong.axxc.parents.common.YtAsyncTask#doInBackground()
     */
    @Override
    protected void doInBackground() {
        LoginInfoDao loginInfoDao = new LoginInfoDao(context);
        DaoLoginInfoBean loginInfoBean = loginInfoDao.getLoginInfo();
        if (loginInfoBean != null && loginInfoBean.getUsr_id() != null) {
            Logger.i(this.getClass(), logTypeName + "存在已登录用户");

            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.LOGIN_EXIST_USER_INFO,
                    buildLoginInfo(loginInfoBean));
        } else {
            Logger.i(this.getClass(), logTypeName + "不存在已登录用户");
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.LOGIN_NO_USER_INFO);
        }
    }

    /**
     * 组装业务层实体
     * @param loginInfoBean
     * @return
     */
    private LoginInfoBean buildLoginInfo(DaoLoginInfoBean daoLoginInfoBean) {
        LoginInfoBean loginInfoBean = new LoginInfoBean();
        loginInfoBean.setAccessToken(daoLoginInfoBean.getAccessToken());
        loginInfoBean.setUsr_account(daoLoginInfoBean.getUsr_phone());
        loginInfoBean.setUsr_pwd(daoLoginInfoBean.getUsr_pwd());
        return loginInfoBean;
    }

}
