package core;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;

public class Utils {

	public static void openMenu(Player p) {
		
		Inventory men = Bukkit.createInventory(null, (int) (Math.ceil((Var.morp.size()+1)/9.0)*9), Var.menuname);
		
		
		for(int i=0;i<Var.morp.size();i++) {
		
			Morphs mor=Var.morp.get(i);
			
			if(p.hasPermission(mor.getPermission()))
				men.setItem(i,creatItm(mor.getItem(),"§2"+mor.getName()));
			
		}
		men.setItem((int) (Math.ceil((Var.morp.size()+1)/9.0)*9)-1, creatItm(Material.BARRIER, "§4remove Morph"));
		p.openInventory(men);
		
	}

	
	public static void removeAI(Entity e,String nam){
		
		LivingEntity data = (LivingEntity) e;
		data.setAI(false);
		data.setGravity(false);
		data.setCustomName(nam);
		data.setCustomNameVisible(true);
		data.setRemoveWhenFarAway(false);
		
	}
	public static ItemStack creatItm(org.bukkit.Material mat, String str) {
		
		ItemStack itm = new ItemStack(mat,1);
		ItemMeta data = itm.getItemMeta();
		
		data.setDisplayName(str);
		
		itm.setItemMeta(data);
		
		return itm;
	}


	@SuppressWarnings("deprecation")
	public static void morph(Player p, Morphs mor) {

		if(mor.canFly()) {
			p.setAllowFlight(true);
			p.setFlying(true);
		}
		
		Entity ent =p.getWorld().spawnEntity(p.getLocation(), mor.getEntity());
		removeAI(ent,p.getDisplayName());
		p.hideEntity(Var.plugin, ent);
		
		p.setMaxHealth(((LivingEntity)ent).getMaxHealth());
		//p.setHealth(p.getMaxHealth());
		if(p.getHealth()>0)
		((LivingEntity)ent).setHealth(p.getHealth());
		
		for(Player other : Var.plugin.getServer().getOnlinePlayers()) {
		
		
			other.hidePlayer(p);
			
			
			((CraftPlayer) other).getHandle().b.a(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.a, ((CraftPlayer) p).getHandle()));
			
		}
		Var.ActiveMorphs.put(p,ent);
		Var.morptype.put(p, mor);

		

		
	}
	@SuppressWarnings("deprecation")
	public static void unMorph(Player p) {
    	
		if(!Var.ActiveMorphs.containsKey(p)) 
			return;
			

			if(Var.morptype.get(p).canFly()&&!p.getGameMode().equals(GameMode.CREATIVE)) {
					p.setFlying(false);
					p.setAllowFlight(false);
			}
			
    		for(Player p2 : Var.plugin.getServer().getOnlinePlayers()) 
    			p2.showPlayer(p);
    		
			p.setMaxHealth(20);
    		Var.ActiveMorphs.get(p).remove();
    		Var.ActiveMorphs.remove(p);
    		Var.morptype.remove(p);
    	
    
	}
	
	
	


	
}
