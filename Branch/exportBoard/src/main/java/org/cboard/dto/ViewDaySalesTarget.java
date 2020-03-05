package org.cboard.dto;
import java.util.Date;

import org.cboard.pojo.DaySalesTarget;

public class ViewDaySalesTarget {
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
	public void setBookSalesAmount(double bookSalesAmount) {
		this.bookSalesAmount = bookSalesAmount;
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
	
	public ViewDaySalesTarget(DaySalesTarget target) {
		this.id = target.getId();
		this.date = target.getDate();
		this.platform = target.getPlatform();
		this.brand = target.getBrand();
		this.store = target.getStore();
		this.salesTarget = target.getSalesTarget();
		this.refundTarget = target.getRefundTarget();
		this.bookSalesAmount = target.getBookSalesAmount();
		this.cashbackTarget = target.getCashbackTarget();
	}
	
}