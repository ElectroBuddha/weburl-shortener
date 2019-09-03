package com.example.weburlshortener.data;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.weburlshortener.model.Url;

public interface UrlRepository extends JpaRepository<Url, Integer> {

}
