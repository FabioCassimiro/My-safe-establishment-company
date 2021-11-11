package br.com.mysafeestablishmentcompany.api.imgur;

import java.util.List;

public class UploadImageResponse {

    String status;
    String success;
    List<DataImage> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<DataImage> getData() {
        return data;
    }

    public void setData(List<DataImage> data) {
        this.data = data;
    }

    public UploadImageResponse() {
    }
}
