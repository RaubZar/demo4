package com.example.enterprise.service;

import com.example.enterprise.model.Enterprise;
import com.example.enterprise.repository.EnterpriseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnterpriseService {

    private final EnterpriseRepository enterpriseRepository;

    @Autowired
    public EnterpriseService(EnterpriseRepository enterpriseRepository) {
        this.enterpriseRepository = enterpriseRepository;
    }

    // Все предприятия
    public List<Enterprise> findAll() {
        return enterpriseRepository.findAll();
    }

    // По ID
    public Optional<Enterprise> findById(Long id) {
        return enterpriseRepository.findById(id);
    }

    // Сохранить или обновить
    public Enterprise save(Enterprise enterprise) {
        return enterpriseRepository.save(enterprise);
    }

    // Удалить по ID
    public void deleteById(Long id) {
        enterpriseRepository.deleteById(id);
    }

    // По названию
    public List<Enterprise> findByNameContaining(String name) {
        return enterpriseRepository.findByNameContainingIgnoreCase(name);
    }

    // По типу предприятия
    public List<Enterprise> findByEnterpriseTypeId(Long enterpriseTypeId) {
        return enterpriseRepository.findByEnterpriseTypeId(enterpriseTypeId);
    }

    // По коду
    public Enterprise findByCode(String code) {
        return enterpriseRepository.findByCode(code);
    }
}