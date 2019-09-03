package com.example.weburlshortener.data;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.weburlshortener.model.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {

}
