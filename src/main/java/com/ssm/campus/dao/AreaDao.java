package com.ssm.campus.dao;



import com.ssm.campus.entity.Area;

import java.util.List;

/**
 * author fangyr
 * date 2018/10/29 19:17
 */
public interface AreaDao {
    /**
     * 列出区域列表
     * @return areaList
     * */
    List<Area> queryArea();
}
