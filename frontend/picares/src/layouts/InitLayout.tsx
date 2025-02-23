"use client";
import React, { useCallback, useEffect } from "react";
import { getLoginUser } from "@/api/user";
import { useDispatch } from "react-redux";
import { setLoginUser } from "@/stores/loginUser";

const InitLayout: React.FC<
  Readonly<{
    children: React.ReactNode;
  }>
> = ({ children }) => {
  const dispatch = useDispatch();
  const doInit = useCallback(async () => {
    const res = await getLoginUser();
    if (res.data) {
      dispatch(setLoginUser(res.data));
    }
  }, []);
  useEffect(() => {
    doInit();
  }, []);
  return <>{children}</>;
};

export default InitLayout;
