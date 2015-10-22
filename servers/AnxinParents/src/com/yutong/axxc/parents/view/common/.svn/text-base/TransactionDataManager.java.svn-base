package com.yutong.axxc.parents.view.common;

import java.util.HashMap;
import java.util.UUID;

/**
 * Application中数据流转的中转站
 * 
 * @author zhouzc
 * 
 */
public class TransactionDataManager {

	private TransactionDataManager() {
	}

	private static TransactionDataManager instance;

	public static TransactionDataManager getInstance() {
		if (instance == null)
			instance = new TransactionDataManager();
		return instance;
	}

	private HashMap<UUID, TransactionData> mData = new HashMap<UUID, TransactionDataManager.TransactionData>();

	/**
	 * 添加数据
	 * 
	 * @param data
	 * @return
	 */
	public TransactionData putData(TransactionData data) {
		synchronized (mData) {
			if (null == data || data.getKey() == null)
				return null;
			mData.put(data.getKey(), data);
			return mData.get(data.getKey());
		}
	}

	/**
	 * 移除数据
	 * 
	 * @param key
	 * @return
	 */
	public TransactionData deleteData(UUID key) {
		synchronized (mData) {
			if (!mData.keySet().contains(key))
				return null;
			TransactionData temp = mData.get(key);
			mData.remove(key);
			return temp;
		}
	}

	/**
	 * 获取数据
	 * 
	 * @param key
	 * @return
	 */
	public TransactionData getData(UUID key) {
		synchronized (mData) {
			return mData.get(key);
		}
	}

	public void clearData() {
		synchronized (mData) {
			mData.clear();
		}

	}

	/**
	 * 中转数据
	 * 
	 * @author zhouzc
	 * 
	 */
	public static class TransactionData {

		public TransactionData(UUID uuid, Class mclass, Object object) {
			this.key = uuid;
			this.obj = object;
			this.objClass = mclass;
		}

		private UUID key;
		private Class objClass;
		private Object obj;

		public UUID getKey() {
			return key;
		}

		public void setKey(UUID key) {
			this.key = key;
		}

		public Class getObjType() {
			return objClass;
		}

		public void setObjType(Class objType) {
			this.objClass = objType;
		}

		public Object getObj() {
			
			
			return obj;
		}

		public void setObj(Object obj) {
			this.obj = obj;
		}
	}

}
