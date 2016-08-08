package org.openmrs.module.kenyaemr.fragment.controller.report;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openmrs.Obs;
import org.openmrs.PatientProgram;
import org.openmrs.Program;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyaemr.api.KenyaEmrService;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

public class GetQuaterlyReportFragmentController {
	public void controller(@RequestParam("quaterly") String quaterly,
			FragmentModel model, UiUtils ui) {
    KenyaEmrService kenyaEmrService = (KenyaEmrService) Context.getService(KenyaEmrService.class);
	//JSONArray substituteConceptNameJsonArray = new JSONArray();
	//JSONObject conceptNameJson2 = new JSONObject();
    Date date = new Date();
	SimpleDateFormat df = new SimpleDateFormat("yyyy");
	String year = df.format(date);
	
	Program program=Context.getProgramWorkflowService().getProgramByUuid("96ec813f-aaf0-45b2-add6-e661d5bf79d6");
	
	if(quaterly!=null && quaterly.equals("First Quater")){
    	String janStartDate=year+"-"+"01"+"-"+"01";
		String janEndDate=year+"-"+"01"+"-"+"31";
		
		String febStartDate=year+"-"+"02"+"-"+"01";
		String febEndDate=year+"-"+"02"+"-"+"28";
		
		String marchStartDate=year+"-"+"03"+"-"+"01";
		String marchEndDate=year+"-"+"03"+"-"+"31";	
		
		List<PatientProgram> patientProgramForJan=kenyaEmrService.getPatientProgram(program,janStartDate,janEndDate);
		List<PatientProgram> patientProgramForFeb=kenyaEmrService.getPatientProgram(program,febStartDate,febEndDate);
		List<PatientProgram> patientProgramForMarch=kenyaEmrService.getPatientProgram(program,marchStartDate,marchEndDate);
		
		List<Obs> patientTransferInForJan=kenyaEmrService.getNoOfPatientTransferredIn(janStartDate,janEndDate);
		List<Obs> patientTransferInForFeb=kenyaEmrService.getNoOfPatientTransferredIn(febStartDate,febEndDate);
		List<Obs> patientTransferInForMarch=kenyaEmrService.getNoOfPatientTransferredIn(marchStartDate,marchEndDate);
		
		List<Obs> patientTransferOutForJan=kenyaEmrService.getNoOfPatientTransferredOut(janStartDate,janEndDate);
		List<Obs> patientTransferOutForFeb=kenyaEmrService.getNoOfPatientTransferredOut(febStartDate,febEndDate);
		List<Obs> patientTransferOutForMarch=kenyaEmrService.getNoOfPatientTransferredOut(marchStartDate,marchEndDate);
		
		model.addAttribute("patientProgramForJan",patientProgramForJan.size());
		model.addAttribute("patientProgramForFeb",patientProgramForFeb.size());
		model.addAttribute("patientProgramForMarch",patientProgramForMarch.size());
		
		model.addAttribute("patientTransferInForJan",patientTransferInForJan.size());
		model.addAttribute("patientTransferInForFeb",patientTransferInForFeb.size());
		model.addAttribute("patientTransferInForMarch",patientTransferInForMarch.size());
		
		model.addAttribute("patientTransferOutForJan",patientTransferOutForJan.size());
		model.addAttribute("patientTransferOutForFeb",patientTransferOutForFeb.size());
		model.addAttribute("patientTransferOutForMarch",patientTransferOutForMarch.size());
      }
	
	if(quaterly!=null && quaterly.equals("Second Quater")){
		String aprilStartDate=year+"-"+"04"+"-"+"01";
		String aprilEndDate=year+"-"+"04"+"-"+"30";
		
		String mayStartDate=year+"-"+"05"+"-"+"01";
		String mayEndDate=year+"-"+"05"+"-"+"31";
		
		String juneStartDate=year+"-"+"06"+"-"+"01";
		String juneEndDate=year+"-"+"06"+"-"+"30";
		
		List<PatientProgram> patientProgramForApril=kenyaEmrService.getPatientProgram(program,aprilStartDate,aprilEndDate);
		List<PatientProgram> patientProgramForMay=kenyaEmrService.getPatientProgram(program,mayStartDate,mayEndDate);
		List<PatientProgram> patientProgramForJune=kenyaEmrService.getPatientProgram(program,juneStartDate,juneEndDate);
		
		List<Obs> patientTransferInForApril=kenyaEmrService.getNoOfPatientTransferredIn(aprilStartDate,aprilEndDate);
		List<Obs> patientTransferInForMay=kenyaEmrService.getNoOfPatientTransferredIn(mayStartDate,mayEndDate);
		List<Obs> patientTransferInForJune=kenyaEmrService.getNoOfPatientTransferredIn(juneStartDate,juneEndDate);
		
		List<Obs> patientTransferOutForApril=kenyaEmrService.getNoOfPatientTransferredOut(aprilStartDate,aprilEndDate);
		List<Obs> patientTransferOutForMay=kenyaEmrService.getNoOfPatientTransferredOut(mayStartDate,mayEndDate);
		List<Obs> patientTransferOutForJune=kenyaEmrService.getNoOfPatientTransferredOut(juneStartDate,juneEndDate);
		
		model.addAttribute("patientProgramForApril",patientProgramForApril.size());
		model.addAttribute("patientProgramForMay",patientProgramForMay.size());
		model.addAttribute("patientProgramForJune",patientProgramForJune.size());
		
		model.addAttribute("patientTransferInForApril",patientTransferInForApril.size());
		model.addAttribute("patientTransferInForMay",patientTransferInForMay.size());
		model.addAttribute("patientTransferInForJune",patientTransferInForJune.size());
		
		model.addAttribute("patientTransferOutForApril",patientTransferOutForApril.size());
		model.addAttribute("patientTransferOutForMay",patientTransferOutForMay.size());
		model.addAttribute("patientTransferOutForJune",patientTransferOutForJune.size());
	}
	
    if(quaterly!=null && quaterly.equals("Third Quater")){
    	String julyStartDate=year+"-"+"07"+"-"+"01";
		String julyEndDate=year+"-"+"07"+"-"+"31";
		
		String augustStartDate=year+"-"+"08"+"-"+"01";
		String augustEndDate=year+"-"+"08"+"-"+"31";
		
		String septemberStartDate=year+"-"+"09"+"-"+"01";
		String septemberEndDate=year+"-"+"09"+"-"+"30";	
		
		List<Obs> patientTransferInForJuly=kenyaEmrService.getNoOfPatientTransferredIn(julyStartDate,julyEndDate);
		List<Obs> patientTransferInForAugust=kenyaEmrService.getNoOfPatientTransferredIn(augustStartDate,augustEndDate);
		List<Obs> patientTransferInForSeptember=kenyaEmrService.getNoOfPatientTransferredIn(septemberStartDate,septemberEndDate);
		
		List<PatientProgram> patientProgramForJuly=kenyaEmrService.getPatientProgram(program,julyStartDate,julyEndDate);
		List<PatientProgram> patientProgramForAugust=kenyaEmrService.getPatientProgram(program,augustStartDate,augustEndDate);
		List<PatientProgram> patientProgramForSeptember=kenyaEmrService.getPatientProgram(program,septemberStartDate,septemberEndDate);
		
		List<Obs> patientTransferOutForJuly=kenyaEmrService.getNoOfPatientTransferredOut(julyStartDate,julyEndDate);
		List<Obs> patientTransferOutForAugust=kenyaEmrService.getNoOfPatientTransferredOut(augustStartDate,augustEndDate);
		List<Obs> patientTransferOutForSeptember=kenyaEmrService.getNoOfPatientTransferredOut(septemberStartDate,septemberEndDate);
		
		model.addAttribute("patientProgramForJuly",patientProgramForJuly.size());
		model.addAttribute("patientProgramForAugust",patientProgramForAugust.size());
		model.addAttribute("patientProgramForSeptember",patientProgramForSeptember.size());
		
		model.addAttribute("patientTransferInForJuly",patientTransferInForJuly.size());
		model.addAttribute("patientTransferInForAugust",patientTransferInForAugust.size());
		model.addAttribute("patientTransferInForSeptember",patientTransferInForSeptember.size());
		
		model.addAttribute("patientTransferOutForJuly",patientTransferOutForJuly.size());
		model.addAttribute("patientTransferOutForAugust",patientTransferOutForAugust.size());
		model.addAttribute("patientTransferOutForSeptember",patientTransferOutForSeptember.size());
	}
    
    if(quaterly!=null && quaterly.equals("Fourth Quater")){
    	String octoberStartDate=year+"-"+"10"+"-"+"01";
		String octoberEndDate=year+"-"+"10"+"-"+"31";
		
		String novemberStartDate=year+"-"+"11"+"-"+"01";
		String novemberEndDate=year+"-"+"11"+"-"+"30";
		
		String decemberStartDate=year+"-"+"12"+"-"+"01";
		String decemberEndDate=year+"-"+"12"+"-"+"31";	
		
		List<PatientProgram> patientProgramForOctober=kenyaEmrService.getPatientProgram(program,octoberStartDate,octoberStartDate);
		List<PatientProgram> patientProgramForNovember=kenyaEmrService.getPatientProgram(program,novemberStartDate,novemberStartDate);
		List<PatientProgram> patientProgramForDecember=kenyaEmrService.getPatientProgram(program,decemberStartDate,decemberEndDate);
		
		List<Obs> patientTransferInForOctober=kenyaEmrService.getNoOfPatientTransferredIn(octoberStartDate,octoberStartDate);
		List<Obs> patientTransferInForNovember=kenyaEmrService.getNoOfPatientTransferredIn(novemberStartDate,novemberStartDate);
		List<Obs> patientTransferInForDecember=kenyaEmrService.getNoOfPatientTransferredIn(decemberStartDate,decemberEndDate);
		
		List<Obs> patientTransferOutForOctober=kenyaEmrService.getNoOfPatientTransferredOut(octoberStartDate,octoberStartDate);
		List<Obs> patientTransferOutForNovember=kenyaEmrService.getNoOfPatientTransferredOut(novemberStartDate,novemberStartDate);
		List<Obs> patientTransferOutForDecember=kenyaEmrService.getNoOfPatientTransferredOut(decemberStartDate,decemberEndDate);
		
		model.addAttribute("patientProgramForOctober",patientProgramForOctober.size());
		model.addAttribute("patientProgramForNovember",patientProgramForNovember.size());
		model.addAttribute("patientProgramForDecember",patientProgramForDecember.size());
		
		model.addAttribute("patientTransferInForOctober",patientTransferInForOctober.size());
		model.addAttribute("patientTransferInForNovember",patientTransferInForNovember.size());
		model.addAttribute("patientTransferInForDecember",patientTransferInForDecember.size());
		
		model.addAttribute("patientTransferOutForOctober",patientTransferOutForOctober.size());
		model.addAttribute("patientTransferOutForNovember",patientTransferOutForNovember.size());
		model.addAttribute("patientTransferOutForDecember",patientTransferOutForDecember.size());
    }
		
		model.addAttribute("year",year);
		model.addAttribute("quaterly",quaterly);
    }
}
