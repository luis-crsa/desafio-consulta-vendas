package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.projections.SellerSummaryProjection;

public class SellerSummaryDTO {

    String sellerName;
    Double total;
    
    public SellerSummaryDTO(String sellerName, Double total) {
        this.sellerName = sellerName;
        this.total = total;
    }

    public SellerSummaryDTO(SellerSummaryProjection projection) {
        sellerName = projection.getSellerName();
        total = projection.getTotal();
    }

    public String getSellerName() {
        return sellerName;
    }

    public Double getTotal() {
        return total;
    }
}
