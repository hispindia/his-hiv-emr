package org.openmrs.module.kenyaemr.fragment.controller.report;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openmrs.api.context.Context;
import org.openmrs.module.kenyaemr.api.KenyaEmrService;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

public class GetNapMonthlyReportForAdultFragmentController {
	public void controller(@RequestParam("startDate") Date startDate,
			@RequestParam("endDate") Date endDate,
			@RequestParam("ageCategory") String ageCategory,
			FragmentModel model, UiUtils ui) {
    KenyaEmrService kenyaEmrService = (KenyaEmrService) Context.getService(KenyaEmrService.class);
	model.addAttribute("patientcount",kenyaEmrService.getPatientCount());
	SimpleDateFormat formatterExt = new SimpleDateFormat("yyyy-MM-dd");
	String startOfPeriod = formatterExt.format(startDate);
	String endOfPeriod = formatterExt.format(endDate);
	model.addAttribute("noOfDeathReported",kenyaEmrService.noOfDeathReported("Female",ageCategory,startOfPeriod,endOfPeriod));
  }
}