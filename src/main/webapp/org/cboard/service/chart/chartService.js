/**
 * Created by yfyuan on 2016/10/28.
 */
'use strict';
cBoard.service('chartService', function ($q, dataService, chartPieService, chartLineService, chartFunnelService,
                                         chartSankeyService, chartTableService, chartKpiService, chartRadarService,
                                         chartMapService, chartScatterService, chartGaugeService, chartWordCloudService,
                                         chartTreeMapService, chartAreaMapService, chartHeatMapCalendarService, chartHeatMapTableService,
                                         chartLiquidFillService, chartMarkLineMapService, $rootScope) {

        this.render = function (containerDom, widget, optionFilter, scope, reload, persist,ModalUtils) {
            var deferred = $q.defer();
            var chart = getChartServices(widget.config);
            dataService.getDataSeries(widget.datasource, widget.query, widget.datasetId, widget.config, function (data) {     
            	
                try {
                    var option = chart.parseOption(data);
                    if (optionFilter) {
                        optionFilter(option);
                    }
                    if (data.drill) {
                        data.drill.drillDown = function (id, value, render) {
                            dataService.getDrillPath(widget.datasetId, id).then(function (path) {
                                var i = 0;
                                _.each(path, function (e, _i) {
                                    if (e.id == id) {
                                        i = _i;
                                    }
                                });
                                var node = path[++i];
                                _.find(widget.config.keys, function (e, _i) {
                                    if (e.id == id) {
                                        e.type = '=';
                                        e.values = [value];
                                        if (!_.find(widget.config.keys, function (e) {
                                                return e.id == node.id;
                                            })) {
                                            widget.config.keys.splice(_i + 1, 0, node);
                                        }
                                        return true;
                                    }
                                });
                                _.find(widget.config.groups, function (e, _i) {
                                    if (e.id == id) {
                                        e.type = '=';
                                        e.values = [value];
                                        if (!_.find(widget.config.groups, function (e) {
                                                return e.id == node.id;
                                            })) {
                                            widget.config.groups.splice(_i + 1, 0, node);
                                        }
                                        return true;
                                    }
                                });
                                dataService.getDataSeries(widget.datasource, widget.query, widget.datasetId, widget.config, function (data) {
                                    var option = chart.parseOption(data);
                                    if (optionFilter) {
                                        optionFilter(option);
                                    }
                                    render(option, data.drill.config);
                                });
                            });
                        };
                        data.drill.drillUp = function (id, render) {
                            _.find(widget.config.keys, function (e, _i) {
                                if (e.id == id) {
                                    widget.config.keys[_i - 1].values = [];
                                    widget.config.keys.splice(_i, 1);
                                    return true;
                                }
                            });
                            _.find(widget.config.groups, function (e, _i) {
                                if (e.id == id) {
                                    widget.config.groups[_i - 1].values = [];
                                    widget.config.groups.splice(_i, 1);
                                    return true;
                                }
                            });
                            dataService.getDataSeries(widget.datasource, widget.query, widget.datasetId, widget.config, function (data) {
                                var option = chart.parseOption(data);
                                if (optionFilter) {
                                    optionFilter(option);
                                }
                                render(option, data.drill.config);
                            });
                        };
                    }
                } finally {
                    deferred.resolve(chart.render(containerDom, option, scope, persist, data.drill,ModalUtils));
                }
            }, reload);
            return deferred.promise;
        };

        this.realTimeRender = function (realTimeTicket, widget, optionFilter, scope, ModalUtils) {
            if (!realTimeTicket) {
                return;
            }
            var chart = getChartServices(widget.config);
            dataService.getDataSeries(widget.datasource, widget.query, widget.datasetId, widget.config, function (data) {
                var option = chart.parseOption(data);
                if (optionFilter) {
                    optionFilter(option);
                }
                //与图表有联动的参数，将参数值写回到图表的选择值
                for(var rname in scope.board.layout.rows){
                	var row = scope.board.layout.rows[rname];
                	if(row.type == "param"){
    	                for(var pname in row.params){
    	                	var param = row.params[pname];
    	                	if(param.linkage == option.name){
    	                		for(var sname in option.series){
    	                			var serie = option.series[sname];
    	                			for(var dname in serie.data){
    	                				var data = serie.data[dname];
    	                				for(var vname in param.values){
    	                					var value = param.values[vname];
    	                					if(data.name == value){
    	                						data.selected = true;
    	                					}
    	                				}
    	                			}
    	                		}
    	                	}
	                    }
	                }
                }
                realTimeTicket(option, data.drill ? data.drill.config : null);
                
                $rootScope.widgetsReqCount =parseInt($rootScope.widgetsReqCount)-1;
                if(parseInt($rootScope.widgetsReqCount) <= 0 && ModalUtils) {
                	ModalUtils.close();
                }
            });
        };

        var getChartServices = function (chartConfig) {
            var chart;
            switch (chartConfig.chart_type) {
                case 'line':
                    chart = chartLineService;
                    break;
                case 'pie':
                    chart = chartPieService;
                    break;
                case 'kpi':
                    chart = chartKpiService;
                    break;
                case 'table':
                    chart = chartTableService;
                    break;
                case 'funnel':
                    chart = chartFunnelService;
                    break;
                case 'sankey':
                    chart = chartSankeyService;
                    break;
                case 'radar':
                    chart = chartRadarService;
                    break;
                case 'map':
                    chart = chartMapService;
                    break;
                case 'scatter':
                    chart = chartScatterService;
                    break;
                case 'gauge':
                    chart = chartGaugeService;
                    break;
                case 'wordCloud':
                    chart = chartWordCloudService;
                    break;
                case 'treeMap':
                    chart = chartTreeMapService;
                    break;
                case 'areaMap':
                    chart = chartAreaMapService;
                    break;
                case 'heatMapCalendar':
                    chart = chartHeatMapCalendarService;
                    break;
                case 'heatMapTable':
                    chart = chartHeatMapTableService;
                    break;
                case 'markLineMap':
                    chart = chartMarkLineMapService;
                     break;
                case 'liquidFill':
                    chart = chartLiquidFillService;
                    break;
            }
            return chart;
        };

    }
);
