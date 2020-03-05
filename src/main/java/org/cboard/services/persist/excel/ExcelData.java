package org.cboard.services.persist.excel;

import java.util.List;
import java.util.Map;

/**
 * Excel数据对象
 *
 * @author OSWorks-XC
 * @since 2010-08-12
 */
public class ExcelData {

	/**
	 * Excel参数元数据对象
	 */
	private Map parametersDto;

	/**
	 * Excel集合元对象
	 */
	private List fieldsList;

	/**
	 * 构造函数
	 *
	 * @param pDto
	 *            元参数对象
	 * @param pList
	 *            集合元对象
	 */
	public ExcelData(Map pDto, List pList) {
		setParametersDto(pDto);
		setFieldsList(pList);
	}

	public Map getParametersDto() {
		return parametersDto;
	}

	public void setParametersDto(Map parametersDto) {
		this.parametersDto = parametersDto;
	}

	public List getFieldsList() {
		return fieldsList;
	}

	public void setFieldsList(List fieldsList) {
		this.fieldsList = fieldsList;
	}

}
