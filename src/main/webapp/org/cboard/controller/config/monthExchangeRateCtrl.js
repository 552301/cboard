cBoard.controller('monthExchangeRateCtrl', function ($scope, $http, ModalUtils, $uibModal){
	$scope.years = [];
	$scope.months = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];
	$scope.nowYear = new Date().getFullYear();
	$scope.rates = [];
	$scope.selYear = $scope.nowYear;
	$scope.selMonth = 0;
	
	$("#importExchangeDialog").dialog({autoOpen: false, modal: true, width: 400, height: 160});
	
	$scope.import = function(){
		$("#importExchangeDialog" ).dialog('open');
	};
	
	var init = function () {
		var min = $scope.nowYear - 5;
		var max = $scope.nowYear + 5;
		for(var i = min; i <= max; i++){
			$scope.years.push(i);
		}
	}
	
	init();
	
	$scope.selectAll = function(){
		var boxes = document.getElementsByClassName('checkbox-id');
		for(var idx = 0; idx < boxes.length; idx++){
			boxes[idx].checked = true;
		}
	};
	
	$scope.deleteSelected = function(){
		ModalUtils.confirm("确定要批量删除吗？", "modal-info", "lg", function () {
			var boxes = document.getElementsByClassName('checkbox-id');
			for(var idx = 0; idx < boxes.length; idx++){
				if(boxes[idx].checked){
					 $http.post("monthexchangerate/deleteMonthExchangeRate.do", {id : boxes[idx].getAttribute('id')}).success(function (serviceStatus) {
						 if(idx >= boxes.length){
							 $scope.search();
						 }
			         });
				}
			}
        });
	}
	
	$scope.search = function(){
		var year = $scope.selYear;
		var month = $scope.selMonth;
		var caustomer = $scope.searchCaustomer;
		var params = null;
		if($scope.selMonth == 0){
			params = {"year": year,"caustomer": caustomer}
		}else {
			params = {"year": year,"month": month,"caustomer": caustomer}
		}
		
		$http.post("monthexchangerate/getMonthExchangeRateList.do", 
				{"params": angular.toJson(params)}	
		).success(function (response) {
	    	$scope.rates = response;
	    });
	};
	
	$scope.search();
	
	$scope.edit = function(id){
		$http.get("monthexchangerate/getMonthExchangeRate.do", {
			params: {  
				"id": id 
		    }}	
		)
	    .success(function (response) {
	    	var rate = response;
	    	$uibModal.open({
	            templateUrl: 'org/cboard/view/config/month_exchange_rate/modal/monthExchangeRateEdit.html',
	            windowTemplateUrl: 'org/cboard/view/util/modal/window.html',
	            backdrop: false,
	            size: 'lg',
	            resolve: {
	            	rate: function(){
	            		return rate;
	        		},
	            	save: function(){
	            		return saveRate;
	            	},
	        		years: function(){
	        			return $scope.years;
	        		}
	            },
	            
				controller: 'monthExchangeRateEditor'
			});
	    });
	}
	
	$scope.add = function(){
		$uibModal.open({
            templateUrl: 'org/cboard/view/config/month_exchange_rate/modal/monthExchangeRateEdit.html',
            windowTemplateUrl: 'org/cboard/view/util/modal/window.html',
            backdrop: false,
            size: 'lg',
            resolve: {
            	rate: function(){
            		return undefined;
        		},
            	save: function(){
            		return saveRate;
        		},
        		years: function(){
        			return $scope.years;
        		}
            },
            
			controller: 'monthExchangeRateEditor'
		});
	}
	
	$scope.deleteRate = function (id) {
        ModalUtils.confirm("确定要删除吗？", "modal-info", "lg", function () {
            $http.post("monthExchangeRate/deletemonthExchangeRate.do", {id : id}).success(function (serviceStatus) {
                if (serviceStatus.status == '1') {
                    $scope.search();
                } else {
                    ModalUtils.alert(serviceStatus.msg, "modal-warning", "lg");
                }
            });
        });
    };
    
    $scope.importRates = function(){
		var fd = new FormData();
		fd.append("upload", 1);
		fd.append("file", $("#importExchangeFile").get(0).files[0]);
		$.ajax({
			url: "/cboard/monthexchangerate/importMonthExchangeRates.do",
			type: "POST",
			processData: false,
			contentType: false,
			data: fd,
			success: function(serviceStatus) {
				if (serviceStatus.status == '1') {
		            ModalUtils.alert(serviceStatus.msg, "导入成功", "sm");
		            $scope.search();
		        } else {
		            ModalUtils.alert(serviceStatus.msg, "导入失败", "sm");
		        }
				
				$('#importExchangeFile').replaceWith($('#importExchangeFile').val('').clone(true));
				$("#importExchangeDialog").dialog('close');
			}});
    }
	
	$scope.$on('ngRepeatFinished', function(ngRepeatFinishedEvent) {
		$('.yearSelector').val($scope.selYear);
		$('.monthSelector').val($scope.selMonth);
	});
	
    function saveRateCallBack(serviceStatus) {
        if (serviceStatus.status == '1') {
            ModalUtils.alert(serviceStatus.msg, "保存成功", "sm");
            $scope.search();
            
            return true;
        } else {
            ModalUtils.alert(serviceStatus.msg, "保存失败", "sm");
        }
        
        return false;
    }
    
	 var saveRate = function (rate, isEdit) {
        if (!isEdit) {
	        return $http.post("monthexchangerate/saveMonthExchangeRate.do", {json: angular.toJson(rate)}).success(saveRateCallBack);
	    } else {
	        return $http.post("monthexchangerate/updateMonthExchangeRate.do", {json: angular.toJson(rate)}).success(saveRateCallBack);
	    }
	};
	

});