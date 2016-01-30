<%
	ui.decorateWith("kenyaui", "panel", [ heading: "ART REGISTER" ])
%>


<% if (config.complete) { %>
<div class="ke-stack-item">
	<div class="widget-content">
		<table width="100%" border="1">
			<tr bgcolor="#778899">
				<td colspan="2">
					<center>Patient Identification</center> 
				</td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: left; vertical-align: top; width: 33%">
				
						<br/>Date  of start of ART :  ${artInitiationDate}
						<br/> <strong>Registration Number : ${patientWrap.napArtRegistrationNumber  }</strong>
						<br/><strong>Name : ${ patientName }</strong>
						<br/><strong>Address : </strong><small> ${ address.address1}.</small>
						<br/><strong>Village/City : </strong><small> ${ address.cityVillage}.</small>
						<strong>Township : </strong><small> ${ address.countyDistrict}.</small>
						<strong>State or Region : </strong><small> ${ address.stateProvince}.</small>
						<% if (personWrap.telephoneContact) { %>
						<strong>Patient's phone number : </strong><small> ${personWrap.telephoneContact}.</small>
						<% } %>
						<br/><strong>Age : ${ patientAge }</strong>
						<strong>Date of Birth : ${ birthDate }</strong>
						<br/><strong>Gender : ${ patientGender } </strong>
						<br/>
						<% if (patientWrap.nextOfKinName) { %>
						<strong>Treatment Supporter's Name: </strong><small>${patientWrap.nextOfKinName}</small>
						<% } %>
						<% if (patientWrap.nextOfKinContact) { %>
						<strong>Treatment Supporter's phone number: </strong><small>${patientWrap.nextOfKinContact}</small>
						<% } %>
						
						<br/>
						<br/> 
				</td>
			</tr>		
			<tr bgcolor="#778899">
				<td colspan="2">
					<center>ART details</center> 
				</td>
			</tr>	
			<tr>
				<td colspan="2" style="text-align: left; vertical-align: top; width: 33%">
						<strong> 
						<br/>Pregnancy : ${pregStatusVal}</strong>
						<br/>EDD : ${eddVal}
						<br/>ANC No. : ${ancNumberVal}
						<strong><br/>Infant No./Name - </strong> 
						<% 
						infantList.eachWithIndex { infant , i ->
  						  println "${i+1}. ${infant.value}"
						};
						%>
						<br/>
						<br/><strong>Date and Cause of end of follow-up</strong>
						<br/>Cause : ${dicontinuationReasonVal}
						<br/>Date : ${dicontinuationDateVal}
				</td>
			</tr>
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