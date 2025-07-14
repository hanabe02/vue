package kr.co.seoulit.erp.logistic.transport.dao;

import kr.co.seoulit.erp.hr.attendance.to.AnnualVacationMgtTO;
import kr.co.seoulit.erp.logistic.transport.to.TransportTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.HashMap;


@Mapper
public interface TransportDAO {
	public ArrayList<TransportTO> searchTransportList(HashMap<String, String> param);
	public ArrayList<TransportTO> searchCommercialVehicleList();
	public ArrayList<TransportTO> searchTransportAbleList();
	public void updateTransportList(
			@Param("transportStatus") String transportStatus,
			@Param("contractNo") String contractNo,
			@Param("commercialVehicle") String commercialVehicle,
			@Param("driver") String driver,
			@Param("memo") String memo
			);
	public ArrayList<TransportTO> searchCommercialVehicleInList();
	public void updateCommercialVehicleInList(AnnualVacationMgtTO annualVacationMgtList);
}
