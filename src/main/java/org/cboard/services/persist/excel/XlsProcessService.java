package org.cboard.services.persist.excel;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.mail.util.MailSSLSocketFactory;

import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.cboard.dao.BoardDao;

import java.io.ByteArrayInputStream;

import org.cboard.dto.Mail;
import org.cboard.dto.sendEmail;
import org.cboard.pojo.DashboardBoard;
import org.cboard.services.MailService;
import org.cboard.services.persist.PersistContext;
import org.cboard.util.SendEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by yfyuan on 2017/2/15.
 */
@Service
public class XlsProcessService {

    @Autowired
    private BoardDao boardDao;

    private XlsProcesser jpgXlsProcesser = new JpgXlsProcesser();
    private XlsProcesser tableXlsProcesser = new TableXlsProcesser();

    public HSSFWorkbook dashboardToXls(List<PersistContext> contexts) {
        XlsProcesserContext context = null;
        for (PersistContext e : contexts) {
            context = dashboardToXls(e, context,0);
        }
        return (HSSFWorkbook) context.getWb();
    }

    public HSSFWorkbook tableToxls(JSONObject data)  {
        XlsProcesserContext context = new XlsProcesserContext();
        HSSFWorkbook wb = new HSSFWorkbook();
        setColorIndex(wb);
        CellStyle titleStyle = createTitleStyle(wb);
        CellStyle thStyle = createThStyle(wb);
        CellStyle tStyle = createTStyle(wb);
        CellStyle percentStyle = wb.createCellStyle();
        percentStyle.cloneStyleFrom(tStyle);
        percentStyle.setDataFormat((short) 0xa);
        context.setWb(wb);
        context.setTableStyle(thStyle);
        context.setTitleStyle(titleStyle);
        context.settStyle(tStyle);
        context.setPercentStyle(percentStyle);
        Sheet sheet = context.getWb().createSheet();
        context.setBoardSheet(sheet);
        context.setC1(0);
        context.setC2(data.getJSONArray("data").getJSONArray(0).size() - 1);
        String wbName ="";
        JSONArray param = data.getJSONArray("paramArray");
        String table = data.getString("tablename");
        String date = "";
        if(data.containsKey("timeArray")){
        	if(data.getString("timeArray").length()>9){
        		date = data.getString("timeArray").substring(2,9);
        	}
        }
        context.setDate(date);
        context.setTable(table);
        if (table.contains("每日销售目标-管理层")) {
          	 context.setR1(1);
          	 if (param.size()>0) {
            	   for (int i = 0; i <param.size(); i++) {
            		   if (i==0) {
            			   wbName +=param.getString(i);
        			}else{
        				wbName +=","+param.getString(i);
        			}
            		   
            		}
                }else{
            	   wbName ="TOTAL";
                }
   		}else  if (table.contains("EPO E-COMMERCE Daily Achievement")) {
   			//MOCO,Edition,MOKIDS  --Edition MO&Co. little MO&Co.
         	 context.setR1(4);
         	 if (param.size()>0) {
           	   for (int i = 0; i <param.size(); i++) {
           		   if (i==0) {
           			   if (param.getString(i).equals("")) {
   						
   					}
   		         			 if (param.getString(i).equals("MOCO")) {
   		         				  wbName +="MO&Co.";
   			         			 }else  if (param.getString(i).equals("Edition")) {
   			       				  wbName +="Edition";
   			         			 }else  if (param.getString(i).equals("MOKIDS")) {
   				       				  wbName +="little MO&Co.";
   									}
       			}else{
       				 if (param.getString(i).equals("MOCO")) {
          				  wbName +=","+"MO&Co.";
   	         			 }else  if (param.getString(i).equals("Edition")) {
   	       				  wbName +=","+"Edition";
   	         			 }else  if (param.getString(i).equals("MOKIDS")) {
   		       				  wbName +=","+"little MO&Co.";
   							}
       			}
           		   
           		}
               }else{
           	   wbName ="EPO ECOMM DAILY";
               }
   		}else if(table.contains("B2C平台销售订单信息日报")){
   			context.setR1(0);
   	       	 if (param.size()>0) {
   	         	   for (int i = 0; i <param.size(); i++) {
   	         		   if (i==0) {
   	         			   wbName +=param.getString(i);
   	     			}else{
   	     				wbName +=","+param.getString(i);
   	     			}
   	         		   
   	         		}
   	             }else{
   	         	   wbName ="TOTAL";
   	             }
   		}else if(table.contains("EPO eCOMM Daily Achivement by Platform")&&!table.contains("TTL")){
   			context.setR1(5);
   	       	 if (param.size()>0) {
   	         	   for (int i = 0; i <param.size(); i++) {
   	         		   if (i==0) {
   	        			   if (param.getString(i).equals("")) {
   							
   						}
   			         			 if (param.getString(i).equals("MOCO")) {
   			         				  wbName +="MO&Co.";
   				         			 }else  if (param.getString(i).equals("Edition")) {
   				       				  wbName +="Edition";
   				         			 }else  if (param.getString(i).equals("MOKIDS")) {
   					       				  wbName +="little MO&Co.";
   										}
   	    			}else{
   	    				 if (param.getString(i).equals("MOCO")) {
   	       				  wbName +=","+"MO&Co.";
   		         			 }else  if (param.getString(i).equals("Edition")) {
   		       				  wbName +=","+"Edition";
   		         			 }else  if (param.getString(i).equals("MOKIDS")) {
   			       				  wbName +=","+"little MO&Co.";
   								}
   	    			}
   	         		  wbName +="-TTL";
   	         		}
   	             }else{
   	         	   wbName ="By Platform (TTL)";
   	             }
   	       	 //EPO-Online Daily Sales by Platform-B2B
   		}else if(table.contains("EPO-Online Daily Sales by Platform-TTL")){
   			context.setR1(5);
   	       	 if (param.size()>0) {
   	         	   for (int i = 0; i <param.size(); i++) {
   	         		  if (i==0) {
   	        			   if (param.getString(i).equals("")) {
   							
   						}
   			         			 if (param.getString(i).equals("MOCO")) {
   			         				  wbName +="MO&Co.";
   				         			 }else  if (param.getString(i).equals("Edition")) {
   				       				  wbName +="Edition";
   				         			 }else  if (param.getString(i).equals("MOKIDS")) {
   					       				  wbName +="little MO&Co.";
   										}
   	    			}else{
   	    				 if (param.getString(i).equals("MOCO")) {
   	       				  wbName +=","+"MO&Co.";
   		         			 }else  if (param.getString(i).equals("Edition")) {
   		       				  wbName +=","+"Edition";
   		         			 }else  if (param.getString(i).equals("MOKIDS")) {
   			       				  wbName +=","+"little MO&Co.";
   								}
   	    			}
   	         		  wbName +="-TTL";
   	         		}
   	             }else{
   	         	   wbName ="By Platform (TTL)";
   	             }
   		}else if(table.contains("EPO-Online Daily Sales by Platform-B2B")){
   			context.setR1(5);
   	       	 if (param.size()>0) {
   	         	   for (int i = 0; i <param.size(); i++) {
   	         		  if (i==0) {
   	        			   if (param.getString(i).equals("")) {
   							
   						}
   			         			 if (param.getString(i).equals("MOCO")) {
   			         				  wbName +="MO&Co.";
   				         			 }else  if (param.getString(i).equals("Edition")) {
   				       				  wbName +="Edition";
   				         			 }else  if (param.getString(i).equals("MOKIDS")) {
   					       				  wbName +="little MO&Co.";
   										}
   	    			}else{
   	    				 if (param.getString(i).equals("MOCO")) {
   	       				  wbName +=","+"MO&Co.";
   		         			 }else  if (param.getString(i).equals("Edition")) {
   		       				  wbName +=","+"Edition";
   		         			 }else  if (param.getString(i).equals("MOKIDS")) {
   			       				  wbName +=","+"little MO&Co.";
   								}
   	    			}
   	         		  wbName +="-B2B";
   	         		   
   	         		}
   	             }else{
   	         	   wbName ="TTL-B2B";
   	             }
   		}else if(table.contains("MOCO-Online Daily Sales by Platform-B2C")){
   			context.setR1(5);
//   	       	 if (param.size()>0) {
//   	         	   for (int i = 0; i <param.size(); i++) {
//   	         		   if (i==0) {
//   	         			   wbName +=param.getString(i);
//   	     			}else{
//   	     				wbName +=","+param.getString(i);
//   	     			}
//   	         		   
//   	         		}
//   	             }else{
   	         	   wbName ="MO&Co. -B2C";
//   	             }
   		}else if(table.contains("MOKIDS-Online Daily Sales by Platform-B2C")){
   			context.setR1(5);
//   	       	 if (param.size()>0) {
//   	         	   for (int i = 0; i <param.size(); i++) {
//   	         		   if (i==0) {
//   	         			   wbName +=param.getString(i);
//   	     			}else{
//   	     				wbName +=","+param.getString(i);
//   	     			}
//   	         		   
//   	         		}
//   	             }else{
   	         	   wbName ="little MO&CO.-B2C";
//   	             }
   		}else if(table.contains("Edition-Online Daily Sales by Platform-B2C")){
   			context.setR1(5);
//   	       	 if (param.size()>0) {
//   	         	   for (int i = 0; i <param.size(); i++) {
//   	         		   if (i==0) {
//   	         			   wbName +=param.getString(i);
//   	     			}else{
//   	     				wbName +=","+param.getString(i);
//   	     			}
//   	         		   
//   	         		}
//   	             }else{
   	         	   wbName ="Edition-B2C";
//   	             }
   		}else{
   			context.setR1(0);
   			wbName ="sheet";
   		}
        
        wb.setSheetName(wb.getSheetIndex(sheet), wbName);
       //Edition-Online Daily Sales by Platform-B2C
        context.setR2(0);
        context.setData(data);
        new TableXlsProcesser().drawContent(context);
        if (!table.contains("EPO E-COMMERCE Daily Achievement")
        		&&!table.contains("EPO eCOMM Daily Achivement by Platform")
        		&&!table.contains("EPO-Online Daily Sales by Platform-TTL")
        		&&!table.contains("MOCO-Online Daily Sales by Platform-B2C")
        		&&!table.contains("EPO-Online Daily Sales by Platform-B2B")
        		&&!table.contains("MOKIDS-Online Daily Sales by Platform-B2C")
        		&&!table.contains("Edition-Online Daily Sales by Platform-B2C")
        		){
        	setAutoWidth(sheet);
        }
        
        
        return wb;
        
        
    }
    
    public HSSFWorkbook tableToxls(JSONObject data,HSSFWorkbook wb ,int sheetIndex)  {
        XlsProcesserContext context = new XlsProcesserContext();
        setColorIndex(wb);
        CellStyle titleStyle = createTitleStyle(wb);
        CellStyle thStyle = createThStyle(wb);
        CellStyle tStyle = createTStyle(wb);
        CellStyle percentStyle = wb.createCellStyle();
        percentStyle.cloneStyleFrom(tStyle);
        percentStyle.setDataFormat((short) 0xa);
        context.setWb(wb);
        context.setTableStyle(thStyle);
        context.setTitleStyle(titleStyle);
        context.settStyle(tStyle);
        context.setPercentStyle(percentStyle);
        Sheet sheet = context.getWb().createSheet();
        context.setBoardSheet(sheet);
        context.setC1(0);
        context.setC2(data.getJSONArray("data").getJSONArray(0).size() - 1);
        String wbName ="";
        JSONArray param = data.getJSONArray("paramArray");
        String table = data.getString("tablename");
        String date = "";
        if(data.containsKey("timeArray")){
        	if(data.getString("timeArray").length()>9){
        		date = data.getString("timeArray").substring(2,9);
        	}
        }
        context.setDate(date);
        
        if (table.contains("每日销售目标-管理层")) {
       	 context.setR1(1);
       	 if (param.size()>0) {
         	   for (int i = 0; i <param.size(); i++) {
         		   if (i==0) {
         			   wbName +=param.getString(i);
     			}else{
     				wbName +=","+param.getString(i);
     			}
         		   
         		}
             }else{
         	   wbName ="TOTAL";
             }
		}else  if (table.contains("EPO E-COMMERCE Daily Achievement")) {
			//MOCO,Edition,MOKIDS  --Edition MO&Co. little MO&Co.
      	 context.setR1(4);
      	 if (param.size()>0) {
        	   for (int i = 0; i <param.size(); i++) {
        		   if (i==0) {
        			   if (param.getString(i).equals("")) {
						
					}
		         			 if (param.getString(i).equals("MOCO")) {
		         				  wbName +="MO&Co.";
			         			 }else  if (param.getString(i).equals("Edition")) {
			       				  wbName +="Edition";
			         			 }else  if (param.getString(i).equals("MOKIDS")) {
				       				  wbName +="little MO&Co.";
									}
    			}else{
    				 if (param.getString(i).equals("MOCO")) {
       				  wbName +=","+"MO&Co.";
	         			 }else  if (param.getString(i).equals("Edition")) {
	       				  wbName +=","+"Edition";
	         			 }else  if (param.getString(i).equals("MOKIDS")) {
		       				  wbName +=","+"little MO&Co.";
							}
    			}
        		   
        		}
            }else{
        	   wbName ="EPO ECOMM DAILY";
            }
		}else if(table.contains("B2C平台销售订单信息日报")){
			context.setR1(0);
	       	 if (param.size()>0) {
	         	   for (int i = 0; i <param.size(); i++) {
	         		   if (i==0) {
	         			   wbName +=param.getString(i);
	     			}else{
	     				wbName +=","+param.getString(i);
	     			}
	         		   
	         		}
	             }else{
	         	   wbName ="TOTAL";
	             }
		}else if(table.contains("EPO eCOMM Daily Achivement by Platform")&&!table.contains("TTL")){
			context.setR1(5);
	       	 if (param.size()>0) {
	         	   for (int i = 0; i <param.size(); i++) {
	         		   if (i==0) {
	        			   if (param.getString(i).equals("")) {
							
						}
			         			 if (param.getString(i).equals("MOCO")) {
			         				  wbName +="MO&Co.";
				         			 }else  if (param.getString(i).equals("Edition")) {
				       				  wbName +="Edition";
				         			 }else  if (param.getString(i).equals("MOKIDS")) {
					       				  wbName +="little MO&Co.";
										}
	    			}else{
	    				 if (param.getString(i).equals("MOCO")) {
	       				  wbName +=","+"MO&Co.";
		         			 }else  if (param.getString(i).equals("Edition")) {
		       				  wbName +=","+"Edition";
		         			 }else  if (param.getString(i).equals("MOKIDS")) {
			       				  wbName +=","+"little MO&Co.";
								}
	    			}
	         		  wbName +="-TTL";
	         		}
	             }else{
	         	   wbName ="By Platform (TTL)";
	             }
	       	 //EPO-Online Daily Sales by Platform-B2B
		}else if(table.contains("EPO-Online Daily Sales by Platform-TTL")){
			context.setR1(5);
	       	 if (param.size()>0) {
	         	   for (int i = 0; i <param.size(); i++) {
	         		  if (i==0) {
	        			   if (param.getString(i).equals("")) {
							
						}
			         			 if (param.getString(i).equals("MOCO")) {
			         				  wbName +="MO&Co.";
				         			 }else  if (param.getString(i).equals("Edition")) {
				       				  wbName +="Edition";
				         			 }else  if (param.getString(i).equals("MOKIDS")) {
					       				  wbName +="little MO&Co.";
										}
	    			}else{
	    				 if (param.getString(i).equals("MOCO")) {
	       				  wbName +=","+"MO&Co.";
		         			 }else  if (param.getString(i).equals("Edition")) {
		       				  wbName +=","+"Edition";
		         			 }else  if (param.getString(i).equals("MOKIDS")) {
			       				  wbName +=","+"little MO&Co.";
								}
	    			}
	         		  wbName +="-TTL";
	         		}
	             }else{
	         	   wbName ="By Platform (TTL)";
	             }
		}else if(table.contains("EPO-Online Daily Sales by Platform-B2B")){
			context.setR1(5);
	       	 if (param.size()>0) {
	         	   for (int i = 0; i <param.size(); i++) {
	         		  if (i==0) {
	        			   if (param.getString(i).equals("")) {
							
						}
			         			 if (param.getString(i).equals("MOCO")) {
			         				  wbName +="MO&Co.";
				         			 }else  if (param.getString(i).equals("Edition")) {
				       				  wbName +="Edition";
				         			 }else  if (param.getString(i).equals("MOKIDS")) {
					       				  wbName +="little MO&Co.";
										}
	    			}else{
	    				 if (param.getString(i).equals("MOCO")) {
	       				  wbName +=","+"MO&Co.";
		         			 }else  if (param.getString(i).equals("Edition")) {
		       				  wbName +=","+"Edition";
		         			 }else  if (param.getString(i).equals("MOKIDS")) {
			       				  wbName +=","+"little MO&Co.";
								}
	    			}
	         		  wbName +="-B2B";
	         		   
	         		}
	             }else{
	         	   wbName ="TTL.-B2B";
	             }
		}else if(table.contains("MOCO-Online Daily Sales by Platform-B2C")){
			context.setR1(5);
//	       	 if (param.size()>0) {
//	         	   for (int i = 0; i <param.size(); i++) {
//	         		   if (i==0) {
//	         			   wbName +=param.getString(i);
//	     			}else{
//	     				wbName +=","+param.getString(i);
//	     			}
//	         		   
//	         		}
//	             }else{
	         	   wbName ="MO&Co. -B2C";
//	             }
		}else if(table.contains("MOKIDS-Online Daily Sales by Platform-B2C")){
			context.setR1(5);
//	       	 if (param.size()>0) {
//	         	   for (int i = 0; i <param.size(); i++) {
//	         		   if (i==0) {
//	         			   wbName +=param.getString(i);
//	     			}else{
//	     				wbName +=","+param.getString(i);
//	     			}
//	         		   
//	         		}
//	             }else{
	         	   wbName ="little MO&CO.-B2C";
//	             }
		}else if(table.contains("Edition-Online Daily Sales by Platform-B2C")){
			context.setR1(5);
//	       	 if (param.size()>0) {
//	         	   for (int i = 0; i <param.size(); i++) {
//	         		   if (i==0) {
//	         			   wbName +=param.getString(i);
//	     			}else{
//	     				wbName +=","+param.getString(i);
//	     			}
//	         		   
//	         		}
//	             }else{
	         	   wbName ="Edition-B2C";
//	             }
		}else{
			context.setR1(0);
			wbName ="sheet";
		}
        
       wb.setSheetName(wb.getSheetIndex(sheet), wbName);
      //Edition-Online Daily Sales by Platform-B2C
       context.setR2(0);
       context.setData(data);
       context.setTable(table);
       new TableXlsProcesser().drawContent(context);
       if (!table.contains("EPO E-COMMERCE Daily Achievement")
       		&&!table.contains("EPO eCOMM Daily Achivement by Platform")
       		&&!table.contains("EPO-Online Daily Sales by Platform-TTL")
       		&&!table.contains("MOCO-Online Daily Sales by Platform-B2C")
       		&&!table.contains("EPO-Online Daily Sales by Platform-B2B")
       		&&!table.contains("MOKIDS-Online Daily Sales by Platform-B2C")
       		&&!table.contains("Edition-Online Daily Sales by Platform-B2C")
       		){
       	setAutoWidth(sheet);
       }
        
        return wb;
        
    }
    
    private XlsProcesserContext dashboardToXls(PersistContext persistContext, XlsProcesserContext context,int sheetIndex) {
        DashboardBoard board = boardDao.getBoard(persistContext.getDashboardId());
        JSONArray rows = JSONObject.parseObject(board.getLayout()).getJSONArray("rows");
        HSSFWorkbook wb = new HSSFWorkbook();
        List<JSONArray> arr = rows.stream().map(e -> (JSONObject) e)
                .filter(e -> e.getString("type") == null || "widget".equals(e.getString("type")))
                .map(e -> e.getJSONArray("widgets"))
                .collect(Collectors.toList());

        int widgets = 0;
        int tables = 0;
        for (JSONArray rw : arr) {
            for (int i = 0; i < rw.size(); i++) {
                JSONObject widget = rw.getJSONObject(i);
                JSONObject v = persistContext.getData().getJSONObject(widget.getLong("widgetId").toString());
                if (v != null && "table".equals(v.getString("type"))) {
                    tables++;
                }
                widgets++;
            }
        }

        int columns = 170;
        int columnWidth = 1700 / columns;
        int column_width12 = 148;
        
        if (context == null) {
            context = new XlsProcesserContext();
            
            setColorIndex(wb);
            CellStyle titleStyle = createTitleStyle(wb);
            CellStyle thStyle = createThStyle(wb);
            CellStyle tStyle = createTStyle(wb);
            CellStyle percentStyle = wb.createCellStyle();
            percentStyle.cloneStyleFrom(tStyle);
            percentStyle.setDataFormat((short) 0xa);
            context.setWb(wb);
            context.setTableStyle(thStyle);
            context.setTitleStyle(titleStyle);
            context.settStyle(tStyle);
            context.setPercentStyle(percentStyle);
        }
        int eachRow = -2;
        int dCol;
        int dRow;
        int widthInRow;
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        context.setDate(sdf.format(new Date()));
        //context.setTable(tablename);
        if (tables != widgets) {
            Sheet sheet = context.getWb().createSheet(board.getName());
            sheet.setDisplayGridlines(false);
            IntStream.range(0, 180).forEach(i -> sheet.setColumnWidth(i, 365));
            context.setBoardSheet(sheet);
            for (JSONArray rw : arr) {
                dCol = Math.round(30.0f / 1700 * columns);
                dRow = eachRow + 3;
                widthInRow = 0;
                for (int i = 0; i < rw.size(); i++) {

                    JSONObject widget = rw.getJSONObject(i);
                    JSONObject v = persistContext.getData().getJSONObject(widget.getLong("widgetId").toString());
                    if (v == null || v.keySet().size() == 0) {
                        continue;
                    }
                    int width = widget.getInteger("width").intValue();
                    int widget_cols = Math.round(1.0f * width / 12 * (148 - (rw.size() - 1) * 2));
                    widthInRow += width;
                    if (widthInRow > 12) {
                        dCol = Math.round(30.0f / 1700 * columns);
                        dRow = eachRow + 3;
                        widthInRow = width;
                    }
                    context.setC1(dCol + 2);
                    context.setC2(dCol + 2 + widget_cols);
                    context.setR1(dRow);
                    context.setR2(dRow);
                    context.setWidget(widget);
                    context.setData(v);
                    XlsProcesser processer = getProcesser(v.getString("type"));
                    ClientAnchor anchor = processer.draw(context);
                    if (anchor.getRow2() > eachRow) {
                        eachRow = anchor.getRow2();
                    }
                    dCol = context.getC2();
                }
            }
        }
        if (tables == 0) {
            return context;
        }
        dRow = 0;
        Sheet dataSheet = context.getWb().createSheet(board.getName() + "_table");
        context.setBoardSheet(dataSheet);
        for (JSONArray rw : arr) {
            for (int i = 0; i < rw.size(); i++) {
                JSONObject widget = rw.getJSONObject(i);
                JSONObject v = persistContext.getData().getJSONObject(widget.getLong("widgetId").toString());
                if (v == null || !"table".equals(v.getString("type"))) {
                    continue;
                }
                context.setC1(0);
                context.setC2(v.getJSONArray("data").getJSONArray(0).size() - 1);
                context.setR1(dRow);
                context.setR2(dRow);
                context.setWidget(widget);
                context.setData(v);
                XlsProcesser processer = getProcesser(v.getString("type"));
                ClientAnchor anchor = processer.draw(context);
                dRow = anchor.getRow2() + 2;
            }
        }
        
        String wbName=null;
        
//        if (tablename.contains("每日销售目标-管理层")) {
//       	 context.setR1(1);
//         	   wbName ="TOTAL";
//             
//		}else  if (tablename.contains("EPO E-COMMERCE Daily Achievement")) {
//      	 context.setR1(4);
//        	   wbName ="TOTAL";
//		}
      
       wb.setSheetName(sheetIndex, wbName);
       
        setAutoWidth(dataSheet);
               
        return context;
    }
    
    

    
    public HSSFWorkbook createExcel(JSONObject data) {
        XlsProcesserContext context = new XlsProcesserContext();
        HSSFWorkbook wb = new HSSFWorkbook();
        setColorIndex(wb);
        CellStyle titleStyle = createTitleStyle(wb);
        CellStyle thStyle = createThStyle(wb);
        CellStyle tStyle = createTStyle(wb);
        CellStyle percentStyle = wb.createCellStyle();
        percentStyle.cloneStyleFrom(tStyle);
        percentStyle.setDataFormat((short) 0xa);
        context.setWb(wb);
        context.setTableStyle(thStyle);
        context.setTitleStyle(titleStyle);
        context.settStyle(tStyle);
        context.setPercentStyle(percentStyle);
        Sheet sheet = context.getWb().createSheet();
        context.setBoardSheet(sheet);
        context.setC1(0);
        context.setC2(data.getJSONArray("data").getJSONArray(0).size() - 1);
        context.setR1(0);
        context.setR2(0);
        context.setData(data);
        new TableXlsProcesser().drawContent(context);
        setAutoWidth(sheet);
        return wb;
    }

   

    private void setAutoWidth(Sheet dataSheet) {
        int max = 0;
        Iterator<Row> i = dataSheet.rowIterator();
        while (i.hasNext()) {
            if (i.next().getLastCellNum() > max) {
                max = i.next().getLastCellNum();
            }
        }
        for (int colNum = 0; colNum < max; colNum++) {
            dataSheet.autoSizeColumn(colNum, true);
        }
    }

    private XlsProcesser getProcesser(String type) {
        switch (type) {
            case "jpg":
                return jpgXlsProcesser;
            case "table":
                return tableXlsProcesser;
        }
        return null;
    }

    private CellStyle createTitleStyle(HSSFWorkbook wb) {
        Font font = wb.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setColor(IndexedColors.BLACK.getIndex());
        font.setFontName("Calibri");
        CellStyle titleStyle = wb.createCellStyle();
        titleStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleStyle.setFont(font);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        
        titleStyle.setBorderBottom(BorderStyle.THIN);
        titleStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        titleStyle.setBorderLeft(BorderStyle.THIN);
        titleStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        titleStyle.setBorderRight(BorderStyle.THIN);
        titleStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        titleStyle.setBorderTop(BorderStyle.THIN);
        titleStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        return titleStyle;
    }

    private CellStyle createThStyle(HSSFWorkbook wb) {
        CellStyle thStyle = wb.createCellStyle();
        
        thStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        thStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        thStyle.setBorderBottom(BorderStyle.THIN);
        thStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        thStyle.setBorderLeft(BorderStyle.THIN);
        thStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        thStyle.setBorderRight(BorderStyle.THIN);
        thStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        thStyle.setBorderTop(BorderStyle.THIN);
        thStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        thStyle.setAlignment(HorizontalAlignment.CENTER);
        thStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        thStyle.setShrinkToFit(true);
        Font font = wb.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setColor(IndexedColors.BLACK.getIndex());
        font.setFontName("Calibri");
        thStyle.setFont(font);
        return thStyle;
    }

    private CellStyle createTStyle(HSSFWorkbook wb) {
        CellStyle tStyle = wb.createCellStyle();
        tStyle.setBorderBottom(BorderStyle.THIN);
        tStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        
        tStyle.setBorderLeft(BorderStyle.THIN);
        tStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        tStyle.setBorderRight(BorderStyle.THIN);
        tStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        tStyle.setBorderTop(BorderStyle.THIN);
        tStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        tStyle.setAlignment(HorizontalAlignment.CENTER);
        tStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        tStyle.setShrinkToFit(true);
        Font font = wb.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setColor(IndexedColors.BLACK.getIndex());
        font.setFontName("Calibri");
        tStyle.setFont(font);
        return tStyle;
    }

    private void setColorIndex(HSSFWorkbook wb) {
        HSSFPalette customPalette = wb.getCustomPalette();
        customPalette.setColorAtIndex(IndexedColors.GREY_25_PERCENT.index, (byte) 26, (byte) 127, (byte) 205);
       // customPalette.setColorAtIndex(IndexedColors.WHITE.index, (byte) 56, (byte) 119, (byte) 166);
        //customPalette.setColorAtIndex(IndexedColors.WHITE.index, (byte) 235, (byte) 235, (byte) 235);
        
    }

}
