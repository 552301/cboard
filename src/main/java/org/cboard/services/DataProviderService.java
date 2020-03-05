package org.cboard.services;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.google.common.base.Functions;
import com.google.common.collect.Maps;

import org.apache.commons.lang3.StringUtils;
import org.cboard.dao.DatasetDao;
import org.cboard.dao.DatasourceDao;
import org.cboard.dataprovider.DataProvider;
import org.cboard.dataprovider.DataProviderManager;
import org.cboard.dataprovider.config.AggConfig;
import org.cboard.dataprovider.config.ConfigComponent;
import org.cboard.dataprovider.config.DimensionConfig;
import org.cboard.dataprovider.result.AggregateResult;
import org.cboard.dto.DataProviderResult;
import org.cboard.dto.InjectionParam;
import org.cboard.exception.CBoardException;
import org.cboard.pojo.DashboardDataset;
import org.cboard.pojo.DashboardDatasource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by yfyuan on 2016/8/15.
 */
@Repository
public class DataProviderService {

    @Autowired
    private DatasourceDao datasourceDao;

    @Autowired
    private DatasetDao datasetDao;

    private DataProvider getDataProvider(Long datasourceId, Map<String, String> query, Dataset dataset) throws Exception {
        if (dataset != null) {
            datasourceId = dataset.getDatasourceId();
            query = dataset.getQuery();
        }
        DashboardDatasource datasource = datasourceDao.getDatasource(datasourceId);
        JSONObject datasourceConfig = JSONObject.parseObject(datasource.getConfig());
        Map<String, String> dataSource = Maps.transformValues(datasourceConfig, Functions.toStringFunction());
        DataProvider dataProvider = DataProviderManager.getDataProvider(datasource.getType(), dataSource, query);
        if (dataset != null && dataset.getInterval() != null && dataset.getInterval() > 0) {
            dataProvider.setInterval(dataset.getInterval());
        }
        return dataProvider;
    }

    public AggregateResult queryAggData(Long datasourceId, Map<String, String> query, Long datasetId, AggConfig config, boolean reload) {
    	String sDate=null;
    	String eDate=null;
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
    	String now =df.format(new Date());
    	Calendar c = Calendar.getInstance();
        try {
            Dataset dataset = getDataset(datasetId);
            attachCustom(dataset, config);
            
            DataProvider dataProvider = getDataProvider(datasourceId, query, dataset);
            String queryStr =  dataProvider.getQuery().get("sql");
            String[] queryExpression = dataProvider.getQuery().get("sql").split("##");
            List<ConfigComponent> filters = config.getFilters();
            
            //注入条件
            for(int idx = 0; idx< config.getFilters().size(); idx++) {
            	ConfigComponent filter = config.getFilters().get(idx);
            	DimensionConfig filterConfig = (DimensionConfig)filter;
            	if(filterConfig.getColumnName().contains(":")) {
            		String[] expressionItems = filterConfig.getColumnName().split(":");
            		if(expressionItems.length >= 2) {
	            		String replaceTarget = "##" + filterConfig.getColumnName() + "##";
	            		String replaceExpression = "";
	            		String operator = filterConfig.getFilterType();
	            		if(operator.equals("=")) {
	            			replaceExpression = expressionItems[1] + " in (";
	            			for(String value : filterConfig.getValues()) {
	            				replaceExpression += "'" + value + "',";
	            			}
	            			replaceExpression += ") ";
	            			
	            			replaceExpression = " and " + replaceExpression.replace("',)", "')");
	            		} else {
	            			if(filterConfig.getValues().size() > 0)
	            				if(operator.equals("≥"))
	            					operator = ">=";
	            				else if(operator.equals("≤"))
	            					operator = "<=";
	            			//判断是否处理开始和结束时间
	            			if (expressionItems[0].contains("sdate")&&datasetId!=79) {
								sDate=filterConfig.getValues().get(0);
								
								if (sDate.equals(now)) {
									//假如开始时间和当前时间一样，则判断为月初，则将开始时间减去一月，作为开始时间；
									c.setTime(df.parse(now));
							        c.add(Calendar.MONTH, -1);
							        Date m = c.getTime();
							    	replaceExpression = " and " + expressionItems[1] + " " + operator + " '" + df.format(m) +  "' "; 
		            				
								}else{
									replaceExpression = " and " + expressionItems[1] + " " + operator + " '" + filterConfig.getValues().get(0) +  "' "; 
								}
		            			
							}else if (expressionItems[0].contains("edate")&&datasetId!=79) {
								eDate=filterConfig.getValues().get(0);
								
								if (sDate.equals(now)) {
									//假如开始时间和当前时间一样，则判断为月初，则将系统当前时间作为开始时间；
							    	replaceExpression = " and " + expressionItems[1] + " " + operator + " '" + now +  "' "; 
		            				
								}else{
									replaceExpression = " and " + expressionItems[1] + " " + operator + " '" + filterConfig.getValues().get(0) +  "' "; 
								}
	            				
							}
							else {
								replaceExpression = " and " + expressionItems[1] + " " + operator + " '" + filterConfig.getValues().get(0) +  "' "; 
	            				
							}
	            			
	            			
	            		}
	            		
	            		if(filterConfig.getValues().size() > 0)
	            			queryStr = queryStr.replace(replaceTarget, replaceExpression);
            		}
            		
            		filters.remove(filter);
            		idx -=1 ;
            	}
            }
            
            query = new HashMap<String, String>();
            System.out.println("sql:"+queryStr);
            query.put("sql", queryStr);
            dataProvider.setQuery(query);
            return dataProvider.getAggData(config, reload);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CBoardException(e.getMessage());
        }
    }

    public DataProviderResult getColumns(Long datasourceId, Map<String, String> query, Long datasetId, boolean reload) {
        DataProviderResult dps = new DataProviderResult();
        try {
            Dataset dataset = getDataset(datasetId);
            DataProvider dataProvider = getDataProvider(datasourceId, query, dataset);
            String[] result = dataProvider.getColumn(reload);
            InjectionParam[] params = dataProvider.getInjectionParams(reload);
            dps.setColumns(result);
            dps.setInjectionParams(params);
            dps.setMsg("1");
        } catch (Exception e) {
            e.printStackTrace();
            dps.setMsg(e.getMessage());
        }
        return dps;
    }

    public String[] getDimensionValues(Long datasourceId, Map<String, String> query, Long datasetId, String columnName, AggConfig config, boolean reload) {
    	String[] result = null;
        try {
            Dataset dataset = getDataset(datasetId);
            attachCustom(dataset, config);
            DataProvider dataProvider = getDataProvider(datasourceId, query, dataset);
            
            if(columnName.contains(":")) {
            	String[] elements = columnName.split(":");
            	if(elements.length == 3) {
            		columnName = elements[2];
            	} else if(elements.length == 2) {
            		return result;
            	}
            }
            if(!columnName.isEmpty()) {
            	result = dataProvider.getDimVals(columnName, config, reload);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String viewAggDataQuery(Long datasourceId, Map<String, String> query, Long datasetId, AggConfig config) {
        try {
            Dataset dataset = getDataset(datasetId);
            attachCustom(dataset, config);
            DataProvider dataProvider = getDataProvider(datasourceId, query, dataset);
            return dataProvider.getViewAggDataQuery(config);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CBoardException(e.getMessage());
        }
    }

    public ServiceStatus test(JSONObject dataSource, Map<String, String> query) {
        try {
            DataProvider dataProvider = DataProviderManager.getDataProvider(dataSource.getString("type"),
                    Maps.transformValues(dataSource.getJSONObject("config"), Functions.toStringFunction()), query);
            dataProvider.getData();
            return new ServiceStatus(ServiceStatus.Status.Success, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ServiceStatus(ServiceStatus.Status.Fail, e.getMessage());
        }
    }

    private void attachCustom(Dataset dataset, AggConfig aggConfig) {
        if (dataset == null || aggConfig == null) {
            return;
        }
        Consumer<DimensionConfig> predicate = (config) -> {
            if (StringUtils.isNotEmpty(config.getId())) {
                String custom = (String) JSONPath.eval(dataset.getSchema(), "$.dimension[id='" + config.getId() + "'][0].custom");
                if (custom == null) {
                    custom = (String) JSONPath.eval(dataset.getSchema(), "$.dimension[type='level'].columns[id='" + config.getId() + "'][0].custom");
                }
                config.setCustom(custom);
            }
        };
        aggConfig.getRows().forEach(predicate);
        aggConfig.getColumns().forEach(predicate);
    }

    protected Dataset getDataset(Long datasetId) {
        if (datasetId == null) {
            return null;
        }
        return new Dataset(datasetDao.getDataset(datasetId));
    }

    protected class Dataset {
        private Long datasourceId;
        private Map<String, String> query;
        private Long interval;
        private JSONObject schema;

        public Dataset(DashboardDataset dataset) {
            JSONObject data = JSONObject.parseObject(dataset.getData());
            this.query = Maps.transformValues(data.getJSONObject("query"), Functions.toStringFunction());
            this.datasourceId = data.getLong("datasource");
            this.interval = data.getLong("interval");
            this.schema = data.getJSONObject("schema");
        }

        public JSONObject getSchema() {
            return schema;
        }

        public void setSchema(JSONObject schema) {
            this.schema = schema;
        }

        public Long getDatasourceId() {
            return datasourceId;
        }

        public void setDatasourceId(Long datasourceId) {
            this.datasourceId = datasourceId;
        }

        public Map<String, String> getQuery() {
            return query;
        }

        public void setQuery(Map<String, String> query) {
            this.query = query;
        }

        public Long getInterval() {
            return interval;
        }

        public void setInterval(Long interval) {
            this.interval = interval;
        }
    }
}
