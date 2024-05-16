import React, {useRef} from 'react';
import type {ActionType, ProColumns} from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import {deleBill, deleCar, searchBills, searchBillsUser, updateBill, updateCar} from "@/services/ant-design-pro/api";
import {Form} from "antd";

const columns: ProColumns<API.CurrentUser>[] = [
  {
    dataIndex: 'id',
    valueType: 'indexBorder',
    width: 48,
  },
  {
    title: '车名',
    dataIndex: 'carname',
    copyable: true,
  },
  {
    title: '总数',
    dataIndex: 'amount',
    copyable: true,
  },
  {
    title: '天数',
    dataIndex: 'day',
    copyable: true,
  },
  {
    title: '总价',
    dataIndex: 'amountprice',
    copyable: true,
  },
  {
    title: '创建时间',
    dataIndex: 'createTime',
    valueType: 'dateTime',
  },
];

export default () => {
  const actionRef = useRef<ActionType>();
  return (
    <ProTable<API.CurrentBill>
      columns={columns}
      actionRef={actionRef}
      cardBordered
      request={async (params = {}, sort, filter) => {
        console.log(sort, filter);
        const userList = await searchBillsUser({params});
        return {
          data: userList
                  }
      }}
      editable={{
        type: 'multiple',
        onSave : async (key,record) => {
          await updateBill(record) // 调用表单验证
        },
        onDelete : async (key,record) => {
          await deleBill(record) // 调用表单验证
        }
      }}
      columnsState={{
        persistenceKey: 'pro-table-singe-demos',
        persistenceType: 'localStorage',
      }}
      rowKey="id"
      search={{
        labelWidth: 'auto',
      }}
      form={{
        // 由于配置了 transform，提交的参与与定义的不同这里需要转化一下
        syncToUrl: (values, type) => {
          if (type === 'get') {
            return {
              ...values,
              created_at: [values.startTime, values.endTime],
            };
          }
          return values;
        },
      }}
      pagination={{
        pageSize: 5,
      }}
      dateFormatter="string"
      headerTitle="高级表格"
    />
  );
};
