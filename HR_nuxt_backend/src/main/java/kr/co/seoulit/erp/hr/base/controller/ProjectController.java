package kr.co.seoulit.erp.hr.base.controller;

import kr.co.seoulit.erp.hr.base.servicefacade.HrBaseServiceFacade;
import kr.co.seoulit.erp.hr.base.to.ProjectTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@CrossOrigin("*")
@RestController
@RequestMapping("/hr/base/*")
public class ProjectController {
    @Autowired
    private HrBaseServiceFacade baseServiceFacade;

    @GetMapping("/project")
    public HashMap<String, Object> findProjectList() {
        HashMap<String, Object> map = new HashMap<>();
        try {
            ArrayList<ProjectTO> projectList = baseServiceFacade.findProjectList();
            map.put("projectList", projectList);
            map.put("errorMsg", "success");
            map.put("errorCode", 0);
        } catch (Exception ioe) {
            map.put("errorCode", -1);
            map.put("errorMsg", ioe.getMessage());
        }
        return map;
    }

    @PostMapping("/project")
    public HashMap<String, Object> addProject(@RequestBody ProjectTO projectTO){
        HashMap<String, Object> map = new HashMap<>();
        System.out.println(projectTO);
        try {
            baseServiceFacade.addProject(projectTO);
            map.put("errorMsg", "success");
            map.put("errorCode", 0);
        } catch (Exception ioe) {
            map.put("errorCode", -1);
            map.put("errorMsg", ioe.getMessage());
        }
        return map;
    }

    @PutMapping("/project")
    public HashMap<String, Object> modifyProject(@RequestBody ProjectTO projectTO) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            baseServiceFacade.modifyProject(projectTO);
            map.put("errorMsg", "success");
            map.put("errorCode", 0);
        } catch (Exception ioe) {
            map.put("errorCode", -1);
            map.put("errorMsg", ioe.getMessage());
        }
        return map;
    }

    @DeleteMapping("/project")
    public HashMap<String, Object> removeProject(@RequestParam String projectID){
        HashMap<String, Object> map = new HashMap<>();
        try {
            baseServiceFacade.removeProject(projectID);
            map.put("errorMsg", "success");
            map.put("errorCode", 0);
        } catch (Exception ioe) {
            map.put("errorCode", -1);
            map.put("errorMsg", ioe.getMessage());
        }
        return map;
    }
}
