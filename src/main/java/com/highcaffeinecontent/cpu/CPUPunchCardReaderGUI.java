package com.highcaffeinecontent.cpu;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class CPUPunchCardReaderGUI extends GuiContainer {

	public CPUPunchCardReaderGUI(InventoryPlayer inventoryPlayer,
			CPUPunchCardReaderTileEntity tileEntity) {
		//the container is instanciated and passed to the superclass for handling
		super(new CPUPunchCardReaderContainer(inventoryPlayer, tileEntity));
	}

	@Override
	public void initGui() {
		super.initGui();
		//make buttons
				
		//id, x, y, width, height, text
		//buttonList.add(new GuiButton(1, 10, 52, 20, 20, "+"));
		//buttonList.add(new GuiButton(2, 40, 72, 20, 20, "-"));
	}

	protected void actionPerformed(GuiButton guibutton) {
		
		int i = 0;
		
		//id is the id you give your button
		switch(guibutton.id) {
		case 1:
			i += 1;
			break;
		case 2:
			i -= 1;
		}
		//Packet code here
		//PacketDispatcher.sendPacketToServer(packet); //send packet
	}
	@Override
	protected void drawGuiContainerForegroundLayer(int param1, int param2) {
		//draw text and stuff here
		//the parameters for drawString are: string, x, y, color
		fontRendererObj.drawString("Punch Card Reader", 8, 6, 4210752);
		//draws "Inventory" or your regional equivalent
		fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2,
			int par3) {
		this.mc.renderEngine.bindTexture(new ResourceLocation("z80:textures/gui/punchCardReader.png"));

		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
	}
}
