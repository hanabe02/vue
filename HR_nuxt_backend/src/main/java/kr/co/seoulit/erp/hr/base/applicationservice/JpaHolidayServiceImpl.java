package kr.co.seoulit.erp.hr.base.applicationservice;

import kr.co.seoulit.erp.hr.base.entity.HolidayEntity;
import kr.co.seoulit.erp.hr.base.repository.JpaHolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class JpaHolidayServiceImpl implements JpaHolidayService {
    @Autowired
    JpaHolidayRepository jpaHolidayRepository;
    @Override
    public void addHoliday(HolidayEntity holiday) {
        jpaHolidayRepository.save(holiday);
    }

    @Override
    public void modifyHoliday(HolidayEntity holiday) {
        jpaHolidayRepository.save(holiday);
    }

    @Override
    public void removeHoliday(String applyDay) {
        jpaHolidayRepository.deleteById(applyDay);
    }

    @Override
    public ArrayList<HolidayEntity> findHolidayList(String applyDay) {
        ArrayList<HolidayEntity> holidayList = jpaHolidayRepository.findByApplyDay(applyDay);
        return holidayList;
    }
}
