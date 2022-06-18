package mabaya.interview.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mabaya.interview.model.Campaign;

public interface CampaignRepository   extends JpaRepository<Campaign,String>  {

	List<Campaign> findByStartDateBefore(LocalDate date);

}
