package com.j2ee.J2EE.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.j2ee.J2EE.model.Category;
import com.j2ee.J2EE.repository.CategoryRepository;

@Component
public class StringToCategoryConverter implements Converter<String, Category> {

    private final CategoryRepository categoryRepository;

    public StringToCategoryConverter(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category convert(String source) {
        if (source == null || source.trim().isEmpty()) {
            return null;
        }
        try {
            Long id = Long.parseLong(source);
            return categoryRepository.findById(id).orElse(null);
        } catch (NumberFormatException ex) {
            return null;
        }
    }
}