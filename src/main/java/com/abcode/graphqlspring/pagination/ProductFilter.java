package com.abcode.graphqlspring.pagination;

import lombok.Data;

@Data
public class ProductFilter {
    private String name;
    private String category;
    private Double minPrice;
    private Double maxPrice;
}