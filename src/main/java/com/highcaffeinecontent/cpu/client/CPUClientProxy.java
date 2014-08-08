package com.highcaffeinecontent.cpu.client;

import com.highcaffeinecontent.cpu.CPUCommonProxy;

import cpw.mods.fml.client.registry.RenderingRegistry;


public class CPUClientProxy extends CPUCommonProxy {

	public static int cpuBoardModel = -1;
	public static int cpuTerminalModel = -1;	
	public static int cpuPunchCardReaderModel = -1;	

	public final static CPUBoardWorldRenderer cpuBoardWorldRenderer = new CPUBoardWorldRenderer();
	public final static CPUTerminalWorldRenderer cpuTerminalWorldRenderer = new CPUTerminalWorldRenderer();
	public final static CPUPunchCardReaderWorldRenderer cpuPunchCardWorldRenderer = new CPUPunchCardReaderWorldRenderer();

	@Override
	public void registerRenderers() {

		//MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(HCCCraft.pipeBlock), pipeItemRenderer);

		cpuBoardModel = RenderingRegistry.getNextAvailableRenderId();
		cpuTerminalModel = RenderingRegistry.getNextAvailableRenderId();
		cpuPunchCardReaderModel = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(cpuBoardWorldRenderer);
		RenderingRegistry.registerBlockHandler(cpuTerminalWorldRenderer);
		RenderingRegistry.registerBlockHandler(cpuPunchCardWorldRenderer);
	}

}