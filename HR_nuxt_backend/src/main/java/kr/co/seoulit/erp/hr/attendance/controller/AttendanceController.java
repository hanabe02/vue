package kr.co.seoulit.erp.hr.attendance.controller;

import kr.co.seoulit.erp.hr.attendance.servicefacade.AttendanceServiceFacade;
import kr.co.seoulit.erp.hr.attendance.to.AttendanceRequestTo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AttendanceController {

    private final AttendanceServiceFacade attendanceServiceFacade;

    @PostMapping("/enter")
    public void callEnter(@RequestBody AttendanceRequestTo to){
        System.out.println("empCode" + to.getEmpCode());
        System.out.println("workDate" + to.getWorkDate());
        attendanceServiceFacade.callEnterProcedure(to.getEmpCode(),to.getWorkDate());

    }


    @PostMapping("/leave")
    public void callLeave(@RequestBody AttendanceRequestTo to) {
        System.out.println("empCode " + to.getEmpCode());
        attendanceServiceFacade.callLeaveProcedure(to.getEmpCode(), to.getWorkDate());
    }
}
