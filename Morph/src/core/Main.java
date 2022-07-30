package core;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	
	
	@Override
	public void onDisable() {
		super.onDisable();
	  	for(Player p : Var.plugin.getServer().getOnlinePlayers()) 
    		if(Var.ActiveMorphs.containsKey(p)) {
    			Utils.unMorph(p);
    		}
	}

	@Override
	public void onEnable() {
		super.onEnable();
		
		Var.plugin=this;
		getServer().getPluginManager().registerEvents(new EventListener(), this);
		getCommand("morph").setExecutor(new CommandExec());
		
		loadmorphs();
		
		
	}

	private void loadmorphs() {
		
		Var.morp.add(new Morphs(EntityType.COW, Material.COW_SPAWN_EGG, "Cow", "morph.cow"));
		Var.morp.add(new Morphs(EntityType.WITHER, Material.WITHER_SKELETON_SKULL, "Wither", "morph.wither",true));
		Var.morp.add(new Morphs(EntityType.BLAZE, Material.BLAZE_SPAWN_EGG, "Blaze", "morph.blaze",true));
		Var.morp.add(new Morphs(EntityType.BAT, Material.BAT_SPAWN_EGG, "Bat", "morph.bat",true));
		Var.morp.add(new Morphs(EntityType.CAT, Material.CAT_SPAWN_EGG, "Cat", "morph.cat"));
		Var.morp.add(new Morphs(EntityType.GHAST, Material.GHAST_SPAWN_EGG, "Ghast", "morph.ghast",true));
		Var.morp.add(new Morphs(EntityType.BEE, Material.BEE_SPAWN_EGG, "Bee", "morph.bee",true));
		Var.morp.add(new Morphs(EntityType.CREEPER, Material.CREEPER_SPAWN_EGG, "Creeper", "morph.creeper"));
		Var.morp.add(new Morphs(EntityType.SHEEP, Material.SHEEP_SPAWN_EGG, "Sheep", "morph.sheep"));
		Var.morp.add(new Morphs(EntityType.CHICKEN, Material.CHICKEN_SPAWN_EGG, "Chicken", "morph.chicken"));
		Var.morp.add(new Morphs(EntityType.LLAMA, Material.LLAMA_SPAWN_EGG, "Llama", "morph.llama"));
		Var.morp.add(new Morphs(EntityType.SKELETON, Material.SKELETON_SPAWN_EGG, "Skeleton", "morph.skeleton"));
		Var.morp.add(new Morphs(EntityType.ZOMBIE, Material.ZOMBIE_SPAWN_EGG, "Zombie", "morph.zombie"));
		Var.morp.add(new Morphs(EntityType.SQUID, Material.SQUID_SPAWN_EGG, "Squid", "morph.squid"));
		Var.morp.add(new Morphs(EntityType.GLOW_SQUID, Material.GLOW_SQUID_SPAWN_EGG, "Glow sqiud", "morph.glowsqiud"));
		Var.morp.add(new Morphs(EntityType.VILLAGER, Material.VILLAGER_SPAWN_EGG, "Villager", "morph.villager"));
		
	}



}
