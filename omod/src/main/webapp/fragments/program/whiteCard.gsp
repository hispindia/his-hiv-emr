<%
	ui.decorateWith("kenyaui", "panel", [ heading: "WHITE CARD" ])
%>

<script type="text/javascript">

 function htmlToExcel() {
    var divToPrint = document.getElementsByClassName('widget-content')[0];
    var uri = 'data:application/vnd.ms-excel,' +'<html><body>' + divToPrint.innerHTML + '</html>';
    
   
    var link = document.createElement("a");    
    link.href = uri;
    
   
    link.style = 'visibility:hidden';
    link.download ='WhiteCard.xls';
    
   
    document.body.appendChild(link);
    link.click();
            
  }
</script>


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
					<strong>Risk Factor for HIV :</strong> ${listAllRiskFactor}
					<br/><strong>For IDus Substitution therapy :</strong>${iduStatusValue} (${iduNameValue})
					<br/><strong>Literate :</strong>${literate}
					<br/><strong>Employed :</strong>${employed}
					<br/><strong>Alcoholic :</strong>${alcoholic} (${alcoholicType})
					<br/><strong>Income :</strong>${income} Kyats
				</td>
				
			</tr>
			<tr>
				<td>
					<br/><center>Family History</center> 
				</td>
			<tr>
			
			<tr>
				<tr>
					<td>Marital Status: ${civilStatusVal}</td>	
				</tr>
				<tr>
					<th>Name of Spouse/children</th>
					<th>Age</th>
					<th>Sex</th>
					<th>HIV +/-/unknown</th>
					<th>ART y/n</th>
				</tr>
				 <% for ( e in familyMembers ) { 
		 		def values = e.value.split(',')	%>
				<tr>
					<td><% println  values[0] %> </td>
					<td><% println  values[1] %> </td>
					<td><% println  values[2] %> </td>
					<td><% println  values[3] %> </td>
					<td><% println  values[4] %> </td>
				</tr>
				<%		} %>
			</tr>
			<tr>
				<td>
					<br/><center>Drug History</center> 
				</td>
			</tr>
			<tr>
				<tr>
					<td>ART received : ${artReceivedVal}</td>
				</tr>
				<tr>
					<td>If Yes : ${artReceivedTypeValue}</td>
				</tr>
				<tr>
					<td>Place : ${artReceivedPlaceValue}</td>
				</tr>
				<tr>
					<th>Drug</th>
					<th>Duration</th>
				</tr>
				 <% for ( d in drugList ) { 
		 		def values = d.value.split(',')	%>
				<tr>
					<td><% println  values[0] %> </td>
					<td><% println  values[1] %> </td>
				</tr>
				<%		} %>
			
				<td width="50%" valign="top">
					<br/>${ ui.includeFragment("kenyaui", "widget/obsHistoryTable", [ id: "tblhistory", patient: currentPatient, concepts: graphingConcepts ]) }
				</td>
			</tr>
		</table>
	</div>	
	<input type="button" onClick="htmlToExcel();"  value="Print" />
</div>
<% } %>




	

