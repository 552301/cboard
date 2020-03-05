/**
 * Created by yfyuan on 2016/8/2.
 */

cBoard.controller('dashboardViewCtrl', function ($timeout, $rootScope, $scope, $state, $stateParams, $http, ModalUtils, chartService, $interval, $uibModal, dataService) {

    $scope.loading = true;
    $scope.paramInit = 0;
    $http.get("dashboard/getDatasetList.do").success(function (response) {
        $scope.datasetList = response;
        $scope.realtimeDataset = {};
        $scope.datasetMeta = {};
        $scope.intervals = [];
        $scope.datasetFilters = {};
        $scope.widgetFilters = {};
        $scope.load(false);
    });

    $scope.timelineColor = ['bg-light-blue', 'bg-red', 'bg-aqua', 'bg-green', 'bg-yellow', 'bg-gray', 'bg-navy', 'bg-teal', 'bg-purple', 'bg-orange', 'bg-maroon', 'bg-black'];

    var groupTimeline = function () {
        $scope.timeline = [];
        var group = undefined;
        _.each($scope.board.layout.rows, function (row, idx) {
            if (idx == 0) {
                $scope.timelineFilter = row;
                return;
            }
            row.show = false;
            if (row.node == 'parent') {
                if (group) {
                    $scope.timeline.push(group);
                }
                group = [];
                row.show = true;
            }
            group.push(row);
        });
        $scope.timeline.push(group);
    };

    $scope.openCloseParentNode = function (group) {
        var find = _.find(group, function (row) {
            return row.node != 'parent' && row.show;
        });
        if (find) {
            _.each(group, function (row) {
                if (row.node != 'parent') {
                    row.show = false;
                    _.each(row.widgets, function (widget) {
                        widget.show = false;
                    });
                }
            });
        } else {
            _.each(group, function (row) {
                if (row.node != 'parent') {
                    row.show = true;
                    _.each(row.widgets, function (widget) {
                        widget.show = true;
                    });
                }
            });
        }
    };

    $scope.openCloseNode = function (row) {
        if (row.show) {
            row.show = false;
            _.each(row.widgets, function (widget) {
                widget.show = false;
            });
        } else {
            row.show = true;
            _.each(row.widgets, function (widget) {
                widget.show = true;
            });
        }
    };

    $http.post("admin/isConfig.do", {type: 'widget'}).success(function (response) {
        $scope.widgetCfg = response;
    });

    var buildRender = function (w, reload) {
        w.render = function (content, optionFilter, scope) {
            chartService.render(content, injectFilter(w.widget).data, optionFilter, scope, reload, null,ModalUtils).then(function (d) {
                w.realTimeTicket = d;
                w.loading = false;
                $scope.showOrCloseScreenWait(false);
            });
            w.realTimeOption = {optionFilter: optionFilter, scope: scope};
        };
        w.modalRender = function (content, optionFilter, scope) {
            w.modalRealTimeTicket = chartService.render(content, injectFilter(w.widget).data, optionFilter, scope);
            w.modalRealTimeOption = {optionFilter: optionFilter, scope: scope};
        };
    };

    $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState) {
            if (fromState.controller == 'dashboardViewCtrl') {
                _.each($scope.intervals, function (i) {
                    $interval.cancel(i);
                })
            }
        }
    );

    $scope.export = function () {
        if ($scope.exportStatus) {
            return;
        }
        $scope.exportStatus = true;
        $http({
            url: "dashboard/exportBoard.do?id=" + $stateParams.id,
            method: "POST",
            headers: {
                'Content-type': 'application/json'
            },
            responseType: 'arraybuffer'
        }).success(function (data) {
            var blob = new Blob([data], {type: "application/vnd.ms-excel"});
            var objectUrl = URL.createObjectURL(blob);
            var aForExcel = $("<a><span class='forExcel'>下载excel</span></a>").attr("href", objectUrl);
            aForExcel.attr("download", $scope.board.name);
            $("body").append(aForExcel);
            $(".forExcel").click();
            aForExcel.remove();
            $scope.exportStatus = false;
        }).error(function (data, status, headers, config) {
            $scope.exportStatus = false;
        });
    };

    var refreshParam = function () {
        _.each($scope.board.layout.rows, function (row) {
            _.each(row.params, function (param) {
                if (param.refresh) {
                    param.refresh();
                }
            });
        });
        paramToFilter();
    };

    var loadWidget = function (reload) {
        paramToFilter();
        $scope.tableCurrentPageDic = new Array();
        $scope.linkageDic = new Array();
        _.each($scope.board.layout.rows, function (row) {
            _.each(row.widgets, function (widget) {
                if (!_.isUndefined(widget.hasRole) && !widget.hasRole) {
                    return;
                }
                
                //将widget的name放到config里面，方便操作widget
                //widget.data.config.name = widget.name;
                
                buildRender(widget, reload);
                widget.loading = true;
                if ($scope.board.layout.type == 'timeline') {
                    if (row.show) {
                        widget.show = true;
                    }
                } else {
                    widget.show = true;
                }
                //real time load task
                var w = widget.widget.data;
                var ds = _.find($scope.datasetList, function (e) {
                    return e.id == w.datasetId;
                });
                if (ds && ds.data.interval && ds.data.interval > 0) {
                    if (!$scope.intervalGroup[w.datasetId]) {
                        $scope.intervalGroup[w.datasetId] = [];
                        $scope.intervals.push($interval(function () {
                            refreshParam();
                            _.each($scope.intervalGroup[w.datasetId], function (e) {
                                e();
                            });
                        }, ds.data.interval * 1000));
                    }
                    $scope.intervalGroup[w.datasetId].push(function () {
                        try {
                            if (widget.show) {
                                chartService.realTimeRender(widget.realTimeTicket, injectFilter(widget.widget).data, $scope);
                                if (widget.modalRealTimeTicket) {
                                    chartService.realTimeRender(widget.modalRealTimeTicket, injectFilter(widget.widget).data, widget.modalRealTimeOption.optionFilter, null);
                                }
                            }
                        } catch (e) {
                            console.error(e);
                        }
                    });
                }
            });
        });
    };

    var paramInitListener;
    $scope.load = function (reload) {
        $scope.paramInit = 0;
        $scope.loading = true;
        _.each($scope.intervals, function (e) {
            $interval.cancel(e);
        });
        $scope.intervals = [];

        if ($scope.board) {
            _.each($scope.board.layout.rows, function (row) {
                _.each(row.widgets, function (widget) {
                    widget.show = false;
                });
            });
        }
        $http.get("dashboard/getBoardData.do?id=" + $stateParams.id).success(function (response) {
            $scope.intervalGroup = {};
            $scope.loading = false;
            $scope.board = response;
            _.each($scope.board.layout.rows, function (row) {
            	if(row.type == "param"){
	                _.each(row.params, function (param) {
	                    if (!param.paramType) {
	                        param.paramType = 'selector';
	                    }
	                    
	                    if(param.paramType == 'textor'){
	                		if(typeof(param.textvalue) == 'undefined')
	                			if(typeof(param.defaultval) != 'undefined')
	                				if(param.defaultval != null)
		                				if(param.defaultval.length > 0)
		                					param.textvalue = param.defaultval;
	                	} else if(param.paramType == 'datepicker'){
	                		if(typeof(param.textvalue) == 'undefined'){
	                			if(typeof(param.defaultval) != 'undefined'){
	                				if(param.defaultval.length > 0){
		                				if(typeof(param.cfg) != 'undefined'){
		        		                    if(typeof(param.cfg.formatter) != 'undefined'){
		        		                    	if(param.defaultval.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/)){
		        		                    		param.textvalue = new Date(param.defaultval).Format(param.cfg.formatter);
		        		                    	}else{
		        		                    		param.textvalue = expressionAnalysis(param.defaultval, param.cfg.formatter);
		        		                    	}		                    		
		    		                    	}
		                				}
	                				}
	                			}else{
	    	                    	param.textvalue = param.defaultval;
	    	                    }
	                		}
	                	}
	                });
            	}
            	
                if(row.type == "widget"){
                	for(var wname in row.widgets){
                		row.widgets[wname].widget.data.config.name = row.widgets[wname].name;
                	}
	            }
            });
            
            if (paramInitListener) {
                paramInitListener(reload);
            }
            _.each($scope.board.layout.rows, function (row) {
                _.each(row.params, function (param) {
                    $scope.paramInit++;
                });
            });
            if ($scope.board.layout.type == 'timeline') {
                groupTimeline();
            }
            if ($scope.paramInit == 0) {
                loadWidget(reload);
            }
            paramInitListener = $scope.$on('paramInitFinish', function (e, d) {
                $scope.paramInit--;
                if ($scope.paramInit == 0) {
                    loadWidget(reload);
                }            	
            });
        });
    };

    var injectFilter = function (widget) {
        widget.data.config.boardFilters = [];
        if (_.isUndefined(widget.data.datasetId)) {
            widget.data.config.boardFilters = $scope.widgetFilters[widget.id];
        } else {
            widget.data.config.boardFilters = $scope.datasetFilters[widget.data.datasetId];
        }
        return widget;
    };

    var paramToFilter = function () {
        $scope.widgetFilters = [];
        $scope.datasetFilters = [];
        _.each($scope.board.layout.rows, function (row) {
            _.each(row.params, function (param) {
            	if(param.paramType == 'textor' || param.paramType == 'datepicker'){
            		if(typeof(param.textvalue) != 'undefined')
            			if(param.textvalue.length > 0)
            				param.values = [param.textvalue];
            	} else if(param.paramType == 'selector'){
             		if(typeof(param.defaultval) != 'undefined') 
             			if(param.defaultval != null)
	             			if(param.defaultval.length > 0)
	             				param.values = [param.defaultval];
             	}            	
                _.each(param.col, function (col) {                	
                    var p = {
                        col: col.column,
                        type: param.type,
                        values: param.values
                    };
                    if (_.isUndefined(col.datasetId)) {
                        if (!$scope.widgetFilters[col.widgetId]) {
                            $scope.widgetFilters[col.widgetId] = [];
                        }
                        $scope.widgetFilters[col.widgetId].push(p);
                    } else {
                        if (!$scope.datasetFilters[col.datasetId]) {
                            $scope.datasetFilters[col.datasetId] = [];
                        }
                        $scope.datasetFilters[col.datasetId].push(p);
                    }
                });
            });
        });
    };

    $scope.applyParamFilter = function () {	
    	_.each($scope.board.layout.rows, function (row) {
            _.each(row.params, function (param) {
	        	if(param.defaultval){ 
	        		param.defaultval = null; //应用参数的时候（即非第一次加载）将所有参数的默认值去掉
	        	}
	        	
	        	if(param.paramType == 'datepicker'){
		        	if(typeof(param.cfg) != 'undefined'){
		        		if(typeof(param.cfg.formatter) != 'undefined'){
		        			if(typeof(param.textvalue) != 'undefined'){
		                    	if(param.textvalue.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/)){
		                    		param.textvalue = new Date(param.textvalue.replaceAll('-', '/')).Format(param.cfg.formatter);
		                    	}
		                    }
			        	}
		        	}
	        	}
	        	
	        	if(param.paramType == 'textor' || param.paramType == 'datepicker') {
	        		param.values = [];
	        		if(typeof(param.textvalue) != 'undefined'){
	        			if(param.textvalue.length > 0){
	        				param.values = [param.textvalue];
	        			}
	        		}
	        	}
            });
    	});
    	
        paramToFilter();
        // 参数多次请求， 统计请求次数
        $rootScope.widgetsReqCount = 0;
        _.each($scope.board.layout.rows, function (row) {
        	if(row.widgets) {
        		$rootScope.widgetsReqCount = parseInt($rootScope.widgetsReqCount) + row.widgets.length;
        	}
        });
        	$scope.showOrCloseScreenWait($rootScope.widgetsReqCount > 0);

        	_.each($scope.board.layout.rows, function (row) {
            _.each(row.widgets, function (w) {
                try {
                    chartService.realTimeRender(w.realTimeTicket, injectFilter(w.widget).data, null, $scope,ModalUtils);
                } catch (e) {
                    console.error(e);
                }
            });
        });
        updateParamTitle();
    };
    // 提示框
    $scope.showOrCloseScreenWait = function( isShow ) {
    	if(!ModalUtils) return;
    	if(ModalUtils.close){
    		ModalUtils.close();
    	}
    	if(isShow) {
    		ModalUtils.wait("正在加载数据，请耐心等待...", "modal-warning", "lg"); // 点击查询，弹框提示等待
    	}
    };
    
    $scope.paramToString = function (row) {
        return _.filter(_.map(row.params, function (e) {
            return e.title;
        }), function (e) {
            return e && e.length > 0;
        }).join('; ');
    };

    $scope.modalChart = function (widget) {
        $uibModal.open({
            templateUrl: 'org/cboard/view/util/modal/chart.html',
            windowTemplateUrl: 'org/cboard/view/util/modal/window.html',
            windowClass: 'modal-fit',
            backdrop: false,
            controller: function ($scope, $uibModalInstance, chartService) {
                $scope.widget = widget;
                $scope.close = function () {
                    $uibModalInstance.close();
                    delete widget.modalRealTimeTicket;
                    delete widget.modalRealTimeOption;
                };
                $scope.render1 = function () {
                    widget.modalRender($('#modal_chart'), function (option) {
                        option.toolbox = {
                            feature: {
                                //saveAsImage: {},
                                dataView: {
                                    show: true,
                                    readOnly: true
                                },
                                magicType: {
                                    type: ['line', 'bar', 'stack', 'tiled']
                                },
                                dataZoom: {
                                    show: true
                                },
                                restore: {
                                    show: true
                                }
                            }
                        };
                    }, null);
                };
            }
        });
    };

    $scope.modalTable = function (widget) {
        $uibModal.open({
            templateUrl: 'org/cboard/view/util/modal/chart.html',
            windowTemplateUrl: 'org/cboard/view/util/modal/window.html',
            windowClass: 'modal-fit',
            backdrop: false,
            controller: function ($scope, $uibModalInstance, chartService) {
                $scope.widget = widget;
                $scope.close = function () {
                    $uibModalInstance.close();
                };
                $scope.render1 = function () {
                    widget.modalRender($('#modal_chart'), null, null);
                };
            }
        });
    };

    $scope.config = function (widget) {
        $state.go('config.widget', {id: widget.widget.id});
    };

    $scope.reload = function (widget) {
        widget.show = false;
        widget.render = function (content, optionFilter, scope) {
            chartService.render(content, widget.widget.data, optionFilter, scope, true).then(function (d) {
                widget.realTimeTicket = d;
                widget.loading = false;
            });
            widget.realTimeOption = {optionFilter: optionFilter, scope: scope};
        };
        $timeout(function () {
            widget.loading = true;
            widget.show = true;
        });
    };

    $http.get("dashboard/getBoardParam.do?boardId=" + $stateParams.id).success(function (response) {
        if (response) {
            $scope.boardParams = JSON.parse(response.config);
        } else {
            $scope.boardParams = [];
        }
    });

    $scope.newBoardParam = function (name) {
        if (name == '') {
            return;
        }
        var params = {};
        _.each($scope.board.layout.rows, function (row) {
            _.each(row.params, function (param) {
                if ('slider' != param.paramType) {
                    params[param.name] = {type: param.type, values: param.values};
                }
            });
        });
        $scope.boardParams.unshift({name: name, params: params});
        $http.post("dashboard/saveBoardParam.do", {
            boardId: $stateParams.id,
            config: angular.toJson($scope.boardParams)
        }).success(function (response) {
        });
    };

    $scope.deleteBoardParam = function (index) {
        $scope.boardParams.splice(index,1);
        $http.post("dashboard/saveBoardParam.do", {
            boardId: $stateParams.id,
            config: angular.toJson($scope.boardParams)
        }).success(function (response) {
        });
    };

    $scope.applyBoardParam = function (param) {
        for (var name in param) {
            _.each($scope.board.layout.rows, function (row) {
                _.each(row.params, function (p) {
                    if (p.name == name) {
                        p.type = param[name].type;
                        p.values = param[name].values;
                        
                        if(p.paramType == 'textor' || p.paramType == 'datepicker') //文本类型和日期类型如果有值就应用到参数上
                        	if(p.values.length > 0)
                        		p.textvalue = param[name].values[0];
                    }
                });
            });
        }
        $scope.applyParamFilter();
    };

    $scope.linkageDic = new Array();//联动图表和参数对应的字典
    
    $scope.tableCurrentPageDic = new Array();
    var updateParamTitle = function () {
        _.each($scope.board.layout.rows, function (row) {
            _.each(row.params, function (param) {
                if ('slider' == param.paramType) {
                    return;
                }
                var paramObj;
                switch (param.type) {
                    case '=':
                    case '≠':
                        paramObj = param.name + ' ' + param.type + ' (' + param.values + ')';
                        break;
                    case '>':
                    case '<':
                    case '≥':
                    case '≤':
                        paramObj = param.name + ' ' + param.type + ' ' + param.values;
                        break;
                    case '(a,b]':
                    case '[a,b)':
                    case '(a,b)':
                    case '[a,b]':
                        var leftBrackets = param.type.split('a')[0];
                        var rightBrackets = param.type.split('b')[1];
                        paramObj = param.name + ' between ' + leftBrackets + param.values[0] + ',' + param.values[1] + rightBrackets;
                        break;
                }
                param.title = param.values.length > 0 ? paramObj : undefined;
            });
        });
    }
    
    //日期表达式解析
    //现在只支持 now - 7D 这样的形式
    //运算符支持 + -
    //单位支持 D M Y
    var expressionAnalysis = function(expression, fomattor){
    	var theDay = new Date();
    	var elements = expression.split(' ');
    	if(elements.length != 3){
    		return theDay.Format(fomattor);
    	}    	

    	var unit = elements[2].substring(elements[2].length -1,elements[2].length);
    	var val = parseInt(elements[2].replace(unit,''));
    	var operator = elements[1];
    	
    	if(operator == '+'){
    		switch(unit){
    		case 'Y':
    			theDay.setYear(theDay.getYear() + val);
    			break;
    		case 'M':
				theDay.setMonth(theDay.getMonth() + val);
    			break;
    		case 'D':
    			 theDay.setDate(theDay.getDate() + val);
    			 break;
    		}
    		
    	}else if(operator == '-'){
    		switch(unit){
    		case 'Y':
    			theDay.setYear(theDay.getYear() - val);
    			break;
    		case 'M':
				theDay.setMonth(theDay.getMonth() - val);
    			break;
    		case 'D':
    			 theDay.setDate(theDay.getDate() - val);
    			 break;
    		}
    	}else{
    		
    	}
    	
    	return theDay.Format(fomattor);
    }

});