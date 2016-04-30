<%
	ui.includeJavascript("kenyaemr", "controllers/drugRegimenController.js")

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
<td class="colC" style="text-align:center"><input type="text" ng-model="strength" id={{choice.strength}} name={{choice.strength}}></td>
<td class="colD" style="text-align:center"><input type="text" ng-model="noOfTablet" id={{choice.noOfTablet}} name={{choice.noOfTablet}}></td>
<td class="colE" style="text-align:center"><input type="text" ng-model="type" id={{choice.type}} name={{choice.type}}></td>
<td class="colF" style="text-align:center"><input type="text" ng-model="frequncy" id={{choice.frequncy}} name={{choice.frequncy}} ></td>
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

