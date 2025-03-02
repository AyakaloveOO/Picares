"use client";
import { PageContainer } from "@ant-design/pro-components";
import { Card, Flex, List, message, Space, Tooltip, Typography } from "antd";
import React, { useEffect, useState } from "react";
import { deletePicture, getPictureByPage } from "@/api/picture";
import { IMAGE_HOST } from "@/constant/user";
import { Image } from "antd/lib";
import NextImage from "next/image";
import { DeleteOutlined, EditOutlined } from "@ant-design/icons";
import dayjs from "dayjs";

const PictureAdminPage: React.FC = () => {
  const [data, setData] = useState();
  const [_expanded, setExpanded] = useState(false);

  const doSearch = async () => {
    try {
      const res = await getPictureByPage({});
      console.log(res.data?.records);
      setData(res.data?.records);
    } catch (e: any) {
      message.error("获取数据失败，" + e.message);
    }
  };

  const doDelete = async (values: API.PictureAdminVO) => {
    try {
      await deletePicture({ id: values.id });
      message.success("删除成功");
      doSearch();
    } catch (e: any) {
      message.error("删除失败，" + e.message);
    }
  };

  useEffect(() => {
    doSearch();
  }, []);

  return (
    <div className={"pictureAdminPage"}>
      <PageContainer>
        <List
          grid={{
            gutter: 16,
            xs: 1,
            sm: 2,
            md: 3,
            lg: 4,
            xl: 5,
            xxl: 6,
          }}
          dataSource={data}
          renderItem={(item: API.PictureAdminVO) => (
            <List.Item>
              <Card
                cover={
                  <Image
                    src={IMAGE_HOST + item.url}
                    alt={item.name || "/"}
                    height={200}
                    style={{
                      objectFit: "cover",
                    }}
                  />
                }
                extra={
                  <Tooltip title={item.userAccount}>
                    <NextImage
                      src={IMAGE_HOST + item.userAvatar}
                      alt={""}
                      width={44}
                      height={44}
                      style={{ borderRadius: "50%" }}
                    />
                  </Tooltip>
                }
                title={item.name}
                actions={[
                  <EditOutlined
                    key="edit"
                    style={{ color: "green" }}
                    title={"编辑"}
                  />,
                  <DeleteOutlined
                    key="delete"
                    style={{ color: "red" }}
                    title={"删除"}
                    onClick={() => doDelete(item)}
                  />,
                ]}
              >
                <Flex justify="space-around" align="start" vertical={true}>
                  <Typography.Paragraph
                    type={"secondary"}
                    ellipsis={{
                      rows: 1,
                      expandable: "collapsible",
                      onExpand: (_, info) => setExpanded(info.expanded),
                    }}
                  >
                    介绍：{item.introduction}
                  </Typography.Paragraph>
                  <Flex justify={"space-between"} align={"center"}>
                    <Space>
                      <Typography.Text type={"secondary"}>
                        大小：{(item.picSize / 1000).toFixed(2)}MB
                      </Typography.Text>
                      <Typography.Text type={"secondary"}>
                        类型：{item.picFormat}
                      </Typography.Text>
                    </Space>
                  </Flex>
                  <Flex justify={"space-between"} align={"center"}>
                    <Space>
                      <Typography.Text type={"secondary"}>
                        宽度：{item.picWidth}
                      </Typography.Text>
                      <Typography.Text type={"secondary"}>
                        高度：{item.picHeight}
                      </Typography.Text>
                    </Space>
                  </Flex>
                  <Typography.Text type={"secondary"}>
                    宽高比：{item.picScale.toFixed(2)}
                  </Typography.Text>
                  <Typography.Link type={"secondary"}>
                    上传者账号：{item.userAccount}
                  </Typography.Link>
                  <Typography.Text type={"secondary"}>
                    创建时间：
                    {dayjs(item.createTime).format("YYYY-MM-DD HH:mm:ss")}
                  </Typography.Text>
                </Flex>
              </Card>
            </List.Item>
          )}
        />
      </PageContainer>
    </div>
  );
};
export default PictureAdminPage;
