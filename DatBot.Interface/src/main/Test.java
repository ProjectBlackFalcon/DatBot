package main;

import main.communication.Communication;
import utils.d2i.d2iManager;
import utils.d2p.MapManager;

public class Test {

	public static void main(String[] args) throws Exception {

		d2iManager.init(Main.D2I_PATH);
		MapManager.init(Main.D2P_PATH);

		boolean arg = false;
		if (args.length != 0) {
			if ((args[0].equals("true") || args[0].equals("True"))) {
				arg = true;
			}
		}

		Communication communication = new Communication(arg);
		Thread communication2 = new Thread(communication);
		communication2.start();
		communication.getReturn("0;0;i;cmd;connect;[wublel5,notabot0,Ilancelet,Julith]");
		Thread.sleep(20000);
//		communication.getReturn("0;0;i;cmd;openHdv;[None]");
//		Thread.sleep(5000);
//		communication.getReturn("0;0;i;cmd;getHdvResourceStats;[6736,274, 276, 285, 286, 287, 288, 289, 290, 291, 292, 300, 301, 302, 303, 304, 305, 306, 307, 308, 309, 310, 311, 312, 313, 315, 316, 350, 351, 360, 361, 362, 363, 364, 365, 366, 368, 369, 370, 371, 373, 374, 375, 376, 377, 378, 379, 380, 381, 382, 383, 384, 385, 386, 387, 388, 389, 390, 391, 392, 393, 394, 395, 396, 397, 398, 399, 400, 401, 405, 406, 407, 408, 409, 410, 411, 412, 413, 414, 415, 416, 417, 418, 419, 420, 421, 422, 423, 17175, 17176, 17177, 17178, 17179, 17180, 17181, 17182, 17183, 17184, 17185, 17186, 17187, 17188, 17189, 17190, 17191, 17192, 17193, 17194, 17195, 17196, 17197, 17198, 17199, 17200, 17201, 17202, 17203, 17204, 17205, 17206, 17207, 17216, 17217, 17218, 17219, 17231, 17241, 17248, 17255, 17256, 17416, 17420, 17422, 17453, 17471, 17526, 17529, 17532, 17534, 17535, 17536, 17653, 17661, 17667, 17730, 17738, 17739, 17740, 17766, 17774, 16426, 16427, 16428, 16429, 16430, 16440, 16441, 16442, 16443, 16444, 16445, 16446, 16447, 16448, 16449, 16450, 16452, 16453, 16454, 16455, 16456, 16457, 16458, 16459, 16460, 16461, 16462, 16463, 16464, 16465, 16466, 16467, 16468, 16469, 16470, 16471, 16472, 16473, 16474, 16487, 16488, 16489, 16490, 16491, 16492, 16493, 16494, 16495, 16496, 16497, 16498, 16499, 16511, 16512, 16513, 16515, 16518, 16522, 16523, 16524, 16525, 16526, 16663, 16691, 16693, 16694, 16695, 16696, 16697, 16698, 16699, 16700, 16701, 16703, 16704, 16705, 16706, 16708, 16709, 16710, 16711, 16837, 16838, 16839, 16840, 16909, 16910, 16911, 16912, 16913, 16914, 16915, 16916, 16917, 16918, 16919, 16920, 16921, 16922, 16923, 16924, 16925, 16926, 17021, 17022, 17031, 17037, 17045, 17046, 17047, 17060, 17065, 17075, 17112, 17113, 17114, 17115, 17116, 17117, 17118, 17123, 17124, 17125, 17126, 17127, 17128, 17129, 17130, 17131, 17132, 17133, 17134, 17135, 17136, 17137, 17138, 17139, 17140, 17141, 17142, 17143, 17144, 17145, 17146, 17147, 17148, 17149, 17150, 17151, 17152, 17153, 17154, 17155, 17156, 17157, 17158, 17159, 17160, 17161, 17414, 17421, 17441, 17442, 17443, 17444, 17553, 17563, 17564, 17565, 17567, 17568, 17569, 17570, 17609, 17611, 17612, 17613, 17614, 17615, 17616, 17618, 17619, 17620, 17621, 17622, 17625, 17626, 17641, 17713, 17714, 17715, 17735, 17736, 17737, 17772, 17777, 17780, 17781, 17785, 17850, 17851, 17852, 17853, 17864, 17865, 17866, 17867, 17868, 17869, 17870, 17963, 17964, 17965, 17966, 17967, 17968, 17970, 17971, 17972, 17973, 17974, 17975, 17976, 17977, 17978, 17979, 17980, 17981, 17982, 17983, 17984, 17985, 17987, 17988, 17989, 17990, 17991, 17992, 17993, 17994, 17995, 18066, 18067, 18068, 18156, 18198, 18209, 18347, 18348, 18357, 18358, 18359, 18361, 18362, 18364, 18366, 18367, 18368, 18369, 18370, 18371, 18383, 18385, 18421, 18422, 18441, 18535, 18536, 18537, 18538, 18539, 18540, 18541, 18542, 18543, 18544, 18552, 18562]");
//		communication.getReturn("0;0;i;cmd;buyResource;[289, 34, 10, 3, 1000]");

		// TESTS FOR INPUTS

		// 0;0;i;cmd;connect;[ceciestuntest,ceciestlemdp1,Gladiatonme,Echo]
		// 1;0;i;cmd;connect;[Jemappellehenry2,azerty123henry,Baddosh,Julith]
		// 2;0;i;cmd;connect;[wublel7,wubwublel7,Dihydroquerina,Julith]
		// 5;0;i;cmd;connect;['wublel6','32407c62d2f','Pot-ator','Julith']
		// 0;0;i;cmd;getMonsters;[None]
		// 0;0;i;cmd;attackMonster;[None]
		// 0;0;i;cmd;getStats;[None]
		// 0;0;i;cmd;getMap;[None]
		// 0;0;i;cmd;move;[255]
	}
}
