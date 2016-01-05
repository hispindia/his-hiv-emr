package org.openmrs.module.kenyaemr.fragment.controller.patient;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.openmrs.DrugOrder;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyaemr.api.KenyaEmrService;
import org.openmrs.module.kenyaemr.model.DrugOrderProcessed;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

public class DrugOrderListFragmentController {
	public void controller(@FragmentParam("patient") Patient patient,FragmentModel model) {
        List <DrugOrder> drugOrders=Context.getOrderService().getDrugOrdersByPatient(patient);
        List <DrugOrderProcessed> drugOrderProcessed=new LinkedList <DrugOrderProcessed>();
        KenyaEmrService kes = (KenyaEmrService) Context.getService(KenyaEmrService.class);
        for(DrugOrder drugOrder:drugOrders){
        	DrugOrderProcessed drugOrderProcessedd=kes.getDrugOrderProcessed(drugOrder);
        	drugOrderProcessed.add(drugOrderProcessedd);
        }
		model.addAttribute("count",1);
		model.addAttribute("drugOrderProcesseds",drugOrderProcessed);
	}
	
	public String processDrugOrder(HttpServletRequest request,@RequestParam(value = "drugOrderProcessedIds", required = false) String[] drugOrderProcessedIds,UiUtils ui) {
		for (String drugOrderProcessedId : drugOrderProcessedIds) {
			Integer drugOrderProcessId = Integer.parseInt(drugOrderProcessedId);
			String issuedQuantity = request.getParameter(drugOrderProcessedId+"issueQuantity");	
			KenyaEmrService kes = (KenyaEmrService) Context.getService(KenyaEmrService.class);
			DrugOrderProcessed drugOrderProces=kes.getDrugOrderProcesed(drugOrderProcessId);
			drugOrderProces.setProcessedStatus(true);
			drugOrderProces.setQuantityPostProcess(Integer.parseInt(issuedQuantity));
			kes.saveDrugOrderProcessed(drugOrderProces);
		}
		//return "redirect:" + ui.pageLink(EmrConstants.MODULE_ID, "dispensary/dispensing",SimpleObject.create("patientId", null));
		return null;
		
	}
}
