package skytils.skytilsmod.features.impl.dungeons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S45PacketTitle;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import skytils.skytilsmod.Skytils;
import skytils.skytilsmod.events.GuiContainerEvent;
import skytils.skytilsmod.events.ReceivePacketEvent;
import skytils.skytilsmod.utils.Utils;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DungeonsFeatures {

    private static final Minecraft mc = Minecraft.getMinecraft();
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChat(ClientChatReceivedEvent event) {
        if (!Utils.inSkyblock) return;
        String unformatted = StringUtils.stripControlCodes(event.message.getUnformattedText());


        if (Skytils.config.hideAbilities && Utils.inDungeons) {
            if (unformatted.contains("is now available!") || unformatted.contains("is ready to use!")) {
                event.setCanceled(true);
            }
        }
        if (Skytils.config.hideBlessings && Utils.inDungeons) {
            if (unformatted.startsWith("DUNGEON BUFF!") || unformatted.startsWith("A Blessing") || unformatted.contains("has obtained Blessing of") || unformatted.contains("Grants you") || unformatted.contains("Granted you") || unformatted.contains("found a Wither Essence!")) {
                event.setCanceled(true);
            }
        }
        if (Skytils.config.hideMilestones && Utils.inDungeons) {
            if (unformatted.startsWith("Mage Milestone") || unformatted.startsWith("Berserk Milestone") || unformatted.startsWith("Archer Milestone") || unformatted.startsWith("Tank Milestone") || unformatted.startsWith("Healer Milestone")) {
                event.setCanceled(true);
            }
        }
        if (Utils.inDungeons && Skytils.config.autoCopyFailToClipboard) {
            Matcher deathFailMatcher = Pattern.compile("(?:^ ☠ .+ and became a ghost\\.$)|(?:^PUZZLE FAIL! .+$)").matcher(unformatted);
            if (deathFailMatcher.matches()) {
                GuiScreen.setClipboardString(unformatted);
                mc.thePlayer.addChatMessage(new ChatComponentText("\u00a7aCopied death/fail to clipboard."));
            }
        }
    }
    
    // Show hidden fels
    @SubscribeEvent
    public void onRenderLivingPre(RenderLivingEvent.Pre event) {
        if (Utils.inDungeons) {
            if (Skytils.config.showHiddenFels && event.entity.isInvisible() && event.entity instanceof EntityEnderman) {
                event.entity.setInvisible(false);
            }
        }
    }

    // Spirit leap names
    @SubscribeEvent
    public void onGuiDrawPost(GuiScreenEvent.DrawScreenEvent.Post event) {
        if (!Utils.inSkyblock) return;
        if (event.gui instanceof GuiChest) {
            GuiChest inventory = (GuiChest) event.gui;
            Container containerChest = inventory.inventorySlots;
            if (containerChest instanceof ContainerChest) {
                ScaledResolution sr = new ScaledResolution(mc);
                FontRenderer fr = mc.fontRendererObj;
                int guiLeft = (sr.getScaledWidth() - 176) / 2;
                int guiTop = (sr.getScaledHeight() - 222) / 2;

                List<Slot> invSlots = inventory.inventorySlots.inventorySlots;
                String displayName = ((ContainerChest) containerChest).getLowerChestInventory().getDisplayName().getUnformattedText().trim();
                int chestSize = inventory.inventorySlots.inventorySlots.size();

                if (Skytils.config.spiritLeapNames && Utils.inDungeons && displayName.equals("Spirit Leap")) {
                    int people = 0;
                    for (Slot slot : invSlots) {
                        if (slot.inventory == mc.thePlayer.inventory) continue;
                        if (slot.getHasStack()) {
                            ItemStack item = slot.getStack();
                            if (item.getItem() == Items.skull) {
                                people++;
                                String name = item.getDisplayName();

                                //slot is 16x16
                                int x = guiLeft + slot.xDisplayPosition + 8;
                                int y = guiTop + slot.yDisplayPosition;
                                // Move down when chest isn't 6 rows
                                if (chestSize != 90) y += (6 - (chestSize - 36) / 9) * 9;

                                if (people % 2 != 0) {
                                    y -= 15;
                                } else {
                                    y += 20;
                                }

                                String text = fr.trimStringToWidth(name, 32);
                                x -= fr.getStringWidth(text) / 2;

                                boolean shouldDrawBkg = true;
                                if (Skytils.usingNEU) {
                                    try {
                                        Class<?> neuClass = Class.forName("io.github.moulberry.notenoughupdates.NotEnoughUpdates");
                                        Field neuInstance = neuClass.getDeclaredField("INSTANCE");
                                        Object neu = neuInstance.get(null);
                                        Field neuConfig = neuClass.getDeclaredField("config");
                                        Object config = neuConfig.get(neu);
                                        Field improvedSBMenu = config.getClass().getDeclaredField("improvedSBMenu");
                                        Object improvedSBMenuS = improvedSBMenu.get(config);
                                        Field enableSbMenus = improvedSBMenuS.getClass().getDeclaredField("enableSbMenus");
                                        boolean customGuiEnabled = enableSbMenus.getBoolean(improvedSBMenuS);
                                        if (customGuiEnabled) shouldDrawBkg = false;
                                    } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException ignored) {
                                    }
                                }

                                GL11.glPushMatrix();
                                GL11.glTranslated(0, 0, 10);
                                if (shouldDrawBkg) Gui.drawRect(x - 2, y - 2, x + fr.getStringWidth(text) + 2, y + fr.FONT_HEIGHT + 2, new Color(47, 40, 40).getRGB());
                                fr.drawStringWithShadow(text, x, y, new Color(255, 255,255).getRGB());
                                GL11.glTranslated(0, 0, -10);
                                GL11.glPopMatrix();

                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onDrawSlot(GuiContainerEvent.DrawSlotEvent.Pre event) {
        if (!Utils.inSkyblock) return;
        Slot slot = event.slot;
        if (event.container instanceof ContainerChest) {
            ContainerChest cc = (ContainerChest) event.container;
            String displayName = cc.getLowerChestInventory().getDisplayName().getUnformattedText().trim();
            if (slot.getHasStack()) {
                ItemStack item = slot.getStack();
                if (Skytils.config.spiritLeapNames && displayName.equals("Spirit Leap")) {
                    if (item.getItem() == Item.getItemFromBlock(Blocks.stained_glass_pane)) {
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onReceivePacket(ReceivePacketEvent event) {
        if (event.packet instanceof S45PacketTitle) {
            S45PacketTitle packet = (S45PacketTitle) event.packet;
            if (packet.getMessage() != null && mc.thePlayer != null) {
                String unformatted = StringUtils.stripControlCodes(packet.getMessage().getUnformattedText());
                if (Skytils.config.hideTerminalCompletionTitles && Utils.inDungeons && !unformatted.contains(mc.thePlayer.getName()) && (unformatted.contains("activated a terminal!") || unformatted.contains("completed a device!") || unformatted.contains("activated a lever!"))) {
                    event.setCanceled(true);
                }
            }
        }
    }
}



