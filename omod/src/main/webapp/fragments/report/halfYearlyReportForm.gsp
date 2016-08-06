<%
	ui.decorateWith("kenyaui", "panel", [ heading: "Half Yearly Report" ])

%>

<form id="halfYearlyReport">

	<label class="ke-field-label">Half Yearly Selection:</label>
	<span class="ke-field-content">
		<select style='width: 155px;height: 30px;' type="text" id="halfYearly" name="halfYearly" >
<% listOfYear.each { year -> %>
<option value="${year}">${year}</option>
<% } %>
</select>
	</span>
	
	<input type="button" value="View" onclick="viewHalfYearlyReport();"/>
	<input type="button" value="Export" onclick="excelExport_halfyearly();"/>
	<input type="button" value="Cancel" onclick="ke_cancel_halfyearly();"/>
</form>


<script type="text/javascript">
//View Report
function viewHalfYearlyReport() {
var halfYearly=jQuery('#halfYearly').val();
jQuery('#viewHalfYearlyReport').empty();

jQuery.ajax({
				type : "GET",
				url : getContextPath_halfyearly() + "/kenyaemr/reports/getHalfYearlyReport.page",
				data : ({
					halfYearly:halfYearly
				}),
				success : function(data) {
				jQuery("#viewHalfYearlyReport").html(data);	
				}
  });
}

//Excel import
function excelExport_halfyearly() {
var halfYearly=jQuery('#halfYearly').val();
jQuery('#viewHalfYearlyReport').empty();
//window.location = getContextPath_halfyearly() + "/kenyaemr/reports/excelExport.page?year=" + year;
//window.location = getContextPath_halfyearly() + "/kenyaemr/downloadd.form?year=" + year;

jQuery.ajax({
				type : "GET",
				url : getContextPath_halfyearly() + "/kenyaemr/reports/excelExport.page",
				data : ({
					halfYearly:halfYearly
				}),
				success : function(data) {
				
				}
  });

}

// get context path in order to build controller url
	function getContextPath_halfyearly() {
		pn = location.pathname;
		len = pn.indexOf("/", 1);
		cp = pn.substring(0, len);
		return cp;
	}

function ke_cancel_halfyearly() {
			ui.navigate('kenyaemr', 'reports/reportsHome');
}
</script>