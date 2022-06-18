package mabaya.interview.dao;

import java.util.List;
import java.util.Optional;

import mabaya.interview.model.Product;


public interface ProductRepository/* extends JpaRepository<Product,Integer> */{
	
	List<Object[]> findProductWithGreatestBid(String category);
	
	List<Object[]> findProductWithGreatestBidOutOfDate(String category);
	
	boolean existsById(Integer serialNumber);

	Product save(Product product);

	Optional<Product> findById(Integer serialNumber);

	Product deleteProduct(Product book);
	
	
	
//
//	@Query("select p, c.bid, c.name from Product p join p.campaigns c where p.category=?1 and c.bid = (select MAX(c.bid) from c)")
//	public List<Product> findProductWithGreatestBid(String category);
//	
//	
//	@Query("select p , c.bid, c.name from Product p join p.campaignOOD c where p.category=?1 and c.bid = (select MAX(c.bid) from c)")
//	public List<Product> findProductWithGreatestBidOutOfDate(String category);
}