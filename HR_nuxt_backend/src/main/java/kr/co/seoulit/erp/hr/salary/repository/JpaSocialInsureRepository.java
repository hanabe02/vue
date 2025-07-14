package kr.co.seoulit.erp.hr.salary.repository;

import kr.co.seoulit.erp.hr.salary.entity.SocialInsureEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface JpaSocialInsureRepository extends CrudRepository<SocialInsureEntity, String>{
    ArrayList<SocialInsureEntity> findByAttributionYear(String year);
}
