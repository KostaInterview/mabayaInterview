package mabaya.interview.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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

public class ProductDto {
	@JsonInclude(value = Include.ALWAYS)	
	Integer serialNumber;
	@JsonInclude(value = Include.NON_NULL)
	String title;
	@JsonInclude(value = Include.NON_NULL)
	String category;
	@JsonInclude(value = Include.NON_NULL)
	BigDecimal price;
	
}
