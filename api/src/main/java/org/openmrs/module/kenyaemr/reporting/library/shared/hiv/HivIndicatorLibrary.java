/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

package org.openmrs.module.kenyaemr.reporting.library.shared.hiv;

import org.openmrs.Concept;
import org.openmrs.Program;
import org.openmrs.module.kenyaemr.Metadata;
import org.openmrs.module.kenyaemr.reporting.library.shared.hiv.art.ArtCohortLibrary;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
import org.openmrs.module.kenyaemr.reporting.library.shared.common.CommonIndicatorLibrary;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.reporting.indicator.CohortIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.openmrs.module.kenyacore.report.ReportUtils.map;
import static org.openmrs.module.kenyaemr.reporting.EmrReportingUtils.cohortIndicator;

/**
 * Library of HIV related indicator definitions. All indicators require parameters ${startDate} and ${endDate}
 */
@Component
public class HivIndicatorLibrary {

	@Autowired
	private CommonIndicatorLibrary commonIndicators;

	@Autowired
	private HivCohortLibrary hivCohorts;

	/**
	 * Number of new patients enrolled in HIV care (excluding transfers)
	 * @return the indicator
	 */
	public CohortIndicator enrolledExcludingTransfers() {
		return commonIndicators.enrolledExcludingTransfers(MetadataUtils.existing(Program.class, HivMetadata._Program.HIV));
	}

	/**
	 * Number of patients ever enrolled in HIV care (including transfers) up to ${endDate}
	 * @return the indicator
	 */
	public CohortIndicator enrolledCumulative() {
		return commonIndicators.enrolledCumulative(MetadataUtils.existing(Program.class, HivMetadata._Program.HIV));
	}
	/**
	 * Number of patients  enrolled in HIV positiveTB received ART up to ${endDate}
	 * @return the indicator
	 */
	public CohortIndicator enrolledCumulativeTB() {
		return commonIndicators.enrolledCumulativeTB(MetadataUtils.existing(Program.class,HivMetadata._Programs.ART));
	}
	/**
	 * Number of patients who were enrolled (excluding transfers) after referral from the given entry points
	 * @return the indicator
	 */
	public CohortIndicator enrolledExcludingTransfersAndReferredFrom(Concept... entryPoints) {
		return cohortIndicator("newly enrolled patients referred from",
				map(hivCohorts.enrolledExcludingTransfersAndReferredFrom(entryPoints), "onOrAfter=${startDate},onOrBefore=${endDate}"));
	}
	//2016-2-26====
		/**
		 * Number of patients who got level of adherence 
		 * @return the indicator
		 */
		public CohortIndicator levelOfAdherence(int minPercentage,int maxPercentage) {
			return cohortIndicator("patients on ART", map(hivCohorts.levelOfAdherence(minPercentage,maxPercentage), "onOrAfter=${startDate},onOrBefore=${endDate}"));
		}
	//==============

	/**
	 * Number of patients who were enrolled (excluding transfers) after referral from services other than the given entry points
	 * @return the indicator
	 */
	public CohortIndicator enrolledExcludingTransfersAndNotReferredFrom(Concept... entryPoints) {
		return cohortIndicator("newly enrolled patients referred from",
				map(hivCohorts.enrolledExcludingTransfersAndNotReferredFrom(entryPoints), "onOrAfter=${startDate},onOrBefore=${endDate}"));
	}

	/**
	 * Number of patients who are on Cotrimoxazole prophylaxis
	 * @return the indicator
	 */
	public CohortIndicator onCotrimoxazoleProphylaxis() {
		return cohortIndicator("patients on CTX prophylaxis", map(hivCohorts.inHivProgramAndOnCtxProphylaxis(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
	}
	
	/**
	 * Number of patients who are on Fluconazole prophylaxis
	 * @return the indicator
	 */
	public CohortIndicator onFluconazoleProphylaxis() {
		return cohortIndicator("patients on Fluconazole prophylaxis", map(hivCohorts.inHivProgramAndOnFluconazoleProphylaxis(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
	}

	/**
	 * Number of patients who are on any form of prophylaxis
	 * @return the indicator
	 */
	public CohortIndicator onProphylaxis() {
		return cohortIndicator("patients on any prophylaxis", map(hivCohorts.inHivProgramAndOnAnyProphylaxis(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
	}
        
        public CohortIndicator givenDrugsForOI() {
                return cohortIndicator("patients treated for Opportunistic Infection", map(hivCohorts.givenOIDrugs(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
        }
        
        public CohortIndicator initiatedARTandTB(){ 
                return cohortIndicator("TB patients received ART", map(hivCohorts.receivedART(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
        }
        
        public CohortIndicator restartART(){
                return cohortIndicator("patients Restarted ART", map(hivCohorts.restartedProgram(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
        }
        
        public CohortIndicator notInART(){
                return cohortIndicator("patients not initialized for ART", map(hivCohorts.notinitializedART(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
        }
        
        public CohortIndicator performanceScaleA(){
            return cohortIndicator("patients with performance scale A", map(hivCohorts.performanceScale(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
        }
        
            public CohortIndicator performanceScaleB(){
                return cohortIndicator("patients with performance scale B", map(hivCohorts.performanceScaleb(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
       }
            public CohortIndicator performanceScaleC(){
                return cohortIndicator("patients with performance scale B", map(hivCohorts.performanceScalec(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
       }
  
        public CohortIndicator riskFactor1(){
                return cohortIndicator("patients with risk Factor 1", map(hivCohorts.riskFactors1(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
       }    
            
       public CohortIndicator riskFactor2(){
                return cohortIndicator("patients with risk Factor 2", map(hivCohorts.riskFactors2(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
       }  
            
       public CohortIndicator riskFactor3(){
           return cohortIndicator("patients with risk Factor 3", map(hivCohorts.riskFactors3(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
       }    
       
       public CohortIndicator riskFactor4(){
           return cohortIndicator("patients with risk Factor 4", map(hivCohorts.riskFactors4(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
       }       
            
       public CohortIndicator riskFactor5(){
           return cohortIndicator("patients with risk Factor 5", map(hivCohorts.riskFactors5(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
       }    
       
       public CohortIndicator riskFactor6(){
           return cohortIndicator("patients with risk Factor 6", map(hivCohorts.riskFactors6(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
       }      
       
       public CohortIndicator riskFactor7(){
           return cohortIndicator("patients with risk Factor 7", map(hivCohorts.riskFactors7(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
       }  
            
}