package com.scala.controlfinan.repository.specification.operators;

import com.scala.controlfinan.repository.specification.Filter;
import org.springframework.data.jpa.domain.Specification;

public class LessThan extends OperatorFunction{
    @Override
    public <T> Specification<T> process(Class<T> c, Filter filter) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lt(root.get(filter.getField()),
                        (Number) castToRequiredType(
                                root.get(filter.getField()).getJavaType(),
                                filter.getValue()));
    }

}
