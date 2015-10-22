package com.yutong.axxc.parents.view.adapter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.beans.StudentInfoBean;
import com.yutong.axxc.parents.view.common.CircularImage;
import com.yutong.axxc.parents.view.common.ResourcesUtils;
import com.yutong.axxc.parents.view.common.YtApplication;

public class BindStudentAdapter extends BaseAdapter {

	private List<StudentInfoBean> students = new ArrayList<StudentInfoBean>();
	private Context context;
	private LayoutInflater inflater;

	public BindStudentAdapter(Context context) {
		super();
		this.context = context;
		inflater = LayoutInflater.from(context);
	}

	public BindStudentAdapter(Context context, List<StudentInfoBean> messages) {
		super();
		this.context = context;
		this.students = messages;

	}

	public void SetDatas(List<StudentInfoBean> messages) {
		this.students = messages;
	}

	@Override
	public int getCount() {
		return students.size();
	}

	@Override
	public Object getItem(int position) {
		return students.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		StudentInfoBean student = students.get(position);
		// System.out.println("position===========" + position);
		try {
			ViewHolder holder;
			if (convertView == null
					|| (holder = (ViewHolder) convertView.getTag()).flag != position) {

				holder = new ViewHolder();
				holder.flag = position;

				convertView = LayoutInflater.from(context).inflate(
						R.layout.bind_student_item, null);
				buildHolder(holder, convertView);
				updateValue(holder, student);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
				updateValue(holder, student);

			}
		} catch (Exception e) {
			Logger.e(getClass(), e.getMessage());
		}
		return convertView;
	}

	private void buildHolder(ViewHolder holder, View convertView) {
		try {
			holder.studentname = (TextView) convertView
					.findViewById(R.id.studentnameTV);

			holder.studentschool = (TextView) convertView
					.findViewById(R.id.studentschoolTV);
			holder.header = (CircularImage) convertView
					.findViewById(R.id.studentheaderIV);
		} catch (Exception e) {
			Logger.e(getClass(), e.getMessage());
		}
	}

	private void updateValue(ViewHolder holder, StudentInfoBean student) {
		try {
			holder.studentname.setText(student.getCld_name());
			holder.studentschool.setText(student.getCld_school());
			if (student.getCld_sex().equals(StudentInfoBean.SEX_FEMAIL)) {
				holder.header.setImageResource(R.drawable.default_gril);
			} else {
				holder.header.setImageResource(R.drawable.default_boy);
			}
			if (StringUtils.isNotBlank(student.getCld_photo()))
				ResourcesUtils.startGetImg(YtApplication.getInstance()
						.getApplicationContext(), holder.header, student
						.getCld_photo());
		} catch (Exception e) {
			Logger.e(getClass(), e.getMessage());
		}
	}

	static class ViewHolder {

		int flag = -1;
		TextView studentschool;
		TextView studentname;
		CircularImage header;

	}

}
