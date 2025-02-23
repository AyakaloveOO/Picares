export const USER_ENUM = {
  USER: { name: "user", value: 1 },
  ADMIN: { name: "admin", value: 2 },
};

export const DEFAULT_USER: API.LoginUserVO = {
  userName: "未登录",
  userAvatar: "/assets/notLoginUser.png",
  userProfile: "暂无简介",
  userRole: "guest",
};

export const getValueByName = (name: string|undefined) => {
  const entry = Object.values(USER_ENUM).find(item => item.name === name);
  return entry?.value??0;
};
