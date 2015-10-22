/**
 * 
 */
package com.yutong.clw.ygbclient.view.widget;


import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.common.utils.PreferencesUtils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author changzhengw
 * 
 */
public class CustomDialog extends Dialog {
	
	
	public CustomDialog(Context context, int theme) {
		super(context, theme);
	}

	public CustomDialog(Context context) {
		super(context);
	}

	/**
	 * Helper class for creating a custom dialog
	 */
	public static class Builder {
		
		private Context context;
		private String title;
		private String message;
		private String positiveButtonText;
		private String negativeButtonText;
		private View contentView;
		
		private int titleLayoutGravity = Gravity.CENTER_HORIZONTAL;
		private int titleTextSize = 20;
		private int titleTextColor = Color.WHITE;
			
		private int messageLayoutGravity = Gravity.LEFT;
		private int messageTextSize = 15;
		private int messageTextColor = Color.WHITE;
		
		private TextView titleTV,messageTV;
		private Button positiveButton,negativeButton;
		
		private LinearLayout gps_setting_LL;
		private CheckBox gps_setting_checkbox;
		private TextView gps_setting_txt;
		private int isShowGpsSetting  = View.GONE;
		private int positiveBtnTextSize = 20 , negativeBtnTextSize = 20;
		
		
		private DialogInterface.OnClickListener positiveButtonClickListener,
				negativeButtonClickListener;
		
		public Builder(Context context) {
			this.context = context;
		}
		
		
		private void initViews(View layout){
			if(layout!=null){
				titleTV = (TextView) layout.findViewById(R.id.title);
				messageTV = (TextView) layout.findViewById(R.id.message);
				positiveButton = (Button) layout.findViewById(R.id.positiveButton);
				negativeButton = (Button) layout.findViewById(R.id.negativeButton);
				
				gps_setting_LL = (LinearLayout) layout.findViewById(R.id.gps_setting);
				gps_setting_LL.setVisibility(isShowGpsSetting);
				gps_setting_checkbox = (CheckBox) layout.findViewById(R.id.gps_setting_checkbox);
				gps_setting_txt = (TextView) layout.findViewById(R.id.gps_setting_txt);
			}
		}
		/**
		 * Create the custom dialog
		 */
		public CustomDialog create() {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			// 初始化对话框
			final CustomDialog dialog = new CustomDialog(context,R.style.Dialog);
			View layout = inflater.inflate(R.layout.control_dialog_custom, null);
			dialog.addContentView(layout, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			initViews(layout);
			dialog.setCanceledOnTouchOutside(false);
			// 设置对话框标题
			if( title!=null && !title.equals("")){
				titleTV.setText(title);
				titleTV.setGravity(titleLayoutGravity);
				titleTV.setTextSize(titleTextSize);
				titleTV.setTextColor(titleTextColor);
			}else{
				titleTV.setVisibility(View.GONE);
			}
			
			// 设置对话框内容
			if (message != null&& !message.equals("")) {
				messageTV.setText(message);
				messageTV.setGravity(messageLayoutGravity);
				messageTV.setTextSize(messageTextSize);
				messageTV.setTextColor(messageTextColor);
			} else if (contentView != null) {
				((LinearLayout) layout.findViewById(R.id.content))
						.removeAllViews();
				((LinearLayout) layout.findViewById(R.id.content)).addView(
						contentView, new LayoutParams(
								LayoutParams.WRAP_CONTENT,
								LayoutParams.WRAP_CONTENT));
			}
			
			
			// 设置"确定"按钮
			if (positiveButtonText != null) {
				positiveButton.setText(positiveButtonText);
				positiveButton.setTextSize(positiveBtnTextSize);
				
				if (positiveButtonClickListener != null) {
					positiveButton.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {
									positiveButtonClickListener.onClick(dialog,DialogInterface.BUTTON_POSITIVE);
								}
							});
				}
			} else {
				positiveButton.setVisibility(View.GONE);
			}
			
			
			// 设置"取消"按钮
			if (negativeButtonText != null) {
				negativeButton.setText(negativeButtonText);
				negativeButton.setTextSize(negativeBtnTextSize);
				
				if (negativeButtonClickListener != null) {
					negativeButton.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {
									negativeButtonClickListener.onClick(dialog,DialogInterface.BUTTON_NEGATIVE);
								}
							});
				}
			} else {
				negativeButton.setVisibility(View.GONE);
			}
			
			if(gps_setting_checkbox!=null){
				gps_setting_checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

						PreferencesUtils.putBoolean(context, "gps_setting", "isShow", isChecked);
					}
				});
			}
			
			dialog.setContentView(layout);
			
			return dialog;
		}
		
		/**
		 * Set the Dialog message from String
		 * 
		 * @param title
		 * @return
		 */
		public Builder setMessage(String message) {
			this.message = message;
			return this;
		}

		/**
		 * Set the Dialog message from resource
		 * 
		 * @param title
		 * @return
		 */
		public Builder setMessage(int message) {
			this.message = (String) context.getText(message);
			return this;
		}
		
		
		/**
		 * @return the messageLayoutGravity
		 */
		public int getMessageLayoutGravity() {
			return messageLayoutGravity;
		}


		/**
		 * @param messageLayoutGravity the messageLayoutGravity to set
		 */
		public void setMessageLayoutGravity(int messageLayoutGravity) {
			this.messageLayoutGravity = messageLayoutGravity;
		}


		/**
		 * Set the Dialog title from resource
		 * 
		 * @param title
		 * @return
		 */
		public Builder setTitle(int title) {
			this.title = (String) context.getText(title);
			return this;
		}

		/**
		 * Set the Dialog title from String
		 * 
		 * @param title
		 * @return
		 */
		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}
		
		
		/**
		 * @return the titleTextSize
		 */
		public int getTitleTextSize() {
			return titleTextSize;
		}

		/**
		 * @param titleTextSize the titleTextSize to set
		 */
		public Builder setTitleTextSize(int titleTextSize) {
			this.titleTextSize = titleTextSize;
			return this;
		}

		/**
		 * @return the messageTextSize
		 */
		public int getMessageTextSize() {
			return messageTextSize;
		}

		/**
		 * @param messageTextSize the messageTextSize to set
		 */
		public Builder setMessageTextSize(int messageTextSize) {
			this.messageTextSize = messageTextSize;
			return this;
		}

		/**
		 * Set a custom content view for the Dialog. If a message is set, the
		 * contentView is not added to the Dialog...
		 * 
		 * @param v
		 * @return
		 */
		public Builder setContentView(View v) {
			this.contentView = v;
			return this;
		}

		/**
		 * Set the positive button resource and it's listener
		 * 
		 * @param positiveButtonText
		 * @param listener
		 * @return
		 */
		public Builder setPositiveButton(int positiveButtonText,
				DialogInterface.OnClickListener listener) {
			this.positiveButtonText = (String) context
					.getText(positiveButtonText);
			this.positiveButtonClickListener = listener;
			return this;
		}

		/**
		 * Set the positive button text and it's listener
		 * 
		 * @param positiveButtonText
		 * @param listener
		 * @return
		 */
		public Builder setPositiveButton(String positiveButtonText,
				DialogInterface.OnClickListener listener) {
			this.positiveButtonText = positiveButtonText;
			this.positiveButtonClickListener = listener;
			return this;
		}
		
		
		/**
		 * @return the titleTextColor
		 */
		public int getTitleTextColor() {
			return titleTextColor;
		}

		/**
		 * @param titleTextColor the titleTextColor to set
		 */
		public Builder setTitleTextColor(int titleTextColor) {
			this.titleTextColor = titleTextColor;
			return this;
		}

		/**
		 * @return the messageTextColor
		 */
		public int getMessageTextColor() {
			return messageTextColor;
		}

		/**
		 * @param messageTextColor the messageTextColor to set
		 */
		public Builder setMessageTextColor(int messageTextColor) {
			this.messageTextColor = messageTextColor;
			return this;
		}
		
		
		/**
		 * @return the positiveBtnTextSize
		 */
		public int getPositiveBtnTextSize() {
			return positiveBtnTextSize;
		}

		/**
		 * @param positiveBtnTextSize the positiveBtnTextSize to set
		 */
		public void setPositiveBtnTextSize(int positiveBtnTextSize) {
			this.positiveBtnTextSize = positiveBtnTextSize;
		}

		/**
		 * @return the negativeBtnTextSize
		 */
		public int getNegativeBtnTextSize() {
			return negativeBtnTextSize;
		}

		/**
		 * @param negativeBtnTextSize the negativeBtnTextSize to set
		 */
		public void setNegativeBtnTextSize(int negativeBtnTextSize) {
			this.negativeBtnTextSize = negativeBtnTextSize;
		}

		/**
		 * Set the negative button resource and it's listener
		 * 
		 * @param negativeButtonText
		 * @param listener
		 * @return
		 */
		public Builder setNegativeButton(int negativeButtonText,
				DialogInterface.OnClickListener listener) {
			this.negativeButtonText = (String) context
					.getText(negativeButtonText);
			this.negativeButtonClickListener = listener;
			return this;
		}

		/**
		 * Set the negative button text and it's listener
		 * 
		 * @param negativeButtonText
		 * @param listener
		 * @return
		 */
		public Builder setNegativeButton(String negativeButtonText,
				DialogInterface.OnClickListener listener) {
			this.negativeButtonText = negativeButtonText;
			this.negativeButtonClickListener = listener;
			return this;
		}


		/**
		 * @return the titleLayoutGravity
		 */
		public int getTitleLayoutGravity() {
			return titleLayoutGravity;
		}


		/**
		 * @param titleLayoutGravity the titleLayoutGravity to set
		 */
		public Builder setTitleLayoutGravity(int titleLayoutGravity) {
			this.titleLayoutGravity = titleLayoutGravity;
			return this;
		}


		public int getIsShowGpsSetting() {
			return isShowGpsSetting;
		}


		public Builder setIsShowGpsSetting(int isShowGpsSetting) {
			this.isShowGpsSetting = isShowGpsSetting;
			return this;
		}
	}
}
