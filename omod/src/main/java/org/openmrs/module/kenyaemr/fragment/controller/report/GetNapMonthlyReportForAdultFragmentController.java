package org.openmrs.module.kenyaemr.fragment.controller.report;

import org.openmrs.api.context.Context;
import org.openmrs.module.kenyaemr.api.KenyaEmrService;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

public class GetNapMonthlyReportForAdultFragmentController {
	public void controller(@RequestParam("year") String year,
			FragmentModel model, UiUtils ui) {
    KenyaEmrService kenyaEmrService = (KenyaEmrService) Context.getService(KenyaEmrService.class);
	model.addAttribute("patientcount",kenyaEmrService.getPatientCount());
	String gender="F";
	String ageCategory=">14";
	String startOfPeriod="2017-01-01";
	String endOfPeriod="2017-01-31";
	model.addAttribute("noOfDeathReported",kenyaEmrService.noOfDeathReported(gender,ageCategory,startOfPeriod,endOfPeriod));
  }
}