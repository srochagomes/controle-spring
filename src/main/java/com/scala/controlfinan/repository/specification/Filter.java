package com.scala.controlfinan.repository.specification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Filter {
    private String field;
    private QueryOperator operator;
    private String value;
    private List<Object> values;//Used in case of IN operator
}
