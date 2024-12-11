package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SellerSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projections.SellerSummaryProjection;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<SaleMinDTO> getReport(String minDate, String maxDate, String name, Pageable pageable) {
		LocalDate max;
		LocalDate min;

		if (maxDate.isEmpty()) {
			max = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		}else{
			max = LocalDate.parse(maxDate);
		}	
		
		if (minDate.isEmpty()) {
			min = max.minusYears(1L);
		}else{
			min = LocalDate.parse(minDate);
		}

		Page<Sale> result = repository.searchReport(min, max, name, pageable);
		Page<SaleMinDTO> list = result.map(x -> new SaleMinDTO(x));
		return list;
	}

	public Page<SellerSummaryDTO> getSummary(String minDate, String maxDate, Pageable pageable) {
		LocalDate max;
		LocalDate min;

		if (maxDate.isEmpty()) {
			max = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		}else{
			max = LocalDate.parse(maxDate);
		}	
		
		if (minDate.isEmpty()) {
			min = max.minusYears(1L);
		}else{
			min = LocalDate.parse(minDate);
		}

		Page<SellerSummaryProjection> result = repository.searchSummary(min, max, pageable);
		Page<SellerSummaryDTO> list = result.map(x -> new SellerSummaryDTO(x));
		return list;
	}
}
