"use client";
import {
  ActionType,
  PageContainer,
  ProColumns,
  ProTable,
} from "@ant-design/pro-components";
import { Button, Space } from "antd";
import React, { useRef } from "react";
import { getPictureByPage } from "@/api/picture";
import { Image as AntdImage } from "antd/lib";
import { IMAGE_HOST } from "@/constant/user";

const PictureAdminPage: React.FC = () => {
  const actionRef = useRef<ActionType>(null);
  const columns: ProColumns<API.PictureAdminVO>[] = [
    {
      title: "id",
      dataIndex: "id",
      valueType: "text",
      hideInSearch: true,
      hideInTable: true,
      hideInForm: true,
    },
    {
      title: "图片",
      dataIndex: "name",
      valueType: "image",
      hideInForm: true,
      hideInSearch: true,
      render: (_, record) => {
        return (
          <AntdImage
            src={IMAGE_HOST + record.url}
            alt={""}
            width={44}
            height={44}
          />
        );
      },
    },
    {
      title: "图片名称",
      dataIndex: "name",
      valueType: "text",
      hideInForm: true,
    },
    {
      title: "简介",
      dataIndex: "introduction",
      valueType: "text",
      hideInForm: true,
      width:240
    },
    {
      title: "分类",
      dataIndex: "category",
      valueType: "text",
    },
    {
      title: "标签",
      dataIndex: "tags",
      valueType: "text",
    },
    {
      title: "图片体积",
      dataIndex: "picSize",
      valueType: "digit",
      hideInSearch: true,
      render: (_, record) => {
        return (record.picSize / 1024).toFixed(2) + "KB";
      },
    },
    {
      title: "宽度",
      dataIndex: "picWidth",
      valueType: "digit",
      hideInSearch: true,
    },
    {
      title: "高度",
      dataIndex: "picHeight",
      valueType: "digit",
      hideInSearch: true,
    },
    {
      title: "宽高比例",
      dataIndex: "picScale",
      valueType: "digit",
      hideInSearch: true,
      render: (_, record) => {
        return record.picScale.toFixed(2);
      },
    },
    {
      title: "格式",
      dataIndex: "picFormat",
      valueType: "text",
    },
    {
      title: "创建人",
      dataIndex: "userAccount",
      valueType: "text",
    },
    {
      title: "创建时间",
      sorter: true,
      dataIndex: "createTime",
      valueType: "dateTime",
      hideInForm: true,
      hideInSearch: true,
      width: 180,
    },
    {
      title: "操作",
      valueType: "option",
      key: "option",
      render: (_, record) => (
        <Space>
          <Button size={"small"} color={"cyan"} variant="filled">
            编辑
          </Button>
          <Button size={"small"} color={"volcano"} variant="filled">
            删除
          </Button>
        </Space>
      ),
      width: 140,
    },
  ];

  return (
    <div className={"pictureAdminPage"}>
      <PageContainer>
        <ProTable
          columns={columns}
          actionRef={actionRef}
          request={async (params, sort, filter) => {
            console.log(params, sort, filter);
            const sortField = Object.keys(sort)?.[0];
            const sortOrder = sort[sortField] ?? undefined;
            const { code, data } = await getPictureByPage({
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
            span: 6,
            defaultCollapsed: false,
          }}
          options={{
            setting: false,
            density: false,
          }}
          pagination={{
            pageSize: 10,
          }}
        />
      </PageContainer>
    </div>
  );
};
export default PictureAdminPage;
