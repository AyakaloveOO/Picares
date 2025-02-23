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
    path: "/aaa",
    name: "aaa",
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
    ],
  },
];

export const menuMap: { [key: string]: string } = {};

export const getMenuMap = (
    accessValue: number | undefined,
    menuItem = menus,
) => {
  return menuItem.filter((item) => {
    if (item.access?.value > (accessValue ?? 0)) {
      return false;
    }
    if (item.path){
      menuMap[item.path] =item.access?.name;
    }
    if (item.children) {
      item.children = getMenuMap(accessValue, item.children);
    }
    return true;
  });
};
