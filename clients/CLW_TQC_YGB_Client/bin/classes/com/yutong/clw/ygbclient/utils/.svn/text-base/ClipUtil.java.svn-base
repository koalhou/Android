package com.yutong.clw.ygbclient.utils;

import android.annotation.SuppressLint;
import android.content.Context;

@SuppressLint({ "NewApi", "ServiceCast" })
public class ClipUtil {

	public static void saveToClip(Context context, String data) {
		int apilevel = android.os.Build.VERSION.SDK_INT;
		if (apilevel < 11) {
			// api <11
			android.text.ClipboardManager cm = (android.text.ClipboardManager) context
					.getSystemService(Context.CLIPBOARD_SERVICE);
			cm.setText(data);
		} else {
			// api >=11
			// android.content.ClipboardManager clipm =
			// (android.content.ClipboardManager) context
			// .getSystemService(Context.CLIPBOARD_SERVICE);
			// // ClipDescription description=new ClipDescription("", new
			// // String[]{"txt"});
			// // Item item=new Item(data);
			// // ClipData clip =new ClipData(description, item);
			// // clipm.setPrimaryClip(clip);
			// clipm.setPrimaryClip(new android.content.ClipData("",
			// new String[] { "txt" }, new android.content.ClipData.Item(
			// data)));
		}
	}
}
