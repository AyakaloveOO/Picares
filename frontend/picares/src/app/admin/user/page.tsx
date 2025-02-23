"use client";
import { PlusOutlined } from "@ant-design/icons";
import type { ActionType, ProColumns } from "@ant-design/pro-components";
import { ProTable } from "@ant-design/pro-components";
import { Button, Space, Tag } from "antd";
import { useRef } from "react";
import { getUserByPage } from "@/api/user";
import { USER_ENUM } from "@/constant/user";

const columns: ProColumns<API.UserVO>[] = [
  {
    title: "id",
    dataIndex: "id",
    valueType: "text",
    hideInSearch: true,
    hideInTable: true,
  },
  {
    title: "账号",
    dataIndex: "userAccount",
    valueType: "text",
  },
  {
    title: "头像",
    dataIndex: "userAvatar",
    valueType: "image",
    fieldProps: {
      width: 64,
    },
    hideInSearch: true,
  },
  {
    title: "简介",
    dataIndex: "userProfile",
    valueType: "text",
    width: 320,
  },
  {
    title: "权限",
    dataIndex: "userRole",
    valueType: "select",
    width:240,
    valueEnum: {
      user: {
        text: "用户",
      },
      admin: {
        text: "管理员",
      },
    },
    render: (_, record) => {
      console.log("render的数据：", record.userRole);
      if (record.userRole === USER_ENUM.ADMIN.name) {
        return (
          <Tag bordered={false} color={"gold"}>
            {record.userRole}
          </Tag>
        );
      }
      if (record.userRole === USER_ENUM.USER.name) {
        return (
          <Tag bordered={false} color={"success"}>
            {record.userRole}
          </Tag>
        );
      }
    },
  },
  {
    title: "创建时间",
    sorter:true,
    dataIndex: "createTime",
    valueType: "dateTime",
    width:240
  },
  {
    title: "操作",
    valueType: "option",
    key: "option",
    render: (_, _record) => (
      <Space>
        <Button onClick={() => {}} color={"cyan"} variant="filled">
          编辑
        </Button>
        <Button onClick={() => {}} color={"volcano"} variant="filled">
          删除
        </Button>
      </Space>
    ),
    width:240
  },
];

const UserAdminPage = () => {
  const actionRef = useRef<ActionType>(null);
  return (
    <ProTable
      columns={columns}
      actionRef={actionRef}
      request={async (params, sort, filter) => {
        console.log(params, sort, filter);
        const sortField = Object.keys(sort)?.[0];
        const sortOrder = sort[sortField] ?? undefined;
        const { code, data } = await getUserByPage({
          ...params,
          sortField,
          sortOrder,
          ...filter,
        });
        return {
          success: code === 0,
          data: data.records,
          total: data.total,
        };
      }}
      rowKey="id"
      search={{
        labelWidth: "auto",
        span: 8,
        submitterColSpanProps: {
          span: 8,
        },
      }}
      options={{
        reload: true,
        setting: false,
        density: false,
      }}
      pagination={{
        pageSize: 10,
      }}
      toolBarRender={() => [
        <Button
          key="button"
          icon={<PlusOutlined />}
          onClick={() => {
            actionRef.current?.reload();
          }}
          type="primary"
        >
          新建
        </Button>,
      ]}
    />
  );
};

export default UserAdminPage;
