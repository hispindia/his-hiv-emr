<form id="drug-order-form" method="post" action="${ ui.actionLink("kenyaemr", "dispensary/drugOrderList", "processDrugOrder") }">
<div>
<input size="5" id="slNoah" name="slNoah" value="S.No" disabled>
	<input type="text" id="drugNameah" name="drugNameah" size="20" value="Drug Name" disabled>
	 <input type="text" id="strengthah" name="strengthah" size="20" value="Strength" disabled>
    <input type="text" id="formulationah" name="formulationah" size="20" value="Unit" disabled>
    <input type="text" id="frequencyah" name="frequencyah" size="20" value="Frequency" disabled>
    <input type="text" id="durationah" name="durationah" size="20" value="Duration" disabled>
    <input type="text" id="issuequantityh" name="issuequantityh" size="20" value="Issue Qunatity" disabled>
</div>

<% drugOrderProcesseds.each { drugOrderProcessed -> %>
<% if (drugOrderProcessed!=null) { %>
<div>
<input type="text" id="slNo" name="slNo" size="5" value="${count++}" disabled>
<input type="text" id="${drugOrderProcessed.id}drugName" name="${drugOrderProcessed.id}drugName" size="20" value="${drugOrderProcessed.drugOrder.concept.name}" disabled>
<input type="text" id="${drugOrderProcessed.id}dose" name="${drugOrderProcessed.id}dose" size="20" value="${drugOrderProcessed.drugOrder.dose}" disabled>
<input type="text" id="${drugOrderProcessed.id}units" name="${drugOrderProcessed.id}units" size="20" value="${drugOrderProcessed.drugOrder.units}" disabled>
<input type="text" id="${drugOrderProcessed.id}frequency" name="${drugOrderProcessed.id}frequency" size="20" value="${drugOrderProcessed.drugOrder.frequency}" disabled>
<input type="text" id="${drugOrderProcessed.id}durationPreProcess" name="${drugOrderProcessed.id}durationPreProcess" size="20" value="${drugOrderProcessed.durationPreProcess}" disabled>
<input type="text" id="${drugOrderProcessed.id}issueQuantity" name="${drugOrderProcessed.id}issueQuantity" size="20">
<input type="hidden" id="drugOrderProcessedIds" name="drugOrderProcessedIds" value="${drugOrderProcessed.id}">
</div>
<% } %>
<% } %>

<% drugOrderObss.each { drugOrderObs -> %>
<% if (drugOrderObs!=null) { %>
<div>
<input type="text" id="slNo" name="slNo" size="5" value="${count++}" disabled>
<input type="text" id="obsDrugName" name="obsDrugName" size="20" value="${drugOrderObs.drug}" disabled>
<input type="text" id="obsStrength" name="obsStrength" size="20" value="${drugOrderObs.strength}" disabled>
<input type="text" id="obsFormulation" name="obsFormulation" size="20" value="${drugOrderObs.formulation}" disabled>
<input type="text" id="obsFrequency" name="obsFrequency" size="20" value="${drugOrderObs.frequency}" disabled>
<input type="text" id="obsDurationPreProcess" name="obsDurationPreProcess" size="20" value="${drugOrderObs.duration}" disabled>
<input type="text" id="${drugOrderObs.obsGroupId}obsIssueQuantity" name="${drugOrderObs.obsGroupId}obsIssueQuantity" size="20">
<input type="hidden" id="obsGroupIds" name="obsGroupIds" value="${drugOrderObs.obsGroupId}">
</div>
<% } %>
<% } %>

<div>
<input type="hidden" id="patient" name="patient" value="${patient.patientId}">
</div>

<div class="ke-panel-footer">
<button type="submit">
				<img src="${ ui.resourceLink("kenyaui", "images/glyphs/ok.png") }" /> ${ "Save" }
			</button>

<button type="button" class="cancel-button" onclick="ke_cancel();"><img src="${ ui.resourceLink("kenyaui", "images/glyphs/cancel.png") }" /> Cancel</button>
</div>

</form>

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
	
</script>