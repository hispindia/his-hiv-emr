package org.openmrs.module.kenyaemr.fragment.controller.report;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openmrs.Obs;
import org.openmrs.PatientProgram;
import org.openmrs.Program;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyaemr.api.KenyaEmrService;
import org.openmrs.module.kenyaemr.model.DrugOrderProcessed;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

public class GetHalfYearlyReportFragmentController {
	public void controller(@RequestParam("halfYearly") String halfYearly,
			FragmentModel model, UiUtils ui) {
    KenyaEmrService kenyaEmrService = (KenyaEmrService) Context.getService(KenyaEmrService.class);
	//JSONArray substituteConceptNameJsonArray = new JSONArray();
	//JSONObject conceptNameJson2 = new JSONObject();
    Date date = new Date();
   	SimpleDateFormat df = new SimpleDateFormat("yyyy");
   	String year = df.format(date);
   	
	Program program=Context.getProgramWorkflowService().getProgramByUuid("96ec813f-aaf0-45b2-add6-e661d5bf79d6");
	
	if(halfYearly!=null && halfYearly.equals("First Half")){
		String janStartDate=year+"-"+"01"+"-"+"01";
		String janEndDate=year+"-"+"01"+"-"+"31";
		
		String febStartDate=year+"-"+"02"+"-"+"01";
		String febEndDate=year+"-"+"02"+"-"+"28";
		
		String marchStartDate=year+"-"+"03"+"-"+"01";
		String marchEndDate=year+"-"+"03"+"-"+"31";
		
		String aprilStartDate=year+"-"+"04"+"-"+"01";
		String aprilEndDate=year+"-"+"04"+"-"+"30";
		
		String mayStartDate=year+"-"+"05"+"-"+"01";
		String mayEndDate=year+"-"+"05"+"-"+"31";
		
		String juneStartDate=year+"-"+"06"+"-"+"01";
		String juneEndDate=year+"-"+"06"+"-"+"30";
		
		List<PatientProgram> patientProgramForJan=kenyaEmrService.getPatientProgram(program,janStartDate,janEndDate);
		List<PatientProgram> patientProgramForFeb=kenyaEmrService.getPatientProgram(program,febStartDate,febEndDate);
		List<PatientProgram> patientProgramForMarch=kenyaEmrService.getPatientProgram(program,marchStartDate,marchEndDate);
		List<PatientProgram> patientProgramForApril=kenyaEmrService.getPatientProgram(program,aprilStartDate,aprilEndDate);
		List<PatientProgram> patientProgramForMay=kenyaEmrService.getPatientProgram(program,mayStartDate,mayEndDate);
		List<PatientProgram> patientProgramForJune=kenyaEmrService.getPatientProgram(program,juneStartDate,juneEndDate);
		
		List<Obs> patientTransferInForJan=kenyaEmrService.getNoOfPatientTransferredIn(janStartDate,janEndDate);
		List<Obs> patientTransferInForFeb=kenyaEmrService.getNoOfPatientTransferredIn(febStartDate,febEndDate);
		List<Obs> patientTransferInForMarch=kenyaEmrService.getNoOfPatientTransferredIn(marchStartDate,marchEndDate);
		List<Obs> patientTransferInForApril=kenyaEmrService.getNoOfPatientTransferredIn(aprilStartDate,aprilEndDate);
		List<Obs> patientTransferInForMay=kenyaEmrService.getNoOfPatientTransferredIn(mayStartDate,mayEndDate);
		List<Obs> patientTransferInForJune=kenyaEmrService.getNoOfPatientTransferredIn(juneStartDate,juneEndDate);
		
		List<Obs> patientTransferOutForJan=kenyaEmrService.getNoOfPatientTransferredOut(janStartDate,janEndDate);
		List<Obs> patientTransferOutForFeb=kenyaEmrService.getNoOfPatientTransferredOut(febStartDate,febEndDate);
		List<Obs> patientTransferOutForMarch=kenyaEmrService.getNoOfPatientTransferredOut(marchStartDate,marchEndDate);
		List<Obs> patientTransferOutForApril=kenyaEmrService.getNoOfPatientTransferredOut(aprilStartDate,aprilEndDate);
		List<Obs> patientTransferOutForMay=kenyaEmrService.getNoOfPatientTransferredOut(mayStartDate,mayEndDate);
		List<Obs> patientTransferOutForJune=kenyaEmrService.getNoOfPatientTransferredOut(juneStartDate,juneEndDate);
		
		Integer totalCohortForJan=kenyaEmrService.getTotalNoOfCohort(janStartDate, janEndDate);
		Integer totalCohortForFeb=kenyaEmrService.getTotalNoOfCohort(febStartDate,febEndDate);
		Integer totalCohortForMarch=kenyaEmrService.getTotalNoOfCohort(marchStartDate,marchEndDate);
		Integer totalCohortForApril=kenyaEmrService.getTotalNoOfCohort(aprilStartDate,aprilEndDate);
		Integer totalCohortForMay=kenyaEmrService.getTotalNoOfCohort(mayStartDate,mayEndDate);
		Integer totalCohortForJune=kenyaEmrService.getTotalNoOfCohort(juneStartDate,juneEndDate);
		
		Integer maleCohortForJan=kenyaEmrService.getCohortBasedOnGender("M",janStartDate, janEndDate);
		Integer maleCohortForFeb=kenyaEmrService.getCohortBasedOnGender("M",febStartDate,febEndDate);
		Integer maleCohortForMarch=kenyaEmrService.getCohortBasedOnGender("M",marchStartDate,marchEndDate);
		Integer maleCohortForApril=kenyaEmrService.getCohortBasedOnGender("M",aprilStartDate,aprilEndDate);
		Integer maleCohortForMay=kenyaEmrService.getCohortBasedOnGender("M",mayStartDate,mayEndDate);
		Integer maleCohortForJune=kenyaEmrService.getCohortBasedOnGender("M",juneStartDate,juneEndDate);
		
		Integer femaleCohortForJan=kenyaEmrService.getCohortBasedOnGender("F",janStartDate, janEndDate);
		Integer femaleCohortForFeb=kenyaEmrService.getCohortBasedOnGender("F",febStartDate,febEndDate);
		Integer femaleCohortForMarch=kenyaEmrService.getCohortBasedOnGender("F",marchStartDate,marchEndDate);
		Integer femaleCohortForApril=kenyaEmrService.getCohortBasedOnGender("F",aprilStartDate,aprilEndDate);
		Integer femaleCohortForMay=kenyaEmrService.getCohortBasedOnGender("F",mayStartDate,mayEndDate);
		Integer femaleCohortForJune=kenyaEmrService.getCohortBasedOnGender("F",juneStartDate,juneEndDate);
		
		Integer age1=0;
		Integer age2=14;
		Integer cohortFor0_14AgeForJan=kenyaEmrService.getCohortBasedOnAge(age1,age2,janStartDate, janEndDate);
		Integer cohortFor0_14AgeForFeb=kenyaEmrService.getCohortBasedOnGender("F",febStartDate,febEndDate);
		Integer cohortFor0_14AgeForMarch=kenyaEmrService.getCohortBasedOnGender("F",marchStartDate,marchEndDate);
		Integer cohortFor0_14AgeForApril=kenyaEmrService.getCohortBasedOnGender("F",aprilStartDate,aprilEndDate);
		Integer cohortFor0_14AgeForMay=kenyaEmrService.getCohortBasedOnGender("F",mayStartDate,mayEndDate);
		Integer cohortFor0_14AgeForJune=kenyaEmrService.getCohortBasedOnGender("F",juneStartDate,juneEndDate);
		
		age1=15;
		age2=24;
		Integer cohortFor15_24AgeForJan=kenyaEmrService.getCohortBasedOnAge(age1,age2,janStartDate, janEndDate);
		Integer cohortFor15_24AgeForFeb=kenyaEmrService.getCohortBasedOnGender("F",febStartDate,febEndDate);
		Integer cohortFor15_24AgeForMarch=kenyaEmrService.getCohortBasedOnGender("F",marchStartDate,marchEndDate);
		Integer cohortFor15_24AgeForApril=kenyaEmrService.getCohortBasedOnGender("F",aprilStartDate,aprilEndDate);
		Integer cohortFor15_24AgeForMay=kenyaEmrService.getCohortBasedOnGender("F",mayStartDate,mayEndDate);
		Integer cohortFor15_24AgeForJune=kenyaEmrService.getCohortBasedOnGender("F",juneStartDate,juneEndDate);
		
		age1=25;
		age2=60;
		Integer cohortFor25_60AgeForJan=kenyaEmrService.getCohortBasedOnAge(age1,age2,janStartDate, janEndDate);
		Integer cohortFor25_60AgeForFeb=kenyaEmrService.getCohortBasedOnGender("F",febStartDate,febEndDate);
		Integer cohortFor25_60AgeForMarch=kenyaEmrService.getCohortBasedOnGender("F",marchStartDate,marchEndDate);
		Integer cohortFor25_60AgeForApril=kenyaEmrService.getCohortBasedOnGender("F",aprilStartDate,aprilEndDate);
		Integer cohortFor25_60AgeForMay=kenyaEmrService.getCohortBasedOnGender("F",mayStartDate,mayEndDate);
		Integer cohortFor25_60AgeForJune=kenyaEmrService.getCohortBasedOnGender("F",juneStartDate,juneEndDate);
		
		List<PatientProgram> noOfCohortAliveAndOnArtForJan=kenyaEmrService.getNoOfCohortAliveAndOnArt(program,janStartDate,janEndDate);
		List<PatientProgram> noOfCohortAliveAndOnArtForFeb=kenyaEmrService.getNoOfCohortAliveAndOnArt(program,febStartDate,febEndDate);
		List<PatientProgram> noOfCohortAliveAndOnArtForMarch=kenyaEmrService.getNoOfCohortAliveAndOnArt(program,marchStartDate,marchEndDate);
		List<PatientProgram> noOfCohortAliveAndOnArtForApril=kenyaEmrService.getNoOfCohortAliveAndOnArt(program,aprilStartDate,aprilEndDate);
		List<PatientProgram> noOfCohortAliveAndOnArtForMay=kenyaEmrService.getNoOfCohortAliveAndOnArt(program,mayStartDate,mayEndDate);
		List<PatientProgram> noOfCohortAliveAndOnArtForJune=kenyaEmrService.getNoOfCohortAliveAndOnArt(program,juneStartDate,juneEndDate);
		
		List<DrugOrderProcessed> noOfOriginalFirstLineRegimenForJan=kenyaEmrService.getOriginalFirstLineRegimen(janStartDate,janEndDate);
		List<DrugOrderProcessed> noOfOriginalFirstLineRegimenForFeb=kenyaEmrService.getOriginalFirstLineRegimen(febStartDate,febEndDate);
		List<DrugOrderProcessed> noOfOriginalFirstLineRegimenForMarch=kenyaEmrService.getOriginalFirstLineRegimen(marchStartDate,marchEndDate);
		List<DrugOrderProcessed> noOfOriginalFirstLineRegimenForApril=kenyaEmrService.getOriginalFirstLineRegimen(aprilStartDate,aprilEndDate);
		List<DrugOrderProcessed> noOfOriginalFirstLineRegimenForMay=kenyaEmrService.getOriginalFirstLineRegimen(mayStartDate,mayEndDate);
		List<DrugOrderProcessed> noOfOriginalFirstLineRegimenForJune=kenyaEmrService.getOriginalFirstLineRegimen(juneStartDate,juneEndDate);
		
		List<DrugOrderProcessed> noOfAlternateFirstLineRegimenForJan=kenyaEmrService.getAlternateFirstLineRegimen(janStartDate,janEndDate);
		List<DrugOrderProcessed> noOfAlternateFirstLineRegimenForFeb=kenyaEmrService.getAlternateFirstLineRegimen(febStartDate,febEndDate);
		List<DrugOrderProcessed> noOfAlternateFirstLineRegimenForMarch=kenyaEmrService.getAlternateFirstLineRegimen(marchStartDate,marchEndDate);
		List<DrugOrderProcessed> noOfAlternateFirstLineRegimenForApril=kenyaEmrService.getAlternateFirstLineRegimen(aprilStartDate,aprilEndDate);
		List<DrugOrderProcessed> noOfAlternateFirstLineRegimenForMay=kenyaEmrService.getAlternateFirstLineRegimen(mayStartDate,mayEndDate);
		List<DrugOrderProcessed> noOfAlternateFirstLineRegimenForJune=kenyaEmrService.getAlternateFirstLineRegimen(juneStartDate,juneEndDate);
		
		List<DrugOrderProcessed> noOfSecondLineRegimenForJan=kenyaEmrService.getSecondLineRegimen(janStartDate,janEndDate);
		List<DrugOrderProcessed> noOfSecondLineRegimenForFeb=kenyaEmrService.getSecondLineRegimen(febStartDate,febEndDate);
		List<DrugOrderProcessed> noOfSecondLineRegimenForMarch=kenyaEmrService.getSecondLineRegimen(marchStartDate,marchEndDate);
		List<DrugOrderProcessed> noOfSecondLineRegimenForApril=kenyaEmrService.getSecondLineRegimen(aprilStartDate,aprilEndDate);
		List<DrugOrderProcessed> noOfSecondLineRegimenForMay=kenyaEmrService.getSecondLineRegimen(mayStartDate,mayEndDate);
		List<DrugOrderProcessed> noOfSecondLineRegimenForJune=kenyaEmrService.getSecondLineRegimen(juneStartDate,juneEndDate);
		
		List<PatientProgram> noOfArtStoppedCohortForJan=kenyaEmrService.getNoOfArtStoppedCohort(program,janStartDate,janEndDate);
		List<PatientProgram> noOfArtStoppedCohortForFeb=kenyaEmrService.getNoOfArtStoppedCohort(program,febStartDate,febEndDate);
		List<PatientProgram> noOfArtStoppedCohortForMarch=kenyaEmrService.getNoOfArtStoppedCohort(program,marchStartDate,marchEndDate);
		List<PatientProgram> noOfArtStoppedCohortForApril=kenyaEmrService.getNoOfArtStoppedCohort(program,aprilStartDate,aprilEndDate);
		List<PatientProgram> noOfArtStoppedCohortForMay=kenyaEmrService.getNoOfArtStoppedCohort(program,mayStartDate,mayEndDate);
		List<PatientProgram> noOfArtStoppedCohortForJune=kenyaEmrService.getNoOfArtStoppedCohort(program,juneStartDate,juneEndDate);
		
		List<PatientProgram> noOfArtDiedCohortForJan=kenyaEmrService.getNoOfArtDiedCohort(program,janStartDate,janEndDate);
		List<PatientProgram> noOfArtDiedCohortForFeb=kenyaEmrService.getNoOfArtDiedCohort(program,febStartDate,febEndDate);
		List<PatientProgram> noOfArtDiedCohortForMarch=kenyaEmrService.getNoOfArtDiedCohort(program,marchStartDate,marchEndDate);
		List<PatientProgram> noOfArtDiedCohortForApril=kenyaEmrService.getNoOfArtDiedCohort(program,aprilStartDate,aprilEndDate);
		List<PatientProgram> noOfArtDiedCohortForMay=kenyaEmrService.getNoOfArtDiedCohort(program,mayStartDate,mayEndDate);
		List<PatientProgram> noOfArtDiedCohortForJune=kenyaEmrService.getNoOfArtDiedCohort(program,juneStartDate,juneEndDate);
		
		List<Obs> noOfPatientLostToFollowUpForJan=kenyaEmrService.getNoOfPatientLostToFollowUp(janStartDate,janEndDate);
		List<Obs> noOfPatientLostToFollowUpForFeb=kenyaEmrService.getNoOfPatientLostToFollowUp(febStartDate,febEndDate);
		List<Obs> noOfPatientLostToFollowUpForMarch=kenyaEmrService.getNoOfPatientLostToFollowUp(marchStartDate,marchEndDate);
		List<Obs> noOfPatientLostToFollowUpForApril=kenyaEmrService.getNoOfPatientLostToFollowUp(aprilStartDate,aprilEndDate);
		List<Obs> noOfPatientLostToFollowUpForMay=kenyaEmrService.getNoOfPatientLostToFollowUp(mayStartDate,mayEndDate);
		List<Obs> noOfPatientLostToFollowUpForJune=kenyaEmrService.getNoOfPatientLostToFollowUp(juneStartDate,juneEndDate);
		
		List<Obs> noOfPatientWithCD4ForJan=kenyaEmrService.getNoOfPatientWithCD4(janStartDate,janEndDate);
		List<Obs> noOfPatientWithCD4ForFeb=kenyaEmrService.getNoOfPatientWithCD4(febStartDate,febEndDate);
		List<Obs> noOfPatientWithCD4ForMarch=kenyaEmrService.getNoOfPatientWithCD4(marchStartDate,marchEndDate);
		List<Obs> noOfPatientWithCD4ForApril=kenyaEmrService.getNoOfPatientWithCD4(aprilStartDate,aprilEndDate);
		List<Obs> noOfPatientWithCD4ForMay=kenyaEmrService.getNoOfPatientWithCD4(mayStartDate,mayEndDate);
		List<Obs> noOfPatientWithCD4ForJune=kenyaEmrService.getNoOfPatientWithCD4(juneStartDate,juneEndDate);
		
		List<Obs> noOfPatientNormalActivityForJan=kenyaEmrService.getNoOfPatientNormalActivity(janStartDate,janEndDate);
		List<Obs> noOfPatientNormalActivityForFeb=kenyaEmrService.getNoOfPatientNormalActivity(febStartDate,febEndDate);
		List<Obs> noOfPatientNormalActivityForMarch=kenyaEmrService.getNoOfPatientNormalActivity(marchStartDate,marchEndDate);
		List<Obs> noOfPatientNormalActivityForApril=kenyaEmrService.getNoOfPatientNormalActivity(aprilStartDate,aprilEndDate);
		List<Obs> noOfPatientNormalActivityForMay=kenyaEmrService.getNoOfPatientNormalActivity(mayStartDate,mayEndDate);
		List<Obs> noOfPatientNormalActivityForJune=kenyaEmrService.getNoOfPatientNormalActivity(juneStartDate,juneEndDate);
		
		List<Obs> noOfPatientBedriddenLessThanFiftyForJan=kenyaEmrService.getNoOfPatientBedriddenLessThanFifty(janStartDate,janEndDate);
		List<Obs> noOfPatientBedriddenLessThanFiftyForFeb=kenyaEmrService.getNoOfPatientBedriddenLessThanFifty(febStartDate,febEndDate);
		List<Obs> noOfPatientBedriddenLessThanFiftyForMarch=kenyaEmrService.getNoOfPatientBedriddenLessThanFifty(marchStartDate,marchEndDate);
		List<Obs> noOfPatientBedriddenLessThanFiftyForApril=kenyaEmrService.getNoOfPatientBedriddenLessThanFifty(aprilStartDate,aprilEndDate);
		List<Obs> noOfPatientBedriddenLessThanFiftyForMay=kenyaEmrService.getNoOfPatientBedriddenLessThanFifty(mayStartDate,mayEndDate);
		List<Obs> noOfPatientBedriddenLessThanFiftyForJune=kenyaEmrService.getNoOfPatientBedriddenLessThanFifty(juneStartDate,juneEndDate);
		
		List<Obs> noOfPatientBedriddenMoreThanFiftyForJan=kenyaEmrService.getNoOfPatientBedriddenMoreThanFifty(janStartDate,janEndDate);
		List<Obs> noOfPatientBedriddenMoreThanFiftyForFeb=kenyaEmrService.getNoOfPatientBedriddenMoreThanFifty(febStartDate,febEndDate);
		List<Obs> noOfPatientBedriddenMoreThanFiftyForMarch=kenyaEmrService.getNoOfPatientBedriddenMoreThanFifty(marchStartDate,marchEndDate);
		List<Obs> noOfPatientBedriddenMoreThanFiftyForApril=kenyaEmrService.getNoOfPatientBedriddenMoreThanFifty(aprilStartDate,aprilEndDate);
		List<Obs> noOfPatientBedriddenMoreThanFiftyForMay=kenyaEmrService.getNoOfPatientBedriddenMoreThanFifty(mayStartDate,mayEndDate);
		List<Obs> noOfPatientBedriddenMoreThanFiftyForJune=kenyaEmrService.getNoOfPatientBedriddenMoreThanFifty(juneStartDate,juneEndDate);
		
		List<Obs> noOfPatientPickedUpArvForSixMonthForJan=kenyaEmrService.getNoOfPatientPickedUpArvForSixMonth(janStartDate,janEndDate);
		List<Obs> noOfPatientPickedUpArvForSixMonthForFeb=kenyaEmrService.getNoOfPatientPickedUpArvForSixMonth(febStartDate,febEndDate);
		List<Obs> noOfPatientPickedUpArvForSixMonthForMarch=kenyaEmrService.getNoOfPatientPickedUpArvForSixMonth(marchStartDate,marchEndDate);
		List<Obs> noOfPatientPickedUpArvForSixMonthForApril=kenyaEmrService.getNoOfPatientPickedUpArvForSixMonth(aprilStartDate,aprilEndDate);
		List<Obs> noOfPatientPickedUpArvForSixMonthForMay=kenyaEmrService.getNoOfPatientPickedUpArvForSixMonth(mayStartDate,mayEndDate);
		List<Obs> noOfPatientPickedUpArvForSixMonthForJune=kenyaEmrService.getNoOfPatientPickedUpArvForSixMonth(juneStartDate,juneEndDate);
		
		List<Obs> noOfPatientPickedUpArvForTwelveMonthForJan=kenyaEmrService.getNoOfPatientPickedUpArvForTwelveMonth(janStartDate,janEndDate);
		List<Obs> noOfPatientPickedUpArvForTwelveMonthForFeb=kenyaEmrService.getNoOfPatientPickedUpArvForTwelveMonth(febStartDate,febEndDate);
		List<Obs> noOfPatientPickedUpArvForTwelveMonthForMarch=kenyaEmrService.getNoOfPatientPickedUpArvForTwelveMonth(marchStartDate,marchEndDate);
		List<Obs> noOfPatientPickedUpArvForTwelveMonthForApril=kenyaEmrService.getNoOfPatientPickedUpArvForTwelveMonth(aprilStartDate,aprilEndDate);
		List<Obs> noOfPatientPickedUpArvForTwelveMonthForMay=kenyaEmrService.getNoOfPatientPickedUpArvForTwelveMonth(mayStartDate,mayEndDate);
		List<Obs> noOfPatientPickedUpArvForTwelveMonthForJune=kenyaEmrService.getNoOfPatientPickedUpArvForTwelveMonth(juneStartDate,juneEndDate);
		
		model.addAttribute("patientProgramForJan",patientProgramForJan.size());
		model.addAttribute("patientProgramForFeb",patientProgramForFeb.size());
		model.addAttribute("patientProgramForMarch",patientProgramForMarch.size());
		model.addAttribute("patientProgramForApril",patientProgramForApril.size());
		model.addAttribute("patientProgramForMay",patientProgramForMay.size());
		model.addAttribute("patientProgramForJune",patientProgramForJune.size());
		
		model.addAttribute("patientTransferInForJan",patientTransferInForJan.size());
		model.addAttribute("patientTransferInForFeb",patientTransferInForFeb.size());
		model.addAttribute("patientTransferInForMarch",patientTransferInForMarch.size());
		model.addAttribute("patientTransferInForApril",patientTransferInForApril.size());
		model.addAttribute("patientTransferInForMay",patientTransferInForMay.size());
		model.addAttribute("patientTransferInForJune",patientTransferInForJune.size());
		
		model.addAttribute("patientTransferOutForJan",patientTransferOutForJan.size());
		model.addAttribute("patientTransferOutForFeb",patientTransferOutForFeb.size());
		model.addAttribute("patientTransferOutForMarch",patientTransferOutForMarch.size());
		model.addAttribute("patientTransferOutForApril",patientTransferOutForApril.size());
		model.addAttribute("patientTransferOutForMay",patientTransferOutForMay.size());
		model.addAttribute("patientTransferOutForJune",patientTransferOutForJune.size());
		
		
		
		model.addAttribute("patientProgramForJan",patientProgramForJan.size());
		model.addAttribute("patientProgramForFeb",patientProgramForFeb.size());
		model.addAttribute("patientProgramForMarch",patientProgramForMarch.size());
		model.addAttribute("patientProgramForApril",patientProgramForApril.size());
		model.addAttribute("patientProgramForMay",patientProgramForMay.size());
		model.addAttribute("patientProgramForJune",patientProgramForJune.size());
		
		
		model.addAttribute("patientTransferInForJan",patientTransferInForJan.size());
		model.addAttribute("patientTransferInForFeb",patientTransferInForFeb.size());
		model.addAttribute("patientTransferInForMarch",patientTransferInForMarch.size());
		model.addAttribute("patientTransferInForApril",patientTransferInForApril.size());
		model.addAttribute("patientTransferInForMay",patientTransferInForMay.size());
		model.addAttribute("patientTransferInForJune",patientTransferInForJune.size());
		
		model.addAttribute("patientTransferOutForJan",patientTransferOutForJan.size());
		model.addAttribute("patientTransferOutForFeb",patientTransferOutForFeb.size());
		model.addAttribute("patientTransferOutForMarch",patientTransferOutForMarch.size());
		model.addAttribute("patientTransferOutForApril",patientTransferOutForApril.size());
		model.addAttribute("patientTransferOutForMay",patientTransferOutForMay.size());
		model.addAttribute("patientTransferOutForJune",patientTransferOutForJune.size());
		
		model.addAttribute("totalCohortForJan",totalCohortForJan.SIZE);
		model.addAttribute("totalCohortForFeb",totalCohortForFeb.SIZE);
		model.addAttribute("totalCohortForMarch",totalCohortForMarch.SIZE);
		model.addAttribute("totalCohortForApril",totalCohortForApril.SIZE);
		model.addAttribute("totalCohortForMay",totalCohortForMay.SIZE);
		model.addAttribute("totalCohortForJune",totalCohortForJune.SIZE);
		
		model.addAttribute("maleCohortForJan",maleCohortForJan.SIZE);
		model.addAttribute("maleCohortForFeb",maleCohortForFeb.SIZE);
		model.addAttribute("maleCohortForMarch",maleCohortForMarch.SIZE);
		model.addAttribute("maleCohortForApril",maleCohortForApril.SIZE);
		model.addAttribute("maleCohortForMay",maleCohortForMay.SIZE);
		model.addAttribute("maleCohortForJune",maleCohortForJune.SIZE);
		
		model.addAttribute("femaleCohortForJan",femaleCohortForJan.SIZE);
		model.addAttribute("femaleCohortForFeb",femaleCohortForFeb.SIZE);
		model.addAttribute("femaleCohortForMarch",femaleCohortForMarch.SIZE);
		model.addAttribute("femaleCohortForApril",femaleCohortForApril.SIZE);
		model.addAttribute("femaleCohortForMay",femaleCohortForMay.shortValue());
		model.addAttribute("femaleCohortForJune",femaleCohortForJune.SIZE);
		
		model.addAttribute("cohortFor0_14AgeForJan",cohortFor0_14AgeForJan.SIZE);
		model.addAttribute("cohortFor0_14AgeForFeb",cohortFor0_14AgeForFeb.SIZE);
		model.addAttribute("cohortFor0_14AgeForMarch",cohortFor0_14AgeForMarch.SIZE);
		model.addAttribute("cohortFor0_14AgeForApril",cohortFor0_14AgeForApril.SIZE);
		model.addAttribute("cohortFor0_14AgeForMay",cohortFor0_14AgeForMay.SIZE);
		model.addAttribute("cohortFor0_14AgeForJune",cohortFor0_14AgeForJune.SIZE);
		
		model.addAttribute("cohortFor15_24AgeForJan",cohortFor15_24AgeForJan.SIZE);
		model.addAttribute("cohortFor15_24AgeForFeb",cohortFor15_24AgeForFeb.SIZE);
		model.addAttribute("cohortFor15_24AgeForMarch",cohortFor15_24AgeForMarch.SIZE);
		model.addAttribute("cohortFor15_24AgeForApril",cohortFor15_24AgeForApril.SIZE);
		model.addAttribute("cohortFor15_24AgeForMay",cohortFor15_24AgeForMay.SIZE);
		model.addAttribute("cohortFor15_24AgeForJune",cohortFor15_24AgeForJune.SIZE);
		
		model.addAttribute("cohortFor25_60AgeForJan",cohortFor25_60AgeForJan.SIZE);
		model.addAttribute("cohortFor25_60AgeForFeb",cohortFor25_60AgeForFeb.SIZE);
		model.addAttribute("cohortFor25_60AgeForMarch",cohortFor25_60AgeForMarch.SIZE);
		model.addAttribute("cohortFor25_60AgeForApril",cohortFor25_60AgeForApril.SIZE);
		model.addAttribute("cohortFor25_60AgeForMay",cohortFor25_60AgeForMay.SIZE);
		model.addAttribute("cohortFor25_60AgeForJune",cohortFor25_60AgeForJune.SIZE);
		
		model.addAttribute("noOfCohortAliveAndOnArtForJan",noOfCohortAliveAndOnArtForJan.size());
		model.addAttribute("noOfCohortAliveAndOnArtForFeb",noOfCohortAliveAndOnArtForFeb.size());
		model.addAttribute("noOfCohortAliveAndOnArtForMarch",noOfCohortAliveAndOnArtForMarch.size());
		model.addAttribute("noOfCohortAliveAndOnArtForApril",noOfCohortAliveAndOnArtForApril.size());
		model.addAttribute("noOfCohortAliveAndOnArtForMay",noOfCohortAliveAndOnArtForMay.size());
		model.addAttribute("noOfCohortAliveAndOnArtForJune",noOfCohortAliveAndOnArtForJune.size());
		
		model.addAttribute("noOfOriginalFirstLineRegimenForJan",noOfOriginalFirstLineRegimenForJan.size());
		model.addAttribute("noOfOriginalFirstLineRegimenForFeb",noOfOriginalFirstLineRegimenForFeb.size());
		model.addAttribute("noOfOriginalFirstLineRegimenForMarch",noOfOriginalFirstLineRegimenForMarch.size());
		model.addAttribute("noOfOriginalFirstLineRegimenForApril",noOfOriginalFirstLineRegimenForApril.size());
		model.addAttribute("noOfOriginalFirstLineRegimenForMay",noOfOriginalFirstLineRegimenForMay.size());
		model.addAttribute("noOfOriginalFirstLineRegimenForJune",noOfOriginalFirstLineRegimenForJune.size());
		
		model.addAttribute("noOfAlternateFirstLineRegimenForJan",noOfAlternateFirstLineRegimenForJan.size());
		model.addAttribute("noOfAlternateFirstLineRegimenForFeb",noOfAlternateFirstLineRegimenForFeb.size());
		model.addAttribute("noOfAlternateFirstLineRegimenForMarch",noOfAlternateFirstLineRegimenForMarch.size());
		model.addAttribute("noOfAlternateFirstLineRegimenForApril",noOfAlternateFirstLineRegimenForApril.size());
		model.addAttribute("noOfAlternateFirstLineRegimenForMay",noOfAlternateFirstLineRegimenForMay.size());
		model.addAttribute("noOfAlternateFirstLineRegimenForJune",noOfAlternateFirstLineRegimenForJune.size());
		
		model.addAttribute("noOfSecondLineRegimenForJan",noOfSecondLineRegimenForJan.size());
		model.addAttribute("noOfSecondLineRegimenForFeb",noOfSecondLineRegimenForFeb.size());
		model.addAttribute("noOfSecondLineRegimenForMarch",noOfSecondLineRegimenForMarch.size());
		model.addAttribute("noOfSecondLineRegimenForApril",noOfSecondLineRegimenForApril.size());
		model.addAttribute("noOfSecondLineRegimenForMay",noOfSecondLineRegimenForMay.size());
		model.addAttribute("noOfSecondLineRegimenForJune",noOfSecondLineRegimenForJune.size());
		
		model.addAttribute("noOfArtStoppedCohortForJan",noOfArtStoppedCohortForJan.size());
		model.addAttribute("noOfArtStoppedCohortForFeb",noOfArtStoppedCohortForFeb.size());
		model.addAttribute("noOfArtStoppedCohortForMarch",noOfArtStoppedCohortForMarch.size());
		model.addAttribute("noOfArtStoppedCohortForApril",noOfArtStoppedCohortForApril.size());
		model.addAttribute("noOfArtStoppedCohortForMay",noOfArtStoppedCohortForMay.size());
		model.addAttribute("noOfArtStoppedCohortForJune",noOfArtStoppedCohortForJune.size());
		
		model.addAttribute("noOfArtDiedCohortForJan",noOfArtDiedCohortForJan.size());
		model.addAttribute("noOfArtDiedCohortForFeb",noOfArtDiedCohortForFeb.size());
		model.addAttribute("noOfArtDiedCohortForMarch",noOfArtDiedCohortForMarch.size());
		model.addAttribute("noOfArtDiedCohortForApril",noOfArtDiedCohortForApril.size());
		model.addAttribute("noOfArtDiedCohortForMay",noOfArtDiedCohortForMay.size());
		model.addAttribute("noOfArtDiedCohortForJune",noOfArtDiedCohortForJune.size());
		
		model.addAttribute("noOfPatientLostToFollowUpForJan",noOfPatientLostToFollowUpForJan.size());
		model.addAttribute("noOfPatientLostToFollowUpForFeb",noOfPatientLostToFollowUpForFeb.size());
		model.addAttribute("noOfPatientLostToFollowUpForMarch",noOfPatientLostToFollowUpForMarch.size());
		model.addAttribute("noOfPatientLostToFollowUpForApril",noOfPatientLostToFollowUpForApril.size());
		model.addAttribute("noOfPatientLostToFollowUpForMay",noOfPatientLostToFollowUpForMay.size());
		model.addAttribute("noOfPatientLostToFollowUpForJune",noOfPatientLostToFollowUpForJune.size());
		
		model.addAttribute("noOfPatientNormalActivityForJan",noOfPatientNormalActivityForJan.size());
		model.addAttribute("noOfPatientNormalActivityForFeb",noOfPatientNormalActivityForFeb.size());
		model.addAttribute("noOfPatientNormalActivityForMarch",noOfPatientNormalActivityForMarch.size());
		model.addAttribute("noOfPatientNormalActivityForApril",noOfPatientNormalActivityForApril.size());
		model.addAttribute("noOfPatientNormalActivityForMay",noOfPatientNormalActivityForMay.size());
		model.addAttribute("noOfPatientNormalActivityForJune",noOfPatientNormalActivityForJune.size());
		
		model.addAttribute("noOfPatientBedriddenLessThanFiftyForJan",noOfPatientBedriddenLessThanFiftyForJan.size());
		model.addAttribute("noOfPatientBedriddenLessThanFiftyForFeb",noOfPatientBedriddenLessThanFiftyForFeb.size());
		model.addAttribute("noOfPatientBedriddenLessThanFiftyForMarch",noOfPatientBedriddenLessThanFiftyForMarch.size());
		model.addAttribute("noOfPatientBedriddenLessThanFiftyForApril",noOfPatientBedriddenLessThanFiftyForApril.size());
		model.addAttribute("noOfPatientBedriddenLessThanFiftyForMay",noOfPatientBedriddenLessThanFiftyForMay.size());
		model.addAttribute("noOfPatientBedriddenLessThanFiftyForJune",noOfPatientBedriddenLessThanFiftyForJune.size());
		
		model.addAttribute("noOfPatientBedriddenMoreThanFiftyForJan",noOfPatientBedriddenMoreThanFiftyForJan.size());
		model.addAttribute("noOfPatientBedriddenMoreThanFiftyForFeb",noOfPatientBedriddenMoreThanFiftyForFeb.size());
		model.addAttribute("noOfPatientBedriddenMoreThanFiftyForMarch",noOfPatientBedriddenMoreThanFiftyForMarch.size());
		model.addAttribute("noOfPatientBedriddenMoreThanFiftyForApril",noOfPatientBedriddenMoreThanFiftyForApril.size());
		model.addAttribute("noOfPatientBedriddenMoreThanFiftyForMay",noOfPatientBedriddenMoreThanFiftyForMay.size());
		model.addAttribute("noOfPatientBedriddenMoreThanFiftyForJune",noOfPatientBedriddenMoreThanFiftyForJune.size());
		
		model.addAttribute("noOfPatientPickedUpArvForSixMonthForJan",noOfPatientPickedUpArvForSixMonthForJan.size());
		model.addAttribute("noOfPatientPickedUpArvForSixMonthForFeb",noOfPatientWithCD4ForFeb.size());
		model.addAttribute("noOfPatientWithCD4ForMarch",noOfPatientPickedUpArvForSixMonthForMarch.size());
		model.addAttribute("noOfPatientPickedUpArvForSixMonthForApril",noOfPatientPickedUpArvForSixMonthForApril.size());
		model.addAttribute("noOfPatientPickedUpArvForSixMonthForMay",noOfPatientPickedUpArvForSixMonthForMay.size());
		model.addAttribute("noOfPatientPickedUpArvForSixMonthForJune",noOfPatientPickedUpArvForSixMonthForJune.size());
		
		model.addAttribute("noOfPatientPickedUpArvForTwelveMonthForJan",noOfPatientPickedUpArvForTwelveMonthForJan.size());
		model.addAttribute("noOfPatientPickedUpArvForTwelveMonthForFeb",noOfPatientPickedUpArvForTwelveMonthForFeb.size());
		model.addAttribute("noOfPatientPickedUpArvForTwelveMonthForMarch",noOfPatientPickedUpArvForTwelveMonthForMarch.size());
		model.addAttribute("noOfPatientPickedUpArvForTwelveMonthForApril",noOfPatientPickedUpArvForTwelveMonthForApril.size());
		model.addAttribute("noOfPatientPickedUpArvForTwelveMonthForMay",noOfPatientPickedUpArvForTwelveMonthForMay.size());
		model.addAttribute("noOfPatientPickedUpArvForTwelveMonthForJune",noOfPatientPickedUpArvForTwelveMonthForJune.size());	
	}
	
	if(halfYearly!=null && halfYearly.equals("Second Half")){
		
		String julyStartDate=year+"-"+"07"+"-"+"01";
		String julyEndDate=year+"-"+"07"+"-"+"31";
		
		String augustStartDate=year+"-"+"08"+"-"+"01";
		String augustEndDate=year+"-"+"08"+"-"+"31";
		
		String septemberStartDate=year+"-"+"09"+"-"+"01";
		String septemberEndDate=year+"-"+"09"+"-"+"30";
		
		String octoberStartDate=year+"-"+"10"+"-"+"01";
		String octoberEndDate=year+"-"+"10"+"-"+"31";
		
		String novemberStartDate=year+"-"+"11"+"-"+"01";
		String novemberEndDate=year+"-"+"11"+"-"+"30";
		
		String decemberStartDate=year+"-"+"12"+"-"+"01";
		String decemberEndDate=year+"-"+"12"+"-"+"31";
		
		List<PatientProgram> patientProgramForJuly=kenyaEmrService.getPatientProgram(program,julyStartDate,julyEndDate);
		List<PatientProgram> patientProgramForAugust=kenyaEmrService.getPatientProgram(program,augustStartDate,augustEndDate);
		List<PatientProgram> patientProgramForSeptember=kenyaEmrService.getPatientProgram(program,septemberStartDate,septemberEndDate);
		List<PatientProgram> patientProgramForOctober=kenyaEmrService.getPatientProgram(program,octoberStartDate,octoberStartDate);
		List<PatientProgram> patientProgramForNovember=kenyaEmrService.getPatientProgram(program,novemberStartDate,novemberStartDate);
		List<PatientProgram> patientProgramForDecember=kenyaEmrService.getPatientProgram(program,decemberStartDate,decemberEndDate);
		
		List<Obs> patientTransferInForJuly=kenyaEmrService.getNoOfPatientTransferredIn(julyStartDate,julyEndDate);
		List<Obs> patientTransferInForAugust=kenyaEmrService.getNoOfPatientTransferredIn(augustStartDate,augustEndDate);
		List<Obs> patientTransferInForSeptember=kenyaEmrService.getNoOfPatientTransferredIn(septemberStartDate,septemberEndDate);
		List<Obs> patientTransferInForOctober=kenyaEmrService.getNoOfPatientTransferredIn(octoberStartDate,octoberStartDate);
		List<Obs> patientTransferInForNovember=kenyaEmrService.getNoOfPatientTransferredIn(novemberStartDate,novemberStartDate);
		List<Obs> patientTransferInForDecember=kenyaEmrService.getNoOfPatientTransferredIn(decemberStartDate,decemberEndDate);
		
		List<Obs> patientTransferOutForJuly=kenyaEmrService.getNoOfPatientTransferredOut(julyStartDate,julyEndDate);
		List<Obs> patientTransferOutForAugust=kenyaEmrService.getNoOfPatientTransferredOut(augustStartDate,augustEndDate);
		List<Obs> patientTransferOutForSeptember=kenyaEmrService.getNoOfPatientTransferredOut(septemberStartDate,septemberEndDate);
		List<Obs> patientTransferOutForOctober=kenyaEmrService.getNoOfPatientTransferredOut(octoberStartDate,octoberStartDate);
		List<Obs> patientTransferOutForNovember=kenyaEmrService.getNoOfPatientTransferredOut(novemberStartDate,novemberStartDate);
		List<Obs> patientTransferOutForDecember=kenyaEmrService.getNoOfPatientTransferredOut(decemberStartDate,decemberEndDate);
		
		Integer totalCohortForJuly=kenyaEmrService.getTotalNoOfCohort(julyStartDate,julyEndDate);
		Integer totalCohortForAugust=kenyaEmrService.getTotalNoOfCohort(augustStartDate,augustEndDate);
		Integer totalCohortForSeptember=kenyaEmrService.getTotalNoOfCohort(septemberStartDate,septemberEndDate);
		Integer totalCohortForOctober=kenyaEmrService.getTotalNoOfCohort(octoberStartDate,octoberStartDate);
		Integer totalCohortForNovember=kenyaEmrService.getTotalNoOfCohort(novemberStartDate,novemberStartDate);
		Integer totalCohortForDecember=kenyaEmrService.getTotalNoOfCohort(decemberStartDate,decemberEndDate);
		
		Integer maleCohortForJuly=kenyaEmrService.getCohortBasedOnGender("M",julyStartDate,julyEndDate);
		Integer maleCohortForAugust=kenyaEmrService.getCohortBasedOnGender("M",augustStartDate,augustEndDate);
		Integer maleCohortForSeptember=kenyaEmrService.getCohortBasedOnGender("M",septemberStartDate,septemberEndDate);
		Integer maleCohortForOctober=kenyaEmrService.getCohortBasedOnGender("M",octoberStartDate,octoberStartDate);
		Integer maleCohortForNovember=kenyaEmrService.getCohortBasedOnGender("M",novemberStartDate,novemberStartDate);
		Integer maleCohortForDecember=kenyaEmrService.getCohortBasedOnGender("M",decemberStartDate,decemberEndDate);
		
		Integer femaleCohortForJuly=kenyaEmrService.getCohortBasedOnGender("F",julyStartDate,julyEndDate);
		Integer femaleCohortForAugust=kenyaEmrService.getCohortBasedOnGender("F",augustStartDate,augustEndDate);
		Integer femaleCohortForSeptember=kenyaEmrService.getCohortBasedOnGender("F",septemberStartDate,septemberEndDate);
		Integer femaleCohortForOctober=kenyaEmrService.getCohortBasedOnGender("F",octoberStartDate,octoberStartDate);
		Integer femaleCohortForNovember=kenyaEmrService.getCohortBasedOnGender("F",novemberStartDate,novemberStartDate);
		Integer femaleCohortForDecember=kenyaEmrService.getCohortBasedOnGender("F",decemberStartDate,decemberEndDate);
		
		Integer age1=0;
		Integer age2=14;
		Integer cohortFor0_14AgeForJuly=kenyaEmrService.getCohortBasedOnGender("F",julyStartDate,julyEndDate);
		Integer cohortFor0_14AgeForAugust=kenyaEmrService.getCohortBasedOnGender("F",augustStartDate,augustEndDate);
		Integer cohortFor0_14AgeForSeptember=kenyaEmrService.getCohortBasedOnGender("F",septemberStartDate,septemberEndDate);
		Integer cohortFor0_14AgeForOctober=kenyaEmrService.getCohortBasedOnGender("F",octoberStartDate,octoberStartDate);
		Integer cohortFor0_14AgeForNovember=kenyaEmrService.getCohortBasedOnGender("F",novemberStartDate,novemberStartDate);
		Integer cohortFor0_14AgeForDecember=kenyaEmrService.getCohortBasedOnGender("F",decemberStartDate,decemberEndDate);
		
		age1=15;
		age2=24;
		Integer cohortFor15_24AgeForJuly=kenyaEmrService.getCohortBasedOnGender("F",julyStartDate,julyEndDate);
		Integer cohortFor15_24AgeForAugust=kenyaEmrService.getCohortBasedOnGender("F",augustStartDate,augustEndDate);
		Integer cohortFor15_24AgeForSeptember=kenyaEmrService.getCohortBasedOnGender("F",septemberStartDate,septemberEndDate);
		Integer cohortFor15_24AgeForOctober=kenyaEmrService.getCohortBasedOnGender("F",octoberStartDate,octoberStartDate);
		Integer cohortFor15_24AgeForNovember=kenyaEmrService.getCohortBasedOnGender("F",novemberStartDate,novemberStartDate);
		Integer cohortFor15_24AgeForDecember=kenyaEmrService.getCohortBasedOnGender("F",decemberStartDate,decemberEndDate);
		
		age1=25;
		age2=60;
		Integer cohortFor25_60AgeForJuly=kenyaEmrService.getCohortBasedOnGender("F",julyStartDate,julyEndDate);
		Integer cohortFor25_60AgeForAugust=kenyaEmrService.getCohortBasedOnGender("F",augustStartDate,augustEndDate);
		Integer cohortFor25_60AgeForSeptember=kenyaEmrService.getCohortBasedOnGender("F",septemberStartDate,septemberEndDate);
		Integer cohortFor25_60AgeForOctober=kenyaEmrService.getCohortBasedOnGender("F",octoberStartDate,octoberStartDate);
		Integer cohortFor25_60AgeForNovember=kenyaEmrService.getCohortBasedOnGender("F",novemberStartDate,novemberStartDate);
		Integer cohortFor25_60AgeForDecember=kenyaEmrService.getCohortBasedOnGender("F",decemberStartDate,decemberEndDate);
		
		List<PatientProgram> noOfCohortAliveAndOnArtForJuly=kenyaEmrService.getNoOfCohortAliveAndOnArt(program,julyStartDate,julyEndDate);
		List<PatientProgram> noOfCohortAliveAndOnArtForAugust=kenyaEmrService.getNoOfCohortAliveAndOnArt(program,augustStartDate,augustEndDate);
		List<PatientProgram> noOfCohortAliveAndOnArtForSeptember=kenyaEmrService.getNoOfCohortAliveAndOnArt(program,septemberStartDate,septemberEndDate);
		List<PatientProgram> noOfCohortAliveAndOnArtForOctober=kenyaEmrService.getNoOfCohortAliveAndOnArt(program,octoberStartDate,octoberStartDate);
		List<PatientProgram> noOfCohortAliveAndOnArtForNovember=kenyaEmrService.getNoOfCohortAliveAndOnArt(program,novemberStartDate,novemberStartDate);
		List<PatientProgram> noOfCohortAliveAndOnArtForDecember=kenyaEmrService.getNoOfCohortAliveAndOnArt(program,decemberStartDate,decemberEndDate);
		
		List<DrugOrderProcessed> noOfOriginalFirstLineRegimenForJuly=kenyaEmrService.getOriginalFirstLineRegimen(julyStartDate,julyEndDate);
		List<DrugOrderProcessed> noOfOriginalFirstLineRegimenForAugust=kenyaEmrService.getOriginalFirstLineRegimen(augustStartDate,augustEndDate);
		List<DrugOrderProcessed> noOfOriginalFirstLineRegimenForSeptember=kenyaEmrService.getOriginalFirstLineRegimen(septemberStartDate,septemberEndDate);
		List<DrugOrderProcessed> noOfOriginalFirstLineRegimenForOctober=kenyaEmrService.getOriginalFirstLineRegimen(octoberStartDate,octoberStartDate);
		List<DrugOrderProcessed> noOfOriginalFirstLineRegimenForNovember=kenyaEmrService.getOriginalFirstLineRegimen(novemberStartDate,novemberStartDate);
		List<DrugOrderProcessed> noOfOriginalFirstLineRegimenForDecember=kenyaEmrService.getOriginalFirstLineRegimen(decemberStartDate,decemberEndDate);
		
		List<DrugOrderProcessed> noOfAlternateFirstLineRegimenForJuly=kenyaEmrService.getAlternateFirstLineRegimen(julyStartDate,julyEndDate);
		List<DrugOrderProcessed> noOfAlternateFirstLineRegimenForAugust=kenyaEmrService.getAlternateFirstLineRegimen(augustStartDate,augustEndDate);
		List<DrugOrderProcessed> noOfAlternateFirstLineRegimenForSeptember=kenyaEmrService.getAlternateFirstLineRegimen(septemberStartDate,septemberEndDate);
		List<DrugOrderProcessed> noOfAlternateFirstLineRegimenForOctober=kenyaEmrService.getAlternateFirstLineRegimen(octoberStartDate,octoberStartDate);
		List<DrugOrderProcessed> noOfAlternateFirstLineRegimenForNovember=kenyaEmrService.getAlternateFirstLineRegimen(novemberStartDate,novemberStartDate);
		List<DrugOrderProcessed> noOfAlternateFirstLineRegimenForDecember=kenyaEmrService.getAlternateFirstLineRegimen(decemberStartDate,decemberEndDate);
		
		List<DrugOrderProcessed> noOfSecondLineRegimenForJuly=kenyaEmrService.getSecondLineRegimen(julyStartDate,julyEndDate);
		List<DrugOrderProcessed> noOfSecondLineRegimenForAugust=kenyaEmrService.getSecondLineRegimen(augustStartDate,augustEndDate);
		List<DrugOrderProcessed> noOfSecondLineRegimenForSeptember=kenyaEmrService.getSecondLineRegimen(septemberStartDate,septemberEndDate);
		List<DrugOrderProcessed> noOfSecondLineRegimenForOctober=kenyaEmrService.getSecondLineRegimen(octoberStartDate,octoberStartDate);
		List<DrugOrderProcessed> noOfSecondLineRegimenForNovember=kenyaEmrService.getSecondLineRegimen(novemberStartDate,novemberStartDate);
		List<DrugOrderProcessed> noOfSecondLineRegimenForDecember=kenyaEmrService.getSecondLineRegimen(decemberStartDate,decemberEndDate);
		
		List<PatientProgram> noOfArtStoppedCohortForJuly=kenyaEmrService.getNoOfArtStoppedCohort(program,julyStartDate,julyEndDate);
		List<PatientProgram> noOfArtStoppedCohortForAugust=kenyaEmrService.getNoOfArtStoppedCohort(program,augustStartDate,augustEndDate);
		List<PatientProgram> noOfArtStoppedCohortForSeptember=kenyaEmrService.getNoOfArtStoppedCohort(program,septemberStartDate,septemberEndDate);
		List<PatientProgram> noOfArtStoppedCohortForOctober=kenyaEmrService.getNoOfArtStoppedCohort(program,octoberStartDate,octoberStartDate);
		List<PatientProgram> noOfArtStoppedCohortForNovember=kenyaEmrService.getNoOfArtStoppedCohort(program,novemberStartDate,novemberStartDate);
		List<PatientProgram> noOfArtStoppedCohortForForDecember=kenyaEmrService.getNoOfArtStoppedCohort(program,decemberStartDate,decemberEndDate);
		
		List<PatientProgram> noOfArtDiedCohortForJuly=kenyaEmrService.getNoOfArtDiedCohort(program,julyStartDate,julyEndDate);
		List<PatientProgram> noOfArtDiedCohortForAugust=kenyaEmrService.getNoOfArtDiedCohort(program,augustStartDate,augustEndDate);
		List<PatientProgram> noOfArtDiedCohortForSeptember=kenyaEmrService.getNoOfArtDiedCohort(program,septemberStartDate,septemberEndDate);
		List<PatientProgram> noOfArtDiedCohortForOctober=kenyaEmrService.getNoOfArtDiedCohort(program,octoberStartDate,octoberStartDate);
		List<PatientProgram> noOfArtDiedCohortForNovember=kenyaEmrService.getNoOfArtDiedCohort(program,novemberStartDate,novemberStartDate);
		List<PatientProgram> noOfArtDiedCohortForDecember=kenyaEmrService.getNoOfArtDiedCohort(program,decemberStartDate,decemberEndDate);
		
		List<Obs> noOfPatientLostToFollowUpForJuly=kenyaEmrService.getNoOfPatientLostToFollowUp(julyStartDate,julyEndDate);
		List<Obs> noOfPatientLostToFollowUpForAugust=kenyaEmrService.getNoOfPatientLostToFollowUp(augustStartDate,augustEndDate);
		List<Obs> noOfPatientLostToFollowUpForSeptember=kenyaEmrService.getNoOfPatientLostToFollowUp(septemberStartDate,septemberEndDate);
		List<Obs> noOfPatientLostToFollowUpForOctober=kenyaEmrService.getNoOfPatientLostToFollowUp(octoberStartDate,octoberStartDate);
		List<Obs> noOfPatientLostToFollowUpForNovember=kenyaEmrService.getNoOfPatientLostToFollowUp(novemberStartDate,novemberStartDate);
		List<Obs> noOfPatientLostToFollowUpForDecember=kenyaEmrService.getNoOfPatientLostToFollowUp(decemberStartDate,decemberEndDate);
		
		List<Obs> noOfPatientWithCD4ForJuly=kenyaEmrService.getNoOfPatientWithCD4(julyStartDate,julyEndDate);
		List<Obs> noOfPatientWithCD4ForAugust=kenyaEmrService.getNoOfPatientWithCD4(augustStartDate,augustEndDate);
		List<Obs> noOfPatientWithCD4ForSeptember=kenyaEmrService.getNoOfPatientWithCD4(septemberStartDate,septemberEndDate);
		List<Obs> noOfPatientWithCD4ForOctober=kenyaEmrService.getNoOfPatientWithCD4(octoberStartDate,octoberStartDate);
		List<Obs> noOfPatientWithCD4ForNovember=kenyaEmrService.getNoOfPatientWithCD4(novemberStartDate,novemberStartDate);
		List<Obs> noOfPatientWithCD4ForDecember=kenyaEmrService.getNoOfPatientWithCD4(decemberStartDate,decemberEndDate);
		
		List<Obs> noOfPatientNormalActivityForJuly=kenyaEmrService.getNoOfPatientNormalActivity(julyStartDate,julyEndDate);
		List<Obs> noOfPatientNormalActivityForAugust=kenyaEmrService.getNoOfPatientNormalActivity(augustStartDate,augustEndDate);
		List<Obs> noOfPatientNormalActivityForSeptember=kenyaEmrService.getNoOfPatientNormalActivity(septemberStartDate,septemberEndDate);
		List<Obs> noOfPatientNormalActivityForOctober=kenyaEmrService.getNoOfPatientNormalActivity(octoberStartDate,octoberStartDate);
		List<Obs> noOfPatientNormalActivityForNovember=kenyaEmrService.getNoOfPatientNormalActivity(novemberStartDate,novemberStartDate);
		List<Obs> noOfPatientNormalActivityForDecember=kenyaEmrService.getNoOfPatientNormalActivity(decemberStartDate,decemberEndDate);
		
		List<Obs> noOfPatientBedriddenLessThanFiftyForJuly=kenyaEmrService.getNoOfPatientBedriddenLessThanFifty(julyStartDate,julyEndDate);
		List<Obs> noOfPatientBedriddenLessThanFiftyForAugust=kenyaEmrService.getNoOfPatientBedriddenLessThanFifty(augustStartDate,augustEndDate);
		List<Obs> noOfPatientBedriddenLessThanFiftyForSeptember=kenyaEmrService.getNoOfPatientBedriddenLessThanFifty(septemberStartDate,septemberEndDate);
		List<Obs> noOfPatientBedriddenLessThanFiftyForOctober=kenyaEmrService.getNoOfPatientBedriddenLessThanFifty(octoberStartDate,octoberStartDate);
		List<Obs> noOfPatientBedriddenLessThanFiftyForNovember=kenyaEmrService.getNoOfPatientBedriddenLessThanFifty(novemberStartDate,novemberStartDate);
		List<Obs> noOfPatientBedriddenLessThanFiftyForDecember=kenyaEmrService.getNoOfPatientBedriddenLessThanFifty(decemberStartDate,decemberEndDate);
		
		List<Obs> noOfPatientBedriddenMoreThanFiftyForJuly=kenyaEmrService.getNoOfPatientBedriddenMoreThanFifty(julyStartDate,julyEndDate);
		List<Obs> noOfPatientBedriddenMoreThanFiftyForAugust=kenyaEmrService.getNoOfPatientBedriddenMoreThanFifty(augustStartDate,augustEndDate);
		List<Obs> noOfPatientBedriddenMoreThanFiftyForSeptember=kenyaEmrService.getNoOfPatientBedriddenMoreThanFifty(septemberStartDate,septemberEndDate);
		List<Obs> noOfPatientBedriddenMoreThanFiftyForOctober=kenyaEmrService.getNoOfPatientBedriddenMoreThanFifty(octoberStartDate,octoberStartDate);
		List<Obs> noOfPatientBedriddenMoreThanFiftyForNovember=kenyaEmrService.getNoOfPatientBedriddenMoreThanFifty(novemberStartDate,novemberStartDate);
		List<Obs> noOfPatientBedriddenMoreThanFiftyForDecember=kenyaEmrService.getNoOfPatientBedriddenMoreThanFifty(decemberStartDate,decemberEndDate);
		
		List<Obs> noOfPatientPickedUpArvForSixMonthForJuly=kenyaEmrService.getNoOfPatientPickedUpArvForSixMonth(julyStartDate,julyEndDate);
		List<Obs> noOfPatientPickedUpArvForSixMonthForAugust=kenyaEmrService.getNoOfPatientPickedUpArvForSixMonth(augustStartDate,augustEndDate);
		List<Obs> noOfPatientPickedUpArvForSixMonthForSeptember=kenyaEmrService.getNoOfPatientPickedUpArvForSixMonth(septemberStartDate,septemberEndDate);
		List<Obs> noOfPatientPickedUpArvForSixMonthForOctober=kenyaEmrService.getNoOfPatientPickedUpArvForSixMonth(octoberStartDate,octoberStartDate);
		List<Obs> noOfPatientPickedUpArvForSixMonthForNovember=kenyaEmrService.getNoOfPatientPickedUpArvForSixMonth(novemberStartDate,novemberStartDate);
		List<Obs> noOfPatientPickedUpArvForSixMonthForDecember=kenyaEmrService.getNoOfPatientPickedUpArvForSixMonth(decemberStartDate,decemberEndDate);
		
		List<Obs> noOfPatientPickedUpArvForTwelveMonthForJuly=kenyaEmrService.getNoOfPatientPickedUpArvForTwelveMonth(julyStartDate,julyEndDate);
		List<Obs> noOfPatientPickedUpArvForTwelveMonthForAugust=kenyaEmrService.getNoOfPatientPickedUpArvForTwelveMonth(augustStartDate,augustEndDate);
		List<Obs> noOfPatientPickedUpArvForTwelveMonthForSeptember=kenyaEmrService.getNoOfPatientPickedUpArvForTwelveMonth(septemberStartDate,septemberEndDate);
		List<Obs> noOfPatientPickedUpArvForTwelveMonthForOctober=kenyaEmrService.getNoOfPatientPickedUpArvForTwelveMonth(octoberStartDate,octoberStartDate);
		List<Obs> noOfPatientPickedUpArvForTwelveMonthForNovember=kenyaEmrService.getNoOfPatientPickedUpArvForTwelveMonth(novemberStartDate,novemberStartDate);
		List<Obs> noOfPatientPickedUpArvForTwelveMonthForDecember=kenyaEmrService.getNoOfPatientPickedUpArvForTwelveMonth(decemberStartDate,decemberEndDate);
		
		model.addAttribute("patientProgramForJuly",patientProgramForJuly.size());
		model.addAttribute("patientProgramForAugust",patientProgramForAugust.size());
		model.addAttribute("patientProgramForSeptember",patientProgramForSeptember.size());
		model.addAttribute("patientProgramForOctober",patientProgramForOctober.size());
		model.addAttribute("patientProgramForNovember",patientProgramForNovember.size());
		model.addAttribute("patientProgramForDecember",patientProgramForDecember.size());
	
		model.addAttribute("patientTransferInForJuly",patientTransferInForJuly.size());
		model.addAttribute("patientTransferInForAugust",patientTransferInForAugust.size());
		model.addAttribute("patientTransferInForSeptember",patientTransferInForSeptember.size());
		model.addAttribute("patientTransferInForOctober",patientTransferInForOctober.size());
		model.addAttribute("patientTransferInForNovember",patientTransferInForNovember.size());
		model.addAttribute("patientTransferInForDecember",patientTransferInForDecember.size());
		
		model.addAttribute("patientTransferOutForJuly",patientTransferOutForJuly.size());
		model.addAttribute("patientTransferOutForAugust",patientTransferOutForAugust.size());
		model.addAttribute("patientTransferOutForSeptember",patientTransferOutForSeptember.size());
		model.addAttribute("patientTransferOutForOctober",patientTransferOutForOctober.size());
		model.addAttribute("patientTransferOutForNovember",patientTransferOutForNovember.size());
		model.addAttribute("patientTransferOutForDecember",patientTransferOutForDecember.size());
		
		model.addAttribute("patientProgramForJuly",patientProgramForJuly.size());
		model.addAttribute("patientProgramForAugust",patientProgramForAugust.size());
		model.addAttribute("patientProgramForSeptember",patientProgramForSeptember.size());
		model.addAttribute("patientProgramForOctober",patientProgramForOctober.size());
		model.addAttribute("patientProgramForNovember",patientProgramForNovember.size());
		model.addAttribute("patientProgramForDecember",patientProgramForDecember.size());
		
		model.addAttribute("patientTransferInForJuly",patientTransferInForJuly.size());
		model.addAttribute("patientTransferInForAugust",patientTransferInForAugust.size());
		model.addAttribute("patientTransferInForSeptember",patientTransferInForSeptember.size());
		model.addAttribute("patientTransferInForOctober",patientTransferInForOctober.size());
		model.addAttribute("patientTransferInForNovember",patientTransferInForNovember.size());
		model.addAttribute("patientTransferInForDecember",patientTransferInForDecember.size());
		
		model.addAttribute("patientTransferOutForJuly",patientTransferOutForJuly.size());
		model.addAttribute("patientTransferOutForAugust",patientTransferOutForAugust.size());
		model.addAttribute("patientTransferOutForSeptember",patientTransferOutForSeptember.size());
		model.addAttribute("patientTransferOutForOctober",patientTransferOutForOctober.size());
		model.addAttribute("patientTransferOutForNovember",patientTransferOutForNovember.size());
		model.addAttribute("patientTransferOutForDecember",patientTransferOutForDecember.size());
		
		model.addAttribute("totalCohortForJuly",totalCohortForJuly.SIZE);
		model.addAttribute("totalCohortForAugust",totalCohortForAugust.SIZE);
		model.addAttribute("totalCohortForSeptember",totalCohortForSeptember.SIZE);
		model.addAttribute("totalCohortForOctober",totalCohortForOctober.SIZE);
		model.addAttribute("totalCohortForNovember",totalCohortForNovember.SIZE);
		model.addAttribute("totalCohortForDecember",totalCohortForDecember.SIZE);
		
		model.addAttribute("maleCohortForJuly",maleCohortForJuly.SIZE);
		model.addAttribute("maleCohortForAugust",maleCohortForAugust.SIZE);
		model.addAttribute("maleCohortForSeptember",maleCohortForSeptember.SIZE);
		model.addAttribute("maleCohortForOctober",maleCohortForOctober.SIZE);
		model.addAttribute("maleCohortForNovember",maleCohortForNovember.SIZE);
		model.addAttribute("maleCohortForDecember",maleCohortForDecember.SIZE);
		
		model.addAttribute("femaleCohortForJuly",femaleCohortForJuly.SIZE);
		model.addAttribute("femaleCohortForAugust",femaleCohortForAugust.SIZE);
		model.addAttribute("femaleCohortForSeptember",femaleCohortForSeptember.SIZE);
		model.addAttribute("femaleCohortForOctober",femaleCohortForOctober.SIZE);
		model.addAttribute("femaleCohortForNovember",femaleCohortForNovember.SIZE);
		model.addAttribute("femaleCohortForDecember",femaleCohortForDecember.SIZE);
		
		model.addAttribute("cohortFor0_14AgeForJuly",cohortFor0_14AgeForJuly.SIZE);
		model.addAttribute("cohortFor0_14AgeForAugust",cohortFor0_14AgeForAugust.SIZE);
		model.addAttribute("cohortFor0_14AgeForSeptember",cohortFor0_14AgeForSeptember.SIZE);
		model.addAttribute("cohortFor0_14AgeForOctober",cohortFor0_14AgeForOctober.SIZE);
		model.addAttribute("cohortFor0_14AgeForNovember",cohortFor0_14AgeForNovember.SIZE);
		model.addAttribute("cohortFor0_14AgeForDecember",cohortFor0_14AgeForDecember.SIZE);
		
		model.addAttribute("cohortFor15_24AgeForJuly",cohortFor15_24AgeForJuly.SIZE);
		model.addAttribute("cohortFor15_24AgeForAugust",cohortFor15_24AgeForAugust.SIZE);
		model.addAttribute("cohortFor15_24AgeForSeptember",cohortFor15_24AgeForSeptember.SIZE);
		model.addAttribute("cohortFor15_24AgeForOctober",cohortFor15_24AgeForOctober.SIZE);
		model.addAttribute("cohortFor15_24AgeForNovember",cohortFor15_24AgeForNovember.SIZE);
		model.addAttribute("cohortFor15_24AgeForDecember",cohortFor15_24AgeForDecember.SIZE);
		
		model.addAttribute("cohortFor25_60AgeForJuly",cohortFor25_60AgeForJuly.SIZE);
		model.addAttribute("cohortFor25_60AgeForAugust",cohortFor25_60AgeForAugust.SIZE);
		model.addAttribute("cohortFor25_60AgeForSeptember",cohortFor25_60AgeForSeptember.SIZE);
		model.addAttribute("cohortFor25_60AgeForOctober",cohortFor25_60AgeForOctober.SIZE);
		model.addAttribute("cohortFor25_60AgeForNovember",cohortFor25_60AgeForNovember.SIZE);
		model.addAttribute("cohortFor25_60AgeForDecember",cohortFor25_60AgeForDecember.SIZE);
		
		model.addAttribute("noOfCohortAliveAndOnArtForJuly",noOfCohortAliveAndOnArtForJuly.size());
		model.addAttribute("noOfCohortAliveAndOnArtForAugust",noOfCohortAliveAndOnArtForAugust.size());
		model.addAttribute("noOfCohortAliveAndOnArtForSeptember",noOfCohortAliveAndOnArtForSeptember.size());
		model.addAttribute("noOfCohortAliveAndOnArtForOctober",noOfCohortAliveAndOnArtForOctober.size());
		model.addAttribute("noOfCohortAliveAndOnArtNovember",noOfCohortAliveAndOnArtForNovember.size());
		model.addAttribute("noOfCohortAliveAndOnArtForDecember",noOfCohortAliveAndOnArtForDecember.size());
		
		model.addAttribute("noOfOriginalFirstLineRegimenForJuly",noOfOriginalFirstLineRegimenForJuly.size());
		model.addAttribute("noOfOriginalFirstLineRegimenForAugust",noOfOriginalFirstLineRegimenForAugust.size());
		model.addAttribute("noOfOriginalFirstLineRegimenForSeptember",noOfOriginalFirstLineRegimenForSeptember.size());
		model.addAttribute("noOfOriginalFirstLineRegimenForOctober",noOfOriginalFirstLineRegimenForOctober.size());
		model.addAttribute("noOfOriginalFirstLineRegimenNovember",noOfOriginalFirstLineRegimenForNovember.size());
		model.addAttribute("noOfOriginalFirstLineRegimenForDecember",noOfCohortAliveAndOnArtForDecember.size());
		
		model.addAttribute("noOfAlternateFirstLineRegimenForJuly",noOfAlternateFirstLineRegimenForJuly.size());
		model.addAttribute("noOfAlternateFirstLineRegimenForAugust",noOfAlternateFirstLineRegimenForAugust.size());
		model.addAttribute("noOfAlternateFirstLineRegimenForSeptember",noOfAlternateFirstLineRegimenForSeptember.size());
		model.addAttribute("noOfAlternateFirstLineRegimenForOctober",noOfAlternateFirstLineRegimenForOctober.size());
		model.addAttribute("noOfAlternateFirstLineRegimenNovember",noOfAlternateFirstLineRegimenForNovember.size());
		model.addAttribute("noOfAlternateFirstLineRegimenForDecember",noOfAlternateFirstLineRegimenForDecember.size());
		
		model.addAttribute("noOfSecondLineRegimenForJuly",noOfSecondLineRegimenForJuly.size());
		model.addAttribute("noOfSecondLineRegimenForAugust",noOfSecondLineRegimenForAugust.size());
		model.addAttribute("noOfSecondLineRegimenForSeptember",noOfSecondLineRegimenForSeptember.size());
		model.addAttribute("noOfSecondLineRegimenForOctober",noOfSecondLineRegimenForOctober.size());
		model.addAttribute("noOfSecondLineRegimenNovember",noOfSecondLineRegimenForNovember.size());
		model.addAttribute("noOfSecondLineRegimenForDecember",noOfCohortAliveAndOnArtForDecember.size());
		
		model.addAttribute("noOfArtStoppedCohortForJuly",noOfArtStoppedCohortForJuly.size());
		model.addAttribute("noOfArtStoppedCohortForAugust",noOfArtStoppedCohortForAugust.size());
		model.addAttribute("noOfArtStoppedCohortForSeptember",noOfArtStoppedCohortForSeptember.size());
		model.addAttribute("noOfArtStoppedCohortForOctober",noOfArtStoppedCohortForOctober.size());
		model.addAttribute("noOfArtStoppedCohortNovember",noOfArtStoppedCohortForNovember.size());
		model.addAttribute("noOfArtStoppedCohortForDecember",noOfCohortAliveAndOnArtForDecember.size());
		
		model.addAttribute("noOfArtDiedCohortForJuly",noOfArtDiedCohortForJuly.size());
		model.addAttribute("noOfArtDiedCohortForAugust",noOfArtDiedCohortForAugust.size());
		model.addAttribute("noOfArtDiedCohortForSeptember",noOfArtDiedCohortForSeptember.size());
		model.addAttribute("noOfArtDiedCohortForOctober",noOfArtDiedCohortForOctober.size());
		model.addAttribute("noOfArtDiedCohortNovember",noOfArtDiedCohortForNovember.size());
		model.addAttribute("noOfArtDiedCohortForDecember",noOfArtDiedCohortForDecember.size());
		
		model.addAttribute("noOfPatientLostToFollowUpForJuly",noOfPatientLostToFollowUpForJuly.size());
		model.addAttribute("noOfPatientLostToFollowUpForAugust",noOfPatientLostToFollowUpForAugust.size());
		model.addAttribute("noOfPatientLostToFollowUpForSeptember",noOfPatientLostToFollowUpForSeptember.size());
		model.addAttribute("noOfPatientLostToFollowUpForOctober",noOfPatientLostToFollowUpForOctober.size());
		model.addAttribute("noOfPatientLostToFollowUpNovember",noOfPatientLostToFollowUpForNovember.size());
		model.addAttribute("noOfPatientLostToFollowUpForDecember",noOfPatientLostToFollowUpForDecember.size());
		
		model.addAttribute("noOfPatientWithCD4ForJuly",noOfPatientWithCD4ForJuly.size());
		model.addAttribute("noOfPatientWithCD4ForAugust",noOfPatientWithCD4ForAugust.size());
		model.addAttribute("noOfPatientWithCD4ForSeptember",noOfPatientWithCD4ForSeptember.size());
		model.addAttribute("noOfPatientWithCD4ForOctober",noOfPatientWithCD4ForOctober.size());
		model.addAttribute("noOfPatientWithCD4November",noOfPatientWithCD4ForNovember.size());
		model.addAttribute("noOfPatientWithCD4ForDecember",noOfPatientWithCD4ForDecember.size());
		
		model.addAttribute("noOfPatientNormalActivityForJuly",noOfPatientNormalActivityForJuly.size());
		model.addAttribute("noOfPatientNormalActivityForAugust",noOfPatientNormalActivityForAugust.size());
		model.addAttribute("noOfPatientNormalActivityForSeptember",noOfPatientNormalActivityForSeptember.size());
		model.addAttribute("noOfPatientNormalActivityForOctober",noOfPatientNormalActivityForOctober.size());
		model.addAttribute("noOfPatientNormalActivityNovember",noOfPatientNormalActivityForNovember.size());
		model.addAttribute("noOfPatientNormalActivityForDecember",noOfPatientNormalActivityForDecember.size());
		
		model.addAttribute("noOfPatientBedriddenLessThanFiftyForJuly",noOfPatientBedriddenLessThanFiftyForJuly.size());
		model.addAttribute("noOfPatientBedriddenLessThanFiftyForAugust",noOfPatientBedriddenLessThanFiftyForAugust.size());
		model.addAttribute("noOfPatientBedriddenLessThanFiftyForSeptember",noOfPatientBedriddenLessThanFiftyForSeptember.size());
		model.addAttribute("noOfPatientBedriddenLessThanFiftyForOctober",noOfPatientBedriddenLessThanFiftyForOctober.size());
		model.addAttribute("noOfPatientBedriddenLessThanFiftyNovember",noOfPatientBedriddenLessThanFiftyForNovember.size());
		model.addAttribute("noOfPatientBedriddenLessThanFiftyForDecember",noOfPatientBedriddenLessThanFiftyForDecember.size());
		
		model.addAttribute("noOfPatientBedriddenMoreThanFiftyForJuly",noOfPatientBedriddenMoreThanFiftyForJuly.size());
		model.addAttribute("noOfPatientBedriddenMoreThanFiftyForAugust",noOfPatientBedriddenMoreThanFiftyForAugust.size());
		model.addAttribute("noOfPatientBedriddenMoreThanFiftyForSeptember",noOfPatientBedriddenMoreThanFiftyForSeptember.size());
		model.addAttribute("noOfPatientBedriddenMoreThanFiftyForOctober",noOfPatientBedriddenMoreThanFiftyForOctober.size());
		model.addAttribute("noOfPatientBedriddenMoreThanFiftyNovember",noOfPatientBedriddenMoreThanFiftyForNovember.size());
		model.addAttribute("noOfPatientBedriddenMoreThanFiftyForDecember",noOfPatientBedriddenMoreThanFiftyForDecember.size());
		
		model.addAttribute("noOfPatientPickedUpArvForSixMonthForJuly",noOfPatientPickedUpArvForSixMonthForJuly.size());
		model.addAttribute("noOfPatientPickedUpArvForSixMonthForAugust",noOfPatientPickedUpArvForSixMonthForAugust.size());
		model.addAttribute("noOfPatientPickedUpArvForSixMonthForSeptember",noOfPatientPickedUpArvForSixMonthForSeptember.size());
		model.addAttribute("noOfPatientPickedUpArvForSixMonthForOctober",noOfPatientPickedUpArvForSixMonthForOctober.size());
		model.addAttribute("noOfPatientPickedUpArvForSixMonthNovember",noOfPatientPickedUpArvForSixMonthForNovember.size());
		model.addAttribute("noOfPatientPickedUpArvForSixMonthForDecember",noOfPatientPickedUpArvForSixMonthForDecember.size());
		
		model.addAttribute("noOfPatientPickedUpArvForTwelveMonthForJuly",noOfPatientPickedUpArvForTwelveMonthForJuly.size());
		model.addAttribute("noOfPatientPickedUpArvForTwelveMonthForAugust",noOfPatientPickedUpArvForTwelveMonthForAugust.size());
		model.addAttribute("noOfPatientPickedUpArvForTwelveMonthForSeptember",noOfPatientPickedUpArvForTwelveMonthForSeptember.size());
		model.addAttribute("noOfPatientPickedUpArvForTwelveMonthForOctober",noOfPatientPickedUpArvForTwelveMonthForOctober.size());
		model.addAttribute("noOfPatientPickedUpArvForTwelveMonthNovember",noOfPatientPickedUpArvForTwelveMonthForNovember.size());
		model.addAttribute("noOfPatientPickedUpArvForTwelveMonthForDecember",noOfPatientPickedUpArvForTwelveMonthForDecember.size());
	 }
		
		model.addAttribute("year",year);
		model.addAttribute("halfYearly",halfYearly);
   }
}
