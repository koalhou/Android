/**
 * @公司名称：YUTONG
 * @作者：houjunhu
 * @版本号：1.0
 * @生成日期：2014-07-24 上午10:02:58
 * @功能描述：
 */
package com.yutong.clw.ygbclient.business.loginfo;

import android.content.Context;

import com.yutong.clw.ygbclient.business.ProxyManager;
import com.yutong.clw.ygbclient.business.common.AbstractDataControl;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.enums.ActionURI;
import com.yutong.clw.ygbclient.common.enums.CommonNetStatus;
import com.yutong.clw.ygbclient.common.exception.CommonException;
import com.yutong.clw.ygbclient.connect.http.common.HttpRes;
import com.yutong.clw.ygbclient.connect.http.loginfo.UsageUploadReq;
import com.yutong.clw.ygbclient.connect.http.loginfo.UsageUploadRes;

/**
 * 用户行为统计逻辑类
 * 
 * @author houjunhu 2013-8-22 上午9:52:20
 */
public class UsageUploadBiz extends AbstractDataControl {

	/**
	 * 构造函数
	 * 
	 * @param context
	 *            Context对象
	 */
	public UsageUploadBiz(Context context) {
		this.context = context;
		logTypeName = "[用户行为统计逻辑类]:";
		setActionURI(ActionURI.REPORT_MOBILE_USAGE_INFO);
	}

	/**
	 * 上报用户行为统计信息
	 * 
	 * @author houjunhu 2013-10-28 上午11:13:39
	 * @throws CommonException
	 */
	public boolean reportUsage(String module_son_id) throws CommonException {
		Logger.i(this.getClass(), logTypeName + "发送请求");
		UsageUploadReq req = buildReq(module_son_id);

		HttpRes httpRes = getHttpRes(req);
		try {
			if (httpRes.isSuccess()) {
				UsageUploadRes res = new UsageUploadRes();
				res.parse(httpRes.getContent());
				if (!res.isError()) {
					Logger.i(this.getClass(), logTypeName + "成功");
					return true;
				} else {
					Logger.e(this.getClass(), logTypeName + "失败");
					throw new CommonException(CommonNetStatus.Error_Info,res.getErrorCode(), res.getErrorDes());
				}
			} else if (httpRes.isTokenExpire()) {
				Logger.e(this.getClass(), logTypeName + "Token失效");
				throw new CommonException(CommonNetStatus.Token_InValid);
			} else if (httpRes.isException()) {
				Logger.e(this.getClass(), logTypeName + "失败：",httpRes.getFailInfo());
				throw new CommonException(CommonNetStatus.NetWork_Exception);
			} else {
				Logger.e(this.getClass(), logTypeName + "失败：",httpRes.getFailInfo());
				throw new CommonException(CommonNetStatus.NetWork_Not_Stable);
			}

		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * 组装请求对象
	 * 
	 * @param context
	 * @return
	 */
	private UsageUploadReq buildReq(String module_son_id) {
		UsageUploadReq req = new UsageUploadReq();
		req.setModule_son_id(module_son_id);
		req.setAccessToken(ProxyManager.getInstance(context).getAccessToken());
		return req;
	}
}
