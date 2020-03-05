package org.cboard.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @Classname PlanTarget
 * @Description TODO
 * @Date 2019/9/20 10:59
 * @Created by zhoujinming
 */
public class PlanTarget {
    private Integer id;
    private String season;
    private String category;
    private String planMonth;
    private String styleBill;
    private int planNums;

    private String createDate;

    private String modifiedDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getStyleBill() {
        return styleBill;
    }

    public void setStyleBill(String styleBill) {
        this.styleBill = styleBill;
    }

    public String getPlanMonth() {
        return planMonth;
    }

    public void setPlanMonth(String planMonth) {
        this.planMonth = planMonth;
    }

    public int getPlanNums() {
        return planNums;
    }

    public void setPlanNums(int planNums) {
        this.planNums = planNums;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
