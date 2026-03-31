package com.j2ee.J2EE.controller;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.j2ee.J2EE.model.Category;
import com.j2ee.J2EE.repository.CategoryRepository;

@Controller
@RequestMapping
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/admin/categories")
    public String list(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/categories/list";
    }

    @GetMapping("/admin/categories/new")
    public String createForm(Model model) {
        model.addAttribute("category", new Category());
        return "admin/categories/form";
    }

    @PostMapping("/admin/categories")
    public String create(@Valid @ModelAttribute("category") Category category,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/categories/form";
        }
        categoryRepository.save(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("category", category);
        return "admin/categories/form";
    }

    @PostMapping("/admin/categories/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("category") Category form,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/categories/form";
        }
        Category existing = categoryRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        existing.setName(form.getName());
        categoryRepository.save(existing);
        return "redirect:/admin/categories";
    }

    @PostMapping("/admin/categories/{id}/delete")
    public String delete(@PathVariable Long id) {
        categoryRepository.deleteById(id);
        return "redirect:/admin/categories";
    }
}