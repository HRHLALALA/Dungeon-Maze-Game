package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import Game.Coward;
import Game.Enemy;
import Game.Hound;
import Game.Hunter;
import Game.Map;
import Game.Player;
import Game.Route;
import Game.Strategist;

class EnemyTest {
	/**Testing Class for the enemy methods,  
	 * 1. scan methods (if the player is within area then hunt starts)
	 * 2. walkable entries( tests the method for returning a content of CO that can be walked on)
	 * 3. life test initial method testing if enemy comes up as alive, dying to player is apart of player junit
	 * 4. test if strategy is corresponding
	 * 5. test coordinate move 
	 * 6. retreat test
	 * 7. noPathTest				these 2 below gets an object from method call and ensures the object exist for a path to follow
	 * 8. playerFound = false test	
	 */
	@Test//test scan method
	void scanTest() {
		Player player = new Player(0,0);
		Enemy hunter = new Enemy(3,3, new Hunter());
		hunter.scan(player);
		assertTrue(hunter.isPlayerFound());
	}
	
	@Test
	void walkableListTest(){
		//make 5x5 test a walkable path from fixed 
		Map map = new Map();
		map.loadMap("map1.txt");
		Enemy hunter = new Enemy(0,0,new Hunter());
		hunter.walkableSpace(map);
		/*
			#####
			#r#e#
			# # #
			#   #
			#####
		not sure how to display but 
		the output and the empty spaces is correctly shown
		*/
		assert(hunter.walkableSpaceSize() == 6);
	}
	
	@Test
	void pathTest(){
		System.out.println("-----------------------------------------------------------");
		//make 5x5 test a walkable path from fixed 
		Map map = new Map();
		map.loadMap("map1.txt");
		Player player = new Player(1,1);
		Enemy hunter = new Enemy(3,3,new Hunter());
		hunter.walkableSpace(map);
		hunter.scan(player);
		assert(hunter.isPlayerFound() == true);
		Route path = hunter.searchPlayer(map);
		assert(path != null);
	}
	//test a bigger map with a navigation for the enemy to go through
	@Test
	void pathTest2(){
		System.out.println("-----------------------------------------------------------");
		//make 5x5 test a walkable path from fixed 
		Map map = new Map();
		map.loadMap("map2.txt");
		Enemy hunter = new Enemy(14,16,new Hunter());
		hunter.walkableSpace(map);
		hunter.setPlayerFound(true);
		Route path = hunter.searchPlayer(map);
		for(Integer[] co:path.getPathLog()) {
			for(Integer num: co) {
				System.out.print(num + " ");
			}
			System.out.println();
		}
		assert(path != null);
	}
	@Test
	void StrategistTest(){
		System.out.println("-----------------------------------------------------------");
		//make 5x5 test a walkable path from fixed 
		Map map = new Map();
		map.loadMap("map2.txt");
		Player player = new Player(1,1);
		Enemy hunter = new Enemy(14,16,new Strategist());
		hunter.walkableSpace(map);
		hunter.scan(player);
		assert(hunter.isPlayerFound() == false);
		hunter.setPlayerFound(true);
		Route path = hunter.searchPlayer(map);
		assert(path != null);
	}
	@Test
	void houndTest(){
		System.out.println("-----------------------------------------------------------");
		//make 5x5 test a walkable path from fixed 
		Map map = new Map();
		map.loadMap("map1.txt");
		Player player = new Player(1,1);
		Enemy hunter = new Enemy(3,3,new Hound());
		hunter.walkableSpace(map);
		hunter.scan(player);
		assert(hunter.isPlayerFound() == true);
		Route path = hunter.searchPlayer(map);
		assert(path != null);
	}
	@Test
	void CowardTest(){
		System.out.println("-----------------------------------------------------------");
		//make 5x5 test a walkable path from fixed 
		Map map = new Map();
		map.loadMap("map1.txt");
		Player player = new Player(1,1);
		Enemy hunter = new Enemy(3,3,new Coward());
		hunter.walkableSpace(map);
		hunter.scan(player);
		assert(hunter.isPlayerFound() == true);
		Route path = hunter.searchPlayer(map);
		for(Integer[] co:path.getPathLog()) {
			for(Integer num: co) {
				System.out.print(num + " ");
			}
			System.out.println();
		}
		assert(path != null);
	}
	@Test
	void pathTest3(){
		System.out.println("-----------------------------------------------------------");
		//make 5x5 test a walkable path from fixed 
		Map map = new Map();
		map.loadMap("map3.txt");
		Enemy hunter = new Enemy(2,2,new Hunter());
		hunter.walkableSpace(map);
		hunter.setPlayerFound(true);
		Route path = hunter.searchPlayer(map);
		for(Integer[] co:path.getPathLog()) {
			for(Integer num: co) {
				System.out.print(num + " ");
			}
			System.out.println();
		}
		assert(path != null);
	}

	//just incase the strategy pattern isn't registered
	@Test
	void strategyTest() {

		Enemy hunter = new Enemy(1,1, new Hunter());
		Enemy Coward = new Enemy(1,1, new Coward());
		Enemy hound = new Enemy(1,1, new Hound());
		assert(hunter.getStrategy() instanceof Hunter);
		assert(Coward.getStrategy() instanceof Coward);
		assert(hound.getStrategy() instanceof Hound);
	}

	@Test
	void moveTest() {
		Map map = new Map();
		map.loadMap("map1.txt");
		Enemy hunter = new Enemy(1,1,new Hunter());
		hunter.walkableSpace(map);
		hunter.move(map, 0, 1);
		assert(hunter.getX() == 0 && hunter.getY() == 1);
	}
	@Test
	void pathAssertTest(){
		System.out.println("-----------------------------------------------------------");
		//make 5x5 test a walkable path from fixed 
		Map map = new Map();
		map.loadMap("map1.txt");
		Player player = new Player(1,1);
		Enemy hunter = new Enemy(3,3,new Hunter());
		//designer known path, assert the path is walked by enemy
		ArrayList<Integer[]> pathList = new ArrayList<Integer[]>();
		Integer[] num = {3,3};
		pathList.add(num);
		Integer[] num2 = {2,3};
		pathList.add(num2);
		Integer[] num3 = {1,3};
		pathList.add(num3);
		Integer[] num4 = {1,2};
		pathList.add(num4);
		Integer[] num5 = {1,1};
		pathList.add(num5);		
		hunter.scan(player);
		int counter = 1;
		hunter.walkableSpace(map);
		while(hunter.getX()!=player.getX() ||
						hunter.getY() != player.getY()){
			hunter.scan(player);
			hunter.walkableSpace(map);
        	Route r = hunter.searchPlayer(map);
        	Integer[] i= r.getPathLog().get(1);
        	hunter.move(map,i[0], i[1]);
        	assert((pathList.get(counter)[0].equals(hunter.getX()))&&(pathList.get(counter)[1].equals(hunter.getY())));
        	counter++;
		}
	}
	
	@Test
	void RetreatTest() {
		//assume that player is invincible
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		Map map = new Map();
		map.loadMap("map1.txt");
		Player player = new Player(1,1);
		Enemy hunter = new Enemy(2,1,new Hunter());
		hunter.walkableSpace(map);
		hunter.scan(player);
		Route flee = hunter.RetreatMethod(player, map);
		assert(flee!=null);
		for(Integer[] nums : flee.getPathLog()) {
			System.out.println(nums[0] + " " +nums[1]);
		}
	}
	
	@Test
	void noPathTest1() {
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		Map map = new Map();
		map.loadMap("enemyNoPath.txt");
		Enemy hunter = new Enemy(1,8,new Hunter());
		hunter.setPlayerFound(true);
		hunter.walkableSpace(map);
		Route flee = hunter.searchPlayer(map);
		assert(flee!=null);
	}
	@Test
	void noPathTest2() {
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		Map map = new Map();
		map.loadMap("enemyNoPath.txt");
		Enemy hunter = new Enemy(1,8,new Strategist());
		hunter.setPlayerFound(true);
		hunter.walkableSpace(map);
		hunter.printWalkable();
		Route flee = hunter.searchPlayer(map);
		assert(flee!=null);
	}
	@Test
	void noPathTest3() {
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		Map map = new Map();
		map.loadMap("enemyNoPath.txt");
		Enemy hunter = new Enemy(1,8,new Hound());
		hunter.setPlayerFound(true);
		hunter.walkableSpace(map);
		Route flee = hunter.searchPlayer(map);
		assert(flee!=null);
	}
	@Test
	void noPathTest4() {
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		Map map = new Map();
		map.loadMap("enemyNoPath.txt");
		Enemy hunter = new Enemy(1,8,new Coward());
		hunter.setPlayerFound(true);
		hunter.walkableSpace(map);
		Route flee = hunter.searchPlayer(map);
		assert(flee!=null);
	}
	
	@Test
	void playerNotFound() {
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		Map map = new Map();
		map.loadMap("enemyNoPath.txt");
		Enemy hunter = new Enemy(1,8,new Hunter());
		hunter.walkableSpace(map);
		Route flee = hunter.searchPlayer(map);
		assert(flee!=null);
	}
	@Test
	void playerNotFound2() {
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		Map map = new Map();
		map.loadMap("enemyNoPath.txt");
		Enemy hunter = new Enemy(1,8,new Strategist());
		hunter.walkableSpace(map);
		Route flee = hunter.searchPlayer(map);
		assert(flee!=null);
	}
	@Test
	void playerNotFound3() {
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		Map map = new Map();
		map.loadMap("enemyNoPath.txt");
		Enemy hunter = new Enemy(1,8,new Hound());
		hunter.walkableSpace(map);
		Route flee = hunter.searchPlayer(map);
		assert(flee!=null);
	}
	@Test
	void playerNotFound4() {
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		Map map = new Map();
		map.loadMap("enemyNoPath.txt");
		Enemy hunter = new Enemy(1,8,new Coward());
		hunter.walkableSpace(map);
		Route flee = hunter.searchPlayer(map);
		assert(flee!=null);
	}
	
	/*
	 * this test can't really be done as coward situation will just keep looping itself, you can enable this and check that at a certain range 
	 * the coward goes away then comes back close to the player
	 * @Test
	void CowardTest() {
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		//make 5x5 test a walkable path from fixed 
		Map map = new Map();
		map.loadMap("src/map2.txt");
		Player player = new Player(1,1);
		Enemy hunter = new Enemy(4,4,1,new Coward());
		hunter.scan(player);
		assert(hunter.isPlayerFound() == true);
		hunter.walkableSpace(map);
		Route path = hunter.searchPlayer(player,map);
		while((hunter.getPosition().x!=player.getPosition().x) || (hunter.getPosition().y != player.getPosition().y)){
			hunter.scan(player);
			hunter.walkableSpace(map);
        	Route r = hunter.searchPlayer(player,map);
        	Integer[] i= r.getPathLog().get(1);
        	hunter.move(i.x, i.y);
        	System.out.println(hunter.getPosition().x+" "+hunter.getPosition().y);
		}
	}*/
	

}
