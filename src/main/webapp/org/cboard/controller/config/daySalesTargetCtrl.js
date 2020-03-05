cBoard.controller('daySalesTargetCtrl', function ($scope, $http, ModalUtils, $uibModal){
	$scope.years = [];
	$scope.months = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];
	$scope.nowYear = new Date().getFullYear();
	$scope.targets = [];
	$scope.selYear = $scope.nowYear;
	$scope.selMonth = new Date().getMonth() + 1;
	
	$("#importDialog").dialog({autoOpen: false, modal: true, width: 400, height: 160});
	
	$scope.import = function(){
		$("#importDialog" ).dialog('open');
	};
	
	var init = function () {
		var min = $scope.nowYear - 5;
		var max = $scope.nowYear + 5;
		for(var i = min; i <= max; i++){
			$scope.years.push(i);
		}
	}
	
	init();
	
	$scope.search = function(){
		var year = $scope.selYear;
		var month = $scope.selMonth;
		var platform = $scope.searchPlatform;
		var brand = $scope.searchBrand;
		var store = $scope.searchStore;
		
		$http.post("daysalestarget/getDaySalesTargetList.do", 
				{"params": angular.toJson({"year": year,"month": month,"platform": platform,"brand": brand, "store": store})}	
		).success(function (response) {
	    	var idx;
	    	for(idx = 0; idx < response.length; idx++){
	    		response[idx].date = new Date(response[idx].date).Format('yyyy-MM-dd');
	    	}
	    	$scope.targets = response;
	    });
	}
	
	$scope.search();
	
	$scope.edit = function(id){
		$http.get("daysalestarget/getDaySalesTarget.do", {
			params: {  
				"id": id 
		    }}	
		)
	    .success(function (response) {
	    	var target = response;
	    	target.date = new Date(target.date).Format('yyyy-MM-dd');
	    	$uibModal.open({
	            templateUrl: 'org/cboard/view/config/day_sales_target/modal/daySalesTargetEdit.html',
	            windowTemplateUrl: 'org/cboard/view/util/modal/window.html',
	            backdrop: false,
	            size: 'lg',
	            resolve: {
	            	target: function(){
	            		return target;
	        		},
	            	save: function(){
	            		return saveTarget;
	        		}
	            },
	            
				controller: 'daySalesTargetEditor'
			});
	    });
	}
	
	$scope.add = function(){
		$uibModal.open({
            templateUrl: 'org/cboard/view/config/day_sales_target/modal/daySalesTargetEdit.html',
            windowTemplateUrl: 'org/cboard/view/util/modal/window.html',
            backdrop: false,
            size: 'lg',
            resolve: {
            	target: function(){
            		return undefined;
        		},
            	save: function(){
            		return saveTarget;
        		},
            },
            
			controller: 'daySalesTargetEditor'
		});
	}
	
	$scope.deleteTarget = function (id) {
        ModalUtils.confirm("确定要删除目标吗？", "modal-info", "lg", function () {
            $http.post("daysalestarget/deleteDaySalesTarget.do", {id : id}).success(function (serviceStatus) {
                if (serviceStatus.status == '1') {
                    $scope.search();
                } else {
                    ModalUtils.alert(serviceStatus.msg, "modal-warning", "lg");
                }
            });
        });
    };
    
    $scope.importTargets = function(){
		var fd = new FormData();
		fd.append("upload", 1);
		fd.append("file", $("#importFile").get(0).files[0]);
		$.ajax({
			url: "/cboard/daysalestarget/importDaySalesTargets.do",
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
				
				$('#importFile').replaceWith($('#importFile').val('').clone(true));
				$("#importDialog").dialog('close');
			}});
    }
	
	$scope.$on('ngRepeatFinished', function(ngRepeatFinishedEvent) {
		$('.yearSelector').val($scope.selYear);
		$('.monthSelector').val($scope.selMonth);
	});
	
    function saveTargetCallBack(serviceStatus) {
        if (serviceStatus.status == '1') {
            ModalUtils.alert(serviceStatus.msg, "目标保存成功", "sm");
            $scope.search();
            
            return true;
        } else {
            ModalUtils.alert(serviceStatus.msg, "目标保存失败", "sm");
        }
        
        return false;
    }
    
	 var saveTarget = function (target, isEdit) {
        if (!isEdit) {
	        return $http.post("daysalestarget/saveDaySalesTarget.do", {json: angular.toJson(target)}).success(saveTargetCallBack);
	    } else {
	        return $http.post("daysalestarget/updateDaySalesTarget.do", {json: angular.toJson(target)}).success(saveTargetCallBack);
	    }
	};
	

});