package org.cboard.dao;

import org.cboard.pojo.BICategoryPlanTarget;
import org.cboard.pojo.BIPlanTarget;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Classname BIPlanTargetDao
 * @Description TODO
 * @Date 2019-10-06 11:22
 * @Created by zhoujinming
 */
@Repository
public interface BIPlanTargetDao {

    List<BIPlanTarget> getBIPlanTargetList(Map<String, Object> map);

    List<BICategoryPlanTarget> getBICategoryPlanTargetList(Map<String, Object> map);
}
