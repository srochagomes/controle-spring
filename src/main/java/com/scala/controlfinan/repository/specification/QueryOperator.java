package com.scala.controlfinan.repository.specification;

import com.scala.controlfinan.repository.specification.operators.BetweenLocalDate;
import com.scala.controlfinan.repository.specification.operators.Equals;
import com.scala.controlfinan.repository.specification.operators.GreaterThan;
import com.scala.controlfinan.repository.specification.operators.LessThan;
import com.scala.controlfinan.repository.specification.operators.Like;
import com.scala.controlfinan.repository.specification.operators.NotEquals;
import com.scala.controlfinan.repository.specification.operators.OperatorFunction;
import org.springframework.data.jpa.domain.Specification;

public enum QueryOperator  {
    EQUAL(Equals.class),
    NOT_EQUAL(NotEquals.class),
    LESS_THAN(LessThan.class),
    GREATER_THAN(GreaterThan.class),
    BETWEEN_DATE(BetweenLocalDate.class),
    LIKE(Like.class)
    ;


    QueryOperator(Class<? extends OperatorFunction> c){
        this.clazz = c;
    }

    private Class<? extends OperatorFunction> clazz;

    public <Z> Specification<Z> createSpec(Class<Z> c,Filter filter){
        try{
            return (this.clazz.getDeclaredConstructor().newInstance()).process(c,filter);
        }catch (Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }

    }

}
