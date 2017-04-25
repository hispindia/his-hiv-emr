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
	
	Integer noOfPatientsHavingFirstLineRegimen1=kenyaEmrService.getNoOfPatientsHavingFirstLineRegimen(ageCategory,startOfPeriod,endOfPeriod,"AZT/3TC/NVP","300/150/200 mg");
	Integer noOfPatientsHavingFirstLineRegimen2=kenyaEmrService.getNoOfPatientsHavingFirstLineRegimen(ageCategory,startOfPeriod,endOfPeriod,"AZT/3TC+NVP","300/150+200 mg");
	Integer noOfPatientsHavingFirstLineRegimen3=kenyaEmrService.getNoOfPatientsHavingFirstLineRegimen(ageCategory,startOfPeriod,endOfPeriod,"AZT/3TC/EFV","300/150/600 mg");
	Integer noOfPatientsHavingFirstLineRegimen4=kenyaEmrService.getNoOfPatientsHavingFirstLineRegimen(ageCategory,startOfPeriod,endOfPeriod,"AZT/3TC+EFV","300/150+600 mg");
	Integer noOfPatientsHavingFirstLineRegimen5=kenyaEmrService.getNoOfPatientsHavingFirstLineRegimen(ageCategory,startOfPeriod,endOfPeriod,"TDF/3TC/NVP","300/300/200 mg");
	Integer noOfPatientsHavingFirstLineRegimen6=kenyaEmrService.getNoOfPatientsHavingFirstLineRegimen(ageCategory,startOfPeriod,endOfPeriod,"TDF/3TC+NVP","300/300+200 mg");
	Integer noOfPatientsHavingFirstLineRegimen7=kenyaEmrService.getNoOfPatientsHavingFirstLineRegimen(ageCategory,startOfPeriod,endOfPeriod,"TDF/3TC/EFV","300/300/600 mg");
	Integer noOfPatientsHavingFirstLineRegimen8=kenyaEmrService.getNoOfPatientsHavingFirstLineRegimen(ageCategory,startOfPeriod,endOfPeriod,"TDF/3TC+EFV","300/300+400 mg");
	Integer noOfPatientsHavingFirstLineRegimen9=kenyaEmrService.getNoOfPatientsHavingFirstLineRegimen(ageCategory,startOfPeriod,endOfPeriod,"TDF/3TC+EFV","300/300+600 mg");
	Integer noOfPatientsHavingFirstLineRegimen10=kenyaEmrService.getNoOfPatientsHavingFirstLineRegimen(ageCategory,startOfPeriod,endOfPeriod,"TDF/FTC/NVP","300/200/200 mg");
	Integer noOfPatientsHavingFirstLineRegimen11=kenyaEmrService.getNoOfPatientsHavingFirstLineRegimen(ageCategory,startOfPeriod,endOfPeriod,"TDF/FTC+NVP","300/200+200 mg");
	Integer noOfPatientsHavingFirstLineRegimen12=kenyaEmrService.getNoOfPatientsHavingFirstLineRegimen(ageCategory,startOfPeriod,endOfPeriod,"TDF/FTC/EFV","300/200/200 mg");
	Integer noOfPatientsHavingFirstLineRegimen13=kenyaEmrService.getNoOfPatientsHavingFirstLineRegimen(ageCategory,startOfPeriod,endOfPeriod,"TDF/FTC+EFV","300/200+400 mg");
	Integer noOfPatientsHavingFirstLineRegimen14=kenyaEmrService.getNoOfPatientsHavingFirstLineRegimen(ageCategory,startOfPeriod,endOfPeriod,"TDF/FTC/EFV","300/200/600 mg");
	Integer noOfPatientsHavingFirstLineRegimen15=kenyaEmrService.getNoOfPatientsHavingFirstLineRegimen(ageCategory,startOfPeriod,endOfPeriod,"TDF/FTC+EFV","300/200+600 mg");
	Integer noOfPatientsHavingFirstLineRegimen16=kenyaEmrService.getNoOfPatientsHavingFirstLineRegimen(ageCategory,startOfPeriod,endOfPeriod,"d4T/3TC/NVP","30/150/200 mg");
	Integer noOfPatientsHavingFirstLineRegimen17=kenyaEmrService.getNoOfPatientsHavingFirstLineRegimen(ageCategory,startOfPeriod,endOfPeriod,"d4T/3TC+NVP","30/150+200 mg");
	Integer noOfPatientsHavingFirstLineRegimen18=kenyaEmrService.getNoOfPatientsHavingFirstLineRegimen(ageCategory,startOfPeriod,endOfPeriod,"d4T/3TC/EFV","30/150/600 mg");
	Integer noOfPatientsHavingFirstLineRegimen19=kenyaEmrService.getNoOfPatientsHavingFirstLineRegimen(ageCategory,startOfPeriod,endOfPeriod,"d4T/3TC+EFV","30/150+600 mg");
	Integer noOfPatientsHavingFirstLineRegimen20=kenyaEmrService.getNoOfPatientsHavingFirstLineRegimenWithoutDose(ageCategory,startOfPeriod,endOfPeriod,"TDF/FTC/DTG");
	Integer noOfPatientsHavingFirstLineRegimen21=kenyaEmrService.getNoOfPatientsHavingFirstLineRegimenWithoutDose(ageCategory,startOfPeriod,endOfPeriod,"TDF/3TC/DTG");
	Integer noOfPatientsHavingFirstLineRegimen22=kenyaEmrService.getNoOfPatientsHavingFirstLineRegimen(ageCategory,startOfPeriod,endOfPeriod,"ABC/3TC+EFV","600/300+600 mg");
	Integer noOfPatientsHavingFirstLineRegimen23=kenyaEmrService.getNoOfPatientsHavingFirstLineRegimenWithoutDose(ageCategory,startOfPeriod,endOfPeriod,"ABC/3TC/DTG");
	Integer noOfPatientsHavingFirstLineRegimen24=kenyaEmrService.getNoOfPatientsHavingFirstLineRegimenWithoutDose(ageCategory,startOfPeriod,endOfPeriod,"ABC/3TC/NVP");
	Integer noOfPatientsHavingFirstLineRegimen25=kenyaEmrService.getNoOfPatientsHavingFirstLineRegimenWithoutDose(ageCategory,startOfPeriod,endOfPeriod,"ABC/FTC/EFV");
	Integer noOfPatientsHavingFirstLineRegimen26=kenyaEmrService.getNoOfPatientsHavingFirstLineRegimenWithoutDose(ageCategory,startOfPeriod,endOfPeriod,"ABC/FTC/DTG");
	Integer noOfPatientsHavingFirstLineRegimen27=kenyaEmrService.getNoOfPatientsHavingFirstLineRegimenWithoutDose(ageCategory,startOfPeriod,endOfPeriod,"ABC/FTC/NVP");
	
	Integer noOfPatientsHavingSecondLineRegimen1=kenyaEmrService.getNoOfPatientsHavingSecondLineRegimen(ageCategory,startOfPeriod,endOfPeriod,"AZT/3TC/LPV/r","300/150/200/50 mg");
	Integer noOfPatientsHavingSecondLineRegimen2=kenyaEmrService.getNoOfPatientsHavingSecondLineRegimen(ageCategory,startOfPeriod,endOfPeriod,"AZT/3TC+LPV/r","300/150+200/50 mg");
	Integer noOfPatientsHavingSecondLineRegimen3=kenyaEmrService.getNoOfPatientsHavingSecondLineRegimen(ageCategory,startOfPeriod,endOfPeriod,"TDF/3TC/LPV/r","300/300/200/50 mg");
	Integer noOfPatientsHavingSecondLineRegimen4=kenyaEmrService.getNoOfPatientsHavingSecondLineRegimen(ageCategory,startOfPeriod,endOfPeriod,"TDF/3TC+LPV/r","300/300/200/50 mg");
	Integer noOfPatientsHavingSecondLineRegimen5=kenyaEmrService.getNoOfPatientsHavingSecondLineRegimen(ageCategory,startOfPeriod,endOfPeriod,"TDF/FTC/LPV/r","300/200/200/50 mg");
	Integer noOfPatientsHavingSecondLineRegimen6=kenyaEmrService.getNoOfPatientsHavingSecondLineRegimen(ageCategory,startOfPeriod,endOfPeriod,"TDF/FTC+LPV/r","300/200+200/50 mg");
	Integer noOfPatientsHavingSecondLineRegimen7=kenyaEmrService.getNoOfPatientsHavingSecondLineRegimen(ageCategory,startOfPeriod,endOfPeriod,"TDF/ABC/LPV/r","300/300/200/50 mg");
	Integer noOfPatientsHavingSecondLineRegimen8=kenyaEmrService.getNoOfPatientsHavingSecondLineRegimen(ageCategory,startOfPeriod,endOfPeriod,"TDF/ABC+LPV/r","300/300/200/50 mg");
	Integer noOfPatientsHavingSecondLineRegimen9=kenyaEmrService.getNoOfPatientsHavingSecondLineRegimen(ageCategory,startOfPeriod,endOfPeriod,"ABC/3TC+LPV/r","600/300+200/50 mg");
	Integer noOfPatientsHavingSecondLineRegimen10=kenyaEmrService.getNoOfPatientsHavingSecondLineRegimenWithoutDose(ageCategory,startOfPeriod,endOfPeriod,"ABC/3TC/ATV/r");
	Integer noOfPatientsHavingSecondLineRegimen11=kenyaEmrService.getNoOfPatientsHavingSecondLineRegimenWithoutDose(ageCategory,startOfPeriod,endOfPeriod,"AZT/3TC/ATV/r");
	Integer noOfPatientsHavingSecondLineRegimen12=kenyaEmrService.getNoOfPatientsHavingSecondLineRegimenWithoutDose(ageCategory,startOfPeriod,endOfPeriod,"TDF/3TC/ATV/r");
	
	Integer noOfPatientsHavingThirdLineRegimen1=kenyaEmrService.getNoOfPatientsHavingThirdLineRegimen(ageCategory,startOfPeriod,endOfPeriod,"AZT/3TC+TDF+LPV/r","300/150+300+200/50 mg");
	// stock dispensed
	Integer noOfPatientsstockDispensed1=kenyaEmrService.getNoOfPatientsstockdispensed(ageCategory,startOfPeriod,endOfPeriod,"AZT/3TC/NVP","300/150/200 mg");
	Integer noOfPatientsstockDispensed2=kenyaEmrService.getNoOfPatientsstockdispensed(ageCategory,startOfPeriod,endOfPeriod,"AZT/3TC+NVP","300/150+200 mg");
	Integer noOfPatientsstockDispensed3=kenyaEmrService.getNoOfPatientsstockdispensed(ageCategory,startOfPeriod,endOfPeriod,"AZT/3TC/EFV","300/150/600 mg");
	Integer noOfPatientsstockDispensed4=kenyaEmrService.getNoOfPatientsstockdispensed(ageCategory,startOfPeriod,endOfPeriod,"AZT/3TC+EFV","300/150+600 mg");
	Integer noOfPatientsstockDispensed5=kenyaEmrService.getNoOfPatientsstockdispensed(ageCategory,startOfPeriod,endOfPeriod,"TDF/3TC/NVP","300/300/200 mg");
	Integer noOfPatientsstockDispensed6=kenyaEmrService.getNoOfPatientsstockdispensed(ageCategory,startOfPeriod,endOfPeriod,"TDF/3TC+NVP","300/300+200 mg");
	Integer noOfPatientsstockDispensed7=kenyaEmrService.getNoOfPatientsstockdispensed(ageCategory,startOfPeriod,endOfPeriod,"TDF/3TC/EFV","300/300/600 mg");
	Integer noOfPatientsstockDispensed8=kenyaEmrService.getNoOfPatientsstockdispensed(ageCategory,startOfPeriod,endOfPeriod,"TDF/3TC+EFV","300/300+400 mg");
	Integer noOfPatientsstockDispensed9=kenyaEmrService.getNoOfPatientsstockdispensed(ageCategory,startOfPeriod,endOfPeriod,"TDF/3TC+EFV","300/300+600 mg");
	Integer noOfPatientsstockDispensed10=kenyaEmrService.getNoOfPatientsstockdispensed(ageCategory,startOfPeriod,endOfPeriod,"TDF/FTC/NVP","300/200/200 mg");
	Integer noOfPatientsstockDispensed11=kenyaEmrService.getNoOfPatientsstockdispensed(ageCategory,startOfPeriod,endOfPeriod,"TDF/FTC+NVP","300/200+200 mg");
	Integer noOfPatientsstockDispensed12=kenyaEmrService.getNoOfPatientsstockdispensed(ageCategory,startOfPeriod,endOfPeriod,"TDF/FTC/EFV","300/200/200 mg");
	Integer noOfPatientsstockDispensed13=kenyaEmrService.getNoOfPatientsstockdispensed(ageCategory,startOfPeriod,endOfPeriod,"TDF/FTC+EFV","300/200+400 mg");
	Integer noOfPatientsstockDispensed14=kenyaEmrService.getNoOfPatientsstockdispensed(ageCategory,startOfPeriod,endOfPeriod,"TDF/FTC/EFV","300/200/600 mg");
	Integer noOfPatientsstockDispensed15=kenyaEmrService.getNoOfPatientsstockdispensed(ageCategory,startOfPeriod,endOfPeriod,"TDF/FTC+EFV","300/200+600 mg");
	Integer noOfPatientsstockDispensed16=kenyaEmrService.getNoOfPatientsstockdispensed(ageCategory,startOfPeriod,endOfPeriod,"d4T/3TC/NVP","30/150/200 mg");
	Integer noOfPatientsstockDispensed17=kenyaEmrService.getNoOfPatientsstockdispensed(ageCategory,startOfPeriod,endOfPeriod,"d4T/3TC+NVP","30/150+200 mg");
	Integer noOfPatientsstockDispensed18=kenyaEmrService.getNoOfPatientsstockdispensed(ageCategory,startOfPeriod,endOfPeriod,"d4T/3TC/EFV","30/150/600 mg");
	Integer noOfPatientsstockDispensed19=kenyaEmrService.getNoOfPatientsstockdispensed(ageCategory,startOfPeriod,endOfPeriod,"d4T/3TC+EFV","30/150+600 mg");
	Integer noOfPatientsstockDispensed20=kenyaEmrService.getNoOfPatientsstockdispensedWithoutDose(ageCategory,startOfPeriod,endOfPeriod,"TDF/FTC/DTG");
	Integer noOfPatientsstockDispensed21=kenyaEmrService.getNoOfPatientsstockdispensedWithoutDose(ageCategory,startOfPeriod,endOfPeriod,"TDF/3TC/DTG");
	Integer noOfPatientsstockDispensed22=kenyaEmrService.getNoOfPatientsstockdispensed(ageCategory,startOfPeriod,endOfPeriod,"ABC/3TC+EFV","600/300+600 mg");
	Integer noOfPatientsstockDispensed23=kenyaEmrService.getNoOfPatientsstockdispensedWithoutDose(ageCategory,startOfPeriod,endOfPeriod,"ABC/3TC/DTG");
	Integer noOfPatientsstockDispensed24=kenyaEmrService.getNoOfPatientsstockdispensedWithoutDose(ageCategory,startOfPeriod,endOfPeriod,"ABC/3TC/NVP");
	Integer noOfPatientsstockDispensed25=kenyaEmrService.getNoOfPatientsstockdispensedWithoutDose(ageCategory,startOfPeriod,endOfPeriod,"ABC/FTC/EFV");
	Integer noOfPatientsstockDispensed26=kenyaEmrService.getNoOfPatientsstockdispensedWithoutDose(ageCategory,startOfPeriod,endOfPeriod,"ABC/FTC/DTG");
	Integer noOfPatientsstockDispensed27=kenyaEmrService.getNoOfPatientsstockdispensedWithoutDose(ageCategory,startOfPeriod,endOfPeriod,"ABC/FTC/NVP");
	Integer noOfPatientsstockDispensed28=kenyaEmrService.getNoOfPatientsstockdispensed(ageCategory,startOfPeriod,endOfPeriod,"AZT/3TC/LPV/r","300/150/200/50 mg");
	Integer noOfPatientsstockDispensed29=kenyaEmrService.getNoOfPatientsstockdispensed(ageCategory,startOfPeriod,endOfPeriod,"AZT/3TC+LPV/r","300/150+200/50 mg");
	Integer noOfPatientsstockDispensed30=kenyaEmrService.getNoOfPatientsstockdispensed(ageCategory,startOfPeriod,endOfPeriod,"TDF/3TC/LPV/r","300/300/200/50 mg");
	Integer noOfPatientsstockDispensed31=kenyaEmrService.getNoOfPatientsstockdispensed(ageCategory,startOfPeriod,endOfPeriod,"TDF/3TC+LPV/r","300/300+200/50 mg");
	Integer noOfPatientsstockDispensed32=kenyaEmrService.getNoOfPatientsstockdispensed(ageCategory,startOfPeriod,endOfPeriod,"TDF/FTC/LPV/r","300/200/200/50 mg");
	Integer noOfPatientsstockDispensed33=kenyaEmrService.getNoOfPatientsstockdispensed(ageCategory,startOfPeriod,endOfPeriod,"TDF/FTC+LPV/r","300/200+200/50 mg");
	Integer noOfPatientsstockDispensed34=kenyaEmrService.getNoOfPatientsstockdispensed(ageCategory,startOfPeriod,endOfPeriod,"TDF/ABC/LPV/r","300/300/200/50 mg");
	Integer noOfPatientsstockDispensed35=kenyaEmrService.getNoOfPatientsstockdispensed(ageCategory,startOfPeriod,endOfPeriod,"TDF+ABC+LPV/r","300+300+200/50 mg");
	Integer noOfPatientsstockDispensed36=kenyaEmrService.getNoOfPatientsstockdispensed(ageCategory,startOfPeriod,endOfPeriod,"ABC/3TC+LPV/r","600/300+200/50 mg");
	Integer noOfPatientsstockDispensed37=kenyaEmrService.getNoOfPatientsstockdispensedWithoutDose(ageCategory,startOfPeriod,endOfPeriod,"ABC/3TC/ATV/r");
	Integer noOfPatientsstockDispensed38=kenyaEmrService.getNoOfPatientsstockdispensedWithoutDose(ageCategory,startOfPeriod,endOfPeriod,"AZT/3TC/ATV/r");
	Integer noOfPatientsstockDispensed39=kenyaEmrService.getNoOfPatientsstockdispensedWithoutDose(ageCategory,startOfPeriod,endOfPeriod,"TDF/3TC/ATV/r");
	Integer noOfPatientsstockDispensed40=kenyaEmrService.getNoOfPatientsstockdispensed(ageCategory,startOfPeriod,endOfPeriod,"AZT/3TC+TDF+LPV/r","300/150+300+200/50 mg");
	
	
	
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
	
	model.addAttribute("noOfPatientsHavingFirstLineRegimen1", noOfPatientsHavingFirstLineRegimen1);
	model.addAttribute("noOfPatientsHavingFirstLineRegimen2", noOfPatientsHavingFirstLineRegimen2);
	model.addAttribute("noOfPatientsHavingFirstLineRegimen3", noOfPatientsHavingFirstLineRegimen3);
	model.addAttribute("noOfPatientsHavingFirstLineRegimen4", noOfPatientsHavingFirstLineRegimen4);
	model.addAttribute("noOfPatientsHavingFirstLineRegimen5", noOfPatientsHavingFirstLineRegimen5);
	model.addAttribute("noOfPatientsHavingFirstLineRegimen6", noOfPatientsHavingFirstLineRegimen6);
	model.addAttribute("noOfPatientsHavingFirstLineRegimen7", noOfPatientsHavingFirstLineRegimen7);
	model.addAttribute("noOfPatientsHavingFirstLineRegimen8", noOfPatientsHavingFirstLineRegimen8);
	model.addAttribute("noOfPatientsHavingFirstLineRegimen9", noOfPatientsHavingFirstLineRegimen9);
	model.addAttribute("noOfPatientsHavingFirstLineRegimen10", noOfPatientsHavingFirstLineRegimen10);
	model.addAttribute("noOfPatientsHavingFirstLineRegimen11", noOfPatientsHavingFirstLineRegimen11);
	model.addAttribute("noOfPatientsHavingFirstLineRegimen12", noOfPatientsHavingFirstLineRegimen12);
	model.addAttribute("noOfPatientsHavingFirstLineRegimen13", noOfPatientsHavingFirstLineRegimen13);
	model.addAttribute("noOfPatientsHavingFirstLineRegimen14", noOfPatientsHavingFirstLineRegimen14);
	model.addAttribute("noOfPatientsHavingFirstLineRegimen15", noOfPatientsHavingFirstLineRegimen15);
	model.addAttribute("noOfPatientsHavingFirstLineRegimen16", noOfPatientsHavingFirstLineRegimen16);
	model.addAttribute("noOfPatientsHavingFirstLineRegimen17", noOfPatientsHavingFirstLineRegimen17);
	model.addAttribute("noOfPatientsHavingFirstLineRegimen18", noOfPatientsHavingFirstLineRegimen18);
	model.addAttribute("noOfPatientsHavingFirstLineRegimen19", noOfPatientsHavingFirstLineRegimen19);
	model.addAttribute("noOfPatientsHavingFirstLineRegimen20", noOfPatientsHavingFirstLineRegimen20);
	model.addAttribute("noOfPatientsHavingFirstLineRegimen21", noOfPatientsHavingFirstLineRegimen21);
	model.addAttribute("noOfPatientsHavingFirstLineRegimen22", noOfPatientsHavingFirstLineRegimen22);
	model.addAttribute("noOfPatientsHavingFirstLineRegimen23", noOfPatientsHavingFirstLineRegimen23);
	model.addAttribute("noOfPatientsHavingFirstLineRegimen24", noOfPatientsHavingFirstLineRegimen24);
	model.addAttribute("noOfPatientsHavingFirstLineRegimen25", noOfPatientsHavingFirstLineRegimen25);
	model.addAttribute("noOfPatientsHavingFirstLineRegimen26", noOfPatientsHavingFirstLineRegimen26);
	model.addAttribute("noOfPatientsHavingFirstLineRegimen27", noOfPatientsHavingFirstLineRegimen27);
	
	model.addAttribute("noOfPatientsHavingSecondLineRegimen1", noOfPatientsHavingSecondLineRegimen1);
	model.addAttribute("noOfPatientsHavingSecondLineRegimen2", noOfPatientsHavingSecondLineRegimen2);
	model.addAttribute("noOfPatientsHavingSecondLineRegimen3", noOfPatientsHavingSecondLineRegimen3);
	model.addAttribute("noOfPatientsHavingSecondLineRegimen4", noOfPatientsHavingSecondLineRegimen4);
	model.addAttribute("noOfPatientsHavingSecondLineRegimen5", noOfPatientsHavingSecondLineRegimen5);
	model.addAttribute("noOfPatientsHavingSecondLineRegimen6", noOfPatientsHavingSecondLineRegimen6);
	model.addAttribute("noOfPatientsHavingSecondLineRegimen7", noOfPatientsHavingSecondLineRegimen7);
	model.addAttribute("noOfPatientsHavingSecondLineRegimen8", noOfPatientsHavingSecondLineRegimen8);
	model.addAttribute("noOfPatientsHavingSecondLineRegimen9", noOfPatientsHavingSecondLineRegimen9);
	model.addAttribute("noOfPatientsHavingSecondLineRegimen10", noOfPatientsHavingSecondLineRegimen10);
	model.addAttribute("noOfPatientsHavingSecondLineRegimen11", noOfPatientsHavingSecondLineRegimen11);
	model.addAttribute("noOfPatientsHavingSecondLineRegimen12", noOfPatientsHavingSecondLineRegimen12);
	
	model.addAttribute("noOfPatientsHavingThirdLineRegimen1", noOfPatientsHavingThirdLineRegimen1);
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
	model.addAttribute("noOfPatientsHavingstockDispensed23", noOfPatientsstockDispensed23);
	model.addAttribute("noOfPatientsHavingstockDispensed24", noOfPatientsstockDispensed24);
	model.addAttribute("noOfPatientsHavingstockDispensed25", noOfPatientsstockDispensed25);
	model.addAttribute("noOfPatientsHavingstockDispensed26", noOfPatientsstockDispensed26);
	model.addAttribute("noOfPatientsHavingstockDispensed27", noOfPatientsstockDispensed27);
	model.addAttribute("noOfPatientsHavingstockDispensed28", noOfPatientsstockDispensed28);
	model.addAttribute("noOfPatientsHavingstockDispensed29", noOfPatientsstockDispensed29);
	model.addAttribute("noOfPatientsHavingstockDispensed30", noOfPatientsstockDispensed30);
	model.addAttribute("noOfPatientsHavingstockDispensed31", noOfPatientsstockDispensed31);
	model.addAttribute("noOfPatientsHavingstockDispensed32", noOfPatientsstockDispensed32);
	model.addAttribute("noOfPatientsHavingstockDispensed33", noOfPatientsstockDispensed33);
	model.addAttribute("noOfPatientsHavingstockDispensed34", noOfPatientsstockDispensed34);
	model.addAttribute("noOfPatientsHavingstockDispensed35", noOfPatientsstockDispensed35);
	model.addAttribute("noOfPatientsHavingstockDispensed36", noOfPatientsstockDispensed36);
	model.addAttribute("noOfPatientsHavingstockDispensed37", noOfPatientsstockDispensed37);
	model.addAttribute("noOfPatientsHavingstockDispensed38", noOfPatientsstockDispensed38);
	model.addAttribute("noOfPatientsHavingstockDispensed39", noOfPatientsstockDispensed39);
	model.addAttribute("noOfPatientsHavingstockDispensed40", noOfPatientsstockDispensed40);
	}
}