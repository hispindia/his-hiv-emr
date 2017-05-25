<%
	ui.decorateWith("kenyaui", "panel", [ heading: "NAP MONTHLY REPORT(Adult)" ])

%>

<form id="napmonthlyreportadultform">

	<label class="ke-field-label">Request Report:</label>
	<span class="ke-field-content">
	Start Date
	${ ui.includeFragment("kenyaui", "widget/field", [ id: "start-date", object: command, property: "startDate" ]) }
	
	<br/>
	<br/>
	End Date&nbsp;&nbsp;
	${ ui.includeFragment("kenyaui", "widget/field", [ id: "end-date", object: command, property: "endDate" ]) }
	</span>
	
	<input type="button" value="View" onclick="viewReport();"/>
	<input type="button" value="Export" onclick="exportNAPAdultReport();"/>
	<input type="button" value="Cancel" onclick="ke_cancel();"/>
</form>


<script type="text/javascript">
//View Report
function viewReport() {
var startDate=jQuery('#start-date').val();
var endDate=jQuery('#end-date').val();
if(startDate=="" || endDate==""){
alert("Provide Start Date and End Date");
return false;
}
else{
var ageCategory=">14";
jQuery('#viewReport').empty();

jQuery.ajax({
				type : "GET",
				url : getContextPath() + "/kenyaemr/reports/getNapMonthlyReportForAdult.page",
				data : ({
					startDate:startDate,
					endDate:endDate,
					ageCategory:ageCategory
				}),
				success : function(data) {
				jQuery("#viewReport").html(data);	
				}
  });
}
}

function exportNAPAdultReport() {
var startDate=jQuery('#start-date').val();
var endDate=jQuery('#end-date').val();
if(startDate=="" || endDate==""){
alert("Provide Start Date and End Date");
return false;
}
else{
var ageCategory=">14";
jQuery('#viewReport').empty();

jQuery.ajax({
				type : "GET",
				url : getContextPath() + "/kenyaemr/reports/getNapMonthlyReportForAdult.page",
				data : ({
					startDate:startDate,
					endDate:endDate,
					ageCategory:ageCategory
				}),
				success : function(data) {
				jQuery("#viewReport").html(data);
				var uri = "data:application/vnd.ms-excel;base64,";
				var table_div = document.getElementById('viewReport');
                var table_html = table_div.innerHTML;
                var template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table >{table}</table></body></html>';
				var link = document.createElement("a");
                var ctx = {worksheet: 'nap adult report', table: table_html};
                link.href = uri + base64(format(template, ctx));
                link.style = 'visibility:hidden';
		        link.download ='${ currDate } - nap adult report.xls';
		        document.body.appendChild(link);
		        link.click();
				}
  });
}
}

function base64(s) {
return window.btoa(unescape(encodeURIComponent(s)));
}

function format(s, c) {
return s.replace(/{(\\w+)}/g, function(m, p) { return c[p]; });
}

// get context path in order to build controller url
	function getContextPath() {
		pn = location.pathname;
		len = pn.indexOf("/", 1);
		cp = pn.substring(0, len);
		return cp;
	}

function ke_cancel() {
			ui.navigate('kenyaemr', 'reports/reportsHome');
}
</script>

<div class="ke-page-content" id="exportYearlyReport" hidden="hidden">

</div>