package com.yutong.clw.ygbclient.view.widget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.common.Logger;

/**
 * 顶部弹出悬浮框
 * 
 * @author zhouzc
 * 
 */
public class PopCheckList {

	PopupWindow mPop;
	Context mcontext;
	OnCheckChangedListener listener;
	OnDismissListener dismisslistener;
	ArrayList<HashMap<String, Object>> mdata = new ArrayList<HashMap<String, Object>>();
	SimpleAdapter adapter;

	int popwidth = -1;

	public PopCheckList(Context context) {
		mcontext = context;
		initView();
	}

	private int[] disableIndexes;

	/**
	 * 构造函数
	 * 
	 * @param context
	 *            上下文
	 * @param intwidth
	 *            弹出框宽度
	 */
	public PopCheckList(Context context, int intwidth) {
		mcontext = context;
		popwidth = intwidth;
		initView();
	}

	/**
	 * 设置数据，并设置默认选中项
	 * 
	 * @param data
	 *            数据源
	 * @param index
	 *            默认选中项
	 */
	public void setData(List<String> data, int index) {

		mdata.clear();
		if (data == null || data.size() == 0)
			return;
		if (index >= data.size() || index < 0)
			index = 0;

		int count = 0;
		for (String item : data) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("flag", count == index);
			map.put("name", item);
			mdata.add(map);
			count++;
		}
	}

	public void setSelection(int index, boolean force) {
		if (index >= mdata.size() || index < 0)
			index = 0;
		SingleSelect(index);
		if (force && listener != null)
			listener.OnChecked(index, mdata.get(index).get("name").toString());
	}

	public int getSelection() {
		for (int i = 0; i < mdata.size(); i++) {
			if (mdata.get(i).get("flag").toString().equals("true"))
				return i;
		}
		return -1;
	}

	public String getSelectionValue() {
		
		for (int i = 0; i < mdata.size(); i++) {
			if (mdata.get(i).get("flag").toString().equals("true"))
				return mdata.get(i).get("name").toString();
		}
		return null;
	}

	public void setSelection(int index) {
		setSelection(index, true);
	}

	/**
	 * 刷新数据显示
	 */
	public void notifyDataSetChanged() {
		if (adapter != null)
			adapter.notifyDataSetChanged();
	}

	private void SingleSelect(int index) {
		if (index < 0 || index >= mdata.size()) {
			Logger.d(this.getClass(), "SingleSelect 选择超出范围");
			return;
		}
		for (int i = 0; i < mdata.size(); i++) {
			mdata.get(i).put("flag", i == index);
		}
		adapter.notifyDataSetChanged();
	}

	private void initView() {

		View content = View.inflate(mcontext, R.layout.control_linelist, null);
		if (popwidth == -1) {
			mPop = new PopupWindow(content,
					WindowManager.LayoutParams.WRAP_CONTENT,
					// DensityUtil.dip2px(mcontext, popwidth),
					WindowManager.LayoutParams.WRAP_CONTENT);
		} else {
			mPop = new PopupWindow(content, popwidth,
					WindowManager.LayoutParams.WRAP_CONTENT);
		}
		mPop.setAnimationStyle(R.style.PopupAnimation);
		
		mPop.setBackgroundDrawable(new ColorDrawable(00000000));
		mPop.setFocusable(true);// menu菜单获得焦点 如果没有获得焦点menu菜单中的控件事件无法响应
		mPop.setOutsideTouchable(true);
		ListView lv_lines = (ListView) content.findViewById(R.id.lv_cl);
		adapter = new SimpleAdapter(mcontext, mdata,
				R.layout.control_linelist_item,
				new String[] { "flag", "name" }, new int[] { R.id.cb_cli_flag,
						R.id.tv_cli_name });

		lv_lines.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (disableIndexes != null
						&& Arrays.asList(disableIndexes).contains(arg2)) {
					return;
				}
	            
				SingleSelect(arg2);
				if (listener != null) {
					listener.OnChecked(arg2, mdata.get(arg2).get("name")
							.toString());
				}

			}
		});
		lv_lines.setAdapter(adapter);

		mPop.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				if (dismisslistener != null)
					dismisslistener.onDismiss();
			}
		});

	}

	public int getWidth() {
		if (mPop.getWidth() != LayoutParams.MATCH_PARENT
				&& mPop.getWidth() != LayoutParams.WRAP_CONTENT)
			return mPop.getWidth();
		mPop.getContentView().measure(
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		return mPop.getContentView().getMeasuredWidth();
	}

	public int getHeight() {
		if (mPop.getHeight() != LayoutParams.MATCH_PARENT
				&& mPop.getHeight() != LayoutParams.WRAP_CONTENT)
			return mPop.getHeight();

		mPop.getContentView().measure(
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		return mPop.getContentView().getMeasuredHeight() * mdata.size();
	}

	/**
	 * 设置弹出显示到View下面
	 * 
	 * @param anchor
	 *            锚点View
	 * @param xoff
	 *            x左边偏移
	 * @param yoff
	 *            y坐标偏移
	 */
	public void showAsDropDown(View anchor, int xoff, int yoff) {
		mPop.showAsDropDown(anchor, xoff, yoff);
	}

	public void showAtLocation(View parent, int gravity, int xoff, int yoff) {
		mPop.showAtLocation(parent, gravity, xoff, yoff);
	}

	/**
	 * 隐藏
	 */
	public void dismiss() {
		mPop.dismiss();
	}

	public interface OnCheckChangedListener {

		public void OnChecked(int index, String value);
	}

	/**
	 * 设置选择项更改侦听器
	 * 
	 * @param listener
	 */
	public void setOnCheckChangedListener(OnCheckChangedListener listener) {
		this.listener = listener;
	}

	/**
	 * 设置弹出窗隐藏侦听器
	 * 
	 * @param dismisslistener
	 */
	public void setOnDismisslistener(OnDismissListener dismisslistener) {
		this.dismisslistener = dismisslistener;
	}

	class CheckListItem {
		CheckListItem(boolean checked, boolean enable, String txt) {
			this.IsChecked = checked;
			this.IsEnable = enable;
			this.Text = txt;

		}

		boolean IsChecked;
		boolean IsEnable;
		String Text;

	}

	class CheckListAdapter extends BaseAdapter {

		List<CheckListItem> mData;
		Context mContext;

		CheckListAdapter(Context context, List<CheckListItem> data) {
			this.mContext = context;
			mData = data;
		}

		@Override
		public int getCount() {
			if (mData == null)
				return 0;
			return mData.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			Holder holder = null;
			if (arg1 == null) {
				arg1 = View.inflate(this.mContext,
						R.layout.control_linelist_item, null);
				holder = new Holder();
				holder.Checked = (CheckBox) arg1.findViewById(R.id.cb_cli_flag);
				holder.Name = (TextView) arg1.findViewById(R.id.tv_cli_name);
				arg1.setTag(holder);
			} else {
				holder = (Holder) arg1.getTag();
			}
			final int dindex=arg0;
			 CheckListItem itemdata=mData.get(dindex);
			 final String txt= itemdata.Text;
			holder.Checked .setChecked(itemdata.IsChecked);
			holder.Name.setText(txt);
			if(itemdata.IsEnable)
			{
				arg1.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						SingleSelect(dindex);
						if (listener != null) {
							listener.OnChecked(dindex, txt);
						}
					}
				});
			}else{
				arg1.setOnClickListener(null);
			}
			return arg1;
		}

		class Holder {
			CheckBox Checked;
			TextView Name;
		}
	}

	public ArrayList<HashMap<String, Object>> getMdata() {
		return mdata;
	}

	public void setMdata(ArrayList<HashMap<String, Object>> mdata) {
		this.mdata = mdata;
	}
	
}
