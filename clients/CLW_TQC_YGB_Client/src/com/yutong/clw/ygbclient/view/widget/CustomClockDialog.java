package com.yutong.clw.ygbclient.view.widget;

/**
 * 
 */

import java.io.IOException;

import com.yutong.clw.ygbclient.R;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author changzhengw
 * 
 */
public class CustomClockDialog extends Dialog {
	
	public CustomClockDialog(Context context, int theme) {
		super(context, theme);
	}

	public CustomClockDialog(Context context) {
		super(context);
	}

	/**
	 * Helper class for creating a custom dialog
	 */
	public static class Builder {
		
		private Context context;
		
		private String clock_remind_text;
		private int clock_remind_textGravity = Gravity.CENTER_HORIZONTAL;
		private int clock_remind_textSize = 15;
		private int clock_remind_textColor = Color.GRAY;
		
		private String station_hint_text;	
		private int station_hint_textGravity = Gravity.LEFT;
		private int station_hint_textSize = 15;
		private int station_hint_textSizeToBig = 18;
		private int station_hint_textColor = Color.WHITE;
		
		private String positiveButtonText;
		private String negativeButtonText;
		
		private View contentView;
		
		private TextView clock_remindTV,station_hintTV;
		private Button positiveButton,negativeButton;
		
		private int positiveBtnTextSize = 20 , negativeBtnTextSize = 20;
		
		private DialogInterface.OnClickListener positiveButtonClickListener,negativeButtonClickListener;
		
		//
		private String leaveFor,busNum,station,distance;
		
		private String  contentStr = "";
		
		//
		private SpannableStringBuilder formatTextStr(String desStr){
			
			if(desStr  == null)
				return null;
			
			String[] arrStr = desStr.trim().split("]");
			
			String[] content = new String[arrStr.length];
			int[] textSize = new int[arrStr.length];
			
			String allStr = "";
			for(int i=0;i<arrStr.length;i++){
				
				String arrFragment = arrStr[i].trim();
				String[] arrStrF  = arrFragment.split("\\[");
				
				allStr += arrStrF[0];
				
				content[i] = arrStrF[0];
				textSize[i]  = Integer.parseInt(arrStrF[1]);
			}
			
			SpannableStringBuilder spanString = new SpannableStringBuilder(allStr);
			
			int len =0;
			for(int j=0;j<content.length;j++){
				
				spanString.setSpan(
						new AbsoluteSizeSpan(textSize[j],true), 
						len, 
						len + content[j].length(), 
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				
				len = len + content[j].length();
			}
			
			
			return spanString;
		}
		
		private SpannableStringBuilder formatText(){
			
			String firstStr = "发往";
			
			String secondStr="";
			if(leaveFor!=null && !leaveFor.equals("") ){
				secondStr = leaveFor;
			}
			
			String a = "的";
			
			String thirdStr = "";
			if(busNum!=null && !busNum.equals("") ){
				thirdStr = busNum;
			}
			
			String b = "班车距离";
			
			String fourthStr = "\"";
			if(station!=null && !station.equals("") ){
				fourthStr = fourthStr + station + "\"";
			}
			
			String c  = "还有";
			
			String fifthStr = "";
			if(distance!=null && !distance.equals("") ){
				fifthStr = distance;
			}
			
			SpannableStringBuilder spanString = new SpannableStringBuilder(firstStr + secondStr + a + thirdStr + b + fourthStr + c + fifthStr);  
			Log.i("wifi", spanString.toString());
			spanString.setSpan(new AbsoluteSizeSpan(station_hint_textSizeToBig,true), 
					firstStr.length(), 
					firstStr.length()+secondStr.length(), 
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); 
			
			int a_len = firstStr.length()+secondStr.length()+a.length();
				
			spanString.setSpan(new AbsoluteSizeSpan(station_hint_textSizeToBig,true), 
					a_len, 
					a_len+thirdStr.length(),
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			
			int b_len = a_len+thirdStr.length()+b.length();
			spanString.setSpan(new AbsoluteSizeSpan(station_hint_textSizeToBig,true), 
					b_len, 
					b_len+fourthStr.length(),
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			
			int c_len = b_len+fourthStr.length()+c.length();
			spanString.setSpan(new AbsoluteSizeSpan(station_hint_textSizeToBig,true), 
					c_len, 
					c_len+fifthStr.length(),
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			
			
			return spanString;
		}
		public Builder(Context context) {
			this.context = context;
		}
		
		
		private void initViews(View layout){
			if(layout!=null){
				clock_remindTV = (TextView) layout.findViewById(R.id.clock_remind);
				station_hintTV = (TextView) layout.findViewById(R.id.station_hint);
				positiveButton = (Button) layout.findViewById(R.id.positiveButton);
				negativeButton = (Button) layout.findViewById(R.id.negativeButton);
			}
		}
		
		/**
		 * Create the custom dialog
		 */
		public CustomClockDialog create() {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			// 初始化对话框
			final CustomClockDialog dialog = new CustomClockDialog(context,R.style.Dialog);
			View layout = inflater.inflate(R.layout.control_dialog_clock_custom, null);
			dialog.addContentView(layout, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			dialog.setCanceledOnTouchOutside(false);
			initViews(layout);
			
			// 设置对话框标题
			if( clock_remind_text!=null && !clock_remind_text.equals("")){
				clock_remindTV.setText(clock_remind_text);
				
				clock_remindTV.setGravity(clock_remind_textGravity);
				clock_remindTV.setTextSize(clock_remind_textSize);
				clock_remindTV.setTextColor(clock_remind_textColor);
			}else{
				clock_remindTV.setVisibility(View.GONE);
			}
			
			// 设置对话框内容
			SpannableStringBuilder sp = formatTextStr(contentStr);
			if (sp != null) {
//				station_hintTV.setText(station_hint_text);
				station_hintTV.append(sp);
				
				station_hintTV.setGravity(station_hint_textGravity);
				station_hintTV.setTextSize(station_hint_textSize);
				station_hintTV.setTextColor(station_hint_textColor);
				
			} else if (contentView != null) {
				((LinearLayout) layout.findViewById(R.id.content))
						.removeAllViews();
				((LinearLayout) layout.findViewById(R.id.content)).addView(
						contentView, new LayoutParams(
								LayoutParams.WRAP_CONTENT,
								LayoutParams.WRAP_CONTENT));
			}
			
			
			
			// 设置左边的按钮
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
			
			
			// 设置右边的按钮
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
		
			
			dialog.setContentView(layout);
			
			return dialog;
		}
		
		/**
		 * Set the Dialog message from String
		 * 
		 * @param title
		 * @return
		 */
		public Builder setClock_remind_text(String clock_remind_text) {
			this.clock_remind_text = clock_remind_text;
			return this;
		}

		/**
		 * Set the Dialog message from resource
		 * 
		 * @param title
		 * @return
		 */
		public Builder setClock_remind_text(int clock_remind_text) {
			this.clock_remind_text = (String) context.getText(clock_remind_text);
			return this;
		}
		
		public Builder setStation_hint_textGravity(int station_hint_textGravity) {
			this.station_hint_textGravity = station_hint_textGravity;
			return this;
		}
		/**
		 * Set the Dialog title from resource
		 * 
		 * @param title
		 * @return
		 */
		public Builder setStation_hint_text(int station_hint_text) {
			this.station_hint_text = (String) context.getText(station_hint_text);
			return this;
		}

		/**
		 * Set the Dialog title from String
		 * 
		 * @param title
		 * @return
		 */
		public Builder setStation_hint_text(String station_hint_text) {
			this.station_hint_text = station_hint_text;
			return this;
		}
		
		public Builder setClock_remind_textGravity(int clock_remind_textGravity) {
			this.clock_remind_textGravity = clock_remind_textGravity;
			return this;
		}
		
		
		/**
		 * @return the titleTextSize
		 */
		public int getClock_remind_textSize() {
			return clock_remind_textSize;
		}

		/**
		 * @param titleTextSize the titleTextSize to set
		 */
		public Builder setClock_remind_textSize(int clock_remind_textSize) {
			this.clock_remind_textSize = clock_remind_textSize;
			return this;
		}

		/**
		 * @return the messageTextSize
		 */
		public int getStation_hint_textSize() {
			return station_hint_textSize;
		}

		/**
		 * @param messageTextSize the messageTextSize to set
		 */
		public Builder setStation_hint_textSize(int station_hint_textSize) {
			this.station_hint_textSize = station_hint_textSize;
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
		public int getclock_remind_textColor() {
			return clock_remind_textColor;
		}

		/**
		 * @param titleTextColor the titleTextColor to set
		 */
		public Builder setClock_remind_textColor(int clock_remind_textColor) {
			this.clock_remind_textColor = clock_remind_textColor;
			return this;
		}

		/**
		 * @return the messageTextColor
		 */
		public int getStation_hint_textColor() {
			return station_hint_textColor;
		}

		/**
		 * @param messageTextColor the messageTextColor to set
		 */
		public Builder setStation_hint_textColor(int station_hint_textColor) {
			this.station_hint_textColor = station_hint_textColor;
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
		 * @return the leaveFor
		 */
		public String getLeaveFor() {
			return leaveFor;
		}


		/**
		 * @param leaveFor the leaveFor to set
		 */
		public Builder setLeaveFor(String leaveFor) {
			this.leaveFor = leaveFor;
			return this;
		}

		public String getBusNum() {
			return busNum;
		}

		public Builder setBusNum(String busNum) {
			this.busNum = busNum;
			return this;
		}

		public String getStation() {
			return station;
		}

		public Builder setStation(String station) {
			this.station = station;
			return this;
		}

		public String getDistance() {
			return distance;
		}
		
		public Builder setDistance(String distance) {
			this.distance = distance;
			return this;
		}
		/**
		 * @return the station_hint_text
		 */
		public String getStation_hint_text() {
			return station_hint_text;
		}
		/**
		 * @return the station_hint_textSizeToBig
		 */
		public int getStation_hint_textSizeToBig() {
			return station_hint_textSizeToBig;
		}
		/**
		 * @param station_hint_textSizeToBig the station_hint_textSizeToBig to set
		 */
		public Builder setStation_hint_textSizeToBig(int station_hint_textSizeToBig) {
			this.station_hint_textSizeToBig = station_hint_textSizeToBig;
			return this;
		}

		/**
		 * @return the contentStr
		 */
		public String getContentStr() {
			return contentStr;
		}

		/**
		 * @param contentStr the contentStr to set
		 */
		public Builder setContentStr(String contentStr) {
			this.contentStr = contentStr;
			return this;
		}
		
	}
}
