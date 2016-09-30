package org.openmrs.module.kenyaemr.fragment.controller.report;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PatientProgram;
import org.openmrs.Program;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyaemr.api.KenyaEmrService;
import org.openmrs.module.kenyaemr.model.DrugOrderProcessed;
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
		
		Integer totalCohortForJan=kenyaEmrService.getTotalNoOfCohort(janStartDate, janEndDate);
		Integer totalCohortForFeb=kenyaEmrService.getTotalNoOfCohort(febStartDate,febEndDate);
		Integer totalCohortForMarch=kenyaEmrService.getTotalNoOfCohort(marchStartDate,marchEndDate);
		
		Integer maleCohortForJan=kenyaEmrService.getCohortBasedOnGender("M",janStartDate, janEndDate);
		Integer maleCohortForFeb=kenyaEmrService.getCohortBasedOnGender("M",febStartDate,febEndDate);
		Integer maleCohortForMarch=kenyaEmrService.getCohortBasedOnGender("M",marchStartDate,marchEndDate);
		
		Integer femaleCohortForJan=kenyaEmrService.getCohortBasedOnGender("F",janStartDate, janEndDate);
		Integer femaleCohortForFeb=kenyaEmrService.getCohortBasedOnGender("F",febStartDate,febEndDate);
		Integer femaleCohortForMarch=kenyaEmrService.getCohortBasedOnGender("F",marchStartDate,marchEndDate);
		
		Integer age1=0;
		Integer age2=14;
		Integer cohortFor0_14AgeForJan=kenyaEmrService.getCohortBasedOnAge(age1,age2,janStartDate, janEndDate);
		Integer cohortFor0_14AgeForFeb=kenyaEmrService.getCohortBasedOnAge(age1,age2,febStartDate,febEndDate);
		Integer cohortFor0_14AgeForMarch=kenyaEmrService.getCohortBasedOnAge(age1,age2,marchStartDate,marchEndDate);
		
		age1=15;
		age2=24;
		Integer cohortFor15_24AgeForJan=kenyaEmrService.getCohortBasedOnAge(age1,age2,janStartDate, janEndDate);
		Integer cohortFor15_24AgeForFeb=kenyaEmrService.getCohortBasedOnAge(age1,age2,febStartDate,febEndDate);
		Integer cohortFor15_24AgeForMarch=kenyaEmrService.getCohortBasedOnAge(age1,age2,marchStartDate,marchEndDate);
		
		age1=25;
		age2=60;
		Integer cohortFor25_60AgeForJan=kenyaEmrService.getCohortBasedOnAge(age1,age2,janStartDate, janEndDate);
		Integer cohortFor25_60AgeForFeb=kenyaEmrService.getCohortBasedOnAge(age1,age2,febStartDate,febEndDate);
		Integer cohortFor25_60AgeForMarch=kenyaEmrService.getCohortBasedOnAge(age1,age2,marchStartDate,marchEndDate);
		
		Integer noOfCohortAliveAndOnArtForJan=kenyaEmrService.getNoOfCohortAliveAndOnArt(program,janStartDate,janEndDate);
		Integer noOfCohortAliveAndOnArtForFeb=kenyaEmrService.getNoOfCohortAliveAndOnArt(program,febStartDate,febEndDate);
		Integer noOfCohortAliveAndOnArtForMarch=kenyaEmrService.getNoOfCohortAliveAndOnArt(program,marchStartDate,marchEndDate);
		
		Set<Patient> noOfOriginalFirstLineRegimenForJan=kenyaEmrService.getOriginalFirstLineRegimen(janStartDate,janEndDate);
		Set<Patient> noOfOriginalFirstLineRegimenForFeb=kenyaEmrService.getOriginalFirstLineRegimen(febStartDate,febEndDate);
		Set<Patient> noOfOriginalFirstLineRegimenForMarch=kenyaEmrService.getOriginalFirstLineRegimen(marchStartDate,marchEndDate);
		
		Set<Patient> noOfAlternateFirstLineRegimenForJan=kenyaEmrService.getAlternateFirstLineRegimen(janStartDate,janEndDate);
		Set<Patient> noOfAlternateFirstLineRegimenForFeb=kenyaEmrService.getAlternateFirstLineRegimen(febStartDate,febEndDate);
		Set<Patient> noOfAlternateFirstLineRegimenForMarch=kenyaEmrService.getAlternateFirstLineRegimen(marchStartDate,marchEndDate);
		
		Set<Patient> noOfSecondLineRegimenForJan=kenyaEmrService.getSecondLineRegimen(janStartDate,janEndDate);
		Set<Patient> noOfSecondLineRegimenForFeb=kenyaEmrService.getSecondLineRegimen(febStartDate,febEndDate);
		Set<Patient> noOfSecondLineRegimenForMarch=kenyaEmrService.getSecondLineRegimen(marchStartDate,marchEndDate);
		
		List<PatientProgram> noOfArtStoppedCohortForJan=kenyaEmrService.getNoOfArtStoppedCohort(program,janStartDate,janEndDate);
		List<PatientProgram> noOfArtStoppedCohortForFeb=kenyaEmrService.getNoOfArtStoppedCohort(program,febStartDate,febEndDate);
		List<PatientProgram> noOfArtStoppedCohortForMarch=kenyaEmrService.getNoOfArtStoppedCohort(program,marchStartDate,marchEndDate);
		
		List<PatientProgram> noOfArtDiedCohortForJan=kenyaEmrService.getNoOfArtDiedCohort(program,janStartDate,janEndDate);
		List<PatientProgram> noOfArtDiedCohortForFeb=kenyaEmrService.getNoOfArtDiedCohort(program,febStartDate,febEndDate);
		List<PatientProgram> noOfArtDiedCohortForMarch=kenyaEmrService.getNoOfArtDiedCohort(program,marchStartDate,marchEndDate);
		
		List<Obs> noOfPatientLostToFollowUpForJan=kenyaEmrService.getNoOfPatientLostToFollowUp(janStartDate,janEndDate);
		List<Obs> noOfPatientLostToFollowUpForFeb=kenyaEmrService.getNoOfPatientLostToFollowUp(febStartDate,febEndDate);
		List<Obs> noOfPatientLostToFollowUpForMarch=kenyaEmrService.getNoOfPatientLostToFollowUp(marchStartDate,marchEndDate);
		
		List<Obs> noOfPatientWithCD4ForJan=kenyaEmrService.getNoOfPatientWithCD4(janStartDate,janEndDate);
		List<Obs> noOfPatientWithCD4ForFeb=kenyaEmrService.getNoOfPatientWithCD4(febStartDate,febEndDate);
		List<Obs> noOfPatientWithCD4ForMarch=kenyaEmrService.getNoOfPatientWithCD4(marchStartDate,marchEndDate);
		
		List<Obs> noOfPatientNormalActivityForJan=kenyaEmrService.getNoOfPatientNormalActivity(janStartDate,janEndDate);
		List<Obs> noOfPatientNormalActivityForFeb=kenyaEmrService.getNoOfPatientNormalActivity(febStartDate,febEndDate);
		List<Obs> noOfPatientNormalActivityForMarch=kenyaEmrService.getNoOfPatientNormalActivity(marchStartDate,marchEndDate);
		
		List<Obs> noOfPatientBedriddenLessThanFiftyForJan=kenyaEmrService.getNoOfPatientBedriddenLessThanFifty(janStartDate,janEndDate);
		List<Obs> noOfPatientBedriddenLessThanFiftyForFeb=kenyaEmrService.getNoOfPatientBedriddenLessThanFifty(febStartDate,febEndDate);
		List<Obs> noOfPatientBedriddenLessThanFiftyForMarch=kenyaEmrService.getNoOfPatientBedriddenLessThanFifty(marchStartDate,marchEndDate);
		
		List<Obs> noOfPatientBedriddenMoreThanFiftyForJan=kenyaEmrService.getNoOfPatientBedriddenMoreThanFifty(janStartDate,janEndDate);
		List<Obs> noOfPatientBedriddenMoreThanFiftyForFeb=kenyaEmrService.getNoOfPatientBedriddenMoreThanFifty(febStartDate,febEndDate);
		List<Obs> noOfPatientBedriddenMoreThanFiftyForMarch=kenyaEmrService.getNoOfPatientBedriddenMoreThanFifty(marchStartDate,marchEndDate);
		
		List<Obs> noOfPatientPickedUpArvForSixMonthForJan=kenyaEmrService.getNoOfPatientPickedUpArvForSixMonth(janStartDate,janEndDate);
		List<Obs> noOfPatientPickedUpArvForSixMonthForFeb=kenyaEmrService.getNoOfPatientPickedUpArvForSixMonth(febStartDate,febEndDate);
		List<Obs> noOfPatientPickedUpArvForSixMonthForMarch=kenyaEmrService.getNoOfPatientPickedUpArvForSixMonth(marchStartDate,marchEndDate);
		
		List<Obs> noOfPatientPickedUpArvForTwelveMonthForJan=kenyaEmrService.getNoOfPatientPickedUpArvForTwelveMonth(janStartDate,janEndDate);
		List<Obs> noOfPatientPickedUpArvForTwelveMonthForFeb=kenyaEmrService.getNoOfPatientPickedUpArvForTwelveMonth(febStartDate,febEndDate);
		List<Obs> noOfPatientPickedUpArvForTwelveMonthForMarch=kenyaEmrService.getNoOfPatientPickedUpArvForTwelveMonth(marchStartDate,marchEndDate);
		
		model.addAttribute("patientProgramForJan",patientProgramForJan.size());
		model.addAttribute("patientProgramForFeb",patientProgramForFeb.size());
		model.addAttribute("patientProgramForMarch",patientProgramForMarch.size());
		
		model.addAttribute("patientTransferInForJan",patientTransferInForJan.size());
		model.addAttribute("patientTransferInForFeb",patientTransferInForFeb.size());
		model.addAttribute("patientTransferInForMarch",patientTransferInForMarch.size());
		
		model.addAttribute("patientTransferOutForJan",patientTransferOutForJan.size());
		model.addAttribute("patientTransferOutForFeb",patientTransferOutForFeb.size());
		model.addAttribute("patientTransferOutForMarch",patientTransferOutForMarch.size());
		
		model.addAttribute("totalCohortForJan",totalCohortForJan);
		model.addAttribute("totalCohortForFeb",totalCohortForFeb);
		model.addAttribute("totalCohortForMarch",totalCohortForMarch);
		
		model.addAttribute("maleCohortForJan",maleCohortForJan);
		model.addAttribute("maleCohortForFeb",maleCohortForFeb);
		model.addAttribute("maleCohortForMarch",maleCohortForMarch);
		
		model.addAttribute("femaleCohortForJan",femaleCohortForJan);
		model.addAttribute("femaleCohortForFeb",femaleCohortForFeb);
		model.addAttribute("femaleCohortForMarch",femaleCohortForMarch);
		
		model.addAttribute("cohortFor0_14AgeForJan",cohortFor0_14AgeForJan);
		model.addAttribute("cohortFor0_14AgeForFeb",cohortFor0_14AgeForFeb);
		model.addAttribute("cohortFor0_14AgeForMarch",cohortFor0_14AgeForMarch);
		
		model.addAttribute("cohortFor15_24AgeForJan",cohortFor15_24AgeForJan);
		model.addAttribute("cohortFor15_24AgeForFeb",cohortFor15_24AgeForFeb);
		model.addAttribute("cohortFor15_24AgeForMarch",cohortFor15_24AgeForMarch);
		
		model.addAttribute("cohortFor25_60AgeForJan",cohortFor25_60AgeForJan);
		model.addAttribute("cohortFor25_60AgeForFeb",cohortFor25_60AgeForFeb);
		model.addAttribute("cohortFor25_60AgeForMarch",cohortFor25_60AgeForMarch);
		
		model.addAttribute("noOfCohortAliveAndOnArtForJan",noOfCohortAliveAndOnArtForJan);
		model.addAttribute("noOfCohortAliveAndOnArtForFeb",noOfCohortAliveAndOnArtForFeb);
		model.addAttribute("noOfCohortAliveAndOnArtForMarch",noOfCohortAliveAndOnArtForMarch);
		
		model.addAttribute("noOfOriginalFirstLineRegimenForJan",noOfOriginalFirstLineRegimenForJan.size());
		model.addAttribute("noOfOriginalFirstLineRegimenForFeb",noOfOriginalFirstLineRegimenForFeb.size());
		model.addAttribute("noOfOriginalFirstLineRegimenForMarch",noOfOriginalFirstLineRegimenForMarch.size());
		
		model.addAttribute("noOfAlternateFirstLineRegimenForJan",noOfAlternateFirstLineRegimenForJan.size());
		model.addAttribute("noOfAlternateFirstLineRegimenForFeb",noOfAlternateFirstLineRegimenForFeb.size());
		model.addAttribute("noOfAlternateFirstLineRegimenForMarch",noOfAlternateFirstLineRegimenForMarch.size());
		
		model.addAttribute("noOfSecondLineRegimenForJan",noOfSecondLineRegimenForJan.size());
		model.addAttribute("noOfSecondLineRegimenForFeb",noOfSecondLineRegimenForFeb.size());
		model.addAttribute("noOfSecondLineRegimenForMarch",noOfSecondLineRegimenForMarch.size());
		
		model.addAttribute("noOfArtStoppedCohortForJan",noOfArtStoppedCohortForJan.size());
		model.addAttribute("noOfArtStoppedCohortForFeb",noOfArtStoppedCohortForFeb.size());
		model.addAttribute("noOfArtStoppedCohortForMarch",noOfArtStoppedCohortForMarch.size());
		
		model.addAttribute("noOfArtDiedCohortForJan",noOfArtDiedCohortForJan.size());
		model.addAttribute("noOfArtDiedCohortForFeb",noOfArtDiedCohortForFeb.size());
		model.addAttribute("noOfArtDiedCohortForMarch",noOfArtDiedCohortForMarch.size());
		
		model.addAttribute("noOfPatientLostToFollowUpForJan",noOfPatientLostToFollowUpForJan.size());
		model.addAttribute("noOfPatientLostToFollowUpForFeb",noOfPatientLostToFollowUpForFeb.size());
		model.addAttribute("noOfPatientLostToFollowUpForMarch",noOfPatientLostToFollowUpForMarch.size());
		
		model.addAttribute("noOfPatientWithCD4ForJan",noOfPatientWithCD4ForJan.size());
		model.addAttribute("noOfPatientWithCD4ForFeb",noOfPatientWithCD4ForFeb.size());
		model.addAttribute("noOfPatientWithCD4ForMarch",noOfPatientWithCD4ForMarch.size());
		
		model.addAttribute("noOfPatientNormalActivityForJan",noOfPatientNormalActivityForJan.size());
		model.addAttribute("noOfPatientNormalActivityForFeb",noOfPatientNormalActivityForFeb.size());
		model.addAttribute("noOfPatientNormalActivityForMarch",noOfPatientNormalActivityForMarch.size());
		
		model.addAttribute("noOfPatientBedriddenLessThanFiftyForJan",noOfPatientBedriddenLessThanFiftyForJan.size());
		model.addAttribute("noOfPatientBedriddenLessThanFiftyForFeb",noOfPatientBedriddenLessThanFiftyForFeb.size());
		model.addAttribute("noOfPatientBedriddenLessThanFiftyForMarch",noOfPatientBedriddenLessThanFiftyForMarch.size());
		
		model.addAttribute("noOfPatientBedriddenMoreThanFiftyForJan",noOfPatientBedriddenMoreThanFiftyForJan.size());
		model.addAttribute("noOfPatientBedriddenMoreThanFiftyForFeb",noOfPatientBedriddenMoreThanFiftyForFeb.size());
		model.addAttribute("noOfPatientBedriddenMoreThanFiftyForMarch",noOfPatientBedriddenMoreThanFiftyForMarch.size());
		
		model.addAttribute("noOfPatientPickedUpArvForSixMonthForJan",noOfPatientPickedUpArvForSixMonthForJan.size());
		model.addAttribute("noOfPatientPickedUpArvForSixMonthForFeb",noOfPatientPickedUpArvForSixMonthForFeb.size());
		model.addAttribute("noOfPatientPickedUpArvForSixMonthForMarch",noOfPatientPickedUpArvForSixMonthForMarch.size());
		
		model.addAttribute("noOfPatientPickedUpArvForTwelveMonthForJan",noOfPatientPickedUpArvForTwelveMonthForJan.size());
		model.addAttribute("noOfPatientPickedUpArvForTwelveMonthForFeb",noOfPatientPickedUpArvForTwelveMonthForFeb.size());
		model.addAttribute("noOfPatientPickedUpArvForTwelveMonthForMarch",noOfPatientPickedUpArvForTwelveMonthForMarch.size());
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
		
		Integer totalCohortForApril=kenyaEmrService.getTotalNoOfCohort(aprilStartDate,aprilEndDate);
		Integer totalCohortForMay=kenyaEmrService.getTotalNoOfCohort(mayStartDate,mayEndDate);
		Integer totalCohortForJune=kenyaEmrService.getTotalNoOfCohort(juneStartDate,juneEndDate);
		
		Integer maleCohortForApril=kenyaEmrService.getCohortBasedOnGender("M",aprilStartDate,aprilEndDate);
		Integer maleCohortForMay=kenyaEmrService.getCohortBasedOnGender("M",mayStartDate,mayEndDate);
		Integer maleCohortForJune=kenyaEmrService.getCohortBasedOnGender("M",juneStartDate,juneEndDate);
		
		Integer femaleCohortForApril=kenyaEmrService.getCohortBasedOnGender("F",aprilStartDate,aprilEndDate);
		Integer femaleCohortForMay=kenyaEmrService.getCohortBasedOnGender("F",mayStartDate,mayEndDate);
		Integer femaleCohortForJune=kenyaEmrService.getCohortBasedOnGender("F",juneStartDate,juneEndDate);
		
		Integer age1=0;
		Integer age2=14;
		Integer cohortFor0_14AgeForApril=kenyaEmrService.getCohortBasedOnAge(age1,age2,aprilStartDate,aprilEndDate);
		Integer cohortFor0_14AgeForMay=kenyaEmrService.getCohortBasedOnAge(age1,age2,mayStartDate,mayEndDate);
		Integer cohortFor0_14AgeForJune=kenyaEmrService.getCohortBasedOnAge(age1,age2,juneStartDate,juneEndDate);
		
		age1=15;
		age2=24;
		Integer cohortFor15_24AgeForApril=kenyaEmrService.getCohortBasedOnAge(age1,age2,aprilStartDate,aprilEndDate);
		Integer cohortFor15_24AgeForMay=kenyaEmrService.getCohortBasedOnAge(age1,age2,mayStartDate,mayEndDate);
		Integer cohortFor15_24AgeForJune=kenyaEmrService.getCohortBasedOnAge(age1,age2,juneStartDate,juneEndDate);
		
		age1=25;
		age2=60;
		Integer cohortFor25_60AgeForApril=kenyaEmrService.getCohortBasedOnAge(age1,age2,aprilStartDate,aprilEndDate);
		Integer cohortFor25_60AgeForMay=kenyaEmrService.getCohortBasedOnAge(age1,age2,mayStartDate,mayEndDate);
		Integer cohortFor25_60AgeForJune=kenyaEmrService.getCohortBasedOnAge(age1,age2,juneStartDate,juneEndDate);
		
		Integer noOfCohortAliveAndOnArtForApril=kenyaEmrService.getNoOfCohortAliveAndOnArt(program,aprilStartDate,aprilEndDate);
		Integer noOfCohortAliveAndOnArtForMay=kenyaEmrService.getNoOfCohortAliveAndOnArt(program,mayStartDate,mayEndDate);
		Integer noOfCohortAliveAndOnArtForJune=kenyaEmrService.getNoOfCohortAliveAndOnArt(program,juneStartDate,juneEndDate);
		
		Set<Patient> noOfOriginalFirstLineRegimenForApril=kenyaEmrService.getOriginalFirstLineRegimen(aprilStartDate,aprilEndDate);
		Set<Patient> noOfOriginalFirstLineRegimenForMay=kenyaEmrService.getOriginalFirstLineRegimen(mayStartDate,mayEndDate);
		Set<Patient> noOfOriginalFirstLineRegimenForJune=kenyaEmrService.getOriginalFirstLineRegimen(juneStartDate,juneEndDate);
		
		Set<Patient> noOfAlternateFirstLineRegimenForApril=kenyaEmrService.getAlternateFirstLineRegimen(aprilStartDate,aprilEndDate);
		Set<Patient> noOfAlternateFirstLineRegimenForMay=kenyaEmrService.getAlternateFirstLineRegimen(mayStartDate,mayEndDate);
		Set<Patient> noOfAlternateFirstLineRegimenForJune=kenyaEmrService.getAlternateFirstLineRegimen(juneStartDate,juneEndDate);
		
		Set<Patient> noOfSecondLineRegimenForApril=kenyaEmrService.getSecondLineRegimen(aprilStartDate,aprilEndDate);
		Set<Patient> noOfSecondLineRegimenForMay=kenyaEmrService.getSecondLineRegimen(mayStartDate,mayEndDate);
		Set<Patient> noOfSecondLineRegimenForJune=kenyaEmrService.getSecondLineRegimen(juneStartDate,juneEndDate);
		
		List<PatientProgram> noOfArtStoppedCohortForApril=kenyaEmrService.getNoOfArtStoppedCohort(program,aprilStartDate,aprilEndDate);
		List<PatientProgram> noOfArtStoppedCohortForMay=kenyaEmrService.getNoOfArtStoppedCohort(program,mayStartDate,mayEndDate);
		List<PatientProgram> noOfArtStoppedCohortForJune=kenyaEmrService.getNoOfArtStoppedCohort(program,juneStartDate,juneEndDate);
		
		List<PatientProgram> noOfArtDiedCohortForApril=kenyaEmrService.getNoOfArtDiedCohort(program,aprilStartDate,aprilEndDate);
		List<PatientProgram> noOfArtDiedCohortForMay=kenyaEmrService.getNoOfArtDiedCohort(program,mayStartDate,mayEndDate);
		List<PatientProgram> noOfArtDiedCohortForJune=kenyaEmrService.getNoOfArtDiedCohort(program,juneStartDate,juneEndDate);
		
		List<Obs> noOfPatientLostToFollowUpForApril=kenyaEmrService.getNoOfPatientLostToFollowUp(aprilStartDate,aprilEndDate);
		List<Obs> noOfPatientLostToFollowUpForMay=kenyaEmrService.getNoOfPatientLostToFollowUp(mayStartDate,mayEndDate);
		List<Obs> noOfPatientLostToFollowUpForJune=kenyaEmrService.getNoOfPatientLostToFollowUp(juneStartDate,juneEndDate);
		
		List<Obs> noOfPatientWithCD4ForApril=kenyaEmrService.getNoOfPatientWithCD4(aprilStartDate,aprilEndDate);
		List<Obs> noOfPatientWithCD4ForMay=kenyaEmrService.getNoOfPatientWithCD4(mayStartDate,mayEndDate);
		List<Obs> noOfPatientWithCD4ForJune=kenyaEmrService.getNoOfPatientWithCD4(juneStartDate,juneEndDate);
		
		List<Obs> noOfPatientNormalActivityForApril=kenyaEmrService.getNoOfPatientNormalActivity(aprilStartDate,aprilEndDate);
		List<Obs> noOfPatientNormalActivityForMay=kenyaEmrService.getNoOfPatientNormalActivity(mayStartDate,mayEndDate);
		List<Obs> noOfPatientNormalActivityForJune=kenyaEmrService.getNoOfPatientNormalActivity(juneStartDate,juneEndDate);
		
		List<Obs> noOfPatientBedriddenLessThanFiftyForApril=kenyaEmrService.getNoOfPatientBedriddenLessThanFifty(aprilStartDate,aprilEndDate);
		List<Obs> noOfPatientBedriddenLessThanFiftyForMay=kenyaEmrService.getNoOfPatientBedriddenLessThanFifty(mayStartDate,mayEndDate);
		List<Obs> noOfPatientBedriddenLessThanFiftyForJune=kenyaEmrService.getNoOfPatientBedriddenLessThanFifty(juneStartDate,juneEndDate);
		
		List<Obs> noOfPatientBedriddenMoreThanFiftyForApril=kenyaEmrService.getNoOfPatientBedriddenMoreThanFifty(aprilStartDate,aprilEndDate);
		List<Obs> noOfPatientBedriddenMoreThanFiftyForMay=kenyaEmrService.getNoOfPatientBedriddenMoreThanFifty(mayStartDate,mayEndDate);
		List<Obs> noOfPatientBedriddenMoreThanFiftyForJune=kenyaEmrService.getNoOfPatientBedriddenMoreThanFifty(juneStartDate,juneEndDate);
		
		List<Obs> noOfPatientPickedUpArvForSixMonthForApril=kenyaEmrService.getNoOfPatientPickedUpArvForSixMonth(aprilStartDate,aprilEndDate);
		List<Obs> noOfPatientPickedUpArvForSixMonthForMay=kenyaEmrService.getNoOfPatientPickedUpArvForSixMonth(mayStartDate,mayEndDate);
		List<Obs> noOfPatientPickedUpArvForSixMonthForJune=kenyaEmrService.getNoOfPatientPickedUpArvForSixMonth(juneStartDate,juneEndDate);
		
		List<Obs> noOfPatientPickedUpArvForTwelveMonthForApril=kenyaEmrService.getNoOfPatientPickedUpArvForTwelveMonth(aprilStartDate,aprilEndDate);
		List<Obs> noOfPatientPickedUpArvForTwelveMonthForMay=kenyaEmrService.getNoOfPatientPickedUpArvForTwelveMonth(mayStartDate,mayEndDate);
		List<Obs> noOfPatientPickedUpArvForTwelveMonthForJune=kenyaEmrService.getNoOfPatientPickedUpArvForTwelveMonth(juneStartDate,juneEndDate);
		
		model.addAttribute("patientProgramForApril",patientProgramForApril.size());
		model.addAttribute("patientProgramForMay",patientProgramForMay.size());
		model.addAttribute("patientProgramForJune",patientProgramForJune.size());
		
		model.addAttribute("patientTransferInForApril",patientTransferInForApril.size());
		model.addAttribute("patientTransferInForMay",patientTransferInForMay.size());
		model.addAttribute("patientTransferInForJune",patientTransferInForJune.size());
		
		model.addAttribute("patientTransferOutForApril",patientTransferOutForApril.size());
		model.addAttribute("patientTransferOutForMay",patientTransferOutForMay.size());
		model.addAttribute("patientTransferOutForJune",patientTransferOutForJune.size());
		
		model.addAttribute("totalCohortForApril",totalCohortForApril);
		model.addAttribute("totalCohortForMay",totalCohortForMay);
		model.addAttribute("totalCohortForJune",totalCohortForJune);
		
		model.addAttribute("maleCohortForApril",maleCohortForApril);
		model.addAttribute("maleCohortForMay",maleCohortForMay);
		model.addAttribute("maleCohortForJune",maleCohortForJune);

		model.addAttribute("femaleCohortForApril",femaleCohortForApril);
		model.addAttribute("femaleCohortForMay",femaleCohortForMay);
		model.addAttribute("femaleCohortForJune",femaleCohortForJune);
		
		model.addAttribute("cohortFor0_14AgeForApril",cohortFor0_14AgeForApril);
		model.addAttribute("cohortFor0_14AgeForMay",cohortFor0_14AgeForMay);
		model.addAttribute("cohortFor0_14AgeForJune",cohortFor0_14AgeForJune);
		
		model.addAttribute("cohortFor15_24AgeForApril",cohortFor15_24AgeForApril);
		model.addAttribute("cohortFor15_24AgeForMay",cohortFor15_24AgeForMay);
		model.addAttribute("cohortFor15_24AgeForJune",cohortFor15_24AgeForJune);
		
		model.addAttribute("cohortFor25_60AgeForApril",cohortFor25_60AgeForApril);
		model.addAttribute("cohortFor25_60AgeForMay",cohortFor25_60AgeForMay);
		model.addAttribute("cohortFor25_60AgeForJune",cohortFor25_60AgeForJune);
		
		model.addAttribute("noOfCohortAliveAndOnArtForApril",noOfCohortAliveAndOnArtForApril);
		model.addAttribute("noOfCohortAliveAndOnArtForMay",noOfCohortAliveAndOnArtForMay);
		model.addAttribute("noOfCohortAliveAndOnArtForJune",noOfCohortAliveAndOnArtForJune);
		
		model.addAttribute("noOfOriginalFirstLineRegimenForApril",noOfOriginalFirstLineRegimenForApril.size());
		model.addAttribute("noOfOriginalFirstLineRegimenForMay",noOfOriginalFirstLineRegimenForMay.size());
		model.addAttribute("noOfOriginalFirstLineRegimenForJune",noOfOriginalFirstLineRegimenForJune.size());
		
		model.addAttribute("noOfAlternateFirstLineRegimenForApril",noOfAlternateFirstLineRegimenForApril.size());
		model.addAttribute("noOfAlternateFirstLineRegimenForMay",noOfAlternateFirstLineRegimenForMay.size());
		model.addAttribute("noOfAlternateFirstLineRegimenForJune",noOfAlternateFirstLineRegimenForJune.size());
		
		model.addAttribute("noOfSecondLineRegimenForApril",noOfSecondLineRegimenForApril.size());
		model.addAttribute("noOfSecondLineRegimenForMay",noOfSecondLineRegimenForMay.size());
		model.addAttribute("noOfSecondLineRegimenForJune",noOfSecondLineRegimenForJune.size());
		
		model.addAttribute("noOfArtStoppedCohortForApril",noOfArtStoppedCohortForApril.size());
		model.addAttribute("noOfArtStoppedCohortForMay",noOfArtStoppedCohortForMay.size());
		model.addAttribute("noOfArtStoppedCohortForJune",noOfArtStoppedCohortForJune.size());
		
		model.addAttribute("noOfArtDiedCohortForApril",noOfArtDiedCohortForApril.size());
		model.addAttribute("noOfArtDiedCohortForMay",noOfArtDiedCohortForMay.size());
		model.addAttribute("noOfArtDiedCohortForJune",noOfArtDiedCohortForJune.size());
		
		model.addAttribute("noOfPatientLostToFollowUpForApril",noOfPatientLostToFollowUpForApril.size());
		model.addAttribute("noOfPatientLostToFollowUpForMay",noOfPatientLostToFollowUpForMay.size());
		model.addAttribute("noOfPatientLostToFollowUpForJune",noOfPatientLostToFollowUpForJune.size());
		
		model.addAttribute("noOfPatientWithCD4ForApril",noOfPatientWithCD4ForApril.size());
		model.addAttribute("noOfPatientWithCD4ForMay",noOfPatientWithCD4ForMay.size());
		model.addAttribute("noOfPatientWithCD4ForJune",noOfPatientWithCD4ForJune.size());
		
		model.addAttribute("noOfPatientNormalActivityForApril",noOfPatientNormalActivityForApril.size());
		model.addAttribute("noOfPatientNormalActivityForMay",noOfPatientNormalActivityForMay.size());
		model.addAttribute("noOfPatientNormalActivityForJune",noOfPatientNormalActivityForJune.size());
		
		model.addAttribute("noOfPatientBedriddenLessThanFiftyForApril",noOfPatientBedriddenLessThanFiftyForApril.size());
		model.addAttribute("noOfPatientBedriddenLessThanFiftyForMay",noOfPatientBedriddenLessThanFiftyForMay.size());
		model.addAttribute("noOfPatientBedriddenLessThanFiftyForJune",noOfPatientBedriddenLessThanFiftyForJune.size());
		
		model.addAttribute("noOfPatientBedriddenMoreThanFiftyForApril",noOfPatientBedriddenMoreThanFiftyForApril.size());
		model.addAttribute("noOfPatientBedriddenMoreThanFiftyForMay",noOfPatientBedriddenMoreThanFiftyForMay.size());
		model.addAttribute("noOfPatientBedriddenMoreThanFiftyForJune",noOfPatientBedriddenMoreThanFiftyForJune.size());
		
		model.addAttribute("noOfPatientPickedUpArvForSixMonthForApril",noOfPatientPickedUpArvForSixMonthForApril.size());
		model.addAttribute("noOfPatientPickedUpArvForSixMonthForMay",noOfPatientPickedUpArvForSixMonthForMay.size());
		model.addAttribute("noOfPatientPickedUpArvForSixMonthForJune",noOfPatientPickedUpArvForSixMonthForJune.size());
		
		model.addAttribute("noOfPatientPickedUpArvForTwelveMonthForApril",noOfPatientPickedUpArvForTwelveMonthForApril.size());
		model.addAttribute("noOfPatientPickedUpArvForTwelveMonthForMay",noOfPatientPickedUpArvForTwelveMonthForMay.size());
		model.addAttribute("noOfPatientPickedUpArvForTwelveMonthForJune",noOfPatientPickedUpArvForTwelveMonthForJune.size());
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
		
		Integer totalCohortForJuly=kenyaEmrService.getTotalNoOfCohort(julyStartDate,julyEndDate);
		Integer totalCohortForAugust=kenyaEmrService.getTotalNoOfCohort(augustStartDate,augustEndDate);
		Integer totalCohortForSeptember=kenyaEmrService.getTotalNoOfCohort(septemberStartDate,septemberEndDate);
		
		Integer maleCohortForJuly=kenyaEmrService.getCohortBasedOnGender("M",julyStartDate,julyEndDate);
		Integer maleCohortForAugust=kenyaEmrService.getCohortBasedOnGender("M",augustStartDate,augustEndDate);
		Integer maleCohortForSeptember=kenyaEmrService.getCohortBasedOnGender("M",septemberStartDate,septemberEndDate);
		
		Integer femaleCohortForJuly=kenyaEmrService.getCohortBasedOnGender("F",julyStartDate,julyEndDate);
		Integer femaleCohortForAugust=kenyaEmrService.getCohortBasedOnGender("F",augustStartDate,augustEndDate);
		Integer femaleCohortForSeptember=kenyaEmrService.getCohortBasedOnGender("F",septemberStartDate,septemberEndDate);
		
		Integer age1=0;
		Integer age2=14;
		Integer cohortFor0_14AgeForJuly=kenyaEmrService.getCohortBasedOnAge(age1,age2,julyStartDate,julyEndDate);
		Integer cohortFor0_14AgeForAugust=kenyaEmrService.getCohortBasedOnAge(age1,age2,augustStartDate,augustEndDate);
		Integer cohortFor0_14AgeForSeptember=kenyaEmrService.getCohortBasedOnAge(age1,age2,septemberStartDate,septemberEndDate);
		
		age1=15;
		age2=24;
		Integer cohortFor15_24AgeForJuly=kenyaEmrService.getCohortBasedOnAge(age1,age2,julyStartDate,julyEndDate);
		Integer cohortFor15_24AgeForAugust=kenyaEmrService.getCohortBasedOnAge(age1,age2,augustStartDate,augustEndDate);
		Integer cohortFor15_24AgeForSeptember=kenyaEmrService.getCohortBasedOnAge(age1,age2,septemberStartDate,septemberEndDate);
		
		age1=25;
		age2=60;
		Integer cohortFor25_60AgeForJuly=kenyaEmrService.getCohortBasedOnAge(age1,age2,julyStartDate,julyEndDate);
		Integer cohortFor25_60AgeForAugust=kenyaEmrService.getCohortBasedOnAge(age1,age2,augustStartDate,augustEndDate);
		Integer cohortFor25_60AgeForSeptember=kenyaEmrService.getCohortBasedOnAge(age1,age2,septemberStartDate,septemberEndDate);
		
		Integer noOfCohortAliveAndOnArtForJuly=kenyaEmrService.getNoOfCohortAliveAndOnArt(program,julyStartDate,julyEndDate);
		Integer noOfCohortAliveAndOnArtForAugust=kenyaEmrService.getNoOfCohortAliveAndOnArt(program,augustStartDate,augustEndDate);
		Integer noOfCohortAliveAndOnArtForSeptember=kenyaEmrService.getNoOfCohortAliveAndOnArt(program,septemberStartDate,septemberEndDate);
		
		Set<Patient> noOfOriginalFirstLineRegimenForJuly=kenyaEmrService.getOriginalFirstLineRegimen(julyStartDate,julyEndDate);
		Set<Patient> noOfOriginalFirstLineRegimenForAugust=kenyaEmrService.getOriginalFirstLineRegimen(augustStartDate,augustEndDate);
		Set<Patient> noOfOriginalFirstLineRegimenForSeptember=kenyaEmrService.getOriginalFirstLineRegimen(septemberStartDate,septemberEndDate);
		
		Set<Patient> noOfAlternateFirstLineRegimenForJuly=kenyaEmrService.getAlternateFirstLineRegimen(julyStartDate,julyEndDate);
		Set<Patient> noOfAlternateFirstLineRegimenForAugust=kenyaEmrService.getAlternateFirstLineRegimen(augustStartDate,augustEndDate);
		Set<Patient> noOfAlternateFirstLineRegimenForSeptember=kenyaEmrService.getAlternateFirstLineRegimen(septemberStartDate,septemberEndDate);
		
		Set<Patient> noOfSecondLineRegimenForJuly=kenyaEmrService.getSecondLineRegimen(julyStartDate,julyEndDate);
		Set<Patient> noOfSecondLineRegimenForAugust=kenyaEmrService.getSecondLineRegimen(augustStartDate,augustEndDate);
		Set<Patient> noOfSecondLineRegimenForSeptember=kenyaEmrService.getSecondLineRegimen(septemberStartDate,septemberEndDate);
		
		List<PatientProgram> noOfArtStoppedCohortForJuly=kenyaEmrService.getNoOfArtStoppedCohort(program,julyStartDate,julyEndDate);
		List<PatientProgram> noOfArtStoppedCohortForAugust=kenyaEmrService.getNoOfArtStoppedCohort(program,augustStartDate,augustEndDate);
		List<PatientProgram> noOfArtStoppedCohortForSeptember=kenyaEmrService.getNoOfArtStoppedCohort(program,septemberStartDate,septemberEndDate);
		
		List<PatientProgram> noOfArtDiedCohortForJuly=kenyaEmrService.getNoOfArtDiedCohort(program,julyStartDate,julyEndDate);
		List<PatientProgram> noOfArtDiedCohortForAugust=kenyaEmrService.getNoOfArtDiedCohort(program,augustStartDate,augustEndDate);
		List<PatientProgram> noOfArtDiedCohortForSeptember=kenyaEmrService.getNoOfArtDiedCohort(program,septemberStartDate,septemberEndDate);
		
		List<Obs> noOfPatientLostToFollowUpForJuly=kenyaEmrService.getNoOfPatientLostToFollowUp(julyStartDate,julyEndDate);
		List<Obs> noOfPatientLostToFollowUpForAugust=kenyaEmrService.getNoOfPatientLostToFollowUp(augustStartDate,augustEndDate);
		List<Obs> noOfPatientLostToFollowUpForSeptember=kenyaEmrService.getNoOfPatientLostToFollowUp(septemberStartDate,septemberEndDate);
		
		List<Obs> noOfPatientWithCD4ForJuly=kenyaEmrService.getNoOfPatientWithCD4(julyStartDate,julyEndDate);
		List<Obs> noOfPatientWithCD4ForAugust=kenyaEmrService.getNoOfPatientWithCD4(augustStartDate,augustEndDate);
		List<Obs> noOfPatientWithCD4ForSeptember=kenyaEmrService.getNoOfPatientWithCD4(septemberStartDate,septemberEndDate);
		
		List<Obs> noOfPatientNormalActivityForJuly=kenyaEmrService.getNoOfPatientNormalActivity(julyStartDate,julyEndDate);
		List<Obs> noOfPatientNormalActivityForAugust=kenyaEmrService.getNoOfPatientNormalActivity(augustStartDate,augustEndDate);
		List<Obs> noOfPatientNormalActivityForSeptember=kenyaEmrService.getNoOfPatientNormalActivity(septemberStartDate,septemberEndDate);
		
		List<Obs> noOfPatientBedriddenLessThanFiftyForJuly=kenyaEmrService.getNoOfPatientBedriddenLessThanFifty(julyStartDate,julyEndDate);
		List<Obs> noOfPatientBedriddenLessThanFiftyForAugust=kenyaEmrService.getNoOfPatientBedriddenLessThanFifty(augustStartDate,augustEndDate);
		List<Obs> noOfPatientBedriddenLessThanFiftyForSeptember=kenyaEmrService.getNoOfPatientBedriddenLessThanFifty(septemberStartDate,septemberEndDate);
		
		List<Obs> noOfPatientBedriddenMoreThanFiftyForJuly=kenyaEmrService.getNoOfPatientBedriddenMoreThanFifty(julyStartDate,julyEndDate);
		List<Obs> noOfPatientBedriddenMoreThanFiftyForAugust=kenyaEmrService.getNoOfPatientBedriddenMoreThanFifty(augustStartDate,augustEndDate);
		List<Obs> noOfPatientBedriddenMoreThanFiftyForSeptember=kenyaEmrService.getNoOfPatientBedriddenMoreThanFifty(septemberStartDate,septemberEndDate);
		
		List<Obs> noOfPatientPickedUpArvForSixMonthForJuly=kenyaEmrService.getNoOfPatientPickedUpArvForSixMonth(julyStartDate,julyEndDate);
		List<Obs> noOfPatientPickedUpArvForSixMonthForAugust=kenyaEmrService.getNoOfPatientPickedUpArvForSixMonth(augustStartDate,augustEndDate);
		List<Obs> noOfPatientPickedUpArvForSixMonthForSeptember=kenyaEmrService.getNoOfPatientPickedUpArvForSixMonth(septemberStartDate,septemberEndDate);
		
		List<Obs> noOfPatientPickedUpArvForTwelveMonthForJuly=kenyaEmrService.getNoOfPatientPickedUpArvForTwelveMonth(julyStartDate,julyEndDate);
		List<Obs> noOfPatientPickedUpArvForTwelveMonthForAugust=kenyaEmrService.getNoOfPatientPickedUpArvForTwelveMonth(augustStartDate,augustEndDate);
		List<Obs> noOfPatientPickedUpArvForTwelveMonthForSeptember=kenyaEmrService.getNoOfPatientPickedUpArvForTwelveMonth(septemberStartDate,septemberEndDate);
		
		model.addAttribute("patientProgramForJuly",patientProgramForJuly.size());
		model.addAttribute("patientProgramForAugust",patientProgramForAugust.size());
		model.addAttribute("patientProgramForSeptember",patientProgramForSeptember.size());
		
		model.addAttribute("patientTransferInForJuly",patientTransferInForJuly.size());
		model.addAttribute("patientTransferInForAugust",patientTransferInForAugust.size());
		model.addAttribute("patientTransferInForSeptember",patientTransferInForSeptember.size());
		
		model.addAttribute("patientTransferOutForJuly",patientTransferOutForJuly.size());
		model.addAttribute("patientTransferOutForAugust",patientTransferOutForAugust.size());
		model.addAttribute("patientTransferOutForSeptember",patientTransferOutForSeptember.size());
		
		model.addAttribute("totalCohortForJuly",totalCohortForJuly);
		model.addAttribute("totalCohortForAugust",totalCohortForAugust);
		model.addAttribute("totalCohortForSeptember",totalCohortForSeptember);
		
		model.addAttribute("maleCohortForJuly",maleCohortForJuly);
		model.addAttribute("maleCohortForAugust",maleCohortForAugust);
		model.addAttribute("maleCohortForSeptember",maleCohortForSeptember);
		
		model.addAttribute("femaleCohortForJuly",femaleCohortForJuly);
		model.addAttribute("femaleCohortForAugust",femaleCohortForAugust);
		model.addAttribute("femaleCohortForSeptember",femaleCohortForSeptember);
	
		model.addAttribute("cohortFor0_14AgeForJuly",cohortFor0_14AgeForJuly);
		model.addAttribute("cohortFor0_14AgeForAugust",cohortFor0_14AgeForAugust);
		model.addAttribute("cohortFor0_14AgeForSeptember",cohortFor0_14AgeForSeptember);
		
		model.addAttribute("cohortFor15_24AgeForJuly",cohortFor15_24AgeForJuly);
		model.addAttribute("cohortFor15_24AgeForAugust",cohortFor15_24AgeForAugust);
		model.addAttribute("cohortFor15_24AgeForSeptember",cohortFor15_24AgeForSeptember);
		
		model.addAttribute("cohortFor25_60AgeForJuly",cohortFor25_60AgeForJuly);
		model.addAttribute("cohortFor25_60AgeForAugust",cohortFor25_60AgeForAugust);
		model.addAttribute("cohortFor25_60AgeForSeptember",cohortFor25_60AgeForSeptember);
		
		model.addAttribute("noOfCohortAliveAndOnArtForJuly",noOfCohortAliveAndOnArtForJuly);
		model.addAttribute("noOfCohortAliveAndOnArtForAugust",noOfCohortAliveAndOnArtForAugust);
		model.addAttribute("noOfCohortAliveAndOnArtForSeptember",noOfCohortAliveAndOnArtForSeptember);
		
		model.addAttribute("noOfOriginalFirstLineRegimenForJuly",noOfOriginalFirstLineRegimenForJuly.size());
		model.addAttribute("noOfOriginalFirstLineRegimenForAugust",noOfOriginalFirstLineRegimenForAugust.size());
		model.addAttribute("noOfOriginalFirstLineRegimenForSeptember",noOfOriginalFirstLineRegimenForSeptember.size());
		
		model.addAttribute("noOfAlternateFirstLineRegimenForJuly",noOfAlternateFirstLineRegimenForJuly.size());
		model.addAttribute("noOfAlternateFirstLineRegimenForAugust",noOfAlternateFirstLineRegimenForAugust.size());
		model.addAttribute("noOfAlternateFirstLineRegimenForSeptember",noOfAlternateFirstLineRegimenForSeptember.size());
		
		model.addAttribute("noOfSecondLineRegimenForJuly",noOfSecondLineRegimenForJuly.size());
		model.addAttribute("noOfSecondLineRegimenForAugust",noOfSecondLineRegimenForAugust.size());
		model.addAttribute("noOfSecondLineRegimenForSeptember",noOfSecondLineRegimenForSeptember.size());
		
		model.addAttribute("noOfArtStoppedCohortForJuly",noOfArtStoppedCohortForJuly.size());
		model.addAttribute("noOfArtStoppedCohortForAugust",noOfArtStoppedCohortForAugust.size());
		model.addAttribute("noOfArtStoppedCohortForSeptember",noOfArtStoppedCohortForSeptember.size());
		
		model.addAttribute("noOfArtDiedCohortForJuly",noOfArtDiedCohortForJuly.size());
		model.addAttribute("noOfArtDiedCohortForAugust",noOfArtDiedCohortForAugust.size());
		model.addAttribute("noOfArtDiedCohortForSeptember",noOfArtDiedCohortForSeptember.size());
		
		model.addAttribute("noOfPatientLostToFollowUpForJuly",noOfPatientLostToFollowUpForJuly.size());
		model.addAttribute("noOfPatientLostToFollowUpForAugust",noOfPatientLostToFollowUpForAugust.size());
		model.addAttribute("noOfPatientLostToFollowUpForSeptember",noOfPatientLostToFollowUpForSeptember.size());
		
		model.addAttribute("noOfPatientWithCD4ForJuly",noOfPatientWithCD4ForJuly.size());
		model.addAttribute("noOfPatientWithCD4ForAugust",noOfPatientWithCD4ForAugust.size());
		model.addAttribute("noOfPatientWithCD4ForSeptember",noOfPatientWithCD4ForSeptember.size());
		
		model.addAttribute("noOfPatientNormalActivityForJuly",noOfPatientNormalActivityForJuly.size());
		model.addAttribute("noOfPatientNormalActivityForAugust",noOfPatientNormalActivityForAugust.size());
		model.addAttribute("noOfPatientNormalActivityForSeptember",noOfPatientNormalActivityForSeptember.size());
		
		model.addAttribute("noOfPatientBedriddenLessThanFiftyForJuly",noOfPatientBedriddenLessThanFiftyForJuly.size());
		model.addAttribute("noOfPatientBedriddenLessThanFiftyForAugust",noOfPatientBedriddenLessThanFiftyForAugust.size());
		model.addAttribute("noOfPatientBedriddenLessThanFiftyForSeptember",noOfPatientBedriddenLessThanFiftyForSeptember.size());
		
		model.addAttribute("noOfPatientBedriddenMoreThanFiftyForJuly",noOfPatientBedriddenMoreThanFiftyForJuly.size());
		model.addAttribute("noOfPatientBedriddenMoreThanFiftyForAugust",noOfPatientBedriddenMoreThanFiftyForAugust.size());
		model.addAttribute("noOfPatientBedriddenMoreThanFiftyForSeptember",noOfPatientBedriddenMoreThanFiftyForSeptember.size());
		
		model.addAttribute("noOfPatientPickedUpArvForSixMonthForJuly",noOfPatientPickedUpArvForSixMonthForJuly.size());
		model.addAttribute("noOfPatientPickedUpArvForSixMonthForAugust",noOfPatientPickedUpArvForSixMonthForAugust.size());
		model.addAttribute("noOfPatientPickedUpArvForSixMonthForSeptember",noOfPatientPickedUpArvForSixMonthForSeptember.size());
		
		model.addAttribute("noOfPatientPickedUpArvForTwelveMonthForJuly",noOfPatientPickedUpArvForTwelveMonthForJuly.size());
		model.addAttribute("noOfPatientPickedUpArvForTwelveMonthForAugust",noOfPatientPickedUpArvForTwelveMonthForAugust.size());
		model.addAttribute("noOfPatientPickedUpArvForTwelveMonthForSeptember",noOfPatientPickedUpArvForTwelveMonthForSeptember.size());
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
		
		Integer totalCohortForOctober=kenyaEmrService.getTotalNoOfCohort(octoberStartDate,octoberStartDate);
		Integer totalCohortForNovember=kenyaEmrService.getTotalNoOfCohort(novemberStartDate,novemberStartDate);
		Integer totalCohortForDecember=kenyaEmrService.getTotalNoOfCohort(decemberStartDate,decemberEndDate);
		
		Integer maleCohortForOctober=kenyaEmrService.getCohortBasedOnGender("M",octoberStartDate,octoberStartDate);
		Integer maleCohortForNovember=kenyaEmrService.getCohortBasedOnGender("M",novemberStartDate,novemberStartDate);
		Integer maleCohortForDecember=kenyaEmrService.getCohortBasedOnGender("M",decemberStartDate,decemberEndDate);
		
		Integer femaleCohortForOctober=kenyaEmrService.getCohortBasedOnGender("F",octoberStartDate,octoberStartDate);
		Integer femaleCohortForNovember=kenyaEmrService.getCohortBasedOnGender("F",novemberStartDate,novemberStartDate);
		Integer femaleCohortForDecember=kenyaEmrService.getCohortBasedOnGender("F",decemberStartDate,decemberEndDate);
		
		Integer age1=0;
		Integer age2=14;
		Integer cohortFor0_14AgeForOctober=kenyaEmrService.getCohortBasedOnAge(age1,age2,octoberStartDate,octoberStartDate);
		Integer cohortFor0_14AgeForNovember=kenyaEmrService.getCohortBasedOnAge(age1,age2,novemberStartDate,novemberStartDate);
		Integer cohortFor0_14AgeForDecember=kenyaEmrService.getCohortBasedOnAge(age1,age2,decemberStartDate,decemberEndDate);
		
		age1=15;
		age2=24;
		Integer cohortFor15_24AgeForOctober=kenyaEmrService.getCohortBasedOnAge(age1,age2,octoberStartDate,octoberStartDate);
		Integer cohortFor15_24AgeForNovember=kenyaEmrService.getCohortBasedOnAge(age1,age2,novemberStartDate,novemberStartDate);
		Integer cohortFor15_24AgeForDecember=kenyaEmrService.getCohortBasedOnAge(age1,age2,decemberStartDate,decemberEndDate);
		
		age1=25;
		age2=60;
		Integer cohortFor25_60AgeForOctober=kenyaEmrService.getCohortBasedOnAge(age1,age2,octoberStartDate,octoberStartDate);
		Integer cohortFor25_60AgeForNovember=kenyaEmrService.getCohortBasedOnAge(age1,age2,novemberStartDate,novemberStartDate);
		Integer cohortFor25_60AgeForDecember=kenyaEmrService.getCohortBasedOnAge(age1,age2,decemberStartDate,decemberEndDate);
		
		Integer noOfCohortAliveAndOnArtForOctober=kenyaEmrService.getNoOfCohortAliveAndOnArt(program,octoberStartDate,octoberStartDate);
		Integer noOfCohortAliveAndOnArtForNovember=kenyaEmrService.getNoOfCohortAliveAndOnArt(program,novemberStartDate,novemberStartDate);
		Integer noOfCohortAliveAndOnArtForDecember=kenyaEmrService.getNoOfCohortAliveAndOnArt(program,decemberStartDate,decemberEndDate);
		
		Set<Patient> noOfOriginalFirstLineRegimenForOctober=kenyaEmrService.getOriginalFirstLineRegimen(octoberStartDate,octoberStartDate);
		Set<Patient> noOfOriginalFirstLineRegimenForNovember=kenyaEmrService.getOriginalFirstLineRegimen(novemberStartDate,novemberStartDate);
		Set<Patient> noOfOriginalFirstLineRegimenForDecember=kenyaEmrService.getOriginalFirstLineRegimen(decemberStartDate,decemberEndDate);
		
		Set<Patient> noOfAlternateFirstLineRegimenForOctober=kenyaEmrService.getAlternateFirstLineRegimen(octoberStartDate,octoberStartDate);
		Set<Patient> noOfAlternateFirstLineRegimenForNovember=kenyaEmrService.getAlternateFirstLineRegimen(novemberStartDate,novemberStartDate);
		Set<Patient> noOfAlternateFirstLineRegimenForDecember=kenyaEmrService.getAlternateFirstLineRegimen(decemberStartDate,decemberEndDate);
		
		Set<Patient> noOfSecondLineRegimenForOctober=kenyaEmrService.getSecondLineRegimen(octoberStartDate,octoberStartDate);
		Set<Patient> noOfSecondLineRegimenForNovember=kenyaEmrService.getSecondLineRegimen(novemberStartDate,novemberStartDate);
		Set<Patient> noOfSecondLineRegimenForDecember=kenyaEmrService.getSecondLineRegimen(decemberStartDate,decemberEndDate);
		
		List<PatientProgram> noOfArtStoppedCohortForOctober=kenyaEmrService.getNoOfArtStoppedCohort(program,octoberStartDate,octoberStartDate);
		List<PatientProgram> noOfArtStoppedCohortForNovember=kenyaEmrService.getNoOfArtStoppedCohort(program,novemberStartDate,novemberStartDate);
		List<PatientProgram> noOfArtStoppedCohortForDecember=kenyaEmrService.getNoOfArtStoppedCohort(program,decemberStartDate,decemberEndDate);
		
		List<PatientProgram> noOfArtDiedCohortForOctober=kenyaEmrService.getNoOfArtDiedCohort(program,octoberStartDate,octoberStartDate);
		List<PatientProgram> noOfArtDiedCohortForNovember=kenyaEmrService.getNoOfArtDiedCohort(program,novemberStartDate,novemberStartDate);
		List<PatientProgram> noOfArtDiedCohortForDecember=kenyaEmrService.getNoOfArtDiedCohort(program,decemberStartDate,decemberEndDate);
	
		List<Obs> noOfPatientLostToFollowUpForOctober=kenyaEmrService.getNoOfPatientLostToFollowUp(octoberStartDate,octoberStartDate);
		List<Obs> noOfPatientLostToFollowUpForNovember=kenyaEmrService.getNoOfPatientLostToFollowUp(novemberStartDate,novemberStartDate);
		List<Obs> noOfPatientLostToFollowUpForDecember=kenyaEmrService.getNoOfPatientLostToFollowUp(decemberStartDate,decemberEndDate);
		
		
		List<Obs> noOfPatientWithCD4ForOctober=kenyaEmrService.getNoOfPatientWithCD4(octoberStartDate,octoberStartDate);
		List<Obs> noOfPatientWithCD4ForNovember=kenyaEmrService.getNoOfPatientWithCD4(novemberStartDate,novemberStartDate);
		List<Obs> noOfPatientWithCD4ForDecember=kenyaEmrService.getNoOfPatientWithCD4(decemberStartDate,decemberEndDate);
		
		List<Obs> noOfPatientNormalActivityForOctober=kenyaEmrService.getNoOfPatientNormalActivity(octoberStartDate,octoberStartDate);
		List<Obs> noOfPatientNormalActivityForNovember=kenyaEmrService.getNoOfPatientNormalActivity(novemberStartDate,novemberStartDate);
		List<Obs> noOfPatientNormalActivityForDecember=kenyaEmrService.getNoOfPatientNormalActivity(decemberStartDate,decemberEndDate);
		
		List<Obs> noOfPatientBedriddenLessThanFiftyForOctober=kenyaEmrService.getNoOfPatientBedriddenLessThanFifty(octoberStartDate,octoberStartDate);
		List<Obs> noOfPatientBedriddenLessThanFiftyForNovember=kenyaEmrService.getNoOfPatientBedriddenLessThanFifty(novemberStartDate,novemberStartDate);
		List<Obs> noOfPatientBedriddenLessThanFiftyForDecember=kenyaEmrService.getNoOfPatientBedriddenLessThanFifty(decemberStartDate,decemberEndDate);
	
		List<Obs> noOfPatientBedriddenMoreThanFiftyForOctober=kenyaEmrService.getNoOfPatientBedriddenMoreThanFifty(octoberStartDate,octoberStartDate);
		List<Obs> noOfPatientBedriddenMoreThanFiftyForNovember=kenyaEmrService.getNoOfPatientBedriddenMoreThanFifty(novemberStartDate,novemberStartDate);
		List<Obs> noOfPatientBedriddenMoreThanFiftyForDecember=kenyaEmrService.getNoOfPatientBedriddenMoreThanFifty(decemberStartDate,decemberEndDate);
		
		List<Obs> noOfPatientPickedUpArvForSixMonthForOctober=kenyaEmrService.getNoOfPatientPickedUpArvForSixMonth(octoberStartDate,octoberStartDate);
		List<Obs> noOfPatientPickedUpArvForSixMonthForNovember=kenyaEmrService.getNoOfPatientPickedUpArvForSixMonth(novemberStartDate,novemberStartDate);
		List<Obs> noOfPatientPickedUpArvForSixMonthForDecember=kenyaEmrService.getNoOfPatientPickedUpArvForSixMonth(decemberStartDate,decemberEndDate);
		
		List<Obs> noOfPatientPickedUpArvForTwelveMonthForOctober=kenyaEmrService.getNoOfPatientPickedUpArvForTwelveMonth(octoberStartDate,octoberStartDate);
		List<Obs> noOfPatientPickedUpArvForTwelveMonthForNovember=kenyaEmrService.getNoOfPatientPickedUpArvForTwelveMonth(novemberStartDate,novemberStartDate);
		List<Obs> noOfPatientPickedUpArvForTwelveMonthForDecember=kenyaEmrService.getNoOfPatientPickedUpArvForTwelveMonth(decemberStartDate,decemberEndDate);
		
		model.addAttribute("patientProgramForOctober",patientProgramForOctober.size());
		model.addAttribute("patientProgramForNovember",patientProgramForNovember.size());
		model.addAttribute("patientProgramForDecember",patientProgramForDecember.size());
		
		model.addAttribute("patientTransferInForOctober",patientTransferInForOctober.size());
		model.addAttribute("patientTransferInForNovember",patientTransferInForNovember.size());
		model.addAttribute("patientTransferInForDecember",patientTransferInForDecember.size());
		
		model.addAttribute("patientTransferOutForOctober",patientTransferOutForOctober.size());
		model.addAttribute("patientTransferOutForNovember",patientTransferOutForNovember.size());
		model.addAttribute("patientTransferOutForDecember",patientTransferOutForDecember.size());
		
		model.addAttribute("totalCohortForOctober",totalCohortForOctober);
		model.addAttribute("totalCohortForNovember",totalCohortForNovember);
		model.addAttribute("totalCohortForDecember",totalCohortForDecember);
		
		model.addAttribute("maleCohortForOctober",maleCohortForOctober);
		model.addAttribute("maleCohortForNovember",maleCohortForNovember);
		model.addAttribute("maleCohortForDecember",maleCohortForDecember);
		
		model.addAttribute("femaleCohortForOctober",femaleCohortForOctober);
		model.addAttribute("femaleCohortForNovember",femaleCohortForNovember);
		model.addAttribute("femaleCohortForDecember",femaleCohortForDecember);
		
		model.addAttribute("cohortFor0_14AgeForOctober",cohortFor0_14AgeForOctober);
		model.addAttribute("cohortFor0_14AgeForNovember",cohortFor0_14AgeForNovember);
		model.addAttribute("cohortFor0_14AgeForDecember",cohortFor0_14AgeForDecember);
		
		model.addAttribute("cohortFor15_24AgeForOctober",cohortFor15_24AgeForOctober);
		model.addAttribute("cohortFor15_24AgeForNovember",cohortFor15_24AgeForNovember);
		model.addAttribute("cohortFor15_24AgeForDecember",cohortFor15_24AgeForDecember);
		
		model.addAttribute("cohortFor25_60AgeForOctober",cohortFor25_60AgeForOctober);
		model.addAttribute("cohortFor25_60AgeForNovember",cohortFor25_60AgeForNovember);
		model.addAttribute("cohortFor25_60AgeForDecember",cohortFor25_60AgeForDecember);
		
		model.addAttribute("noOfCohortAliveAndOnArtForOctober",noOfCohortAliveAndOnArtForOctober);
		model.addAttribute("noOfCohortAliveAndOnArtNovember",noOfCohortAliveAndOnArtForNovember);
		model.addAttribute("noOfCohortAliveAndOnArtForDecember",noOfCohortAliveAndOnArtForDecember);
		
		model.addAttribute("noOfOriginalFirstLineRegimenForOctober",noOfOriginalFirstLineRegimenForOctober.size());
		model.addAttribute("noOfOriginalFirstLineRegimenNovember",noOfOriginalFirstLineRegimenForNovember.size());
		model.addAttribute("noOfOriginalFirstLineRegimenForDecember",noOfOriginalFirstLineRegimenForDecember.size());
		
		model.addAttribute("noOfAlternateFirstLineRegimenForOctober",noOfAlternateFirstLineRegimenForOctober.size());
		model.addAttribute("noOfAlternateFirstLineRegimenNovember",noOfAlternateFirstLineRegimenForNovember.size());
		model.addAttribute("noOfAlternateFirstLineRegimenForDecember",noOfAlternateFirstLineRegimenForDecember.size());
		
		model.addAttribute("noOfSecondLineRegimenForOctober",noOfSecondLineRegimenForOctober.size());
		model.addAttribute("noOfSecondLineRegimenNovember",noOfSecondLineRegimenForNovember.size());
		model.addAttribute("noOfSecondLineRegimenForDecember",noOfSecondLineRegimenForDecember.size());
		
		model.addAttribute("noOfArtStoppedCohortForOctober",noOfArtStoppedCohortForOctober.size());
		model.addAttribute("noOfArtStoppedCohortNovember",noOfArtStoppedCohortForNovember.size());
		model.addAttribute("noOfArtStoppedCohortForDecember",noOfArtStoppedCohortForDecember.size());
		
		model.addAttribute("noOfArtDiedCohortForOctober",noOfArtDiedCohortForOctober.size());
		model.addAttribute("noOfArtDiedCohortNovember",noOfArtDiedCohortForNovember.size());
		model.addAttribute("noOfArtDiedCohortForDecember",noOfArtDiedCohortForDecember.size());
		
		model.addAttribute("noOfPatientLostToFollowUpForOctober",noOfPatientLostToFollowUpForOctober.size());
		model.addAttribute("noOfPatientLostToFollowUpNovember",noOfPatientLostToFollowUpForNovember.size());
		model.addAttribute("noOfPatientLostToFollowUpForDecember",noOfPatientLostToFollowUpForDecember.size());
		
		model.addAttribute("noOfPatientWithCD4ForOctober",noOfPatientWithCD4ForOctober.size());
		model.addAttribute("noOfPatientWithCD4November",noOfPatientWithCD4ForNovember.size());
		model.addAttribute("noOfPatientWithCD4ForDecember",noOfPatientWithCD4ForDecember.size());
		
		model.addAttribute("noOfPatientNormalActivityForOctober",noOfPatientNormalActivityForOctober.size());
		model.addAttribute("noOfPatientNormalActivityNovember",noOfPatientNormalActivityForNovember.size());
		model.addAttribute("noOfPatientNormalActivityForDecember",noOfPatientNormalActivityForDecember.size());
		
		model.addAttribute("noOfPatientBedriddenLessThanFiftyForOctober",noOfPatientBedriddenLessThanFiftyForOctober.size());
		model.addAttribute("noOfPatientBedriddenLessThanFiftyNovember",noOfPatientBedriddenLessThanFiftyForNovember.size());
		model.addAttribute("noOfPatientBedriddenLessThanFiftyForDecember",noOfPatientBedriddenLessThanFiftyForDecember.size());
		
		model.addAttribute("noOfPatientBedriddenMoreThanFiftyForOctober",noOfPatientBedriddenMoreThanFiftyForOctober.size());
		model.addAttribute("noOfPatientBedriddenMoreThanFiftyNovember",noOfPatientBedriddenMoreThanFiftyForNovember.size());
		model.addAttribute("noOfPatientBedriddenMoreThanFiftyForDecember",noOfPatientBedriddenMoreThanFiftyForDecember.size());
		
		model.addAttribute("noOfPatientPickedUpArvForSixMonthForOctober",noOfPatientPickedUpArvForSixMonthForOctober.size());
		model.addAttribute("noOfPatientPickedUpArvForSixMonthNovember",noOfPatientPickedUpArvForSixMonthForNovember.size());
		model.addAttribute("noOfPatientPickedUpArvForSixMonthForDecember",noOfPatientPickedUpArvForSixMonthForDecember.size());
		
		model.addAttribute("noOfPatientPickedUpArvForTwelveMonthForOctober",noOfPatientPickedUpArvForTwelveMonthForOctober.size());
		model.addAttribute("noOfPatientPickedUpArvForTwelveMonthNovember",noOfPatientPickedUpArvForTwelveMonthForNovember.size());
		model.addAttribute("noOfPatientPickedUpArvForTwelveMonthForDecember",noOfPatientPickedUpArvForTwelveMonthForDecember.size());
    }
		
		model.addAttribute("year",year);
		model.addAttribute("quaterly",quaterly);
    }
}
