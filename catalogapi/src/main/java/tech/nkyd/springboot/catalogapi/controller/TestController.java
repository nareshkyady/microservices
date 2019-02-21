package tech.nkyd.springboot.catalogapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tech.nkyd.springboot.catalogapi.entities.Product;
import tech.nkyd.springboot.catalogapi.repositories.ProductRepository;


@RestController
public class TestController {

	@RequestMapping("/")
	public String home() {
		StringBuffer message = new StringBuffer();
		message.append("Hey, spring boot application is up and running");
		message.append("\n");
		message.append("api supported as of now /catalog/products");
		return message.toString();
	}
	@Autowired
    private ProductRepository productRepository;

    @GetMapping("/catalog/products")
    public List<Product>  getAllProducts() {	
       return productRepository.findAll();
    }
    @PostMapping("/catalog/products")
    public void createProduct(@RequestBody Product product) {
    	productRepository.saveProduct(product);
    }
    @DeleteMapping("/catalog/products/{id}")
    public Product deleteProduct(@PathVariable String id) {
        return productRepository.deleteById(id);
    }
    @GetMapping("/catalog/products/{id}")
    public Product getProduct(@PathVariable String id) {
        return productRepository.findById(id);
    }
}