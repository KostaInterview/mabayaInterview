package mabaya.interview.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
@Table(name = "campaign",
indexes = {
         @Index(
           name = "index_bid", 
           columnList="bid", 
           unique = false 
        		)
         }
)
@EqualsAndHashCode(of = {"name"})
public class Campaign  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4448337815347854726L;
	@Id
	String name;
	LocalDate startDate;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "campaign_product",
	   joinColumns = @JoinColumn(name = "campaign"),
	   inverseJoinColumns = @JoinColumn(name = "product"))
	Set<Product> identifiersToPromote;
	@Column(name = "bid", nullable = false)
	BigDecimal bid;
}
