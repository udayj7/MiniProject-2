package com.uday.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "AIT_COURSES")
@Data
public class CourseEntity {

	@Id
	@GeneratedValue
	private Integer courseId;

	private String courseName;

}
