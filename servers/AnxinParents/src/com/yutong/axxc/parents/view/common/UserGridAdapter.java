package com.yutong.axxc.parents.view.common;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.OnHierarchyChangeListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.business.other.ResDownloadBiz;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.beans.ResourceInfoBean;
import com.yutong.axxc.parents.view.util.DensityUtil;

public class UserGridAdapter extends BaseAdapter {

	Context mcontext;
	List<UserGridInfo> mdata;
	UserGridExchangeListener exlistener = null;
	UserGridSelectItemListener sclistener = null;

	private int itemWidth = 60;// dip

	private ISelectChildrenBar msv;
	private List<Integer> selectedIndexs = new ArrayList<Integer>();

	private boolean isSingleSelcect = false;

	/**
	 * 获取当前选中项集合
	 * 
	 * @return
	 */
	public List<Integer> getSelectedIndexs() {
		if (mdata == null || mdata.size() == 0)
			return new ArrayList<Integer>();
		return selectedIndexs;
	}

	/**
	 * 设置当前选中项
	 * 
	 * @param selectedIndex
	 */
	public void setSelectedIndex(int selectedIndex) {
		if (selectedIndex >= mdata.size())
			return;

		if (selectedIndexs != null && selectedIndexs.size() == 1
				&& selectedIndexs.get(0) == selectedIndex) {
			return;
		}

		List<Integer> temp = getCloneSelections();
		if (temp != null && temp.size() == 1 && temp.get(0) == selectedIndex)
			return;
		this.selectedIndexs.clear();
		if (isSingleSelcect) {
			if (selectedIndex != 0) {
				exchangeData(0, selectedIndex);
			}
			this.selectedIndexs.add(0);
		} else
			this.selectedIndexs.add(selectedIndex);
		if (sclistener != null) {
			sclistener.onChanged(temp, this.selectedIndexs);
		}
		notifyDataSetChanged();
	}

	private List<Integer> getCloneSelections() {
		List<Integer> clone = new ArrayList<Integer>();
		if (this.selectedIndexs != null) {
			for (int i : this.selectedIndexs) {
				clone.add(i);
			}
		}
		return clone;
	}

	/**
	 * 改变选中项的状态
	 * 
	 * @param index
	 */
	public void changeSelectStatus(int index) {

		List<Integer> tempselec = getCloneSelections();
		if (selectedIndexs.contains(index)) {
			removeSelectItem(index);
		} else {
			selectedIndexs.add(index);
		}
		if (sclistener != null) {
			sclistener.onChanged(tempselec, this.selectedIndexs);
		}
		notifyDataSetChanged();
	}

	/**
	 * 设置当前选中项集合
	 * 
	 * @param selectedIndexs
	 */
	public void setSelectedIndexs(List<Integer> selectedIndexs) {
		List<Integer> tempselec = getCloneSelections();
		this.selectedIndexs = selectedIndexs;
		if (sclistener != null) {
			sclistener.onChanged(tempselec, this.selectedIndexs);
		}
		notifyDataSetChanged();
	}

	/**
	 * 设置项交换事件侦听
	 * 
	 * @param exlistener
	 */
	public void setExChangelistener(UserGridExchangeListener exlistener) {
		this.exlistener = exlistener;
	}

	/**
	 * 设置选中项改变事件侦听
	 * 
	 * @param sclistener
	 */
	public void setOnUserGridSelectItemChangedListener(
			UserGridSelectItemListener sclistener) {
		this.sclistener = sclistener;
	}

	public UserGridAdapter(Context context, List<UserGridInfo> data,
			ISelectChildrenBar sv) {

		mcontext = context;
		mdata = data;
		msv = sv;
		msv.getGridView().setOnHierarchyChangeListener(
				new OnHierarchyChangeListener() {
					@Override
					public void onChildViewRemoved(View parent, View child) {
						fitLinearParent(mdata.size());
					}

					@Override
					public void onChildViewAdded(View parent, View child) {
						fitLinearParent(mdata.size());
					}
				});
		if (mdata != null) {
			fitLinearParent(mdata.size());
		}
		selectedIndexs.clear();
		selectedIndexs.add(0);
	}

	@Override
	public int getCount() {
		if (mdata == null)
			return 0;
		return mdata.size();
	}

	@Override
	public Object getItem(int arg0) {
		if (mdata == null || mdata.size() <= arg0)
			return null;
		else
			return mdata.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holder = null;
		if (arg1 == null) {
			holder = new ViewHolder();
			arg1 = View.inflate(mcontext, R.layout.userselect_scrollitem, null);

			holder.Background = (LinearLayout) arg1
					.findViewById(R.id.ll_griditem2_bg);
			holder.Head = (ImageView) arg1
					.findViewById(R.id.circularImage_head);
			arg1.setTag(holder);
		} else {
			holder = (ViewHolder) arg1.getTag();
		}
		UserGridInfo info = mdata.get(arg0);

		if (isItemSelected(arg0)) {// 选中状态

			holder.Background.setBackgroundDrawable(mcontext.getResources()
					.getDrawable(R.drawable.userimg_bg_current));
		} else {
			((MarkCircularImage) holder.Head).setNeedMark(false);
			holder.Background.setBackgroundDrawable(mcontext.getResources()
					.getDrawable(R.drawable.userimg_bg_normal));
		}
		((MarkCircularImage) holder.Head).setNeedMark(info.NeedMark);

		if (info.HeadImage != null)
			holder.Head.setImageDrawable(info.HeadImage);
		if (info.ResourceId != null && !(info.ResourceId.equals(""))) {
			handleView2LoadRemoteImage(info);
		}

		((MarkCircularImage) holder.Head).setMessage(info.Message);
		return arg1;
	}

	private void handleView2LoadRemoteImage(final UserGridInfo info) {
		if (info.ResourceId == null || info.ResourceId.equals("")
				|| !info.needRemote)
			return;

		Log.i("", "开始加载【" + info.Name + "】的头像");
		ResDownloadBiz biz = new ResDownloadBiz(mcontext, new YtHandler() {
			@Override
			public void handleMessage(Message msg) {

				switch (msg.what) {
				case ThreadCommStateCode.REMOTE_DATA_CHANGED:
				case ThreadCommStateCode.REMOTE_DATA_NO_CHANGED:
				case ThreadCommStateCode.COMMON_SUCCESS:
					try {
						ResourceInfoBean resource = (ResourceInfoBean) msg.obj;
						byte[] buffer = resource.getResourceBytes();
						Bitmap bitmap = BitmapFactory.decodeByteArray(buffer,
								0, buffer.length);
						if (bitmap == null) {
							Log.i("", "加载【" + info.Name + "】的头像为NULL");
							info.needRemote = true;
						} else {
							BitmapDrawable dra = new BitmapDrawable(bitmap);
							info.HeadImage = dra;
							info.needRemote = false;
							Log.i("", "加载【" + info.Name + "】的头像完成");
						}
						notifyDataSetChanged();
					}
					catch(OutOfMemoryError outmr)
					{
						Log.e("", "加载【" + info.Name + "】的头像异常");
						info.needRemote = true;
						outmr.printStackTrace();
					}
					catch (Exception e) {
						Log.e("", "加载【" + info.Name + "】的头像异常");
						info.needRemote = true;
						e.printStackTrace();
					}
					break;

				default:
					break;
				}

				super.handleMessage(msg);
			}
		}, info.ResourceId);
		info.needRemote = false;// 保证一次只有一个在进行加载
		biz.execute();

	}

	private boolean isExchangeing = false;

	public boolean isSingleSelcect() {
		return isSingleSelcect;
	}

	public void setSingleSelcect(boolean isSingleSelcect) {
		this.isSingleSelcect = isSingleSelcect;
	}

	public int getItemWidth() {
		return itemWidth;
	}

	public void setItemWidth(int itemWidth) {
		this.itemWidth = itemWidth;
	}

	private boolean isItemSelected(int index) {

		if (selectedIndexs == null || selectedIndexs.size() == 0) {
			return false;
		}
		return selectedIndexs.contains(index);

	}

	public void exchange(final int index1, final int index2,
			final boolean scrollToIndex1) {
		Log.d("", "exchange :[" + index1 + "]<==>[" + index2 + "] scro");
		// Log.d("", "exchange start select:" + selectItemsString());

		if (index1 >= 0 && index2 >= 0 && index1 < getCount()
				&& index2 < getCount()) {

			if (index1 != index2) {
				final List<Integer> tempselec = getCloneSelections();

				AnimationListener alistener = new AnimationListener() {
					@Override
					public void onAnimationStart(Animation animation) {
					}

					@Override
					public void onAnimationRepeat(Animation animation) {

					}

					@Override
					public void onAnimationEnd(Animation animation) {
						if (scrollToIndex1) {
							int x = DensityUtil.dip2px(mcontext, itemWidth
									* index1);
							msv.getScrollView().scrollTo(x, 0);
							Log.d("", "exchange ScrollTO :" + x);
						}
						exchangeData(index1, index2);

						notifyDataSetChanged();

						if (sclistener != null) {
							sclistener.onChanged(tempselec, selectedIndexs);
						}
						isExchangeing = false;

						if (exlistener != null) {
							Log.d("", "exchange end  select:"
									+ selectItemsString());
							exlistener.ExchangeEnd(index1, index2);
							if (index1 == 0) {
								exlistener.GotoTop(index2);
							} else if (index2 == 0) {
								exlistener.GotoTop(index1);
							}
						}

					}
				};

				animateExchange(index1, index2, alistener);

			} else {

			}
		}
	}

	public String selectItemsString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		if (this.selectedIndexs != null) {
			for (int num : this.selectedIndexs) {
				builder.append(num + ",");
			}
		}
		builder.append("]");
		return builder.toString();
	}

	private void exchangeData(int index1, int index2) {
		UserGridInfo temp = mdata.get(index1).copy();
		mdata.set(index1, mdata.get(index2).copy());
		mdata.set(index2, temp);
	}

	private void removeSelectItem(int value) {
		if (this.selectedIndexs == null)
			return;
		for (int i = this.selectedIndexs.size() - 1; i >= 0; i--) {
			if (value == this.selectedIndexs.get(i).intValue()) {
				this.selectedIndexs.remove(i);
			}
		}

	}

	public void moveToFirst(int index) {
		exchange(0, index, false);
	}

	public void moveToFirst(int index, boolean scrollToFirst) {
		exchange(0, index, scrollToFirst);
	}

	/**
	 * 刷新所有图片
	 */
	public void refreshAllImages() {
		if (mdata == null || mdata.size() == 0)
			return;
		for (UserGridInfo info : mdata) {
			info.needRemote = true;
		}
		notifyDataSetChanged();
	}

	@Override
	public void notifyDataSetChanged() {
		if (mdata != null) {
			fitLinearParent(mdata.size());
		}
		super.notifyDataSetChanged();
	}

	int lastfitnum = -1;

	/**
	 * 头像列表自适应
	 * 
	 * @param num
	 */
	private void fitLinearParent(int num) {

		if (lastfitnum == num) {
			return;
		} else {
			lastfitnum = num;
		}
		int width = 0;
		int itemwidth = DensityUtil.dip2px(mcontext, itemWidth);
		msv.getGridView().setColumnWidth(itemwidth);
		WindowManager wm = (WindowManager) mcontext
				.getSystemService(Context.WINDOW_SERVICE);

		int windowwidth = wm.getDefaultDisplay().getWidth();// 屏幕宽度
		int pwidth = 0;
		if (msv.getParentView() != null) {
			pwidth = msv.getParentView().getWidth();
		}
		if (pwidth == 0) {
			lastfitnum = -1;
			pwidth = windowwidth;
		}

		int fixnum = 5;
		if (fixnum > num)
			width = fixnum * itemwidth;
		else
			width = ((num) * itemwidth);

		LayoutParams lp = msv.getBackgroundView().getLayoutParams();
		Log.d("", "==>goto fitLinearParent[" + num + "]:" + width);
		if (width != lp.width) {
			if (width < pwidth)
				width = pwidth;
			msv.getBackgroundView().setLayoutParams(
					new FrameLayout.LayoutParams(width,
							LayoutParams.WRAP_CONTENT));
		}
	}

	private void animateExchange(int index1, int index2,
			AnimationListener listener) {

		if (isExchangeing)
			return;
		long animDuration = (index1 == index2) ? 200 : 200 * Math.abs(index1
				- index2);// 动画速度
		isExchangeing = true;
		if (exlistener != null)
			exlistener.ExchangeStart(index1, index2);
		View v1 = msv.getGridView().getChildAt(index1);
		View v2 = msv.getGridView().getChildAt(index2);

		int[] location1 = new int[2];
		int[] location2 = new int[2];
		v1.getLocationOnScreen(location1);
		v2.getLocationOnScreen(location2);
		int xdelta = (location1[0] - location2[0]);
		int ydelta = (location1[1] - location2[1]);

		Animation transani = new TranslateAnimation(0, -xdelta, 0, -ydelta);
		Animation transani1 = new TranslateAnimation(0, xdelta, 0, ydelta);
		transani.setFillEnabled(true);
		transani1.setFillEnabled(true);

		transani1.setDuration(animDuration);
		transani.setDuration(animDuration);
		transani.setAnimationListener(listener);

		v1.startAnimation(transani);
		v2.startAnimation(transani1);

	}

	class ViewHolder {
		public LinearLayout Background;
		public ImageView Head;
	}

	/**
	 * 用户信息，可以再丰富
	 * 
	 * @author zhouzc
	 * 
	 */
	public static class UserGridInfo {

		public UserGridInfo(String childId, String name, Drawable image,
				String msg) {
			ChildId = childId;
			Name = name;
			HeadImage = image;
			Message = msg;
		}

		public UserGridInfo(String childId, String name, Drawable image,
				String msg, String resourceId) {
			ChildId = childId;
			Name = name;
			HeadImage = image;
			Message = msg;
			ResourceId = resourceId;
		}

		public UserGridInfo(String childId, String name, Drawable image,
				String msg, String resourceId, boolean needmark) {
			ChildId = childId;
			Name = name;
			HeadImage = image;
			Message = msg;
			ResourceId = resourceId;
			NeedMark = needmark;
		}

		public String ChildId;
		public String Name;
		public Drawable HeadImage;
		public String Message;
		public String ResourceId;
		public boolean NeedMark = false;

		public boolean needRemote = true;

		public UserGridInfo copy() {
			return new UserGridInfo(ChildId, Name, HeadImage, Message,
					ResourceId, NeedMark);
		}
	}

}
