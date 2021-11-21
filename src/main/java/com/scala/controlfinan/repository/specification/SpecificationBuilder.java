package com.scala.controlfinan.repository.specification;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.util.List;

@AllArgsConstructor
public class SpecificationBuilder<T>{
    private Class<T> clazz;


    public Specification<T> getSpecificationFromFilters(List<Filter> filter){
        if (ObjectUtils.isEmpty(filter)){
            return null;
        }
        Specification<T> specification =
                Specification.where(createSpecification(filter.remove(0)));
        for (Filter input : filter) {
            specification = specification.and(createSpecification(input));
        }
        return specification;
    }

    private Specification<T> createSpecification(Filter input) {
        return input.getOperator().createSpec(this.clazz,input);
    }

}
