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
import org.openmrs.api.context.Context;
import org.openmrs.calculation.result.CalculationResult;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.calculation.EmrCalculationUtils;
import org.openmrs.module.kenyaemr.regimen.RegimenManager;
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
		
		model.addAttribute("calculations", calculationResults);

		Concept medSet = regimenManager.getMasterSetConcept("TB");
		RegimenChangeHistory history = RegimenChangeHistory.forPatient(patient, medSet);
		model.addAttribute("regimenHistory", history);
		
		String iptStatus="";
		String iptDate="";
		SimpleDateFormat formatterExt = new SimpleDateFormat("dd-MMM-yyyy");
		List<Obs> obsListForProphylaxis = Context.getObsService().getObservationsByPersonAndConcept(patient, Dictionary.getConcept(Dictionary.PROPHYLAXIS));
		for(Obs obsListForProphylaxi:obsListForProphylaxis){
		if(obsListForProphylaxi.getValueCoded().getUuid().equals("78280AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")){
			iptStatus="Yes";
			iptDate = formatterExt.format(obsListForProphylaxi.getDateCreated());
		  }
		}
		model.addAttribute("ipt", iptStatus);
		model.addAttribute("iptDate", iptDate);
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