package org.openmrs.module.kenyaemr.fragment.controller.intake;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.ConceptComplex;
import org.openmrs.ConceptNumeric;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.Visit;
import org.openmrs.api.APIException;
import org.openmrs.api.EncounterService;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyacore.form.FormManager;
import org.openmrs.module.kenyaemr.EmrWebConstants;
import org.openmrs.module.kenyaemr.api.KenyaEmrService;
import org.openmrs.module.kenyaui.KenyaUiUtils;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentActionRequest;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.ui.framework.resource.ResourceFactory;
import org.openmrs.validator.ValidateUtil;
import org.springframework.web.bind.annotation.RequestParam;

public class EnterLabResultFragmentController {
	
	protected static final Log log = LogFactory.getLog(EnterLabResultFragmentController.class);
	
	public void controller(
			@RequestParam(required = false, value = "encounterId") Encounter encounter,
            @RequestParam(required = false, value = "returnUrl") String returnUrl,
            UiUtils ui,
            PageModel model) {
		
		Set<Obs> listResultObs = null;
		Encounter resultEncounter = null;
		Visit visit = encounter.getVisit();
		if (visit != null) {
			EncounterType encType = Context.getEncounterService().getEncounterType("Lab Results");
			List<Encounter> encs = Context.getEncounterService().getEncounters(visit.getPatient(), null, null, null, null, Collections.singleton(encType), null, null, Collections.singleton(visit), false);
			if (encs != null && !encs.isEmpty()) {
				resultEncounter = encs.get(encs.size() - 1);
			}
			
			if (resultEncounter != null) {
				 listResultObs = resultEncounter.getAllObs();
			}
		}
		model.addAttribute("resultEncounter", resultEncounter);
		model.addAttribute("listResultObs", listResultObs);
		model.addAttribute("visit", visit);
		model.addAttribute("returnUrl", returnUrl);
		 
		List<TestObject> listTests = null;
		if (encounter != null) {
			listTests = new ArrayList<EnterLabResultFragmentController.TestObject>();
			for (Obs obs : encounter.getAllObs()) {
				TestObject test = new TestObject(obs,visit.getPatient());
				if (resultEncounter != null) {
					test.setResult(listResultObs);
				}
				listTests.add(test);
			}
		}
		model.addAttribute("listTests", listTests);
		model.addAttribute("confirmed", resultEncounter != null && resultEncounter.getDateChanged() != null ? true: false);
		
		
	}
	
	public SimpleObject submit(
			 @RequestParam(value = "visitId", required = false) Visit visit,
			 @RequestParam(value="conceptIds", required = true) String[] conceptIds,
			 @RequestParam(value = "encounterId", required = false) Encounter resultEncounter,
			 @RequestParam(value = "confirmed", required = false) Boolean confirm,
			 @SpringBean ResourceFactory resourceFactory,
			 @SpringBean KenyaUiUtils kenyaUi,
			 @SpringBean FormManager formManager,
			  UiUtils ui,
			 HttpSession session,
			 FragmentActionRequest actionRequest) throws Exception {
		EncounterService encService = Context.getService(EncounterService.class);
		
		Date curDate = new Date();

		if (resultEncounter != null) {
			// edit
			Set<Obs> listObs = resultEncounter.getAllObs();
			boolean changed = false;
			boolean isNewObs = true;
			for (String submittedConceptId : conceptIds) {
				isNewObs = true;
				String isRadiology = actionRequest.getParameter(submittedConceptId+"_isRadiology");
				String value = "";
				if ("true".equals(isRadiology)) {
					value = actionRequest.getParameter(submittedConceptId+"_valueFinding") + "|" + actionRequest.getParameter(submittedConceptId+"_valueImpression") ;
				} else {
					value = actionRequest.getParameter(submittedConceptId+"_value");
				}
				Concept concept = Context.getConceptService().getConcept(NumberUtils.toInt(submittedConceptId));
				for (Obs obs : listObs) {
					if (obs.getConcept().getConceptId().equals(concept.getConceptId())) {
						// edit existed Obs
						isNewObs = false;
						if (obs.getValueText() != null && !obs.getValueText().equalsIgnoreCase(value) ) {
						// value has been changed
							obs.setValueText(value);
							obs.setDateChanged(curDate);
							changed = true;
						}
					} 
				}
				if (isNewObs) {
					// save new Obs
					Obs newObs = new Obs();
					newObs.setConcept(concept);
					newObs.setValueText(value);
					newObs.setEncounter(resultEncounter);
					newObs.setDateCreated(curDate);
					resultEncounter.addObs(newObs);
					changed = true;
				}
			} // end loop for conceptIds 
			if (changed) {
				if (confirm) {
					resultEncounter.setDateChanged(curDate);
				}
				resultEncounter.setEncounterDatetime(curDate);
				try {
					ValidateUtil.validate(resultEncounter);
				} catch (APIException e) {
					e.printStackTrace();
					kenyaUi.notifyError(session, e.getMessage());
					return SimpleObject.create("success", false, "errors", e.getMessage());
				}
				Context.getEncounterService().saveEncounter(resultEncounter);
			}
		} else {
			// save new
			
			
			Encounter encounter = new Encounter();
			encounter.setDateCreated(curDate);
			encounter.setEncounterType(encService.getEncounterType("Lab Results"));
			encounter.setVisit(visit);
			encounter.setPatient(visit.getPatient());
			encounter.setEncounterDatetime(curDate);
			if (confirm) {
				encounter.setDateChanged(curDate);
			}
			
			for (String conceptId : conceptIds) {
				String value = actionRequest.getParameter(conceptId+"_value");	
				Concept concept = Context.getConceptService().getConcept(conceptId);
				
				Obs obs = new Obs();
				obs.setConcept(concept);
				obs.setEncounter(encounter);
				obs.setDateCreated(curDate);
				String isRadiology = actionRequest.getParameter(conceptId+"_isRadiology");
				if ("true".equals(isRadiology)) {
					StringBuffer text = new StringBuffer();
					text.append(actionRequest.getParameter(conceptId+"_valueFinding"));
					text.append("|");
					text.append(actionRequest.getParameter(conceptId+"_valueImpression"));
					obs.setValueText(text.toString());
				} else {
					obs.setValueText(value);
				}
				encounter.addObs(obs);
			}
			try {
				ValidateUtil.validate(encounter);
			} catch (APIException e) {
				e.printStackTrace();
				kenyaUi.notifyError(session, e.getMessage());
				return SimpleObject.create("success", false, "errors", e.getMessage());
			}
			encounter.setChangedBy(Context.getUserContext().getAuthenticatedUser());
			
			encService.saveEncounter(encounter);
		}

		return SimpleObject.create("success",true);
	}
	
	public class TestObject {
		public Concept concept;
		public Integer conceptId;
		public String name;
		public String units = "";
		public String range = "";
		public String comment = "";
		public String handlerKey;
		public String resultFinding = ""; // finding
		public String resultImpression = ""; // impression
		public String result = "";
		public Obs obs;
		public boolean isRadioloy = false;

		public TestObject (Obs obs,Patient patient) {
			this.obs = obs;
			this.concept = obs.getValueCoded();
			this.conceptId = concept.getConceptId();
			 if (concept.getDatatype().isNumeric()) {
				ConceptNumeric cn = Context.getConceptService().getConceptNumeric(conceptId);;
				this.units = cn.getUnits();
				range = calculateRange(patient.getGender(), cn);
			 } else if (concept.isComplex()) {
				ConceptComplex complex = (ConceptComplex) concept;
				this.handlerKey = complex.getHandler();
			 } 
			 name = concept.getName().getName();
			 
			 Concept radiologyParentConcept = Context.getConceptService().getConceptByUuid(EmrWebConstants.RADIOLOGY_PARENT_CONCEPT_UUID);
			 for (ConceptAnswer con : radiologyParentConcept.getAnswers()) {
				 if (con.getAnswerConcept().getConceptId().equals(concept.getConceptId())) {
					 isRadioloy = true;
					 break;
				 }
			 }
		}
		
		public void setResult(Set<Obs> listResultObs) {
			for (Obs resultObs : listResultObs) {
				if (resultObs.getConcept().getConceptId().equals(concept.getConceptId())) {
					if (isRadioloy) {
						String text = resultObs.getValueText();
						if (text != null) {
							String[] arr = StringUtils.split(text, '|');
							resultFinding = arr[0];
							resultImpression = arr[1];
						}
					} else {
						result = resultObs.getValueText();
					}
					break;
				}
			}
		}
		
		public String calculateRange(String gender, ConceptNumeric concept) {
			
			if (gender.equalsIgnoreCase("male")) {
				return "" + concept.getLowCritical() + "-" + concept.getHiCritical();
			} else {
				return "" + concept.getLowNormal() + "-" + concept.getHiNormal();
			}
			
		}
		
		
	}
	
}
