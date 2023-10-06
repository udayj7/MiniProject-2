package com.uday.service;

import java.util.List;

import com.uday.binding.DashboardResponse;
import com.uday.binding.EnquiryForm;
import com.uday.binding.EnquirySearchCriteria;
import com.uday.entity.StudentEnqEntity;

public interface EnquiryService {

	public DashboardResponse getDashboardData(Integer userId);

	public List<String> getCourse();

	public List<String> getEnquStatuses();

	public boolean saveEnquiry(EnquiryForm form);

	public List<StudentEnqEntity> getEnquiries();

	public List<StudentEnqEntity> getFilteredEnqs(EnquirySearchCriteria criteria, Integer userId);

//	public String upsertEnquiry(EnquiryForm form);
//
//	public List<EnquiryForm> getEnquires(Integer userId, EnquirySearchCriteria criteria);
//
//	public EnquiryForm getEnquiry(Integer enqId);

}
