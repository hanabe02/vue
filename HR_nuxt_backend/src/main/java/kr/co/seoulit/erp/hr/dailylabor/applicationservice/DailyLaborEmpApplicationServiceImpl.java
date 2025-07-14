package kr.co.seoulit.erp.hr.dailylabor.applicationservice;

import kr.co.seoulit.erp.hr.dailylabor.dao.DailyLaborEmpDAO;
import kr.co.seoulit.erp.hr.dailylabor.to.DailyLaborEmpLogTO;
import kr.co.seoulit.erp.hr.dailylabor.to.DailyLaborEmpTO;
import kr.co.seoulit.erp.hr.dailylabor.to.DailyLaborSalaryTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Component
public class DailyLaborEmpApplicationServiceImpl implements DailyLaborEmpApplicationService {
    @Autowired
    private DailyLaborEmpDAO empDAO;

    @Override
    public ArrayList<DailyLaborEmpTO> findEmpList() {
        System.out.println("===============일용근로 직원 전체 조회~~==============");
        // TODO Auto-generated method stub
        return empDAO.selectDailyLaborEmpAllList();
    }

    @Override
    public void dailyLaborRegiEmployee(DailyLaborEmpTO dailyLaborEmpTO) {
        empDAO.dailyLaborRegiEmployee(dailyLaborEmpTO);
    }

    @Override
    public ArrayList<DailyLaborEmpTO> findDailyLaborEmpDetail(String empCode) {
        return empDAO.selectDailyLaborEmpDetail(empCode);
    }

    @Override
    public void dailyLaborEmpInfoUpdate(DailyLaborEmpTO dailyLaborEmpTO) {
        empDAO.updateDailyLaborEmployee(dailyLaborEmpTO);
    }

    @Override
    public void deleteDailyLaborEmp(DailyLaborEmpTO dailyLaborEmpTO) {
        empDAO.deleteEmp(dailyLaborEmpTO);
        empDAO.insertLog(dailyLaborEmpTO);
    }

    @Override
    public List<DailyLaborEmpLogTO> dailyLaborEmpLogList() {
        List<DailyLaborEmpLogTO> list = empDAO.selectEmpLogList();
        return list;
    }

    @Override
    public void deleteDailyLaborEmpLog(List<DailyLaborEmpLogTO> dailyLaborEmpLogTO) {
        for (DailyLaborEmpLogTO item : dailyLaborEmpLogTO) {
            empDAO.deleteEmpLog(item);
        }
    }
}
