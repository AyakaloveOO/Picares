import request from "@/libs/request";

export function userRegister(body: API.UserRegisterDTO) {
  return request("/user/register", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    data: body,
  });
}

export function userLogin(body: API.UserLoginDTO) {
  return request("/user/login", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    data: body,
  });
}

export function getLoginUser() {
  return request("/user/get/login", {
    method: "GET",
  });
}

export function userLogout() {
  return request("/user/logout", {
    method: "POST",
  });
}

export function updateUser(body: API.UserUpdateDTO) {
  return request("/user/update", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    data: body,
  });
}

export function getUserByPage(body: API.UserQueryDTO): Promise<API.BaseResponse> {
  return request("/user/get", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    data: body,
  });
}

export function deleteUser(body: API.DeleteDTO) {
  return request("/user/delete", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    data: body,
  });
}
