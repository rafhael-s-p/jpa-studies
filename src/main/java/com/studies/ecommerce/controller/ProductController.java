package com.studies.ecommerce.controller;

import com.studies.ecommerce.models.Product;
import com.studies.ecommerce.repository.Products;
import com.studies.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private Products products;

    @Autowired
    private ProductService service;

    @PostMapping("/{id}/update")
    public ModelAndView update(@RequestAttribute String tenant,
                               @PathVariable Integer id,
                               @RequestParam Map<String, Object> product,
                               RedirectAttributes redirectAttributes) {
        service.update(id, tenant, product);

        redirectAttributes.addFlashAttribute("message", "Update done successfully!");

        return new ModelAndView("redirect:/products/{id}/update");
    }

    @GetMapping("/{id}/update")
    public ModelAndView update(@RequestAttribute String tenant,
                               @PathVariable Integer id) {
        return newProduct(products.find(id, tenant));
    }

    @PostMapping("/new")
    public ModelAndView save(@RequestAttribute String tenant,
                             Product product,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        Product updatedProduct = service.save(tenant, product);

        redirectAttributes.addFlashAttribute("message", "Registration created successfully!");

        return new ModelAndView("redirect:/products/{id}/update", "id", updatedProduct.getId());
    }

    @GetMapping("/new")
    public ModelAndView newProduct(Product product) {
        ModelAndView mv = new ModelAndView("products/products-form");
        mv.addObject("product", product);

        return mv;
    }

    @GetMapping
    public ModelAndView list(@RequestAttribute String tenant) {
        ModelAndView mv = new ModelAndView("products/products-list");
        mv.addObject("products", products.list(tenant));

        return mv;
    }
}
