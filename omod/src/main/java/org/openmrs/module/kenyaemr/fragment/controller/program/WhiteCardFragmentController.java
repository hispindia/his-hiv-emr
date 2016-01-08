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

		String listAllRiskFactor="";
		String iduStatusValue="";
		String iduNameValue="";
		String literate="";
		String employed="";
		String alcoholic="";
		String alcoholicType="";
		String income="";
		
		Obs riskFactor = getAllLatestObs(patient, Dictionary.HIV_RISK_FACTOR);
		if(riskFactor!=null){
			EncounterWrapper wrapped = new EncounterWrapper(riskFactor.getEncounter());
			List<Obs> obsList = wrapped.allObs(riskFactor.getConcept());
			
			for (Obs obs : obsList) {
				if(listAllRiskFactor.isEmpty()){
					listAllRiskFactor = listAllRiskFactor.concat(obs.getValueCoded().getName().toString());
				}
				else{
					listAllRiskFactor = listAllRiskFactor.concat(", "+obs.getValueCoded().getName().toString());	
				}
			}
		}
		
		model.addAttribute("listAllRiskFactor", listAllRiskFactor);
		
		Obs iduStatusObs = getAllLatestObs(patient, Dictionary.IDU_PERSONAL_HISTORY);
		if(iduStatusObs!=null){
			EncounterWrapper wrapped = new EncounterWrapper(iduStatusObs.getEncounter());
			List<Obs> obsList = wrapped.allObs(iduStatusObs.getConcept());
			for (Obs obs : obsList) {
				iduStatusValue = iduStatusValue.concat(obs.getValueCoded().getName().toString());
			}
		}
		model.addAttribute("iduStatusValue", iduStatusValue);
		
		Obs iduStatusNameObs = getAllLatestObs(patient, Dictionary.IDU_NAME_PERSONAL_HISTORY);
		if(iduStatusNameObs!=null){
			EncounterWrapper wrapped = new EncounterWrapper(iduStatusNameObs.getEncounter());
			List<Obs> obsList = wrapped.allObs(iduStatusNameObs.getConcept());
			for (Obs obs : obsList) {
				iduNameValue = iduNameValue.concat(obs.getValueCoded().getName().toString());
			}
		}
		model.addAttribute("iduNameValue", iduNameValue);

		Obs literateObs = getAllLatestObs(patient, Dictionary.LITERATE);
		if(literateObs!=null){
			EncounterWrapper wrapped = new EncounterWrapper(literateObs.getEncounter());
			List<Obs> obsList = wrapped.allObs(literateObs.getConcept());
			for (Obs obs : obsList) {
				literate = literate.concat(obs.getValueCoded().getName().toString());
			}
		}
		model.addAttribute("literate", literate);
		
		Obs employedObs = getAllLatestObs(patient, Dictionary.EMPLOYED);
		if(employedObs!=null){
			EncounterWrapper wrapped = new EncounterWrapper(employedObs.getEncounter());
			List<Obs> obsList = wrapped.allObs(employedObs.getConcept());
			for (Obs obs : obsList) {
				employed = employed.concat(obs.getValueCoded().getName().toString());
			}
		}
		model.addAttribute("employed", employed);
		
		Obs alcoholicObs = getAllLatestObs(patient, Dictionary.ALCOHOLIC);
		if(alcoholicObs!=null){
			EncounterWrapper wrapped = new EncounterWrapper(alcoholicObs.getEncounter());
			List<Obs> obsList = wrapped.allObs(alcoholicObs.getConcept());
			for (Obs obs : obsList) {
				alcoholic = alcoholic.concat(obs.getValueCoded().getName().toString());
			}
		}
		model.addAttribute("alcoholic", alcoholic);
		
		Obs alcoholicTypeObs = getAllLatestObs(patient, Dictionary.ALCOHOLIC_TYPE);
		if(alcoholicTypeObs!=null){
			EncounterWrapper wrapped = new EncounterWrapper(alcoholicTypeObs.getEncounter());
			List<Obs> obsList = wrapped.allObs(alcoholicTypeObs.getConcept());
			for (Obs obs : obsList) {
				alcoholicType = alcoholicType.concat(obs.getValueCoded().getName().toString());
			}
		}
		model.addAttribute("alcoholicType", alcoholicType);
		
		Obs incomeObs = getAllLatestObs(patient, Dictionary.PATIENT_INCOME);
		if(incomeObs!=null){
			EncounterWrapper wrapped = new EncounterWrapper(incomeObs.getEncounter());
			List<Obs> obsList = wrapped.allObs(incomeObs.getConcept());
			for (Obs obs : obsList) {
				income = obs.getValueNumeric().toString();
			}
		}
		model.addAttribute("income", income);
		
		
		/*
		 * Family History
		 * */
		
		
		
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
