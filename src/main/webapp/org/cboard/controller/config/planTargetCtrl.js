cBoard.controller('planTargetCtrl', function ($scope, $http, ModalUtils, $uibModal){
	$scope.season = ['','春季','夏季','秋季','冬季','通季'];
	$scope.styleBill = ['','针织类','梭织类','毛织类','牛仔类','真丝类','外采类','羽绒类'];
	$scope.styleBills = [{name:'针织类',type:'针织类'},{name:'梭织类',type:'梭织类'},{name:'毛织类',type:'毛织类'},{name:'牛仔类',type:'牛仔类'}
	,{name:'真丝类',type:'真丝类'},{name:'外采类',type:'外采类'},{name:'羽绒类',type:'羽绒类'}];
	$scope.planMonth = [];
    $scope.category = "";
    $scope.planTargets = [];
	$scope.planNums = [];
	$scope.createDate = [];
	$scope.modifiedDate = [];
	
	$("#importDialog").dialog({autoOpen: false, modal: true, width: 400, height: 160});
	
	$scope.import = function(){
		$("#importDialog" ).dialog('open');
	};
	
	var init = function () {
		// var min = $scope.nowYear - 5;
		// var max = $scope.nowYear + 5;
		// for(var i = min; i <= max; i++){
		// 	$scope.years.push(i);
		// }
	}
	
	init();
	
	$scope.search = function(){
		var season = $scope.searchSeason;
		var category = $scope.searchCategory;
		var styleBill = $scope.selStyleBill;
		var planMonth = $scope.searchPlanMonth==undefined || $scope.searchPlanMonth=="" ?$scope.searchPlanMonth:new Date($scope.searchPlanMonth).Format('yyyy-MM');
		
		$http.post("plantarget/getPlanTargetList.do",
				{"params": angular.toJson({"season": season,"category": category,"styleBill":styleBill,"planMonth": planMonth})}
		).success(function (response) {
	    	// var idx;
	    	// for(idx = 0; idx < response.length; idx++){
	    	// 	response[idx].date = new Date(response[idx].date).Format('yyyy-MM-dd');
	    	// }
	    	$scope.planTargets = response;
	    });
	}
	
	$scope.search();
	
	$scope.edit = function(id){
		$http.get("plantarget/getPlanTarget.do", {
			params: {  
				"id": id 
		    }}	
		)
	    .success(function (response) {
	    	var planTarget = response;
			planTarget.planMonth = new Date(planTarget.planMonth).Format('yyyy-MM');
	    	$uibModal.open({
	            templateUrl: 'org/cboard/view/config/planTarget/modal/planTargetEdit.html',
	            windowTemplateUrl: 'org/cboard/view/util/modal/window.html',
	            backdrop: false,
	            size: 'lg',
	            resolve: {
                    planTarget: function(){
	            		return planTarget;
	        		},
	            	save: function(){
	            		return saveTarget;
	        		}
	            },
	            
				controller: 'planTargetEditor'
			});
	    });
	}
	
	$scope.add = function(){
		$uibModal.open({
            templateUrl: 'org/cboard/view/config/planTarget/modal/planTargetEdit.html',
            windowTemplateUrl: 'org/cboard/view/util/modal/window.html',
            backdrop: false,
            size: 'lg',
            resolve: {
                planTarget: function(){
            		return undefined;
        		},
            	save: function(){
            		return saveTarget;
        		},
            },
            
			controller: 'planTargetEditor'
		});
	}
	
	$scope.deleteTarget = function (id) {
        ModalUtils.confirm("确定要删除目标吗？", "modal-info", "lg", function () {
            $http.post("plantarget/deletePlanTarget.do", {id : id}).success(function (serviceStatus) {
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
			url: "plantarget/importPlanTargets.do",
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
    };
	
	$scope.$on('ngRepeatFinished', function(ngRepeatFinishedEvent) {
		// $('.yearSelector').val($scope.season);
		// $('.monthSelector').val($scope.category);
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
    
	 var saveTarget = function (planTarget, isEdit) {
        if (!isEdit) {
	        return $http.post("plantarget/savePlanTarget.do", {json: angular.toJson(planTarget)}).success(saveTargetCallBack);
	    } else {
	        return $http.post("plantarget/updatePlanTarget.do", {json: angular.toJson(planTarget)}).success(saveTargetCallBack);
	    }
	};
	

});