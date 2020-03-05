cBoard.controller('biPlanTargetCtrl', function ($scope, $http, ModalUtils, $uibModal){

	$scope.planMonth = [];
	$scope.styleBill = [];

	$("#importDialog").dialog({autoOpen: false, modal: true, width: 400, height: 160});

	$scope.search = function(){
		var styleBill = document.getElementById('selStyleBill').value;
		var planMonth = document.getElementById('searchPlanMonth').value==undefined || document.getElementById('searchPlanMonth').value=="" ?document.getElementById('searchPlanMonth').value:new Date(document.getElementById('searchPlanMonth').value).Format('yyyy-MM');

		var postData = {"params": angular.toJson({"styleBill": styleBill,"planMonth": planMonth})};

		jQuery("#list2").jqGrid('setGridParam', {
			url : "biplantarget/getBIPlanTargetList.do",
			postData:postData,
			datatype : "json",
			mtype : "post",//防止乱码
			rows:20,
			page : 1
		}).trigger("reloadGrid");
	};

	$scope.search();

    $scope.imports = function(){
		$("#importDialog" ).dialog('open');
	};

	var idTmr;
	function  getExplorer() {
		var explorer = window.navigator.userAgent ;
		//ie
		if (explorer.indexOf("MSIE") >= 0) {
			return 'ie';
		}
		//firefox
		else if (explorer.indexOf("Firefox") >= 0) {
			return 'Firefox';
		}
		//Chrome
		else if(explorer.indexOf("Chrome") >= 0){
			return 'Chrome';
		}
		//Opera
		else if(explorer.indexOf("Opera") >= 0){
			return 'Opera';
		}
		//Safari
		else if(explorer.indexOf("Safari") >= 0){
			return 'Safari';
		}
	}

	 $scope.exportTable = function(tableid) {//整个表格拷贝到EXCEL中

		var filename = 'MSSHE产能计划与目标报表' +  Date.now() + '.xls';
		if(getExplorer()=='ie')
		{
			var curTbl = document.getElementById(tableid);
			var oXL = new ActiveXObject("Excel.Application");

			//创建AX对象excel
			var oWB = oXL.Workbooks.Add();
			//获取workbook对象
			var xlsheet = oWB.Worksheets(1);
			//激活当前sheet
			var sel = document.body.createTextRange();
			sel.moveToElementText(curTbl);
			//把表格中的内容移到TextRange中
			sel.select();
			//全选TextRange中内容
			sel.execCommand("Copy");
			//复制TextRange中内容
			xlsheet.Paste();
			//粘贴到活动的EXCEL中
			oXL.Visible = true;
			//设置excel可见属性

			try {
				var fname = oXL.Application.GetSaveAsFilename(filename, "Excel Spreadsheets (*.xls), *.xls");
			} catch (e) {
				print("Nested catch caught " + e);
			} finally {
				oWB.SaveAs(fname);

				oWB.Close(savechanges = false);
				//xls.visible = false;
				oXL.Quit();
				oXL = null;
				//结束excel进程，退出完成
				//window.setInterval("Cleanup();",1);
				idTmr = window.setInterval("Cleanup();", 1);

			}

		}
		else
		{
			tableToExcel(tableid, filename);
		}
	}

	var tableToExcel = (function() {
		var uri = 'data:application/vnd.ms-excel;base64,',
			template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><meta charset="UTF-8"><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>',
			base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s))) },
			format = function(s, c) {
				return s.replace(/{(\w+)}/g,
					function(m, p) { return c[p]; })
			}
		return function(table, name) {
			if (!table.nodeType)
				table = document.getElementById(table)

			var ctx = {worksheet: 'sheet1' || 'Worksheet', table: table.innerHTML.replace('全选','id').replaceAll('<input id="','').replaceAll('" class="checkbox-id" type="checkbox">','')}
			document.getElementById('exportTable').href = uri + base64(format(template, ctx));
			document.getElementById("exportTable").download = name;
			document.getElementById("exportTable").click();
		}
	})();
});