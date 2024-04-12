package kodlama.io.ecommerce.repository.concretes;

import kodlama.io.ecommerce.entities.concretes.Product;
import kodlama.io.ecommerce.repository.abstracts.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryProductRepository implements ProductRepository {

    List<Product> products;

    public InMemoryProductRepository() {
        products = new ArrayList<>();

        products.add(new Product(1,"iPhone 15", 10, 29999.99, "Apple Smart Phone"));
        products.add(new Product(2,"Macbook Air", 10, 27000.99, "Apple Laptop"));
        products.add(new Product(3,"Xbox", 5, 20000.99, "Microsoft game console"));
        products.add(new Product(4,"PS5", 7, 29999.99, "Sony game console"));
        products.add(new Product(5,"Dyson V15", 8, 29999.99, "Dyson vacuum cleaner"));

    }

    @Override
    public List<Product> getAll() {
        return products;
    }

    @Override
    public Product getById(int id) {
        return products.get(id-1);
    }

    @Override
    public Product add(Product product) {
        products.add(product);
        return product;
    }

    @Override
    public Product update(int id, Product product) {
        products.set(id, product);
        return product;
    }

    @Override
    public void delete(int id) {
        products.remove(id);
    }
}
