package kr.co.seoulit.erp.hr.attendance.servicefacade;

import kr.co.seoulit.erp.hr.attendance.dao.AttendanceDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttendanceServiceFacadeImpl implements AttendanceServiceFacade {
    private final AttendanceDAO attendanceDAO;

    @Override
    public void callEnterProcedure(String empcode, String workDate) {
        attendanceDAO.callEnterProcedure(empcode, workDate);
    }
    @Override
    public void callLeaveProcedure(String empcode, String workDate) {
        attendanceDAO.callLeaveProcedure(empcode, workDate);
    }
}
