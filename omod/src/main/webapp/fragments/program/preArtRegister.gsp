<%
	ui.decorateWith("kenyaui", "panel", [ heading: "PRE ART REGISTER" ])
%>

<% if (config.complete) { %>
<div class="ke-stack-item">
	<div class="widget-content">
		<table id="table1" width="100%" border="1">
			<tr bgcolor="#778899">
				<td colspan="4">
					<h4><strong><center>Patient Identification</center> </strong></h4>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: left; vertical-align: top; width: 30%; padding-left:1%">
						<br/> <strong>Registration Number : </strong>
						<br/><strong>Name :</strong>
						<br/><strong>Address : </strong>
						<br/><strong>Village/City : </strong>
						<br/><strong>Township : </strong>
						<br/><strong>State or Region : </strong>
						<br/><strong>Age : </strong>
						<br/><strong>Date of Birth : </strong>
						<br/><strong>Gender :  </strong>
						
						<br/>
						<br/> 
				</td>
                <td colspan="2" style="text-align: left; vertical-align: top; width: 70%; padding-left:1%">
						<br/> ${patientWrap.preArtRegistrationNumber  }
						<br/> ${ patientName }
						<br/> ${ address.address1}.
						<br/> ${ address.cityVillage}.
						<br/> ${ address.countyDistrict}.
						<br/> ${ address.stateProvince}.
						<br/> ${ patientAge }
						<br/> ${ birthDate }
						<br/> ${ patientGender } 
						
						<br/>
						<br/> 
				</td>
			</tr>		
			<tr bgcolor="#778899">
				<td colspan="4">
					<h4><strong><center>Pre-ART details</center></strong></h4>
				</td>
			</tr>	
			<tr>
				<td colspan="2" style="text-align: left; vertical-align: top; width: 30%; padding-left:1%">
					<% if (enrollmentStatus) { %>
						<strong>Status of enrollement : </strong>
					<% } %>	
						<strong> 
						<br/>Pregnancy : </strong>
						<br/><strong>EDD : </strong>
						<br/><strong>ANC No. :</strong> 
						<strong><br/>Infant No./Name - </strong> 
						
						<br/><strong>Risk Factor : 	</strong>
						<br/><strong>Date medically eligible for ART : </strong>
						<br/><strong>ART start date (transfered to ART register) :</strong>
							<br/>
						<br/>  
				</td>
                <td colspan="2" style="text-align: left; vertical-align: top; width: 70%; padding-left:1%">
					<% if (enrollmentStatus) { %>
						${ enrollmentStatus.valueCoded.name }
					<% } %>	
						
						<br/> ${pregStatusVal}
						<br/> ${eddVal}
						<br/> ${ancNumberVal}
						<br/>
						<% 
						infantList.eachWithIndex { infant , i ->
  						  println "${i+1}. ${infant.value}"
						};
						%>
						<br/>	${listAllRiskFactor}
						<br/>  ${artEligibleDate}
						<br/>  ${artInitiationDate}
							<br/>
						<br/> 
				</td>
			</tr>
			<tr>
				<td colspan="8" width="50%" valign="top">
					${ ui.includeFragment("kenyaui", "widget/obsHistoryTable", [ id: "tblhistory", patient: currentPatient, concepts: graphingConcepts ]) }
				</td>
			</tr>
		</table>
	</div>
	<a id="dlink"  style="display:none;"></a> 
	<input type="button" onClick="tableToExcel('table1','PRE ART Register','${patientWrap.preArtRegistrationNumber}-PRE ART Register.xls');"  value="Export as Excel" />
	
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
