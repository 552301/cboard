cBoard.controller('targetEditor', function ($scope, $uibModalInstance, target, years, save, $http) {

	var isEdit = false;
	
	$scope.title = "";
	
	$scope.years = years;
	$scope.targetObjects = [];
	
	$scope.dimensionChange = function(){
		var enDimension = '';
		if($scope.target.dimension == '平台')
			enDimension = 'platform';
		else if($scope.target.dimension == '品牌')
			enDimension = 'brand';
		else if($scope.target.dimension == '店铺')
			enDimension = 'store';
		
		$http.get("salestarget/getHistoryTargetObjects.do", {
			params: {  
				"enDimension": enDimension 
		    }}	
		).success(function (response) {
			$scope.targetObjects = response;
		});
	}
	
	if(typeof(target) != 'undefined'){
		isEdit = true;
		$scope.target = target;
		$scope.title = "编辑销售目标";
		$scope.dimensionChange();
	}else{
		$scope.dimensionSelected = "";
		$scope.title = "新增销售目标";
	}
	
    $scope.close = function () {
        $uibModalInstance.close();
    };
    $scope.ok = function () {
    	save($scope.target, isEdit);
        $uibModalInstance.close();
    };
    
    $scope.$on('ngRepeatFinished', function(ngRepeatFinishedEvent) {
    	if(isEdit){
    		$('.yearSelector').val($scope.target.year);
    		$('.monthSelector').val($scope.target.month);
    	}
	});
});
