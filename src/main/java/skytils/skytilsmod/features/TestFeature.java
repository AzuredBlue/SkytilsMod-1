package skytils.skytilsmod.features;

import com.google.common.collect.Lists;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.ClientCommandHandler;
import skytils.skytilsmod.Skytils;
import skytils.skytilsmod.core.Feature;
import skytils.skytilsmod.core.FeatureManager;

import java.util.List;

public class TestFeature extends Feature {
    public TestFeature() {
        super(-1, "Test Feature", true);
        ClientCommandHandler.instance.registerCommand(new TestCommand());
    }

    class TestCommand extends CommandBase {
        @Override
        public String getCommandName() {
            return "test";
        }

        @Override
        public int getRequiredPermissionLevel() {
            return 0;
        }

        @Override
        public List<String> getCommandAliases() {
            return Lists.newArrayList("sttest");
        }

        @Override
        public String getCommandUsage(ICommandSender sender) {
            return "/" + getCommandName();
        }

        @Override
        public void processCommand(ICommandSender sender, String[] args) {
            FeatureManager fm = Skytils.FEATURE_MANAGER;
            EntityPlayerSP player = (EntityPlayerSP) sender;
            player.addChatComponentMessage(new ChatComponentText("Test Feature is currently set to: " + TestFeature.this.getToggled()));
        }
    }
}
