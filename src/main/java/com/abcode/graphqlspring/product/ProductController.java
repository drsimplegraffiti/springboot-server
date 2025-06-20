package com.abcode.graphqlspring.product;


import com.abcode.graphqlspring.pagination.PaginationRequest;
import com.abcode.graphqlspring.pagination.PaginationResponse;
import com.abcode.graphqlspring.pagination.ProductFilter;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @MutationMapping
    public Boolean uploadFiles(@Argument List<MultipartFile> files) throws IOException {
        for (MultipartFile file : files) {
            Path path = Paths.get(System.getProperty("java.io.tmpdir"), file.getOriginalFilename());
            Files.write(path, file.getBytes());
        }
        return true;
    }

    @MutationMapping
    public Boolean uploadFile(@Argument MultipartFile file) throws IOException {
        try {
            String filename = file.getOriginalFilename();
            byte[] bytes = file.getBytes();

            Path path = Paths.get(System.getProperty("java.io.tmpdir"), filename);
            Files.write(path, bytes);

            System.out.println("File saved to: " + path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @PreAuthorize("isAuthenticated()") //@PreAuthorize("hasRole('ADMIN')")
    @QueryMapping(value = "getProducts")
    public List<Product> getProducts() {
        return productService.getProducts();
    }



    @PreAuthorize("isAuthenticated()")
    @QueryMapping
    public PaginationResponse<Product> getProductsPaginated(@Argument ProductFilter filter,
                                                   @Argument PaginationRequest pagination) {
        return productService.getProducts(filter, pagination);
    }

    @QueryMapping
    public List<Product> getProductsByCategory(@Argument String category) {
        return productService.getProductsByCategory(category);
    }

    @MutationMapping
    public Product saveProduct(@Argument ProductInput input) {
        Product product = new Product(
                input.getName(),
                input.getCategory(),
                (float) input.getPrice(),
                input.getStock()
        );
        return productService.saveProduct(product);
    }

    @MutationMapping
    public Product updateStock(@Argument int id, @Argument int quantity) {
        return productService.updateStock(id, quantity);
    }

    @MutationMapping
    public Boolean deleteProduct(@Argument int id) {
        productService.deleteProduct(id);
        return true;
    }

}
