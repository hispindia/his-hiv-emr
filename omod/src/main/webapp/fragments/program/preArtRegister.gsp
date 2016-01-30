<%
	ui.decorateWith("kenyaui", "panel", [ heading: "PRE ART REGISTER" ])
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
						<br/> <strong>Registration Number : ${patientWrap.preArtRegistrationNumber  }</strong>
						<br/><strong>Name : ${ patientName }</strong>
						<br/><strong>Address : </strong><small> ${ address.address1}.</small>
						<br/><strong>Village/City : </strong><small> ${ address.cityVillage}.</small>
						<strong>Township : </strong><small> ${ address.countyDistrict}.</small>
						<strong>State or Region : </strong><small> ${ address.stateProvince}.</small>
						<br/><strong>Age : ${ patientAge }</strong>
						<strong>Date of Birth : ${ birthDate }</strong>
						<br/><strong>Gender : ${ patientGender } </strong>
						<br/>
						<br/> 
				</td>
			</tr>		
			<tr bgcolor="#778899">
				<td colspan="2">
					<center>Pre-ART details</center> 
				</td>
			</tr>	
			<tr>
				<td colspan="2" style="text-align: left; vertical-align: top; width: 33%">
						<strong>Status of enrollement : </strong>${ enrollmentStatus.valueCoded.name }
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
						<br/>Risk Factor : 	${listAllRiskFactor}
						<br/>Date medically eligible for ART :  ${artEligibleDate}
						<br/>ART start date (transfered to ART register) :  ${artInitiationDate}
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