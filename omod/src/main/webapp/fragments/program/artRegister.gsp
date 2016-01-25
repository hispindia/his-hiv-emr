<%
	ui.decorateWith("kenyaui", "panel", [ heading: "ART REGISTER" ])
%>

<% if (config.complete) { %>
<div class="ke-stack-item">
	<div class="widget-content">
	<table width="100%" border="0">
		<tr>
			<td width="50%" valign="top">
				${ ui.includeFragment("kenyaui", "widget/obsHistoryTable", [ id: "tblhistory", patient: currentPatient, concepts: graphingConcepts ]) }
			</td>
		</tr>
	</table>
	</div>
		<input type="button" onClick="htmlToExcel();"  value="Print" />
</div>
<% } %>


<script type="text/javascript">

 function htmlToExcel() {
     var divToPrint = document.getElementsByClassName('widget-content')[0];
    var uri = 'data:application/vnd.ms-excel,' +'<html><body>' + divToPrint.innerHTML + '</html>';
    
   
    var link = document.createElement("a");    
    link.href = uri;
    
   
    link.style = 'visibility:hidden';
    link.download ='MyReport.xls';
    
   
    document.body.appendChild(link);
    link.click();
            
  }
</script>
