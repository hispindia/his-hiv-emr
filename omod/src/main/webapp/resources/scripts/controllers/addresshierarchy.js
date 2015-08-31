

kenyaemrApp.controller('AddresshierarchyCtrl', ['$scope', function($scope) {

	
	$scope.countySelection = function(myCounty) {
		jq.getJSON('/' + OPENMRS_CONTEXT_PATH + '/kenyaemr/emrUtils/addressHierarchy.action',{ county: myCounty})
		.done(function(data) {
			
			$scope.$apply(function(){ 
				$scope.subcounties = data.subcounty;
				console.debug("$scope.subcounties = data.subcounty;");
			});
			
	    });
	};
	
	$scope.subCountySelection = function(myCounty,mySubCounty) {
		jq.getJSON('/' + OPENMRS_CONTEXT_PATH + '/kenyaemr/emrUtils/addressHierarchy.action',{ county: myCounty,subcounty: mySubCounty})
		.done(function(data) {
			$scope.$apply(function(){ 
				$scope.locations = data.location;
				console.debug("$scope.locations = data.location;");
			});
	    });
	};
	
	$scope.init = function(){
		jq.getJSON('/' + OPENMRS_CONTEXT_PATH + '/kenyaemr/emrUtils/addressHierarchy.action')
	    .done(function(data) {
	    	$scope.$apply(function(){ 
	    		$scope.counties = data.county;
		    	console.debug("$scope.counties = data.county;");
			});
	    	
	    });
	}
}]);

