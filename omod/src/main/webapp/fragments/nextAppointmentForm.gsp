<%
	ui.decorateWith("kenyaui", "panel", [ heading: "Appointment" ])
%>
<script type="text/javascript">
 	jq(function() {
		jq('#startDate_date').change(function() {
			var dateStart = document.getElementById('startDate_date').value;
			var patientId =${patient.id};
		
			jQuery.ajax(ui.fragmentActionLink("kenyaemr" , "nextAppointmentCount",  "getTotalPatient"),{ data: { patient:patientId ,date:dateStart}, dataType: 'json'
			}).done(function(data) {
		            alert("There are "+data.count+" patients appointed on "+dateStart);
		 });

		});
	});
 
function saveAppointment(){
		var dateStart = document.getElementById('startDate_date').value;
		var patientId =${patient.id} ;

		jQuery.ajax(ui.fragmentActionLink("kenyaemr" , "nextAppointmentCount",  "saveAppointment"),{ data: { patient:patientId ,date:dateStart}, dataType: 'json'
		}).done(function(data) {
	            alert("Saved");
	 });
};
</script>
<div>
	<span class="ke-field-content">
		Next Appointment Date : ${ ui.includeFragment("kenyaui", "field/java.util.Date" ,[id:'startDate', initialValue:appointmentDate, formFieldName:'startDate', showTime: false])}
	</span>
	<span class="ke-field-content">
		<input type="button" value="Save" onclick="saveAppointment();"/>
	</span>
</div>		
