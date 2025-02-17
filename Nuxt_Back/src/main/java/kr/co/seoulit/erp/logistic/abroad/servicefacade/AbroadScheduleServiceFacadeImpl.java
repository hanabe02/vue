package kr.co.seoulit.erp.logistic.abroad.servicefacade;

import kr.co.seoulit.erp.logistic.abroad.domain.AbroadSchedule;
import kr.co.seoulit.erp.logistic.abroad.repository.AbroadScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;


@Service
public class AbroadScheduleServiceFacadeImpl implements AbroadScheduleServiceFacade{

    @Autowired
    private AbroadScheduleRepository abroadRepository;
    private ModelMap modelMap=new ModelMap();
    @Override
    public ModelMap getAbroadScheduleList() {
        Iterable<AbroadSchedule> abroadScheduleList = abroadRepository.findAll();
        modelMap.put("abroadScheduleList", abroadScheduleList);
        return modelMap;
    }


    @Override
    public void insertAbroadScheduleCode(AbroadSchedule abroadScheduleInfoTO) {
        abroadRepository.save(abroadScheduleInfoTO);
    }

    @Transactional
    @Override
    public void deleteAbroadScheduleCode(String abroadScheduleCode) {
        abroadRepository.deleteByAbroadScheduleCode(abroadScheduleCode);
    }

    @Transactional
    @Override
    public void updateAbroadScheduleCode(AbroadSchedule abroadScheduleInfoTO) {
        abroadRepository.save(abroadScheduleInfoTO);

    }

}
