cBoard.controller('monthReturnRateB2BEditor', function ($scope, $uibModalInstance, rate, save, $http, years) {

	var isEdit = false;
	
	$scope.title = "";
	$scope.years = years;
	if(typeof(rate) != 'undefined'){
		isEdit = true;
		$scope.rate = rate;
		$scope.title = "编辑B2B退货率";
	}else{
		$scope.title = "新增B2B退货率";
	}
	
    $scope.close = function () {
        $uibModalInstance.close();
    };
    $scope.ok = function () {
    	save($scope.rate, isEdit);
        $uibModalInstance.close();
    };
    
    $scope.$on('ngRepeatFinished', function(ngRepeatFinishedEvent) {
		$('.yearSelector').val($scope.rate.year);
		$('.monthSelector').val($scope.rate.month);
	});
});
