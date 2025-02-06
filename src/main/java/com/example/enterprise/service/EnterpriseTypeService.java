package com.example.enterprise.service;

import com.example.enterprise.model.EnterpriseType;
import com.example.enterprise.repository.EnterpriseTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EnterpriseTypeService {

    private final EnterpriseTypeRepository enterpriseTypeRepository;

    @Autowired
    public EnterpriseTypeService(EnterpriseTypeRepository enterpriseTypeRepository) {
        this.enterpriseTypeRepository = enterpriseTypeRepository;
    }

    // Все типы предприятий
    public List<EnterpriseType> findAll() {
        return enterpriseTypeRepository.findAll();
    }

    // По ID
    public Optional<EnterpriseType> findById(Long id) {
        return enterpriseTypeRepository.findById(id);
    }

    // Сохранить или обновить тип предприятия
    public EnterpriseType save(EnterpriseType enterpriseType) {
        return enterpriseTypeRepository.save(enterpriseType);
    }

    // Удалить по ID
    public void deleteById(Long id) {
        enterpriseTypeRepository.deleteById(id);
    }

    // По названию
    public List<EnterpriseType> findByNameContaining(String name) {
        return enterpriseTypeRepository.findByNameContainingIgnoreCase(name);
    }

    // По коду
    public EnterpriseType findByCode(String code) {
        return enterpriseTypeRepository.findByCode(code);
    }
}