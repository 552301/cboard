cBoard.controller('dayReturnB2BCtrl', function ($scope, $http, ModalUtils, $uibModal){
	$scope.years = [];
	$scope.months = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];
	$scope.days = [];
	$scope.nowYear = new Date().getFullYear();
	$scope.refunds = [];
	$scope.selYear = $scope.nowYear;
	$scope.selMonth = new Date().getMonth() + 1;
	$scope.selDay = new Date().getDate();
	
	$("#importReturnDialog").dialog({autoOpen: false, modal: true, width: 400, height: 160});
	
	$scope.import = function(){
		$("#importReturnDialog" ).dialog('open');
	};
	
	var init = function () {
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
	
	$scope.selMonthChange = function(){
		$scope.days = [];
		var dayCountOfMonth = new Date($scope.selYear, $scope.selMonth, 0).getDate();
		for(var date = 0; date < dayCountOfMonth; date++){
			$scope.days.push(date + 1);
		}
		$scope.selDay = 0;
	}
	
	$scope.selectAll = function(){
		var boxes = document.getElementsByClassName('checkbox-id');
		for(var idx = 0; idx < boxes.length; idx++){
			boxes[idx].checked = true;
		}
	};
	
	$scope.deleteSelected = function(){
		ModalUtils.confirm("确定要批量删除目标吗？", "modal-info", "lg", function () {
			var boxes = document.getElementsByClassName('checkbox-id');
			for(var idx = 0; idx < boxes.length; idx++){
				if(boxes[idx].checked){
					 $http.post("dayreturnb2b/deleteDayReturnB2B.do", {id : boxes[idx].getAttribute('id')}).success(function (serviceStatus) {
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
		if($scope.selDay != 0){
			var date = year + '-' + month + '-' + $scope.selDay;
			params = {"year": year,"month": month,"date": date,"caustomer": caustomer}
		}else {
			params = {"year": year,"month": month,"caustomer": caustomer}
		}
		
		$http.post("dayreturnb2b/getDayReturnB2BList.do", 
				{"params": angular.toJson(params)}	
		).success(function (response) {
	    	var idx;
	    	for(idx = 0; idx < response.length; idx++){
	    		response[idx].date = new Date(response[idx].date).Format('yyyy-MM-dd');
	    	}
	    	$scope.refunds = response;
	    });
	};
	
	$scope.search();
	
	$scope.edit = function(id){
		$http.get("dayreturnb2b/getDayReturnB2B.do", {
			params: {  
				"id": id 
		    }}	
		)
	    .success(function (response) {
	    	var refund = response;
	    	refund.date = new Date(refund.date).Format('yyyy-MM-dd');
	    	$uibModal.open({
	            templateUrl: 'org/cboard/view/config/day_return_b2b/modal/dayReturnB2BEdit.html',
	            windowTemplateUrl: 'org/cboard/view/util/modal/window.html',
	            backdrop: false,
	            size: 'lg',
	            resolve: {
	            	refund: function(){
	            		return refund;
	        		},
	            	save: function(){
	            		return saveReturn;
	        		}
	            },
	            
				controller: 'dayReturnB2BEditor'
			});
	    });
	}
	
	$scope.add = function(){
		$uibModal.open({
            templateUrl: 'org/cboard/view/config/day_return_b2b/modal/dayReturnB2BEdit.html',
            windowTemplateUrl: 'org/cboard/view/util/modal/window.html',
            backdrop: false,
            size: 'lg',
            resolve: {
            	refund: function(){
            		return undefined;
        		},
            	save: function(){
            		return saveReturn;
        		},
            },
            
			controller: 'dayReturnB2BEditor'
		});
	}
	
	$scope.deleteSale = function (id) {
        ModalUtils.confirm("确定要删除目标吗？", "modal-info", "lg", function () {
            $http.post("dayreturnb2b/deleteDayReturnB2B.do", {id : id}).success(function (serviceStatus) {
                if (serviceStatus.status == '1') {
                    $scope.search();
                } else {
                    ModalUtils.alert(serviceStatus.msg, "modal-warning", "lg");
                }
            });
        });
    };
    
    $scope.importReturns = function(){
		var fd = new FormData();
		fd.append("upload", 1);
		fd.append("file", $("#importReturnFile").get(0).files[0]);
		$.ajax({
			url: "/cboard/dayreturnb2b/importDayReturnB2B.do",
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
				
				$('#importReturnFile').replaceWith($('#importReturnFile').val('').clone(true));
				$("#importReturnDialog").dialog('close');
			}});
    }
	
	$scope.$on('ngRepeatFinished', function(ngRepeatFinishedEvent) {
		$('.yearSelector').val($scope.selYear);
		$('.monthSelector').val($scope.selMonth);
		$('.daySelector').val($scope.selDay);
	});
	
    function saveReturnCallBack(serviceStatus) {
        if (serviceStatus.status == '1') {
            ModalUtils.alert(serviceStatus.msg, "保存成功", "sm");
            $scope.search();
            
            return true;
        } else {
            ModalUtils.alert(serviceStatus.msg, "保存失败", "sm");
        }
        
        return false;
    }
    
	 var saveReturn = function (refund, isEdit) {
        if (!isEdit) {
	        return $http.post("dayreturnb2b/saveDayReturnB2B.do", {json: angular.toJson(refund)}).success(saveReturnCallBack);
	    } else {
	        return $http.post("dayreturnb2b/updateDayReturnB2B.do", {json: angular.toJson(sarefundle)}).success(saveReturnCallBack);
	    }
	};
	

});