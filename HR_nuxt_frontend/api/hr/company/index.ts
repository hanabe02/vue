import { hrApi } from '@/api'

function searchCustomerList(searchOption: any) {
  console.log(searchOption)

  return hrApi.get('/company/searchCustomer', {
    params: {
      ...searchOption,
    },
  })
}

export { searchCustomerList }
