<%
	def units = [ " " , "mg", "g", "ml", "tab" ]

	def frequencies = [
			OD: "Once daily",
			NOCTE: "Once daily, at bedtime",
			qPM: "Once daily, in the evening",
			qAM: "Once daily, in the morning",
			BD: "Twice daily",
			TDS: "Thrice daily"
	]

	def refDefIndex = 0;

	def groupOptions = {
		it.regimens.collect( { reg -> """<option value="${ refDefIndex++ }">${ reg.name }</option>""" } ).join()
	}

	def drugOptions = drugs.collect( { """<option value="${ it }">${ kenyaEmrUi.formatDrug(it, ui) }</option>""" } ).join()
	def unitsOptions = units.collect( { """<option value="${ it }">${ it }</option>""" } ).join()
	def frequencyOptions = frequencies.collect( { """<option value="${ it.key }">${ it.value }</option>""" } ).join()
%>
<script type="text/javascript">
	// Create variable to hold regimen presets
	if (typeof regimen_presets === 'undefined') {
		regimen_presets = new Array();
	}
	regimen_presets['${ config.id }'] = ${ ui.toJson(regimenDefinitions) };

	jq(function() {
		jq('#${ config.id }-container .standard-regimen-select').change(function () {
			// Get selected regimen definition
			var stdRegIndex = parseInt(jq(this).val());
			var stdReg = regimen_presets['${ config.id }'][stdRegIndex];
			var components = stdReg.components;

			// Get container div and component fields
			var container = jq(this).parent();
			var drugFields = container.find('.regimen-component-drug');
			var doseFields = container.find('.regimen-component-dose');
			var unitsFields = container.find('.regimen-component-units');
			var frequencyFields = container.find('.regimen-component-frequency');

			// Clear all inputs
			container.find('input, select').val('');

			// Set component controls for each component of selected regimen
			for (var c = 0; c < components.length; c++) {
				var component = components[c];
				jq(drugFields[c]).val(component.drugRef);
				jq(doseFields[c]).val(component.dose);
				jq(unitsFields[c]).val(component.units);
				jq(frequencyFields[c]).val(component.frequency);
			}

			kenyaemr.updateRegimenFromDisplay('${ config.id }');
		});

		jq('#${ config.id }-container .regimen-component-drug, #${ config.id }-container .regimen-component-dose, #${ config.id }-container .regimen-component-units, #${ config.id }-container .regimen-component-frequency').blur(function() {
			kenyaemr.updateRegimenFromDisplay('${ config.id }');
		});
	});
</script>

<div id="${ config.id }-container">
	<input type="hidden" id="${ config.id }" name="${ config.formFieldName }" />
	<i>Use standard:</i> <select class="standard-regimen-select">
		<option label="Select..." value="" />
		<% regimenGroups.each { group -> %>
			<optgroup label="${ group.name }">${ groupOptions(group) }</optgroup>
		<% } %>
	</select><br />
	<br />
	<span id="${ config.id }-error" class="error" style="display: none"></span>
	<% for (def c = 0; c < maxComponents; ++c) { %>
	<div class="regimen-component">
		Drug: <select class="regimen-component-drug"><option value="" />${ drugOptions }</select>
		Dosage: <input class="regimen-component-dose" type="text" size="5" /><select class="regimen-component-units">${ unitsOptions }</select>
		Frequency: <select class="regimen-component-frequency">${ frequencyOptions }</select>
	</div>
	<% } %>
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


<table>
<div>
<b>
ARV Treatment
</b>
</div>
<div>
	<input type="text" id="slNoah" name="slNoah" size="5" value="S.No" disabled>
	<input type="text" id="drugNameah" name="drugNameah" value="Drug Name" disabled>
    <input type="text" id="formulationah" name="formulationah" value="Formulation" disabled>
    <input type="text" id="strengthah" name="strengthah" value="Strength" disabled>
    <input type="text" id="frequencyah" name="frequencyah" value="Frequency" disabled>
    <input type="text" id="durationah" name="durationah" value="Duration (in days)" disabled>
</div>
</br>

<div>
	<input type="text" id="slNoa0" name="slNoa0" size="5" readonly='readonly'>
	<input type="text" id="drugNamea0" name="drugNamea0">
    <select id="formulationa0" name="formulationa0" style='width: 158px;height: 30px;'>
    ${ unitsOptions }
    </select>
    <select id="strengta0" name="strengta0" style='width: 158px;height: 30px;'>
    ${ unitsOptions }
    </select>
    <select type="text" id="frequencya0" name="frequencya0" style='width: 158px;height: 30px;'>
    ${ frequencyOptions }
	</select>
    <input type="text" id="durationa0" name="durationa0">
</div>

<div id="headerValuea">							
	<div>
	<input type="text" id="slNoa1" name="slNoa1" size="5" readonly='readonly'>
	<input type="text" id="drugNamea1" name="drugNamea1">
    <select id="formulationa1" name="formulationa1" style='width: 158px;height: 30px;'>
    ${ unitsOptions }
    </select>
    <select id="strengtha1" name="strengtha1" style='width: 158px;height: 30px;'>
    ${ unitsOptions }
    </select>
    <select type="text" id="frequencya1" name="frequencya1" style='width: 158px;height: 30px;'>
    ${ frequencyOptions }
	</select>
    <input type="text" id="durationa1" name="durationa1">
	<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="Add" onClick="addDrugOrderForARVTreatment();" />
	</div>							
</div>

</table>


<table>
<div>
<b>
OI Treatment
</b>
</div>
<div>
	<input type="text" id="slNooh" name="slNooh" size="5" value="S.No" disabled>
	<input type="text" id="drugNameoh" name="drugNameoh" value="Drug Name" disabled>
    <input type="text" id="formulationoh" name="formulationoh" value="Formulation" disabled>
    <input type="text" id="strengthoh" name="strengthoh" value="Strength" disabled>
    <input type="text" id="frequecyoh" name="frequecyoh" value="Frequency" disabled>
    <input type="text" id="durationoh" name="durationoh" value="Duration (in days)" disabled>
</div>
</br>

<div>
	<input type="text" id="slNoo0" name="slNoo0" size="5" readonly='readonly'>
	<input type="text" id="drugNameo0" name="drugNameo0">
    <select id="formulationo0" name="formulationo0" style='width: 158px;height: 30px;'>
    ${ unitsOptions }
    </select>
    <select id="strengto0" name="strengto0" style='width: 158px;height: 30px;'>
    ${ unitsOptions }
    </select>
    <select type="text" id="frequecyo0" name="frequecyo0" style='width: 158px;height: 30px;'>
    ${ frequencyOptions }
	</select>
    <input type="text" id="durationo0" name="durationo0">
</div>

<div id="headerValueo">							
	<div>
	<input type="text" id="slNoo1" name="slNoo1" size="5" readonly='readonly'>
	<input type="text" id="drugNameo1" name="drugNameo1">
    <select id="formulationo1" name="formulationo1" style='width: 158px;height: 30px;'>
    ${ unitsOptions }
    </select>
    <select id="strengtho1" name="strengtho1" style='width: 158px;height: 30px;'>
    ${ unitsOptions }
    </select>
    <select type="text" id="frequecyo1" name="frequecyo1" style='width: 158px;height: 30px;'>
    ${ frequencyOptions }
	</select>
    <input type="text" id="durationo1" name="durationo1">
	<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="Add" onClick="addDrugOrderForOITreatment();" />
	</div>							
</div>

</table>

<table>
<div>
<b>
Prophylaxis
</b>
</div>
<div>
	<input type="text" id="slNoph" name="slNoh" size="5" value="S.No" disabled>
	<input type="text" id="drugNameph" name="drugNameph" value="Drug Name" disabled>
    <input type="text" id="formulationph" name="formulationph" value="Formulation" disabled>
    <input type="text" id="strengthph" name="strengthph" value="Strength" disabled>
    <input type="text" id="frequecyph" name="frequencyph" value="Frequency" disabled>
    <input type="text" id="durationph" name="durationph" value="Duration (in days)" disabled>
</div>
</br>

<div>
	<input type="text" id="slNop0" name="slNo0" size="5" readonly='readonly'>
	<input type="text" id="drugNamep0" name="drugNamep0">
    <select id="formulationp0" name="formulationp0" style='width: 158px;height: 30px;'>
    ${ unitsOptions }
    </select>
    <select id="strengtp0" name="strengtp0" style='width: 158px;height: 30px;'>
    ${ unitsOptions }
    </select>
    <select type="text" id="frequecyp0" name="frequecyp0" style='width: 158px;height: 30px;'>
    ${ frequencyOptions }
	</select>
    <input type="text" id="durationp0" name="durationp0">
</div>

<div id="headerValuep">							
	<div>
	<input type="text" id="slNop1" name="slNo1" size="5" readonly='readonly'>
	<input type="text" id="drugNamep1" name="drugNamep1">
    <select id="formulationp1" name="formulationp1" style='width: 158px;height: 30px;'>
    ${ unitsOptions }
    </select>
    <select id="strengthp1" name="strengthp1" style='width: 158px;height: 30px;'>
    ${ unitsOptions }
    </select>
    <select type="text" id="frequecyp1" name="frequecyp1" style='width: 158px;height: 30px;'>
    ${ frequencyOptions }
	</select>
    <input type="text" id="durationp1" name="durationp1">
	<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="Add" onClick="addDrugOrderForProphylaxis();" />
	</div>							
</div>

</table>


<table>
<div>
<b>
Other Treatment
</b>
</div>
<div>
	<input type="text" id="slNoh" name="slNoh" size="5" value="S.No" disabled>
	<input type="text" id="drugNameh" name="drugNameh" value="Drug Name" disabled>
    <input type="text" id="formulationh" name="formulationh" value="Formulation" disabled>
    <input type="text" id="strengthh" name="strengthh" value="Strength" disabled>
    <input type="text" id="frequecyh" name="frequecyh" value="Frequency" disabled>
    <input type="text" id="durationh" name="durationh" value="Duration (in days)" disabled>
</div>
</br>

<div>
	<input type="text" id="slNo0" name="slNo0" size="5" readonly='readonly'>
	<input type="text" id="drugName0" name="drugName0">
    <select id="formulation0" name="formulation0" style='width: 158px;height: 30px;'>
    ${ unitsOptions }
    </select>
    <select id="strength0" name="strength0" style='width: 158px;height: 30px;'>
    ${ unitsOptions }
    </select>
    <select type="text" id="frequecy0" name="frequecy0" style='width: 158px;height: 30px;'>
    ${ frequencyOptions }
	</select>
    <input type="text" id="duration0" name="duration0">
</div>

<div id="headerValue">							
	<div>
	<input type="text" id="slNo1" name="slNo1" size="5" readonly='readonly'>
	<input type="text" id="drugName1" name="drugName1">
    <select id="formulation1" name="formulation1" style='width: 158px;height: 30px;'>
    ${ unitsOptions }
    </select>
    <select id="strength1" name="strength1" style='width: 158px;height: 30px;'>
    ${ unitsOptions }
    </select>
    <select type="text" id="frequecy1" name="frequecy1" style='width: 158px;height: 30px;'>
    ${ frequencyOptions }
	</select>
    <input type="text" id="duration1" name="duration1">
	<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="Add" onClick="addDrugOrder();" />
	</div>							
</div>

</table>

<script type="text/javascript">
var oitreatment=2;
var prophylaxis=2;
var otherTreatment=2;
var drugIssuedList1 = new Array();
var drugIssuedList2 = new Array();

drugIssuedList1.push("0");
drugIssuedList1.push("1");
drugIssuedList1.push(prophylaxis);

drugIssuedList2.push("0");
drugIssuedList2.push("1");
drugIssuedList2.push(otherTreatment);

function addDrugOrderForOITreatment() {
   var deleteString = 'deleteInputOITreatment(\"'+oitreatment+'\")';
   var htmlText =  "<div id='com_"+oitreatment+"_div'>"
	       	 +"<input id='slNo"+prophylaxis+"'  name='drugOrder' type='text' size='5' readonly='readonly'/>&nbsp;"
	       	 +"<input id='drugName"+prophylaxis+"'  name='drugOrder' type='text' size='20'/>&nbsp;"
	       	 +"<select id='formulation"+prophylaxis+"'  name='drugOrder' style='width: 158px;height: 30px;'>"
	         +'${ unitsOptions }'
	       	 +"</select>&nbsp;"
	       	 +"<select id='strength"+prophylaxis+"'  name='drugOrder' style='width: 158px;height: 30px;'>"
	         +'${ unitsOptions }'
	       	 +"</select>&nbsp;"
	       	 +"<select id='frequecy"+prophylaxis+"'  name='drugOrder' style='width: 158px;height: 30px;'>"
	         +'${ frequencyOptions }'
	       	 +"</select>&nbsp;"
	       	 +"<input id='duration"+prophylaxis+"'  name='drugOrder' type='text' size='20'/>&nbsp;"
	       	 +"<a style='color:red' href='#' onclick='"+deleteString+"' >[X]</a>"	
	       	 +"</div>";
	       	
   var newElement = document.createElement('div');
   newElement.setAttribute("id", oitreatment);   
   newElement.innerHTML = htmlText;
   var fieldsArea = document.getElementById('headerValueo');
   fieldsArea.appendChild(newElement);
   oitreatment++;
}

function deleteInputOITreatment(oitreatment) {
   var parentDiv = 'headerValueo';
   var child = document.getElementById(oitreatment);
   var parent = document.getElementById(parentDiv);
   parent.removeChild(child); 
   Array.prototype.remove = function(v) { this.splice(this.indexOf(v) == -1 ? this.length : this.indexOf(v), 1); }
}


//
function addDrugOrderForARVTreatment() {
   var deleteString = 'deleteInputARVTreatment(\"'+oitreatment+'\")';
   var htmlText =  "<div id='com_"+oitreatment+"_div'>"
	       	 +"<input id='slNo"+prophylaxis+"'  name='drugOrder' type='text' size='5' readonly='readonly'/>&nbsp;"
	       	 +"<input id='drugName"+prophylaxis+"'  name='drugOrder' type='text' size='20'/>&nbsp;"
	       	 +"<select id='formulation"+prophylaxis+"'  name='drugOrder' style='width: 158px;height: 30px;'>"
	         +'${ unitsOptions }'
	       	 +"</select>&nbsp;"
	       	 +"<select id='strength"+prophylaxis+"'  name='drugOrder' style='width: 158px;height: 30px;'>"
	         +'${ unitsOptions }'
	       	 +"</select>&nbsp;"
	       	 +"<select id='frequecy"+prophylaxis+"'  name='drugOrder' style='width: 158px;height: 30px;'>"
	         +'${ frequencyOptions }'
	       	 +"</select>&nbsp;"
	       	 +"<input id='duration"+prophylaxis+"'  name='drugOrder' type='text' size='20'/>&nbsp;"
	       	 +"<a style='color:red' href='#' onclick='"+deleteString+"' >[X]</a>"	
	       	 +"</div>";
	       	
   var newElement = document.createElement('div');
   newElement.setAttribute("id", oitreatment);   
   newElement.innerHTML = htmlText;
   var fieldsArea = document.getElementById('headerValueo');
   fieldsArea.appendChild(newElement);
   oitreatment++;
}

function deleteInputARVTreatment(oitreatment) {
   var parentDiv = 'headerValueo';
   var child = document.getElementById(oitreatment);
   var parent = document.getElementById(parentDiv);
   parent.removeChild(child); 
   Array.prototype.remove = function(v) { this.splice(this.indexOf(v) == -1 ? this.length : this.indexOf(v), 1); }
}
//

function addDrugOrderForProphylaxis() {
alert("hELLO"+prophylaxis);
   var deleteString = 'deleteInputProphylaxis(\"'+prophylaxis+'\")';
   var htmlText =  "<div id='com_"+prophylaxis+"_div'>"
	       	 +"<input id='slNo"+prophylaxis+"'  name='drugOrder' type='text' size='5' readonly='readonly'/>&nbsp;"
	       	 +"<input id='drugName"+prophylaxis+"'  name='drugOrder' type='text' size='20'/>&nbsp;"
	       	 +"<select id='formulation"+prophylaxis+"'  name='drugOrder' style='width: 158px;height: 30px;'>"
	         +'${ unitsOptions }'
	       	 +"</select>&nbsp;"
	       	 +"<select id='strength"+prophylaxis+"'  name='drugOrder' style='width: 158px;height: 30px;'>"
	         +'${ unitsOptions }'
	       	 +"</select>&nbsp;"
	       	 +"<select id='frequecy"+prophylaxis+"'  name='drugOrder' style='width: 158px;height: 30px;'>"
	         +'${ frequencyOptions }'
	       	 +"</select>&nbsp;"
	       	 +"<input id='duration"+prophylaxis+"'  name='drugOrder' type='text' size='20'/>&nbsp;"
	       	 +"<a style='color:red' href='#' onclick='"+deleteString+"' >[X]</a>"	
	       	 +"</div>";
	       	
   var newElement = document.createElement('div');
   newElement.setAttribute("id", prophylaxis);   
   newElement.innerHTML = htmlText;
   var fieldsArea = document.getElementById('headerValuep');
   fieldsArea.appendChild(newElement);
   prophylaxis++;
}

function deleteInputProphylaxis(prophylaxis) {
alert("i m here");
   var parentDiv = 'headerValuep';
   var child = document.getElementById(prophylaxis);
   var parent = document.getElementById(parentDiv);
   parent.removeChild(child); 
   Array.prototype.remove = function(v) { this.splice(this.indexOf(v) == -1 ? this.length : this.indexOf(v), 1); }
}

function addDrugOrder() {
alert("hiiii"+otherTreatment);
   var deleteString = 'deleteInput(\"'+otherTreatment+'\")';
   var htmlText =  "<div id='com_"+otherTreatment+"_div'>"
	       	 +"<input id='slNo"+otherTreatment+"'  name='drugOrder' type='text' size='5' readonly='readonly'/>&nbsp;"
	       	 +"<input id='drugName"+otherTreatment+"'  name='drugOrder' type='text' size='20'/>&nbsp;"
	       	 +"<select id='formulation"+otherTreatment+"'  name='drugOrder' style='width: 158px;height: 30px;'>"
	         +'${ unitsOptions }'
	       	 +"</select>&nbsp;"
	       	 +"<select id='strength"+otherTreatment+"'  name='drugOrder' style='width: 158px;height: 30px;'>"
	         +'${ unitsOptions }'
	       	 +"</select>&nbsp;"
	       	 +"<select id='frequecy"+otherTreatment+"'  name='drugOrder' style='width: 158px;height: 30px;'>"
	         +'${ frequencyOptions }'
	       	 +"</select>&nbsp;"
	       	 
	       	 +"<input id='duration"+otherTreatment+"'  name='drugOrder' type='text' size='20'/>&nbsp;"
	       	 +"<a style='color:red' href='#' onclick='"+deleteString+"' >[X]</a>"	
	       	 +"</div>";
	       	
   var newElement = document.createElement('div');
   newElement.setAttribute("id", otherTreatment);   
   newElement.innerHTML = htmlText;
   var fieldsArea = document.getElementById('headerValue');
   fieldsArea.appendChild(newElement);
   otherTreatment++;
}

function deleteInput(otherTreatment) {
   var parentDiv = 'headerValue';
   var child = document.getElementById(otherTreatment);
   var parent = document.getElementById(parentDiv);
   parent.removeChild(child); 
   Array.prototype.remove = function(v) { this.splice(this.indexOf(v) == -1 ? this.length : this.indexOf(v), 1); }
}

</script>