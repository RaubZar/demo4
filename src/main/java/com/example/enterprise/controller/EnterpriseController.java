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
@RequestMapping("/enterprises")
public class EnterpriseController {

    private final EnterpriseService enterpriseService;
    private final EnterpriseTypeService enterpriseTypeService;

    @Autowired
    public EnterpriseController(EnterpriseService enterpriseService, EnterpriseTypeService enterpriseTypeService) {
        this.enterpriseService = enterpriseService;
        this.enterpriseTypeService = enterpriseTypeService;
    }

    // Список всех предприятий
    @GetMapping
    public String getAllEnterprises(Model model) {
        List<Enterprise> enterprises = enterpriseService.findAll();
        model.addAttribute("enterprises", enterprises);
        return "enterprises";
    }

    // Форма добавления предприятия
    @GetMapping("/add")
    public String showAddForm(Model model) {
        List<EnterpriseType> enterpriseTypes = enterpriseTypeService.findAll();
        model.addAttribute("enterprise", new Enterprise());
        model.addAttribute("enterpriseTypes", enterpriseTypes);
        return "add-enterprise";
    }

    // Обработать добавление предприятия
    @PostMapping("/add")
    public String addEnterprise(@ModelAttribute Enterprise enterprise, RedirectAttributes redirectAttributes) {
        try {
            enterpriseService.save(enterprise);
            redirectAttributes.addFlashAttribute("successMessage", "Предприятие успешно добавлено!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при добавлении предприятия: " + e.getMessage());
            return "redirect:/enterprises/add";
        }
        return "redirect:/enterprises";
    }

    // Форма редактирования предприятия
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            Enterprise enterprise = enterpriseService.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid enterprise Id:" + id));
            List<EnterpriseType> enterpriseTypes = enterpriseTypeService.findAll();
            model.addAttribute("enterprise", enterprise);
            model.addAttribute("enterpriseTypes", enterpriseTypes);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Ошибка при загрузке предприятия: " + e.getMessage());
            return "redirect:/enterprises";
        }
        return "edit-enterprise";
    }

    // Обработать редактирование предприятия
    @PostMapping("/edit/{id}")
    public String updateEnterprise(@PathVariable Long id, @ModelAttribute Enterprise enterprise, RedirectAttributes redirectAttributes) {
        try {
            enterprise.setId(id);
            enterpriseService.save(enterprise);
            redirectAttributes.addFlashAttribute("successMessage", "Предприятие успешно обновлено!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при обновлении предприятия: " + e.getMessage());
            return "redirect:/enterprises/edit/" + id;
        }
        return "redirect:/enterprises";
    }

    // Удалить предприятие
    @GetMapping("/delete/{id}")
    public String deleteEnterprise(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            enterpriseService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Предприятие успешно удалено!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при удалении предприятия: " + e.getMessage());
        }
        return "redirect:/enterprises";
    }
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