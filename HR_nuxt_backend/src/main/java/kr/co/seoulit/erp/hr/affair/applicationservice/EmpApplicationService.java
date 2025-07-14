package kr.co.seoulit.erp.hr.affair.applicationservice;

import kr.co.seoulit.erp.hr.affair.to.*;
import kr.co.seoulit.erp.hr.base.to.DeptTO;
import kr.co.seoulit.erp.hr.company.to.PositionTO;
import kr.co.seoulit.erp.hr.salary.to.BaseSalaryTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface EmpApplicationService {


	/////////////////////////75기 인사  직원/////////////////////////
	// 직원 리스트 조회
	public ArrayList<EmpTO> findEmpList();
	//선택 직원 조회
	ArrayList<EmployeeDetailTO> findEmpDetail(String empCode);

	// 직원 정보 수정
	public void empInfoUpdate(EmpUpdateTO empBean);

	// 직원 삭제
	public void deleteEmp(String empBean);
	// 직원 등록
	public void registEmployee(RegiEmpTO regiEmp);
	/////////////////////////75기 인사  직원 끝/////////////////////////


	/////////////////////////75기 인사 발령/////////////////////////
	//발정 리스트 조회
	public ArrayList<AssignEmpTO> getAppointList();
	// 발령 승인 반려 내역 조회
	public ArrayList<AssignEmpTO> findAppointLog();
	// 발령 등록
	public void insertAssign(RegiAppTO appRegi);
	// 발령 승인 반려 내역 갱신
	public void updateAppoint(AppointTO appResult);
	// 발령 삭제
	public void appointdelete(HashMap<String, String> appResult);
	/////////////////////////75기 인사 발령 끝/////////////////////////


	void updateEmpInfo(EmpUpdateTO emp);

	public ArrayList<EmpTO> findEmpMemberListByDept(String deptName);

	public ArrayList<EmpTO> findEmployeeListByDept(String deptName);



	/* public void modifyEmployee(EmpTO emp); */

	public ArrayList<DeptTO> findDeptList();

	public ArrayList<PositionTO> selectPositionList();

	public ArrayList<EmpTO> findAllEmpInfo(HashMap<String, String> map);

	public ArrayList<EmployeeBasicTO> getEmpBasicInfo(String empCode);

	// update
	public void updateDeptCode(HashMap<String, String> map);

	public ArrayList<BestEmpTO> getBestEmp();

	public EmpTO getBonusEmp(String empCode);

	public List<DeptTO> getEmpListByDept(String deptCode);


	////////////////////기초정보 부서////////////////////////
	public ArrayList<EmpTO> findDeptEmpList(String deptCode);

	public ArrayList<DeptTO> findDeptManageList();


}
