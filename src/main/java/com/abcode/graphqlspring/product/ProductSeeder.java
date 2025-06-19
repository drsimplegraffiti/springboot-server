package com.abcode.graphqlspring.product;


import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ProductSeeder {

    private final ProductRepository productRepository;

    public ProductSeeder(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostConstruct
    public void initDb(){
        List<Product> products = Stream.of(
                Product.builder().name("Laptop").category("Electronics").price(999.99f).stock(50).build(),
                Product.builder().name("Smartphone").category("Electronics").price(499.99f).stock(100).build(),
                Product.builder().name("Headphones").category("Accessories").price(199.99f).stock(200).build(),
                Product.builder().name("Coffee Maker").category("Home Appliances").price(79.99f).stock(30).build(),
                Product.builder().name("Blender").category("Home Appliances").price(49.99f).stock(80).build()
        ).toList();
        List<Product> existingProducts = productRepository.findAll();

        if (existingProducts.isEmpty()) {
            productRepository.saveAll(products);
            System.out.println("Database seeded with initial products.");
        } else {
            System.out.println("Database already contains products, skipping seeding.");
        }
    }
}
