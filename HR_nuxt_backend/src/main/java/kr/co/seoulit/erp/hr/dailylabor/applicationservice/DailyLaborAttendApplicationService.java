package kr.co.seoulit.erp.hr.dailylabor.applicationservice;

import kr.co.seoulit.erp.hr.dailylabor.to.DailyLaborAttendTO;

import java.util.ArrayList;

public interface DailyLaborAttendApplicationService {
    public ArrayList<DailyLaborAttendTO> findAttendList();

    void dailyLaborRegiAttend(ArrayList<DailyLaborAttendTO> dailyLaborAttendTO);

    void deleteDailyLaborAttend(String empId, String workDate);

    void updateDailyLaborAttend(String approvalRequest);

    void updateDailyLaborAttendApproval(ArrayList<DailyLaborAttendTO> dailyLaborAttendTO);

    void updateWorkAttendance(ArrayList<DailyLaborAttendTO> dailyLaborAttendTO);

    ArrayList<DailyLaborAttendTO> findAttendDateList(String workDate, String deadlineRequest);

    void deleteDailyLaborAttend(ArrayList<DailyLaborAttendTO> dailyLaborAttendTO);
}
