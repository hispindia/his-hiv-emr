<%
	ui.decorateWith("kenyaui", "panel", [ heading: "List Lab Orders" ])
%>
<table style="width:100%; text-align:center;">
<thead >
<tr>
<th>No.</th>
<th>Visit Date</th>
<th>Order date</th>
<th>Update Date</th>
<th>Action</th>
</tr>
</thead>
<% encounters.eachWithIndex { encounter , count -> %>
<tr class="ke-stack-item ke-navigable">
<td>${count+1}</td>
<td>${encounter.visit.dateCreated}</td>
<td>${encounter.dateCreated}</td>
<td></td>
<td><button class="ke-compact" type="button" onclick="enterResult('${encounter.id}')" ><img src="/openmrs/ms/uiframework/resource/kenyaui/images/glyphs/edit.png"></button</td>
</tr>
<% } %>
</table>
<input type="hidden" id="returnUrl" value="${returnUrl}"/>
<script>

function enterResult(encounterId) {
	var url  = jq("#returnUrl").val();
	ui.navigate(ui.pageLink('kenyaemr', 'intake/enterLabResultMain', { encounterId :  encounterId , returnUrl : url}) );
}

</script>
