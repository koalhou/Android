/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-30 上午11:20:17
 * @功能描述：
 */
package com.yutong.clw.ygbclient.common.utils;

import java.util.ArrayList;
import java.util.List;

import com.yutong.clw.ygbclient.common.enums.other.FilterEnum;

/**
 * @author zhangzhia 2013-10-30 上午11:20:17
 */
public class BusinessUtils
{
    /**
     * 将list条件转换为filter枚举
     * 
     * @author zhangzhia 2013-10-30 上午11:33:55
     * @param filters
     * @return
     * @throws Exception
     */
    public static FilterEnum convertToFilter(List<String> filters) throws Exception
    {

        if (filters == null || filters.size() == 0)
        {
            throw new Exception("filters未选择条件");
        }

        String filterValue = "";
        for (String filter : filters)
        {
            filterValue += filter + "#";
        }
        filterValue = filterValue.substring(0, filterValue.length() - 1);
        for (int i = 0; i < FilterEnum.values().length; i++)
        {
            FilterEnum filterEnum = FilterEnum.values()[i];
            if (filterEnum.getName().equals(filterValue))
            {
                return filterEnum;
            }
        }
        return null;
    }

    /**
     * 将filter枚举转换为list条件
     * 
     * @author zhangzhia 2013-10-30 上午11:39:50
     * @param filter
     * @return
     */
    public static List<String> convertToList(FilterEnum filter)
    {
        List<String> filters = new ArrayList<String>();
        String filterValue = "";

        filterValue = filter.getName();
        String[] array = filterValue.split("#");

        for (String str : array)
        {
            filters.add(str);
        }

        return filters;
    }
}
