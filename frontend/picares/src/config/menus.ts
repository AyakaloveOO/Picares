import { MenuDataItem } from "@ant-design/pro-layout";
import { USER_ENUM } from "@/constant/user";

export const menus: MenuDataItem[] = [
  {
    path: "/",
    name: "主页",
    hideInMenu: true,
    access: USER_ENUM.USER,
  },
  {
    path: "/test",
    name: "图片",
    access: USER_ENUM.USER,
  },
  {
    name: "管理",
    access: USER_ENUM.ADMIN,
    children: [
      {
        path: "/admin/user",
        name: "用户管理",
        access: USER_ENUM.ADMIN,
      },
      {
        path: "/admin/picture",
        name: "图片管理",
        access: USER_ENUM.ADMIN,
      },
    ],
  },
];

export const getFilterMenu = (
  accessValue: number | undefined,
  menuItem = menus,
) => {
  return menuItem.filter((item) => {
    if (item.access?.value > (accessValue ?? 0)) {
      return false;
    }
    if (item.children) {
      item.children = getFilterMenu(accessValue, item.children);
    }
    return true;
  });
};

export const getAccessMenu = (pathname: string) => {
  const getMenu=(menuItem = menus): MenuDataItem | null=>{
    for (const menu of menuItem) {
      if (menu.path === pathname) {
        return menu;
      }
      if (menu.children) {
        return getMenu(menu.children);
      }
    }
    return null;
  }
  return getMenu()?.access;
};
