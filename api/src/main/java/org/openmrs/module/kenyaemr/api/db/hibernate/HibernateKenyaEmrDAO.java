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

package org.openmrs.module.kenyaemr.api.db.hibernate;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Cohort;
import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.DrugOrder;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Obs;
import org.openmrs.OrderType;
import org.openmrs.Patient;
import org.openmrs.PatientProgram;
import org.openmrs.Person;
import org.openmrs.PersonAddress;
import org.openmrs.Program;
import org.openmrs.Visit;
import org.openmrs.api.context.Context;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.kenyaemr.api.db.KenyaEmrDAO;
import org.openmrs.module.kenyaemr.model.DrugInfo;
import org.openmrs.module.kenyaemr.model.DrugObsProcessed;
import org.openmrs.module.kenyaemr.model.DrugOrderProcessed;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.validation.constraints.Size;

/**
 * Hibernate specific data access functions. This class should not be used directly.
 */
@SuppressWarnings("deprecation")
public class HibernateKenyaEmrDAO implements KenyaEmrDAO {

	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	SimpleDateFormat formatterExt = new SimpleDateFormat("yyyy-MM-dd");
	
	private SessionFactory sessionFactory;

	/**
	 * Sets the session factory
	 * @param sessionFactory the session factory
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * Convenience method to get current session
	 * @return the session
	 */
	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<Object> executeSqlQuery(String query, Map<String, Object> substitutions) {
		SQLQuery q = sessionFactory.getCurrentSession().createSQLQuery(query);

		for (Map.Entry<String, Object> e : substitutions.entrySet()) {
			if (e.getValue() instanceof Collection) {
				q.setParameterList(e.getKey(), (Collection) e.getValue());
			} else if (e.getValue() instanceof Object[]) {
				q.setParameterList(e.getKey(), (Object[]) e.getValue());
			} else if (e.getValue() instanceof Cohort) {
				q.setParameterList(e.getKey(), ((Cohort) e.getValue()).getMemberIds());
			} else if (e.getValue() instanceof Date) {
				q.setDate(e.getKey(), (Date) e.getValue());
			} else {
				q.setParameter(e.getKey(), e.getValue());
			}


		}

		q.setReadOnly(true);

		List<Object> r = q.list();
		return r;
	}

	@Override
	public List<Object> executeHqlQuery(String query, Map<String, Object> substitutions) {
		Query q = sessionFactory.getCurrentSession().createQuery(query);

		applySubstitutions(q, substitutions);

		// optimizations go here
		q.setReadOnly(true);

		return q.list();
	}

	private void applySubstitutions(Query q, Map<String, Object> substitutions) {
		for (Map.Entry<String, Object> e : substitutions.entrySet()) {
			if (e.getValue() instanceof Collection) {
				q.setParameterList(e.getKey(), (Collection) e.getValue());
			} else if (e.getValue() instanceof Object[]) {
				q.setParameterList(e.getKey(), (Object[]) e.getValue());
			} else if (e.getValue() instanceof Cohort) {
				q.setParameterList(e.getKey(), ((Cohort) e.getValue()).getMemberIds());
			} else if (e.getValue() instanceof Date) {
				q.setDate(e.getKey(), (Date) e.getValue());
			} else {
				q.setParameter(e.getKey(), e.getValue());
			}
		}
	}
	
	/*
	 * ENCOUNTER
	 */
	public Encounter getFirstEncounterByDateTime(Patient patient,Visit visit) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Encounter.class);
		criteria.add(Restrictions.eq("patient", patient));
		criteria.add(Restrictions.eq("visit", visit));
		criteria.addOrder(Order.asc("encounterDatetime"));
		criteria.setMaxResults(1);
		return (Encounter) criteria.uniqueResult();
	}
	
	public Encounter getFirstEncounterByCreatedDateTime(Patient patient,Visit visit) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Encounter.class);
		criteria.add(Restrictions.eq("patient", patient));
		criteria.add(Restrictions.eq("visit", visit));
		criteria.addOrder(Order.asc("dateCreated"));
		criteria.setMaxResults(1);
		return (Encounter) criteria.uniqueResult();
	}
	
	public Encounter getLastEncounterByDateTime(Patient patient,Visit visit) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Encounter.class);
		criteria.add(Restrictions.eq("patient", patient));
		criteria.add(Restrictions.eq("visit", visit));
		criteria.addOrder(Order.desc("encounterDatetime"));
		criteria.setMaxResults(1);
		return (Encounter) criteria.uniqueResult();
	}
	
	public Encounter getLastEncounterByCreatedDateTime(Patient patient,Visit visit) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Encounter.class);
		criteria.add(Restrictions.eq("patient", patient));
		criteria.add(Restrictions.eq("visit", visit));
		criteria.addOrder(Order.desc("dateCreated"));
		criteria.setMaxResults(1);
		return (Encounter) criteria.uniqueResult();
	}
	
	public Encounter getLastEncounterByDateTime(Patient patient,Set<EncounterType> encounterTypes) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Encounter.class);
		criteria.add(Restrictions.eq("patient", patient));
		criteria.add(Restrictions.in("encounterType", encounterTypes));
		criteria.addOrder(Order.desc("encounterDatetime"));
		criteria.setMaxResults(1);
		return (Encounter) criteria.uniqueResult();
	}
	
	public Encounter getLastEncounterByCreatedDateTime(Patient patient,Set<EncounterType> encounterTypes) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Encounter.class);
		criteria.add(Restrictions.eq("patient", patient));
		criteria.add(Restrictions.in("encounterType", encounterTypes));
		criteria.addOrder(Order.desc("dateCreated"));
		criteria.setMaxResults(1);
		return (Encounter) criteria.uniqueResult();
	}
	
	public List<org.openmrs.Order> getOrderByDateAndOrderType(Date date,OrderType orderType) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(org.openmrs.Order.class,"order");
		criteria.add(Restrictions.eq("order.orderType", orderType));
		if(date!=null){
		String datee = formatterExt.format(date);
		String startFromDate = datee + " 00:00:00";
		String endFromDate = datee + " 23:59:59";
		try {
			criteria.add(Restrictions.and(Restrictions.ge("order.startDate", formatter.parse(startFromDate)),
				    Restrictions.le("order.startDate", formatter.parse(endFromDate))));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		}
		return criteria.list();
	}
	
	public List<Obs> getObsGroupByDate(Date date) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Obs.class,"obs");
		if(date!=null){
		String dat = formatterExt.format(date);
		String startFromDate = dat + " 00:00:00";
		String endFromDate = dat + " 23:59:59";
		Concept concept1=Context.getConceptService().getConceptByUuid("163021AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		Concept concept2=Context.getConceptService().getConceptByUuid("163022AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		Concept concept3=Context.getConceptService().getConceptByUuid("163023AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		List<Concept> obsGroupCollection=new LinkedList<Concept>();
		obsGroupCollection.add(concept1);
		obsGroupCollection.add(concept2);
		obsGroupCollection.add(concept3);
		try {
			criteria.add(Restrictions.and(Restrictions.ge("obs.dateCreated", formatter.parse(startFromDate)),
				    Restrictions.le("obs.dateCreated", formatter.parse(endFromDate))));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		criteria.add(Restrictions.in("obs.concept", obsGroupCollection));
		}
		criteria.add(Restrictions.isNull("comment"));
		return criteria.list();
	}
	
	public List<Obs> getObsGroupByDateAndPerson(Date date,Person person) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Obs.class,"obs");
		criteria.add(Restrictions.eq("obs.person", person));
		Concept concept1=Context.getConceptService().getConceptByUuid("163021AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		Concept concept2=Context.getConceptService().getConceptByUuid("163022AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		Concept concept3=Context.getConceptService().getConceptByUuid("163023AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		List<Concept> obsGroupCollection=new LinkedList<Concept>();
		obsGroupCollection.add(concept1);
		obsGroupCollection.add(concept2);
		obsGroupCollection.add(concept3);
		if(date!=null){
		String dat = formatterExt.format(date);
		String startFromDate = dat + " 00:00:00";
		String endFromDate = dat + " 23:59:59";
		try {
			criteria.add(Restrictions.and(Restrictions.ge("obs.dateCreated", formatter.parse(startFromDate)),
				    Restrictions.le("obs.dateCreated", formatter.parse(endFromDate))));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		}
		criteria.add(Restrictions.in("obs.concept", obsGroupCollection));
		criteria.add(Restrictions.isNull("comment"));
		return criteria.list();
	}
	
	public List<Obs> getObsByObsGroup(Obs obsGroup) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Obs.class,"obs");
		criteria.add(Restrictions.eq("obs.obsGroup", obsGroup));
		return criteria.list();
	}
	
	public Obs saveOrUpdateObs(Obs obs) throws DAOException {
		return (Obs) sessionFactory.getCurrentSession().merge(obs);
	}
	
	public DrugOrderProcessed saveDrugOrderProcessed(DrugOrderProcessed drugOrderProcessed) throws DAOException {
		return (DrugOrderProcessed) sessionFactory.getCurrentSession().merge(drugOrderProcessed);
	}
	
	public DrugObsProcessed saveDrugObsProcessed(DrugObsProcessed drugObsProcessed) throws DAOException {
		return (DrugObsProcessed) sessionFactory.getCurrentSession().merge(drugObsProcessed);
	}
	
	public DrugOrderProcessed getDrugOrderProcessed(DrugOrder drugOrder) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugOrderProcessed.class,"drugOrderProcessed");
		criteria.add(Restrictions.eq("drugOrder", drugOrder));
		criteria.add(Restrictions.eq("processedStatus", false));
		criteria.add(Restrictions.isNull("discontinuedDate"));
		return (DrugOrderProcessed) criteria.uniqueResult();
	}
	
	public DrugOrderProcessed getLastDrugOrderProcessed(DrugOrder drugOrder) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugOrderProcessed.class,"drugOrderProcessed");
		criteria.add(Restrictions.eq("drugOrder", drugOrder));
		criteria.addOrder(Order.desc("createdDate"));
		criteria.setMaxResults(1);
		return (DrugOrderProcessed) criteria.uniqueResult();
	}
	
	public DrugOrderProcessed getLastDrugOrderProcessedNotDiscontinued(DrugOrder drugOrder) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugOrderProcessed.class,"drugOrderProcessed");
		criteria.add(Restrictions.eq("drugOrder", drugOrder));
		criteria.add(Restrictions.isNull("discontinuedDate"));
		criteria.addOrder(Order.desc("createdDate"));
		criteria.setMaxResults(1);
		return (DrugOrderProcessed) criteria.uniqueResult();
	}
	
	public List<DrugOrderProcessed> getDrugOrderProcessedCompleted(DrugOrder drugOrder) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugOrderProcessed.class,"drugOrderProcessed");
		criteria.add(Restrictions.eq("drugOrder", drugOrder));
		criteria.add(Restrictions.eq("processedStatus", true));
		return criteria.list();
	}
	
	public DrugOrderProcessed getDrugOrderProcesedById(Integer id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugOrderProcessed.class,"drugOrderProcessed");
		criteria.add(Restrictions.eq("id", id));
		return (DrugOrderProcessed) criteria.uniqueResult();
	}
	
	public List<DrugOrderProcessed> getDrugOrdersByProcessedDate(Date date) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugOrderProcessed.class,"drugOrderProcessed");
		String dat = formatterExt.format(date);
		String startFromDate = dat + " 00:00:00";
		String endFromDate = dat + " 23:59:59";
		try {
			criteria.add(Restrictions.and(Restrictions.ge("processedDate", formatter.parse(startFromDate)),
					Restrictions.le("processedDate", formatter.parse(endFromDate))));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return criteria.list();
	}
	
	public List<DrugObsProcessed> getObsDrugOrdersByProcessedDate(Date date) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugObsProcessed.class,"DrugObsProcessed");
		String dat = formatterExt.format(date);
		String startFromDate = dat + " 00:00:00";
		String endFromDate = dat + " 23:59:59";
		try {
			criteria.add(Restrictions.and(Restrictions.ge("processedDate", formatter.parse(startFromDate)),
				    Restrictions.le("processedDate", formatter.parse(endFromDate))));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return criteria.list();
	}
	
	public List<DrugOrderProcessed> getDrugOrdersByPatientAndProcessedDate(Patient patient,Date processedDate) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugOrderProcessed.class,"drugOrderProcessed");
		criteria.add(Restrictions.eq("patient", patient));
		criteria.add(Restrictions.eq("processedStatus", true));
		if(processedDate!=null){
		String dat = formatterExt.format(processedDate);
		String startFromDate = dat + " 00:00:00";
		String endFromDate = dat + " 23:59:59";
		try {
			criteria.add(Restrictions.and(Restrictions.ge("processedDate", formatter.parse(startFromDate)),
					Restrictions.le("processedDate", formatter.parse(endFromDate))));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		}
		return criteria.list();
	}
	
	public List<DrugObsProcessed> getObsDrugOrdersByPatientAndProcessedDate(Patient patient,Date processedDate) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugObsProcessed.class,"DrugObsProcessed");
		criteria.add(Restrictions.eq("patient", patient));
		if(processedDate!=null){
		String dat = formatterExt.format(processedDate);
		String startFromDate = dat + " 00:00:00";
		String endFromDate = dat + " 23:59:59";
		try {
			criteria.add(Restrictions.and(Restrictions.ge("processedDate", formatter.parse(startFromDate)),
				    Restrictions.le("processedDate", formatter.parse(endFromDate))));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		}
		return criteria.list();
	}
	
	public List<DrugInfo> getDrugInfo() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugInfo.class,"drugInfo");
		return criteria.list();
	}
	
	public DrugInfo getDrugInfo(String drugCode) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugInfo.class,"drugInfo");
		criteria.add(Restrictions.eq("drugCode", drugCode));
		return (DrugInfo) criteria.uniqueResult();
	}
	
	public DrugOrderProcessed getLastRegimenChangeType(Patient patient) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugOrderProcessed.class,"drugOrderProcessed");
		criteria.add(Restrictions.eq("patient", patient));
		//criteria.add(Restrictions.isNotNull("regimenChangeType"));
		//criteria.add(Restrictions.isNotNull("discontinuedDate"));
		//criteria.addOrder(Order.desc("discontinuedDate"));
		criteria.addOrder(Order.desc("createdDate"));
		criteria.setMaxResults(1);
		return (DrugOrderProcessed) criteria.uniqueResult();
	}
	
	public List<ConceptAnswer> getConceptAnswerByAnsweConcept(Concept answerConcept) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ConceptAnswer.class,"conceptAnswer");
		criteria.add(Restrictions.eq("answerConcept", answerConcept));
		return criteria.list();
	}
		@Override
	public List<DrugOrderProcessed> getAllfirstLine() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugOrderProcessed.class,"drugOrderProcessed");
		
		criteria.add(Restrictions.isNotNull("typeOfRegimen"));
		
		return criteria.list();
	}
	
	public List<PersonAddress> getPatientsByTownship(String township) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PersonAddress.class,"personAddress");
		criteria.add(Restrictions.ilike("countyDistrict", township+"%"));
		return criteria.list();
	}
	
	public List<Obs> getObsByScheduledDate(Date date) {
	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Obs.class,"obs");
	Collection<Concept> conList=new	ArrayList<Concept>();
	conList.add(Context.getConceptService().getConceptByUuid("5096AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"));
	conList.add(Context.getConceptService().getConceptByUuid("1879AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"));
	criteria.add(Restrictions.in("concept", conList));
	criteria.add(Restrictions.eq("valueDatetime", date));
	return criteria.list();
	}
	
	public List<PatientProgram> getPatientProgram(Program program,String startDate,String endDate) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PatientProgram.class,"patientProgram");
		criteria.add(Restrictions.eq("program", program));
		String startFromDate = startDate + " 00:00:00";
		String endFromDate = endDate + " 23:59:59";
		try {
			criteria.add(Restrictions.and(Restrictions.ge("dateEnrolled", formatter.parse(startFromDate)),
				    Restrictions.le("dateEnrolled", formatter.parse(endFromDate))));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return criteria.list();
		}
	
	public List<Obs> getNoOfPatientTransferredIn(String startDate,String endDate) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Obs.class,"obs");
		Collection<Concept> conList=new	ArrayList<Concept>();
		conList.add(Context.getConceptService().getConceptByUuid("4b73234a-15db-49a0-b089-c26c239fe90d"));
		conList.add(Context.getConceptService().getConceptByUuid("feee14d1-6cd6-4f5d-a3f6-056ed91526e5"));
		criteria.add(Restrictions.in("valueCoded", conList));
		String startFromDate = startDate + " 00:00:00";
		String endFromDate = endDate + " 23:59:59";
		try {
			criteria.add(Restrictions.and(Restrictions.ge("obsDatetime", formatter.parse(startFromDate)),
				    Restrictions.le("obsDatetime", formatter.parse(endFromDate))));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return criteria.list();
		}
	
	public List<Obs> getNoOfPatientTransferredOut(String startDate,String endDate) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Obs.class,"obs");
		Concept conceptTransferredOut=Context.getConceptService().getConceptByUuid("159492AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		criteria.add(Restrictions.eq("valueCoded", conceptTransferredOut));
		String startFromDate = startDate + " 00:00:00";
		String endFromDate = endDate + " 23:59:59";
		try {
			criteria.add(Restrictions.and(Restrictions.ge("obsDatetime", formatter.parse(startFromDate)),
				    Restrictions.le("obsDatetime", formatter.parse(endFromDate))));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return criteria.list();
		}
	
	public Visit getVisitsByPatient(Patient patient) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Visit.class,"visit");
		criteria.add(Restrictions.eq("patient", patient));
		criteria.addOrder(Order.asc("startDatetime"));
		criteria.setMaxResults(1);
		return (Visit) criteria.uniqueResult();
		}
	
	public Integer getTotalNoOfCohort(String startDate,String endDate) {
		Program program=Context.getProgramWorkflowService().getProgramByUuid("96ec813f-aaf0-45b2-add6-e661d5bf79d6");
		Integer noOfPaientOnART=getPatientProgram(program,startDate,endDate).size();
		Integer noOfPaientTransferredOut=getNoOfPatientTransferredOut(startDate,endDate).size();
		return noOfPaientOnART-noOfPaientTransferredOut;
		}
	
	public Integer getCohortBasedOnGender(String gender,String startDate,String endDate) {
		Program program=Context.getProgramWorkflowService().getProgramByUuid("96ec813f-aaf0-45b2-add6-e661d5bf79d6");
		List<PatientProgram> noOfPatientOnCohort=getPatientProgram(program,startDate,endDate);
		List<Obs> noOfPaientTransferredOut=getNoOfPatientTransferredOut(startDate,endDate);
		
		List<Person> personList=getListOfPatient(gender);
		List<Patient> patientList=new LinkedList<Patient>();
		for(Person person:personList){
			Patient patient=(Patient) person;
			patientList.add(patient);
		}
		
		Integer noOfPatientOnART=0;
		for(PatientProgram patientProgram:noOfPatientOnCohort){
			if(patientList.contains(patientProgram.getPatient())){
				noOfPatientOnART++;	
			}	
		}
		
		Integer noOfPatientTransferredOut=0;
		for(Obs obs:noOfPaientTransferredOut){
			if(patientList.contains(obs.getPatient())){
				noOfPatientTransferredOut++;	
			}	
		}
		return noOfPatientOnART-noOfPatientTransferredOut;
		}
	
	public Integer getCohortBasedOnAge(Integer age1,Integer age2,String startDate,String endDate) {
		Program program=Context.getProgramWorkflowService().getProgramByUuid("96ec813f-aaf0-45b2-add6-e661d5bf79d6");
		List<PatientProgram> noOfPatientOnCohort=getPatientProgram(program,startDate,endDate);
		List<Obs> noOfPaientTransferredOut=getNoOfPatientTransferredOut(startDate,endDate);
		
		List<Person> personList=getListOfPatient(age1,age2);
		List<Patient> patientList=new LinkedList<Patient>();
		for(Person person:personList){
			if(person.getAge()>=age1 && person.getAge()<=age2){
			Patient patient=(Patient) person;
			patientList.add(patient);
			}
		}
		
		Integer noOfPatientOnART=0;
		for(PatientProgram patientProgram:noOfPatientOnCohort){
			if(patientList.contains(patientProgram.getPatient())){
				noOfPatientOnART++;	
			}	
		}
		
		Integer noOfPatientTransferredOut=0;
		for(Obs obs:noOfPaientTransferredOut){
			if(patientList.contains(obs.getPatient())){
				noOfPatientTransferredOut++;	
			}	
		}
		return noOfPatientOnART-noOfPatientTransferredOut;
		}
	
	public Integer getNoOfCohortAliveAndOnArt(Program program,String startDate,String endDate) {
		Set<Patient> patientList=new LinkedHashSet<Patient>();
		List<PatientProgram> noOfArtStoppedCohorts=getNoOfArtStoppedCohort(program,startDate,endDate);
		List<PatientProgram> noOfArtDiedCohorts=getNoOfArtDiedCohort(program,startDate,endDate);
		List<Obs> noOfPatientLostToFollowUps=getNoOfPatientLostToFollowUp(startDate,endDate);
		
		for(PatientProgram noOfArtStoppedCohort:noOfArtStoppedCohorts){
			patientList.add(noOfArtStoppedCohort.getPatient());	
		}
		
        for(PatientProgram noOfArtDiedCohort:noOfArtDiedCohorts){
        	patientList.add(noOfArtDiedCohort.getPatient());		
		}
        
        for(Obs noOfPatientLostToFollowUp:noOfPatientLostToFollowUps){
        	patientList.add(noOfPatientLostToFollowUp.getPatient());	
		}
	
		return getTotalNoOfCohort(startDate,endDate)-patientList.size();
		}
	
	public Set<Patient> getOriginalFirstLineRegimen(String startDate,String endDate) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugOrderProcessed.class,"drugOrderProcessed");
		criteria.add(Restrictions.eq("regimenChangeType", "Start"));
		criteria.add(Restrictions.eq("typeOfRegimen", "First line Anti-retoviral drugs"));
		String startFromDate = startDate + " 00:00:00";
		String endFromDate = endDate + " 23:59:59";
		try {
			criteria.add(Restrictions.and(Restrictions.ge("createdDate", formatter.parse(startFromDate)),
				    Restrictions.le("createdDate", formatter.parse(endFromDate))));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<DrugOrderProcessed> drugOrderProcesseds=criteria.list();
		Set<Patient> dops=new LinkedHashSet<Patient>();
		for(DrugOrderProcessed drugOrderProcessed:drugOrderProcesseds){
			dops.add(drugOrderProcessed.getPatient());	
		}
		return dops;
	}
	
	public Set<Patient> getAlternateFirstLineRegimen(String startDate,String endDate) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugOrderProcessed.class,"drugOrderProcessed");
		criteria.add(Restrictions.eq("regimenChangeType", "Substitute"));
		criteria.add(Restrictions.eq("typeOfRegimen", "First line Anti-retoviral drugs"));
		String startFromDate = startDate + " 00:00:00";
		String endFromDate = endDate + " 23:59:59";
		try {
			criteria.add(Restrictions.and(Restrictions.ge("createdDate", formatter.parse(startFromDate)),
				    Restrictions.le("createdDate", formatter.parse(endFromDate))));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<DrugOrderProcessed> drugOrderProcesseds=criteria.list();
		Set<Patient> dops=new LinkedHashSet<Patient>();
		for(DrugOrderProcessed drugOrderProcessed:drugOrderProcesseds){
			dops.add(drugOrderProcessed.getPatient());	
		}
		return dops;
	}
	
	public Set<Patient> getSecondLineRegimen(String startDate,String endDate) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugOrderProcessed.class,"drugOrderProcessed");
		
		List<String> typeOfRegimen=new ArrayList<String>();
		typeOfRegimen.add("Second line ART");
		typeOfRegimen.add("Fixed dose combinations (FDCs)");
		
		criteria.add(Restrictions.eq("regimenChangeType", "Switch"));
		criteria.add(Restrictions.in("typeOfRegimen", typeOfRegimen));
		String startFromDate = startDate + " 00:00:00";
		String endFromDate = endDate + " 23:59:59";
		try {
			criteria.add(Restrictions.and(Restrictions.ge("createdDate", formatter.parse(startFromDate)),
				    Restrictions.le("createdDate", formatter.parse(endFromDate))));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<DrugOrderProcessed> drugOrderProcesseds=criteria.list();
		Set<Patient> dops=new LinkedHashSet<Patient>();
		for(DrugOrderProcessed drugOrderProcessed:drugOrderProcesseds){
			dops.add(drugOrderProcessed.getPatient());	
		}
		return dops;
	}
	
	public List<PatientProgram> getNoOfArtStoppedCohort(Program program,String startDate,String endDate) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PatientProgram.class,"patientProgram");
		criteria.add(Restrictions.eq("program", program));
		criteria.add(Restrictions.isNotNull("dateCompleted"));
		String startFromDate = startDate + " 00:00:00";
		String endFromDate = endDate + " 23:59:59";
		try {
			criteria.add(Restrictions.and(Restrictions.ge("dateCompleted", formatter.parse(startFromDate)),
				    Restrictions.le("dateCompleted", formatter.parse(endFromDate))));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return criteria.list();
		}
	
	
	public List<PatientProgram> getNoOfArtDiedCohort(Program program,String startDate,String endDate) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PatientProgram.class,"patientProgram");
		criteria.add(Restrictions.eq("program", program));
		criteria.add(Restrictions.isNull("dateCompleted"));
		String startFromDate = startDate + " 00:00:00";
		String endFromDate = endDate + " 23:59:59";
	
		List<Person> personList=getListOfDiedPatient(startDate,endDate);
		List<Patient> patientList=new LinkedList<Patient>();
		for(Person person:personList){
			Patient patient=(Patient) person;
			patientList.add(patient);
		}
		
		List<PatientProgram> patientProgram=new ArrayList<PatientProgram>();
		if(patientList.size()!=0){
			criteria.add(Restrictions.in("patient", patientList));
			patientProgram=criteria.list();	
		}
		
		return patientProgram;
		}
	
	public List<Obs> getNoOfPatientLostToFollowUp(String startDate,String endDate) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Obs.class,"obs");
		Concept conceptLostToFollowUp=Context.getConceptService().getConceptByUuid("5240AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

		criteria.add(Restrictions.eq("valueCoded", conceptLostToFollowUp));
		String startFromDate = startDate + " 00:00:00";
		String endFromDate = endDate + " 23:59:59";
		try {
			criteria.add(Restrictions.and(Restrictions.ge("obsDatetime", formatter.parse(startFromDate)),
				    Restrictions.le("obsDatetime", formatter.parse(endFromDate))));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		criteria.add(Restrictions.isNull("voided"));
		return criteria.list();
		}
	
	public List<Obs> getNoOfPatientWithCD4(String startDate,String endDate) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Obs.class,"obs");
		Concept cd4Concept=Context.getConceptService().getConceptByUuid("5497AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		Integer obj = new Integer(200);
		double doub = obj.doubleValue();

		criteria.add(Restrictions.and(Restrictions.eq("concept", cd4Concept),Restrictions.ge("valueNumeric", doub)));
		String startFromDate = startDate + " 00:00:00";
		String endFromDate = endDate + " 23:59:59";
		try {
			criteria.add(Restrictions.and(Restrictions.ge("obsDatetime", formatter.parse(startFromDate)),
				    Restrictions.le("obsDatetime", formatter.parse(endFromDate))));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		criteria.add(Restrictions.isNull("voided"));
		return criteria.list();
		}
		
		public List<Obs> getNoOfPatientNormalActivity(String startDate,String endDate) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Obs.class,"obs");
		Concept scaleA=Context.getConceptService().getConceptByUuid("e8a480a7-1f05-402c-9adf-9acbd6ff446f");

		criteria.add(Restrictions.eq("valueCoded", scaleA));
		String startFromDate = startDate + " 00:00:00";
		String endFromDate = endDate + " 23:59:59";
		try {
			criteria.add(Restrictions.and(Restrictions.ge("obsDatetime", formatter.parse(startFromDate)),
				    Restrictions.le("obsDatetime", formatter.parse(endFromDate))));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		criteria.add(Restrictions.isNull("voided"));
		return criteria.list();
		}
		
		public List<Obs> getNoOfPatientBedriddenLessThanFifty(String startDate,String endDate) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Obs.class,"obs");
		Concept scaleB=Context.getConceptService().getConceptByUuid("585dcf92-c42f-42af-ac44-fdd2fb66ae3a");

		criteria.add(Restrictions.eq("valueCoded", scaleB));
		String startFromDate = startDate + " 00:00:00";
		String endFromDate = endDate + " 23:59:59";
		try {
			criteria.add(Restrictions.and(Restrictions.ge("obsDatetime", formatter.parse(startFromDate)),
				    Restrictions.le("obsDatetime", formatter.parse(endFromDate))));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		criteria.add(Restrictions.isNull("voided"));
		return criteria.list();
		}
		
		public List<Obs> getNoOfPatientBedriddenMoreThanFifty(String startDate,String endDate) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Obs.class,"obs");
		Concept scaleC=Context.getConceptService().getConceptByUuid("a70cd549-aa63-4310-9a38-715dfc3ebbd2");

		criteria.add(Restrictions.eq("valueCoded", scaleC));
		String startFromDate = startDate + " 00:00:00";
		String endFromDate = endDate + " 23:59:59";
		try {
			criteria.add(Restrictions.and(Restrictions.ge("obsDatetime", formatter.parse(startFromDate)),
				    Restrictions.le("obsDatetime", formatter.parse(endFromDate))));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		criteria.add(Restrictions.isNull("voided"));
		return criteria.list();
		}
		
		public List<Obs> getNoOfPatientPickedUpArvForSixMonth(String startDate,String endDate) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Obs.class,"obs");
		Concept conceptLostToFollowUp=Context.getConceptService().getConceptByUuid("5240AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		criteria.add(Restrictions.eq("valueCoded", conceptLostToFollowUp));
		String startFromDate = startDate + " 00:00:00";
		String endFromDate = endDate + " 23:59:59";
		try {
			criteria.add(Restrictions.and(Restrictions.ge("obsDatetime", formatter.parse(startFromDate)),
				    Restrictions.le("obsDatetime", formatter.parse(endFromDate))));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		criteria.add(Restrictions.isNull("voided"));
		//getNoOfPatientLostToFollowUp(startDate,endDate);
		return criteria.list();
		}
		
		public List<Obs> getNoOfPatientPickedUpArvForTwelveMonth(String startDate,String endDate) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Obs.class,"obs");
		Concept conceptLostToFollowUp=Context.getConceptService().getConceptByUuid("5240AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		criteria.add(Restrictions.eq("valueCoded", conceptLostToFollowUp));
		String startFromDate = startDate + " 00:00:00";
		String endFromDate = endDate + " 23:59:59";
		try {
			criteria.add(Restrictions.and(Restrictions.ge("obsDatetime", formatter.parse(startFromDate)),
				    Restrictions.le("obsDatetime", formatter.parse(endFromDate))));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		criteria.add(Restrictions.isNull("voided"));
		return criteria.list();
		}
		
		
	public List<DrugOrderProcessed> getDrugOrderProcessedByPatient(Patient patient) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugOrderProcessed.class);
		criteria.add(Restrictions.eq("patient", patient));
		return criteria.list();
	}
	
	public List<Person> getListOfDiedPatient(String startDate,String endDate) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Person.class,"person");
		String startFromDate = startDate + " 00:00:00";
		String endFromDate = endDate + " 23:59:59";
			
		try {
			criteria.add(Restrictions.and(Restrictions.ge("deathDate", formatter.parse(startFromDate)),
					    Restrictions.le("deathDate", formatter.parse(endFromDate))));
		} catch (ParseException e) {
				e.printStackTrace();
		}
			
		criteria.add(Restrictions.eq("dead", true));
		return criteria.list();	
	}
	
	public List<Person> getListOfDiedPatient() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Person.class,"person");
		criteria.add(Restrictions.eq("dead", true));
		criteria.add(Restrictions.ge("personId", 38));
		return criteria.list();	
	}
	
	public List<Person> getListOfAlivePatient() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Person.class,"person");
		criteria.add(Restrictions.eq("dead", false));
		criteria.add(Restrictions.ge("personId", 38));
		return criteria.list();	
	}
	
	public List<Person> getListOfPatient(String gender) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Person.class,"person");
		criteria.add(Restrictions.eq("gender", gender));
		criteria.add(Restrictions.ge("personId", 38));
		return criteria.list();	
	}
	
	public List<Person> getListOfPatient(Integer age1,Integer age2) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Person.class,"person");
		criteria.add(Restrictions.ge("personId", 38));
		return criteria.list();	
	}

}