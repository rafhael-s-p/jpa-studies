package com.studies.ecommerce.service;

import com.studies.ecommerce.models.Product;
import com.studies.ecommerce.repository.Products;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class ProductService {

    @Autowired
    private Products products;

    @Transactional
    public Product save(Product product) {
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        return products.save(product);
    }

    @Transactional
    public Product update(Integer id, Map<String, Object> product) {
        Product currentProduct = products.find(id);

        try {
            BeanUtils.populate(currentProduct, product);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        currentProduct.setUpdatedAt(LocalDateTime.now());

        return products.save(currentProduct);
    }
}
