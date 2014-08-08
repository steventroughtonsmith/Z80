package com.highcaffeinecontent.cpu;

import java.util.Random;

import com.highcaffeinecontent.cpu.client.CPUClientProxy;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class CPUPunchCardReaderBlock extends BlockContainer {

	@SideOnly(Side.CLIENT)
	private IIcon blockFrontIcon;
	@SideOnly(Side.CLIENT)
	public IIcon blockCardIcon;


	public CPUPunchCardReaderBlock(Material material) {
		super(material);
		setHardness(1.0F);
		setStepSound(Block.soundTypeMetal);
		setBlockName("punchCardReaderBlock");
		setCreativeTab(CPUMod.tabBlocks);
		setBlockTextureName("z80:punchCardReader");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		this.blockIcon = iconRegister.registerIcon(this.getTextureName());
		this.blockFrontIcon = iconRegister.registerIcon(this.getTextureName() + "_front");
		this.blockCardIcon = iconRegister.registerIcon("z80:punchCard");
	}
	
	/**
	 * Gets the block's texture. Args: side, meta
	 */
	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int meta)
	{
		if (side == meta)
			return this.blockFrontIcon;

		return this.blockIcon;
	}
	
	 /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack)
    {
        int l = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (l == 0)
        {
            world.setBlockMetadataWithNotify(x, y, z, 2, 2);
        }

        if (l == 1)
        {
            world.setBlockMetadataWithNotify(x, y, z, 5, 2);
        }

        if (l == 2)
        {
            world.setBlockMetadataWithNotify(x, y, z, 3, 2);
        }

        if (l == 3)
        {
            world.setBlockMetadataWithNotify(x, y, z, 4, 2);
        }
    }
    
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z,
                    EntityPlayer player, int metadata, float what, float these, float are) {
            TileEntity tileEntity = world.getTileEntity(x, y, z);
            if (tileEntity == null || player.isSneaking()) {
                    return false;
            }

            player.openGui(CPUMod.instance, 0, world, x, y, z);
            return true;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int par6) {
            dropItems(world, x, y, z);
            super.breakBlock(world, x, y, z, block, par6);
    }

    private void dropItems(World world, int x, int y, int z){
    	/*
            Random rand = new Random();

            TileEntity tileEntity = world.getTileEntity(x, y, z);
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
                                            x + rx, y + ry, z + rz,
                                            new ItemStack(item.itemID, item.stackSize, item.getItemDamage()));

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
            */
    }
    
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
        return new CPUPunchCardReaderTileEntity();
	}
	
	@Override
	public int getRenderType() {
		return CPUClientProxy.cpuPunchCardReaderModel;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	public boolean isACube() {
		return false;
	}
}
