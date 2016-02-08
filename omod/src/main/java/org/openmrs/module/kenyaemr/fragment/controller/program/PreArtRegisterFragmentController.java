package org.openmrs.module.kenyaemr.fragment.controller.program;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.Metadata;
import org.openmrs.module.kenyaemr.wrapper.EncounterWrapper;
import org.openmrs.module.kenyaemr.wrapper.PatientWrapper;
import org.openmrs.module.kenyaemr.wrapper.PersonWrapper;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

public class PreArtRegisterFragmentController {
	public void controller(
			@RequestParam(value = "patientId", required = false) Person person,
			@RequestParam(value = "patientId", required = false) Patient patient,
			FragmentModel model) {
		/*
		 * Constant value across all visit
		 */
		
		model.addAttribute("patientName", person.getGivenName());
		model.addAttribute("patientAge", person.getAge());
		model.addAttribute("birthDate", new SimpleDateFormat("dd-MMMM-yyyy")
				.format(person.getBirthdate()));
		model.addAttribute("patientGender", person.getGender());
		model.addAttribute("address", person.getPersonAddress());

		PatientWrapper wrapperPatient = new PatientWrapper(patient);
		PersonWrapper wrapperPerson = new PersonWrapper(person);

		model.addAttribute("patientWrap", wrapperPatient);
		model.addAttribute("personWrap", wrapperPerson);
				
		Obs enrollmentStatus = getLatestObs(patient,
				Dictionary.ENROLLMENT_STATUS);
		model.addAttribute("enrollmentStatus", enrollmentStatus);


		
		/*
		 * Obstetric History
		 * */
		String pregStatusVal = "";
		String eddVal = "";
		String ancNumberVal = "";
		
		Obs pregStatus = getLatestObs(patient, Dictionary.PREGNANCY_STATUS);
		if (pregStatus != null) {
				pregStatusVal = pregStatus.getValueCoded().getName().toString();
		}
		model.addAttribute("pregStatusVal", pregStatusVal);
		
		Obs edd = getLatestObs(patient, Dictionary.EXPECTED_DATE_OF_DELIVERY);
		if (edd != null) {
			eddVal = new SimpleDateFormat("dd-MMMM-yyyy").format(edd.getValueDate());
		}
		model.addAttribute("eddVal", eddVal);
		
		Obs ancNumber = getLatestObs(patient, Dictionary.ANTENATAL_CASE_NUMBER);
		if (ancNumber != null) {
			ancNumberVal = ancNumber.getValueNumeric().toString();
		}
		model.addAttribute("ancNumberVal", ancNumberVal);
		
		
		
		Obs obstetricHistoryDetail = getAllLatestObs(patient,
				Dictionary.OBSTETRIC_HIS_DETAIL);
		Obs infantName = getAllLatestObs(patient, Dictionary.INFANT_NAME);

		Map<Integer, String> infantList = new HashMap<Integer, String>();
		Integer infantIndex = 0;
		if (obstetricHistoryDetail != null) {
			EncounterWrapper wrappedObsGroup = new EncounterWrapper(
					obstetricHistoryDetail.getEncounter());
			List<Obs> obsGroupList = wrappedObsGroup
					.allObs(obstetricHistoryDetail.getConcept());
			for (Obs obsG : obsGroupList) {
				String infantNameVal = "";

				if (infantName != null) {
					EncounterWrapper wrapped = new EncounterWrapper(
							infantName.getEncounter());
					List<Obs> obsList = wrapped.allObs(infantName.getConcept());
					for (Obs obs : obsList) {
						if (obs.getObsGroupId() == obsG.getObsId()) {
							infantNameVal = infantNameVal.concat(obs
									.getValueText().toString());
						}
					}
				}

				String val = infantNameVal ;
				infantList.put(infantIndex, val);
				infantIndex++;

			}
		}
		model.addAttribute("infantList", infantList);
		
		/*
		 * Personal History
		 */

		String listAllRiskFactor = "";

		Obs riskFactor = getAllLatestObs(patient, Dictionary.HIV_RISK_FACTOR);
		if (riskFactor != null) {
			EncounterWrapper wrapped = new EncounterWrapper(
					riskFactor.getEncounter());
			List<Obs> obsList = wrapped.allObs(riskFactor.getConcept());

			for (Obs obs : obsList) {
				if (listAllRiskFactor.isEmpty()) {
					listAllRiskFactor = listAllRiskFactor.concat(obs
							.getValueCoded().getName().toString());
				} else {
					listAllRiskFactor = listAllRiskFactor.concat(", "
							+ obs.getValueCoded().getName().toString());
				}
			}
		}

		model.addAttribute("listAllRiskFactor", listAllRiskFactor);
		
		/*
		 * Encounter details
		 * */
		Date dateArt = new Date();
		List<Encounter> artEncounters = Context.getEncounterService().getEncounters(patient);
		for (Encounter en : artEncounters) {
			if(en.getEncounterType().getUuid().equals("0cb4417d-b98d-4265-92aa-c6ee3d3bb317")){
				if(dateArt.after(en.getEncounterDatetime())){
					dateArt =  en.getEncounterDatetime();
				}
			}
		}
		model.addAttribute("artInitiationDate",new SimpleDateFormat("dd-MMMM-yyyy").format(dateArt));
		
		Date dateArtEligible = new Date();
		List<Encounter> artEligibleEncounters = Context.getEncounterService().getEncounters(patient);
		for (Encounter en : artEligibleEncounters) {
			if(en.getEncounterType().getUuid().equals("de78a6be-bfc5-4634-adc3-5f1a280455cc")){
				if(dateArtEligible.after(en.getEncounterDatetime())){
					dateArtEligible =  en.getEncounterDatetime();
				}
			}
		}
		model.addAttribute("artEligibleDate",new SimpleDateFormat("dd-MMMM-yyyy").format(dateArtEligible));
		
		model.addAttribute("graphingConcepts", Dictionary.getConcepts(Dictionary.TUBERCULOSIS_TREATMENT_NUMBER, Dictionary.TUBERCULOSIS_DRUG_TREATMENT_START_DATE, Dictionary.CURRENT_WHO_STAGE));
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
	
}