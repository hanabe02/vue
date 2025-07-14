package kr.co.seoulit.erp.hr.dailylabor.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.seoulit.erp.hr.dailylabor.servicefacade.DailyLaborSalaryServiceFacade;
import kr.co.seoulit.erp.hr.dailylabor.to.DailyLaborSalaryTO;
import kr.co.seoulit.erp.hr.dailylabor.to.DailyLaborSalaryTaxTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@Tag(name = "일용근로 급여관리", description = "일용근로 근태관리 API")
@RestController
@RequestMapping("/hr/dailysal/*")
public class DailyLaborSalaryController {
    @Autowired
    private DailyLaborSalaryServiceFacade dailyLaborSalaryServiceFacade;

    @Operation(summary = "일용근로 급여생성", description = "일용근로 급여생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
    })
    @PostMapping(value="salary")
    public ResponseEntity<Map<String, Object>> dailyLaborRegiSalaryList(@RequestBody DailyLaborSalaryTO dailyLaborSalaryTO) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            String date = dailyLaborSalaryTO.getWorkDate();
            System.out.println("date = " + date);
            dailyLaborSalaryServiceFacade.dailyLaborRegiSalaryList(date);
            map.put("errorMsg", "success");
            map.put("errorCode", 0);
            return ResponseEntity.ok(map);
        } catch (Exception dae){
            map.clear();
            map.put("errorCode", -1);
            map.put("errorMsg", dae.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

    @Operation(summary = "일용근로 급여생성 리스트 조회", description = "일용근로 근태생성 리스트를 출력합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
    })
    @GetMapping("salary")
    public ResponseEntity<Map<String, Object>> dailyLaborSalaryList(
            @RequestParam("date") String date, @RequestParam("deadline") String deadline, @RequestParam("status") String status) {
        Map<String, Object> map = new HashMap<>();
        try{
            List<DailyLaborSalaryTO> list = dailyLaborSalaryServiceFacade.dailyLaborSalaryList(date, deadline, status);
            map.put("dailyLaborSalaryList", list);
            map.put("errorMsg", "success");
            map.put("errorCode", 0);
        }catch(Exception ioe){
            map.clear();
            map.put("errorCode", -1);
            map.put("errorMsg", ioe.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

    @Operation(summary = "일용근로 급여 등록여부 수정", description = "일용근로 급여 등록여부 수정.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
    })
    @PutMapping(value="salary")
    public ResponseEntity<Map<String, Object>> updateDailyLaborSalaryList(@RequestBody ArrayList<DailyLaborSalaryTO> dailyLaborSalaryTO) {
        HashMap<String, Object> map = new HashMap<>();
        try{
            dailyLaborSalaryServiceFacade.updateDailyLaborSalaryList(dailyLaborSalaryTO);
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
    @PutMapping(value="delete")
    public Map<String, Object> deleteDailyLaborSalary(@RequestBody ArrayList<DailyLaborSalaryTO> dailyLaborSalaryTO) {
        HashMap<String, Object> map = new HashMap<>();
        try{
            dailyLaborSalaryServiceFacade.deleteDailyLaborSalary(dailyLaborSalaryTO);
            map.put("errorMsg", "success");
            map.put("errorCode", 0);
        }catch(Exception ioe){
            map.clear();
            map.put("errorCode", -1);
            map.put("errorMsg", ioe.getMessage());
        }
        return map;
    }

    @Operation(summary = "일용근로 급여 공제 포함 리스트 조회", description = "일용근로 급여 공제 포험 리스트를 출력합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
    })
    @GetMapping("salarytax")
    public ResponseEntity<Map<String, Object>> dailyLaborSalaryTaxList(
            @RequestParam("workDate") String workDate, @RequestParam("deadlineRequest") String deadlineRequest, @RequestParam("status") String status) {
        Map<String, Object> map = new HashMap<>();
        try{
            List<DailyLaborSalaryTaxTO> list = dailyLaborSalaryServiceFacade.dailyLaborSalaryTaxList(workDate, deadlineRequest, status);
            map.put("dailyLaborSalaryTaxList", list);
            map.put("errorMsg", "success");
            map.put("errorCode", 0);
        }catch(Exception ioe){
            map.clear();
            map.put("errorCode", -1);
            map.put("errorMsg", ioe.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

    @Operation(summary = "일용근로 급여 공제 포함 리스트 마감", description = "일용근로 급여 공제 포험 마감합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
    })
    @PutMapping("salarytax")
    public ResponseEntity<Map<String, Object>> updateDailyLaborSalaryTaxList(@RequestBody List<DailyLaborSalaryTO> dailyLaborSalaryTO) {
        Map<String, Object> map = new HashMap<>();
        try{
            dailyLaborSalaryServiceFacade.updateDailyLaborSalaryTaxList(dailyLaborSalaryTO);
            map.put("errorMsg", "success");
            map.put("errorCode", 0);
        }catch(Exception ioe){
            map.clear();
            map.put("errorCode", -1);
            map.put("errorMsg", ioe.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

    @Operation(summary = "일용근로 급여 공제 포함 리스트 삭제", description = "일용근로 급여 공제 포험 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
    })
    @PutMapping("deletesalarytax")
    public ResponseEntity<Map<String, Object>> deleteDailyLaborSalaryTaxList(@RequestBody List<DailyLaborSalaryTO> dailyLaborSalaryTO) {
        Map<String, Object> map = new HashMap<>();
        try{
            dailyLaborSalaryServiceFacade.deleteDailyLaborSalaryTaxList(dailyLaborSalaryTO);
            map.put("errorMsg", "success");
            map.put("errorCode", 0);
        }catch(Exception ioe){
            map.clear();
            map.put("errorCode", -1);
            map.put("errorMsg", ioe.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

    @Operation(summary = "일용근로 공제 퍼센트 리스트 조회", description = "일용근로 공제 퍼센트 리스트를 출력합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
    })
    @GetMapping("tax")
    public ResponseEntity<Map<String, Object>> dailyLaborTaxList(@RequestParam("date") String date) {
        Map<String, Object> map = new HashMap<>();
        try{
            List<DailyLaborSalaryTO> list = dailyLaborSalaryServiceFacade.dailyLaborTaxList(date);
            map.put("dailyLaborTaxList", list);
            map.put("errorMsg", "success");
            map.put("errorCode", 0);
        }catch(Exception ioe){
            map.clear();
            map.put("errorCode", -1);
            map.put("errorMsg", ioe.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }
}
