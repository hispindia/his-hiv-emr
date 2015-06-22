
<%
	ui.decorateWith("kenyaemr", "standardPage", [ layout: "sidebar" ])


def sampleFieldRows = [
	[
		[ object: command, property: "firstName", label: "First Name"],
		[ object: command, property: "middleName", label: "Middle Name"],
		[ object: command, property: "lastName", label: "Last Name"]
	]
]
%>
<form class="ke-form" id="edit-patient-form" method="post" action="${ ui.actionLink("kenyaemr", "sample/editPerson", "savePerson") }">

				<div class="ke-panel-content">
					<div class="ke-form-globalerrors" style="display: none"></div>
			
					<div class="ke-form-instructions">
						<strong>*</strong> indicates a required field
					</div>
				</div>
				<fieldset>
					<legend>Field Set</legend>
					
					<% sampleFieldRows.each { %>
				${ ui.includeFragment("kenyaui", "widget/rowOfFields", [ fields: it ]) }
			<% } %>
					
					
					
					<table>
						<tr>
							<td valign="top">
								<label class="ke-field-label">Age *</label>
								<span class="ke-field-content">
									<input type="text" name="age"/>
								</span>
							</td>
						</tr>
					</table>
			
				</fieldset>
				<div class="ke-panel-footer">
					<button type="submit">
						<img src="${ ui.resourceLink("kenyaui", "images/glyphs/ok.png") }" /> ${ command.original ? "Save Changes" : "Create Person" }
					</button>
						<button type="button" class="cancel-button"><img src="${ ui.resourceLink("kenyaui", "images/glyphs/cancel.png") }" /> Cancel</button>
				</div>
			</form>