// 进销存编辑
cBoard.controller('saleStorageEditor', function ($scope, $uibModalInstance, sale, save, $http) {

	var isEdit = false;
	
	$scope.title = "";
    
	if(typeof(sale) != 'undefined'){
		isEdit = true;
		$scope.sale = sale;
		$scope.title = "编辑进销存基础数据";
	}else{
		$scope.title = "新增进销存基础数据";
	}
	
    $scope.close = function () {
        $uibModalInstance.close();
    };
    $scope.ok = function () {
    	save($scope.sale, isEdit);
        $uibModalInstance.close();
    };
});
