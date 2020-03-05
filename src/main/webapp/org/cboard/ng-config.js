/**
 * Created by Peter on 2016/10/22.
 */

angular.module('cBoard').config(['$stateProvider', function ($stateProvider) {
    $stateProvider
        .state('dashboard', {
            url: '/dashboard',
            abstract: true,
            template: '<div ui-view></div>'
        })
        .state('mine', {
            url: '/mine',
            abstract: true,
            template: '<div ui-view></div>'
        })
        .state('mine.view', {
            url: '/{id}',
            params: {id: null},
            templateUrl: 'org/cboard/view/dashboard/view.html',
            controller: 'dashboardViewCtrl'
        })
        .state('dashboard.category', {
            url: '/{category}',
            params: {category: null},
            abstract: true,
            template: '<div ui-view></div>',
        })
        .state('dashboard.category.view', {
            url: '/{id}',
            params: {id: null},
            templateUrl: 'org/cboard/view/dashboard/view.html',
            controller: 'dashboardViewCtrl'
        })
        .state('config', {
            url: '/config',
            abstract: true,
            template: '<div ui-view></div>'
        })
        .state('config.board', {
            url: '/board',
            templateUrl: 'org/cboard/view/config/board.html',
            controller: 'boardCtrl'
        })
        .state('config.widget', {
            url: '/widget',
            params: {id: null},
            templateUrl: 'org/cboard/view/config/widget.html',
            controller: 'widgetCtrl'
        })
        .state('config.datasource', {
            url: '/datasource',
            templateUrl: 'org/cboard/view/config/datasource.html',
            controller: 'datasourceCtrl'
        })
        .state('config.category', {
            url: '/category',
            templateUrl: 'org/cboard/view/config/category.html',
            controller: 'categoryCtrl'
        })
        .state('config.dataset', {
            url: '/dataset',
            templateUrl: 'org/cboard/view/config/dataset.html',
            controller: 'datasetCtrl'
        })
        .state('config.job', {
            url: '/job',
            templateUrl: 'org/cboard/view/config/job.html',
            controller: 'jobCtrl'
        })
        .state('config.role', {
            url: '/role',
            templateUrl: 'org/cboard/view/config/shareResource.html',
            controller: 'shareResCtrl'
        })
        .state('config.target',{
        	url: '/target',
        	templateUrl: 'org/cboard/view/config/target.html',
        	controller: 'targetCtrl'
        })
        .state('config.planTarget',{
        	url: '/planTarget',
        	templateUrl: 'org/cboard/view/config/planTarget.html',
        	controller: 'planTargetCtrl'
        })
        .state('config.biPlanTarget',{
            url: '/biPlanTarget',
            templateUrl: 'org/cboard/view/config/biPlanTarget.html',
            controller: 'biPlanTargetCtrl'
        })
        .state('config.daySalesTarget',{
        	url: "/daySalesTarget",
        	templateUrl: 'org/cboard/view/config/daySalesTarget.html',
        	controller: "daySalesTargetCtrl"
        })
        .state('config.daySalesB2B',{
        	url: "/daySalesB2B",
        	templateUrl: 'org/cboard/view/config/daySalesB2B.html',
        	controller: "daySalesB2BCtrl"
        })
        .state('config.dayReturnB2B',{
        	url: "/dayReturnB2B",
        	templateUrl: 'org/cboard/view/config/dayReturnB2B.html',
        	controller: "dayReturnB2BCtrl"
        })
        .state('config.monthReturnRateB2B',{
        	url: "/monthReturnRateB2B",
        	templateUrl: 'org/cboard/view/config/monthReturnRateB2B.html',
        	controller: "monthReturnRateB2BCtrl"
        })
        .state('config.monthExchangeRate',{
        	url: "/monthExchangeRate",
        	templateUrl: 'org/cboard/view/config/monthExchangeRate.html',
        	controller: "monthExchangeRateCtrl"
        })
         .state('config.saleStorage',{
        	url: "/saleStorage",
        	templateUrl: 'org/cboard/view/config/saleStorage.html',
        	controller: "saleStorageCtrl"
        })
        .state('admin', {
            url: '/admin',
            abstract: true,
            template: '<div ui-view></div>'
        })
        .state('admin.user', {
            url: '/user',
            templateUrl: 'org/cboard/view/admin/user.html',
            controller: 'userAdminCtrl'
        });

}]);

angular.module('cBoard').factory('sessionHelper', ["$rootScope","$q", function ($rootScope,$q) {
    var sessionHelper = {
        responseError: function (response) {
            if (response.data.status == 2) {
                if ($rootScope.alert) {
                    $rootScope.alert(response.data.msg);
                }
            }
            return $q.reject(response);
        }
    };
    return sessionHelper;
}]);


angular.module('cBoard').config(function ($httpProvider) {
    $httpProvider.defaults.headers.put['Content-Type'] = 'application/x-www-form-urlencoded';
    $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

    // Override $http service's default transformRequest
    $httpProvider.defaults.transformRequest = [function (data) {
        /**
         * The workhorse; converts an object to x-www-form-urlencoded serialization.
         * @param {Object} obj
         * @return {String}
         */
        var param = function (obj) {
            var query = '';
            var name, value, fullSubName, subName, subValue, innerObj, i;

            for (name in obj) {
                value = obj[name];

                if (value instanceof Array) {
                    for (i = 0; i < value.length; ++i) {
                        subValue = value[i];
                        fullSubName = name + '[' + i + ']';
                        innerObj = {};
                        innerObj[fullSubName] = subValue;
                        query += param(innerObj) + '&';
                    }
                } else if (value instanceof Object) {
                    for (subName in value) {
                        subValue = value[subName];
                        fullSubName = name + '[' + subName + ']';
                        innerObj = {};
                        innerObj[fullSubName] = subValue;
                        query += param(innerObj) + '&';
                    }
                } else if (value !== undefined && value !== null) {
                    query += encodeURIComponent(name) + '='
                        + encodeURIComponent(value) + '&';
                }
            }

            return query.length ? query.substr(0, query.length - 1) : query;
        };

        return angular.isObject(data) && String(data) !== '[object File]'
            ? param(data)
            : data;
    }];

    $httpProvider.interceptors.push('sessionHelper');

});


angular.module('cBoard').config(function ($translateProvider, $translatePartialLoaderProvider) {
    $translatePartialLoaderProvider.addPart('cboard');
    $translateProvider.useLoader('$translatePartialLoader', {
        urlTemplate: 'i18n/{lang}/{part}.json'
    });

    $translateProvider.preferredLanguage(settings.preferredLanguage);
});

angular.module('cBoard').directive('onFinishRender',['$timeout', '$parse', function ($timeout, $parse) {
    return {
        restrict: 'A',
        link: function (scope, element, attr) {
            if (scope.$last === true) {
                $timeout(function () {
                    scope.$emit('ngRepeatFinished'); //事件通知
//                        var fun = $scope.$eval(attrs.onFinishRender);
//                        if(fun && typeof(fun)=='function'){  
//                            fun();  //回调函数
//                        }  
                });
            }
        }
    }
}]);



