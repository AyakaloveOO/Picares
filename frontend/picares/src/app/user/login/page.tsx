"use client";
import { LockOutlined, UserOutlined } from "@ant-design/icons";
import { LoginForm, ProForm, ProFormText } from "@ant-design/pro-components";
import Link from "next/link";
import Image from "next/image";
import { userLogin } from "@/api/user";
import { message } from "antd";
import { useDispatch } from "react-redux";
import { setLoginUser } from "@/stores/loginUser";
import { useRouter } from "next/navigation";
import "./index.css";

const UserLoginPage = () => {
  const [form] = ProForm.useForm();
  const dispatch = useDispatch();
  const router = useRouter();
  const doSubmit = async (values: API.UserLoginDTO) => {
    try {
      const res = await userLogin(values);
      if (res.data) {
        message.success("登录成功");
        dispatch(setLoginUser(res.data));
        router.replace("/");
        form.resetFields();
      }
    } catch (e: any) {
      message.error("登录失败，" + e.message);
    }
  };
  return (
    <div id={"userLoginPage"}>
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
        <div
          style={{
            marginBlockEnd: 24,
            textAlign: "end",
          }}
        >
          <Link href={"/user/register"}>注册账号</Link>
        </div>
      </LoginForm>
    </div>
  );
};

export default UserLoginPage;
