package core;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class Morphs {
	
	private EntityType ent;
	private Material me;
	private String name;
	private String perm;
	private boolean fly;

	public Morphs(EntityType ent,Material me,String name,String perm) {
		
		this.ent=ent;
		this.me=me;
		this.name=name;
		this.perm=perm;
		
	}
	public Morphs(EntityType ent,Material me,String name,String perm, boolean b) {
		this.ent=ent;
		this.me=me;
		this.name=name;
		this.perm=perm;
		fly=b;
	}
	public String getPermission(){
		
		return perm;
	}
	public Material getItem() {
		return me;
	}
	public String getName() {
		return name;
	}
	public EntityType getEntity() {
		return ent;
	}
	public boolean canFly() {
		// TODO Auto-generated method stub
		return fly;
	}
}
