//商品进销存控制器
cBoard.controller("saleStorageCtrl", function($scope, $http, ModalUtils, $uibModal) {
	$scope.years = [];
	$scope.months = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];
	$scope.days = [];
	$scope.nowYear = new Date().getFullYear();
	$scope.sales = [];
	$scope.selYear = $scope.nowYear;
	$scope.selMonth = new Date().getMonth() + 1;
	$scope.selDay = new Date().getDate();
	
	
	// 数据上传弹框
	$("#importSaleStorageDialog").dialog({autoOpen: false, modal: true, width: 400, height: 160});
	
	$scope.import = function(){
		$("#importSaleStorageDialog" ).dialog('open');
	};
	
	// 初始日期
	var init = function() {
		var min = $scope.nowYear - 5;
		var max = $scope.nowYear + 5;
		for(var i = min; i <= max; i++){
			$scope.years.push(i);
		}
		
		var dayCountOfMonth = new Date($scope.selYear, $scope.selMonth, 0).getDate();
		for(var date = 0; date < dayCountOfMonth; date++){
			$scope.days.push(date + 1);
		}
	}
	init();
	
	// 月份修改
	$scope.selMonthChange = function() {
		$scope.days = [];
		var dayCountOfMonth = new Date($scope.selYear, $scope.selMonth, 0).getDate();
		for(var date = 0; date < dayCountOfMonth; date++){
			$scope.days.push(date + 1);
		}
		$scope.selDay = 0;
	}


	
	// 渲染完成， 填充数据
	$scope.$on('ngRepeatFinished', function(ngRepeatFinishedEvent) {
		$('.yearSelector').val($scope.selYear);
		$('.monthSelector').val($scope.selMonth);
		$('.daySelector').val($scope.selDay);
	});
	
	// 查询
	$scope.search = function(){
		var year = $scope.selYear;
		var month = $scope.selMonth;
		var sku = $scope.searchSku;
		
		var params = null;
		if($scope.selDay != 0){
			var date = year + '-' + month + '-' + $scope.selDay;
			params = {"year": year,"month": month,"date": date,"sku": sku}
		}else {
			params = {"year": year,"month": month,"sku": sku}
		}
		
		$http.post("salestorage/getSaleStorageList.do", 
				{"params": angular.toJson(params)}	
		).success(function (response) {
	    	var idx;
	    	for(idx = 0; idx < response.length; idx++){
	    		response[idx].date = new Date(response[idx].date).Format('yyyy-MM-dd');
	    	}
	    	$scope.sales = response;
	    });
	}
	
	$scope.search();
	
	// 编辑
	$scope.edit = function(id) {
		$http.get("salestorage/getSaleStorage.do", {
			params: {  
				"id": id 
		    }}
		)
		.success(function(response) {
			var sale = response;
	    	sale.date = new Date(sale.date).Format('yyyy-MM-dd');
			$uibModal.open({
	            templateUrl: 'org/cboard/view/config/saleStorage/modal/saleStorageEdit.html',
	            windowTemplateUrl: 'org/cboard/view/util/modal/window.html',
	            backdrop: false,
	            size: 'lg',
	            resolve: {
	            	sale: function(){
	            		return sale;
	        		},
	            	save: function(){
	            		return saveSale;
	        		},
	            },
	            
				controller: 'saleStorageEditor'
			});
		});
	}
	
	// 添加数据
	$scope.add = function(){
		$uibModal.open({
            templateUrl: 'org/cboard/view/config/saleStorage/modal/saleStorageEdit.html',
            windowTemplateUrl: 'org/cboard/view/util/modal/window.html',
            backdrop: false,
            size: 'lg',
            resolve: {
            	sale: function(){
            		return undefined;
        		},
            	save: function(){
            		return saveSale;
        		},
            },
            
			controller: 'saleStorageEditor'
		});
	}
	// 删除
	$scope.deleteSale = function (id) {
        ModalUtils.confirm("确定要删除目标吗？", "modal-info", "lg", function () {
            $http.post("salestorage/deleteSaleStorage.do", {id : id}).success(function (serviceStatus) {
                if (serviceStatus.status == '1') {
                    $scope.search();
                } else {
                    ModalUtils.alert(serviceStatus.msg, "modal-warning", "lg");
                }
            });
        });
    };
    
	$scope.selectAll = function(){
		var boxes = document.getElementsByClassName('checkbox-id');
		for(var idx = 0; idx < boxes.length; idx++){
			boxes[idx].checked = true;
		}
	};
	// 批量删除
	$scope.deleteSelected = function(){
		ModalUtils.confirm("确定要批量删除目标吗？", "modal-info", "lg", function () {
			var boxes = document.getElementsByClassName('checkbox-id');
			for(var idx = 0; idx < boxes.length; idx++){
				if(boxes[idx].checked){
					 $http.post("salestorage/deleteSaleStorage.do", {id : boxes[idx].getAttribute('id')}).success(function (serviceStatus) {
						 if(idx >= boxes.length){
							 $scope.search();
						 }
			         });
				}
			}
        });
	}
	// 导入上传
	$scope.importSaleStorage = function(){
		var fd = new FormData();
		fd.append("upload", 1);
		fd.append("file", $("#importSaleStorageFile").get(0).files[0]);
		$.ajax({
			url: "/cboard/salestorage/importSalestorage.do",
			type: "POST",
			processData: false,
			contentType: false,
			data: fd,
			success: function(serviceStatus) {
				if (serviceStatus.status == '1') {
		            ModalUtils.alert(serviceStatus.msg, "目标导入成功", "sm");
		            $scope.search();
		        } else {
		            ModalUtils.alert(serviceStatus.msg, "目标导入失败", "sm");
		        }
				
				$('#importSaleStorageFile').replaceWith($('#importSaleStorageFile').val('').clone(true));
				$("#importSaleStorageDialog").dialog('close');
			}});
    }
	
	function saveSaleCallBack(serviceStatus) {
        if (serviceStatus.status == '1') {
            ModalUtils.alert(serviceStatus.msg, "目标保存成功", "sm");
            $scope.search();
            
            return true;
        } else {
            ModalUtils.alert(serviceStatus.msg, "目标保存失败", "sm");
        }
        
        return false;
    }
	var saveSale = function (sale, isEdit) {
        if (!isEdit) {
	        return $http.post("salestorage/saveSaleStorage.do", {json: angular.toJson(sale)}).success(saveSaleCallBack);
	    } else {
	        return $http.post("salestorage/updateSaleStorage.do", {json: angular.toJson(sale)}).success(saveSaleCallBack);
	    }
	};
});