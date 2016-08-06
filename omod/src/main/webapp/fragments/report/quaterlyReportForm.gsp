<%
	ui.decorateWith("kenyaui", "panel", [ heading: "Quaterly Report" ])

%>

<form id="hfgjjbb">

	<label class="ke-field-label">Quaterly Selection:</label>
	<span class="ke-field-content">
		<select style='width: 155px;height: 30px;' type="text" id="quaterly" name="quaterly" >
<% listOfYear.each { year -> %>
<option value="${year}">${year}</option>
<% } %>
</select>
	</span>
	
	<input type="button" value="View" onclick="viewQuaterlyReport();"/>
	<input type="button" value="Export" onclick="excelExport_quaterly();"/>
	<input type="button" value="Cancel" onclick="ke_cancel_quaterly();"/>
</form>


<script type="text/javascript">
//View Report
function viewQuaterlyReport() {
var quaterly=jQuery('#quaterly').val();
jQuery('#viewReportQuaterly').empty();

jQuery.ajax({
				type : "GET",
				url : getContextPath_quaterly() + "/kenyaemr/reports/getQuaterlyReport.page",
				data : ({
					quaterly:quaterly
				}),
				success : function(data) {
				jQuery("#viewReportQuaterly").html(data);	
				}
  });
}

//Excel import
function excelExport_quaterly() {
var year=jQuery('#year').val();
jQuery('#viewReport').empty();
//window.location = getContextPath_quaterly() + "/kenyaemr/reports/excelExport.page?year=" + year;
//window.location = getContextPath_quaterly() + "/kenyaemr/downloadd.form?year=" + year;

jQuery.ajax({
				type : "GET",
				url : getContextPath() + "/kenyaemr/reports/excelExport.page",
				data : ({
					year:year
				}),
				success : function(data) {
				
				}
  });

}

// get context path in order to build controller url
	function getContextPath_quaterly() {
		pn = location.pathname;
		len = pn.indexOf("/", 1);
		cp = pn.substring(0, len);
		return cp;
	}

function ke_cancel_quaterly() {
			ui.navigate('kenyaemr', 'reports/reportsHome');
}
</script>