<%
	ui.decorateWith("kenyaemr", "standardPage", [ patient: currentPatient ])
%>

<div class="ke-page-sidebar">
	<div class="ke-panel-frame">
		<div class="ke-panel-heading">Visits(${ visitsCount })</div>

		<% if (!visits) {
			print ui.includeFragment("kenyaui", "widget/panelMenuItem", [
				label: ui.message("general.none"),
			])
		}
		else {
			visits.each { visit ->
				print ui.includeFragment("kenyaui", "widget/panelMenuItem", [
						label: ui.format(visit.visitType),
						href: ui.pageLink("kenyaemr", "intake/intakeViewPatient", [ patientId: currentPatient.id, visitId: visit.id ]),
						extra: kenyaui.formatVisitDates(visit),
						active: (selection == "visit-" + visit.id)
				])
			}
		} %>
	</div>

</div>

<div class="ke-page-content">
		${ ui.includeFragment("kenyaemr", "intake/enterLabResult", [visit : visit, returnUrl : ui.thisUrl() ]) }
</div>