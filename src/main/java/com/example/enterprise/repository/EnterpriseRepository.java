package com.example.enterprise.repository;

import com.example.enterprise.model.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> {

    // Поиск предприятий по названию
    List<Enterprise> findByNameContainingIgnoreCase(String name);

    // Поиск предприятий по типу предприятия
    List<Enterprise> findByEnterpriseTypeId(Long enterpriseTypeId);

    // Поиск предприятий по коду
    Enterprise findByCode(String code);
}