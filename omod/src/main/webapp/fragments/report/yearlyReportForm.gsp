<%
	ui.decorateWith("kenyaui", "panel", [ heading: "Yearly Report" ])

%>

<form id="hfgjjbb">

	<label class="ke-field-label">Year Selection:</label>
	<span class="ke-field-content">
		<select style='width: 155px;height: 30px;' type="text" id="year" name="year" >
<% listOfYear.each { year -> %>
<option value="${year}">${year}</option>
<% } %>
</select>
	</span>
	
	<input type="button" value="View" onclick="viewReport();"/>
	<input type="button" value="Export" onclick="excelExport();"/>
	<input type="button" value="Cancel" onclick="ke_cancel();"/>
</form>


<script type="text/javascript">
//View Report
function viewReport() {
var year=jQuery('#year').val();
jQuery('#viewReport').empty();

jQuery.ajax({
				type : "GET",
				url : getContextPath() + "/kenyaemr/reports/getReport.page",
				data : ({
					year:year
				}),
				success : function(data) {
				jQuery("#viewReport").html(data);	
				}
  });
}

//Excel import
function excelExport() {
var year=jQuery('#year').val();
jQuery('#viewReport').empty();
//window.location = getContextPath() + "/kenyaemr/reports/excelExport.page?year=" + year;
//window.location = getContextPath() + "/kenyaemr/downloadd.form?year=" + year;

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