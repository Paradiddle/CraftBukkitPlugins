package com.github.paradiddle.rockpaperscissors;

import com.github.paradiddle.rockpaperscissors.RockPaperScissors.Sign;

public class RpsRequest
{
	private String from;
	private String to;
	
	private Sign sign;
	
	public RpsRequest(String from, String to, Sign sign)
	{
		this.from = from;
		this.to = to;
		this.sign = sign;
	}
	
	public String getFrom()
	{
		return from;
	}
	
	public String getTo()
	{
		return to;
	}
	
	public Sign getSign()
	{
		return sign;
	}
}
