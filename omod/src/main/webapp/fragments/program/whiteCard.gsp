<%
	ui.decorateWith("kenyaui", "panel", [ heading: "WHITE CARD" ])
%>

<% if (config.complete) { %>
<div class="ke-stack-item">
	<div class="widget-content">
		<table width="100%" border="0">
			<tr>
			<td style="text-align: left; vertical-align: top; width: 33%">
					<strong>${ patientAge }</strong><br/>
					${ patientGender } <small> ${ patientAddress }</small>
				</td>
				
			</tr>
			<tr>
				<td width="50%" valign="top">
					${ ui.includeFragment("kenyaui", "widget/obsHistoryTable", [ id: "tblhistory", patient: currentPatient, concepts: graphingConcepts ]) }
				</td>
			</tr>
		</table>
	</div>	
	<input type="button" onclick="PrintDiv()" value="Print" />
</div>
<% } %>

<script type="text/javascript">
        function PrintDiv() {
            var divToPrint = document.getElementsByClassName('widget-content')[0];
            var popupWin = window.open('', '_blank', 'width=300,height=400,location=no,left=200px');
            popupWin.document.open();
            popupWin.document.write('<html><body onload="window.print()">' + divToPrint.innerHTML + '</html>');
            popupWin.document.close();
        }
</script>