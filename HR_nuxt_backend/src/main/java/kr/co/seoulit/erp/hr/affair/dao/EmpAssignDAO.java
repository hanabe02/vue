package kr.co.seoulit.erp.hr.affair.dao;

import java.util.ArrayList;
import java.util.HashMap;

import kr.co.seoulit.erp.hr.affair.to.AppointTO;
import kr.co.seoulit.erp.hr.affair.to.RegiAppTO;
import org.apache.ibatis.annotations.Mapper;

import kr.co.seoulit.erp.hr.affair.to.AssignEmpTO;

@Mapper
public interface EmpAssignDAO {

	/////////////////////////75기 인사 발령/////////////////////////
	// 발령 리스트 조회
	public ArrayList<AssignEmpTO> selectAppointList();
	// 발령 승인 반려 내역 조회
	public ArrayList<AssignEmpTO> selectAppointLog();
	//발령 등록
	public void insertAssign(RegiAppTO map);
	// 발령 승인 내역 갱신
	public void updateAppoint(AppointTO map);
	// 발령 삭제
	public void deleteAppoint(HashMap<String, String> map);
	/////////////////////////75기 인사 발령 끝/////////////////////////


}
