package org.cboard.dto;

import java.util.Date;

/*
 * 商品进销存基础数据传输
 */
public class ViewSaleStorage {
	private int id;
	private Date date;
	private String sku;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

}
