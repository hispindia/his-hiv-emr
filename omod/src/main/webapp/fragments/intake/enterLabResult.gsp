<%
	ui.decorateWith("kenyaui", "panel", [ heading: "Enter Lab Results" ])
%>

<% if (listTests == null) { %>
No record found.
<% } else { %>
<form id="enterLabResultForm" action="${ ui.actionLink("kenyaemr", "intake/enterLabResult", "submit") }" method="post">
		<input type="hidden" name="visitId" value="${visit.visitId}"/>
		<% if (resultEncounter != null) { %>
			<input type="hidden" name="encounterId" value="${resultEncounter.encounterId}"/>
		<% } %>
<table>
<thead>
<tr>
<th><b>No.</b></th>
<th><b>Lab Test</b></th>
<th><b>Test Result</b></th>
<th>Unit</th>
<th><b>Reference Range</b></th>
<th><b>Comment</b></th>
</tr>
</thead>
<% listTests.eachWithIndex { test , count -> %>
<tr>
<td>${count+1}</td>
<td><input type="text" id="${test.obs.obsId}"  size="40" value="${test.name}" alt="${test.name}" disabled>
<input type="hidden" name="conceptIds" value="${test.conceptId}"/>
</td> 
<% if (listResultObs != null && listResultObs.size() > 0) { %>
	<% listResultObs.each { resultObs -> %>
		
		<% if (resultObs.concept.conceptId == test.conceptId) { %>
		<td>
		<% if (resultEncounter != null) { %>
			<input type="text" id="${test.conceptId}_value" name="${test.conceptId}_value" size="15" value="${resultObs.valueText}"  onblur="calculateComment(${test.conceptId})"></td><td>${test.units}</td>
		<% } else { %>
			<input type="text" id="${test.conceptId}_value" name="${test.conceptId}_value" size="15" value="" onblur="calculateComment(${test.conceptId})></td><td>${test.units}</td>
		<% } %>
		</td>
		<% } %>
	<% } %>
<% } else { %>

	<td><input type="text" id="${test.conceptId}_value" name="${test.conceptId}_value" size="15" value="" onblur="calculateComment(${test.conceptId})></td></td><td>${test.units}</td>
<% } %>
<td><input disabled id="${test.conceptId}_range" type="text" value="${test.range}"/></td>
<td><input disabled id="${test.conceptId}_comment" type="text" value=""/></td>
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

function calculateComment(conceptId) {
	var value =  parseInt(jq("#"+conceptId+"_value").val());
	var range = jq("#"+conceptId+"_range").val();
	var low = parseInt(range.split("-")[0]);
	var high = parseInt(range.split("-")[1]);
	var comment = "";
	var commentColor = "";
	if (!isNaN(value) && !isNaN(low) && !isNaN(high)) {
		if (value < low) {
			comment = "Low";
			commentColor = "red"; 
		} else if (value > high) {
			comment = "High";
			commentColor = "red";
		} else {
			comment = "Normal";
			commentColor = "green";
		}
	}
	jq("#"+conceptId+"_comment").val(comment);
	jq("#"+conceptId+"_comment").css("color",commentColor);
}

</script>