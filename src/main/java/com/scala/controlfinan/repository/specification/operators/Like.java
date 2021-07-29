package com.scala.controlfinan.repository.specification.operators;

import com.scala.controlfinan.repository.specification.Filter;
import org.springframework.data.jpa.domain.Specification;

public class Like extends OperatorFunction{
    @Override
    public <T> Specification<T> process(Class<T> c, Filter filter) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get(filter.getField()),
                        "%"+filter.getValue()+"%");
    }

}
