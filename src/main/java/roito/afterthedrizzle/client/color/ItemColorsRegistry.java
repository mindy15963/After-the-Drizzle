package roito.afterthedrizzle.client.color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import roito.afterthedrizzle.common.item.ItemsRegistry;

import static roito.afterthedrizzle.common.fluid.FluidsRegistry.ITEMS;

public final class ItemColorsRegistry
{
    private final static IItemColor BUCKET_COLOR = new BucketItemColors();
    private final static IItemColor CUP_COLOR = new CupItemColors();
    private final static IItemColor BOTTLE_COLOR = new BottleItemColors();

    public ItemColorsRegistry()
    {
        ITEMS.getEntries().forEach(e -> Minecraft.getInstance().getItemColors().register(BUCKET_COLOR, e.get()));
        Minecraft.getInstance().getItemColors().register(CUP_COLOR, ItemsRegistry.PORCELAIN_CUP_DRINK);
        Minecraft.getInstance().getItemColors().register(BOTTLE_COLOR, ItemsRegistry.BOTTLE_DRINK);
    }
}