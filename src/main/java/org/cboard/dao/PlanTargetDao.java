package org.cboard.dao;

import org.cboard.pojo.PlanTarget;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Classname PlanTargetDao
 * @Description TODO
 * @Date 2019-09-20 11:09
 * @Created by zhoujinming
 */
@Repository
public interface PlanTargetDao {

    int save(PlanTarget target);

    int update(PlanTarget target);

    PlanTarget getPlanTarget(int id);

    List<PlanTarget> getPlanTargetList(Map<String, Object> map);

    int delete(int id);
}
