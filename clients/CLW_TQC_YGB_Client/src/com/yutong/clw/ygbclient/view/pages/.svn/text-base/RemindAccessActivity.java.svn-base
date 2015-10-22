package com.yutong.clw.ygbclient.view.pages;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Gravity;

import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.enums.StatusRange;
import com.yutong.clw.ygbclient.common.enums.remind.RemindRange;
import com.yutong.clw.ygbclient.common.enums.remind.RemindType;
import com.yutong.clw.ygbclient.common.utils.PreferencesUtils;
import com.yutong.clw.ygbclient.dao.other.AdvicePushInfoDao;
import com.yutong.clw.ygbclient.view.bean.push.AlarmPushBean;
import com.yutong.clw.ygbclient.view.bean.push.FeedBackPushBean;
import com.yutong.clw.ygbclient.view.bean.push.PushBean;
import com.yutong.clw.ygbclient.view.bizAccess.BizDataTypeEnum;
import com.yutong.clw.ygbclient.view.bizAccess.BizResultProcess;
import com.yutong.clw.ygbclient.view.bizAccess.common.BizRemind;
import com.yutong.clw.ygbclient.view.common.ActivityCommConstant;
import com.yutong.clw.ygbclient.view.common.PrefDataUtil;
import com.yutong.clw.ygbclient.view.common.PushMessageRouter.MessageRouteDirection;
import com.yutong.clw.ygbclient.view.pages.main.inFactory.InFactoryActivity;
import com.yutong.clw.ygbclient.view.pages.main.outOfFactory.OutOfFactoryActivity;
import com.yutong.clw.ygbclient.view.widget.CustomClockDialog;

/**
 * 需要接收提醒信息的父页面
 */
public abstract class RemindAccessActivity extends PushMessageAccessActivity {

	private AdvicePushInfoDao mAdvicePushInfoDao;
	CustomClockDialog remindDialog = null;
	BizRemind bizremind;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		listenAlarm = true;
		listenAnnounce = false;
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		
		super.onResume();
		if (remindDialog != null && remindDialog.isShowing()){
			YtApplication.getInstance().getPushMessageRouter().setAlarmRouteDriection(MessageRouteDirection.Notification);
		}else{
			YtApplication.getInstance().getPushMessageRouter().setAlarmRouteDriection(MessageRouteDirection.Page);
		}
	}

	@Override
	protected void onPause() {
		
		YtApplication.getInstance().getPushMessageRouter().setAlarmRouteDriection(MessageRouteDirection.Notification);
		if (bizremind != null){
			bizremind.stop();
		}
		super.onPause();
	}

	@Override
	protected void onStop() {
		
		if (bizremind != null){
			bizremind.stop();
		}
		super.onStop();
	}

	@Override
	protected void destoryRunningWork() {
		super.destoryRunningWork();
		if (bizremind != null) {
			bizremind.destory();
		}
	}

	@Override
	protected void onReceivedPushMessage(PushBean msg) {
		
		super.onReceivedPushMessage(msg);
		whenReceiveRemind(msg);
	}
	
	
	/**
	 * 解析推送数据
	 * @param pushmsg 推送消息抽象类
	 * @return
	 * */
	private void whenReceiveRemind(PushBean pushmsg) {
		
		Logger.i(getClass(), "准备处理提醒信息推送消息");
		
		if (isActive()) {
			if (pushmsg instanceof AlarmPushBean) {
				showRemindDialog(pushmsg);
				playRemindMedia(pushmsg);
				startViberate();
			}
		}
		
		//处理公告推送，不管程序前后台运行都要进行接收处理
		
		if (pushmsg instanceof FeedBackPushBean) {
			
			FeedBackPushBean feedBackPushBean = (FeedBackPushBean) pushmsg;
			/*feedBackPushBean.msgID = System.currentTimeMillis()+"";*/
			
			String userCode = PreferencesUtils.getString(RemindAccessActivity.this,  ActivityCommConstant.prefName,  ActivityCommConstant.EMP_NUMBER);
			feedBackPushBean.userCode =userCode;
			feedBackPushBean.readFlag = ActivityCommConstant.ADVICE_UN_READ;
			feedBackPushBean.replyFlag = ActivityCommConstant.ADVICE_REPLY;
			
			mAdvicePushInfoDao = new AdvicePushInfoDao(RemindAccessActivity.this);
			mAdvicePushInfoDao.updateAdviceReplyInfo(feedBackPushBean);
		}
	}

	private BizRemind getBizRemind() {
		
		if (bizremind == null)
			bizremind = new BizRemind(this);
		return bizremind;
	}

	protected void showRemindDialog(final PushBean pushmsg) {
		Logger.i(getClass(), "准备显示提醒对话框");
		if (pushmsg == null)
			return;
		YtApplication.getInstance().getPushMessageRouter()
				.setAlarmRouteDriection(MessageRouteDirection.Notification);
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				CustomClockDialog.Builder builder = new CustomClockDialog.Builder(
						RemindAccessActivity.this);
				String title = "";
				String content = "";
				switch (pushmsg.getType()) {
				case News:
					return;
				case FactoryInside:
					title = "厂内通勤到站提醒";
					final AlarmPushBean alb = (AlarmPushBean) pushmsg;
					content = getRemindContent(alb);
					builder.setClock_remind_text(title)
							.setClock_remind_textGravity(
									Gravity.CENTER_HORIZONTAL)
							.setStation_hint_textSizeToBig(20)
							.setContentStr(content)
							.setPositiveButton("结束提醒", new OnClickListener() {
								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {
									arg0.dismiss();
									setNoLongerRemindToday(alb.getRemindId(),
											alb.getStationId());

								}
							})
							.setNegativeButton("下辆再提醒", new OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
								}
							});
					break;
				case FactoryOuter:
					title = "厂外通勤到站提醒";
					final AlarmPushBean ab = (AlarmPushBean) pushmsg;
					content = getRemindContent(ab);
					builder.setClock_remind_text(title)
							.setClock_remind_textGravity(
									Gravity.CENTER_HORIZONTAL)
							.setStation_hint_textSizeToBig(20)
							.setContentStr(content);
					if (ab.getRemindRange() == RemindRange.OnlyStation) {
						builder.setPositiveButton("结束提醒",
								new OnClickListener() {
									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										arg0.dismiss();
										// TODO
										setNoLongerRemindToday(
												ab.getRemindId(),
												ab.getStationId());
									}
								}).setNegativeButton("下辆再提醒",
								new OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.dismiss();
									}
								});
					} else {
						builder.setPositiveButton("关闭", new OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								arg0.dismiss();
							}
						});
					}
					break;
				default:
					return;
				}

				remindDialog = builder.create();
				remindDialog.setCancelable(false);
				remindDialog.setOnDismissListener(new OnDismissListener() {
					@Override
					public void onDismiss(DialogInterface arg0) {
						stopRemindMedia();
						stopViberate();
						YtApplication
								.getInstance()
								.getPushMessageRouter()
								.setAlarmRouteDriection(
										MessageRouteDirection.Page);
					}
				});
				remindDialog.show();
			}
		});

	}

	

	protected void startViberate() {
		if (!PrefDataUtil.getRemindVibrate(this))
			return;
		Vibrator v = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
		v.vibrate(new long[] { 1000, 10, 100, 1000 }, -1);
	}

	protected void stopViberate() {
		if (!PrefDataUtil.getRemindVibrate(this))
			return;
		Vibrator v = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
		v.cancel();
	}

	private void setNoLongerRemindToday(String remindid, String stationid) {
		// 设置下次不再提醒
		getBizRemind().nolongerRemindToday(remindid, stationid,
				new BizResultProcess<Boolean>() {
					@Override
					public void onBizExecuteError(Exception exception,
							Error error) {
						ToastMessage("设置不再提醒失败");
					}

					@Override
					public void onBizExecuteEnd(BizDataTypeEnum datatype,
							Boolean t) {
						ToastMessage("设置不再提醒成功");
						if (RemindAccessActivity.this instanceof InFactoryActivity) {
							// 如果当前是厂内，则刷新站点信息
							((InFactoryActivity) RemindAccessActivity.this)
									.reloadLineStations(true);
						} else if (RemindAccessActivity.this instanceof OutOfFactoryActivity) {
							// 如果当前是厂外，则刷新站点提醒信息
							((OutOfFactoryActivity) RemindAccessActivity.this)
									.refreshRealVehicleInfo();
						}
					}
				});
	}

	private String getRemindContent(AlarmPushBean bean) {
		String content = "";
		switch (bean.getType()) {
		case FactoryInside:
			String ContentModuleIn = "{VehicleNumber}号[18]班车已到达[15]“{StationName}”站[18]";
			content = ContentModuleIn;
			content = content.replace("{VehicleNumber}",
					bean.getVehicleNumber());
			content = content.replace("{StationName}", bean.getStationName());
			break;
		case FactoryOuter:
			String ContentModuleOut;
			if (bean.getStatusRange() == StatusRange.NightWork) {
				if (bean.getRemindType() == RemindType.Date) {
					ContentModuleOut = "线路[15]{LineName}[18]上[15]{VehicleNumber}号[18]班车预计[15]{RemindValue}{RemindType}[18]后到达[15]“{StationName}站”[18]";
				} else if (bean.getRemindType() == RemindType.Distance) {
					ContentModuleOut = "线路[15]{LineName}[18]上[15]{VehicleNumber}号[18]班车距离[15]“{StationName}站”[18]还有[15]{RemindValue}{RemindType}[18]";
				} else {
					ContentModuleOut = "线路[15]{LineName}[18]上[15]{VehicleNumber}号[18]班车距离[15]“{StationName}站”[18]还有[15]{RemindValue}{RemindType}[18]";
				}
			} else {
				if (bean.getRemindType() == RemindType.Date) {
					ContentModuleOut = "发往[15]{AreaType}[18]的[15]{VehicleNumber}号[18]班车预计[15]{RemindValue}{RemindType}[18]后到达[15]“{StationName}站”[18]";
				} else if (bean.getRemindType() == RemindType.Distance) {
					ContentModuleOut = "发往[15]{AreaType}[18]的[15]{VehicleNumber}号[18]班车距离[15]“{StationName}站”[18]还有[15]{RemindValue}{RemindType}[18]";
				} else {
					ContentModuleOut = "发往[15]{AreaType}[18]的[15]{VehicleNumber}号[18]班车距离[15]“{StationName}站”[18]还有[15]{RemindValue}{RemindType}[18]";
				}
			}
			content = ContentModuleOut;
			content = content.replace("{LineName}",
					bean.getLinename());
			content = content.replace("{VehicleNumber}",
					bean.getVehicleNumber());
			content = content.replace("{AreaType}", bean.getAreaType()
					.getName());
			content = content.replace("{StationName}", bean.getStationName());
			content = content.replace("{RemindType}",
					bean.getRemindType() == RemindType.Date ? "分钟" : "米");
			content = content.replace("{RemindValue}",
					String.valueOf(bean.getRemindValue()));
			break;
		default:
			break;
		}
		return content;
	}

	protected void playRemindMedia(PushBean pushmsg) {
		Logger.i(getClass(), "开始播放提醒音乐");
		Uri mediauri = PrefDataUtil.getRemindRingUri(this);
		
		/*if (mediauri == null) {
			mediauri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
					+ "://" + YtApplication.getInstance().getPackageName()
					+ "/raw/alarm");
		}*/
		if (mediauri == null) {
			if(pushmsg!=null){
				AlarmPushBean alarmPushBean = null;
				if(pushmsg instanceof AlarmPushBean){
					alarmPushBean = (AlarmPushBean) pushmsg;
					String ruleId = alarmPushBean.getRuleId();
					
					//厂内
					if(ruleId.equals("02")){
						
						mediauri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
								+ "://" + YtApplication.getInstance().getPackageName()
								+ "/raw/station_arrived");
						
					}else if( ruleId.equals("03") ){//厂外
						StatusRange statusRange = alarmPushBean.getStatusRange();
						
						if(statusRange.value() == 0){//早班
							mediauri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
									+ "://" + YtApplication.getInstance().getPackageName()
									+ "/raw/station_arrived");
						}else if(statusRange.value() == 1){//晚班
							mediauri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
									+ "://" + YtApplication.getInstance().getPackageName()
									+ "/raw/station_arrived");
						}
					}
				}
			}
		}
		
		
		YtApplication.getInstance().getMediaManager()
				.PlayMedia(mediauri, PrefDataUtil.getRemindRingVolume(this));

	}

	protected void stopRemindMedia() {
		Logger.i(getClass(), "停止播放提醒音乐");
		YtApplication.getInstance().getMediaManager().StopPlay();
	}
	
	protected void changeVolume(int volume) {
		YtApplication.getInstance().getMediaManager().changeVolume(volume);
	}
}
