package com.abcode.graphqlspring.product;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductInput {
    private String name;
    private String category;
    private double price;
    private int stock;

    // and Setters
}