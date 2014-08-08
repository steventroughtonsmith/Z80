package com.highcaffeinecontent.cpu.creative_tabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.highcaffeinecontent.cpu.CPUMod;

public class CPUBlockTab extends CreativeTabs {
	public CPUBlockTab()
	{
		super("CPUBlockTab");
	}
	
	@Override
	public ItemStack getIconItemStack()
	{
		return new ItemStack(CPUMod.cpuBlock);
	}
	
	@Override
	public String getTabLabel()
	{
		return "Z80";
	}
	
	@Override
	public String getTranslatedTabLabel()
	{
		return getTabLabel();
	}

	@Override
	public Item getTabIconItem() {
		return Item.getItemFromBlock(CPUMod.cpuBlock);
	}
}
