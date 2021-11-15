package br.com.mysafeestablishmentcompany.api.imgur;

import java.util.List;
import java.util.StringJoiner;

public class UploadImageResponse {

    private String status;
    private String success;
    private DataImage data;

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

    public DataImage getData() {
        return data;
    }

    public void setData(DataImage data) {
        this.data = data;
    }

    public UploadImageResponse() {
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UploadImageResponse.class.getSimpleName() + "[", "]")
                .add("status='" + status + "'")
                .add("success='" + success + "'")
                .add("data=" + data)
                .toString();
    }
}
