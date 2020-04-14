package roito.afterthedrizzle.common.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.fluids.FluidStack;
import roito.afterthedrizzle.common.drink.DrinkIngredientsManager;
import roito.afterthedrizzle.common.fluid.FluidsRegistry;
import roito.afterthedrizzle.common.item.ItemsRegistry;
import roito.afterthedrizzle.common.recipe.bamboo_tray.*;
import roito.afterthedrizzle.common.recipe.drink.DrinkRecipeInput;
import roito.afterthedrizzle.common.recipe.drink.DrinkRecipeManager;

public final class RecipesRegistry
{
    public final static IBambooTrayRecipeManager MANAGER_BAMBOO_TRAY_OUTDOORS = new BambooTrayOutdoorsManager();
    public final static IBambooTrayRecipeManager MANAGER_BAMBOO_TRAY_INDOORS = new BambooTrayIndoorsManager();
    public final static IBambooTrayRecipeManager MANAGER_BAMBOO_TRAY_IN_RAIN = new BambooTrayWetManager();
    public final static IBambooTrayRecipeManager MANAGER_BAMBOO_TRAY_BAKE = new BambooTrayBakeManager();
    public final static IBambooTrayRecipeManager MANAGER_BAMBOO_TRAY_PROCESS = new BambooTrayProcessManager();
    public final static DrinkRecipeManager MANAGER_DRINK_MAKER = new DrinkRecipeManager();

    public RecipesRegistry()
    {
        addBasketOutdoorsRecipes();
        addBasketIndoorsRecipes();
        addBasketWetRecipes();
        registerDrinkIngredients();
        addDrinkRecipes();
    }

    private static void addBasketOutdoorsRecipes()
    {
        MANAGER_BAMBOO_TRAY_OUTDOORS.add(new BambooTaryRecipe(new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.LEATHER)));
        MANAGER_BAMBOO_TRAY_OUTDOORS.add(new BambooTaryRecipe(new ItemStack(Items.RABBIT), new ItemStack(ItemsRegistry.RABBIT_JERKY)));
        MANAGER_BAMBOO_TRAY_OUTDOORS.add(new BambooTaryRecipe(new ItemStack(Items.PORKCHOP), new ItemStack(ItemsRegistry.PORK_JERKY)));
        MANAGER_BAMBOO_TRAY_OUTDOORS.add(new BambooTaryRecipe(new ItemStack(Items.BEEF), new ItemStack(ItemsRegistry.BEEF_JERKY)));
        MANAGER_BAMBOO_TRAY_OUTDOORS.add(new BambooTaryRecipe(new ItemStack(Items.MUTTON), new ItemStack(ItemsRegistry.MUTTON_JERKY)));
        MANAGER_BAMBOO_TRAY_OUTDOORS.add(new BambooTaryRecipe(new ItemStack(Items.CHICKEN), new ItemStack(ItemsRegistry.CHICKEN_JERKY)));
        MANAGER_BAMBOO_TRAY_OUTDOORS.add(new BambooTaryRecipe(new ItemStack(Items.BEETROOT), new ItemStack(ItemsRegistry.DRIED_BEETROOT)));
        MANAGER_BAMBOO_TRAY_OUTDOORS.add(new BambooTaryRecipe(new ItemStack(Items.CARROT), new ItemStack(ItemsRegistry.DRIED_CARROT)));
        MANAGER_BAMBOO_TRAY_OUTDOORS.add(new BambooTaryRecipe("forge:food/jerky", new ItemStack(Items.LEATHER)));
    }

    private static void addBasketIndoorsRecipes()
    {
        MANAGER_BAMBOO_TRAY_INDOORS.add(new BambooTaryRecipe("forge:food/jerky", new ItemStack(Items.ROTTEN_FLESH)));
        MANAGER_BAMBOO_TRAY_INDOORS.add(new BambooTaryRecipe(new ItemStack(Items.SPIDER_EYE), new ItemStack(Items.FERMENTED_SPIDER_EYE)));
    }

    private static void addBasketWetRecipes()
    {
        MANAGER_BAMBOO_TRAY_IN_RAIN.add(new BambooTaryRecipe(new ItemStack(ItemsRegistry.RABBIT_JERKY), new ItemStack(Items.RABBIT)));
        MANAGER_BAMBOO_TRAY_IN_RAIN.add(new BambooTaryRecipe(new ItemStack(ItemsRegistry.PORK_JERKY), new ItemStack(Items.PORKCHOP)));
        MANAGER_BAMBOO_TRAY_IN_RAIN.add(new BambooTaryRecipe(new ItemStack(ItemsRegistry.BEEF_JERKY), new ItemStack(Items.BEEF)));
        MANAGER_BAMBOO_TRAY_IN_RAIN.add(new BambooTaryRecipe(new ItemStack(ItemsRegistry.MUTTON_JERKY), new ItemStack(Items.MUTTON)));
        MANAGER_BAMBOO_TRAY_IN_RAIN.add(new BambooTaryRecipe(new ItemStack(ItemsRegistry.CHICKEN_JERKY), new ItemStack(Items.CHICKEN)));
        MANAGER_BAMBOO_TRAY_IN_RAIN.add(new BambooTaryRecipe(new ItemStack(ItemsRegistry.DRIED_CARROT), new ItemStack(Items.CARROT)));
        MANAGER_BAMBOO_TRAY_IN_RAIN.add(new BambooTaryRecipe(new ItemStack(ItemsRegistry.DRIED_BEETROOT), new ItemStack(Items.BEETROOT)));
    }

    private static void registerDrinkIngredients()
    {
        DrinkIngredientsManager.registerIngredientItem(Items.SUGAR, "sugar");
        DrinkIngredientsManager.registerIngredientItem(ItemsRegistry.GREEN_TEA_LEAVES, "green_tea_leaves");
        DrinkIngredientsManager.registerIngredientItem(ItemsRegistry.BLACK_TEA_LEAVES, "black_tea_leaves");
        DrinkIngredientsManager.registerIngredientItem(ItemsRegistry.GREEN_TEA_BAG, "green_tea_bag");
        DrinkIngredientsManager.registerIngredientItem(ItemsRegistry.BLACK_TEA_BAG, "black_tea_bag");
    }

    private static void addDrinkRecipes()
    {
        MANAGER_DRINK_MAKER.add(new DrinkRecipeInput("sugar", "sugar", "sugar", "sugar"), new FluidStack(FluidsRegistry.SUGARY_WATER_STILL.get(), 500));
        MANAGER_DRINK_MAKER.add(new DrinkRecipeInput("green_tea_leaves", "green_tea_leaves", "green_tea_leaves", "green_tea_leaves"), new FluidStack(FluidsRegistry.GREEN_TEA_STILL.get(), 500));
        MANAGER_DRINK_MAKER.add(new DrinkRecipeInput("green_tea_bag"), new FluidStack(FluidsRegistry.GREEN_TEA_STILL.get(), 500));
        MANAGER_DRINK_MAKER.add(new DrinkRecipeInput("black_tea_leaves", "black_tea_leaves", "black_tea_leaves", "black_tea_leaves"), new FluidStack(FluidsRegistry.BLACK_TEA_STILL.get(), 500));
        MANAGER_DRINK_MAKER.add(new DrinkRecipeInput("black_tea_bag"), new FluidStack(FluidsRegistry.BLACK_TEA_STILL.get(), 500));
    }
}
