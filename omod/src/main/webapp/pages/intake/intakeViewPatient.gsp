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
	<% if (visit) { %>
		<% if (!visit.voided) { %>
			${ ui.includeFragment("kenyaemr", "intake/completedLabResult", [ visit: visit ]) }
			${ ui.includeFragment("kenyaemr", "intake/visitAvailableForms", [ visit: visit ]) }
		<% } %>

	<% } else if (form) { %>

		<div class="ke-panel-frame">
			<div class="ke-panel-heading">${ ui.format(form) }</div>
			<div class="ke-panel-content">

				<% if (encounter) { %>
					${ ui.includeFragment("kenyaemr", "form/viewHtmlForm", [ encounter: encounter ]) }
				<% } else { %>
					<em>Not filled out</em>
				<% } %>

			</div>
		</div>

	<% } %>
	
</div>