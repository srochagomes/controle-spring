package com.scala.controlfinan.repository;

import com.scala.controlfinan.repository.data.UserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Users extends PagingAndSortingRepository<UserEntity, Integer> {
}
