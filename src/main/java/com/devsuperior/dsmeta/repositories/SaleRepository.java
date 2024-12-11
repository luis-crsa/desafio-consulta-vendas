package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projections.SellerSummaryProjection;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT obj " +
        "FROM Sale obj " +
        "WHERE obj.date BETWEEN :minDate AND :maxDate " +
        "AND " +
        "UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :name, '%'))")
    Page<Sale> searchReport(LocalDate minDate, LocalDate maxDate, String name, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT sel.name AS sellerName, SUM(sal.amount) AS total " +
            "FROM tb_seller sel  " +
            "INNER JOIN tb_sales sal ON sel.id = sal.seller_id " +
            "WHERE sal.date BETWEEN :minDate AND :maxDate " +
            "GROUP BY sel.name ")
    Page<SellerSummaryProjection> searchSummary(LocalDate minDate, LocalDate maxDate, Pageable pageable);
}
