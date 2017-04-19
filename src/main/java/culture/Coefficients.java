// U.S.A. coefficients, the default.
package culture;
import java.util.*;

/** 
 * Default equations. 
 * ABO and Emotion-Identity changed Feb, 2013.
 */
public class Coefficients extends ListResourceBundle {
  static final Object[][] contents = {
  {"Source", "America"},
  {"ABOmale", new String[] {
	  "Z000000000", "-0.26", "-0.10", " 0.14", "-0.19", " 0.06", " 0.11", "-0.11", "-0.37", " 0.02", 
	  "Z100000000", " 0.41", " 0.00", " 0.05", " 0.11", " 0.00", " 0.02", " 0.00", " 0.00", " 0.00", 
	  "Z010000000", " 0.00", " 0.56", " 0.00", " 0.00", " 0.16", "-0.06", " 0.00", " 0.00", " 0.00", 
	  "Z001000000", " 0.00", " 0.06", " 0.64", " 0.00", " 0.00", " 0.27", " 0.00", " 0.00", " 0.00", 
	  "Z000100000", " 0.42", "-0.07", "-0.06", " 0.53", "-0.13", " 0.04", " 0.11", " 0.18", " 0.02", 
	  "Z000010000", "-0.02", " 0.44", " 0.00", " 0.00", " 0.70", " 0.00", " 0.00", "-0.11", " 0.00", 
	  "Z000001000", "-0.10", " 0.00", " 0.29", "-0.12", " 0.00", " 0.64", " 0.00", " 0.00", " 0.00", 
	  "Z000000100", " 0.03", " 0.04", " 0.00", " 0.00", " 0.03", " 0.00", " 0.61", "-0.08", " 0.03", 
	  "Z000000010", " 0.06", " 0.00", " 0.00", " 0.05", " 0.01", " 0.00", " 0.00", " 0.66", "-0.05", 
	  "Z000000001", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.03", " 0.07", " 0.66", 
	  "Z100100000", " 0.05", " 0.00", " 0.00", " 0.00", " 0.01", " 0.00", " 0.03", " 0.00", " 0.00", 
	  "Z100000010", " 0.03", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", 
	  "Z010010000", " 0.00", "-0.05", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", 
	  "Z001001000", " 0.00", " 0.00", "-0.06", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", 
	  "Z000100100", " 0.12", " 0.01", " 0.00", " 0.11", " 0.03", " 0.00", " 0.04", " 0.03", " 0.00", 
	  "Z000100010", "-0.05", " 0.00", " 0.00", "-0.05", " 0.00", " 0.00", " 0.00", " 0.03", " 0.00", 
	  "Z000010100", "-0.05", " 0.00", " 0.00", "-0.02", " 0.00", " 0.00", "-0.03", " 0.00", " 0.00", 
	  "Z000010010", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", "-0.05", " 0.00", 
	  "Z100100100", " 0.03", " 0.00", " 0.00", " 0.02", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", 
	  "Z100100010", "-0.02", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00"  
	  }
  },
  {"ABOfemale", new String[] {
	  "Z000000000", "-0.05", "-0.10", " 0.14", "-0.09", " 0.06", " 0.23", " 0.16", "-0.42", "-0.09", 
	  "Z100000000", " 0.41", " 0.00", " 0.05", " 0.11", " 0.00", " 0.02", " 0.00", " 0.00", " 0.00", 
	  "Z010000000", " 0.00", " 0.56", " 0.00", " 0.00", " 0.16", "-0.06", " 0.00", " 0.00", " 0.00", 
	  "Z001000000", " 0.00", " 0.06", " 0.77", " 0.00", " 0.00", " 0.33", " 0.00", " 0.00", " 0.00", 
	  "Z000100000", " 0.42", "-0.14", "-0.06", " 0.58", "-0.17", " 0.04", " 0.11", " 0.12", " 0.02", 
	  "Z000010000", "-0.02", " 0.44", " 0.00", " 0.00", " 0.67", " 0.00", " 0.00", "-0.11", " 0.00", 
	  "Z000001000", "-0.10", " 0.00", " 0.29", "-0.12", " 0.00", " 0.64", " 0.00", " 0.00", " 0.00", 
	  "Z000000100", " 0.03", " 0.04", " 0.00", " 0.00", " 0.03", " 0.00", " 0.61", "-0.15", " 0.03", 
	  "Z000000010", " 0.06", " 0.00", " 0.00", " 0.05", "-0.04", " 0.00", " 0.00", " 0.66", "-0.05", 
	  "Z000000001", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", "-0.05", " 0.07", " 0.83", 
	  "Z100100000", " 0.05", " 0.00", " 0.00", " 0.00", " 0.01", " 0.00", " 0.03", " 0.00", " 0.00", 
	  "Z100000010", " 0.03", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", 
	  "Z010010000", " 0.00", "-0.05", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", 
	  "Z001001000", " 0.00", " 0.00", "-0.06", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", 
	  "Z000100100", " 0.12", " 0.01", " 0.00", " 0.11", " 0.01", " 0.00", " 0.04", " 0.03", " 0.00", 
	  "Z000100010", "-0.05", " 0.00", " 0.00", "-0.05", " 0.00", " 0.00", " 0.00", " 0.03", " 0.00", 
	  "Z000010100", "-0.05", " 0.00", " 0.00", "-0.02", " 0.00", " 0.00", "-0.03", " 0.00", " 0.00", 
	  "Z000010010", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", "-0.05", " 0.00", 
	  "Z100100100", " 0.03", " 0.00", " 0.00", " 0.02", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", 
	  "Z100100010", "-0.02", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00"  
	  }
  },
  {"ABOSmale", new String[] {
  "Z000000000000", "-0.25", "-0.14", " 0.08", "-0.13", " 0.06", " 0.00", "-0.10", "-0.43", "-0.03", "-0.35", "-0.06", " 0.09",
  "Z100000000000", " 0.35", " 0.00", " 0.06", " 0.10", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00",
  "Z010000000000", " 0.00", " 0.46", "-0.05", " 0.00", " 0.13", "-0.06", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00",
  "Z001000000000", " 0.00", " 0.08", " 0.65", " 0.00", " 0.00", " 0.27", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.07",
  "Z000100000000", " 0.52", "-0.08", "-0.08", " 0.56", "-0.12", "-0.06", " 0.17", " 0.19", " 0.03", " 0.11", " 0.00", " 0.00",
  "Z000010000000", "-0.05", " 0.46", " 0.10", "-0.06", " 0.68", " 0.12", " 0.00", "-0.12", " 0.00", " 0.00", " 0.10", " 0.00",
  "Z000001000000", "-0.09", " 0.00", " 0.27", "-0.12", " 0.00", " 0.61", " 0.00", " 0.05", " 0.05", " 0.00", " 0.00", " 0.00",
  "Z000000100000", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.49", "-0.09", " 0.00", " 0.00", " 0.00", " 0.00",
  "Z000000010000", " 0.00", " 0.00", " 0.00", "-0.14", " 0.00", " 0.00", "-0.14", " 0.38", "-0.05", " 0.00", " 0.00", " 0.00",
  "Z000000001000", " 0.12", " 0.00", " 0.00", " 0.00", " 0.00", " 0.04", " 0.00", " 0.08", " 0.48", " 0.00", " 0.00", " 0.00",
  "Z000000000100", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.08", " 0.00", " 0.60", "-0.14", "-0.07",
  "Z000000000010", " 0.00", " 0.00", " 0.00", " 0.00", " 0.09", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.66", " 0.00",
  "Z000000000001", " 0.07", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", "-0.06", " 0.00", " 0.74",
  "Z100100000000", " 0.05", " 0.00", " 0.00", " 0.01", " 0.00", " 0.00", " 0.03", " 0.01", " 0.00", " 0.00", " 0.00", "-0.03",
  "Z100010000000", "-0.04", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.03", " 0.00", " 0.00", " 0.00", " 0.00",
  "Z100000100000", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", "-0.02", " 0.00", " 0.00", " 0.00",
  "Z010100000000", " 0.00", " 0.05", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00",
  "Z010010000000", " 0.00", "-0.07", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00",
  "Z010001000000", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", "-0.15", " 0.00", " 0.00", " 0.00", " 0.00",
  "Z010000100000", " 0.08", " 0.00", " 0.00", " 0.09", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00",
  "Z010000010000", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", "-0.03", " 0.00", " 0.00", " 0.00",
  "Z010000001000", " 0.00", " 0.00", " 0.00", " 0.00", " 0.03", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00",
  "Z001010000000", " 0.00", " 0.00", "-0.06", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00",
  "Z001001000000", " 0.00", "-0.03", "-0.05", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00",
  "Z000100100000", " 0.12", " 0.02", " 0.00", " 0.11", " 0.02", " 0.00", " 0.04", " 0.03", " 0.01", " 0.03", " 0.00", " 0.00",
  "Z000100010000", "-0.06", "-0.02", " 0.00", "-0.05", " 0.00", " 0.00", " 0.00", " 0.02", " 0.00", " 0.00", " 0.00", " 0.00",
  "Z000010100000", "-0.05", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", "-0.02", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00",
  "Z000010010000", "-0.01", " 0.00", " 0.00", " 0.04", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00",
  "Z000010001000", " 0.00", " 0.03", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.06", " 0.00",
  "Z000001010000", " 0.00", " 0.00", " 0.00", " 0.03", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00",
  "Z100100100000", " 0.02", " 0.01", " 0.00", " 0.02", " 0.01", " 0.00", " 0.01", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00",
  "Z100010010000", " 0.03", " 0.00", " 0.00", " 0.02", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00",
  "Z010010010000", " 0.00", " 0.00", " 0.00", "-0.02", " 0.00", " 0.02", " 0.00", "-0.02", " 0.00", " 0.00", " 0.00", " 0.00",
  "Z010010001000", " 0.00", " 0.00", "-0.02", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00"}
  },
  {"ABOSfemale", new String[] {
  "Z000000000000", "-0.02", "-0.14", " 0.08", "-0.03", " 0.06", " 0.11", " 0.15", "-0.43", "-0.12", "-0.35", " 0.09", " 0.09",
  "Z100000000000", " 0.35", "-0.08", " 0.06", " 0.10", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00",
  "Z010000000000", " 0.00", " 0.46", "-0.05", " 0.00", " 0.13", "-0.06", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00",
  "Z001000000000", " 0.00", " 0.08", " 0.77", " 0.00", " 0.00", " 0.33", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.07",
  "Z000100000000", " 0.52", "-0.15", "-0.08", " 0.56", "-0.16", "-0.06", " 0.17", " 0.13", " 0.03", " 0.11", " 0.00", " 0.00",
  "Z000010000000", "-0.05", " 0.46", " 0.10", "-0.06", " 0.68", " 0.12", " 0.00", "-0.12", " 0.00", " 0.00", " 0.10", " 0.00",
  "Z000001000000", "-0.09", " 0.00", " 0.27", "-0.12", " 0.00", " 0.61", " 0.00", " 0.05", " 0.05", " 0.00", " 0.00", " 0.00",
  "Z000000100000", " 0.05", " 0.05", " 0.00", " 0.00", " 0.05", " 0.00", " 0.49", "-0.18", " 0.05", " 0.00", " 0.00", " 0.00",
  "Z000000010000", " 0.00", " 0.00", " 0.00", "-0.14", "-0.04", " 0.00", "-0.14", " 0.38", "-0.05", " 0.00", " 0.00", " 0.00",
  "Z000000001000", " 0.12", " 0.00", " 0.00", " 0.00", " 0.00", " 0.04", " 0.00", " 0.08", " 0.64", " 0.00", " 0.00", " 0.00",
  "Z000000000100", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.08", " 0.00", " 0.60", "-0.14", "-0.07",
  "Z000000000010", " 0.00", " 0.00", " 0.00", " 0.00", " 0.09", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.66", " 0.00",
  "Z000000000001", " 0.07", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", "-0.06", " 0.00", " 0.74",
  "Z100100000000", " 0.05", " 0.00", " 0.00", " 0.01", " 0.00", " 0.00", " 0.03", " 0.01", " 0.00", " 0.00", " 0.00", "-0.03",
  "Z100010000000", "-0.04", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.03", " 0.00", " 0.00", " 0.00", " 0.00",
  "Z100001000000", " 0.00", " 0.00", " 0.00", " 0.00", "-0.04", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00",
  "Z100000100000", " 0.00", " 0.00", " 0.00", " 0.03", " 0.00", " 0.00", " 0.00", " 0.00", "-0.02", " 0.00", " 0.00", " 0.00",
  "Z010100000000", " 0.00", " 0.05", " 0.00", " 0.03", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00",
  "Z010010000000", " 0.00", "-0.07", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00",
  "Z010001000000", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", "-0.15", " 0.00", " 0.00", " 0.00", " 0.00",
  "Z010000100000", " 0.08", " 0.00", " 0.00", " 0.09", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00",
  "Z010000010000", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", "-0.03", " 0.00", " 0.00", " 0.00",
  "Z010000001000", " 0.00", " 0.00", " 0.00", " 0.00", " 0.03", " 0.00", " 0.03", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00",
  "Z001010000000", " 0.00", " 0.00", "-0.06", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00",
  "Z001001000000", " 0.00", "-0.03", "-0.05", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00",
  "Z000100100000", " 0.12", " 0.02", " 0.00", " 0.11", " 0.02", " 0.00", " 0.04", " 0.03", " 0.01", " 0.03", " 0.00", " 0.00",
  "Z000100010000", "-0.06", "-0.02", " 0.00", "-0.05", " 0.00", " 0.00", " 0.00", " 0.02", " 0.00", " 0.00", " 0.00", " 0.00",
  "Z000010100000", "-0.05", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", "-0.02", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00",
  "Z000010010000", "-0.01", "-0.04", " 0.00", " 0.04", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00",
  "Z000010001000", " 0.00", " 0.03", " 0.00", " 0.00", " 0.00", " 0.00", "-0.05", " 0.00", " 0.00", " 0.00", " 0.06", " 0.00",
  "Z000001100000", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.05", " 0.00", " 0.00", " 0.00", " 0.00",
  "Z000001010000", " 0.00", " 0.00", " 0.00", " 0.03", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00",
  "Z100100100000", " 0.02", " 0.01", " 0.00", " 0.02", " 0.01", " 0.00", " 0.01", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00",
  "Z100010010000", " 0.03", " 0.00", " 0.00", " 0.02", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00",
  "Z010010010000", " 0.00", " 0.00", " 0.00", "-0.02", " 0.00", " 0.02", " 0.00", "-0.02", " 0.00", " 0.00", " 0.00", " 0.00",
  "Z010010001000", " 0.00", " 0.00", "-0.02", " 0.00", " 0.00", " 0.00", " 0.00", "-0.04", " 0.00", " 0.00", " 0.00", " 0.00 "}
  },
  {"ABmale", new String[] {
  "Z000000", "-0.31", "-0.57", "-0.19", "-0.45", "-0.53", "-0.26",
  "Z100000", " 0.47", " 0.00", " 0.00", " 0.31", " 0.07", " 0.00",
  "Z010000", " 0.00", " 0.37", "-0.07", " 0.00", " 0.22", "-0.06",
  "Z001000", " 0.00", " 0.00", " 0.57", " 0.00", " 0.00", " 0.43",
  "Z000100", " 0.24", " 0.16", " 0.10", " 0.29", " 0.07", " 0.07",
  "Z000010", " 0.00", " 0.00", "-0.18", " 0.00", " 0.16", "-0.14",
  "Z000001", " 0.00", " 0.21", " 0.37", " 0.00", " 0.13", " 0.45",
  "Z100100", " 0.08", " 0.00", " 0.00", " 0.07", " 0.00", " 0.02",
  "Z100010", "-0.06", " 0.00", " 0.00", "-0.08", " 0.00", " 0.00",
  "Z010100", " 0.00", " 0.00", " 0.02", " 0.00", " 0.00", " 0.00",
  "Z010001", "-0.07", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00",
  "Z001100", "-0.03", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00"}
  },
  {"ABfemale", new String[] {
  "Z000000", "-0.31", "-0.57", "-0.19", "-0.45", "-0.53", "-0.26",
  "Z100000", " 0.47", " 0.00", " 0.00", " 0.31", " 0.07", " 0.00",
  "Z010000", " 0.00", " 0.37", "-0.07", " 0.00", " 0.22", "-0.06",
  "Z001000", " 0.00", " 0.00", " 0.57", " 0.00", " 0.00", " 0.43",
  "Z000100", " 0.24", " 0.16", " 0.10", " 0.29", " 0.07", " 0.07",
  "Z000010", " 0.00", "-0.09", "-0.18", " 0.00", " 0.16", "-0.14",
  "Z000001", " 0.00", " 0.21", " 0.37", " 0.00", " 0.13", " 0.45",
  "Z100100", " 0.08", " 0.05", " 0.04", " 0.14", " 0.04", " 0.02",
  "Z100010", "-0.06", " 0.00", " 0.00", "-0.08", " 0.00", " 0.00",
  "Z010100", " 0.00", " 0.00", " 0.02", " 0.00", " 0.00", " 0.00",
  "Z010001", "-0.07", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00",
  "Z001100", "-0.03", " 0.00", " 0.00", " 0.00", " 0.00", " 0.00"}
  },
  {"TraitMale", new String[] {
  "Z000000",  "-0.32",  "-0.18",  "-0.11",
  "Z100000",   "0.69",  "-0.18",  "-0.04",
  "Z010000",  "-0.36",   "0.65",   "0.07",
  "Z001000",   "0.00",   "0.01",   "0.53",
  "Z000100",   "0.47",  "-0.01",  "-0.02",
  "Z000010",   "0.01",   "0.59",  "-0.02",
  "Z000001",  "-0.07",   "0.05",   "0.64",
  "Z100100",   "0.12",   "0.00",   "0.00"}
  },
  {"TraitFemale", new String[] {
  "Z000000",  "-0.32",  "-0.18",  "-0.11",
  "Z100000",   "0.69",  "-0.18",  "-0.04",
  "Z010000",  "-0.36",   "0.65",   "0.07",
  "Z001000",   "0.00",   "0.01",   "0.53",
  "Z000100",   "0.47",  "-0.01",  "-0.02",
  "Z000010",   "0.01",   "0.59",  "-0.02",
  "Z000001",  "-0.07",   "0.05",   "0.64",
  "Z100100",   "0.12",   "0.00",   "0.00"}
  },
  {"EmotionMale", new String[] {
	  "Z000000", "-0.36", "-0.17", "-0.22",
	  "Z100000", " 0.50", " 0.00", " 0.00",
	  "Z010000", " 0.00", " 0.32", " 0.00",
	  "Z001000", "-0.23", " 0.00", " 0.44",
	  "Z000100", " 0.46", " 0.00", "-0.05",
	  "Z000010", " 0.00", " 0.62", " 0.00",
	  "Z000001", " 0.00", " 0.00", " 0.66",
	  "Z100100", " 0.12", " 0.00", " 0.02",
	  "Z001100", " 0.00", " 0.05", " 0.03"
	  }
  },
  {"EmotionFemale", new String[] {
	  "Z000000", "-0.36", "-0.17", "-0.22",
	  "Z100000", " 0.50", " 0.00", " 0.00",
	  "Z010000", " 0.00", " 0.32", " 0.00",
	  "Z001000", "-0.23", " 0.10", " 0.44",
	  "Z000100", " 0.46", " 0.00", "-0.05",
	  "Z000010", " 0.00", " 0.55", " 0.00",
	  "Z000001", " 0.00", " 0.00", " 0.66",
	  "Z100100", " 0.12", " 0.00", " 0.02",
	  "Z001100", " 0.00", " 0.05", " 0.03"
	  }
  }
};

  public Object[][] getContents() {
    return contents;
  }
}