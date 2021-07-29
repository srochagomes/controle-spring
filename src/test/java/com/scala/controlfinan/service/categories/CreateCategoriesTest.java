package com.scala.controlfinan.service.categories;

import com.scala.controlfinan.domain.Category;
import com.scala.controlfinan.domain.CategoryType;
import com.scala.controlfinan.repository.Categories;
import com.scala.controlfinan.service.categories.CreateCategories;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;


@Slf4j
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class CreateCategoriesTest {

    private CreateCategories createCategories;

    @Mock
    private Categories categories;

    @BeforeEach
    void init() {

        log.info("@BeforeEach - executes before each test method in this class");
        createCategories = new CreateCategories(categories);

    }

    @Test
    @DisplayName("Test when Pass Null Category Should Throw NotFound exception")
    public void whenPassNullCategoryShouldThrowException(){
        Category category = null;
        var exception = assertThrows(NotFoundException.class, () -> createCategories.process(category));
        assertEquals("Main Object is null.",exception.getMessage());
    }

    @Test
    @DisplayName("Test when Pass Null Name Category Should Throw NotFound exception")
    public void whenPassNullNameCategoryShouldThrowException(){
        Category category = Category.builder().build();
        var exception = assertThrows(BadRequestException.class, () -> createCategories.process(category));
        assertEquals("Name Category is required.",exception.getMessage());
    }

    @Test
    @DisplayName("Test when Pass Null Type Category Should Throw NotFound exception")
    public void whenPassNullTypeCategoryShouldThrowException(){
        Category category = Category.builder().name("teste").build();
        var exception = assertThrows(BadRequestException.class, () -> createCategories.process(category));
        assertEquals("Type Category is required.",exception.getMessage());
    }

    @Test
    @DisplayName("Test when Pass all requirements")
    public void whenPassAllRequiremets(){
        Category category = Category.builder().name("teste").type(CategoryType.ENTRADA).build();
        Category process = createCategories.process(category);
        then(categories).should(times(1)).save(any());
        assertNotNull(process);

    }

}