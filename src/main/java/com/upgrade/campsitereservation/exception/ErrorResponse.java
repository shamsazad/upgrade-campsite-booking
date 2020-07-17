package com.upgrade.campsitereservation.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.io.Serializable;
import java.util.Objects;

public class ErrorResponse implements Serializable {

    @DecimalMin("100")
    @DecimalMax("599")
    private Integer status;

    private String type;

    private String message;

    private String code;

    private String detailMessage;

    public ErrorResponse(ErrorResponseBuilder errorResponseBuilder) {
        this.code = errorResponseBuilder.code;
        this.message = errorResponseBuilder.message;
        this.status = errorResponseBuilder.status;
        this.type = errorResponseBuilder.type;
        this.detailMessage = errorResponseBuilder.detailMessage;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    public void setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
    }

    public static ErrorResponseBuilder builder() {
        return new ErrorResponseBuilder();
    }

    @JsonIgnore
    public boolean isBadRequest() {
        return Objects.equals(status, 400);
    }

    @JsonIgnore
    public boolean isNotFound() {
        return Objects.equals(status, 404);
    }

    @JsonIgnore
    public boolean isInternalServerError() {
        return Objects.equals(status, 500);
    }

    @JsonIgnore
    public boolean isServiceUnavailable() {
        return Objects.equals(status, 503);
    }

    public static class ErrorResponseBuilder {

        private Integer status;
        private String type;
        private String message;
        private String code;
        private String detailMessage;

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDetailMessage() {
            return detailMessage;
        }

        public void setDetailMessage(String detailMessage) {
            this.detailMessage = detailMessage;
        }

        public ErrorResponseBuilder status(Integer status) {
            this.status = status;
            return this;
        }

        public ErrorResponseBuilder status(HttpStatus httpStatus) {
            this.status = httpStatus.value();
            return this;
        }

        public ErrorResponseBuilder type(String type) {
            this.type = type;
            return this;
        }

        public ErrorResponseBuilder message(String message) {
            this.message = message;
            return this;
        }

        public ErrorResponseBuilder code(String code) {
            this.code = code;
            return this;
        }

        public ErrorResponseBuilder detailMessage(String detailMessage) {
            this.detailMessage = detailMessage;
            return this;
        }

        public ErrorResponse build() {
            return new ErrorResponse(this);
        }
    }
}
