package skytils.skytilsmod.commands;

import com.google.common.collect.Lists;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import java.util.List;

public class SkytilsCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "skytils";
    }

    @Override
    public List<String> getCommandAliases() {
        return Lists.newArrayList("st");
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "/" + getCommandName();
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] strings) throws CommandException {
        EntityPlayerSP player = (EntityPlayerSP) iCommandSender;
        player.addChatComponentMessage(new ChatComponentText("Thanks for using Skytils!"));
    }
}
