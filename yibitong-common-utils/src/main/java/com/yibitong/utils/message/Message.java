package com.yibitong.utils.message;

import com.yibitong.common.Code;

import java.io.Serializable;

/**
 * @author Beeant
 */
public class Message<T> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -5068272108270221337L;

    /* API调用结果 true:成功，false：失败 */
    private boolean success = false;
    // 错误码
    private int code = Code.FAIL;

    // 返回的数据，可以任意集合或对象
    private T data;

    // 结果说明
    private String msg = "";

    /**
     * @return the success true:成功，false：失败
     */
    public boolean getSuccess() {
        return success;
    }

    /**
     * @param success the success to set true:成功，false：失败
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(int code) {
        this.code = code;
    }

    public void setCode(String code) {
        this.code = Integer.valueOf(code);
    }

    /**
     * @return the data
     */
    public T getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg the msg to set
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
