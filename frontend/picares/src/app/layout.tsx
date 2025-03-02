"use client";
import "./globals.css";
import { AntdRegistry } from "@ant-design/nextjs-registry";
import BasicLayout from "@/layouts/BasicLayout";
import InitLayout from "@/layouts/InitLayout";
import { Provider } from "react-redux";
import stores from "@/stores";
import React from "react";
import "@ant-design/v5-patch-for-react-19";
import AccessLayout from "@/layouts/AccessLayout";

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body>
        <AntdRegistry>
          <Provider store={stores}>
            <InitLayout>
              <BasicLayout>
                <AccessLayout>{children}</AccessLayout>
              </BasicLayout>
            </InitLayout>
          </Provider>
        </AntdRegistry>
      </body>
    </html>
  );
}
