"use client";

import { usePathname } from "next/navigation";
import { menuMap } from "@/config/menus";
import Forbidden from "@/app/forbidden";

const AccessLayout: React.FC<
  Readonly<{
    children: React.ReactNode;
  }>
> = ({ children }) => {
  const pathname = usePathname();

  if (!menuMap[pathname]) {
    return <Forbidden />;
  }

  return <>{children}</>;
};

export default AccessLayout;
