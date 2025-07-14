package kr.co.seoulit.erp.hr.dailylabor.dao;

import kr.co.seoulit.erp.hr.dailylabor.to.DailyLaborAttendTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface DailyLaborAttendDAO {
   ArrayList<DailyLaborAttendTO> selectDailyLaborAttendAllList();

    void insertAttend(DailyLaborAttendTO dailyLaborAttendTO);

    void updateWorkAttendance(DailyLaborAttendTO dailyLaborAttendTO);

    ArrayList<DailyLaborAttendTO> selectDailyLaborAttendDateList(
            @Param("workDate") String workDate, @Param("deadlineRequest")String deadlineRequest);

    void deleteDailyLaborAttend(DailyLaborAttendTO dailyLaborAttendTO);

    void updateDailyLaborAttendApproval(DailyLaborAttendTO item);

}
