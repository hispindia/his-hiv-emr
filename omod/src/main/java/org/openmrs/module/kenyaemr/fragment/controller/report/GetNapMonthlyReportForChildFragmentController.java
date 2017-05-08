package org.openmrs.module.kenyaemr.fragment.controller.report;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openmrs.api.context.Context;
import org.openmrs.module.kenyaemr.api.KenyaEmrService;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

public class GetNapMonthlyReportForChildFragmentController {
	public void controller(@RequestParam("startDate") Date startDate,
			@RequestParam("endDate") Date endDate,
			@RequestParam("ageCategory") String ageCategory,
			FragmentModel model, UiUtils ui) {
    KenyaEmrService kenyaEmrService = (KenyaEmrService) Context.getService(KenyaEmrService.class);
	model.addAttribute("patientcount",kenyaEmrService.getPatientCount());
	SimpleDateFormat formatterExt = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat formatterExtt = new SimpleDateFormat("dd-MMM-yyyy");
	String startOfPeriod = formatterExt.format(startDate);
	String endOfPeriod = formatterExt.format(endDate);
	
	Integer noOfNewPatientEnrolledInHivCareForMale=kenyaEmrService.getNoOfNewPatientEnrolledInHivCare("M",ageCategory,startOfPeriod,endOfPeriod);
	Integer noOfNewPatientEnrolledInHivCareForFemale=kenyaEmrService.getNoOfNewPatientEnrolledInHivCare("F",ageCategory,startOfPeriod,endOfPeriod);
	
	Integer noOfPatientTreatedForOpportunisticInfectionsForMale=kenyaEmrService.getNoOfPatientTreatedForOpportunisticInfections("M",ageCategory,startOfPeriod,endOfPeriod);
	Integer noOfPatientTreatedForOpportunisticInfectionsForFemale=kenyaEmrService.getNoOfPatientTreatedForOpportunisticInfections("F",ageCategory,startOfPeriod,endOfPeriod);
	
	Integer noOfMedicallyEligiblePatientsWaitingForARTForMale=kenyaEmrService.getNoOfMedicallyEligiblePatientsWaitingForART("M",ageCategory,startOfPeriod,endOfPeriod);
	Integer noOfMedicallyEligiblePatientsWaitingForARTForFemale=kenyaEmrService.getNoOfMedicallyEligiblePatientsWaitingForART("F",ageCategory,startOfPeriod,endOfPeriod);
	
	Integer cumulativeNoOfActiveFollowUpPatientsStartedAtBegOfMonthForMale=kenyaEmrService.getCumulativeNoOfActiveFollowUpPatientsStartedAtBegOfMonth("M",ageCategory,startOfPeriod,endOfPeriod);
	Integer cumulativeNoOfActiveFollowUpPatientsStartedAtBegOfMonthForFemale=kenyaEmrService.getCumulativeNoOfActiveFollowUpPatientsStartedAtBegOfMonth("F",ageCategory,startOfPeriod,endOfPeriod);
	
	Integer noOfNewPatientsStartedOnARTForMale=kenyaEmrService.getNoOfNewPatientsStartedOnART("M",ageCategory,startOfPeriod,endOfPeriod);
	Integer noOfNewPatientsStartedOnARTForFemale=kenyaEmrService.getNoOfNewPatientsStartedOnART("F",ageCategory,startOfPeriod,endOfPeriod);
	
	Integer noOfPatientsOnARTTransferredInForMale=kenyaEmrService.getNoOfPatientsOnARTTransferredIn("M",ageCategory,startOfPeriod,endOfPeriod);
	Integer noOfPatientsOnARTTransferredInForFemale=kenyaEmrService.getNoOfPatientsOnARTTransferredIn("F",ageCategory,startOfPeriod,endOfPeriod);
	
	Integer noOfCumulativeNoOfActiveFollowUpPatientsStartedAtEndOfMonthForMale=kenyaEmrService.getCumulativeNoOfActiveFollowUpPatientsStartedAtEndOfMonth("M",ageCategory,startOfPeriod,endOfPeriod);
	Integer noOfCumulativeNoOfActiveFollowUpPatientsStartedAtEndOfMonthForFemale=kenyaEmrService.getCumulativeNoOfActiveFollowUpPatientsStartedAtEndOfMonth("F",ageCategory,startOfPeriod,endOfPeriod);
	
	Integer noOfDeathReportedForMale=kenyaEmrService.getNoOfDeathReported("M",ageCategory,startOfPeriod,endOfPeriod);
	Integer noOfDeathReportedForFemale=kenyaEmrService.getNoOfDeathReported("F",ageCategory,startOfPeriod,endOfPeriod);
	
	Integer noOfPatientsTransferredOutUnderARVForMale=kenyaEmrService.getNoOfPatientsTransferredOutUnderARV("M",ageCategory,startOfPeriod,endOfPeriod);
	Integer noOfPatientsTransferredOutUnderARVForFemale=kenyaEmrService.getNoOfPatientsTransferredOutUnderARV("F",ageCategory,startOfPeriod,endOfPeriod);
	
	Integer noOfPatientsLostToFollowUpForMale=kenyaEmrService.getNoOfPatientsLostToFollowUp("M",ageCategory,startOfPeriod,endOfPeriod);
	Integer noOfPatientsLostToFollowUpForFemale=kenyaEmrService.getNoOfPatientsLostToFollowUp("F",ageCategory,startOfPeriod,endOfPeriod);
	
	Integer noOfPatientsStopppedARTForMale=kenyaEmrService.getNoOfPatientsStopppedART("M",ageCategory,startOfPeriod,endOfPeriod);
	Integer noOfPatientsStopppedARTForFemale=kenyaEmrService.getNoOfPatientsStopppedART("F",ageCategory,startOfPeriod,endOfPeriod);
	
	Integer noOfPatientsOnARTForMale=kenyaEmrService.getNoOfPatientsOnART("M",ageCategory,startOfPeriod,endOfPeriod);
	Integer noOfPatientsOnARTForFemale=kenyaEmrService.getNoOfPatientsOnART("F",ageCategory,startOfPeriod,endOfPeriod);
	
	Integer noOfPatientsOnOriginalFirstLineRegimForMale=kenyaEmrService.getNoOfPatientsOnOriginalFirstLineRegim("M",ageCategory,startOfPeriod,endOfPeriod);
	Integer noOfPatientsOnOriginalFirstLineRegimForFemale=kenyaEmrService.getNoOfPatientsOnOriginalFirstLineRegim("F",ageCategory,startOfPeriod,endOfPeriod);
	
	Integer noOfPatientsSubstitutedFirstLineRegimForMale=kenyaEmrService.getNoOfPatientsSubstitutedFirstLineRegim("M",ageCategory,startOfPeriod,endOfPeriod);
	Integer noOfPatientsSubstitutedFirstLineRegimForFemale=kenyaEmrService.getNoOfPatientsSubstitutedFirstLineRegim("F",ageCategory,startOfPeriod,endOfPeriod);
	
	Integer noOfPatientsSubstitutedSecondLineRegimForMale=kenyaEmrService.getNoOfPatientsSubstitutedSecondLineRegim("M",ageCategory,startOfPeriod,endOfPeriod);
	Integer noOfPatientsSubstitutedSecondLineRegimForFemale=kenyaEmrService.getNoOfPatientsSubstitutedSecondLineRegim("F",ageCategory,startOfPeriod,endOfPeriod);
	
	Integer noOfPatientsSubstitutedThirdLineRegimForMale=kenyaEmrService.getNoOfPatientsSubstitutedThirdLineRegim("M",ageCategory,startOfPeriod,endOfPeriod);
	Integer noOfPatientsSubstitutedThirdLineRegimForFemale=kenyaEmrService.getNoOfPatientsSubstitutedThirdLineRegim("F",ageCategory,startOfPeriod,endOfPeriod);
	
	Integer noOfHIVPositiveTBPatientsForMale=kenyaEmrService.getNoOfHIVPositiveTBPatients("M",ageCategory,startOfPeriod,endOfPeriod);
	Integer noOfHIVPositiveTBPatientsForFemale=kenyaEmrService.getNoOfHIVPositiveTBPatients("F",ageCategory,startOfPeriod,endOfPeriod);
	
	Integer cumulativeNoOfHIVPositiveTBPatientsForMale=kenyaEmrService.getCumulativeNoOfHIVPositiveTBPatients("M",ageCategory,startOfPeriod,endOfPeriod);
	Integer cumulativeNoOfHIVPositiveTBPatientsForFemale=kenyaEmrService.getCumulativeNoOfHIVPositiveTBPatients("F",ageCategory,startOfPeriod,endOfPeriod);
	
	Integer noOfPatientsAssessedForAdherenceDuringThisMonth=kenyaEmrService.getNoOfPatientsAssessedForAdherenceDuringThisMonth(ageCategory,startOfPeriod,endOfPeriod);
	
	Integer noOfPatientsAssessedForAdherenceDuringTheLastMonthLevelOneTot=kenyaEmrService. getNoOfPatientsAssessedForAdherenceDuringTheLastMonthLevelOneTot(ageCategory,startOfPeriod,endOfPeriod);
	
	Integer noOfPatientsAssessedForAdherenceDuringTheLastMonthLevelTwoTot=kenyaEmrService.getNoOfPatientsAssessedForAdherenceDuringTheLastMonthLevelTwoTot(ageCategory,startOfPeriod,endOfPeriod);
	
	Integer noOfPatientsAssessedForAdherenceDuringTheLastMonthLevelThreeTot=kenyaEmrService.getNoOfPatientsAssessedForAdherenceDuringTheLastMonthLevelThreeTot(ageCategory,startOfPeriod,endOfPeriod);
	
	Integer noOfPatientsOnPerformanceScaleAForMale=kenyaEmrService.getNoOfPatientsOnPerformanceScaleA("M",ageCategory,startOfPeriod,endOfPeriod);
	Integer noOfPatientsOnPerformanceScaleAForFemale=kenyaEmrService.getNoOfPatientsOnPerformanceScaleA("F",ageCategory,startOfPeriod,endOfPeriod);
	
	Integer noOfPatientsOnPerformanceScaleBForMale=kenyaEmrService.getNoOfPatientsOnPerformanceScaleB("M",ageCategory,startOfPeriod,endOfPeriod);
	Integer noOfPatientsOnPerformanceScaleBForFemale=kenyaEmrService.getNoOfPatientsOnPerformanceScaleB("F",ageCategory,startOfPeriod,endOfPeriod);
	
	Integer noOfPatientsOnPerformanceScaleCForMale=kenyaEmrService.getNoOfPatientsOnPerformanceScaleC("M",ageCategory,startOfPeriod,endOfPeriod);
	Integer noOfPatientsOnPerformanceScaleCForFemale=kenyaEmrService.getNoOfPatientsOnPerformanceScaleC("F",ageCategory,startOfPeriod,endOfPeriod);
	
	Integer noOfPatientSWithRiskFactorsCodeOneForMale=kenyaEmrService.getNoOfPatientSWithRiskFactorsCodeOne("M",ageCategory,startOfPeriod,endOfPeriod);
	Integer noOfPatientSWithRiskFactorsCodeOneForFemale=kenyaEmrService.getNoOfPatientSWithRiskFactorsCodeOne("F",ageCategory,startOfPeriod,endOfPeriod);
	
	Integer noOfPatientSWithRiskFactorsCodeTwoForMale=kenyaEmrService.getNoOfPatientSWithRiskFactorsCodeTwo("M",ageCategory,startOfPeriod,endOfPeriod);
	Integer noOfPatientSWithRiskFactorsCodeTwoForFemale=kenyaEmrService.getNoOfPatientSWithRiskFactorsCodeTwo("F",ageCategory,startOfPeriod,endOfPeriod);
	
	Integer noOfPatientSWithRiskFactorsCodeThreeForMale=kenyaEmrService.getNoOfPatientSWithRiskFactorsCodeThree("M",ageCategory,startOfPeriod,endOfPeriod);
	Integer noOfPatientSWithRiskFactorsCodeThreeForFemale=kenyaEmrService.getNoOfPatientSWithRiskFactorsCodeThree("F",ageCategory,startOfPeriod,endOfPeriod);
	
	Integer noOfPatientSWithRiskFactorsCodeFourForMale=kenyaEmrService.getNoOfPatientSWithRiskFactorsCodeFour("M",ageCategory,startOfPeriod,endOfPeriod);
	Integer noOfPatientSWithRiskFactorsCodeFourForFemale=kenyaEmrService.getNoOfPatientSWithRiskFactorsCodeFour("F",ageCategory,startOfPeriod,endOfPeriod);
	
	Integer noOfPatientSWithRiskFactorsCodeFiveForMale=kenyaEmrService.getNoOfPatientSWithRiskFactorsCodeFive("M",ageCategory,startOfPeriod,endOfPeriod);
	Integer noOfPatientSWithRiskFactorsCodeFiveForFemale=kenyaEmrService.getNoOfPatientSWithRiskFactorsCodeFive("F",ageCategory,startOfPeriod,endOfPeriod);
	
	Integer noOfPatientSWithRiskFactorsCodeSixForMale=kenyaEmrService.getNoOfPatientSWithRiskFactorsCodeSix("M",ageCategory,startOfPeriod,endOfPeriod);
	Integer noOfPatientSWithRiskFactorsCodeSixForFemale=kenyaEmrService.getNoOfPatientSWithRiskFactorsCodeSix("F",ageCategory,startOfPeriod,endOfPeriod);
	
	Integer noOfPatientSWithRiskFactorsCodeSevenForMale=kenyaEmrService.getNoOfPatientSWithRiskFactorsCodeSeven("M",ageCategory,startOfPeriod,endOfPeriod);
	Integer noOfPatientSWithRiskFactorsCodeSevenForFemale=kenyaEmrService.getNoOfPatientSWithRiskFactorsCodeSeven("F",ageCategory,startOfPeriod,endOfPeriod);
	
	Integer noOfPatientsTestedForCD4CountForMale=kenyaEmrService.getNoOfPatientsTestedForCD4Count("M",ageCategory,startOfPeriod,endOfPeriod);
	Integer noOfPatientsTestedForCD4CountForFemale=kenyaEmrService.getNoOfPatientsTestedForCD4Count("F",ageCategory,startOfPeriod,endOfPeriod);
	
	Integer noOfPatientsTestedForViralLoadForMale=kenyaEmrService.getNoOfPatientsTestedForViralLoad("M",ageCategory,startOfPeriod,endOfPeriod);
	Integer noOfPatientsTestedForViralLoadForFemale=kenyaEmrService.getNoOfPatientsTestedForViralLoad("F",ageCategory,startOfPeriod,endOfPeriod);
	//REGIMEN AT THE END OF THE MONTH
	Integer noOfChildPatientsHavingRegimen1=kenyaEmrService.getNoOfChildPatientsHavingRegimen(ageCategory,startOfPeriod,endOfPeriod,"AZT/3TC+NVP","60/30+50 mg");
	Integer noOfChildPatientsHavingRegimen2=kenyaEmrService.getNoOfChildPatientsHavingRegimen(ageCategory,startOfPeriod,endOfPeriod,"AZT/3TC+LPV/r","60/30+100/25 mg");
	Integer noOfChildPatientsHavingRegimen3=kenyaEmrService.getNoOfChildPatientsHavingRegimen(ageCategory,startOfPeriod,endOfPeriod,"AZT/3TC+EFV","60/30+200 mg");
	Integer noOfChildPatientsHavingRegimen4=kenyaEmrService.getNoOfChildPatientsHavingRegimen(ageCategory,startOfPeriod,endOfPeriod,"AZT/3TC+EFV","60/30+600 mg");
	Integer noOfChildPatientsHavingRegimen5=kenyaEmrService.getNoOfChildPatientsHavingRegimen(ageCategory,startOfPeriod,endOfPeriod,"ABC/3TC+NVP","60/30+50 mg");
	Integer noOfChildPatientsHavingRegimen6=kenyaEmrService.getNoOfChildPatientsHavingRegimen(ageCategory,startOfPeriod,endOfPeriod,"ABC/3TC+EFV","60/30+200 mg");
	Integer noOfChildPatientsHavingRegimen7=kenyaEmrService.getNoOfChildPatientsHavingRegimen(ageCategory,startOfPeriod,endOfPeriod,"ABC/3TC+EFV","60/30+600 mg");
	Integer noOfChildPatientsHavingRegimen8=kenyaEmrService.getNoOfChildPatientsHavingRegimen(ageCategory,startOfPeriod,endOfPeriod,"ABC/3TC+LPV/r","60/30+100/25 mg");
	Integer noOfChildPatientsHavingRegimen9=kenyaEmrService.getNoOfChildPatientsHavingRegimen(ageCategory,startOfPeriod,endOfPeriod,"AZT/3TC+ABC","60/30+60 mg");
	Integer noOfChildPatientsHavingRegimen10=kenyaEmrService.getNoOfChildPatientsHavingRegimenWithoutDose(ageCategory,startOfPeriod,endOfPeriod,"d4T+3TC+NVP");
	Integer noOfChildPatientsHavingRegimen11=kenyaEmrService.getNoOfChildPatientsHavingRegimenWithoutDose(ageCategory,startOfPeriod,endOfPeriod,"d4T+3TC+LPV/r");
	Integer noOfChildPatientsHavingRegimen12=kenyaEmrService.getNoOfChildPatientsHavingRegimenWithoutDose(ageCategory,startOfPeriod,endOfPeriod,"d4T+3TC+EFV");
	Integer noOfChildPatientsHavingRegimen13=kenyaEmrService.getNoOfChildPatientsHavingRegimenWithoutDose(ageCategory,startOfPeriod,endOfPeriod,"d4T+3TC+ABC");
	Integer noOfChildPatientsHavingRegimen14=kenyaEmrService.getNoOfChildPatientsHavingRegimenWithoutDose(ageCategory,startOfPeriod,endOfPeriod,"AZT+3TC+RAL");
	Integer noOfChildPatientsHavingRegimen15=kenyaEmrService.getNoOfChildPatientsHavingRegimenWithoutDose(ageCategory,startOfPeriod,endOfPeriod,"AZT+3TC+ATV/r");
	Integer noOfChildPatientsHavingRegimen16=kenyaEmrService.getNoOfChildPatientsHavingRegimenWithoutDose(ageCategory,startOfPeriod,endOfPeriod,"ABC+3TC+RAL");
	Integer noOfChildPatientsHavingRegimen17=kenyaEmrService.getNoOfChildPatientsHavingRegimenWithoutDose(ageCategory,startOfPeriod,endOfPeriod,"TDF+3TC+EFV");
	Integer noOfChildPatientsHavingRegimen18=kenyaEmrService.getNoOfChildPatientsHavingRegimenWithoutDose(ageCategory,startOfPeriod,endOfPeriod,"TDF+3TC+NVP");
	Integer noOfChildPatientsHavingRegimen19=kenyaEmrService.getNoOfChildPatientsHavingRegimenWithoutDose(ageCategory,startOfPeriod,endOfPeriod,"TDF+3TC+LPV/r");
	Integer noOfChildPatientsHavingRegimen20=kenyaEmrService.getNoOfChildPatientsHavingRegimenWithoutDose(ageCategory,startOfPeriod,endOfPeriod,"TDF+3TC+RAL");
	Integer noOfChildPatientsHavingRegimen21=kenyaEmrService.getNoOfChildPatientsHavingRegimenWithoutDose(ageCategory,startOfPeriod,endOfPeriod,"TDF+3TC+ATV/r");
	Integer noOfChildPatientsHavingRegimen22=kenyaEmrService.getNoOfChildPatientsHavingRegimenWithoutDose(ageCategory,startOfPeriod,endOfPeriod,"ABC+3TC+ATV/r");
	//stock dispensed
	Integer noOfPatientsstockDispensed1=kenyaEmrService.getNoOfPatientsstockdispensed(ageCategory,startOfPeriod,endOfPeriod,"AZT/3TC+NVP","60/30+50 mg");
	Integer noOfPatientsstockDispensed2=kenyaEmrService.getNoOfPatientsstockdispensed(ageCategory,startOfPeriod,endOfPeriod,"AZT/3TC+LPV/r","60/30+100/25 mg");
	Integer noOfPatientsstockDispensed3=kenyaEmrService.getNoOfPatientsstockdispensed(ageCategory,startOfPeriod,endOfPeriod,"AZT/3TC+EFV","60/30+200 mg");
	Integer noOfPatientsstockDispensed4=kenyaEmrService.getNoOfPatientsstockdispensed(ageCategory,startOfPeriod,endOfPeriod,"AZT/3TC+EFV","60/30+600 mg");
	Integer noOfPatientsstockDispensed5=kenyaEmrService.getNoOfPatientsstockdispensed(ageCategory,startOfPeriod,endOfPeriod,"ABC/3TC+NVP","60/30+50 mg");
	Integer noOfPatientsstockDispensed6=kenyaEmrService.getNoOfPatientsstockdispensed(ageCategory,startOfPeriod,endOfPeriod,"ABC/3TC+EFV","60/30+200 mg");
	Integer noOfPatientsstockDispensed7=kenyaEmrService.getNoOfPatientsstockdispensed(ageCategory,startOfPeriod,endOfPeriod,"ABC/3TC+EFV","60/30+600 mg");
	Integer noOfPatientsstockDispensed8=kenyaEmrService.getNoOfPatientsstockdispensed(ageCategory,startOfPeriod,endOfPeriod,"ABC/3TC+LPV/r","60/30+100/25 mg");
	Integer noOfPatientsstockDispensed9=kenyaEmrService.getNoOfPatientsstockdispensed(ageCategory,startOfPeriod,endOfPeriod,"AZT/3TC+ABC","60/30+60 mg");
	Integer noOfPatientsstockDispensed10=kenyaEmrService.getNoOfPatientsstockdispensedWithoutDose(ageCategory,startOfPeriod,endOfPeriod,"d4T+3TC+NVP");
	Integer noOfPatientsstockDispensed11=kenyaEmrService.getNoOfPatientsstockdispensedWithoutDose(ageCategory,startOfPeriod,endOfPeriod,"d4T+3TC+LPV/r");
	Integer noOfPatientsstockDispensed12=kenyaEmrService.getNoOfPatientsstockdispensedWithoutDose(ageCategory,startOfPeriod,endOfPeriod,"d4T+3TC+EFV");
	Integer noOfPatientsstockDispensed13=kenyaEmrService.getNoOfPatientsstockdispensedWithoutDose(ageCategory,startOfPeriod,endOfPeriod,"d4T+3TC+ABC");
	Integer noOfPatientsstockDispensed14=kenyaEmrService.getNoOfPatientsstockdispensedWithoutDose(ageCategory,startOfPeriod,endOfPeriod,"AZT+3TC+RAL");
	Integer noOfPatientsstockDispensed15=kenyaEmrService.getNoOfPatientsstockdispensedWithoutDose(ageCategory,startOfPeriod,endOfPeriod,"AZT+3TC+ATV/r");
	Integer noOfPatientsstockDispensed16=kenyaEmrService.getNoOfPatientsstockdispensedWithoutDose(ageCategory,startOfPeriod,endOfPeriod,"ABC+3TC+RAL");
	Integer noOfPatientsstockDispensed17=kenyaEmrService.getNoOfPatientsstockdispensedWithoutDose(ageCategory,startOfPeriod,endOfPeriod,"TDF+3TC+EFV");
	Integer noOfPatientsstockDispensed18=kenyaEmrService.getNoOfPatientsstockdispensedWithoutDose(ageCategory,startOfPeriod,endOfPeriod,"TDF+3TC+NVP");
	Integer noOfPatientsstockDispensed19=kenyaEmrService.getNoOfPatientsstockdispensedWithoutDose(ageCategory,startOfPeriod,endOfPeriod,"TDF+3TC+LPV/r");
	Integer noOfPatientsstockDispensed20=kenyaEmrService.getNoOfPatientsstockdispensedWithoutDose(ageCategory,startOfPeriod,endOfPeriod,"TDF+3TC+RAL");
	Integer noOfPatientsstockDispensed21=kenyaEmrService.getNoOfPatientsstockdispensedWithoutDose(ageCategory,startOfPeriod,endOfPeriod,"TDF+3TC+ATV/r");
	Integer noOfPatientsstockDispensed22=kenyaEmrService.getNoOfPatientsstockdispensedWithoutDose(ageCategory,startOfPeriod,endOfPeriod,"ABC+3TC+ATV/r");
	
	//Integer noOfPatientsHavingRegimen=kenyaEmrService.getNoOfPatientsHavingRegimen(ageCategory,startOfPeriod,endOfPeriod,drugRegimen,doseRegimen);
	
	model.addAttribute("noOfNewPatientEnrolledInHivCareForMale",noOfNewPatientEnrolledInHivCareForMale);
	model.addAttribute("noOfNewPatientEnrolledInHivCareForFemale",noOfNewPatientEnrolledInHivCareForFemale);
	model.addAttribute("noOfNewPatientEnrolledInHivCareTotal",noOfNewPatientEnrolledInHivCareForMale+noOfNewPatientEnrolledInHivCareForFemale);
	
	model.addAttribute("noOfPatientTreatedForOpportunisticInfectionsForMale",noOfPatientTreatedForOpportunisticInfectionsForMale);
	model.addAttribute("noOfPatientTreatedForOpportunisticInfectionsForFemale",noOfPatientTreatedForOpportunisticInfectionsForFemale);
	model.addAttribute("noOfPatientTreatedForOpportunisticInfectionsTotal",noOfPatientTreatedForOpportunisticInfectionsForMale+noOfPatientTreatedForOpportunisticInfectionsForFemale);
	
	model.addAttribute("noOfMedicallyEligiblePatientsWaitingForARTForMale",noOfMedicallyEligiblePatientsWaitingForARTForMale);
	model.addAttribute("noOfMedicallyEligiblePatientsWaitingForARTForFemale",noOfMedicallyEligiblePatientsWaitingForARTForFemale);
	model.addAttribute("noOfMedicallyEligiblePatientsWaitingForARTTotal",noOfMedicallyEligiblePatientsWaitingForARTForMale+noOfMedicallyEligiblePatientsWaitingForARTForFemale);
	
	model.addAttribute("cumulativeNoOfActiveFollowUpPatientsStartedAtBegOfMonthForMale",cumulativeNoOfActiveFollowUpPatientsStartedAtBegOfMonthForMale);
	model.addAttribute("cumulativeNoOfActiveFollowUpPatientsStartedAtBegOfMonthForFemale",cumulativeNoOfActiveFollowUpPatientsStartedAtBegOfMonthForFemale);
	model.addAttribute("cumulativeNoOfActiveFollowUpPatientsStartedAtBegOfMonthTotal",cumulativeNoOfActiveFollowUpPatientsStartedAtBegOfMonthForMale+cumulativeNoOfActiveFollowUpPatientsStartedAtBegOfMonthForFemale);
	
	model.addAttribute("noOfNewPatientsStartedOnARTForMale",noOfNewPatientsStartedOnARTForMale);
	model.addAttribute("noOfNewPatientsStartedOnARTForFemale",noOfNewPatientsStartedOnARTForFemale);
	model.addAttribute("noOfNewPatientsStartedOnARTTotal",noOfNewPatientsStartedOnARTForMale+noOfNewPatientsStartedOnARTForFemale);
	
	model.addAttribute("noOfPatientsOnARTTransferredInForMale",noOfPatientsOnARTTransferredInForMale);
	model.addAttribute("noOfPatientsOnARTTransferredInForFemale",noOfPatientsOnARTTransferredInForFemale);
	model.addAttribute("noOfPatientsOnARTTransferredInTotal",noOfPatientsOnARTTransferredInForMale+noOfPatientsOnARTTransferredInForFemale);
	
	model.addAttribute("noOfCumulativeNoOfActiveFollowUpPatientsStartedAtEndOfMonthForMale",noOfCumulativeNoOfActiveFollowUpPatientsStartedAtEndOfMonthForMale);
	model.addAttribute("noOfCumulativeNoOfActiveFollowUpPatientsStartedAtEndOfMonthForFemale",noOfCumulativeNoOfActiveFollowUpPatientsStartedAtEndOfMonthForFemale);
	model.addAttribute("noOfCumulativeNoOfActiveFollowUpPatientsStartedAtEndOfMonthTotal",noOfCumulativeNoOfActiveFollowUpPatientsStartedAtEndOfMonthForMale+noOfCumulativeNoOfActiveFollowUpPatientsStartedAtEndOfMonthForFemale);
	
	model.addAttribute("noOfDeathReportedForMale",noOfDeathReportedForMale);
	model.addAttribute("noOfDeathReportedForFemale",noOfDeathReportedForFemale);
	model.addAttribute("noOfDeathReportedTotal",noOfDeathReportedForMale+noOfDeathReportedForFemale);
	
	model.addAttribute("noOfPatientsTransferredOutUnderARVForMale",noOfPatientsTransferredOutUnderARVForMale);
	model.addAttribute("noOfPatientsTransferredOutUnderARVForFemale",noOfPatientsTransferredOutUnderARVForFemale);
	model.addAttribute("noOfPatientsTransferredOutUnderARVTotal",noOfPatientsTransferredOutUnderARVForMale+noOfPatientsTransferredOutUnderARVForFemale);
	
	model.addAttribute("noOfPatientsLostToFollowUpForMale",noOfPatientsLostToFollowUpForMale);
	model.addAttribute("noOfPatientsLostToFollowUpForFemale",noOfPatientsLostToFollowUpForFemale);
	model.addAttribute("noOfPatientsLostToFollowUpTotal",noOfPatientsLostToFollowUpForMale+noOfPatientsLostToFollowUpForFemale);
	
	model.addAttribute("noOfPatientsStopppedARTForMale",noOfPatientsStopppedARTForMale);
	model.addAttribute("noOfPatientsStopppedARTForFemale",noOfPatientsStopppedARTForFemale);
	model.addAttribute("noOfPatientsStopppedARTTotal",noOfPatientsStopppedARTForMale+noOfPatientsStopppedARTForFemale);
	
	model.addAttribute("noOfPatientsOnARTForMale",noOfPatientsOnARTForMale);
	model.addAttribute("noOfPatientsOnARTForFemale",noOfPatientsOnARTForFemale);
	model.addAttribute("noOfPatientsOnARTTotal",noOfPatientsOnARTForMale+noOfPatientsOnARTForFemale);
	
	model.addAttribute("noOfPatientsOnOriginalFirstLineRegimForMale",noOfPatientsOnOriginalFirstLineRegimForMale);
	model.addAttribute("noOfPatientsOnOriginalFirstLineRegimForFemale",noOfDeathReportedForFemale);
	model.addAttribute("noOfPatientsOnOriginalFirstLineRegimTotal",noOfPatientsOnOriginalFirstLineRegimForMale+noOfPatientsOnOriginalFirstLineRegimForFemale);
	
	model.addAttribute("noOfPatientsSubstitutedFirstLineRegimForMale",noOfPatientsSubstitutedFirstLineRegimForMale);
	model.addAttribute("noOfPatientsSubstitutedFirstLineRegimForFemale",noOfPatientsSubstitutedFirstLineRegimForFemale);
	model.addAttribute("noOfPatientsSubstitutedFirstLineRegimTotal",noOfPatientsSubstitutedFirstLineRegimForMale+noOfPatientsSubstitutedFirstLineRegimForFemale);
	
	model.addAttribute("noOfPatientsSubstitutedSecondLineRegimForMale",noOfPatientsSubstitutedSecondLineRegimForMale);
	model.addAttribute("noOfPatientsSubstitutedSecondLineRegimForFemale",noOfPatientsSubstitutedSecondLineRegimForFemale);
	model.addAttribute("noOfPatientsSubstitutedSecondLineRegimTotal",noOfPatientsSubstitutedSecondLineRegimForMale+noOfPatientsSubstitutedSecondLineRegimForFemale);
	
	model.addAttribute("noOfPatientsSubstitutedThirdLineRegimForMale",noOfPatientsSubstitutedThirdLineRegimForMale);
	model.addAttribute("noOfPatientsSubstitutedThirdLineRegimForFemale",noOfPatientsSubstitutedThirdLineRegimForFemale);
	model.addAttribute("noOfPatientsSubstitutedThirdLineRegimTotal",noOfPatientsSubstitutedThirdLineRegimForMale+noOfPatientsSubstitutedThirdLineRegimForFemale);
	
	model.addAttribute("noOfHIVPositiveTBPatientsForMale",noOfHIVPositiveTBPatientsForMale);
	model.addAttribute("noOfHIVPositiveTBPatientsForFemale",noOfHIVPositiveTBPatientsForFemale);
	model.addAttribute("noOfHIVPositiveTBPatientsTotal",noOfHIVPositiveTBPatientsForMale+noOfHIVPositiveTBPatientsForFemale);
	
	model.addAttribute("cumulativeNoOfHIVPositiveTBPatientsForMale",cumulativeNoOfHIVPositiveTBPatientsForMale);
	model.addAttribute("cumulativeNoOfHIVPositiveTBPatientsForFemale",cumulativeNoOfHIVPositiveTBPatientsForFemale);
	model.addAttribute("cumulativeNoOfHIVPositiveTBPatientsTotal",cumulativeNoOfHIVPositiveTBPatientsForMale+cumulativeNoOfHIVPositiveTBPatientsForFemale);
	
	model.addAttribute("noOfPatientsAssessedForAdherenceDuringThisMonth",noOfPatientsAssessedForAdherenceDuringThisMonth);
	
	model.addAttribute("noOfPatientsAssessedForAdherenceDuringTheLastMonthLevelOneTot",noOfPatientsAssessedForAdherenceDuringTheLastMonthLevelOneTot);
	
	model.addAttribute("noOfPatientsAssessedForAdherenceDuringTheLastMonthLevelTwoTot",noOfPatientsAssessedForAdherenceDuringTheLastMonthLevelTwoTot);
	
	model.addAttribute("noOfPatientsAssessedForAdherenceDuringTheLastMonthLevelThreeTot",noOfPatientsAssessedForAdherenceDuringTheLastMonthLevelThreeTot);
	
	model.addAttribute("noOfPatientsOnPerformanceScaleAForMale",noOfPatientsOnPerformanceScaleAForMale);
	model.addAttribute("noOfPatientsOnPerformanceScaleAForFemale",noOfPatientsOnPerformanceScaleAForFemale);
	model.addAttribute("noOfPatientsOnPerformanceScaleATotal",noOfPatientsOnPerformanceScaleAForMale+noOfPatientsOnPerformanceScaleAForFemale);
	
	model.addAttribute("noOfPatientsOnPerformanceScaleBForMale",noOfPatientsOnPerformanceScaleBForMale);
	model.addAttribute("noOfPatientsOnPerformanceScaleBForFemale",noOfPatientsOnPerformanceScaleBForFemale);
	model.addAttribute("noOfPatientsOnPerformanceScaleBTotal",noOfPatientsOnPerformanceScaleBForMale+noOfPatientsOnPerformanceScaleBForFemale);
	
	model.addAttribute("noOfPatientsOnPerformanceScaleCForMale",noOfPatientsOnPerformanceScaleCForMale);
	model.addAttribute("noOfPatientsOnPerformanceScaleCForFemale",noOfPatientsOnPerformanceScaleCForFemale);
	model.addAttribute("noOfPatientsOnPerformanceScaleCTotal",noOfPatientsOnPerformanceScaleCForMale+noOfPatientsOnPerformanceScaleCForFemale);
	
	model.addAttribute("noOfPatientSWithRiskFactorsCodeOneForMale",noOfPatientSWithRiskFactorsCodeOneForMale);
	model.addAttribute("noOfPatientSWithRiskFactorsCodeOneForFemale",noOfPatientSWithRiskFactorsCodeOneForFemale);
	
	model.addAttribute("noOfPatientSWithRiskFactorsCodeTwoForMale",noOfPatientSWithRiskFactorsCodeTwoForMale);
	model.addAttribute("noOfPatientSWithRiskFactorsCodeTwoForFemale",noOfPatientSWithRiskFactorsCodeTwoForFemale);
	
	model.addAttribute("noOfPatientSWithRiskFactorsCodeThreeForMale",noOfPatientSWithRiskFactorsCodeThreeForMale);
	model.addAttribute("noOfPatientSWithRiskFactorsCodeThreeForFemale",noOfPatientSWithRiskFactorsCodeThreeForFemale);
	
	model.addAttribute("noOfPatientSWithRiskFactorsCodeFourForMale",noOfPatientSWithRiskFactorsCodeFourForMale);
	model.addAttribute("noOfPatientSWithRiskFactorsCodeFourForFemale",noOfPatientSWithRiskFactorsCodeFourForFemale);
	
	model.addAttribute("noOfPatientSWithRiskFactorsCodeFiveForMale",noOfPatientSWithRiskFactorsCodeFiveForMale);
	model.addAttribute("noOfPatientSWithRiskFactorsCodeFiveForFemale",noOfPatientSWithRiskFactorsCodeFiveForFemale);
	
	model.addAttribute("noOfPatientSWithRiskFactorsCodeSixForMale",noOfPatientSWithRiskFactorsCodeSixForMale);
	model.addAttribute("noOfPatientSWithRiskFactorsCodeSixForFemale",noOfPatientSWithRiskFactorsCodeSixForFemale);
	
	model.addAttribute("noOfPatientSWithRiskFactorsCodeSevenForMale",noOfPatientSWithRiskFactorsCodeSevenForMale);
	model.addAttribute("noOfPatientSWithRiskFactorsCodeSevenForFemale",noOfPatientSWithRiskFactorsCodeSevenForFemale);
	
	model.addAttribute("noOfPatientsTestedForCD4CountForMale",noOfPatientsTestedForCD4CountForMale);
	model.addAttribute("noOfPatientsTestedForCD4CountForFemale",noOfPatientsTestedForCD4CountForFemale);
	model.addAttribute("noOfPatientsTestedForCD4CountTotal",noOfPatientsTestedForCD4CountForMale+noOfPatientsTestedForCD4CountForFemale);
	
	model.addAttribute("noOfPatientsTestedForViralLoadForMale",noOfPatientsTestedForViralLoadForMale);
	model.addAttribute("noOfPatientsTestedForViralLoadForFemale",noOfPatientsTestedForViralLoadForFemale);
	model.addAttribute("noOfPatientsTestedForViralLoadTotal",noOfPatientsTestedForViralLoadForMale+noOfPatientsTestedForViralLoadForFemale);
	model.addAttribute("noOfChildPatientsHavingRegimen1", noOfChildPatientsHavingRegimen1);
	//REGIMEN AT THE END OF THE MONTH
	model.addAttribute("noOfChildPatientsHavingRegimen1", noOfChildPatientsHavingRegimen1);
	model.addAttribute("noOfChildPatientsHavingRegimen2", noOfChildPatientsHavingRegimen2);
	model.addAttribute("noOfChildPatientsHavingRegimen3", noOfChildPatientsHavingRegimen3);
	model.addAttribute("noOfChildPatientsHavingRegimen4", noOfChildPatientsHavingRegimen4);
	model.addAttribute("noOfChildPatientsHavingRegimen5", noOfChildPatientsHavingRegimen5);
	model.addAttribute("noOfChildPatientsHavingRegimen6", noOfChildPatientsHavingRegimen6);
	model.addAttribute("noOfChildPatientsHavingRegimen7", noOfChildPatientsHavingRegimen7);
	model.addAttribute("noOfChildPatientsHavingRegimen8", noOfChildPatientsHavingRegimen8);
	model.addAttribute("noOfChildPatientsHavingRegimen9", noOfChildPatientsHavingRegimen9);
	model.addAttribute("noOfChildPatientsHavingRegimen10", noOfChildPatientsHavingRegimen10);
	model.addAttribute("noOfChildPatientsHavingRegimen11", noOfChildPatientsHavingRegimen11);
	model.addAttribute("noOfChildPatientsHavingRegimen12", noOfChildPatientsHavingRegimen12);
	model.addAttribute("noOfChildPatientsHavingRegimen13", noOfChildPatientsHavingRegimen13);
	model.addAttribute("noOfChildPatientsHavingRegimen14", noOfChildPatientsHavingRegimen14);
	model.addAttribute("noOfChildPatientsHavingRegimen15", noOfChildPatientsHavingRegimen15);
	model.addAttribute("noOfChildPatientsHavingRegimen16", noOfChildPatientsHavingRegimen16);
	model.addAttribute("noOfChildPatientsHavingRegimen17", noOfChildPatientsHavingRegimen17);
	model.addAttribute("noOfChildPatientsHavingRegimen18", noOfChildPatientsHavingRegimen18);
	model.addAttribute("noOfChildPatientsHavingRegimen19", noOfChildPatientsHavingRegimen19);
	model.addAttribute("noOfChildPatientsHavingRegimen20", noOfChildPatientsHavingRegimen20);
	model.addAttribute("noOfChildPatientsHavingRegimen21", noOfChildPatientsHavingRegimen21);
	model.addAttribute("noOfChildPatientsHavingRegimen22", noOfChildPatientsHavingRegimen22);
	//stock dispensed
	model.addAttribute("noOfPatientsHavingstockDispensed1", noOfPatientsstockDispensed1);
	model.addAttribute("noOfPatientsHavingstockDispensed2", noOfPatientsstockDispensed2);
	model.addAttribute("noOfPatientsHavingstockDispensed3", noOfPatientsstockDispensed3);
	model.addAttribute("noOfPatientsHavingstockDispensed4", noOfPatientsstockDispensed4);
	model.addAttribute("noOfPatientsHavingstockDispensed5", noOfPatientsstockDispensed5);
	model.addAttribute("noOfPatientsHavingstockDispensed6", noOfPatientsstockDispensed6);
	model.addAttribute("noOfPatientsHavingstockDispensed7", noOfPatientsstockDispensed7);
	model.addAttribute("noOfPatientsHavingstockDispensed8", noOfPatientsstockDispensed8);
	model.addAttribute("noOfPatientsHavingstockDispensed9", noOfPatientsstockDispensed9);
	model.addAttribute("noOfPatientsHavingstockDispensed10", noOfPatientsstockDispensed10);
	model.addAttribute("noOfPatientsHavingstockDispensed11", noOfPatientsstockDispensed11);
	model.addAttribute("noOfPatientsHavingstockDispensed12", noOfPatientsstockDispensed12);
	model.addAttribute("noOfPatientsHavingstockDispensed13", noOfPatientsstockDispensed13);
	model.addAttribute("noOfPatientsHavingstockDispensed14", noOfPatientsstockDispensed14);
	model.addAttribute("noOfPatientsHavingstockDispensed15", noOfPatientsstockDispensed15);
	model.addAttribute("noOfPatientsHavingstockDispensed16", noOfPatientsstockDispensed16);
	model.addAttribute("noOfPatientsHavingstockDispensed17", noOfPatientsstockDispensed17);
	model.addAttribute("noOfPatientsHavingstockDispensed18", noOfPatientsstockDispensed18);
	model.addAttribute("noOfPatientsHavingstockDispensed19", noOfPatientsstockDispensed19);
	model.addAttribute("noOfPatientsHavingstockDispensed20", noOfPatientsstockDispensed20);
	model.addAttribute("noOfPatientsHavingstockDispensed21", noOfPatientsstockDispensed21);
	model.addAttribute("noOfPatientsHavingstockDispensed22", noOfPatientsstockDispensed22);
	
	model.addAttribute("startDate", formatterExtt.format(startDate));
	model.addAttribute("endDate", formatterExtt.format(endDate));
	model.addAttribute("location", Context.getService(KenyaEmrService.class).getDefaultLocation());
  }
}