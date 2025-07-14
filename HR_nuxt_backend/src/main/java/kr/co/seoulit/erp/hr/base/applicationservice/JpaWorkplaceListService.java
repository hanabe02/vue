package kr.co.seoulit.erp.hr.base.applicationservice;

import kr.co.seoulit.erp.hr.base.entity.WorkplaceListEntity;

import java.util.ArrayList;

public interface JpaWorkplaceListService {
    public ArrayList<WorkplaceListEntity> findWorkplaceList(String companyCode);
}
