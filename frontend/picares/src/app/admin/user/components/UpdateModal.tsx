import React from "react";
import { Modal } from "antd";
import { ProColumns, ProTable } from "@ant-design/pro-components";

interface Props {
  visible: boolean;
  onCancel: () => void;
  columns: ProColumns<API.UserVO>[];
  oldData?: API.UserVO;
  onSubmit: (values: API.UserVO, id: number) => void;
}

const UpdateModal: React.FC<Props> = (props) => {
  const { visible, onCancel, columns, oldData, onSubmit } = props;
  return (
    <Modal
      title={"更新用户"}
      footer={null}
      open={visible}
      onCancel={onCancel}
      destroyOnClose
    >
      <ProTable
        type={"form"}
        columns={columns}
        form={{
          initialValues: oldData,
        }}
        onSubmit={async (values) => {
          console.log("更新的值：", values);
          onSubmit(values, oldData?.id ?? 0);
        }}
      />
    </Modal>
  );
};

export default UpdateModal;
