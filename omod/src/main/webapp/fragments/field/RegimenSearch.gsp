<%
	ui.includeJavascript("kenyaemr", "controllers/drugRegimenController.js")

%>
<script type="text/javascript">
var patientId=${patient.patientId};	
</script>

<div ng-controller="DrugCtrl" data-ng-init="init()">
<input type="text" ng-model="drugKey" id="drugKey" name="drugKey" placeholder="search box" uib-typeahead="drug as drug for drug in myDrug | filter : drugKey">
</div>