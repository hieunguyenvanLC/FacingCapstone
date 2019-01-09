package group.demo.service;

import group.demo.entity.Product;
import group.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        List<Product> products = productRepository.findByStatus(1);
        return products;
    }

    public void createProduct(String name , Double price) {
        Product productEntity = new Product();
        productEntity.setName(name);
        productEntity.setPrice(price);
        productEntity.setStatus(1);
        productRepository.save(productEntity);
    }


    public void updateProduct(Integer editProductId, String name , Double price) {
        Product productEntity = productRepository.findById(editProductId).get();
        productEntity.setName(name);
        productEntity.setPrice(price);
        productRepository.save(productEntity);
    }

    public void hideProduct(Integer deleteProductId) {
        Product productEntity = productRepository.findById(deleteProductId).get();
        productEntity.setStatus(-1);
        productRepository.save(productEntity);
    }
}
