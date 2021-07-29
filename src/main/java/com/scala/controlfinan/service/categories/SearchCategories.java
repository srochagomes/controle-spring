package com.scala.controlfinan.service.categories;

import com.scala.controlfinan.domain.Category;
import com.scala.controlfinan.repository.Categories;
import com.scala.controlfinan.repository.data.CategoryEntity;
import com.scala.controlfinan.repository.specification.Filter;
import com.scala.controlfinan.repository.specification.QueryOperator;
import com.scala.controlfinan.repository.specification.SpecificationBuilder;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SearchCategories {

    private Categories categories;


    public Category findById(Integer id){

        Optional.ofNullable(id).orElseThrow(()-> new BadRequestException("Id Category is required."));
        return Category.parser(
                categories.findById(id)
                        .orElseThrow(()-> new NotFoundException("Id Category not found.")));
    }


    public Page<Category> findAll(Category category, Pageable data){
        Page<CategoryEntity> page = categories.findAll(this.makeSpec(category), data);
        if (page.getTotalElements()==0){
            throw new NotFoundException("Search Category was not found.");
        }
        return new PageImpl<>(page.get().map(Category::parser).collect(Collectors.toList()),data,page.getTotalElements());
    }


    private Specification<CategoryEntity> makeSpec(Category account){
        List<Filter> filters = new ArrayList<>();
        SpecificationBuilder<CategoryEntity> specAccount = new SpecificationBuilder<>(CategoryEntity.class);
        if (Objects.nonNull(account.getName())){
            filters.add(Filter.builder()
                    .field("name")
                    .operator(QueryOperator.LIKE)
                    .value(String.valueOf(account.getName()))
                    .build());
        }

        return specAccount.getSpecificationFromFilters(filters);
    }
}
