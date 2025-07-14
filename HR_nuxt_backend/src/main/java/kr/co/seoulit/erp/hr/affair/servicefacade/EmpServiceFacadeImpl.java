package kr.co.seoulit.erp.hr.affair.servicefacade;

import kr.co.seoulit.erp.hr.affair.applicationservice.EmpApplicationService;
import kr.co.seoulit.erp.hr.affair.to.*;
import kr.co.seoulit.erp.hr.base.to.DeptTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class EmpServiceFacadeImpl implements EmpServiceFacade {
	@Autowired
	private EmpApplicationService empApplicationService;

	/////////////////////////75기 인사  직원/////////////////////////
	// 직원 리스트 조회
	@Override
	public List<EmpTO> findEmpList() {
		// TODO Auto-generated method stub
		ArrayList<EmpTO> empList = empApplicationService.findEmpList();
		return empList;
	}
	//선택 직원 조회
	@Override
	public ArrayList<EmployeeDetailTO> findEmpDetail(String empCode){
		System.out.println(empCode);
		return empApplicationService.findEmpDetail(empCode);
	}
	// 직원 정보 수정
	@Override
	public void empInfoUpdate(EmpUpdateTO empBean) {
		System.out.println("=============================EmpServiceFacadeImpl===================================");
		System.out.println("EmpServiceFacade" + empBean);
		empApplicationService.empInfoUpdate(empBean);
	}
	// 직원 삭제
	@Override
	public void deleteEmp(String empBean) {
		System.out.println("=============================EmpServiceFacadeImpl===================================");
		System.out.println("deleteEmp"+empBean);
		empApplicationService.deleteEmp(empBean);
	}
	// 직원 등록
	@Override
	public void registEmployee(RegiEmpTO regiEmp) {
		empApplicationService.registEmployee(regiEmp);
	}
	/////////////////////////75기 인사  직원 끝/////////////////////////

	/////////////////////////75기 인사 발령/////////////////////////
	//발정 리스트 조회
	@Override
	public ArrayList<AssignEmpTO> findAppointList() {
		// TODO Auto-generated method stub
		return empApplicationService.getAppointList();
	}
	// 발령 승인 반려 내역 조회
	@Override
	public ArrayList<AssignEmpTO> findAppointLog(){
		return  empApplicationService.findAppointLog();
	}
	// 발령 등록
	@Override
	public void addAssignInfo(RegiAppTO appRegi) {
		empApplicationService.insertAssign(appRegi);
	}
	// 발령 승인 반려 내역 갱신
	@Override
	public void updateAppointment(AppointTO appResult){
		empApplicationService.updateAppoint(appResult);
	}
	// 발령 삭제
	@Override
	public void deleteAppointment(HashMap<String, String> appResult){
		empApplicationService.appointdelete(appResult);
	}
	/////////////////////////75기 인사 발령 끝/////////////////////////


	@Override
	public List<EmpTO> findEmpList(String dept) {
		ArrayList<EmpTO> empList = empApplicationService.findEmployeeListByDept(dept);
		return empList;
	}

	public List<EmpTO> findEmpMemberList(String dept) {
		ArrayList<EmpTO> empList = empApplicationService.findEmpMemberListByDept(dept);
		return empList;
	}

	@Override
	public ArrayList<EmpTO> findAllEmpInfo(HashMap<String, String> map) {

		ArrayList<EmpTO> empDetailedList = null;
		try {
			System.out.println("=============================EmpServiceFacadeImpl===================================");

			empDetailedList = empApplicationService.findAllEmpInfo(map);

		} catch (DataAccessException e) {
			e.printStackTrace();
			throw e;
		}

		return empDetailedList;

	}

	@Override
	public ArrayList<DeptTO> findDeptList() {
		ArrayList<DeptTO> deptList = empApplicationService.findDeptList();
		return deptList;
	}



	@Override
	public void updateEmpInfo(EmpUpdateTO emp){
		empApplicationService.updateEmpInfo(emp);
	}

	// 은비 사원 기본정보 조회
	@Override
	public ArrayList<EmployeeBasicTO> getEmpBasicInfo(String empCode) {

		ArrayList<EmployeeBasicTO> basicInfoList = null;

		try {

			basicInfoList = empApplicationService.getEmpBasicInfo(empCode);

		} catch (DataAccessException e) {
			e.printStackTrace();
			throw e;
		}

		return basicInfoList;
	}





	@Override
	public void updateDeptCode(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		empApplicationService.updateDeptCode(map);

	}

	// 범석 best 사원 뽑기
	@Override
	public ArrayList<BestEmpTO> bestEmp() {

		System.out.println("facade");
		ArrayList<BestEmpTO> bastEmp = null;
		bastEmp = empApplicationService.getBestEmp();

		return bastEmp;

	}

	@Override
	public EmpTO findBonusEmp(String empCode) {
		return empApplicationService.getBonusEmp(empCode);
	}

	@Override
	public List<DeptTO> findEmpByDept(String deptCode) {
		return  empApplicationService.getEmpListByDept(deptCode);
	}




	////////////////////기초정보 부서////////////////////////

	@Override
	public ArrayList<EmpTO> findDeptEmpList(String deptCode) {
		System.out.println("부서직원 조회"+deptCode);
		// TODO Auto-generated method stub
		return empApplicationService.findDeptEmpList(deptCode);
	}


	@Override
	public ArrayList<DeptTO> findDeptManageList() {
		// TODO Auto-generated method stub
		return empApplicationService.findDeptManageList();
	}

	@Override
	public ArrayList<DeptTO> findDeptListDetail() {
		return null;
	}
}
