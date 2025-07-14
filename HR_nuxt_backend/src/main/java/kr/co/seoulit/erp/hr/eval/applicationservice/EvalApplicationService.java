package kr.co.seoulit.erp.hr.eval.applicationservice;

import kr.co.seoulit.erp.hr.eval.to.EmpEvalTO;
import kr.co.seoulit.erp.hr.eval.to.EmpKpiTO;
import kr.co.seoulit.erp.hr.eval.to.SelEmpEvalTO;

import java.util.ArrayList;

public interface EvalApplicationService {

    public void updateEmpEval(EmpEvalTO empEvalTO);
    public void updateEmpEvalByDept(EmpEvalTO empEvalTO);
    public ArrayList<EmpEvalTO> findEmpEval(String empCode, String searchYear);
    public ArrayList<EmpKpiTO> findKpiPerf(String empCode, String targetYear);
    public ArrayList<EmpKpiTO> findKpiPlan(String targetYear, String status);
    public ArrayList<EmpEvalTO> findEmpEvalByDept(String deptName, String searchYear);
    public ArrayList<EmpEvalTO> findEmpEvalByStatus(String searchYear, String status);
    public void removeEmpEval(String empCode, String searchYear);
    public ArrayList<SelEmpEvalTO> registerEvalStatus(SelEmpEvalTO selEmpEvalTO);
    public void insertKpi(EmpKpiTO kpiRegi);
    public void removeKpiPlan(EmpKpiTO empKpiTO);
    public void editKpiStatus(EmpKpiTO empKpiTO);
    public void addKpiPerf(EmpKpiTO empKpiTO);
    public ArrayList<SelEmpEvalTO> selEmpEval();
    public ArrayList<SelEmpEvalTO> addEmpEval(SelEmpEvalTO selEmpEvalTO);

}
