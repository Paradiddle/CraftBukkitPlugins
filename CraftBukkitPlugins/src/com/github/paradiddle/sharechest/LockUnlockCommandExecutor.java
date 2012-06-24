package com.github.paradiddle.sharechest;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.github.paradiddle.sharechest.ChatUtil;
import com.github.paradiddle.sharechest.Config;
import com.github.paradiddle.sharechest.ShareChestPlugin;
import com.github.paradiddle.sharechest.SharedChest;
import com.github.paradiddle.sharechest.Utility;

public class LockUnlockCommandExecutor implements CommandExecutor
{
	private ShareChestPlugin plugin;

	public LockUnlockCommandExecutor(ShareChestPlugin plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String label,
			String[] args)
	{
		boolean lock = true;
		if (label.equals("sclock"))
			lock = true;
		else if (label.equals("scunlock"))
			lock = false;
		else
			return false;

		if (args.length != 1)
			return false;

		SharedChest sc = plugin.getData().getSharedChestByName(args[0]);
		if (sc.getCreator().equals(sender.getName()) || sender.isOp())
		{
			if (lock)
				sc.lock();
			else
				sc.unlock();
			String str = lock ? "locked." : "unlocked.";
			ChatUtil.sendMessage(sender, "Chest ",
					Config.CC_CHEST_NAME	+ sc.getName(),
					Config.CC_DEFAULT + " was successfully ", 
					str);
			return true;
		}
		ChatUtil.sendMessage(sender,
				"You do not have authorization to modify the lock on chest ",
				Config.CC_CHEST_NAME + sc.getName(),
				Config.CC_DEFAULT + ".");
		return true;
	}

}
