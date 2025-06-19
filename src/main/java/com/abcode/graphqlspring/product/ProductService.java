package com.abcode.graphqlspring.product;


import com.abcode.graphqlspring.pagination.PaginationRequest;
import com.abcode.graphqlspring.pagination.PaginationResponse;
import com.abcode.graphqlspring.pagination.ProductFilter;
import com.abcode.graphqlspring.pagination.ProductSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private  final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public PaginationResponse<Product> getProducts(ProductFilter filter, PaginationRequest pagination) {
        Pageable pageable = PageRequest.of(pagination.getPage(), pagination.getSize());
        Specification<Product> spec = ProductSpecification.withFilters(filter != null ? filter : new ProductFilter());
        Page<Product> page = productRepository.findAll(spec, pageable);

        return new PaginationResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateStock(int id, int quantity) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        product.setStock(product.getStock() - quantity);
        return productRepository.save(product);
    }

    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }
}
