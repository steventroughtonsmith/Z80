package com.highcaffeinecontent.cpu.creative_tabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.highcaffeinecontent.cpu.CPUMod;

public class CPUItemTab extends CreativeTabs {
	public CPUItemTab()
	{
		super("CPUItemTab");
	}
	
	@Override
	public ItemStack getIconItemStack()
	{
		return new ItemStack(CPUMod.punchCard);
	}
	
	@Override
	public String getTabLabel()
	{
		return "Z80 Items";
	}
	
	@Override
	public String getTranslatedTabLabel()
	{
		return getTabLabel();
	}

	@Override
	public Item getTabIconItem() {
		return CPUMod.punchCard;
	}

}
