package roito.afterthedrizzle.client.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import roito.afterthedrizzle.AfterTheDrizzle;
import roito.afterthedrizzle.common.capability.CapabilityPlayerTemperature;
import roito.afterthedrizzle.common.environment.Humidity;
import roito.afterthedrizzle.common.item.ItemsRegistry;
import roito.afterthedrizzle.helper.EnvHelper;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = AfterTheDrizzle.MODID)
public final class OverlayEventHandler
{
    public final static ResourceLocation DEFAULT = new ResourceLocation("minecraft", "textures/gui/icons.png");

    private final static ThermometerBarRenderer BAR_0 = new ThermometerBarRenderer(Minecraft.getInstance());
    private final static RainGaugeBarRenderer BAR_1 = new RainGaugeBarRenderer(Minecraft.getInstance());
    private final static HygrometerBarRenderer BAR_2 = new HygrometerBarRenderer(Minecraft.getInstance());
    private final static PlayerTemperatureRenderer BAR_3 = new PlayerTemperatureRenderer(Minecraft.getInstance());


    @SubscribeEvent(receiveCanceled = true)
    public static void onEvent(RenderGameOverlayEvent.Pre event)
    {
        ClientPlayerEntity playerEntity = Minecraft.getInstance().player;
        if (playerEntity != null)
        {
            if (!playerEntity.getHeldItemMainhand().isEmpty())
            {
                if (playerEntity.getHeldItemMainhand().getItem().equals(ItemsRegistry.THERMOMETER))
                {
                    float temp = playerEntity.getEntityWorld().getBiome(playerEntity.getPosition()).getTemperature(playerEntity.getPosition());
                    Humidity h = Humidity.getHumid(playerEntity.getEntityWorld().getBiome(playerEntity.getPosition()).getDownfall(), temp);
                    double env = EnvHelper.getEnvDailyTemp(playerEntity.getEntityWorld().getBiome(playerEntity.getPosition()).getTemperature(playerEntity.getPosition()), h, playerEntity.getEntityWorld().getDayTime());
                    BAR_0.renderStatusBar(event.getWindow().getScaledWidth(), event.getWindow().getScaledHeight(), (float) env);
                }
                else if (playerEntity.getHeldItemMainhand().getItem().equals(ItemsRegistry.RAIN_GAUGE))
                {
                    BAR_1.renderStatusBar(event.getWindow().getScaledWidth(), event.getWindow().getScaledHeight(), playerEntity.getEntityWorld().getBiome(playerEntity.getPosition()).getDownfall());
                }
                else if (playerEntity.getHeldItemMainhand().getItem().equals(ItemsRegistry.HYGROMETER))
                {
                    BAR_2.renderStatusBar(event.getWindow().getScaledWidth(), event.getWindow().getScaledHeight(), playerEntity.getEntityWorld().getBiome(playerEntity.getPosition()).getTemperature(playerEntity.getPosition()), playerEntity.getEntityWorld().getBiome(playerEntity.getPosition()).getDownfall());
                }
            }
            playerEntity.getCapability(CapabilityPlayerTemperature.PLAYER_TEMP).ifPresent(t ->
            {
                float temp = playerEntity.getEntityWorld().getBiome(playerEntity.getPosition()).getTemperature(playerEntity.getPosition());
                Humidity h = Humidity.getHumid(playerEntity.getEntityWorld().getBiome(playerEntity.getPosition()).getDownfall(), temp);
                double env = EnvHelper.getEnvDailyTemp(playerEntity.getEntityWorld().getBiome(playerEntity.getPosition()).getTemperature(playerEntity.getPosition()), h, playerEntity.getEntityWorld().getDayTime());
                BAR_3.renderStatusBar(event.getWindow().getScaledWidth(), event.getWindow().getScaledHeight(), t.getPlayerTemperature(), env);
            });
        }
    }
}