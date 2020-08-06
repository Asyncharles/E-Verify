package net.everify.commands;

import net.everify.EVerify;
import net.everify.mail.JavaMail;
import net.everify.utils.GUI;
import net.everify.utils.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import javax.mail.internet.AddressException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class EmailInsertCommand extends EVCommand{

    private List<String> illegalCharacter = Arrays.asList("/", "?", "!", "&", ",", "#");

    public EmailInsertCommand() {
        super("mail", "Inserts an email for a player", false);
    }

    @Override
    public void onCommand(Player player, String[] args) {

        if(args.length != 2) {
            player.sendMessage("§cPlease insert your email");
            return;
        }

        if(illegalCharacter.contains(args[1])) {
            player.sendMessage("§cIllegal character");
            return;
        }

        if(args[1].contains("@") && args[1].contains(".")) {
            String email = args[1];
            GUI confirmation = new GUI(EVerify.getInstance(), "§3Confirmation", 1);
            confirmation.setItem(1, new ItemStackBuilder(Material.SLIME_BALL).name("§aConfirm").lore(" ",
                    " §8→ Click to confirm").create(), (p, event) -> {
                int code = randomCodeGenerator();
                JavaMail.sendVerificationEmail(email, code, player);
                new AwaitingVerification(player.getUniqueId(), code);
                p.closeInventory();
            });
            confirmation.setItem(4, new ItemStackBuilder(Material.DETECTOR_RAIL).name("§6Information").lore(" ", "§eSelected email :",
                    "§f" + email, " ", "§eVerified :",
                    AwaitingVerification.isAwaitingVerification(player.getUniqueId()) ? "§ayes" : "§cno")
                    .create(), (p, event) -> {

            });
            confirmation.setItem(7, new ItemStackBuilder(Material.MAGMA_CREAM).name("§cCancel").lore(" ",
                    " §8→ Click to confirm").create(), (p, event) -> {
                p.closeInventory();
            });
            confirmation.setLocked(true);
            confirmation.open(player);

        } else {
            player.sendMessage("§cPlease send a valid email.");
        }

    }

    private final static int randomCodeGenerator() {
        Random r = new Random();
        return r.nextInt(8999) + 1000;
    }
}
