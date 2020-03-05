/**
 * Created by yfyuan on 2017/3/28.
 */
'user strict';
cBoard.controller('paramCtrl', function ($scope, $uibModal, $http) {

    var evalValue = function (value) {
        if (isNaN(Number(value))) {
            var now = function (i, key) {
                if (i == undefined) {
                    return +moment();
                }
                return +moment().add(i, key);
            };
            var interval = function (i, key) {
                if (i == undefined) {
                    i == 1
                }
                var y = 0;
                switch (key) {
                    case 'h':
                        y = 60 * 60 * 1000;
                        break;
                    case 'd':
                        y = 24 * 60 * 60 * 1000;
                        break;
                    case 'm':
                        y = 60 * 1000;
                        break;
                    case 's':
                        y = 1000;
                        break;
                }
                return i * y;
            };
            return eval(value);
        } else {
            return value;
        }
    };

    var formatter = function (d, f) {
        if (_.isUndefined(f)) {
            return d;
        } else {
            return moment(d).format(f);
        }
    };

    $scope.init = function () {
        $scope.param = $scope.$parent.param;
        $scope.param.selects = [];
        $scope.param.type = '=';
        $scope.param.values = [];
        if ($scope.param.paramType == 'slider') {
            var cfg = $scope.param.cfg;
            var _max = evalValue(_.result(cfg, 'max', null));
            var _min = evalValue(_.result(cfg, 'min', null));
            var apply = _.debounce($scope.$parent.applyParamFilter, 800);
            $scope.slider = {
                minValue: _max - Number(evalValue(_.result(cfg, 'range', 0))),
                maxValue: _max,
                options: {
                    floor: _min,
                    ceil: _max,
                    draggableRange: true,
                    enforceStep: false,
                    maxRange: Number(evalValue(_.result(cfg, 'maxRange', null))),
                    step: evalValue(_.result(cfg, 'step', 1 * 60 * 1000)),
                    translate: function (value) {
                        return formatter(value, cfg.formatter);
                    },
                    onChange: function (sliderId, modelValue, highValue, pointerType) {
                        $scope.param.type = '[a,b]';
                        $scope.param.values = [formatter(modelValue, cfg.value_fmt), formatter(highValue, cfg.value_fmt)];
                        apply();
                    }
                }
            };
            $scope.param.type = '[a,b]';
            $scope.param.values = [formatter($scope.slider.minValue, cfg.value_fmt), formatter($scope.slider.maxValue, cfg.value_fmt)];
            $scope.param.refresh = function () {
                if ($scope.slider.maxValue == $scope.slider.options.ceil) {
                    var _range = $scope.slider.maxValue - $scope.slider.minValue;
                    var cfg = $scope.param.cfg;
                    var max = evalValue(_.result(cfg, 'max', null));
                    var min = evalValue(_.result(cfg, 'min', null));
                    $scope.slider.maxValue = max;
                    $scope.slider.minValue = max - _range;
                    $scope.slider.options.floor = min;
                    $scope.slider.options.ceil = max;
                    $scope.param.type = '[a,b]';
                    $scope.param.values = [formatter($scope.slider.minValue, cfg.value_fmt), formatter($scope.slider.maxValue, cfg.value_fmt)];
                }
            }
        } else {
            _.each($scope.param.col, function (c) {
                var p;
                if (_.isUndefined(c.datasetId)) {
                    _.each($scope.board.layout.rows, function (row) {
                        _.each(row.widgets, function (widget) {
                            if (widget.widget.id == c.widgetId) {
                                p = {
                                    datasourceId: widget.widget.data.datasource,
                                    query: angular.toJson(widget.widget.data.query),
                                    datasetId: null
                                };
                            }
                        });
                    });
                } else {
                    p = {datasourceId: null, query: null, datasetId: c.datasetId};
                }

                if($scope.param.paramType == 'selector' || $scope.param.paramType == 'textor'){
	                $http.post("dashboard/getDimensionValues.do", {
	                    datasourceId: p.datasourceId,
	                    query: p.query,
	                    datasetId: p.datasetId,
	                    colmunName: c.column
	                }).success(function (response) {
	                    _.each(response, function (s) {
	                        if (_.indexOf($scope.param.selects, s) < 0) {
	                            $scope.param.selects.push(s);
	                        }
	                    });
	                   
	                	if($scope.param.paramType == 'textor'){
	                		$('#txt_param_' + $scope.param.name).autocomplete({
	                		      source: $scope.param.selects
	                	    });
	                	}	
	                });
                }
                
                if($scope.param.paramType == 'datepicker')
                		if(typeof($scope.param.cfg) != 'undefined')
                			if(typeof($scope.param.cfg) != 'undefined')
                				$scope.param.type = $scope.param.cfg.filterType;
            });
        }
        
        if(typeof($scope.param.defaultval) != 'undefined'){
	        if($scope.param.defaultval.indexOf('##') == 0){ //如果参数是以##开头，即表明是全局变量,需要调用后台getSystemParam
	        	$http.get("systemParam/getSystemParam.do?name=" + $scope.param.defaultval.replace('##','')).success(function (response) {
	        		$scope.param.values = response;
	        		$scope.param.defaultval = '';
	        		$scope.param.textvalue = '';
	        		
	        		$scope.$parent.param.values = response;
	        		$scope.$parent.param.defaultval = '';
	        		$scope.$parent.param.textvalue = '';
	        		
	        		$scope.$emit('paramInitFinish', $scope.param);
	        	});
	        } else{
	        	$scope.$emit('paramInitFinish', $scope.param);
	        }
        } else{
        	$scope.$emit('paramInitFinish', $scope.param);
        }
    };

    $scope.editParam = function () {
        $uibModal.open({
            templateUrl: 'org/cboard/view/dashboard/modal/param.html',
            windowTemplateUrl: 'org/cboard/view/util/modal/window.html',
            backdrop: false,
            size: 'lg',
            resolve: {
                param: function () {
                    if ($scope.param) {
                        return angular.copy($scope.param);
                    } else {
                        return {type: '='}
                    }
                },
                filter: function () {
                    return false;
                },
                getSelects: function () {
                    return function (byFilter, column, callback) {
                        callback($scope.param.selects);
                    };
                },
                ok: function () {
                    return function (param) {
                        $scope.param.values = param.values;
                        $scope.param.type = param.type;
                        $scope.applyParamFilter();
                    }
                }
            },
            controller: 'paramSelector'
        });
    };

});