package com.littlefox.chinese.edu.object;

public class UserPaymentStatus
{
    private String product_id   = "";
    private String product_name = "";
    private String expire_date  = "";
    private String transaction_date = "";

    public String getProductId() {
        return product_id;
    }
    public String getProductName() {
        return product_name;
    }
    public String getExpireDate() {
        return expire_date;
    }
    public String getTransacctionDate() {
        return transaction_date;
    }
}
