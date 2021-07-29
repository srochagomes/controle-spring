package com.scala.controlfinan.repository.data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TransactionEntity.class)
public abstract class TransactionEntity_ {

	public static volatile SingularAttribute<TransactionEntity, LocalDateTime> createdAt;
	public static volatile SingularAttribute<TransactionEntity, Integer> id;
	public static volatile SingularAttribute<TransactionEntity, CategoryEntity> category;
	public static volatile SingularAttribute<TransactionEntity, BigDecimal> value;
	public static volatile SingularAttribute<TransactionEntity, AccountEntity> account;

	public static final String CREATED_AT = "createdAt";
	public static final String ID = "id";
	public static final String CATEGORY = "category";
	public static final String VALUE = "value";
	public static final String ACCOUNT = "account";

}

