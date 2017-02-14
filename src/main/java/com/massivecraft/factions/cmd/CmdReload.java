package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Factions;
import com.massivecraft.factions.Permission;
import com.massivecraft.factions.entity.Conf;
import com.massivecraft.factions.zcore.util.TL;

public class CmdReload extends FCommand {

    public CmdReload() {
        super();
        this.aliases.add("reload");

        //this.requiredArgs.add("");
        this.optionalArgs.put("file", "all");

        this.permission = Permission.RELOAD.node;
        this.disableOnLock = false;

        senderMustBePlayer = false;
        senderMustBeMember = false;
        senderMustBeModerator = false;
        senderMustBeAdmin = false;
    }

    @Override
    public void perform() {
        long timeInitStart = System.currentTimeMillis();
        Conf.load();
        Factions.get().reloadConfig();
        Factions.get().loadLang();
        long timeReload = (System.currentTimeMillis() - timeInitStart);

        msg(TL.COMMAND_RELOAD_TIME, timeReload);
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_RELOAD_DESCRIPTION;
    }
}
