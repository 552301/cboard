package org.cboard.pojo;

import java.util.Date;

public class DayReturnB2B{
	private int id;
	private String caustomer;
	private Date date;
	private String sku;
	private int qty;
	private double amount;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCaustomer() {
		return caustomer;
	}
	public void setCaustomer(String caustomer) {
		this.caustomer = caustomer;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
}