package org.cboard.dto;

import org.cboard.pojo.BICategoryPlanTarget;

/**
 * @Classname ViewBICategoryPlanTarget
 * @Description TODO
 * @Date 2019-10-07 10:16
 * @Created by zhoujinming
 */
public class ViewBICategoryPlanTarget {

    private String styleCategory;
    private String planMonth;
    private Double finishNums;
    private Double sellNums;
    private Double planNums;
    private Double planRate;

    public ViewBICategoryPlanTarget(BICategoryPlanTarget target) {
        this.styleCategory = target.getStyleCategory();
        this.planMonth = target.getPlanMonth();
        this.finishNums = target.getFinishNums();
        this.sellNums = target.getSellNums();
        this.planNums = target.getPlanNums();
        this.planRate = target.getPlanRate();
    }

    public String getStyleCategory() {
        return styleCategory;
    }

    public void setStyleCategory(String styleCategory) {
        this.styleCategory = styleCategory;
    }

    public String getPlanMonth() {
        return planMonth;
    }

    public void setPlanMonth(String planMonth) {
        this.planMonth = planMonth;
    }

    public Double getFinishNums() {
        return finishNums;
    }

    public void setFinishNums(Double finishNums) {
        this.finishNums = finishNums;
    }

    public Double getSellNums() {
        return sellNums;
    }

    public void setSellNums(Double sellNums) {
        this.sellNums = sellNums;
    }

    public Double getPlanNums() {
        return planNums;
    }

    public void setPlanNums(Double planNums) {
        this.planNums = planNums;
    }

    public Double getPlanRate() {
        return planRate;
    }

    public void setPlanRate(Double planRate) {
        this.planRate = planRate;
    }
}
