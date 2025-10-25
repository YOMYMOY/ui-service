package ar.edu.unt.dds.k3003.ui_service.command;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractCommand implements ITelegramCommand {
    //helpers
    protected String estadoEmoji(String estado) {
        if (estado == null) return "⚪";
        return switch (estado.toLowerCase()) {
            case "activo" -> "🟢";
            case "pendiente" -> "🟡";
            case "borrado", "rechazado" -> "🔴";
            default -> "🔵";
        };
    }

    protected void validarArgumentos(String[] args, int expected, String usageExample) {
        if (args.length < expected) {
            throw new IllegalArgumentException(
                    String.format("Se esperaban %d argumentos. Uso: `%s`", expected, usageExample)
            );
        }
    }

    @SuppressWarnings("unchecked")
    protected String val(Map map, String key) {
        if (map == null) return null;
        Object v = map.get(key);
        return v == null ? null : String.valueOf(v);
    }

    protected String nullTo(String v, String def) {
        return (v == null || v.isBlank()) ? def : v;
    }

    protected boolean looksLikeUrl(String s) {
        return s != null && (s.startsWith("http://") || s.startsWith("https://"));
    }

    // Evita glitches con Markdown básico (no V2). Escapamos algunos símbolos comunes.
    protected static String safe(String s) {
        if (s == null) return "";
        return s.replace("*","\\*")
                .replace("_","\\_")
                .replace("`","\\`")
                .replace("[", "\\[");
    }

    protected static List<String> splitPipe(String args, int expected) {
        List<String> parts = Arrays.stream(args.split("\\|"))
                .map(String::trim).collect(Collectors.toList());
        if (parts.size() < expected)
            throw new IllegalArgumentException("Se esperaban " + expected + " campos separados por `|`");
        return parts.subList(0, expected);
    }

    @Override
    public boolean showInMenu() {
        return false;
    }
}
