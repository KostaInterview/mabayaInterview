package mabaya.interview.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter

public class CampaignDto {
	String name;
	LocalDate startDate;
	List<ProductDto> productsToPromote;
	BigDecimal bid;
}
