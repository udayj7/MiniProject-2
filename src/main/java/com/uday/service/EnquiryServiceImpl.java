package com.uday.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uday.binding.DashboardResponse;
import com.uday.binding.EnquiryForm;
import com.uday.entity.CourseEntity;
import com.uday.entity.EnqStatusEntity;
import com.uday.entity.StudentEnqEntity;
import com.uday.entity.UserDtlsEntity;
import com.uday.repo.CourseRepo;
import com.uday.repo.EnqStatusRepo;
import com.uday.repo.StudentEnqRepo;
import com.uday.repo.UserDtlsRepo;

@Service
public class EnquiryServiceImpl implements EnquiryService {

	@Autowired
	private StudentEnqRepo enqRepo;

	@Autowired
	private UserDtlsRepo userDtlsRepo;

	@Autowired
	private CourseRepo courseRepo;

	@Autowired
	private EnqStatusRepo statusRepo;

	@Autowired
	private HttpSession session;

	@Override
	public DashboardResponse getDashboardData(Integer userId) {

		DashboardResponse response = new DashboardResponse();
		Optional<UserDtlsEntity> findById = userDtlsRepo.findById(userId);

		if (findById.isPresent()) {
			UserDtlsEntity userDtlsEntity = findById.get();

			// One user multiple enquires
			List<StudentEnqEntity> enquiries = userDtlsEntity.getEnquiries();
			Integer totalEnquiries = enquiries.size();

			List<StudentEnqEntity> enrolled = enquiries.stream().filter(i -> i.getEnqStatus().equals("Enrolled"))
					.collect(Collectors.toList());

			Integer enrolledCnt = enrolled.size();

			Integer lostCnt = enquiries.stream().filter(i -> i.getEnqStatus().equals("Lost"))
					.collect(Collectors.toList()).size();

			response.setTotalEnquiresCnt(totalEnquiries);
			response.setEnrolledCnt(enrolledCnt);
			response.setLostCnt(lostCnt);

		}

		return response;
	}

	@Override
	public List<String> getCourse() {
		List<CourseEntity> findAll = courseRepo.findAll();

		List<String> names = new ArrayList<>();

		for (CourseEntity entity : findAll) {
			names.add(entity.getCourseName());
		}

		return names;
	}

	@Override
	public List<String> getEnquStatuses() {

		List<EnqStatusEntity> findAll = statusRepo.findAll();

		List<String> statusList = new ArrayList<>();

		for (EnqStatusEntity entity : findAll) {
			statusList.add(entity.getStatusName());
		}

		return statusList;
	}

	@Override
	public boolean saveEnquiry(EnquiryForm form) {

		StudentEnqEntity enqEntity = new StudentEnqEntity();
		BeanUtils.copyProperties(form, enqEntity);

		Integer userId = (Integer) session.getAttribute("userId");

		UserDtlsEntity userDtlsEntity = userDtlsRepo.findById(userId).get();
		enqEntity.setUser(userDtlsEntity);
		enqRepo.save(enqEntity);

		return true;
	}

	@Override
	public List<StudentEnqEntity> getEnquiries() {

		Integer userId = (Integer) session.getAttribute("userId");

		Optional<UserDtlsEntity> findById = userDtlsRepo.findById(userId);
		if (findById.isPresent()) {
			UserDtlsEntity userDtlsEntity = findById.get();
			List<StudentEnqEntity> enquiries = userDtlsEntity.getEnquiries();
			return enquiries;
		}

		return null;
	}
}
