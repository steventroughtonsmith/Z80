package com.highcaffeinecontent.cpu.client;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
//import com.highcaffeinecontent.PoweredEntity;

public class CPUBoardWorldRenderer implements ISimpleBlockRenderingHandler
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
		block.setBlockBounds(0, 0, 0.0f, 1.0f, 1.0f/8.0f, 1.0f);

		int metadata = renderblocks.blockAccess.getBlockMetadata(x, y, z);

		if (metadata == 2 || metadata == 3)
		{
			if (metadata == 3)
			{
				renderblocks.uvRotateTop = 3;
				renderblocks.uvRotateBottom = 3;

			}	
		}
		else if (metadata == 5)
		{
			renderblocks.uvRotateTop = 1;
			renderblocks.uvRotateBottom = 1;
		}
		else if (metadata == 4)
		{
			renderblocks.uvRotateTop = 2;
			renderblocks.uvRotateBottom = 2;
		}

		if (metadata == 2 || metadata == 3)
		{
			renderblocks.setRenderBoundsFromBlock(block);
			renderblocks.renderStandardBlock(block, x, y, z);

			renderblocks.setRenderBounds(3.0f/8.0f, 0, 4.0f/16.0f, 5.0f/8.0f, 2.0f/8.0f, 12.0f/16.0f);
			renderblocks.renderStandardBlock(block, x, y, z);

			for (int i = 0; i < 4; i++)
			{
				float zf = 4.5f+(i*2);
				float xf = 5;

				renderblocks.setRenderBounds(xf/16.0f, 2.0f/16.0f, zf/16.0f, (xf+6)/16.0f, 3.0f/16.0f, (zf+1)/16.0f);
				renderblocks.renderStandardBlock(Blocks.iron_block, x, y, z);
			}
		}
		if (metadata == 4 || metadata == 5)
		{
			renderblocks.setRenderBoundsFromBlock(block);
			renderblocks.renderStandardBlock(block, x, y, z);

			renderblocks.setRenderBounds(4.0f/16.0f, 0, 3.0f/8.0f, 12.0f/16.0f, 2.0f/8.0f, 5.0f/8.0f);
			renderblocks.renderStandardBlock(block, x, y, z);

			for (int i = 0; i < 4; i++)
			{
				float xf = 4.5f+(i*2);
				float zf = 5;

				renderblocks.setRenderBounds(xf/16.0f, 2.0f/16.0f, zf/16.0f, (xf+1)/16.0f, 3.0f/16.0f, (zf+6)/16.0f);
				renderblocks.renderStandardBlock(Blocks.iron_block, x, y, z);
			}
		}

		renderblocks.uvRotateTop = 0;
		renderblocks.uvRotateBottom = 0;
		
		return true;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderblocks)
	{
		metadata = 5;
		
		block.setBlockBounds(0, 0, 0.0f, 1.0f, 1.0f/8.0f, 1.0f);

		renderblocks.setRenderBoundsFromBlock(block);
		renderStandardInvBlock(renderblocks, block, metadata);

		renderblocks.setRenderBounds(3.0f/8.0f, 0, 2.0f/8.0f, 5.0f/8.0f, 2.0f/8.0f, 6.0f/8.0f);
		renderStandardInvBlock(renderblocks, block, metadata);

		for (int i = 0; i < 4; i++)
		{
			float zf = 5+(i*2);
			float xf = 5;

			renderblocks.setRenderBounds(xf/16.0f, 2.0f/16.0f, zf/16.0f, (xf+1)/16.0f, 3.0f/16.0f, (zf+1)/16.0f);
			renderStandardInvBlock(renderblocks, Blocks.iron_block, metadata);
		}
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
		return CPUClientProxy.cpuBoardModel;
	}

	@Override
	public boolean shouldRender3DInInventory (int modelID)
	{
		return true;
	}
}
