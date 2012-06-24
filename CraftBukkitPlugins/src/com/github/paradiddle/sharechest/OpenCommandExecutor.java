package com.github.paradiddle.sharechest;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import com.github.paradiddle.sharechest.ChatUtil;
import com.github.paradiddle.sharechest.Config;
import com.github.paradiddle.sharechest.ShareChestPlugin;
import com.github.paradiddle.sharechest.SharedChest;
import com.github.paradiddle.sharechest.Utility;

public class OpenCommandExecutor implements CommandExecutor
{

	private ShareChestPlugin plugin;

	public OpenCommandExecutor(ShareChestPlugin plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args)
	{
		if (!(sender instanceof Player))
		{
			sender.sendMessage("Only players can open a shared chest.");
			return true;
		}

		Player p = (Player) sender;

		if (args.length != 1)
			return false;
		String name = args[0];
		SharedChest chest = plugin.getData().getSharedChestByName(name);
		if (chest == null)
		{
			ChatUtil.sendMessage(sender, "Cannot find a shared chest with the name of ",
					Config.CC_CHEST_NAME + name, Config.CC_DEFAULT + ".");
			return true;
		}

		World w = p.getWorld();
		Block b = w.getBlockAt(chest.getLocation());

		if (b.getState() instanceof InventoryHolder)
		{
			if (chest.isLocked() && !sender.getName().equals(chest.getCreator()) && !sender.isOp())
			{
				ChatUtil.sendMessage(sender, "Chest ",
						Config.CC_CHEST_NAME + chest.getName(), Config.CC_DEFAULT
								+ " is locked.");
				return true;
			}
			InventoryHolder cb = (InventoryHolder) b.getState();
			Inventory i = cb.getInventory();
			p.openInventory(i);
			return true;
		}
		
		ChatUtil.sendMessage(sender, "Chest no longer exists. Removing from list.");
		plugin.getData().removeChestByName(name);
		return true;
	}
}
