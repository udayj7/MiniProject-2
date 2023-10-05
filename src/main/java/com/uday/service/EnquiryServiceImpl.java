package com.uday.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uday.binding.DashboardResponse;
import com.uday.entity.StudentEnqEntity;
import com.uday.entity.UserDtlsEntity;
import com.uday.repo.UserDtlsRepo;

@Service
public class EnquiryServiceImpl implements EnquiryService {

	@Autowired
	private UserDtlsRepo userDtlsRepo;

	@Override
	public DashboardResponse getDashboardData(Integer userId) {

		DashboardResponse response = new DashboardResponse();
		Optional<UserDtlsEntity> findById = userDtlsRepo.findById(userId);

		if (findById.isPresent()) {
			UserDtlsEntity userDtlsEntity = findById.get();

			// One user multiple enquires
			List<StudentEnqEntity> enquiries = userDtlsEntity.getEnquiries();
			Integer totalEnquiries = enquiries.size();

			List<StudentEnqEntity> enrolled = enquiries.stream().filter(i -> i.getEnqStatus().equals("ENROLLED"))
					.collect(Collectors.toList());

			Integer enrolledCnt = enrolled.size();

			Integer lostCnt = enquiries.stream().filter(i -> i.getEnqStatus().equals("LOST"))
					.collect(Collectors.toList()).size();

			response.setTotalEnquiresCnt(totalEnquiries);
			response.setEnrolledCnt(enrolledCnt);
			response.setLostCnt(lostCnt);

		}

		return response;
	}

}
