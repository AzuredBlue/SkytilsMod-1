package skytils.skytilsmod;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import skytils.skytilsmod.commands.SkytilsCommand;
import skytils.skytilsmod.commands.ToggleCommand;
import skytils.skytilsmod.core.FeatureManager;

@Mod(modid = Skytils.MODID, name = Skytils.MOD_NAME, version = Skytils.VERSION, acceptedMinecraftVersions = "[1.8.9]", clientSideOnly = true)
public class Skytils {
    public static final String MODID = "skytils";
    public static final String MOD_NAME = "Skytils";
    public static final String VERSION = "1.0";
    public static final FeatureManager FEATURE_MANAGER = new FeatureManager();

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ClientCommandHandler.instance.registerCommand(new SkytilsCommand());
        ClientCommandHandler.instance.registerCommand(new ToggleCommand());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
//        Skytils.FEATURE_MANAGER.init();
    }

}
