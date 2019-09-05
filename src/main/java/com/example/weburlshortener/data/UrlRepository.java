package com.example.weburlshortener.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.weburlshortener.model.Url;

@Repository
public interface UrlRepository extends JpaRepository<Url, Integer> {

	List<Url> findByAccountId(int id);
}
