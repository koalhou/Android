package com.yutong.axxc.parents.view.settings.child;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.business.student.GetStudentMsgRecordBiz;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.StringTemplateHelper;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.Tools;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.beans.MsgRecordBean;
import com.yutong.axxc.parents.common.beans.StudentInfoBean;
import com.yutong.axxc.parents.view.common.LoadingOverlay;
import com.yutong.axxc.parents.view.common.MessageBuilder;
import com.yutong.axxc.parents.view.common.MessageBuilder.msgconfig;
import com.yutong.axxc.parents.view.common.PullToRefreshView;
import com.yutong.axxc.parents.view.common.PullToRefreshView.OnHeaderRefreshListener;
import com.yutong.axxc.parents.view.common.YtHandler;
import com.yutong.axxc.parents.view.home.ExtendedListView;
import com.yutong.axxc.parents.view.home.PushMessage;
import com.yutong.axxc.parents.view.home.state.SrvStateContext;

/**
 * 
 * 历史记录细节页---viewpager的单页
 * 
 * @author zhangyongn
 * 
 */
public class RecordPageView {
	private final static String vehicle_lon = "vehicle_lon";
	private final static String vehicle_lat = "vehicle_lat";
	private final static String station_name = "station_name";
	private final static String teacher_phone = "teacher_phone";
	private final static String teacher = "teacher";

	private SrvStateContext stateContext;
	// 历史消息列表
	private List<MsgRecordBean> historymsgs = new ArrayList<MsgRecordBean>();

	private List<PushMessage> messages = new ArrayList<PushMessage>();

	private View view;
	private LoadingOverlay loadingoverlay;

	private TravelRecordTimeLineActivity activity;

	private Calendar currentCal;
	private StudentInfoBean child;

	private LayoutInflater inflater;

	/** 下拉刷新对象 */
	private PullToRefreshView pullToRefreshView;

	private ExtendedListView listView;

	private RecordAdapter adapter;

	private YtAsyncTask msgBiz; // 历史消息

	public RecordPageView(TravelRecordTimeLineActivity activity,
			Calendar curCal, StudentInfoBean chld) {
		this.activity = activity;
		inflater = LayoutInflater.from(activity);
		this.currentCal = curCal;
		this.child = chld;
		this.creatView();
	}

	private void creatView() {
		view = inflater.inflate(R.layout.timeline_pager_content, null);

		RelativeLayout arrowRL = (RelativeLayout) view
				.findViewById(R.id.arrowRL);
		arrowRL.setVisibility(View.GONE);

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

						loadingoverlay.setVisibility(View.INVISIBLE);
						pullToRefreshView.onHeaderRefreshComplete();
					}
				});
		adapter = new RecordAdapter(activity);
		listView.setAdapter(adapter);

		this.loadDatas();
	}

	private final int OK = 0;

	private void loadDatas() {
		view.findViewById(R.id.loadingoverlay).setVisibility(View.VISIBLE);

		startLoadMsgsTask();

		// PageHandler mHandler = new PageHandler(this);
		// mHandler.sendEmptyMessageDelayed(OK, 3000);

	}

	public void start() {
		Logger.i(getClass(), "view start");

	}

	public void stop() {
		Logger.i(getClass(), "view stop");

	}

	public void destroy() {
		if (msgBiz != null)
			msgBiz.cancel();

		this.stop();
	}

	private void startLoadMsgsTask() {
		if (msgBiz != null) {
			msgBiz.cancel();
			msgBiz = null;
		}
		msgBiz = new GetStudentMsgRecordBiz(activity, new LoadMsgHandler(this),
				this.child.getCld_id(), Tools.getFormatTime(this.currentCal));
		msgBiz.execute();
	}

	public View getView() {
		return this.view;
	}

	public Calendar getCurrentCal() {
		return currentCal;
	}

	private static class LoadMsgHandler extends YtHandler {
		private final WeakReference<RecordPageView> viewW;

		public LoadMsgHandler(RecordPageView view) {
			super();
			this.viewW = new WeakReference<RecordPageView>(view);
		}

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			try {
				
				RecordPageView v = viewW.get();
				super.handleMessage(msg, v.activity);

				switch (msg.what) {
				case ThreadCommStateCode.REMOTE_DATA_CHANGED:
				case ThreadCommStateCode.REMOTE_DATA_NO_CHANGED:
				case ThreadCommStateCode.COMMON_SUCCESS:

					v.historymsgs = (List<MsgRecordBean>) msg.obj;

					break;
				case ThreadCommStateCode.COMMON_FAILED:
					Logger.e(this.getClass(), msg.obj);

					break;
				}

				if (v.historymsgs != null)
					Collections.sort(v.historymsgs, v.new SortByTime());
				v.buildMsgs(v.historymsgs);
				v.adapter.SetDatas(v.messages);
				v.listView.setDivider(null);
				v.loadingoverlay.setVisibility(View.GONE);
				v.pullToRefreshView.onHeaderRefreshComplete();

			} catch (Exception e) {
				e.printStackTrace();
			}
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
			m.setGps_lat(b.getContent(vehicle_lat));
			m.setGps_lon(b.getContent(vehicle_lon));
			m.setTeachername(b.getContent(teacher));
			m.setTeacherphone(b.getContent(teacher_phone));
			m.setStationname(b.getContent(station_name));

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
		Map<String, String> data = MessageBuilder.buildDataMap(msg, child);
		String body = StringTemplateHelper.parse(c.getBody(), data);
		return body;
	}

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
}
