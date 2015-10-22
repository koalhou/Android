package com.yutong.clw.ygbclient.common.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.yutong.clw.ygbclient.YtApplication;

public class ResourceUtils {

	public static String getFromAssets(String fileName) {
		try {
			InputStreamReader inputReader = new InputStreamReader(YtApplication
					.getInstance().getResources().getAssets().open(fileName));
			BufferedReader bufReader = new BufferedReader(inputReader);
			String line = "";
			String Result = "";
			while ((line = bufReader.readLine()) != null)
				Result += line;
			return Result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
