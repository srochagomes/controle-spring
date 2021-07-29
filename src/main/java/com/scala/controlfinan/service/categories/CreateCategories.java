package com.scala.controlfinan.service.categories;

import com.scala.controlfinan.domain.Category;
import com.scala.controlfinan.repository.Categories;
import com.scala.controlfinan.repository.data.CategoryEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CreateCategories {

    private Categories categories;

    @Transactional
    public Category process(Category data) {
        Optional.ofNullable(data).orElseThrow(()-> new NotFoundException("Main Object is null."));
        Optional.ofNullable(data.getName()).orElseThrow(()-> new BadRequestException("Name Category is required."));
        Optional.ofNullable(data.getType()).orElseThrow(()-> new BadRequestException("Type Category is required."));

        CategoryEntity category = CategoryEntity.builder()
                .name(data.getName())
                .type(data.getType()).build();
        categories.save(category);
        data.setId(category.getId());
        return data;

    }
}
