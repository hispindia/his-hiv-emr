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
	
	//
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
	
	model.addAttribute("noOfPatientSWithRiskFactorsCodeOneForMale",noOfPatientSWithRiskFactorsCodeOneForFemale);
	model.addAttribute("noOfPatientSWithRiskFactorsCodeOneForFemale",noOfPatientSWithRiskFactorsCodeOneForFemale);
	model.addAttribute("noOfPatientSWithRiskFactorsCodeOneTotal",noOfPatientSWithRiskFactorsCodeOneForMale+noOfPatientSWithRiskFactorsCodeOneForFemale);
	
	model.addAttribute("noOfPatientSWithRiskFactorsCodeTwoForMale",noOfPatientSWithRiskFactorsCodeTwoForFemale);
	model.addAttribute("noOfPatientSWithRiskFactorsCodeTwoForFemale",noOfPatientSWithRiskFactorsCodeTwoForFemale);
	model.addAttribute("noOfPatientSWithRiskFactorsCodeTwoTotal",noOfPatientSWithRiskFactorsCodeTwoForMale+noOfPatientSWithRiskFactorsCodeTwoForFemale);
	
	model.addAttribute("noOfPatientSWithRiskFactorsCodeThreeForMale",noOfPatientSWithRiskFactorsCodeThreeForFemale);
	model.addAttribute("noOfPatientSWithRiskFactorsCodeThreeForFemale",noOfPatientSWithRiskFactorsCodeThreeForFemale);
	model.addAttribute("noOfPatientSWithRiskFactorsCodeThreeTotal",noOfPatientSWithRiskFactorsCodeThreeForMale+noOfPatientSWithRiskFactorsCodeThreeForFemale);
	
	model.addAttribute("noOfPatientSWithRiskFactorsCodeFourForMale",noOfPatientSWithRiskFactorsCodeFourForFemale);
	model.addAttribute("noOfPatientSWithRiskFactorsCodeFourForFemale",noOfPatientSWithRiskFactorsCodeFourForFemale);
	model.addAttribute("noOfPatientSWithRiskFactorsCodeFourTotal",noOfPatientSWithRiskFactorsCodeFourForMale+noOfPatientSWithRiskFactorsCodeFourForFemale);
	
	model.addAttribute("noOfPatientSWithRiskFactorsCodeFiveForMale",noOfPatientSWithRiskFactorsCodeFiveForFemale);
	model.addAttribute("noOfPatientSWithRiskFactorsCodeFiveForFemale",noOfPatientSWithRiskFactorsCodeFiveForFemale);
	model.addAttribute("noOfPatientSWithRiskFactorsCodeFiveTotal",noOfPatientSWithRiskFactorsCodeFiveForMale+noOfPatientSWithRiskFactorsCodeFiveForFemale);
	
	model.addAttribute("noOfPatientSWithRiskFactorsCodeSixForMale",noOfPatientSWithRiskFactorsCodeSixForFemale);
	model.addAttribute("noOfPatientSWithRiskFactorsCodeSixForFemale",noOfPatientSWithRiskFactorsCodeSixForFemale);
	model.addAttribute("noOfPatientSWithRiskFactorsCodeSixTotal",noOfPatientSWithRiskFactorsCodeSixForMale+noOfPatientSWithRiskFactorsCodeSixForFemale);
	
	model.addAttribute("noOfPatientSWithRiskFactorsCodeSevenForMale",noOfPatientSWithRiskFactorsCodeSevenForFemale);
	model.addAttribute("noOfPatientSWithRiskFactorsCodeSevenForFemale",noOfPatientSWithRiskFactorsCodeSevenForFemale);
	model.addAttribute("noOfPatientSWithRiskFactorsCodeSevenTotal",noOfPatientSWithRiskFactorsCodeSevenForMale+noOfPatientSWithRiskFactorsCodeSevenForFemale);
	
	model.addAttribute("noOfPatientsTestedForCD4CountForMale",noOfPatientsTestedForCD4CountForMale);
	model.addAttribute("noOfPatientsTestedForCD4CountForFemale",noOfPatientsTestedForCD4CountForFemale);
	model.addAttribute("noOfPatientsTestedForCD4CountTotal",noOfPatientsTestedForCD4CountForMale+noOfPatientsTestedForCD4CountForFemale);
	
	model.addAttribute("noOfPatientsTestedForViralLoadForMale",noOfPatientsTestedForViralLoadForMale);
	model.addAttribute("noOfPatientsTestedForViralLoadForFemale",noOfPatientsTestedForViralLoadForFemale);
	model.addAttribute("noOfPatientsTestedForViralLoadTotal",noOfPatientsTestedForViralLoadForMale+noOfPatientsTestedForViralLoadForFemale);
  }
}