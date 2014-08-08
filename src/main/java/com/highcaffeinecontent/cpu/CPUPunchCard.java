package com.highcaffeinecontent.cpu;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.Item;

public class CPUPunchCard extends Item {

	public CPUPunchCard() {
		setCreativeTab(CPUMod.tabItems);
		setUnlocalizedName("punchCard");
		this.setTextureName("z80:punchCard");
	}
	
	/**
     * Returns True is the item is renderer in full 3D when hold.
     */
    @SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return true;
    }
}
