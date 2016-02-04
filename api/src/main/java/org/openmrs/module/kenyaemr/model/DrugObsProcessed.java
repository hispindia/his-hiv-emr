package org.openmrs.module.kenyaemr.model;

import java.util.Date;
import org.openmrs.Obs;

public class DrugObsProcessed implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Obs obs;
	private Date createdDate;
	private Integer quantityPostProcess;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Obs getObs() {
		return obs;
	}

	public void setObs(Obs obs) {
		this.obs = obs;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getQuantityPostProcess() {
		return quantityPostProcess;
	}

	public void setQuantityPostProcess(Integer quantityPostProcess) {
		this.quantityPostProcess = quantityPostProcess;
	}
}
