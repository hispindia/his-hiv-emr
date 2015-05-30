package org.openmrs.module.myanmaremr.reporting.library.shared.hiv;

import org.openmrs.module.reporting.indicator.CohortIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.openmrs.module.myanmarcore.report.ReportUtils.map;
import static org.openmrs.module.myanmaremr.reporting.EmrReportingUtils.cohortIndicator;

/**
 * Library of DHIS2 related indicator definitions. All indicators require parameters ${startDate} and ${endDate}
 */
@Component
public class Dhis2IndicatorLibrary {
	 @Autowired
	 Dhis2CohortLibrary dhis2CohortLibrary;

	public CohortIndicator dummyCohortIndicatorMethod(){
		return  cohortIndicator("Dummy Indicator", map(dhis2CohortLibrary.dummyCohortDefinitionMethod(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
	}
}
