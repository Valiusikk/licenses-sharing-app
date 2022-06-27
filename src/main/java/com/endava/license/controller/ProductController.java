package com.endava.license.controller;

import static com.endava.license.security.Roles.Fields.ADMIN;
import static com.endava.license.security.Roles.Fields.USER;

import com.endava.license.dto.ProductDto;
import com.endava.license.service.ProductService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @RolesAllowed(ADMIN)
    public ResponseEntity<ProductDto> addProduct(
        @Parameter(required = true, schema = @Schema(implementation = ProductDto.class))
        @Valid @RequestBody ProductDto productDto
    ) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(productService.addProduct(productDto));
    }

    @GetMapping("/{productName}")
    @RolesAllowed({USER,ADMIN})
    public ResponseEntity<Page<ProductDto>> getProduct(
            @PathVariable String productName,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "limit", defaultValue = "10") int limit,
            @RequestParam(name = "sort", defaultValue = "productName") String sort,
            @RequestParam(name = "order", defaultValue = "ASC") Sort.Direction order
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.getProductByName(productName, page, limit, sort, order));
    }

    @GetMapping()
    @RolesAllowed({USER,ADMIN})
    public ResponseEntity<Page<ProductDto>> getAllProducts(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "limit", defaultValue = "10") int limit,
            @RequestParam(name = "sort", defaultValue = "productName") String sort,
            @RequestParam(name = "order", defaultValue = "ASC") Sort.Direction order
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.getAllProducts(page, limit, sort, order));
    }

}
