<!-- On clicking on the patient ID; for new patient – automatically, 
the first “4 forms” will pop up sequentially that is personal history, drug history , family history and obstetric history. 
After that , the user on clicking each form the respective form opens up.  

http://localhost:8080/openmrs/kenyaemr/enterForm.page?patientId=4&formUuid=e4b506c1-7379-42b6-a374-284469cba8da&appId=kenyaemr.registration&returnUrl=%2Fopenmrs%2Fkenyaemr%2Fregistration%2FregistrationViewPatient.page%3FpatientId%3D4%26&
formUuid=e4b506c1-7379-42b6-a374-284469cba8da

 patientId: patient.id, formUuid: defaultEnrollmentForm.targetUuid, appId: currentApp.id, returnUrl: ui.thisUrl() 
 
 
-->
<%
	ui.includeJavascript("kenyaemr", "controllers/patient.js")

	def heading = config.heading ?: "Matching Patients"
	
	//def formUuid = "e4b506c1-7379-42b6-a374-284469cba8da"
	def formUuid = "7efa0ee0-6617-4cd7-8310-9f95dfee7a82";
	
%>
<div class="ke-panel-frame" ng-controller="PatientSearchResults" ng-init="init('${ currentApp.id }', '${ config.pageProvider }', '${ config.page }')">
	<div class="ke-panel-heading">${ heading }</div>
	<div class="ke-panel-content">
		<div class="ke-stack-item ke-navigable" ng-repeat="patient in results" ng-click="onResultClickNewPatient(patient, '${currentApp.id}' , '${formUuid}', '${ ui.thisUrl() }' )">
			${ ui.includeFragment("kenyaemr", "patient/result.full") }
		</div>
		<div ng-if="results.length == 0" style="text-align: center; font-style: italic">None</div>
	</div>
</div>