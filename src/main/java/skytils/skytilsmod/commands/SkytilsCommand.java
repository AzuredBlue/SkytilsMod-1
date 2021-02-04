package skytils.skytilsmod.commands;

import com.google.common.collect.Lists;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import skytils.skytilsmod.Skytils;
import skytils.skytilsmod.core.FeatureManager;

import java.util.List;

public class SkytilsCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "skytils";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public List<String> getCommandAliases() {
        return Lists.newArrayList("st");
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/" + getCommandName();
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        FeatureManager fm = Skytils.FEATURE_MANAGER;
        EntityPlayerSP player = (EntityPlayerSP) sender;
        player.addChatComponentMessage(new ChatComponentText("Thank you for using Skytils!"));
        player.addChatComponentMessage(new ChatComponentText(fm.list()));
    }
}
