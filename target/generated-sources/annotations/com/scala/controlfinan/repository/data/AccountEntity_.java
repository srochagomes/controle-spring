package com.scala.controlfinan.repository.data;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AccountEntity.class)
public abstract class AccountEntity_ {

	public static volatile SingularAttribute<AccountEntity, BigDecimal> income;
	public static volatile SingularAttribute<AccountEntity, BigDecimal> balance;
	public static volatile SingularAttribute<AccountEntity, Integer> id;
	public static volatile SingularAttribute<AccountEntity, BigDecimal> expense;
	public static volatile SingularAttribute<AccountEntity, UserEntity> user;

	public static final String INCOME = "income";
	public static final String BALANCE = "balance";
	public static final String ID = "id";
	public static final String EXPENSE = "expense";
	public static final String USER = "user";

}

