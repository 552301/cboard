package org.cboard.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.cboard.dto.ViewPlanTarget;
import org.cboard.pojo.PlanTarget;
import org.cboard.services.PlanTargetService;
import org.cboard.services.ServiceStatus;
import org.cboard.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * @Classname PlanTargetController
 * @Description TODO
 * @Date 2019-09-20 11:23
 * @Created by zhoujinming
 */
@RestController
@RequestMapping("/plantarget")
public class PlanTargetController {
    @Autowired
    private PlanTargetService planTargetService;

    @RequestMapping(value = "/savePlanTarget")
    public ServiceStatus savePlanTarget(@RequestParam(name = "json") String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        PlanTarget target = new PlanTarget();
        target.setSeason(jsonObject.getString("season"));
        target.setCategory(jsonObject.getString("category"));
        target.setSeason(jsonObject.getString(" styleBill"));
        target.setPlanMonth(jsonObject.getString("planMonth"));
        target.setPlanNums(jsonObject.getInteger("planNums"));
        target.setCreateDate(jsonObject.getString("createDate"));

        return planTargetService.save(target);
    }

    @RequestMapping(value = "/updatePlanTarget")
    public ServiceStatus updatePlanTarget(@RequestParam(name = "json") String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        PlanTarget target = new PlanTarget();
        target.setId(jsonObject.getInteger("id"));
        target.setCreateDate(jsonObject.getString("createDate"));
        target.setSeason(jsonObject.getString("season"));
        target.setStyleBill(jsonObject.getString("styleBill"));
        target.setCategory(jsonObject.getString("category"));
        target.setPlanMonth(jsonObject.getString("planMonth"));
        target.setPlanNums(jsonObject.getInteger("planNums"));

        return planTargetService.update(target);
    }

    @RequestMapping(value = "/deletePlanTarget")
    public ServiceStatus deletePlanTarget(@RequestParam(name = "id") int id) {
        return planTargetService.delete(id);
    }

    @RequestMapping(value = "/getPlanTargetList")
    public List<ViewPlanTarget> getPlanTargetList(@RequestParam(name = "params") String params) {
        JSONObject jsonObject = JSONObject.parseObject(params);
        Map<String, Object> map = new HashMap<String, Object>();
        if (jsonObject.containsKey("season")) {
            if (!jsonObject.getString("season").isEmpty())
                map.put("season", jsonObject.getString("season"));
        }
        if (jsonObject.containsKey("category")) {
            if (!jsonObject.getString("category").isEmpty())
                map.put("category", jsonObject.getString("category"));
        }
        if (jsonObject.containsKey("styleBill")) {
            if (!jsonObject.getString("styleBill").isEmpty())
                map.put("styleBill", jsonObject.getString("styleBill"));
        }
        if (jsonObject.containsKey("planMonth")) {
            if (!jsonObject.getString("planMonth").isEmpty())
                map.put("planMonth", jsonObject.getString("planMonth"));
        }
        if (jsonObject.containsKey("planNums")) {
            if (!jsonObject.getString("planNums").isEmpty())
                map.put("planNums", jsonObject.getInteger("planNums"));
        }
        if (jsonObject.containsKey("createDate")) {
            if (!jsonObject.getString("createDate").isEmpty())
                map.put("createDate", jsonObject.getDate("createDate"));
        }
        if (jsonObject.containsKey("modifiedDate")) {
            if (!jsonObject.getString("modifiedDate").isEmpty())
                map.put("modifiedDate", jsonObject.getDate("modifiedDate"));
        }
        
        List<ViewPlanTarget> vts = new ArrayList<ViewPlanTarget>();
        List<PlanTarget> ts = planTargetService.getPlanTargetList(map);

        for (PlanTarget t : ts) {
            vts.add(new ViewPlanTarget(t));
        }

        return vts;
    }

    @RequestMapping(value = "/getPlanTarget")
    public ViewPlanTarget getDaySalesTarge(@RequestParam(name = "id") int id) {
        PlanTarget t = planTargetService.getPlanTarget(id);
        return new ViewPlanTarget(t);
    }

    /**
     * 通过Excel导入目标
     *
     * @param file
     * @return
     **/
    @RequestMapping(value = "/importPlanTargets")
    public ServiceStatus importPlanTargets(@RequestParam(name = "file") MultipartFile file,
                                               HttpServletRequest request, HttpServletResponse response) {
        try {
            CommonsMultipartFile cmpf = (CommonsMultipartFile) file;
            File dir = new File(request.getSession().getServletContext().getRealPath("/uplpaded"));
            if (!dir.exists())
                dir.mkdirs();

            String fileName = request.getSession().getServletContext().getRealPath("/uplpaded") + "\\"
                    + new Date().getTime() + ".xlsx";
            File excelFile = new File(fileName);
            try {
                cmpf.getFileItem().write(excelFile);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            InputStream is = new FileInputStream(fileName);
            Workbook wb = new XSSFWorkbook(is);
            Sheet sheet = wb.getSheetAt(0);
            int totalRows = sheet.getPhysicalNumberOfRows();
            int totalCols = 0;

            if (totalRows > 1 && sheet.getRow(0) != null) {

                totalCols = sheet.getRow(0).getPhysicalNumberOfCells();
            }
//            if (totalCols != 8) {
//                return new ServiceStatus(ServiceStatus.Status.Fail, "导入失败，不符合预期的数据，请使用模板进行数据导入");
//            }

            int importedCount = 0;
            int currentRow = 1;
            while (currentRow < totalRows) {
                Row row = sheet.getRow(currentRow);
                try {
                    if(row.getCell(2) == null || row.getCell(2).getStringCellValue() == null)
                        break;

                    PlanTarget dst = new PlanTarget();

                    dst.setSeason(row.getCell(0)==null?null:row.getCell(0).getStringCellValue());
                    dst.setCategory(row.getCell(1)==null?null:row.getCell(1).getStringCellValue());
                    dst.setStyleBill(row.getCell(2)==null?null:row.getCell(2).getStringCellValue());
                    dst.setPlanMonth(row.getCell(3)==null?null:row.getCell(3).getStringCellValue());
                    dst.setPlanNums(new Double(row.getCell(4).getNumericCellValue()).intValue());
                    dst.setCreateDate(DateUtil.convertDate2Str(new Date()));
                        


                    if (dst.getStyleBill() != null && dst.getPlanMonth() != null) {

                        // 判断是否已有数据
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("season", dst.getSeason());
                        map.put("category", dst.getCategory());
                        map.put("styleBill", dst.getStyleBill());
                        map.put("planMonth", dst.getPlanMonth());
                        map.put("planNums", dst.getPlanNums());
                        map.put("createDate",new Date());
                        List<PlanTarget> ts = new ArrayList<PlanTarget>();
                        ts = planTargetService.getPlanTargetList(map);
                        if (ts.size() > 0) {
                            dst.setId(ts.get(0).getId());
                            if (planTargetService.update(dst).getStatus().equals(ServiceStatus.Status.Success.toString()))
                                importedCount++;
                        }else {
                            if (planTargetService.save(dst).getStatus().equals(ServiceStatus.Status.Success.toString()))
                                importedCount++;
                        }

                    }
                } catch (IllegalStateException ie) {
                    ie.printStackTrace();
                } catch (NumberFormatException ne) {
                    ne.printStackTrace();
                }

                currentRow++;
            }
            return new ServiceStatus(ServiceStatus.Status.Success,
                    "导入成功，共导入 " + importedCount + "/" + (totalRows - 1) + "条记录");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }

        return new ServiceStatus(ServiceStatus.Status.Fail, "导入失败");
    }
}
