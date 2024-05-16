import React, {useRef} from 'react';
import type {ActionType, ProColumns} from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import {dele, searchUsers, update} from "@/services/ant-design-pro/api";
import {Button, Form, Image,message} from "antd";
import { PlusOutlined } from '@ant-design/icons';
import {
  ModalForm,
  ProForm,
  ProFormDateRangePicker,
  ProFormSelect,
  ProFormText,
} from '@ant-design/pro-components';
const waitTime = (time: number = 100) => {
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve(true);
    }, time);
  });
};

const columns: ProColumns<API.CurrentUser>[] = [
  {
    dataIndex: 'id',
    valueType: 'indexBorder',
    width: 48,
  },
  {
    title: '用户名',
    dataIndex: 'userName',
    copyable: true,
  },
  {
    title: '用户账户',
    dataIndex: 'userAccount',
    copyable: true,
  },
  {
    title: '头像',
    dataIndex: 'avatarUrl',
    render: (_, record) => (
      <div>
        <Image src={record.avatarUrl} width={100} />
      </div>
    ),
  },
  {
    title: '性别',
    dataIndex: 'gender',
    valueEnum: {
      0: {text: '女', status: 'Default'},
      1: {
        text: '男',
        status: 'Success',
      },
    },
  },
  {
    title: '电话',
    dataIndex: 'phone',
    copyable: true,
  },
  {
    title: '邮件',
    dataIndex: 'email',
    copyable: true,
  },
  {
    title: '状态',
    dataIndex: 'userStatus',
    valueType: 'select',
    valueEnum: {
      0: { text: '注销用户', status: 'Default' },
      1: {
        text: '活跃用户',
        status: 'Success',
      },
    },
  },
  {
    title: '角色',
    dataIndex: 'userRole',
    valueType: 'select',
    valueEnum: {
      0: { text: '普通用户', status: 'Default' },
      1: {
        text: '管理员',
        status: 'Success',
      },
    },
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
      </a>
    ],
  },

];

export default () => {
  const [form] = Form.useForm<{ name: string; company: string }>();
  const actionRef = useRef<ActionType>();
  return (

    <ProTable<API.CurrentUser>
      columns={columns}
      actionRef={actionRef}
      cardBordered
      request={async (params = {}, sort, filter) => {
        console.log(sort, filter);
        const userList = await searchUsers({params});
        return {
          data: userList
        }
      }}
      RecordCreator={{
        position: 'bottom',
        record: () => ({}),
        creatorButtonText: '新增',
        style: {
          display: 'none',
        },
      }}

      editable={{
        type: 'multiple',
        onSave : async (key,record) => {
          await update(record) // 调用表单验证
        },
        onDelete : async (key,record) => {
          await dele(record) // 调用表单验证
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
        // <> <ModalForm<{
        //   name: string;
        //   company: string;
        // }>
        //   title="新建表单"
        //   trigger={
        //     <Button type="primary">
        //       <PlusOutlined />
        //       新建表单
        //     </Button>
        //   }
        //   form={form}
        //   autoFocusFirstInput
        //   modalProps={{
        //     destroyOnClose: true,
        //     onCancel: () => console.log('run'),
        //   }}
        //   submitTimeout={2000}
        //   onFinish={async (values) => {
        //     await waitTime(2000);
        //     console.log(values.name);
        //     message.success('提交成功');
        //     return true;
        //   }}
        // >
        //   <ProForm.Group>
        //     <ProFormText
        //       width="md"
        //       name="name"
        //       label="签约客户名称"
        //       tooltip="最长为 24 位"
        //       placeholder="请输入名称"
        //     />
        //
        //     <ProFormText
        //       width="md"
        //       name="company"
        //       label="我方公司名称"
        //       placeholder="请输入名称"
        //     />
        //   </ProForm.Group>
        //   <ProForm.Group>
        //     <ProFormText
        //       width="md"
        //       name="contract"
        //       label="合同名称"
        //       placeholder="请输入名称"
        //     />
        //     <ProFormDateRangePicker name="contractTime" label="合同生效时间" />
        //   </ProForm.Group>
        //   <ProForm.Group>
        //     <ProFormSelect
        //       request={async () => [
        //         {
        //           value: 'chapter',
        //           label: '盖章后生效',
        //         },
        //       ]}
        //       width="xs"
        //       name="useMode"
        //       label="合同约定生效方式"
        //     />
        //     <ProFormSelect
        //       width="xs"
        //       options={[
        //         {
        //           value: 'time',
        //           label: '履行完终止',
        //         },
        //       ]}
        //       name="unusedMode"
        //       label="合同约定失效效方式"
        //     />
        //   </ProForm.Group>
        //   <ProFormText width="sm" name="id" label="主合同编号" />
        //   <ProFormText
        //     name="project"
        //     disabled
        //     label="项目名称"
        //     initialValue="xxxx项目"
        //   />
        //   <ProFormText
        //     width="xs"
        //     name="mangerName"
        //     disabled
        //     label="商务经理"
        //     initialValue="启途"
        //   />
        // </ModalForm>
        //   </>
      ]}
    />

  );
};
