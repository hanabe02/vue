package kr.co.seoulit.erp.hr.base.applicationservice;

import kr.co.seoulit.erp.hr.base.entity.WorkplaceListEntity;
import kr.co.seoulit.erp.hr.base.repository.JpaWorkplaceListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class JpaWorkplaceListServiceImpl implements JpaWorkplaceListService {
    @Autowired
    JpaWorkplaceListRepository workplaceListRepository;
    @Override
    public ArrayList<WorkplaceListEntity> findWorkplaceList(String companyCode) {
        ArrayList<WorkplaceListEntity> workplaceList = workplaceListRepository.findByCompanyCode(companyCode);
        return workplaceList;
    }
}
