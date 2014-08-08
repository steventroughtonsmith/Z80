package com.highcaffeinecontent.cpu;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.highcaffeinecontent.cpu.client.CPUClientProxy;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CPUTerminalBlock extends BlockDirectional {


	@SideOnly(Side.CLIENT)
	private IIcon blockFrontIcon;

	public CPUTerminalBlock(Material material) {
		super(material);
		setHardness(1.0F);
		setStepSound(Block.soundTypeMetal);
		setBlockName("cpuTerminalBlock");
		setCreativeTab(CPUMod.tabBlocks);
		setBlockTextureName("z80:terminal");
	}

	/**
	 * Called upon block activation (right click on the block.)
	 */
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
	{
		if (!world.isRemote)
		{
			CPUUI monitor = new CPUUI(entityPlayer, world, x, y, z);

			FMLClientHandler.instance().showGuiScreen(monitor); // open gui
		}
		return true;
	}

	@Override
    public boolean shouldSideBeRendered (IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return true;
    }
	
	/**
	 * Gets the block's texture. Args: side, meta
	 */
	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int meta)
	{
		// 0 = bottom
		// 1 = top
		// 2 = right
		// 3 = left
		// 4 = back
		// 5 = front
		if (side == meta)
			return this.blockFrontIcon;

		return this.blockIcon;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		this.blockIcon = iconRegister.registerIcon(this.getTextureName());
		this.blockFrontIcon = iconRegister.registerIcon(this.getTextureName() + "_front");
	}
	
	@Override
	public int getRenderType() {
		return CPUClientProxy.cpuTerminalModel;
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

	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k) {
		float xMin = 0, xMax = 1, yMin = 0, yMax = 1.0f, zMin = 0, zMax = 1;
		return AxisAlignedBB.getBoundingBox((double) i + xMin, (double) j + yMin, (double) k, (double) i + xMax, (double) j + yMax, (double) k + 1.0);
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
}
