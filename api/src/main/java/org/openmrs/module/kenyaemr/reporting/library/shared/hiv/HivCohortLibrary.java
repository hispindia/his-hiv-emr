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
import org.openmrs.EncounterType;
import org.openmrs.Program;
import org.openmrs.api.PatientSetService;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyacore.report.cohort.definition.CalculationCohortDefinition;
import org.openmrs.module.kenyacore.report.cohort.definition.DateObsValueBetweenCohortDefinition;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.calculation.library.hiv.OnCtxWithinDurationCalculation;
import org.openmrs.module.kenyaemr.calculation.library.hiv.TBCasesAmongPLHIVSixMonthCalculation;
import org.openmrs.module.kenyaemr.calculation.library.hiv.art.LevelOfAdherenceCalculation;
import org.openmrs.module.kenyaemr.calculation.library.hiv.art.NewHIVPatientEnrolledCalculation;
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
import org.openmrs.module.kenyaemr.reporting.library.shared.common.CommonCohortLibrary;
import org.openmrs.module.kenyaemr.reporting.library.shared.hiv.art.ArtCohortLibrary;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.reporting.cohort.definition.CodedObsCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.common.SetComparator;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import org.openmrs.module.kenyaemr.Metadata;

/**
 * Library of ART related cohort definitions
 */
@Component
public class HivCohortLibrary {

	@Autowired
	private CommonCohortLibrary commonCohorts;
    
	@Autowired
	private ArtCohortLibrary artCohorts;
	/**
	 * Patients referred from the given entry point onto the HIV program
	 * @param entryPoints the entry point concepts
	 * @return the cohort definition
	 */
	public CohortDefinition referredFrom(Concept... entryPoints) {
		EncounterType hivEnrollEncType = MetadataUtils.existing(EncounterType.class, HivMetadata._EncounterType.HIV_ENROLLMENT);
		Concept methodOfEnrollment = Dictionary.getConcept(Dictionary.METHOD_OF_ENROLLMENT);

		CodedObsCohortDefinition cd = new CodedObsCohortDefinition();
		cd.setName("referred from");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.setTimeModifier(PatientSetService.TimeModifier.ANY);
		cd.setQuestion(methodOfEnrollment);
		cd.setValueList(Arrays.asList(entryPoints));
		cd.setOperator(SetComparator.IN);
		cd.setEncounterTypeList(Collections.singletonList(hivEnrollEncType));
		return cd;
	}

	/**
	 * Patients referred from the given entry point onto the HIV program
	 * @param entryPoints the entry point concepts
	 * @return the cohort definition
	 */
	public CohortDefinition referredNotFrom(Concept... entryPoints) {
		EncounterType hivEnrollEncType = MetadataUtils.existing(EncounterType.class, HivMetadata._EncounterType.HIV_ENROLLMENT);
		Concept methodOfEnrollment = Dictionary.getConcept(Dictionary.METHOD_OF_ENROLLMENT);

		CodedObsCohortDefinition cd = new CodedObsCohortDefinition();
		cd.setName("referred not from");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.setTimeModifier(PatientSetService.TimeModifier.ANY);
		cd.setQuestion(methodOfEnrollment);
		cd.setValueList(Arrays.asList(entryPoints));
		cd.setOperator(SetComparator.NOT_IN);
		cd.setEncounterTypeList(Collections.singletonList(hivEnrollEncType));
		return cd;
	}

	/**
	 * Patients who were enrolled in HIV care (including transfers) between ${onOrAfter} and ${onOrBefore}
	 * @return the cohort definition
	 */
	public CohortDefinition enrolled() {
		return commonCohorts.enrolled(MetadataUtils.existing(Program.class, HivMetadata._Program.HIV));
	}

	/**
	 * Patients who were enrolled in HIV care (excluding transfers) between ${onOrAfter} and ${onOrBefore}
	 * @return the cohort definition
	 */
	public CohortDefinition enrolledExcludingTransfers() {
		return commonCohorts.enrolledExcludingTransfers(MetadataUtils.existing(Program.class, HivMetadata._Program.HIV));
	}

	/**
	 * Patients who were enrolled in HIV care (excluding transfers) from the given entry points between ${onOrAfter} and ${onOrBefore}
	 * @return the cohort definition
	 */
	public CohortDefinition enrolledExcludingTransfersAndReferredFrom(Concept... entryPoints) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("enrolled excluding transfers in HIV care from entry points");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("enrolledExcludingTransfers", ReportUtils.map(enrolledExcludingTransfers(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("referredFrom", ReportUtils.map(referredFrom(entryPoints), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("completeProgram", ReportUtils.map(commonCohorts.compltedProgram(), "completedOnOrBefore=${onOrBefore}"));
		cd.setCompositionString("enrolledExcludingTransfers AND referredFrom AND NOT completeProgram");
		return cd;
	}

	//2016-2-26====	
		/**
		 * Patients who who got level of adherence on ${onDate}
		 * @return the cohort definition
		 */
		public CohortDefinition levelOfAdherence(int minPercentage,int maxPercentage) {
			CalculationCohortDefinition cd = new CalculationCohortDefinition(new LevelOfAdherenceCalculation(minPercentage,maxPercentage));
			cd.setName("on ART on date");
			cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
			cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
			return cd;
		}
	//=============
		public CohortDefinition hivCohort() {
			CalculationCohortDefinition cd = new CalculationCohortDefinition(new TBCasesAmongPLHIVSixMonthCalculation());
			                                       
			cd.setName("on Hiv on date");
			cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
			
			return cd;
		}

	/**
	 * Patients who were enrolled in HIV care (excluding transfers) not from the given entry points between ${onOrAfter} and ${onOrBefore}
	 * @return the cohort definition
	 */
	public CohortDefinition enrolledExcludingTransfersAndNotReferredFrom(Concept... entryPoints) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("enrolled excluding transfers in HIV care not from entry points");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("enrolledExcludingTransfers", ReportUtils.map(enrolledExcludingTransfers(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("referredNotFrom", ReportUtils.map(referredNotFrom(entryPoints), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("completeProgram", ReportUtils.map(commonCohorts.compltedProgram(), "completedOnOrBefore=${onOrBefore}"));
		cd.setCompositionString("enrolledExcludingTransfers AND referredNotFrom AND NOT completeProgram");
		return cd;
	}
        
        public CohortDefinition restartedProgram(){
            CompositionCohortDefinition cd = new CompositionCohortDefinition();
            cd.setName("enrolled excluding transfers in HIV care not from entry points");
            cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
            cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
            cd.addSearch("enrolled", ReportUtils.map(commonCohorts.enrolled(MetadataUtils.existing(Program.class, Metadata.Program.ART)),"enrolledOnOrAfter=${onOrAfter},enrolledOnOrBefore=${onOrBefore}"));
            cd.addSearch("completeProgram", ReportUtils.map(commonCohorts.compltedProgram(MetadataUtils.existing(Program.class, Metadata.Program.ART)), "completedOnOrBefore=${onOrBefore}"));
            cd.setCompositionString("enrolled AND  completeProgram");
            return cd;
        }

        public CohortDefinition reasonOfoutcome(){
        	Concept outcome = Dictionary.getConcept(Dictionary.REASON_FOR_PROGRAM_DISCONTINUATION);
        	Concept reason = Dictionary.getConcept(Dictionary.DIED);

            CompositionCohortDefinition cd = new CompositionCohortDefinition();
            cd.setName("outcome result of patient due to death");
            cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
            cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
            cd.addSearch("enrolled", ReportUtils.map(commonCohorts.enrolled(MetadataUtils.existing(Program.class, Metadata.Program.ART)),"enrolledOnOrAfter=${onOrAfter},enrolledOnOrBefore=${onOrBefore}"));
            cd.addSearch("outcomeReason", ReportUtils.map(commonCohorts.hasObs(outcome ,reason), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
            cd.setCompositionString("enrolled AND  outcomeReason");
            return cd;
        }
        
        public CohortDefinition reasonOfoutcometransfer(){
        	Concept outcome = Dictionary.getConcept(Dictionary.REASON_FOR_PROGRAM_DISCONTINUATION);
        	Concept reason = Dictionary.getConcept(Dictionary.TRANSFERRED_OUT);

            CompositionCohortDefinition cd = new CompositionCohortDefinition();
            cd.setName("outcome result of patient due to death");
            cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
            cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
            cd.addSearch("enrolled", ReportUtils.map(commonCohorts.enrolled(MetadataUtils.existing(Program.class, Metadata.Program.ART)),"enrolledOnOrAfter=${onOrAfter},enrolledOnOrBefore=${onOrBefore}"));
            cd.addSearch("outcomeReason", ReportUtils.map(commonCohorts.hasObs(outcome ,reason), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
            cd.setCompositionString("enrolled AND  outcomeReason");
            return cd;
        }
        
        public CohortDefinition reasonOfoutcomeMissing(){
        	Concept outcome = Dictionary.getConcept(Dictionary.REASON_FOR_PROGRAM_DISCONTINUATION);
        	Concept reason = Dictionary.getConcept(Dictionary.LOST_MISSING_FOLLOW);

            CompositionCohortDefinition cd = new CompositionCohortDefinition();
            cd.setName("outcome result of patient due to death");
            cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
            cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
            cd.addSearch("enrolled", ReportUtils.map(commonCohorts.enrolled(MetadataUtils.existing(Program.class, Metadata.Program.ART)),"enrolledOnOrAfter=${onOrAfter},enrolledOnOrBefore=${onOrBefore}"));
            cd.addSearch("outcomeReason", ReportUtils.map(commonCohorts.hasObs(outcome ,reason), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
            cd.setCompositionString("enrolled AND  outcomeReason");
            return cd;
        }
        
        public CohortDefinition totalArtpatient(){
        	
        	
            CompositionCohortDefinition cd = new CompositionCohortDefinition();
            cd.setName("outcome result of patient due to death");
            cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
            cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
            cd.addSearch("enrolled", ReportUtils.map(commonCohorts.enrolled(MetadataUtils.existing(Program.class, Metadata.Program.ART)),"enrolledOnOrAfter=${onOrAfter},enrolledOnOrBefore=${onOrBefore}"));
            cd.addSearch("outcomeReason1", ReportUtils.map(reasonOfoutcome(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
            cd.addSearch("outcomeReason2", ReportUtils.map(reasonOfoutcometransfer(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
            cd.addSearch("outcomeReason3", ReportUtils.map(reasonOfoutcomeMissing(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
            cd.addSearch("complete", ReportUtils.map(restartedProgram(),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
            cd.addSearch("completeprog",ReportUtils.map(artCohorts.startedArtExcludingTransferinsOnDates(),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
            cd.setCompositionString("enrolled AND completeprog AND NOT outcomeReason1 AND NOT outcomeReason2 AND NOT outcomeReason3 AND NOT complete");
            return cd;
        }

       public CohortDefinition onCTX(){
    	   Concept oi = Dictionary.getConcept(Dictionary.OI_TREATMENT_DRUG);
    	   Concept ctx = Dictionary.getConcept(Dictionary.SULFAMETHOXAZOLE_TRIMETHOPRIM);
        	
            CompositionCohortDefinition cd = new CompositionCohortDefinition();
            cd.setName("hiv patient on CTX");
            cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
            cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
            cd.addSearch("enrolledHIV", ReportUtils.map(commonCohorts.enrolled(MetadataUtils.existing(Program.class, Metadata.Program.HIV)),"enrolledOnOrAfter=${onOrAfter},enrolledOnOrBefore=${onOrBefore}"));
            cd.addSearch("enrolled", ReportUtils.map(commonCohorts.enrolled(MetadataUtils.existing(Program.class, Metadata.Program.ART)),"enrolledOnOrBefore=${onOrBefore}"));
            cd.addSearch("hasctx", ReportUtils.map(commonCohorts.hasObs(oi,ctx), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
            cd.setCompositionString("enrolledHIV AND NOT enrolled AND hasctx");
            return cd;
        }
       
       public CohortDefinition startedTB(){
           Concept tbPatients = Dictionary.getConcept(Dictionary.TB_PATIENT);
           Concept tbstarted = Dictionary.getConcept(Dictionary.TUBERCULOSIS_DRUG_TREATMENT_START_DATE);
           CompositionCohortDefinition cd = new CompositionCohortDefinition();
           cd.setName("Number of HIV-infected patients with incident TB diagnosed and started on TB treatment during the reporting period");
           cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
	        cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
           cd.addSearch("tbpat", ReportUtils.map(commonCohorts.hasObs(tbPatients), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
           cd.addSearch("tbstart", ReportUtils.map(commonCohorts.hasObs(tbstarted), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
           cd.addSearch("enrolled", ReportUtils.map(commonCohorts.enrolled(MetadataUtils.existing(Program.class, Metadata.Program.HIV)),"enrolledOnOrAfter=${onOrAfter},enrolledOnOrBefore=${onOrBefore}"));
           cd.setCompositionString("tbpat AND tbstart AND enrolled");
           
           
           return cd;
        }
       
       public CohortDefinition incidentTB(){
           Concept tbPatients = Dictionary.getConcept(Dictionary.TB_PATIENT);
           Concept tbstarted = Dictionary.getConcept(Dictionary.TUBERCULOSIS_DRUG_TREATMENT_START_DATE);
           CompositionCohortDefinition cd = new CompositionCohortDefinition();
           cd.setName("PLHIV with incident TB");
           cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
	        cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
           cd.addSearch("tbpat", ReportUtils.map(commonCohorts.hasObs(tbPatients), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
           cd.addSearch("tbstart", ReportUtils.map(commonCohorts.hasObs(tbstarted), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
           cd.addSearch("enrolled", ReportUtils.map(commonCohorts.enrolled(MetadataUtils.existing(Program.class, Metadata.Program.HIV)),"enrolledOnOrAfter=${onOrAfter},enrolledOnOrBefore=${onOrBefore}"));
           cd.setCompositionString("tbpat AND NOT tbstart AND enrolled");
           
           
           return cd;
        }
        
       public CohortDefinition isoniazidTB(){
    	   Concept prophyl= Dictionary.getConcept(Dictionary.PROPHYLAXIS);
           Concept isionizd= Dictionary.getConcept(Dictionary.ISONIAZID);
           CompositionCohortDefinition cd = new CompositionCohortDefinition();
           cd.setName("PLHIV on IPT");
           cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
	        cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
           cd.addSearch("isionizd", ReportUtils.map(commonCohorts.hasObs(prophyl,isionizd), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
           cd.addSearch("enrolled", ReportUtils.map(commonCohorts.enrolled(MetadataUtils.existing(Program.class, Metadata.Program.HIV)),"enrolledOnOrAfter=${onOrAfter},enrolledOnOrBefore=${onOrBefore}"));
           cd.setCompositionString("isionizd AND enrolled");
           
           
           return cd;
        }
       
       public CohortDefinition screenedTB(){
           Concept tbPatients = Dictionary.getConcept(Dictionary.TB_SCREENING);
           Concept assessed1 = Dictionary.getConcept(Dictionary. COUGH_LASTING_MORE_THAN_TWO_WEEKS);
           Concept assessed2 = Dictionary.getConcept(Dictionary.FEVER_TB);
           Concept assessed3 = Dictionary.getConcept(Dictionary.NIGHT_SWEATS);
           Concept assessed4 = Dictionary.getConcept(Dictionary.WEIGHT_LOSS);
           Concept assessed5 = Dictionary.getConcept(Dictionary.LYMPH_NODE);
           Concept assessed6 = Dictionary.getConcept(Dictionary.NONE);
           CompositionCohortDefinition cd = new CompositionCohortDefinition();
           cd.setName("PPLHIV assessed for TB");
           cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
	        cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
           cd.addSearch("tbpatcogh", ReportUtils.map(commonCohorts.hasObs(tbPatients,assessed1), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
           cd.addSearch("tbpatfev", ReportUtils.map(commonCohorts.hasObs(tbPatients,assessed2), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
           cd.addSearch("tbpatsweat", ReportUtils.map(commonCohorts.hasObs(tbPatients,assessed3), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
           cd.addSearch("tbpatweight", ReportUtils.map(commonCohorts.hasObs(tbPatients,assessed4), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
           cd.addSearch("tbpatlymph", ReportUtils.map(commonCohorts.hasObs(tbPatients,assessed5), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
           cd.addSearch("tbpatnone", ReportUtils.map(commonCohorts.hasObs(tbPatients,assessed6), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
           cd.addSearch("enrolled", ReportUtils.map(commonCohorts.enrolled(MetadataUtils.existing(Program.class, Metadata.Program.HIV)),"enrolledOnOrAfter=${onOrAfter},enrolledOnOrBefore=${onOrBefore}"));
           cd.setCompositionString("tbpatcogh OR tbpatfev OR tbpatsweat OR tbpatweight OR tbpatlymph OR tbpatnone AND enrolled");
           
           
           return cd;
        }
	/**
	 * Patients with a CD4 result between {onOrAfter} and {onOrBefore}
	 * @return the cohort definition
	 */
	public CohortDefinition hasCd4Result() {
		Concept cd4Count = Dictionary.getConcept(Dictionary.CD4_COUNT);
		Concept cd4Percent = Dictionary.getConcept(Dictionary.CD4_PERCENT);

		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("patients with CD4 results");
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addSearch("hasCdCount", ReportUtils.map(commonCohorts.hasObs(cd4Count), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("hasCd4Percent", ReportUtils.map(commonCohorts.hasObs(cd4Percent), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("hasCdCount OR hasCd4Percent");
		return cd;
	}
        
        public CohortDefinition givenOIDrugs(){
                Concept oiTxDrugs = Dictionary.getConcept(Dictionary.OI_TREATMENT_DRUG);
                
                CompositionCohortDefinition cd = new CompositionCohortDefinition();
                cd.setName("Patients Treated for Opportunistic Infections");
                cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		       cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
                cd.addSearch("givenDrugs", ReportUtils.map(commonCohorts.hasObs(oiTxDrugs), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
                cd.setCompositionString("givenDrugs");
                return cd;
        }
        
        public CohortDefinition receivedART(){
            Concept tbPatients = Dictionary.getConcept(Dictionary.TB_PATIENT);
              
                CompositionCohortDefinition cd = new CompositionCohortDefinition();
                cd.setName("Patients HIV positive TB patients who have received ART");
                cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		        cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
                cd.addSearch("givenDrugs", ReportUtils.map(commonCohorts.hasObs(tbPatients), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
                cd.addSearch("enrolled", ReportUtils.map(commonCohorts.enrolled(MetadataUtils.existing(Program.class, Metadata.Program.ART)),"enrolledOnOrAfter=${onOrAfter},enrolledOnOrBefore=${onOrBefore}"));
                cd.setCompositionString("givenDrugs AND enrolled");
                
                
                return cd;
        }
        
        public CohortDefinition notinitializedART(){
            CompositionCohortDefinition cd = new CompositionCohortDefinition();
            cd.setName("waiting list for ART");
            cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
            cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
            cd.addSearch("enrolledHIV", ReportUtils.map(commonCohorts.enrolled(MetadataUtils.existing(Program.class, Metadata.Program.HIV)),"enrolledOnOrAfter=${onOrAfter},enrolledOnOrBefore=${onOrBefore}"));
            cd.addSearch("enrolled", ReportUtils.map(commonCohorts.enrolled(MetadataUtils.existing(Program.class, Metadata.Program.ART)),"enrolledOnOrBefore=${onOrBefore}"));
            cd.setCompositionString("enrolledHIV AND NOT enrolled");
            return cd;
        }
        
        public CohortDefinition testedForCDFour(){
        	Concept laboratoryOrder = Dictionary.getConcept(Dictionary.lABORATORY_ORDER);
        	Concept cd4Count = Dictionary.getConcept(Dictionary.CD4_COUNT);
        	
        	CompositionCohortDefinition cd = new CompositionCohortDefinition();
            cd.setName("tested for CD 4 count");
            cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
            cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
            cd.addSearch("testedForCD4count", ReportUtils.map(commonCohorts.hasObs(laboratoryOrder,cd4Count), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
            cd.setCompositionString("testedForCD4count");
            return cd;
        }
        
        public CohortDefinition testedForViralLoad(){
        	Concept laboratoryOrder = Dictionary.getConcept(Dictionary.lABORATORY_ORDER);
        	Concept viralLoad = Dictionary.getConcept(Dictionary.HIV_VIRAL_LOAD);
        	
        	CompositionCohortDefinition cd = new CompositionCohortDefinition();
            cd.setName("tested for viral load");
            cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
            cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
            cd.addSearch("testedForViralLoad", ReportUtils.map(commonCohorts.hasObs(laboratoryOrder,viralLoad), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
            cd.setCompositionString("testedForViralLoad");
            return cd;
        }
        
        public CohortDefinition performanceScale(){
        	Concept performance = Dictionary.getConcept(Dictionary.PERFORMANCE);
        	Concept scaleA = Dictionary.getConcept(Dictionary.PERFSCALE_A);
        	
        	
            CompositionCohortDefinition cd = new CompositionCohortDefinition();
            cd.setName("patient with performance scale A");
            cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
            cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
            cd.addSearch("enrolledHIV", ReportUtils.map(commonCohorts.enrolled(MetadataUtils.existing(Program.class, Metadata.Program.HIV)),"enrolledOnOrAfter=${onOrAfter},enrolledOnOrBefore=${onOrBefore}"));
            cd.addSearch("enrolled", ReportUtils.map(commonCohorts.enrolled(MetadataUtils.existing(Program.class, Metadata.Program.ART)),"enrolledOnOrBefore=${onOrBefore}"));
            cd.addSearch("scaleA", ReportUtils.map(commonCohorts.hasObs(performance,scaleA), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
            cd.setCompositionString("enrolledHIV AND scaleA AND NOT enrolled");
            return cd;
        } 
        public CohortDefinition performanceScaleb(){
        	Concept performance = Dictionary.getConcept(Dictionary.PERFORMANCE);
        	
        	Concept scaleB = Dictionary.getConcept(Dictionary.PERFSCALE_B);
        	
            CompositionCohortDefinition cd = new CompositionCohortDefinition();
            cd.setName("patient with performance scale A");
            cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
            cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
            cd.addSearch("enrolledHIV", ReportUtils.map(commonCohorts.enrolled(MetadataUtils.existing(Program.class, Metadata.Program.HIV)),"enrolledOnOrAfter=${onOrAfter},enrolledOnOrBefore=${onOrBefore}"));
            cd.addSearch("enrolled", ReportUtils.map(commonCohorts.enrolled(MetadataUtils.existing(Program.class, Metadata.Program.ART)),"enrolledOnOrBefore=${onOrBefore}"));
            cd.addSearch("scaleB", ReportUtils.map(commonCohorts.hasObs(performance,scaleB), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
            cd.setCompositionString("enrolledHIV AND scaleB AND NOT enrolled");
            return cd;
        } 
        public CohortDefinition performanceScalec(){
        	Concept performance = Dictionary.getConcept(Dictionary.PERFORMANCE);
        	
        	Concept scaleC = Dictionary.getConcept(Dictionary.PERFSCALE_C);
        	
            CompositionCohortDefinition cd = new CompositionCohortDefinition();
            cd.setName("patient with performance scale A");
            cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
            cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
            cd.addSearch("enrolledHIV", ReportUtils.map(commonCohorts.enrolled(MetadataUtils.existing(Program.class, Metadata.Program.HIV)),"enrolledOnOrAfter=${onOrAfter},enrolledOnOrBefore=${onOrBefore}"));
            cd.addSearch("enrolled", ReportUtils.map(commonCohorts.enrolled(MetadataUtils.existing(Program.class, Metadata.Program.ART)),"enrolledOnOrBefore=${onOrBefore}"));
            cd.addSearch("scaleC", ReportUtils.map(commonCohorts.hasObs(performance,scaleC), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
            cd.setCompositionString("enrolledHIV AND scaleC AND NOT enrolled");
            return cd;
        } 
        
        
        
        public CohortDefinition riskFactors1(){
        	Concept risk = Dictionary.getConcept(Dictionary.HIV_RISK_FACTOR);
        	
        	Concept risk1 = Dictionary.getConcept(Dictionary.RISK_FACTOR_MSM);
        	
            CompositionCohortDefinition cd = new CompositionCohortDefinition();
            cd.setName("patient with risk 1");
            cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
            cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
            cd.addSearch("enrolledHIV", ReportUtils.map(commonCohorts.enrolled(MetadataUtils.existing(Program.class, Metadata.Program.HIV)),"enrolledOnOrAfter=${onOrAfter},enrolledOnOrBefore=${onOrBefore}"));
            cd.addSearch("enrolled", ReportUtils.map(commonCohorts.enrolled(MetadataUtils.existing(Program.class, Metadata.Program.ART)),"enrolledOnOrBefore=${onOrBefore}"));
            cd.addSearch("risk1", ReportUtils.map(commonCohorts.hasObs(risk,risk1), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
            cd.setCompositionString("enrolledHIV AND risk1 AND NOT enrolled");
            return cd;
        }
        
        public CohortDefinition riskFactors2(){
        	Concept risk = Dictionary.getConcept(Dictionary.HIV_RISK_FACTOR);
        	
        	Concept risk2 = Dictionary.getConcept(Dictionary.RISK_FACTOR_SW);
        	
            CompositionCohortDefinition cd = new CompositionCohortDefinition();
            cd.setName("patient with risk 2");
            cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
            cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
            cd.addSearch("enrolledHIV", ReportUtils.map(commonCohorts.enrolled(MetadataUtils.existing(Program.class, Metadata.Program.HIV)),"enrolledOnOrAfter=${onOrAfter},enrolledOnOrBefore=${onOrBefore}"));
            cd.addSearch("enrolled", ReportUtils.map(commonCohorts.enrolled(MetadataUtils.existing(Program.class, Metadata.Program.ART)),"enrolledOnOrBefore=${onOrBefore}"));
            cd.addSearch("risk2", ReportUtils.map(commonCohorts.hasObs(risk,risk2), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
            cd.setCompositionString("enrolledHIV AND risk2 AND NOT enrolled");
            return cd;
        }
        
        public CohortDefinition riskFactors3(){
        	Concept risk = Dictionary.getConcept(Dictionary.HIV_RISK_FACTOR);
        	
        	Concept risk3 = Dictionary.getConcept(Dictionary.RISK_FACTOR_HETRO);
        	
            CompositionCohortDefinition cd = new CompositionCohortDefinition();
            cd.setName("patient with risk 3");
            cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
            cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
            cd.addSearch("enrolledHIV", ReportUtils.map(commonCohorts.enrolled(MetadataUtils.existing(Program.class, Metadata.Program.HIV)),"enrolledOnOrAfter=${onOrAfter},enrolledOnOrBefore=${onOrBefore}"));
            cd.addSearch("enrolled", ReportUtils.map(commonCohorts.enrolled(MetadataUtils.existing(Program.class, Metadata.Program.ART)),"enrolledOnOrBefore=${onOrBefore}"));
            cd.addSearch("risk3", ReportUtils.map(commonCohorts.hasObs(risk,risk3), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
            cd.setCompositionString("enrolledHIV AND risk3 AND NOT enrolled");
            return cd;
        }
        
        
        public CohortDefinition riskFactors4(){
        	Concept risk = Dictionary.getConcept(Dictionary.HIV_RISK_FACTOR);
        	
        	Concept risk4 = Dictionary.getConcept(Dictionary.RISK_FACTOR_IDU);
        	
            CompositionCohortDefinition cd = new CompositionCohortDefinition();
            cd.setName("patient with risk 4");
            cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
            cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
            cd.addSearch("enrolledHIV", ReportUtils.map(commonCohorts.enrolled(MetadataUtils.existing(Program.class, Metadata.Program.HIV)),"enrolledOnOrAfter=${onOrAfter},enrolledOnOrBefore=${onOrBefore}"));
            cd.addSearch("enrolled", ReportUtils.map(commonCohorts.enrolled(MetadataUtils.existing(Program.class, Metadata.Program.ART)),"enrolledOnOrBefore=${onOrBefore}"));
            cd.addSearch("risk4", ReportUtils.map(commonCohorts.hasObs(risk,risk4), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
            cd.setCompositionString("enrolledHIV AND risk4 AND NOT enrolled");
            return cd;
        }
        
        public CohortDefinition riskFactors5(){
        	Concept risk = Dictionary.getConcept(Dictionary.HIV_RISK_FACTOR);
        	
        	Concept risk5 = Dictionary.getConcept(Dictionary.RISK_FACTOR_MOTHERTOCHILD);
        	
            CompositionCohortDefinition cd = new CompositionCohortDefinition();
            cd.setName("patient with risk 5");
            cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
            cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
            cd.addSearch("enrolledHIV", ReportUtils.map(commonCohorts.enrolled(MetadataUtils.existing(Program.class, Metadata.Program.HIV)),"enrolledOnOrAfter=${onOrAfter},enrolledOnOrBefore=${onOrBefore}"));
            cd.addSearch("enrolled", ReportUtils.map(commonCohorts.enrolled(MetadataUtils.existing(Program.class, Metadata.Program.ART)),"enrolledOnOrBefore=${onOrBefore}"));
            cd.addSearch("risk5", ReportUtils.map(commonCohorts.hasObs(risk,risk5), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
            cd.setCompositionString("enrolledHIV AND risk5 AND NOT enrolled");
            return cd;
        }
        
        public CohortDefinition riskFactors6(){
        	Concept risk = Dictionary.getConcept(Dictionary.HIV_RISK_FACTOR);
        	
        	Concept risk6 = Dictionary.getConcept(Dictionary.RISK_FACTOR_BLOODTRANS);
        	
            CompositionCohortDefinition cd = new CompositionCohortDefinition();
            cd.setName("patient with risk 6");
            cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
            cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
            cd.addSearch("enrolledHIV", ReportUtils.map(commonCohorts.enrolled(MetadataUtils.existing(Program.class, Metadata.Program.HIV)),"enrolledOnOrAfter=${onOrAfter},enrolledOnOrBefore=${onOrBefore}"));
            cd.addSearch("enrolled", ReportUtils.map(commonCohorts.enrolled(MetadataUtils.existing(Program.class, Metadata.Program.ART)),"enrolledOnOrBefore=${onOrBefore}"));
            cd.addSearch("risk6", ReportUtils.map(commonCohorts.hasObs(risk,risk6), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
            cd.setCompositionString("enrolledHIV AND risk6 AND NOT enrolled");
            return cd;
        }
        
        
        public CohortDefinition riskFactors7(){
        	Concept risk = Dictionary.getConcept(Dictionary.HIV_RISK_FACTOR);
        	
        	Concept risk7 = Dictionary.getConcept(Dictionary.RISK_FACTOR_UNKNOWN);
        	
            CompositionCohortDefinition cd = new CompositionCohortDefinition();
            cd.setName("patient with risk 7");
            cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
            cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
            cd.addSearch("enrolledHIV", ReportUtils.map(commonCohorts.enrolled(MetadataUtils.existing(Program.class, Metadata.Program.HIV)),"enrolledOnOrAfter=${onOrAfter},enrolledOnOrBefore=${onOrBefore}"));
            cd.addSearch("enrolled", ReportUtils.map(commonCohorts.enrolled(MetadataUtils.existing(Program.class, Metadata.Program.ART)),"enrolledOnOrBefore=${onOrBefore}"));
            cd.addSearch("risk7", ReportUtils.map(commonCohorts.hasObs(risk,risk7), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
            cd.setCompositionString("enrolledHIV AND risk7 AND NOT enrolled");
            return cd;
        }
        
        
   
	/**
	 * Patients with a HIV care visit between {onOrAfter} and {onOrBefore}
	 * @return the cohort definition
	 */
	public CohortDefinition hasHivVisit() {
		EncounterType hivEnrollment = MetadataUtils.existing(EncounterType.class, HivMetadata._EncounterType.HIV_ENROLLMENT);
		EncounterType hivConsultation = MetadataUtils.existing(EncounterType.class, HivMetadata._EncounterType.HIV_CONSULTATION);
		return commonCohorts.hasEncounter(hivEnrollment, hivConsultation);
	}

	/**
	 * Patients who took CTX prophylaxis between ${onOrAfter} and ${onOrBefore}
	 * @return the cohort definition
	 */
	public CohortDefinition onCtxProphylaxis() {
		CodedObsCohortDefinition onCtx = new CodedObsCohortDefinition();
		onCtx.setName("on CTX prophylaxis");
		onCtx.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		onCtx.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		onCtx.setTimeModifier(PatientSetService.TimeModifier.LAST);
		onCtx.setQuestion(Dictionary.getConcept(Dictionary.COTRIMOXAZOLE_DISPENSED));
		onCtx.setValueList(Arrays.asList(Dictionary.getConcept(Dictionary.YES)));
		onCtx.setOperator(SetComparator.IN);

		//we need to include those patients who have either ctx in the med orders
		//that was not captured coded obs

		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Having CTX either dispensed");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("onCtx", ReportUtils.map(onCtx, "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("onMedCtx", ReportUtils.map(commonCohorts.medicationDispensed(Dictionary.getConcept(Dictionary.SULFAMETHOXAZOLE_TRIMETHOPRIM)),"onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("onCtxOnDuration", ReportUtils.map(onCtxOnDuration(), "onDate=${onOrBefore}"));
		cd.setCompositionString("onCtx OR onMedCtx OR onCtxOnDuration");

		return cd;
	}

	/**
	 * Patients who are in HIV care and are taking CTX prophylaxis between ${onOrAfter} and ${onOrBefore}
	 * @return
	 */
	public CohortDefinition inHivProgramAndOnCtxProphylaxis() {
		Program hivProgram = MetadataUtils.existing(Program.class, HivMetadata._Program.HIV);
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("in HIV program and on CTX prophylaxis");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("inProgram", ReportUtils.map(commonCohorts.inProgram(hivProgram), "onDate=${onOrBefore}"));
		cd.addSearch("onCtxProphylaxis", ReportUtils.map(onCtxProphylaxis(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("inProgram AND onCtxProphylaxis");
		return cd;
	}

	/**
	 * Patients who are in HIV care and are taking Fluconazole prophylaxis between ${onOrAfter} and ${onOrBefore}
	 * @return
	 */
	public CohortDefinition inHivProgramAndOnFluconazoleProphylaxis() {
		Concept flucanozole = Dictionary.getConcept(Dictionary.FLUCONAZOLE);
		Program hivProgram = MetadataUtils.existing(Program.class, HivMetadata._Program.HIV);
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("in HIV program and on Fluconazole prophylaxis");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("inProgram", ReportUtils.map(commonCohorts.inProgram(hivProgram), "onDate=${onOrBefore}"));
		cd.addSearch("onMedication", ReportUtils.map(commonCohorts.medicationDispensed(flucanozole), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("inProgram AND onMedication");
		return cd;
	}

	/**
	 * Patients who are in HIV care and are on Flucanzole or CTX prophylaxis
	 * @return
	 */
	public CohortDefinition inHivProgramAndOnAnyProphylaxis() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("in HIV program and on any prophylaxis");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("onCtx", ReportUtils.map(inHivProgramAndOnCtxProphylaxis(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("onFlucanozole", ReportUtils.map(inHivProgramAndOnFluconazoleProphylaxis(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("onCtx OR onFlucanozole");
		return cd;
	}

	/**
	 * Patients tested for HIV between ${onOrAfter} and ${onOrBefore}
	 * @return the cohort definition
	 */
	public  CohortDefinition testedForHiv() {
		Concept hivStatus = Dictionary.getConcept(Dictionary.HIV_STATUS);
		Concept indeterminate = Dictionary.getConcept(Dictionary.INDETERMINATE);
		Concept hivInfected = Dictionary.getConcept(Dictionary.HIV_INFECTED);
		Concept unknown = Dictionary.getConcept(Dictionary.UNKNOWN);
		Concept positive = Dictionary.getConcept(Dictionary.POSITIVE);
		Concept negative = Dictionary.getConcept(Dictionary.NEGATIVE);
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("tested for HIV");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("resultOfHivTest", ReportUtils.map(commonCohorts.hasObs(hivStatus, unknown, positive, negative), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("testedForHivHivInfected", ReportUtils.map(commonCohorts.hasObs(hivInfected, indeterminate,positive,negative), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("resultOfHivTest OR testedForHivHivInfected");
		return cd;
	}

	/**
	 * Patients tested for HIV and turn to be positive ${onOrAfter} and ${onOrBefore}
	 * @return the cohort definition
	 */
	public  CohortDefinition testedHivStatus(Concept status) {
		Concept hivStatus = Dictionary.getConcept(Dictionary.HIV_STATUS);
		Concept hivInfected = Dictionary.getConcept(Dictionary.HIV_INFECTED);
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("tested for positive for HIV");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("resultOfHivTestPositive", ReportUtils.map(commonCohorts.hasObs(hivStatus, status), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("testedForHivHivInfectedPositive", ReportUtils.map(commonCohorts.hasObs(hivInfected ,status), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("resultOfHivTestPositive OR testedForHivHivInfectedPositive");
		return cd;
	}

	/**
	 * Patients who started art from the transfer facility
	 * @return the cohort definition
	 */
	public CohortDefinition startedArtFromTransferringFacilityOnDate() {
		Concept starteArtFromTransferringFacility = Dictionary.getConcept(Dictionary.ANTIRETROVIRAL_TREATMENT_START_DATE);
		DateObsValueBetweenCohortDefinition cd = new DateObsValueBetweenCohortDefinition();
		cd.setName("Patients Who Started ART From the Transferring Facility between date");
		cd.setQuestion(starteArtFromTransferringFacility);
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		return cd;

	}

	/**
	 * Patients who are on ctx on ${onDate}
	 * @return the cohort definition
	 */
	public CohortDefinition onCtxOnDuration() {
		CalculationCohortDefinition cd = new CalculationCohortDefinition(new OnCtxWithinDurationCalculation());
		cd.setName("On CTX on date");
		cd.addParameter(new Parameter("onDate", "On Date", Date.class));
		return cd;
	}
}