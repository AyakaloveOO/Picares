import { dirname } from "path";
import { fileURLToPath } from "url";
import { FlatCompat } from "@eslint/eslintrc";

const __filename = fileURLToPath(import.meta.url);
const __dirname = dirname(__filename);

const compat = new FlatCompat({
  baseDirectory: __dirname,
});

const eslintConfig = [
  ...compat.extends(
    "next/core-web-vitals",
    "next/typescript",
    "next",
    "prettier",
  ),
  {
    rules: {
      "@typescript-eslint/no-explicit-any": "off", // 关闭 any 检查
      "@typescript-eslint/no-unused-vars": [
        "error",
        {
          argsIgnorePattern: "^_", // 忽略以 _ 开头的函数参数
          varsIgnorePattern: "^_", // 忽略以 _ 开头的变量
          caughtErrorsIgnorePattern: "^_", // 忽略以 _ 开头的 catch 错误参数
        },
      ],
    },
  },
];

export default eslintConfig;
