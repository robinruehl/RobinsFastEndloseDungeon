package application;

import java.util.Random;

public class Fights {
	
	//tuhen sie es sich bitte nicht an es ist cancer
	
	boolean yourTurn = false;
	Random rand = new Random();
	float enemyNewMax = 50; //habe schon einen wert gegeben, da dann etwas im GUI am anfang dargestellt werden kann oder so
	
	Fights(Controller GUI, Game GAME){
		this.GUI = GUI;
		this.GAME = GAME;
	}
	
	Enemy enemy;
	
	Player player;
	
	Controller GUI;
	
	Game GAME;

	public void setGUI(Controller gUI) {
		GUI = gUI;
	}
	
	//Fight check beim Raum betreten
	
	public  void isFight(Raum Raum) {
		if (Raum.isEnemy()) {
			System.out.println("n gegner oh nee!");
			GUI.displayEnem();
			if (Raum.isBoss()) {
				System.out.println("Boss");
				FightBoss();
			}
			else {
				Fight();
			}
		}
		else {
			System.out.println("kein gegner o jaaa!");
			GUI.disableEnem();
		}
	}
	
	//selbsterklärlich
	public void instakill() {
		yourTurn = false;
		GAME.encounterIsWaitingForInput = false;
		System.out.println("instakill");
    	int temp = player.getAttackDamage();
    	enemy.health = -1;
    	GUI.consoleWrite("Du triffst " + enemy.name + " für " + temp + " Schaden und " + enemy.name + " hat noch " + enemy.health + " Leben!");
    	GUI.update(player);
    	checkFight();
	}
	
	//Boss Fight starten!
	private  void FightBoss() {
		player = GUI.getPlayer();
		if (GAME.CurrentRoom.isMegasuperboss()) {
			enemy = new Boss(player, true);
		}
		else {
			enemy = new Boss(player);
		}
		enemyNewMax = enemy.health;
		GUI.update(player);
		GUI.consoleWrite("Ein epischer Boss ist aufgetaucht!");
		encounter();
	}
	
	//normalen Kampf starten bzw. gegner erstellen
	private  void Fight() {
		player = GUI.getPlayer();
		enemy = new Enemy(player);
		enemyNewMax = enemy.health;
		GUI.update(player);
		GUI.consoleWrite("Ein Gegner ist aufgetaucht!");
		encounter();
	}
	
	//Gegner schlagen.
	public void slash() {
		yourTurn = false;
		GAME.encounterIsWaitingForInput = false;
    	System.out.println("slash");
    	int acc = rand.nextInt(100);
    	GUI.consoleWrite("Du würfelst " + acc);
    	if (player.getHitChance() >= acc) {
        	int temp = player.getAttackDamage();
        	enemy.health = enemy.health-temp;
        	GUI.consoleWrite("Du triffst " + enemy.name + " für " + temp + " Schaden und " + enemy.name + " hat noch " + enemy.health + " Leben!");
    	}
    	else {
    		GUI.consoleWrite("Du hast den Gegner verfehlt!");
    	}
    	
    	acc = rand.nextInt(100);
    	if (enemy.accuracy >= acc) {
    		int temp = rand.nextInt(enemy.maxAttackDMG/2)+enemy.maxAttackDMG/2;
    		player.setHealth(player.getHealth()-temp);
    		GUI.consoleWrite("Der Gegner trifft dich für " + temp + " Schaden und du hast noch " + player.getHealth() + " Leben!");
    	}
    	else {
    		GUI.consoleWrite("Der Gegner hat dich verfehlt!");
    	}
    	GUI.update(player);
    	checkFight();
    }
	
	//Gegner abstechen.
	public void stab() {
		yourTurn = false;
		GAME.encounterIsWaitingForInput = false;
    	System.out.println("slash");
    	int trys = 1;
    	int hits = 0;
    	float damagemult = 1;
    	System.out.println(trys);
    	int acc;
    	while (trys > 0 && hits < 20) {
    		acc = rand.nextInt(100);
    		GUI.consoleWrite("Du würfelst " + acc);
    		if (player.getHitChance() >= acc) {
            	int temp = rand.nextInt(player.getAttackDamage())+5;
            	System.out.println(temp);
            	temp *= damagemult;
            	System.out.println(damagemult);
            	enemy.health = (enemy.health-temp);
            	GUI.consoleWrite("Du triffst " + enemy.name + " für " + temp + " Schaden und " + enemy.name + " hat noch " + enemy.health + " Leben!");
            	damagemult = damagemult/4*3;
            	hits++;
        	}
        	else {
        		GUI.consoleWrite("Du hast den Gegner verfehlt!");
        		trys--;
        	}
    	}
    	
    	acc = rand.nextInt(100);
    	if (enemy.accuracy >= acc) {
    		int temp = rand.nextInt(enemy.maxAttackDMG);
    		player.setHealth(player.getHealth()-temp);
    		GUI.consoleWrite("Der Gegner trifft dich für " + temp + " Schaden und du hast noch " + player.getHealth() + " Leben!");
    	}
    	else {
    		GUI.consoleWrite("Der Gegner hat dich verfehlt!");
    	}
    	GUI.update(player);
    	checkFight();
	}
	
	//Angriff Abwehren
    public void block() {
    	System.out.println("block");
    	yourTurn = false;
		GAME.encounterIsWaitingForInput = false;
    	int acc;
    	
    	
    	acc = rand.nextInt(100);
    	if (enemy.accuracy >= acc) {
    		int temp = rand.nextInt(enemy.maxAttackDMG*5/4);
    		acc = rand.nextInt(100);
    		GUI.consoleWrite("Der Gegner greift dich an!");
    		GUI.consoleWrite("Du würfelst " + acc);
    		if (player.getHitChance() > acc) {
    			GUI.consoleWrite("Du kannst den gegnerischen Angriff mit deinem Schild abwenden!");
    			int tempp = enemy.maxAttackDMG/4;
    			enemy.health -= tempp;
    			GUI.consoleWrite("Der Gegner nimmt " + tempp + " Rückstoßschaden!");
    			
    		}
    		else {
    			player.setHealth(player.getHealth()-(temp));
    			GUI.consoleWrite("Der Gegner trifft dich für " + temp + " Schaden und du hast noch " + player.getHealth() + " Leben!");
    		}
    	}
    	else {
    		GUI.consoleWrite("Der Gegner hat dich verfehlt!");
    	}
    	GUI.update(player);
    	checkFight();
    }
    
    //Elixir trinken...
    public void potion() {
    	if (yourTurn && player.getHealth() > 0){
			yourTurn = false;
			GAME.encounterIsWaitingForInput = false;
			//GUI.update(player);
			if(player.getHealthPots() > 0) {
				player.setHealth(player.getHealth()+ player.getHealthPotsHeal()*player.getLuck());
				player.setHealthPots(player.getHealthPots()-1);
				GUI.pottiboii(player.getHealthPots());
				if (player.getHealth() > player.getMaxHealth()) {
					player.setHealth(player.getMaxHealth());
					//GUI.update(player);
				}
				//GUI.update(player);
				GUI.getHealthBar().setProgress(player.getHealth()/player.getMaxHealth());
				GUI.consoleWrite("Du hast einen Lebens Elixir getrunken und deine Lebenspunkte auf " + player.getHealth() + " erhöht!");
				//response = true;
				if (player.getHealth() <= 0) {
					GUI.consoleWrite("Youve taken too much damage and have died.");
				}
				else if (enemy.health <= 0) {
					GUI.consoleWrite("The enemy has died");
					//lootdrop();
					//newEnemy();
				}
				else {
					int acc = rand.nextInt(100);
			    	if (enemy.accuracy >= acc) {
			    		int temp = rand.nextInt(enemy.maxAttackDMG/2);
			    		player.setHealth(player.getHealth()-temp);
			    		GUI.consoleWrite("Der Gegner trifft dich für " + temp + " Schaden und du hast noch " + player.getHealth() + " Leben!");
			    	}
			    	else {
			    		GUI.consoleWrite("Der Gegner hat dich verfehlt!");
			    	}
					encounter();
				}
			}
			else {
				GUI.consoleWrite("No health pots left");
				//response = true;
				if (player.getHealth() <= 0) {
					GUI.consoleWrite("Youve taken too much damage and have died.");
					//response = true;
				}
				else if (enemy.health <= 0) {
					GUI.consoleWrite("The enemy has died");
					//newEnemy();
				}
				else {
					encounter();
				}
			}
		}
		else if (!yourTurn && !GAME.encounterIsWaitingForInput && player.getHealth() > 0){
	    	System.out.println("potion");
	    	if (player.getHealth() == player.getMaxHealth()) {
    			GUI.consoleWrite("Deine Lebenspunkte sind aber schon voll. Das würde keinen Sinn ergeben.");
    			return;
			}
			if(player.getHealthPots() > 0) {
				player.setHealth(player.getHealth()+ player.getHealthPotsHeal()*player.getLuck());
				player.setHealthPots(player.getHealthPots()-1);
				if (player.getHealth() > player.getMaxHealth()) {
					player.setHealth(player.getMaxHealth());
					//GUI.update(player);
				}
				GUI.update(player);
				GUI.consoleWrite("Du hast einen Lebens Elixir getrunken und deine Lebenspunkte auf " + player.getHealth() + " erhöht!");
				GUI.pottiboii(player.getHealthPots());
			}
		}
    }
    
    public void potionfight() {
    	if (yourTurn && player.getHealth() > 0){
    		if (player.getHealth() == player.getMaxHealth()) {
    			GUI.consoleWrite("Deine Lebenspunkte sind aber schon voll. Das würde keinen Sinn ergeben.");
    			return;
			}
			yourTurn = false;
			GAME.encounterIsWaitingForInput = false;
			//GUI.update(player);
			if(player.getHealthPots() > 0) {
				player.setHealth(player.getHealth() + player.getHealthPotsHeal()*player.getLuck());
				player.setHealthPots(player.getHealthPots()-1);
				GUI.pottiboii(player.getHealthPots());
				if (player.getHealth() > player.getMaxHealth()) {
					player.setHealth(player.maxHealth);
					//GUI.update(player);
				}
				//GUI.update(player);
				GUI.update(player);
				GUI.consoleWrite("Du hast einen Lebens Elixir getrunken und deine Lebenspunkte auf " + player.getHealth() + " erhöht!");
				//response = true;
				if (player.getHealth() <= 0) {
					GUI.consoleWrite("Youve taken too much damage and have died.");
				}
				else if (enemy.health <= 0) {
					GUI.consoleWrite("The enemy has died");
					//lootdrop();
					//newEnemy();
				}
				else {
					int acc = rand.nextInt(100);
			    	if (enemy.accuracy >= acc) {
			    		int temp = rand.nextInt(enemy.maxAttackDMG/2);
			    		player.setHealth(player.getHealth()-temp);
			    		GUI.consoleWrite("Der Gegner trifft dich für " + temp + " Schaden und du hast noch " + player.getHealth() + " Leben!");
			    	}
			    	else {
			    		GUI.consoleWrite("Der Gegner hat dich verfehlt!");
			    	}
					encounter();
				}
			}
			else {
				GUI.consoleWrite("No health pots left");
				//response = true;
				if (player.getHealth() <= 0) {
					GUI.consoleWrite("Youve taken too much damage and have died.");
					//response = true;
				}
				else if (enemy.health <= 0) {
					GUI.consoleWrite("The enemy has died");
					//newEnemy();
				}
				else {
					encounter();
				}
			}
		}
		else {

		}
    }
    
    //Jede Runde überprüfen ob was passiert ist zu dir oder du den gegner getötet hast
    public void checkFight() {
    	if (GAME.CurrentRoom.isMegasuperboss() && enemy.health<=0) {
    		GUI.consoleWrite("Du hast es geschaft. Du bist frei. Du hast es geschaft den Grand Champion zu erlegen und hast deine Freiheit gewonnen. Du gehst langsam zur anderen Seite der Arena und findest dort einen Ausweg.");
    	}
		if (player.getHealth()<=0) {
			System.out.println("you ded");
			GUI.consoleWrite("Du bist gestorben!");
		}
		else if (enemy.health<=0) {
			System.out.println("enemy ded");
			GUI.consoleWrite("Der Gegner ist gestorben!");
			xpdrop();
			GAME.CurrentRoom.setenemy(false);
			GAME.CurrentRoom.setDeadEnamy(true);
			GUI.update(player);
			GUI.consoleWrite("----------------------------------------------");
			GUI.consoleWrite(GAME.CurrentRoom.getBeschreibung());
		}
		else {
			encounter();
		}
	}
    
    //Jede Runde leben auslesen
	private void encounter() {
		//turn ++;
		GUI.consoleWrite("----------------------------------------------");
		GUI.consoleWrite(" Gegner " + enemy.name);
		GUI.consoleWrite("----------------------------------------------");
		GUI.consoleWrite("\t Spieler Lebenspunkte: "+player.getHealth());
		GUI.consoleWrite("\t Gegner Lebenspunkte: " + enemy.health);
		GUI.consoleWrite("\t Was willst du machen? \n");
		yourTurn = true;
		GAME.encounterIsWaitingForInput = true;
	}
	
	//boah kein bock auf meine begründung hierfür aber ja das ist ein xp system
	private void xpdrop() {
		
		//alt
		/*int exp = (5*(player.intelligence/2)*enemy.level/2)+(rand.nextInt(50)*player.luck/20);*/
		if (GAME.CurrentRoom.isBoss()) {
			int exp = ((5*(player.intelligence/2)*enemy.level/2)+(rand.nextInt(50)*2))*3;
			GUI.consoleWrite("Du erhältst vom epischen Boss " + exp + "Erfahrung!");
			player.experience = (player.experience+exp);
			if (player.experience>player.getXpToMax()) {
				player.experience = player.experience-player.getXpToMax();
				player.level = (player.level+1);
				player.perkpoints ++;
				GUI.consoleWrite("Du erreichst das Level" + player.level + " und hast noch " + player.perkpoints + " Meisterschaftspunkte zu verwenden!");
			}
			GUI.update(player);
		}
		else {
			int exp = (5*(player.intelligence/2)*enemy.level/2)+(rand.nextInt(50)*2);
			GUI.consoleWrite("Du erhältst vom Gegner " + exp + "Erfahrung!");
			player.experience = (player.experience+exp);
			if (player.experience>player.getXpToMax()) {
				player.experience = player.experience-player.getXpToMax();
				player.level = (player.level+1);
				player.perkpoints ++;
				GUI.consoleWrite("Du erreichst das Level" + player.level + " und hast noch " + player.perkpoints + " Meisterschaftspunkte!");
			}
			GUI.update(player);
		}
		
	}
	
	//irgendwie muss leben dazugewonnen werden
	public void droppot() {
		int acc;
		acc = rand.nextInt(100);
		if (player.getHitChance() >= acc) {
			player.healthPots ++;
			GUI.consoleWrite("Du has ein Elixir gefunden!");
		}
		
	}
}
