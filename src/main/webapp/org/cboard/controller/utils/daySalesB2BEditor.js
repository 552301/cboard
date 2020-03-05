cBoard.controller('daySalesB2BEditor', function ($scope, $uibModalInstance, sale, save, $http) {

	var isEdit = false;
	
	$scope.title = "";
    
	if(typeof(sale) != 'undefined'){
		isEdit = true;
		$scope.sale = sale;
		$scope.title = "编辑B2B销售";
	}else{
		$scope.title = "新增B2B销售";
	}
	
    $scope.close = function () {
        $uibModalInstance.close();
    };
    $scope.ok = function () {
    	save($scope.sale, isEdit);
        $uibModalInstance.close();
    };
});
