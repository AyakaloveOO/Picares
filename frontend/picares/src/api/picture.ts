import request from "@/libs/request";

export function getPictureByPage(body: API.PictureQueryDTO): Promise<API.BaseResponse> {
    return request("/picture/get", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        data: body,
    });
}

export function deletePicture(body: API.DeleteDTO){
    return request("/picture/delete", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        data: body,
    });
}