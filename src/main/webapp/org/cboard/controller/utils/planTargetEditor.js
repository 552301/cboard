cBoard.controller('planTargetEditor', function ($scope, $uibModalInstance, planTarget, save, $http) {

	var isEdit = false;
	
	$scope.title = "";
	
	$scope.targetObjects = [];
    
	if(typeof(planTarget) != 'undefined'){
		isEdit = true;
		$scope.planTarget = planTarget;
		$scope.title = "编辑产能计划目标";
	}else{
		$scope.title = "新增产能计划目标";
	}
	
    $scope.close = function () {
        $uibModalInstance.close();
    };
    $scope.ok = function () {
    	save($scope.planTarget, isEdit);
        $uibModalInstance.close();
    };
});
