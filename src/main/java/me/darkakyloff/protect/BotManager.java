package me.darkakyloff.protect;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.EditMessageText;
import me.darkakyloff.protect.Events.DetectAction;
import me.darkakyloff.protect.Events.DetectPermissionEvent;

public class BotManager
{
    public static String BOT_KEY = JavaMain.getInstance().getConfig().getString("TG-SETTINGS.BOT-KEY");
    public static String CHET_ID = JavaMain.getInstance().getConfig().getString("TG-SETTINGS.CHAT-ID");

    public static TelegramBot Bot = new TelegramBot(BOT_KEY);


    // Приём обновлений бота //
    public void getUpdates()
    {
        BotManager.Bot.setUpdatesListener(updates ->
        {
            updates.forEach(this::TreatmentUpdates);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }


    // Обработка обновлений //
    public void TreatmentUpdates(Update update)
    {
        CallbackQuery callbackQuery = update.callbackQuery();

        switch (callbackQuery.data())
        {
            case "accept":
            {
                new DetectAction().onAccept();
                String ACCEPT_MESSAGE = JavaMain.getInstance().getConfig().getString("TG-SETTINGS.ACCEPT").replace("{user}", callbackQuery.from().username()).replace("{name}", DetectPermissionEvent.getPlayer().getName());
                Bot.execute(new EditMessageText(CHET_ID, callbackQuery.message().messageId(), ACCEPT_MESSAGE));
                break;
            }
            case "cancel":
            {
                String CANCEL_MESSAGE = JavaMain.getInstance().getConfig().getString("TG-SETTINGS.CANCEL").replace("{user}", callbackQuery.from().username()).replace("{name}", DetectPermissionEvent.getPlayer().getName());
                Bot.execute(new EditMessageText(CHET_ID, callbackQuery.message().messageId(), CANCEL_MESSAGE));
                new DetectAction().onCancel();
            }
        }
    }

}
