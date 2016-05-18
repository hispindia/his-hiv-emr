package org.openmrs.module.kenyaemr.calculation.library.hiv;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.BooleanResult;
import org.openmrs.module.kenyaemr.api.KenyaEmrService;
import org.openmrs.module.kenyaemr.model.DrugOrderProcessed;

public class OnSwitchLineArtCalculation extends AbstractPatientCalculation {

	
	@Override
    public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> arg1, PatientCalculationContext context) {

		
		CalculationResultMap ret = new CalculationResultMap();
		for (Integer ptId : cohort) {
			boolean onOrigFirstLine = false;
			 KenyaEmrService kenyaEmrService = (KenyaEmrService) Context.getService(KenyaEmrService.class);
		 	   List<DrugOrderProcessed> drugorderprocess = kenyaEmrService.getAllfirstLine();
		 	   {
		 	  for(DrugOrderProcessed order:drugorderprocess)
		 	  { 
		 		 if((ptId.equals(order.getPatient().getPatientId())) && (order.getTypeOfRegimen().equals("Second line ART")) && (order.getRegimenChangeType().equals("Switch")) )
		 		 {
		 			onOrigFirstLine=true; 
		 		 }
		 		if(order.getDiscontinuedDate()!=null)
			 	  { 
			 		 onOrigFirstLine=false; 
			 	  }
		 	  }
		}
			

			ret.put(ptId, new BooleanResult(onOrigFirstLine, this, context));
		}
		return ret;
    }
}