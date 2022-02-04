package me.darkakyloff.protect.Events;

import me.darkakyloff.protect.JavaMain;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.event.EventBus;
import net.luckperms.api.event.node.NodeMutateEvent;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import java.util.UUID;

public class DetectPermissionEvent
implements Listener
{

    private static Player player;
    private static JavaMain plugin;

    public static Player getPlayer()
    {
        return player;
    }


    @EventHandler
    public void onPlayerJoin (PlayerJoinEvent event)
    {
        player = event.getPlayer();

        if (player.isOp() || player.hasPermission("*"))
        {
            new DetectAction().onDetect();
        }

    }
    public DetectPermissionEvent(JavaMain plugin, LuckPerms luckPerms)
    {
        DetectPermissionEvent.plugin = plugin;
        EventBus eventBus = luckPerms.getEventBus();
        eventBus.subscribe(DetectPermissionEvent.plugin, NodeMutateEvent.class, this::onNodeMutate);
    }

    public void onNodeMutate(NodeMutateEvent event)
    {
        if (event.getTarget() instanceof User)
        {
            User user = (User) event.getTarget();
            UUID uuid = user.getUniqueId();
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
            if (offlinePlayer.isOnline())
            {
                Player player = offlinePlayer.getPlayer();

                if (player.isOp() || player.hasPermission("*"))
                {
                    new DetectAction().onDetect();
                }
            }
        }
    }


}
