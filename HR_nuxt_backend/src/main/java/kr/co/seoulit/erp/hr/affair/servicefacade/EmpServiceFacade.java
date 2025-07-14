package kr.co.seoulit.erp.hr.affair.servicefacade;

import kr.co.seoulit.erp.hr.affair.to.*;
import kr.co.seoulit.erp.hr.base.to.DeptTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface EmpServiceFacade {

	/////////////////////////75기 인사  직원/////////////////////////
	// 직원 리스트 조회
	public List<EmpTO> findEmpList();
	// 선택 직원 조회
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
	public ArrayList<AssignEmpTO> findAppointList();
	// 발령 승인 반려 내역 조회
	public ArrayList<AssignEmpTO> findAppointLog();
	// 발령 등록
	public void addAssignInfo(RegiAppTO appRegi);
	// 발령 승인 반려 내역 갱신
	public void updateAppointment(AppointTO appResult);
	// 발령 삭제
	public void deleteAppointment(HashMap<String, String> appResult);
	/////////////////////////75기 인사 발령 끝/////////////////////////

	/////////////////////////75기 인사 부서/////////////////////////
	public ArrayList<EmpTO> findDeptEmpList(String deptCode);
	/////////////////////////75기 인사 부서 끝/////////////////////////



	ArrayList<EmpTO> findAllEmpInfo(HashMap<String, String> map);
	void updateEmpInfo(EmpUpdateTO emp);
	public List<EmpTO> findEmpList(String dept); // findEmployeeListByDept
	public List<EmpTO> findEmpMemberList(String dept);





	/* public void modifyEmployee(EmpTO emp); */


	public ArrayList<DeptTO> findDeptList();

	public ArrayList<EmployeeBasicTO> getEmpBasicInfo(String empCode);








	// update
	public void updateDeptCode(HashMap<String, String> map);

	public ArrayList<BestEmpTO> bestEmp();



	//////////////////////////보너스/////////////////////////
    public EmpTO findBonusEmp(String empCode);

	public List<DeptTO> findEmpByDept(String deptCode);


	////////////////////기초정보 부서////////////////////////

	ArrayList<DeptTO> findDeptManageList();

	ArrayList<DeptTO> findDeptListDetail();
}
