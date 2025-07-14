package kr.co.seoulit.erp.hr.dailylabor.servicefacade;

import kr.co.seoulit.erp.hr.dailylabor.to.DailyLaborEmpLogTO;
import kr.co.seoulit.erp.hr.dailylabor.to.DailyLaborEmpTO;

import java.util.ArrayList;
import java.util.List;

public interface DailyLaborEmpServiceFacade {

    public List<DailyLaborEmpTO> findDailyLaborEmpList();

    void dailyLaborRegiEmployee(DailyLaborEmpTO dailyLaborEmpTO);

    ArrayList<DailyLaborEmpTO> findEmpDetail(String clickEmp);

    void dailyLaborEmpInfoUpdate(DailyLaborEmpTO dailyLaborEmpTO);

    void deleteDailyLaborEmp(DailyLaborEmpTO dailyLaborEmpTO);

    List<DailyLaborEmpLogTO> dailyLaborEmpLogList();

    void deleteDailyLaborEmpLog(List<DailyLaborEmpLogTO> dailyLaborEmpLogTO);
}
