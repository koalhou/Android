package com.yutong.clw.ygbclient.business.setting;

import java.util.List;

import android.content.Context;

import com.yutong.clw.ygbclient.business.ProxyManager;
import com.yutong.clw.ygbclient.business.common.AbstractDataControl;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.AdviseReply;
import com.yutong.clw.ygbclient.common.enums.ActionURI;
import com.yutong.clw.ygbclient.common.enums.CommonNetStatus;
import com.yutong.clw.ygbclient.common.exception.CommonException;
import com.yutong.clw.ygbclient.connect.http.common.HttpRes;
import com.yutong.clw.ygbclient.connect.http.setting.AdviseFeedbackReq;
import com.yutong.clw.ygbclient.connect.http.setting.AdviseReplyReq;
import com.yutong.clw.ygbclient.connect.http.setting.AdviseReplyRes;
import com.yutong.clw.ygbclient.dao.other.AdvicePushInfoDao;
import com.yutong.clw.ygbclient.view.bean.push.FeedBackPushBean;

/**
 * 本地意见反馈逻辑类
 * 
 */
public class AdviseReceiveBiz extends AbstractDataControl {
	/** Context对象 */
	private Context mContext;

	/**
	 * 构造函数
	 * 
	 * @param context
	 */
	public AdviseReceiveBiz(Context context) {
		this.mContext = context;
		logTypeName = "[本地意见反馈处理逻辑类]:";
		setActionURI(ActionURI.ADVISE_REPLY);
	}

	public int getAdviceInfoNumByReadFlag(String readFlag, String replyFlag) {
		return new AdvicePushInfoDao(mContext).getAdviceInfoNumByReadFlag(readFlag, replyFlag);
	}

	public List<FeedBackPushBean> getUnReadAdviceList(String userCode) {
		return new AdvicePushInfoDao(mContext).getAdviceInfoList(userCode);
	}

	public boolean setAdviceListStatus(String readFlag, String replyFlag) {

		return new AdvicePushInfoDao(mContext).setAdviceListStatus(readFlag,replyFlag);
	}

	/* 从服务器获取并入库 */
	public List<FeedBackPushBean> getAdviseReplyList() throws CommonException {

		Logger.i(this.getClass(), logTypeName + "发送请求");
		AdviseReplyReq req = buildReq();

		HttpRes httpRes = getHttpRes(req);

		if (httpRes.isSuccess()) {
			AdviseReplyRes res = new AdviseReplyRes();
			res.parse(httpRes.getContent());
			if (!res.isError()) {
				Logger.i(this.getClass(), logTypeName + "成功");
				return res.getFeedBackPushBeanList();
			} else {
				Logger.e(this.getClass(), logTypeName + "失败");
				throw new CommonException(CommonNetStatus.Error_Info,res.getErrorCode(), res.getErrorDes());
			}
		} else if (httpRes.isTokenExpire()) {
			Logger.e(this.getClass(), logTypeName + "Token失效");
			throw new CommonException(CommonNetStatus.Token_InValid);
		} else if (httpRes.isException()) {
			Logger.e(this.getClass(), logTypeName + "失败：",
					httpRes.getFailInfo());
			throw new CommonException(CommonNetStatus.NetWork_Exception);
		} else {
			Logger.e(this.getClass(), logTypeName + "失败：",
					httpRes.getFailInfo());
			throw new CommonException(CommonNetStatus.NetWork_Not_Stable);
		}
	}

	/* 批量更新本地库 */
	public void setAdviceListStatus(List<FeedBackPushBean> adviseReplyList) throws CommonException {
		new AdvicePushInfoDao(mContext).setAdviceListStatus(adviseReplyList);
	}
	
	
	/**
	 * 组装请求对象
	 * 
	 * @param context
	 * @return
	 */
	private AdviseReplyReq buildReq() {
		AdviseReplyReq req = new AdviseReplyReq();
		req.setAccessToken(ProxyManager.getInstance(context).getAccessToken());
		return req;
	}
}