package com.systex.project.model;

import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface AccountRepository extends JpaRepository <Account, Integer>  {
	@Query("SELECT a FROM Account a WHERE a.account = :account")
    Optional<Account> findByAccount(String account);
}
