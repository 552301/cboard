package org.cboard.dto;

/**
 * 注入条件
 */
public class InjectionParam{
	
	public InjectionParam() {}
	
	public InjectionParam(String paramName, String columnName, String dimensionName) {
		this.paramName = paramName;
		this.columnName = columnName;
		this.dimensionName = dimensionName;
	}
	
	private String paramName;
	private String columnName;
	private String dimensionName;
	
	public String getDimensionName() {
		return dimensionName;
	}

	public void setDimensionName(String dimensionName) {
		this.dimensionName = dimensionName;
	}

	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
}