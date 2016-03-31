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

package org.openmrs.module.kenyaemr.fragment.controller.field;

import java.text.SimpleDateFormat;

import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyaemr.api.KenyaEmrService;
import org.openmrs.module.kenyaemr.model.DrugOrderProcessed;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.fragment.FragmentModel;

/**
 *
 */
public class HivRegimenChangeFragmentController {

	public void controller(@FragmentParam("patient") Patient patient,
			FragmentModel model) {
		KenyaEmrService kenyaEmrService = (KenyaEmrService) Context
				.getService(KenyaEmrService.class);
		DrugOrderProcessed drugOrderProcessed = kenyaEmrService
				.getLastRegimenChangeType(patient);
		model.addAttribute("drugOrderProcessed", drugOrderProcessed);
		SimpleDateFormat formatterExt = new SimpleDateFormat("dd-MMM-yyyy");
		if(drugOrderProcessed!=null){
		String date = formatterExt.format(drugOrderProcessed.getDiscontinuedDate());
		model.addAttribute("date", date);
		}
	}

}