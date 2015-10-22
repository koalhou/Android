package com.yutong.axxc.parents.view.common;

import java.util.HashMap;
import java.util.Map;

import android.graphics.Color;

import com.yutong.axxc.parents.R;

/**
 * 小孩个性色彩配置对应表
 * 
 * @author zhouzc
 * 
 */
public class ChildCustomConfigs {

	private ChildCustomConfigs() {
		loadThemes();

	}

	private static ChildCustomConfigs instance = null;

	public static ChildCustomConfigs getInstance() {
		if (instance == null)
			instance = new ChildCustomConfigs();
		return instance;
	}

	private void loadThemes() {
		themes.put("0", new ChildCustomTheme("0", "宁静绿",
				R.drawable.childbg_green, R.drawable.childtheme_bg_green,
				R.drawable.color_green, Color.parseColor("#1b986d"),
				R.drawable.home_green, R.drawable.school_green,
				R.drawable.home_green_editing,
				R.drawable.map_carbg_green));
		themes.put("1", new ChildCustomTheme("1", "可爱红",
				R.drawable.childbg_pink, R.drawable.childtheme_bg_pink,
				R.drawable.color_pink, Color.parseColor("#ff4567"),
				R.drawable.home_pink, R.drawable.school_pink,
				R.drawable.home_pink_editing,
				R.drawable.map_carbg_pink));
		themes.put("2", new ChildCustomTheme("2", "活泼红",
				R.drawable.childbg_red, R.drawable.childtheme_bg_red,
				R.drawable.color_red, Color.parseColor("#d64747"),
				R.drawable.home_red, R.drawable.school_red,
				R.drawable.home_red_editing,
				R.drawable.map_carbg_red));
		themes.put("3", new ChildCustomTheme("3", "优雅紫",
				R.drawable.childbg_purple, R.drawable.childtheme_bg_purple,
				R.drawable.color_purple, Color.parseColor("#ad2bdd"),
				R.drawable.home_purple, R.drawable.school_purple,
				R.drawable.home_purple_editing,
				R.drawable.map_carbg_purple));
		themes.put("4", new ChildCustomTheme("4", "透彻蓝",
				R.drawable.childbg_blue, R.drawable.childtheme_bg_blue,
				R.drawable.color_blue, Color.parseColor("#417edc"),
				R.drawable.home_blue, R.drawable.school_blue,
				R.drawable.home_blue_editing,
				R.drawable.map_carbg_blue));
	}

	private Map<String, ChildCustomTheme> themes = new HashMap<String, ChildCustomConfigs.ChildCustomTheme>();

	public ChildCustomTheme getChildCustomThemeByKey(String key) {
		ChildCustomTheme theme = themes.get(key);
		if (theme == null) {
			return new ChildCustomTheme("0", "宁静绿", R.drawable.childbg_green,
					R.drawable.childtheme_bg_green, R.drawable.color_green,
					Color.parseColor("#1b986d"), R.drawable.home_green,
					R.drawable.school_green, R.drawable.home_green_editing,R.drawable.map_carbg_green);
		}
		return themes.get(key);
	}

	public static class ChildCustomTheme {

		public ChildCustomTheme(String key, String name, int mbgresid,
				int ibgresid, int iconresId, int color, int homeresId,
				int schoolresId, int editResId, int mapCarBgResId) {
			this.key = key;
			this.name = name;
			this.mainBackgroundResId = mbgresid;
			this.infoBackgroundResId = ibgresid;
			this.iconResId = iconresId;
			this.colorvalue = color;
			this.homeResId = homeresId;
			this.schoolResId = schoolresId;
			this.editResId = editResId;

			this.mapCarBgResId = mapCarBgResId;

		}

		private String key;
		private String name;
		private int mainBackgroundResId;
		private int infoBackgroundResId;
		private int iconResId;
		private int colorvalue;
		private int homeResId;
		private int schoolResId;
		// add by lizyi
		private int editResId;

		/**
		 * 地图，小孩背景资源
		 */
		private int mapCarBgResId;

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getMainBackgroundResId() {
			return mainBackgroundResId;
		}

		public void setMainBackgroundResId(int mainBackgroundResId) {
			this.mainBackgroundResId = mainBackgroundResId;
		}

		public int getInfoBackgroundResId() {
			return infoBackgroundResId;
		}

		public void setInfoBackgroundResId(int infoBackgroundResId) {
			this.infoBackgroundResId = infoBackgroundResId;
		}

		public int getIconResId() {
			return iconResId;
		}

		public void setIconResId(int iconResId) {
			this.iconResId = iconResId;
		}

		public int getColorvalue() {
			return colorvalue;
		}

		public void setColorvalue(int colorvalue) {
			this.colorvalue = colorvalue;
		}

		/**
		 * @return the homeResId
		 */
		public int getHomeResId() {
			return homeResId;
		}

		/**
		 * @param homeResId
		 *            the homeResId to set
		 */
		public void setHomeResId(int homeResId) {
			this.homeResId = homeResId;
		}

		/**
		 * @return the schoolResId
		 */
		public int getSchoolResId() {
			return schoolResId;
		}

		/**
		 * @param schoolResId
		 *            the schoolResId to set
		 */
		public void setSchoolResId(int schoolResId) {
			this.schoolResId = schoolResId;
		}

		// add by lizyi
		/**
		 * @return the schoolResId
		 */
		public int getEditResId() {
			return editResId;
		}

		/**
		 * @param schoolResId
		 *            the schoolResId to set
		 */
		public void setEditResId(int editResId) {
			this.editResId = editResId;
		}

		/**
		 * @return the mapCarBgResId
		 */
		public int getMapCarBgResId() {
			return mapCarBgResId;
		}

		/**
		 * @param mapCarBgResId
		 *            the mapCarBgResId to set
		 */
		public void setMapCarBgResId(int mapCarBgResId) {
			this.mapCarBgResId = mapCarBgResId;
		}
	}
}
