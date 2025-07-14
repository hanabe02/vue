package kr.co.seoulit.erp.hr.dailylabor.servicefacade;

import kr.co.seoulit.erp.hr.dailylabor.applicationservice.DailyLaborSalaryApplicationService;
import kr.co.seoulit.erp.hr.dailylabor.to.DailyLaborSalaryTO;
import kr.co.seoulit.erp.hr.dailylabor.to.DailyLaborSalaryTaxTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DailyLaborSalaryServiceFacadeImpl implements DailyLaborSalaryServiceFacade {
    @Autowired
    private DailyLaborSalaryApplicationService dailyLaborSalaryApplicationService;

    @Override
    public List<DailyLaborSalaryTO> dailyLaborSalaryList(String date, String deadline, String status) {
        List<DailyLaborSalaryTO> dailyLaborSalaryList = dailyLaborSalaryApplicationService.findSalaryList(date, deadline, status);
        return dailyLaborSalaryList;
    }

    @Override
    public void dailyLaborRegiSalaryList(String date) {
        dailyLaborSalaryApplicationService.insertSalaryList(date);
    }

    @Override
    public void updateDailyLaborSalaryList(ArrayList<DailyLaborSalaryTO> dailyLaborSalaryTO) {
        dailyLaborSalaryApplicationService.updateDailyLaborSalaryList(dailyLaborSalaryTO);
    }

    @Override
    public void deleteDailyLaborSalary(ArrayList<DailyLaborSalaryTO> dailyLaborSalaryTO) {
        dailyLaborSalaryApplicationService.deleteDailyLaborSalary(dailyLaborSalaryTO);
    }

    @Override
    public List<DailyLaborSalaryTO> dailyLaborTaxList(String date) {
        List<DailyLaborSalaryTO> list = dailyLaborSalaryApplicationService.dailyLaborTaxList(date);
        return list;
    }

    @Override
    public List<DailyLaborSalaryTaxTO> dailyLaborSalaryTaxList(String workDate, String deadlineRequest, String status) {
        List<DailyLaborSalaryTaxTO> list = dailyLaborSalaryApplicationService.dailyLaborSalaryTaxList(workDate, deadlineRequest, status);
        return list;
    }

    @Override
    public void deleteDailyLaborSalaryTaxList(List<DailyLaborSalaryTO> dailyLaborSalaryTO) {
        dailyLaborSalaryApplicationService.deleteDailyLaborSalaryTaxList(dailyLaborSalaryTO);
    }

    @Override
    public void updateDailyLaborSalaryTaxList(List<DailyLaborSalaryTO> dailyLaborSalaryTO) {
        dailyLaborSalaryApplicationService.updateDailyLaborSalaryTaxList(dailyLaborSalaryTO);
    }
}