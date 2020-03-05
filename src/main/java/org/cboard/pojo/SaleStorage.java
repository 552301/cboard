package org.cboard.pojo;

import java.util.Date;
/**
 * 商品进销存基础数据
 */
public class SaleStorage {
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
