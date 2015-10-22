package com.yutong.clw.ygbclient.view.pages.setting.ring;

import java.io.File;
import java.util.Locale;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.Gravity;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.view.common.PrefDataUtil;
import com.yutong.clw.ygbclient.view.pages.RemindAccessActivity;
import com.yutong.clw.ygbclient.view.util.DensityUtil;
import com.yutong.clw.ygbclient.view.widget.CommonCheckBox;

/**
 * 铃声设置界面
 * 
 * @author zhouzc
 */
public class RingActivity extends RemindAccessActivity implements
		OnClickListener {
	private RelativeLayout ringRL;

	private TextView tv_curring;

	private CommonCheckBox cb_vibrate;

	private SeekBar sbar_volume;

	private TextView tv_volume;

	private Uri ringUri = null;

	private String ringName = "";

	private PopupWindow pop_volume;

	private int ringVolume = 0;

	private static final int REQUESTCODE_FIND_RING = 0X002;

	private MediaPlayer mediaPlayer;
	
	private AudioManager audioManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting_ring);
		initViews();
		initListener();

	}

	public void initViews() {
		cb_vibrate = (CommonCheckBox) findViewById(R.id.cb_vibrate);

		ringRL = (RelativeLayout) findViewById(R.id.ringRL);
		tv_curring = (TextView) findViewById(R.id.tv_curring);

		tv_volume = new TextView(this);
		tv_volume.setGravity(Gravity.CENTER);
		tv_volume.setTextColor(Color.WHITE);
		tv_volume.setTextSize(DensityUtil.sp2px(this, 11));
		tv_volume.setBackgroundResource(R.drawable.bg_background);
		tv_volume.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		pop_volume = new PopupWindow(tv_volume, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, false);

		sbar_volume = (SeekBar) findViewById(R.id.seekBar_amosr_volume);
		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		int max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		sbar_volume.setMax(max);
		mediaPlayer=new MediaPlayer();
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

	}
	
	private void initListener() {
		/*ringRL.setOnClickListener(this);*/
		cb_vibrate.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// 开关震动
				PrefDataUtil.setRemindVibrate(RingActivity.this, isChecked);
			}
		});
		sbar_volume.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				ringVolume = arg0.getProgress();
				PrefDataUtil.setRemindRingVolume(RingActivity.this, ringVolume);
				pop_volume.dismiss();
				
				stopRemindMedia();
			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				playRemindMedia(null);
			}

			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				if (!arg2)
					return;
				
				changeVolume(arg1);
				
				
				/*当参数为null的时候，播放默认声音*/
				playRemindMedia(null);
				
				String volstr = String.valueOf(arg1);
				tv_volume.setText(volstr);
				pop_volume.getContentView()
						.measure(
								MeasureSpec.makeMeasureSpec(0,
										MeasureSpec.UNSPECIFIED),
								MeasureSpec.makeMeasureSpec(0,
										MeasureSpec.UNSPECIFIED));

				pop_volume.showAsDropDown(
						sbar_volume,
						(sbar_volume.getWidth() - pop_volume.getContentView()
								.getMeasuredWidth()) / 2,
						-sbar_volume.getHeight()
								- DensityUtil.dip2px(getApplicationContext(),
										40));
				
				
			}

		});

		/*sbar_volume.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(playThread != null){
					stopRemindMedia();
					playThread.interrupt();
					playThread  = null;
				}
				
				playThread = new Thread(new Runnable() {
					
					@Override
					public void run() {

						
							playRemindMedia(null);
							try {
								Thread.sleep(1500);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}finally{
								stopRemindMedia();
							}
					}
				});
				
				
				playThread.start();
			}
		});*/
	}
	
	Thread playThread  =null;
	
	@Override
	protected void onResume() {
		super.onResume();

		loadlocalSettings();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUESTCODE_FIND_RING) {
			if (resultCode == RESULT_OK) {
				if (data == null || data.getData() == null) {
					Logger.i(getClass(), "没有获取到媒体音频数据");
					return;
				}

				Uri uri = data.getData();
				// String[] proj = { MediaStore.Images.Media.DATA };
				ContentResolver resolver = getApplicationContext()
						.getContentResolver();
				String[] proj = { MediaStore.Images.Media.DATA };
				Cursor actualimagecursor = resolver.query(uri, proj, null,
						null, null);
				// Cursor actualimagecursor = managedQuery(uri, proj, null,
				// null, null);
				File file = null;
				if (actualimagecursor != null) {
					int actual_image_column_index = actualimagecursor
							.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					actualimagecursor.moveToFirst();
					String img_path = actualimagecursor
							.getString(actual_image_column_index);
					file = new File(img_path);
				} else {
					file = new File(uri.getPath());
				}
				if (file.getName().toLowerCase(Locale.CHINA).endsWith(".mp3")
						|| file.getName().toLowerCase(Locale.CHINA)
								.endsWith(".wav")
						|| file.getName().toLowerCase(Locale.CHINA)
								.endsWith(".wma")
						|| file.getName().toLowerCase(Locale.CHINA)
								.endsWith(".amr")
						|| file.getName().toLowerCase(Locale.CHINA)
								.endsWith(".aac")) {
					ringName = file.getName();
					ringUri = Uri.fromFile(file);
					PrefDataUtil.setRemindRingName(this, ringName);
					PrefDataUtil.setRemindRingUri(this, ringUri);
					// PrefDataUtil.setRemindRingUri(this, uri);
					// PrefDataUtil.setRemindRingName(this, file.getName());

					tv_curring.setText(file.getName());
				} else {
					ToastMessage("请选择以下类型的音频文件\r\n（*.mp3,*.wav,*.wma,*.amr,*.aac）");
				}
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected boolean hasTitle() {
		return true;
	}

	@Override
	protected void loadTitleByPageSetting(Button btn_left, Button btn_right,
			RelativeLayout rl_center, ImageView iv_tri, TextView tv_large,
			TextView tv_small) {
		btn_left.setBackgroundResource(R.drawable.bg_prevbtn);
		btn_right.setBackgroundResource(R.drawable.bg_anxinbtn);
		tv_large.setText("到站提醒响铃方式");
		tv_small.setVisibility(View.GONE);
		iv_tri.setVisibility(View.GONE);
		btn_right.setVisibility(View.GONE);

		btn_left.setOnClickListener(this);
		btn_right.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.btn_it_left:
			finish();
			break;

		case R.id.ringRL:
			// 搜索音频
			Intent intent = new Intent();
			intent.setType("audio/*");
			/* 使用Intent.ACTION_GET_CONTENT这个Action */
			intent.setAction(Intent.ACTION_GET_CONTENT);
			Intent wrapperIntent = Intent.createChooser(intent, null);
			startActivityForResult(wrapperIntent, REQUESTCODE_FIND_RING);

			break;

		default:
			break;
		}

	}

	private void loadlocalSettings() {
		// 加载铃声
		ringUri = PrefDataUtil.getRemindRingUri(this);
		ringName = PrefDataUtil.getRemindRingName(this);
		tv_curring.setText(ringName);

		// 加载音量
		ringVolume = PrefDataUtil.getRemindRingVolume(this);
		if (ringVolume > sbar_volume.getMax()) {
			sbar_volume.setProgress(sbar_volume.getMax());
		} else {
			sbar_volume.setProgress(ringVolume);
		}

		boolean needViberate = PrefDataUtil.getRemindVibrate(this);
		// 加载震动
		cb_vibrate.setChecked(needViberate);

	}

}
