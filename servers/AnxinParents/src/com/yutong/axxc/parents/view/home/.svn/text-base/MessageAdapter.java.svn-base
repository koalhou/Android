package com.yutong.axxc.parents.view.home;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.beans.MsgRecordBean;
import com.yutong.axxc.parents.common.context.StringUtil;
import com.yutong.axxc.parents.view.common.ActivityCommConstant;
import com.yutong.axxc.parents.view.common.YtApplication;

public class MessageAdapter extends BaseAdapter {

	private List<PushMessage> msgs = new ArrayList<PushMessage>();
	private Context context;
	private LayoutInflater inflater;
	private View.OnClickListener callbtnlistener = new callBtnOnOkListener();
	private View.OnClickListener curgpsbtnlistener = null;
	private View.OnClickListener gpsbtnlistener = new gpsBtnOnOkListener();

	public MessageAdapter(Context context,
			View.OnClickListener curgpsbtnlistener) {
		super();
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.curgpsbtnlistener = curgpsbtnlistener;
	}

	public MessageAdapter(Context context, List<PushMessage> messages) {
		super();
		this.context = context;
		this.msgs = messages;

	}

	public void SetDatas(List<PushMessage> messages) {
		this.msgs = messages;
	}

	@Override
	public int getCount() {
		return msgs.size();
	}

	@Override
	public Object getItem(int position) {
		return msgs.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		PushMessage message = msgs.get(position);
		// System.out.println("position===========" + position);
		try {
			ViewHolder holder;
			int type = message.getType();
			if (convertView == null
					|| (holder = (ViewHolder) convertView.getTag()).flag != position) {

				holder = new ViewHolder();
				holder.flag = position;
				switch (type) {
				case PushMessage.CURRENTMSG:

					convertView = LayoutInflater.from(context).inflate(
							R.layout.timeline_listitem_current, null);

					break;
				case PushMessage.FIRSTMSG:
					convertView = LayoutInflater.from(context).inflate(
							R.layout.timeline_listitem_first, null);

					break;
				case PushMessage.NORMALMSG:
				default:
					convertView = LayoutInflater.from(context).inflate(
							R.layout.timeline_listitem_normal, null);

					break;

				}
				buildHolder(holder, type, convertView);
				updateValue(type, holder, message);
				setListener(type, holder, message);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();

				updateValue(type, holder, message);
				setListener(type, holder, message);
			}
		} catch (Exception e) {
			Logger.e(getClass(), e.getMessage());
		}
		return convertView;
	}

	private void setListener(int type, ViewHolder holder, PushMessage m) {

		switch (type) {

		case PushMessage.CURRENTMSG:
			if (m.isShowCall()) {
				holder.callbtn.setOnClickListener(null);
				holder.callbtn.setOnClickListener(this.callbtnlistener);
			}
			if (m.isShowGps()) {
				holder.gpsbtn.setOnClickListener(null);
				if (this.curgpsbtnlistener != null)
					holder.gpsbtn.setOnClickListener(this.curgpsbtnlistener);
			}
			break;
		case PushMessage.NORMALMSG:
			if (m.isShowCall()) {
				holder.callbtn.setOnClickListener(null);
				holder.callbtn.setOnClickListener(this.callbtnlistener);
			}
			if (m.isShowGps()) {
				holder.gpsbtn.setOnClickListener(null);
				holder.gpsbtn.setOnClickListener(this.gpsbtnlistener);
			}

			break;
		}
	}

	private void updateValue(int type, ViewHolder holder, PushMessage m) {
		try {
			// holder.body.setText(m.getBody());
			String html = m.getBody();
			holder.body.setText(Html.fromHtml(html));
			holder.time.setText(m.getTime());

			switch (type) {
			case PushMessage.CURRENTMSG:
				holder.callbtn.setVisibility(m.isShowCall() ? View.VISIBLE
						: View.GONE);
				holder.gpsbtn.setVisibility(m.isShowGps() ? View.VISIBLE
						: View.GONE);
				holder.detailLL.setVisibility(m.isShowDetail() ? View.VISIBLE
						: View.GONE);
				holder.btnLL
						.setVisibility((m.isShowCall() || m.isShowGps()) ? View.VISIBLE
								: View.INVISIBLE);

				holder.driver.setText(m.getDriver());
				holder.speed.setText(m.getSpeed());
				holder.teacher.setText(m.getTeachername());

				holder.callbtn.setTag(m);
				holder.gpsbtn.setTag(m);

				break;
			case PushMessage.FIRSTMSG:
				break;
			case PushMessage.NORMALMSG:

			default:
				holder.callbtn.setVisibility(m.isShowCall() ? View.VISIBLE
						: View.GONE);
				holder.gpsbtn.setVisibility(m.isShowGps() ? View.VISIBLE
						: View.GONE);
				holder.btnLL
						.setVisibility((m.isShowCall() || m.isShowGps()) ? View.VISIBLE
								: View.INVISIBLE);
				String t = StringUtil.isNull(m.getStationname()) ? "无站点名称" : m
						.getStationname();
				holder.gpsbtn.setText(t);
				holder.callbtn.setTag(m);
				holder.gpsbtn.setTag(m);

				break;
			}
		} catch (Exception e) {
			Logger.e(getClass(), e.getMessage());
		}
	}

	private void buildHolder(ViewHolder holder, int type, View v) {
		holder.body = (TextView) v.findViewById(R.id.timeline_body_titleTV);
		holder.time = (TextView) v.findViewById(R.id.timeline_timeTV);
		holder.dot = (ImageView) v.findViewById(R.id.timeline_dotIV);

		switch (type) {
		case PushMessage.CURRENTMSG:
			holder.btnLL = (LinearLayout) v.findViewById(R.id.busbtnLL);
			holder.callbtn = (Button) v.findViewById(R.id.callBtn);
			holder.detailLL = (LinearLayout) v.findViewById(R.id.busdetailLL);
			holder.driver = (TextView) v.findViewById(R.id.drivervalue);
			holder.gpsbtn = (Button) v.findViewById(R.id.gpsBtn);
			holder.speed = (TextView) v.findViewById(R.id.speedvalue);
			holder.teacher = (TextView) v.findViewById(R.id.teachervalue);

			break;
		case PushMessage.FIRSTMSG:
			break;
		case PushMessage.NORMALMSG:
		default:
			holder.btnLL = (LinearLayout) v.findViewById(R.id.busbtnLL);
			holder.callbtn = (Button) v.findViewById(R.id.callBtn);
			holder.gpsbtn = (Button) v.findViewById(R.id.gpsBtn);

			break;

		}

	}

	static class ViewHolder {
		ImageView dot;
		TextView body;
		TextView time;

		TextView driver;
		TextView speed;
		TextView teacher;

		Button gpsbtn;
		Button callbtn;

		LinearLayout btnLL;
		LinearLayout detailLL;

		int flag = -1;
	}

	private class callBtnOnOkListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			PushMessage m = (PushMessage) v.getTag();
			if (m == null) {
				Logger.e(this.getClass(), "Pushmessage为null。");
				return;
			}

			if (StringUtil.isNull(m.getTeacherphone())) {
				Toast.makeText(
						YtApplication.getInstance().getApplicationContext(),
						"无电话信息。", Toast.LENGTH_SHORT).show();
				return;
			}
			Uri uri = Uri.parse("tel:" + m.getTeacherphone());
			Intent it = new Intent(Intent.ACTION_DIAL, uri);
			it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			YtApplication.getInstance().getApplicationContext()
					.startActivity(it);

			// Toast.makeText(YtApplication.getInstance().getApplicationContext(),
			// "呼叫：" + m.getBody(), Toast.LENGTH_SHORT).show();
		}
	}

	private class gpsBtnOnOkListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			PushMessage m = (PushMessage) v.getTag();
			if (m == null) {
				Logger.e(this.getClass(), "Pushmessage为null。");
				return;
			}
			try {
				if (!checkExsitGPS(m)) {
					Toast.makeText(
							YtApplication.getInstance().getApplicationContext(),
							"无位置信息！", Toast.LENGTH_SHORT).show();
					return;
				}
				Intent intent2 = new Intent(YtApplication.getInstance()
						.getApplicationContext(), LocationMapActivity.class);
				intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				Bundle bundle1 = new Bundle();

				bundle1.putString(ActivityCommConstant.STATION_NAME,
						m.getStationname());
				bundle1.putSerializable(ActivityCommConstant.LOCATIN_MSG,
						m.getInnerMsg());
				bundle1.putSerializable(ActivityCommConstant.STUDENT_INFO,
						m.getStudent());

				intent2.putExtras(bundle1);
				YtApplication.getInstance().getApplicationContext()
						.startActivity(intent2);

			} catch (Exception e) {
				Logger.e(getClass(), e.getMessage());
				Toast.makeText(
						YtApplication.getInstance().getApplicationContext(),
						"发生错误！", Toast.LENGTH_SHORT).show();
			}
		}
	}

	public boolean checkExsitGPS(PushMessage m) {
		try {
			String zero = "0.0";
			boolean b = !StringUtil.isNull(m.getGps_lat());
			boolean b1 = !StringUtil.isNull(m.getGps_lon());
			boolean b3 = !StringUtil.equals(zero, m.getGps_lat());
			boolean b4 = !StringUtil.equals(zero, m.getGps_lon());
			return b && b1 && b3 && b4;
		} catch (Exception e) {
			Logger.e(getClass(), e.getMessage());
			return false;
		}
	}
}
