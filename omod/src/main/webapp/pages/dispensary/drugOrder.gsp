<%
		
	ui.decorateWith("kenyaemr", "standardPage", [ patient: currentPatient ])
%>

<div class="ke-patientheader">

</div>

<form id="drug-order-form" method="post" action="${ ui.actionLink("kenyaemr", "dispensary/drugOrderList", "processDrugOrder") }">
<div>
<input size="5" id="slNoah" name="slNoah" value="S.No" disabled>
	<input type="text" id="drugNameah" name="drugNameah" size="20" value="Drug Name" disabled>
    <input type="text" id="formulationah" name="formulationah" size="20" value="Formulation" disabled>
    <input type="text" id="strengthah" name="strengthah" size="20" value="Strength" disabled>
    <input type="text" id="frequencyah" name="frequencyah" size="20" value="Frequency" disabled>
    <input type="text" id="durationah" name="durationah" size="20" value="Duration" disabled>
    <input type="text" id="issuequantityh" name="issuequantityh" size="20" value="Issue Qunatity" disabled>
</div>

<div>

	${ ui.includeFragment("kenyaemr", "dispensary/drugOrderList", [ patient: currentPatient ]) }

</div>

<div class="ke-panel-footer">
<button type="submit">
				<img src="${ ui.resourceLink("kenyaui", "images/glyphs/ok.png") }" /> ${ "Save" }
			</button>

<button type="button" class="cancel-button"><img src="${ ui.resourceLink("kenyaui", "images/glyphs/cancel.png") }" /> Cancel</button>
</div>

</form>


