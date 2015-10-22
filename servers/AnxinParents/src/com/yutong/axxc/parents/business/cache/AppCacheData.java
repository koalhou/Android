package com.yutong.axxc.parents.business.cache;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.yutong.axxc.parents.common.beans.LineInfoBean;
import com.yutong.axxc.parents.common.beans.StationInfoBean;
import com.yutong.axxc.parents.common.beans.StationRemindInfoBean;
import com.yutong.axxc.parents.common.beans.StudentInfoBean;

public class AppCacheData
{
    private static Map<String, List<StudentInfoBean>> studentInfoMap = new HashMap<String, List<StudentInfoBean>>();

    private static Map<String, LineInfoBean> lineInfoMap = new HashMap<String, LineInfoBean>();

    private static Map<String, List<StationInfoBean>> stationInfoMap = new HashMap<String, List<StationInfoBean>>();

    private static Map<String, List<StationRemindInfoBean>> stationRemindInfoMap = new HashMap<String, List<StationRemindInfoBean>>();

    // public static List<StudentInfoBean> getStudentInfoBeans()
    // {
    // List<StudentInfoBean> list = new ArrayList<StudentInfoBean>();
    // Iterator<Map.Entry<String, StudentInfoBean>> it =
    // studentInfoMap.entrySet().iterator();
    // while (it.hasNext())
    // {
    // Map.Entry<String, StudentInfoBean> entry = it.next();
    // list.add(entry.getValue());
    // }
    //
    // return list;
    // }

    public static void putStudentInfo(String usr_id, StudentInfoBean studentInfoBean)
    {
        List<StudentInfoBean> list = getStudentInfos(usr_id);

        Iterator<StudentInfoBean> sListIterator = list.iterator();
        while (sListIterator.hasNext())
        {
            StudentInfoBean item = sListIterator.next();
            if (item.getCld_id().equals(studentInfoBean.getCld_id()))
            {
                sListIterator.remove();

                break;
            }
        }

        list.add(studentInfoBean);
        putStudentInfos(usr_id, list);
    }

    public static void putStudentInfos(String usr_id, List<StudentInfoBean> studentInfoBeans)
    {
        studentInfoMap.put(usr_id, studentInfoBeans);
    }

    @SuppressWarnings("unchecked")
    public static List<StudentInfoBean> getStudentInfos(String usr_id)
    {
        if (studentInfoMap.containsKey(usr_id))
        {
            List<StudentInfoBean> students = studentInfoMap.get(usr_id);
            Collections.sort(students, new ComparatorStudent());

            return students;
        }
        else
        {
            return null;
        }
    }

    public static StudentInfoBean getStudentInfo(String usr_id, String cld_id)
    {
        List<StudentInfoBean> list = getStudentInfos(usr_id);
        if (list != null && list.size() > 0)
        {
            for (StudentInfoBean item : list)
            {
                if (item.getCld_id().equals(cld_id))
                {
                    return item;
                }
            }

            return null;
        }
        else
        {
            return null;
        }
    }

    public static void putLineInfo(String cld_id, String line_type, LineInfoBean lineInfoBean)
    {
        lineInfoMap.put(cld_id + line_type, lineInfoBean);
    }

    public static LineInfoBean getLineInfo(String cld_id, String line_type)
    {
        if (lineInfoMap.containsKey(cld_id + line_type))
        {
            return lineInfoMap.get(cld_id + line_type);
        }
        else
        {
            return null;
        }
    }

    public static void putStationInfos(String cld_id, String line_type, List<StationInfoBean> stationInfoBeans)
    {
        stationInfoMap.put(cld_id + line_type, stationInfoBeans);
    }

    public static List<StationInfoBean> getStationInfos(String cld_id, String line_type)
    {
        if (stationInfoMap.containsKey(cld_id + line_type))
        {
            return stationInfoMap.get(cld_id + line_type);
        }
        else
        {
            return null;
        }
    }

    public static void putStationRemindInfos(String cld_id, String line_type, List<StationRemindInfoBean> stationRemindInfoBeans)
    {
        stationRemindInfoMap.put(cld_id + line_type, stationRemindInfoBeans);
    }

    public static List<StationRemindInfoBean> getStationRemindInfos(String cld_id, String line_type)
    {
        if (stationRemindInfoMap.containsKey(cld_id + line_type))
        {
            return stationRemindInfoMap.get(cld_id + line_type);
        }
        else
        {
            return null;
        }
    }

    private static class ComparatorStudent implements Comparator
    {

        public int compare(Object arg0, Object arg1)
        {
            StudentInfoBean user0 = (StudentInfoBean) arg0;
            StudentInfoBean user1 = (StudentInfoBean) arg1;

            // 首先比较年龄，如果年龄相同，则比较名字

            int flag = user0.getCld_id().compareTo(user1.getCld_id());
            return flag;
        }

    }
}

