<%
def count=0;
%>
<script type="text/javascript">
	
</script>


<div id="${ config.id }-container">
	<input type="hidden" id="${ config.id }" name="${ config.formFieldName }" />
	<div>
	<input size="5" id="slNoah" name="slNoah" value="S.No" disabled>
	<input type="text" id="drugNameah" name="drugNameah" size="20" value="Drug Name" disabled>
    <input type="text" id="formulationah" name="formulationah" size="20" value="Formulation" disabled>
    <input type="text" id="strengthah" name="strengthah" size="20" value="Strength" disabled>
    <input type="text" id="frequencyah" name="frequencyah" size="20" value="Frequency" disabled>
    <input type="text" id="durationah" name="durationah" size="20" value="Duration (in days)" disabled>
</div>

	<% continueRegimen.each { continueRegim -> %>
	<div>
		<input type="text" id="slNoa${++count}" name="slNoa${count}" size="5" value="${count}" disabled>
	    <input type="text" id="drugName${count}" name="drugName${count}" size="20" value="${continueRegim.concept.name}" disabled>
        <input type="text" id="formulation${count}" name="formulation${count}" size="20" value="${continueRegim.dose}" disabled>
        <input type="text" id="strength${count}" name="strength${count}" size="20" value="${continueRegim.units}" disabled>
        <input type="text" id="frequency${count}" name="frequency${count}" size="20" value="${continueRegim.frequency}" disabled>
        <input type="text" id="duration${continueRegim.concept.name}" name="duration${continueRegim.concept.name}" size="20">
        <input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" size="5" id="info${count}" name="info${count}" value="Info" onClick="artDrugInfo('drugName${count}');" />
	</div>
	<% } %>
</div>

<div id="guideDiv" style="visibility:hidden;">

</div>

<div id="drugInfoDiv" style="visibility:hidden;">		
</div>

<% if (config.parentFormId) { %>
<script type="text/javascript">
	jq(function() {
		subscribe('${ config.parentFormId }.reset', function() {
			jq('#${ config.id } input, #${ config.id } select').val('');
		});

		jq('#${ config.id }').change(function() {
			publish('${ config.parentFormId }/changed');
		});
	});
</script>
<% } %>

<script type="text/javascript">
function artDrugInfo(drugParameter){
var drugName=jQuery('#'+drugParameter).val();
jQuery.ajax(ui.fragmentActionLink("kenyaemr", "field/drugInfo", "drugDetails"), { data: { drugName: drugName }, dataType: 'json'
						}).done(function(data) {druNam=data.drugName; toxicity=data.toxicity;riskFactor=data.riskFactor;suggestedManagement=data.suggestedManagement;
						drugInteraction=data.drugInteraction;suggestedManagementInteraction=data.suggestedManagementInteraction;
var htmlText =  "<table>"
                +"<tr>"
                +"<th>"
                +"Drug Name&nbsp;"
                +"</th>"
                +"<th>"
                +'Toxicity'
                +"</th>"
                 +"<th>"
                +"Risk Factor&nbsp;"
                +"</th>"
                +"<th>"
                +"Suggested Management&nbsp;"
                +"</th>"
                 +"<th>"
                +"Drug Interaction&nbsp;"
                +"</th>"
                +"<th>"
                +'Suggested Management Interaction'
                +"</th>"
                +"</tr>"
                
                +"<tr>"
                +"<td>"
                +druNam
                +"</td>"
                +"<td>"
                +toxicity
                +"</td>"
                 +"<td>"
                +riskFactor
                +"</td>"
                +"<td>"
                +suggestedManagement
                +"</td>"
                 +"<td>"
                +drugInteraction
                +"</td>"
                +"<td>"
                +suggestedManagementInteraction
                +"</td>"
                +"</tr>"
                +"</table>"
var newElement = document.createElement('div');
newElement.setAttribute("id", "drugDiv"); 
newElement.innerHTML = htmlText;
var fieldsArea = document.getElementById('drugInfoDiv');
fieldsArea.appendChild(newElement);
var url = "#TB_inline?height=300&width=750&inlineId=drugDiv";
tb_show("Drug Info",url,false);
 });
}

function guidee(){
var age=${patient.age};
if(age>12){
var htmlText =  "<img src='${ ui.resourceLink('kenyaui', 'images/glyphs/flow_chart_adult.JPG') }' />"
var newElement = document.createElement('div');
newElement.setAttribute("id", "guideDivAdult"); 
newElement.innerHTML = htmlText;
var fieldsArea = document.getElementById('guideDiv');
fieldsArea.appendChild(newElement);
var url = "#TB_inline?height=750&width=1150&inlineId=guideDivAdult";
tb_show("Guide",url,false);
}
else{
var htmlText =  "<img src='${ ui.resourceLink('kenyaui', 'images/glyphs/flow_chart_child.JPG') }' />"
var newElement = document.createElement('div');
newElement.setAttribute("id", "guideDivChild"); 
newElement.innerHTML = htmlText;
var fieldsArea = document.getElementById('guideDiv');
fieldsArea.appendChild(newElement);
var url = "#TB_inline?height=750&width=1350&inlineId=guideDivChild";
tb_show("Guide",url,false);
}
}
</script>