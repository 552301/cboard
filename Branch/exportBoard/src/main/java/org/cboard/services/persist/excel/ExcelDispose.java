package org.cboard.services.persist.excel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.cboard.util.CboardUtils;
import org.cboard.util.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;

/**
 * Excel处理
 * 
 * @author lu.wang
 *
 */
@Component
public class ExcelDispose {

	private static final Logger LOG = LoggerFactory.getLogger(ExcelDispose.class);

	/**
	 * 导出Excel文件
	 * 
	 * @param hssfWorkBook
	 *            待导出的工作表
	 * @param exportFullPath
	 *            导出的Excel文件完整路径，如C:\test.xls
	 */
	public void exportHssfWorkBook(HSSFWorkbook hssfWorkBook, String exportFullPath) {
		if (hssfWorkBook == null || hssfWorkBook.getNumberOfSheets() == 0) {
			LOG.error("需要导出的工作薄为空，终止本次操作");
			return;
		}
		if (CboardUtils.isEmpty(exportFullPath)) {
			LOG.error("导出的Excel文件完整路径为空，终止本次操作");
			return;
		}
		File file = new File(exportFullPath);
		try {
			hssfWorkBook.write(file);
			this.closeHssfWorkBook(hssfWorkBook);
		} catch (IOException e) {
			LOG.error("HSSFWorkbook工作薄导出异常>>" + e.getMessage());
		}
	}

	/**
	 * 读取Excel文件并转换为输入流{@link InputStream}
	 * 
	 * @param readFullPath
	 *            待读取的文件完整路径，如C:\test.xlsx
	 * @return 输入流,提示：用完释放资源.close()
	 */
	public InputStream readToInputStream(String readFullPath) {
		if (CboardUtils.isEmpty(readFullPath)) {
			LOG.error("需要读取的Excel文件路径为空，终止本次操作");
			return null;
		}
		File file = new File(readFullPath);
		if (!file.exists()) {
			LOG.error("Excel完整路径>>" + readFullPath + "不存在");
			return null;
		}
		InputStream is = null;
		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			LOG.error("文件转换为FileInputStream时异常>>" + e.getMessage());
			return null;
		}
		return is;
	}

	/**
	 * 将工作薄{@link HSSFWorkbook}转换为输入流{@link InputStream}
	 * 
	 * @param hssfWorkBook
	 *            带转换的工作薄
	 * @return 输入流，提示：用完释放资源.close()
	 */
	public InputStream hssfWorkBookToInputStream(HSSFWorkbook hssfWorkBook) {
		if (hssfWorkBook == null || hssfWorkBook.getNumberOfSheets() == 0) {
			LOG.error("需要转换为输入流InputStream的工作薄为空，终止本次操作");
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			hssfWorkBook.write(baos);
			baos.flush();
			this.closeHssfWorkBook(hssfWorkBook);
		} catch (IOException e) {
			LOG.error("工作薄HSSFWorkbook转换为ByteArrayOutputStream时异常>>" + e.getMessage());
			return null;
		}
		return new ByteArrayInputStream(baos.toByteArray());
	}

	/**
	 * 获取设置好的Excel工作薄{@link HSSFWorkbook}
	 * 
	 * @param list
	 *            待填入Excel的Json（字符串）数据
	 * @return 工作薄,提示：用完释放资源.close()
	 */
	public HSSFWorkbook getfullHssfWorkBook(List<String> list) {
		if (list == null) {
			LOG.error("Json字符串集合（List）为null");
			return null;
		}
		HSSFWorkbook hssfWorkBook = new HSSFWorkbook();
		int i = 0;
		for (String dataJson : list) {
			this.setWorkBook(hssfWorkBook, i++, dataJson);
		}
		return hssfWorkBook.getNumberOfSheets() == 0 ? null : hssfWorkBook;
	}

	/**
	 * 将工作薄{@link HSSFWorkbook}填入数据、设置样式<br>
	 * 目前仅支持{@link HSSFWorkbook}，与{@link XlsProcessService}业务类处理有关
	 * 
	 * @param hssfWorkBook
	 *            待写入的工作薄，注：不可为null，应为实例化后的对象
	 * @param sheetIndex
	 *            工作表下标
	 * @param dataJson
	 *            Json格式字符串
	 */
	public void setWorkBook(HSSFWorkbook hssfWorkBook, int sheetIndex, String dataJson) {
		if (hssfWorkBook == null) {
			LOG.error("HSSFWorkbook为null，不能对Excel（HSSFWorkbook）对象进行操作");
			return;
		}
		if (sheetIndex < 0)
			sheetIndex = 0;

		if (CboardUtils.isEmpty(dataJson)) {
			LOG.error("要解析的Json格式字符串为空，终止本次操作");
			return;
		}
		// Json字符串解析
		JSONObject data;
		try {
			data = JSONObject.parseObject(dataJson);
		} catch (Exception e) {
			LOG.error("Json字符串解析异常>>" + e.getMessage());
			return;
		}
		// 判断是否为空，例如解析后的结果：{}
		if (data.isEmpty()) {
			LOG.error("解析完成后的JSONObject为空>>" + data);
			return;
		}
		XlsProcessService xlsProcessService = SpringContextUtil.getBean(XlsProcessService.class);
		// 调用业务类填充数据以及设置样式
		try {
			xlsProcessService.tableToxls(data, hssfWorkBook, sheetIndex);
		} catch (Exception e) {
			LOG.error("XlsProcessService业务类处理异常>>" + e.getMessage());
		}
	}

	/**
	 * 关闭工作薄{@link HSSFWorkbook}
	 * 
	 * @param hssfWorkBook
	 */
	public void closeHssfWorkBook(HSSFWorkbook hssfWorkBook) {
		if (hssfWorkBook == null)
			return;
		try {
			hssfWorkBook.close();
		} catch (IOException e) {
			LOG.equals("关闭工作薄HSSFWorkbook资源异常>>" + e.getMessage());
		}
	}
}
