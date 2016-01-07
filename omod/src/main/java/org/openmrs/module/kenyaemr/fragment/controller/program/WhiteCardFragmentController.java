package org.openmrs.module.kenyaemr.fragment.controller.program;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openmrs.Concept;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.wrapper.EncounterWrapper;
import org.openmrs.module.kenyaemr.wrapper.PatientWrapper;
import org.openmrs.module.kenyaemr.wrapper.PersonWrapper;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

public class WhiteCardFragmentController {
	public void controller(@RequestParam(value = "patientId", required = false) Person person,@RequestParam(value = "patientId", required = false) Patient patient,
			FragmentModel model) {

		
		/*
		 * Constant value across all visit
		 * */
		model.addAttribute("patientName",person.getGivenName());
		model.addAttribute("patientAge",person.getAge());
	    model.addAttribute("birthDate", new SimpleDateFormat("dd-MMMM-yyyy").format(person.getBirthdate()));
		model.addAttribute("patientGender", person.getGender());
		model.addAttribute("address", person.getPersonAddress());
		
		PatientWrapper wrapperPatient = new PatientWrapper(patient);
		PersonWrapper wrapperPerson = new PersonWrapper(person);
		
		model.addAttribute("patientWrap", wrapperPatient);
		model.addAttribute("personWrap", wrapperPerson);
		
		Obs savedEntryPoint = getLatestObs(patient, Dictionary.METHOD_OF_ENROLLMENT);
		model.addAttribute("savedEntryPoint", savedEntryPoint);
		if(savedEntryPoint.getValueDate()!=null){
			model.addAttribute("savedEntryPointValueDate",new SimpleDateFormat("dd-MMMM-yyyy").format(savedEntryPoint.getValueDate()));
		}
		else{
			model.addAttribute("savedEntryPointValueDate","");
		}
		
		/*
		 * Personal History
		 * */
		Obs riskFactor = getAllLatestObs(patient, Dictionary.HIV_RISK_FACTOR);
		System.out.println(riskFactor.getEncounter());
		
		EncounterWrapper wrapped = new EncounterWrapper(riskFactor.getEncounter());
		List<Obs> obsList = wrapped.allObs(riskFactor.getConcept());
		String listAllRiskFactor="";
		for (Obs obs : obsList) {
			if(listAllRiskFactor.isEmpty()){
				listAllRiskFactor = listAllRiskFactor.concat(obs.getValueCoded().getName().toString());
			}
			else{
				listAllRiskFactor = listAllRiskFactor.concat(", "+obs.getValueCoded().getName().toString());	
			}
		}
		model.addAttribute("listAllRiskFactor", listAllRiskFactor);
		
		/*
		 * Varaible for each visit
		 * */
		model.addAttribute("graphingConcepts", Dictionary.getConcepts(
				Dictionary.RETURN_VISIT_DATE, Dictionary.WEIGHT_KG,
				Dictionary.HEIGHT_CM, Dictionary.CURRENT_WHO_STAGE,
				Dictionary.PREGNANCY_STATUS,
				Dictionary.EXPECTED_DATE_OF_DELIVERY,
				Dictionary.METHOD_OF_FAMILY_PLANNING, Dictionary.OI_TB_FORM,
				Dictionary.MEDICATION_ORDERS, Dictionary.TB_PATIENT,
				Dictionary.CD4_COUNT, Dictionary.CD4_PERCENT,
				Dictionary.HIV_VIRAL_LOAD));

	}
	
	private Obs getLatestObs(Patient patient, String conceptIdentifier) {
		Concept concept = Dictionary.getConcept(conceptIdentifier);
		List<Obs> obs = Context.getObsService().getObservationsByPersonAndConcept(patient, concept);
		if (obs.size() > 0) {
			// these are in reverse chronological order
			return obs.get(0);
		}
		return null;
	}
	
	private Obs getAllLatestObs(Patient patient, String conceptIdentifier) {
		Concept concept = Dictionary.getConcept(conceptIdentifier);
		List<Obs> obs = Context.getObsService().getObservationsByPersonAndConcept(patient, concept);
		int count=obs.size()-1;
		if (obs.size() > 0) {
			// these are in reverse chronological order
			return obs.get(count);
		}
		return null;
	}
}
