package cloud.lemonslice.afterthedrizzle.common.recipe.bamboo_tray;

import cloud.lemonslice.afterthedrizzle.common.recipe.serializer.RecipeSerializersRegistry;
import cloud.lemonslice.afterthedrizzle.common.recipe.type.NormalRecipeTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class BambooTrayInRainRecipe extends BambooTraySingleInRecipe
{
    public BambooTrayInRainRecipe(ResourceLocation idIn, String groupIn, Ingredient ingredientIn, ItemStack resultIn, int workTime)
    {
        super(idIn, groupIn, ingredientIn, resultIn, workTime);
    }

    @Override
    public IRecipeSerializer<?> getSerializer()
    {
        return RecipeSerializersRegistry.BAMBOO_TRAY_IN_RAIN.get();
    }

    @Override
    public IRecipeType<?> getType()
    {
        return NormalRecipeTypes.BT_IN_RAIN;
    }
}
