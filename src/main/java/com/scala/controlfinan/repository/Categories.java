package com.scala.controlfinan.repository;

import com.scala.controlfinan.repository.data.CategoryEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Categories extends CrudRepository<CategoryEntity,Integer>, JpaSpecificationExecutor<CategoryEntity> {
}
