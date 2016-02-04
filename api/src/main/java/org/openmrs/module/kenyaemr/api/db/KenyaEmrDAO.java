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

package org.openmrs.module.kenyaemr.api.db;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openmrs.DrugOrder;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Obs;
import org.openmrs.Order;
import org.openmrs.OrderType;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.module.kenyaemr.model.DrugInfo;
import org.openmrs.module.kenyaemr.model.DrugOrderProcessed;

/**
 * Database access functions
 */
public interface KenyaEmrDAO {
	public List<Object> executeSqlQuery(String query, Map<String, Object> substitutions);
	public List<Object> executeHqlQuery(String query, Map<String, Object> substitutions);
	/**
	 * Get last encounter
	 * @param patient
	 * @return
	 */
	public Encounter getLastEncounter(Patient patient,Set<EncounterType> encounterTypes);
	public List<Order> getOrderByDateAndOrderType(Date date,OrderType orderType);
	public List<Obs> getObsGroupByDate(Date date);
	public List<Obs> getObsGroupByDateAndPerson(Date date,Person person);
	public List<Obs> getObsByObsGroup(Obs obsGroup);
	public DrugOrderProcessed saveDrugOrderProcessed(DrugOrderProcessed drugOrderProcessed);
	public DrugOrderProcessed getDrugOrderProcessed(DrugOrder drugOrder);
	public DrugOrderProcessed getDrugOrderProcesedById(Integer id);
	public List<DrugInfo> getDrugInfo();
	public DrugInfo getDrugInfo(String drugName);
}