<%
	ui.decorateWith("kenyaui", "panel", [ heading: "HIV Care" ])

	def dataPoints = []

	if (config.complete) {
		def initialArtStartDate = calculations.initialArtStartDate ? calculations.initialArtStartDate.value : null
		if (initialArtStartDate) {
			def regimen = calculations.initialArtRegimen ? kenyaEmrUi.formatRegimenLong(calculations.initialArtRegimen.value, ui) : null

			
			dataPoints << [ label: "Initial ART regimen", value: regimen ]
		} else {
			
		}
	}

	if (initialArtStartDate) {
		dataPoints << [ label: "ART start date", value: initialArtStartDate, showDateInterval: true ]
	} else {
		dataPoints << [ label: "ART start date", value: "" ]
	}
	
	if (calculations.lastWHOStage) {
		dataPoints << [ label: "Last WHO stage", value: ui.format(calculations.lastWHOStage.value.valueCoded), extra: calculations.lastWHOStage.value.obsDatetime ]
	} else {
		dataPoints << [ label: "Last WHO stage", value: "None" ]
	}
	
	if(patient.age.value > 13){
		if (cd4Count) {
			dataPoints << [ label: "Last CD4 count", value: ui.format(cd4Count) + " cells/&micro;L"]
		} else {
			dataPoints << [ label: "Last CD4 count", value: "None" ]
		}
	}
	else {
		if (cd4PerCount) {
			dataPoints << [ label: "Last CD4 percentage", value: ui.format(cd4PerCount) + " %" ]
		}
		else {
			dataPoints << [ label: "Last CD4 percentage", value: "None" ]
		}
	}

	if (viralLoadResult) {
		dataPoints << [ label: "Last Viral Load", value: viralLoadResult]
	}
	else{
		dataPoints << [ label: "Last Viral Load", value: "None" ]
	}

	
	
	if (listAllDiag) {
		dataPoints << [ label: "Last Diagnosis", value: listAllDiag ]
	}
	else{
		dataPoints << [ label: "Last Diagnosis", value: "None" ]
	}

	if (cpt) {
		dataPoints << [ label: "Currently on CPT", value: cpt ]
	}
	else{
		dataPoints << [ label: "Currently on CPT", value: "None" ]
	}
	
%>

<% if (config.complete) { %>
<div class="ke-stack-item">
	<table width="100%" border="0">
		<tr>
			<td width="50%" valign="top">
				${ ui.includeFragment("kenyaui", "widget/obsHistoryTable", [ id: "tblhistory", patient: currentPatient, concepts: graphingConcepts ]) }
			</td>
			<td width="50%" valign="top">
				${ ui.includeFragment("kenyaui", "widget/obsHistoryGraph", [ id: "cd4graph", patient: currentPatient, concepts: graphingConcepts, showUnits: true, style: "height: 300px" ]) }
			</td>
		</tr>
	</table>
</div>
<% } %>
<div class="ke-stack-item">
	<% dataPoints.each { print ui.includeFragment("kenyaui", "widget/dataPoint", it) } %>
</div>
<div class="ke-stack-item">
	<% if (activeVisit && currentEnrollment) { %>
	<button type="button" class="ke-compact" onclick="ui.navigate('${ ui.pageLink("kenyaemr", "regimenEditor", [ patientId: currentPatient.id, category: "ARV", appId: currentApp.id, returnUrl: ui.thisUrl() ]) }')">
		<img src="${ ui.resourceLink("kenyaui", "images/glyphs/edit.png") }" />
	</button>
	<% } %>

	<%
		if (regimenHistory.lastChange) {
			def lastChange = regimenHistory.lastChangeBeforeNow
			def regimen = lastChange.started ? kenyaEmrUi.formatRegimenLong(lastChange.started, ui) : ui.message("general.none")
			def dateLabel = lastChange.started ? "Started" : "Stopped"
	%>
	${ ui.includeFragment("kenyaui", "widget/dataPoint", [ label: "Regimen", value: regimen ]) }
	${ ui.includeFragment("kenyaui", "widget/dataPoint", [ label: dateLabel, value: lastChange.date, showDateInterval: true ]) }
	${ ui.includeFragment("kenyaemr", "field/HivRegimenChange", [patient: currentPatient ]) }
	<% } else { %>
	${ ui.includeFragment("kenyaui", "widget/dataPoint", [ label: "Regimen", value: ui.message("kenyaemr.neverOnARVs") ]) }
	<% } %>
</div>