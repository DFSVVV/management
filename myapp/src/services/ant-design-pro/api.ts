// @ts-ignore
/* eslint-disable */
import  request  from '@/plugins/globalRequest';

/** 获取当前的用户 GET /api/user/current */
export async function currentUser(options?: { [key: string]: any }) {
  return request<API.BaseResponse<API.CurrentUser>>
  ('/api/user/current', {
    method: 'GET',
    ...(options || {}),
  });
}
/** 搜索用户 GET /api/user/current */
export async function searchUsers(options?: { [key: string]: any }) {
  return request <API.BaseResponse<API.CurrentUser[]>>
  ('/api/user/search', {
    method: 'Get',
    ...(options || {}),
  });
}
/** 搜索车 GET /api/car/search */
export async function searchCars(options?: { [key: string]: any }) {
  return request <API.BaseResponse<API.CurrentCar[]>>
  ('/api/car/search', {
    method: 'GET',
    ...(options || {}),
  });
}
/** 搜索账单 GET /api/bill/search */
export async function searchBills(options?: { [key: string]: any }) {
  return request <API.BaseResponse<API.CurrentBill[]>>
  ('/api/bill/search', {
    method: 'GET',
    ...(options || {}),
  });
}
/** 搜索账单 GET /api/bill/searchUser */
export async function searchBillsUser(options?: { [key: string]: any }) {
  return request <API.BaseResponse<API.CurrentBill[]>>
  ('/api/bill/searchUser', {
    method: 'GET',
    ...(options || {}),
  });
}
/** 退出登录接口 POST /api/user/logout */
export async function outLogin(options?: { [key: string]: any }) {
  return request<API.BaseResponse<number>>('/api/user/logout', {
    method: 'POST',
    ...(options || {}),
  });
}

/** 登录接口 POST /api/login/account */
export async function login(body: API.LoginParams, options?: { [key: string]: any }) {
  return request<API.BaseResponse<API.LoginResult>>('/api/user/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
/** 注册接口 POST /api/user/register */
export async function register(body: API.RegisterParams , options?: { [key: string]: any }) {
  return request<API.BaseResponse<API.RegisterParams>>('/api/user/register', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
/** 注册接口 POST /api/car/register */
export async function registerCar(body: API.RegisterCarParams , options?: { [key: string]: any }) {
  return request<API.BaseResponse<API.RegisterCarParams>>('/api/car/register', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
/** 注册接口 POST /api/bill/register */
export async function registerBill(body: API.RegisterBillParams , options?: { [key: string]: any }) {
  return request<API.BaseResponse<API.RegisterBillParams>>('/api/bill/register', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
/** 更新接口 POST /api/user/update */
export async function update(body: API.UpdateUser , options?: { [key: string]: any }) {
  return request<API.BaseResponse<API.UpdateUser>>('/api/user/update', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
/** 更新接口 POST /api/car/update */
export async function updateCar(body: API.CurrentCar , options?: { [key: string]: any }) {
  return request<API.BaseResponse<API.CurrentCar>>('/api/car/update', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
/** 更新接口 POST /api/bill/update */
export async function updateBill(body: API.CurrentBill , options?: { [key: string]: any }) {
  return request<API.BaseResponse<API.CurrentBill>>('/api/bill/update', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
/** 删除接口 POST /api/user/delete */
export async function dele(body: API.DeleteBody , options?: { [key: string]: any }) {
  return request<API.BaseResponse<API.DeleteBody>>('/api/user/delete', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
/** 删除接口 POST /api/car/delete */
export async function deleCar(body: API.DeleteBody , options?: { [key: string]: any }) {
  return request<API.BaseResponse<API.DeleteBody>>('/api/car/delete', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
/** 删除接口 POST /api/bill/delete */
export async function deleBill(body: API.DeleteBody , options?: { [key: string]: any }) {
  return request<API.BaseResponse<API.DeleteBody>>('/api/bill/delete', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
/** 此处后端没有提供注释 GET /api/notices */
export async function getNotices(options?: { [key: string]: any }) {
  return request<API.NoticeIconList>('/api/notices', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 获取规则列表 GET /api/rule */
export async function rule(
  params: {
    // query
    /** 当前的页码 */
    current?: number;
    /** 页面的容量 */
    pageSize?: number;
  },
  options?: { [key: string]: any },
) {
  return request<API.RuleList>('/api/rule', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 新建规则 PUT /api/rule */
export async function updateRule(options?: { [key: string]: any }) {
  return request<API.RuleListItem>('/api/rule', {
    method: 'PUT',
    ...(options || {}),
  });
}

/** 新建规则 POST /api/rule */
export async function addRule(options?: { [key: string]: any }) {
  return request<API.RuleListItem>('/api/rule', {
    method: 'POST',
    ...(options || {}),
  });
}

/** 删除规则 DELETE /api/rule */
export async function removeRule(options?: { [key: string]: any }) {
  return request<Record<string, any>>('/api/rule', {
    method: 'DELETE',
    ...(options || {}),
  });
}
