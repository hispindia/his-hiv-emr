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

package org.openmrs.module.kenyaemr.fragment.controller.program.tb;

import org.openmrs.Concept;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.Visit;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.result.CalculationResult;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.calculation.EmrCalculationUtils;
import org.openmrs.module.kenyaemr.regimen.RegimenManager;
import org.openmrs.module.kenyaemr.calculation.library.hiv.LastCptCalculation;
import org.openmrs.module.kenyaemr.calculation.library.tb.TbDiseaseClassificationCalculation;
import org.openmrs.module.kenyaemr.calculation.library.tb.TbPatientClassificationCalculation;
import org.openmrs.module.kenyaemr.calculation.library.tb.TbTreatmentNumberCalculation;
import org.openmrs.module.kenyaemr.calculation.library.tb.TbTreatmentOutcomeCalculation;
import org.openmrs.module.kenyaemr.calculation.library.tb.TbTreatmentOutcomeDate;
import org.openmrs.module.kenyaemr.calculation.library.tb.TbTreatmentDrugSensitivity;
import org.openmrs.module.kenyaemr.calculation.library.tb.TbTreatmentDrugRegimen;
import org.openmrs.module.kenyaemr.calculation.library.tb.TbTreatmentStartDateCalculation;
import org.openmrs.module.kenyaemr.regimen.RegimenChangeHistory;
import org.openmrs.module.kenyaemr.wrapper.EncounterWrapper;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller for TB care summary
 */
public class TbCarePanelFragmentController {

	public void controller(@FragmentParam("patient") Patient patient,
						   @FragmentParam("complete") Boolean complete,
						   FragmentModel model,
						   @SpringBean RegimenManager regimenManager) {

		Map<String, Object> calculationResults = new HashMap<String, Object>();

		CalculationResult result = EmrCalculationUtils.evaluateForPatient(TbDiseaseClassificationCalculation.class, null, patient);
		calculationResults.put("tbDiseaseSite", result != null ? result.getValue() : null);
	String listAllSite = "";
		
		Obs siteList = getLatestObs(patient, Dictionary.TB_SITE);
		
		Obs consultationObs =   getAllLatestObs(patient, Dictionary.TB_DIAG_CLASSIFICATION);
		
		if(consultationObs!=null){
			EncounterWrapper wrappedG = new EncounterWrapper(
					consultationObs.getEncounter());
			List<Obs> obsGroupList = wrappedG.allObs(consultationObs.getConcept());
			for (Obs obsG : obsGroupList) {
				if (siteList != null) {
					List<Obs> obsList = Context.getObsService().getObservationsByPersonAndConcept(patient, Dictionary.getConcept(Dictionary.TB_SITE));
					
					for (Obs obs : obsList) {
					
						if(obs.getObsGroupId() == obsG.getObsId()){
							if (listAllSite.isEmpty()) {
								listAllSite = listAllSite.concat(obs
										.getValueCoded().getName().toString());
								
							} else { 
								listAllSite = listAllSite.concat(", "
										+ obs.getValueCoded().getName().toString());
								
							}
							
						}
					}
				}
			}
			
		}

		model.addAttribute("listAllSite", listAllSite);	
		
		result = EmrCalculationUtils.evaluateForPatient(TbPatientClassificationCalculation.class, null, patient);
		calculationResults.put("tbPatientStatus", result != null ? result.getValue() : null);

	//	result = EmrCalculationUtils.evaluateForPatient(TbTreatmentNumberCalculation.class, null, patient);
	//	calculationResults.put("tbTreatmentNumber", result != null ? result.getValue() : null);
		
		result = EmrCalculationUtils.evaluateForPatient(TbTreatmentOutcomeCalculation.class, null, patient);
		calculationResults.put("tbTreatmentOutcome", result != null ? result.getValue() : null);

		result = EmrCalculationUtils.evaluateForPatient(TbTreatmentOutcomeDate.class, null, patient);
		calculationResults.put("tbTreatmentOutcomeDate", result != null ? result.getValue() : null);
		
		result = EmrCalculationUtils.evaluateForPatient(TbTreatmentDrugSensitivity.class, null, patient);
		calculationResults.put("tbTreatmentDrugSensitivity", result != null ? result.getValue() : null);

		result = EmrCalculationUtils.evaluateForPatient(TbTreatmentDrugRegimen.class, null, patient);
		calculationResults.put("tbTreatmentDrugRegimen", result != null ? result.getValue() : null);
		
		result = EmrCalculationUtils.evaluateForPatient(TbTreatmentStartDateCalculation.class, null, patient);
		calculationResults.put("tbTreatmentDrugStartDate", result != null ? result.getValue() : null);
		
		calculationResults.put("onIpt", EmrCalculationUtils.evaluateForPatient(LastCptCalculation.class, null, patient));
		model.addAttribute("calculations", calculationResults);

		Concept medSet = regimenManager.getMasterSetConcept("TB");
		RegimenChangeHistory history = RegimenChangeHistory.forPatient(patient, medSet);
		model.addAttribute("regimenHistory", history);
		
		String iptStatus="";
		
		SimpleDateFormat formatterExt = new SimpleDateFormat("dd-MMM-yyyy");
		List<Obs> obsListForProphylaxis = Context.getObsService().getObservationsByPersonAndConcept(patient, Dictionary.getConcept(Dictionary.PROPHYLAXIS));
		for(Obs obsListForProphylaxi:obsListForProphylaxis){
		if(obsListForProphylaxi.getValueCoded().getUuid().equals("78280AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")){
			iptStatus="Yes";
			
		  }
		
		
		}
		model.addAttribute("ipt", iptStatus);
		List<Visit> visit=Context.getVisitService().getVisitsByPatient(patient);
		Double duration=0.0;Integer duratin=0;
		Obs prophly=getLatestObs(patient, Dictionary.PROPHYLAXIS);
		Obs medduration=getLatestObs(patient, Dictionary.MEDICATION_DURATION);
		 if(prophly!=null && medduration!=null){
		List<Obs> obsListForProphyl = Context.getObsService().getObservationsByPersonAndConcept(patient, Dictionary.getConcept(Dictionary.PROPHYLAXIS));
		for(Obs obsListForProphylaxi:obsListForProphyl){ 
		if(obsListForProphylaxi.getValueCoded().getUuid().equals("78280AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")){
		List<Obs> obsListForDuration = Context.getObsService().getObservationsByPersonAndConcept(patient, Dictionary.getConcept(Dictionary.MEDICATION_DURATION));
	          for(Obs obsListForDurationss:obsListForDuration)
	          {
	        	  if(obsListForDurationss!=null && obsListForDurationss.getObsGroupId().equals(obsListForProphylaxi.getObsGroupId()))
	        	  {         duration= obsListForDurationss.getValueNumeric();
	        				duratin=duration.intValue();
	        				Calendar calendar = Calendar.getInstance();
	        		        Date endDatecpt =obsListForDurationss.getObsDatetime(); 
	        		        calendar.setTime(endDatecpt);
	        				calendar.add(Calendar.DATE, duratin);
	        				endDatecpt = calendar.getTime();
	        				SimpleDateFormat smd=new SimpleDateFormat("dd/M/yyyy");
	        				Date startDatecpt=new Date();
	        				for(Visit visi:visit)
	        				 {
	        					 
	        						if(visi.getStopDatetime()==null && duratin>=1 )
	    	        				{
	    	        				 
	    	        					 model.addAttribute("duration",duratin);
	    	        				 
	    	   
	    	        				}
	    	        				else
	    	        				{
	    	        					if(smd.format(startDatecpt).equals(smd.format(endDatecpt)) || (startDatecpt.before(endDatecpt)) )
	    		        				 { 
	    		        					 model.addAttribute("duration",duratin);
	    		        				 }
	    		        				 else
	    		        				 {
	    		        					 model.addAttribute("duration","");
	    		        				 }
	    	        				}
	        				 }
	        	  }
	        	  else
	        	  {
	        		  model.addAttribute("duration","");  
	        	  }
	          }
	          break;
	 		
		       }
		else
		 { 
			 model.addAttribute("duration","");
		 }
}
	}
		else
		 {
			 model.addAttribute("duration","");
		 }
		
	}
	private Obs getAllLatestObs(Patient patient, String conceptIdentifier) {
		Concept concept = Dictionary.getConcept(conceptIdentifier);
		List<Obs> obs = Context.getObsService()
				.getObservationsByPersonAndConcept(patient, concept);
		int count = obs.size() - 1;
		if (obs.size() > 0) {
			// these are in reverse chronological order
			return obs.get(count);
		}
		return null;
	}
	
	private Obs getLatestObs(Patient patient, String conceptIdentifier) {
		Concept concept = Dictionary.getConcept(conceptIdentifier);
		List<Obs> obs = Context.getObsService()
				.getObservationsByPersonAndConcept(patient, concept);
		if (obs.size() > 0) {
			// these are in reverse chronological order
			return obs.get(0);
		}
		return null;
	}
}