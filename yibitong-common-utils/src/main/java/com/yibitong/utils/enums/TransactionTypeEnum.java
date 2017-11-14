package com.yibitong.utils.enums;

/**
 * Created by Beeant on 2016/2/18.
 */
public enum TransactionTypeEnum {
    /**
     * 充值
     */
    RECHARGE("RC", "充值"),

    /**
     * 提现
     */
    WITHDRAWALS("WD", "提现"),

    /**
     * 投资
     */
    INVESTMENT("IM", "投资"),

    /**
     * 订单赎回
     */
    ORDER_REDEEM("OR", "订单赎回"),

    /**
     * 自动副投
     */
    AUTO_REPEAT("AR", "自动复投"),

    ;

    private String code;

    private String value;

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    TransactionTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    /**
     * 按code获取TransactionTypeEnum
     * @param code
     * @return
     */
    public static TransactionTypeEnum getTransactionTypeEnum(String code) {
        TransactionTypeEnum[] enums = TransactionTypeEnum.values();
        for (TransactionTypeEnum transactionTypeEnum : enums) {
            if (transactionTypeEnum.code == code) {
                return transactionTypeEnum;
            }
        }
        return null;
    }

    /**
     * 按code获取message
     * @param code
     * @return
     */
    public String getValue(String code) {
        return getTransactionTypeEnum(code).getValue();
    }

}
