package kr.co.seoulit.erp.hr.base.applicationservice;

import kr.co.seoulit.erp.hr.base.entity.MonthEntity;
import kr.co.seoulit.erp.hr.base.entity.YearEntity;

import java.util.ArrayList;

public interface JpaYearMonthService {
    ArrayList<YearEntity> findYear();
    ArrayList<MonthEntity> findMonth();
}
