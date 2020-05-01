package roito.afterthedrizzle.common.recipe.type;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import roito.afterthedrizzle.common.environment.FlowerColor;
import roito.afterthedrizzle.common.item.HybridizableFlowerBlockItem;

public class FlowerDyeRecipe extends SpecialRecipe
{

    public FlowerDyeRecipe(ResourceLocation idIn)
    {
        super(idIn);
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn)
    {
        ItemStack itemstack = ItemStack.EMPTY;

        for (int i = 0; i < inv.getSizeInventory(); ++i)
        {
            ItemStack itemstack1 = inv.getStackInSlot(i);
            if (!itemstack1.isEmpty())
            {
                if (itemstack1.getItem() instanceof HybridizableFlowerBlockItem)
                {
                    if (!itemstack.isEmpty())
                    {
                        return false;
                    }

                    itemstack = itemstack1;
                }
            }
        }

        return !itemstack.isEmpty();
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory inv)
    {
        ItemStack itemstack = ItemStack.EMPTY;

        for (int i = 0; i < inv.getSizeInventory(); ++i)
        {
            ItemStack itemstack1 = inv.getStackInSlot(i);
            if (!itemstack1.isEmpty())
            {
                if (itemstack1.getItem() instanceof HybridizableFlowerBlockItem)
                {
                    if (!itemstack.isEmpty())
                    {
                        return ItemStack.EMPTY;
                    }

                    itemstack = itemstack1;
                }
            }
        }
        FlowerColor color = FlowerColor.getFlowerColor(itemstack.getOrCreateTag().getString("color"));
        if (color.getDye() != null)
        {
            return new ItemStack(color.getDye());
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canFit(int width, int height)
    {
        return true;
    }

    @Override
    public IRecipeSerializer<?> getSerializer()
    {
        return RecipeSerializersRegistry.CRAFTING_SPECIAL_FLOWERDYE.get();
    }
}