package skytils.skytilsmod;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import skytils.skytilsmod.commands.SkytilsCommand;

@Mod(modid = Skytils.MODID, name = Skytils.MOD_NAME, version = Skytils.VERSION, acceptedMinecraftVersions = "[1.8.9]", clientSideOnly = true)
public class Skytils {
    public static final String MODID = "skytils";
    public static final String MOD_NAME = "Skytils";
    public static final String VERSION = "1.0";

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ClientCommandHandler.instance.registerCommand(new SkytilsCommand());
        MinecraftForge.EVENT_BUS.register(this);
    }

}
