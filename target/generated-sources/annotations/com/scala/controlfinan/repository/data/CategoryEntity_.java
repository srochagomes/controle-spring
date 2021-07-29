package com.scala.controlfinan.repository.data;

import com.scala.controlfinan.domain.CategoryType;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CategoryEntity.class)
public abstract class CategoryEntity_ {

	public static volatile SingularAttribute<CategoryEntity, String> name;
	public static volatile SingularAttribute<CategoryEntity, Integer> id;
	public static volatile SingularAttribute<CategoryEntity, CategoryType> type;

	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String TYPE = "type";

}

