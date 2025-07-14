package kr.co.seoulit.erp.hr.eval.dao;

import kr.co.seoulit.erp.hr.eval.to.EmpEvalTO;
import kr.co.seoulit.erp.hr.eval.to.EmpKpiTO;
import kr.co.seoulit.erp.hr.eval.to.SelEmpEvalTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

    @Mapper
    public interface EmpEvalDAO {
        public ArrayList<EmpEvalTO> selectEmpEval(@Param("empCode")String empCode, @Param("searchYear") String searchYear);
        public ArrayList<EmpEvalTO> selectEmpEvalByDept(@Param("searchYear") String searchYear,@Param("deptCode") String deptCode);
        public ArrayList<EmpEvalTO> selectEmpEvalByStatus(@Param("searchYear") String searchYear,@Param("status") String status);
        public void insertEmpEval(EmpEvalTO empEvalTO);
        public void insertEmpEvalByDept(EmpEvalTO empEvalTO);
        public void deleteEmpEval(@Param("empCode") String empCode, @Param("searchYear") String searchYear);
        public ArrayList<SelEmpEvalTO> updateEvalStatus(SelEmpEvalTO selEmpEvalTO);

        //kpi 등록
        public void insertKpi(EmpKpiTO map);
        public ArrayList<EmpKpiTO> selectKpiPlan(@Param("targetYear") String targetYear,@Param("status") String status);
        public ArrayList<EmpKpiTO> selectKpiPerf(@Param("empCode") String empCode, @Param("targetYear") String targetYear);

        public void deleteKpiPlan(EmpKpiTO empKpiTO);
        public void updateKpiStatus(EmpKpiTO empKpi);
        public void insertKpiPerf(EmpKpiTO empKpi);

        public ArrayList<SelEmpEvalTO> selEmpEval();
        public ArrayList<SelEmpEvalTO> addEmpEval(SelEmpEvalTO selEmpEvalTO);
//        public List<SelEmpEvalTO> requestStatus(SelEmpEvalTO selEmpEvalTO);
    }
