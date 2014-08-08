package com.highcaffeinecontent;

import java.io.UnsupportedEncodingException;

import scala.Int;
import sun.security.ssl.Debug;
import net.sleepymouse.microprocessor.IBaseDevice;
import net.sleepymouse.microprocessor.IMemory;

public class Memory implements IMemory, IBaseDevice {

	public String buffer = "";
	
	static int CONIN = 0x80;//3;
	static int CONOUT = 0x81;//1;
	
	static int CONIN2 = 2;
	static int CONOUT2 = 0;
	
	public int key = 0;

	int[] io = new int[0xff];
	int[] ram = new int[128*1024];

	int[]   ram2  = new int[] {
			0x21, 0x0C, 0x00, // ld hl, 0008
			0x06, 0x0F, // ld b,0f
			0x7e, // ld a,(hl)
			0x23, // inc hl
			0xD3, 0x00, // out (00), a
			0x10, 0xFA, // djnz
			0x76, // halt
			0x48, 0x65, 0x6C, 0x6C, 0x6F, // Message ASCII
			0x20, 0x77, 0x6F, 0x72, 0x6C, //
			0x64, 0x21, 0x20, 0x0D, 0x0A };

	public Memory() {
	}

	@Override
	public int readByte(int address) {
		return ram[address];
	}

	@Override
	public int readWord(int address) {
		return readByte(address)+(readByte(address + 1)*256);
	}

	@Override
	public void writeByte(int address, int data) {
		ram[address] = data&0xff;
	}

	@Override
	public void writeWord(int address, int data) {
		writeByte(address, (data&0x00ff));
		writeByte(address+1, (data&0xff00)>>8);
	}

	@Override
	public int IORead(int address) {

//		Debug.println("IORead", "Address = 0x"+Integer.toHexString(address).toUpperCase());

		if (address == CONIN) // con in
		{
			return 1<<2|1<<1|1;
		}


		if (address == CONIN2)
		{
			return (1<<2);
		}
		
		if (address == CONOUT) // con in
		{
			return key;
		}
		

		return io[address];
	}

	@Override
	public void IOWrite(int address, int data) {

		
//		Debug.println("IOWrite Address = 0x"+Integer.toHexString(address).toUpperCase(), "data = 0x"+Integer.toHexString(data).toUpperCase());

		if (address == 9999)
		{
			if (data == '\n' || data == '\r')
				return;

			byte[] _bytes = new byte[2];
			_bytes[0] = (byte) data;
			_bytes[1] = 0;
			try {
				buffer = buffer + new String(_bytes,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (address == CONOUT) // con out
		{
			if (data != 0xc && data != 0xD6 && data != 0x96 && data < 130)
			{
				if (data == '\n')
				{
					buffer = buffer + "\n";
					return;
				}
				
				if (data == '\r')
					return;
				
				byte[] _bytes = new byte[2];
				_bytes[0] = (byte) data;
				_bytes[1] = 0;


				try {
				//	Debug.println("CON '"+new String(_bytes,"UTF-8")+"'","");
					buffer = buffer + new String(_bytes,"UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		io[address] = data;
	}

}
