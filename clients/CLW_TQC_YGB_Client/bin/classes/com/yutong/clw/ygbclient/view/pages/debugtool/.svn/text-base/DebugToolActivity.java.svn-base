/**
 * @公司名称：YUTONG
 * @作者：zhouzc
 * @版本号：1.0
 * @生成日期：2013-12-2 上午9:18:03
 * @功能描述：
 */
package com.yutong.clw.ygbclient.view.pages.debugtool;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.utils.SystemInfoUtil;
import com.yutong.clw.ygbclient.view.bean.command.CommandExecuteResult;
import com.yutong.clw.ygbclient.view.bean.command.CommandParseException;
import com.yutong.clw.ygbclient.view.common.command.CommandExecutor;
import com.yutong.clw.ygbclient.view.pages.BaseActivity;
import com.yutong.clw.ygbclient.view.widget.querylist.QueryResultItem;
import com.yutong.clw.ygbclient.view.widget.querylist.QueryResultListAdapter;

/**
 * @author zhouzc 2013-12-2 上午9:18:03
 * 
 */

public class DebugToolActivity extends BaseActivity implements OnClickListener {

	private ListView lv_results;
	private EditText tv_command;
	private SeekBar sbar_position;
	private List<QueryResultItem> resultdata;
	private QueryResultListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_debugtool);

		initViews();
		// prepareTestData();
	}

	private void initViews() {
		resultdata = new ArrayList<QueryResultItem>();
		adapter = new QueryResultListAdapter(this, resultdata);
		lv_results = (ListView) findViewById(R.id.lv_ad_content);
		lv_results.setAdapter(adapter);

		tv_command = (EditText) findViewById(R.id.et_ad_querytxt);
		tv_command.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					if (lv_results != null) {
						if (lv_results.getCount() > 0)
							lv_results.setSelection(lv_results.getCount() - 1);
					}
//					int index = etv.getSelectionStart();
//					int total = etv.getText().toString().length();
//					adaptToSeekBar(total, index);
				}
			}
		});
		sbar_position = (SeekBar) findViewById(R.id.sbar_ad_location);
		bindEditTextAndSeekBar(tv_command, sbar_position);
		findViewById(R.id.btn_ad_query).setOnClickListener(this);
	}

	private void adaptToSeekBar(int total, int index) {
		sbar_position.setMax(total);
		sbar_position.setProgress(index);
	}

	private void prepareTestData() {

		QueryResultItem item = new QueryResultItem();
		item.setBackground(null);
		item.setTitle("查询版本号");
		item.setContent(SystemInfoUtil.getVersionName());
		item.setTag("");
		resultdata.add(item);
		item = new QueryResultItem();
		item.setBackground(null);
		item.setTitle("查询当前用户");
		item.setContent("UserId:90077777\r\nToken:10762");
		item.setTag("");
		resultdata.add(item);
		item = new QueryResultItem();
		item.setBackground(null);
		item.setTitle("查询当前路线");
		item.setContent("空");
		item.setTag("");
		resultdata.add(item);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_ad_query:
			doquery();
			break;

		default:
			break;
		}

	}

	private void bindEditTextAndSeekBar(final EditText etv, SeekBar seekbar) {
		etv.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				return false;
			}
		});
		etv.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				int index = etv.getSelectionStart();
				int total = etv.getText().toString().length();
				adaptToSeekBar(total, index);

			}
		});
		tv_command.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int index = tv_command.getSelectionStart();
				int total = tv_command.getText().toString().length();
				adaptToSeekBar(total, index);
			}
		});

		seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				setTextCursorPosition(tv_command, arg1);
			}
		});
	}

	private void setTextCursorPosition(EditText etv, int position) {
		CharSequence text = etv.getText();
		if (text instanceof Spannable) {
			Spannable spanText = (Spannable) text;
			if (position >= 0 && position < text.length()) {
				Selection.setSelection(spanText, position);
			} else
				Selection.setSelection(spanText, text.length());
		}

	}

	private void doquery() {
		String commandString = tv_command.getText().toString().trim();
		try {
			CommandExecuteResult result = CommandExecutor
					.execute(commandString);
			QueryResultItem item = new QueryResultItem();
			item.setBackground(null);
			item.setTitle(result.getCommand().getName());
			item.setContent(result.getContent());
			item.setTag("");
			resultdata.add(item);
			adapter.notifyDataSetChanged();
			lv_results.setSelection(lv_results.getCount() - 1);
		} catch (CommandParseException e) {
			e.printStackTrace();
			QueryResultItem item = new QueryResultItem();
			item.setBackground(null);
			item.setTitle("消息");
			item.setContent(e.getMessage());
			item.setTag("");
			resultdata.add(item);
			adapter.notifyDataSetChanged();
			lv_results.setSelection(lv_results.getCount() - 1);
		}

	}

}
