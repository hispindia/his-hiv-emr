/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.kenyaemr.reporting.builder.common;

import static org.openmrs.module.kenyacore.report.ReportUtils.map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.openmrs.module.kenyacore.report.ReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyacore.report.builder.AbstractReportBuilder;
import org.openmrs.module.kenyacore.report.builder.Builds;
import org.openmrs.module.kenyaemr.reporting.ColumnParameters;
import org.openmrs.module.kenyaemr.reporting.EmrReportingUtils;
import org.openmrs.module.kenyaemr.reporting.library.shared.common.CommonDimensionLibrary;
import org.openmrs.module.kenyaemr.reporting.library.shared.hiv.HivIndicatorLibrary;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Builds({"kenyaemr.common.report.newHIVPatientEnrolled"})
public class NewHIVPatientEnrolledBuilder extends AbstractReportBuilder{

	@Autowired
	private CommonDimensionLibrary commonDimensions;

	@Autowired
	private HivIndicatorLibrary hivIndicators;

	/**
	 * @see org.openmrs.module.kenyacore.report.builder.AbstractReportBuilder#getParameters(org.openmrs.module.kenyacore.report.ReportDescriptor)
	 */
	@Override
	protected List<Parameter> getParameters(ReportDescriptor descriptor) {
		return Arrays.asList(
				new Parameter("startDate", "Start Date", Date.class),
				new Parameter("endDate", "End Date", Date.class)
		);
	}
	
	/**
	 * @see org.openmrs.module.kenyacore.report.builder.AbstractReportBuilder#buildDataSets(org.openmrs.module.kenyacore.report.ReportDescriptor, org.openmrs.module.reporting.report.definition.ReportDefinition)
	 */
	@Override
	protected List<Mapped<DataSetDefinition>> buildDataSets(ReportDescriptor descriptor, ReportDefinition report) {
		return Arrays.asList(
				ReportUtils.map(createNewHIVEnrolledDataSet(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createHIVEnrolledwithPerformanceADataSet(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createHIVEnrolledwithRiskDataSet(), "startDate=${startDate},endDate=${endDate}")
		);
	}

	
	/**
	 * Creates the HIV enrolled data set
	 * @return the data set
	 */
	private DataSetDefinition createNewHIVEnrolledDataSet() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("P");
		dsd.setDescription("New HIV Patient Enrolled");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("age", map(commonDimensions.standardAgeGroups(), "onDate=${endDate}"));
		dsd.addDimension("gender", map(commonDimensions.gender()));
		
		
		List<ColumnParameters> columns = new ArrayList<ColumnParameters>();
		columns.add(new ColumnParameters("FP", "0-14 years, female", "gender=F|age=<15"));
		columns.add(new ColumnParameters("MP", "0-14 years, male", "gender=M|age=<15"));
		columns.add(new ColumnParameters("FA", ">14 years, female", "gender=F|age=15+"));
		columns.add(new ColumnParameters("MA", ">14 years, male", "gender=M|age=15+"));
		columns.add(new ColumnParameters("T", "total", ""));

		String indParams = "startDate=${startDate},endDate=${endDate}";
                
		EmrReportingUtils.addRow(dsd, "P1", "No. of ", ReportUtils.map(hivIndicators.notInART(), indParams), columns);
		return dsd;
	}
	
	private DataSetDefinition createHIVEnrolledwithPerformanceADataSet() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("S");
		dsd.setDescription("Performance scale (A, B, C)");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("age", map(commonDimensions.standardAgeGroups(), "onDate=${endDate}"));
		dsd.addDimension("gender", map(commonDimensions.gender()));
		dsd.addDimension("parameter", map(commonDimensions.performanceScales()));
		
		
		List<ColumnParameters> columnsA = new ArrayList<ColumnParameters>();
		columnsA.add(new ColumnParameters("FP", "Performance scale A,0-14 years, female", "gender=F|age=<15|parameter=A"));
		columnsA.add(new ColumnParameters("MP", "Performance scale A,0-14 years, male", "gender=M|age=<15|parameter=A"));
		columnsA.add(new ColumnParameters("FA", "Performance scale A,>14 years, female", "gender=F|age=15+|parameter=A"));
		columnsA.add(new ColumnParameters("MA", "Performance scale A,>14 years, male", "gender=M|age=15+|parameter=A"));
		columnsA.add(new ColumnParameters("T", "Performance scale A,total", ""));
		
		List<ColumnParameters> columnsB = new ArrayList<ColumnParameters>();
		columnsB.add(new ColumnParameters("FP1", "Performance scale B,0-14 years, female", "gender=F|age=<15|parameter=B"));
		columnsB.add(new ColumnParameters("MP1", "Performance scale B,0-14 years, male", "gender=M|age=<15|parameter=B"));
		columnsB.add(new ColumnParameters("FA1", "Performance scale B,>14 years, female", "gender=F|age=15+|parameter=B"));
		columnsB.add(new ColumnParameters("MA1", "Performance scale B,>14 years, male", "gender=M|age=15+|parameter=B"));
		columnsB.add(new ColumnParameters("T1", "Performance scale B,total", ""));
		
		List<ColumnParameters> columnsC = new ArrayList<ColumnParameters>();
		columnsC.add(new ColumnParameters("FP2", "Performance scale C,0-14 years, female", "gender=F|age=<15|parameter=C"));
		columnsC.add(new ColumnParameters("MP2", "Performance scale C,0-14 years, male", "gender=M|age=<15|parameter=C"));
		columnsC.add(new ColumnParameters("FA2", "Performance scale C,>14 years, female", "gender=F|age=15+|parameter=C"));
		columnsC.add(new ColumnParameters("MA2", "Performance scale C,>14 years, male", "gender=M|age=15+|parameter=C"));
		columnsC.add(new ColumnParameters("T2", "Performance scale C,total", ""));
		String indParams = "startDate=${startDate},endDate=${endDate}";
                
		EmrReportingUtils.addRow(dsd, "S1", "No. of detected case (Performance Scale A) ", ReportUtils.map(hivIndicators.performanceScaleA(), indParams), columnsA);
		EmrReportingUtils.addRow(dsd, "S2", "No. of detected case (Performance Scale B) ", ReportUtils.map(hivIndicators.performanceScaleB(), indParams), columnsB);
		EmrReportingUtils.addRow(dsd, "S3", "No. of detected case (Performance Scale C) ", ReportUtils.map(hivIndicators.performanceScaleC(), indParams), columnsC);
		return dsd;
	}
	
	private DataSetDefinition createHIVEnrolledwithRiskDataSet() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("T");
		dsd.setDescription("Risk Factors Code ");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("parameter", map(commonDimensions.riskFactor()));
		
		
		List<ColumnParameters> columns1 = new ArrayList<ColumnParameters>();
		columns1.add(new ColumnParameters("T1", " risk factor for HIV as MSM ,total", "parameter=1"));
		List<ColumnParameters> columns2 = new ArrayList<ColumnParameters>();
		columns2.add(new ColumnParameters("T2", "risk factor for HIV as SW,total", "parameter=2"));
		List<ColumnParameters> columns3 = new ArrayList<ColumnParameters>();
		columns3.add(new ColumnParameters("T3", "risk factor for HIV as heterosexual,total", "parameter=3"));
		List<ColumnParameters> columns4 = new ArrayList<ColumnParameters>();
		columns4.add(new ColumnParameters("T4", "risk factor for HIV as IDU,total", "parameter=4"));
		List<ColumnParameters> columns5 = new ArrayList<ColumnParameters>();
		columns5.add(new ColumnParameters("T5", "risk factor for HIV as Blood transfusion,total", "parameter=5"));
		List<ColumnParameters> columns6 = new ArrayList<ColumnParameters>();
		columns6.add(new ColumnParameters("T6", "risk factor for HIV as Mother to child,total", "parameter=6"));
		List<ColumnParameters> columns7 = new ArrayList<ColumnParameters>();
		columns7.add(new ColumnParameters("T7", "risk factor for HIV as unknown,total", "parameter=7"));
		
		String indParams = "startDate=${startDate},endDate=${endDate}";
                
		EmrReportingUtils.addRow(dsd, "S1", "Total No. of detected case   ", ReportUtils.map(hivIndicators.riskFactor1(), indParams), columns1);
		EmrReportingUtils.addRow(dsd, "S2", "Total No. of detected case ", ReportUtils.map(hivIndicators.riskFactor2(), indParams), columns2);
		EmrReportingUtils.addRow(dsd, "S3", "Total No. of detected case  ", ReportUtils.map(hivIndicators.riskFactor3(), indParams), columns3);
		EmrReportingUtils.addRow(dsd, "S4", "Total No. of detected case  ", ReportUtils.map(hivIndicators.riskFactor4(), indParams), columns4);
		EmrReportingUtils.addRow(dsd, "S5", "Total No. of detected case  ", ReportUtils.map(hivIndicators.riskFactor5(), indParams), columns5);
		EmrReportingUtils.addRow(dsd, "S6", "Total No. of detected case ", ReportUtils.map(hivIndicators.riskFactor6(), indParams), columns6);
		EmrReportingUtils.addRow(dsd, "S7", "Total No. of detected case ", ReportUtils.map(hivIndicators.riskFactor7(), indParams), columns7);
		return dsd;
	}

}
