package kr.co.seoulit.erp.hr.base.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "HOLIDAY")
@Data
@NoArgsConstructor
public class HolidayEntity {
    @Id
    private String applyDay;
    private String holidayName;
    private String holidayType;
    private String holidayCode;
//    private String status;
//    private String dateName;
//    private String locdate;
}
