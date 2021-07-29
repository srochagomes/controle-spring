package com.scala.controlfinan.controller.categories;

import com.scala.controlfinan.config.GetCategoryParameterConfig;
import com.scala.controlfinan.domain.Account;
import com.scala.controlfinan.domain.Category;
import com.scala.controlfinan.service.accounts.SearchAccounts;
import com.scala.controlfinan.service.categories.SearchCategories;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@AllArgsConstructor
@RestController
public class GetCategory extends CategoriesRootController {

    private SearchCategories searchCategories;

    @GetMapping(value = "v1/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation("Search category by id")
    public ResponseEntity<Category> getAccount(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(searchCategories.findById(id));
    }

    @GetMapping(value = "v1", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation("Search All categories")
    @GetCategoryParameterConfig
    public ResponseEntity<Page<Category>> getAllAccount(
            @PageableDefault(sort = {"id"}, page = 0, size = 10) Pageable pageable,
            @RequestParam(value = "name", required = false) String name
    ) {

        Category categoryFilter = Category.builder().name(name).build();

        return ResponseEntity.ok(searchCategories.findAll(categoryFilter, pageable));
    }
}
