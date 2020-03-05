package org.cboard.dto;

import org.cboard.pojo.SalesTarget;

public class ViewSalesTarget {
	private Long id;
	private int year;
	private int month;
	private String dimension;
	private String object;
	private float target_payment;
	private int target_num;
	
	public ViewSalesTarget() {
		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public float getTarget_payment() {
		return target_payment;
	}

	public void setTarget_payment(float target_payment) {
		this.target_payment = target_payment;
	}

	public int getTarget_num() {
		return target_num;
	}

	public void setTarget_num(int target_num) {
		this.target_num = target_num;
	}

	public ViewSalesTarget(SalesTarget target) {
		this.id = target.getId();
		this.year = target.getYear();
		this.month = target.getMonth();
		this.dimension = target.getDimension();
		this.object = target.getObject();
		this.target_payment = target.getTarget_Payment();
		this.target_num = target.getTarget_Num();
	}
	
}
