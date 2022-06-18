package mabaya.interview.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import mabaya.interview.model.Product;
@Repository
public class ProductRepositoryImpl implements ProductRepository {
	@PersistenceContext
	EntityManager em;
	
	    public List<Object[]> findProductWithGreatestBid(String category) {
	        Query query = em
	          .createQuery("select p, c.bid, c.name from Product p join p.campaigns c where p.category=:category and c.bid = (select MAX(c.bid) from c)");
	        query.setParameter("category", category);
	        return query.getResultList();
	    }
	    
	    public List<Object[]> findProductWithGreatestBidOutOfDate(String category) {
	        Query query = em
	          .createQuery("select p , c.bid, c.name from Product p join p.campaignOOD c where p.category=:category and c.bid = (select MAX(c.bid) from c)");
	        query.setParameter("category", category);
	        return query.getResultList();
	    }

		@Override
		public boolean existsById(Integer serialNumber) {
			Product product = em.find(Product.class, serialNumber);
			return product != null;
		}

		@Override
		public Product save(Product product) {
			if(!existsById(product.getSerialNumber()))
			em.persist(product);
			return product;
		}

		@Override
		public Optional<Product> findById(Integer serialNumber) {
			Product product = em.find(Product.class, serialNumber);
			return Optional.ofNullable(product);
		}

		@Override
		public Product deleteProduct(Product product) {
			em.remove(product);
			return product;
		}
}
