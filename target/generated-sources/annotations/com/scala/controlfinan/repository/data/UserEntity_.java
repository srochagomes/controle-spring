package com.scala.controlfinan.repository.data;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserEntity.class)
public abstract class UserEntity_ {

	public static volatile SingularAttribute<UserEntity, String> name;
	public static volatile SingularAttribute<UserEntity, Integer> id;
	public static volatile SingularAttribute<UserEntity, String> email;

	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String EMAIL = "email";

}

