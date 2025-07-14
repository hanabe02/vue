package kr.co.seoulit.erp.hr.eval.servicefacade;

import kr.co.seoulit.erp.hr.eval.applicationservice.EvalApplicationService;
import kr.co.seoulit.erp.hr.eval.to.EmpEvalTO;
import kr.co.seoulit.erp.hr.eval.to.EmpKpiTO;
import kr.co.seoulit.erp.hr.eval.to.SelEmpEvalTO;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class EvalServiceFacadeImpl implements EvalServiceFacade{
    @Autowired
    private EvalApplicationService evalApplicationService;

    @Override
    public ArrayList<EmpEvalTO> findEmpEval(String empCode, String searchYear){
    ArrayList<EmpEvalTO> evalList=evalApplicationService.findEmpEval(empCode, searchYear);
    return evalList;
    }
    @Override
    public ArrayList<EmpKpiTO> findKpiPerf(String empCode, String targetYear){
        ArrayList<EmpKpiTO> kpiList=evalApplicationService.findKpiPerf(empCode, targetYear);
        return kpiList;
    }

    @Override
    public ArrayList<EmpKpiTO> findKpiPlan(String targetYear, String status){
        ArrayList<EmpKpiTO> kpiList=evalApplicationService.findKpiPlan(targetYear, status);
        return kpiList;
    }

    @Override
    public ArrayList<EmpEvalTO> findEmpEvalByDept(String searchYear, String deptCode) {
    ArrayList<EmpEvalTO> evalList = evalApplicationService.findEmpEvalByDept(searchYear, deptCode);
    return evalList;
    }

    @Override
    public ArrayList<EmpEvalTO> findEmpEvalByStatus(String searchYear, String status) {
        ArrayList<EmpEvalTO> evalList = evalApplicationService.findEmpEvalByStatus(searchYear, status);
        return evalList;
    }

    @Override
    public void updateEmpEval(EmpEvalTO empEvalTO){
    // TODO Auto-generated method stub
    evalApplicationService.updateEmpEval(empEvalTO);
    }

    @Override
    public void updateEmpEvalByDept(EmpEvalTO empEvalTO){
//        String deptCode=EmpEvalTO.getDeptCode();
//        String searchYear=EmpEvalTO.getSearchYear();
        // TODO Auto-generated method stub
        evalApplicationService.updateEmpEvalByDept(empEvalTO);
    }

    @Override
    public void removeEmpEval(String empCode, String searchYear) {
        // TODO Auto-generated method stub
        evalApplicationService.removeEmpEval(empCode, searchYear);
    }
    @Override
    public void removeKpiPlan(EmpKpiTO empKpiTO) {
        // TODO Auto-generated method stub
        evalApplicationService.removeKpiPlan(empKpiTO);
    }

    @Override
    public ArrayList<SelEmpEvalTO> registerEvalStatus(SelEmpEvalTO selEmpEvalTO) {
        // TODO Auto-generated method stub
        ArrayList<SelEmpEvalTO> approList = evalApplicationService.registerEvalStatus(selEmpEvalTO);
        return approList;
    }

    // kpi 등록
    @Override
    public void addKpiInfo(EmpKpiTO kpiRegi) {
        evalApplicationService.insertKpi(kpiRegi);
    }

    @Override
    public void editKpiStatus(EmpKpiTO empKpiTO) {
        // TODO Auto-generated method stub
        evalApplicationService.editKpiStatus(empKpiTO);
    }

    @Override
    public void addKpiPerf(EmpKpiTO empKpiTO) {
        // TODO Auto-generated method stub
        evalApplicationService.addKpiPerf(empKpiTO);
    }

    @Override
    public ArrayList<SelEmpEvalTO> selEmpEval() {
        ArrayList<SelEmpEvalTO> selEvalList = evalApplicationService.selEmpEval();
        return selEvalList;
    }

    @Override
    public ArrayList<SelEmpEvalTO> addEmpEval(SelEmpEvalTO selEmpEvalTO) {
        ArrayList<SelEmpEvalTO> addEmpEval = evalApplicationService.addEmpEval(selEmpEvalTO);
        return addEmpEval;
    }

}
