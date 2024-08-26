package com.javalab.humaninfo.repository;

import com.javalab.humaninfo.entity.Human;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HumanRepository extends JpaRepository<Human, Long> {
}

