function AddresshierarchyCtrl($scope) {
	jq.getJSON('/' + OPENMRS_CONTEXT_PATH + '/kenyaemr/emrUtils/addressHierarchy.action')
    .done(function(data) {
    	$scope.counties = data.county;
    });
	
	$scope.countySelection = function(myCounty) {
		jq.getJSON('/' + OPENMRS_CONTEXT_PATH + '/kenyaemr/emrUtils/addressHierarchy.action',{ county: myCounty})
		.done(function(data) {
			$scope.subcounties = data.subcounty;
	    });
	};
	
	$scope.subCountySelection = function(myCounty,mySubCounty) {
		jq.getJSON('/' + OPENMRS_CONTEXT_PATH + '/kenyaemr/emrUtils/addressHierarchy.action',{ county: myCounty,subcounty: mySubCounty})
		.done(function(data) {
			$scope.locations = data.location;
	    });
	};
}

