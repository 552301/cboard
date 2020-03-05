package org.cboard.dto;

import org.cboard.pojo.PlanTarget;

import java.util.Date;

/**
 * @Classname ViewPlanTarget
 * @Description TODO
 * @Date 2019-09-20 11:06
 * @Created by zhoujinming
 */
public class ViewPlanTarget {
    private int id;
    private String season;
    private String category;
    private String planMonth;
    private String styleBill;
    private int planNums;
    private String createDate;
    private String modifiedDate;

    public ViewPlanTarget(PlanTarget planTarget) {
        this.id = planTarget.getId();
        this.season = planTarget.getSeason();
        this.category = planTarget.getCategory();
        this.planMonth = planTarget.getPlanMonth();
        this.styleBill = planTarget.getStyleBill();
        this.planNums = planTarget.getPlanNums();
        this.createDate = planTarget.getCreateDate();
        this.modifiedDate = planTarget.getModifiedDate();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPlanMonth() {
        return planMonth;
    }

    public void setPlanMonth(String planMonth) {
        this.planMonth = planMonth;
    }

    public String getStyleBill() {
        return styleBill;
    }

    public void setStyleBill(String styleBill) {
        this.styleBill = styleBill;
    }

    public int getPlanNums() {
        return planNums;
    }

    public void setPlanNums(int planNums) {
        this.planNums = planNums;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
