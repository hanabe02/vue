package kr.co.seoulit.erp.hr.base.dao;

import kr.co.seoulit.erp.hr.base.to.ProjectTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface ProjectDAO {
    ArrayList<ProjectTO> selectProjectAllList();
    void insertProject(ProjectTO projectTO);

    void updateProject(ProjectTO projectTO);
    void deleteProject(String projectID);
}
