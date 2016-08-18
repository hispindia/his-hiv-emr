/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

package org.openmrs.module.kenyaemr.fragment.controller.patient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.DrugOrder;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Form;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.Order;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.PatientIdentifierType;
import org.openmrs.PatientProgram;
import org.openmrs.Person;
import org.openmrs.PersonAddress;
import org.openmrs.PersonName;
import org.openmrs.Program;
import org.openmrs.Visit;
import org.openmrs.VisitAttribute;
import org.openmrs.VisitAttributeType;
import org.openmrs.VisitType;
import org.openmrs.api.VisitService;
import org.openmrs.api.context.Context;
import org.openmrs.module.idgen.service.IdentifierSourceService;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.Metadata;
import org.openmrs.module.kenyaemr.api.KenyaEmrService;
import org.openmrs.module.kenyaemr.metadata.ArtMetadata;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
import org.openmrs.module.kenyaemr.model.DrugOrderProcessed;
import org.openmrs.module.kenyaemr.wrapper.PatientWrapper;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.ui.framework.fragment.action.SuccessResult;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.util.OpenmrsConstants;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 * Merge patients form fragment
 */
public class ImportPatientsListFragmentController {

	protected static final Log log = LogFactory
			.getLog(ImportPatientsListFragmentController.class);
	String[] firstlineConcept = { "163494", "163495", "160124", "1652",
			"162563", "160124", "163496", "163497", "163498", "163499",
			"163500", "162199", "163501" };
	String[] secondlineConcept = { "162561", "162959", "163503", "162562",
			"163504", "163505", "163506", "163507", "163508" };
	String[] thirdlineConcept = { "163510" };

	public void controller(
			@RequestParam(required = false, value = "returnUrl") String returnUrl,
			PageModel model) {
		model.addAttribute("returnUrl", returnUrl);
	}

	@SuppressWarnings("deprecation")
	public Object submit(HttpServletRequest request) throws Exception {
		// Constant values used across all the code

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile multipartModuleFile = multipartRequest.getFile("upload");
		InputStream inputStream = multipartModuleFile.getInputStream();

		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = firstSheet.iterator();
		int rowCount = 0;

		while (iterator.hasNext()) {
			Row nextRow = iterator.next();
			Iterator<Cell> cellIterator = nextRow.cellIterator();
			if (rowCount > 0) {
				ArrayList<String> legacyData = new ArrayList<String>();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						legacyData.add(cell.getColumnIndex(),
								cell.getStringCellValue());
						break;
					case Cell.CELL_TYPE_NUMERIC:
						if (HSSFDateUtil.isCellDateFormatted(cell)) {
							legacyData.add(cell.getColumnIndex(),
									String.valueOf(cell.getDateCellValue()));
						} else {

							legacyData.add(cell.getColumnIndex(),
									NumberToTextConverter.toText(cell
											.getNumericCellValue()));
						}
						break;
					case Cell.CELL_TYPE_BLANK:
						legacyData.add(cell.getColumnIndex(), null);
						break;
					}
				}
				int i = 0;
				for (String s : legacyData) {

					i++;
				}
				/*
				 * Start Patient Creation
				 */
				try {
					if (legacyData.get(0) != null) {
						Patient toSave = new Patient(); // Creating a new
														// patient
														// and
						// person
						PersonName personName = new PersonName();
						PersonAddress personAddress = new PersonAddress();
						Location location;

						SimpleDateFormat formatter = new SimpleDateFormat(
								"E MMM dd HH:mm:ss Z yyyy");
						Date dateBith = new Date();
						try {
							dateBith = (Date) formatter
									.parse(legacyData.get(2));

						} catch (ParseException e) {
							e.printStackTrace();
						}

						toSave.setGender(legacyData.get(3));
						toSave.setBirthdate(dateBith);
						toSave.setBirthdateEstimated(false);
						toSave.setDead(false);
						/*
						 * toSave.setDeathDate(deathDate);
						 * toSave.setCauseOfDeath(dead ? Dictionary
						 * .getConcept(CAUSE_OF_DEATH_PLACEHOLDER) : null);
						 */

						if (legacyData.get(1) != "") {
							personName.setGivenName(legacyData.get(1));
							personName.setFamilyName("(NULL)");
							toSave.addName(personName);
						}

						// toSave.
						personAddress.setAddress1(legacyData.get(9));
						personAddress.setCountyDistrict(legacyData.get(10));
						toSave.addAddress(personAddress);

						PatientWrapper wrapper = new PatientWrapper(toSave);

						wrapper.getPerson().setTelephoneContact(
								legacyData.get(8));

						location = Context.getService(KenyaEmrService.class)
								.getDefaultLocation();

						if (legacyData.get(4) != null) {
							wrapper.setPreArtRegistrationNumber(
									legacyData.get(4), location);
						}

						if (legacyData.get(5) != null) {
							wrapper.setArtRegistrationNumber(legacyData.get(5),
									location);
						}

						if (legacyData.get(6) != null) {
							wrapper.setNapArtRegistrationNumber(
									legacyData.get(5), location);
						}

						// Algorithm to generate system generated patient
						// Identifier
						Calendar now = Calendar.getInstance();
						String shortName = Context
								.getAdministrationService()
								.getGlobalProperty(
										OpenmrsConstants.GLOBAL_PROPERTY_PATIENT_IDENTIFIER_PREFIX);

						String noCheck = shortName
								+ String.valueOf(now.get(Calendar.YEAR))
										.substring(2, 4)
								+ String.valueOf(now.get(Calendar.MONTH) + 1)
								+ String.valueOf(now.get(Calendar.DATE))

								+ String.valueOf(now.get(Calendar.HOUR))
								+ String.valueOf(now.get(Calendar.MINUTE))
								+ String.valueOf(now.get(Calendar.SECOND))
								+ String.valueOf(new Random()
										.nextInt(9999 - 999 + 1));

						wrapper.setSystemPatientId(noCheck + "-"
								+ generateCheckdigit(noCheck), location);

						wrapper.setNextOfKinName(legacyData.get(11));
						wrapper.setNextOfKinContact(legacyData.get(12));

						if (legacyData.get(13) != null) {
							wrapper.setPreviousHivTestStatus("Yes");
							wrapper.setPreviousHivTestPlace(legacyData.get(14));

							Date capturedTestDate = new Date();
							try {
								capturedTestDate = formatter.parse(legacyData
										.get(13));

							} catch (ParseException e) {
								e.printStackTrace();
							}

							DateFormat testDate = new SimpleDateFormat(
									"dd-MMMM-yyyy");
							wrapper.setPreviousHivTestDate(testDate
									.format(capturedTestDate));
						} else {
							wrapper.setPreviousHivTestStatus("No");
						}

						wrapper.setPreviousClinicName(legacyData.get(16));

						// Make sure everyone gets an OpenMRS ID
						PatientIdentifierType openmrsIdType = MetadataUtils
								.existing(
										PatientIdentifierType.class,
										CommonMetadata._PatientIdentifierType.OPENMRS_ID);
						PatientIdentifier openmrsId = toSave
								.getPatientIdentifier(openmrsIdType);

						if (openmrsId == null) {
							String generated = Context.getService(
									IdentifierSourceService.class)
									.generateIdentifier(openmrsIdType,
											"Registration");
							openmrsId = new PatientIdentifier(generated,
									openmrsIdType, location);
							toSave.addIdentifier(openmrsId);

							if (!toSave.getPatientIdentifier().isPreferred()) {
								openmrsId.setPreferred(true);
							}
						}

						Patient ret = Context.getPatientService().savePatient(
								toSave);

						// Explicitly save all identifier objects including
						// voided
						for (PatientIdentifier identifier : toSave
								.getIdentifiers()) {
							Context.getPatientService().savePatientIdentifier(
									identifier);
						}

						/*
						 * End Patient Creation
						 */

						// With value text and Date
						if (legacyData.get(15) != null) {

							Date dateTransfer = null;
							if (legacyData.get(17) != null) {
								try {
									dateTransfer = formatter.parse(legacyData
											.get(17));

								} catch (ParseException e) {
									e.printStackTrace();
								}
							}

							Concept enrollementConcept = Context
									.getConceptService()
									.getConcept(
											Integer.parseInt(legacyData.get(15)));

							handleOncePerPatientObs(
									ret,
									Dictionary
											.getConcept(Dictionary.METHOD_OF_ENROLLMENT),
									enrollementConcept, "", dateTransfer, null);
						}

						if (legacyData.get(7) != null) {
							Concept ingoConcept = Context
									.getConceptService()
									.getConcept(
											Integer.parseInt(legacyData.get(7)));
							handleOncePerPatientObs(
									ret,
									Dictionary.getConcept(Dictionary.INGO_NAME),
									ingoConcept, "", null, null);
						}

						/**
						 ** Start : Visit, Encounter and Program creation for
						 * Patient entered as Legacy Data Visit start date
						 **/
						Date curDate = new Date();
						Date dateVisit = null;
						try {
							dateVisit = formatter.parse(legacyData.get(18));

						} catch (ParseException e) {
							e.printStackTrace();
						}

						DateFormat visitDateInExcel = new SimpleDateFormat(
								"dd-MMM-yyyy");
						String dateCheck = visitDateInExcel.format(dateVisit);
						SimpleDateFormat mysqlDateTimeFormatter = new SimpleDateFormat(
								"dd-MMM-yy HH:mm:ss");

						if (legacyData.get(18) != null) {
							try {
								dateVisit = mysqlDateTimeFormatter
										.parse(dateCheck + " "
												+ curDate.getHours() + ":"
												+ curDate.getMinutes() + ":"
												+ curDate.getSeconds());
							} catch (ParseException e) {
								dateVisit = curDate;
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

						Visit visit = new Visit();
						visit.setPatient(ret);
						visit.setStartDatetime(dateVisit);
						visit.setVisitType(MetadataUtils.existing(
								VisitType.class,
								CommonMetadata._VisitType.OUTPATIENT));
						visit.setLocation(Context.getService(
								KenyaEmrService.class).getDefaultLocation());

						VisitAttributeType attrType = Context
								.getService(VisitService.class)
								.getVisitAttributeTypeByUuid(
										CommonMetadata._VisitAttributeType.NEW_PATIENT);
						if (attrType != null) {
							VisitAttribute attr = new VisitAttribute();
							attr.setAttributeType(attrType);
							attr.setVisit(visit);
							attr.setDateCreated(curDate);
							attr.setValue(true);
							visit.addAttribute(attr);
						}

						Visit visitSave = Context.getVisitService().saveVisit(
								visit);

						EncounterType hivEnrollEncType = MetadataUtils
								.existing(
										EncounterType.class,
										HivMetadata._EncounterType.HIV_ENROLLMENT);

						EncounterType registrationEncType = MetadataUtils
								.existing(
										EncounterType.class,
										CommonMetadata._EncounterType.REGISTRATION);

						Encounter hivEnrollmentEncounter = new Encounter();

						hivEnrollmentEncounter
								.setEncounterType(hivEnrollEncType);
						hivEnrollmentEncounter.setPatient(ret);
						hivEnrollmentEncounter.setLocation(Context.getService(
								KenyaEmrService.class).getDefaultLocation());

						hivEnrollmentEncounter.setDateCreated(curDate);
						hivEnrollmentEncounter.setEncounterDatetime(dateVisit);

						hivEnrollmentEncounter.setForm(MetadataUtils.existing(
								Form.class, HivMetadata._Form.HIV_ENROLLMENT));
						hivEnrollmentEncounter.setVisit(visitSave);
						hivEnrollmentEncounter.setVoided(false);
						Encounter enHivNew = Context.getEncounterService()
								.saveEncounter(hivEnrollmentEncounter);

						PatientProgram patientProgram = new PatientProgram();
						patientProgram.setPatient(ret);
						patientProgram.setProgram(MetadataUtils.existing(
								Program.class, HivMetadata._Program.HIV));
						patientProgram.setDateEnrolled(enHivNew
								.getEncounterDatetime());
						patientProgram.setDateCreated(curDate);
						Context.getProgramWorkflowService().savePatientProgram(
								patientProgram);

						Encounter personalEncounter = new Encounter();

						personalEncounter.setEncounterType(hivEnrollEncType);
						personalEncounter.setPatient(ret);

						personalEncounter.setDateCreated(curDate);
						personalEncounter.setEncounterDatetime(dateVisit);
						personalEncounter.setLocation(Context.getService(
								KenyaEmrService.class).getDefaultLocation());

						personalEncounter
								.setForm(MetadataUtils.existing(Form.class,
										Metadata.Form.HIV_PERSONAL_HISTORY));
						personalEncounter.setVisit(visitSave);
						personalEncounter.setVoided(false);
						Encounter enpersonalrecordresultNew = Context
								.getEncounterService().saveEncounter(
										personalEncounter);
						if (legacyData.get(19) != null) {

							Concept literate = Context
									.getConceptService()
									.getConcept(
											Integer.parseInt(legacyData.get(19)));
							handleOncePerPatientObs(ret,
									Dictionary.getConcept(Dictionary.LITERATE),
									literate, "", null, null,
									enpersonalrecordresultNew, null);

						}
						if (legacyData.get(21) != null) {

							String value = legacyData.get(21);

							String[] valueList = value.split("\\s*,\\s*");

							for (String riskname : valueList) {

								Concept riskConcept = Context
										.getConceptService().getConcept(
												riskname);

								handleOncePerPatientObs(
										ret,
										Dictionary
												.getConcept(Dictionary.HIV_RISK_FACTOR),
										riskConcept, "", null, null,
										enpersonalrecordresultNew, null);

							}

						}
						if (legacyData.get(22) != null) {

							Concept idssubstituion = Context
									.getConceptService()
									.getConcept(
											Integer.parseInt(legacyData.get(22)));
							handleOncePerPatientObs(
									ret,
									Dictionary
											.getConcept(Dictionary.IDU_PERSONAL_HISTORY),
									idssubstituion, "", null, null,
									enpersonalrecordresultNew, null);

						}

						if (legacyData.get(23) != null) {

							Concept idssubstituionvalue = Context
									.getConceptService()
									.getConcept(
											Integer.parseInt(legacyData.get(23)));
							handleOncePerPatientObs(
									ret,
									Dictionary
											.getConcept(Dictionary.IDU_NAME_PERSONAL_HISTORY),
									idssubstituionvalue, "", null, null,
									enpersonalrecordresultNew, null);

						}
						if (legacyData.get(29) != null) {

							Concept employedvalue = Context
									.getConceptService()
									.getConcept(
											Integer.parseInt(legacyData.get(29)));
							handleOncePerPatientObs(ret,
									Dictionary.getConcept(Dictionary.EMPLOYED),
									employedvalue, "", null, null,
									enpersonalrecordresultNew, null);
						}

						if (legacyData.get(30) != null) {

							Concept alcoholicvalue = Context
									.getConceptService()
									.getConcept(
											Integer.parseInt(legacyData.get(30)));
							handleOncePerPatientObs(
									ret,
									Dictionary
											.getConcept(Dictionary.ALCOHOLIC_TYPE),
									alcoholicvalue, "", null, null,
									enpersonalrecordresultNew, null);
						}

						Encounter familyEncounter = new Encounter();

						familyEncounter.setEncounterType(registrationEncType);
						familyEncounter.setPatient(ret);

						familyEncounter.setDateCreated(curDate);
						familyEncounter.setEncounterDatetime(dateVisit);
						familyEncounter.setLocation(Context.getService(
								KenyaEmrService.class).getDefaultLocation());

						familyEncounter.setForm(MetadataUtils.existing(
								Form.class, Metadata.Form.HIV_FAMILY_HISTORY));
						familyEncounter.setVisit(visitSave);
						familyEncounter.setVoided(false);
						Encounter enfamilyrecordresultNew = Context
								.getEncounterService().saveEncounter(
										familyEncounter);
						if (legacyData.get(20) != null) {

							Concept martalstatus = Context
									.getConceptService()
									.getConcept(
											Integer.parseInt(legacyData.get(20)));
							handleOncePerPatientObs(
									ret,
									Dictionary
											.getConcept(Dictionary.CIVIL_STATUS),
									martalstatus, "", null, null,
									enfamilyrecordresultNew, null);
						}
						Encounter drugEncounter = new Encounter();

						drugEncounter.setEncounterType(hivEnrollEncType);
						drugEncounter.setPatient(ret);

						drugEncounter.setDateCreated(curDate);
						drugEncounter.setEncounterDatetime(dateVisit);
						drugEncounter.setLocation(Context.getService(
								KenyaEmrService.class).getDefaultLocation());

						drugEncounter.setForm(MetadataUtils.existing(
								Form.class, Metadata.Form.HIV_DRUG_HISTORY));
						drugEncounter.setVisit(visitSave);
						drugEncounter.setVoided(false);
						Encounter endrugrecordresultNew = Context
								.getEncounterService().saveEncounter(
										drugEncounter);
						if (legacyData.get(24) != null) {

							Concept drughistoryart = Context
									.getConceptService()
									.getConcept(
											Integer.parseInt(legacyData.get(24)));

							handleOncePerPatientObs(
									ret,
									Dictionary
											.getConcept(Dictionary.DRUG_HISTORY_ART_RECEIVED),
									drughistoryart, "", null, null,
									endrugrecordresultNew, null);

						}
						if (legacyData.get(25) != null) {

							Concept drughistoryarttype = Context
									.getConceptService()
									.getConcept(
											Integer.parseInt(legacyData.get(25)));

							handleOncePerPatientObs(
									ret,
									Dictionary
											.getConcept(Dictionary.DRUG_HISTORY_ART_RECEIVED_TYPE),
									drughistoryarttype, "", null, null,
									endrugrecordresultNew, null);

						}
						if (legacyData.get(26) != null) {
							// String text="";

							boolean value = false;
							Obs drugtreatmentGroup = new Obs();
							drugtreatmentGroup.setPerson(ret);
							drugtreatmentGroup.setConcept(Dictionary
									.getConcept(Dictionary.DRUG_HISTORY_GROUP));

							drugtreatmentGroup.setObsDatetime(new Date());

							// Added value coded as per default obs object
							// format.
							drugtreatmentGroup.setValueCoded(null);
							// drugtreatmentGroup.setValueText(text);
							drugtreatmentGroup
									.setLocation(Context.getService(
											KenyaEmrService.class)
											.getDefaultLocation());

							drugtreatmentGroup
									.setEncounter(endrugrecordresultNew);

							drugtreatmentGroup.setValueBoolean(value);
							Obs drugtreat = Context.getObsService().saveObs(
									drugtreatmentGroup,
									"KenyaEMR History Details");

							if (legacyData.get(26) != null) {

								Concept place = Context.getConceptService()
										.getConcept(
												Integer.parseInt(legacyData
														.get(26)));
								handleOncePerPatientObs(
										ret,
										Dictionary
												.getConcept(Dictionary.DRUG_HISTORY_ART_RECEIVED_PLACE),
										place, "", null, null,
										endrugrecordresultNew, drugtreat);
							}

							if (legacyData.get(27) != null) {

								Concept drugarv = Context.getConceptService()
										.getConcept(
												Integer.parseInt(legacyData
														.get(27)));
								handleOncePerPatientObs(
										ret,
										Dictionary
												.getConcept(Dictionary.DRUG_REGIMEN_DRUG_HISTORY),
										drugarv, "", null, null,
										endrugrecordresultNew, drugtreat);
							}

							if (legacyData.get(28) != null) {

								Double dur = 0.0;
								Integer durationreslt = 0;

								durationreslt = Integer.parseInt(legacyData
										.get(28));
								dur = durationreslt.doubleValue();

								handleOncePerPatientObs(
										ret,
										Dictionary
												.getConcept(Dictionary.DRUG_DURATION),
										null, null, null, dur,
										endrugrecordresultNew, drugtreat);

							}

						}

						/*
						 * End : Visit, Encounter and Program creation for
						 * Patient entered as Legacy Data
						 */

						/*
						 * Personal History encounter
						 * 
						 * Encounter personalHistoryEncounter = new Encounter();
						 * 
						 * personalHistoryEncounter.setEncounterType(
						 * hivEnrollEncType);
						 * personalHistoryEncounter.setPatient(ret);
						 * personalHistoryEncounter
						 * .setLocation(Context.getService(
						 * KenyaEmrService.class).getDefaultLocation());
						 * 
						 * personalHistoryEncounter.setDateCreated(curDate);
						 * personalHistoryEncounter
						 * .setEncounterDatetime(visitSave .getStartDatetime());
						 * 
						 * personalHistoryEncounter
						 * .setForm(MetadataUtils.existing(Form.class,
						 * HivMetadata._Form.HIV_PERSONAL_HISTORY));
						 * 
						 * personalHistoryEncounter.setVoided(false); Encounter
						 * enPersonalHistoryNew = Context
						 * .getEncounterService().saveEncounter(
						 * personalHistoryEncounter);
						 * 
						 * // Risk Factor if (legacyData.get(20)!=null) {
						 * Concept riskFactor = Context.getConceptService()
						 * .getConcept( Integer.parseInt(legacyData.get(20)));
						 * handleOncePerPatientObs( ret, Dictionary
						 * .getConcept(Dictionary.HIV_RISK_FACTOR), riskFactor,
						 * "", null,null, enPersonalHistoryNew, null); }
						 * 
						 * // Literacy if (legacyData.get(18)!=null) { Concept
						 * literate = Context.getConceptService() .getConcept(
						 * Integer.parseInt(legacyData.get(18)));
						 * handleOncePerPatientObs(ret,
						 * Dictionary.getConcept(Dictionary.LITERATE), literate,
						 * "", null,null, enPersonalHistoryNew, null); }
						 * 
						 * // Employed if (legacyData.get(30)!=null) { Concept
						 * employed = Context.getConceptService() .getConcept(
						 * Integer.parseInt(legacyData.get(30)));
						 * handleOncePerPatientObs(ret,
						 * Dictionary.getConcept(Dictionary.EMPLOYED), employed,
						 * "", null, null,enPersonalHistoryNew, null); }
						 * 
						 * // Alcoholic if (legacyData.get(31)!=null) { Concept
						 * alcoholic = Context.getConceptService() .getConcept(
						 * Integer.parseInt(legacyData.get(31)));
						 * handleOncePerPatientObs(ret, Dictionary
						 * .getConcept(Dictionary.ALCOHOLIC_TYPE), alcoholic,
						 * "", null, null,enPersonalHistoryNew, null); }
						 * 
						 * // IDU Substitute if (legacyData.get(21)!=null) {
						 * Concept iduSubstitute = Context.getConceptService()
						 * .getConcept( Integer.parseInt(legacyData.get(21)));
						 * handleOncePerPatientObs(ret,
						 * 
						 * Dictionary.getConcept(Dictionary.IDU_PERSONAL_HISTORY)
						 * , iduSubstitute, "", null,null, enPersonalHistoryNew,
						 * null); }
						 * 
						 * // IDU Substitute type if (legacyData.get(22)!=null)
						 * { Concept iduSubstituteType =
						 * Context.getConceptService() .getConcept(
						 * Integer.parseInt(legacyData.get(22)));
						 * handleOncePerPatientObs( ret, Dictionary
						 * .getConcept(Dictionary.IDU_NAME_PERSONAL_HISTORY),
						 * iduSubstituteType, "",null, null,
						 * enPersonalHistoryNew, null); }
						 */
						/*
						 * Family History encounter
						 * 
						 * Encounter familyHistoryEncounter = new Encounter();
						 * 
						 * familyHistoryEncounter
						 * .setEncounterType(registrationEncType);
						 * familyHistoryEncounter.setPatient(ret);
						 * familyHistoryEncounter
						 * .setLocation(Context.getService(
						 * KenyaEmrService.class).getDefaultLocation());
						 * 
						 * familyHistoryEncounter.setDateCreated(curDate);
						 * familyHistoryEncounter.setEncounterDatetime(visitSave
						 * .getStartDatetime());
						 * 
						 * familyHistoryEncounter.setForm(MetadataUtils.existing(
						 * Form.class, HivMetadata._Form.FAMILY_HISTORY));
						 * 
						 * familyHistoryEncounter.setVoided(false); Encounter
						 * enFamilyHistoryNew = Context
						 * .getEncounterService().saveEncounter(
						 * familyHistoryEncounter);
						 * 
						 * // Married if (legacyData.get(22)!=null) { Concept
						 * married = Context.getConceptService() .getConcept(
						 * Integer.parseInt(legacyData.get(19)));
						 * handleOncePerPatientObs(ret,
						 * Dictionary.getConcept(Dictionary.CIVIL_STATUS),
						 * married, "", null, null,enFamilyHistoryNew, null); }
						 */
						/*
						 * Obstertric History encounter
						 * 
						 * Encounter obstetricHistoryEncounter = new
						 * Encounter();
						 * 
						 * obstetricHistoryEncounter
						 * .setEncounterType(registrationEncType);
						 * obstetricHistoryEncounter.setPatient(ret);
						 * obstetricHistoryEncounter
						 * .setLocation(Context.getService(
						 * KenyaEmrService.class).getDefaultLocation());
						 * 
						 * obstetricHistoryEncounter.setDateCreated(curDate);
						 * obstetricHistoryEncounter.(visitSave
						 * .getStartDatetime());
						 * 
						 * obstetricHistoryEncounter.setForm(MetadataUtils.existing
						 * ( Form.class, HivMetadata._Form.FAMILY_HISTORY));
						 * 
						 * obstetricHistoryEncounter.setVoided(false); Encounter
						 * enObstetricHistoryNew = Context
						 * .getEncounterService().saveEncounter(
						 * obstetricHistoryEncounter);
						 * 
						 * // Pregnancy if (legacyData.get(23)!=null) { Concept
						 * pregnancy = Context.getConceptService() .getConcept(
						 * Integer.parseInt(legacyData.get(23)));
						 * handleOncePerPatientObs( ret, Dictionary
						 * .getConcept(Dictionary.PREGNANCY_STATUS), pregnancy,
						 * "", null,null, enObstetricHistoryNew, null); }
						 * 
						 * // Family Planning if (legacyData.get(24)!=null) {
						 * Concept pregnancy = Context.getConceptService()
						 * .getConcept( Integer.parseInt(legacyData.get(24)));
						 * handleOncePerPatientObs( ret, Dictionary
						 * .getConcept(Dictionary.FAMILY_PLANNING), pregnancy,
						 * "", null, null,enObstetricHistoryNew, null); }
						 */
						/*
						 * Drug History encounter
						 * 
						 * Encounter drugHistoryEncounter = new Encounter();
						 * 
						 * drugHistoryEncounter.setEncounterType(hivEnrollEncType
						 * ); drugHistoryEncounter.setPatient(ret);
						 * drugHistoryEncounter.setLocation(Context.getService(
						 * KenyaEmrService.class).getDefaultLocation());
						 * 
						 * drugHistoryEncounter.setDateCreated(curDate);
						 * drugHistoryEncounter.setEncounterDatetime(visitSave
						 * .getStartDatetime());
						 * 
						 * drugHistoryEncounter.setForm(MetadataUtils.existing(
						 * Form.class, HivMetadata._Form.FAMILY_HISTORY));
						 * 
						 * drugHistoryEncounter.setVoided(false); Encounter
						 * enDrugHistoryNew = Context.getEncounterService()
						 * .saveEncounter(drugHistoryEncounter);
						 * 
						 * // Previous ART if (legacyData.get(25)!=null) {
						 * Concept previousArt = Context.getConceptService()
						 * .getConcept( Integer.parseInt(legacyData.get(25)));
						 * handleOncePerPatientObs( ret,
						 * 
						 * Dictionary
						 * .getConcept(Dictionary.DRUG_HISTORY_ART_RECEIVED),
						 * previousArt, "", null,null, enDrugHistoryNew, null);
						 * }
						 * 
						 * // Previous ART Type if (legacyData.get(26)!=null) {
						 * Concept previousArtType = Context.getConceptService()
						 * .getConcept( Integer.parseInt(legacyData.get(25)));
						 * handleOncePerPatientObs( ret, Dictionary
						 * .getConcept(Dictionary
						 * .DRUG_HISTORY_ART_RECEIVED_TYPE), previousArtType,
						 * "", null, null,enDrugHistoryNew, null); }
						 * 
						 * Obs drugGroup = null; // Create group for Drug
						 * History if (legacyData.get(28)!=null) { Obs o = new
						 * Obs(); o.setPerson(ret); o.setConcept(Dictionary
						 * .getConcept(Dictionary.DRUG_HISTORY_GROUP));
						 * o.setObsDatetime(new Date()); // Added value coded as
						 * per default obs object format.
						 * o.setValueCoded(Context
						 * .getConceptService().getConcept(1066));
						 * o.setLocation(
						 * Context.getService(KenyaEmrService.class)
						 * .getDefaultLocation());
						 * o.setEncounter(enDrugHistoryNew); drugGroup =
						 * Context.getObsService().saveObs(o,
						 * "KenyaEMR History Details");
						 * 
						 * // Duration if (legacyData.get(29)!=null) { double
						 * duration = Integer.parseInt(legacyData.get(29));
						 * handleOncePerPatientObs( ret, Dictionary
						 * .getConcept(Dictionary.DRUG_DURATION), null, "",
						 * null,duration, enDrugHistoryNew,
						 * drugGroup.getObsGroupId()); }
						 * 
						 * // Place if (legacyData.get(27)!=null) { Concept
						 * place = Context .getConceptService() .getConcept(
						 * Integer.parseInt(legacyData.get(27)));
						 * handleOncePerPatientObs( ret, Dictionary
						 * .getConcept(Dictionary
						 * .DRUG_HISTORY_ART_RECEIVED_PLACE), place, "",
						 * null,null, enDrugHistoryNew,
						 * drugGroup.getObsGroupId()); }
						 * 
						 * // Regimen Concept place =
						 * Context.getConceptService().getConcept(
						 * Integer.parseInt(legacyData.get(28)));
						 * handleOncePerPatientObs( ret, Dictionary
						 * .getConcept(Dictionary.DRUG_REGIMEN_DRUG_HISTORY),
						 * place, "", null, null,enDrugHistoryNew, drugGroup
						 * .getObsGroupId());
						 * 
						 * }
						 */
					}
				} catch (IndexOutOfBoundsException e) {
					e.printStackTrace();
				}
			}
			rowCount++;
		}

		int noOfSheets = workbook.getNumberOfSheets();

		if (noOfSheets < 1) {
			Sheet secondSheet = workbook.getSheetAt(1);
			Iterator<Row> iteratorSecond = secondSheet.iterator();
			int rowCountVisit = 0;

			while (iteratorSecond.hasNext()) {
				Row nextRow = iteratorSecond.next();
				Iterator<Cell> cellIterator = nextRow.cellIterator();
				if (rowCountVisit > 0) {

					ArrayList<String> legacyData = new ArrayList<String>();
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_STRING:
							legacyData.add(cell.getColumnIndex(),
									cell.getStringCellValue());
							break;
						case Cell.CELL_TYPE_NUMERIC:
							if (HSSFDateUtil.isCellDateFormatted(cell)) {
								legacyData
										.add(cell.getColumnIndex(), String
												.valueOf(cell
														.getDateCellValue()));
							} else {

								legacyData.add(cell.getColumnIndex(),
										NumberToTextConverter.toText(cell
												.getNumericCellValue()));
							}
							break;
						case Cell.CELL_TYPE_BLANK:
							legacyData.add(cell.getColumnIndex(), "");
							break;
						}
					}
					int i = 0;
					for (String s : legacyData) {

						i++;
					}
					Person person = null;
					Patient patient = null;
					PatientWrapper wrapper = null;
					int count = 0;
					Location location;
					location = Context.getService(KenyaEmrService.class)
							.getDefaultLocation();

					try {

						PatientIdentifierType pt = Context.getPatientService()
								.getPatientIdentifierTypeByUuid(
										"d59d0f67-4a05-4e41-bfad-342da68feb6f");

						List<PatientIdentifier> patList = Context
								.getPatientService().getPatientIdentifiers(
										legacyData.get(0).toString(), pt);

						for (PatientIdentifier p : patList) {

							patient = Context.getPatientService().getPatient(
									p.getPatient().getPatientId());
							wrapper = new PatientWrapper(patient);
							person = Context.getPersonService().getPerson(
									patient);
						}

						if (!legacyData.get(0).isEmpty()) {

							wrapper.setPreArtRegistrationNumber(
									legacyData.get(0), location);
						}
						if (!legacyData.get(1).isEmpty()) {

							wrapper.setNapArtRegistrationNumber(
									legacyData.get(1), location);

						}
						if (!legacyData.get(2).isEmpty()) {

							wrapper.setArtRegistrationNumber(legacyData.get(2),
									location);
						}
						SimpleDateFormat formatter = new SimpleDateFormat(
								"E MMM dd HH:mm:ss Z yyyy");
						Date curDate = new Date();
						Date dateVisit = null;
						try {
							dateVisit = formatter.parse(legacyData.get(3));

						} catch (ParseException e) {
							e.printStackTrace();
						}
						DateFormat visitDateInExcel = new SimpleDateFormat(
								"dd-MMM-yyyy");
						String dateCheck = "";
						SimpleDateFormat mysqlDateTimeFormatter = new SimpleDateFormat(
								"dd-MMM-yy HH:mm:ss");

						if (legacyData.get(3) != null) {
							Date curDatenew = new Date();
							dateCheck = visitDateInExcel.format(dateVisit);
							try {
								dateVisit = mysqlDateTimeFormatter
										.parse(dateCheck + " "
												+ curDatenew.getHours() + ":"
												+ curDatenew.getMinutes() + ":"
												+ curDatenew.getSeconds());

							} catch (ParseException e) {
								dateVisit = curDatenew;
								e.printStackTrace();
							}
						}

						List<Visit> visits = Context.getVisitService()
								.getActiveVisitsByPatient(patient);

						Visit v = new Visit();
						if (visits.isEmpty()) {
							Visit visit = new Visit();
							visit.setPatient(patient);
							visit.setStartDatetime(dateVisit);
							visit.setVisitType(MetadataUtils.existing(
									VisitType.class,
									CommonMetadata._VisitType.OUTPATIENT));
							visit.setLocation(Context.getService(
									KenyaEmrService.class).getDefaultLocation());
							if (person.getDead() == false) {
								v = Context.getVisitService().saveVisit(visit);
							}

						} else {
							for (Visit vLoop : visits) {
								v = vLoop;

							}
						}

						if (v.getId() != null) {
							EncounterType artEnrollEncType = MetadataUtils
									.existing(
											EncounterType.class,
											ArtMetadata._EncounterType.INITIATE_ART);
							Encounter artEncounter = new Encounter();

							artEncounter.setEncounterType(artEnrollEncType);
							artEncounter.setPatient(patient);
							artEncounter
									.setLocation(Context.getService(
											KenyaEmrService.class)
											.getDefaultLocation());

							artEncounter.setDateCreated(curDate);
							artEncounter.setEncounterDatetime(dateVisit);

							artEncounter
									.setForm(MetadataUtils.existing(Form.class,
											ArtMetadata._Form.INITIATE_ART));
							artEncounter.setVisit(v);

							artEncounter.setVoided(false);
							if (!legacyData.get(9).isEmpty()) {

								Encounter enartNew = Context
										.getEncounterService().saveEncounter(
												artEncounter);
							}
							PatientProgram pp = new PatientProgram();
							if (!legacyData.get(9).isEmpty()) {

								pp.setPatient(patient);
								pp.setProgram(MetadataUtils
										.existing(Program.class,
												ArtMetadata._Program.ART));

								Date artStartDate = null;
								Date curDatenew = new Date();

								try {
									artStartDate = (Date) formatter
											.parse(legacyData.get(9));
									dateCheck = visitDateInExcel
											.format(artStartDate);
									artStartDate = mysqlDateTimeFormatter
											.parse(dateCheck + " "
													+ curDatenew.getHours()
													+ ":"
													+ curDatenew.getMinutes()
													+ ":"
													+ curDatenew.getSeconds());
									pp.setDateEnrolled(artStartDate);
								} catch (ParseException e) {
									e.printStackTrace();
								}

								if (pp.getDateEnrolled() != null
										&& pp.getDateCompleted() == null) {
									PatientProgram program = Context
											.getProgramWorkflowService()
											.savePatientProgram(pp);
								}

							}

							EncounterType regEnrollEncType = MetadataUtils
									.existing(
											EncounterType.class,
											CommonMetadata._EncounterType.REGIMEN_ORDER);
							Encounter regEncounter = new Encounter();
							regEncounter.setEncounterType(regEnrollEncType);
							regEncounter.setPatient(patient);
							regEncounter
									.setLocation(Context
											.getLocationService()
											.getLocationByUuid(
													"8d6c993e-c2cc-11de-8d13-0010c6dffd0f"));

							regEncounter.setDateCreated(curDate);
							regEncounter.setEncounterDatetime(dateVisit);
							regEncounter.setVisit(v);

							regEncounter.setVoided(false);

							Encounter enregNew = Context.getEncounterService()
									.saveEncounter(regEncounter);
							Order ordersave = new Order();

							String reg = "";
							Order oo = new Order();

							if (!legacyData.get(4).isEmpty()) {

								DrugOrder dod = new DrugOrder();

								dod.setOrderType(Context.getOrderService()
										.getOrderType(2));
								dod.setConcept(Context.getConceptService()
										.getConcept(
												Integer.parseInt(legacyData
														.get(4))));
								dod.setEncounter(enregNew);

								dod.setStartDate(dateVisit);
								dod.setDateCreated(curDate);
								dod.setPatient(patient);
								dod.setUnits("tab");
								if (legacyData.get(4).equals("163494")
										|| legacyData.get(4).equals("163495")
										|| legacyData.get(4).equals("163496")
										|| legacyData.get(4).equals("162959")
										|| legacyData.get(4).equals("163503")
										|| legacyData.get(4).equals("163505")
										|| legacyData.get(4).equals("163506")
										|| legacyData.get(4).equals(" 163507")
										|| legacyData.get(4).equals("163508")
										|| legacyData.get(4).equals("163510")) {

									dod.setFrequency("od");
								} else {
									dod.setFrequency("bd");
								}

								ordersave = Context.getOrderService()
										.saveOrder(dod);
								List<Order> ord = Context.getOrderService()
										.getOrdersByPatient(patient);
								for (Order ooo : ord) {
									if (!legacyData.get(36).isEmpty()) {
										Date discontinuedDate = new Date();
										discontinuedDate = (Date) formatter
												.parse(legacyData.get(36));

										String dtechk = visitDateInExcel
												.format(discontinuedDate);
										try {
											Date curDat = new Date();
											List<Visit> visitdrug = Context
													.getVisitService()
													.getVisitsByPatient(patient);

											for (Visit visdr : visitdrug) {
												if (visdr.getStopDatetime() != null) {
													if (oo.getDiscontinuedDate() == null) {
														discontinuedDate = mysqlDateTimeFormatter
																.parse(dtechk
																		+ " "
																		+ curDat.getHours()
																		+ ":"
																		+ curDat.getMinutes()
																		+ ":"
																		+ curDat.getSeconds());

														oo.setDiscontinuedDate(discontinuedDate);
													}
												}
											}

										} catch (ParseException e) {
											e.printStackTrace();
										}

									}
									if (!legacyData.get(35).isEmpty()) {
										List<Visit> visitdrug = Context
												.getVisitService()
												.getVisitsByPatient(patient);

										Concept discontinuedReason = Context
												.getConceptService()
												.getConcept(
														Integer.parseInt(legacyData
																.get(35)));

										for (Visit visdr : visitdrug) {
											if (visdr.getStopDatetime() != null) {
												if (oo.getDiscontinuedReason() == null) {
													if (ooo.getConcept() != oo
															.getConcept()) {
														oo.setDiscontinuedReason(discontinuedReason);
													}
												}
											}
										}

									}

									oo = ooo;

									ordersave = Context.getOrderService()
											.saveOrder(oo);
								}

								Concept regimenConcept = Context
										.getConceptService().getConcept(
												Integer.parseInt(legacyData
														.get(4)));
								reg = reg.concat(regimenConcept.getName()
										.toString());

								KenyaEmrService kes = (KenyaEmrService) Context
										.getService(KenyaEmrService.class);

								DrugOrderProcessed orderprocess = new DrugOrderProcessed();
								orderprocess.setDrugOrder(Context
										.getOrderService().getDrugOrder(
												ordersave.getOrderId()));
								orderprocess.setPatient(patient);
								orderprocess.setDrugRegimen(reg);
								orderprocess.setCreatedDate(dateVisit);

								orderprocess
										.setRoute(Context
												.getConceptService()
												.getConceptByUuid(
														"160240AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"));
								Integer tablet = 0;
								if (legacyData.get(4).equals("163494")
										|| legacyData.get(4).equals("163495")
										|| legacyData.get(4).equals("163496")
										|| legacyData.get(4).equals("162959")
										|| legacyData.get(4).equals("163503")
										|| legacyData.get(4).equals("163505")
										|| legacyData.get(4).equals("163506")
										|| legacyData.get(4).equals(" 163507")
										|| legacyData.get(4).equals("163508")
										|| legacyData.get(4).equals("163510")) {

									tablet = Integer
											.parseInt(legacyData.get(6));
									orderprocess.setNoOfTablet(tablet);

								}

								else {
									tablet = Integer
											.parseInt(legacyData.get(6)) * 2;
									orderprocess.setNoOfTablet(tablet);
								}
								orderprocess.setQuantityPostProcess(tablet);
								orderprocess.setProcessedStatus(true);
								try {
									Date curDat = new Date();

									dateVisit = mysqlDateTimeFormatter
											.parse(dateCheck + " "
													+ curDat.getHours() + ":"
													+ curDat.getMinutes() + ":"
													+ curDat.getSeconds());

									orderprocess.setProcessedDate(dateVisit);

								} catch (ParseException e) {
									e.printStackTrace();
								}
								if (!legacyData.get(6).isEmpty()) {
									orderprocess.setDurationPreProcess(Integer
											.parseInt(legacyData.get(6)));
								}

								if (!legacyData.get(5).isEmpty()) {
									orderprocess.setDose(legacyData.get(5));
									orderprocess.setDoseRegimen(legacyData
											.get(5));
								}
								for (String firstline : firstlineConcept) {
									if (legacyData.get(4).equals(firstline)) {
										orderprocess
												.setTypeOfRegimen("First line Anti-retoviral drugs");
										;
									}
								}
								for (String secndline : secondlineConcept) {
									if (legacyData.get(4).equals(secndline)) {
										orderprocess
												.setTypeOfRegimen("Second line Anti-retoviral drugs");
										;
									}
								}
								for (String thirdline : thirdlineConcept) {
									if (legacyData.get(4).equals(thirdline)) {
										orderprocess
												.setTypeOfRegimen("Third line Anti-retoviral drugs");
										;
									}
								}
								List<DrugOrderProcessed> dopp = kes
										.getDrugOrderProcessedByPatient(patient);
								DrugOrderProcessed drugproce = new DrugOrderProcessed();
								drugproce = kes
										.saveDrugOrderProcessed(orderprocess);
								DrugOrderProcessed drugprocess = new DrugOrderProcessed();
								if (dopp.size() == 0) {
									drugprocess = drugproce;
									drugprocess.setRegimenChangeType("Start");
								}
								for (DrugOrderProcessed dd : dopp) {
									drugprocess = drugproce;

									if (dd.getDrugRegimen().equals(
											drugprocess.getDrugRegimen()))

									{
										if (dd.getDoseRegimen().equals(
												drugprocess.getDoseRegimen())
												&& dd.getTypeOfRegimen()
														.equals(drugprocess
																.getTypeOfRegimen())) {
											drugprocess
													.setRegimenChangeType("Continue");

										}

									} else {
										if (dd.getTypeOfRegimen().equals(
												drugprocess.getTypeOfRegimen())) {
											drugprocess
													.setRegimenChangeType("Substitue");
										} else {
											drugprocess
													.setRegimenChangeType("Switch");
										}

									}

									drugprocess = dd;
								}
								kes.saveDrugOrderProcessed(drugprocess);

								DrugOrderProcessed drugoo = new DrugOrderProcessed();
								List<DrugOrderProcessed> druordpro = kes
										.getDrugOrderProcessedByPatient(patient);
								for (DrugOrderProcessed ooo : druordpro) {
									if (!legacyData.get(36).isEmpty()) {
										Date discontinuedDate = new Date();
										discontinuedDate = (Date) formatter
												.parse(legacyData.get(36));
										String dtechk = visitDateInExcel
												.format(discontinuedDate);
										try {
											Date curDat = new Date();
											List<Visit> visitdrug = Context
													.getVisitService()
													.getVisitsByPatient(patient);

											for (Visit visdr : visitdrug) {
												if (visdr.getStopDatetime() != null) {
													if (drugoo
															.getDiscontinuedDate() == null) {
														discontinuedDate = mysqlDateTimeFormatter
																.parse(dtechk
																		+ " "
																		+ curDat.getHours()
																		+ ":"
																		+ curDat.getMinutes()
																		+ ":"
																		+ curDat.getSeconds());

														drugoo.setDiscontinuedDate(discontinuedDate);
													}
												}
											}

										} catch (ParseException e) {
											e.printStackTrace();
										}

									}
									if (!legacyData.get(35).isEmpty()) {
										List<Visit> visitdrug = Context
												.getVisitService()
												.getVisitsByPatient(patient);
										Concept discontinuedReason = Context
												.getConceptService()
												.getConcept(
														Integer.parseInt(legacyData
																.get(35)));

										for (Visit visdr : visitdrug) {
											if (visdr.getStopDatetime() != null) {
												if (oo.getDiscontinuedReason() == null) {
													if (!ooo.getDrugRegimen()
															.equals(drugoo
																	.getDrugRegimen())) {
														drugoo.setDiscontinuedReason(discontinuedReason);
													}
												}
											}
										}
									}

									drugoo = ooo;

									kes.saveDrugOrderProcessed(drugoo);
								}
							}
							EncounterType labEnrollEncType = MetadataUtils
									.existing(
											EncounterType.class,
											CommonMetadata._EncounterType.LAB_ORDERS);
							Encounter labEncounter = new Encounter();

							labEncounter.setEncounterType(labEnrollEncType);
							labEncounter.setPatient(patient);
							labEncounter
									.setLocation(Context.getService(
											KenyaEmrService.class)
											.getDefaultLocation());

							labEncounter.setDateCreated(curDate);
							labEncounter.setEncounterDatetime(dateVisit);

							labEncounter.setForm(MetadataUtils
									.existing(Form.class,
											CommonMetadata._Form.LAB_ORDERS));
							labEncounter.setVisit(v);

							labEncounter.setVoided(false);

							Encounter enlabNew = Context.getEncounterService()
									.saveEncounter(labEncounter);

							if (!legacyData.get(7).isEmpty()) {
								Concept labOrder = Dictionary
										.getConcept(Dictionary.CD4_COUNT);

								handleOncePerPatientObs(
										patient,
										Dictionary
												.getConcept(Dictionary.lABORATORY_ORDER),
										labOrder, "", null, null, enlabNew,
										null);

							}
							if (!legacyData.get(39).isEmpty()) {
								Concept labOrder = Context
										.getConceptService()
										.getConceptByUuid(
												"122858AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

								handleOncePerPatientObs(
										patient,
										Dictionary
												.getConcept(Dictionary.lABORATORY_ORDER),
										labOrder, "", null, null, enlabNew,
										null);

							}
							if (!legacyData.get(38).isEmpty()) {
								Concept labOrder = Context
										.getConceptService()
										.getConceptByUuid(
												"654AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

								handleOncePerPatientObs(
										patient,
										Dictionary
												.getConcept(Dictionary.lABORATORY_ORDER),
										labOrder, "", null, null, enlabNew,
										null);

							}
							if (!legacyData.get(8).isEmpty()) {

								Concept labviralOrder = Dictionary
										.getConcept(Dictionary.HIV_VIRAL_LOAD);

								handleOncePerPatientObs(
										patient,
										Dictionary
												.getConcept(Dictionary.lABORATORY_ORDER),
										labviralOrder, "", null, null,
										enlabNew, null);

							}

							if (!legacyData.get(37).isEmpty()) {

								Concept labhaemoOrder = Context
										.getConceptService()
										.getConceptByUuid(
												"1019AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

								handleOncePerPatientObs(
										patient,
										Dictionary
												.getConcept(Dictionary.lABORATORY_ORDER),
										labhaemoOrder, "", null, null,
										enlabNew, null);

							}
							if (!legacyData.get(40).isEmpty()) {

								Concept labcreatinineOrder = Context
										.getConceptService()
										.getConceptByUuid(
												"790AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
								handleOncePerPatientObs(
										patient,
										Dictionary
												.getConcept(Dictionary.lABORATORY_ORDER),
										labcreatinineOrder, "", null, null,
										enlabNew, null);

							}
							EncounterType labresultEnrollEncType = MetadataUtils
									.existing(
											EncounterType.class,
											CommonMetadata._EncounterType.LAB_RESULTS);
							Encounter labresultEncounter = new Encounter();

							labresultEncounter
									.setEncounterType(labresultEnrollEncType);
							labresultEncounter.setPatient(patient);

							labresultEncounter.setDateCreated(curDate);
							labresultEncounter.setEncounterDatetime(dateVisit);
							labresultEncounter.setVisit(v);

							labresultEncounter.setVoided(false);
							Encounter enlabresultNew = Context
									.getEncounterService().saveEncounter(
											labresultEncounter);

							if (!legacyData.get(7).isEmpty()) {
								String labResult = "";
								Integer labreslt = 0;
								Double lab = 0.0;

								labResult = legacyData.get(7);
								labreslt = Integer.parseInt(legacyData.get(7));
								lab = labreslt.doubleValue();
								handleOncePerPatientObs(
										patient,
										Dictionary
												.getConcept(Dictionary.CD4_COUNT),
										null, labResult, null, lab,
										enlabresultNew, null);

							}
							if (!legacyData.get(37).isEmpty()) {

								String labResult = legacyData.get(37);

								handleOncePerPatientObs(
										patient,
										Context.getConceptService()
												.getConceptByUuid(
														"1019AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"),
										null, labResult, null, null,
										enlabresultNew, null);

							}
							if (!legacyData.get(38).isEmpty()) {

								String labResult = legacyData.get(38);
								Integer labreslt = 0;
								Double lab = 0.0;
								labreslt = Integer.parseInt(legacyData.get(38));
								lab = labreslt.doubleValue();
								handleOncePerPatientObs(
										patient,
										Context.getConceptService()
												.getConceptByUuid(
														"122858AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"),
										null, labResult, null, lab,
										enlabresultNew, null);

							}
							if (!legacyData.get(39).isEmpty()) {

								String labResult = legacyData.get(39);
								Integer labreslt = 0;
								Double lab = 0.0;
								labreslt = Integer.parseInt(legacyData.get(39));
								lab = labreslt.doubleValue();
								handleOncePerPatientObs(
										patient,
										Context.getConceptService()
												.getConceptByUuid(
														"654AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"),
										null, labResult, null, lab,
										enlabresultNew, null);

							}
							if (!legacyData.get(8).isEmpty()) {

								Double lab = 0.0;
								Integer labreslt = 0;
								String labResult = legacyData.get(8);
								labreslt = Integer.parseInt(legacyData.get(8));
								lab = labreslt.doubleValue();
								handleOncePerPatientObs(
										patient,
										Dictionary
												.getConcept(Dictionary.HIV_VIRAL_LOAD),
										null, labResult, null, lab,
										enlabresultNew, null);

							}
							if (!legacyData.get(40).isEmpty()) {

								Double lab = 0.0;
								String labResult = legacyData.get(40);
								lab = Double.parseDouble(legacyData.get(40));

								handleOncePerPatientObs(
										patient,
										Context.getConceptService()
												.getConceptByUuid(
														"790AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"),
										null, labResult, null, lab,
										enlabresultNew, null);

							}
							EncounterType tbOIEnrollEncType = MetadataUtils
									.existing(
											EncounterType.class,
											CommonMetadata._EncounterType.CONSULTATION);
							Encounter tbOIEncounter = new Encounter();

							tbOIEncounter.setEncounterType(tbOIEnrollEncType);
							tbOIEncounter.setPatient(patient);

							tbOIEncounter.setDateCreated(curDate);
							tbOIEncounter.setEncounterDatetime(dateVisit);
							tbOIEncounter
									.setLocation(Context.getService(
											KenyaEmrService.class)
											.getDefaultLocation());

							tbOIEncounter.setForm(MetadataUtils.existing(
									Form.class,
									CommonMetadata._Form.TB_SCREENING));
							tbOIEncounter.setVisit(v);
							tbOIEncounter.setVoided(false);
							Encounter entbOIresultNew = Context
									.getEncounterService().saveEncounter(
											tbOIEncounter);
							Obs o = null;
							if (!legacyData.get(27).isEmpty()) {
								String text = "";
								Obs OIGroup = new Obs();
								OIGroup.setPerson(patient);
								OIGroup.setConcept(Dictionary
										.getConcept(Dictionary.OI_GROUP_TB_FORM));

								OIGroup.setObsDatetime(new Date());
								// Added value coded as per default obs object
								// format.
								OIGroup.setValueCoded(null);
								OIGroup.setValueText(text);
								OIGroup.setLocation(Context.getService(
										KenyaEmrService.class)
										.getDefaultLocation());

								OIGroup.setEncounter(entbOIresultNew);

								if (!legacyData.get(27).isEmpty()) {
									o = Context.getObsService()
											.saveObs(OIGroup,
													"KenyaEMR History Details");
								}

								if (!legacyData.get(27).isEmpty()) {
									String oivalue = legacyData.get(27);
									String[] valueList = oivalue
											.split("\\s*,\\s*");

									for (String oiname : valueList) {

										Concept oiConcept = Context
												.getConceptService()
												.getConcept(oiname);

										handleOncePerPatientObs(
												patient,
												Dictionary
														.getConcept(Dictionary.HIV_CARE_DIAGNOSIS),
												oiConcept, "", null, null,
												entbOIresultNew, o);

									}

								}

							}

							if (!legacyData.get(10).isEmpty()) {

								Concept tbStatus = Context.getConceptService()
										.getConcept(
												Integer.parseInt(legacyData
														.get(10)));

								handleOncePerPatientObs(
										patient,
										Dictionary
												.getConcept(Dictionary.TB_PATIENT),
										tbStatus, "", null, null,
										entbOIresultNew, null);

							}
							if (!legacyData.get(11).isEmpty()) {

								Concept tbDiseaseClassification = Context
										.getConceptService().getConcept(
												Integer.parseInt(legacyData
														.get(11)));
								handleOncePerPatientObs(
										patient,
										Dictionary
												.getConcept(Dictionary.SITE_OF_TUBERCULOSIS_DISEASE),
										tbDiseaseClassification, "", null,
										null, entbOIresultNew, null);
								if (!legacyData.get(12).isEmpty()) {
									if (!tbDiseaseClassification.equals("42")) {
										Concept tbsiteClassification = Context
												.getConceptService()
												.getConcept(
														Integer.parseInt(legacyData
																.get(12)));
										handleOncePerPatientObs(
												patient,
												Dictionary
														.getConcept(Dictionary.TB_SITE),
												tbsiteClassification, "", null,
												null, entbOIresultNew, null);
									}
								}
							}

							if (!legacyData.get(13).isEmpty()) {

								SimpleDateFormat sdf = new SimpleDateFormat(
										"E MMM dd HH:mm:ss Z yyyy");
								Date tbStartDate = new Date();
								try {
									tbStartDate = (Date) formatter
											.parse(legacyData.get(13));

								} catch (ParseException e) {
									e.printStackTrace();
								}

								handleOncePerPatientObs(
										patient,
										Dictionary
												.getConcept(Dictionary.TUBERCULOSIS_DRUG_TREATMENT_START_DATE),
										null, null, tbStartDate, null,
										entbOIresultNew, null);
							}
							if (!legacyData.get(14).isEmpty()) {

								Concept tbTownship = Context
										.getConceptService().getConcept(
												Integer.parseInt(legacyData
														.get(14)));
								handleOncePerPatientObs(
										patient,
										Dictionary
												.getConcept(Dictionary.TOWNSHIP),
										tbTownship, "", null, null,
										entbOIresultNew, null);
							}
							if (!legacyData.get(15).isEmpty()) {

								String tbclinicName = "";
								tbclinicName = legacyData.get(15);

								handleOncePerPatientObs(
										patient,
										Dictionary
												.getConcept(Dictionary.TB_CLINIC_NAME),
										null, tbclinicName, null, null,
										entbOIresultNew, null);
							}
							if (!legacyData.get(16).isEmpty()) {

								String tbregistrationNumber = "";
								tbregistrationNumber = legacyData.get(16);

								handleOncePerPatientObs(
										patient,
										Dictionary
												.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_NUMBER),
										null, tbregistrationNumber, null, null,
										entbOIresultNew, null);
							}
							if (!legacyData.get(17).isEmpty()) {
								Concept tbRegimen = Context.getConceptService()
										.getConcept(
												Integer.parseInt(legacyData
														.get(17)));
								handleOncePerPatientObs(
										patient,
										Dictionary
												.getConcept(Dictionary.TB_FORM_REGIMEN),
										tbRegimen, "", null, null,
										entbOIresultNew, null);

							}
							if (!legacyData.get(18).isEmpty()) {

								Concept tbOutcome = Context.getConceptService()
										.getConcept(
												Integer.parseInt(legacyData
														.get(18)));

								handleOncePerPatientObs(
										patient,
										Dictionary
												.getConcept(Dictionary.TUBERCULOSIS_TREATMENT_OUTCOME),
										tbOutcome, null, null, null,
										entbOIresultNew, null);
							}

							if (!legacyData.get(19).isEmpty()) {

								Date tbOutcomeDate = null;
								Date curDatenew = new Date();
								try {
									tbOutcomeDate = (Date) formatter
											.parse(legacyData.get(19));
									dateCheck = visitDateInExcel
											.format(tbOutcomeDate);
									tbOutcomeDate = mysqlDateTimeFormatter
											.parse(dateCheck + " "
													+ curDatenew.getHours()
													+ ":"
													+ curDatenew.getMinutes()
													+ ":"
													+ curDatenew.getSeconds());
								} catch (ParseException e) {
									e.printStackTrace();
								}

								handleOncePerPatientObs(
										patient,
										Dictionary
												.getConcept(Dictionary.TB_OUTCOME_DATE),
										null, null, tbOutcomeDate, null,
										entbOIresultNew, null);
							}
							int flag = 0;
							EncounterType HivdiscontEnrollEncType = MetadataUtils
									.existing(
											EncounterType.class,
											HivMetadata._EncounterType.HIV_DISCONTINUATION);
							Encounter hivDiscontEncounter = new Encounter();

							hivDiscontEncounter
									.setEncounterType(HivdiscontEnrollEncType);
							hivDiscontEncounter.setPatient(patient);

							hivDiscontEncounter.setDateCreated(curDate);
							hivDiscontEncounter.setEncounterDatetime(dateVisit);
							hivDiscontEncounter
									.setLocation(Context.getService(
											KenyaEmrService.class)
											.getDefaultLocation());

							hivDiscontEncounter.setForm(MetadataUtils.existing(
									Form.class,
									HivMetadata._Form.HIV_DISCONTINUATION));
							hivDiscontEncounter.setVisit(v);
							hivDiscontEncounter.setVoided(false);
							Encounter enhivDiscontresultNew = Context
									.getEncounterService().saveEncounter(
											hivDiscontEncounter);
							if (!legacyData.get(20).isEmpty()) {

								Concept endOfFollowup = Context
										.getConceptService().getConcept(
												Integer.parseInt(legacyData
														.get(20)));
								if (legacyData.get(20).equals("160034")) {
									handleOncePerPatientObs(
											patient,
											Dictionary
													.getConcept(Dictionary.REASON_FOR_PROGRAM_DISCONTINUATION),
											endOfFollowup, null, null, null,
											enhivDiscontresultNew, null);
									handleOncePerPatientObs(
											patient,
											Dictionary
													.getConcept(Dictionary.DEATH_DATE),
											null, null, dateVisit, null,
											enhivDiscontresultNew, null);
									flag = 1;

								} else if (legacyData.get(20).equals("159492")) {
									handleOncePerPatientObs(
											patient,
											Dictionary
													.getConcept(Dictionary.REASON_FOR_PROGRAM_DISCONTINUATION),
											endOfFollowup, null, null, null,
											enhivDiscontresultNew, null);
									if (!legacyData.get(22).isEmpty()) {
										String transferdto = legacyData.get(22);

										handleOncePerPatientObs(
												patient,
												Dictionary
														.getConcept(Dictionary.TRANSFERRED_OUT_TO),
												null, transferdto, null, null,
												enhivDiscontresultNew, null);

									}
									handleOncePerPatientObs(
											patient,
											Dictionary
													.getConcept(Dictionary.DATE_TRANSFERRED_OUT),
											null, null, dateVisit, null,
											enhivDiscontresultNew, null);

								} else {
									handleOncePerPatientObs(
											patient,
											Dictionary
													.getConcept(Dictionary.REASON_FOR_PROGRAM_DISCONTINUATION),
											endOfFollowup, null, null, null,
											enhivDiscontresultNew, null);

								}

								if (!legacyData.get(21).isEmpty()) {
									Date programcmpleteDate = null;
									Date curDatenew = new Date();
									try {
										programcmpleteDate = (Date) formatter
												.parse(legacyData.get(21));
										dateCheck = visitDateInExcel
												.format(programcmpleteDate);
										programcmpleteDate = mysqlDateTimeFormatter
												.parse(dateCheck
														+ " "
														+ curDatenew.getHours()
														+ ":"
														+ curDatenew
																.getMinutes()
														+ ":"
														+ curDatenew
																.getSeconds());
										Collection<PatientProgram> hivprogram = Context
												.getProgramWorkflowService()
												.getPatientPrograms(patient);
										for (PatientProgram prog : hivprogram) {
											if (prog.getPatient().equals(
													patient)) {

												prog.setDateCompleted(programcmpleteDate);
												Context.getProgramWorkflowService()
														.savePatientProgram(
																prog);

											}
										}
									} catch (ParseException e) {
										e.printStackTrace();
									}

								}

							}

							EncounterType ArtdiscontEnrollEncType = MetadataUtils
									.existing(EncounterType.class,
											ArtMetadata._EncounterType.STOP_ART);
							Encounter artDiscontEncounter = new Encounter();

							artDiscontEncounter
									.setEncounterType(ArtdiscontEnrollEncType);
							artDiscontEncounter.setPatient(patient);

							artDiscontEncounter.setDateCreated(curDate);
							artDiscontEncounter.setEncounterDatetime(dateVisit);
							artDiscontEncounter
									.setLocation(Context.getService(
											KenyaEmrService.class)
											.getDefaultLocation());

							artDiscontEncounter.setForm(MetadataUtils.existing(
									Form.class, ArtMetadata._Form.STOP_ART));
							artDiscontEncounter.setVisit(v);
							artDiscontEncounter.setVoided(false);
							Encounter enartDiscontresultNew = Context
									.getEncounterService().saveEncounter(
											artDiscontEncounter);
							if (!legacyData.get(23).isEmpty()) {
								Date programcmpleteDate = null;
								Date curDatenew = new Date();
								try {
									programcmpleteDate = (Date) formatter
											.parse(legacyData.get(23));
									dateCheck = visitDateInExcel
											.format(programcmpleteDate);
									programcmpleteDate = mysqlDateTimeFormatter
											.parse(dateCheck + " "
													+ curDatenew.getHours()
													+ ":"
													+ curDatenew.getMinutes()
													+ ":"
													+ curDatenew.getSeconds());
									pp.setDateCompleted(programcmpleteDate);
								} catch (ParseException e) {
									e.printStackTrace();
								}

								Context.getProgramWorkflowService()
										.savePatientProgram(pp);
							}
							if (!legacyData.get(24).isEmpty()) {
								Concept endOfArt = Context.getConceptService()
										.getConcept(
												Integer.parseInt(legacyData
														.get(24)));

								handleOncePerPatientObs(
										patient,
										Context.getConceptService()
												.getConceptByUuid(
														"1252AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"),
										endOfArt, null, null, null,
										enartDiscontresultNew, null);

							}
							EncounterType consultEnrollEncType = MetadataUtils
									.existing(
											EncounterType.class,
											CommonMetadata._EncounterType.CONSULTATION);
							Encounter consultEncounter = new Encounter();

							consultEncounter
									.setEncounterType(consultEnrollEncType);
							consultEncounter.setPatient(patient);

							consultEncounter.setDateCreated(curDate);
							consultEncounter.setEncounterDatetime(dateVisit);
							consultEncounter
									.setLocation(Context.getService(
											KenyaEmrService.class)
											.getDefaultLocation());

							consultEncounter
									.setForm(MetadataUtils
											.existing(
													Form.class,
													CommonMetadata._Form.CONSULTATION_ENCOUNTER));
							consultEncounter.setVisit(v);
							consultEncounter.setVoided(false);
							Encounter enconsultresultNew = Context
									.getEncounterService().saveEncounter(
											consultEncounter);
							if (!legacyData.get(30).isEmpty()) {

								Concept sideffectsOfArt = Context
										.getConceptService().getConcept(
												Integer.parseInt(legacyData
														.get(30)));

								handleOncePerPatientObs(
										patient,
										Context.getConceptService()
												.getConceptByUuid(
														"159935AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"),
										Dictionary.getConcept(Dictionary.YES),
										null, null, null, enconsultresultNew,
										null);
								handleOncePerPatientObs(
										patient,
										Dictionary
												.getConcept(Dictionary.ART_SIDE_EFFECTS_VALUES),
										sideffectsOfArt, null, null, null,
										enconsultresultNew, null);

							}
							if (!legacyData.get(31).isEmpty()) {

								String levelOfAdherence = legacyData.get(31);

								handleOncePerPatientObs(
										patient,
										Dictionary
												.getConcept(Dictionary.ART_ADHERENCE),
										null, levelOfAdherence, null, null,
										enconsultresultNew, null);

							}
							if (!legacyData.get(41).isEmpty()) {

								Concept temporaryreferal = Context
										.getConceptService().getConcept(
												Integer.parseInt(legacyData
														.get(41)));

								handleOncePerPatientObs(
										patient,
										Context.getConceptService()
												.getConceptByUuid(
														"5e05d243-e039-4f04-9988-18d5a499329e"),
										Dictionary.getConcept(Dictionary.YES),
										null, null, null, enconsultresultNew,
										null);
								handleOncePerPatientObs(
										patient,
										Context.getConceptService()
												.getConceptByUuid(
														"c648f69b-7065-4255-9af2-6076348c87dc"),
										temporaryreferal, null, null, null,
										enconsultresultNew, null);

							}
							if (!legacyData.get(28).isEmpty()) {

								Concept tbOutcome = new Concept();
								String performance = legacyData.get(28);
								if (performance.equals("A")) {
									tbOutcome = Dictionary
											.getConcept(Dictionary.PERFSCALE_A);
								} else if (performance.equals("B")) {
									tbOutcome = Dictionary
											.getConcept(Dictionary.PERFSCALE_B);
								} else {
									tbOutcome = Dictionary
											.getConcept(Dictionary.PERFSCALE_C);
								}
								handleOncePerPatientObs(
										patient,
										Dictionary
												.getConcept(Dictionary.PERFORMANCE),
										tbOutcome, null, null, null,
										entbOIresultNew, null);
							}

							if (!legacyData.get(29).isEmpty()) {

								Concept tbOutcome = new Concept();
								String stage = legacyData.get(29);
								if (stage.equals("IV")) {
									tbOutcome = Dictionary
											.getConcept(Dictionary.WHO_STAGE_4_ADULT);
								} else if (stage.equals("III")) {
									tbOutcome = Dictionary
											.getConcept(Dictionary.WHO_STAGE_3_ADULT);
								} else if (stage.equals("II")) {
									tbOutcome = Dictionary
											.getConcept(Dictionary.WHO_STAGE_2_ADULT);
								} else {
									tbOutcome = Dictionary
											.getConcept(Dictionary.WHO_STAGE_1_ADULT);
								}
								handleOncePerPatientObs(
										patient,
										Dictionary
												.getConcept(Dictionary.CURRENT_WHO_STAGE),
										tbOutcome, null, null, null,
										entbOIresultNew, null);

							}
							EncounterType nextAppointEncType = MetadataUtils
									.existing(
											EncounterType.class,
											CommonMetadata._EncounterType.CONSULTATION);
							Encounter nextAppointEncounter = new Encounter();

							nextAppointEncounter
									.setEncounterType(nextAppointEncType);
							nextAppointEncounter.setPatient(patient);

							nextAppointEncounter.setDateCreated(curDate);
							nextAppointEncounter
									.setEncounterDatetime(dateVisit);
							nextAppointEncounter.setLocation(Context
									.getService(KenyaEmrService.class)
									.getDefaultLocation());
							nextAppointEncounter.setVisit(v);
							nextAppointEncounter.setVoided(false);
							Encounter ennextAppointresultNew = new Encounter();
							if (!legacyData.get(32).isEmpty()) {
								ennextAppointresultNew = Context
										.getEncounterService().saveEncounter(
												nextAppointEncounter);
							}
							if (!legacyData.get(32).isEmpty()) {
								SimpleDateFormat sdf = new SimpleDateFormat(
										"E MMM dd HH:mm:ss Z yyyy");
								Date nextAppointDate = new Date();
								try {
									nextAppointDate = (Date) formatter
											.parse(legacyData.get(32));

								} catch (ParseException e) {
									e.printStackTrace();
								}

								handleOncePerPatientObs(
										patient,
										Dictionary
												.getConcept(Dictionary.RETURN_VISIT_DATE),
										null, null, nextAppointDate, null,
										ennextAppointresultNew, null);
							}

							EncounterType otherMedicationEnrollEncType = MetadataUtils
									.existing(
											EncounterType.class,
											CommonMetadata._EncounterType.CONSULTATION);
							Encounter otherMedEncounter = new Encounter();

							otherMedEncounter
									.setEncounterType(otherMedicationEnrollEncType);
							otherMedEncounter.setPatient(patient);

							otherMedEncounter.setDateCreated(curDate);
							otherMedEncounter.setEncounterDatetime(dateVisit);
							otherMedEncounter
									.setLocation(Context.getService(
											KenyaEmrService.class)
											.getDefaultLocation());

							otherMedEncounter.setForm(MetadataUtils.existing(
									Form.class,
									CommonMetadata._Form.OTHER_MEDICATIONS));
							otherMedEncounter.setVisit(v);
							otherMedEncounter.setVoided(false);
							Encounter enotherresultNew = Context
									.getEncounterService().saveEncounter(
											otherMedEncounter);
							if (!legacyData.get(33).isEmpty()) {
								String text = "";
								Obs prophylGroup = new Obs();
								prophylGroup.setPerson(patient);
								prophylGroup
										.setConcept(Context
												.getConceptService()
												.getConceptByUuid(
														"163022AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"));
								prophylGroup.setObsDatetime(new Date());
								prophylGroup.setValueCoded(null);
								prophylGroup.setValueText(text);
								prophylGroup.setLocation(Context.getService(
										KenyaEmrService.class)
										.getDefaultLocation());

								prophylGroup.setEncounter(enotherresultNew);
								Obs prophyl = Context.getObsService().saveObs(
										prophylGroup,
										"KenyaEMR History Details");

								if (!legacyData.get(33).isEmpty()) {

									Concept oivalue = Context
											.getConceptService().getConcept(
													Integer.parseInt(legacyData
															.get(33)));
									handleOncePerPatientObs(
											patient,
											Dictionary
													.getConcept(Dictionary.PROPHYLAXIS),
											oivalue, "", null, null,
											enotherresultNew, prophyl);

								}

							}
							if (!legacyData.get(34).isEmpty()) {
								String text = "";
								Obs oitreatmentGroup = new Obs();
								oitreatmentGroup.setPerson(patient);
								oitreatmentGroup
										.setConcept(Context
												.getConceptService()
												.getConceptByUuid(
														"163021AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"));

								oitreatmentGroup.setObsDatetime(new Date());

								oitreatmentGroup.setValueCoded(null);
								oitreatmentGroup.setValueText(text);
								oitreatmentGroup.setLocation(Context
										.getService(KenyaEmrService.class)
										.getDefaultLocation());

								oitreatmentGroup.setEncounter(enotherresultNew);

								Obs oitreat = Context.getObsService().saveObs(
										oitreatmentGroup,
										"KenyaEMR History Details");

								if (!legacyData.get(34).isEmpty()) {

									Concept oitreatvalue = Context
											.getConceptService().getConcept(
													Integer.parseInt(legacyData
															.get(34)));
									handleOncePerPatientObs(
											patient,
											Dictionary
													.getConcept(Dictionary.OI_TREATMENT_DRUG),
											oitreatvalue, "", null, null,
											enotherresultNew, oitreat);

								}
							}
							EncounterType recordEncType = MetadataUtils
									.existing(
											EncounterType.class,
											CommonMetadata._EncounterType.CONSULTATION);
							Encounter recordEncounter = new Encounter();

							recordEncounter.setEncounterType(recordEncType);
							recordEncounter.setPatient(patient);

							recordEncounter.setDateCreated(curDate);
							recordEncounter.setEncounterDatetime(dateVisit);
							recordEncounter
									.setLocation(Context.getService(
											KenyaEmrService.class)
											.getDefaultLocation());

							recordEncounter.setForm(MetadataUtils.existing(
									Form.class, CommonMetadata._Form.TRIAGE));
							recordEncounter.setVisit(v);
							recordEncounter.setVoided(false);
							Encounter enrecordvitalresultNew = Context
									.getEncounterService().saveEncounter(
											recordEncounter);
							if (!legacyData.get(25).isEmpty()) {

								Double lab = 0.0;
								Integer labreslt = 0;

								labreslt = Integer.parseInt(legacyData.get(25));
								lab = labreslt.doubleValue();

								handleOncePerPatientObs(
										patient,
										Dictionary
												.getConcept(Dictionary.WEIGHT_KG),
										null, null, null, lab,
										enrecordvitalresultNew, null);

							}
							if (!legacyData.get(26).isEmpty()) {

								Double lab = 0.0;
								Integer labreslt = 0;

								labreslt = Integer.parseInt(legacyData.get(26));
								lab = labreslt.doubleValue();

								handleOncePerPatientObs(
										patient,
										Dictionary
												.getConcept(Dictionary.HEIGHT_CM),
										null, null, null, lab,
										enrecordvitalresultNew, null);

							}

							EncounterType hivEnrollEncType = MetadataUtils
									.existing(
											EncounterType.class,
											HivMetadata._EncounterType.HIV_ENROLLMENT);
							EncounterType registrationEncType = MetadataUtils
									.existing(
											EncounterType.class,
											CommonMetadata._EncounterType.REGISTRATION);
							Encounter obstericEncounter = new Encounter();

							obstericEncounter
									.setEncounterType(registrationEncType);
							obstericEncounter.setPatient(patient);

							obstericEncounter.setDateCreated(curDate);
							obstericEncounter.setEncounterDatetime(dateVisit);
							obstericEncounter
									.setLocation(Context.getService(
											KenyaEmrService.class)
											.getDefaultLocation());

							obstericEncounter.setForm(MetadataUtils
									.existing(Form.class,
											Metadata.Form.OBSTETRIC_HISTORY));
							obstericEncounter.setVisit(v);
							obstericEncounter.setVoided(false);
							Encounter enobstericrecordresultNew = Context
									.getEncounterService().saveEncounter(
											obstericEncounter);
							if (!legacyData.get(42).isEmpty()) {

								Concept pregstatus = Context
										.getConceptService().getConcept(
												Integer.parseInt(legacyData
														.get(42)));
								handleOncePerPatientObs(
										patient,
										Dictionary
												.getConcept(Dictionary.PREGNANCY_STATUS),
										pregstatus, "", null, null,
										enobstericrecordresultNew, null);

							}
							if (!legacyData.get(43).isEmpty()) {

								Concept familyplanningstatus = Dictionary
										.getConcept(Dictionary.YES);

								handleOncePerPatientObs(
										patient,
										Context.getConceptService()
												.getConceptByUuid(
														"5271AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"),
										familyplanningstatus, "", null, null,
										enobstericrecordresultNew, null);

							}
							if (!legacyData.get(43).isEmpty()) {

								Concept familyplanningvalue = Context
										.getConceptService().getConcept(
												Integer.parseInt(legacyData
														.get(43)));

								handleOncePerPatientObs(
										patient,
										Dictionary
												.getConcept(Dictionary.METHOD_OF_FAMILY_PLANNING),
										familyplanningvalue, "", null, null,
										enobstericrecordresultNew, null);

							}

							DateFormat visitDatesInExcel = new SimpleDateFormat(
									"dd-MMM-yyyy");
							String dateChecks = visitDatesInExcel
									.format(dateVisit);
							if (legacyData.get(3) != null) {
								Date Datenew = new Date();
								try {
									dateVisit = mysqlDateTimeFormatter
											.parse(dateChecks + " "
													+ Datenew.getHours() + ":"
													+ Datenew.getMinutes()
													+ ":"
													+ Datenew.getSeconds());

								} catch (ParseException e) {
									dateVisit = Datenew;

									e.printStackTrace();
								}
							}

							v.setStopDatetime(dateVisit);

							Context.getVisitService().saveVisit(v);

							if (flag == 1) {
								person.setDead(true);
								person.setDeathDate(dateVisit);
								person.setCauseOfDeath(Dictionary
										.getConcept(Dictionary.UNKNOWN));
								Context.getPersonService().savePerson(person);
							}
						}

					}

					catch (IndexOutOfBoundsException e) {
						e.printStackTrace();
					}

				}

				rowCountVisit++;
			}
		}

		inputStream.close();
	//	workbook.close();
		return new SuccessResult("Saved Patient Data");
	}

	private static int generateCheckdigit(String input) {
		int factor = 2;
		int sum = 0;
		int n = 10;
		int length = input.length();

		if (!input.matches("[\\w]+"))
			throw new RuntimeException("Invalid character in patient id: "
					+ input);
		// Work from right to left
		for (int i = length - 1; i >= 0; i--) {
			int codePoint = input.charAt(i) - 48;
			// slight openmrs peculiarity to Luhn's algorithm
			int accum = factor * codePoint - (factor - 1)
					* (int) (codePoint / 5) * 9;

			// Alternate the "factor"
			factor = (factor == 2) ? 1 : 2;

			sum += accum;
		}

		int remainder = sum % n;
		return (n - remainder) % n;
	}

	protected void handleOncePerPatientObs(Patient patient, Concept question,
			Concept newValue, String textValue, Date textDate,
			Double numnericValue, Encounter en, Obs obsGroup) {
		Obs o = new Obs();
		o.setPerson(patient);
		o.setConcept(question);
		o.setObsDatetime(new Date());
		o.setLocation(Context.getService(KenyaEmrService.class)
				.getDefaultLocation());
		if (newValue != null && !newValue.equals("")) {
			o.setValueCoded(newValue);
		}
		if (textValue != null && !textValue.equals("")) {
			o.setValueText(textValue);
		}

		if (numnericValue != null) {
			o.setValueNumeric(numnericValue);
		}
		if (textDate != null && !textDate.equals("")) {
			o.setValueDate(textDate);
		}
		if (en != null) {
			o.setEncounter(en);
		}
		if (obsGroup != null) {
			o.setObsGroup(obsGroup);
		}
		Context.getObsService().saveObs(o, "KenyaEMR History Details");
	}

	protected void handleOncePerPatientObs(Patient patient, Concept question,
			Concept newValue, String textValue, Date textDate, Encounter en) {

		if (newValue != null) {
			Obs o = new Obs();
			o.setPerson(patient);
			o.setConcept(question);
			o.setObsDatetime(new Date());
			o.setLocation(Context.getService(KenyaEmrService.class)
					.getDefaultLocation());
			if (newValue != null && !newValue.equals("")) {
				o.setValueCoded(newValue);
			}
			if (textValue != null && !textValue.equals("")) {
				o.setValueText(textValue);
			}
			if (textDate != null && !textDate.equals("")) {
				o.setValueDate(textDate);
			}
			if (en != null) {
				o.setEncounter(en);
			}

			Context.getObsService().saveObs(o, "Patient Registration Details");
		}

	}
}