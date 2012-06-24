package com.github.paradiddle.sharechest;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;

import org.bukkit.Location;

public class SharedChestData
{
	private LinkedList<SharedChest> sharedChests;
	private ShareChestPlugin plugin;
	private boolean canIO = false;

	public SharedChestData(ShareChestPlugin plugin)
	{
		this.plugin = plugin;
		sharedChests = new LinkedList<SharedChest>();
	}

	public void load()
	{
		makeSureDataDirectoryAndChestsCreated();
		if(!canIO)
			return;
		plugin.getLogger().log(Level.INFO, "Attempting to load the chests file.");
		File f = new File(plugin.getDataFolder(), "chests");
		if (!f.exists())
		{
			plugin.getLogger().log(Level.INFO,
					"Could not find chests file. Attempting to create one now.");
			try
			{
				boolean created = f.createNewFile();
				if (created)
					plugin.getLogger().log(Level.INFO,
							"Successfully created the chests file.");
				else
				{
					plugin.getLogger().log(Level.WARNING,
							"Still couldn't create the chests file.");
					return;
				}
			} catch (IOException e)
			{
				plugin.getLogger().log(Level.WARNING,
						"Could not create file 'chests'. IOExecption.");
				e.printStackTrace();
			}
		}
		try
		{
			DataInputStream ds = new DataInputStream(new FileInputStream(f));
			int numChests = ds.readInt();
			if (numChests > 0)
				sharedChests.clear();
			for (int i = 0; i < numChests; i++)
			{
				String name = ds.readUTF();
				String world = ds.readUTF();
				String creator = ds.readUTF();
				boolean locked = ds.readBoolean();

				double x = ds.readDouble();
				double y = ds.readDouble();
				double z = ds.readDouble();
				Location loc = new Location(plugin.getServer().getWorld(world), x, y, z);
				SharedChest sc = new SharedChest(name, world, creator, loc);
				if (locked)
					sc.lock();
				else
					sc.unlock();
				sharedChests.add(sc);
			}
			ds.close();
			plugin.getLogger().log(Level.INFO,
					"Successfully loaded " + numChests + " chests!");
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void save()
	{
		makeSureDataDirectoryAndChestsCreated();
		if(!canIO)
			return;
		File f = new File(plugin.getDataFolder(), "chests");
		try
		{
			DataOutputStream ds = new DataOutputStream(new FileOutputStream(f));
			ds.writeInt(sharedChests.size());
			for (SharedChest c : sharedChests)
			{
				ds.writeUTF(c.getName());
				ds.writeUTF(c.getWorld());
				ds.writeUTF(c.getCreator());
				ds.writeBoolean(c.isLocked());

				Location loc = c.getLocation();

				ds.writeDouble(loc.getX());
				ds.writeDouble(loc.getY());
				ds.writeDouble(loc.getZ());
			}
			ds.flush();
			ds.close();
			plugin.getLogger().log(Level.INFO, "Successfully saved " + sharedChests.size() + " Shared Chests.");
		} catch (FileNotFoundException e)
		{
			plugin.getLogger().log(Level.WARNING, "Could not find file 'chests'.");
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void makeSureDataDirectoryAndChestsCreated()
	{
		File dataFolder = plugin.getDataFolder();
		if (!dataFolder.exists())
		{
			dataFolder.mkdirs();
		}

		File chests = new File(plugin.getDataFolder(), "chests");
		if (dataFolder.exists())
		{
			if (!chests.exists())
			{
				try
				{
					chests.createNewFile();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		if (plugin.getDataFolder().exists() && chests.exists())
		{
			canIO = true;
		} else
		{
			canIO = false;
			plugin.getLogger()
					.log(Level.WARNING,
							"Folders/files not created, something bad is happening. Persistence is disabled for now.");
		}
	}

	public void shareChest(SharedChest sc)
	{
		sharedChests.add(sc);
	}

	public LinkedList<SharedChest> getSharedChests()
	{
		return sharedChests;
	}

	public boolean chestNameInUse(String name)
	{
		for (SharedChest sc : sharedChests)
		{
			if (sc.getName().equals(name))
				return true;
		}
		return false;
	}

	public SharedChest getChestAt(Location loc)
	{
		for (SharedChest sc : sharedChests)
		{
			if (sc.overlapping(loc))
				return sc;
		}
		return null;
	}

	public SharedChest getSharedChestByName(String name)
	{
		for (SharedChest sc : sharedChests)
		{
			if (sc.getName().equals(name))
				return sc;
		}
		return null;
	}

	public boolean removeChestByName(String name)
	{
		Iterator<SharedChest> iter = sharedChests.iterator();
		while (iter.hasNext())
		{
			SharedChest sc = iter.next();
			if (sc.getName().equals(name))
			{
				iter.remove();
				return true;
			}
		}
		return false;
	}

}