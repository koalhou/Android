package com.yutong.axxc.parents.view.common;

/**
	 * 头像列表子项切换侦听器
	 * 
	 * @author zhouzc
	 * 
	 */
	public  interface UserGridExchangeListener {

		/**
		 * 跳转到头条
		 * 
		 * @param oldindex
		 */
		public  void GotoTop(int oldindex);

		/**
		 * 切换开始
		 * 
		 * @param index1
		 * @param index2
		 */
		public  void ExchangeStart(int index1, int index2);

		/**
		 * 切换完成
		 * 
		 * @param oldIndex 
		 * @param newIndex
		 */
		public  void ExchangeEnd(int oldIndex, int newIndex);
	}

