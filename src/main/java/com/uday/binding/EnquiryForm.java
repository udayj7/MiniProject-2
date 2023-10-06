package com.uday.binding;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
public class EnquiryForm {

	private String studentName;

	private Long studentPhno;

	private LocalDate dateCreated;

	private String enqStatus;

	private String classMode;

	private String courseName;

}
