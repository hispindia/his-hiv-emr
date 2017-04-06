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
	
	Integer getNoOfPatientsStopppedARTForMale=kenyaEmrService.getNoOfPatientsStopppedART("M",ageCategory,startOfPeriod,endOfPeriod);
	Integer getNoOfPatientsStopppedARTForFemale=kenyaEmrService.getNoOfPatientsStopppedART("F",ageCategory,startOfPeriod,endOfPeriod);
	
	Integer getNoOfPatientsOnARTForMale=kenyaEmrService.getNoOfPatientsOnART("M",ageCategory,startOfPeriod,endOfPeriod);
	Integer getNoOfPatientsOnARTForFemale=kenyaEmrService.getNoOfPatientsOnART("F",ageCategory,startOfPeriod,endOfPeriod);
	
	Integer getNoOfPatientsOnOriginalFirstLineRegimForMale=kenyaEmrService.getNoOfPatientsOnOriginalFirstLineRegim("M",ageCategory,startOfPeriod,endOfPeriod);
	Integer getNoOfPatientsOnOriginalFirstLineRegimForFemale=kenyaEmrService.getNoOfPatientsOnOriginalFirstLineRegim("F",ageCategory,startOfPeriod,endOfPeriod);
	
	Integer getNoOfPatientsSubstitutedFirstLineRegimForMale=kenyaEmrService.getNoOfPatientsSubstitutedFirstLineRegim("M",ageCategory,startOfPeriod,endOfPeriod);
	Integer getNoOfPatientsSubstitutedFirstLineRegimForFemale=kenyaEmrService.getNoOfPatientsSubstitutedFirstLineRegim("F",ageCategory,startOfPeriod,endOfPeriod);
	
	Integer getNoOfPatientsSubstitutedSecondLineRegimForMale=kenyaEmrService.getNoOfPatientsSubstitutedSecondLineRegim("M",ageCategory,startOfPeriod,endOfPeriod);
	Integer getNoOfPatientsSubstitutedSecondLineRegimForFemale=kenyaEmrService.getNoOfPatientsSubstitutedSecondLineRegim("F",ageCategory,startOfPeriod,endOfPeriod);
	
	Integer getNoOfPatientsSubstitutedThirdLineRegimForMale=kenyaEmrService.getNoOfPatientsSubstitutedThirdLineRegim("M",ageCategory,startOfPeriod,endOfPeriod);
	Integer getNoOfPatientsSubstitutedThirdLineRegimForFemale=kenyaEmrService.getNoOfPatientsSubstitutedThirdLineRegim("F",ageCategory,startOfPeriod,endOfPeriod);
	
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
	
	model.addAttribute("noOfPatientsStopppedARTForMale",getNoOfPatientsStopppedARTForMale);
	model.addAttribute("noOfPatientsStopppedARTForFemale",getNoOfPatientsStopppedARTForFemale);
	model.addAttribute("noOfPatientsStopppedARTTotal",getNoOfPatientsStopppedARTForMale+getNoOfPatientsStopppedARTForFemale);
	
	model.addAttribute("noOfPatientsOnARTForMale",getNoOfPatientsOnARTForMale);
	model.addAttribute("noOfPatientsOnARTForFemale",getNoOfPatientsOnARTForFemale);
	model.addAttribute("noOfPatientsOnARTTotal",getNoOfPatientsOnARTForMale+getNoOfPatientsOnARTForFemale);
	
	model.addAttribute("noOfPatientsOnOriginalFirstLineRegimForMale",getNoOfPatientsOnOriginalFirstLineRegimForMale);
	model.addAttribute("noOfPatientsOnOriginalFirstLineRegimForFemale",noOfDeathReportedForFemale);
	model.addAttribute("noOfPatientsOnOriginalFirstLineRegimTotal",getNoOfPatientsOnOriginalFirstLineRegimForMale+getNoOfPatientsOnOriginalFirstLineRegimForFemale);
	
	model.addAttribute("noOfPatientsSubstitutedFirstLineRegimForMale",getNoOfPatientsSubstitutedFirstLineRegimForMale);
	model.addAttribute("noOfPatientsSubstitutedFirstLineRegimForFemale",getNoOfPatientsSubstitutedFirstLineRegimForFemale);
	model.addAttribute("noOfPatientsSubstitutedFirstLineRegimTotal",getNoOfPatientsSubstitutedFirstLineRegimForMale+getNoOfPatientsSubstitutedFirstLineRegimForFemale);
	
	model.addAttribute("noOfPatientsSubstitutedSecondLineRegimForMale",getNoOfPatientsSubstitutedSecondLineRegimForMale);
	model.addAttribute("noOfPatientsSubstitutedSecondLineRegimForFemale",getNoOfPatientsSubstitutedSecondLineRegimForFemale);
	model.addAttribute("noOfPatientsSubstitutedSecondLineRegimTotal",getNoOfPatientsSubstitutedSecondLineRegimForMale+getNoOfPatientsSubstitutedSecondLineRegimForFemale);
	
	model.addAttribute("noOfPatientsSubstitutedThirdLineRegimForMale",getNoOfPatientsSubstitutedThirdLineRegimForMale);
	model.addAttribute("noOfPatientsSubstitutedThirdLineRegimForFemale",getNoOfPatientsSubstitutedThirdLineRegimForFemale);
	model.addAttribute("noOfPatientsSubstitutedThirdLineRegimTotal",getNoOfPatientsSubstitutedThirdLineRegimForMale+getNoOfPatientsSubstitutedThirdLineRegimForFemale);
  }
}