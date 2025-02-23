"use client";
import {
  GithubFilled,
  InfoCircleFilled,
  LogoutOutlined,
  QuestionCircleFilled,
} from "@ant-design/icons";
import { ProLayout } from "@ant-design/pro-components";
import { Dropdown, message } from "antd";
import React from "react";
import Link from "next/link";
import { usePathname, useRouter } from "next/navigation";
import Image from "next/image";
import { getMenuMap, menus } from "@/config/menus";
import { useDispatch, useSelector } from "react-redux";
import { setLoginUser } from "@/stores/loginUser";
import {DEFAULT_USER, getValueByName} from "@/constant/user";
import "./index.css";
import GlobalFooter from "@/layouts/BasicLayout/components/GlobalFooter";
import { userLogout } from "@/api/user";
import { RootState } from "@/stores";

interface Props {
  children: React.ReactNode;
}

export default function BasicLayout({ children }: Props) {
  const loginUser = useSelector((state: RootState) => state.loginUser);
  const dispatch = useDispatch();
  const router = useRouter();
  const pathname = usePathname();

  const doLogout = async () => {
    try {
      await userLogout();
      dispatch(setLoginUser(DEFAULT_USER));
      message.success("已退出登录");
      router.replace("/user/login");
    } catch (e: any) {
      message.error("操作失败，" + e.message);
    }
  };

  return (
    <div
      id="test-pro-layout"
      style={{
        height: "100vh",
        overflow: "auto",
      }}
    >
      <ProLayout
        title={"Picares"}
        layout={"top"}
        logo={
          <Image
            src={"/assets/logo.png"}
            alt={"picares"}
            width={32}
            height={32}
          />
        }
        location={{
          pathname,
        }}
        avatarProps={{
          src: loginUser.userAvatar || "/assets/notLoginUser.png",
          size: "small",
          title: loginUser.userName,
          render: (props, dom) => {
            return (
              <Dropdown
                menu={{
                  items: [
                    {
                      key: "logout",
                      icon: <LogoutOutlined />,
                      label: "退出登录",
                    },
                  ],
                  onClick: async (e: { key: React.Key }) => {
                    const { key } = e;
                    if (key === "logout") {
                      doLogout();
                    }
                  },
                }}
              >
                {dom}
              </Dropdown>
            );
          },
        }}
        actionsRender={(props) => {
          if (props.isMobile) return [];
          return [
            <InfoCircleFilled key="InfoCircleFilled" />,
            <QuestionCircleFilled key="QuestionCircleFilled" />,
            <GithubFilled key="GithubFilled" />,
          ];
        }}
        headerTitleRender={(logo, title, _) => {
          const defaultDom = (
            <a href={"/"}>
              {logo}
              {title}
            </a>
          );
          return <>{defaultDom}</>;
        }}
        footerRender={() => {
          return <GlobalFooter />;
        }}
        onMenuHeaderClick={(e) => console.log(e)}
        menuDataRender={() => {
          return getMenuMap(getValueByName(loginUser.userRole));
        }}
        menuItemRender={(item, dom) => (
          <Link href={item.path || "/"} target={item.target}>
            {dom}
          </Link>
        )}
      >
        {children}
      </ProLayout>
    </div>
  );
}
