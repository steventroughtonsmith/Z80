package com.highcaffeinecontent.cpu;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import com.highcaffeinecontent.cpu.creative_tabs.CPUBlockTab;
import com.highcaffeinecontent.cpu.creative_tabs.CPUItemTab;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid="CPUMod", name="Z80", version="1.0.0")
public class CPUMod
{
	@Instance("CPUMod")
	public static CPUMod instance;

	public static CreativeTabs tabBlocks = new CPUBlockTab();
	public static CreativeTabs tabItems = new CPUItemTab();

	public final static Block cpuBlock = new CPUBlock(Material.iron);
	public final static Block terminalBlock = new CPUTerminalBlock(Material.iron);
	public final static Block punchCardReaderBlock = new CPUPunchCardReaderBlock(Material.iron);

	public final static Item punchCard = new CPUPunchCard();
	
	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide="com.highcaffeinecontent.cpu.client.CPUClientProxy", serverSide="com.highcaffeinecontent.cpu.CPUCommonProxy")
	public static CPUCommonProxy proxy;

	//@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		// Stub Method
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.registerRenderers();

		GameRegistry.registerBlock(cpuBlock, "cpuBlock");
		GameRegistry.registerBlock(terminalBlock, "terminalBlock");
		GameRegistry.registerBlock(punchCardReaderBlock, "punchCardReaderBlock");

		GameRegistry.registerItem(punchCard, "punchCard");
		
		GameRegistry.registerTileEntity(CPUPunchCardReaderTileEntity.class,"punchCardReaderTileEntity");


		NetworkRegistry.INSTANCE.registerGuiHandler(this, new CPUPunchCardReaderGUIHandler());
	}

	//@PostInit
	public void postInit(FMLPostInitializationEvent event) {
		// Stub Method
	}
}