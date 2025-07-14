package kr.co.seoulit.erp.hr.base.repository;

import kr.co.seoulit.erp.hr.base.entity.HolidayEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface JpaHolidayRepository extends JpaRepository<HolidayEntity, String> {
    ArrayList<HolidayEntity> findByApplyDay(String applyDay);
}
