package kr.co.seoulit.erp.hr.dailylabor.applicationservice;

import kr.co.seoulit.erp.hr.dailylabor.to.DailyLaborSalaryTO;
import kr.co.seoulit.erp.hr.dailylabor.to.DailyLaborSalaryTaxTO;

import java.util.ArrayList;
import java.util.List;

public interface DailyLaborSalaryApplicationService {

    List<DailyLaborSalaryTO> findSalaryList(String date, String deadline, String status);

    void insertSalaryList(String date);

    void updateDailyLaborSalaryList(ArrayList<DailyLaborSalaryTO> dailyLaborSalaryTO);

    void deleteDailyLaborSalary(ArrayList<DailyLaborSalaryTO> dailyLaborSalaryTO);

    List<DailyLaborSalaryTO> dailyLaborTaxList(String date);

    List<DailyLaborSalaryTaxTO> dailyLaborSalaryTaxList(String workDate, String deadlineRequest, String status);

    void deleteDailyLaborSalaryTaxList(List<DailyLaborSalaryTO> dailyLaborSalaryTO);

    void updateDailyLaborSalaryTaxList(List<DailyLaborSalaryTO> dailyLaborSalaryTO);
}
