package org.openmrs.module.kenyaemr.fragment.controller.program;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.openmrs.Patient;
import org.openmrs.module.kenyacore.UiResource;
import org.openmrs.module.kenyacore.program.ProgramDescriptor;
import org.openmrs.module.kenyacore.program.ProgramManager;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.EmrWebConstants;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;

public class WhiteCardFragmentController {
	public void controller(FragmentModel model) {
		model.addAttribute("graphingConcepts", Dictionary.getConcepts(Dictionary.WEIGHT_KG, Dictionary.CD4_COUNT, Dictionary.CD4_PERCENT, Dictionary.HIV_VIRAL_LOAD, Dictionary.CURRENT_WHO_STAGE));
	}
}
