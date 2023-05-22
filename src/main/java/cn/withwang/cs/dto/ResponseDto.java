package cn.withwang.cs.dto;

import org.springframework.http.HttpStatus;


/**
 * A DTO for HTTP responses.
 *
 * @param <ResDataType> 响应结果中的data类型
 */
public class ResponseDto<ResDataType> {

    private HttpStatus code;
    private ResDataType data;
    private String message;
    private boolean success;

    /**
     * ResponseDto 构造函数
     *
     * @param code HTTP状态码
     * @param data 响应结果中的data
     * @param message 响应结果中的message
     * @param success 响应结果中的success boolean值
     */
    public ResponseDto(HttpStatus code, ResDataType data, String message, boolean success) {
        this.code = HttpStatus.valueOf(code.value());
        this.data = data;
        this.message = message;
        this.success = success;
    }


    public int getCode() {
        return code.value();
    }

    public void setCode(HttpStatus code) {
        this.code = code;
    }

    public ResDataType getData() {
        return data;
    }

    public void setData(ResDataType data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * 响应成功的静态方法
     * @param data  响应结果中的data
     * @param message 响应结果中的message
     * @return ResponseDto
     * @param <ResDataType> 响应结果中的data类型
     *
     * 下面的方法都是重载的这个方法
     */
    public static <ResDataType> ResponseDto<ResDataType> success(ResDataType data, String message) {
        return new ResponseDto<>(HttpStatus.OK, data, message, true);
    }

    public static <ResDataType> ResponseDto<ResDataType> success(ResDataType data) {
        return new ResponseDto<>(HttpStatus.OK, data, "操作成功", true);
    }

    public static <ResDataType> ResponseDto<ResDataType> success(String message) {
        return new ResponseDto<>(HttpStatus.OK, null, message, true);
    }

    public static <ResDataType> ResponseDto<ResDataType> success() {
        return new ResponseDto<>(HttpStatus.OK, null, "操作成功", true);
    }
}
