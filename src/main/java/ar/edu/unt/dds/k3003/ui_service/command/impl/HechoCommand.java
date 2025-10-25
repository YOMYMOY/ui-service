package ar.edu.unt.dds.k3003.ui_service.command.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import ar.edu.unt.dds.k3003.ui_service.bot.TelegramBot;
import ar.edu.unt.dds.k3003.ui_service.command.AbstractCommand;
import ar.edu.unt.dds.k3003.ui_service.dto.HechoResponse;
import ar.edu.unt.dds.k3003.ui_service.dto.PdiResponse;
import ar.edu.unt.dds.k3003.ui_service.service.FuentesMicroservicioService;
import ar.edu.unt.dds.k3003.ui_service.service.ProcesadorPdiMicroservicioService;

@Component
public class HechoCommand extends AbstractCommand {
    private final FuentesMicroservicioService fuentesService;
    private final ProcesadorPdiMicroservicioService pdiService; //Nuevo

    @Autowired
    public HechoCommand(FuentesMicroservicioService fuentesService,
                        ProcesadorPdiMicroservicioService pdiService) {
        this.fuentesService = fuentesService;
        this.pdiService = pdiService;
    }

    @Override
    public String getCommandName() {
        return "/hecho";
    }

    @Override
    public String getHelpText() {
        return "<id>";
    }

    @Override
    public void execute(Update update, String rawArgs, TelegramBot bot) {
        long chatId = update.getMessage().getChatId();

        if (rawArgs == null || rawArgs.isBlank()) {
            throw new IllegalArgumentException("Falta `<id>` del hecho.");
        }

        String hechoId = rawArgs.trim();

        HechoResponse hecho = fuentesService.obtenerDetalleHecho(hechoId);

        if (hecho == null) {
            bot.reply(chatId, "🔎 No encontré el hecho `" + hechoId + "`.");
            return;
        }

        List<PdiResponse> pdis = pdiService.obtenerPdisPorHecho(hechoId);
        //Nuevo para manejo de  procesado de PDI
        StringBuilder respuesta = new StringBuilder();
        respuesta.append("📰 *").append(safe(hecho.getTitulo())).append("*\n");
        respuesta.append("Estado: ").append(estadoEmoji(nullTo(hecho.getEstado(), "activo")))
                 .append(" `").append(hecho.getEstado()).append("`\n");
        respuesta.append("Colección: ").append(safe(nullTo(hecho.getNombreColeccion(), "-"))).append("\n\n");
        


        //Lo cambie todo para mostrar bien los PDIs
        if (pdis != null && !pdis.isEmpty()) {
            respuesta.append("🖼️ *PDIs asociados* (").append(pdis.size()).append("):\n\n");

            for (PdiResponse pdi : pdis.stream().limit(5).collect(Collectors.toList())) {
                respuesta.append("━━━━━━━━━━━━━━━\n");
                respuesta.append("📌 *PDI `").append(pdi.getId()).append("`*\n\n");

                // Etiquetas
                if (pdi.getEtiquetas() != null && !pdi.getEtiquetas().isEmpty()) {
                    String tags = pdi.getEtiquetas().stream()
                        .limit(5)
                        .collect(Collectors.joining(", "));
                    respuesta.append("🏷️ *Etiquetas:* _").append(tags).append("_\n");
                    
                    if (pdi.getEtiquetas().size() > 5) {
                        respuesta.append("   ... y ").append(pdi.getEtiquetas().size() - 5).append(" más\n");
                    }
                }

                // OCR (mostrar resumen si hay texto)
                if (pdi.getOcrResultado() != null && !pdi.getOcrResultado().contains("error")) {
                    String textoOcr = extraerTextoOCR(pdi.getOcrResultado());
                    if (textoOcr != null && !textoOcr.isBlank()) {
                        respuesta.append("📝 *OCR:* _").append(safe(truncar(textoOcr, 100))).append("_\n");
                    }
                }

                // Estado de procesamiento
                if (pdi.getProcesado() != null && pdi.getProcesado()) {
                    respuesta.append("✅ Procesado\n");
                }

                // Enviar imagen si existe
                if (pdi.getImagenUrl() != null && looksLikeUrl(pdi.getImagenUrl())) {
                    try {
                        bot.sendPhoto(chatId, pdi.getImagenUrl(), "PDI " + pdi.getId());
                    } catch (Exception e) {
                        respuesta.append("⚠️ Error al cargar imagen\n");
                    }
                }

                respuesta.append("\n");
            }

            if (pdis.size() > 5) {
                respuesta.append("\n_... y ").append(pdis.size() - 5).append(" PDIs más_");
            }
        } else {
            respuesta.append("_Este hecho no tiene PDIs asociados._");
        }

        bot.reply(chatId, respuesta.toString());
        }

        private String extraerTextoOCR(String ocrJson) {
        try {
            if (ocrJson.contains("ParsedText")) {
                int start = ocrJson.indexOf("\"ParsedText\":\"") + 14;
                int end = ocrJson.indexOf("\"", start);
                if (start > 14 && end > start) {
                    String texto = ocrJson.substring(start, end);  
                    return texto.replace("\\r", " ").replace("\\n", " ").replace("\\t", " ").trim();
                }
            }
        } catch (Exception e) {
            System.err.println("Error extrayendo texto OCR: " + e.getMessage());
        }
        return null;
    }

    
     // Trunca un texto con una max de caracteres 
     
    private String truncar(String texto, int maxLen) {
        if (texto == null) return "";
        if (texto.length() <= maxLen) return texto;
        return texto.substring(0, maxLen) + "...";
    }

}

