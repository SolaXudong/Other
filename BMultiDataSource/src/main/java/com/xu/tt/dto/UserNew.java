package com.xu.tt.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_new")
public class UserNew {
	@Id
	@Column(name = "user_id")
	private String userId;

	private String name;

	private Integer age;

	private Date birth;
}