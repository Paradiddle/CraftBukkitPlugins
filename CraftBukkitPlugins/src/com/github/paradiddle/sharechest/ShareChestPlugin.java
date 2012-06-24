package com.github.paradiddle.sharechest;

import org.bukkit.plugin.java.JavaPlugin;

public class ShareChestPlugin extends JavaPlugin
{
	private SharedChestData data;

	@Override
	public void onEnable()
	{		
		data = new SharedChestData(this);
		data.load();

		getCommand("scshare").setExecutor(new ShareCommandExecutor(this));
		getCommand("sclist").setExecutor(new ListCommandExecutor(this));
		getCommand("scopen").setExecutor(new OpenCommandExecutor(this));
		getCommand("scunshare").setExecutor(new UnshareChestCommandExecutor(this));

		LockUnlockCommandExecutor lockUnlock = new LockUnlockCommandExecutor(this);

		getCommand("scunlock").setExecutor(lockUnlock);
		getCommand("sclock").setExecutor(lockUnlock);
	}
	
	@Override
	public void onDisable()
	{
		data.save();
	}

	public void log(String message)
	{
		getLogger().info(message);
	}
	
	public SharedChestData getData()
	{
		return data;
	}

}
