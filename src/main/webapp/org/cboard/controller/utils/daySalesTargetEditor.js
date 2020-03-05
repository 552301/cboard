cBoard.controller('daySalesTargetEditor', function ($scope, $uibModalInstance, target, save, $http) {

	var isEdit = false;
	
	$scope.title = "";
	
	$scope.targetObjects = [];
    
	if(typeof(target) != 'undefined'){
		isEdit = true;
		$scope.target = target;
		$scope.title = "编辑销售目标";
	}else{
		$scope.title = "新增销售目标";
	}
	
    $scope.close = function () {
        $uibModalInstance.close();
    };
    $scope.ok = function () {
    	save($scope.target, isEdit);
        $uibModalInstance.close();
    };
});
