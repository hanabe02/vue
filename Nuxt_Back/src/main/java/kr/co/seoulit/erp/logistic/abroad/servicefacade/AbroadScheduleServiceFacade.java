package kr.co.seoulit.erp.logistic.abroad.servicefacade;

import kr.co.seoulit.erp.logistic.abroad.to.abroadScheduleInfoTO;
import kr.co.seoulit.erp.logistic.abroad.domain.AbroadSchedule;
import org.springframework.ui.ModelMap;

public interface AbroadScheduleServiceFacade {
    public ModelMap getAbroadScheduleList();

    public void insertAbroadScheduleCode(AbroadSchedule abroadScheduleInfoTO);

    public void deleteAbroadScheduleCode(String abroadScheduleCode);

    void updateAbroadScheduleCode(AbroadSchedule abroadScheduleInfoTO);
}
