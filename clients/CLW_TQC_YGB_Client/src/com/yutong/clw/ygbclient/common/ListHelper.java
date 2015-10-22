/**
 * @公司名称：YUTONG
 * @作者：zhouzc
 * @版本号：1.0
 * @生成日期：2013-11-29 上午10:56:03
 * @功能描述：
 */
package com.yutong.clw.ygbclient.common;

import java.util.LinkedList;
import java.util.List;

/**
 * @author zhouzc 2013-11-29 上午10:56:03
 *
 */
public abstract class ListHelper<T, T1> {

	public List<T1> getNewList(List<T> old) {
		if (old == null)
			return null;
		List<T1> newdata = new LinkedList<T1>();
		for (T d : old) {
			newdata.add(getItem(d));
		}
		return newdata;

	}

	protected abstract T1 getItem(T data);

}

