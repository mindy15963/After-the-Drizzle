package roito.afterthedrizzle.client.color.block;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IEnviromentBlockReader;
import roito.afterthedrizzle.common.block.HybridizableFlowerBlock;
import roito.afterthedrizzle.common.environment.flower.FlowerColor;

import javax.annotation.Nullable;

public class HybridizableFlowerBlockColor implements IBlockColor
{
    @Override
    public int getColor(BlockState state, @Nullable IEnviromentBlockReader world, @Nullable BlockPos pos, int index)
    {
        if (index == 1)
        {
            return FlowerColor.getFlowerColor(state.get(HybridizableFlowerBlock.FLOWER_COLOR).getName()).getColorValue();
        }
        return -1;
    }
}
