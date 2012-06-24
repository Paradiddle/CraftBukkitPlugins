package com.github.paradiddle.sharechest;

import org.bukkit.command.CommandSender;

public class ChatUtil
{	
	public static void sendMessage(CommandSender sender, Object... parts)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(Config.CC_DEFAULT);
		for(Object s: parts)
		{
			sb.append(s);
		}
		sender.sendMessage(sb.toString());
	}
}
