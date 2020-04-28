package nl.beerik.easyenchantments.block;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.ItemStackHandler;
import nl.beerik.easyenchantments.init.EETileEntityTypes;
import nl.beerik.easyenchantments.tile.EnchanterTileEntity;

public class EnchanterBlock extends Block {

	public EnchanterBlock(Properties properties) {
		super(properties);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Nullable
	@Override
	public TileEntity createTileEntity(final BlockState state, final IBlockReader world) {
		return EETileEntityTypes.ENCHANTER.get().create();
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onReplaced(BlockState oldState, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (oldState.getBlock() != newState.getBlock()) {
			TileEntity tileEntity = worldIn.getTileEntity(pos);
			if (tileEntity instanceof EnchanterTileEntity) {
				final ItemStackHandler inventory = ((EnchanterTileEntity) tileEntity).inventory;
				for (int slot = 0; slot < inventory.getSlots(); ++slot)
					InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), inventory.getStackInSlot(slot));
			}
		}
		super.onReplaced(oldState, worldIn, pos, newState, isMoving);
	}
	
	@Override
	public ActionResultType onBlockActivated(final BlockState state, final World worldIn, final BlockPos pos, final PlayerEntity player, final Hand handIn, final BlockRayTraceResult hit) {
		if (!worldIn.isRemote) {
			final TileEntity tileEntity = worldIn.getTileEntity(pos);
			if (tileEntity instanceof EnchanterTileEntity)
				NetworkHooks.openGui((ServerPlayerEntity) player, (EnchanterTileEntity) tileEntity, pos);
		}
		return ActionResultType.SUCCESS;
	}
}
