package kr.co.seoulit.erp.hr.dailylabor.applicationservice;

import kr.co.seoulit.erp.hr.dailylabor.dao.DailyLaborSalaryDAO;
import kr.co.seoulit.erp.hr.dailylabor.to.DailyLaborSalaryTO;
import kr.co.seoulit.erp.hr.dailylabor.to.DailyLaborSalaryTaxTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DailyLaborSalaryApplicationServiceImpl implements DailyLaborSalaryApplicationService{
    @Autowired
    private DailyLaborSalaryDAO empDAO;

    @Override
    public List<DailyLaborSalaryTO> findSalaryList(String date, String deadline, String status) {
        List<DailyLaborSalaryTO> SalaryList = empDAO.findSalaryList(date, deadline, status);
        return SalaryList;
    }

    @Override
    public void insertSalaryList(String date) {
        empDAO.insertSalaryList(date);
    }

    @Override
    public void updateDailyLaborSalaryList(ArrayList<DailyLaborSalaryTO> dailyLaborSalaryTO) {
        String date = dailyLaborSalaryTO.get(0).getWorkDate();
        for (DailyLaborSalaryTO item : dailyLaborSalaryTO) {
            empDAO.updateSalaryList(item);
        }
        empDAO.insertTaxList(date);
    }

    @Override
    public void deleteDailyLaborSalary(ArrayList<DailyLaborSalaryTO> dailyLaborSalaryTO) {
        for (DailyLaborSalaryTO item : dailyLaborSalaryTO) {
            empDAO.deleteSalary(item);
        }
    }

    @Override
    public List<DailyLaborSalaryTO> dailyLaborTaxList(String date) {
        List<DailyLaborSalaryTO> list = empDAO.findTaxList(date);
        return list;
    }

    @Override
    public List<DailyLaborSalaryTaxTO> dailyLaborSalaryTaxList(String workDate, String deadlineRequest, String status) {
        List<DailyLaborSalaryTaxTO> list = empDAO.selectSalaryTaxList(workDate, deadlineRequest, status);
        return list;
    }

    @Override
    public void deleteDailyLaborSalaryTaxList(List<DailyLaborSalaryTO> dailyLaborSalaryTO) {
        for (DailyLaborSalaryTO item : dailyLaborSalaryTO) {
            empDAO.deleteTax(item);
            empDAO.deleteSalary(item);
        }
    }

    @Override
    public void updateDailyLaborSalaryTaxList(List<DailyLaborSalaryTO> dailyLaborSalaryTO) {
        for (DailyLaborSalaryTO item : dailyLaborSalaryTO) {
            empDAO.updateSalaryTaxList(item);
        }
    }
}
