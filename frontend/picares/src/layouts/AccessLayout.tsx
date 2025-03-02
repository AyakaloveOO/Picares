"use client";

import { usePathname } from "next/navigation";
import Forbidden from "@/app/forbidden";
import React from "react";
import { useSelector } from "react-redux";
import { RootState } from "@/stores";
import { getAccessMenu } from "@/config/menus";
import { getValueByName } from "@/constant/user";

const AccessLayout: React.FC<
  Readonly<{
    children: React.ReactNode;
  }>
> = ({ children }) => {
  const loginUser = useSelector((state: RootState) => state.loginUser);
  const pathname = usePathname();
  const menuAccess = getAccessMenu(pathname);
  if (getValueByName(loginUser.userRole) < (menuAccess?.value ?? 0)) {
    return <Forbidden />;
  }
  return <>{children}</>;
};

export default AccessLayout;
