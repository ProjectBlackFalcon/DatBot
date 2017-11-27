package ia.fight.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import ia.fight.map.CreateMap;
import ia.fight.map.CreateMap;

class ReplaceTest {

	@Test
	void test() {
		assertArrayEquals(new int[] {0,13}, CreateMap.rotate(new int[] {0,0}));
		assertArrayEquals(new int[] {1,12}, CreateMap.rotate(new int[] {1,0}));
		assertArrayEquals(new int[] {2,11}, CreateMap.rotate(new int[] {2,0}));
		assertArrayEquals(new int[] {3,10}, CreateMap.rotate(new int[] {3,0}));
		assertArrayEquals(new int[] {4,9}, CreateMap.rotate(new int[] {4,0}));
		
		assertArrayEquals(new int[] {0,14}, CreateMap.rotate(new int[] {0,1}));
		assertArrayEquals(new int[] {1,13}, CreateMap.rotate(new int[] {1,1}));
		assertArrayEquals(new int[] {2,12}, CreateMap.rotate(new int[] {2,1}));
		assertArrayEquals(new int[] {3,11}, CreateMap.rotate(new int[] {3,1}));
		assertArrayEquals(new int[] {4,10}, CreateMap.rotate(new int[] {4, 1}));
		
		assertArrayEquals(new int[] {1,14}, CreateMap.rotate(new int[] {0,2}));
		assertArrayEquals(new int[] {2,13}, CreateMap.rotate(new int[] {1,2}));
		assertArrayEquals(new int[] {3,12}, CreateMap.rotate(new int[] {2,2}));
		assertArrayEquals(new int[] {4,11}, CreateMap.rotate(new int[] {3,2}));
		assertArrayEquals(new int[] {5,10}, CreateMap.rotate(new int[] {4,2}));
		
		assertArrayEquals(new int[] {1,15}, CreateMap.rotate(new int[] {0,3}));
		assertArrayEquals(new int[] {2,14}, CreateMap.rotate(new int[] {1,3}));
		assertArrayEquals(new int[] {3,13}, CreateMap.rotate(new int[] {2,3}));
		assertArrayEquals(new int[] {4,12}, CreateMap.rotate(new int[] {3,3}));
		assertArrayEquals(new int[] {5,11}, CreateMap.rotate(new int[] {4,3}));
		
		assertArrayEquals(new int[] {1,15}, CreateMap.rotate(new int[] {0,3}));
		assertArrayEquals(new int[] {2,16}, CreateMap.rotate(new int[] {0,5}));
		assertArrayEquals(new int[] {3,17}, CreateMap.rotate(new int[] {0,7}));
		assertArrayEquals(new int[] {4,18}, CreateMap.rotate(new int[] {0,9}));
		assertArrayEquals(new int[] {5,19}, CreateMap.rotate(new int[] {0,11}));
		assertArrayEquals(new int[] {6,20}, CreateMap.rotate(new int[] {0,13}));
		assertArrayEquals(new int[] {7,21}, CreateMap.rotate(new int[] {0,15}));
		
		assertArrayEquals(new int[] {2,15}, CreateMap.rotate(new int[] {0,4}));
		assertArrayEquals(new int[] {3,16}, CreateMap.rotate(new int[] {0,6}));
		assertArrayEquals(new int[] {4,17}, CreateMap.rotate(new int[] {0,8}));
		assertArrayEquals(new int[] {5,18}, CreateMap.rotate(new int[] {0,10}));
		assertArrayEquals(new int[] {6,19}, CreateMap.rotate(new int[] {0,12}));
		assertArrayEquals(new int[] {7,20}, CreateMap.rotate(new int[] {0,14}));
		assertArrayEquals(new int[] {8,21}, CreateMap.rotate(new int[] {0,16}));
	}

}
