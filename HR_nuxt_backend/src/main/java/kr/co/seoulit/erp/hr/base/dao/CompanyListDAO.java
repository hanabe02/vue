package kr.co.seoulit.erp.hr.base.dao;

import kr.co.seoulit.erp.hr.company.to.CompanyTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface CompanyListDAO {
	public ArrayList<CompanyTO> findCompanyList();


}
