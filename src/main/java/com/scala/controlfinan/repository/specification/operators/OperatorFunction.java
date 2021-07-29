package com.scala.controlfinan.repository.specification.operators;

import com.scala.controlfinan.repository.specification.Filter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public abstract class OperatorFunction {
    public abstract <T> Specification<T> process(Class<T> c, Filter filter);

    protected Object castToRequiredType(Class fieldType, String value) {
        if(fieldType.isAssignableFrom(Double.class)) {
            return Double.valueOf(value);
        } else if(fieldType.isAssignableFrom(Integer.class)) {
            return Integer.valueOf(value);
        } else if(Enum.class.isAssignableFrom(fieldType)) {
            return Enum.valueOf(fieldType, value);
        }
        return null;
    }

    protected Object castToRequiredType(Class fieldType, List<String> value) {
        List<Object> lists = new ArrayList<>();
        for (String s : value) {
            lists.add(castToRequiredType(fieldType, s));
        }
        return lists;
    }

    public <T> Expression<?> navigate(Root<T> root, String field) {
        String[] split = field.split("\\.");
        Join<Object, Object> join = null;
        Path<Object> objectPath = null;
        for(int counter = 0; split.length>counter;counter++){

            if (counter==0){
                objectPath = root.get(split[counter]);
            }else if (counter>0){
                join = root.join(split[counter - 1]);
                objectPath = join.get(split[counter]);
            }
        }
        return objectPath;
    }



}
