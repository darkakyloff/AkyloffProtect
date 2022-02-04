package me.darkakyloff.protect;

import me.darkakyloff.protect.Events.DetectPermissionEvent;
import me.darkakyloff.protect.Events.DisableEvents;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class JavaMain
extends JavaPlugin
{

    private static JavaMain instance;

    public static JavaMain getInstance()
    {
        return instance;
    }

    @Override
    public void onEnable()
    {
        instance = this;
        new BotManager().getUpdates();

        Bukkit.getPluginManager().registerEvents(new DisableEvents(), this);
        Bukkit.getPluginManager().registerEvents(new DetectPermissionEvent(this, LuckPermsProvider.get()), this);
    }
}
