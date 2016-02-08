package org.openmrs.module.kenyaemr.fragment.controller.intake;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Encounter;
import org.openmrs.Patient;
import org.openmrs.Visit;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyacore.form.FormManager;
import org.openmrs.module.kenyaemr.EmrWebConstants;
import org.openmrs.module.kenyaui.KenyaUiUtils;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentActionRequest;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.ui.framework.resource.ResourceFactory;
import org.springframework.web.bind.annotation.RequestParam;

public class ListLabOrdersFragmentController {
	
	protected static final Log log = LogFactory.getLog(ListLabOrdersFragmentController.class);
	
	public void controller(
			@RequestParam(required = false, value = "patientId") Patient patient,
            @RequestParam(required = false, value = "returnUrl") String returnUrl,
            UiUtils ui,
            PageModel model) {
		
			log.error("patient: "+patient);
			List<Visit> visits = Context.getVisitService().getVisitsByPatient(patient, true, false);

			List<Encounter> encounters = new ArrayList<Encounter>();
			for (Visit v : visits) 	{
				for (Encounter enc : v.getEncounters()) {
					if (enc.getEncounterType().getUuid().equals(EmrWebConstants.ENCOUNTER_TYPE_LAB_ORDER_UUID)) {
						encounters.add(enc);
					}
				}
			}
			
			model.addAttribute("encounters", encounters);
	}
	
	public SimpleObject submit(
			 @SpringBean ResourceFactory resourceFactory,
			 @SpringBean KenyaUiUtils kenyaUi,
			 @SpringBean FormManager formManager,
			  UiUtils ui,
			 HttpSession session,
			 FragmentActionRequest actionRequest) throws Exception {
		
		return null;
	}
}
