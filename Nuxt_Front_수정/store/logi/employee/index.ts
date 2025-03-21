import { defineStore } from 'pinia';
import { getEmployeeList, getCompanyInfo, getEmpEdit, getmpDelete, getEmployeeAdd } from '@/api/logi/employee'

export const employeeStore = defineStore('employeeStore', {
    state: () => ({
        employeeInfo: [] as any,
        compnayInfo: [] as any,
        employeeEdit: [] as any,
        employeeDelete: [] as any,
    }),
    actions: {
        async GET_EMP_INFO() {
            try {
                const res = await getEmployeeList()
                console.log(res.data.employeeList);
                this.employeeInfo = res.data.employeeList
            } catch (error) {
                console.log("error 발생");
            }
        },
        async GET_COMPANY_INFO() {
            try {
                const res = await getCompanyInfo()
                this.compnayInfo = res.data.companyInfo
                // this.employeeAdd = res.data.
            } catch (error) {
                console.log("error 발생");
            }
        },
        async GET_EMP_ADD(clientdata: any) {
            try {
                await getEmployeeAdd(clientdata)
            } catch (error) {
                console.log("error 발생" + error)
            }
        },
        async GET_EMP_EDIT(updatedEmployee: any) {
            try {
                const res = await getEmpEdit(updatedEmployee)
                // this.employeeEdit = res.data.
            } catch (error) {
                console.log("error 발생");
            }
        },
        async GET_EMP_DELETE(empCode: String) {
            try {
                console.log("pinia" + empCode);
                const res = await getmpDelete(empCode)
                // this.employeeDelete = res.data.
            } catch (error) {

            }
        },
    }
}
)