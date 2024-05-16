import React, {useRef} from 'react';
import type {ActionType, ProColumns} from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import {
  deleBill,
  deleCar,
  registerBill,
  registerCar,
  searchBills,
  updateBill,
  updateCar
} from "@/services/ant-design-pro/api";
import {ModalForm, ProForm, ProFormDateTimePicker, ProFormText} from "@ant-design/pro-components";
import {Button, Form, message} from "antd";
import {PlusOutlined} from "@ant-design/icons";
const waitTime = (time: number = 100) => {
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve(true);
    }, time);
  });
};

const columns: ProColumns<API.CurrentBill>[] = [
  {
    dataIndex: 'id',
    valueType: 'indexBorder',
    width: 48,
  },
  {
    title: '账号',
    dataIndex: 'useraccount',
    copyable: true,
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
  {
    title: '操作',
    valueType: 'option',
    render: (text, record, _, action) => [
      <a
        key="editable"
        onClick={() => {
          action?.startEditable?.(record.id);
        }}
      >
        编辑
      </a>,
    ],
  },
];

export default () => {
  const [form] = Form.useForm<{carName: string; amount: number;day: number;}>();
  const actionRef = useRef<ActionType>();
  return (
    <ProTable<API.CurrentBill>
      columns={columns}
      actionRef={actionRef}
      cardBordered
      request={async (params = {}, sort, filter) => {
        console.log(sort, filter);
        const userList = await searchBills({params});
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
      toolBarRender={() => [
        <> <ModalForm<{
          carName: string;
          amount: number;
          day: number;
        }>
          title="添加账单"
          trigger={
            <Button type="primary">
              <PlusOutlined />
              添加账单
            </Button>
          }
          form={form}
          autoFocusFirstInput
          modalProps={{
            destroyOnClose: true,
            onCancel: () => console.log('run'),
          }}
          submitTimeout={2000}
          onFinish={async (values) => {
            await waitTime(2000);
            try {
              // 注册
              const id = await registerBill(values);
              if (id) {
                const defaultLoginSuccessMessage = '添加成功！';
                message.success(defaultLoginSuccessMessage);
              }
            } catch (error: any) {
              const defaultLoginFailureMessage = '添加失败，请重试！';
              message.error(defaultLoginFailureMessage);
            }
            //console.log(values.name);
            //message.success('提交成功');

            return true;
          }}
        >
          <ProForm.Group>
            <ProFormText
              width="md"
              name="carName"
              label="车名"
              placeholder="请输入名称"
            />
            <ProFormText
              width="md"
              name="amount"
              label="总数"
              tooltip="请输入数字"
              rules={[
                {required: true, message: '价格不能为空'},
                {pattern : /^[0-9]+$/,message: '只能输入数字'}
              ]}
              placeholder="请输入总数"
            />
            <ProFormText
              width="md"
              name="day"
              label="天数"
              tooltip="请输入数字"
              rules={[
                {required: true, message: '天数不能为空'},
                {pattern : /^[0-9]+$/,message: '只能输入数字'}
              ]}
              placeholder="请输入天数"
            />
          </ProForm.Group>
        </ModalForm>
        </>
      ]}
    />
  );
};
