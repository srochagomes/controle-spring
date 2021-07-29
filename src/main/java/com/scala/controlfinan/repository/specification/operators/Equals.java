package com.scala.controlfinan.repository.specification.operators;

import com.scala.controlfinan.repository.specification.Filter;
import org.springframework.data.jpa.domain.Specification;

public class Equals extends OperatorFunction{
    @Override
    public <T> Specification<T> process(Class<T> c, Filter filter) {

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(navigate(root,filter.getField()),
                        castToRequiredType(navigate(root,filter.getField()).getJavaType(),
                                filter.getValue()));
    }


}
