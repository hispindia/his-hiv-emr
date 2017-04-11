package org.openmrs.module.kenyaemr.fragment.controller.report;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.fragment.FragmentModel;

public class NapMonthlyReportChildFormFragmentController {
	public void controller(FragmentModel model, UiUtils ui) {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy");
		String year = df.format(date);
		int currYear = Integer.parseInt(year);
		List<Integer> listOfYear = new ArrayList<Integer>();
		for (Integer i = currYear; i > currYear - 11; i--) {
			listOfYear.add(i);
		}
		model.addAttribute("listOfYear", listOfYear);
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		model.addAttribute("currDate", formatter.format(new Date()));
		
		model.addAttribute("command", newNapMonthlyReportAdultForm());
	}
	
	public class NapMonthlyReportAdultForm{
		private Date startDate;
		private Date endDate;
		public Date getStartDate() {
			return startDate;
		}
		public void setStartDate(Date startDate) {
			this.startDate = startDate;
		}
		public Date getEndDate() {
			return endDate;
		}
		public void setEndDate(Date endDate) {
			this.endDate = endDate;
		}
	}
	
	public NapMonthlyReportAdultForm newNapMonthlyReportAdultForm(){
		return new NapMonthlyReportAdultForm();
    
    }
}
