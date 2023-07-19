package com.fastcampus.programming.dmaker.repository;

import com.fastcampus.programming.dmaker.entity.DeveloperEntity;
import com.fastcampus.programming.dmaker.entity.RetiredDeveloperEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RetiredDeveloperRepository extends JpaRepository<RetiredDeveloperEntity, Long> {
}
