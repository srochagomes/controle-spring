package com.scala.controlfinan.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.scala.controlfinan.repository.data.TransactionEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Transaction {

    @ApiModelProperty(hidden = true)
    private Integer id;

    @NotNull(message = "Account is required.")
    private Integer accountId;

    @NotNull(message = "Value is required.")
    private BigDecimal value;

    @ApiModelProperty(hidden = true)
    private LocalDateTime createdAt;

    @NotNull(message = "Category is required.")
    private Integer categoryId;

    @ApiModelProperty(hidden = true)
    private LocalDateTime startedAt;

    @ApiModelProperty(hidden = true)
    private LocalDateTime endedAt;

    public static Transaction parser(TransactionEntity transaction) {
        return Transaction.builder()
                .id(transaction.getId())
                .accountId(transaction.getAccount().getId())
                .categoryId(transaction.getCategory().getId())
                .value(transaction.getValue())
                .createdAt(transaction.getCreatedAt())
                .build();
    }
}
