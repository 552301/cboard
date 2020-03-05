package org.cboard.services;

import org.cboard.dao.BIPlanTargetDao;
import org.cboard.pojo.BICategoryPlanTarget;
import org.cboard.pojo.BIPlanTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname BIPlanTargetService
 * @Description TODO
 * @Date 2019-10-06 11:11
 * @Created by zhoujinming
 */
@Repository
public class BIPlanTargetService {
    @Autowired
    private BIPlanTargetDao biplanTargetDao;

    public List<BIPlanTarget> getBIPlanTargetList(Map<String, Object> map){
        List<BIPlanTarget> targetList = biplanTargetDao.getBIPlanTargetList(map);
        return targetList;
    }

    public List<BICategoryPlanTarget> getBICategoryPlanTargetList(Map<String, Object> map){
        List<BICategoryPlanTarget> targetList = biplanTargetDao.getBICategoryPlanTargetList(map);
        return targetList;
    }
}
