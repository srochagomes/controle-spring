package com.scala.controlfinan.controller.categories;

import com.scala.controlfinan.domain.Category;
import com.scala.controlfinan.service.categories.CreateCategories;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@AllArgsConstructor
@RestController
public class PostCreateCategory extends CategoriesRootController {

    private CreateCategories createCategories;

    @ApiOperation("Create a category")
    @PostMapping(value = "/v1",produces = {MediaType.APPLICATION_JSON_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Category> createNewCategory(@Valid @RequestBody Category data) {
        Category categoryCreated = createCategories.process(data);
        final URI uri =
                MvcUriComponentsBuilder.fromController(getClass())
                        .path("/{id}")
                        .buildAndExpand(categoryCreated.getId())
                        .toUri();
        return ResponseEntity.created(uri).body(categoryCreated);
    }
}
