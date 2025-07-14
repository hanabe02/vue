package kr.co.seoulit.erp.hr.dailylabor.servicefacade;

import kr.co.seoulit.erp.hr.dailylabor.applicationservice.DailyLaborAttendApplicationService;
import kr.co.seoulit.erp.hr.dailylabor.to.DailyLaborAttendTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DailyLaborAttendServiceFacadeImpl implements DailyLaborAttendServiceFacade {
    @Autowired
    private DailyLaborAttendApplicationService dailyLaborAttendApplicationService;

    @Override
    public List<DailyLaborAttendTO> findDailyLaborAttendList() {
        ArrayList<DailyLaborAttendTO> dailyLaborAttendList = dailyLaborAttendApplicationService.findAttendList();
        return dailyLaborAttendList;
    }

    @Override
    public void dailyLaborRegiAttend(ArrayList<DailyLaborAttendTO> dailyLaborAttendTO) {
        dailyLaborAttendApplicationService.dailyLaborRegiAttend(dailyLaborAttendTO);
    }

    @Override
    public void deleteDailyLaborAttend(String empId, String workDate) {
        dailyLaborAttendApplicationService.deleteDailyLaborAttend(empId, workDate);
    }

    @Override
    public void updateDailyLaborAttend(String approvalRequest) {
        dailyLaborAttendApplicationService.updateDailyLaborAttend(approvalRequest);
    }

    @Override
    public void updateDailyLaborAttendApproval(ArrayList<DailyLaborAttendTO> dailyLaborAttendTO) {
        dailyLaborAttendApplicationService.updateDailyLaborAttendApproval(dailyLaborAttendTO);
    }

    @Override
    public void updateWorkAttendance(ArrayList<DailyLaborAttendTO> dailyLaborAttendTO) {
        dailyLaborAttendApplicationService.updateWorkAttendance(dailyLaborAttendTO);
    }

    @Override
    public List<DailyLaborAttendTO> findDailyLaborAttendDateList(String workDate, String deadlineRequest) {
        ArrayList<DailyLaborAttendTO> dailyLaborAttendDateList = dailyLaborAttendApplicationService.findAttendDateList(workDate, deadlineRequest);
        return dailyLaborAttendDateList;
    }

    @Override
    public void deleteDailyLaborAttend(ArrayList<DailyLaborAttendTO> dailyLaborAttendTO) {
        dailyLaborAttendApplicationService.deleteDailyLaborAttend(dailyLaborAttendTO);
    }

}