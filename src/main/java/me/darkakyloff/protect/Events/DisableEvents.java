package me.darkakyloff.protect.Events;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.*;

public class DisableEvents
implements Listener
{


    // Блокировка передвижения //
    @EventHandler
    public void onPlayerMove (PlayerMoveEvent event)
    {

        Location to = event.getTo();
        Location from = event.getFrom();

        if (to.getX() != from.getX() || to.getY() != from.getY() || to.getZ() != from.getZ())
        {
            if (event.getPlayer().isFlying() || to.getY() >= from.getY())
            {
                if (event.getPlayer().hasMetadata("detected")) event.setCancelled(true);
            }
        }
    }

    // Блокировка выполнения команд //
    @EventHandler
    public void onPlayerCommandPreprocess (PlayerCommandPreprocessEvent event)
    {
        if (event.getPlayer().hasMetadata("detected")) event.setCancelled(true);
    }

    // Блокировка отправки сообщений в чат //
    @EventHandler
    public void onAsyncPlayerCha (AsyncPlayerChatEvent event)
    {
        if (event.getPlayer().hasMetadata("detected")) event.setCancelled(true);
    }

    // Блокировка установки блоков //
    @EventHandler
    public void onBlockPlace (BlockPlaceEvent event)
    {
        if (event.getPlayer().hasMetadata("detected")) event.setCancelled(true);
    }

    // Блокировка ломания блоков //
    @EventHandler
    public void onBlockBreak (BlockBreakEvent event)
    {
        if (event.getPlayer().hasMetadata("detected")) event.setCancelled(true);
    }

    // Блокировка выбрасивания предметов //
    @EventHandler
    public void onPlayerDropItem (PlayerDropItemEvent event)
    {
        if (event.getPlayer().hasMetadata("detected")) event.setCancelled(true);
    }

    // Блокировка нанесения дамага //
    @EventHandler
    public void onEntityDamageByEntity (EntityDamageByEntityEvent event)
    {
        if (event.getDamager().hasMetadata("detected")) event.setCancelled(true);
    }

    // Блокировка нанесения дамага //
    @EventHandler
    public void onEntityDamage (EntityDamageEvent event)
    {
        if (event.getEntity().hasMetadata("detected")) event.setCancelled(true);
    }

    // Блокировка инветраря //
    @EventHandler
    public void onInventoryClick (InventoryClickEvent event)
    {
        if (event.getWhoClicked().hasMetadata("detected")) event.setCancelled(true);
    }

    // Блокировка инвентаря //
    @EventHandler
    public void onInventoryDrag (InventoryDragEvent event)
    {
        if (event.getWhoClicked().hasMetadata("detected")) event.setCancelled(true);
    }

    // Блокировка основных дейтсвий (клики ЛКМ/ПКМ) //
    @EventHandler
    public void onPlayerInteract (PlayerInteractEvent event)
    {
        if (event.getPlayer().hasMetadata("detected")) event.setCancelled(true);
    }

}
