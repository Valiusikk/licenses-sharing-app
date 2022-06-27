package com.endava.license.service;

import com.endava.license.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;


public interface ProductService {
    /**
     * Should return Product entity, if product with such name is stored in Database
     * Otherwise throw LicenseException
     *
     * @param productName name of Product
     * @return Instance of ProductEntity
     */
    Page<ProductDto> getProductByName(String productName, int page, int limit, String sortedBy, Sort.Direction order);

    /**
     * Should return Page with certain number of products, defined by @param limit, sorted by chosen key in chosen order
     *
     * @param page zero-based page index, must not be negative.
     * @param limit number of products that will be displayed on one page, must be greater than 0
     * @param sortedBy param that is sort-key for sorting, can be 'productName' or 'status'
     * @param order sorting order, can be only 'ASC' or 'DESC'
     * @return
     */
    Page<ProductDto> getAllProducts(int page, int limit, String sortedBy, Sort.Direction order);
    ProductDto addProduct(ProductDto product);
}
