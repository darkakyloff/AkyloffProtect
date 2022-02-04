package me.darkakyloff.protect.Events;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import me.darkakyloff.protect.BotManager;
import me.darkakyloff.protect.JavaMain;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.metadata.FixedMetadataValue;

public class DetectAction
{

    String PLAYER_ACCEPT = JavaMain.getInstance().getConfig().getString("PLAYER-MESSAGES.ACCEPT");
    String PLAYER_ACCEPTED = JavaMain.getInstance().getConfig().getString("PLAYER-MESSAGES.ACCEPTED");
    String ADMIN_ACCEPT = JavaMain.getInstance().getConfig().getString("ADMIN-MESSAGES.ACCEPT").replace("{name}", DetectPermissionEvent.getPlayer().getName());
    String ADMIN_ACCEPTED = JavaMain.getInstance().getConfig().getString("ADMIN-MESSAGES.ACCEPTED").replace("{name}", DetectPermissionEvent.getPlayer().getName());
    String ADMIN_BAN = JavaMain.getInstance().getConfig().getString("ADMIN-MESSAGES.BAN").replace("{name}", DetectPermissionEvent.getPlayer().getName());

    String KEYBOARD_TEXT = JavaMain.getInstance().getConfig().getString("TG-SETTINGS.MESSAGE").replace("{name}", DetectPermissionEvent.getPlayer().getName()).replace("{address}", DetectPermissionEvent.getPlayer().getAddress().getHostName()).replace("{server}", Bukkit.getServerName());


    // Дейтвие, если у игрока обнаружит право //
    public void onDetect()
    {
        DetectPermissionEvent.getPlayer().setMetadata("detected", new FixedMetadataValue(JavaMain.getInstance(), "darkakyloff"));

        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup(new InlineKeyboardButton("✅ Авторизировать").callbackData("accept"), new InlineKeyboardButton("❌ Заблокировать").callbackData("cancel"));

        BotManager.Bot.execute(new SendMessage(BotManager.CHET_ID, KEYBOARD_TEXT).replyMarkup(inlineKeyboard));
        DetectPermissionEvent.getPlayer().sendMessage((ChatColor.translateAlternateColorCodes('&', PLAYER_ACCEPT)));
        Bukkit.getOnlinePlayers().stream().filter(player -> player.hasPermission("protect.broadcast") && !player.hasMetadata("detected")).forEach(player -> player.sendMessage(ChatColor.translateAlternateColorCodes('&', ADMIN_ACCEPT)));
    }

    // Дейтсвие, если авторизация будет подтверждена //
    public void onAccept()
    {
        DetectPermissionEvent.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', PLAYER_ACCEPTED));
        Bukkit.getOnlinePlayers().stream().filter(player -> player.hasPermission("protect.broadcast") && !player.hasMetadata("detected")).forEach(player -> player.sendMessage((ChatColor.translateAlternateColorCodes('&', ADMIN_ACCEPTED))));
        DetectPermissionEvent.getPlayer().removeMetadata("detected", JavaMain.getInstance());
    }

    // Дейтвие, если авторизация будет отклонена //
    public void onCancel()
    {
        String COMMAND = JavaMain.getInstance().getConfig().getString("PLAYER-MESSAGES");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), COMMAND);
        Bukkit.getOnlinePlayers().stream().filter(player -> player.hasPermission("protect.broadcast") && !player.hasMetadata("detected")).forEach(player -> player.sendMessage(ChatColor.translateAlternateColorCodes('&', ADMIN_BAN)));

    }

}
