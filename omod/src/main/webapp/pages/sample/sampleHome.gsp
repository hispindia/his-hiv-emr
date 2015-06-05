<%
	ui.decorateWith("kenyaemr", "standardPage", [ layout: "sidebar" ])

	ui.includeJavascript("kenyaemr", "controllers/visit.js")

	def menuItems = [
			[ label: "Find or create patient", iconProvider: "kenyaui", icon: "buttons/patient_search.png", href: ui.pageLink("kenyaemr", "registration/registrationSearch") ]
	]
%>

<div class="ke-page-sidebar">
Hello, this is sample page :)
</div>