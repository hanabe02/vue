package kr.co.seoulit.erp.hr.dailylabor.servicefacade;

import kr.co.seoulit.erp.hr.dailylabor.applicationservice.DailyLaborEmpApplicationService;
import kr.co.seoulit.erp.hr.dailylabor.to.DailyLaborEmpLogTO;
import kr.co.seoulit.erp.hr.dailylabor.to.DailyLaborEmpTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DailyLaborEmpServiceFacadeImpl implements DailyLaborEmpServiceFacade {
    @Autowired
    private DailyLaborEmpApplicationService dailyLaborEmpApplicationService;

    @Override
    public List<DailyLaborEmpTO> findDailyLaborEmpList() {
        ArrayList<DailyLaborEmpTO> DailyLaborEmpList = dailyLaborEmpApplicationService.findEmpList();
        return DailyLaborEmpList;
    }

    @Override
    public void dailyLaborRegiEmployee(DailyLaborEmpTO dailyLaborEmpTO) {
        dailyLaborEmpApplicationService.dailyLaborRegiEmployee(dailyLaborEmpTO);
    }

    @Override
    public ArrayList<DailyLaborEmpTO> findEmpDetail(String empCode) {
        System.out.println(empCode);
        return dailyLaborEmpApplicationService.findDailyLaborEmpDetail(empCode);
    }

    @Override
    public void dailyLaborEmpInfoUpdate(DailyLaborEmpTO dailyLaborEmpTO) {
        dailyLaborEmpApplicationService.dailyLaborEmpInfoUpdate(dailyLaborEmpTO);
    }

    @Override
    public void deleteDailyLaborEmp(DailyLaborEmpTO dailyLaborEmpTO) {
        dailyLaborEmpApplicationService.deleteDailyLaborEmp(dailyLaborEmpTO);
    }
    @Override
    public List<DailyLaborEmpLogTO> dailyLaborEmpLogList() {
        List<DailyLaborEmpLogTO> list = dailyLaborEmpApplicationService.dailyLaborEmpLogList();
        return list;
    }

    @Override
    public void deleteDailyLaborEmpLog(List<DailyLaborEmpLogTO> dailyLaborEmpLogTO) {
        dailyLaborEmpApplicationService.deleteDailyLaborEmpLog(dailyLaborEmpLogTO);
    }
}
