package com.github.paradiddle.coords;

public class CoordinatesRequest
{
	private String requester;
	private String requestee;
	
	public CoordinatesRequest(String requester, String requestee)
	{
		this.requester = requester;
		this.requestee = requestee;
	}
	
	public String getRequester()
	{
		return requester;
	}
	
	public String getRequestee()
	{
		return requestee;
	}
}
