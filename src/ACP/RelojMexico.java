// Clase RelojMexico.java
package ACP;

import javax.swing.*;
import java.awt.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class RelojMexico extends JLabel {
    private static final ZoneId ZONA_MEXICO = ZoneId.of("America/Mexico_City");
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("HH:mm:ss");

    public RelojMexico() {
        setFont(new Font("Segoe UI", Font.BOLD, 14));
        setForeground(new Color(0, 102, 204));
        setHorizontalAlignment(SwingConstants.RIGHT);

        Timer timer = new Timer(1000, e -> actualizarHora());
        timer.start();
        actualizarHora();
    }

    private void actualizarHora() {
        LocalTime horaActual = LocalTime.now(ZONA_MEXICO);
        setText("" + horaActual.format(FORMATO));
    }
}
