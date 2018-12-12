package com.ssm.campus.web.superadmin;

import com.ssm.campus.entity.Area;
import com.ssm.campus.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author fangyr
 * date 2018/12/11 17:02
 */

@Controller
@RequestMapping("/superadmin")
public class AreaController {
   Logger logger = LoggerFactory.getLogger(AreaController.class);
    @Autowired
    private AreaService areaService;
    @RequestMapping(value = "/listarea",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> listArea(){
        //日志开始信息
        logger.info("=== start ===");
        //获取开始时间
        Long startTime = System.currentTimeMillis();
        Map<String,Object> modelMap = new HashMap<String, Object>();
        List<Area> list = new ArrayList<Area>();
        try {
            list = areaService.getAreaList();
            modelMap.put("rows",list);
            modelMap.put("total",list.size());
        }catch (Exception e){
            e.printStackTrace();
            modelMap.put("success",false);
            modelMap.put("errMsg",e.toString());
        }
        logger.error("test error!");
        Long endTime = System.currentTimeMillis();
        logger.debug("costTime[{}ms]",endTime - startTime);//花费时间
        logger.info("=== end ===");
        return modelMap;
    }
}
