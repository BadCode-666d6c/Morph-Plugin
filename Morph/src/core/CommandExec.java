package core;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandExec implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sen, Command cmd, String lab, String[] arg) {
		
		if(cmd.getName().equals("morph")) {
			
			if(!(sen instanceof Player)) {
				sen.sendMessage("You have to be a Player to use that command");
				return true;
			}
			Player p = (Player) sen;
			if(!p.hasPermission("morph"))
				return true;
			
			Utils.openMenu(p);
			
			return true;
		}
		
		return false;
	}
	


}
