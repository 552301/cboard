cBoard.controller('dayReturnB2BEditor', function ($scope, $uibModalInstance, refund, save, $http) {

	var isEdit = false;
	
	$scope.title = "";
	
	if(typeof(refund) != 'undefined'){
		isEdit = true;
		$scope.refund = refund;
		$scope.title = "编辑B2B退货";
	}else{
		$scope.title = "新增B2B退货";
	}
	
    $scope.close = function () {
        $uibModalInstance.close();
    };
    $scope.ok = function () {
    	save($scope.refund, isEdit);
        $uibModalInstance.close();
    };
});
