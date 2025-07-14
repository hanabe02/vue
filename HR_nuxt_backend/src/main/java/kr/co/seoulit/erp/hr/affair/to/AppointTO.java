package kr.co.seoulit.erp.hr.affair.to;

import kr.co.seoulit.common.to.BaseTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

//******************************************************08-27 손유찬 **************************************************** */
@Data
@EqualsAndHashCode(callSuper=false)
public class AppointTO extends BaseTO {
	private String appointNo;
	private String appointDept;
	private String appointDate;
	private String appointLevel;
	private String appointHobong;
	private String appointType;
	private String appointWP;
	private String deptCode;
	private String deptName;
	private String empCode;
	private String empName;
	private String positionCode;
	private String positionName;
	private String status;
	private String workPlaceCode;
	private String workPlaceName;














	//******************************************************08-27 손유찬 **************************************************** */

}
