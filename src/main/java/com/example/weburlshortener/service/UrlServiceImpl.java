package com.example.weburlshortener.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.weburlshortener.model.Url;
import com.example.weburlshortener.repository.UrlRepository;
import com.example.weburlshortener.util.Utils;

@Service
public class UrlServiceImpl implements UrlService {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	protected UrlRepository repo;
	
	public Url createUrl(int accountId, String address, int redirectType) {
		
		Url url = new Url(accountId, address, redirectType, Utils.getRandomStringForUrlShortKey(), null);

		return this.repo.save(url);
	}

	public List<Url> getUrlsByAccountId(int accountId) {
		
		List<Url> urls = this.repo.findByAccountId(accountId);
		
		return urls;
	}
	
	public List<Url> getAllUrls() {
		List<Url> urls = this.repo.findAll();
		
		return urls;
	}
	
	/**
	 * Utilize 'PESSIMISTIC_WRITE' lock so that all other write operations must wait
	 * until we finish reading, updating and persisting the url.
	 */
	@Transactional
	public Url getUrlByShortKeyAndIncrementTopHits(String shortKey) {
		Url url = null;
		
		try {
			url = this.repo.findByShortKeyForWrite(shortKey);
			
			if (url != null) {
				url.setNumOfHits(url.getNumOfHits() + 1);
				this.repo.save(url);
			}
		}
		// Catch PessimisticLockException (if we could not acquire the lock),
		// or any other exception type
		catch(Exception e) {
			log.error(e.getMessage());
			url = null;
		}
		
		return url;
	}
	
	
 
}
