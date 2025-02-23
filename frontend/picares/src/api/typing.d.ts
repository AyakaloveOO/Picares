declare namespace API {
  type BaseResponse = {
    code?: number;
    data?: T;
    message?: string;
  };
  type UserRegisterDTO = {
    userAccount?: string;
    userPassword?: string;
    checkPassword?: string;
  };
  type UserLoginDTO = {
    userAccount?: string;
    userPassword?: string;
  };
  type UserUpdateDTO = {
    id?: number;
    userName?: string;
    userAvatar?: string;
    userProfile?: string;
    userRole?: string;
  };
  type UserQueryDTO = {
    current?: number;
    pageSize?: number;
    sortField?: string;
    sortOrder?: string;
    userAccount?: string;
    userName?: string;
    userProfile?: string;
    userRole?: string;
  };
  type DeleteDTO = {
    id?: number;
  };
  type LoginUserVO = {
    id?: number;
    userAccount?: string;
    userName?: string;
    userAvatar?: string;
    userProfile?: string;
    userRole?: string;
  };
  type UserVO = {
    id?: number;
    userAccount?: string;
    userName?: string;
    userAvatar?: string;
    userProfile?: string;
    userRole?: string;
    createTime?: string;
  };
  type UserPageVO = {
    records?: UserVO[];
    total?: number;
  };
}
