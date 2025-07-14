package kr.co.seoulit.erp.hr.dailylabor.servicefacade;

import kr.co.seoulit.erp.hr.dailylabor.to.DailyLaborAttendTO;

import java.util.ArrayList;
import java.util.List;

public interface DailyLaborAttendServiceFacade {
    public List<DailyLaborAttendTO> findDailyLaborAttendList();

    void dailyLaborRegiAttend(ArrayList<DailyLaborAttendTO> dailyLaborAttendTO);

    void deleteDailyLaborAttend(String empId, String workDate);

    void updateDailyLaborAttend(String approvalRequest);

    void updateDailyLaborAttendApproval(ArrayList<DailyLaborAttendTO> dailyLaborAttendTO);

    void updateWorkAttendance(ArrayList<DailyLaborAttendTO> dailyLaborAttendTO);

    List<DailyLaborAttendTO> findDailyLaborAttendDateList(String workDate, String deadlineRequest);

    void deleteDailyLaborAttend(ArrayList<DailyLaborAttendTO> dailyLaborAttendTO);
}
