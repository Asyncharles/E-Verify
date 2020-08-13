package net.everify.mail;

import com.google.common.base.Strings;
import com.google.common.io.Resources;
import net.everify.EVerify;
import org.apache.commons.io.FileUtils;
import org.bukkit.Server;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailHTMLCode {

    private String content, style, htmlcode;

    private int code;
    private String name, id;
    private Server server = EVerify.getInstance().getServer();

    public MailHTMLCode(int code, String name, String id) {
        this.code = code;
        this.name = name;
        this.id = id;
        try {
            this.content = Resources.toString(getClass().getResource("resources/index.html"), StandardCharsets.UTF_8);
            this.style = Resources.toString(getClass().getResource("resources/style.css"), StandardCharsets.UTF_8);
            this.htmlcode = "<head><style>" + style + "</style></head>" + content;
        } catch (IOException e) {
            e.printStackTrace();
        }

        htmlcode = htmlcode.replace("%code%", String.valueOf(code)).replace("%name%", name).replace("%id%", id)
        .replace("%server%", server.getName()).replace("%ip%", server.getIp()).replace("%version%", server.getVersion());

    }

    /**
     *
     * @return The email HTML code
     */

    public String getHTMLCode() {
        return htmlcode;

    }





}
