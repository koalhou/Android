/**
 * @公司名称：YUTONG
 * @作者：zhouzc
 * @版本号：1.0
 * @生成日期：2013-11-9 下午3:42:52
 * @功能描述：
 */
package com.yutong.clw.ygbclient.view.widget;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yutong.clw.ygbclient.R;

/**
 * @author zhouzc 2013-11-9 下午3:42:52
 * 
 */
public class ExDropDownList {

	private PopupWindow pop_type;
	private ListView lv;
	private Context mContext;

	private Drawable bg_first;
	private Drawable bg_middle;
	private Drawable bg_last;
	private Drawable bg_only;
	private ExDropListAdapter adapter;
	private List<String> mData;
	private Drawable bg_divider;

	private int textColor;
	
	
	public ExDropDownList(Context context, int width, int height,
			List<String> data) {
		mContext = context;
		lv = new ListView(context);
		lv.setDividerHeight(2);
		pop_type = new PopupWindow(lv, width, height, true);
		pop_type.setBackgroundDrawable(new ColorDrawable(00000000));// 这个是确保背景可点
		pop_type.setOutsideTouchable(true);
		mData = data;
		adapter = new ExDropListAdapter();
		lv.setAdapter(adapter);
		lv.setClickable(true);
		lv.setBackgroundResource(R.drawable.bg_exdrop_shadow);
		lv.setPadding(10, 0, 10, 10);
		bg_first = new ColorDrawable(Color.WHITE);
		bg_middle = new ColorDrawable(Color.WHITE);
		bg_last = new ColorDrawable(Color.WHITE);
		bg_only = new ColorDrawable(Color.WHITE);
		textColor=Color.WHITE;
		bg_divider = new ColorDrawable(Color.BLACK);
		
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (clistener != null)
					clistener.onItemClick(arg0, arg1, arg2, arg3);
			}
		});
		
	}

	public void setItemDrawable(Drawable first, Drawable middle, Drawable last,
			Drawable only) {
		bg_first = first;
		bg_middle = middle;
		bg_last = last;
		bg_only = only;
	}

	public void setDivider(Drawable divider) {
		bg_divider = divider;
		//lv.setDivider(divider);
		//lv.invalidate();
	}

	public void setTextColor(int textColor) {
		this.textColor = textColor;
	}

	public void setWidth(int width) {
		pop_type.setWidth(width + 20);
	}

	public void setHeight(int height) {
		pop_type.setHeight(height);
	}

	public void reloadData(List<String> data) {
		mData = data;
		adapter.notifyDataSetChanged();
	}

	public void showAsDropDown(View anchor) {
		pop_type.showAsDropDown(anchor);
	}

	public void showAsDropDown(View anchor, int xoff, int yoff) {
		pop_type.showAsDropDown(anchor, xoff - 10, yoff);
	}

	public void showAsDropDown(View parent, int gravity, int x, int y) {
		pop_type.showAtLocation(parent, gravity, x, y);
	}

	public boolean isShowing() {
		return pop_type.isShowing();
	}

	public void dismiss() {

		pop_type.dismiss();
	}

	OnItemClickListener clistener;

	public void setOnItemClickListener(OnItemClickListener clistener) {
		this.clistener = clistener;
	}

	class ExDropListAdapter extends BaseAdapter {

		public ExDropListAdapter() {

		}

		@Override
		public int getCount() {
			return mData == null ? 0 : mData.size();
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			ViewHolder holder = null;
			if (arg1 == null) {
				arg1 = View.inflate(mContext,
						R.layout.control_exdropdownlistitem, null);
				holder = new ViewHolder();
				holder.BODY = (LinearLayout) arg1.findViewById(R.id.tv_ce_body);
				holder.CONTENT = (TextView) arg1
						.findViewById(R.id.tv_ce_content);
				arg1.setTag(holder);

			} else {
				holder = (ViewHolder) arg1.getTag();
			}
			if (arg0 == 0 && getCount() == 1)
				holder.BODY.setBackgroundDrawable(bg_only);
			else if (arg0 == 0)
				holder.BODY.setBackgroundDrawable(bg_first);
			else if (arg0 == getCount() - 1)
				holder.BODY.setBackgroundDrawable(bg_last);
			else
				holder.BODY.setBackgroundDrawable(bg_middle);
			holder.CONTENT.setText(mData.get(arg0));
			holder.CONTENT.setTextColor(textColor);
			return arg1;
		}

		class ViewHolder {
			LinearLayout BODY;
			TextView CONTENT;
		}
	}
}
