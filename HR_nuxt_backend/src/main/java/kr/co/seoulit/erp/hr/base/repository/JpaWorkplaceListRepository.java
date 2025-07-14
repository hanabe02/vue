package kr.co.seoulit.erp.hr.base.repository;

import kr.co.seoulit.erp.hr.base.entity.WorkplaceListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
@Repository
public interface JpaWorkplaceListRepository extends CrudRepository<WorkplaceListEntity, String> {
    ArrayList<WorkplaceListEntity> findByCompanyCode(String companyCode);
}
