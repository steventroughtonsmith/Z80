package com.highcaffeinecontent.cpu.client;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import com.highcaffeinecontent.cpu.CPUPunchCardReaderBlock;
import com.highcaffeinecontent.cpu.CPUPunchCardReaderTileEntity;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
//import com.highcaffeinecontent.PoweredEntity;

public class CPUPunchCardReaderWorldRenderer implements ISimpleBlockRenderingHandler
{
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderblocks) 
	{
		// 0 = bottom
		// 1 = top
		// 2 = right
		// 3 = left
		// 4 = back
		// 5 = front

		int metadata = renderblocks.blockAccess.getBlockMetadata(x, y, z);

		renderblocks.setRenderBounds(0, 0, 0,  1.0f, 1.0f, 1.0f);
		renderblocks.renderStandardBlock(block, x, y, z);

		boolean shouldRenderPunchCard = false;
		
		TileEntity te = world.getTileEntity(x, y, z);
		
		if (te instanceof CPUPunchCardReaderTileEntity)
		{
			if (((CPUPunchCardReaderTileEntity) te).getStackInSlot(0) != null && ((CPUPunchCardReaderTileEntity) te).getStackInSlot(0).stackSize > 0)
			{
				shouldRenderPunchCard = true;
			}
		}

		if (shouldRenderPunchCard)
		{
			if (metadata == 5)
				renderblocks.setRenderBounds(9.0f/16.0f, 12.5f/16.0f, 4.0f/16.0f, 1.0f+1.0f/16.0f, 13.5f/16.0f, 12.0f/16.0f);
			else if (metadata == 4)
				renderblocks.setRenderBounds(-1.0f/16.0f, 12.5f/16.0f, 4.0f/16.0f, 9.0f/16.0f, 13.5f/16.0f, 12.0f/16.0f);
			else if (metadata == 3)
				renderblocks.setRenderBounds(4.0f/16.0f, 12.5f/16.0f, 9.0f/16.0f, 12.0f/16.0f, 13.5f/16.0f, 1.0f+1.0f/16.0f);
			else if (metadata == 2)
				renderblocks.setRenderBounds(4.0f/16.0f, 12.5f/16.0f, -1.0f/16.0f, 12.0f/16.0f, 13.5f/16.0f, 9.0f/16.0f);

			renderblocks.setOverrideBlockTexture(((CPUPunchCardReaderBlock)block).blockCardIcon);
			renderblocks.renderStandardBlock(block, x, y, z);
			renderblocks.clearOverrideBlockTexture();
		}
		return true;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderblocks)
	{
		metadata = 5;

		renderblocks.setRenderBounds(0.0f, 0, 0, 7.0f/8.0f, 1.0f, 1.0f);
		renderStandardInvBlock(renderblocks, block, metadata);
	}

	public static void renderStandardInvBlock (RenderBlocks renderblocks, Block block, int meta)
	{
		Tessellator tessellator = Tessellator.instance;
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1F, 0.0F);
		renderblocks.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, meta));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderblocks.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(1, meta));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1F);
		renderblocks.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(2, meta));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderblocks.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(3, meta));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(-1F, 0.0F, 0.0F);
		renderblocks.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(4, meta));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderblocks.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(5, meta));
		tessellator.draw();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}


	@Override
	public int getRenderId() {
		return CPUClientProxy.cpuPunchCardReaderModel;
	}

	@Override
	public boolean shouldRender3DInInventory (int modelID)
	{
		return true;
	}

	private void renderIcon(IIcon icon)
	{
		Tessellator tessellator = Tessellator.instance;
		float f3 = icon.getMinU();
		float f4 = icon.getMaxU();
		float f5 = icon.getMinV();
		float f6 = icon.getMaxV();
		float f7 = 1.0F;
		float f8 = 0.5F;
		float f9 = 0.25F;


		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		tessellator.addVertexWithUV(0.0F - f8, 0.0F - f9, 0.0D, f3, f6);
		tessellator.addVertexWithUV(  f7 - f8, 0.0F - f9, 0.0D, f4, f6);
		tessellator.addVertexWithUV(  f7 - f8, 1.0F - f9, 0.0D, f4, f5);
		tessellator.addVertexWithUV(0.0F - f8, 1.0F - f9, 0.0D, f3, f5);
		tessellator.addVertexWithUV(0.0F - f8, 1.0F - f9, 0.0D, f3, f5);
		tessellator.addVertexWithUV(  f7 - f8, 1.0F - f9, 0.0D, f4, f5);
		tessellator.addVertexWithUV(  f7 - f8, 0.0F - f9, 0.0D, f4, f6);
		tessellator.addVertexWithUV(0.0F - f8, 0.0F - f9, 0.0D, f3, f6);
		tessellator.draw();

	}

}
