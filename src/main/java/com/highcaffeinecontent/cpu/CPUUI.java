package com.highcaffeinecontent.cpu;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.lwjgl.input.Keyboard;

import com.highcaffeinecontent.Memory;

import sun.security.ssl.Debug;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.sleepymouse.microprocessor.IBaseDevice;
import net.sleepymouse.microprocessor.IMemory;
import net.sleepymouse.microprocessor.ProcessorException;
import net.sleepymouse.microprocessor.Z80.Z80Core;
import net.sleepymouse.microprocessor.Z80.Z80Core.RegisterNames;

public class CPUUI extends GuiScreen {

	int xSize, ySize;

	Memory cpuRAM = new Memory();
	IBaseDevice cpuIO;
	Z80Core cpuCore = new Z80Core(cpuRAM, cpuRAM);

	public void loadHexStringIntoRAM(String s) {
		int len = s.length();
		for (int i = 0; i < len; i += 2) {
			cpuRAM.writeByte((int)i/2, (int) ((Character.digit(s.charAt(i), 16) << 4)
					+ Character.digit(s.charAt(i+1), 16)));
		}
	}

	public static byte[] getBytesFromFile(File file) throws IOException {        
		// Get the size of the file
		long length = file.length();

		// You cannot create an array using a long type.
		// It needs to be an int type.
		// Before converting to an int type, check
		// to ensure that file is not larger than Integer.MAX_VALUE.
		if (length > Integer.MAX_VALUE) {
			// File is too large
			throw new IOException("File is too large!");
		}

		// Create the byte array to hold the data
		byte[] bytes = new byte[(int)length];

		// Read in the bytes
		int offset = 0;
		int numRead = 0;

		InputStream is = new FileInputStream(file);
		try {
			while (offset < bytes.length
					&& (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
				offset += numRead;
			}
		} finally {
			is.close();
		}

		// Ensure all the bytes have been read in
		if (offset < bytes.length) {
			throw new IOException("Could not completely read file "+file.getName());
		}
		return bytes;
	}

	public void loadFileIntoRAM(String s, int offset) 
	{
		try {
			byte[] filebytes = getBytesFromFile(new File(s));

			for (int i = 0; i < filebytes.length; i++)
			{
				cpuRAM.writeByte(offset+i, filebytes[i]);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadFileIntoRAM(InputStream s, int offset) 
	{
		try {
			byte[] filebytes = IOUtils.toByteArray(s);

			for (int i = 0; i < filebytes.length; i++)
			{
				cpuRAM.writeByte(offset+i, filebytes[i]);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public CPUUI(EntityPlayer player, World par2World, int par3, int par4, int par5)
	{
		super();
		this.xSize = 248;
		this.ySize = 177;

		cpuCore.reset();

		/*
		loadFileIntoRAM("/Users/steven/Dropbox/Projects/LD Challenges/Z80/Z80/CPM/MONITOR.OBJ", 0);
		loadFileIntoRAM("/Users/steven/Dropbox/Projects/LD Challenges/Z80/Z80/CPM/CPM22.OBJ", 0xD000);
		loadFileIntoRAM("/Users/steven/Dropbox/Projects/LD Challenges/Z80/Z80/CPM/CBIOS64.OBJ", 0xD000+0x1600);
		loadFileIntoRAM("/Users/steven/Dropbox/Projects/LD Challenges/Z80/Z80/CPM/BASIC.OBJ", 0x2000);
		 */

		InputStream input = getClass().getResourceAsStream("/assets/z80/INTMINI.OBJ");
		InputStream input2 = getClass().getResourceAsStream("/assets/z80/BASICMINI.OBJ");

		loadFileIntoRAM(input, 0x0);
		loadFileIntoRAM(input2, 0x100);
	}

	public void handleKeyboardInput()
	{
		super.handleKeyboardInput();

		if (Keyboard.getEventKeyState())
		{
			int key = Keyboard.getEventCharacter();

			if (key != Keyboard.KEY_LSHIFT && key != Keyboard.KEY_RSHIFT)
				cpuRAM.key = key;

			cpuCore.setProgramCounter(0x38); // RST38H, keyboard interrupt
		}
	}

	public void updateScreen()
	{
		if (cpuCore.getHalt())
			return;

		try {
			for (int i = 0; i < 200; i++)
			{
				cpuCore.executeOneInstruction();
			}
		} catch (ProcessorException e) {
			e.printStackTrace();
		}
	}

	public void drawScreen(int i, int j, float f) 
	{
		// 6x8

		drawDefaultBackground();
		drawBackgroundLayer(f, i, j);

		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;

		drawString(fontRendererObj, "Z80", x+4, y+4, 0xffffff);

		int startingY = y+6+8+1;

		String textStr[] = cpuRAM.buffer.split("\\r?\\n");

		int startingIndex = 0;

		if (textStr.length > 16)
			startingIndex = textStr.length-16;

		int n = 0;
		for ( int m = startingIndex; m < textStr.length; m++)
		{
			String subStr = textStr[m];
			subStr.replace("\r", "");
			subStr.replace("\n", "");
			drawString(fontRendererObj, subStr, x+6, startingY+10*n, 0xffffff);
			n++;
		}
	}

	protected void drawBackgroundLayer(float f, int i, int j) {
		this.mc.renderEngine.bindTexture(new ResourceLocation("z80:textures/gui/display.png"));

		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
	}
}
