
<%
	ui.decorateWith("kenyaui", "panel", [ heading: "ART REGISTER" ])
%>


<% if (config.complete) { %>
<div class="ke-stack-item">
	<div class="widget-content">
		
			
		<table id="table1"  width="100%" border="1" bordercolor="#000000" style="vertical-align:top">
		<tr bgcolor="#778899">
				<td colspan="4">
					<h4><strong><center>Patient Identification</center> </strong></h4>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: left; vertical-align: top; width: 30%; padding-left:1%">
				
						<br/><strong>Date  of start of ART : </strong> 
						<br/> <strong>Registration Number :</strong> 
						<br/><strong>Name : </strong>
						<br/><strong>Address : </strong>
						<br/><strong>Village/City : </strong>
						<br/><strong>Township : </strong>
						<br/><strong>State or Region : </strong>
						<% if (personWrap.telephoneContact) { %>
						<br/><strong>Patient's phone number : </strong>
						<% } %>
						<br/><strong>Age : </strong>
						<br/><strong>Date of Birth :</strong>
						<br/><strong>Gender :  </strong>
						<br/>
						<% if (patientWrap.nextOfKinName) { %>
						<strong>Treatment Supporter's Name: </strong>
						<% } %>
						<% if (patientWrap.nextOfKinContact) { %>
						<br/><strong>Treatment Supporter's phone number: </strong>
						<% } %>
						
						<br/>
						<br/>
						 
				</td>
                <td colspan="2" style="text-align: left; vertical-align: top; width: 70%; padding-left:1%">
				
						<br/> ${artInitiationDate}
						<br/>  ${patientWrap.napArtRegistrationNumber  }
						<br/>${ patientName }
						<br/><small> ${ address.address1}.</small>
						<br/><small> ${ address.cityVillage}.</small>
						<br/><small> ${ address.countyDistrict}.</small>
						<br/><small> ${ address.stateProvince}.</small>
						<% if (personWrap.telephoneContact) { %>
						<br/><small> ${personWrap.telephoneContact}.</small>
						<% } %>
						<br/> ${ patientAge }
						<br/>${ birthDate }
						<br/> ${ patientGender } 
						<br/>
						<% if (patientWrap.nextOfKinName) { %>
						<small>${patientWrap.nextOfKinName}</small>
						<% } %>
						<% if (patientWrap.nextOfKinContact) { %>
						<br/><small>${patientWrap.nextOfKinContact}</small>
						<% } %>
						
						<br/>
						<br/>
					
				</td>
			</tr>	
				
			
		
		<tr bgcolor="#778899">
				<td colspan="4">
					<h4><strong><center>ART details</center></strong></h4>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: left; vertical-align: top;  width: 30%; padding-left:1%">
						<strong> 
						<br/>Pregnancy : </strong>
						<br/><strong>EDD : </strong>
						<br/><strong>ANC No. :</strong> 
						<strong><br/>Infant No./Name - </strong> 
						
						<br/>
						<br/><strong>Date and Cause of end of follow-up</strong>
						<br/><strong>Cause : </strong>
						<br/><strong>Date : </strong>
						<br/>
						<br/>
						
				</td>
                <td colspan="2" style="text-align: left; vertical-align: top; width: 70%; padding-left:1%">
						
						<br/> ${pregStatusVal}
						<br/> ${eddVal}
						<br/> ${ancNumberVal}
						<br/>  
						<% 
						infantList.eachWithIndex { infant , i ->
  						  println "${i+1}. ${infant.value}"
						};
						%>
						<br/>
						<br/>
						<br/>${dicontinuationReasonVal}
						<br/>${dicontinuationDateVal}
						<br/>
						<br/>
						
				</td>
			</tr>
            
			
			<tr>
				<td colspan="8" bordercolor="#000000" width="50%" valign="top">
					${ ui.includeFragment("kenyaui", "widget/obsHistoryTable", [ id: "tblhistory", patient: currentPatient, concepts: graphingConcepts ]) }
				</td>
				
			</tr>
		</table>
		
	</div>
	<a id="dlink"  style="display:none;"></a> 
	<input type="button" onClick="tableToExcel('table1','ART Register','${patientWrap.napArtRegistrationNumber}-ART Register.xls');"  value="Export as Excel" />
</div>
<% } %>




<script type="text/javascript">
var tableToExcel = (function() {
  var uri = 'data:application/vnd.ms-excel;base64,'
    , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table >{table}</table></body></html>'
    , base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s))) }
    , format = function(s, c) { return s.replace(/{(\\w+)}/g, function(m, p) { return c[p]; }) }
  return function(table, name, filename) {
    if (!table.nodeType) table = document.getElementById(table)
    var ctx = {worksheet: name || 'Worksheet', table: table.innerHTML}
    
            document.getElementById("dlink").href = uri + base64(format(template, ctx));
            document.getElementById("dlink").download = filename;
            document.getElementById("dlink").click();
    
  }

})()
</script>
  