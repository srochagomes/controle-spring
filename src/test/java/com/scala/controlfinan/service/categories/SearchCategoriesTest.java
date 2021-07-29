package com.scala.controlfinan.service.categories;

import com.scala.controlfinan.domain.Category;
import com.scala.controlfinan.domain.CategoryType;
import com.scala.controlfinan.repository.Categories;
import com.scala.controlfinan.repository.Users;
import com.scala.controlfinan.repository.data.CategoryEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@Slf4j
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class SearchCategoriesTest {
    @Mock
    private Users users;

    @Mock
    private Categories categories;

    private SearchCategories searchCategories;


    @BeforeEach
    void init() {

        log.info("@BeforeEach - executes before each test method in this class");
        searchCategories = new SearchCategories(categories);

    }


    @Test
    @DisplayName("Test when Pass Null id category Should Throw NotFound exception")
    public void whenPassNullCategoryShouldThrowException() {
        Integer id = null;
        var exception = assertThrows(BadRequestException.class, () -> searchCategories.findById(id));
        assertEquals("Id Category is required.",exception.getMessage());

    }

    @Test
    @DisplayName("Test when Pass id category an not found Should Throw NotFound exception")
    public void whenPassIdCategoryAndNotFoundShouldThrowException() {
        Integer id = 5;
        var exception = assertThrows(NotFoundException.class, () -> searchCategories.findById(id));
        assertEquals("Id Category not found.",exception.getMessage());

    }

    @Test
    @DisplayName("Test when Pass id category an found Should Throw NotFound exception")
    public void whenPassIdCategoryAndFoundShouldThrowException() {
        Integer id = 5;
        CategoryEntity categoryParameter = CategoryEntity.builder()
                .id(5)
                .name("Teste")
                .type(CategoryType.ENTRADA)
                .build();
        when(categories.findById(any())).thenReturn(Optional.of(categoryParameter));
        Category categoryFound = searchCategories.findById(id);
        assertNotNull(categoryFound);

        assertEquals(id,categoryFound.getId());

    }

    @Test
    @DisplayName("Test when Search all NotFound exception")
    public void whenSearchNotFoundShouldThrowException() {
        Integer id = 5;
        Page<CategoryEntity> page = Mockito.mock(Page.class);
        Pageable pageable = Mockito.mock(Pageable.class);

        when(categories.findAll(any(),any(Pageable.class))).thenReturn(page);
        when(page.getTotalElements()).thenReturn(0l);
        var exception = assertThrows(NotFoundException.class, () -> searchCategories.findAll(Category.builder().build(),pageable));

        assertEquals("Search Category was not found.",exception.getMessage());
    }

    @Test
    @DisplayName("Test when Search all and exists")
    public void whenSearchFoundShouldThrowException() {
        Integer id = 5;
        Page<CategoryEntity> page = Mockito.mock(Page.class);

        when(categories.findAll(any(),any(Pageable.class))).thenReturn(page);
        when(page.getTotalElements()).thenReturn(1l);
        Pageable pageable = Mockito.mock(Pageable.class);
        Stream<CategoryEntity> categoryEntities = Stream.of(CategoryEntity.builder()
                .id(5)
                .name("teste")
                .type(CategoryType.ENTRADA)
                .build());
        when(page.get()).thenReturn(categoryEntities);

        Page<Category> all = searchCategories.findAll(Category.builder().build(), pageable);

        assertNotNull(all);
        assertEquals(1,all.getContent().size());
    }

}