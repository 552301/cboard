/**
 * Created by yfyuan on 2017/02/16.
 */
cBoard.controller('otherJobCtrl', function ($scope, $uibModalInstance, $http, $filter) {
    var translate = $filter('translate');
   
    var init = function () {
        if (!$scope.$parent.job.config) {
            $scope.config = {};
        }else{
            $scope.config = angular.copy($scope.$parent.job.config);
        }
    }();


    $scope.close = function () {
        $uibModalInstance.close();
    };
    $scope.ok = function () {
        $scope.$parent.job.config = $scope.config;
        $uibModalInstance.close();
    };

});