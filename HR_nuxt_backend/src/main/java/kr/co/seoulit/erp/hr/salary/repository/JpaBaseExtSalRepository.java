package kr.co.seoulit.erp.hr.salary.repository;

import kr.co.seoulit.erp.hr.salary.entity.BaseExtSalEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface JpaBaseExtSalRepository extends CrudRepository<BaseExtSalEntity, String>{
    ArrayList<BaseExtSalEntity> findAllByOrderByExtSalCodeAsc();
}
