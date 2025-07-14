package kr.co.seoulit.erp.hr.base.applicationservice;

import kr.co.seoulit.erp.hr.base.entity.HolidayEntity;
import kr.co.seoulit.erp.hr.base.to.HolidayTO;

import java.util.ArrayList;

public interface JpaHolidayService {

        public void addHoliday(HolidayEntity holiday);

        public void modifyHoliday(HolidayEntity holiday);

        public void removeHoliday(String applyDay);

        public ArrayList<HolidayEntity> findHolidayList(String applyDay);
}
