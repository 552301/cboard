package org.cboard.controller;

import com.alibaba.fastjson.JSONObject;
import org.cboard.dto.ViewBICategoryPlanTarget;
import org.cboard.dto.ViewBIPlanTarget;
import org.cboard.pojo.BICategoryPlanTarget;
import org.cboard.pojo.BIPlanTarget;
import org.cboard.services.BIPlanTargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

/**
 * @Classname BIPlanTargetController
 * @Description TODO
 * @Date 2019-10-06 11:23
 * @Created by zhoujinming
 */
@RestController
@RequestMapping("/biplantarget")
public class BIPlanTargetController {
    @Autowired
    private BIPlanTargetService biplanTargetService;

    @RequestMapping(value = "/getBIPlanTargetList")
    public List<ViewBIPlanTarget> getBIPlanTargetList(
            @RequestParam(name = "page") int page
            ,@RequestParam(name = "rows") int rows
            ,@RequestParam(name = "sidx") String sidx
            ,@RequestParam(name = "sord") String sord
            ,@RequestParam(name = "_search") String _search
//            ,@RequestParam(name = "filters") String filters
            ,@RequestParam(name = "params") String params) {
        JSONObject jsonObject = JSONObject.parseObject(params);
        Map<String, Object> map = new HashMap<String, Object>();
        if (jsonObject.containsKey("styleBill")) {
            if (!jsonObject.getString("styleBill").isEmpty())
                map.put("styleBill", jsonObject.getString("styleBill"));
        }
        if (jsonObject.containsKey("planMonth")) {
            if (!jsonObject.getString("planMonth").isEmpty())
                map.put("planMonth", jsonObject.getString("planMonth"));
        }
        //从第几条数据开始
        int firstIndex = (page - 1) * rows;
        //到第几条数据结束
        int lastIndex = page * rows;
        List<ViewBIPlanTarget> vts = new ArrayList<ViewBIPlanTarget>();
        List<BIPlanTarget> ts = biplanTargetService.getBIPlanTargetList(map);
        if (ts.size() > lastIndex)
            ts.subList(firstIndex,lastIndex);
        else
            ts.subList(firstIndex, ts.size());
        switch (sidx){
            case "styleBill":{
                if(sord.equals("asc"))
                    Collections.sort(ts, Comparator.comparing(BIPlanTarget::getStyleBill));
                else
                    Collections.sort(ts, Comparator.comparing(BIPlanTarget::getStyleBill).reversed());
                break;
            }
            case "planMonth":{
                if(sord.equals("asc"))
                    Collections.sort(ts, Comparator.comparing(BIPlanTarget::getPlanMonth));
                else
                    Collections.sort(ts, Comparator.comparing(BIPlanTarget::getPlanMonth).reversed());
                break;
            }
            default:{
                if(sord.equals("asc"))
                    Collections.sort(ts, Comparator.comparing(BIPlanTarget::getPlanMonth));
                else
                    Collections.sort(ts, Comparator.comparing(BIPlanTarget::getPlanMonth).reversed());
                break;
            }
        }

        for (BIPlanTarget t : ts) {
            vts.add(new ViewBIPlanTarget(t));
        }

        return vts;
    }

    @RequestMapping(value = "/getBICategoryPlanTargetList")
    public List<ViewBICategoryPlanTarget> getBICategoryPlanTargetList(
            @RequestParam(name = "page") int page
            ,@RequestParam(name = "rows") int rows
            ,@RequestParam(name = "sidx") String sidx
            ,@RequestParam(name = "sord") String sord
            ,@RequestParam(name = "styleBill") String styleBill
//            ,@RequestParam(name = "filters") String filters
            ,@RequestParam(name = "planMonth") String planMonth) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (!styleBill.isEmpty())
            map.put("styleBill", styleBill);
        if (!planMonth.isEmpty())
            map.put("planMonth", planMonth);
        //从第几条数据开始
        int firstIndex = (page - 1) * rows;
        //到第几条数据结束
        int lastIndex = page * rows;
        List<ViewBICategoryPlanTarget> vts = new ArrayList<ViewBICategoryPlanTarget>();
        List<BICategoryPlanTarget> ts = biplanTargetService.getBICategoryPlanTargetList(map);
        if (ts.size() > lastIndex)
            ts.subList(firstIndex,lastIndex);
        else
            ts.subList(firstIndex, ts.size());
        switch (sidx){
            case "styleCategory":{
                if(sord.equals("asc"))
                    Collections.sort(ts, Comparator.comparing(BICategoryPlanTarget::getStyleCategory));
                else
                    Collections.sort(ts, Comparator.comparing(BICategoryPlanTarget::getStyleCategory).reversed());
                break;
            }
            case "planMonth":{
                if(sord.equals("asc"))
                    Collections.sort(ts, Comparator.comparing(BICategoryPlanTarget::getPlanMonth));
                else
                    Collections.sort(ts, Comparator.comparing(BICategoryPlanTarget::getPlanMonth).reversed());
                break;
            }
            default:{
                if(sord.equals("asc"))
                    Collections.sort(ts, Comparator.comparing(BICategoryPlanTarget::getPlanMonth));
                else
                    Collections.sort(ts, Comparator.comparing(BICategoryPlanTarget::getPlanMonth).reversed());
                break;
            }
        }

        for (BICategoryPlanTarget t : ts) {
            vts.add(new ViewBICategoryPlanTarget(t));
        }

        return vts;
    }

}
