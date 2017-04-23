package com.massivecraft.legacyfactions.cmd;

import org.bukkit.configuration.ConfigurationSection;

import com.massivecraft.legacyfactions.Factions;
import com.massivecraft.legacyfactions.Permission;
import com.massivecraft.legacyfactions.TL;
import com.massivecraft.legacyfactions.entity.Conf;
import com.massivecraft.legacyfactions.integration.vault.VaultEngine;

import java.util.ArrayList;
import java.util.List;


public class CmdHelp extends FCommand {

    public CmdHelp() {
        super();
        this.aliases.add("help");
        this.aliases.add("h");
        this.aliases.add("?");

        //this.requiredArgs.add("");
        this.optionalArgs.put("page", "1");

        this.permission = Permission.HELP.node;
        this.disableOnLock = false;

        senderMustBePlayer = false;
        senderMustBeMember = false;
        senderMustBeModerator = false;
        senderMustBeAdmin = false;
    }

    @Override
    public void perform() {
        if (Factions.get().getConfig().getBoolean("use-old-help", true)) {
            if (helpPages == null) {
                updateHelp();
            }

            int page = this.argAsInt(0, 1);
            sendMessage(Factions.get().txt.titleize("Factions Help (" + page + "/" + helpPages.size() + ")"));

            page -= 1;

            if (page < 0 || page >= helpPages.size()) {
                msg(TL.COMMAND_HELP_404.format(String.valueOf(page)));
                return;
            }
            sendMessage(helpPages.get(page));
            return;
        }
        ConfigurationSection help = Factions.get().getConfig().getConfigurationSection("help");
        if (help == null) {
            help = Factions.get().getConfig().createSection("help"); // create new help section
            List<String> error = new ArrayList<String>();
            error.add("&cUpdate help messages in config.yml!");
            error.add("&cSet use-old-help for legacy help messages");
            help.set("'1'", error); // add default error messages
        }
        String pageArg = this.argAsString(0, "1");
        List<String> page = help.getStringList(pageArg);
        if (page == null || page.isEmpty()) {
            msg(TL.COMMAND_HELP_404.format(pageArg));
            return;
        }
        for (String helpLine : page) {
            sendMessage(Factions.get().txt.parse(helpLine));
        }
    }

    //----------------------------------------------//
    // Build the help pages
    //----------------------------------------------//

    public ArrayList<ArrayList<String>> helpPages;

    public void updateHelp() {
        helpPages = new ArrayList<ArrayList<String>>();
        ArrayList<String> pageLines;

        pageLines = new ArrayList<String>();
        pageLines.add(Factions.get().cmdBase.cmdHelp.getUseageTemplate(true));
        pageLines.add(Factions.get().cmdBase.cmdList.getUseageTemplate(true));
        pageLines.add(Factions.get().cmdBase.cmdShow.getUseageTemplate(true));
        pageLines.add(Factions.get().cmdBase.cmdPower.getUseageTemplate(true));
        pageLines.add(Factions.get().cmdBase.cmdJoin.getUseageTemplate(true));
        pageLines.add(Factions.get().cmdBase.cmdLeave.getUseageTemplate(true));
        pageLines.add(Factions.get().cmdBase.cmdChat.getUseageTemplate(true));
        pageLines.add(Factions.get().cmdBase.cmdToggleAllianceChat.getUseageTemplate(true));
        pageLines.add(Factions.get().cmdBase.cmdHome.getUseageTemplate(true));
        pageLines.add(Factions.get().txt.parse(TL.COMMAND_HELP_NEXTCREATE.toString()));
        helpPages.add(pageLines);

        pageLines = new ArrayList<String>();
        pageLines.add(Factions.get().cmdBase.cmdCreate.getUseageTemplate(true));
        pageLines.add(Factions.get().cmdBase.cmdDescription.getUseageTemplate(true));
        pageLines.add(Factions.get().cmdBase.cmdTag.getUseageTemplate(true));
        pageLines.add(Factions.get().txt.parse(TL.COMMAND_HELP_INVITATIONS.toString()));
        pageLines.add(Factions.get().cmdBase.cmdOpen.getUseageTemplate(true));
        pageLines.add(Factions.get().cmdBase.cmdInvite.getUseageTemplate(true));
        pageLines.add(Factions.get().cmdBase.cmdDeinvite.getUseageTemplate(true));
        pageLines.add(Factions.get().txt.parse(TL.COMMAND_HELP_HOME.toString()));
        pageLines.add(Factions.get().cmdBase.cmdSethome.getUseageTemplate(true));
        helpPages.add(pageLines);

        if (VaultEngine.isSetup() && Conf.econEnabled && Conf.bankEnabled) {
            pageLines = new ArrayList<String>();
            pageLines.add("");
            pageLines.add(Factions.get().txt.parse(TL.COMMAND_HELP_BANK_1.toString()));
            pageLines.add(Factions.get().txt.parse(TL.COMMAND_HELP_BANK_2.toString()));
            pageLines.add(Factions.get().txt.parse(TL.COMMAND_HELP_BANK_3.toString()));
            pageLines.add("");
            pageLines.add(Factions.get().cmdBase.cmdMoney.getUseageTemplate(true));
            pageLines.add("");
            pageLines.add("");
            pageLines.add("");
            helpPages.add(pageLines);
        }

        pageLines = new ArrayList<String>();
        pageLines.add(Factions.get().cmdBase.cmdClaim.getUseageTemplate(true));
        pageLines.add(Factions.get().cmdBase.cmdAutoClaim.getUseageTemplate(true));
        pageLines.add(Factions.get().cmdBase.cmdUnclaim.getUseageTemplate(true));
        pageLines.add(Factions.get().cmdBase.cmdUnclaimall.getUseageTemplate(true));
        pageLines.add(Factions.get().cmdBase.cmdKick.getUseageTemplate(true));
        pageLines.add(Factions.get().cmdBase.cmdMod.getUseageTemplate(true));
        pageLines.add(Factions.get().cmdBase.cmdAdmin.getUseageTemplate(true));
        pageLines.add(Factions.get().cmdBase.cmdTitle.getUseageTemplate(true));
        pageLines.add(Factions.get().cmdBase.cmdSB.getUseageTemplate(true));
        pageLines.add(Factions.get().cmdBase.cmdSeeChunk.getUseageTemplate(true));
        pageLines.add(Factions.get().cmdBase.cmdStatus.getUseageTemplate(true));
        pageLines.add(Factions.get().txt.parse(TL.COMMAND_HELP_PLAYERTITLES.toString()));
        helpPages.add(pageLines);

        pageLines = new ArrayList<String>();
        pageLines.add(Factions.get().cmdBase.cmdMap.getUseageTemplate(true));
        pageLines.add(Factions.get().cmdBase.cmdBoom.getUseageTemplate(true));
        pageLines.add(Factions.get().cmdBase.cmdOwner.getUseageTemplate(true));
        pageLines.add(Factions.get().cmdBase.cmdOwnerList.getUseageTemplate(true));
        pageLines.add(Factions.get().txt.parse(TL.COMMAND_HELP_OWNERSHIP_1.toString()));
        pageLines.add(Factions.get().txt.parse(TL.COMMAND_HELP_OWNERSHIP_2.toString()));
        pageLines.add(Factions.get().txt.parse(TL.COMMAND_HELP_OWNERSHIP_3.toString()));
        helpPages.add(pageLines);

        pageLines = new ArrayList<String>();
        pageLines.add(Factions.get().cmdBase.cmdDisband.getUseageTemplate(true));
        pageLines.add("");
        pageLines.add(Factions.get().cmdBase.cmdRelationAlly.getUseageTemplate(true));
        pageLines.add(Factions.get().cmdBase.cmdRelationNeutral.getUseageTemplate(true));
        pageLines.add(Factions.get().cmdBase.cmdRelationEnemy.getUseageTemplate(true));
        pageLines.add(Factions.get().txt.parse(TL.COMMAND_HELP_RELATIONS_1.toString()));
        pageLines.add(Factions.get().txt.parse(TL.COMMAND_HELP_RELATIONS_2.toString()));
        pageLines.add(Factions.get().txt.parse(TL.COMMAND_HELP_RELATIONS_3.toString()));
        pageLines.add(Factions.get().txt.parse(TL.COMMAND_HELP_RELATIONS_4.toString()));
        helpPages.add(pageLines);

        pageLines = new ArrayList<String>();
        pageLines.add(Factions.get().txt.parse(TL.COMMAND_HELP_RELATIONS_5.toString()));
        pageLines.add(Factions.get().txt.parse(TL.COMMAND_HELP_RELATIONS_6.toString()));
        pageLines.add(Factions.get().txt.parse(TL.COMMAND_HELP_RELATIONS_7.toString()));
        pageLines.add(TL.COMMAND_HELP_RELATIONS_8.toString());
        pageLines.add(Factions.get().txt.parse(TL.COMMAND_HELP_RELATIONS_9.toString()));
        pageLines.add(Factions.get().txt.parse(TL.COMMAND_HELP_RELATIONS_10.toString()));
        pageLines.add(Factions.get().txt.parse(TL.COMMAND_HELP_RELATIONS_11.toString()));
        pageLines.add(Factions.get().txt.parse(TL.COMMAND_HELP_RELATIONS_12.toString()));
        pageLines.add(Factions.get().txt.parse(TL.COMMAND_HELP_RELATIONS_13.toString()));
        helpPages.add(pageLines);

        pageLines = new ArrayList<String>();
        pageLines.add(Factions.get().txt.parse(TL.COMMAND_HELP_PERMISSIONS_1.toString()));
        pageLines.add(Factions.get().txt.parse(TL.COMMAND_HELP_PERMISSIONS_2.toString()));
        pageLines.add(Factions.get().txt.parse(TL.COMMAND_HELP_PERMISSIONS_3.toString()));
        pageLines.add(TL.COMMAND_HELP_PERMISSIONS_4.toString());
        pageLines.add(Factions.get().txt.parse(TL.COMMAND_HELP_PERMISSIONS_5.toString()));
        pageLines.add(Factions.get().txt.parse(TL.COMMAND_HELP_PERMISSIONS_6.toString()));
        pageLines.add(Factions.get().txt.parse(TL.COMMAND_HELP_PERMISSIONS_7.toString()));
        pageLines.add(Factions.get().txt.parse(TL.COMMAND_HELP_PERMISSIONS_8.toString()));
        pageLines.add(Factions.get().txt.parse(TL.COMMAND_HELP_PERMISSIONS_9.toString()));
        helpPages.add(pageLines);

        pageLines = new ArrayList<String>();
        pageLines.add(TL.COMMAND_HELP_MOAR_1.toString());
        pageLines.add(Factions.get().cmdBase.cmdBypass.getUseageTemplate(true));
        pageLines.add(Factions.get().txt.parse(TL.COMMAND_HELP_ADMIN_1.toString()));
        pageLines.add(Factions.get().txt.parse(TL.COMMAND_HELP_ADMIN_2.toString()));
        pageLines.add(Factions.get().txt.parse(TL.COMMAND_HELP_ADMIN_3.toString()));
        pageLines.add(Factions.get().cmdBase.cmdSafeunclaimall.getUseageTemplate(true));
        pageLines.add(Factions.get().cmdBase.cmdWarunclaimall.getUseageTemplate(true));
        
        pageLines.add(Factions.get().txt.parse("<i>Note: " + Factions.get().cmdBase.cmdUnclaim.getUseageTemplate(false) + Factions.get().txt.parse("<i>") + " works on safe/war zones as well."));
        pageLines.add(Factions.get().cmdBase.cmdPeaceful.getUseageTemplate(true));
        helpPages.add(pageLines);

        pageLines = new ArrayList<String>();
        pageLines.add(Factions.get().txt.parse(TL.COMMAND_HELP_MOAR_2.toString()));
        pageLines.add(Factions.get().cmdBase.cmdChatSpy.getUseageTemplate(true));
        pageLines.add(Factions.get().cmdBase.cmdPermanent.getUseageTemplate(true));
        pageLines.add(Factions.get().cmdBase.cmdPermanentPower.getUseageTemplate(true));
        pageLines.add(Factions.get().cmdBase.cmdPowerBoost.getUseageTemplate(true));
        pageLines.add(Factions.get().cmdBase.cmdConfig.getUseageTemplate(true));
        helpPages.add(pageLines);

        pageLines = new ArrayList<String>();
        pageLines.add(Factions.get().txt.parse(TL.COMMAND_HELP_MOAR_3.toString()));
        pageLines.add(Factions.get().cmdBase.cmdLock.getUseageTemplate(true));
        pageLines.add(Factions.get().cmdBase.cmdReload.getUseageTemplate(true));
        pageLines.add(Factions.get().cmdBase.cmdSaveAll.getUseageTemplate(true));
        pageLines.add(Factions.get().cmdBase.cmdVersion.getUseageTemplate(true));
        helpPages.add(pageLines);
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_HELP_DESCRIPTION;
    }
}
