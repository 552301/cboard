package org.cboard.pojo;

/**
 * @Classname BIPlanTarget
 * @Description TODO
 * @Date 2019-10-06 11:22
 * @Created by zhoujinming
 */
public class BIPlanTarget {

    private String styleBill;
    private String planMonth;
    private Double finishNums;
    private Double planNums;
    private Double sellNums;
    private Double finishRate;

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

    public Double getFinishNums() {
        return finishNums;
    }

    public void setFinishNums(Double finishNums) {
        this.finishNums = finishNums;
    }

    public Double getPlanNums() {
        return planNums;
    }

    public void setPlanNums(Double planNums) {
        this.planNums = planNums;
    }

    public Double getSellNums() {
        return sellNums;
    }

    public void setSellNums(Double sellNums) {
        this.sellNums = sellNums;
    }

    public Double getFinishRate() {
        return finishRate;
    }

    public void setFinishRate(Double finishRate) {
        this.finishRate = finishRate;
    }
}
