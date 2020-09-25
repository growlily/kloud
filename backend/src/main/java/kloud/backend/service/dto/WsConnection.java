package kloud.backend.service.dto;

import io.kubernetes.client.Exec;
import io.kubernetes.client.openapi.ApiException;
import kloud.backend.util.UserNSUtil;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class WsConnection implements Runnable {

    private OutputStream outputStream;
    private final Exec exec;
    private final WebSocketSession session;

    private final String podName;
    private final String namespace;

    private final ConsoleSize consoleSize;
    private Boolean tryBash = false;

    public WsConnection(Map<String, String> params, WebSocketSession session) {
        this.session = session;
        exec = new Exec();
        namespace = UserNSUtil.toNS(params.get("id"));
        podName = params.get("podName");
        consoleSize = new ConsoleSize(params.get("cols"), params.get("rows"));
    }

    @Override
    public void run() {
        List<String> cmds = Arrays.asList("/bin/bash", "/bin/ash", "/bin/sh");
        cmds.forEach((s) -> {
            if (!tryBash) {
                startProcess(s);
            }
        });

    }

    private void startProcess(String shellPath) {
        boolean initValid = true;
        try {
            Process proc = exec.exec(namespace, podName, new String[]{shellPath}, null, true, true);
            outputStream = proc.getOutputStream();
            InputStream inputStream = proc.getInputStream();
            String width = Optional.ofNullable(consoleSize.getCols()).map(s -> "COLUMNS=" + s).orElse("");
            String height = Optional.ofNullable(consoleSize.getRows()).map(s -> "LINES=" + s).orElse("");
            String export = "";
            if (!"".equals(width + height)) {
                export = "export " + width + height + ";";
            }
            String cmdArgs = export + shellPath + "\nclear\n";
            outputStream.write(cmdArgs.getBytes());
            try {
                while (true) {
                    byte[] data = new byte[1024];
                    if (inputStream.read(data) != -1) {
                        TextMessage textMessage = new TextMessage(data);
                        if (initValid && isValidBash(textMessage, shellPath)) {
                            break;
                        } else {
                            tryBash = true;
                            initValid = false;
                        }
                        session.sendMessage(textMessage);
                    }
                }
            } catch (IOException e) {
                System.out.println("Pipe closed");
            } finally {
                proc.destroy();
                System.out.println("session closed... exit thread");
            }


        } catch (ApiException | IOException e) {
            e.printStackTrace();
            try {
                System.out.println("ApiException or IOException... close session");
                session.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }


    }

    //验证shell
    private boolean isValidBash(TextMessage textMessage, String shellPath) {
        String failMessage = "OCI runtime exec failed";
        return textMessage.getPayload().trim().contains(failMessage);
    }


    //退出 Process
    public void exit() {
        try {
            outputStream.write("exit\nexit\n".getBytes());
        } catch (IOException ignored) {
        }
/*        proc.destroyForcibly();
        try {
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

}
