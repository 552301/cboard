cBoard.controller('targetCtrl', function ($scope, $http, ModalUtils, $uibModal){
	$scope.years = [];
	$scope.nowYear = new Date().getFullYear();
	$scope.targets = [];
	$scope.selYear = $scope.nowYear;

	
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
		$http.get("salestarget/getSalesTargetListYear.do", {
			params: {  
				"year": year 
		    }}	
		)
	    .success(function (response) {
	    	$scope.targets = response;
	    });
	}
	
	$scope.search();
	
	$scope.edit = function(id){
		$http.get("salestarget/getSalesTarget.do", {
			params: {  
				"id": id 
		    }}	
		)
	    .success(function (response) {
	    	var target = response;
	    	$uibModal.open({
	            templateUrl: 'org/cboard/view/config/target/modal/targetEdit.html',
	            windowTemplateUrl: 'org/cboard/view/util/modal/window.html',
	            backdrop: false,
	            size: 'lg',
	            resolve: {
	            	years: function(){
	            		return $scope.years;
	            	},
	            	target: function(){
	            		return target;
	        		},
	            	save: function(){
	            		return saveTarget;
	        		}
	            },
	            
				controller: 'targetEditor'
			});
	    });
	}
	
	$scope.add = function(){
		$uibModal.open({
            templateUrl: 'org/cboard/view/config/target/modal/targetEdit.html',
            windowTemplateUrl: 'org/cboard/view/util/modal/window.html',
            backdrop: false,
            size: 'lg',
            resolve: {
            	years: function(){
            		return $scope.years;
            	},
            	target: function(){
            		return undefined;
        		},
            	save: function(){
            		return saveTarget;
        		},
            },
            
			controller: 'targetEditor'
		});
	}
	
	$scope.deleteTarget = function (id) {
        ModalUtils.confirm("确定要删除目标吗？", "modal-info", "lg", function () {
            $http.post("salestarget/deleteSalesTarget.do", {id : id}).success(function (serviceStatus) {
                if (serviceStatus.status == '1') {
                    $scope.search();
                } else {
                    ModalUtils.alert(serviceStatus.msg, "modal-warning", "lg");
                }
            });
        });
    };
    
	
	$scope.$on('ngRepeatFinished', function(ngRepeatFinishedEvent) {
		$('.yearSelector').val($scope.selYear);
	});
	
    function saveTargetCallBack(serviceStatus) {
        if (serviceStatus.status == '1') {
            ModalUtils.alert(serviceStatus.msg, "目标保存成功", "sm");
            $scope.search();
        } else {
            ModalUtils.alert(serviceStatus.msg, "目标保存失败", "sm");
        }
    }
    
	 var saveTarget = function (target, isEdit) {
        if (!isEdit) {
	        return $http.post("salestarget/saveSalesTarget.do", {json: angular.toJson(target)}).success(saveTargetCallBack);
	    } else {
	        return $http.post("salestarget/updateSalesTarget.do", {json: angular.toJson(target)}).success(saveTargetCallBack);
	    }
	};
	

});