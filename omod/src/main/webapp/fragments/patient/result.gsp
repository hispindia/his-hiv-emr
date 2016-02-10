<table style="width: 100%">
		<tr>
		<td>{{ patient.count }}</td>
		<td>Dispensed Date </td>
		<td>{{ patient.patientIdentifier }}</td>
		<td>{{ patient.patientName }}</td>
		<td>{{ patient.age }}</td>
		<td><img width="32" height="32" ng-src="${ ui.resourceLink("kenyaui", "images/buttons/patient_") }{{ patient.gender }}.png" /></td>
		<td><a href="#">View Details</a></td>
		<td>Dispensed By</td>
		</tr>
</table>