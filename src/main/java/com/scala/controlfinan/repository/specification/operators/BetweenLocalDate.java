package com.scala.controlfinan.repository.specification.operators;

import com.scala.controlfinan.repository.specification.Filter;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class BetweenLocalDate extends OperatorFunction{
    public <T> Specification<T> process(Class<T> c, Filter filter) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.between(root.get(filter.getField()),
                    (LocalDateTime)filter.getValues().get(0),
                    (LocalDateTime) filter.getValues().get(1));
        };
    }

}
