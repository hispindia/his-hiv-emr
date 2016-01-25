package org.openmrs.module.kenyaemr.page.controller.intake;

import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Visit;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyaemr.api.KenyaEmrService;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

public class EnterLabResultMainPageController {
	protected static final Log log = LogFactory.getLog(EnterLabResultMainPageController.class);
	
	public void controller(@RequestParam(required = false, value = "visitId") Visit visit,
            @RequestParam(required = false, value = "formUuid") String formUuid,
            @RequestParam("returnUrl") String returnUrl,
            UiUtils ui,
            PageModel model) {
		
		Encounter enc = Context.getService(KenyaEmrService.class).getLabbOrderEncounter(visit);
		
		Set<Obs> listObs  = null;
		if (enc != null) {
			 listObs = enc.getAllObs();
			if (listObs != null) {
				model.addAttribute("listObs", listObs);
			}
		}
		model.addAttribute("listObs", listObs);
		 model.addAttribute("visit", visit);
		 model.addAttribute("returnUrl", returnUrl);
	}
	
	
	
}
