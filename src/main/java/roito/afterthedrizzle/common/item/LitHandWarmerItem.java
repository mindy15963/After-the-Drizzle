package roito.afterthedrizzle.common.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;

public class LitHandWarmerItem extends NormalItem implements IItemWithTemperature
{

    public LitHandWarmerItem()
    {
        super("lit_handwarmer", getNormalItemProperties().containerItem(ItemsRegistry.HANDWARMER).maxDamage(120));
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context)
    {
        return ActionResultType.FAIL;
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        if (stack.isDamageable() && entityIn instanceof PlayerEntity && (((PlayerEntity) entityIn).getHeldItemMainhand().equals(stack) || ((PlayerEntity) entityIn).getHeldItemOffhand().equals(stack)))
        {
            if (!((PlayerEntity) entityIn).getCooldownTracker().hasCooldown(this))
            {
                ((PlayerEntity) entityIn).getCooldownTracker().setCooldown(this, 100);
                stack.setDamage(stack.getDamage() + 1);
                if (stack.getDamage() + 1 >= stack.getMaxDamage())
                {
                    ItemHandlerHelper.giveItemToPlayer((PlayerEntity) entityIn, stack.getContainerItem());
                    stack.shrink(1);
                }
            }
        }
    }

    @Override
    public String getResistanceType()
    {
        return "Cold";
    }

    @Override
    public int getResistancePoint()
    {
        return 4;
    }

    @Override
    public boolean shouldHeld()
    {
        return true;
    }
}