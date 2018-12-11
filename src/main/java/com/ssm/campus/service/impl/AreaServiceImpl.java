package com.ssm.campus.service.impl;

import com.ssm.campus.dao.AreaDao;
import com.ssm.campus.entity.Area;
import com.ssm.campus.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author fangyr
 * date 2018/12/11 14:35
 */

@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaDao areaDao;
    @Override
    public List<Area> getAreaList() {
        return areaDao.queryArea();
    }
}
