package com.yutong.axxc.parents.view.home;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.business.common.CommonPushMsgUtil;
import com.yutong.axxc.parents.business.student.GetStudentMsgRecordBiz;
import com.yutong.axxc.parents.business.student.GetStudentStatusBiz;
import com.yutong.axxc.parents.common.LocationUtils;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.NeuGeoPoint;
import com.yutong.axxc.parents.common.StringTemplateHelper;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.Tools;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.beans.MsgRecordBean;
import com.yutong.axxc.parents.common.beans.StudentInfoBean;
import com.yutong.axxc.parents.common.beans.WeatherInfoBean;
import com.yutong.axxc.parents.view.common.LoadingOverlay;
import com.yutong.axxc.parents.view.common.MessageBuilder;
import com.yutong.axxc.parents.view.common.MessageBuilder.curmsgconfig;
import com.yutong.axxc.parents.view.common.MessageBuilder.msgconfig;
import com.yutong.axxc.parents.view.common.PullToRefreshView;
import com.yutong.axxc.parents.view.common.PullToRefreshView.OnHeaderRefreshListener;
import com.yutong.axxc.parents.view.common.ActivityCommConstant;
import com.yutong.axxc.parents.view.common.StudentStateConstant;
import com.yutong.axxc.parents.view.common.YtApplication;
import com.yutong.axxc.parents.view.common.YtHandler;
import com.yutong.axxc.parents.view.home.state.IOnMsgUpdateListener;
import com.yutong.axxc.parents.view.home.state.SrvStateContext;

/**
 * @author zhangyongn
 */
public class TimeLineListPagerView {

	private SrvStateContext stateContext;

	// 历史消息列表
	private List<MsgRecordBean> historymsgs = new ArrayList<MsgRecordBean>();

	private String curSrvState = StudentStateConstant.SRV_SCHOOL_BEFORE;

	private List<PushMessage> messages = new ArrayList<PushMessage>();

	private View view;

	private LoadingOverlay loadingoverlay;

	private MainActivity activity;

	private Calendar currentCal;

	private StudentInfoBean child;

	private LayoutInflater inflater;

	private View.OnClickListener curgpsBtnOnOkListener = new curgpsBtnOnOkListener();
	/** 下拉刷新对象 */
	private PullToRefreshView pullToRefreshView;

	private ExtendedListView listView;

	private MessageAdapter adapter;

	private YtAsyncTask msgBiz; // 历史消息

	private YtAsyncTask curStateBiz; // 小孩状态

	public TimeLineListPagerView(MainActivity activity, Calendar curCal,
			StudentInfoBean chld) {
		this.activity = activity;
		inflater = LayoutInflater.from(activity);
		this.setCurrentCal(curCal);
		this.setChild(chld);
		this.creatView();
	}

	private void creatView() {
		view = inflater.inflate(R.layout.timeline_pager_content, null);
		listView = (ExtendedListView) view.findViewById(R.id.list_view);
		loadingoverlay = (LoadingOverlay) view
				.findViewById(R.id.loadingoverlay);
		pullToRefreshView = (PullToRefreshView) view
				.findViewById(R.id.pullToRefreshView);
		pullToRefreshView
				.setOnHeaderRefreshListener(new OnHeaderRefreshListener() {
					@Override
					public void onHeaderRefresh(PullToRefreshView view) {
						// isReflush = true;
						// 这里更新一遍
						stop();
						loadDatas();
						// pullToRefreshView.onHeaderRefreshComplete();
					}
				});
		loadingoverlay
				.addOnCancelListener(new LoadingOverlay.OnCancelListener() {

					@Override
					public void onCancel() {
						stop();
						if (msgBiz != null)
							msgBiz.cancel();
						if (curStateBiz != null)
							curStateBiz.cancel();
						loadingoverlay.setVisibility(View.INVISIBLE);
						pullToRefreshView.onHeaderRefreshComplete();
					}
				});
		adapter = new MessageAdapter(activity, curgpsBtnOnOkListener);
		listView.setAdapter(adapter);

		this.loadDatas();
	}

	private final int OK = 0;

	public void loadDatas() {
		// view.findViewById(R.id.loadingoverlay).setVisibility(View.VISIBLE);

		startLoadMsgsTask();

		// PageHandler mHandler = new PageHandler(this);
		// mHandler.sendEmptyMessageDelayed(OK, 3000);

	}

	public void start() {
		Logger.i(getClass(), "view start");
		if (this.stateContext != null)
			this.stateContext.start(this.getChild());

	}

	public void stop() {
		Logger.i(getClass(), "view stop");
		if (msgBiz != null)
			msgBiz.cancel();
		if (curStateBiz != null)
			curStateBiz.cancel();
		if (this.stateContext != null)
			this.stateContext.stop();

	}

	public void destroy() {
		if (msgBiz != null)
			msgBiz.cancel();
		if (curStateBiz != null)
			curStateBiz.cancel();
		this.stop();
	}

	private void startLoadMsgsTask() {
		if (msgBiz != null) {
			msgBiz.cancel();
			msgBiz = null;
		}
		msgBiz = new GetStudentMsgRecordBiz(activity, new LoadMsgHandler(this),
				this.getChild().getCld_id(),
				Tools.getFormatTime(this.getCurrentCal()));
		msgBiz.execute();
	}

	public void startLoadCurStateTask() {
		if (curStateBiz != null) {
			curStateBiz.cancel();
			curStateBiz = null;
		}
		curStateBiz = new GetStudentStatusBiz(activity,
				new LoadCurStateHandler(this), this.getChild().getCld_id());
		curStateBiz.execute();

	}

	public View getView() {
		return this.view;
	}

	public Calendar getCurrentCal() {
		return currentCal;
	}

	private static class LoadMsgHandler extends YtHandler {
		private final WeakReference<TimeLineListPagerView> viewW;

		public LoadMsgHandler(TimeLineListPagerView view) {
			super();
			this.viewW = new WeakReference<TimeLineListPagerView>(view);
		}

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			TimeLineListPagerView v = viewW.get();
			if (v == null)
			{
				Logger.i(getClass(), "发生错误！");
				return;
			}
			try {

				super.handleMessage(msg, v.activity);

				switch (msg.what) {
				case ThreadCommStateCode.REMOTE_DATA_CHANGED:
				case ThreadCommStateCode.REMOTE_DATA_NO_CHANGED:
				case ThreadCommStateCode.COMMON_SUCCESS:
					if (Tools.isToday(v.getCurrentCal())) {
						CommonPushMsgUtil.deleteChildNotificationCount(v
								.getChild().getCld_id());
					}
					v.historymsgs = (List<MsgRecordBean>) msg.obj;

					break;
				case ThreadCommStateCode.COMMON_FAILED:
					Logger.e(this.getClass(), msg.obj);

					break;
				default:
					Logger.e(this.getClass(), msg.obj);
					break;
				}

				if (v.historymsgs != null)
					Collections.sort(v.historymsgs, v.new SortByTime());
				v.buildMsgs(v.historymsgs);
				if (!Tools.isToday(v.getCurrentCal())) {

					v.adapter.SetDatas(v.messages);
					v.listView.setDivider(null);
					v.loadingoverlay.setVisibility(View.INVISIBLE);
					v.pullToRefreshView.onHeaderRefreshComplete();
					return;
				}
				v.loadingoverlay.setLoadingTip("获取学生当前状态...");
				v.startLoadCurStateTask();

			} catch (Exception e) {
				e.printStackTrace();
				if (v != null) {
					v.loadingoverlay.setVisibility(View.INVISIBLE);
					Toast.makeText(v.activity, "失败", Toast.LENGTH_SHORT).show();
				}
			}
		}
	}

	/**
	 * 加载学生当前状态Handler
	 * 
	 * @author zhangyongn
	 */
	private static class LoadCurStateHandler extends YtHandler {
		private final WeakReference<TimeLineListPagerView> viewW;

		public LoadCurStateHandler(TimeLineListPagerView view) {
			super();
			this.viewW = new WeakReference<TimeLineListPagerView>(view);
		}

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			try {

				TimeLineListPagerView v = viewW.get();
				super.handleMessage(msg, v.activity);

				switch (msg.what) {
				case ThreadCommStateCode.COMMON_SUCCESS:
					// v.loadingoverlay.setLoadingTip("获取学生当前状态...");
					v.curSrvState = (String) msg.obj;
					break;
				case ThreadCommStateCode.COMMON_FAILED:
					Logger.e(this.getClass(), msg.obj);

					v.curSrvState = StudentStateConstant.SRV_SCHOOL_BEFORE;

					break;
				}

				v.buldCurrentMsg(v.curSrvState);
				v.adapter.SetDatas(v.messages);
				v.listView.setDivider(null);
				v.loadingoverlay.setVisibility(View.INVISIBLE);
				v.pullToRefreshView.onHeaderRefreshComplete();
				v.activity.updateChildMsg();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void buldCurrentMsg(String cursrvstate) {
		if (this.stateContext == null)
			this.stateContext = new SrvStateContext();
		this.stateContext.init(this.activity);
		msgupdatelistener = new CurMsgUpdateListener();
		this.stateContext.addListener(msgupdatelistener);

		PushMessage m = null;
		this.stateContext.switchTo(cursrvstate, this.getChild());
		m = this.stateContext.getMsg(getChild());
		messages.add(0, m);// 在索引0的位置插入当前状态item
	}

	private CurMsgUpdateListener msgupdatelistener = null;

	private class CurMsgUpdateListener implements IOnMsgUpdateListener {

		@Override
		public void onMessageUpdated(PushMessage msg) {
			// TODO Auto-generated method stub
			if (messages == null || messages.size() <= 0)
				return;
			messages.set(0, msg);
			adapter.notifyDataSetChanged();
		}

	}

	private void buildMsgs(List<MsgRecordBean> returnmsgs) {
		messages.clear();
		buildFirstMsg();
		PushMessage m = null;
		if (returnmsgs == null || returnmsgs.size() <= 0) {
			m = new PushMessage();
			m.setBody("无消息记录。");
			m.setTime(Tools.getFormatTime(Calendar.getInstance(), "HH:mm"));
			m.setType(PushMessage.NORMALMSG);
			messages.add(0, m);
			return;
		}

		for (int i = 0; i < returnmsgs.size(); i++) {
			MsgRecordBean b = returnmsgs.get(i);
			msgconfig c = MessageBuilder.getMsgConfig(b);
			m = new PushMessage();
			if (c != null) {
				m.setBody(buildMsgBody(c, b));
				m.setShowCall(c.isCall());
				m.setShowGps(c.isGps());
			} else {
				m.setBody(b.getRule_id());

			}
			// 直接转换为百度地图坐标
			NeuGeoPoint bdGeoPoint = LocationUtils.fromWgs84ToBaidu(
					b.getContent(MsgRecordBean.KEY_GPS_LON),
					b.getContent(MsgRecordBean.KEY_GPS_LAT));
			m.setGps_lon(String.valueOf(bdGeoPoint.getX()));
			m.setGps_lat(String.valueOf(bdGeoPoint.getY()));

			m.setTeachername(b.getContent(MsgRecordBean.KEY_TEACHER));
			m.setTeacherphone(b.getContent(MsgRecordBean.KEY_TEACHER_PHONE));
			m.setStationname(b.getContent(MsgRecordBean.KEY_STATION_NAME));

			m.setTime(Tools.getFormatTime(b.getMsg_time(), "yyyyMMddHHmmss",
					"HH:mm"));

			m.setInnerMsg(b);
			m.setStudent(child);
			m.setType(PushMessage.NORMALMSG);
			messages.add(0, m);
		}
	}

	private void buildFirstMsg() {
		PushMessage m = new PushMessage();
		m.setBody("新的一天开始。");
		m.setTime("00:00");
		m.setType(PushMessage.FIRSTMSG);
		messages.add(0, m);

	}

	/**
	 * 建造历史消息正文
	 * 
	 * @param msg
	 * @return
	 */
	private String buildMsgBody(msgconfig c, MsgRecordBean msg) {
		Map<String, String> data = MessageBuilder.buildDataMap(msg, getChild());
		String body = StringTemplateHelper.parse(c.getBody(), data);
		return body;
	}

	// 这个类比较结果不一定正确。
	public class SortByTime implements Comparator {
		public int compare(Object o1, Object o2) {
			MsgRecordBean s1 = (MsgRecordBean) o1;
			MsgRecordBean s2 = (MsgRecordBean) o2;
			if (Tools.strToDate(s1.getMsg_time(), "yyyyMMddHHmmss").getTime() > Tools
					.strToDate(s2.getMsg_time(), "yyyyMMddHHmmss").getTime())
				return 1;
			else if (Tools.strToDate(s1.getMsg_time(), "yyyyMMddHHmmss")
					.getTime() < Tools.strToDate(s2.getMsg_time(),
					"yyyyMMddHHmmss").getTime())
				return -1;
			return 0;
		}
	}

	private class curgpsBtnOnOkListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			try {
				PushMessage m = (PushMessage) v.getTag();
				if (m == null) {
					Logger.e(this.getClass(), "Pushmessage为null。");
					return;
				}
				// Toast.makeText(YtApplication.getInstance().getApplicationContext(),
				// "current gps：" + m.getBody(), Toast.LENGTH_SHORT).show();

				Intent intent3 = new Intent(activity.getApplication(),
						MainMapActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable(ActivityCommConstant.STUDENT_INFO,
						getChild());
				intent3.putExtras(bundle);
				activity.startActivity(intent3);
			} catch (Exception e) {
				Logger.e(getClass(), e.getMessage());
				Toast.makeText(
						YtApplication.getInstance().getApplicationContext(),
						"发生错误！", Toast.LENGTH_SHORT).show();
			}

		}
	}

	public void OnChildClick() {
		pullToRefreshView.setHeaderRefreshing();
		stop();
		loadDatas();
	}

	/**
	 * @return the child
	 */
	public StudentInfoBean getChild() {
		return child;
	}

	/**
	 * @param child
	 *            the child to set
	 */
	public void setChild(StudentInfoBean child) {
		this.child = child;
	}

	/**
	 * @param currentCal the currentCal to set
	 */
	public void setCurrentCal(Calendar currentCal) {
		this.currentCal = currentCal;
	}
}
