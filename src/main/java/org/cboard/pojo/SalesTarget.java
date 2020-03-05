package org.cboard.pojo;

import java.util.Date;

public class SalesTarget {
	private Long id;
	private int year;
    private int month;
    private String dimension;
    private String object;
	public Long getId() {
		return id;
	}
	public void setId(Long Id) {
		this.id = Id;
	}
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
	public String getDimension() {
		return dimension;
	}
	public void setDimension(String dimension) {
		this.dimension = dimension;
	}
	public String getObject() {
		return object;
	}
	public void setObject(String object) {
		this.object = object;
	}
	public float getTarget_Payment() {
		return target_payment;
	}
	public void setTarget_Payment(float target_Payment) {
		this.target_payment = target_Payment;
	}
	private float target_payment;
	private int target_num;
	public int getTarget_Num() {
		return this.target_num;
	}
	public void setTarget_Num(int target_Num) {
		this.target_num = target_Num;
	}
    
}
