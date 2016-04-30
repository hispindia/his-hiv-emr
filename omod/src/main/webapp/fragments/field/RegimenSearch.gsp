<%
	ui.includeJavascript("kenyaemr", "controllers/drugRegimenController.js")
	
	def strengths = ["300/300/600 mg","300/200/600 mg","300/150/300 mg","300/150/200 mg","30/150/200 mg","600/300 mg","300/300 mg","300/200 mg","300/150 mg","300/100 mg",
	                 "200/50 mg","100/25 mg","80/20 mg","60/30 mg","30/150 mg","6/30 mg","800 mg","600 mg","400 mg","300 mg","200 mg","150 mg","100 mg","60 mg","50 mg",
	                 "30 mg","20 mg","10 mg"]
	                 
	def types = ["tab","ml"]
	
	def frequencys = ["od","bd","tds","qid","cm","hs","prn","stat"]
	
	def strengthOptions = strengths.collect( { """<option value="${ it }">${ it }</option>""" } ).join()
	
	def typeOptions = types.collect( { """<option value="${ it }">${ it }</option>""" } ).join()
	
	def frequencyOptions = frequencys.collect( { """<option value="${ it }">${ it }</option>""" } ).join()

%>

<div ng-controller="DrugCtrl" data-ng-init="init()">

<table>
<tbody>
<tr>
<td class="colA" style="text-align:center">S.No</td>
<td class="colB" style="text-align:center">Drug</td>
<td class="colC" style="text-align:center">Strength(per tab or ml)</td>
<td class="colD" style="text-align:center">Tablets</td>
<td class="colE" style="text-align:center">Tab/Ml</td>
<td class="colF" style="text-align:center">Frequency</td>
<td class="colG" style="text-align:center">Duration(in days)</td>
<td class="colH"></td>
<td class="colI"></td>
<td class="colJ"></td>
</tr>
</tbody>
</table>
<fieldset  data-ng-repeat="choice in choices">
<table>
<tbody>
<tr>
<td class="colA" style="text-align:center">{{choice.srNo}}</td>
<td class="colB" style="text-align:center"><input type="text" ng-model="drugKey" id={{choice.drugKey}} name={{choice.drugKey}} placeholder="search box" uib-typeahead="drug as drug.drugName for drug in myDrug | filter : drugKey" typeahead-on-select="drugSearch(drugKey,choice);"></td>
<td class="colC" style="text-align:center"><select style='width: 155px;height: 30px;' id={{choice.strength}}  name={{choice.strength}}><option value="" />${ strengthOptions }</select></td>
<td class="colD" style="text-align:center"><input type="text" ng-model="noOfTablet" id={{choice.noOfTablet}} name={{choice.noOfTablet}}></td>
<td class="colE" style="text-align:center"><select style='width: 155px;height: 30px;' type="text" ng-model="type" id={{choice.type}} name={{choice.type}}>${typeOptions}</select></td>
<td class="colF" style="text-align:center"><select style='width: 155px;height: 30px;' type="text" ng-model="frequncy" id={{choice.frequncy}} name={{choice.frequncy}} >${ frequencyOptions }</select></td>
<td class="colG" style="text-align:center"><input type="text" ng-model="duration" id={{choice.duration}} name={{choice.duration}}></td>
<td class="colH" style="text-align:center"><input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" size="5" id="info" name="info" value="Info" onClick="" /></td>
<td class="colI" style="text-align:center"><input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" id="add" name="add" value="Add" ng-click="addNewChoice()"/></td>
<td class="colJ" style="text-align:center"><input type="hidden" id={{choice.srNumber}} name="srNo" value={{choice.srNo}}></td>
</tr>
</tbody>
</table>
</fieldset>

</div>

<script type="text/javascript">
var patientId=${patient.patientId};
</script>

<style type="text/css">
  table { width: 100%; }
  td.colA { width: 5%; }
  td.colB { width: 10%; }
  td.colC { width: 10%; }
  td.colD { width: 10%; }
  td.colE { width: 10%; }
  td.colF { width: 10%; }
  td.colG { width: 10%; }
  td.colH { width: 5%; }
  td.colI { width: 5%; }
  td.colJ { width: 5%; }
</style>
