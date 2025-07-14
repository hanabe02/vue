package kr.co.seoulit.erp.hr.base.repository;

import kr.co.seoulit.erp.hr.base.entity.MonthEntity;
import kr.co.seoulit.erp.hr.base.entity.YearEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface JpaMonthRepository extends CrudRepository<MonthEntity, String>{
    ArrayList<MonthEntity> findAllByOrderByMonthAsc();
}