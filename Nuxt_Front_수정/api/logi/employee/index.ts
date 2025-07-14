import { logiApi } from '@/api'

const EMPLOYEE_URL = '/employee'
const COMPANY_INFO_URL = '/companyInfo'
const EMPLOYEE_EDIT_URL = '/employeeEdit'
const EMPLOYEE_DELETE_URL = '/employeeDelete'
const EMPLOYEE_ADD_URL = '/employeeAdd'

function getEmployeeList() {
    return logiApi.get(`${EMPLOYEE_URL}`)
}
function getCompanyInfo() {
    return logiApi.get(`${COMPANY_INFO_URL}`)
}
function getEmpEdit(updatedEmployee: any) {
    return logiApi.get(`${EMPLOYEE_EDIT_URL}`, {
        params: updatedEmployee
    })
}
function getmpDelete(empCode: String) {
    console.log("api" + empCode);
    return logiApi.get(`${EMPLOYEE_DELETE_URL}`, {
        params: {
            empCode: empCode
        }
    })
}
function getEmployeeAdd(clientdata: any) {
    console.log(clientdata)
    return logiApi.get(`${EMPLOYEE_ADD_URL}`, {
        params: clientdata
    })
}

export {
    getEmployeeList,
    getCompanyInfo,
    getEmpEdit,
    getmpDelete,
    getEmployeeAdd,
}