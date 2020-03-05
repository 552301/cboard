package org.cboard.services;

import org.cboard.dao.PlanTargetDao;
import org.cboard.pojo.PlanTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname PlanTargetService
 * @Description TODO
 * @Date 2019-09-20 11:11
 * @Created by zhoujinming
 */
@Repository
public class PlanTargetService {
    @Autowired
    private PlanTargetDao planTargetDao;

    public ServiceStatus save(PlanTarget target) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("season", target.getSeason());
        map.put("category", target.getCategory());
        map.put("styleBill", target.getStyleBill());
        map.put("planMonth",target.getPlanMonth());
        map.put("planTarget", target.getPlanNums());
        map.put("createDate", new Date());
        if(getPlanTargetList(map).size() == 0) {
            if(planTargetDao.save(target) > 0)
                return new ServiceStatus(ServiceStatus.Status.Success, "保存成功");
        }else {
            return new ServiceStatus(ServiceStatus.Status.Fail, "保存失败，记录已存在");
        }


        return new ServiceStatus(ServiceStatus.Status.Fail,"保存失败");
    }

    public ServiceStatus update(PlanTarget target) {
        int result = planTargetDao.update(target);
        if(result > 0)
            return new ServiceStatus(ServiceStatus.Status.Success, "保存成功");

        return new ServiceStatus(ServiceStatus.Status.Fail,"保存失败");
    }

    public ServiceStatus delete(int id) {
        if(planTargetDao.delete(id) > 0)
            return new ServiceStatus(ServiceStatus.Status.Success, "删除成功");

        return new ServiceStatus(ServiceStatus.Status.Fail,"删除失败");
    }

    public List<PlanTarget> getPlanTargetList(Map<String, Object> map){
        List<PlanTarget> targetList = planTargetDao.getPlanTargetList(map);
        return targetList;
    }

    public PlanTarget getPlanTarget(int id) {
        PlanTarget target = planTargetDao.getPlanTarget(id);
        return target;
    }
}
