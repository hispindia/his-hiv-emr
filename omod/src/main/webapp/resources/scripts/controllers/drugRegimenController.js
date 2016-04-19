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
		
$scope.init = function(){
		jq.getJSON('/' + OPENMRS_CONTEXT_PATH + '/kenyaemr/emrUtils/drugConcept.action',{ patientId: patientId})
	    .done(function(data) {
	    	$scope.$apply(function(){ 
	    		$scope.myDrug = data.drugConceptName;
		    	
			});
	    	
	     });
	 }

}]);

