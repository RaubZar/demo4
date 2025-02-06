package com.example.enterprise.repository;

import com.example.enterprise.model.EnterpriseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnterpriseTypeRepository extends JpaRepository<EnterpriseType, Long> {

    // Поиск типов предприятий по названию
    List<EnterpriseType> findByNameContainingIgnoreCase(String name);

    // Поиск типа предприятия по коду
    EnterpriseType findByCode(String code);
}