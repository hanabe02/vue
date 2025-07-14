package kr.co.seoulit.erp.hr.salary.applicationservice;

import kr.co.seoulit.erp.hr.salary.entity.BaseExtSalEntity;
import kr.co.seoulit.erp.hr.salary.to.BaseExtSalTO;

import java.util.ArrayList;

public interface JpaBaseExtSalService {
    public ArrayList<BaseExtSalEntity> findBaseExtSalList();

    public void registerBaseExtSal(BaseExtSalEntity baseExtSal);

    public void deleteBaseExtSal(String extSalCode);

    public void modifyBaseExtSal(BaseExtSalEntity baseExtSal);
}
