package mabaya.interview.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ProductResponceDto {
	
	Integer serialNumber;
	String title;
	String category;
	BigDecimal price;
	String campaignName;
	BigDecimal bid;
}
