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
import org.json.simple.JSONObject;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Form;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.PatientIdentifierType;
import org.openmrs.PatientProgram;
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
import org.openmrs.module.kenyaemr.api.KenyaEmrService;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
import org.openmrs.module.kenyaemr.wrapper.PatientWrapper;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.ui.framework.resource.ResourceFactory;
import org.openmrs.util.OpenmrsConstants;
import org.openmrs.util.OpenmrsUtil;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import java.io.FileInputStream;
import java.util.Enumeration;

/**
 * Merge patients form fragment
 */
public class ImportPatientsListFragmentController {

	protected static final Log log = LogFactory
			.getLog(ImportPatientsListFragmentController.class);

	public void controller(
			@RequestParam(required = false, value = "returnUrl") String returnUrl,
			PageModel model) {
		model.addAttribute("returnUrl", returnUrl);
	}

	public SimpleObject submit(HttpServletRequest request) throws Exception {

		Enumeration params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String paramName = (String) params.nextElement();
			// System.out.println("Parameter Name - " + paramName + ", Value - "
			// + request.getParameter(paramName));
		}

		FileInputStream inputStream = new FileInputStream(new File(
				"C:/Users/Sagar/Downloads/Legacy_data.xlsm"));

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
					System.out.println(s + "-" + i);
					i++;
				}

				/*
				 * Start Patient Creation
				 */

				if (legacyData.get(0)!=null) {
					Patient toSave = new Patient(); // Creating a new patient
													// and
					// person
					PersonName personName = new PersonName();
					PersonAddress personAddress = new PersonAddress();
					Location location;

					SimpleDateFormat formatter = new SimpleDateFormat(
							"E MMM dd HH:mm:ss Z yyyy");
					Date dateBith = new Date();
					try {
						dateBith = (Date) formatter.parse(legacyData.get(2));

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

					wrapper.getPerson().setTelephoneContact(legacyData.get(8));

					location = Context.getService(KenyaEmrService.class)
							.getDefaultLocation();

					if (legacyData.get(4)!=null) {
						wrapper.setPreArtRegistrationNumber(legacyData.get(4),
								location);
					}

					if (legacyData.get(5)!=null) {
						wrapper.setArtRegistrationNumber(legacyData.get(5),
								location);
					}

					if (legacyData.get(6)!=null) {
						wrapper.setNapArtRegistrationNumber(legacyData.get(5),
								location);
					}

					// Algorithm to generate system generated patient Identifier
					Calendar now = Calendar.getInstance();
					String shortName = Context
							.getAdministrationService()
							.getGlobalProperty(
									OpenmrsConstants.GLOBAL_PROPERTY_PATIENT_IDENTIFIER_PREFIX);

					String noCheck = shortName
							+ String.valueOf(now.get(Calendar.YEAR)).substring(
									2, 4)
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

					if (legacyData.get(13)!=null) {
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
					System.out
							.println("###########################################################---"
									+ ret.getPatientId());
					// Explicitly save all identifier objects including voided
					for (PatientIdentifier identifier : toSave.getIdentifiers()) {
						Context.getPatientService().savePatientIdentifier(
								identifier);
					}

					/*
					 * End Patient Creation
					 */

					// With value text and Date
					if (legacyData.get(15)!=null) {

						Date dateTransfer = null;
						if (legacyData.get(17)!=null) {
							try {
								dateTransfer = formatter.parse(legacyData
										.get(17));

							} catch (ParseException e) {
								e.printStackTrace();
							}
						}

						Concept enrollementConcept = Context
								.getConceptService().getConcept(
										Integer.parseInt(legacyData.get(15)));
						handleOncePerPatientObs(
								ret,
								Dictionary
										.getConcept(Dictionary.METHOD_OF_ENROLLMENT),
								enrollementConcept, "", dateTransfer, null);
					}

					if (legacyData.get(7)!=null) {
						Concept ingoConcept = Context.getConceptService()
								.getConcept(Integer.parseInt(legacyData.get(7)));
						handleOncePerPatientObs(ret,
								Dictionary.getConcept(Dictionary.INGO_NAME),
								ingoConcept, "", null, null);
					}

					/**
					 ** Start : Visit, Encounter and Program creation for Patient
					 * entered as Legacy Data Visit start date
					 **/
					Date curDate = new Date();
					Date dateVisit = null;
					try {
						dateVisit = formatter.parse(legacyData.get(32));

					} catch (ParseException e) {
						e.printStackTrace();
					}

					DateFormat visitDateInExcel = new SimpleDateFormat(
							"dd-MMM-yyyy");
					String dateCheck = visitDateInExcel.format(dateVisit);
					SimpleDateFormat mysqlDateTimeFormatter = new SimpleDateFormat(
							"dd-MMM-yy HH:mm:ss");

					if (legacyData.get(32)!=null) {
						try {
							dateVisit = mysqlDateTimeFormatter.parse(dateCheck
									+ " " + curDate.getHours() + ":"
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
					visit.setVisitType(MetadataUtils.existing(VisitType.class,
							CommonMetadata._VisitType.OUTPATIENT));
					visit.setLocation(Context.getService(KenyaEmrService.class)
							.getDefaultLocation());

					VisitAttributeType attrType = Context.getService(
							VisitService.class).getVisitAttributeTypeByUuid(
							CommonMetadata._VisitAttributeType.NEW_PATIENT);
					if (attrType != null) {
						VisitAttribute attr = new VisitAttribute();
						attr.setAttributeType(attrType);
						attr.setVisit(visit);
						attr.setDateCreated(curDate);
						attr.setValue(true);
						visit.addAttribute(attr);
					}

					Visit visitSave = Context.getVisitService()
							.saveVisit(visit);

					EncounterType hivEnrollEncType = MetadataUtils.existing(
							EncounterType.class,
							HivMetadata._EncounterType.HIV_ENROLLMENT);

					EncounterType registrationEncType = MetadataUtils.existing(
							EncounterType.class,
							CommonMetadata._EncounterType.REGISTRATION);

					Encounter hivEnrollmentEncounter = new Encounter();

					hivEnrollmentEncounter.setEncounterType(hivEnrollEncType);
					hivEnrollmentEncounter.setPatient(ret);
					hivEnrollmentEncounter.setLocation(Context.getService(
							KenyaEmrService.class).getDefaultLocation());

					hivEnrollmentEncounter.setDateCreated(curDate);
					hivEnrollmentEncounter.setEncounterDatetime(visitSave
							.getStartDatetime());

					hivEnrollmentEncounter.setForm(MetadataUtils.existing(
							Form.class, HivMetadata._Form.HIV_ENROLLMENT));

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
					/*
					 * End : Visit, Encounter and Program creation for Patient
					 * entered as Legacy Data
					 */
					/*
					 * Personal History encounter
					 */

					Encounter personalHistoryEncounter = new Encounter();

					personalHistoryEncounter.setEncounterType(hivEnrollEncType);
					personalHistoryEncounter.setPatient(ret);
					personalHistoryEncounter.setLocation(Context.getService(
							KenyaEmrService.class).getDefaultLocation());

					personalHistoryEncounter.setDateCreated(curDate);
					personalHistoryEncounter.setEncounterDatetime(visitSave
							.getStartDatetime());

					personalHistoryEncounter
							.setForm(MetadataUtils.existing(Form.class,
									HivMetadata._Form.HIV_PERSONAL_HISTORY));

					personalHistoryEncounter.setVoided(false);
					Encounter enPersonalHistoryNew = Context
							.getEncounterService().saveEncounter(
									personalHistoryEncounter);

					// Risk Factor
					if (legacyData.get(20)!=null) {
						Concept riskFactor = Context.getConceptService()
								.getConcept(
										Integer.parseInt(legacyData.get(20)));
						handleOncePerPatientObs(
								ret,
								Dictionary
										.getConcept(Dictionary.HIV_RISK_FACTOR),
								riskFactor, "", null, enPersonalHistoryNew,
								null);
					}

					// Literacy
					if (legacyData.get(18)!=null) {
						Concept literate = Context.getConceptService()
								.getConcept(
										Integer.parseInt(legacyData.get(18)));
						handleOncePerPatientObs(ret,
								Dictionary.getConcept(Dictionary.LITERATE),
								literate, "", null, enPersonalHistoryNew, null);
					}

					// Employed
					if (legacyData.get(30)!=null) {
						Concept employed = Context.getConceptService()
								.getConcept(
										Integer.parseInt(legacyData.get(30)));
						handleOncePerPatientObs(ret,
								Dictionary.getConcept(Dictionary.EMPLOYED),
								employed, "", null, enPersonalHistoryNew, null);
					}

					// Alcoholic
					if (legacyData.get(31)!=null) {
						Concept alcoholic = Context.getConceptService()
								.getConcept(
										Integer.parseInt(legacyData.get(31)));
						handleOncePerPatientObs(ret,
								Dictionary
										.getConcept(Dictionary.ALCOHOLIC_TYPE),
								alcoholic, "", null, enPersonalHistoryNew, null);
					}

					// IDU Substitute
					if (legacyData.get(21)!=null) {
						Concept iduSubstitute = Context.getConceptService()
								.getConcept(
										Integer.parseInt(legacyData.get(21)));
						handleOncePerPatientObs(ret,

						Dictionary.getConcept(Dictionary.IDU_PERSONAL_HISTORY),
								iduSubstitute, "", null, enPersonalHistoryNew,
								null);
					}

					// IDU Substitute type
					if (legacyData.get(22)!=null) {
						Concept iduSubstituteType = Context.getConceptService()
								.getConcept(
										Integer.parseInt(legacyData.get(22)));
						handleOncePerPatientObs(
								ret,
								Dictionary
										.getConcept(Dictionary.IDU_NAME_PERSONAL_HISTORY),
								iduSubstituteType, "", null,
								enPersonalHistoryNew, null);
					}

					/*
					 * Family History encounter
					 */
					Encounter familyHistoryEncounter = new Encounter();

					familyHistoryEncounter
							.setEncounterType(registrationEncType);
					familyHistoryEncounter.setPatient(ret);
					familyHistoryEncounter.setLocation(Context.getService(
							KenyaEmrService.class).getDefaultLocation());

					familyHistoryEncounter.setDateCreated(curDate);
					familyHistoryEncounter.setEncounterDatetime(visitSave
							.getStartDatetime());

					familyHistoryEncounter.setForm(MetadataUtils.existing(
							Form.class, HivMetadata._Form.FAMILY_HISTORY));

					familyHistoryEncounter.setVoided(false);
					Encounter enFamilyHistoryNew = Context
							.getEncounterService().saveEncounter(
									familyHistoryEncounter);

					// Married
					if (legacyData.get(22)!=null) {
						Concept married = Context.getConceptService()
								.getConcept(
										Integer.parseInt(legacyData.get(19)));
						handleOncePerPatientObs(ret,
								Dictionary.getConcept(Dictionary.CIVIL_STATUS),
								married, "", null, enFamilyHistoryNew, null);
					}

					/*
					 * Obstertric History encounter
					 */
					Encounter obstetricHistoryEncounter = new Encounter();

					obstetricHistoryEncounter
							.setEncounterType(registrationEncType);
					obstetricHistoryEncounter.setPatient(ret);
					obstetricHistoryEncounter.setLocation(Context.getService(
							KenyaEmrService.class).getDefaultLocation());

					obstetricHistoryEncounter.setDateCreated(curDate);
					obstetricHistoryEncounter.setEncounterDatetime(visitSave
							.getStartDatetime());

					obstetricHistoryEncounter.setForm(MetadataUtils.existing(
							Form.class, HivMetadata._Form.FAMILY_HISTORY));

					obstetricHistoryEncounter.setVoided(false);
					Encounter enObstetricHistoryNew = Context
							.getEncounterService().saveEncounter(
									obstetricHistoryEncounter);

					// Pregnancy
					if (legacyData.get(23)!=null) {
						Concept pregnancy = Context.getConceptService()
								.getConcept(
										Integer.parseInt(legacyData.get(23)));
						handleOncePerPatientObs(
								ret,
								Dictionary
										.getConcept(Dictionary.PREGNANCY_STATUS),
								pregnancy, "", null, enObstetricHistoryNew,
								null);
					}

					// Family Planning
					if (legacyData.get(24)!=null) {
						Concept pregnancy = Context.getConceptService()
								.getConcept(
										Integer.parseInt(legacyData.get(24)));
						handleOncePerPatientObs(
								ret,
								Dictionary
										.getConcept(Dictionary.FAMILY_PLANNING),
								pregnancy, "", null, enObstetricHistoryNew,
								null);
					}

					/*
					 * Drug History encounter
					 */
					Encounter drugHistoryEncounter = new Encounter();

					drugHistoryEncounter.setEncounterType(hivEnrollEncType);
					drugHistoryEncounter.setPatient(ret);
					drugHistoryEncounter.setLocation(Context.getService(
							KenyaEmrService.class).getDefaultLocation());

					drugHistoryEncounter.setDateCreated(curDate);
					drugHistoryEncounter.setEncounterDatetime(visitSave
							.getStartDatetime());

					drugHistoryEncounter.setForm(MetadataUtils.existing(
							Form.class, HivMetadata._Form.FAMILY_HISTORY));

					drugHistoryEncounter.setVoided(false);
					Encounter enDrugHistoryNew = Context.getEncounterService()
							.saveEncounter(drugHistoryEncounter);

					// Previous ART
					if (legacyData.get(25)!=null) {
						Concept previousArt = Context.getConceptService()
								.getConcept(
										Integer.parseInt(legacyData.get(25)));
						handleOncePerPatientObs(
								ret,

								Dictionary
										.getConcept(Dictionary.DRUG_HISTORY_ART_RECEIVED),
								previousArt, "", null, enDrugHistoryNew, null);
					}

					// Previous ART Type
					if (legacyData.get(26)!=null) {
						Concept previousArtType = Context.getConceptService()
								.getConcept(
										Integer.parseInt(legacyData.get(25)));
						handleOncePerPatientObs(
								ret,
								Dictionary
										.getConcept(Dictionary.DRUG_HISTORY_ART_RECEIVED_TYPE),
								previousArtType, "", null, enDrugHistoryNew,
								null);
					}

					Obs drugGroup = null;
					// Create group for Drug History
					if (legacyData.get(28)!=null) {
						Obs o = new Obs();
						o.setPerson(ret);
						o.setConcept(Dictionary
								.getConcept(Dictionary.DRUG_HISTORY_GROUP));
						o.setObsDatetime(new Date());
						o.setLocation(Context.getService(KenyaEmrService.class)
								.getDefaultLocation());
						o.setEncounter(enDrugHistoryNew);
						drugGroup = Context.getObsService().saveObs(o,
								"KenyaEMR History Details");

						// Duration
						if (legacyData.get(29)!=null) {
							Concept duration = Context
									.getConceptService()
									.getConcept(
											Integer.parseInt(legacyData.get(29)));
							handleOncePerPatientObs(
									ret,
									Dictionary
											.getConcept(Dictionary.DRUG_DURATION),
									duration, "", null, enDrugHistoryNew,
									drugGroup.getObsGroupId());
						}

						// Place
						if (legacyData.get(27)!=null) {
							Concept place = Context
									.getConceptService()
									.getConcept(
											Integer.parseInt(legacyData.get(27)));
							handleOncePerPatientObs(
									ret,
									Dictionary
											.getConcept(Dictionary.DRUG_HISTORY_ART_RECEIVED_PLACE),
									place, "", null, enDrugHistoryNew,
									drugGroup.getObsGroupId());
						}

						// Regimen
						Concept place = Context.getConceptService().getConcept(
								Integer.parseInt(legacyData.get(28)));
						handleOncePerPatientObs(
								ret,
								Dictionary
										.getConcept(Dictionary.DRUG_REGIMEN_DRUG_HISTORY),
								place, "", null, enDrugHistoryNew, drugGroup
										.getObsGroupId());

					}

				}
				
				System.out.println("#############################Patient done:"+ rowCount+1);
			}
			rowCount++;
		}
		
		
		
		
		
		
		
		
		
		Sheet secondSheet = workbook.getSheetAt(1);
		Iterator<Row> iteratorSecond = secondSheet.iterator();
		int rowCountVisit = 0;

		while (iteratorSecond.hasNext()) {
			System.out.println("######################################Inside Second sheet");
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
							legacyData.add(cell.getColumnIndex(),
									String.valueOf(cell.getDateCellValue()));
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
					System.out.println(s + "-" + i);
					i++;
				}
		
			}
		}	
		
		
		
		
		
		

		inputStream.close();
		return SimpleObject.create("success", true);

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
			Concept newValue, String textValue, Date textDate, Encounter en,
			Integer obsGroup) {
		if (!newValue.equals("")) {
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
			if (obsGroup != null) {
				o.setObsGroupId(obsGroup);
			}
			Context.getObsService().saveObs(o, "KenyaEMR History Details");

		}
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