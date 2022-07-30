package core;


import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityAirChangeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;

public class EventListener implements Listener {

    @SuppressWarnings("deprecation")
	@EventHandler
    public void MorphMenu(InventoryClickEvent e) {
    	if(!e.getView().getTitle().equals(Var.menuname))
    		return;
    	
    	e.setCancelled(true);
    
    	Player p = (Player) e.getWhoClicked();
    	
    	ItemStack itm = e.getCurrentItem();
    	if (itm==null||itm.getType().isAir())
    		return;
    	
    	
    	if(Var.ActiveMorphs.containsKey(p)) {
    		Utils.unMorph(p);
    	}
    		
    	if(itm.getType().equals(Material.BARRIER)) {
    		for(Player p2 : Var.plugin.getServer().getOnlinePlayers()) 
    			p2.showPlayer(p);
    	p.closeInventory();
    	return;
    	}
    	
		for(int i=0;i<Var.morp.size();i++) {
		
			Morphs mor=Var.morp.get(i);
			
			if(itm.getType().equals(mor.getItem())) 
				if(p.hasPermission(mor.getPermission())) {
					
					Utils.morph(p,mor);
					p.closeInventory();
					return;
				}
    	
		}

		
    }
    
    
    @EventHandler
    public void Movement(PlayerMoveEvent e) {
    	Player p = e.getPlayer();
    	
    	if(Var.ActiveMorphs.containsKey(p)) 
    	Var.ActiveMorphs.get(p).teleport(p);
    		
    }
    
    @EventHandler
    public void Quitevent(PlayerQuitEvent e) {
    	Player p = e.getPlayer();
    	
    	Utils.unMorph(p);
    	
    }
    @SuppressWarnings("deprecation")
	@EventHandler
    public void Join(PlayerJoinEvent e) {
    	Player p = e.getPlayer();
    	
    	for(Player p2 : Var.plugin.getServer().getOnlinePlayers()) 
    		if(Var.ActiveMorphs.containsKey(p2)) {
    		p.hidePlayer(p2);
    		((CraftPlayer) p).getHandle().b.a(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.a, ((CraftPlayer) p2).getHandle()));
    		}
    	
    			
    	
    }
	@EventHandler
    public void Damage(EntityDamageEvent e) {
		

		
		if(e.getEntity() instanceof Player) 
			if(Var.ActiveMorphs.containsKey(e.getEntity())) {
				if(e.getCause().equals(DamageCause.DROWNING)) {
					e.setDamage(0);
					e.setCancelled(true);
					return;
				}
				
				if(((LivingEntity)Var.ActiveMorphs.get(e.getEntity())).getNoDamageTicks()>0)
					e.setCancelled(true);
				
	  			if(e.getCause().equals(DamageCause.LAVA)) {
    				e.setCancelled(true);
    				return;
    			}
	  			if(Var.morptype.get(e.getEntity()).getEntity().equals(EntityType.GHAST)||
	  					Var.morptype.get(e.getEntity()).getEntity().equals(EntityType.BLAZE)) {
	    			if(e.getCause().equals(DamageCause.FIRE)) {
	    				e.setCancelled(true);
	    				return;
	    			}
	    			if(e.getCause().equals(DamageCause.FIRE_TICK)) {
	    				e.setCancelled(true);
	    				return;
	    			}
	  			}
	  			
				double dmg =((LivingEntity)e.getEntity()).getHealth()-e.getDamage();
    			if(dmg<0)
    				dmg=0;
				((LivingEntity)Var.ActiveMorphs.get(e.getEntity())).setHealth(dmg);
			
			}
		
		
		
		if(Var.ActiveMorphs.containsValue(e.getEntity())) {
			for(Player p : Var.plugin.getServer().getOnlinePlayers()) 
	    		if(Var.ActiveMorphs.get(p)==e.getEntity()) {
	    			
	    	
	    			
	    			

	    			
	    			if(e.getCause().equals(DamageCause.SUFFOCATION)) {
	    				e.setCancelled(true);
	    				return;
	    			}
	  
	    			
	    	
	    			((LivingEntity)p).setFireTicks( ((LivingEntity)e.getEntity()).getFireTicks());
	    			
	    			double dmg =((LivingEntity)e.getEntity()).getHealth()-e.getDamage();
	    			if(dmg<0)
	    				dmg=0;
	    			p.setHealth(dmg);
	    			
	    		}
			
			
		}
		
	}
	@EventHandler
    public void Damage(EntityDamageByEntityEvent e) {
		
		if(Var.ActiveMorphs.containsValue(e.getEntity())) {
			for(Player p : Var.plugin.getServer().getOnlinePlayers()) 
	    		if(Var.ActiveMorphs.get(p)==e.getEntity()) {
	    			
	    			
				    if(p.getNoDamageTicks()>0)	
				    	e.setCancelled(true);
				    
				    Entity damager = e.getDamager();
				    Entity victim = e.getEntity();
				 
				    Vector vVec = victim.getLocation().toVector().clone();
				    Vector dVec = damager.getLocation().toVector().clone();
				    Vector direction = vVec.subtract(dVec).normalize();
				 
				    final float POWER = .5f;
				    direction.add(new Vector(0.0D, POWER*1.5D, 0.0D)).multiply(POWER);
			
				    p.setVelocity(direction);
	    		}
		}
		
	}
	@SuppressWarnings("deprecation")
	@EventHandler
    public void Regen(EntityRegainHealthEvent e) {
		if(e.getEntity() instanceof Player) 
			if(Var.ActiveMorphs.containsKey(e.getEntity())) {

				if(((LivingEntity)Var.ActiveMorphs.get(e.getEntity())).getNoDamageTicks()>0)
					e.setCancelled(true);
				
				double dmg =((LivingEntity)e.getEntity()).getHealth()+e.getAmount();
    			if(dmg>((LivingEntity)e.getEntity()).getMaxHealth())
    				dmg=((LivingEntity)e.getEntity()).getMaxHealth();
				((LivingEntity)Var.ActiveMorphs.get(e.getEntity())).setHealth(dmg);
			
			}
		
		
		
		if(Var.ActiveMorphs.containsValue(e.getEntity())) {
			for(Player p : Var.plugin.getServer().getOnlinePlayers()) 
	    		if(Var.ActiveMorphs.get(p)==e.getEntity()) {
	    			
	    	
	    			
	    			
	    	
	    			((LivingEntity)p).setFireTicks( ((LivingEntity)e.getEntity()).getFireTicks());
	    			
					double dmg =((LivingEntity)e.getEntity()).getHealth()+e.getAmount();
	    			if(dmg>((LivingEntity)e.getEntity()).getMaxHealth())
	    				dmg=((LivingEntity)e.getEntity()).getMaxHealth();
	    			p.setHealth(dmg);
	    			
	    		}
			
			
		}
		
	}
		
	@EventHandler
    public void Death(EntityDeathEvent e) {
		
		if(Var.ActiveMorphs.containsValue(e.getEntity())) {
			for(Player p : Var.plugin.getServer().getOnlinePlayers()) 
	    		if(Var.ActiveMorphs.get(p)==e.getEntity()) {
	    			
	    			for(ItemStack itm :e.getDrops()) 
	    				itm.setAmount(0);
	    			
	    			
	    			
	    			p.setHealth(0);
	    		}
		}
		
	}
	@EventHandler
    public void RespawnPlayer(PlayerRespawnEvent e) {
		
		Player p = e.getPlayer();
		
		if(!Var.ActiveMorphs.containsKey(p))
			return;
			
		Morphs m= Var.morptype.get(p);
		
		Utils.unMorph(p);
		Utils.morph(p, m);
		
		
	}
	@EventHandler
    public void Sneak(PlayerToggleSneakEvent e) {
		Player p = e.getPlayer();
		
		if(!Var.ActiveMorphs.containsKey(p))
			return;

		LivingEntity data = (LivingEntity) Var.ActiveMorphs.get(p);
		data.setCustomNameVisible(!e.isSneaking());

		
		
	}
	@EventHandler
    public void hideplayers(EntityTargetLivingEntityEvent e) {
		
		if(e.getTarget() instanceof Player)
		if(Var.ActiveMorphs.containsKey(e.getTarget()))
			if(e.getReason()==TargetReason.CLOSEST_PLAYER)
				e.setCancelled(true);
		
		
	}
	@EventHandler
	 public void dorwn(EntityAirChangeEvent e) {
		
		if(Var.ActiveMorphs.containsValue(e.getEntity()))
			for(Player p : Var.plugin.getServer().getOnlinePlayers()) 
	    		if(Var.ActiveMorphs.get(p)==e.getEntity()) 
	    			p.setRemainingAir(e.getAmount());
	}
}
