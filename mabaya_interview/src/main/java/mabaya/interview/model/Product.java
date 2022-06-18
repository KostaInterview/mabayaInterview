package mabaya.interview.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "product",
indexes = {
        @Index(
          name = "index_category", 
          columnList="category", 
          unique = false 
       		)
        }
)
@EqualsAndHashCode(of = {"serialNumber"})
public class Product  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6580669942404459165L;
	@Id
	Integer serialNumber;
	String title;
	String category;
	BigDecimal price;
	@ManyToMany(mappedBy = "identifiersToPromote" , fetch = FetchType.EAGER)

	Set<Campaign> campaigns;
	
	@ManyToMany(mappedBy = "identifiersToPromote" , fetch = FetchType.EAGER)
	Set<CampaignOOD> campaignOOD;
}
