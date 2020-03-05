/**
 * Created by Peter on 2016/10/22.
 */
// var AgGrid = require('ag-grid');
agGrid.initialiseAgGridWithAngular1(angular);

var cBoard = angular.module('cBoard', ['ui.router', 'angular-md5', 'dndLists', 'treeControl','agGrid',
    'ui.bootstrap', 'ngSanitize', 'ui.select', 'pascalprecht.translate', 'ui.ace', 'ngJsTree', 'daterangepicker', 'angular-cron-jobs', 'rzModule','uuid4']);