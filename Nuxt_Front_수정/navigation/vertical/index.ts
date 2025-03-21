export default [
  {
    title: "메인페이지",
    to: { name: "index" },
    icon: { icon: "tabler-home-heart" },
  },
  {
    title: "물류 정보",
    icon: { icon: "tabler-submarine" },
    children: [
      {
        title: "기초 정보",
        icon: { icon: "tabler-smart-home" },
        children: [
          {
            title: "회사 정보",
            to: { name: "logistic-base-page-BaseInfo-CompanyFormPage" },
          },
          {
            title: "사업장 정보",
            to: { name: "logistic-base-page-BaseInfo-WorkPlaceFormPage" },
          },
          {
            title: "부서정보",
            to: { name: "logistic-base-page-BaseInfo-DeptFormPage" },
          },
          {
            title: "거래처 정보",
            to: {
              name: "logistic-base-page-BaseInfo-clientInfo-ClientFormPage",
            },
          },
        ],
      },
      {
        title: "코드 관리",
        icon: { icon: "tabler-smart-home" },
        children: [
          {
            title: "코드 관리",
            to: { name: "logistic-base-page-codeInfo-CodeFormPage" },
          },
        ],
      },
      {
        title: "품목관리",
        icon: { icon: "tabler-smart-home" },
        to: { name: "logistic-base-page-itemInfo-ItemFormPage" },
      },
      {
        title: "창고관리",
        icon: { icon: "tabler-smart-home" },
        to: { name: "logistic-base-page-BaseInfo-warehouseInfo" },
      },
    ],
  },
  {
    title: "영업관리",
    icon: { icon: "tabler-briefcase" },
    children: [
      {
        title: "견적 관리",
        icon: { icon: "tabler-smart-home" },
        children: [
          {
            title: "견적 등록",
            to: { name: "logistic-sales-page-estimateRegist-EstimateRegist" },
          },
          {
            title: "견적 조회",
            to: { name: "logistic-sales-page-estimateInfo-EstimateInfo" },
          },
        ],
      },
      {
        title: "수주 관리",
        icon: { icon: "tabler-smart-home" },
        children: [
          {
            title: "수주 등록",
            to: { name: "logistic-sales-page-contract-contractRegist" },
          },
          {
            title: "수주 조회",
            to: { name: "logistic-sales-page-contract-contractInfo" },
          },
        ],
      },
      {
        title: "납품 관리",
        icon: { icon: "tabler-smart-home" },
        to: { name: "logistic-sales-page-deliveryInfo-DeliveryInfo" },
      },
      {
        title: "판매 계획",
        icon: { icon: "tabler-smart-home" },
        to: { name: "logistic-sales-page-salesPlan-SalesPlan" },
      },
    ],
  },
  {
    title: "생산 관리",
    icon: { icon: "tabler-propeller" },
    children: [
      {
        title: "주 생산 계획(MPS)",
        icon: { icon: "tabler-smart-home" },
        children: [
          {
            title: "수주->MPS",

            to: {
              name: "logistic-productionManagement-page-mps-ContractRegistMps",
            },
          },
          {
            title: "판매 계획->MPS",
            to: {
              name: "logistic-productionManagement-page-mps-SalesPlanRegistMps",
            },
          },
        ],
      },
      {
        title: "소요랑전개(MRP)",
        icon: { icon: "tabler-smart-home" },
        children: [
          {
            title: "소요량 전개",

            to: {
              name: "logistic-productionManagement-page-mrp-MrpRegisterMain",
            },
          },
          {
            title: "소요량 취합 결과 조회",
            to: { name: "logistic-productionManagement-page-mrp-MrpInfo" },
          },
        ],
      },
      {
        title: "작업지시/생산실적 관리",
        icon: { icon: "tabler--hoe" },

        to: { name: "logistic-productionManagement-page-WorkOrderFormPage" },
      },
      {
        title: "작업장/작업장 로그",
        icon: { icon: "tabler--hoe" },
        to: { name: "logistic-productionManagement-page-WorkSiteFormPage" },
      },
    ],
  },
  {
    title: "자재 구매 관리",
    icon: { icon: "tabler-receipt-tax" },
    children: [
      {
        title: "자재명세서(BOM)",
        icon: { icon: "tabler--hoe" },
        to: { name: "logistic-purchase-bomInfo-BomInfoFormPage" },
      },
      {
        title: "발주 및 재고처리",
        icon: { icon: "tabler--hoe" },
        to: { name: "logistic-purchase-orderRegistInfo-OrderStockFormPage" },
      },
      {
        title: "재고 관리",
        icon: { icon: "tabler--hoe" },
        to: { name: "logistic-purchase-stockInfo-StockInfoFormPage" },
      },
    ],
  },
  {
    title: "외주 관리",
    icon: { icon: "tabler-credit-card" },
    children: [
      {
        title: "외주 발주 관리",
        icon: { icon: "tabler-smart-home" },
        children: [
          {
            title: "외주 발주 등록/조회",
            to: { name: "logistic-outsource-page-order-finalorder" },
          },
          {
            title: "외주 발주 등록",
            to: { name: "logistic-outsource-page-order-order1" },
          },
          {
            title: "외주 발주 조회",
            to: { name: "logistic-outsource-page-order-order" },
          },
        ],
      },
      {
        title: "외주 자재 출고 관리",
        icon: { icon: "tabler--hoe" },
        to: { name: "logistic-outsource-page-forward-forward" },
      },
      {
        title: "외주 자재 실적 검사",
        icon: { icon: "tabler--hoe" },
        to: { name: "logistic-outsource-page-inspection-inspection" },
      },
    ],
  },
  {
    title: "출고 관리",
    icon: { icon: "tabler-car" },
    children: [
      {
        title: "출고등록",
        to: { name: "logistic-outputInfo-ShippingRegistFormPage" },
      },
    ],
  },
  {
    title: "배송 관리",
    icon: { icon: "tabler-camper" },
    children: [
      {
        title: "배송서비스 관리",
        to: { name: "logistic-clientDelivery-DeliveryFormPage" },
      },
      {
        title: "배송 상세 관리",
        to: { name: "logistic-clientDelivery-DeliveryDetailFormPage" },
      },
    ],
  },
  {
    title: "운송수단관리",
    icon: { icon: "tabler-brand-docker" },
    children: [
      {
        title: "운송수단관리",
        to: { name: "logistic-transportation-transportationFormPage" },
      },
    ],
  },
  {
    title: "반품요청 관리",
    icon: { icon: "tabler-brand-wechat" },
    children: [
      {
        title: "외주물품 반품요청 관리",
        to: { name: "logistic-returnInfo-ReturnoutsInfoFormPage" },
      },
      {
        title: "남품물품 반품 관리",
        icon: { icon: "tabler-smart-home" },
        children: [
          {
            title: "납품물품 반품요청 관리",
            to: { name: "logistic-returnInfo-ReturnInfoFormPage" },
          },
          {
            title: "납품반품 재고입고 요청관리",
            to: { name: "logistic-returnInfo-ReturnStockInfoFormPage" },
          },
        ]
      },
    ],
  },
  {
    title: '해외 홍보 정보',
    icon: { icon: 'tabler-file' },
    children: [
      {
        title: '해외 홍보 일정',
        icon: { icon: 'tabler-hoe'},
        to: {name: 'logistic-abroad-abroadSchedule-abroadSchedule'},
      },
    ],
  },
  {
    title: '회원 정보',
    icon: { icon: 'tabler-user' },
    children: [
      {
        title: '회원 정보 관리',
        icon: { icon: 'tabler-user', size: '22' },
        to: {name: 'logistic-user-userProfile-userProfile'}
      }
    ]
  },
  {
    title: '사원 정보',
    icon: { icon: 'tabler-user' },
    children: [
      {
        title: '사원 정보 관리',
        icon: { icon: 'tabler-user', size: '22' },
        to: { name: 'logistic-employee-employee' }
      }
    ]
  }
];
