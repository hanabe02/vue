package kr.co.seoulit.erp.hr.base.dao;

import kr.co.seoulit.erp.hr.company.to.WorkplaceTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface WorkplaceListDAO {
	public ArrayList<WorkplaceTO> findWorkplaceList();

}
