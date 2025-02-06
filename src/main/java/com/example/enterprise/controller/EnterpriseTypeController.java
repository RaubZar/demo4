package com.example.enterprise.controller;

import com.example.enterprise.model.Enterprise;
import com.example.enterprise.model.EnterpriseType;
import com.example.enterprise.service.EnterpriseService;
import com.example.enterprise.service.EnterpriseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/enterprise-types")
public class EnterpriseTypeController {

    private final EnterpriseTypeService enterpriseTypeService;
    private final EnterpriseService enterpriseService;

    @Autowired
    public EnterpriseTypeController(EnterpriseTypeService enterpriseTypeService, EnterpriseService enterpriseService) {
        this.enterpriseTypeService = enterpriseTypeService;
        this.enterpriseService = enterpriseService;
    }

    // Список всех типов предприятий
    @GetMapping
    public String getAllEnterpriseTypes(Model model) {
        List<EnterpriseType> enterpriseTypes = enterpriseTypeService.findAll();
        model.addAttribute("enterpriseTypes", enterpriseTypes);
        return "enterprise-types";
    }

    // Форма добавления типа предприятия
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("enterpriseType", new EnterpriseType());
        return "add-enterprise-type";
    }

    // Обработать добавление типа предприятия
    @PostMapping("/add")
    public String addEnterpriseType(@ModelAttribute EnterpriseType enterpriseType) {
        enterpriseTypeService.save(enterpriseType);
        return "redirect:/enterprise-types";
    }

    // Форма редактирования типа предприятия
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        EnterpriseType enterpriseType = enterpriseTypeService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid enterprise type Id:" + id));
        model.addAttribute("enterpriseType", enterpriseType);
        return "edit-enterprise-type";
    }

    // Обработать редактирование типа предприятия
    @PostMapping("/edit/{id}")
    public String updateEnterpriseType(@PathVariable Long id, @ModelAttribute EnterpriseType enterpriseType, RedirectAttributes redirectAttributes) {
        try {

            EnterpriseType existingEnterpriseType = enterpriseTypeService.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid enterprise type Id:" + id));

            existingEnterpriseType.setName(enterpriseType.getName());
            existingEnterpriseType.setCode(enterpriseType.getCode());
            existingEnterpriseType.setComment(enterpriseType.getComment());


            enterpriseTypeService.save(existingEnterpriseType);

            redirectAttributes.addFlashAttribute("successMessage", "Тип предприятия успешно обновлен!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при обновлении типа предприятия: " + e.getMessage());
            return "redirect:/enterprise-types/edit/" + id;
        }
        return "redirect:/enterprise-types";
    }

    // Удалить тип предприятия
    @GetMapping("/delete/{id}")
    public String deleteEnterpriseType(@PathVariable Long id) {
        enterpriseTypeService.deleteById(id);
        return "redirect:/enterprise-types";
    }
    // Отобразить список предприятий для конкретного типа предприятия
    @GetMapping("/{id}/enterprises")
    public String getEnterprisesByType(@PathVariable Long id, Model model) {
        try {
            EnterpriseType enterpriseType = enterpriseTypeService.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid enterprise type Id:" + id));
            List<Enterprise> enterprises = enterpriseService.findByEnterpriseTypeId(id);
            model.addAttribute("enterpriseType", enterpriseType);
            model.addAttribute("enterprises", enterprises);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Ошибка при загрузке предприятий: " + e.getMessage());
            return "redirect:/enterprise-types";
        }
        return "enterprises-by-type";
    }
}