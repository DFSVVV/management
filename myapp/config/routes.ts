export default [
  {
    path: '/user',
    layout: false,
    routes: [
      {path: '/user', routes: [
          {name: '登录', path: '/user/login', component: './user/Login'},
          {name: '注册', path: '/user/register', component: './user/Register'}
          ]},
      { component: './404' },
    ],
  },
  { path: '/welcome', name: '欢迎', icon: 'smile', component: './Welcome' },
  {
    path: '/admin',
    name: '管理页',
    icon: 'crown',
    access: 'canAdmin',
    component: './Admin',
    routes: [
      { path: '/admin/user-manage', name: '用户管理', icon: 'smile', component: './Admin/UserManage' },
      { path: '/admin/car-manage', name: '车辆管理', icon: 'smile', component: './Admin/CarManage' },
      { path: '/admin/bill-manage', name: '账单管理', icon: 'smile', component: './Admin/BillManage' },
      { component: './404' },
    ],
  },

  {
    path: '/userborrow',
    name: '用户页',
    icon: 'crown',
    //component: './UserBorrow',
    routes: [
      { path: '/userborrow/car-borrow', name: '车辆租赁', icon: 'smile', component: './UserBorrow/CarBorrow' },
      { path: '/userborrow/bill', name: '账单', icon: 'smile', component: './UserBorrow/Bill' },
      { component: './404' },
    ],
  },
  { path: '/', redirect: '/welcome' },
  { component: './404' },
];
