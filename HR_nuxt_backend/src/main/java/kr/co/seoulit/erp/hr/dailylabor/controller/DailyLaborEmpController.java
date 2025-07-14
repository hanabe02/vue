package kr.co.seoulit.erp.hr.dailylabor.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.seoulit.erp.hr.dailylabor.servicefacade.DailyLaborEmpServiceFacade;
import kr.co.seoulit.erp.hr.dailylabor.Exception.DailyLaborUserNotFoundException;
import kr.co.seoulit.erp.hr.dailylabor.to.DailyLaborEmpLogTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import kr.co.seoulit.erp.hr.dailylabor.to.DailyLaborEmpTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@Tag(name = "일용근로 직원관리", description = "일용근로 직원관리 API")
@RestController
@RequestMapping("/hr/dailyemp/*")
public class DailyLaborEmpController {

    @Autowired
    private DailyLaborEmpServiceFacade dailyLaborEmpServiceFacade;

    @Operation(summary = "일용근로 직원 리스트 조회", description = "직원 리스트를 출력합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
    })
    @GetMapping("emp")
    public ResponseEntity<Map<String, Object>> dailyLaborEmpList() {

        Map<String, Object> map = new HashMap<>();
        List<DailyLaborEmpTO> list = dailyLaborEmpServiceFacade.findDailyLaborEmpList();

        if (list.isEmpty())
            throw new DailyLaborUserNotFoundException("사용자 정보가 없습니다.");

        map.put("dailyLaborEmpList", list);
        map.put("errorMsg", "success");
        map.put("errorCode", 0);

        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

    @Operation(summary = "일용직원 선택 조회", description = "일용직원 정보를 출력합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
    })
    @GetMapping("/emp/{clickEmp}")
    public ResponseEntity<Map<String, Object>> findDailyLaborEmp(@PathVariable String clickEmp) {
        Map<String, Object> map = new HashMap<>();
        ArrayList<DailyLaborEmpTO> empDetail = dailyLaborEmpServiceFacade.findEmpDetail(clickEmp);
        System.out.println(empDetail);
        if(empDetail.isEmpty())
            throw new DailyLaborUserNotFoundException("검색된 사용자 정보가 없습니다.");

        map.put("dailyLaborEmpDetailInfo", empDetail);
        map.put("errorCode", 1);
        map.put("errorMsg", "success");
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

    @Operation(summary = "일용근로 직원 등록", description = "직원을 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
    })
    @PostMapping(value="emp")
    public ResponseEntity<Map<String, Object>> dailyLaborRegiEmployee(@RequestBody DailyLaborEmpTO dailyLaborEmpTO) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            System.out.println("========================일용근로 직원 정보 등록하겠습니다=======================");
            System.out.println("직원등록 데이타"+dailyLaborEmpTO);
            dailyLaborEmpServiceFacade.dailyLaborRegiEmployee(dailyLaborEmpTO);
            map.put("errorMsg","직원이 등록되었습니다.");
            map.put("errorCode", 0);
            return ResponseEntity.ok(map);
        } catch (Exception dae){
            map.put("errorMsg", "직원 등록에 실패했습니다 : "+dae.getMessage());
            map.put("errorCode", -1);
        }
        return null;
    }

    @Operation(summary = "일용근로 직원 정보 수정", description = "일용근로 직원 정보를 수정합니다..")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
    })
    @PutMapping("/emp")
    public ResponseEntity<Map<String, Object>> dailyLaborEmpInfoUpdate(@RequestBody DailyLaborEmpTO dailyLaborEmpTO) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            System.out.println("========================일용근로 직원 정보 수정하겠습니다=======================");
            dailyLaborEmpServiceFacade.dailyLaborEmpInfoUpdate(dailyLaborEmpTO);
            map.put("errorCode", 0);
            map.put("errorMsg", "성공");
            return ResponseEntity.ok(map);
        } catch (Exception e2) {
            e2.printStackTrace();
            map.put("errorCode", -1);
            map.put("errorMsg", e2.getMessage());
        }
        return null;
    }

    @Operation(summary = "일용근로 직원 정보 삭제", description = "일용근로 직원 정보를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
    })
    @PutMapping("/deleteemp")
    public ResponseEntity<Map<String, Object>> deleteDailyLaborEmp(@RequestBody DailyLaborEmpTO dailyLaborEmpTO) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            System.out.println("========================일용근로 직원 정보 삭제하겠습니다=======================");
            dailyLaborEmpServiceFacade.deleteDailyLaborEmp(dailyLaborEmpTO);
            map.put("errorCode", 0);
            map.put("errorMsg", "성공");
            return ResponseEntity.ok(map);
        } catch (Exception e2) {
            e2.printStackTrace();
            map.put("errorCode", -1);
            map.put("errorMsg", e2.getMessage());
        }
        return null;
    }

    @Operation(summary = "일용근로 직원이력 리스트 조회", description = "직원이력 리스트를 출력합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
    })
    @GetMapping("/log")
    public ResponseEntity<Map<String, Object>> dailyLaborEmpLogList() {

        Map<String, Object> map = new HashMap<>();
        List<DailyLaborEmpLogTO> list = dailyLaborEmpServiceFacade.dailyLaborEmpLogList();

        if (list.isEmpty())
            throw new DailyLaborUserNotFoundException("사용자 이력이 없습니다.");

        map.put("dailyLaborEmpLogList", list);
        map.put("errorMsg", "success");
        map.put("errorCode", 0);

        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

    @Operation(summary = "일용근로 직원 정보 삭제", description = "일용근로 직원 정보를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
    })
    @PutMapping("/log")
    public ResponseEntity<Map<String, Object>> deleteDailyLaborEmpLog(@RequestBody List<DailyLaborEmpLogTO> dailyLaborEmpLogTO) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            System.out.println("========================일용근로 직원 정보 삭제하겠습니다=======================");
            dailyLaborEmpServiceFacade.deleteDailyLaborEmpLog(dailyLaborEmpLogTO);
            map.put("errorCode", 0);
            map.put("errorMsg", "성공");
            return ResponseEntity.ok(map);
        } catch (Exception e2) {
            e2.printStackTrace();
            map.put("errorCode", -1);
            map.put("errorMsg", e2.getMessage());
        }
        return null;
    }
}


