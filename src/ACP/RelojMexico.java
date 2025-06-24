package ACP;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class RelojMexico extends JLabel {

    private final SimpleDateFormat formato = new SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy | HH:mm:ss", new Locale("es", "MX"));

    public RelojMexico() {
        formato.setTimeZone(TimeZone.getTimeZone("America/Mexico_City"));
        Timer timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String texto = formato.format(new Date());
            	setText(Character.toUpperCase(texto.charAt(0)) + texto.substring(1));
            }
        });
        timer.start();
    }
}
