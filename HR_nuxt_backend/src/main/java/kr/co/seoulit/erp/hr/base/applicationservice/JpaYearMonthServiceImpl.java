package kr.co.seoulit.erp.hr.base.applicationservice;

import kr.co.seoulit.erp.hr.base.entity.MonthEntity;
import kr.co.seoulit.erp.hr.base.entity.YearEntity;
import kr.co.seoulit.erp.hr.base.repository.JpaMonthRepository;
import kr.co.seoulit.erp.hr.base.repository.JpaYearRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JpaYearMonthServiceImpl implements JpaYearMonthService {
    @Autowired
    JpaYearRepository jpaYearRepository;
    @Autowired
    JpaMonthRepository jpaMonthRepository;

    @Override
    public ArrayList<YearEntity> findYear() {
        ArrayList<YearEntity> year = jpaYearRepository.findAll();
        return year;
    }
    @Override
    public ArrayList<MonthEntity> findMonth() {
        ArrayList<MonthEntity> month = jpaMonthRepository.findAllByOrderByMonthAsc();
        return month;
    }
}
