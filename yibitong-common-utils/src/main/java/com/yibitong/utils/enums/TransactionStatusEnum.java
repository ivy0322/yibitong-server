package com.yibitong.utils.enums;

/**
 * 交易状态枚举
 * Created by 东方 on 2017/11/10.
 */
public enum TransactionStatusEnum {

    /**
     * 申请中
     */
    APPLYING(0, "申请中"),

    /**
     * 交易成功
     */
    SUCCESS(1, "交易成功"),

    /**
     * 交易失败
     */
    FAIL(2, "交易失败"),

    ;

    private int code;

    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    TransactionStatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 按code获取TransactionTypeEnum
     * @param code
     * @return
     */
    public static TransactionStatusEnum getTransactionStatusEnum(int code) {
        TransactionStatusEnum[] enums = TransactionStatusEnum.values();
        for (TransactionStatusEnum transactionStatusEnum : enums) {
            if (transactionStatusEnum.code == code) {
                return transactionStatusEnum;
            }
        }
        return null;
    }

}
