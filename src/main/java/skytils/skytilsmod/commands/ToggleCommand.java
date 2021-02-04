package skytils.skytilsmod.commands;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import skytils.skytilsmod.Skytils;
import skytils.skytilsmod.core.FeatureManager;
import skytils.skytilsmod.core.Feature;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ToggleCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "sttoggle";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

//    @Override
//    public List<String> getCommandAliases() {
//        return Lists.newArrayList("sttest");
//    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        List<Feature> features = Skytils.FEATURE_MANAGER.getAllFeatures();
        return "/" + getCommandName() + " <" + String.join("/", features.stream().map(Feature::getName).collect(Collectors.toList())) + ">";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.addChatMessage(new ChatComponentText(getCommandUsage(sender)));
            return;
        }
        FeatureManager fm = Skytils.FEATURE_MANAGER;
        EntityPlayerSP player = (EntityPlayerSP) sender;
        Feature feature = fm.getFeatureByName(String.join(" ", args).toLowerCase(Locale.ENGLISH));
        player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + String.format("Set %s to %s", feature.getName(), feature.toggle())));
    }
}
