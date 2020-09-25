package net.everify.mail;

import com.google.common.io.Resources;
import main.java.net.everify.EVerify;
import org.bukkit.Server;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class MailHTMLCode {

    private String htmlcode, content, style;

    private int code;
    private String name, id;
    private Server server = EVerify.getInstance().getServer();

    public MailHTMLCode(int code, String name, String id) {
        this.code = code;
        this.name = name;
        this.id = id;

        try {
            this.content = Resources.toString(EVerify.getInstance().getHTMLURL(), StandardCharsets.UTF_8);
            this.style = Resources.toString(EVerify.getInstance().getCssURL(), StandardCharsets.UTF_8);
            this.htmlcode = "<head><style>" + style + "</style></head>" + content;
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        htmlcode = htmlcode.replace("%code%", String.valueOf(code)).replace("%name%", name).replace("%id%", id)
        .replace("%server%", server.getServerName()).replace("%ip%", server.getIp()).replace("%version%", server.getVersion());

    }

    /**
     *
     * @return The email HTML code
     */

    public String getHTMLCode() {
        return htmlcode;

    }





}
