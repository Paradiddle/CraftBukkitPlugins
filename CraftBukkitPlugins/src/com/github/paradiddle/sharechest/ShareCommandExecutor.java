package com.github.paradiddle.sharechest;

import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.Inventory;

import com.github.paradiddle.sharechest.ChatUtil;
import com.github.paradiddle.sharechest.Config;
import com.github.paradiddle.sharechest.ShareChestPlugin;
import com.github.paradiddle.sharechest.SharedChest;
import com.github.paradiddle.sharechest.Utility;

public class ShareCommandExecutor implements CommandExecutor
{
	private ShareChestPlugin plugin;

	public ShareCommandExecutor(ShareChestPlugin jp)
	{
		this.plugin = jp;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args)
	{
		// Only players can share chests
		if (!(sender instanceof Player))
		{
			sender.sendMessage("This command may only be executed in-game.");
			return true;
		}

		// Safe to cast since we know it's a Player
		Player pSender = (Player) sender;

		// There must be only one argument [chest name]
		if (args.length != 1)
			return false;

		Block blockLookingAt = Utility.getLookingAtBlock(pSender);

		// Make sure the player is looking at a chest
		if (blockLookingAt != null && blockLookingAt.getState() instanceof Chest)
		{
			String chestName = args[0];
			Chest attemptedShareChest = (Chest) blockLookingAt.getState();
			Inventory attemptedShareInventory = attemptedShareChest.getInventory();

			// If the chest the player is trying to share is a double chest
			// then just share the left side of it so we only need to store
			// one Location in the world.
			if (attemptedShareChest.getInventory() instanceof DoubleChestInventory)
			{
				DoubleChest dc = ((DoubleChestInventory) attemptedShareInventory)
						.getHolder();
				attemptedShareChest = (Chest) dc.getLeftSide();
			}

			// Check to see if there is already a chest at the location of the
			// chest the player is trying to share.
			SharedChest sc = plugin.getData().getChestAt(
					attemptedShareChest.getLocation());
			if (sc != null)
			{
				ChatUtil.sendMessage(sender, "Chest already shared as ",
						Config.CC_CHEST_NAME + sc.getName(), Config.CC_DEFAULT + " (",
						Config.CC_CREATOR + sc.getCreator(), Config.CC_DEFAULT + ").");
				return true;
			}

			// Check to see if there is an existing chest with this name
			if (plugin.getData().chestNameInUse(chestName))
			{
				ChatUtil.sendMessage(sender, "Chest name ", Config.CC_CHEST_NAME
						+ chestName, Config.CC_DEFAULT + " is already in use.");
				return true;
			}

			SharedChest sharedChest = new SharedChest(chestName, pSender.getWorld()
					.getName(), pSender.getName(), attemptedShareChest.getLocation());

			plugin.getData().shareChest(sharedChest);
			ChatUtil.sendMessage(sender, "Chest successfully shared as ",
					Config.CC_CHEST_NAME + sharedChest.getName(), Config.CC_DEFAULT + ".");
			return true;
		}
		return false;
	}

}
