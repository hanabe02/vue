package kr.co.seoulit.erp.hr.eval.contorller;

import kr.co.seoulit.erp.hr.eval.servicefacade.EvalServiceFacade;
import kr.co.seoulit.erp.hr.eval.to.EmpEvalTO;
import kr.co.seoulit.erp.hr.eval.to.EmpKpiTO;
import kr.co.seoulit.erp.hr.eval.to.SelEmpEvalTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;



@CrossOrigin("*")
@RequestMapping("/hr/eval/*")
@RestController
public class EmpEvalController {

    @Autowired
    private EvalServiceFacade evalServiceFacade;
    HashMap<String, Object> map = new HashMap<>();

    @RequestMapping("/selEmpEvalList")
    public HashMap<String, Object> selEmpEval() {
        try {
            ArrayList<SelEmpEvalTO> selEvalList = evalServiceFacade.selEmpEval();
            map.clear();
            map.put("selEvalList", selEvalList);
            map.put("errorMsg", "success");
            map.put("errorCode", 0);
        } catch (Exception e) {
            map.clear();
            map.put("errorCode", -1);
            map.put("errorMsg", e.getMessage());
        }
        System.out.println("값 돌려주기"+ map);
        return map;
    }

    @RequestMapping("/addEmpEval")
    public ArrayList<SelEmpEvalTO> addEmpEval(@RequestBody SelEmpEvalTO selEmpEvalTO) {
        ArrayList<SelEmpEvalTO> addEvalList = null;
        System.out.println("값 들어옴2"+selEmpEvalTO);
        try {
            addEvalList = evalServiceFacade.addEmpEval(selEmpEvalTO);
            map.clear();
            map.put("errorMsg", "success");
            map.put("errorCode", 0);
        } catch ( Exception e) {
            map.clear();
            map.put("errorCode", -1);
            map.put("errorMsg", e.getMessage());
        }
        System.out.println("값 돌려주기2"+map);
        return addEvalList;
    }

    @RequestMapping("/SearchEmpEval")
    public HashMap<String, Object> findEmpEval(@RequestParam String empCode, @RequestParam String searchYear) {
        System.out.println("empCode:"+empCode);
        System.out.println("searchYEar:"+searchYear);
        try {
            ArrayList<EmpEvalTO> evalList = evalServiceFacade.findEmpEval(empCode, searchYear);
            map.clear();
            map.put("evalList", evalList);
            map.put("errorMsg", "success");
            map.put("errorCode", 0);
        } catch (Exception ioe) {
            map.clear();
            map.put("errorCode", -1);
            map.put("errorMsg", ioe.getMessage());
        }
        System.out.println(map);
        return map;
    }

    @GetMapping("/searchEmpEvalByDept")
    public HashMap<String, Object> findEmpEvalByDept(@RequestParam String searchYear, @RequestParam String deptCode) {
        System.out.println("deptCode:"+deptCode);
        System.out.println("searchYear:"+searchYear);
        try {
            ArrayList<EmpEvalTO> evalList = evalServiceFacade.findEmpEvalByDept(searchYear, deptCode);
            map.clear();
            map.put("evalList", evalList);
            map.put("errorMsg", "success");
            map.put("errorCode", 0);
        } catch (Exception ioe) {
            map.clear();
            map.put("errorCode", -1);
            map.put("errorMsg", ioe.getMessage());
        }
        System.out.println(map);
        return map;
    }

    @GetMapping("/searchEmpEvalByStatus")
    public HashMap<String, Object> findEmpEvalByStatus(@RequestParam String searchYear, @RequestParam String status) {
        System.out.println("status:"+status);
        System.out.println("searchYEar:"+searchYear);
        try {
            ArrayList<EmpEvalTO> evalList = evalServiceFacade.findEmpEvalByStatus(searchYear, status);
            map.clear();
            map.put("evalList", evalList);
            map.put("errorMsg", "success");
            map.put("errorCode", 0);
        } catch (Exception ioe) {
            map.clear();
            map.put("errorCode", -1);
            map.put("errorMsg", ioe.getMessage());
        }
        System.out.println(map);
        return map;
    }

    @PostMapping("/updateEmpEval")
    public HashMap<String, Object> updateEmpEval(@RequestBody EmpEvalTO empEvalTO){
        System.out.println(empEvalTO);
        try {
            evalServiceFacade.updateEmpEval(empEvalTO);
        }catch(Exception err) {
            map.clear();
            map.put("errorCode", -1);
            map.put("errorMsg", err.getMessage());
        }
        return map;
    }

    @PostMapping("/updateEmpEvalByDept")
    public HashMap<String, Object> updateEmpEvalByDept(@RequestBody EmpEvalTO empEvalTO){
        System.out.println(empEvalTO);
        try {
            evalServiceFacade.updateEmpEvalByDept(empEvalTO);
        }catch(Exception err) {
            map.clear();
            map.put("errorCode", -1);
            map.put("errorMsg", err.getMessage());
        }
        return map;
    }

    @DeleteMapping("/removeEmpEval")
    public HashMap<String, Object> removeEmpEval(@RequestParam String empCode, @RequestParam String searchYear){
        try {
            evalServiceFacade.removeEmpEval(empCode, searchYear);
            map.put("errorMsg", "success");
            map.put("errorCode", 0);
        }catch(Exception err) {
            map.clear();
            map.put("errorCode", -1);
            map.put("errorMsg", err.getMessage());
        }
        System.out.println(map);
        return map;
    }

    // KPI 목표 삭제
    @DeleteMapping("/removeKpiPlan")
    public HashMap<String, Object> removeKpiPlan(@RequestParam String empCode, @RequestParam String targetYear, @RequestParam String performanceIndicator, @RequestParam String status) {
        try {
            EmpKpiTO kpiRegi = new EmpKpiTO();
            kpiRegi.setEmpCode(empCode);
            kpiRegi.setTargetYear(targetYear);
            kpiRegi.setPerformanceIndicator(performanceIndicator);
            kpiRegi.setStatus(status);

            evalServiceFacade.removeKpiPlan(kpiRegi);
        } catch (Exception err) {
            map.clear();
            map.put("errorCode", -1);
            map.put("errorMsg", err.getMessage());
        }
        System.out.println(map);
        return map;
    }

    @PostMapping("/registerEvalStatus")
    public HashMap<String, Object> registerEvalStatus(@RequestBody SelEmpEvalTO selEmpEvalTO){
        try {
            ArrayList<SelEmpEvalTO> approList =  evalServiceFacade.registerEvalStatus(selEmpEvalTO);
            map.put("approList", approList);
            map.put("errorMsg", "success");
            map.put("errorCode", 0);
        }catch(Exception err) {
            map.clear();
            map.put("errorCode", -1);
            map.put("errorMsg", err.getMessage());
        }
        System.out.println(map);
        return map;
    }

//    @PostMapping("/registerEvalStatus")
//    public HashMap<String, Object> registerEvalStatus(@RequestBody EmpEvalTO empEvalTO){
//        try {
//            evalServiceFacade.registerEvalStatus(empEvalTO);
//            map.put("errorMsg", "success");
//            map.put("errorCode", 0);
//        }catch(Exception err) {
//            map.clear();
//            map.put("errorCode", -1);
//            map.put("errorMsg", err.getMessage());
//        }
//        System.out.println(map);
//        return map;
//    }
    
    // KPI 상태 갱신
    @PostMapping("/addKpiRegi")
    public void appointEmployment(@RequestBody ArrayList<EmpKpiTO> kpiRegi) {

        try {
            System.out.println("kpiRegi : " + kpiRegi);
            for (EmpKpiTO regiKpi : kpiRegi) {
                System.out.println("kpiRegi : " + kpiRegi);
                evalServiceFacade.addKpiInfo(regiKpi);
            }

        } catch (Exception e2) {
            e2.printStackTrace();

        }
    }

    // KPI 성과 조회
    @RequestMapping("/getKpiPerf")
    public HashMap<String, Object> findEmpKpi(@RequestParam String targetYear, @RequestParam String empCode) {
        System.out.println("empCode:"+empCode);
        System.out.println("targetYear:"+targetYear);
        try {
            ArrayList<EmpKpiTO> kpiList = evalServiceFacade.findKpiPerf(empCode, targetYear);
            map.clear();
            map.put("kpiList", kpiList);
            map.put("errorMsg", "success");
            map.put("errorCode", 0);
        } catch (Exception ioe) {
            map.clear();
            map.put("errorCode", -1);
            map.put("errorMsg", ioe.getMessage());
        }
        System.out.println(map);
        return map;
    }

    // kpi 목표 조회
    @GetMapping("/getKpiPlan")
    public HashMap<String, Object> findKpiPlan(@RequestParam String targetYear, @RequestParam String status) {
        System.out.println("status:"+ status);
        System.out.println("targetYear:"+targetYear);
        try {
            ArrayList<EmpKpiTO> kpiList = evalServiceFacade.findKpiPlan(targetYear, status);
            map.clear();
            map.put("kpiList", kpiList);
            map.put("errorMsg", "success");
            map.put("errorCode", 0);
        } catch (Exception ioe) {
            map.clear();
            map.put("errorCode", -1);
            map.put("errorMsg", ioe.getMessage());
        }
        System.out.println(map);
        return map;
    }

    // 승인,반려 수정
    @PostMapping("/editKpiStatus")
    public HashMap<String, Object> editKpiStatus(@RequestBody EmpKpiTO EmpKpiTO){
        try {
            evalServiceFacade.editKpiStatus(EmpKpiTO);
            map.put("errorMsg", "success");
            map.put("errorCode", 0);
        }catch(Exception err) {
            map.clear();
            map.put("errorCode", -1);
            map.put("errorMsg", err.getMessage());
        }
        System.out.println(map);
        return map;
    }


    // KPI 성과 등록

    @PostMapping("/addKpiPerf")
    public HashMap<String, Object> addKpiPerf(@RequestBody EmpKpiTO EmpKpiTO){
        try {
            evalServiceFacade.addKpiPerf(EmpKpiTO);
            map.put("errorMsg", "success");
            map.put("errorCode", 0);
        }catch(Exception err) {
            map.clear();
            map.put("errorCode", -1);
            map.put("errorMsg", err.getMessage());
        }
        System.out.println(map);
        return map;
    }


}
