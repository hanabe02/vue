package kr.co.seoulit.erp.hr.attendance.servicefacade;

public interface AttendanceServiceFacade {
    void callEnterProcedure(String empcode, String workDate);

    void callLeaveProcedure(String empcode, String workDate);
}
