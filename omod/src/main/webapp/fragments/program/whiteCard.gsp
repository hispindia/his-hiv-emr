<%
	ui.decorateWith("kenyaui", "panel", [ heading: "WHITE CARD" ])
%>

<style>
td, th, table, tr{
	padding : 5px;
}
</style>

<style>
.tbheader {
    color: white;
} 
</style>

<script>
	var tableToExcel = (function() {
		var uri = 'data:application/vnd.ms-excel;base64,'
		, template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table >{table}</table></body></html>'
		, base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s))) }
		, format = function(s, c) { return s.replace(/{(\\w+)}/g, function(m, p) { return c[p]; }) }
		return function(table, name) {
		if (!table.nodeType) table = document.getElementById(table)
		var ctx = {worksheet: name || 'White Card', table: table.innerHTML}
		
		var link = document.createElement("a");
		link.href = uri + base64(format(template, ctx));

		link.style = 'visibility:hidden';
		link.download ='${ patientName } - WhiteCard.xls';

		document.body.appendChild(link);
		link.click();
		
		}
	})()
</script>


<% if (config.complete) { %>
<div class="ke-stack-item" width="100%">
	
	<div class="widget-content" id="printArea" width="100%">
		
		<table border="1" width="100%"  id="tablecontent" class="table2excel">
			
			<!--PATIENT DETAILS ==========================================================================================================================================-->
			<tr >
				<td colspan="2" class="tbheader" bgcolor="#778899">
					<center><strong>Patient Identification</strong></center>
				</td>
			</tr>
			<tr>
				<td colspan="2" width="100%">
					<table width="100%">
						<tr>
							<td width="50%">
								<strong>Name : </strong>${ patientName }
								</br><strong>Age : </strong>${ patientAge }
								</br><strong>Date of Birth : </strong>${ birthDate }
								</br><strong>Gender : </strong>${ patientGender }
								</br><strong>Address : </strong> <small> ${ address.address1}.</small>
								<% if (personWrap.telephoneContact) { %>
									</br><strong>Patient's phone number : </strong><small> ${personWrap.telephoneContact}.</small>
								<% } %>
								</br><strong>Village/City : </strong><small> ${ address.cityVillage}.</small>
								</br><strong>Township : </strong><small> ${ address.countyDistrict}.</small>
								</br><strong>State or Region : </strong><small> ${ address.stateProvince}.</small></br>
								
								</br><strong>Date HIV+ test: </strong><small>${patientWrap.previousHivTestDate}</small>
								</br><strong>Plcae: </strong><small>${patientWrap.previousHivTestPlace}</small>
								</br><strong>Entry Point : </strong><small>${savedEntryPoint.valueCoded.name}</small></br>
								
								<% if (savedEntryPointValueDate) { %>
									<strong>Name Previous Clinic: </strong><small>${savedEntryPoint.valueText}</small>
									</br><strong>Date Transferred in: </strong><small>${savedEntryPointValueDate}</small></br>
								<% } %>
							</td>
							<td width="50%" valign="top">
								<% if (patientWrap.nextOfKinName) { %>
									<strong>Treatment Supporter's Name: </strong><small>${patientWrap.nextOfKinName}</small>
								<% } %>
								<% if (patientWrap.nextOfKinAddress) { %>
									</br><strong>Treatment Supporter's Address: </strong><small>${patientWrap.nextOfKinAddress}</small>
								<% } %>
								<% if (patientWrap.nextOfKinContact) { %>
									</br><strong>Treatment Supporter's phone number: </strong><small>${patientWrap.nextOfKinContact}</small>
								<% } %>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			
			<!--HISTORY ==========================================================================================================================================-->
			<tr>
				<td width="50%" colspan="1" valign="top">
					<table border="0" width="100%">
						<tr >
							<td class="tbheader" bgcolor="#778899">
								<center><strong>Personal History</strong></center>
							</td>
						</tr>
						<tr>
							<td>
								<strong>Risk Factor for HIV :</strong> ${listAllRiskFactor}
								</br><strong>For IDus Substitution therapy :</strong>${iduStatusValue} (${iduNameValue})
								</br><strong>Literate :</strong>${literate}
								</br><strong>Employed :</strong>${employed}
								</br><strong>Alcoholic :</strong>${alcoholic} (${alcoholicType})
								</br><strong>Income :</strong>${income} Kyats
							</td>
						</tr>
					</table>
				</td>	
				
				<td width="50%" colspan="1" valign="top">
					<table border="0" width="100%">
						<tr >
							<td class="tbheader" bgcolor="#778899">
								<center><strong>Family History</strong></center>
							</td>
						</tr>
						<tr>
							<td><strong>Marital Status: <strong> ${civilStatusVal}</br></td>
						</tr>
						
						<tr>
							<td>
								<table border="1" width="100%">
									<tr>
										<td><strong>Name of Spouse/Children</strong></td>
										<td><strong>Age</strong></td>
										<td><strong>Sex</strong></td>
										<td><strong>HIV +/-/ Unknown</strong></td>
										<td><strong>ART Y/N</strong></br></td>
										
									</tr>
									<% for ( e in familyMembers ) {  %>
										<% def values = e.value.split (",") %>	
										<tr>
											<td><% println  values[0] %> </td>
											<td><% println  values[1] %> </td>
											<td><% println  values[2] %> </td>
											<td><% println  values[3] %> </td>
											<td><% println  values[4] %> </br></td>
										</tr>
									<% } %>						
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			
			<!--DRUGS ========================================================================================================================================== -->
			<tr>
				<td width="50%" valign="top">
					<table width="100%">
						<tr>
							<td colspan="2" class="tbheader" bgcolor="#778899">
								<center><strong>Drug History</strong></center>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<table border="0" width="100%">
									<tr>
										<td>
											<tr>
												<td colspan="3"><strong>ART received : </strong>${artReceivedVal}</br></td>
											</tr>
											<tr>
												<td colspan="3"><strong>If Yes : </strong>${artReceivedTypeValue}</br></td>
											</tr>
											<tr>
												<td colspan="3"><strong>Place : </strong>${artReceivedPlaceValue}</br></td>
											</tr>
											<tr>
												<td colspan="3"><strong>Duration : </strong>${drugDurationVal} months</br></td>
											</tr>
											<tr>
												<td colspan="3"><strong>Drug Regimen : </strong>${drugNameVal}</br>
												</td>
											</tr>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td> 
			
			<!--Exposed-infant follow-up ========================================================================================================================================== -->
				<td width="50%" valign="top">
					<table width="100%">
						<tr>
							<td class="tbheader" bgcolor="#778899">
								<center><strong>End of Follow-up for Antiretroviral therapy</strong></center>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<b>Reason: </b>${programDiscontinuationReasonVal} </br> 
								<b>Date : </b>${dataPlaceVal}
							</td>
						</tr>
					</table>
				</td>
			</tr>
			
			<!--Exposed-infant follow-up ========================================================================================================================================== -->
			
			<tr >
				<td colspan="2" class="tbheader" bgcolor="#778899">
					<center><strong>Exposed Infant Follow Up</strong></center>
				</td>
			</tr> 
			<tr>
				<td colspan="2">
					<table border="1" width="100%">
						<tr >
							
							<th><strong>Exposed-Infant Name/No</strong></th>
							<th><strong>DOB</strong></th>
							<th><strong>Infant feeding practice</strong></th>
							<th><strong>CPT started date</strong></th>
							<th><strong>HIV Test type</strong></th>
							<th><strong>Result</strong></th>
							<th><strong>Result date</strong></th>
							<th><strong>Final status</strong></th>
							<th><strong>Unique Id (if confirmed)</strong></br></th>
							
						</tr>
						<% for ( d in infantList ) { %>
						<% def values = d.value.split(",")	%>
							<tr>
								<td><% println  values[0] %> </td>
								<td><% println  values[1] %> </td>
								<td><% println  values[2] %> </td>
								<td><% println  values[3] %> </td>
								<td><% println  values[4] %> </td>
								<td><% println  values[5] %> </td>
								<td><% println  values[6] %> </td>
								<td><% println  values[7] %> </td>
								<td><% println  values[8] %> </br></td>
							</tr>
						<% } %>
					</table>
				</td>
			</tr>
			
			
			
			
		</table>
		<table border="1" id="HIVtable">
			
		<!--Patient HIV care & Antiretroviral treatment Follow-up============================================================================================================= -->
			<tr>
				<td colspan="2" class="tbheader" bgcolor="#778899">
					<center><strong>Patient HIV Care & Antiretroviral Treatment Follow Up</strong></center>
				</td>
			</tr>
			<tr>
				<td valign="top" class="table" colspan="2">
					${ ui.includeFragment("kenyaui", "widget/obsHistoryTable", [ id: "tblhistory", patient: currentPatient, concepts: graphingConcepts ]) }
				</td>
			</tr>
		</table>
	</div>
	
	</br></br>
	
	<input type="button" id="toexcel" value="Export as Excel" onclick="tableToExcel('printArea')"/>
	
</div>
<% } %>
