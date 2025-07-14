package kr.co.seoulit.erp.hr.education.controller;

import kr.co.seoulit.erp.hr.affair.to.PrintCertificateTO;
import kr.co.seoulit.erp.hr.education.to.EduTO;
import kr.co.seoulit.erp.hr.education.to.PrintEduTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import kr.co.seoulit.erp.hr.education.servicefacade.EduServiceFacade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin("*")
@RequestMapping("/hr/edu/*")
@RestController
public class EduController {

    @Autowired
    private EduServiceFacade eduServiceFacade;

    HashMap<String, Object> map = new HashMap<>();
    private final ModelMap modelMap = new ModelMap();

    @RequestMapping("/requestEduList")
    public HashMap<String, Object> requestEduList(@RequestBody EduTO eduTO) {
        System.out.println("파라미터"+eduTO);
        try {
            eduServiceFacade.requestEduList(eduTO);
            map.clear();
            map.put("errorMsg", "success");
            map.put("errorCode", 0);
        } catch ( Exception e ) {
            map.clear();
            map.put("errorMsg", e.getMessage());
            map.put("errorCode", -1);
        }
        return map;
    }

    @RequestMapping("/selectEduNameList")
    public ArrayList<EduTO> selectEduList() {
        ArrayList<EduTO> selectEduNameList = null;
        try {
            selectEduNameList = eduServiceFacade.selEduNameList();
            map.clear();
            map.put("errorMsg", "success");
            map.put("errorCode", 0);
        } catch ( Exception e ) {
            map.clear();
            map.put("errorMsg", e.getMessage());
            map.put("errorCode", -1);
        }
        System.out.println(map);
        return selectEduNameList;
    }

    @RequestMapping("/registerEduList")
    public HashMap<String, Object> registerEduList(@RequestBody EduTO eduTO) {
        System.out.println(eduTO);
        try{
            eduServiceFacade.addEduList(eduTO);
            map.clear();
            map.put("errorMsg", "success");
            map.put("errorCode", 0);
        } catch (Exception e) {
            map.clear();
            map.put("errorMsg", e.getMessage());
            map.put("errorCode", -1);
        }
        System.out.println(map);
        return map;
    }

    @RequestMapping(value = "/approReqEdu", method = RequestMethod.POST)
    public HashMap<String, Object> approReqEdu(@RequestBody EduTO eduTO) {
        System.out.println("교육 디버깅 포인트1"+eduTO);
        try{
            eduServiceFacade.approReqEdu(eduTO);
            map.clear();
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

    @RequestMapping("/searchEduListByStatus")
    public ArrayList<EduTO> searchEduListByStatus(@RequestParam("sDate") String startDate, @RequestParam("eDate") String endDate, @RequestParam("status") String status) {
        System.out.println("교육 디버깅 포인트2"+"\n"+startDate+"\n"+endDate+"\n"+status);
        ArrayList<EduTO> selEduList = null;
        startDate = startDate.replace("-", "");
        endDate = endDate.replace("-", "");
        try{
            selEduList = eduServiceFacade.selEdu(startDate, endDate, status);
            map.clear();
            map.put("errorMsg", "success");
            map.put("errorCode", 0);
        } catch ( Exception e) {
            map.clear();
            map.put("errorCode", -1);
            map.put("errorMsg", e.getMessage());
        }
        System.out.println("값 돌려주기2"+selEduList);
        return selEduList;

    }

    @DeleteMapping("/removeEduList")
    public HashMap<String, Object> removeEduList(EduTO eduTO) {
        System.out.println("교육 디버깅 포인트3"+eduTO);
        try{
            ArrayList<EduTO> delEduList = eduServiceFacade.delEdu(eduTO);
            map.clear();
            map.put("delEduList", delEduList);
            map.put("errorMsg", "success");
            map.put("errorCode", 0);
        } catch (Exception e) {
            map.clear();
            map.put("errorCode", -1);
            map.put("errorMsg", e.getMessage());
        }
        System.out.println("값 돌려주기3"+map);
        return map;
    }

    @GetMapping("/eduList")
    public HashMap<String, Object> getAllEduList() {
        HashMap<String, Object> map = new HashMap<>();
        try {
            ArrayList<EduTO> eduList = eduServiceFacade.findAllEduList();
            map.put("eduList", eduList);
            map.put("errorMsg", "success");
            map.put("errorCode", 0);
        } catch (Exception e) {
            map.put("errorCode", -1);
            map.put("errorMsg", e.getMessage());
        }
        return map;
    }

    @PostMapping("/delReqEdu")
    public HashMap<String, Object> delReqEdu(@RequestBody EduTO eduTO) {
        System.out.println("신청취소 요청"+eduTO);
        try{
            ArrayList<EduTO> delEduList = eduServiceFacade.delReqEdu(eduTO);
            map.clear();
            map.put("delEduList", delEduList);
            map.put("errorMsg", "success");
            map.put("errorCode", 0);
        } catch (Exception e) {
            map.clear();
            map.put("errorCode", -1);
            map.put("errorMsg", e.getMessage());
        }
        System.out.println("값"+map);
        return map;
    }

    @PostMapping("/checkEduHistory")
    public HashMap<String, Object> checkEduHistory(@RequestBody EduTO eduTO) {
        try {
            ArrayList<EduTO> eduHistory = eduServiceFacade.checkEduHistory(eduTO);
                map.put("eduHistory", eduHistory);
                map.put("errorMsg", "success");
                map.put("errorCode", 0);
            } catch ( Exception e ) {
                map.put("errorMsg", e.getMessage());
                map.put("errorCode", -1);
                map.put("errorMsg", e.getMessage());
        }
        return map;
    }

    @GetMapping("/findEduList")
    public HashMap<String, Object> findApproEduList(@RequestParam String empCode, @RequestParam String deptName, @RequestParam String startDate, @RequestParam String endDate ) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            ArrayList<EduTO> eduList = eduServiceFacade.findApproEdu(empCode, deptName, startDate, endDate);
            map.put("eduList", eduList);
            map.put("errorMsg", "success");
            map.put("errorCode", 0);
        } catch (Exception e) {
            map.put("errorCode", -1);
            map.put("errorMsg", e.getMessage());
        }
        return map;
    }

    @PostMapping("/approEduList")
    public ModelMap modifyApproEduList(@RequestBody ArrayList<EduTO> eduList) {
        System.out.println("eduList"+eduList);
        try {
            eduServiceFacade.modifyApproEduList(eduList);
            modelMap.put("eduList", eduList);
            modelMap.put("errorMsg", "success");
            modelMap.put("errorCode", 0);
        } catch ( Exception e ) {
            modelMap.clear();
            modelMap.put("errorMsg", e.getMessage());
            modelMap.put("errorCode", -1);
        }
        return modelMap;
    }

    @PatchMapping("/updateUseDate/{eduCode}")
    public ResponseEntity<Map<String, Object>> upgradeUseDate(@PathVariable String eduCode) {
        try {
            eduServiceFacade.upgradeDate(eduCode);
            map.put("errorMsg", "success");
            map.put("errorCode", 0);
            return ResponseEntity.status(HttpStatus.OK).body(map);
        } catch ( Exception e ) {
            map.put("errorCode", -1);
            map.put("errorMsg", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
        }
    }

    @GetMapping("printEduCertificate")
    public ResponseEntity<Map<String, Object>> printEduCertificate(String eduCode) {
        try {
            PrintEduTO printCertificate = eduServiceFacade.printCertificate(eduCode);
            map.put("printCertificate", printCertificate);
            map.put("errorMsg", "success");
            map.put("errorCode", 0);
            return ResponseEntity.status(HttpStatus.OK).body(map);
        } catch (Exception e) {
            map.put("errorCode", -1);
            map.put("errorMsg", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
        }
    }
}

