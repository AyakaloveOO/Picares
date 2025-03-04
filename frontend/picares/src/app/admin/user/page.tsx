"use client";
import {
  ActionType,
  PageContainer,
  ProColumns,
  ProTable,
} from "@ant-design/pro-components";
import {
  Button,
  type GetProp,
  message,
  Space,
  Tag,
  Upload,
  UploadFile,
  type UploadProps,
} from "antd";
import React, { useRef, useState } from "react";
import { deleteUser, getUserByPage, updateUser } from "@/api/user";
import { IMAGE_HOST, USER_ENUM } from "@/constant/user";
import UpdateModal from "@/app/admin/user/components/UpdateModal";
import { Image as AntdImage } from "antd/lib";
import ImgCrop from "antd-img-crop";
import { LoadingOutlined } from "@ant-design/icons";

type FileType = Parameters<GetProp<UploadProps, "beforeUpload">>[0];

const UserAdminPage = () => {
  const actionRef = useRef<ActionType>(null);
  const [visible, setVisible] = useState<boolean>(false);
  const [currentDate, setCurrentDate] = useState<API.UserAdminVO>();
  const [imageUrl, setImageUrl] = useState<string>();
  const [loading, setLoading] = useState(false);
  const [file, setFile] = useState(null);

  const getBase64 = (img: FileType, callback: (url: string) => void) => {
    const reader = new FileReader();
    reader.addEventListener("load", () => callback(reader.result as string));
    reader.readAsDataURL(img);
  };

  const onPreview = async (file: UploadFile) => {
    let src = file.url as string;
    if (!src) {
      src = await new Promise((resolve) => {
        const reader = new FileReader();
        reader.readAsDataURL(file.originFileObj as FileType);
        reader.onload = () => resolve(reader.result as string);
      });
    }
    const image = new Image();
    image.src = src;
    const imgWindow = window.open(src);
    imgWindow?.document.write(image.outerHTML);
  };

  const handleChange = (info: any) => {
    if (info.file.status === "uploading") {
      setLoading(true);
      return;
    }
    if (info.file.status === "done") {
      console.log("状态", info.file.status);
      setFile(info.file.originFileObj);
      getBase64(info.file.originFileObj as FileType, (url) => {
        setLoading(false);
        setImageUrl(url);
      });
    }
  };

  const doDelete = async (values: API.UserAdminVO) => {
    const hide = message.loading("正在删除");
    try {
      await deleteUser({
        id: values.id,
      });
      hide();
      message.success("删除成功");
      actionRef.current?.reload();
    } catch (e: any) {
      hide();
      message.error("删除失败，" + e.message);
    }
  };

  const doSubmit = async (values: API.UserAdminVO, id: number) => {
    const formData = new FormData();
    if (file) {
      formData.append("avatar", file);
    }
    Object.keys(values).forEach((key) => {
      if (key !== "userAvatar") {
        formData.append(key, values[key]);
      }
    });
    formData.append("id", id.toString());
    try {
      await updateUser(formData);
      message.success("更新成功");
      setVisible(false);
      setFile(null);
      setImageUrl("");
      actionRef.current?.reload();
    } catch (e: any) {
      message.error("更新失败，" + e.message);
    }
  };

  const columns: ProColumns<API.UserAdminVO>[] = [
    {
      title: "id",
      dataIndex: "id",
      valueType: "text",
      hideInSearch: true,
      hideInTable: true,
      hideInForm: true,
    },
    {
      title: "账号",
      dataIndex: "userAccount",
      valueType: "text",
      hideInForm: true,
    },
    {
      title: "名称",
      dataIndex: "userName",
      valueType: "text",
    },
    {
      title: "头像",
      dataIndex: "userAvatar",
      valueType: "image",
      fieldProps: {
        width: 64,
      },
      render: (_, record) => {
        return (
          <AntdImage
            src={
              record.userAvatar
                ? IMAGE_HOST + record.userAvatar
                : "/assets/notLoginUser.png"
            }
            alt={""}
            width={44}
            height={44}
          />
        );
      },
      hideInSearch: true,
      renderFormItem: () => {
        return (
          //在这个组件点击并上传图片后没有图片回显
          <ImgCrop>
            <Upload
              listType="picture-card"
              showUploadList={false}
              onChange={handleChange}
              onPreview={onPreview}
            >
              {loading ? (
                <LoadingOutlined />
              ) : (
                <AntdImage
                  src={imageUrl}
                  alt="avatar"
                  style={{ width: "100%" }}
                  preview={false}
                />
              )}
            </Upload>
          </ImgCrop>
        );
      },
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
      width: 240,
      valueEnum: {
        user: {
          text: "用户",
        },
        admin: {
          text: "管理员",
        },
      },
      render: (_, record) => {
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
      sorter: true,
      dataIndex: "createTime",
      valueType: "dateTime",
      hideInForm: true,
      hideInSearch: true,
      width: 240,
    },
    {
      title: "操作",
      valueType: "option",
      key: "option",
      render: (_, record) => (
        <Space>
          <Button
            onClick={() => {
              setVisible(true);
              setCurrentDate(record);
              if (record.userAvatar) {
                setImageUrl(IMAGE_HOST + record.userAvatar);
              } else {
                setImageUrl("/assets/notLoginUser.png");
              }
            }}
            size={"small"}
            color={"cyan"}
            variant="filled"
          >
            编辑
          </Button>
          <Button
            onClick={() => doDelete(record)}
            size={"small"}
            color={"volcano"}
            variant="filled"
          >
            删除
          </Button>
        </Space>
      ),
      width: 180,
    },
  ];

  return (
    <div id={"userAdminPage"}>
      <PageContainer>
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
        <UpdateModal
          visible={visible}
          onCancel={() => {
            setVisible(false);
            setImageUrl("");
          }}
          columns={columns}
          oldData={currentDate}
          onSubmit={doSubmit}
        />
      </PageContainer>
    </div>
  );
};

export default UserAdminPage;
