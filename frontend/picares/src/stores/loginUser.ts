import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { DEFAULT_USER } from "@/constant/user";

export const loginUser = createSlice({
  name: "loginUser",
  initialState: DEFAULT_USER,
  reducers: {
    setLoginUser: (state, action: PayloadAction<API.LoginUserVO>) => {
      return {
        ...action.payload,
      };
    },
  },
});

// Action creators are generated for each case reducer function
export const { setLoginUser } = loginUser.actions;

export default loginUser.reducer;
