package com.scala.controlfinan.domain;

import java.math.BigDecimal;

public enum CategoryType {
    ENTRADA(1,1,0),
    SAIDA(-1,0,1);

    CategoryType(int optBl,int optIn,int optEx){
        this.operationBalance = optBl;
        this.operationIncome = optIn ;
        this.operationExpense = optEx;
    }

    private int operationBalance;
    private int operationIncome;
    private int operationExpense;

    public BigDecimal calculateBalance(BigDecimal value){
        return value.multiply(BigDecimal.valueOf(this.operationBalance));
    }

    public BigDecimal calculateIncome(BigDecimal value){
        return value.multiply(BigDecimal.valueOf(this.operationIncome));
    }

    public BigDecimal calculateExpense(BigDecimal value){
        return value.multiply(BigDecimal.valueOf(this.operationExpense));
    }


}
