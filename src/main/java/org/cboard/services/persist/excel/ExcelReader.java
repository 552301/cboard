package org.cboard.services.persist.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.cboard.util.CboardUtils;

/**
 * Excel数据读取器
 *
 * @author OSWorks-XC
 * @since 2010-08-12
 */
public class ExcelReader {
	private String metaData = null;
	private InputStream is = null;

	public ExcelReader() {
	};

	/**
	 * 构造函数
	 *
	 * @param pMetaData
	 *            元数据
	 * @param pIs
	 *            Excel数据流
	 * @throws IOException
	 * @throws BiffException
	 */
	public ExcelReader(String pMetaData, InputStream pIs) {
		setIs(pIs);
		setMetaData(pMetaData);
	}

	/**
	 * 读取Excel数据
	 *
	 * @param pBegin
	 *            从第几行开始读数据<br>
	 *            <b>注意下标索引从0开始的哦!
	 * @return 以List<BaseDTO>形式返回数据
	 * @throws BiffException
	 * @throws IOException
	 */
	public List read(int pBegin) throws BiffException, IOException {
		List list = new ArrayList();
		Workbook workbook = Workbook.getWorkbook(getIs());
		Sheet sheet = workbook.getSheet(0);
		int rows = sheet.getRows();
		String[] arrMeta = getMetaData().trim().split(",");
		for (int i = pBegin; i < rows; i++) {
			Map rowDto = new HashMap();
			Cell[] cells = sheet.getRow(i);
			for (int j = 0; j < arrMeta.length; j++) {
				String key = arrMeta[j];
				String cv = j > cells.length - 1 ? null : cells[j].getContents();
				if (CboardUtils.isNotEmpty(key) && CboardUtils.isNotEmpty(cv)) {
					rowDto.put(key,cv);
				}
			}
			if (CboardUtils.isEmpty(rowDto)) {
				rowDto.clear();
				continue;
			}
			list.add(rowDto);
		}
		getIs().close();
		return list;
	}

	/**
	 * 读取Excel数据
	 *
	 * @param pBegin
	 *            从第几行开始读数据<br>
	 *            <b>注意下标索引从0开始的哦!</b>
	 * @param pBack
	 *            工作表末尾减去的行数
	 * @return 以List<BaseDTO>形式返回数据
	 * @throws BiffException
	 * @throws IOException
	 */
	public List read(int pBegin, int pBack) throws BiffException, IOException {
		List list = new ArrayList();
		Workbook workbook = Workbook.getWorkbook(getIs());
		Sheet sheet = workbook.getSheet(0);
		int rows = sheet.getRows();
		String[] arrMeta = getMetaData().trim().split(",");
		for (int i = pBegin; i < rows - pBack; i++) {
			Map rowDto = new HashMap();
			Cell[] cells = sheet.getRow(i);
			for (int j = 0; j < arrMeta.length; j++) {
				String key = arrMeta[j];
				String cv = j > cells.length - 1 ? null : cells[j].getContents();
				if (CboardUtils.isNotEmpty(key) && CboardUtils.isNotEmpty(cv)) {
					rowDto.put(key,cv);
				}
			}
			if (CboardUtils.isEmpty(rowDto)) {
				rowDto.clear();
				continue;
			}
			list.add(rowDto);
		}
		getIs().close();
		return list;
	}

	public InputStream getIs() {
		return is;
	}

	public void setIs(InputStream is) {
		this.is = is;
	}

	public String getMetaData() {
		return metaData;
	}

	public void setMetaData(String metaData) {
		this.metaData = metaData;
	};
}
