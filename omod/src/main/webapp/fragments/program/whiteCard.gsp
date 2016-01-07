<%
	ui.decorateWith("kenyaui", "panel", [ heading: "WHITE CARD" ])
%>

<% if (config.complete) { %>
<div class="ke-stack-item">
	<div class="widget-content">
		<table width="100%" border="0">
			<tr>
				<td>
					<center>Patient Identification</center> 
				</td>
			</tr>
			<tr>
			<td style="text-align: left; vertical-align: top; width: 33%">
					<br/><strong>Name : ${ patientName }</strong>
					<br/><strong>Age : ${ patientAge }</strong>
					<strong>Date of Birth : ${ birthDate }</strong>
					<br/><strong>Gender : ${ patientGender } </strong>
					<br/><strong>Address : </strong><small> ${ address.address1}.</small>
					<% if (personWrap.telephoneContact) { %>
						<strong>Patient's phone number : </strong><small> ${personWrap.telephoneContact}.</small>
					<% } %>
					<br/><strong>Village/City : </strong><small> ${ address.cityVillage}.</small>
					<strong>Township : </strong><small> ${ address.countyDistrict}.</small>
					<strong>State or Region : </strong><small> ${ address.stateProvince}.</small>
					<br/>
					<% if (patientWrap.nextOfKinName) { %>
						<strong>Treatment Supporter's Name: </strong><small>${patientWrap.nextOfKinName}</small>
					<% } %>
					<% if (patientWrap.nextOfKinAddress) { %>
						<strong>Treatment Supporter's Address: </strong><small>${patientWrap.nextOfKinAddress}</small>
					<% } %>
					<% if (patientWrap.nextOfKinContact) { %>
						<strong>Treatment Supporter's phone number: </strong><small>${patientWrap.nextOfKinContact}</small>
					<% } %>
					
					<br/><strong>Date HIV+ test: </strong><small>${patientWrap.previousHivTestDate}</small> 
					<strong>Plcae: </strong><small>${patientWrap.previousHivTestPlace}</small>
					<br/>	<strong>Entry Point : </strong><small>${savedEntryPoint.valueCoded.name}</small>
					<br/>
					<% if (savedEntryPointValueDate) { %>
						<strong>Name Previous Clinic: </strong><small>${savedEntryPoint.valueText}</small>
					<strong>Date Transferred in: </strong><small>${savedEntryPointValueDate}</small>
					<% } %>
				</td>
			</tr>
			<tr>
				<td>
					<br/><center>Personal History</center> 
				</td>
			<tr>
			<tr>
				<td>
					${listAllRiskFactor}
				</td>
			</tr>
				<td width="50%" valign="top">
					<br/>${ ui.includeFragment("kenyaui", "widget/obsHistoryTable", [ id: "tblhistory", patient: currentPatient, concepts: graphingConcepts ]) }
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