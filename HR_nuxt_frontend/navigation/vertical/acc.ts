export default [
  { heading: 'hr' },
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
]
