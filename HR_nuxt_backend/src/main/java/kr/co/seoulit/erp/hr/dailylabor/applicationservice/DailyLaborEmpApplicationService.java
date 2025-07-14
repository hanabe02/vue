package kr.co.seoulit.erp.hr.dailylabor.applicationservice;

import kr.co.seoulit.erp.hr.dailylabor.to.DailyLaborEmpLogTO;
import kr.co.seoulit.erp.hr.dailylabor.to.DailyLaborEmpTO;

import java.util.ArrayList;
import java.util.List;

public interface DailyLaborEmpApplicationService {
    public ArrayList<DailyLaborEmpTO> findEmpList();
    void dailyLaborRegiEmployee(DailyLaborEmpTO dailyLaborEmpTO);

    ArrayList<DailyLaborEmpTO> findDailyLaborEmpDetail(String empCode);

    void dailyLaborEmpInfoUpdate(DailyLaborEmpTO dailyLaborEmpTO);

    void deleteDailyLaborEmp(DailyLaborEmpTO dailyLaborEmpTO);

    List<DailyLaborEmpLogTO> dailyLaborEmpLogList();

    void deleteDailyLaborEmpLog(List<DailyLaborEmpLogTO> dailyLaborEmpLogTO);
}
