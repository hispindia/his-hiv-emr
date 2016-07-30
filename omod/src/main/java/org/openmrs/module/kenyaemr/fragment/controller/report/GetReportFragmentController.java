package org.openmrs.module.kenyaemr.fragment.controller.report;

import java.util.List;

import org.openmrs.PatientProgram;
import org.openmrs.Program;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyaemr.api.KenyaEmrService;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

public class GetReportFragmentController {
	public void controller(@RequestParam("year") String year,
			FragmentModel model, UiUtils ui) {
    KenyaEmrService kenyaEmrService = (KenyaEmrService) Context.getService(KenyaEmrService.class);
	//JSONArray substituteConceptNameJsonArray = new JSONArray();
	//JSONObject conceptNameJson2 = new JSONObject();
	Program program=Context.getProgramWorkflowService().getProgramByUuid("96ec813f-aaf0-45b2-add6-e661d5bf79d6");
	
	if(year!=null){
		String janStartDate=year+"-"+"01"+"-"+"01";
		String janEndDate=year+"-"+"01"+"-"+"31";
		List<PatientProgram> patientProgramForJan=kenyaEmrService.getPatientProgram(program,janStartDate,janEndDate);
		
		String febStartDate=year+"-"+"02"+"-"+"01";
		String febEndDate=year+"-"+"02"+"-"+"28";
		List<PatientProgram> patientProgramForFeb=kenyaEmrService.getPatientProgram(program,febStartDate,febEndDate);
		
		String marchStartDate=year+"-"+"03"+"-"+"01";
		String marchEndDate=year+"-"+"03"+"-"+"31";
		List<PatientProgram> patientProgramForMarch=kenyaEmrService.getPatientProgram(program,marchStartDate,marchEndDate);
		
		String aprilStartDate=year+"-"+"04"+"-"+"01";
		String aprilEndDate=year+"-"+"04"+"-"+"30";
		List<PatientProgram> patientProgramForApril=kenyaEmrService.getPatientProgram(program,aprilStartDate,aprilEndDate);
		
		String mayStartDate=year+"-"+"05"+"-"+"01";
		String mayEndDate=year+"-"+"05"+"-"+"31";
		List<PatientProgram> patientProgramForMay=kenyaEmrService.getPatientProgram(program,mayStartDate,mayEndDate);
		
		String juneStartDate=year+"-"+"06"+"-"+"01";
		String juneEndDate=year+"-"+"06"+"-"+"30";
		List<PatientProgram> patientProgramForJune=kenyaEmrService.getPatientProgram(program,juneStartDate,juneEndDate);
		
		String julyStartDate=year+"-"+"07"+"-"+"01";
		String julyEndDate=year+"-"+"07"+"-"+"31";
		List<PatientProgram> patientProgramForJuly=kenyaEmrService.getPatientProgram(program,julyStartDate,julyEndDate);
		
		String augustStartDate=year+"-"+"08"+"-"+"01";
		String augustEndDate=year+"-"+"08"+"-"+"31";
		List<PatientProgram> patientProgramForAugust=kenyaEmrService.getPatientProgram(program,augustStartDate,augustEndDate);
		
		String septemberStartDate=year+"-"+"09"+"-"+"01";
		String septemberEndDate=year+"-"+"09"+"-"+"30";
		List<PatientProgram> patientProgramForSeptember=kenyaEmrService.getPatientProgram(program,janStartDate,janEndDate);
		
		String octoberStartDate=year+"-"+"10"+"-"+"01";
		String octoberEndDate=year+"-"+"10"+"-"+"31";
		List<PatientProgram> patientProgramForOctober=kenyaEmrService.getPatientProgram(program,febStartDate,febEndDate);
		
		String novemberStartDate=year+"-"+"11"+"-"+"01";
		String novemberEndDate=year+"-"+"11"+"-"+"30";
		List<PatientProgram> patientProgramForNovember=kenyaEmrService.getPatientProgram(program,janStartDate,janEndDate);
		
		String decemberStartDate=year+"-"+"12"+"-"+"01";
		String decemberEndDate=year+"-"+"12"+"-"+"31";
		List<PatientProgram> patientProgramForDecember=kenyaEmrService.getPatientProgram(program,febStartDate,febEndDate);
		
		model.addAttribute("year",year);
		model.addAttribute("patientProgramForJan",patientProgramForJan.size());
		model.addAttribute("patientProgramForFeb",patientProgramForFeb.size());
		model.addAttribute("patientProgramForMarch",patientProgramForMarch.size());
		model.addAttribute("patientProgramForApril",patientProgramForApril.size());
		model.addAttribute("patientProgramForMay",patientProgramForMay.size());
		model.addAttribute("patientProgramForJune",patientProgramForJune.size());
		model.addAttribute("patientProgramForJuly",patientProgramForJuly.size());
		model.addAttribute("patientProgramForAugust",patientProgramForAugust.size());
		model.addAttribute("patientProgramForSeptember",patientProgramForSeptember.size());
		model.addAttribute("patientProgramForOctober",patientProgramForOctober.size());
		model.addAttribute("patientProgramForNovember",patientProgramForNovember.size());
		model.addAttribute("patientProgramForDecember",patientProgramForDecember.size());
	}
	}
}
