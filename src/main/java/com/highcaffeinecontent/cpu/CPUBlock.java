package com.highcaffeinecontent.cpu;

import com.highcaffeinecontent.cpu.client.CPUClientProxy;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class CPUBlock extends Block {

	@SideOnly(Side.CLIENT)
	private IIcon blockSideIcon;
	private IIcon blockFrontIcon;
	private IIcon blockBottomIcon;

	public CPUBlock(Material material) {
		super(material);
		setHardness(1.0F);
		setStepSound(Block.soundTypeMetal);
		setBlockName("cpuBlock");
		setCreativeTab(CPUMod.tabBlocks);
		setBlockTextureName("z80:z80");
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

		if (side == 0)
			return this.blockBottomIcon;
		else if (side == 2 || side == 3)
			return this.blockFrontIcon;
		else if (side == 4 || side == 5)
			return this.blockSideIcon;

		return this.blockIcon;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		this.blockIcon = iconRegister.registerIcon(this.getTextureName());
		this.blockSideIcon = iconRegister.registerIcon(this.getTextureName() + "_side");
		this.blockFrontIcon = iconRegister.registerIcon(this.getTextureName() + "_front");
		this.blockBottomIcon = iconRegister.registerIcon(this.getTextureName() + "_bottom");
	}

	@Override
	public int getRenderType() {
		return CPUClientProxy.cpuBoardModel;
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

	@Override
	public boolean shouldSideBeRendered (IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return true;
	}

	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k) {
		float xMin = 0, xMax = 1, yMin = 0, yMax = 1.0f/8.0f, zMin = 0, zMax = 1;
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
