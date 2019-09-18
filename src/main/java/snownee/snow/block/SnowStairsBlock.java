package snownee.snow.block;

import java.util.List;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.StairsBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import snownee.kiwi.block.ModBlock;
import snownee.kiwi.util.Util;
import snownee.snow.MainModule;

public class SnowStairsBlock extends StairsBlock implements ISnowVariant
{

    public SnowStairsBlock(Properties properties)
    {
        super(Blocks.STONE.getDefaultState(), properties);
    }

    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new SnowTextureTile();
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player)
    {
        return ModBlock.pickBlock(state, target, world, pos, player);
    }

    @Override
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving)
    {
        if (state.hasTileEntity() && state.getBlock() != newState.getBlock())
        {
            worldIn.removeTileEntity(pos);
        }
        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }

    @Override
    public boolean canContainFluid(IBlockReader worldIn, BlockPos pos, BlockState state, Fluid fluidIn)
    {
        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        String key = Util.getTextureItem(stack, "0");
        if (!key.isEmpty())
        {
            tooltip.add(new TranslationTextComponent(key).applyTextStyle(TextFormatting.GRAY));
        }
    }

    //    @Override
    //    public BlockState getStateForPlacement(BlockItemUseContext context)
    //    {
    //        if (state.get(StairsBlock.HALF) == Half.TOP)
    //        {
    //            world.setBlockState(pos, getRaw(state, world, pos));
    //            spawnAsEntity(world, pos, new ItemStack(Items.SNOWBALL));
    //        }
    //        return super.getStateForPlacement(p_196258_1_);
    //    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items)
    {
        MainModule.fillTextureItems(ItemTags.STAIRS, this, items);
    }
}