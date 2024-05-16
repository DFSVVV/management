import React, {useRef} from 'react';
import type {ActionType, ProColumns} from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import {deleCar, register, registerCar, searchCars, updateCar} from "@/services/ant-design-pro/api";
import {Button, Form, message} from "antd";
import {PlusOutlined} from "@ant-design/icons";
import {
  ModalForm,
  ProForm,
  ProFormDateRangePicker,
  ProFormSelect,
  ProFormText,
} from '@ant-design/pro-components';
import {history} from "@@/core/history";
const waitTime = (time: number = 100) => {
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve(true);
    }, time);
  });
};
const columns: ProColumns<API.CurrentCar>[] = [
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
    title: '价格',
    dataIndex: 'price',
    copyable: true,
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
  const [form] = Form.useForm<{ carName: string; price: number }>();
  const actionRef = useRef<ActionType>();
  return (
    <ProTable<API.CurrentCar>
      columns={columns}
      actionRef={actionRef}
      cardBordered
      request={async (params = {}, sort, filter) => {
        console.log(sort, filter);
        const userList = await searchCars({params});
        return {
          data: userList
        }
      }}
      editable={{
        type: 'multiple',
        onSave : async (key,record) => {
          await updateCar(record) // 调用表单验证
        },
        onDelete : async (key,record) => {
          await deleCar(record) // 调用表单验证
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
          price: number;
        }>
          title="添加汽车信息"
          trigger={
            <Button type="primary">
              <PlusOutlined />
              添加汽车信息
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
              const id = await registerCar(values);
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
              label="汽车名"
              placeholder="请输入名称"
            />

            <ProFormText
              width="md"
              name="price"
              label="价格"
              tooltip="请输入数字"
              rules={[
                {required: true, message: '价格不能为空'},
                {pattern : /^[0-9]+$/,message: '只能输入数字'}
                ]}
              placeholder="请输入价格"
            />
          </ProForm.Group>
        </ModalForm>
          </>
      ]}
    />
  );
};
