package kr.co.seoulit.erp.hr.dailylabor.applicationservice;


import kr.co.seoulit.erp.hr.dailylabor.dao.DailyLaborAttendDAO;
import kr.co.seoulit.erp.hr.dailylabor.to.DailyLaborAttendTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DailyLaborAttendApplicationServiceImpl implements DailyLaborAttendApplicationService{

    @Autowired
    DailyLaborAttendDAO empDAO;

    @Override
    public ArrayList<DailyLaborAttendTO> findAttendList() {
        return null;
    }

    @Override
    public void dailyLaborRegiAttend(ArrayList<DailyLaborAttendTO> dailyLaborAttendTO) {
        System.out.println("dailyLaborAttendTO = " + dailyLaborAttendTO);

        for (DailyLaborAttendTO item : dailyLaborAttendTO) {
            empDAO.insertAttend(item);
        }
    }

    @Override
    public void deleteDailyLaborAttend(String empId, String workDate) {

    }

    @Override
    public void updateDailyLaborAttend(String approvalRequest) {

    }

    @Override
    public void updateDailyLaborAttendApproval(ArrayList<DailyLaborAttendTO> dailyLaborAttendTO) {
        System.out.println("dailyLaborAttendTO = " + dailyLaborAttendTO);

        for (DailyLaborAttendTO item : dailyLaborAttendTO) {
            empDAO.updateDailyLaborAttendApproval(item);
        }
    }

    @Override
    public void updateWorkAttendance(ArrayList<DailyLaborAttendTO> dailyLaborAttendTO) {
        System.out.println("dailyLaborAttendTO = " + dailyLaborAttendTO);

        for (DailyLaborAttendTO item : dailyLaborAttendTO) {
            empDAO.updateWorkAttendance(item);
        }
    }

    @Override
    public ArrayList<DailyLaborAttendTO> findAttendDateList(String workDate, String deadlineRequest) {
        System.out.println("workDate = " + workDate);
        System.out.println("deadlineRequest = " + deadlineRequest);
        ArrayList<DailyLaborAttendTO> dailyLaborAttendDateList = empDAO.selectDailyLaborAttendDateList(workDate, deadlineRequest);
        return dailyLaborAttendDateList;
    }

    @Override
    public void deleteDailyLaborAttend(ArrayList<DailyLaborAttendTO> dailyLaborAttendTO) {
        System.out.println("dailyLaborAttendTO = " + dailyLaborAttendTO);

        for (DailyLaborAttendTO item : dailyLaborAttendTO) {
            empDAO.deleteDailyLaborAttend(item);
        }
    }
}
