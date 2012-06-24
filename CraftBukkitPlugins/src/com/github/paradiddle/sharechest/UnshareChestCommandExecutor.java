package com.github.paradiddle.sharechest;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.github.paradiddle.sharechest.ChatUtil;
import com.github.paradiddle.sharechest.Config;
import com.github.paradiddle.sharechest.ShareChestPlugin;
import com.github.paradiddle.sharechest.SharedChest;
import com.github.paradiddle.sharechest.Utility;

public class UnshareChestCommandExecutor implements CommandExecutor
{
	private ShareChestPlugin plugin;
	
	public UnshareChestCommandExecutor(ShareChestPlugin plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args)
	{
		if (args.length != 1)
			return false;

		SharedChest sc = plugin.getData().getSharedChestByName(args[0]);
		if(sc == null)
		{
			ChatUtil.sendMessage(sender, "Could not find chest " + Config.CC_CHEST_NAME
					+ args[0], Config.CC_DEFAULT + ".");
			return true;
		}
		if (sc.getCreator().equals(sender.getName()) || sender.isOp())
		{
			boolean removed = plugin.getData().removeChestByName(args[0]);
			if(removed)
			{
				ChatUtil.sendMessage(sender, "Chest " + Config.CC_CHEST_NAME
					+ sc.getName(), Config.CC_DEFAULT
					+ " was successfully unshared.");
				return true;
			}
		}
		ChatUtil.sendMessage(sender, "You do not have authorization to unshare chest ",
				Config.CC_CHEST_NAME + sc.getName(),
				Config.CC_DEFAULT + ".");
		return true;
	}

}
