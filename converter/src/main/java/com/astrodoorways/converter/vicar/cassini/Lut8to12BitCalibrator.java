package com.astrodoorways.converter.vicar.cassini;

import java.io.IOException;

import javax.imageio.metadata.IIOMetadata;

import org.w3c.dom.Node;

public class Lut8to12BitCalibrator extends AbstractBaseCalibrator {

	//refit the 256 value, 12-bit table to a psedu-logarithmic scale for 16-bit
	private static final double[] LUT_8_TO_12 = new double[] {
		1, 3, 5, 7, 9, 11, 14, 16, 19, 22, 25, 28, 31, 35, 38, 42, 46, 49, 53, 57, 61, 65, 70, 74, 79, 83, 88, 93, 98, 103,
		108, 113, 119, 125, 130, 136, 142, 148, 154, 160, 166, 173, 179, 186, 193, 199, 206, 213, 221, 228, 236,
		243, 251, 259, 267, 275, 283, 291, 299, 307, 316, 324, 333, 342, 351, 360, 369, 379, 388, 398, 407, 417,
		427, 437, 447, 457, 467, 478, 488, 499, 509, 520, 531, 542, 554, 565, 576, 588, 600, 611, 623, 635, 647,
		659, 672, 684, 697, 709, 722, 735, 747, 761, 774, 787, 801, 814, 828, 842, 856, 870, 884, 898, 912, 926,
		941, 955, 970, 985, 1000, 1015, 1030, 1045, 1061, 1076, 1092, 1108, 1124, 1140, 1156, 1172, 1188, 1204,
		1221, 1237, 1254, 1271, 1288, 1305, 1322, 1339, 1357, 1374, 1392, 1410, 1428, 1446, 1464, 1482, 1500, 1518,
		1537, 1555, 1574, 1593, 1612, 1631, 1650, 1669, 1689, 1708, 1728, 1748, 1768, 1788, 1808, 1828, 1848, 1868,
		1889, 1909, 1930, 1951, 1971, 1993, 2014, 2035, 2057, 2078, 2100, 2121, 2143, 2165, 2187, 2209, 2232, 2254,
		2276, 2299, 2322, 2344, 2367, 2390, 2413, 2437, 2460, 2484, 2507, 2531, 2555, 2579, 2603, 2627, 2651, 2676,
		2700, 2725, 2749, 2774, 2799, 2824, 2849, 2874, 2900, 2925, 2951, 2977, 3003, 3029, 3055, 3081, 3107, 3133,
		3160, 3186, 3213, 3239, 3266, 3293, 3321, 3348, 3375, 3403, 3430, 3458, 3486, 3514, 3542, 3570, 3598, 3627,
		3655, 3684, 3712, 3741, 3770, 3799, 3828, 3857, 3887, 3916, 3946, 3976, 4005, 4035, 4065, 4095
	};

	@Override
	public boolean calibrate(double[] imageArray, IIOMetadata metadata) throws IOException {
		Node node = metadata.getAsTree(METADATAFORMAT);
		String nodeString = node.getLastChild().getLastChild().getAttributes().item(2).getNodeValue();

		if (!extractValue("DATA_CONVERSION_TYPE", nodeString).equals("TABLE"))
			return false;

		lut8to12BitConvert(imageArray);

		return true;
	}

	public void lut8to12BitConvert(double[] imageArray) {
		for (int i = 0; i < imageArray.length; i++) {
			double pixel = imageArray[i];
			double lutPixel = LUT_8_TO_12[(int) pixel];
			imageArray[i] = lutPixel;
		}
	}

}
