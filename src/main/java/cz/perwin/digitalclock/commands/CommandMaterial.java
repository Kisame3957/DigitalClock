package cz.perwin.digitalclock.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import cz.perwin.digitalclock.DigitalClock;
import cz.perwin.digitalclock.core.Clock;

public class CommandMaterial extends MaterialCommand implements ICommand {
	@Override
	public int getArgsSize() {
		return 3;
	}

	@Override
	public String getPermissionName() {
		return "digitalclock.material";
	}

	@Override
	public boolean specialCondition(DigitalClock main, Player player, String[] args) {
		return false;
	}

	@Override
	public boolean checkClockExistence() {
		return true;
	}
	
	@Override
	public boolean neededClockExistenceValue() {
		return true;
	}

	@Override
	public String reactBadArgsSize(String usedCmd) {
		return ChatColor.DARK_RED + DigitalClock.getMessagePrefix() + ChatColor.RED + " Correct usage: '/"+ usedCmd + " material <name> <material>'";
	}

	@Override
	public String reactNoPermissions() {
		return ChatColor.DARK_RED + DigitalClock.getMessagePrefix() + ChatColor.RED + " You aren't allowed to use this command!";
	}

	@Override
	public void specialConditionProcess(DigitalClock main, Player player, String[] args) {
		return;
	}

	@Override
	public String reactBadClockList(String clockName) {
		return ChatColor.DARK_RED + DigitalClock.getMessagePrefix() + ChatColor.RED + " Clock '" + clockName + "' not found!";
	}

	@SuppressWarnings("deprecation")
	@Override
	public void process(DigitalClock main, Player player, String[] args) {
		Clock clock = Clock.loadClockByClockName(args[1]);
		String oldmat = clock.getMaterial().name().toLowerCase().replace("_", " ");
		if(super.isUsableName(args[2])) {
			Material m = Material.getMaterial(args[2]);
			if(super.isSolid(m)) {
				player.sendMessage(ChatColor.DARK_GREEN + DigitalClock.getMessagePrefix() + ChatColor.GREEN + " Your clock '" + args[1] + "' changed material from " + oldmat + " to "+ clock.changeMaterial(m).name().toLowerCase().replace("_", " "));
			} else {
				player.sendMessage(ChatColor.DARK_RED + DigitalClock.getMessagePrefix() + ChatColor.RED + " You can't use "+ m.name().toLowerCase().replace("_", " ") +" as a material, because it is not a block (cuboid shape)!");	
			}
		} else {
			if(args[2].contains(":")) {
				String[] matdata = args[2].split(":");
				if(super.isUsableName(matdata[1])) {
					if(super.isUsableName(matdata[0])) {
						Material m = Material.getMaterial(matdata[0]);
						if(super.isSolid(m)) {
							player.sendMessage(ChatColor.DARK_GREEN + DigitalClock.getMessagePrefix() + ChatColor.GREEN + " Your clock '" + args[1] + "' changed material from " + oldmat + " to "+ clock.changeMaterial(m).name().toLowerCase().replace("_", " "));
						} else {
							player.sendMessage(ChatColor.DARK_RED + DigitalClock.getMessagePrefix() + ChatColor.RED + " You can't use "+ m.name().toLowerCase().replace("_", " ") +" as a material, because it is not a block (cuboid shape)!");	
						}
					} else {
						try {
							Material m = Material.valueOf(matdata[0].toUpperCase());
							if(m != null) {
								if(super.isSolid(m)) {
									player.sendMessage(ChatColor.DARK_GREEN + DigitalClock.getMessagePrefix() + ChatColor.GREEN + " Your clock '" + args[1] + "' changed material from " + oldmat + " to "+ clock.changeMaterial(m).name().toLowerCase().replace("_", " "));
								} else {
									player.sendMessage(ChatColor.DARK_RED + DigitalClock.getMessagePrefix() + ChatColor.RED + " You can't use "+ m.name().toLowerCase().replace("_", " ") +" as a material, because it is not a block (cuboid shape)!");			
								}
							} else {
								player.sendMessage(ChatColor.DARK_RED + DigitalClock.getMessagePrefix() + ChatColor.RED + " Material "+ matdata[0] +" does not exist!");
							}
						} catch(IllegalArgumentException e) {
							player.sendMessage(ChatColor.DARK_RED + DigitalClock.getMessagePrefix() + ChatColor.RED + " Material '"+ matdata[0] +"' does not exist!");
						}
					}
				} else {
					player.sendMessage(ChatColor.DARK_RED + DigitalClock.getMessagePrefix() + ChatColor.RED + " Material data must be positive integer!");
				}
			} else {
				try {
					Material m = Material.valueOf(args[2].toUpperCase());
					if(m != null) {
						if(super.isSolid(m)) {
							player.sendMessage(ChatColor.DARK_GREEN + DigitalClock.getMessagePrefix() + ChatColor.GREEN + " Your clock '" + args[1] + "' changed material from " + oldmat + " to "+ clock.changeMaterial(m).name().toLowerCase().replace("_", " "));
						} else {
							player.sendMessage(ChatColor.DARK_RED + DigitalClock.getMessagePrefix() + ChatColor.RED + " You can't use "+ m.name().toLowerCase().replace("_", " ") +" as a material, because it is not a block (cuboid shape)!");			
						}
					} else {
						player.sendMessage(ChatColor.DARK_RED + DigitalClock.getMessagePrefix() + ChatColor.RED + " Material "+ args[2] +" does not exist!");
					}
				} catch(IllegalArgumentException e) {
					player.sendMessage(ChatColor.DARK_RED + DigitalClock.getMessagePrefix() + ChatColor.RED + " Material '"+ args[2] +"' does not exist!");
				}
			}
		}
	}
}
