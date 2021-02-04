package skytils.skytilsmod.commands;

import com.google.common.collect.Lists;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import skytils.skytilsmod.Skytils;
import skytils.skytilsmod.core.Feature;
import skytils.skytilsmod.core.FeatureManager;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

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
        List<Feature> features = Skytils.FEATURE_MANAGER.getAllFeatures();
        return "/" + getCommandName() + "\n/" + getCommandName() + " toggle <" + String.join("/", features.stream().map(Feature::getName).collect(Collectors.toList())) + ">";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        FeatureManager fm = Skytils.FEATURE_MANAGER;
        EntityPlayerSP player = (EntityPlayerSP) sender;
        if (args.length == 0) {
            player.addChatMessage(new ChatComponentText("Thank you for using Skytils!"));
            player.addChatMessage(new ChatComponentText(fm.list()));
            return;
        }
        String subcommand = args[0].toLowerCase(Locale.ENGLISH);
        switch (subcommand) {
            case "toggle":
                if (args.length == 1) {
                    player.addChatMessage(new ChatComponentText("Please specify the feature you want to toggle"));
                    return;
                }
                Feature selected = fm.getFeatureByName(String.join(" ", Arrays.copyOfRange(args, 1, args.length)));
                player.addChatMessage(new ChatComponentText(selected.getName() + " has been toggled to " + selected.toggle()));
                break;
            default:
                player.addChatMessage(new ChatComponentText("/" + getCommandName() + " toggle <" + String.join("/", fm.getAllFeatures().stream().map(Feature::getName).collect(Collectors.toList())) + ">"));
        }
    }
}
