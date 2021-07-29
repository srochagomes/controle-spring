package com.scala.controlfinan.repository;

import com.scala.controlfinan.repository.data.AccountEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface Accounts extends PagingAndSortingRepository<AccountEntity, Integer>, JpaSpecificationExecutor<AccountEntity> {
}
