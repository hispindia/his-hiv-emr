<%
	ui.decorateWith("kenyaui", "panel", [ heading: "Enter Lab Results" ])
%>

<% if (listObs == null) { %>
No record found.
<% } else { %>
<form id="enterLabResultForm" action="${ ui.actionLink("kenyaemr", "intake/enterLabResult", "submit") }" method="post">
		<input type="hidden" name="visitId" value="${visit.visitId}"/>
		<% if (resultEncounter != null) { %>
			<input type="hidden" name="encounterId" value="${resultEncounter.encounterId}"/>
		<% } %>
<table>
<tr>
<td><b>Lab Test</b></td>
<td><b>Test Result</b></td>
</tr>
<% listObs.each { obs -> %>
<tr>
<td><input type="text" id="${obs.obsId}"  size="40" value="${obs.valueCoded.name}" disabled>
<input type="hidden" name="conceptIds" value="${obs.valueCoded.conceptId}"/>
</td> 
<% if (listResultObs != null && listResultObs.size() > 0) { %>
	<% listResultObs.each { resultObs -> %>
		
		<% if (resultObs.concept.conceptId == obs.valueCoded.conceptId) { %>
		<td>
		<% if (resultEncounter != null) { %>
			<input type="text" id="${obs.valueCoded.conceptId}_value" name="${obs.valueCoded.conceptId}_value" size="20" value="${resultObs.valueText}" >
		<% } else { %>
			<input type="text" id="${obs.valueCoded.conceptId}_value" name="${obs.valueCoded.conceptId}_value" size="20" value="" >
		<% } %>
		</td>
		<% } %>
	<% } %>
<% } else { %>

	<td><input type="text" id="${obs.valueCoded.conceptId}_value" name="${obs.valueCoded.conceptId}_value" size="20" value="" ></td>
<% } %>
</tr>
<% } %>
</table> 
</br>
<input type="submit" value="Save"/>
<input type="button" value="Cancel" onclick="ui.navigate('${returnUrl}')"/>
</form>
<% } %>

<script type="text/javascript">
jq(function() {
	jq('#enterLabResultForm .cancel-button').click(function() {
		ui.navigate('${ returnUrl }');
	});

	kenyaui.setupAjaxPost('enterLabResultForm', {
		onSuccess: function(data) {
			ui.navigate('${ returnUrl }');
		}
	});
});
</script>