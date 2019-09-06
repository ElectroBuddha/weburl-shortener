package com.example.weburlshortener.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.weburlshortener.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

	Account findByUsername(String username);
}
