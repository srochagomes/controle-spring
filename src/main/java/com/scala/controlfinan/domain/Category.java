package com.scala.controlfinan.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.scala.controlfinan.repository.data.CategoryEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Category {

    @ApiModelProperty(hidden = true)
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private CategoryType type;

    public static Category parser(CategoryEntity category) {
        if(ObjectUtils.isEmpty(category)){
            return Category.builder().build();
        }
        return Category.builder()
                .id(category.getId())
                .name(category.getName())
                .type(category.getType())
                .build();
    }
}
