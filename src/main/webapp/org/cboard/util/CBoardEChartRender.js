/**
 * Created by zyong on 2016/7/25.
 */

var echartsBasicOption = {
    title: {},
    grid: {
        left: '50',
        right: '20',
        bottom: '15%',
        top: '15%',
        containLabel: false
    },
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        x: 'left',
        itemWidth: 15,
        itemHeight: 10
    },
    name :""
};

var CBoardEChartRender = function (jqContainer, options, isDeepSpec, scope) {
    this.container = jqContainer; // jquery object
    this.ecc = echarts.init(jqContainer.get(0), this.theme);
    this.isDeppSpec = isDeepSpec;

    this.basicOption = echartsBasicOption;
    this.options = options;
    
    //遍历board的所有参数，如果linkage的名称和图表的名称一样，
    //则添加单击事件，以实现联动
    if(typeof(scope) != 'undefined'){
    	if(typeof(scope.board) == 'undefined')
    		return;
    	
	    var row = 0;
	    var paramIndex = 0;
	    var chartName = this.options.name;

	    for(row = 0; row < scope.board.layout.rows.length; row++){
	    	if(scope.board.layout.rows[row].params){
	    		for(paramIndex = 0; paramIndex < scope.board.layout.rows[row].params.length; paramIndex++){
	    			var param = scope.board.layout.rows[row].params[paramIndex];
	    			if(param.linkage == chartName){
	    				this.ecc.cid = chartName;
	    				scope.linkageDic[this.ecc.cid] = param.name;
	    				for(var sname in options.series){
	    					var serie = options.series[sname];
    						if(serie.type == 'pie'){ //为饼图绑定参数联动
    							this.ecc.on('pieselectchanged', function(tag){
									var ps = new Array(); //参数字典
									var p = new Object(); //参数
									p.name = scope.linkageDic[this.cid]; //参数名称
									p.type = '='; //参数类型
									p.values = [];
									
									for(var item in tag.selected){
										if(tag.selected[item]){
											p.values.push(item);
										}
									}
									
									ps[p.name] = p; //将参数加入字典
									scope.applyBoardParam(ps); //调用scope的函数应用参数
    							});
							} else if(serie.type == 'bar'){ //为柱状图绑定参数联动
    							this.ecc.on('click', function(tag){
									var ps = new Array(); //参数字典
									var p = new Object(); //参数
									p.name = scope.linkageDic[this.cid]; //参数名称
									p.type = '='; //参数类型
									p.values = [];
									p.values.push(tag.name);
									
									ps[p.name] = p; //将参数加入字典
									scope.applyBoardParam(ps); //调用scope的函数应用参数
    							});
							} else if(serie.type == 'heatmap'){ //日历图的参数联动
								this.ecc.on('click', function(tag){
									if(typeof(tag.value) == 'undefined')
										return;
									
									if(tag.value.length == 0)
										return;
									
									var ps = new Array(); //参数字典
									var p = new Object(); //参数
									p.name = scope.linkageDic[this.cid]; //参数名称
									p.type = '='; //参数类型
									p.values = [];
									p.values.push(tag.value[0]);
									
									ps[p.name] = p; //将参数加入字典
									scope.applyBoardParam(ps); //调用scope的函数应用参数
								});
							}
						}
	    			}
	    		}
	    	}
	    }
    }
};

CBoardEChartRender.prototype.theme = "theme-fin1"; // 主题

CBoardEChartRender.prototype.chart = function (group, persist) {
    var self = this;
    var options = this.isDeppSpec == true ? self.options : $.extend(true, {}, self.basicOption, self.options);
    if (options.visualMap != undefined) {
        $(this.jqContainer).css({
            height: 500 + "px",
            width: '100%'
        });
    }
    if (options.legend.data && options.legend.data.length > 35) {
        options.grid.top = '5%';
        options.legend.show =false;
    }
    if(persist){
        options.animation = false;
    }
		
    self.ecc.setOption(options);
    self.changeSize(self.ecc);
    self.container.resize(function (e) {
        self.ecc.resize();
        self.changeSize(self.ecc);
    }); // 图表大小自适应
    if (group) {
        self.ecc.group = group;
        echarts.connect(group);
    }
    if (persist) {
        setTimeout(function () {
            self.container.css('background', '#fff');
            html2canvas(self.container[0], {
                onrendered: function (canvas) {
                    persist.data = canvas.toDataURL("image/jpeg");
                    persist.type = "jpg"
                }
            });
        }, 1000);
    }
    
    return function (o) {
        o = $.extend(true, {}, self.basicOption, o)
        self.ecc.setOption(o, true);
        self.changeSize(self.ecc);
    }
};

CBoardEChartRender.prototype.changeSize = function (instance) {
    var o = instance.getOption();
    var seriesType = o.series[0] ? o.series[0].type : null;
    if (seriesType == 'pie') {
        var l = o.series.length;
        var b = instance.getWidth() / (l + 1 + l * 8)
        for (var i = 0; i < l; i++) {
            if ((b * 8) < (instance.getHeight() * 0.50)) {
                o.series[i].radius = [o.in_radius, b * 4];
            } else {
                o.series[i].radius = [o.in_radius, typeof(o.out_radius)=='undefined'?'70%':o.out_radius];
            }
            
            o.series[i].selectedMode = "single";
        }
        instance.setOption(o);
    } else if(seriesType == 'bar'){
    	var l = o.series.length;
    	for (var i = 0; i < l; i++) {
    		o.series[i].barGap = 0;
        	o.series[i].barWidth=20;
        }
    	
    	instance.setOption(o);
    } else if (seriesType == 'heatmap'){
    	var serie =o.series[0];
    	var labelData=[];
    	_.each(serie.data,function(data){
    		labelData.push(data);
    	});
    	
    	labelSerie ={
    		 type: 'scatter',
    	        coordinateSystem: 'calendar',
    	        symbolSize: 1,
    	        label: {
    	            normal: {
    	                show: true,
    	                formatter: function (params) {
    	                    var d = echarts.number.parseDate(params.value[0]);
    	                    return d.getDate() + '\n\n' + params.value[1] ;
    	                },
    	                textStyle: {
    	                    color: '#000'
    	                }
    	            }
    	        },
    	        data: labelData
    			
    	}
    	
    	o.series.push(labelSerie);
    	instance.setOption(o);
    	
    }
    
    if(o.series[0].type == 'heatmap'){ //2017-11-02 罗立强 显示data[0]（日期)  data[1](值)作为日历的标签 
		var serie = o.series[0];
		var labelData = [];
		_.each(serie.data, function(data){
			labelData.push(data)
		});
		
		labelSerie = {
	        type: 'scatter',
	        coordinateSystem: 'calendar',
	        symbolSize: 1,
	        label: {
	            normal: {
	                show: true,
	                formatter: function (params) {
	                    var d = echarts.number.parseDate(params.value[0]);
	                    return d.getDate() + '\n\n' + params.value[1];
	                },
	                textStyle: {
	                    color: '#000',
	                    fontSize: 10
	                }
	            }
	        },
	        data: labelData
		}
		
		o.series.push(labelSerie);
		
		instance.setOption(o);
	}
};