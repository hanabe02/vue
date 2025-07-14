package kr.co.seoulit.erp.logistic.abroad.repository;

import kr.co.seoulit.erp.logistic.abroad.domain.AbroadSchedule;
import org.springframework.data.repository.CrudRepository;

public interface AbroadScheduleRepository extends CrudRepository<AbroadSchedule, String> {

    public Iterable<AbroadSchedule> findAll();

    public void deleteByAbroadScheduleCode(String abroadScheduleCode);
}
