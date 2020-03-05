package org.cboard.pojo;

import java.util.Date;

public class DaySalesTarget {
	private Long id;
	private Date date;
    private String platform;
    private String brand;
    private String store;
    
    public Date getDate() {
    	return date;
    }
    public void setDate(Date date) {
    	this.date = date;
    }
	public double getSalesTarget() {
		return salesTarget;
	}
	public void setSalesTarget(double d) {
		this.salesTarget = d;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}
	public double getRefundTarget() {
		return refundTarget;
	}
	public void setRefundTarget(double refundTarget) {
		this.refundTarget = refundTarget;
	}
	public double getBookSalesAmount() {
		return bookSalesAmount;
	}
	public void setBookSalesAmount(double bookSalseAmount) {
		this.bookSalesAmount = bookSalseAmount;
	}
	private double salesTarget;    
    private double refundTarget;
    private double bookSalesAmount;
    private double cashbackTarget;
    
	public double getCashbackTarget() {
		return cashbackTarget;
	}
	public void setCashbackTarget(double cashbackTarget) {
		this.cashbackTarget = cashbackTarget;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long Id) {
		this.id = Id;
	}
	
}