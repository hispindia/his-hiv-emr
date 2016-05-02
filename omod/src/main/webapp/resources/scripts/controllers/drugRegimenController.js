/*
kenyaemrApp.controller('DrugCtrl', ['$scope', function($scope) {

$scope.drugSearch = function(drugKey){
	if(drugKey.length>2){
		jq.getJSON('/' + OPENMRS_CONTEXT_PATH + '/kenyaemr/emrUtils/drugConcept.action',{ patientId: patientId,drugKey: drugKey})
	    .done(function(data) {
	    	$scope.$apply(function(){ 
	    		$scope.myDrug = data.drugConceptName;
		    	
			});
	    	
	    });
	 }
  }

}]);
*/

kenyaemrApp.controller('DrugCtrl', ['$scope', function($scope) {
	
	$scope.choices = [{srNo:'1',srNumber:'srNumber1',id:'choice1',drugKey:'drugKey1',strength:'strength1',noOfTablet:'noOfTablet1',type: 'type1',frequncy: 'frequncy1',duration:'duration1'}];
	$scope.addNewChoice = function() {
		var newItemNo = $scope.choices.length+1;
		$scope.choices.push({srNo:newItemNo,srNumber:'srNumber'+newItemNo,id:'choice'+newItemNo,drugKey:'drugKey'+newItemNo,strength:'strength'+newItemNo,noOfTablet:'noOfTablet'+newItemNo,type: 'type'+newItemNo,frequncy:'frequncy'+newItemNo,duration:'duration'+newItemNo});
	}
	
	$scope.drugSearch = function(drugKey,choice){
	//var drugKey="drugKey"+count.toString();
	//$scope.strength = $scope[drugKey].strength;
	//$scope.strength = $scope.drugKey.strength;
	var srNo=choice.srNo;
	$('#strength'+srNo).val(drugKey.strength);
	$('#noOfTablet'+srNo).val(drugKey.noOfTablet);
	$('#type'+srNo).val(drugKey.type);
	$('#frequncy'+srNo).val(drugKey.frequency);
	$('#duration'+srNo).val(drugKey.duration);
	}
	
	$scope.artDrugInfoForRegimenSearch=function(drugKey){
		var drugName=drugKey.drugName;
		jQuery.ajax(ui.fragmentActionLink("kenyaemr", "field/drugInfo", "drugDetails"), { data: { drugNames: drugName }, dataType: 'json'
		}).done(function(data) {
        var htmlText =  "<table style='width: 100%'>"
        +"<tr>"
        +"<th>"
        +"Drug Name&nbsp;"
        +"</th>"
        +"<th>"
        +'Toxicity&nbsp;'
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

        $.each(data, function(i, item){
            $.each(this,function(j) {
        
            	htmlText=htmlText
            	 +"<tr>"
            	 +"<td>"
                 +this.drugName
                 +"</td>"
                 +"<td>"
                 +this.toxicity
                 +"</td>"
                 +"<td>"
                 +this.riskFactor
                 +"</td>"
                 +"<td>"
                 +this.suggestedManagement
                 +"</td>"
                 +"<td>"
                 +this.drugInteraction
                 +"</td>"
                 +"<td>"
                 +this.suggestedManagementInteraction
                 +"</td>"
                 +"</tr>"
            });
        });
		htmlText=htmlText
		 +"</table>"
       var newElement = document.createElement('div');
      newElement.setAttribute("id", "drugDiv"); 
      newElement.innerHTML = htmlText;
      var fieldsArea = document.getElementById('drugInfoDiv');
      jQuery('#guideDiv').empty();
      jQuery('#drugInfoDiv').empty();
      fieldsArea.appendChild(newElement);
      var url = "#TB_inline?height=500&width=750&inlineId=drugDiv";
      tb_show("Drug Info",url,false);
      });
	}
	
	$scope.init = function(){
		jq.getJSON('/' + OPENMRS_CONTEXT_PATH + '/kenyaemr/emrUtils/drugConcept.action',{ patientId: patientId})
	    .done(function(data) {
	    	$scope.$apply(function(){ 
	    		$scope.myDrug = data.drugConceptName;
			});
	    	
	     });
	 }

}]);
