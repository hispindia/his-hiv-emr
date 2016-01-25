<%
	ui.decorateWith("kenyaemr", "standardPage", [ patient: currentPatient ])
%>
${ ui.includeFragment("kenyaemr", "intake/enterLabResult", [visit : visit, returnUrl : returnUrl ]) }