package kr.co.seoulit.erp.hr.dailylabor.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.seoulit.erp.hr.dailylabor.servicefacade.DailyLaborAttendServiceFacade;
import kr.co.seoulit.erp.hr.dailylabor.to.DailyLaborAttendTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@Tag(name = "일용근로 근태관리", description = "일용근로 근태관리 API")
@RestController
@RequestMapping("/hr/dailyattend/*")
public class DailyLaborAttendController {

    @Autowired
    private DailyLaborAttendServiceFacade dailyLaborAttendServiceFacade;

    @Operation(summary = "일용근로 근태 리스트 조회", description = "일용근로 근태 리스트를 출력합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
    })
    @GetMapping("attned")
    public ResponseEntity<Map<String, Object>> dailyLaborAttendList() {
        Map<String, Object> map = new HashMap<>();
        try{
            List<DailyLaborAttendTO> list = dailyLaborAttendServiceFacade.findDailyLaborAttendList();
            map.put("dailyLaborAttendList", list);
            map.put("errorMsg", "success");
            map.put("errorCode", 0);
        }catch(Exception ioe){
            map.clear();
            map.put("errorCode", -1);
            map.put("errorMsg", ioe.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

    @Operation(summary = "일용근로 근태 리스트 DATE 사용하여 조회", description = "일용근로 근태 리스트를 출력합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
    })
    @GetMapping("attneddate")
    public ResponseEntity<Map<String, Object>> findDailyLaborAttendList(
            @RequestParam("workDate") String workDate, @RequestParam("deadlineRequest") String deadlineRequest) {
        Map<String, Object> map = new HashMap<>();
        try{
            System.out.println("workDate = " + workDate);
            System.out.println("deadlineRequest = " + deadlineRequest);
            List<DailyLaborAttendTO> list = dailyLaborAttendServiceFacade.findDailyLaborAttendDateList(workDate, deadlineRequest);
            map.put("dailyLaborAttendList", list);
            map.put("errorMsg", "success");
            map.put("errorCode", 0);
        }catch(Exception ioe){
            map.clear();
            map.put("errorCode", -1);
            map.put("errorMsg", ioe.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

    @Operation(summary = "일용근로 근태 등록", description = "일용근로 근태 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
    })
    @PostMapping(value="attned")
    public ResponseEntity<Map<String, Object>> dailyLaborRegiAttend(@RequestBody ArrayList<DailyLaborAttendTO> dailyLaborAttendTO) {
        HashMap<String, Object> map = new HashMap<>();
        try{
            dailyLaborAttendServiceFacade.dailyLaborRegiAttend(dailyLaborAttendTO);
            map.put("errorMsg", "success");
            map.put("errorCode", 0);
        }catch(Exception ioe){
            map.clear();
            map.put("errorCode", -1);
            map.put("errorMsg", ioe.getMessage());
        }
        return null;
    }

    @Operation(summary = "일용근로 퇴근 프로시저", description = "일용근로 퇴근 프로시저.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
    })
    @PutMapping(value="attned")
    public ResponseEntity<Map<String, Object>> updateWorkAttendance(@RequestBody ArrayList<DailyLaborAttendTO> dailyLaborAttendTO) {
        HashMap<String, Object> map = new HashMap<>();
        try{
            dailyLaborAttendServiceFacade.updateWorkAttendance(dailyLaborAttendTO);
            map.put("errorMsg", "success");
            map.put("errorCode", 0);
        }catch(Exception ioe){
            map.clear();
            map.put("errorCode", -1);
            map.put("errorMsg", ioe.getMessage());
        }
        return null;
    }


    @Operation(summary = "일용근로 근태 삭제", description = "일용근로 근태를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
    })
    @PutMapping(value="attenddate")
    public Map<String, Object> deleteDailyLaborAttend(@RequestBody ArrayList<DailyLaborAttendTO> dailyLaborAttendTO) {
        HashMap<String, Object> map = new HashMap<>();
        try{
            dailyLaborAttendServiceFacade.deleteDailyLaborAttend(dailyLaborAttendTO);
            map.put("errorMsg", "success");
            map.put("errorCode", 0);
        }catch(Exception ioe){
            map.clear();
            map.put("errorCode", -1);
            map.put("errorMsg", ioe.getMessage());
        }
        return map;
    }

    @Operation(summary = "일용근로 근태 수정", description = "수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
    })
    @PutMapping(value="attend")
    public Map<String, Object> updateDailyLaborAttend(@RequestParam String approvalRequest) {
        HashMap<String, Object> map = new HashMap<>();
        try{
            dailyLaborAttendServiceFacade.updateDailyLaborAttend(approvalRequest);
            map.put("errorMsg", "success");
            map.put("errorCode", 0);
        }catch(Exception ioe){
            map.clear();
            map.put("errorCode", -1);
            map.put("errorMsg", ioe.getMessage());
        }
        return map;
    }

    @Operation(summary = "일용근로 근태 승인 및 반려", description = "일용근로 근태를 승인 및 반려합니다..")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
    })
    @PutMapping(value="approval")
    public Map<String, Object> updateDailyLaborAttendApproval(@RequestBody ArrayList<DailyLaborAttendTO> dailyLaborAttendTO) {
        HashMap<String, Object> map = new HashMap<>();
        try{
            dailyLaborAttendServiceFacade.updateDailyLaborAttendApproval(dailyLaborAttendTO);
            map.put("errorMsg", "success");
            map.put("errorCode", 0);
        }catch(Exception ioe){
            map.clear();
            map.put("errorCode", -1);
            map.put("errorMsg", ioe.getMessage());
        }
        return map;
    }
}
