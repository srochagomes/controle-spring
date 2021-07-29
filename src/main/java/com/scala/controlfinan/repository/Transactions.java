package com.scala.controlfinan.repository;

import com.scala.controlfinan.repository.data.TransactionEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Transactions extends CrudRepository<TransactionEntity,Integer>, JpaSpecificationExecutor<TransactionEntity> {
}
