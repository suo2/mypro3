package com.huawei.solarsafe.utils.pnlogger;

/**
 * Helper class that provides utility methods.
 * 
 * @author Dieter Wimberger
 * @author John Charlton
 * 
 * @version @version@ (@date@)
 */
public final class ModbusUtil {

	/**
	 * Converts an int value to a byte[4] array.
	 * 
	 * @param v
	 *            the value to be converted.
	 * @return a byte[4] containing the value.
	 */
	public static final byte[] intToRegisters(int v) {
		byte[] registers = new byte[4];
		registers[0] = (byte) (0xff & (v >> 24));
		registers[1] = (byte) (0xff & (v >> 16));
		registers[2] = (byte) (0xff & (v >> 8));
		registers[3] = (byte) (0xff & v);
		return registers;
	}
}
