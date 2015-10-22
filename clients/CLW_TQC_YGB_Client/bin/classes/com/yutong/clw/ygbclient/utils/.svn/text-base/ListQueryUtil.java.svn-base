/**
 * @公司名称：YUTONG
 * @作者：zhouzc
 * @版本号：1.0
 * @生成日期：2013-12-31 上午10:52:51
 * @功能描述：
 */
package com.yutong.clw.ygbclient.utils;

import java.util.List;

/**
 * @author zhouzc 2013-12-31 上午10:52:51
 *
 */
public class ListQueryUtil {

	public  static <T> T query(List<T> data,IListComparer<T> comp){
		for(T t: data){
			if(comp.check(t))
				return t;
		}
		return null;
	}
	
	
	public static interface IListComparer<T>{
		
		public boolean check(T t);
	}
}

