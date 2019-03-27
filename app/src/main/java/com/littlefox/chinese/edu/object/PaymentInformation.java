package com.littlefox.chinese.edu.object;

/**
 * 결제 정보 저장 클래스 
 * @author 정재현
 *
 */
public class PaymentInformation
{
	private String mProductID 			= "";
	private String mTransactionID 		="";
	private long mTransactionDate 		= 0L;
	private long mExpireDate 			= 0L;
	
	public  PaymentInformation(String productId, String transactionId, long transactionDate, long expireDate)
	{
		mProductID 			= productId;
		mTransactionID 		= transactionId;
		mTransactionDate 	= transactionDate;
		mExpireDate			= expireDate;
	}
	
	public  PaymentInformation(String productId, String transactionId, long transactionDate)
	{
		mProductID 			= productId;
		mTransactionID 		= transactionId;
		mTransactionDate 	= transactionDate;
		mExpireDate			= 0L;
	}

	public String getProductID()
	{
		return mProductID;
	}

	public String getTransactionID()
	{
		return mTransactionID;
	}

	public long getTransactionDate()
	{
		return mTransactionDate;
	}

	public long getExpireDate()
	{
		return mExpireDate;
	}
	
	
}
