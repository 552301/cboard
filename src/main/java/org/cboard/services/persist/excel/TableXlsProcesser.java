package org.cboard.services.persist.excel;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.apache.poi.common.usermodel.fonts.FontInfo;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeUtil;
import org.apache.poi.xssf.streaming.SXSSFRow.FilledCellIterator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by yfyuan on 2017/2/15.
 */
public class TableXlsProcesser extends XlsProcesser {
	

    @Override
    protected ClientAnchor drawContent(XlsProcesserContext context)  {
    	
    	HSSFFont redFont = (HSSFFont) context.getWb().createFont();  
    	redFont.setColor(HSSFColor.RED.index);// 红色  
    	redFont.setBold(true);
    	
    	HSSFFont blackFont = (HSSFFont) context.getWb().createFont();  
    	blackFont.setColor(HSSFColor.BLACK.index);// 蓝色  
    	blackFont.setBold(true);
    	
    	//红色不加粗字体
    	 Font font =context.getWb().createFont();
         font.setFontHeightInPoints((short) 8);//字号
         font.setFontName("Calibri");
         font.setColor(IndexedColors.RED.getIndex());
         
       //红色不加粗字体
    	 Font littleFont =context.getWb().createFont();
    	 littleFont.setFontHeightInPoints((short) 10);//字号
    	 littleFont.setFontName("Calibri");
    	 littleFont.setColor(IndexedColors.RED.getIndex());
         
         
         //黑色不加粗字体
         Font blackLittleFont =context.getWb().createFont();
         blackLittleFont.setFontHeightInPoints((short) 8);//字号
         blackLittleFont.setFontName("Calibri");
         blackLittleFont.setColor(IndexedColors.BLACK.getIndex());
         
       //黑色不加粗字体
         Font blackLittleEightFont =context.getWb().createFont();
         blackLittleEightFont.setFontHeightInPoints((short) 10);//字号
         blackLittleEightFont.setFontName("Calibri");
         blackLittleEightFont.setColor(IndexedColors.BLACK.getIndex());
         
         //黑色加粗字体
         Font wideFont =context.getWb().createFont();
         wideFont.setFontHeightInPoints((short) 10);//字号
         wideFont.setFontName("Calibri");
         wideFont.setColor(IndexedColors.BLACK.getIndex());
//         wideFont.setBoldweight();//粗体显示
         wideFont.setBold(true);//粗体显示
         
         //黑色加粗字体
         Font wideRedFont =context.getWb().createFont();
         wideRedFont.setFontHeightInPoints((short) 10);
         wideRedFont.setFontName("Calibri");
         wideRedFont.setColor(IndexedColors.BLACK.getIndex());
//         wideFont.setBoldweight();//粗体显示
         wideRedFont.setBold(true);//粗体显示
         //黑色加粗字体
         Font wideRed12Font =context.getWb().createFont();
         wideRed12Font.setFontHeightInPoints((short) 12);
         wideRed12Font.setFontName("Calibri");
         wideRed12Font.setColor(IndexedColors.BLACK.getIndex());
//         wideFont.setBoldweight();//粗体显示
         wideRed12Font.setBold(true);//粗体显示
         
         //黑色加粗字体
         Font wideRed11Font =context.getWb().createFont();
         wideRed11Font.setFontHeightInPoints((short) 11);
         wideRed11Font.setFontName("Calibri");
         wideRed11Font.setColor(IndexedColors.BLACK.getIndex());
//         wideFont.setBoldweight();//粗体显示
         wideRed11Font.setBold(true);//粗体显示
         
         //白色背景加粗字体
         CellStyle blodFont12Style = context.getWb().createCellStyle();
         blodFont12Style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
         blodFont12Style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
         blodFont12Style.setAlignment(HorizontalAlignment.LEFT);
         blodFont12Style.setVerticalAlignment(VerticalAlignment.CENTER);
         blodFont12Style.setBorderBottom(BorderStyle.NONE);
         blodFont12Style.setBorderLeft(BorderStyle.NONE);
         blodFont12Style.setBorderRight(BorderStyle.NONE);
         blodFont12Style.setBorderTop(BorderStyle.NONE);
         blodFont12Style.setFont(wideRed12Font);
         
         //白色背景加粗字体
         CellStyle blodFont11Style = context.getWb().createCellStyle();
         blodFont11Style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
         blodFont11Style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
         blodFont11Style.setAlignment(HorizontalAlignment.LEFT);
         blodFont11Style.setVerticalAlignment(VerticalAlignment.CENTER);
         blodFont11Style.setBorderBottom(BorderStyle.NONE);
         blodFont11Style.setBorderLeft(BorderStyle.NONE);
         blodFont11Style.setBorderRight(BorderStyle.NONE);
         blodFont11Style.setBorderTop(BorderStyle.NONE);
         blodFont11Style.setFont(wideRed11Font);
         
        
         
       //白色背景加粗字体
         CellStyle blodFontStyle = context.getWb().createCellStyle();
         blodFontStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
         blodFontStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
         blodFontStyle.setAlignment(HorizontalAlignment.LEFT);
         blodFontStyle.setVerticalAlignment(VerticalAlignment.CENTER);
         blodFontStyle.setBorderBottom(BorderStyle.NONE);
         blodFontStyle.setBorderLeft(BorderStyle.NONE);
         blodFontStyle.setBorderRight(BorderStyle.NONE);
         blodFontStyle.setBorderTop(BorderStyle.NONE);
         blodFontStyle.setFont(wideFont);
         
         
         String day=null;
         //红色字体
         CellStyle yellowStyle = context.getWb().createCellStyle();
//         redStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
         yellowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
         yellowStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
//         yellowStyle.setFont(font);
         yellowStyle.setAlignment(HorizontalAlignment.CENTER);
         yellowStyle.setVerticalAlignment(VerticalAlignment.CENTER);
         yellowStyle.setBorderBottom(BorderStyle.THIN);
        // yellowStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
         yellowStyle.setBorderLeft(BorderStyle.THIN);
        // yellowStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
         yellowStyle.setBorderRight(BorderStyle.THIN);
        // yellowStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
         yellowStyle.setBorderTop(BorderStyle.THIN);
        // yellowStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
         
         //白色背景
         CellStyle whiteStyle = context.getWb().createCellStyle();
         whiteStyle.setFont(blackLittleFont);
         whiteStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
         whiteStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
         whiteStyle.setAlignment(HorizontalAlignment.CENTER);
         whiteStyle.setVerticalAlignment(VerticalAlignment.CENTER);
         whiteStyle.setBorderBottom(BorderStyle.THIN);
         whiteStyle.setBorderLeft(BorderStyle.THIN);
         whiteStyle.setBorderRight(BorderStyle.THIN);
         whiteStyle.setBorderTop(BorderStyle.THIN);
         
       //白色背景加粗字体
         CellStyle whiteBlodStyle = context.getWb().createCellStyle();
         whiteBlodStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
         whiteBlodStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
         whiteBlodStyle.setAlignment(HorizontalAlignment.CENTER);
         whiteBlodStyle.setVerticalAlignment(VerticalAlignment.CENTER);
         whiteBlodStyle.setBorderBottom(BorderStyle.THIN);
         whiteBlodStyle.setBorderLeft(BorderStyle.THIN);
         whiteBlodStyle.setBorderRight(BorderStyle.MEDIUM);
         whiteBlodStyle.setBorderTop(BorderStyle.MEDIUM);
         whiteBlodStyle.setFont(wideFont);
       
       
         
         //红色字体
         CellStyle redStyle = context.getWb().createCellStyle();
//         redStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
         redStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
         redStyle.setFont(font);
         redStyle.setAlignment(HorizontalAlignment.CENTER);
         redStyle.setVerticalAlignment(VerticalAlignment.CENTER);
         redStyle.setBorderBottom(BorderStyle.THIN);
         redStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
         redStyle.setBorderLeft(BorderStyle.THIN);
         redStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
         redStyle.setBorderRight(BorderStyle.THIN);
         redStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
         redStyle.setBorderTop(BorderStyle.THIN);
         redStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
         
         //红色字体
         CellStyle redRightStyle = context.getWb().createCellStyle();
         redRightStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
         redRightStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
         redRightStyle.setFont(font);
         redRightStyle.setAlignment(HorizontalAlignment.CENTER);
         redRightStyle.setVerticalAlignment(VerticalAlignment.CENTER);
         redRightStyle.setBorderBottom(BorderStyle.THIN);
         redRightStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
         redRightStyle.setBorderLeft(BorderStyle.THIN);
         redRightStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
         redRightStyle.setBorderRight(BorderStyle.MEDIUM);
         redRightStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
         redRightStyle.setBorderTop(BorderStyle.THIN);
         redRightStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
         
         //黑色字体白色背景
         CellStyle blackRightStyle = context.getWb().createCellStyle();
         blackRightStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
         blackRightStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
         blackRightStyle.setFont(blackLittleFont);
         blackRightStyle.setAlignment(HorizontalAlignment.CENTER);
         blackRightStyle.setVerticalAlignment(VerticalAlignment.CENTER);
         blackRightStyle.setBorderBottom(BorderStyle.THIN);
         blackRightStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
         blackRightStyle.setBorderLeft(BorderStyle.THIN);
         blackRightStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
         blackRightStyle.setBorderRight(BorderStyle.MEDIUM);
         blackRightStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
         blackRightStyle.setBorderTop(BorderStyle.THIN);
         blackRightStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
         
       //黑色字体白色背景
         CellStyle blackLeftStyle = context.getWb().createCellStyle();
         blackLeftStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
         blackLeftStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
         blackLeftStyle.setFont(blackLittleFont);
         blackLeftStyle.setAlignment(HorizontalAlignment.CENTER);
         blackLeftStyle.setVerticalAlignment(VerticalAlignment.CENTER);
         blackLeftStyle.setBorderBottom(BorderStyle.THIN);
         blackLeftStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
         blackLeftStyle.setBorderLeft(BorderStyle.MEDIUM);
         blackLeftStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
         blackLeftStyle.setBorderRight(BorderStyle.THIN);
         blackLeftStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
         blackLeftStyle.setBorderTop(BorderStyle.THIN);
         blackLeftStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
         
		//转为RGB码  
		HSSFWorkbook wb=(HSSFWorkbook) context.getWb();
		//自定义cell颜色  
		HSSFPalette palette = wb.getCustomPalette();   
		//这里的9是索引  
		palette.setColorAtIndex((short)HSSFColor.GREY_25_PERCENT.index, (byte) 208, (byte) 205, (byte) 209); 

         //单独灰色背景
         CellStyle greyStyle = context.getWb().createCellStyle();
         greyStyle.setFillForegroundColor((short)HSSFColor.GREY_25_PERCENT.index);
         greyStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
         greyStyle.setAlignment(HorizontalAlignment.CENTER);
         greyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
         greyStyle.setBorderBottom(BorderStyle.THIN);
         greyStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
         greyStyle.setBorderLeft(BorderStyle.THIN);
         greyStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
         greyStyle.setBorderRight(BorderStyle.THIN);
         greyStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
         greyStyle.setBorderTop(BorderStyle.THIN);
         greyStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
         greyStyle.setFont(blackLittleFont);
         
       //单独灰色背景右边框粗线
         CellStyle greyRightStyle = context.getWb().createCellStyle();
         greyRightStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
         greyRightStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
         greyRightStyle.setAlignment(HorizontalAlignment.CENTER);
         greyRightStyle.setVerticalAlignment(VerticalAlignment.CENTER);
         greyRightStyle.setBorderBottom(BorderStyle.THIN);
         greyRightStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
         greyRightStyle.setBorderLeft(BorderStyle.THIN);
         greyRightStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
         greyRightStyle.setBorderRight(BorderStyle.MEDIUM);
         greyRightStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
         greyRightStyle.setBorderTop(BorderStyle.THIN);
         greyRightStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
         greyRightStyle.setFont(blackLittleFont);
         
       //单独灰色背景左边框粗线
         CellStyle greyLeftStyle = context.getWb().createCellStyle();
         greyLeftStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
         greyLeftStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
         greyLeftStyle.setAlignment(HorizontalAlignment.CENTER);
         greyLeftStyle.setVerticalAlignment(VerticalAlignment.CENTER);
         greyLeftStyle.setBorderBottom(BorderStyle.THIN);
         greyLeftStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
         greyLeftStyle.setBorderLeft(BorderStyle.MEDIUM);
         greyLeftStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
         greyLeftStyle.setBorderRight(BorderStyle.THIN);
         greyLeftStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
         greyLeftStyle.setBorderTop(BorderStyle.THIN);
         greyLeftStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
         greyLeftStyle.setFont(blackLittleFont);
         
       //单独灰色背景下边框粗线
         CellStyle grey10BlodBottomStyle = context.getWb().createCellStyle();
         grey10BlodBottomStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
         grey10BlodBottomStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
         grey10BlodBottomStyle.setAlignment(HorizontalAlignment.CENTER);
         grey10BlodBottomStyle.setVerticalAlignment(VerticalAlignment.CENTER);
         grey10BlodBottomStyle.setBorderBottom(BorderStyle.MEDIUM);
         grey10BlodBottomStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
         grey10BlodBottomStyle.setBorderLeft(BorderStyle.THIN);
         grey10BlodBottomStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
         grey10BlodBottomStyle.setBorderRight(BorderStyle.THIN);
         grey10BlodBottomStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
         grey10BlodBottomStyle.setBorderTop(BorderStyle.THIN);
         grey10BlodBottomStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
         grey10BlodBottomStyle.setFont(blackLittleEightFont);	
         
       //单独灰色背景下边框粗线
         CellStyle greyBlodTopStyle = context.getWb().createCellStyle();
         greyBlodTopStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
         greyBlodTopStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
         greyBlodTopStyle.setAlignment(HorizontalAlignment.CENTER);
         greyBlodTopStyle.setVerticalAlignment(VerticalAlignment.CENTER);
         greyBlodTopStyle.setBorderBottom(BorderStyle.MEDIUM);
         greyBlodTopStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
         greyBlodTopStyle.setBorderLeft(BorderStyle.THIN);
         greyBlodTopStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
         greyBlodTopStyle.setBorderRight(BorderStyle.THIN);
         greyBlodTopStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
         greyBlodTopStyle.setBorderTop(BorderStyle.MEDIUM);
         greyBlodTopStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
         greyBlodTopStyle.setFont(blackLittleFont);
         
       //单独灰色背景下边框粗线
         CellStyle greyBlodBottomStyle = context.getWb().createCellStyle();
         greyBlodBottomStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
         greyBlodBottomStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
         greyBlodBottomStyle.setAlignment(HorizontalAlignment.CENTER);
         greyBlodBottomStyle.setVerticalAlignment(VerticalAlignment.CENTER);
         greyBlodBottomStyle.setBorderBottom(BorderStyle.MEDIUM);
         greyBlodBottomStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
         greyBlodBottomStyle.setBorderLeft(BorderStyle.THIN);
         greyBlodBottomStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
         greyBlodBottomStyle.setBorderRight(BorderStyle.THIN);
         greyBlodBottomStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
         greyBlodBottomStyle.setBorderTop(BorderStyle.THIN);
         greyBlodBottomStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
         greyBlodBottomStyle.setFont(blackLittleFont);	
         
       //单独灰色背景下边框粗线红色字体
         CellStyle greyBlodBottomRedStyle = context.getWb().createCellStyle();
         greyBlodBottomRedStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
         greyBlodBottomRedStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
         greyBlodBottomRedStyle.setAlignment(HorizontalAlignment.CENTER);
         greyBlodBottomRedStyle.setVerticalAlignment(VerticalAlignment.CENTER);
         greyBlodBottomRedStyle.setBorderBottom(BorderStyle.MEDIUM);
         greyBlodBottomRedStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
         greyBlodBottomRedStyle.setBorderLeft(BorderStyle.THIN);
         greyBlodBottomRedStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
         greyBlodBottomRedStyle.setBorderRight(BorderStyle.THIN);
         greyBlodBottomRedStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
         greyBlodBottomRedStyle.setBorderTop(BorderStyle.THIN);
         greyBlodBottomRedStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
         greyBlodBottomRedStyle.setFont(font);
         
       //单独灰色背景四边框粗线
         CellStyle greyBlodTopRightStyle = context.getWb().createCellStyle();
         greyBlodTopRightStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
         greyBlodTopRightStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
         greyBlodTopRightStyle.setAlignment(HorizontalAlignment.CENTER);
         greyBlodTopRightStyle.setVerticalAlignment(VerticalAlignment.CENTER);
         greyBlodTopRightStyle.setBorderBottom(BorderStyle.MEDIUM);
         greyBlodTopRightStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
         greyBlodTopRightStyle.setBorderLeft(BorderStyle.THIN);
         greyBlodTopRightStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
         greyBlodTopRightStyle.setBorderRight(BorderStyle.MEDIUM);
         greyBlodTopRightStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
         greyBlodTopRightStyle.setBorderTop(BorderStyle.MEDIUM);
         greyBlodTopRightStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
         greyBlodTopRightStyle.setFont(blackLittleFont);
         
         //单独灰色背景下边框粗线
         CellStyle greyBlodBottomRightStyle = context.getWb().createCellStyle();
         greyBlodBottomRightStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
         greyBlodBottomRightStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
         greyBlodBottomRightStyle.setAlignment(HorizontalAlignment.CENTER);
         greyBlodBottomRightStyle.setVerticalAlignment(VerticalAlignment.CENTER);
         greyBlodBottomRightStyle.setBorderBottom(BorderStyle.MEDIUM);
         greyBlodBottomRightStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
         greyBlodBottomRightStyle.setBorderLeft(BorderStyle.THIN);
         greyBlodBottomRightStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
         greyBlodBottomRightStyle.setBorderRight(BorderStyle.MEDIUM);
         greyBlodBottomRightStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
         greyBlodBottomRightStyle.setBorderTop(BorderStyle.THIN);
         greyBlodBottomRightStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
         greyBlodBottomRightStyle.setFont(blackLittleFont);
         
         //单独灰色背景四边框粗线红色字体
         CellStyle greyBlodTopRightRedStyle = context.getWb().createCellStyle();
         greyBlodTopRightRedStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
         greyBlodTopRightRedStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
         greyBlodTopRightRedStyle.setAlignment(HorizontalAlignment.CENTER);
         greyBlodTopRightRedStyle.setVerticalAlignment(VerticalAlignment.CENTER);
         greyBlodTopRightRedStyle.setBorderBottom(BorderStyle.MEDIUM);
         greyBlodTopRightRedStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
         greyBlodTopRightRedStyle.setBorderLeft(BorderStyle.THIN);
         greyBlodTopRightRedStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
         greyBlodTopRightRedStyle.setBorderRight(BorderStyle.MEDIUM);
         greyBlodTopRightRedStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
         greyBlodTopRightRedStyle.setBorderTop(BorderStyle.MEDIUM);
         greyBlodTopRightRedStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
         greyBlodTopRightRedStyle.setFont(font);
         
       //单独灰色背景下边框粗线红色字体
         CellStyle greyBlodBottomRightRedStyle = context.getWb().createCellStyle();
         greyBlodBottomRightRedStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
         greyBlodBottomRightRedStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
         greyBlodBottomRightRedStyle.setAlignment(HorizontalAlignment.CENTER);
         greyBlodBottomRightRedStyle.setVerticalAlignment(VerticalAlignment.CENTER);
         greyBlodBottomRightRedStyle.setBorderBottom(BorderStyle.MEDIUM);
         greyBlodBottomRightRedStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
         greyBlodBottomRightRedStyle.setBorderLeft(BorderStyle.THIN);
         greyBlodBottomRightRedStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
         greyBlodBottomRightRedStyle.setBorderRight(BorderStyle.MEDIUM);
         greyBlodBottomRightRedStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
         greyBlodBottomRightRedStyle.setBorderTop(BorderStyle.THIN);
         greyBlodBottomRightRedStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
         greyBlodBottomRightRedStyle.setFont(font);
         
         //标题灰色背景
         CellStyle titleStyle = context.getWb().createCellStyle();
         titleStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
         titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
         titleStyle.setAlignment(HorizontalAlignment.CENTER);
         titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
         titleStyle.setBorderBottom(BorderStyle.THIN);
         titleStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
         titleStyle.setBorderLeft(BorderStyle.THIN);
         titleStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
         titleStyle.setBorderRight(BorderStyle.THIN);
         titleStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
         titleStyle.setBorderTop(BorderStyle.THIN);
         titleStyle.setFont(blackLittleFont);
         titleStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
         
       //标题灰色背景
         CellStyle titleBoldStyle = context.getWb().createCellStyle();
         titleBoldStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
         titleBoldStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
         titleBoldStyle.setAlignment(HorizontalAlignment.CENTER);
         titleBoldStyle.setVerticalAlignment(VerticalAlignment.CENTER);
         titleBoldStyle.setBorderBottom(BorderStyle.MEDIUM);
         titleBoldStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
         titleBoldStyle.setBorderLeft(BorderStyle.MEDIUM);
         titleBoldStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
         titleBoldStyle.setBorderRight(BorderStyle.MEDIUM);
         titleBoldStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
         titleBoldStyle.setBorderTop(BorderStyle.MEDIUM);
         titleBoldStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
         titleBoldStyle.setFont(wideFont);
         
       //标题灰色背景红字
         CellStyle titleRedStyle = context.getWb().createCellStyle();
         titleRedStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
         titleRedStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
         titleRedStyle.setAlignment(HorizontalAlignment.CENTER);
         titleRedStyle.setFont(font);
         titleRedStyle.setVerticalAlignment(VerticalAlignment.CENTER);
         titleRedStyle.setBorderBottom(BorderStyle.THIN);
         titleRedStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
         titleRedStyle.setBorderLeft(BorderStyle.THIN);
         titleRedStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
         titleRedStyle.setBorderRight(BorderStyle.THIN);
         titleRedStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
         titleRedStyle.setBorderTop(BorderStyle.THIN);
         titleRedStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
         
       //标题灰色背景红字有边框粗线
         CellStyle titleRedRightStyle = context.getWb().createCellStyle();
         titleRedRightStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
         titleRedRightStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
         titleRedRightStyle.setAlignment(HorizontalAlignment.CENTER);
         titleRedRightStyle.setFont(font);
         titleRedRightStyle.setVerticalAlignment(VerticalAlignment.CENTER);
         titleRedRightStyle.setBorderBottom(BorderStyle.THIN);
         titleRedRightStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
         titleRedRightStyle.setBorderLeft(BorderStyle.THIN);
         titleRedRightStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
         titleRedRightStyle.setBorderRight(BorderStyle.MEDIUM);
         titleRedRightStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
         titleRedRightStyle.setBorderTop(BorderStyle.THIN);
         titleRedRightStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
         
       //标题灰色背景红字左边框粗线
         CellStyle titleRedLeftStyle = context.getWb().createCellStyle();
         titleRedLeftStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
         titleRedLeftStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
         titleRedLeftStyle.setAlignment(HorizontalAlignment.CENTER);
         titleRedLeftStyle.setFont(font);
         titleRedLeftStyle.setVerticalAlignment(VerticalAlignment.CENTER);
         titleRedLeftStyle.setBorderBottom(BorderStyle.THIN);
         titleRedLeftStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
         titleRedLeftStyle.setBorderLeft(BorderStyle.MEDIUM);
         titleRedLeftStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
         titleRedLeftStyle.setBorderRight(BorderStyle.THIN);
         titleRedLeftStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
         titleRedLeftStyle.setBorderTop(BorderStyle.THIN);
         titleRedLeftStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
         
       //标题灰色背景红字粗下边框
         CellStyle titleRedBottomStyle = context.getWb().createCellStyle();
         titleRedBottomStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
         titleRedBottomStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
         titleRedBottomStyle.setAlignment(HorizontalAlignment.CENTER);
         titleRedBottomStyle.setFont(font);
         titleRedBottomStyle.setVerticalAlignment(VerticalAlignment.CENTER);
         titleRedBottomStyle.setBorderBottom(BorderStyle.MEDIUM);
         titleRedBottomStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
         titleRedBottomStyle.setBorderLeft(BorderStyle.THIN);
         titleRedBottomStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
         titleRedBottomStyle.setBorderRight(BorderStyle.THIN);
         titleRedBottomStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
         titleRedBottomStyle.setBorderTop(BorderStyle.THIN);
         titleRedBottomStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
         
       //标题灰色背景红字粗下、右边框
         CellStyle titleRedBottomRightStyle = context.getWb().createCellStyle();
         titleRedBottomRightStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
         titleRedBottomRightStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
         titleRedBottomRightStyle.setAlignment(HorizontalAlignment.CENTER);
         titleRedBottomRightStyle.setFont(font);
         titleRedBottomRightStyle.setVerticalAlignment(VerticalAlignment.CENTER);
         titleRedBottomRightStyle.setBorderBottom(BorderStyle.MEDIUM);
         titleRedBottomRightStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
         titleRedBottomRightStyle.setBorderLeft(BorderStyle.THIN);
         titleRedBottomRightStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
         titleRedBottomRightStyle.setBorderRight(BorderStyle.MEDIUM);
         titleRedBottomRightStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
         titleRedBottomRightStyle.setBorderTop(BorderStyle.THIN);
         titleRedBottomRightStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
         
         //如果是符合条件的报表，则将标题行数据去掉，手动生成。
        JSONArray tData = context.getData().getJSONArray("data");
        if (context.getTable().contains("EPO E-COMMERCE Daily Achievement")||context.getTable().contains("EPO eCOMM Daily Achivement by Platform")
        		||context.getTable().contains("EPO-Online Daily Sales by Platform-TTL")
        		||context.getTable().contains("EPO-Online Daily Sales by Platform-B2B")
        		||context.getTable().contains("MOCO-Online Daily Sales by Platform-B2C")
        		||context.getTable().contains("MOKIDS-Online Daily Sales by Platform-B2C")
        		||context.getTable().contains("Edition-Online Daily Sales by Platform-B2C")
        		
        		){
        for (int i = 0; i < tData.size(); i++) {
			for (int j = 0; j < tData.getJSONArray(i).size(); j++) {
				 JSONArray  caa=tData.getJSONArray(i);
				 if (caa.getJSONObject(j).getString("property").equals("header_key")) {
					 tData.remove(caa);
				}
			}
		}
        }

        final ClientAnchor tAnchor = new HSSFClientAnchor();
        tAnchor.setCol1(context.getC1());
        tAnchor.setRow1(context.getR1());
        int colSpan = (context.getC2() - context.getC1()) / tData.getJSONArray(0).size();
        int delta = (context.getC2() - context.getC1()) % tData.getJSONArray(0).size();
        int rowHeader = 0;
        List<Integer> columnHeaderCellIdx = new ArrayList<>();
        List<Integer> columnDataCellIdx = new ArrayList<>();
        List<CellRangeAddress> mergeRegion = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM");
    	Date headDate =null;
    	try {
    		headDate=sdf.parse(context.getDate());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
     
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
    	String now =df.format(new Date());
        String tableName =context.getTable();
        String dateStr=tableName.substring(tableName.length()-21);
        dateStr=dateStr.substring(0,10);
        if (dateStr.equals(now)) {
        	cal.setTime(headDate);
        	cal.add(Calendar.MONTH, -1);
        	headDate = cal.getTime();
        	context.setDate(sdf.format(headDate));
		}
        
        //  每日销售目标-管理层
        if (context.getTable().contains("每日销售目标-管理层")) {
            Row row1 = context.getBoardSheet().createRow(0);
        	Cell cell1=row1.createCell(0);
        	String sheetName =context.getWb().getSheetName(context.getSheetIndex());
//        	cell1.setCellValue("EPO");
        	cell1.setCellValue("EPO E-COMMERCE Daily Report (Sales)——"+sheetName+".");
        	
        	
        	
        	 CellRangeAddress cra =new CellRangeAddress(0, 0, 0, 12); // 起始行, 终止行, 起始列, 终止列  
        	 context.getBoardSheet().addMergedRegion(cra); 
        	 
		}else if (context.getTable().contains("EPO E-COMMERCE Daily Achievement(B2C)")) {
			 Row row1 = context.getBoardSheet().createRow(0);
	        	Cell cell1=row1.createCell(0);
	        	String sheetName =context.getWb().getSheetName(context.getSheetIndex());
//	        	cell1.setCellValue("EPO");
	        	Locale l = new Locale("en");
	        	
	        	String tName =context.getTable();
	        	String month = String.format(l,"%tb", headDate);
	        	String year = String.format(l,"%ty", headDate);
	        	
	        	String title ="EPO "+sheetName+" Achievement (B2C)-"+month+" 20"+year;
	        	
	        	
	        	if (tName.contains("-MOCO")&&!tName.contains("Edition")&&!tName.contains("MOKIDS")) {
	        		title="MO&Co. Online Daily Achievement(B2C) -"+month+" 20"+year;
				}else if (tName.contains("-Edition")&&!tName.contains("MOCO")&&!tName.contains("MOKIDS")) {
	        		title="Edition Online Daily Achievement(B2C) -"+month+" 20"+year;
				}else if (tName.contains("-MOKIDS")&&!tName.contains("MOCO")&&!tName.contains("Edition")) {
	        		title="little MO&Co. Online Daily Achievement(B2C) -"+month+" 20"+year;
				}else if (tName.contains("MOCO")&&tName.contains("MOKIDS")&&!tName.contains("Edition")) {
	        		title="MO&Co.、little MO&Co. Online Daily Achievement(B2C) -"+month+" 20"+year;
				}else if (tName.contains("MOCO")&&tName.contains("Edition")&&!tName.contains("MOKIDS")) {
	        		title="MO&Co.、Edition Online Daily Achievement(B2C) -"+month+" 20"+year;
				}else if (tName.contains("MOCO")&&tName.contains("Edition")&&tName.contains("MOKIDS")) {
	        		title="MO&Co.、Edition、little MO&Co. Online Daily Achievement(B2C) -"+month+" 20"+year;
				}else if (tName.contains("Edition")&&tName.contains("MOKIDS")&&!tName.contains("MOCO")) {
	        		title="Edition、little MO&Co. Online Daily Achievement(B2C) -"+month+" 20"+year;
				}else  {
	        		title="EPO E-COMMERCE Daily Achievement(B2C) -"+month+" 20"+year;
				}
//	        	HSSFRichTextString richString = new HSSFRichTextString(title );  
//	        	//对前半部分设置黑色字体  
//	        	richString.applyFont( 0, 39, blackFont );  
//	        	//对时间部分设置红色字体
//	        	richString.applyFont( 39, richString.length(), redFont );  
	        	
	        	cell1.setCellValue(title);
//	        	cell1.setCellStyle(blodFontStyle);
	        	 CellRangeAddress cra =new CellRangeAddress(0, 0, 0, 19); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra); 
	        	 cell1.setCellStyle(blodFont11Style);
	        	 
	        	 CellRangeAddress cra10=new CellRangeAddress(1, 1, 0, 19); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra10); 
	        	 
	        	 row1 = context.getBoardSheet().createRow(1);
	         	 cell1=row1.createCell(0);
	         	cell1.setCellValue("");
	        	 
	        	 
	        	 row1=context.getBoardSheet().createRow(2);
	        	 cell1=row1.createCell(0);
	        	 cell1.setCellValue("WK");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 CellRangeAddress cra1 =new CellRangeAddress(2, 3, 0, 0); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra1); 
	        	 
	        	 
	        	 cell1=row1.createCell(1);
	        	 cell1.setCellValue("Date");
	        	 CellRangeAddress cra2 =new CellRangeAddress(2, 3, 1, 1); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra2); 
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 
	        	 cell1=row1.createCell(2);
	        	 cell1.setCellValue("Cashback");
	        	 CellRangeAddress cra3 =new CellRangeAddress(2, 2, 2, 4); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra3); 
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 cell1=row1.createCell(3);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 

	        	 cell1=row1.createCell(4);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(5);
	        	 cell1.setCellValue("M1");
	        	 CellRangeAddress cra4 =new CellRangeAddress(2, 2, 5, 7); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra4); 
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(6);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(7);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(8);
	        	 cell1.setCellValue("Front Sales (Inc. O2O)");
	        	 CellRangeAddress cra5 =new CellRangeAddress(2, 2, 8, 10); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra5); 
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(9);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(10);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(11);
	        	 cell1.setCellValue("Return (Inc. O2O)");
	        	 CellRangeAddress cra6=new CellRangeAddress(2, 2, 11, 14); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra6); 
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(12);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(13);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(14);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(15);
	        	 cell1.setCellValue("O2O");
	        	 CellRangeAddress cra7 =new CellRangeAddress(2, 2, 15, 19); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra7); 
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(16);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(17);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(18);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(19);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 row1=context.getBoardSheet().createRow(3);
	        	 cell1=row1.createCell(0);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(1);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(2);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(grey10BlodBottomStyle);
	        	 
	        	 cell1=row1.createCell(3);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(grey10BlodBottomStyle);

	        	 cell1=row1.createCell(4);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(greyBlodBottomRightStyle);

	        	 cell1=row1.createCell(5);
	        	 cell1.setCellValue("DR%");
	        	 cell1.setCellStyle(grey10BlodBottomStyle);

	        	 cell1=row1.createCell(6);
	        	 cell1.setCellValue("M1");
	        	 cell1.setCellStyle(grey10BlodBottomStyle);

	        	 cell1=row1.createCell(7);
	        	 cell1.setCellValue("M1%");
	        	 cell1.setCellStyle(greyBlodBottomRightStyle);
	        	 
	        	 cell1=row1.createCell(8);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(grey10BlodBottomStyle);

	        	 cell1=row1.createCell(9);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(grey10BlodBottomStyle);

	        	 cell1=row1.createCell(10);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(greyBlodBottomRightStyle);

	        	 cell1=row1.createCell(11);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(grey10BlodBottomStyle);

	        	 cell1=row1.createCell(12);
	        	 cell1.setCellValue("Target%");
	        	 cell1.setCellStyle(grey10BlodBottomStyle);

	        	 cell1=row1.createCell(13);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(grey10BlodBottomStyle);

	        	 cell1=row1.createCell(14);
	        	 cell1.setCellValue("Actual%");
	        	 cell1.setCellStyle(greyBlodBottomRightStyle);

	        	 cell1=row1.createCell(15);
	        	 cell1.setCellValue("Delivery");
	        	 cell1.setCellStyle(grey10BlodBottomStyle);

	        	 cell1=row1.createCell(16);
	        	 cell1.setCellValue("Deliv%");
	        	 cell1.setCellStyle(grey10BlodBottomStyle);

	        	 cell1=row1.createCell(17);
	        	 cell1.setCellValue("Return");
	        	 cell1.setCellStyle(grey10BlodBottomStyle);

	        	 cell1=row1.createCell(18);
	        	 cell1.setCellValue("Retur%");
	        	 cell1.setCellStyle(grey10BlodBottomStyle);

	        	 cell1=row1.createCell(19);
	        	 cell1.setCellValue("O2O%");
	        	 cell1.setCellStyle(greyBlodBottomRightStyle);
	        	 
	        	 context.getBoardSheet().setColumnWidth(0, 4* 256+184);
	        	 context.getBoardSheet().setColumnWidth(1, 4* 256+184);
	        	 context.getBoardSheet().setColumnWidth(2, 8* 256+184);
	        	 context.getBoardSheet().setColumnWidth(3, 8* 256+184);
	        	 context.getBoardSheet().setColumnWidth(4, 4* 256+184);
	        	 context.getBoardSheet().setColumnWidth(5, 4* 256+184);
	        	 context.getBoardSheet().setColumnWidth(6, 8* 256+184);
	        	 context.getBoardSheet().setColumnWidth(7, 4* 256+184);
	        	 context.getBoardSheet().setColumnWidth(8, 8* 256+184);
	        	 context.getBoardSheet().setColumnWidth(9, 8* 256+184);
	        	 context.getBoardSheet().setColumnWidth(10, 4* 256+184);
	        	 context.getBoardSheet().setColumnWidth(11, 8* 256+184);
	        	 context.getBoardSheet().setColumnWidth(12, 6* 256+184);
	        	 context.getBoardSheet().setColumnWidth(13, 8* 256+184);
	        	 context.getBoardSheet().setColumnWidth(14, 6* 256+184);
	        	 context.getBoardSheet().setColumnWidth(15, 8* 256+184);
	        	 context.getBoardSheet().setColumnWidth(16, 6* 256+184);
	        	 context.getBoardSheet().setColumnWidth(17, 8* 256+184);
	        	 context.getBoardSheet().setColumnWidth(18, 6* 256+184);
	        	 context.getBoardSheet().setColumnWidth(19, 6* 256+184);
	        	 
	        	 
	        	 
	        	 
	        	 
		}else if (context.getTable().contains("EPO E-COMMERCE Daily Achievement")&&!context.getTable().contains("(B2C)")) {
			 Row row1 = context.getBoardSheet().createRow(0);
			 row1.setHeightInPoints((float) 16.5);
	        	Cell cell1=row1.createCell(0);
	        	String sheetName =context.getWb().getSheetName(context.getSheetIndex());
//	        	cell1.setCellValue("EPO");
	        	Locale l = new Locale("en");
//	        	SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM");
//	        	Date headDate =null;
//	        	try {
//	        		headDate=sdf.parse(context.getDate());
//				} catch (ParseException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
	        	String tName =context.getTable();
	        	String month = String.format(l,"%tb", headDate);
	        	String year = String.format(l,"%ty", headDate);
	        	
	        	String title ="EPO "+sheetName+" Achievement-"+month+" 20"+year;
	        	
	        	if (tName.contains("-MOCO")&&!tName.contains("Edition")&&!tName.contains("MOKIDS")) {
	        		title="MO&Co. Online Daily Achievement -"+month+" 20"+year;
				}else if (tName.contains("-Edition")&&!tName.contains("MOCO")&&!tName.contains("MOKIDS")) {
	        		title="Edition Online Daily Achievement -"+month+" 20"+year;
				}else if (tName.contains("-MOKIDS")&&!tName.contains("MOCO")&&!tName.contains("Edition")) {
	        		title="little MO&Co. Online Daily Achievement -"+month+" 20"+year;
				}else if (tName.contains("MOCO")&&tName.contains("MOKIDS")&&!tName.contains("Edition")) {
	        		title="MO&Co.、little MO&Co. Online Daily Achievement -"+month+" 20"+year;
				}else if (tName.contains("MOCO")&&tName.contains("Edition")&&!tName.contains("MOKIDS")) {
	        		title="MO&Co.、Edition Online Daily Achievement -"+month+" 20"+year;
				}else if (tName.contains("MOCO")&&tName.contains("Edition")&&tName.contains("MOKIDS")) {
	        		title="MO&Co.、Edition、little MO&Co. Online Daily Achievement -"+month+" 20"+year;
				}else if (tName.contains("Edition")&&tName.contains("MOKIDS")&&!tName.contains("MOCO")) {
	        		title="Edition、little MO&Co. Online Daily Achievement -"+month+" 20"+year;
				}else  {
	        		title="EPO E-COMMERCE Daily Achievement -"+month+" 20"+year;
				}
//	        	HSSFRichTextString richString = new HSSFRichTextString(title );  
//	        	//对前半部分设置黑色字体  
//	        	richString.applyFont( 0, 39, blackFont );  
//	        	//对时间部分设置红色字体
//	        	richString.applyFont( 39, richString.length(), redFont );  
	        	
	        	cell1.setCellValue(title);
//	        	cell1.setCellStyle(blodFontStyle);
	        	 CellRangeAddress cra =new CellRangeAddress(0, 0, 0, 23); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra); 
	        	 cell1.setCellStyle(blodFont12Style);
	        	 
	        	 CellRangeAddress cra10=new CellRangeAddress(1, 1, 0, 23); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra10); 
	        	 
	        	 
	        	 row1 = context.getBoardSheet().createRow(1);
	        	 row1.setHeightInPoints((float) 13.5);
	         	 cell1=row1.createCell(0);
	         	cell1.setCellValue("");
	         	
	         	
	        	 row1=context.getBoardSheet().createRow(2);
	        	 row1.setHeightInPoints((float) 15.25);
	        	 cell1=row1.createCell(0);
	        	 cell1.setCellValue("WK");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 CellRangeAddress cra1 =new CellRangeAddress(2, 3, 0, 0); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra1); 
	        	 
	        	 
	        	 cell1=row1.createCell(1);
	        	 cell1.setCellValue("Date");
	        	 CellRangeAddress cra2 =new CellRangeAddress(2, 3, 1, 1); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra2); 
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 
	        	 cell1=row1.createCell(2);
	        	 cell1.setCellValue("Cashback");
	        	 CellRangeAddress cra3 =new CellRangeAddress(2, 2, 2, 4); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra3); 
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 cell1=row1.createCell(3);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 

	        	 cell1=row1.createCell(4);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(5);
	        	 cell1.setCellValue("M1");
	        	 CellRangeAddress cra4 =new CellRangeAddress(2, 2, 5, 7); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra4); 
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(6);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(7);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(8);
	        	 cell1.setCellValue("Front Sales (Inc. O2O)");
	        	 CellRangeAddress cra5 =new CellRangeAddress(2, 2, 8, 10); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra5); 
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(9);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(10);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(11);
	        	 cell1.setCellValue("B2C Return (Inc. O2O)");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 CellRangeAddress cra6=new CellRangeAddress(2, 2, 11, 14); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra6); 



	        	 cell1=row1.createCell(12);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(13);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(14);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(15);
	        	 cell1.setCellValue("B2B Return");
	        	 CellRangeAddress cra7 =new CellRangeAddress(2, 2, 15, 18); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra7); 
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(16);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(17);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(18);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(19);
	        	 cell1.setCellValue("O2O");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 CellRangeAddress cra8 =new CellRangeAddress(2, 2, 19, 23); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra8);
	        	 
	        	 cell1=row1.createCell(20);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(21);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(22);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 cell1=row1.createCell(23);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 row1=context.getBoardSheet().createRow(3);
	        	 row1.setHeightInPoints((float) 15.25);
	        	 cell1=row1.createCell(0);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(1);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(2);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(grey10BlodBottomStyle);
	        	 
	        	 cell1=row1.createCell(3);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(grey10BlodBottomStyle);

	        	 cell1=row1.createCell(4);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(greyBlodBottomRightStyle);
	        	 
	        	

	        	 cell1=row1.createCell(5);
	        	 cell1.setCellValue("DR%");
	        	 cell1.setCellStyle(grey10BlodBottomStyle);

	        	 cell1=row1.createCell(6);
	        	 cell1.setCellValue("M1");
	        	 cell1.setCellStyle(grey10BlodBottomStyle);

	        	 cell1=row1.createCell(7);
	        	 cell1.setCellValue("M1%");
	        	 cell1.setCellStyle(greyBlodBottomRightStyle);
	        	 
	        	 cell1=row1.createCell(8);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(grey10BlodBottomStyle);

	        	 cell1=row1.createCell(9);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(grey10BlodBottomStyle);

	        	 cell1=row1.createCell(10);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(greyBlodBottomRightStyle);

	        	 cell1=row1.createCell(11);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(grey10BlodBottomStyle);

	        	 cell1=row1.createCell(12);
	        	 cell1.setCellValue("Tar%");
	        	 cell1.setCellStyle(grey10BlodBottomStyle);

	        	 cell1=row1.createCell(13);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(grey10BlodBottomStyle);

	        	 cell1=row1.createCell(14);
	        	 cell1.setCellValue("Act%");
	        	 cell1.setCellStyle(greyBlodBottomRightStyle);

	        	 cell1=row1.createCell(15);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(grey10BlodBottomStyle);

	        	 cell1=row1.createCell(16);
	        	 cell1.setCellValue("Tar%");
	        	 cell1.setCellStyle(grey10BlodBottomStyle);

	        	 cell1=row1.createCell(17);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(grey10BlodBottomStyle);

	        	 cell1=row1.createCell(18);
	        	 cell1.setCellValue("Act%");
	        	 cell1.setCellStyle(greyBlodBottomRightStyle);

	        	 cell1=row1.createCell(19);
	        	 cell1.setCellValue("Delivery");
	        	 cell1.setCellStyle(grey10BlodBottomStyle);
	        	 
	        	 cell1=row1.createCell(20);
	        	 cell1.setCellValue("Del%");
	        	 cell1.setCellStyle(grey10BlodBottomStyle);

	        	 cell1=row1.createCell(21);
	        	 cell1.setCellValue("Return");
	        	 cell1.setCellStyle(grey10BlodBottomStyle);

	        	 cell1=row1.createCell(22);
	        	 cell1.setCellValue("Return%");
	        	 cell1.setCellStyle(grey10BlodBottomStyle);

	        	 cell1=row1.createCell(23);
	        	 cell1.setCellValue("O2O%");
	        	 cell1.setCellStyle(greyBlodBottomRightStyle);
	        	 
	        	 
	        	 context.getBoardSheet().setColumnWidth(0, 4* 256+184);
	        	 context.getBoardSheet().setColumnWidth(1, 4* 256+184);
	        	 context.getBoardSheet().setColumnWidth(2, 10* 256+184);
	        	 context.getBoardSheet().setColumnWidth(3, 10* 256+184);
	        	 context.getBoardSheet().setColumnWidth(4, 6* 256+184);
	        	 context.getBoardSheet().setColumnWidth(5, 6* 256+184);
	        	 context.getBoardSheet().setColumnWidth(6, 10* 256+184);
	        	 context.getBoardSheet().setColumnWidth(7, 6* 256+184);
	        	 context.getBoardSheet().setColumnWidth(8, 10* 256+184);
	        	 context.getBoardSheet().setColumnWidth(9, 10* 256+184);
	        	 context.getBoardSheet().setColumnWidth(10, 6* 256+184);
	        	 context.getBoardSheet().setColumnWidth(11, 10* 256+184);
	        	 context.getBoardSheet().setColumnWidth(12, 6* 256+184);
	        	 context.getBoardSheet().setColumnWidth(13, 10* 256+184);
	        	 context.getBoardSheet().setColumnWidth(14, 6* 256+184);
	        	 context.getBoardSheet().setColumnWidth(15, 10* 256+184);
	        	 context.getBoardSheet().setColumnWidth(16, 6* 256+184);
	        	 context.getBoardSheet().setColumnWidth(17, 10* 256+184);
	        	 context.getBoardSheet().setColumnWidth(18, 6* 256+184);
	        	 context.getBoardSheet().setColumnWidth(19, 10* 256+184);
	        	 context.getBoardSheet().setColumnWidth(20, 6* 256+184);
	        	 context.getBoardSheet().setColumnWidth(21, 10* 256+184);
	        	 context.getBoardSheet().setColumnWidth(22, 6* 256+184);
	        	 context.getBoardSheet().setColumnWidth(23, 5* 256+184);
	        	 
	        	 
	        	 
	        	 
	        	 
		}
		else if (context.getTable().contains("MOCO-Online Daily Sales by Platform-B2C")) {
			 Row row1 = context.getBoardSheet().createRow(0);
	        	Cell cell1=row1.createCell(0);
	        	String sheetName =context.getWb().getSheetName(context.getSheetIndex());
//	        	cell1.setCellValue("EPO");
	        	Locale l = new Locale("en");
//	        	SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM");
//	        	Date headDate =null;
//	        	try {
//	        		headDate=sdf.parse(context.getDate());
//				} catch (ParseException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
	        	String tName =context.getTable();
	        	String month = String.format(l,"%tb", headDate);
	        	String year = String.format(l,"%ty", headDate);
	        	
	        	String title ="EPO "+sheetName+" Achievement (B2C)-"+month+" 20"+year;
	        	
	        	
	        	if (tName.contains("-MOCO")&&!tName.contains("Edition")&&!tName.contains("MOKIDS")) {
	        		title="MO&Co. Online Daily Sales by Platform (B2C ) -"+month+" 20"+year;
				}else if (tName.contains("-Edition")&&!tName.contains("MOCO")&&!tName.contains("MOKIDS")) {
	        		title="Edition Online Daily Sales by Platform (B2C ) -"+month+" 20"+year;
				}else if (tName.contains("-MOKIDS")&&!tName.contains("MOCO")&&!tName.contains("Edition")) {
	        		title="little MO&Co. Online Daily Sales by Platform (B2C ) -"+month+" 20"+year;
				}else if (tName.contains("MOCO")&&tName.contains("MOKIDS")&&!tName.contains("Edition")) {
	        		title="MO&Co.、little MO&Co. Online Daily Sales by Platform (B2C ) -"+month+" 20"+year;
				}else if (tName.contains("MOCO")&&tName.contains("Edition")&&!tName.contains("MOKIDS")) {
	        		title="MO&Co.、Edition Online Daily Sales by Platform (B2C ) -"+month+" 20"+year;
				}else if (tName.contains("MOCO")&&tName.contains("Edition")&&tName.contains("MOKIDS")) {
	        		title="MO&Co.、Edition、little MO&Co. Online Daily Sales by Platform (B2C ) -"+month+" 20"+year;
				}else if (tName.contains("Edition")&&tName.contains("MOKIDS")&&!tName.contains("MOCO")) {
	        		title="Edition、little MO&Co. Online Daily Sales by Platform (B2C ) -"+month+" 20"+year;
				}else  {
	        		title="MO&Co. Online Daily Sales by Platform (B2C ) -"+month+" 20"+year;
				}
//	        	HSSFRichTextString richString = new HSSFRichTextString(title );  
//	        	//对前半部分设置黑色字体  
//	        	richString.applyFont( 0, 39, blackFont );  
//	        	//对时间部分设置红色字体
//	        	richString.applyFont( 39, richString.length(), redFont );  
	        	
	        	cell1.setCellValue(title);
//	        	cell1.setCellStyle(blodFontStyle);
	        	 CellRangeAddress cra =new CellRangeAddress(0, 0, 0, 100); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra); 
	        	 cell1.setCellStyle(blodFont11Style);
	        	 
	        	 CellRangeAddress cra10=new CellRangeAddress(1, 1, 0, 100); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra10); 
	        	 
	        	 row1 = context.getBoardSheet().createRow(1);
	         	 cell1=row1.createCell(0);
	         	cell1.setCellValue("");
	        	 
	        	 
	        	 row1=context.getBoardSheet().createRow(2);
	        	 cell1=row1.createCell(0);
	        	 cell1.setCellValue("Date");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 CellRangeAddress cra1 =new CellRangeAddress(2, 4, 0, 0); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra1); 
	        	 
	        	 
	        	 cell1=row1.createCell(1);
	        	 cell1.setCellValue("天猫");
	        	
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 
	        	 cell1=row1.createCell(2);
	        	 cell1.setCellValue(""); 
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 cell1=row1.createCell(3);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 

	        	 cell1=row1.createCell(4);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(5);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(6);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 CellRangeAddress cra2 =new CellRangeAddress(2, 2, 1, 14); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra2); 

	        	 

	        	 cell1=row1.createCell(7);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(8);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(9);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(10);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(11);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(12);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 CellRangeAddress cra4 =new CellRangeAddress(2, 2, 15, 26); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra4); 

	        	 
	        	 cell1=row1.createCell(13);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(14);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(15);
	        	 cell1.setCellValue("京东");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(16);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(17);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(18);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 CellRangeAddress cra3 =new CellRangeAddress(2, 2, 27, 38); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra3);

	        	 
	        	 cell1=row1.createCell(19);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 cell1=row1.createCell(20);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(21);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(22);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(23);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 cell1=row1.createCell(24);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 CellRangeAddress cra5 =new CellRangeAddress(2, 2, 39, 50); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra5); 
	        	 
	        	 cell1=row1.createCell(25);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(26);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(27);
	        	 cell1.setCellValue("小红书");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(28);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 cell1=row1.createCell(29);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(30);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 CellRangeAddress cra7 =new CellRangeAddress(2, 2, 51, 62); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra7); 


	        	 cell1=row1.createCell(31);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(32);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 cell1=row1.createCell(33);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(34);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(35);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(36);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 CellRangeAddress cra8 =new CellRangeAddress(2, 2, 63, 74); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra8); 
	        	 
	        	 cell1=row1.createCell(37);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 cell1=row1.createCell(38);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(39);
	        	 cell1.setCellValue("考拉");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(40);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(41);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 cell1=row1.createCell(42);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 CellRangeAddress cra9 =new CellRangeAddress(2, 2, 75, 86); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra9); 


	        	 cell1=row1.createCell(43);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(44);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(45);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 cell1=row1.createCell(46);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(47);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(48);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 CellRangeAddress cra11 =new CellRangeAddress(2, 2, 87, 100); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra11); 
	        	 
	        	 cell1=row1.createCell(49);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	
	        	 
	        	 cell1=row1.createCell(50);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 cell1=row1.createCell(51);
	        	 cell1.setCellValue("寺库");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(52);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(53);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(54);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
//	        	 CellRangeAddress cra12 =new CellRangeAddress(2, 2, 89, 100); // 起始行, 终止行, 起始列, 终止列  
//	        	 context.getBoardSheet().addMergedRegion(cra12); 
	        	 cell1=row1.createCell(55);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 

	        	
	        	 
	        	 cell1=row1.createCell(56);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(57);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(58);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(59);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 cell1=row1.createCell(60);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
//	        	 CellRangeAddress cra13 =new CellRangeAddress(2, 2, 55, 60); // 起始行, 终止行, 起始列, 终止列  
//	        	 context.getBoardSheet().addMergedRegion(cra13); 
	        	 
	        	 cell1=row1.createCell(61);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	


	        	 cell1=row1.createCell(62);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(63);
	        	 cell1.setCellValue("尚品");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(64);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 cell1=row1.createCell(65);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 cell1=row1.createCell(66);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
//
//	        	 CellRangeAddress cra14 =new CellRangeAddress(2, 2, 61, 66); // 起始行, 终止行, 起始列, 终止列  
//	        	 context.getBoardSheet().addMergedRegion(cra14); 
	        	 
	        	 cell1=row1.createCell(67);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 


	        	 cell1=row1.createCell(68);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(69);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 cell1=row1.createCell(70);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 cell1=row1.createCell(71);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(72);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


//	        	 CellRangeAddress cra15 =new CellRangeAddress(2, 2, 67, 72); // 起始行, 终止行, 起始列, 终止列  
//	        	 context.getBoardSheet().addMergedRegion(cra15); 
	        	 
	        	 cell1=row1.createCell(73);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(74);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 cell1=row1.createCell(75);
	        	 cell1.setCellValue("淘宝分销");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(76);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(77);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

//	        	 CellRangeAddress cra16 =new CellRangeAddress(2, 2, 73, 78); // 起始行, 终止行, 起始列, 终止列  
//	        	 context.getBoardSheet().addMergedRegion(cra16); 
	        	 
	        	 cell1=row1.createCell(78);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	
	        	 
	        	 cell1=row1.createCell(79);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 cell1=row1.createCell(80);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(81);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(82);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(83);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
//	        	 CellRangeAddress cra17 =new CellRangeAddress(2, 2, 79, 84); // 起始行, 终止行, 起始列, 终止列  
//	        	 context.getBoardSheet().addMergedRegion(cra17); 
	        	 cell1=row1.createCell(84);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 

	        	
	        	 
	        	 cell1=row1.createCell(85);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(86);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(87);
	        	 cell1.setCellValue("官网");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(88);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 cell1=row1.createCell(89);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
//	        	 CellRangeAddress cra18 =new CellRangeAddress(2, 2, 85, 90); // 起始行, 终止行, 起始列, 终止列  
//	        	 context.getBoardSheet().addMergedRegion(cra18); 
	        	 
	        	 cell1=row1.createCell(90);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	


	        	 cell1=row1.createCell(91);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(92);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(93);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 cell1=row1.createCell(94);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 cell1=row1.createCell(95);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

//	        	 CellRangeAddress cra19 =new CellRangeAddress(2, 2, 91, 96); // 起始行, 终止行, 起始列, 终止列  
//	        	 context.getBoardSheet().addMergedRegion(cra19); 
	        	 
	        	 cell1=row1.createCell(96);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 


	        	 cell1=row1.createCell(97);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(98);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 cell1=row1.createCell(99);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 cell1=row1.createCell(100);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
//	        	 CellRangeAddress cra20 =new CellRangeAddress(2, 2, 97, 100); // 起始行, 终止行, 起始列, 终止列  
//	        	 context.getBoardSheet().addMergedRegion(cra20); 


	        	

	        	 
	        	 row1=context.getBoardSheet().createRow(3);
	        	 
	        	 cell1=row1.createCell(0);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(1);
	        	 cell1.setCellValue("Cashback");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 
	        	 cell1=row1.createCell(2);
	        	 cell1.setCellValue(""); 
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(3);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 CellRangeAddress cra21 =new CellRangeAddress(3, 3, 1, 3); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra21); 

	        	 cell1=row1.createCell(4);
	        	 cell1.setCellValue("Front Sales");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 
	        	 cell1=row1.createCell(5);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(6);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra22 =new CellRangeAddress(3, 3, 4, 6); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra22); 

	        	 

	        	 cell1=row1.createCell(7);
	        	 cell1.setCellValue("M1");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 
	        	 cell1=row1.createCell(8);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(9);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 CellRangeAddress cra23 =new CellRangeAddress(3, 3, 7, 9); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra23); 

	        	 cell1=row1.createCell(10);
	        	 cell1.setCellValue("Return");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 
	        	 cell1=row1.createCell(11);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(12);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra24 =new CellRangeAddress(3, 3, 10, 12); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra24); 

	        	 
	        	 cell1=row1.createCell(13);
	        	 cell1.setCellValue("O2O");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(14);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 CellRangeAddress cra25 =new CellRangeAddress(3, 3, 13, 14); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra25); 
	        	 
	        	 cell1=row1.createCell(15);
	        	 cell1.setCellValue("Cashback");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 

	        	 cell1=row1.createCell(16);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(17);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra26 =new CellRangeAddress(3, 3, 15, 17); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra26);

	        	 cell1=row1.createCell(18);
	        	 cell1.setCellValue("Front Sales");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(19);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(20);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra27 =new CellRangeAddress(3, 3, 18, 20); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra27);

	        	 cell1=row1.createCell(21);
	        	 cell1.setCellValue("M1");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	

	        	 cell1=row1.createCell(22);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 
	        	 cell1=row1.createCell(23);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra28 =new CellRangeAddress(3, 3, 21, 23); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra28); 
	        	 
	        	 
	        	 cell1=row1.createCell(24);
	        	 cell1.setCellValue("Return");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	
	        	 
	        	 cell1=row1.createCell(25);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(26);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra29 =new CellRangeAddress(3, 3, 24, 26); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra29);
	        	 

	        	 cell1=row1.createCell(27);
	        	 cell1.setCellValue("Cashback");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	
	        	 cell1=row1.createCell(28);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(29);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra30 =new CellRangeAddress(3, 3, 27, 29); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra30); 

	        	 cell1=row1.createCell(30);
	        	 cell1.setCellValue("Front Sales");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(31);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 
	        	 cell1=row1.createCell(32);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra31 =new CellRangeAddress(3, 3, 30, 32); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra31);
	        	 
	        	 cell1=row1.createCell(33);
	        	 cell1.setCellValue("M1");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	

	        	 cell1=row1.createCell(34);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(35);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra32 =new CellRangeAddress(3, 3, 33, 35); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra32); 
	        	 
	        	 cell1=row1.createCell(36);
	        	 cell1.setCellValue("Return");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	
	        	 
	        	 cell1=row1.createCell(37);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(38);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra33 =new CellRangeAddress(3, 3, 36, 38); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra33);

	        	 cell1=row1.createCell(39);
	        	 cell1.setCellValue("Cashback");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	
	        	 cell1=row1.createCell(40);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 
	        	 cell1=row1.createCell(41);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra34 =new CellRangeAddress(3, 3, 39, 41); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra34); 
	        	 
	        	 cell1=row1.createCell(42);
	        	 cell1.setCellValue("Front Sales");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(43);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(44);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra35 =new CellRangeAddress(3, 3,42 ,44 ); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra35);
	        	 
	        	 cell1=row1.createCell(45);
	        	 cell1.setCellValue("M1");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	
	        	 
	        	 cell1=row1.createCell(46);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(47);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra36 =new CellRangeAddress(3, 3, 45, 47); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra36); 

	        	 cell1=row1.createCell(48);
	        	 cell1.setCellValue("Return");
	        	 cell1.setCellStyle(titleBoldStyle);

	        
	        	 
	        	 cell1=row1.createCell(49);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	
	        	 
	        	 cell1=row1.createCell(50);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 CellRangeAddress cra37 =new CellRangeAddress(3, 3,48 ,50 ); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra37);
	        	 
	        	 cell1=row1.createCell(51);
	        	 cell1.setCellValue("Cashback");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	

	        	 cell1=row1.createCell(52);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(53);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra38 =new CellRangeAddress(3, 3, 51, 53); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra38); 
	        	 
	        	 cell1=row1.createCell(54);
	        	 cell1.setCellValue("Front Sales");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        
	        	 cell1=row1.createCell(55);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 

	        	
	        	 
	        	 cell1=row1.createCell(56);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra39 =new CellRangeAddress(3, 3,54 , 56); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra39);

	        	 cell1=row1.createCell(57);
	        	 cell1.setCellValue("M1");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	

	        	 cell1=row1.createCell(58);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 
	        	 cell1=row1.createCell(59);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra40 =new CellRangeAddress(3, 3, 57, 59); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra40); 
	        	 
	        	 cell1=row1.createCell(60);
	        	 cell1.setCellValue("Return");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	
	        	 
	        	 cell1=row1.createCell(61);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(62);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 CellRangeAddress cra41 =new CellRangeAddress(3, 3, 60,62 ); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra41);

	        	 cell1=row1.createCell(63);
	        	 cell1.setCellValue("Cashback");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 
	        	 cell1=row1.createCell(64);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(65);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra42 =new CellRangeAddress(3, 3, 63, 65); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra42); 
	        	 
	        	 cell1=row1.createCell(66);
	        	 cell1.setCellValue("Front Sales");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(67);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 

	        	 cell1=row1.createCell(68);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra43 =new CellRangeAddress(3, 3, 66, 68); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra43); 
	        	 
	        	 cell1=row1.createCell(69);
	        	 cell1.setCellValue("M1");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        
	        	 
	        	 cell1=row1.createCell(70);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(71);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 CellRangeAddress cra45 =new CellRangeAddress(3, 3, 69,71 ); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra45);

	        	 cell1=row1.createCell(72);
	        	 cell1.setCellValue("Retuen");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(73);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	


	        	 cell1=row1.createCell(74);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 CellRangeAddress cra46 =new CellRangeAddress(3, 3, 72, 74); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra46); 

	        	 cell1=row1.createCell(75);
	        	 cell1.setCellValue("Cashback");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 
	        	 cell1=row1.createCell(76);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(77);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra47 =new CellRangeAddress(3, 3, 75, 77); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra47); 
	        	 
	        	 cell1=row1.createCell(78);
	        	 cell1.setCellValue("Front Sales");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(79);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 



	        	 cell1=row1.createCell(80);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra48 =new CellRangeAddress(3, 3, 78, 80); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra48); 
	        	 
	        	 cell1=row1.createCell(81);
	        	 cell1.setCellValue("M1");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	
	        	 cell1=row1.createCell(82);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(83);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 CellRangeAddress cra49 =new CellRangeAddress(3, 3, 81,83 ); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra49);

	        	 cell1=row1.createCell(84);
	        	 cell1.setCellValue("Return");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 
	        	 
	        	 cell1=row1.createCell(85);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	


	        	 cell1=row1.createCell(86);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 CellRangeAddress cra50 =new CellRangeAddress(3, 3, 84, 86); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra50); 

	        	 cell1=row1.createCell(87);
	        	 cell1.setCellValue("Cashback");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 
	        	 cell1=row1.createCell(88);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(89);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 CellRangeAddress cra51 =new CellRangeAddress(3, 3, 87, 89); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra51); 
	        	 
	        	 cell1=row1.createCell(90);
	        	 cell1.setCellValue("Front Sales");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(91);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 


	        	 cell1=row1.createCell(92);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 CellRangeAddress cra52 =new CellRangeAddress(3, 3, 90, 92); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra52);

	        	 
	        	 cell1=row1.createCell(93);
	        	 cell1.setCellValue("M1");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 
	        	 cell1=row1.createCell(94);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(95);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra53 =new CellRangeAddress(3, 3, 93, 95); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra53); 

	        	 cell1=row1.createCell(96);
	        	 cell1.setCellValue("Return");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(97);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        
	        	 
	        	 cell1=row1.createCell(98);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra54 =new CellRangeAddress(3, 3, 96, 98); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra54); 
	        	 
	        	 cell1=row1.createCell(99);
	        	 cell1.setCellValue("O2O");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(100);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	         	 CellRangeAddress cra55 =new CellRangeAddress(3, 3, 99, 100); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra55); 
	        	 
	        	 
	        	 row1=context.getBoardSheet().createRow(4);

	        	 cell1=row1.createCell(0);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(1);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(2);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(3);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(4);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(5);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(6);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	

	        	 cell1=row1.createCell(7);
	        	 cell1.setCellValue("DR%");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(8);
	        	 cell1.setCellValue("M1");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(9);
	        	 cell1.setCellValue("M1%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(10);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(11);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(12);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(13);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(14);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	

	        	 cell1=row1.createCell(15);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(16);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(17);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(18);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(19);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(20);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(21);
	        	 cell1.setCellValue("DR%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(22);
	        	 cell1.setCellValue("M1");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(23);
	        	 cell1.setCellValue("M1%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(24);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(25);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(26);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(27);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(28);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(29);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	
	        	 cell1=row1.createCell(30);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(31);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(32);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	
	        	 cell1=row1.createCell(33);
	        	 cell1.setCellValue("DR%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(34);
	        	 cell1.setCellValue("M1");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 cell1=row1.createCell(35);
	        	 cell1.setCellValue("M1%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(36);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(37);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(38);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(39);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(40);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(41);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(42);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(43);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(44);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	
	        	 cell1=row1.createCell(45);
	        	 cell1.setCellValue("DR%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(46);
	        	 cell1.setCellValue("M1");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(47);
	        	 cell1.setCellValue("M1%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(48);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(49);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(50);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(51);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(52);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(53);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(54);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(55);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(56);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	
	        	 cell1=row1.createCell(57);
	        	 cell1.setCellValue("DR%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(58);
	        	 cell1.setCellValue("M1");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(59);
	        	 cell1.setCellValue("M1%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(60);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(61);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(62);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(63);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(64);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(65);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(66);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(67);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(68);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	
	        	 cell1=row1.createCell(69);
	        	 cell1.setCellValue("DR%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(70);
	        	 cell1.setCellValue("M1");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(71);
	        	 cell1.setCellValue("M1%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(72);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(73);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(74);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(75);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(76);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(77);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(78);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(79);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(80);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	
	        	 cell1=row1.createCell(81);
	        	 cell1.setCellValue("DR%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(82);
	        	 cell1.setCellValue("M1");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(83);
	        	 cell1.setCellValue("M1%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(84);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(85);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(86);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(87);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(88);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(89);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(90);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(91);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(92);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	//DR%	M1	M1%
	        	 cell1=row1.createCell(93);
	        	 cell1.setCellValue("DR%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(94);
	        	 cell1.setCellValue("M1");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	
	        	 cell1=row1.createCell(95);
	        	 cell1.setCellValue("M1%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(96);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(97);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(98);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(99);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(100);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	
	        	 
	        	 
	        	 
	        	 
	        	 
	        	 
		}else if (context.getTable().contains("Edition-Online Daily Sales by Platform-B2C")) {
			 Row row1 = context.getBoardSheet().createRow(0);
	        	Cell cell1=row1.createCell(0);
	        	String sheetName =context.getWb().getSheetName(context.getSheetIndex());
//	        	cell1.setCellValue("EPO");
	        	Locale l = new Locale("en");
//	        	SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM");
//	        	Date headDate =null;
//	        	try {
//	        		headDate=sdf.parse(context.getDate());
//				} catch (ParseException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
	        	String tName =context.getTable();
	        	String month = String.format(l,"%tb", headDate);
	        	String year = String.format(l,"%ty", headDate);
	        	
	        	String title ="EPO "+sheetName+" Achievement (B2C)-"+month+" 20"+year;
	        	
	        	
	        	if (tName.contains("-MOCO")&&!tName.contains("Edition")&&!tName.contains("MOKIDS")) {
	        		title="MO&Co. Online Daily Sales by Platform (B2C ) -"+month+" 20"+year;
				}else if (tName.contains("-Edition")&&!tName.contains("MOCO")&&!tName.contains("MOKIDS")) {
	        		title="Edition Online Daily Sales by Platform (B2C ) -"+month+" 20"+year;
				}else if (tName.contains("-MOKIDS")&&!tName.contains("MOCO")&&!tName.contains("Edition")) {
	        		title="little MO&Co. Online Daily Sales by Platform (B2C ) -"+month+" 20"+year;
				}else if (tName.contains("MOCO")&&tName.contains("MOKIDS")&&!tName.contains("Edition")) {
	        		title="MO&Co.、little MO&Co. Online Daily Sales by Platform (B2C ) -"+month+" 20"+year;
				}else if (tName.contains("MOCO")&&tName.contains("Edition")&&!tName.contains("MOKIDS")) {
	        		title="MO&Co.、Edition Online Daily Sales by Platform (B2C ) -"+month+" 20"+year;
				}else if (tName.contains("MOCO")&&tName.contains("Edition")&&tName.contains("MOKIDS")) {
	        		title="MO&Co.、Edition、little MO&Co. Online Daily Sales by Platform (B2C ) -"+month+" 20"+year;
				}else if (tName.contains("Edition")&&tName.contains("MOKIDS")&&!tName.contains("MOCO")) {
	        		title="Edition、little MO&Co. Online Daily Sales by Platform (B2C ) -"+month+" 20"+year;
				}else  {
	        		title="Edition Online Daily Sales by Platform (B2C ) -"+month+" 20"+year;
				}
//	        	HSSFRichTextString richString = new HSSFRichTextString(title );  
//	        	//对前半部分设置黑色字体  
//	        	richString.applyFont( 0, 39, blackFont );  
//	        	//对时间部分设置红色字体
//	        	richString.applyFont( 39, richString.length(), redFont );  
	        	
	        	cell1.setCellValue(title);
//	        	cell1.setCellStyle(blodFontStyle);
	        	 CellRangeAddress cra =new CellRangeAddress(0, 0, 0, 62); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra); 
	        	 cell1.setCellStyle(blodFont11Style);
	        	 
	        	 CellRangeAddress cra10=new CellRangeAddress(1, 1, 0, 62); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra10); 
	        	 
	        	 row1 = context.getBoardSheet().createRow(1);
	         	 cell1=row1.createCell(0);
	         	cell1.setCellValue("");
	        	 
	        	 
	        	 row1=context.getBoardSheet().createRow(2);
	        	 cell1=row1.createCell(0);
	        	 cell1.setCellValue("Date");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 CellRangeAddress cra1 =new CellRangeAddress(2, 4, 0, 0); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra1); 
	        	 
	        	 
	        	 cell1=row1.createCell(1);
	        	 cell1.setCellValue("天猫");
	        	
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 
	        	 cell1=row1.createCell(2);
	        	 cell1.setCellValue(""); 
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 cell1=row1.createCell(3);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 

	        	 cell1=row1.createCell(4);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(5);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(6);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 CellRangeAddress cra2 =new CellRangeAddress(2, 2, 1, 14); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra2); 

	        	 

	        	 cell1=row1.createCell(7);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(8);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(9);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(10);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(11);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(12);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 CellRangeAddress cra4 =new CellRangeAddress(2, 2, 15, 26); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra4); 

	        	 
	        	 cell1=row1.createCell(13);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(14);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(15);
	        	 cell1.setCellValue("京东");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(16);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(17);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(18);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 CellRangeAddress cra3 =new CellRangeAddress(2, 2, 27, 38); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra3);

	        	 
	        	 cell1=row1.createCell(19);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 cell1=row1.createCell(20);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(21);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(22);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(23);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 cell1=row1.createCell(24);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 CellRangeAddress cra5 =new CellRangeAddress(2, 2, 39, 50); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra5); 
	        	 
	        	 cell1=row1.createCell(25);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(26);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(27);
	        	 cell1.setCellValue("考拉");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(28);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 cell1=row1.createCell(29);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(30);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 CellRangeAddress cra7 =new CellRangeAddress(2, 2, 51, 62); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra7); 


	        	 cell1=row1.createCell(31);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(32);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 cell1=row1.createCell(33);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(34);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(35);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(36);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 CellRangeAddress cra8 =new CellRangeAddress(2, 2, 63, 74); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra8); 
	        	 
	        	 cell1=row1.createCell(37);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 cell1=row1.createCell(38);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(39);
	        	 cell1.setCellValue("寺库");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(40);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(41);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	  
	        	 
	        	 cell1=row1.createCell(42);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 CellRangeAddress cra9 =new CellRangeAddress(2, 2, 75, 86); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra9); 


	        	 cell1=row1.createCell(43);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(44);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(45);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 cell1=row1.createCell(46);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(47);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(48);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 CellRangeAddress cra11 =new CellRangeAddress(2, 2, 87, 100); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra11); 
	        	 
	        	 cell1=row1.createCell(49);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	
	        	 
	        	 cell1=row1.createCell(50);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 cell1=row1.createCell(51);
	        	 cell1.setCellValue("尚品");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(52);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(53);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(54);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 cell1=row1.createCell(55);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 

	        	
	        	 
	        	 cell1=row1.createCell(56);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(57);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(58);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(59);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 cell1=row1.createCell(60);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 
	        	 cell1=row1.createCell(61);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(62);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 row1=context.getBoardSheet().createRow(3);
	        	 
	        	 cell1=row1.createCell(0);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(1);
	        	 cell1.setCellValue("Cashback");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 
	        	 cell1=row1.createCell(2);
	        	 cell1.setCellValue(""); 
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(3);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 CellRangeAddress cra21 =new CellRangeAddress(3, 3, 1, 3); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra21); 

	        	 cell1=row1.createCell(4);
	        	 cell1.setCellValue("Front Sales");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 
	        	 cell1=row1.createCell(5);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(6);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra22 =new CellRangeAddress(3, 3, 4, 6); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra22); 

	        	 

	        	 cell1=row1.createCell(7);
	        	 cell1.setCellValue("M1");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 
	        	 cell1=row1.createCell(8);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(9);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 CellRangeAddress cra23 =new CellRangeAddress(3, 3, 7, 9); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra23); 

	        	 cell1=row1.createCell(10);
	        	 cell1.setCellValue("Return");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 
	        	 cell1=row1.createCell(11);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(12);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra24 =new CellRangeAddress(3, 3, 10, 12); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra24); 

	        	 
	        	 cell1=row1.createCell(13);
	        	 cell1.setCellValue("O2O");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(14);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 CellRangeAddress cra25 =new CellRangeAddress(3, 3, 13, 14); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra25); 
	        	 
	        	 cell1=row1.createCell(15);
	        	 cell1.setCellValue("Cashback");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 

	        	 cell1=row1.createCell(16);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(17);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra26 =new CellRangeAddress(3, 3, 15, 17); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra26);

	        	 cell1=row1.createCell(18);
	        	 cell1.setCellValue("Front Sales");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(19);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(20);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra27 =new CellRangeAddress(3, 3, 18, 20); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra27);

	        	 cell1=row1.createCell(21);
	        	 cell1.setCellValue("M1");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	

	        	 cell1=row1.createCell(22);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 
	        	 cell1=row1.createCell(23);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra28 =new CellRangeAddress(3, 3, 21, 23); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra28); 
	        	 
	        	 
	        	 cell1=row1.createCell(24);
	        	 cell1.setCellValue("Return");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	
	        	 
	        	 cell1=row1.createCell(25);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(26);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra29 =new CellRangeAddress(3, 3, 24, 26); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra29);
	        	 

	        	 cell1=row1.createCell(27);
	        	 cell1.setCellValue("Cashback");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	
	        	 cell1=row1.createCell(28);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(29);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra30 =new CellRangeAddress(3, 3, 27, 29); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra30); 

	        	 cell1=row1.createCell(30);
	        	 cell1.setCellValue("Front Sales");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(31);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 
	        	 cell1=row1.createCell(32);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra31 =new CellRangeAddress(3, 3, 30, 32); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra31);
	        	 
	        	 cell1=row1.createCell(33);
	        	 cell1.setCellValue("M1");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	

	        	 cell1=row1.createCell(34);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(35);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra32 =new CellRangeAddress(3, 3, 33, 35); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra32); 
	        	 
	        	 cell1=row1.createCell(36);
	        	 cell1.setCellValue("Return");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	
	        	 
	        	 cell1=row1.createCell(37);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(38);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra33 =new CellRangeAddress(3, 3, 36, 38); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra33);

	        	 cell1=row1.createCell(39);
	        	 cell1.setCellValue("Cashback");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	
	        	 cell1=row1.createCell(40);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 
	        	 cell1=row1.createCell(41);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra34 =new CellRangeAddress(3, 3, 39, 41); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra34); 
	        	 
	        	 cell1=row1.createCell(42);
	        	 cell1.setCellValue("Front Sales");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(43);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(44);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra35 =new CellRangeAddress(3, 3,42 ,44 ); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra35);
	        	 
	        	 cell1=row1.createCell(45);
	        	 cell1.setCellValue("M1");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	
	        	 
	        	 cell1=row1.createCell(46);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(47);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra36 =new CellRangeAddress(3, 3, 45, 47); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra36); 

	        	 cell1=row1.createCell(48);
	        	 cell1.setCellValue("Return");
	        	 cell1.setCellStyle(titleBoldStyle);

	        
	        	 
	        	 cell1=row1.createCell(49);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	
	        	 
	        	 cell1=row1.createCell(50);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 CellRangeAddress cra37 =new CellRangeAddress(3, 3,48 ,50 ); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra37);
	        	 
	        	 cell1=row1.createCell(51);
	        	 cell1.setCellValue("Cashback");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	

	        	 cell1=row1.createCell(52);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(53);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra38 =new CellRangeAddress(3, 3, 51, 53); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra38); 
	        	 
	        	 cell1=row1.createCell(54);
	        	 cell1.setCellValue("Front Sales");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        
	        	 cell1=row1.createCell(55);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 

	        	
	        	 
	        	 cell1=row1.createCell(56);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra39 =new CellRangeAddress(3, 3,54 , 56); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra39);

	        	 cell1=row1.createCell(57);
	        	 cell1.setCellValue("M1");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	

	        	 cell1=row1.createCell(58);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 
	        	 cell1=row1.createCell(59);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra40 =new CellRangeAddress(3, 3, 57, 59); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra40); 
	        	 
	        	 cell1=row1.createCell(60);
	        	 cell1.setCellValue("Return");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	
	        	 
	        	 cell1=row1.createCell(61);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(62);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 CellRangeAddress cra41 =new CellRangeAddress(3, 3, 60,62 ); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra41);

	        	
	        	 
	        	 
	        	 row1=context.getBoardSheet().createRow(4);

	        	 cell1=row1.createCell(0);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(1);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(2);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(3);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(4);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(5);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(6);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	

	        	 cell1=row1.createCell(7);
	        	 cell1.setCellValue("DR%");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(8);
	        	 cell1.setCellValue("M1");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(9);
	        	 cell1.setCellValue("M1%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(10);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(11);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(12);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(13);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(14);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	

	        	 cell1=row1.createCell(15);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(16);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(17);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(18);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(19);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(20);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(21);
	        	 cell1.setCellValue("DR%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(22);
	        	 cell1.setCellValue("M1");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(23);
	        	 cell1.setCellValue("M1%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(24);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(25);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(26);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(27);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(28);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(29);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	
	        	 cell1=row1.createCell(30);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(31);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(32);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	
	        	 cell1=row1.createCell(33);
	        	 cell1.setCellValue("DR%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(34);
	        	 cell1.setCellValue("M1");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 cell1=row1.createCell(35);
	        	 cell1.setCellValue("M1%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(36);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(37);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(38);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(39);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(40);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(41);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(42);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(43);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(44);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	
	        	 cell1=row1.createCell(45);
	        	 cell1.setCellValue("DR%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(46);
	        	 cell1.setCellValue("M1");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(47);
	        	 cell1.setCellValue("M1%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(48);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(49);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(50);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(51);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(52);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(53);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(54);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(55);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(56);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	
	        	 cell1=row1.createCell(57);
	        	 cell1.setCellValue("DR%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(58);
	        	 cell1.setCellValue("M1");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(59);
	        	 cell1.setCellValue("M1%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(60);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(61);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(62);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 
	        	 
	        	 
		}else if (context.getTable().contains("MOKIDS-Online Daily Sales by Platform-B2C")) {
			 Row row1 = context.getBoardSheet().createRow(0);
	        	Cell cell1=row1.createCell(0);
	        	String sheetName =context.getWb().getSheetName(context.getSheetIndex());
//	        	cell1.setCellValue("EPO");
	        	Locale l = new Locale("en");
//	        	SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM");
//	        	Date headDate =null;
//	        	try {
//	        		headDate=sdf.parse(context.getDate());
//				} catch (ParseException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
	        	String tName =context.getTable();
	        	String month = String.format(l,"%tb", headDate);
	        	String year = String.format(l,"%ty", headDate);
	        	
	        	String title ="EPO "+sheetName+" Achievement (B2C)-"+month+" 20"+year;
	        	
	        	
	        	if (tName.contains("-MOCO")&&!tName.contains("Edition")&&!tName.contains("MOKIDS")) {
	        		title="MO&Co. Online Daily Sales by Platform (B2C ) -"+month+" 20"+year;
				}else if (tName.contains("-Edition")&&!tName.contains("MOCO")&&!tName.contains("MOKIDS")) {
	        		title="Edition Online Daily Sales by Platform (B2C ) -"+month+" 20"+year;
				}else if (tName.contains("-MOKIDS")&&!tName.contains("MOCO")&&!tName.contains("Edition")) {
	        		title="little MO&Co. Online Daily Sales by Platform (B2C ) -"+month+" 20"+year;
				}else if (tName.contains("MOCO")&&tName.contains("MOKIDS")&&!tName.contains("Edition")) {
	        		title="MO&Co.、little MO&Co. Online Daily Sales by Platform (B2C ) -"+month+" 20"+year;
				}else if (tName.contains("MOCO")&&tName.contains("Edition")&&!tName.contains("MOKIDS")) {
	        		title="MO&Co.、Edition Online Daily Sales by Platform (B2C ) -"+month+" 20"+year;
				}else if (tName.contains("MOCO")&&tName.contains("Edition")&&tName.contains("MOKIDS")) {
	        		title="MO&Co.、Edition、little MO&Co. Online Daily Sales by Platform (B2C ) -"+month+" 20"+year;
				}else if (tName.contains("Edition")&&tName.contains("MOKIDS")&&!tName.contains("MOCO")) {
	        		title="Edition、little MO&Co. Online Daily Sales by Platform (B2C ) -"+month+" 20"+year;
				}else  {
	        		title="little MO&Co. Online Daily Sales by Platform (B2C ) -"+month+" 20"+year;
				}
//	        	HSSFRichTextString richString = new HSSFRichTextString(title );  
//	        	//对前半部分设置黑色字体  
//	        	richString.applyFont( 0, 39, blackFont );  
//	        	//对时间部分设置红色字体
//	        	richString.applyFont( 39, richString.length(), redFont );  
	        	
	        	cell1.setCellValue(title);
//	        	cell1.setCellStyle(blodFontStyle);
	        	 CellRangeAddress cra =new CellRangeAddress(0, 0, 0, 50); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra); 
	        	 cell1.setCellStyle(blodFont11Style);
	        	 
	        	 CellRangeAddress cra10=new CellRangeAddress(1, 1, 0, 50); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra10); 
	        	 
	        	 row1 = context.getBoardSheet().createRow(1);
	         	 cell1=row1.createCell(0);
	         	cell1.setCellValue("");
	        	 
	        	 
	        	 row1=context.getBoardSheet().createRow(2);
	        	 cell1=row1.createCell(0);
	        	 cell1.setCellValue("Date");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 CellRangeAddress cra1 =new CellRangeAddress(2, 4, 0, 0); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra1); 
	        	 
	        	 
	        	 cell1=row1.createCell(1);
	        	 cell1.setCellValue("天猫");
	        	
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 
	        	 cell1=row1.createCell(2);
	        	 cell1.setCellValue(""); 
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 cell1=row1.createCell(3);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 

	        	 cell1=row1.createCell(4);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(5);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(6);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 CellRangeAddress cra2 =new CellRangeAddress(2, 2, 1, 14); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra2); 

	        	 

	        	 cell1=row1.createCell(7);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(8);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(9);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(10);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(11);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(12);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 CellRangeAddress cra4 =new CellRangeAddress(2, 2, 15, 26); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra4); 

	        	 
	        	 cell1=row1.createCell(13);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(14);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(15);
	        	 cell1.setCellValue("京东");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(16);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(17);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(18);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 CellRangeAddress cra3 =new CellRangeAddress(2, 2, 27, 38); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra3);

	        	 
	        	 cell1=row1.createCell(19);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 cell1=row1.createCell(20);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(21);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(22);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(23);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 cell1=row1.createCell(24);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 CellRangeAddress cra5 =new CellRangeAddress(2, 2, 39, 50); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra5); 
	        	 
	        	 cell1=row1.createCell(25);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(26);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(27);
	        	 cell1.setCellValue("考拉");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(28);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 cell1=row1.createCell(29);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(30);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 CellRangeAddress cra7 =new CellRangeAddress(2, 2, 51, 62); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra7); 


	        	 cell1=row1.createCell(31);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(32);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 cell1=row1.createCell(33);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(34);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(35);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(36);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 CellRangeAddress cra8 =new CellRangeAddress(2, 2, 63, 74); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra8); 
	        	 
	        	 cell1=row1.createCell(37);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 cell1=row1.createCell(38);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(39);
	        	 cell1.setCellValue("尚品");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(40);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(41);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 cell1=row1.createCell(42);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 CellRangeAddress cra9 =new CellRangeAddress(2, 2, 75, 86); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra9); 


	        	 cell1=row1.createCell(43);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(44);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(45);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 cell1=row1.createCell(46);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(47);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(48);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 CellRangeAddress cra11 =new CellRangeAddress(2, 2, 87, 100); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra11); 
	        	 
	        	 cell1=row1.createCell(49);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	
	        	 
	        	 cell1=row1.createCell(50);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	

	        	 
	        	 row1=context.getBoardSheet().createRow(3);
	        	 
	        	 cell1=row1.createCell(0);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(1);
	        	 cell1.setCellValue("Cashback");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 
	        	 cell1=row1.createCell(2);
	        	 cell1.setCellValue(""); 
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(3);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 CellRangeAddress cra21 =new CellRangeAddress(3, 3, 1, 3); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra21); 

	        	 cell1=row1.createCell(4);
	        	 cell1.setCellValue("Front Sales");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 
	        	 cell1=row1.createCell(5);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(6);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra22 =new CellRangeAddress(3, 3, 4, 6); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra22); 

	        	 

	        	 cell1=row1.createCell(7);
	        	 cell1.setCellValue("M1");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 
	        	 cell1=row1.createCell(8);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(9);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 CellRangeAddress cra23 =new CellRangeAddress(3, 3, 7, 9); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra23); 

	        	 cell1=row1.createCell(10);
	        	 cell1.setCellValue("Return");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 
	        	 cell1=row1.createCell(11);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(12);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra24 =new CellRangeAddress(3, 3, 10, 12); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra24); 

	        	 
	        	 cell1=row1.createCell(13);
	        	 cell1.setCellValue("O2O");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(14);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 CellRangeAddress cra25 =new CellRangeAddress(3, 3, 13, 14); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra25); 
	        	 
	        	 cell1=row1.createCell(15);
	        	 cell1.setCellValue("Cashback");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 

	        	 cell1=row1.createCell(16);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(17);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra26 =new CellRangeAddress(3, 3, 15, 17); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra26);

	        	 cell1=row1.createCell(18);
	        	 cell1.setCellValue("Front Sales");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(19);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(20);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra27 =new CellRangeAddress(3, 3, 18, 20); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra27);

	        	 cell1=row1.createCell(21);
	        	 cell1.setCellValue("M1");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	

	        	 cell1=row1.createCell(22);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 
	        	 cell1=row1.createCell(23);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra28 =new CellRangeAddress(3, 3, 21, 23); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra28); 
	        	 
	        	 
	        	 cell1=row1.createCell(24);
	        	 cell1.setCellValue("Return");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	
	        	 
	        	 cell1=row1.createCell(25);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(26);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra29 =new CellRangeAddress(3, 3, 24, 26); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra29);
	        	 

	        	 cell1=row1.createCell(27);
	        	 cell1.setCellValue("Cashback");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	
	        	 cell1=row1.createCell(28);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(29);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra30 =new CellRangeAddress(3, 3, 27, 29); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra30); 

	        	 cell1=row1.createCell(30);
	        	 cell1.setCellValue("Front Sales");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(31);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 
	        	 cell1=row1.createCell(32);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra31 =new CellRangeAddress(3, 3, 30, 32); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra31);
	        	 
	        	 cell1=row1.createCell(33);
	        	 cell1.setCellValue("M1");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	

	        	 cell1=row1.createCell(34);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(35);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra32 =new CellRangeAddress(3, 3, 33, 35); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra32); 
	        	 
	        	 cell1=row1.createCell(36);
	        	 cell1.setCellValue("Return");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	
	        	 
	        	 cell1=row1.createCell(37);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(38);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra33 =new CellRangeAddress(3, 3, 36, 38); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra33);

	        	 cell1=row1.createCell(39);
	        	 cell1.setCellValue("Cashback");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	
	        	 cell1=row1.createCell(40);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 
	        	 cell1=row1.createCell(41);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra34 =new CellRangeAddress(3, 3, 39, 41); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra34); 
	        	 
	        	 cell1=row1.createCell(42);
	        	 cell1.setCellValue("Front Sales");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(43);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(44);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra35 =new CellRangeAddress(3, 3,42 ,44 ); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra35);
	        	 
	        	 cell1=row1.createCell(45);
	        	 cell1.setCellValue("M1");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	
	        	 
	        	 cell1=row1.createCell(46);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(47);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra36 =new CellRangeAddress(3, 3, 45, 47); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra36); 

	        	 cell1=row1.createCell(48);
	        	 cell1.setCellValue("Return");
	        	 cell1.setCellStyle(titleBoldStyle);

	        
	        	 
	        	 cell1=row1.createCell(49);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	
	        	 
	        	 cell1=row1.createCell(50);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 CellRangeAddress cra37 =new CellRangeAddress(3, 3,48 ,50 ); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra37);
	        	 
	        	 
	        	 row1=context.getBoardSheet().createRow(4);

	        	 cell1=row1.createCell(0);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(1);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(2);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(3);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(4);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(5);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(6);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	

	        	 cell1=row1.createCell(7);
	        	 cell1.setCellValue("DR%");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(8);
	        	 cell1.setCellValue("M1");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(9);
	        	 cell1.setCellValue("M1%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(10);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(11);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(12);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(13);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(14);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	

	        	 cell1=row1.createCell(15);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(16);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(17);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(18);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(19);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(20);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(21);
	        	 cell1.setCellValue("DR%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(22);
	        	 cell1.setCellValue("M1");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(23);
	        	 cell1.setCellValue("M1%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(24);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(25);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(26);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(27);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(28);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(29);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	
	        	 cell1=row1.createCell(30);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(31);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(32);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	
	        	 cell1=row1.createCell(33);
	        	 cell1.setCellValue("DR%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(34);
	        	 cell1.setCellValue("M1");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 cell1=row1.createCell(35);
	        	 cell1.setCellValue("M1%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(36);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(37);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(38);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(39);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(40);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(41);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(42);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(43);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(44);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	
	        	 cell1=row1.createCell(45);
	        	 cell1.setCellValue("DR%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(46);
	        	 cell1.setCellValue("M1");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(47);
	        	 cell1.setCellValue("M1%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(48);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(49);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(50);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	
	        	 
	        	 
	        	 
	        	 
	        	 
	        	 
		}
		else if (context.getTable().contains("EPO-Online Daily Sales by Platform-B2B")) {
			 Row row1 = context.getBoardSheet().createRow(0);
	        	Cell cell1=row1.createCell(0);
	        	String sheetName =context.getWb().getSheetName(context.getSheetIndex());
//	        	cell1.setCellValue("EPO");
	        	Locale l = new Locale("en");
//	        	SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM");
//	        	Date headDate =null;
//	        	try {
//	        		headDate=sdf.parse(context.getDate());
//				} catch (ParseException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
	        	String tName =context.getTable();
	        	String month = String.format(l,"%tb", headDate);
	        	String year = String.format(l,"%ty", headDate);
	        	
	        	String title ="EPO "+sheetName+" Achievement (B2C)-"+month+" 20"+year;
	        	
	        	
	        	if (tName.contains("-MOCO")&&!tName.contains("Edition")&&!tName.contains("MOKIDS")) {
	        		title="MO&Co. Online Daily Sales by Platform ( B2B )  -"+month+" 20"+year;
				}else if (tName.contains("-Edition")&&!tName.contains("MOCO")&&!tName.contains("MOKIDS")) {
	        		title="Edition Online Daily Sales by Platform ( B2B )  -"+month+" 20"+year;
				}else if (tName.contains("-MOKIDS")&&!tName.contains("MOCO")&&!tName.contains("Edition")) {
	        		title="little MO&Co. Online Daily Sales by Platform ( B2B )  -"+month+" 20"+year;
				}else if (tName.contains("MOCO")&&tName.contains("MOKIDS")&&!tName.contains("Edition")) {
	        		title="MO&Co.、little MO&Co. Online Daily Sales by Platform ( B2B )  -"+month+" 20"+year;
				}else if (tName.contains("MOCO")&&tName.contains("Edition")&&!tName.contains("MOKIDS")) {
	        		title="MO&Co.、Edition Online Daily Sales by Platform ( B2B )  -"+month+" 20"+year;
				}else if (tName.contains("MOCO")&&tName.contains("Edition")&&tName.contains("MOKIDS")) {
	        		title="MO&Co.、Edition、little MO&Co. Online Daily Sales by Platform ( B2B )  -"+month+" 20"+year;
				}else if (tName.contains("Edition")&&tName.contains("MOKIDS")&&!tName.contains("MOCO")) {
	        		title="Edition、little MO&Co. Online Daily Sales by Platform ( B2B )  -"+month+" 20"+year;
				}else  {
	        		title="EPO Online Daily Sales by Platform ( B2B )  -"+month+" 20"+year;
				}
//	        	HSSFRichTextString richString = new HSSFRichTextString(title );  
//	        	//对前半部分设置黑色字体  
//	        	richString.applyFont( 0, 39, blackFont );  
//	        	//对时间部分设置红色字体
//	        	richString.applyFont( 39, richString.length(), redFont );  
	        	
	        	cell1.setCellValue(title);
//	        	cell1.setCellStyle(blodFontStyle);
	        	 CellRangeAddress cra =new CellRangeAddress(0, 0, 0, 72); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra); 
	        	 cell1.setCellStyle(blodFont11Style);
	        	 
	        	 CellRangeAddress cra10=new CellRangeAddress(1, 1, 0, 72); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra10); 
	        	 
	        	 row1 = context.getBoardSheet().createRow(1);
	         	 cell1=row1.createCell(0);
	         	cell1.setCellValue("");
	        	 
	        	 
	        	 row1=context.getBoardSheet().createRow(2);
	        	 cell1=row1.createCell(0);
	        	 cell1.setCellValue("Date");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 CellRangeAddress cra1 =new CellRangeAddress(2, 4, 0, 0); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra1); 
	        	 
	        	 
	        	 cell1=row1.createCell(1);
	        	 cell1.setCellValue("唯品会");
	        	
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 
	        	 cell1=row1.createCell(2);
	        	 cell1.setCellValue(""); 
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 cell1=row1.createCell(3);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 

	        	 cell1=row1.createCell(4);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(5);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(6);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	

	        	 

	        	 cell1=row1.createCell(7);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(8);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(9);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(10);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(11);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(12);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	
	        	 CellRangeAddress cra2 =new CellRangeAddress(2, 2, 1, 12); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra2); 
	        	 
	        	 cell1=row1.createCell(13);
	        	 cell1.setCellValue("有货");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(14);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(15);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(16);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(17);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(18);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	

	        	 
	        	 cell1=row1.createCell(19);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 cell1=row1.createCell(20);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(21);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(22);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(23);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 cell1=row1.createCell(24);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 CellRangeAddress cra3 =new CellRangeAddress(2, 2, 13, 24); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra3);
	        	 
	        	 cell1=row1.createCell(25);
	        	 cell1.setCellValue("魅力惠");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(26);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(27);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(28);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 cell1=row1.createCell(29);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(30);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 


	        	 cell1=row1.createCell(31);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(32);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 cell1=row1.createCell(33);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(34);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(35);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(36);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 CellRangeAddress cra7 =new CellRangeAddress(2, 2, 25, 36); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra7); 
	        	 
	        	 
	        	 row1=context.getBoardSheet().createRow(3);
	        	 
	        	 cell1=row1.createCell(0);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(1);
	        	 cell1.setCellValue("Cashback");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 
	        	 cell1=row1.createCell(2);
	        	 cell1.setCellValue(""); 
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(3);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 CellRangeAddress cra21 =new CellRangeAddress(3, 3, 1, 3); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra21); 

	        	 cell1=row1.createCell(4);
	        	 cell1.setCellValue("Front Sales");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 
	        	 cell1=row1.createCell(5);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(6);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra22 =new CellRangeAddress(3, 3, 4, 6); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra22); 

	        	 

	        	 cell1=row1.createCell(7);
	        	 cell1.setCellValue("M1");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 
	        	 cell1=row1.createCell(8);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(9);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 CellRangeAddress cra23 =new CellRangeAddress(3, 3, 7, 9); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra23); 

	        	 cell1=row1.createCell(10);
	        	 cell1.setCellValue("Return");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 
	        	 cell1=row1.createCell(11);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(12);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra24 =new CellRangeAddress(3, 3, 10, 12); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra24); 

	        	 
	        	 cell1=row1.createCell(13);
	        	 cell1.setCellValue("Cashback");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(14);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 
	        	 cell1=row1.createCell(15);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 CellRangeAddress cra25 =new CellRangeAddress(3, 3, 13, 15); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra25); 

	        	 cell1=row1.createCell(16);
	        	 cell1.setCellValue("Front Sales");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(17);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(18);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 CellRangeAddress cra26 =new CellRangeAddress(3, 3, 16, 18); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra26);

	        	 
	        	 cell1=row1.createCell(19);
	        	 cell1.setCellValue("M1");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(20);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(21);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra27 =new CellRangeAddress(3, 3, 19, 21); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra27);


	        	 cell1=row1.createCell(22);
	        	 cell1.setCellValue("Return");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 
	        	 cell1=row1.createCell(23);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(24);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 CellRangeAddress cra28 =new CellRangeAddress(3, 3, 22, 24); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra28); 
	        	 
	        	 cell1=row1.createCell(25);
	        	 cell1.setCellValue("Cashback");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(26);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(27);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra29 =new CellRangeAddress(3, 3, 25, 27); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra29);
	        	 
	        	 cell1=row1.createCell(28);
	        	 cell1.setCellValue("Front Sales");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(29);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(30);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 CellRangeAddress cra30 =new CellRangeAddress(3, 3, 28, 30); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra30); 


	        	 cell1=row1.createCell(31);
	        	 cell1.setCellValue("M1");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 
	        	 cell1=row1.createCell(32);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(33);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra31 =new CellRangeAddress(3, 3, 31, 33); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra31);

	        	 cell1=row1.createCell(34);
	        	 cell1.setCellValue("Retuen");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(35);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 
	        	 cell1=row1.createCell(36);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 CellRangeAddress cra32 =new CellRangeAddress(3, 3, 34, 36); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra32); 
	        	 
	        	 
	        	 
	        	 
	        	 row1=context.getBoardSheet().createRow(4);

	        	 cell1=row1.createCell(0);
	        	 cell1.setCellValue("Date");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(1);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(2);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(3);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(4);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(5);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(6);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(7);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	

	        	 cell1=row1.createCell(8);
	        	 cell1.setCellValue("M1");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(9);
	        	 cell1.setCellValue("M1%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(10);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(11);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(12);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(13);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(14);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(15);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(16);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(17);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(18);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	
	        	 cell1=row1.createCell(19);
	        	 cell1.setCellValue("DR%");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(20);
	        	 cell1.setCellValue("M1");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(21);
	        	 cell1.setCellValue("M1%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(22);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(23);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(24);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(25);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(26);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(27);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(28);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(29);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	
	        	 
	        	 cell1=row1.createCell(30);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(31);
	        	 cell1.setCellValue("DR%");
	        	 cell1.setCellStyle(titleBoldStyle);


	        	 cell1=row1.createCell(32);
	        	 cell1.setCellValue("M1");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(33);
	        	 cell1.setCellValue("M1%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(34);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 cell1=row1.createCell(35);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(36);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 
	        	 
	        	 
	        	 
	        	 
	        	 
		}else if (context.getTable().contains("EPO-Online Daily Sales by Platform-TTL")) {
			 Row row1 = context.getBoardSheet().createRow(0);
	        	Cell cell1=row1.createCell(0);
	        	String sheetName =context.getWb().getSheetName(context.getSheetIndex());
//	        	cell1.setCellValue("EPO");
	        	Locale l = new Locale("en");
//	        	SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM");
//	        	Date headDate =null;
//	        	try {
//	        		headDate=sdf.parse(context.getDate());
//				} catch (ParseException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
	        	String tName =context.getTable();
	        	String month = String.format(l,"%tb", headDate);
	        	String year = String.format(l,"%ty", headDate);
	        	
	        	String title ="MO&Co Online Daily Sales by Platform -"+month+" 20"+year;
	        	
	        	
	        	if (tName.contains("-MOCO")&&!tName.contains("Edition")&&!tName.contains("MOKIDS")) {
	        		title="MO&Co. Online Daily Sales by Platform -"+month+" 20"+year;
				}else if (tName.contains("-Edition")&&!tName.contains("MOCO")&&!tName.contains("MOKIDS")) {
	        		title="Edition Online Daily Sales by Platform -"+month+" 20"+year;
				}else if (tName.contains("-MOKIDS")&&!tName.contains("MOCO")&&!tName.contains("Edition")) {
	        		title="little MO&Co. Online Daily Sales by Platform -"+month+" 20"+year;
				}else if (tName.contains("MOCO")&&tName.contains("MOKIDS")&&!tName.contains("Edition")) {
	        		title="MO&Co.、little MO&Co. Online Daily Sales by Platform -"+month+" 20"+year;
				}else if (tName.contains("MOCO")&&tName.contains("Edition")&&!tName.contains("MOKIDS")) {
	        		title="MO&Co.、Edition Online Daily Sales by Platform -"+month+" 20"+year;
				}else if (tName.contains("MOCO")&&tName.contains("Edition")&&tName.contains("MOKIDS")) {
	        		title="MO&Co.、Edition、little MO&Co. Online Daily Sales by Platform -"+month+" 20"+year;
				}else if (tName.contains("Edition")&&tName.contains("MOKIDS")&&!tName.contains("MOCO")) {
	        		title="Edition、little MO&Co. Online Daily Sales by Platform -"+month+" 20"+year;
				}else  {
	        		title="EPO Online Daily Sales by Platform -"+month+" 20"+year;
				}
//	        	HSSFRichTextString richString = new HSSFRichTextString(title );  
//	        	//对前半部分设置黑色字体  
//	        	richString.applyFont( 0, 39, blackFont );  
//	        	//对时间部分设置红色字体
//	        	richString.applyFont( 39, richString.length(), redFont );  
	        	
	        	cell1.setCellValue(title);
//	        	cell1.setCellStyle(blodFontStyle);
	        	 CellRangeAddress cra =new CellRangeAddress(0, 0, 0, 14); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra); 
	        	 cell1.setCellStyle(blodFont11Style);
	        	 
	        	 CellRangeAddress cra10=new CellRangeAddress(1, 1, 0, 14); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra10); 
	        	 
	        	 row1 = context.getBoardSheet().createRow(1);
	         	 cell1=row1.createCell(0);
	         	cell1.setCellValue("");
	        	 
	        	 
	        	 row1=context.getBoardSheet().createRow(2);
	        	 cell1=row1.createCell(0);
	        	 cell1.setCellValue("Date");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 CellRangeAddress cra1 =new CellRangeAddress(2, 4, 0, 0); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra1); 
	        	 
	        	 
	        	 cell1=row1.createCell(1);
	        	 cell1.setCellValue("TTL");
	        	 CellRangeAddress cra2 =new CellRangeAddress(2, 2, 1, 14); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra2); 
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 
	        	 cell1=row1.createCell(2);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 cell1=row1.createCell(3);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 

	        	 cell1=row1.createCell(4);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(5);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(6);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(7);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(8);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(9);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(10);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(11);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(12);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 cell1=row1.createCell(13);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);


	        	 cell1=row1.createCell(14);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);

	        	 
	        	 
	        	 
	        	 row1=context.getBoardSheet().createRow(3);
	        	 cell1=row1.createCell(0);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(whiteBlodStyle);
	        	 
	        	 cell1=row1.createCell(1);
	        	 cell1.setCellValue("Cashback");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(2);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(3);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 CellRangeAddress cra3 =new CellRangeAddress(3, 3, 1, 3); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra3); 

	        	 cell1=row1.createCell(4);
	        	 cell1.setCellValue("Front Sales");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 cell1=row1.createCell(5);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 cell1=row1.createCell(6);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 CellRangeAddress cra4 =new CellRangeAddress(3, 3, 4, 6); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra4); 

	        	 cell1=row1.createCell(7);
	        	 cell1.setCellValue("M1");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(8);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 cell1=row1.createCell(9);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 CellRangeAddress cra5 =new CellRangeAddress(3, 3, 7, 9); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra5); 
	        	 
	        	 cell1=row1.createCell(10);
	        	 cell1.setCellValue("Return");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 

	        	 cell1=row1.createCell(11);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 cell1=row1.createCell(12);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra6 =new CellRangeAddress(3, 3, 10, 12); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra6); 

	        	 cell1=row1.createCell(13);
	        	 cell1.setCellValue("O2O");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 cell1=row1.createCell(14);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 CellRangeAddress cra7 =new CellRangeAddress(3, 3, 13, 14); // 起始行, 终止行, 起始列, 终止列  
	        	 context.getBoardSheet().addMergedRegion(cra7); 

	        	 
	        	 row1=context.getBoardSheet().createRow(4);
	        	 cell1=row1.createCell(0);
	        	 cell1.setCellValue("");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(1);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(2);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(3);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 cell1=row1.createCell(4);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 cell1=row1.createCell(5);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 cell1=row1.createCell(6);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 

	        	 cell1=row1.createCell(7);
	        	 cell1.setCellValue("DR%");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 
	        	 cell1=row1.createCell(8);
	        	 cell1.setCellValue("M1");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 cell1=row1.createCell(9);
	        	 cell1.setCellValue("M1%");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 
	        	 cell1=row1.createCell(10);
	        	 cell1.setCellValue("Target");
	        	 cell1.setCellStyle(titleBoldStyle);
	        	 

	        	 cell1=row1.createCell(11);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 cell1=row1.createCell(12);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 cell1=row1.createCell(13);
	        	 cell1.setCellValue("Actual");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 cell1=row1.createCell(14);
	        	 cell1.setCellValue("Ach%");
	        	 cell1.setCellStyle(titleBoldStyle);

	        	 
	        	 
	        	 
	        	 
		}
   
        for (int r = 0; r < tData.size(); r++) {
        	
        	
            Row row = context.getBoardSheet().getRow(context.getR1() + r);

            if (row == null) {
                row = context.getBoardSheet().createRow(context.getR1() + r);
            }
            if (context.getTable().contains("EPO E-COMMERCE Daily Achievement")&&!context.getTable().contains("(B2C)")){
            	row.setHeightInPoints((float) 15.25);
            }
            
            int colStart = context.getC1();
            for (int c = 0; c < tData.getJSONArray(r).size(); c++) {
                JSONObject cData = tData.getJSONArray(r).getJSONObject(c);
            
                int deltaSpan = colSpan;
                if (c <= delta) {
                    deltaSpan = colSpan + 1;
                }
                String property = cData.getString("property");

                if (c == tData.getJSONArray(r).size() - 1) {
                    if (!"data".equals(property)) {
                        rowHeader = r;
                    }
                }
                if (r == tData.size() - 1) {
                    if (!"data".equals(property)) {
                        columnHeaderCellIdx.add(colStart);
                    } else {
                        columnDataCellIdx.add(colStart);
                        
                    }
                }
                
               
                for (int j = colStart; j < colStart + deltaSpan; j++) {
                	CellStyle tStyle = context.getTitleStyle();
                	 if (r == tData.size()-5 ) {
                		 tStyle.setFillBackgroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
                		 redStyle.setFillBackgroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
                     }else{
                    	 redStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
                     }
                    Cell cell = row.createCell(j);
                   
                    
//                    context.getBoardSheet().autoSizeColumn(j);
//                    context.getBoardSheet().autoSizeColumn(j, true);
                    
                    if ("header_key".equals(property) || "header_empty".equals(property)) {
                    	
                    	
                    		greyStyle.setWrapText(true);
                            cell.setCellStyle(greyStyle);
                    	
                    	
                        
                    } else if ("data".equals(property)|| "column_key".equals(property)) {
                    	try {
                    		Double.parseDouble(cData.getString("data"));
                    		
                    		tStyle.setDataFormat((short) 0x2);
                    		
                    		
                    		
                          	   cell.setCellStyle(tStyle);
                          	  
                             
                    	}catch(NumberFormatException e) {
                    		cell.setCellStyle(context.gettStyle());
                    		
                    	}
                    }
                    if (j == colStart) {
                    	
                        if ("data".equals(property)) {
                        	
                        	
                            if (cData.getString("data") != null && cData.getString("data").contains("%")) {
                                cell.setCellValue(cData.getString("data"));
                                
                                if (cData.getString("data").contains("-")) {
                                	  
                                    	   cell.setCellStyle(redStyle);
								}else{
									 
                                  	   cell.setCellStyle(context.getPercentStyle());
								}
                                
                            } else {
                                cell.setCellValue(cData.getString("data"));
                                if (cData.getString("data").contains("-")) {
                                	
                                	
                                    	   cell.setCellStyle(redStyle);
                                       
								}
                            }
                        } else {
                            cell.setCellValue(cData.getString("data"));
                            
                            Pattern pattern = Pattern.compile("[0-9]{1,}");
                            Matcher matcher = pattern.matcher((CharSequence)cData.getString("data"));
                            boolean result=matcher.matches();
                            
                            //判断是否为日期，如果是日期才更新日期变量
                            if(cData.getString("data") != null&&!cData.getString("data").equals(" ")&&!cData.getString("data").equals("MTD")&&!cData.getString("data").contains("Ach")){
                            	
                            	if (result) {
                            		day=cData.getString("data");
								} 
                            }else{
                            	day=null;
                            }
                            
                            
                            if (result) {
                            	if (!cData.getString("data").contains("%")) {
                            		if (Double.parseDouble(cData.getString("data"))<0) {
        								 
                                      	   cell.setCellStyle(redStyle);
                                         
        							}
								}
                            	
							}
                            
                        }
                    }
                   
            	
                    
                    
                    //判断日期是否非初始值，判断J大小可避免周数单元格灰色背景。
                    if(day!=null&&!day.equals("")&&(context.getTable().contains("EPO E-COMMERCE Daily Achievement")
                    		||context.getTable().contains("EPO eCOMM Daily Achivement by Platform")
                    		||context.getTable().contains("EPO-Online Daily Sales by Platform-TTL")
                    		||context.getTable().contains("EPO-Online Daily Sales by Platform-B2B")
                    		||context.getTable().contains("MOCO-Online Daily Sales by Platform-B2C")
                    		||context.getTable().contains("MOKIDS-Online Daily Sales by Platform-B2C")
                    		||context.getTable().contains("Edition-Online Daily Sales by Platform-B2C")
                    		)
                    		&&j>0){
                    	String date=context.getDate()+"-"+day;


                        Date date1 =null;
    					try {
    						date1 = df.parse(date);
    					} catch (ParseException e1) {
    						// TODO Auto-generated catch block
    						e1.printStackTrace();
    					}

                        Calendar calendar = Calendar.getInstance();

                        calendar.setTime(date1);
                        
                        boolean flag = isWeekend(calendar);
                        if(!context.getTable().contains("EPO E-COMMERCE Daily Achievement(B2C)")
                        		&&!context.getTable().contains("EPO eCOMM Daily Achivement by Platform")
                        		&&!context.getTable().contains("EPO-Online Daily Sales by Platform-TTL")
                        		&&!context.getTable().contains("EPO-Online Daily Sales by Platform-B2B")
                        		&&!context.getTable().contains("MOCO-Online Daily Sales by Platform-B2C")
                        		&&!context.getTable().contains("MOKIDS-Online Daily Sales by Platform-B2C")
                        		&&!context.getTable().contains("Edition-Online Daily Sales by Platform-B2C")
                        		
                        		){
                        	if (flag==true){
                            	String value =cell.toString();
                            	if (j==1) {
                            		cell.setCellStyle(greyRightStyle);
    							}else if(j==4||j==7||j==10||j==14||j==18||j==23){
    								
    								if(value.contains("-")){
    									
    	                        		 cell.setCellStyle(titleRedRightStyle);
    	                        	}else{
    	                        		 cell.setCellStyle(greyRightStyle);
    	                        	}
    							}else {
    								if(value.contains("-")){
    	                        		 cell.setCellStyle(titleRedStyle);
    	                        	}else{
    	                        		 cell.setCellStyle(greyStyle);
    	                        	}
    							}
                            	
                            	
                            }else {
                            	String value =cell.toString();
                            	if (j==1) {
                            		cell.setCellStyle(blackRightStyle);
    							}else if(j==4||j==7||j==10||j==14||j==18||j==23){
    								
    								if(value.contains("-")){
    	                        		 cell.setCellStyle(redRightStyle);
    	                        	}else{
    	                        		 cell.setCellStyle(blackRightStyle);
    	                        	}
    							}else {
    								if(value.contains("-")){
    	                        		 cell.setCellStyle(redStyle);
    	                        	}else{
    	                        	cell.setCellStyle(whiteStyle);
    	                        	}
    								
    							}
                            
                            }
                        }else if(context.getTable().contains("EPO eCOMM Daily Achivement by Platform")){   //此处处理周六日灰色背景
                        	if (flag==true){
                             	String value =cell.toString();
                             	if (j==1) {
                            		cell.setCellStyle(greyLeftStyle);
    							}else if(j==0||j==3||j==6||j==9||j==12||j==15||j==18||j==21||j==24||j==27||j==30||j==33||j==36||j==39||j==42||j==45||j==48||j==51||j==54||j==57||j==60||j==63||j==66||j==69||j==72){
     								
     								if(value.contains("-")){
     	                        		 cell.setCellStyle(titleRedRightStyle);
     	                        	}else{
     	                        		 cell.setCellStyle(greyRightStyle);
     	                        	}
     							}else {
     								if(value.contains("-")){
     	                        		 cell.setCellStyle(titleRedStyle);
     	                        	}else{
     	                        		 cell.setCellStyle(greyStyle);
     	                        	}
     							}
                             	
                             	
                             }else {
                             	String value =cell.toString();
                             	if (j==1) {
                            		cell.setCellStyle(blackLeftStyle);
    							}else if(j==0||j==3||j==6||j==9||j==12||j==15||j==18||j==21||j==24||j==27||j==30||j==33||j==36||j==39||j==42||j==45||j==48||j==51||j==54||j==57||j==60||j==63||j==66||j==69||j==72){
     								
     								if(value.contains("-")){
     	                        		 cell.setCellStyle(redRightStyle);
     	                        	}else{
     	                        		 cell.setCellStyle(blackRightStyle);
     	                        	}
     							}else {
     								if(value.contains("-")){
     	                        		 cell.setCellStyle(redStyle);
     	                        	}else{
     	                        	cell.setCellStyle(whiteStyle);
     	                        	}
     								
     							}
                             
                             }
                        }else if(context.getTable().contains("EPO-Online Daily Sales by Platform-TTL")){   //此处处理周六日灰色背景
                        	if (flag==true){
                             	String value =cell.toString();
                             	if (j==1) {
                            		cell.setCellStyle(greyLeftStyle);
    							}else if(j==0||j==3||j==6||j==9||j==12||j==14){
     								
     								if(value.contains("-")){
     	                        		 cell.setCellStyle(titleRedRightStyle);
     	                        	}else{
     	                        		 cell.setCellStyle(greyRightStyle);
     	                        	}
     							}else {
     								if(value.contains("-")){
     	                        		 cell.setCellStyle(titleRedStyle);
     	                        	}else{
     	                        		 cell.setCellStyle(greyStyle);
     	                        	}
     							}
                             	
                             	
                             }else {
                             	String value =cell.toString();
                             	if (j==1) {
                            		cell.setCellStyle(blackLeftStyle);
    							}else if(j==0||j==3||j==6||j==9||j==12||j==14){
     								
     								if(value.contains("-")){
     	                        		 cell.setCellStyle(redRightStyle);
     	                        	}else{
     	                        		 cell.setCellStyle(blackRightStyle);
     	                        	}
     							}else {
     								if(value.contains("-")){
     	                        		 cell.setCellStyle(redStyle);
     	                        	}else{
     	                        	cell.setCellStyle(whiteStyle);
     	                        	}
     								
     							}
                             
                             }
                        }else if(context.getTable().contains("EPO-Online Daily Sales by Platform-B2B")){   //此处处理周六日灰色背景
                        	if (flag==true){
                             	String value =cell.toString();
                             	if (j==1) {
                            		cell.setCellStyle(greyLeftStyle);
    							}else if(j==0||j==3||j==6||j==9||j==12||j==15||j==18||j==21||j==24||j==27||j==30||j==33||j==36){
     								
     								if(value.contains("-")){
     	                        		 cell.setCellStyle(titleRedRightStyle);
     	                        	}else{
     	                        		 cell.setCellStyle(greyRightStyle);
     	                        	}
     							}else {
     								if(value.contains("-")){
     	                        		 cell.setCellStyle(titleRedStyle);
     	                        	}else{
     	                        		 cell.setCellStyle(greyStyle);
     	                        	}
     							}
                             	
                             	
                             }else {
                             	String value =cell.toString();
                             	if (j==1) {
                            		cell.setCellStyle(blackLeftStyle);
    							}else if(j==0||j==3||j==6||j==9||j==12||j==15||j==18||j==21||j==24||j==27||j==30||j==33||j==36){
     								
     								if(value.contains("-")){
     	                        		 cell.setCellStyle(redRightStyle);
     	                        	}else{
     	                        		 cell.setCellStyle(blackRightStyle);
     	                        	}
     							}else {
     								if(value.contains("-")){
     	                        		 cell.setCellStyle(redStyle);
     	                        	}else{
     	                        	cell.setCellStyle(whiteStyle);
     	                        	}
     								
     							}
                             
                             }
                        }else if(context.getTable().contains("MOCO-Online Daily Sales by Platform-B2C")){   //此处处理周六日灰色背景
                        	if (flag==true){
                             	String value =cell.toString();
                             	if (j==1) {
                            		cell.setCellStyle(greyLeftStyle);
    							}else if(j==0||j==3||j==6||j==9||j==12||j==14||j==17||j==20||j==23||j==26||j==29||j==32||j==35
    									||j==38||j==41||j==44||j==47||j==50||j==53||j==56||j==59||j==62||j==65||j==68||j==71||j==74||j==77
    									||j==80||j==83||j==86||j==89||j==92||j==95||j==98||j==100
    									){
     								
     								if(value.contains("-")){
     	                        		 cell.setCellStyle(titleRedRightStyle);
     	                        	}else{
     	                        		 cell.setCellStyle(greyRightStyle);
     	                        	}
     							}else {
     								if(value.contains("-")){
     	                        		 cell.setCellStyle(titleRedStyle);
     	                        	}else{
     	                        		 cell.setCellStyle(greyStyle);
     	                        	}
     							}
                             	
                             	
                             }else {
                             	String value =cell.toString();
                             	if (j==1) {
                            		cell.setCellStyle(blackLeftStyle);
    							}else if(j==0||j==3||j==6||j==9||j==12||j==14||j==17||j==20||j==23||j==26||j==29||j==32||j==35
    									||j==38||j==41||j==44||j==47||j==50||j==53||j==56||j==59||j==62||j==65||j==68||j==71||j==74||j==77
    									||j==80||j==83||j==86||j==89||j==92||j==95||j==98||j==100){
     								
     								if(value.contains("-")){
     	                        		 cell.setCellStyle(redRightStyle);
     	                        	}else{
     	                        		 cell.setCellStyle(blackRightStyle);
     	                        	}
     							}else {
     								if(value.contains("-")){
     	                        		 cell.setCellStyle(redStyle);
     	                        	}else{
     	                        	cell.setCellStyle(whiteStyle);
     	                        	}
     								
     							}
                             
                             }
                        }else if(context.getTable().contains("Edition-Online Daily Sales by Platform-B2C")){   //此处处理周六日灰色背景
                        	if (flag==true){
                             	String value =cell.toString();
                             	if (j==1) {
                            		cell.setCellStyle(greyLeftStyle);
    							}else if(j==0||j==3||j==6||j==9||j==12||j==14||j==17||j==20||j==23||j==26||j==29||j==32||j==35
    									||j==38||j==41||j==44||j==47||j==50||j==53||j==56||j==59||j==62
    									){
     								
     								if(value.contains("-")){
     	                        		 cell.setCellStyle(titleRedRightStyle);
     	                        	}else{
     	                        		 cell.setCellStyle(greyRightStyle);
     	                        	}
     							}else {
     								if(value.contains("-")){
     	                        		 cell.setCellStyle(titleRedStyle);
     	                        	}else{
     	                        		 cell.setCellStyle(greyStyle);
     	                        	}
     							}
                             	
                             	
                             }else {
                             	String value =cell.toString();
                             	if (j==1) {
                            		cell.setCellStyle(blackLeftStyle);
    							}else if(j==0||j==3||j==6||j==9||j==12||j==14||j==17||j==20||j==23||j==26||j==29||j==32||j==35
    									||j==38||j==41||j==44||j==47||j==50||j==53||j==56||j==59||j==62){
     								
     								if(value.contains("-")){
     	                        		 cell.setCellStyle(redRightStyle);
     	                        	}else{
     	                        		 cell.setCellStyle(blackRightStyle);
     	                        	}
     							}else {
     								if(value.contains("-")){
     	                        		 cell.setCellStyle(redStyle);
     	                        	}else{
     	                        	cell.setCellStyle(whiteStyle);
     	                        	}
     								
     							}
                             
                             }
                        }else if(context.getTable().contains("MOKIDS-Online Daily Sales by Platform-B2C")){   //此处处理周六日灰色背景
                        	if (flag==true){
                             	String value =cell.toString();
                             	if (j==1) {
                            		cell.setCellStyle(greyLeftStyle);
    							}else if(j==0||j==3||j==6||j==9||j==12||j==14||j==17||j==20||j==23||j==26||j==29||j==32||j==35
    									||j==38||j==41||j==44||j==47||j==50
    									){
     								
     								if(value.contains("-")){
     	                        		 cell.setCellStyle(titleRedRightStyle);
     	                        	}else{
     	                        		 cell.setCellStyle(greyRightStyle);
     	                        	}
     							}else {
     								if(value.contains("-")){
     	                        		 cell.setCellStyle(titleRedStyle);
     	                        	}else{
     	                        		 cell.setCellStyle(greyStyle);
     	                        	}
     							}
                             	
                             	
                             }else {
                             	String value =cell.toString();
                             	if (j==1) {
                            		cell.setCellStyle(blackLeftStyle);
    							}else if(j==0||j==3||j==6||j==9||j==12||j==14||j==17||j==20||j==23||j==26||j==29||j==32||j==35
    									||j==38||j==41||j==44||j==47||j==50){
     								
     								if(value.contains("-")){
     	                        		 cell.setCellStyle(redRightStyle);
     	                        	}else{
     	                        		 cell.setCellStyle(blackRightStyle);
     	                        	}
     							}else {
     								if(value.contains("-")){
     	                        		 cell.setCellStyle(redStyle);
     	                        	}else{
     	                        	cell.setCellStyle(whiteStyle);
     	                        	}
     								
     							}
                             
                             }
                        }else {
                        	 if (flag==true){
                             	String value =cell.toString();
                             	if (j==1) {
                             		cell.setCellStyle(greyRightStyle);
     							}else if(j==4||j==7||j==10||j==14||j==19){
     								
     								if(value.contains("-")){
     	                        		 cell.setCellStyle(titleRedRightStyle);
     	                        	}else{
     	                        		 cell.setCellStyle(greyRightStyle);
     	                        	}
     							}else {
     								if(value.contains("-")){
     	                        		 cell.setCellStyle(titleRedStyle);
     	                        	}else{
     	                        		 cell.setCellStyle(greyStyle);
     	                        	}
     							}
                             	
                             	
                             }else {
                             	String value =cell.toString();
                             	if (j==1) {
                             		cell.setCellStyle(blackRightStyle);
     							}else if(j==4||j==7||j==10||j==14||j==19){
     								
     								if(value.contains("-")){
     	                        		 cell.setCellStyle(redRightStyle);
     	                        	}else{
     	                        		 cell.setCellStyle(blackRightStyle);
     	                        	}
     							}else {
     								if(value.contains("-")){
     	                        		 cell.setCellStyle(redStyle);
     	                        	}else{
     	                        	cell.setCellStyle(whiteStyle);
     	                        	}
     								
     							}
                             
                             }
                        }
                       
                    }
                    
                    	
                    if (r == tData.size() - 1) {
                    	
                    	
                    	String value =cell.toString();
                    	
                    		if (context.getTable().contains("EPO E-COMMERCE Daily Achievement(B2C)")) {
                    			if(j==0){
                       			 cell.setCellStyle(greyBlodBottomStyle);
                            	}else if (j==1){
                            	
                            		cell.setCellStyle(greyBlodBottomRightStyle);
                            	}else if(j==4||j==7||j==10||j==14||j==19) {
                            		if(value.contains("-")){
                            			cell.setCellStyle(greyBlodBottomRightRedStyle);
                            		}else {
                            			cell.setCellStyle(greyBlodBottomRightStyle);
                            		}
                            		
                            	}else {
                            		if(value.contains("-")){
                            			cell.setCellStyle(greyBlodBottomRedStyle);
                            		}else {
                            			cell.setCellStyle(greyBlodBottomStyle);
                            		}
                            	}
                    			
                    			context.getBoardSheet().setMargin(HSSFSheet.TopMargin,0.736734694);// 页边距（上）    
                    			context.getBoardSheet().setMargin(HSSFSheet.BottomMargin,0.736734694);// 页边距（下）    
                    			context.getBoardSheet().setMargin(HSSFSheet.LeftMargin,0.62438024388 );// 页边距（左）    
                    			context.getBoardSheet().setMargin(HSSFSheet.RightMargin,0.24);// 页边距（右    
                  
                                PrintSetup ps = context.getBoardSheet().getPrintSetup();    
                                ps.setLandscape(true); // 打印方向，true：横向，false：纵向(默认)    
                                ps.setVResolution((short)600);    
                                ps.setPaperSize(PrintSetup.A4_PAPERSIZE); //纸张类型    
                                  
                    		}else if (context.getTable().contains("EPO E-COMMERCE Daily Achievement")&&!context.getTable().contains("(B2C)")) {
                    			
                    	
                                
                    			if(j==0){
                       			 cell.setCellStyle(greyBlodBottomStyle);
                            	}else if (j==1){
                            	
                            		cell.setCellStyle(greyBlodBottomRightStyle);
                            	}else if(j==4||j==7||j==10||j==14||j==18||j==23) {
                            		if(value.contains("-")){
                            			cell.setCellStyle(greyBlodBottomRightRedStyle);
                            		}else {
                            			cell.setCellStyle(greyBlodBottomRightStyle);
                            		}
                            		
                            	}else {
                            		if(value.contains("-")){
                            			cell.setCellStyle(greyBlodBottomRedStyle);
                            		}else {
                            			cell.setCellStyle(greyBlodBottomStyle);
                            			
                            		}
                            	}
                    			
                    			
                    			
                    			context.getBoardSheet().setMargin(HSSFSheet.TopMargin,0.736734694);// 页边距（上）    
                    			context.getBoardSheet().setMargin(HSSFSheet.BottomMargin,0.736734694);// 页边距（下）    
                    			context.getBoardSheet().setMargin(HSSFSheet.LeftMargin,0.70434782607 );// 页边距（左）    
                    			context.getBoardSheet().setMargin(HSSFSheet.RightMargin,0.24);// 页边距（右    
                  
                                PrintSetup ps = context.getBoardSheet().getPrintSetup();    
                                ps.setLandscape(true); // 打印方向，true：横向，false：纵向(默认)    
                                ps.setVResolution((short)600);    
                                ps.setPaperSize(PrintSetup.A3_PAPERSIZE); //纸张类型    
                    		}else if (context.getTable().contains("EPO-Online Daily Sales by Platform-TTL")) {
                    			
                            	
                                
                   			 if(j==0||j==3||j==6||j==9||j==12||j==14) {
                           		if(value.contains("-")){
                           			cell.setCellStyle(greyBlodBottomRightRedStyle);
                           		}else {
                           			cell.setCellStyle(greyBlodBottomRightStyle);
                           		}
                           		
                           	}else {
                           		if(value.contains("-")){
                           			cell.setCellStyle(greyBlodBottomRedStyle);
                           		}else {
                           			cell.setCellStyle(greyBlodBottomStyle);
                           			
                           		}
                           	}
                   			
                   			
                   			
                   			context.getBoardSheet().setMargin(HSSFSheet.TopMargin,0.1);// 页边距（上）    
                   			context.getBoardSheet().setMargin(HSSFSheet.BottomMargin,0.1);// 页边距（下）    
                   			context.getBoardSheet().setMargin(HSSFSheet.LeftMargin,0.25789 );// 页边距（左）    
                   			context.getBoardSheet().setMargin(HSSFSheet.RightMargin,0.1);// 页边距（右    
                 
                               PrintSetup ps = context.getBoardSheet().getPrintSetup();    
                               ps.setLandscape(true); // 打印方向，true：横向，false：纵向(默认)    
                               ps.setVResolution((short)600);    
                               ps.setPaperSize(PrintSetup.A4_PAPERSIZE); //纸张类型    
                   		}else if (context.getTable().contains("EPO eCOMM Daily Achivement by Platform")) {
                    			
                    	
                                
                    			 if(j==0||j==3||j==6||j==9||j==12||j==15||j==18||j==21||j==24||j==27||j==30||j==33||j==36||j==39||j==42||j==45||j==48||j==51||j==54||j==57||j==60||j==63||j==66||j==69||j==72) {
                            		if(value.contains("-")){
                            			cell.setCellStyle(greyBlodBottomRightRedStyle);
                            		}else {
                            			cell.setCellStyle(greyBlodBottomRightStyle);
                            		}
                            		
                            	}else {
                            		if(value.contains("-")){
                            			cell.setCellStyle(greyBlodBottomRedStyle);
                            		}else {
                            			cell.setCellStyle(greyBlodBottomStyle);
                            			
                            		}
                            	}
                    			
                    			
                    			
                    			context.getBoardSheet().setMargin(HSSFSheet.TopMargin,0.736734694);// 页边距（上）    
                    			context.getBoardSheet().setMargin(HSSFSheet.BottomMargin,0.736734694);// 页边距（下）    
                    			context.getBoardSheet().setMargin(HSSFSheet.LeftMargin,0.70434782607 );// 页边距（左）    
                    			context.getBoardSheet().setMargin(HSSFSheet.RightMargin,0.24);// 页边距（右    
                  
                                PrintSetup ps = context.getBoardSheet().getPrintSetup();    
                                ps.setLandscape(true); // 打印方向，true：横向，false：纵向(默认)    
                                ps.setVResolution((short)600);    
                                ps.setPaperSize(PrintSetup.A3_PAPERSIZE); //纸张类型    
                    		}else if (context.getTable().contains("EPO-Online Daily Sales by Platform-B2B")) {
                    			
                            	
                                
                   			 if(j==0||j==3||j==6||j==9||j==12||j==15||j==18||j==21||j==24||j==27||j==30||j==33||j==36) {
                           		if(value.contains("-")){
                           			cell.setCellStyle(greyBlodBottomRightRedStyle);
                           		}else {
                           			cell.setCellStyle(greyBlodBottomRightStyle);
                           		}
                           		
                           	}else {
                           		if(value.contains("-")){
                           			cell.setCellStyle(greyBlodBottomRedStyle);
                           		}else {
                           			cell.setCellStyle(greyBlodBottomStyle);
                           			
                           		}
                           	}
                   			
                   			
                   			
                   			context.getBoardSheet().setMargin(HSSFSheet.TopMargin,0.736734694);// 页边距（上）    
                   			context.getBoardSheet().setMargin(HSSFSheet.BottomMargin,0.736734694);// 页边距（下）    
                   			context.getBoardSheet().setMargin(HSSFSheet.LeftMargin,0.70434782607 );// 页边距（左）    
                   			context.getBoardSheet().setMargin(HSSFSheet.RightMargin,0.24);// 页边距（右    
                 
                               PrintSetup ps = context.getBoardSheet().getPrintSetup();    
                               ps.setLandscape(true); // 打印方向，true：横向，false：纵向(默认)    
                               ps.setVResolution((short)600);    
                               ps.setPaperSize(PrintSetup.A3_PAPERSIZE); //纸张类型     
                   		}else if (context.getTable().contains("MOKIDS-Online Daily Sales by Platform-B2C")) {
                			
                        	
                            
               			 if(j==0||j==3||j==6||j==9||j==12||j==14||j==17||j==20||j==23||j==26||j==29||j==32||j==35
									||j==38||j==41||j==44||j==47||j==50) {
                       		if(value.contains("-")){
                       			cell.setCellStyle(greyBlodBottomRightRedStyle);
                       		}else {
                       			cell.setCellStyle(greyBlodBottomRightStyle);
                       		}
                       		
                       	}else {
                       		if(value.contains("-")){
                       			cell.setCellStyle(greyBlodBottomRedStyle);
                       		}else {
                       			cell.setCellStyle(greyBlodBottomStyle);
                       			
                       		}
                       	}
               			
               			
               			
               			context.getBoardSheet().setMargin(HSSFSheet.TopMargin,0.736734694);// 页边距（上）    
               			context.getBoardSheet().setMargin(HSSFSheet.BottomMargin,0.736734694);// 页边距（下）    
               			context.getBoardSheet().setMargin(HSSFSheet.LeftMargin,0.70434782607 );// 页边距（左）    
               			context.getBoardSheet().setMargin(HSSFSheet.RightMargin,0.24);// 页边距（右    
             
                           PrintSetup ps = context.getBoardSheet().getPrintSetup();    
                           ps.setLandscape(true); // 打印方向，true：横向，false：纵向(默认)    
                           ps.setVResolution((short)600);    
                           ps.setPaperSize(PrintSetup.A3_PAPERSIZE); //纸张类型    
               		}else if (context.getTable().contains("MOCO-Online Daily Sales by Platform-B2C")) {
                			
                        	
                            
               			 if(j==0||j==3||j==6||j==9||j==12||j==14||j==17||j==20||j==23||j==26||j==29||j==32||j==35
									||j==38||j==41||j==44||j==47||j==50||j==53||j==56||j==59||j==62||j==65||j==68||j==71||j==74||j==77
									||j==80||j==83||j==86||j==89||j==92||j==95||j==98||j==100) {
                       		if(value.contains("-")){
                       			cell.setCellStyle(greyBlodBottomRightRedStyle);
                       		}else {
                       			cell.setCellStyle(greyBlodBottomRightStyle);
                       		}
                       		
                       	}else {
                       		if(value.contains("-")){
                       			cell.setCellStyle(greyBlodBottomRedStyle);
                       		}else {
                       			cell.setCellStyle(greyBlodBottomStyle);
                       			
                       		}
                       	}
               			
               			
               			
               			context.getBoardSheet().setMargin(HSSFSheet.TopMargin,0.736734694);// 页边距（上）    
               			context.getBoardSheet().setMargin(HSSFSheet.BottomMargin,0.736734694);// 页边距（下）    
               			context.getBoardSheet().setMargin(HSSFSheet.LeftMargin,0.70434782607 );// 页边距（左）    
               			context.getBoardSheet().setMargin(HSSFSheet.RightMargin,0.24);// 页边距（右    
             
                           PrintSetup ps = context.getBoardSheet().getPrintSetup();    
                           ps.setLandscape(true); // 打印方向，true：横向，false：纵向(默认)    
                           ps.setVResolution((short)600);    
                           ps.setPaperSize(PrintSetup.A3_PAPERSIZE); //纸张类型    
               		}else if (context.getTable().contains("Edition-Online Daily Sales by Platform-B2C")) {
            			
                    	
                        
           			 if(j==0||j==3||j==6||j==9||j==12||j==14||j==17||j==20||j==23||j==26||j==29||j==32||j==35
								||j==38||j==41||j==44||j==47||j==50||j==53||j==56||j==59||j==62) {
                   		if(value.contains("-")){
                   			cell.setCellStyle(greyBlodBottomRightRedStyle);
                   		}else {
                   			cell.setCellStyle(greyBlodBottomRightStyle);
                   		}
                   		
                   	}else {
                   		if(value.contains("-")){
                   			cell.setCellStyle(greyBlodBottomRedStyle);
                   		}else {
                   			cell.setCellStyle(greyBlodBottomStyle);
                   			
                   		}
                   	}
           			
           			
           			
           			context.getBoardSheet().setMargin(HSSFSheet.TopMargin,0.736734694);// 页边距（上）    
           			context.getBoardSheet().setMargin(HSSFSheet.BottomMargin,0.736734694);// 页边距（下）    
           			context.getBoardSheet().setMargin(HSSFSheet.LeftMargin,0.70434782607 );// 页边距（左）    
           			context.getBoardSheet().setMargin(HSSFSheet.RightMargin,0.24);// 页边距（右    
         
                       PrintSetup ps = context.getBoardSheet().getPrintSetup();    
                       ps.setLandscape(true); // 打印方向，true：横向，false：纵向(默认)    
                       ps.setVResolution((short)600);    
                       ps.setPaperSize(PrintSetup.A3_PAPERSIZE); //纸张类型    
           		}else {
                    			cell.setCellStyle(greyStyle);
                    		}
                    		
                    		 
                        	if (value.contains("·")) {
    							value=value.replace("·", "");
    							cell.setCellValue(value);
    						}
                    	
                    }else  if (r == tData.size() - 2&&context.getTable().contains("EPO eCOMM Daily Achivement by Platform")) {
                    	
                    	
                    	String value =cell.toString();
                    	
                    			 if(j==0||j==3||j==6||j==9||j==12||j==15||j==18||j==21||j==24||j==27||j==30||j==33||j==36||j==39||j==42||j==45||j==48||j==51||j==54||j==57||j==60||j==63||j==66||j==69||j==72) {
                            		if(value.contains("-")){
                            			cell.setCellStyle(greyBlodTopRightRedStyle);
                            		}else {
                            			cell.setCellStyle(greyBlodTopRightStyle);
                            		}
                            		
                            	}else {
                            		if(value.contains("-")){
                            			cell.setCellStyle(greyBlodTopRightRedStyle);
                            		}else {
                            			cell.setCellStyle(greyBlodTopStyle);
                            		}
                            	}
                    			
                                  
                    		
                    	
                    }else  if (r == tData.size() - 2&&context.getTable().contains("EPO-Online Daily Sales by Platform-B2B")) {
                    	
                    	
                    	String value =cell.toString();
                    	
                    			 if(j==0||j==3||j==6||j==9||j==12||j==15||j==18||j==21||j==24||j==27||j==30||j==33||j==36) {
                            		if(value.contains("-")){
                            			cell.setCellStyle(greyBlodTopRightRedStyle);
                            		}else {
                            			cell.setCellStyle(greyBlodTopRightStyle);
                            		}
                            		
                            	}else {
                            		if(value.contains("-")){
                            			cell.setCellStyle(greyBlodTopRightRedStyle);
                            		}else {
                            			cell.setCellStyle(greyBlodTopStyle);
                            		}
                            	}
                    			
                                  
                    		
                    	
                    }else  if (r == tData.size() - 2&&context.getTable().contains("EPO-Online Daily Sales by Platform-TTL")) {
                    	
                    	
                    	String value =cell.toString();
                    	
                    			 if(j==0||j==3||j==6||j==9||j==12||j==14) {
                            		if(value.contains("-")){
                            			cell.setCellStyle(greyBlodTopRightRedStyle);
                            		}else {
                            			cell.setCellStyle(greyBlodTopRightStyle);
                            		}
                            		
                            	}else {
                            		if(value.contains("-")){
                            			cell.setCellStyle(greyBlodTopRightRedStyle);
                            		}else {
                            			cell.setCellStyle(greyBlodTopStyle);
                            		}
                            	}
                    			
                                  
                    			 
                    	
                    }else  if (r == tData.size() - 2&&context.getTable().contains("MOCO-Online Daily Sales by Platform-B2C")) {
                    	
                    	
                    	String value =cell.toString();
                    	
                    			 if(j==0||j==3||j==6||j==9||j==12||j==14||j==17||j==20||j==23||j==26||j==29||j==32||j==35
     									||j==38||j==41||j==44||j==47||j==50||j==53||j==56||j==59||j==62||j==65||j==68||j==71||j==74||j==77
     									||j==80||j==83||j==86||j==89||j==92||j==95||j==98||j==100) {
                            		if(value.contains("-")){
                            			cell.setCellStyle(greyBlodTopRightRedStyle);
                            		}else {
                            			cell.setCellStyle(greyBlodTopRightStyle);
                            		}
                            		
                            	}else {
                            		if(value.contains("-")){
                            			cell.setCellStyle(greyBlodTopRightRedStyle);
                            		}else {
                            			cell.setCellStyle(greyBlodTopStyle);
                            		}
                            	}
                    			
                                  
                    		
                    	
                    }else  if (r == tData.size() - 2&&context.getTable().contains("Edition-Online Daily Sales by Platform-B2C")) {
                    	
                    	
                    	String value =cell.toString();
                    	
                    			 if(j==0||j==3||j==6||j==9||j==12||j==14||j==17||j==20||j==23||j==26||j==29||j==32||j==35
     									||j==38||j==41||j==44||j==47||j==50||j==53||j==56||j==59||j==62) {
                            		if(value.contains("-")){
                            			cell.setCellStyle(greyBlodTopRightRedStyle);
                            		}else {
                            			cell.setCellStyle(greyBlodTopRightStyle);
                            		}
                            		
                            	}else {
                            		if(value.contains("-")){
                            			cell.setCellStyle(greyBlodTopRightRedStyle);
                            		}else {
                            			cell.setCellStyle(greyBlodTopStyle);
                            		}
                            	}
                    			
                                  
                    		
                    	
                    }else  if (r == tData.size() - 2&&context.getTable().contains("MOKIDS-Online Daily Sales by Platform-B2C")) {
                    	
                    	
                    	String value =cell.toString();
                    	
                    			 if(j==0||j==3||j==6||j==9||j==12||j==14||j==17||j==20||j==23||j==26||j==29||j==32||j==35
     									||j==38||j==41||j==44||j==47||j==50) {
                            		if(value.contains("-")){
                            			cell.setCellStyle(greyBlodTopRightRedStyle);
                            		}else {
                            			cell.setCellStyle(greyBlodTopRightStyle);
                            		}
                            		
                            	}else {
                            		if(value.contains("-")){
                            			cell.setCellStyle(greyBlodTopRightRedStyle);
                            		}else {
                            			cell.setCellStyle(greyBlodTopStyle);
                            		}
                            	}
                    			
                                  
                    		
                    	
                    }
                    
                 
                    /**
                     * 将文字格式转换成数字格式
                     * */
                	Boolean isNum = false;//data是否为数值型
                    Boolean isPercent=false;//data是否为百分数
                    String cellValue =cell.toString();
                    if (cellValue != null || "".equals(cellValue)) {
                        isNum = cellValue.toString().matches(":[a-zA-Z]");
                        isPercent=cellValue.toString().contains("%");
                    }
                    
                    HSSFDataFormat  hdf = (HSSFDataFormat) context.getWb().createDataFormat(); // 此处设置数据格式
                    if (!isNum&&!isPercent&&j!=0&&j!=1&&context.getTable().contains("EPO E-COMMERCE Daily Achievement")) {
                    	
                    		cellValue=cellValue.replace(",", "");
                    		cell.setCellValue(Double.parseDouble(cellValue));
                        	cell.setCellType(CellType.NUMERIC);
                        	CellStyle tempStyle =context.getWb().createCellStyle();
                    		tempStyle =	cell.getCellStyle();
                        	tempStyle.setDataFormat(hdf.getFormat("#,##0"));
                        	cell.setCellStyle(tempStyle);
                        	
                    	
					}else  if (!isNum&&!isPercent&&j!=0&&context.getTable().contains("EPO eCOMM Daily Achivement by Platform")) {
                    	
                		cellValue=cellValue.replace(",", "");
                		cell.setCellValue(Double.parseDouble(cellValue));
                    	cell.setCellType(CellType.NUMERIC);
                    	CellStyle tempStyle =context.getWb().createCellStyle();
                		tempStyle =	cell.getCellStyle();
                    	tempStyle.setDataFormat(hdf.getFormat("#,##0"));
                    	cell.setCellStyle(tempStyle);
                    	
                	
					}else  if (!isNum&&!isPercent&&j!=0&&context.getTable().contains("EPO-Online Daily Sales by Platform-TTL")) {
                    	
                		cellValue=cellValue.replace(",", "");
                		cell.setCellValue(Double.parseDouble(cellValue));
                    	cell.setCellType(CellType.NUMERIC);
                    	CellStyle tempStyle =context.getWb().createCellStyle();
                		tempStyle =	cell.getCellStyle();
                    	tempStyle.setDataFormat(hdf.getFormat("#,##0"));
                    	cell.setCellStyle(tempStyle);
                    	
                	
				}
					else  if (!isNum&&!isPercent&&j!=0&&j!=1&&context.getTable().contains("MOCO-Online Daily Sales by Platform-B2C")) {
                	
            		cellValue=cellValue.replace(",", "");
            		cell.setCellValue(Double.parseDouble(cellValue));
                	cell.setCellType(CellType.NUMERIC);
                	CellStyle tempStyle =context.getWb().createCellStyle();
            		tempStyle =	cell.getCellStyle();
                	tempStyle.setDataFormat(hdf.getFormat("#,##0"));
                	cell.setCellStyle(tempStyle);
                	
            	
			}
					else  if (!isNum&&!isPercent&&j!=0&&context.getTable().contains("EPO-Online Daily Sales by Platform-B2B")) {
            	
        		cellValue=cellValue.replace(",", "");
        		cell.setCellValue(Double.parseDouble(cellValue));
            	cell.setCellType(CellType.NUMERIC);
            	CellStyle tempStyle =context.getWb().createCellStyle();
        		tempStyle =	cell.getCellStyle();
            	tempStyle.setDataFormat(hdf.getFormat("#,##0"));
            	cell.setCellStyle(tempStyle);
            	
        	
		}else  if (!isNum&&!isPercent&&j!=0&&context.getTable().contains("Edition-Online Daily Sales by Platform-B2C")) {
        	
    		cellValue=cellValue.replace(",", "");
    		cell.setCellValue(Double.parseDouble(cellValue));
        	cell.setCellType(CellType.NUMERIC);
        	CellStyle tempStyle =context.getWb().createCellStyle();
    		tempStyle =	cell.getCellStyle();
        	tempStyle.setDataFormat(hdf.getFormat("#,##0"));
        	cell.setCellStyle(tempStyle);
        	
    	
	}
                    
                   
                    
                }
                
                mergeRegion.add(new CellRangeAddress(row.getRowNum(), row.getRowNum(), colStart, colStart + deltaSpan - 1));
                
                colStart = colStart + deltaSpan;

                tAnchor.setCol2(context.getC2());
                tAnchor.setRow2(row.getRowNum());
            }
        }

        mergeRegion.stream().filter(e -> e.getFirstColumn() != e.getLastColumn() || e.getFirstRow() != e.getLastRow()).forEach(e -> context.getBoardSheet().addMergedRegion(e));
        return tAnchor;
    }

    /** 
     * 判断是否是周末 
     * @return 
     */  
    private boolean isWeekend(Calendar cal){  
        int week=cal.get(Calendar.DAY_OF_WEEK)-1;  
        if(week ==6 || week==0){//0代表周日，6代表周六  
            return true;  
        }  
        return false;  
    }
    
    private void mergeCellHelper(List<CellRangeAddress> mergeRegion, List<CellHelper> mergeList) {
        if (mergeList.size() < 2) {
            return;
        }
        for (CellHelper cellHelper : mergeList) {
            mergeRegion.remove(cellHelper.getMergedCell());
        }
        CellRangeAddress[] toMerged = mergeList.stream().map(e -> e.getMergedCell()).collect(Collectors.toList()).toArray(new CellRangeAddress[0]);
        CellRangeAddress[] merged = CellRangeUtil.mergeCellRanges(toMerged);
        Arrays.stream(merged).forEach(e -> mergeRegion.add(e));
    }

    private abstract class CellHelper {
        private Cell cell;
        private CellRangeAddress mergedCell;
        private String mergeKey;

        public CellHelper(XlsProcesserContext context, List<CellRangeAddress> mergeRegion, Cell cell) {
            Optional<CellRangeAddress> r = mergeRegion.stream().filter(c -> c.isInRange(cell.getRowIndex(), cell.getColumnIndex())).findFirst();
            CellRangeAddress _c = r.get();
            this.cell = cell;
            this.mergedCell = _c;

            parseMergeKey(context, cell);
        }

        protected abstract void parseMergeKey(XlsProcesserContext context, Cell cell);

        public String getMergeKey() {
            return mergeKey;
        }

        public Cell getCell() {
            return cell;
        }

        public void setCell(Cell cell) {
            this.cell = cell;
        }

        public CellRangeAddress getMergedCell() {
            return mergedCell;
        }
    }

    private class HCell extends CellHelper {

        public HCell(XlsProcesserContext context, List<CellRangeAddress> mergeRegion, Cell cell) {
            super(context, mergeRegion, cell);
        }

        @Override
        protected void parseMergeKey(XlsProcesserContext context, Cell cell) {
            StringBuilder sb = new StringBuilder();
            for (int c = cell.getColumnIndex(); c >= context.getC1(); c--) {
                Cell _cell = context.getBoardSheet().getRow(cell.getRowIndex()).getCell(c);
                if (_cell != null && _cell.getStringCellValue() != null) {
                    sb.append(_cell.getStringCellValue());
                }
            }
            super.mergeKey = sb.toString();
        }

    }

    private class VCell extends CellHelper {

        public VCell(XlsProcesserContext context, List<CellRangeAddress> mergeRegion, Cell cell) {
            super(context, mergeRegion, cell);
        }

        @Override
        protected void parseMergeKey(XlsProcesserContext context, Cell cell) {
            StringBuilder sb = new StringBuilder();
            for (int r = cell.getRowIndex(); r >= context.getR1(); r--) {
                Cell _cell = context.getBoardSheet().getRow(r).getCell(cell.getColumnIndex());
                if (_cell != null && _cell.getStringCellValue() != null) {
                    sb.append(_cell.getStringCellValue());
                }
            }
            super.mergeKey = sb.toString();
        }

    }

}
