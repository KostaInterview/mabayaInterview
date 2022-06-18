package mabaya.interview.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mabaya.interview.dao.CampaignOODRepository;
import mabaya.interview.dao.CampaignRepository;
import mabaya.interview.dao.ProductRepository;
import mabaya.interview.dto.CampaignDto;
import mabaya.interview.dto.ProductDto;
import mabaya.interview.dto.ProductResponceDto;
import mabaya.interview.exceptions.CampaignNameAlreadyExists;
import mabaya.interview.exceptions.NoSuchCategoryException;
import mabaya.interview.model.Campaign;
import mabaya.interview.model.CampaignOOD;
import mabaya.interview.model.Product;

@Service
@EnableScheduling
public class PromoteServiceImpl implements PromoteService {
	
	private static final Logger logger = LoggerFactory.getLogger(PromoteServiceImpl.class);
	
	@Value(value = "${campaign.duration.days}")
	private long amount;
	
	CampaignRepository campaignRepository;

	ProductRepository productRepository;
	
	CampaignOODRepository campaignOODRepository;
		
	ModelMapper modelMapper;
	
	@Autowired
	public PromoteServiceImpl(CampaignRepository campaignRepository, ProductRepository productRepository,CampaignOODRepository campaignOODRepository,
			ModelMapper modelMapper) {
		this.campaignRepository = campaignRepository;
		this.productRepository = productRepository;
		this.campaignOODRepository = campaignOODRepository;
		this.modelMapper = modelMapper;
	}

	@Transactional
	@Override
	public CampaignDto createCampaign(CampaignDto campaignDto){
		if(campaignRepository.existsById(campaignDto.getName()) || campaignOODRepository.existsById(campaignDto.getName())) {
			logger.info(campaignDto.getName() + " Already exists in DB");
			throw new CampaignNameAlreadyExists(campaignDto.getName());
		}
		
		Set<Product> products = campaignDto.getProductsToPromote().stream()
																   .map(prod -> productRepository.findById(prod.getSerialNumber())
																		   .orElse(productRepository.save(Product.builder()
																				   								 .serialNumber(prod.getSerialNumber())
																				   							     .title(prod.getTitle())
																				   							     .category(prod.getCategory())
																				   							     .price(prod.getPrice())
																				   							     .build())))
				
																   .collect(Collectors.toSet());
		
		Campaign campaign =new Campaign(campaignDto.getName(), campaignDto.getStartDate(), products, campaignDto.getBid());
		 
		campaign = campaignRepository.save(campaign);
		CampaignDto campaignResp =new CampaignDto(campaign.getName()
												 ,campaign.getStartDate()
												 ,campaign.getIdentifiersToPromote().stream()
												 										.map(prod -> modelMapper.map(prod,ProductDto.class))
												 										.collect(Collectors.toList())
												 ,campaign.getBid());
		
		return campaignResp;
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public ProductResponceDto serveAd(String category) {

		List<Object[]> productDetails = productRepository.findProductWithGreatestBid(category);
		if(productDetails == null) {
			productDetails = productRepository.findProductWithGreatestBidOutOfDate(category);
			if(productDetails == null) {
			logger.info(category + " category does not exists in DB");
			throw new NoSuchCategoryException(category);
			}
		}

		
			Product product = (Product) productDetails.get(0)[0];
		    BigDecimal bid = (BigDecimal) productDetails.get(0)[1];
		    String campaignName = (String) productDetails.get(0)[2];
		
//		new Product(serialNumber, title, category, price, campaigns, campaignOOD)
		 
		return new ProductResponceDto(product.getSerialNumber(), product.getTitle(), product.getCategory(), product.getPrice(), campaignName, bid);	}
	
//	@Override
//	@Transactional(readOnly = true)
//	public ProductResponceDto serveAd(String category) {
//		Product product = productRepository.findProductWithGreatestBid(category).get(0);
//		if(product == null) {
//			product = productRepository.findProductWithGreatestBidOutOfDate(category).get(0);
//			if(product == null) {
//			logger.info(category + " category does not exists in DB");
//			throw new NoSuchCategoryException(category);
//			}
//		}
//		ProductResponceDto productResponceDto = modelMapper.map(product, ProductResponceDto.class);
////Haven't  understood why only Product but not with Campaign.name with Campaign.bit Thats  why have made such DTO
//		return productResponceDto;
//      }
	
	
//Every two minutes to demonstrate method	
	@Scheduled(cron = "2 * * * * *")
	@Transactional
	public void deleteOutOfPeriodCampaigns() {
		
		LocalDate date = LocalDate.now();
		System.out.println(date);
		List<Campaign> campaigns = (List<Campaign>) campaignRepository.findByStartDateBefore(date.minusDays(amount));
		if(campaigns.isEmpty() || campaigns == null) {
			logger.info("All campaigns is up to date");
			return;
		}

		campaigns.stream().map(cam -> new CampaignOOD(cam.getName(), cam.getStartDate(),cam.getIdentifiersToPromote(), cam.getBid()))
						  .forEach(cam -> campaignOODRepository.save(cam));
		
		campaigns.stream().forEach(cam -> logger.info(cam.getName() + " " + cam.getStartDate() + "out of date"));
		campaigns.stream().forEach(cam -> campaignRepository.delete(cam));
	}

}
