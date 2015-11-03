

kenyaemrApp.controller('DrugCtrl', ['$scope', function($scope) {
	//var category = "ARV";
	$scope.init = function(){
		jq.getJSON('/' + OPENMRS_CONTEXT_PATH + '/kenyaemr/emrUtils/drugRegimen.action')
	    .done(function(data) {
	    	$scope.$apply(function(){ 
	    		$scope.drugs = data.drugName;
			});
	    	
	    });
	}
}]);