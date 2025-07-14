package kr.co.seoulit.erp.hr.base.controller;

import kr.co.seoulit.erp.hr.attendance.to.AttendanceRequestTo;
import kr.co.seoulit.erp.hr.base.applicationservice.CreateVacationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("hr/base/*")
@RequiredArgsConstructor
public class CreateVacationController {

    private final CreateVacationService createVacationService;

    @PostMapping("/vacationcreate")
    public void createVacation(){
        System.out.println("🎯 vacationcreate API 진입 성공");
        try {
            createVacationService.executeCreateProcedure();
System.out.println("연차생성성공");
        }catch (Exception e){
            System.out.println("연차생성실패z");
            e.printStackTrace();
        }
    }

}
