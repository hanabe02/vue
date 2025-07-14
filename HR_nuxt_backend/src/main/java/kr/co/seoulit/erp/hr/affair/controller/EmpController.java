package kr.co.seoulit.erp.hr.affair.controller;



import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.seoulit.erp.hr.affair.Exception.UserNotFoundException;
import kr.co.seoulit.erp.hr.affair.servicefacade.EmpServiceFacade;
import kr.co.seoulit.erp.hr.affair.to.EmpTO;
import kr.co.seoulit.erp.hr.affair.to.EmpUpdateTO;
import kr.co.seoulit.erp.hr.affair.to.EmployeeDetailTO;
import kr.co.seoulit.erp.hr.affair.to.RegiEmpTO;
import kr.co.seoulit.erp.hr.base.to.DeptTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@Tag(name = "직원관리", description = "직원관리 API")
@RestController
@RequestMapping("/hr/affair/*")
public class EmpController {

    @Autowired
    private EmpServiceFacade empServiceFacade;
    private String empCode = null;


    /**
     * <h2>RESTFUL API</h2>
     * 해당컨트롤러는 뷰에서 구현한 인사관리의 용도에 맞게 재설정되었습니다.<br/>
     * EmpAssign , EmpDetail, EmpRegist Controller를 사용하지 않는쪽으로 설정<br/>
     * Success 에러코드,에러메세지 AOP 설정은 시간관계상 하지않았습니다.<br/>
     * 직접구현하시면 도움이 되실듯합니다.
     * //
     **/
    /////////////////////////75기 인사  직원/////////////////////////
    //파일 등록용
    public String empcode() {return this.empCode;}

    // 직원 리스트 조회
    @Operation(summary = "직원 리스트 조회", description = "직원 리스트를 출력합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
    })
    @GetMapping("emp")
    public ResponseEntity<Map<String, Object>> empList() {

        Map<String, Object> map = new HashMap<>();
        List<EmpTO> list = empServiceFacade.findEmpList();

        if (list.isEmpty())
            throw new UserNotFoundException("사용자 정보가 없습니다.");

        map.put("empList", list);
        map.put("errorMsg", "success");
        map.put("errorCode", 0);

        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

    // 직원 선택 조회
    @Operation(summary = "직원 선택 조회", description = "직원 정보를 출력합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
    })
    @GetMapping("/emp/{clickEmp}")
    public ResponseEntity<Map<String, Object>> findEmp(@PathVariable String clickEmp) {
        Map<String, Object> map = new HashMap<>();
        ArrayList<EmployeeDetailTO> empDetail = empServiceFacade.findEmpDetail(clickEmp);
        System.out.println(empDetail);
            if(empDetail.isEmpty())
                throw new UserNotFoundException("검색된 사용자 정보가 없습니다.");

            map.put("empDetailInfo", empDetail);
            map.put("errorCode", 1);
            map.put("errorMsg", "success");
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }
    // 직원 정보 수정
    @Operation(summary = "직원 정보 수정", description = "직원 정보를 수정합니다..")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
    })
    @PutMapping("/emp")
    public ResponseEntity<Map<String, Object>> empInfoUpdate(@RequestBody EmpUpdateTO empBean) {
        HashMap<String, Object> map = new HashMap<>();
        this.empCode = empBean.getEmpCode();
        try {
            System.out.println("=========================EmpDetailController  empInfoUpdate()=======================");
            System.out.println("1111" + empBean);
            System.out.println("2222" + this.empCode);

            empServiceFacade.empInfoUpdate(empBean);
            map.put("errorCode", 0);
            map.put("errorMsg", "success");
            return ResponseEntity.ok(map);
        } catch (Exception e2) {
            e2.printStackTrace();
            map.put("errorCode", -1);
            map.put("errorMsg", e2.getMessage());
        }
        return null;
    }

    // 직원 삭제
    @Operation(summary = "직원 삭제", description = "직원을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
    })
    @DeleteMapping("/emp/{empCode}")
    public ResponseEntity<Map<String, Object>> deleteEmp(@PathVariable String empCode) {
        HashMap<String, Object> map = new HashMap<>();

        System.out.println("delete 자료" + empCode);
        try {
            empServiceFacade.deleteEmp(empCode);
            String filePath = "@\\assets\\images\\avatars\\" + empCode + ".png";
            File file = new File(filePath);
            if (file.delete()) {
                System.out.println("파일 삭제 성공");
            } else {
                System.out.println("파일 삭제 실패");
            }
            map.put("errorMsg", "삭제되었습니다");
        } catch (Exception e) {
            map.put("errorMsg", "삭제실패하였습니다");
        }
        return ResponseEntity.ok(map);
    }
    //직원 등록
    @Operation(summary = "직원 등록", description = "직원을 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
    })
    @PostMapping(value="emp")
    public ResponseEntity<Map<String, Object>> regiEmployee(@RequestBody RegiEmpTO regiEmp) {
        HashMap<String, Object> map = new HashMap<>();
        this.empCode=regiEmp.getEmpCode();
        System.out.println(this.empCode);
        try {
            System.out.println("사원등록 데이타"+regiEmp);
            empServiceFacade.registEmployee(regiEmp);
            map.put("errorMsg","사원이 등록되었습니다.");
            map.put("errorCode", 0);
            return ResponseEntity.ok(map);
        } catch (Exception dae){
            map.put("errorMsg", "사원 등록에 실패했습니다 : "+dae.getMessage());
            map.put("errorCode", -1);
        }
        return null;
    }
    /////////////////////////75기 인사  직원 끝/////////////////////////

    /////////////////////////75기 인사  부서 조회/////////////////////////
    @GetMapping("/emp/findDeptEmpList")
    public Map<String, Object> findDeptEmpList(@RequestParam String deptCode){
        System.out.println("부서직원 조회"+deptCode);
        HashMap<String, Object> map = new HashMap<>();
        try{
            ArrayList<EmpTO> deptEmpList = empServiceFacade.findDeptEmpList(deptCode);
            map.clear();
            map.put("deptEmpList", deptEmpList);
            map.put("errorMsg", "success");
            map.put("errorCode", 0);
        }catch(Exception ioe){
            map.clear();
            map.put("errorCode", -1);
            map.put("errorMsg", ioe.getMessage());
        }
        return map;
    }

    /////////////////////////75기 인사  부서 조회 끝 /////////////////////////




    @PutMapping("/empList")
    public ResponseEntity<EmpUpdateTO> updateEmp (@RequestBody EmpUpdateTO emp){

        empServiceFacade.updateEmpInfo(emp);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(emp)
                .toUri();

        return ResponseEntity.created(location).build();
    }



    ///////////////////////////////급여 - 보너스 적용 ////////////////////////////
    @RequestMapping(value = "/findBonusEmpDetail",method = RequestMethod.GET)
    public HashMap<String,Object> findBonusEmp(@RequestParam String empCode){
        HashMap<String, Object> map = new HashMap<String, Object>();
        try {
            EmpTO emp = empServiceFacade.findBonusEmp(empCode);
            map.put("emp", emp);
            map.put("errorMsg","success");
            map.put("errorCode", 0);

        } catch (Exception ioe) {
            map.clear();
            map.put("errorCode", -1);
            map.put("errorMsg", ioe.getMessage());
        }

        return map;
    }


    ////////////////////회계에서 사용//////////////////////////////////////
    @RequestMapping(value="/empNameByDept", method=RequestMethod.GET )
    @ResponseBody
    public Map<String, Object> findEmpByDept(@RequestParam String deptCode){
        Map<String, Object> map = new HashMap<String, Object>();
        System.out.println("이거야?");
        List<DeptTO> list = empServiceFacade.findEmpByDept(deptCode);
        map.put("list", list);
        map.put("errorMsg","success");
        map.put("errorCode", 0);
        System.out.println(map.get("list"));
        return map;
    }



}
