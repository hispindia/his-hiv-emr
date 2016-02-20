<div>
<form id="drug-order-form" method="post" action="${ ui.actionLink("kenyaemr", "dispensary/drugOrderList", "processDrugOrder") }">
<table style='width: 60%'>

<tr>
<th>S.No</th>
<th>Drug Name </th>
<th>Strength </th>
<th>Unit </th>
<th>Frequency </th>
<th>Duration </th>
<th>Issue Qunatity </th>
<th>Drug Regimen </th>
</tr>

<% drugOrderProcesseds.each { drugOrderProcessed -> %>
<% if (drugOrderProcessed!=null) { %>
<tr>
<td>${count++}</td>
<td>${drugOrderProcessed.drugOrder.concept.name} </td>
<td>${drugOrderProcessed.drugOrder.dose}</td>
<td>${drugOrderProcessed.drugOrder.units} </td>
<td>${drugOrderProcessed.drugOrder.frequency}</td>
<td>${drugOrderProcessed.durationPreProcess}</td>
<td><input type="text" id="${drugOrderProcessed.id}issueQuantity" name="${drugOrderProcessed.id}issueQuantity" size="12"></td>
<td>${drugOrderProcessed.drugRegimen}</td>
<td><input type="hidden" id="drugOrderProcessedIds" name="drugOrderProcessedIds" value="${drugOrderProcessed.id}"> </td>
</tr>
<% } %>
<% } %>

<% drugOrderObss.each { drugOrderObs -> %>
<% if (drugOrderObs!=null) { %>
<tr>
<td>${count++}</td>
<td>${drugOrderObs.drug} </td>
<td>${drugOrderObs.strength}</td>
<td>${drugOrderObs.formulation} </td>
<td>${drugOrderObs.frequency}</td>
<td>${drugOrderObs.duration}</td>
<td><input type="text" id="${drugOrderObs.obsGroupId}obsIssueQuantity" name="${drugOrderObs.obsGroupId}obsIssueQuantity" size="12"></td>
<td><input type="hidden" id="obsGroupIds" name="obsGroupIds" value="${drugOrderObs.obsGroupId}"></td>
</tr>
<% } %>
<% } %>

<tr>
<td>
<input type="hidden" id="patient" name="patient" value="${patient.patientId}">
</td>
</tr>
</table>

<div>
<button type="submit">
				<img src="${ ui.resourceLink("kenyaui", "images/glyphs/ok.png") }" /> ${ "Save" }
			</button>

<button type="button" class="cancel-button" onclick="ke_cancel();"><img src="${ ui.resourceLink("kenyaui", "images/glyphs/cancel.png") }" /> Cancel</button>
</div>

</form>
</div>

<script type="text/javascript">
jq(function() {
	kenyaui.setupAjaxPost('drug-order-form', {
		onSuccess: function(data) {
			ui.navigate('kenyaemr', 'dispensary/dispensing');
		}
	});
});
	
function ke_cancel() {
			ui.navigate('kenyaemr', 'dispensary/dispensing');
		}

function confirmDrugOrder() {
//onsubmit="javascript:return confirmDrugOrder();"		
if(confirm("Are you sure?")){
return true;
 }
return false;
}	
</script>