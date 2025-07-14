export default [
  { heading: 'hr' },
  {
    title: 'insaHome',
    to: { name: 'index' },
    icon: { icon: 'tabler-smart-home' },
  },
  {
    title: '인사관리',
    icon: { icon: 'tabler-user-circle' },
    children: [
      {
        title: '직원정보 관리',
        children: [
          {
            title: '직원정보 조회',
            to: { name: 'hr-affair-empList' },
          },
          {
            title: '직원정보 관리',
            to: { name: 'hr-affair-empDetail' },
          },
          {
            title: '직원정보 등록',
            to: { name: 'hr-affair-registerEmpList' },
          },
        ],
      },
      {
        title: '인사고과',
        children: [
          {
            title: '인사고과 대상자 추가',
            to: { name: 'hr-empeval-add' },
          },
          {
            title: '인사고과 조회',
            to: { name: 'hr-empeval-list' },
          },
          {
            title: '인사고과 관리',
            to: { name: 'hr-empeval-manage' },
          },
        ],
      },
      {
        title: '인사발령',
        children: [
          {
            title: '인사발령 신청',
            to: { name: 'hr-appo-request' },
          },
          {
            title: '인사발령 관리',
            to: { name: 'hr-appo-manage' },
          },
          {
            title: '인사발령 이력',
            to: { name: 'hr-appo-log' },
          },
        ],
      },
      {
        title: '경조비 관리',
        children: [
          {
            title: '경조비 신청',
            to: { name: 'hr-conexpense-request' },
          },
          {
            title: '경조비 신청내역',
            to: { name: 'hr-conexpense-presentCondition' },
          },
          {
            title: '경조비 신청승인',
            to: { name: 'hr-conexpense-approval' },
          },
        ],
      },
      {
        title: '증명서 신청',
        children: [
          {
            title: '증명서 조회/신청',
            to: { name: 'hr-certificate-issue' },
          },
          {
            title: '증명서 신청 및 발급',
            to: { name: 'hr-certificate-approval' },
          },
        ],
      },
    ],
  },
  {
    title: '급여관리',
    icon: { icon: 'tabler-brand-cashapp' },
    children: [
      {
        title: '급여 조회',
        children: [
          {
            title: '급여조회',
            to: { name: 'hr-salcheck-month' },
          },
          {
            title: '상여 및 성과금 조회',
            to: { name: 'hr-salcheck-awrds' },
          },
          {
            title: '퇴직금 조회',
            to: { name: 'hr-salcheck-severancePay' },
          },
        ],
      },
      {
        title: '급여 등록',
        children: [
          {
            title: '월 급여 등록',
            to: { name: 'hr-salreg-month' },
          },
          {
            title: '성과급 등록',
            to: { name: 'hr-salreg-award' },
          },
          {
            title: '퇴직금 등록',
            to: { name: 'hr-salreg-severancePay' },
          },
        ],
      },
      {
        title: '급여 기준 관리',
        children: [
          {
            title: '기준급여관리',
            to: { name: 'hr-salbase-manage' },
          },
          {
            title: '초과수당관리',
            to: { name: 'hr-salbase-extManage' },
          },
          {
            title: '사회보험관리',
            to: { name: 'hr-salbase-socialInsure' },
          },
        ],
      },
    ],
  },
  {
    title: '근태관리',
    icon: { icon: 'tabler-alarm' },
    children: [
      {
        title: '근태 신청 및 등록',
        children: [
          {
            title: '일근태등록',
            to: { name: 'hr-attendreg-daily' },
          },
     
          {
            title: '근태외 신청',
            to: { name: 'hr-attendreg-rest' },
          },
          {
            title: '휴가 신청',
            to: { name: 'hr-attendreg-leave' },
          },
          {
            title: '출장 / 교육신청',
            to: { name: 'hr-attendreg-travelAndEducationRequest' },
          },
          {
            title: '초과근무 신청',
            to: { name: 'hr-attendreg-overWork' },
          },
        ],
      },
      {
        title: '근태관리',
        children: [
          {
            title: '일근태관리',
            to: { name: 'hr-attndmgmt-daily' },
          },
          {
            title: '월근태관리',
            to: { name: 'hr-attndmgmt-month' },
          },
          {
            title: '휴가 관리',
            to: { name: 'hr-attndmgmt-annual' },
          },
          {
            title: '근태외 관리',
            to: { name: 'hr-attndmgmt-break' },
          },
        ],
      },
    ],
  },
  {
    title: '교육관리',
    icon: { icon: 'tabler-book' },
    children: [
      {
        title: '교육 등록 및 신청',
        to: { name: 'hr-education-add' },
      },
      {
        title: '교육 조회 및 신청취소',
        to: { name: 'hr-education-list' },
      },
      {
        title: '교육 관리 및 승인요청',
        to: { name: 'hr-education-manage' },
      },
      {
        title: '교육 승인 및 반려',
        to: { name: 'hr-education-approval' },
      },
    ],
  },
  {
    title: '일용근로 관리',
    icon: { icon: 'tabler-hammer' },
    children: [
      {
        title: '일용근로 직원관리',
        children: [
          {
            title: '일용근로 직원관리',
            to: { name: 'hr-dailyemp-manage' },
          },
          {
            title: '일용근로 직원이력 관리',
            to: { name: 'hr-dailyemp-log' },
          },
        ],
      },
      {
        title: '일용근로 근태관리',
        children: [
          {
            title: '일용근로 근태등록',
            to: { name: 'hr-dailyattend-register' },
          },
          {
            title: '일용근로 근태관리',
            to: { name: 'hr-dailyattend-awrds' },
          },
        ],
      },
      {
        title: '일용근로 급여관리',
        children: [
          {
            title: '일용근로 급여등록',
            to: { name: 'hr-dailysal-register' },
          },
          {
            title: '일용근로 급여관리',
            to: { name: 'hr-dailysal-awrds' },
          },
          {
            title: '일용근로 공제관리',
            to: { name: 'hr-dailysal-salary' },
          },
        ],
      },
    ],
  },
  {
    title: '기초정보 관리',
    icon: { icon: 'tabler-clipboard-text' },
    children: [
      {
        title: '휴일정보관리',
        to: { name: 'hr-base-holidayManage' },
      },
      {
        title: '부서정보관리',
        to: { name: 'hr-base-deptManage' },
      },
      {
        title: '직급정보관리',
        to: { name: 'hr-base-positionManage' },
      },
      {
        title: '기준근무시간관리',
        to: { name: 'hr-base-baseWorkTimeManage' },
      },
      {
        title: '프로젝트 관리',
        to: { name: 'hr-base-project' },
      },
      {
        title: '권한관리',
        to: { name: 'hr-base-authManage' },
      },
      {
        title: '코드조회',
        to: { name: 'hr-base-codeManage' },
      },
    ],
  },
]
