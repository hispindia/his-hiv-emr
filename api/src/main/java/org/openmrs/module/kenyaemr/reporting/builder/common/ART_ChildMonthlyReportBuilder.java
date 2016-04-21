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
import org.openmrs.module.kenyaemr.reporting.library.shared.hiv.art.ArtIndicatorLibrary;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Builds({"kenyaemr.common.report.artChildmonthlyReport"})
public class ART_ChildMonthlyReportBuilder extends AbstractReportBuilder{
	@Autowired
	private CommonDimensionLibrary commonDimensions;

	@Autowired
	private HivIndicatorLibrary hivIndicators;
	
	@Autowired
	private ArtIndicatorLibrary artIndicators;
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
				ReportUtils.map(creatArtOutcomeDeathDataSet(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(creatArtOutcomeTransferredOutDataSet(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(creatArtOutcomeLostMissingDataSet(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(creatArtOutcomeCompletedArtDataSet(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(creatArtOutcomeDataSet(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createCumulativeactiveTbDataSet(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createNewInitiatedPatientTbDataSet(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbDataSet(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createCumulativeTbDataSet(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createHivDataSet(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createCumulativeHivDataSet(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map( creatDataSetForCdFourCount(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(creatDataSetForViralLoad(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createNewHIVEnrolledDataSet(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createHIVEnrolledwithPerformanceADataSet(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createHIVEnrolledwithRiskDataSet(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createOIDataSet(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createHIVAdherenceDataSet(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createLevelAdherenceDataSet(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createElligibleARTDataSet(), "startDate=${startDate},endDate=${endDate}")
				
		);
	}
	
	/**
	 * Creates the HIV enrolled data set
	 * @return the data set
	 */
	private DataSetDefinition creatArtOutcomeDeathDataSet() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("A");
		dsd.setDescription("No. of death reported at the end of this month ");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("age", map(commonDimensions.standardAgeGroups(), "onDate=${endDate}"));
		dsd.addDimension("gender", map(commonDimensions.gender()));

		ColumnParameters childfemale =new ColumnParameters("FP", "0-14 years, female", "gender=F|age=<15");
		ColumnParameters childmale =new ColumnParameters("MP", "0-14 years, male", "gender=M|age=<15");
		ColumnParameters total=new ColumnParameters("T", "total", "age=<15");

		String indParams = "startDate=${startDate},endDate=${endDate}";
		List<ColumnParameters> allColumns = Arrays.asList(childfemale, childmale ,total);
		List<String> indSuffixes = Arrays.asList("CF","CM","TT");    
                
		EmrReportingUtils.addRow(dsd, "A1", "No. of ", ReportUtils.map(hivIndicators.outcomeArt(), indParams), allColumns,indSuffixes);
		return dsd;
	}
    
	private DataSetDefinition creatArtOutcomeTransferredOutDataSet() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("B");
		dsd.setDescription("No. of death reported at the end of this month ");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("age", map(commonDimensions.standardAgeGroups(), "onDate=${endDate}"));
		dsd.addDimension("gender", map(commonDimensions.gender()));

		ColumnParameters childfemale =new ColumnParameters("FP", "0-14 years, female", "gender=F|age=<15");
		ColumnParameters childmale =new ColumnParameters("MP", "0-14 years, male", "gender=M|age=<15");
		ColumnParameters total=new ColumnParameters("T", "total", "age=<15");

		String indParams = "startDate=${startDate},endDate=${endDate}";
		List<ColumnParameters> allColumns = Arrays.asList(childfemale, childmale ,total);
		List<String> indSuffixes = Arrays.asList("CF","CM","TT");    
                
		EmrReportingUtils.addRow(dsd, "B1", "No. of ", ReportUtils.map(hivIndicators.outcometransferredArt(), indParams), allColumns,indSuffixes);
		return dsd;
	}
	
	private DataSetDefinition creatArtOutcomeLostMissingDataSet() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("C");
		dsd.setDescription("No. of death reported at the end of this month ");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("age", map(commonDimensions.standardAgeGroups(), "onDate=${endDate}"));
		dsd.addDimension("gender", map(commonDimensions.gender()));

		ColumnParameters childfemale =new ColumnParameters("FP", "0-14 years, female", "gender=F|age=<15");
		ColumnParameters childmale =new ColumnParameters("MP", "0-14 years, male", "gender=M|age=<15");
		ColumnParameters total=new ColumnParameters("T", "total", "age=<15");

		String indParams = "startDate=${startDate},endDate=${endDate}";
		List<ColumnParameters> allColumns = Arrays.asList(childfemale, childmale ,total);
		List<String> indSuffixes = Arrays.asList("CF","CM","TT");    
                
		EmrReportingUtils.addRow(dsd, "C1", "No. of ", ReportUtils.map(hivIndicators.outcomelostmissingArt(), indParams), allColumns,indSuffixes);
		return dsd;
	}
	
	private DataSetDefinition creatArtOutcomeCompletedArtDataSet() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("D");
		dsd.setDescription("No. of patients stopping ART at the end of this month  ");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("age", map(commonDimensions.standardAgeGroups(), "onDate=${endDate}"));
		dsd.addDimension("gender", map(commonDimensions.gender()));

		ColumnParameters childfemale =new ColumnParameters("FP", "0-14 years, female", "gender=F|age=<15");
		ColumnParameters childmale =new ColumnParameters("MP", "0-14 years, male", "gender=M|age=<15");
		ColumnParameters total=new ColumnParameters("T", "total", "age=<15");

		String indParams = "startDate=${startDate},endDate=${endDate}";
		List<ColumnParameters> allColumns = Arrays.asList(childfemale, childmale ,total);
		List<String> indSuffixes = Arrays.asList("CF","CM","TT");    
                
		EmrReportingUtils.addRow(dsd, "D1", "No. of ", ReportUtils.map(hivIndicators.restartART(), indParams), allColumns,indSuffixes);
		return dsd;
	}
	
	private DataSetDefinition creatArtOutcomeDataSet() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("E");
		dsd.setDescription("No. of patients on ART at the end of this month ");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("age", map(commonDimensions.standardAgeGroups(), "onDate=${endDate}"));
		dsd.addDimension("gender", map(commonDimensions.gender()));

		ColumnParameters childfemale =new ColumnParameters("FP", "0-14 years, female", "gender=F|age=<15");
		ColumnParameters childmale =new ColumnParameters("MP", "0-14 years, male", "gender=M|age=<15");
		ColumnParameters total=new ColumnParameters("T", "total", "age=<15");

		String indParams = "startDate=${startDate},endDate=${endDate}";
		List<ColumnParameters> allColumns = Arrays.asList(childfemale, childmale ,total);
		List<String> indSuffixes = Arrays.asList("CF","CM","TT");    
                
		EmrReportingUtils.addRow(dsd, "E1", "No. of ", ReportUtils.map(hivIndicators.startArt(), indParams), allColumns,indSuffixes);
		return dsd;
	}
	private DataSetDefinition createCumulativeactiveTbDataSet() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("F");
		dsd.setDescription("Cumulative no. of active follow up  patients ever started on ART at the beginning of this month");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("age", map(commonDimensions.standardAgeGroups(), "onDate=${endDate}"));
		dsd.addDimension("gender", map(commonDimensions.gender()));
		
		
		ColumnParameters childfemale =new ColumnParameters("FP", "0-14 years, female", "gender=F|age=<15");
		ColumnParameters childmale =new ColumnParameters("MP", "0-14 years, male", "gender=M|age=<15");
		
		ColumnParameters total =new ColumnParameters("T", "total", "age=<15");

		String indParams = "startDate=${startDate},endDate=${endDate}";
		List<ColumnParameters> allColumns =Arrays.asList(childfemale, childmale ,total);
		List<String> indSuffixes = Arrays.asList("CF","CM", "TT"); 
		EmrReportingUtils.addRow(dsd, "F1", "No. of detected cases (Cumulative no. of active follow up  patients ever started on ART )", ReportUtils.map(artIndicators.startedArtCumulative(), indParams), allColumns,indSuffixes);
		
		return dsd;
	}
		
		private DataSetDefinition createNewInitiatedPatientTbDataSet() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("G");
		dsd.setDescription("New patients started on ART during this month");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("age", map(commonDimensions.standardAgeGroups(), "onDate=${endDate}"));
		dsd.addDimension("gender", map(commonDimensions.gender()));

		
		ColumnParameters childfemale =new ColumnParameters("FP", "0-14 years, female", "gender=F|age=<15");
		ColumnParameters childmale =new ColumnParameters("MP", "0-14 years, male", "gender=M|age=<15");
		
		ColumnParameters total =new ColumnParameters("T", "total", "age=<15");

		String indParams = "startDate=${startDate},endDate=${endDate}";
		List<ColumnParameters> allColumns =Arrays.asList(childfemale, childmale ,total);
		List<String> indSuffixes = Arrays.asList("CF","CM", "TT");     
		EmrReportingUtils.addRow(dsd, "G1", "  No. of detected cases (New patients started on ART)", ReportUtils.map(artIndicators.startedArt(), indParams), allColumns,indSuffixes);
		
		return dsd;
		}
		
		private DataSetDefinition createTbDataSet() {
			CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
			dsd.setName("H");
			dsd.setDescription("No. of patients on ART transferred in this month");
			dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
			dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
			dsd.addDimension("age", map(commonDimensions.standardAgeGroups(), "onDate=${endDate}"));
			dsd.addDimension("gender", map(commonDimensions.gender()));

			
			ColumnParameters childfemale =new ColumnParameters("FP", "0-14 years, female", "gender=F|age=<15");
			ColumnParameters childmale =new ColumnParameters("MP", "0-14 years, male", "gender=M|age=<15");
			
			ColumnParameters total =new ColumnParameters("T", "total", "age=<15");

			String indParams = "startDate=${startDate},endDate=${endDate}";
			List<ColumnParameters>allColumns =Arrays.asList(childfemale, childmale ,total);
			List<String> indSuffixes = Arrays.asList("CF","CM", "TT");   
	                
			EmrReportingUtils.addRow(dsd, "H1", "No. of detected cases (Transferred in)", ReportUtils.map(hivIndicators.restartART(), indParams), allColumns,indSuffixes);
			return dsd;
		}
		

		
		private DataSetDefinition createCumulativeTbDataSet() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("I");
		dsd.setDescription("Cumulative no. of active follow up patients ever started on ART at the end of this month ");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("age", map(commonDimensions.standardAgeGroups(), "onDate=${endDate}"));
		dsd.addDimension("gender", map(commonDimensions.gender()));

		ColumnParameters childfemale =new ColumnParameters("FP", "0-14 years, female", "gender=F|age=<15");
		ColumnParameters childmale =new ColumnParameters("MP", "0-14 years, male", "gender=M|age=<15");
		ColumnParameters total =new ColumnParameters("T", "total", "age=<15");


		String indParams = "startDate=${startDate},endDate=${endDate}";
		List<ColumnParameters> allColumns =Arrays.asList(childfemale, childmale ,total);
		List<String> indSuffixes = Arrays.asList("CF","CM", "TT");   
	            
	            
		EmrReportingUtils.addRow(dsd, "I1", "  No. of detected cases (Cumulative no. of patients ever started on ART )", ReportUtils.map(artIndicators.startedArtCumulativeResult(), indParams), allColumns,indSuffixes);
		return dsd;
	}
	
		private DataSetDefinition createHivDataSet() { 
			CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
			dsd.setName("J");
			dsd.setDescription("No. of  HIV positive TB patients who have received ART during this month");
			dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
			dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
			dsd.addDimension("age", map(commonDimensions.standardAgeGroups(), "onDate=${endDate}"));
			dsd.addDimension("gender", map(commonDimensions.gender()));

			ColumnParameters childfemale =new ColumnParameters("FP", "0-14 years, female", "gender=F|age=<15");
			ColumnParameters childmale =new ColumnParameters("MP", "0-14 years, male", "gender=M|age=<15");
			ColumnParameters total=new ColumnParameters("T", "total", "age=<15");
			
			
			String indParams = "startDate=${startDate},endDate=${endDate}";
			List<ColumnParameters> allColumns = Arrays.asList(childfemale, childmale ,total);
			List<String> indSuffixes = Arrays.asList("CF","CM","TT");    
	                
			EmrReportingUtils.addRow(dsd, "J1", "No. of detected cases (TB patients on ART)", ReportUtils.map(hivIndicators.initiatedARTandTB(), indParams), allColumns,indSuffixes);
			return dsd;
		}
		
		private DataSetDefinition createCumulativeHivDataSet() { 
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("K");
		dsd.setDescription("Cumulative no. of HIV positive TB patients who received ART at the end of this month ");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("age", map(commonDimensions.standardAgeGroups(), "onDate=${endDate}"));
		dsd.addDimension("gender", map(commonDimensions.gender()));

		
		ColumnParameters childfemale =new ColumnParameters("FP", "0-14 years, female", "gender=F|age=<15");
		ColumnParameters childmale =new ColumnParameters("MP", "0-14 years, male", "gender=M|age=<15");
		ColumnParameters total=new ColumnParameters("T", "total", "age=<15");
		
		
		String indParams = "startDate=${startDate},endDate=${endDate}";
		List<ColumnParameters> allColumns = Arrays.asList(childfemale, childmale ,total);
		List<String> indSuffixes = Arrays.asList("CF","CM","TT");    
		EmrReportingUtils.addRow(dsd, "K1", "Cumulative No. of detected cases (TB patients on ART)", ReportUtils.map(hivIndicators.enrolledCumulativeTB(), indParams),  allColumns ,indSuffixes);
		
		return dsd;
	}
		private DataSetDefinition creatDataSetForCdFourCount() {
			CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
			dsd.setName("L");
			dsd.setDescription("No. of patients tested for CD4 count");
			dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
			dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
			dsd.addDimension("age", map(commonDimensions.standardAgeGroups(), "onDate=${endDate}"));
			dsd.addDimension("gender", map(commonDimensions.gender()));
			
			
			ColumnParameters childfemale =new ColumnParameters("FP", "0-14 years, female", "gender=F|age=<15");
			ColumnParameters childmale =new ColumnParameters("MP", "0-14 years, male", "gender=M|age=<15");
			ColumnParameters total=new ColumnParameters("T", "total", "age=<15");

			String indParams = "startDate=${startDate},endDate=${endDate}";
			List<ColumnParameters> allColumns = Arrays.asList(childfemale, childmale ,total);
			List<String> indSuffixes = Arrays.asList("CF","CM","TT");    
	                
			EmrReportingUtils.addRow(dsd, "L1", "No. of patients tested for CD4 count ", ReportUtils.map(hivIndicators.cdFourTest(), indParams), allColumns,indSuffixes);
			return dsd;
		}
		
		private DataSetDefinition creatDataSetForViralLoad() {
			CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
			dsd.setName("M");
			dsd.setDescription("No. of patients tested for viral load");
			dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
			dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
			dsd.addDimension("age", map(commonDimensions.standardAgeGroups(), "onDate=${endDate}"));
			dsd.addDimension("gender", map(commonDimensions.gender()));

			ColumnParameters childfemale =new ColumnParameters("FP", "0-14 years, female", "gender=F|age=<15");
			ColumnParameters childmale =new ColumnParameters("MP", "0-14 years, male", "gender=M|age=<15");
			ColumnParameters total=new ColumnParameters("T", "total", "age=<15");

			String indParams = "startDate=${startDate},endDate=${endDate}";
			List<ColumnParameters> allColumns = Arrays.asList(childfemale, childmale ,total);
			List<String> indSuffixes = Arrays.asList("CF","CM","TT");    
	                
			EmrReportingUtils.addRow(dsd, "M1", "No. of patients tested for viral load", ReportUtils.map(hivIndicators.viralLoadTest(), indParams), allColumns,indSuffixes);
			return dsd;
		}
		
		private DataSetDefinition createNewHIVEnrolledDataSet() {
			CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
			dsd.setName("N");
			dsd.setDescription("New patients enrolled in HIV care during this month");
			dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
			dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
			dsd.addDimension("age", map(commonDimensions.standardAgeGroups(), "onDate=${endDate}"));
			dsd.addDimension("gender", map(commonDimensions.gender()));
			
			
			ColumnParameters childfemale =new ColumnParameters("FP", "0-14 years, female", "gender=F|age=<15");
			ColumnParameters childmale =new ColumnParameters("MP", "0-14 years, male", "gender=M|age=<15");
			ColumnParameters total=new ColumnParameters("T", "total", "age=<15");

			String indParams = "startDate=${startDate},endDate=${endDate}";
			List<ColumnParameters> allColumns = Arrays.asList(childfemale, childmale ,total);
			List<String> indSuffixes = Arrays.asList("CF","CM","TT");  
	                
			EmrReportingUtils.addRow(dsd, "N1", "No. of ", ReportUtils.map(hivIndicators.notInART(), indParams), allColumns,indSuffixes);
			return dsd;
		}
		
		private DataSetDefinition createHIVEnrolledwithPerformanceADataSet() {
			CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
			dsd.setName("O");
			dsd.setDescription("Performance scale (A, B, C)");
			dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
			dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
			dsd.addDimension("age", map(commonDimensions.standardAgeGroups(), "onDate=${endDate}"));
			dsd.addDimension("gender", map(commonDimensions.gender()));
			dsd.addDimension("parameter", map(commonDimensions.performanceScales()));
			
			
			List<ColumnParameters> columnsA = new ArrayList<ColumnParameters>();
			columnsA.add(new ColumnParameters("FP", "Performance scale A,0-14 years, female", "gender=F|age=<15|parameter=A"));
			columnsA.add(new ColumnParameters("MP", "Performance scale A,0-14 years, male", "gender=M|age=<15|parameter=A"));
			columnsA.add(new ColumnParameters("T", "Performance scale A,total", ""));
			
			List<ColumnParameters> columnsB = new ArrayList<ColumnParameters>();
			columnsB.add(new ColumnParameters("FP1", "Performance scale B,0-14 years, female", "gender=F|age=<15|parameter=B"));
			columnsB.add(new ColumnParameters("MP1", "Performance scale B,0-14 years, male", "gender=M|age=<15|parameter=B"));
			columnsB.add(new ColumnParameters("T1", "Performance scale B,total", ""));
			
			List<ColumnParameters> columnsC = new ArrayList<ColumnParameters>();
			columnsC.add(new ColumnParameters("FP2", "Performance scale C,0-14 years, female", "gender=F|age=<15|parameter=C"));
			columnsC.add(new ColumnParameters("MP2", "Performance scale C,0-14 years, male", "gender=M|age=<15|parameter=C"));
			columnsC.add(new ColumnParameters("T2", "Performance scale C,total", ""));
			String indParams = "startDate=${startDate},endDate=${endDate}";
	                
			EmrReportingUtils.addRow(dsd, "O1", "No. of detected case (Performance Scale A) ", ReportUtils.map(hivIndicators.performanceScaleA(), indParams), columnsA);
			EmrReportingUtils.addRow(dsd, "O2", "No. of detected case (Performance Scale B) ", ReportUtils.map(hivIndicators.performanceScaleB(), indParams), columnsB);
			EmrReportingUtils.addRow(dsd, "O3", "No. of detected case (Performance Scale C) ", ReportUtils.map(hivIndicators.performanceScaleC(), indParams), columnsC);
			return dsd;
		}
		
		private DataSetDefinition createHIVEnrolledwithRiskDataSet() {
			CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
			dsd.setName("P");
			dsd.setDescription("Risk Factors Code ");
			dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
			dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
			dsd.addDimension("parameter", map(commonDimensions.riskFactor()));
			dsd.addDimension("gender", map(commonDimensions.gender()));
			dsd.addDimension("age", map(commonDimensions.standardAgeGroups(), "onDate=${endDate}"));
			
			ColumnParameters MSMfemale=new ColumnParameters("T1", " risk factor for HIV as MSM ,female", "parameter=1|gender=F|age=<15");
			ColumnParameters MSMmale=new ColumnParameters("T11", " risk factor for HIV as MSM ,male", "parameter=1|gender=M|age=<15");
			ColumnParameters MSMtotal=new ColumnParameters("T12", "risk factor for HIV as MSM,total", "age=<15");
			
			ColumnParameters SWfemale=new ColumnParameters("T2", "risk factor for HIV as SW,female", "parameter=2|gender=F|age=<15");
			ColumnParameters SWmale=new ColumnParameters("T22", "risk factor for HIV as SW,male", "parameter=2|gender=M|age=<15");
			ColumnParameters SWtotal=new ColumnParameters("T23", "risk factor for HIV as SW,total", "age=<15");
			
			ColumnParameters heterosexualfemale=new ColumnParameters("T3", "risk factor for HIV as heterosexual,female", "parameter=3|gender=F|age=<15");
			ColumnParameters heterosexualmale=new ColumnParameters("T33", "risk factor for HIV as heterosexual,male", "parameter=3|gender=M|age=<15");
			ColumnParameters heterosexualtotal=new ColumnParameters("T34", "risk factor for HIV as heterosexual,total", "age=<15");
			
			ColumnParameters HIVfemale=new ColumnParameters("T4", "risk factor for HIV as IDU,female", "parameter=4|gender=F|age=<15");
			ColumnParameters HIVmale=new ColumnParameters("T44", "risk factor for HIV as IDU,male", "parameter=4|gender=M|age=<15");
			ColumnParameters HIVtotal=new ColumnParameters("T45", "risk factor for HIV as IDU,total", "age=<15");
			
			ColumnParameters Bloodtransfusionfemale=new ColumnParameters("T5", "risk factor for HIV as Blood transfusion,female", "parameter=5|gender=F|age=<15");
			ColumnParameters Bloodtransfusionmale=new ColumnParameters("T55", "risk factor for HIV as Blood transfusion,male", "parameter=5|gender=M|age=<15");
			ColumnParameters Bloodtransfusiontotal=new ColumnParameters("T56", "risk factor for HIV as Blood transfusion,total", "age=<15");
			
			ColumnParameters Motfemale=new ColumnParameters("T6", "risk factor for HIV as Mother to child,female", "parameter=6|gender=F|age=<15");
			ColumnParameters Motmale=new ColumnParameters("T66", "risk factor for HIV as Mother to child,male", "parameter=6|gender=M|age=<15");
			ColumnParameters Mottotal=new ColumnParameters("T67", "risk factor for HIV as Mother to child,total", "age=<15");
			
			ColumnParameters unknwnfemale=new ColumnParameters("T7", "risk factor for HIV as unknown,female", "parameter=7|gender=F|age=<15");
			ColumnParameters unknwnmale=new ColumnParameters("T77", "risk factor for HIV as unknown,male", "parameter=7|gender=M|age=<15");
			ColumnParameters unknwntotal=new ColumnParameters("T78", "risk factor for HIV as unknown,total", "age=<15");
			List<ColumnParameters> allColumns1 = Arrays.asList(MSMfemale, MSMmale, MSMtotal);
			List<ColumnParameters> allColumns2 = Arrays.asList(SWfemale, SWmale, SWtotal);
			List<ColumnParameters> allColumns3 = Arrays.asList(heterosexualfemale, heterosexualmale, heterosexualtotal);
			List<ColumnParameters> allColumns4 = Arrays.asList(HIVfemale, HIVmale, HIVtotal);
			List<ColumnParameters> allColumns5 = Arrays.asList(Bloodtransfusionfemale, Bloodtransfusionmale, Bloodtransfusiontotal);
			List<ColumnParameters> allColumns6 = Arrays.asList(Motfemale, Motmale, Mottotal);
			List<ColumnParameters> allColumns7 = Arrays.asList(unknwnfemale, unknwnmale, unknwntotal);
			String indParams = "startDate=${startDate},endDate=${endDate}";
			
			EmrReportingUtils.addRow(dsd, "P1", "Total No. of detected case   ", ReportUtils.map(hivIndicators.riskFactor1(), indParams), allColumns1,Arrays.asList("01", "02","11"));
			EmrReportingUtils.addRow(dsd, "P2", "Total No. of detected case ", ReportUtils.map(hivIndicators.riskFactor2(), indParams), allColumns2,Arrays.asList("03", "04","22"));
			EmrReportingUtils.addRow(dsd, "P3", "Total No. of detected case  ", ReportUtils.map(hivIndicators.riskFactor3(), indParams), allColumns3,Arrays.asList("05", "06","33"));
			EmrReportingUtils.addRow(dsd, "P4", "Total No. of detected case  ", ReportUtils.map(hivIndicators.riskFactor4(), indParams), allColumns4,Arrays.asList("07", "08","44"));
			EmrReportingUtils.addRow(dsd, "P5", "Total No. of detected case  ", ReportUtils.map(hivIndicators.riskFactor5(), indParams), allColumns5,Arrays.asList("09", "10","55"));
			EmrReportingUtils.addRow(dsd, "P6", "Total No. of detected case ", ReportUtils.map(hivIndicators.riskFactor6(), indParams), allColumns6,Arrays.asList("11", "12","66"));
			EmrReportingUtils.addRow(dsd, "P7", "Total No. of detected case ", ReportUtils.map(hivIndicators.riskFactor7(), indParams), allColumns7,Arrays.asList("13", "14","77"));
			return dsd;
		}
		private DataSetDefinition createOIDataSet() {
			CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
			dsd.setName("Q");
			dsd.setDescription("No. of patients treated for  Opportunistic Infections during this month ");
			dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
			dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
			dsd.addDimension("gender", map(commonDimensions.gender()));
			dsd.addDimension("age", map(commonDimensions.standardAgeGroups(), "onDate=${endDate}"));
			
			
			ColumnParameters childfemale =new ColumnParameters("FP", "0-14 years, female", "gender=F|age=<15");
			ColumnParameters childmale =new ColumnParameters("MP", "0-14 years, male", "gender=M|age=<15");
			ColumnParameters total=new ColumnParameters("T", "total", "age=<15");

			String indParams = "startDate=${startDate},endDate=${endDate}";
			List<ColumnParameters> allColumns = Arrays.asList(childfemale, childmale ,total);
			List<String> indSuffixes = Arrays.asList("CF","CM","TT");  
			
			EmrReportingUtils.addRow(dsd, "Q1", "No. of detected cases (Treated for OI)", ReportUtils.map(hivIndicators.givenDrugsForOI(), indParams), allColumns,indSuffixes);
			return dsd;
		}
		
		private DataSetDefinition createHIVAdherenceDataSet() {
			CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
			dsd.setName("R");
			dsd.setDescription("Of those assessed for adherence, level of adherence in the last month");
			dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
			dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
			dsd.addDimension("age", ReportUtils.map(commonDimensions.standardAgeGroups(), "onDate=${endDate}"));
			dsd.addDimension("gender", ReportUtils.map(commonDimensions.gender()));

			ColumnParameters childfemale =new ColumnParameters("FP", "0-14 years, female", "gender=F|age=<15");
			ColumnParameters childmale =new ColumnParameters("MP", "0-14 years, male", "gender=M|age=<15");
			ColumnParameters total=new ColumnParameters("T", "total", "age=<15");

			String indParams = "startDate=${startDate},endDate=${endDate}";
			List<ColumnParameters> allColumns = Arrays.asList(childfemale, childmale ,total);
			List<String> indSuffixes = Arrays.asList("CF","CM","TT");  

			EmrReportingUtils.addRow(dsd, "R1-1", "< 5% of doses missed in a period of 30 days (>95%)", ReportUtils.map(hivIndicators.levelOfAdherence(0,5), indParams), allColumns,indSuffixes);
			EmrReportingUtils.addRow(dsd, "R1-2", "(5-20)% of doses missed in a period of 30 days (80-95%)", ReportUtils.map(hivIndicators.levelOfAdherence(5,20), indParams), allColumns,indSuffixes);
			EmrReportingUtils.addRow(dsd, "R1-3", ">20% of doses missed in a period of 30 days (<80%)", ReportUtils.map(hivIndicators.levelOfAdherence(20,100), indParams), allColumns,indSuffixes);


			return dsd;
		}
		private DataSetDefinition createLevelAdherenceDataSet() {
			CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
			dsd.setName("S");
			dsd.setDescription(" No. of patients assessed for adherence during this month");
			dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
			dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
			dsd.addDimension("age", ReportUtils.map(commonDimensions.standardAgeGroups(), "onDate=${endDate}"));
			dsd.addDimension("gender", ReportUtils.map(commonDimensions.gender()));

			
			ColumnParameters childfemale =new ColumnParameters("FP", "0-14 years, female", "gender=F|age=<15");
			ColumnParameters childmale =new ColumnParameters("MP", "0-14 years, male", "gender=M|age=<15");
			ColumnParameters total=new ColumnParameters("T", "total", "age=<15");

			String indParams = "startDate=${startDate},endDate=${endDate}";
			List<ColumnParameters> allColumns = Arrays.asList(childfemale, childmale ,total);
			List<String> indSuffixes = Arrays.asList("CF","CM","TT");  
			EmrReportingUtils.addRow(dsd, "S1", "No. of patients assessed for adherence during this month", ReportUtils.map(hivIndicators.levelOfAdherence(0,100), indParams), allColumns,indSuffixes);
			


			return dsd;
		}
		
		private DataSetDefinition createElligibleARTDataSet() {
			CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
			dsd.setName("T");
			dsd.setDescription("No. of medically eligible patients currently remaining on waiting list for ART at the end of this month");
			dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
			dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
			dsd.addDimension("age", map(commonDimensions.standardAgeGroups(), "onDate=${endDate}"));
			dsd.addDimension("gender", map(commonDimensions.gender()));

			ColumnParameters childfemale =new ColumnParameters("FP", "0-14 years, female", "gender=F|age=<15");
			ColumnParameters childmale =new ColumnParameters("MP", "0-14 years, male", "gender=M|age=<15");
			ColumnParameters total=new ColumnParameters("T", "total", "age=<15");

			String indParams = "startDate=${startDate},endDate=${endDate}";
			List<ColumnParameters> allColumns = Arrays.asList(childfemale, childmale ,total);
			List<String> indSuffixes = Arrays.asList("CF","CM","TT");  
	                
			EmrReportingUtils.addRow(dsd, "T1", "No. of detected cases (Eligible for ART)", ReportUtils.map(hivIndicators.notInART(), indParams), allColumns,indSuffixes);
			return dsd;
		}
	
}
