package org.cboard.pojo;

public class MonthReturnRateB2B{
	private int id;
	private int year;
	private int month;
	private String caustomer;
	private double rate;
	
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
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
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	
	
}