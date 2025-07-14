package kr.co.seoulit.erp.hr.eval.applicationservice;


import kr.co.seoulit.erp.hr.eval.dao.EmpEvalDAO;
import kr.co.seoulit.erp.hr.eval.to.EmpEvalTO;
import kr.co.seoulit.erp.hr.eval.to.EmpKpiTO;
import kr.co.seoulit.erp.hr.eval.to.SelEmpEvalTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class EvalApplicationServiceImpl implements EvalApplicationService {

	@Autowired
	private EmpEvalDAO empEvalDAO;

	@Override
	public ArrayList<EmpEvalTO> findEmpEval(String empCode, String searchYear) {

		ArrayList<EmpEvalTO> evalList = empEvalDAO.selectEmpEval(empCode, searchYear);
		return evalList;
	}

	@Override
	public ArrayList<EmpEvalTO> findEmpEvalByDept(String searchYear, String deptCode) {

		ArrayList<EmpEvalTO> evalList = empEvalDAO.selectEmpEvalByDept(searchYear, deptCode);
		return evalList;
	}

	@Override
	public ArrayList<EmpEvalTO> findEmpEvalByStatus(String searchYear, String status) {

		ArrayList<EmpEvalTO> evalList = empEvalDAO.selectEmpEvalByStatus(searchYear, status);
		return evalList;
	}

	@Override
	public void removeEmpEval(String empCode, String searchYear) {
		// TODO Auto-generated method stub
		System.out.println("empCode = " + empCode);
		System.out.println("searchYear = " + searchYear);
		empEvalDAO.deleteEmpEval(empCode, searchYear);
	}

	@Override
	public void  updateEmpEval(EmpEvalTO empEvalTO) {
		// TODO Auto-generated method stub
		empEvalDAO.insertEmpEval(empEvalTO);
	}

	@Override
	public void  updateEmpEvalByDept(EmpEvalTO empEvalTO) {
		// TODO Auto-generated method stub
		empEvalDAO.insertEmpEvalByDept(empEvalTO);
	}

	@Override
	public ArrayList<SelEmpEvalTO>  registerEvalStatus(SelEmpEvalTO selEmpEvalTO) {
		// TODO Auto-generated method stub
		ArrayList<SelEmpEvalTO> approList = empEvalDAO.updateEvalStatus(selEmpEvalTO);
		return approList;
	}

	// KPI 등록
	@Override
	public void insertKpi(EmpKpiTO kpiRegi) {
		// TODO Auto-generated method stub
		System.out.println("===============KPI등록~~==============");
		System.out.println(kpiRegi);
		empEvalDAO.insertKpi(kpiRegi);
	}

	@Override
	public ArrayList<EmpKpiTO> findKpiPlan(String targetYear, String status) {

		ArrayList<EmpKpiTO> kpiList = empEvalDAO.selectKpiPlan(targetYear, status);
		return kpiList;
	}

	@Override
	public ArrayList<EmpKpiTO> findKpiPerf(String empCode, String targetYear) {

		ArrayList<EmpKpiTO> kpiList = empEvalDAO.selectKpiPerf(empCode, targetYear);
		return kpiList;
	}

	@Override
	public void removeKpiPlan(EmpKpiTO empKpiTO) {
		// TODO Auto-generated method stub
		empEvalDAO.deleteKpiPlan(empKpiTO);
	}


	@Override
	public void editKpiStatus(EmpKpiTO empKpiTO) {
		// TODO Auto-generated method stub
		empEvalDAO.updateKpiStatus(empKpiTO);
	}

	@Override
	public void addKpiPerf(EmpKpiTO empKpiTO) {
		// TODO Auto-generated method stub
		empEvalDAO.insertKpiPerf(empKpiTO);
	}

	@Override
	public ArrayList<SelEmpEvalTO> selEmpEval() {
		ArrayList<SelEmpEvalTO> selEvalList = empEvalDAO.selEmpEval();
		return selEvalList;
	}

	@Override
	public ArrayList<SelEmpEvalTO> addEmpEval(SelEmpEvalTO selEmpEvalTO) {
		ArrayList<SelEmpEvalTO> addEmpEvalList = empEvalDAO.addEmpEval(selEmpEvalTO);
		return addEmpEvalList;
	}
}
