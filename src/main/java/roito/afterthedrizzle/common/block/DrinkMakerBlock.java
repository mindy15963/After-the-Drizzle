package roito.afterthedrizzle.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;
import roito.afterthedrizzle.common.tileentity.DrinkMakerTileEntity;
import roito.afterthedrizzle.common.tileentity.TileEntityTypeRegistry;
import roito.afterthedrizzle.helper.BlocksHelper;

import javax.annotation.Nullable;

public class DrinkMakerBlock extends NormalHorizontalBlock
{
    public static final BooleanProperty LEFT = BooleanProperty.create("left");
    boolean flag = false;

    protected DrinkMakerBlock()
    {
        super(Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(0.5F), "drink_maker");
        this.setDefaultState(this.getStateContainer().getBaseState().with(HORIZONTAL_FACING, Direction.NORTH).with(LEFT, true));
    }

    @Override
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos)
    {
        return false;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos)
    {
        return true;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(HORIZONTAL_FACING, LEFT);
    }

    @Override
    @SuppressWarnings("deprecation")
    @OnlyIn(Dist.CLIENT)
    public float getAmbientOcclusionLightValue(BlockState state, IBlockReader worldIn, BlockPos pos)
    {
        return 0.5F;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos)
    {
        return worldIn.isAirBlock(pos.down());
    }

    @Override
    public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack)
    {
        super.harvestBlock(worldIn, player, pos, state, te, stack);
        Direction enumfacing = state.get(HORIZONTAL_FACING);

        if (state.get(LEFT))
        {
            if (worldIn.getBlockState(pos.offset(BlocksHelper.getNextHorizontal(enumfacing))).getBlock() == this)
            {
                worldIn.destroyBlock(pos.offset(BlocksHelper.getNextHorizontal(enumfacing)), true);
            }
        }
        else if (worldIn.getBlockState(pos.offset(BlocksHelper.getPreviousHorizontal(enumfacing))).getBlock() == this)
        {
            worldIn.destroyBlock(pos.offset(BlocksHelper.getPreviousHorizontal(enumfacing)), true);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving)
    {
        if (state.hasTileEntity() && !(newState.getBlock() == this))
        {
            dropItems(worldIn, pos);
            worldIn.removeTileEntity(pos);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean causesSuffocation(BlockState state, IBlockReader worldIn, BlockPos pos)
    {
        return false;
    }

    private void dropItems(World worldIn, BlockPos pos)
    {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te != null)
        {
            te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.UP).ifPresent(inv ->
            {
                for (int i = inv.getSlots() - 1; i >= 0; --i)
                {
                    if (inv.getStackInSlot(i) != ItemStack.EMPTY)
                    {
                        Block.spawnAsEntity(worldIn, pos, inv.getStackInSlot(i));
                        ((IItemHandlerModifiable) inv).setStackInSlot(i, ItemStack.EMPTY);
                    }
                }
            });
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if (!worldIn.isRemote)
        {
            flag = false;
            if (!state.get(LEFT))
            {
                pos = pos.offset(BlocksHelper.getPreviousHorizontal(state.get(HORIZONTAL_FACING)));
            }
            TileEntity te = worldIn.getTileEntity(pos);
            FluidUtil.getFluidHandler(ItemHandlerHelper.copyStackWithSize(player.getHeldItem(handIn), 1)).ifPresent(item ->
            {
                if (item.getContainer().isEmpty())
                {
                    te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, hit.getFace()).ifPresent(fluid ->
                            flag = FluidUtil.interactWithFluidHandler(player, handIn, fluid));
                }
            });
            if (flag) return true;
            if (te instanceof DrinkMakerTileEntity)
            {
                NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) te, te.getPos());
            }
        }
        return true;
    }

    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return state.get(LEFT);
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return TileEntityTypeRegistry.DRINK_MAKER.create();
    }
}
