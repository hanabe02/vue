export interface AttenHeaders { 
  title: string, 
  key: string, 
  width?: number,
}

type AttenProps = {
  text: string;
  value: string;
}[]


type DayAttdProps = {
  empCode: string,
  applyDay: string,
  attdTypeCode: string,
  attdTypeName: string,
  time: string,
}

type DeptItemProps = {
  deptCode: deptCode;
  deptName: deptName;
}

type EmpItemProps = {
  empCode: empCode;
  empName: empName;
}

type MonthItemProps = {
  month: month;
}

type YearItemProps = {
  year: year;
}

type ItemPropsTypes = {
  text: text;
  value: value;
}
