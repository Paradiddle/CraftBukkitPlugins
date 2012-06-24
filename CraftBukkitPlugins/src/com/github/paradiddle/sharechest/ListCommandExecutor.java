package com.github.paradiddle.sharechest;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.github.paradiddle.sharechest.ChatUtil;
import com.github.paradiddle.sharechest.Config;
import com.github.paradiddle.sharechest.ShareChestPlugin;
import com.github.paradiddle.sharechest.SharedChest;

public class ListCommandExecutor implements CommandExecutor
{
	private ShareChestPlugin plugin;

	public ListCommandExecutor(ShareChestPlugin plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2,
			String[] arg3)
	{
		String lock = Config.CC_LOCK + "(*)";
		
		ChatUtil.sendMessage(sender, Config.CC_HEADER + "Shared Chests:");
		for (SharedChest sc : plugin.getData().getSharedChests())
		{
			ChatUtil.sendMessage(sender,
					Config.CC_CHEST_NAME + sc.getName(),
					sc.isLocked() ? lock : "", 
					Config.CC_DEFAULT + " created by ",
					Config.CC_CREATOR + sc.getCreator());
		}
		return true;
	}
}
