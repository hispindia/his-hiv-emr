package org.openmrs.module.kenyaemr.model;

import java.util.Date;

import org.openmrs.DrugOrder;

public class DrugOrderProcessed implements java.io.Serializable {
	private static final long serialVersionUID = 4757208144130681309L;
	private Integer id;
	private DrugOrder drugOrder;
	private Date createdDate;
	private Boolean processedStatus = Boolean.FALSE;
	private Integer durationPreProcess;
	private Date discontinuedDate;
	private Integer quantityPostProcess;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public DrugOrder getDrugOrder() {
		return drugOrder;
	}

	public void setDrugOrder(DrugOrder drugOrder) {
		this.drugOrder = drugOrder;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Boolean getProcessedStatus() {
		return processedStatus;
	}

	public void setProcessedStatus(Boolean processedStatus) {
		this.processedStatus = processedStatus;
	}

	public Integer getDurationPreProcess() {
		return durationPreProcess;
	}

	public void setDurationPreProcess(Integer durationPreProcess) {
		this.durationPreProcess = durationPreProcess;
	}

	public Date getDiscontinuedDate() {
		return discontinuedDate;
	}

	public void setDiscontinuedDate(Date discontinuedDate) {
		this.discontinuedDate = discontinuedDate;
	}

	public Integer getQuantityPostProcess() {
		return quantityPostProcess;
	}

	public void setQuantityPostProcess(Integer quantityPostProcess) {
		this.quantityPostProcess = quantityPostProcess;
	}
}
