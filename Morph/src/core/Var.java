package core;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

public class Var {

	public static Inventory menu;
	public static String menuname="§5Morphmenu";
	public static ArrayList<Morphs> morp = new ArrayList<>();
	public static Plugin plugin;
	public static HashMap<Player, Entity> ActiveMorphs = new HashMap<>();
	public static HashMap<Player, Morphs> morptype = new HashMap<>();

	
}
