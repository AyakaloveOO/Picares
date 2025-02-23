"use client";
import { LockOutlined, UserOutlined } from "@ant-design/icons";
import { LoginForm, ProForm, ProFormText } from "@ant-design/pro-components";
import Link from "next/link";
import Image from "next/image";
import { userRegister } from "@/api/user";
import { message } from "antd";
import { useRouter } from "next/navigation";
import "./index.css"

const UserRegisterPage = () => {
  const [form] = ProForm.useForm();
  const router = useRouter();
  const doSubmit = async (values: API.UserRegisterDTO) => {
    try {
      const res = await userRegister(values);
      if (res.data) {
        message.success("注册成功");
        router.replace("/user/login");
        form.resetFields();
      }
    } catch (e: any) {
      message.error("注册失败，" + e.message);
    }
  };
  return (
    <div id={"userRegisterPage"}>
      <LoginForm
        form={form}
        logo={
          <Image
            src={"/assets/logo.png"}
            alt={"Picares"}
            width={44}
            height={44}
          />
        }
        title="Picares"
        subTitle="阿瑞斯云图库"
        submitter={{
          searchConfig: {
            submitText: "注册",
          },
        }}
        onFinish={doSubmit}
      >
        <ProFormText
          name="userAccount"
          fieldProps={{
            size: "large",
            prefix: <UserOutlined className={"prefixIcon"} />,
          }}
          placeholder={"请输入账号"}
          rules={[
            {
              required: true,
              message: "请输入用户名!",
            },
            {
              min: 4,
              message: "账号过短",
            },
            {
              max: 16,
              message: "账号过长",
            },
            {
              pattern: /^[a-zA-Z0-9]+$/,
              message: "账号包含非法字符",
            },
          ]}
        />
        <ProFormText.Password
          name="userPassword"
          fieldProps={{
            size: "large",
            prefix: <LockOutlined className={"prefixIcon"} />,
          }}
          placeholder={"请输入密码"}
          rules={[
            {
              required: true,
              message: "请输入密码！",
            },
            {
              min: 6,
              message: "密码过短",
            },
            {
              max: 16,
              message: "密码过长",
            },
          ]}
        />
        <ProFormText.Password
          name="checkPassword"
          fieldProps={{
            size: "large",
            prefix: <LockOutlined className={"prefixIcon"} />,
          }}
          placeholder={"请确认密码"}
          rules={[
            {
              required: true,
              message: "请确认密码！",
            },
            {
              min: 6,
              message: "密码过短",
            },
            {
              max: 16,
              message: "密码过长",
            },
          ]}
        />
        <div
          style={{
            marginBlockEnd: 24,
            textAlign: "end",
          }}
        >
          <Link href={"/user/login"}>返回登录</Link>
        </div>
      </LoginForm>
    </div>
  );
};

export default UserRegisterPage;
