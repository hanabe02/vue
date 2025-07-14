package kr.co.seoulit.erp.hr.salary.applicationservice;

import kr.co.seoulit.erp.hr.salary.entity.BaseExtSalEntity;
import kr.co.seoulit.erp.hr.salary.repository.JpaBaseExtSalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class JpaBaseExtSalServiceImpl implements JpaBaseExtSalService {
    @Autowired
    JpaBaseExtSalRepository baseExtSalRepository;
    @Override
    public ArrayList<BaseExtSalEntity> findBaseExtSalList() {
        ArrayList<BaseExtSalEntity> baseExtSal = baseExtSalRepository.findAllByOrderByExtSalCodeAsc();
        return baseExtSal;
    }

    @Override
    public void registerBaseExtSal(BaseExtSalEntity baseExtSal) {
        baseExtSalRepository.save(baseExtSal);
    }

    @Override
    public void deleteBaseExtSal(String extSalCode) {
        baseExtSalRepository.deleteById(extSalCode);
    }

    @Override
    public void modifyBaseExtSal(BaseExtSalEntity baseExtSal) {
        baseExtSalRepository.save(baseExtSal);
    }
}
