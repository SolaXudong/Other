package com.xu.tt.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserNew {
	@TableId(value = "user_id", type = IdType.ASSIGN_ID)
	private String userId;

	private String name;

	private Integer age;

	private Date birth;
}