package org.openmrs.module.kenyaemr.fragment.controller.program;

import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

public class WhiteCardFragmentController {
	public void controller(@RequestParam(value = "patientId", required = false) Person person,@RequestParam(value = "patientId", required = false) Patient patient,
			FragmentModel model) {

		
		model.addAttribute("patientAge",patient.getAge());
		model.addAttribute("patientGender", patient.getGender());
		model.addAttribute("patientAddress", person.getPersonAddress().getAddress1()+' '+person.getPersonAddress().getCityVillage()+' '+
											 person.getPersonAddress().getCountyDistrict()+' '+person.getPersonAddress().getStateProvince());
		
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
}
