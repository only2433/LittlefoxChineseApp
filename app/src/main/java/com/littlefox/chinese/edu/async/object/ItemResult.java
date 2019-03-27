package com.littlefox.chinese.edu.async.object;

public class ItemResult
{
	public String	code			= "";
	public String	message			= "";

	@Override
	public String toString()
	{
		return String.format("{\"code\":\"%s\", \"message\":\"%s\"}",  code, message);
	}
}