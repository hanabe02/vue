package kr.co.seoulit.erp.hr.base.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.seoulit.erp.hr.base.to.DeptTO;

@Mapper
public interface DeptDAO {
	public ArrayList<DeptTO> selectDeptList();

	public void updateDept(DeptTO dept);
//	public void registDept(DeptTO dept);
	public void deleteDept(DeptTO dept);
	public void deleteDept(@Param("deptCode") String deptCode, @Param("workplaceCode") String workplaceCode);


	public ArrayList<DeptTO> selectEmpList();
	public ArrayList<DeptTO> selectDeptListDetail();
	public ArrayList<DeptTO> selectDeptManageList();
	public void insertDept(DeptTO dept);
}
