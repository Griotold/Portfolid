package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.TbUser;

public interface TbUserRepository extends JpaRepository<TbUser, String>{

	@EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
	@Query("select m from TbUser m where m.id = :id")
	Optional<TbUser> findById(@Param("id") String id);
	
}