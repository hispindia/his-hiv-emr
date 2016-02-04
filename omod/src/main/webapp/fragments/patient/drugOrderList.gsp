<% drugOrderProcesseds.each { drugOrderProcessed -> %>
<% if (drugOrderProcessed!=null) { %>
<div>
<input type="text" id="slNo" name="slNo" size="5" value="${count++}" disabled>
<input type="text" id="${drugOrderProcessed.id}drugName" name="${drugOrderProcessed.id}drugName" size="20" value="${drugOrderProcessed.drugOrder.concept.name}" disabled>
<input type="text" id="${drugOrderProcessed.id}units" name="${drugOrderProcessed.id}units" size="20" value="${drugOrderProcessed.drugOrder.units}" disabled>
<input type="text" id="${drugOrderProcessed.id}dose" name="${drugOrderProcessed.id}dose" size="20" value="${drugOrderProcessed.drugOrder.dose}" disabled>
<input type="text" id="${drugOrderProcessed.id}frequency" name="${drugOrderProcessed.id}frequency" size="20" value="${drugOrderProcessed.drugOrder.frequency}" disabled>
<input type="text" id="${drugOrderProcessed.id}durationPreProcess" name="${drugOrderProcessed.id}durationPreProcess" size="20" value="${drugOrderProcessed.durationPreProcess}" disabled>
<input type="text" id="${drugOrderProcessed.id}issueQuantity" name="${drugOrderProcessed.id}issueQuantity" size="20">
<input type="hidden" id="drugOrderProcessedIds" name="drugOrderProcessedIds" value="${drugOrderProcessed.id}">
</div>
<% } %>
<% } %>

<% drugOrderObss.each { drugOrderObs -> %>
<% if (drugOrderObs!=null) { %>
<div>
<input type="text" id="slNo" name="slNo" size="5" value="${count++}" disabled>
<input type="text" id="drugName" name="drugName" size="20" value="${drugOrderObs.drug}" disabled>
<input type="text" id="formulation" name="formulation" size="20" value="${drugOrderObs.formulation}" disabled>
<input type="text" id="strength" name="strength" size="20" value="${drugOrderObs.strength}" disabled>
<input type="text" id="frequency" name="frequency" size="20" value="${drugOrderObs.frequency}" disabled>
<input type="text" id="durationPreProcess" name="durationPreProcess" size="20" value="${drugOrderObs.duration}" disabled>
<input type="text" id="issueQuantity" name="issueQuantity" size="20">
</div>
<% } %>
<% } %>