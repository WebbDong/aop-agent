package com.webbdong.aop.agent.util;

import java.util.List;

/**
 * List 工具类
 * @author Webb Dong
 * @date 2021-02-18 15:20
 */
public class ListUtils {

    /**
     * 有序的往一个 List 添加数据
     * @param list 集合实例
     * @param v 要添加的值
     * @param isDesc 是否降序，true 为降序，false 为升序
     * @param <T> 要添加的值的泛型
     */
    public static <T extends Comparable> void orderedAdd(List<T> list, T v, boolean isDesc) {
        if (list.size() == 0) {
            list.add(v);
        } else {
            int high = list.size() - 1;
            int low = 0;
            int mid;
            while (low <= high) {
                mid = (low + high) / 2;
                if (isDesc == (list.get(mid).compareTo(v) <= 0)) {
                    high = mid - 1;
                }
                if (isDesc == (list.get(mid).compareTo(v) > 0)) {
                    low = mid + 1;
                }
            }
            list.add(high + 1, v);
        }
    }

    private ListUtils() {}

}
