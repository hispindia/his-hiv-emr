package org.openmrs.module.kenyaemr.fragment.controller.program;

import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.ui.framework.fragment.FragmentModel;

public class ArtRegisterFragmentController {
	public void controller(FragmentModel model) {
		model.addAttribute("graphingConcepts", Dictionary.getConcepts(Dictionary.WEIGHT_KG, Dictionary.CD4_COUNT, Dictionary.CD4_PERCENT, Dictionary.HIV_VIRAL_LOAD, Dictionary.CURRENT_WHO_STAGE));
	}
}
