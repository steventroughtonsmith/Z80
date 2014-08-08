package com.highcaffeinecontent.cpu.client;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
//import com.highcaffeinecontent.PoweredEntity;

public class CPUTerminalWorldRenderer implements ISimpleBlockRenderingHandler
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
				
		// bottom
		renderblocks.setRenderBounds(0, 0, 0, 1.0f, 1.0f/8.0f, 1.0f);
		renderblocks.renderStandardBlock(block, x, y, z);

		//top
		renderblocks.setRenderBounds(0, 7.0f/8.0f, 0, 1.0f, 1.0f, 1.0f);
		renderblocks.renderStandardBlock(block, x, y, z);

		if (metadata == 5) // front
		{
			renderblocks.setRenderBounds(0.0f, 1.0f/8.0f, 1.0f/8.0f, 7.0f/8.0f, 7.0f/8.0f, 7.0f/8.0f);
			renderblocks.renderStandardBlock(block, x, y, z);

			// right
			renderblocks.setRenderBounds(0, 1.0f/8.0f, 7.0f/8.0f, 1.0f, 7.0f/8.0f, 1.0f);
			renderblocks.renderStandardBlock(block, x, y, z);

			//left		
			renderblocks.setRenderBounds(0, 1.0f/8.0f, 0.0f, 1.0f, 7.0f/8.0f, 1.0f/8.0f);
			renderblocks.renderStandardBlock(block, x, y, z);
		}
		else if (metadata == 4) // back
		{
			renderblocks.setRenderBounds(1.0f/8.0f, 1.0f/8.0f, 1.0f/8.0f, 1.0f, 7.0f/8.0f, 7.0f/8.0f);
			renderblocks.renderStandardBlock(block, x, y, z);

			// right
			renderblocks.setRenderBounds(0, 1.0f/8.0f, 7.0f/8.0f, 1.0f, 7.0f/8.0f, 1.0f);
			renderblocks.renderStandardBlock(block, x, y, z);

			//left		
			renderblocks.setRenderBounds(0, 1.0f/8.0f, 0.0f, 1.0f, 7.0f/8.0f, 1.0f/8.0f);
			renderblocks.renderStandardBlock(block, x, y, z);
		}
		else if (metadata == 3) // left
		{
			renderblocks.setRenderBounds(1.0f/8.0f, 1.0f/8.0f, 0.0f, 7.0f/8.0f, 7.0f/8.0f, 7.0f/8.0f);
			renderblocks.renderStandardBlock(block, x, y, z);
			
			//left		
			renderblocks.setRenderBounds(0, 1.0f/8.0f, 0.0f, 1.0f/8.0f, 7.0f/8.0f, 1.0f);
			renderblocks.renderStandardBlock(block, x, y, z);

			// right
			renderblocks.setRenderBounds(7.0f/8.0f, 1.0f/8.0f, 0, 1.0f, 7.0f/8.0f, 1.0f);
			renderblocks.renderStandardBlock(block, x, y, z);

		}
		else if (metadata == 2) // right
		{
			renderblocks.setRenderBounds(1.0f/8.0f, 1.0f/8.0f, 1.0f/8.0f, 7.0f/8.0f, 7.0f/8.0f, 1.0f);
			renderblocks.renderStandardBlock(block, x, y, z);

			// left
			renderblocks.setRenderBounds(7.0f/8.0f, 1.0f/8.0f, 0, 1.0f, 7.0f/8.0f, 1.0f);
			renderblocks.renderStandardBlock(block, x, y, z);

			// right		
			renderblocks.setRenderBounds(0, 1.0f/8.0f, 0.0f, 1.0f/8.0f, 7.0f/8.0f, 1.0f);
			renderblocks.renderStandardBlock(block, x, y, z);
		}

		return true;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderblocks)
	{
		metadata = 5;
		
		renderblocks.setRenderBounds(0.0f, 0, 0, 7.0f/8.0f, 1.0f, 1.0f);
		renderStandardInvBlock(renderblocks, block, metadata);

		// bottom
		renderblocks.setRenderBounds(7.0f/8.0f, 0, 0, 1.0f, 1.0f/8.0f, 1.0f);
		renderStandardInvBlock(renderblocks, block, metadata);

		//top
		renderblocks.setRenderBounds(7.0f/8.0f, 7.0f/8.0f, 0, 1.0f, 1.0f, 1.0f);
		renderStandardInvBlock(renderblocks, block, metadata);

		// right
		renderblocks.setRenderBounds(7.0f/8.0f, 1.0f/8.0f, 7.0f/8.0f, 1.0f, 7.0f/8.0f, 1.0f);
		renderStandardInvBlock(renderblocks, block, metadata);

		//left		
		renderblocks.setRenderBounds(7.0f/8.0f, 1.0f/8.0f, 0.0f, 1.0f, 7.0f/8.0f, 1.0f/8.0f);
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
		return CPUClientProxy.cpuTerminalModel;
	}

	@Override
	public boolean shouldRender3DInInventory (int modelID)
	{
		return true;
	}

}
