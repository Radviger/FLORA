package flora.core.block;

import flora.core.CommonProxy;
import flora.core.ConstantsFLORA;
import flora.core.FLORA;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockInfuser extends Block implements ITileEntityProvider{
    public static final String blockName = "FLORAInfuser";
    public static BlockInfuser instance;

    public BlockInfuser() {
		super(Material.PISTON);
	}

	public static void register(){
		instance=new BlockInfuser();
		instance.setUnlocalizedName(blockName);
		instance.setCreativeTab(CommonProxy.tab);
		GameRegistry.registerBlock(instance, instance.getUnlocalizedName());
		GameRegistry.registerTileEntity(TileInfuser.class, blockName);
	}

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(world, pos, state, placer, stack);
        int l = MathHelper.floor_double((double) (placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        /*if (l == 0) {
            world.setBlockMetadataWithNotify(x, y, z, 2, 2);
        }

        if (l == 1) {
            world.setBlockMetadataWithNotify(x, y, z, 5, 2);
        }

        if (l == 2) {
            world.setBlockMetadataWithNotify(x, y, z, 3, 2);
        }

        if (l == 3) {
            world.setBlockMetadataWithNotify(x, y, z, 4, 2);
        }*/
    }


	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileInfuser();
	}


	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		dropItems(world, pos);
		super.breakBlock(world, pos, state);
	}
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		TileEntity tileEntity = world.getTileEntity(pos);
		if (tileEntity == null) {
			return false;
		}
		if(player.inventory.getCurrentItem() !=null && (player.inventory.getCurrentItem().getItem() instanceof ItemBucket)){
			FluidStack fluidStack=TileInfuser.getFluidFromItem(player.inventory.getCurrentItem());
			if(fluidStack==null){
				return false;
			}
			if(((TileInfuser)tileEntity).fillArmorWithFluid(fluidStack, true)){
				player.getHeldItem(hand).stackSize--;
				if(player.getHeldItem(hand).stackSize==0){
					player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
				}
				player.inventory.addItemStackToInventory(new ItemStack(Items.BUCKET));
			}
			return true;
		}
		if(!player.isSneaking()){
			player.openGui(FLORA.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}


	private void dropItems(World world, BlockPos pos){
		Random rand = new Random();

		TileEntity tileEntity = world.getTileEntity(pos);
		if (!(tileEntity instanceof IInventory)) {
			return;
		}
		IInventory inventory = (IInventory) tileEntity;

		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack item = inventory.getStackInSlot(i);

			if (item != null && item.stackSize > 0) {
				float rx = rand.nextFloat() * 0.8F + 0.1F;
				float ry = rand.nextFloat() * 0.8F + 0.1F;
				float rz = rand.nextFloat() * 0.8F + 0.1F;

				EntityItem entityItem = new EntityItem(world,
						pos.getX() + rx, pos.getY() + ry, pos.getZ() + rz,
						new ItemStack(item.getItem(), item.stackSize, item.getItemDamage()));

				if (item.hasTagCompound()) {
					entityItem.getEntityItem().setTagCompound((NBTTagCompound) item.getTagCompound().copy());
				}

				float factor = 0.05F;
				entityItem.motionX = rand.nextGaussian() * factor;
				entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
				entityItem.motionZ = rand.nextGaussian() * factor;
				world.spawnEntityInWorld(entityItem);
				item.stackSize = 0;
			}
		}
	}


}
