"use client";

import { LoadingOutlined, PlusOutlined } from "@ant-design/icons";
import type { GetProp, UploadFile, UploadProps } from "antd";
import { Upload } from "antd";
import React, { useState } from "react";
import ImgCrop from "antd-img-crop";

type FileType = Parameters<GetProp<UploadProps, "beforeUpload">>[0];

const getBase64 = (img: FileType, callback: (url: string) => void) => {
  const reader = new FileReader();
  reader.addEventListener("load", () => callback(reader.result as string));
  reader.readAsDataURL(img);
};

// const beforeUpload = (file: FileType) => {
//   const isJpgOrPng = file.type === "image/jpeg" || file.type === "image/png";
//   if (!isJpgOrPng) {
//     message.error("You can only upload JPG/PNG file!");
//   }
//   const isLt2M = file.size / 1024 / 1024 < 2;
//   if (!isLt2M) {
//     message.error("Image must smaller than 2MB!");
//   }
//   return isJpgOrPng && isLt2M;
// };

const TestPage: React.FC = () => {
  const [loading, setLoading] = useState(false);
  const [imageUrl, setImageUrl] = useState<string>();
  const [file, setFile] = useState(null);

  const handleChange = (info: any) => {
    console.log("change", info);
    if (info.file.status === "done") {
      setFile(info.file.originFileObj);
      getBase64(info.file.originFileObj as FileType, (url) => {
        setLoading(false);
        setImageUrl(url);
      });
    }
  };

  const onPreview = async (file: UploadFile) => {
    let src = file.url as string;
    if (!src) {
      src = await new Promise((resolve) => {
        const reader = new FileReader();
        reader.readAsDataURL(file.originFileObj as FileType);
        reader.onload = () => resolve(reader.result as string);
      });
    }
    const image = new Image();
    image.src = src;
    const imgWindow = window.open(src);
    imgWindow?.document.write(image.outerHTML);
  };

  const uploadButton=() => (
    <button style={{ border: 0, background: "none" }} type="button">
      <div style={{ marginTop: 8 }}></div>
    </button>
  );
  return (
    <>
      <ImgCrop rotationSlider>
        <Upload
          listType="picture-card"
          showUploadList={false}
          onChange={handleChange}
          onPreview={onPreview}
        >
          {imageUrl ? (
            <img src={imageUrl} alt="avatar" style={{ width: "100%" }} />
          ) : (
            uploadButton()
          )}
        </Upload>
      </ImgCrop>
      {/*<Image*/}
      {/*  src="http://localhost:8080/api/test/image/apple.jpeg"*/}
      {/*  alt="aaa"*/}
      {/*  width={44}*/}
      {/*  height={44}*/}
      {/*/>*/}
    </>
  );
};

export default TestPage;

//type FileType = Parameters<GetProp<UploadProps, "beforeUpload">>[0];
//
// const getBase64 = (img: FileType, callback: (url: string) => void) => {
//   const reader = new FileReader();
//   reader.addEventListener("load", () => callback(reader.result as string));
//   reader.readAsDataURL(img);
// };
//
// const beforeUpload = (file: FileType) => {
//   const isJpgOrPng = file.type === "image/jpeg" || file.type === "image/png";
//   if (!isJpgOrPng) {
//     message.error("You can only upload JPG/PNG file!");
//   }
//   const isLt2M = file.size / 1024 / 1024 < 2;
//   if (!isLt2M) {
//     message.error("Image must smaller than 2MB!");
//   }
//   return isJpgOrPng && isLt2M;
// };
//
// const TestPage: React.FC = () => {
//   const [loading, setLoading] = useState(false);
//   const [imageUrl, setImageUrl] = useState<string>();
//   const [file, setFile] = useState(null);
//   const [form] = Form.useForm();
//
//   const handleChange = (info: any) => {
//     if (info.file.status === "done") {
//       setFile(info.file.originFileObj);
//       getBase64(info.file.originFileObj as FileType, (url) => {
//         setLoading(false);
//         setImageUrl(url);
//       });
//     }
//   };
//
//   const doSubmit = async (values: any) => {
//     //在这里拿到图片和图片的描述和作者，调用后端的upload接口将图片和图片信息一起上传到后端
//     if (!file) {
//       message.error("请选择上传图片");
//       return;
//     }
//
//     const formData = new FormData();
//     formData.append("image", file);
//     formData.append("name", values.name);
//     formData.append("introduction", values.introduction);
//
//     try {
//       await request("/picture/upload", {
//         method: "POST",
//         headers: {
//           "Content-Type": "multipart/form-data",
//         },
//         data: formData,
//       });
//       message.success("上传成功");
//       form.resetFields();
//       setFile(null);
//     } catch (error) {
//       message.error("上传失败");
//       console.error("上传错误:", error);
//     }
//   };
//
//   const uploadButton = (
//     <button style={{ border: 0, background: "none" }} type="button">
//       {loading ? <LoadingOutlined /> : <PlusOutlined />}
//       <div style={{ marginTop: 8 }}>Upload</div>
//     </button>
//   );
//   const normFile = (e: any) => {
//     if (Array.isArray(e)) {
//       return e;
//     }
//     return e?.fileList;
//   };
//   return (
//     <>
//       <Form
//         labelCol={{ span: 4 }}
//         wrapperCol={{ span: 14 }}
//         layout="horizontal"
//         style={{ maxWidth: 600 }}
//         onFinish={doSubmit}
//         onChange={handleChange}
//       >
//         <Form.Item
//           name="image"
//           valuePropName="fileList"
//           getValueFromEvent={normFile}
//         >
//           <Upload
//             name="image"
//             listType="picture-card"
//             className="avatar-uploader"
//             showUploadList={false}
//             beforeUpload={beforeUpload}
//             onChange={handleChange}
//           >
//             {imageUrl ? (
//               <img src={imageUrl} alt="avatar" style={{ width: "100%" }} />
//             ) : (
//               uploadButton
//             )}
//           </Upload>
//         </Form.Item>
//         <Form.Item label="name" name="name">
//           <Input />
//         </Form.Item>
//         <Form.Item label="introduction" name="introduction">
//           <Input />
//         </Form.Item>
//         <Form.Item label={null}>
//           <Button type="primary" htmlType="submit">
//             提交
//           </Button>
//         </Form.Item>
//       </Form>
//       <Image
//         src="http://localhost:8080/api/test/image/apple.jpeg"
//         alt="aaa"
//         width={44}
//         height={44}
//       ></Image>
//     </>
//   );
// };