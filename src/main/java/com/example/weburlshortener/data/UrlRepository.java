package com.example.weburlshortener.data;

import java.util.List;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import com.example.weburlshortener.model.Url;

@Repository
public interface UrlRepository extends JpaRepository<Url, Integer> 
{

	List<Url> findByAccountId(int accountId);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "5000")})
	@Query("select u from Url u where u.shortKey = :shortKey")
	Url findByShortKeyForWrite(String shortKey);
	

}
