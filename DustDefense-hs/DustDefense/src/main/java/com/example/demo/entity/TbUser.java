package com.example.demo.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TbUser extends BaseEntity{

	@Id
	private String id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "nickname", nullable = false)
	private String nickname;
	
	@Column(name = "email", nullable = false)
	private String email;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "passwordQusestionCode", nullable = false)
	private String passwordQusestionCode;
	
	@Column(name = "passwordQuestionAnswer", nullable = false)
	private String passwordQuestionAnswer;
	
	@Column(name = "validMember", nullable = false)
	private boolean validMember;
	
	
	@ElementCollection(fetch = FetchType.LAZY)
    private Set<TbUserRole> roleSet;
	
	public void addMemberRole(TbUserRole tbUserRole) {
		roleSet.add(tbUserRole);
	}
}