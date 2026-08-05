package org.mydevnotes.mst.web;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.awt.Desktop;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.Executors;

public class StaticFileHttpServer implements AutoCloseable {

    private final HttpServer server;
    private final Path root;

    public Path getRoot() {
        return root;
    }

    private static final Map<String, String> MIME_TYPES = Map.ofEntries(
            Map.entry("html", "text/html; charset=UTF-8"),
            Map.entry("htm", "text/html; charset=UTF-8"),
            Map.entry("css", "text/css; charset=UTF-8"),
            Map.entry("js", "application/javascript; charset=UTF-8"),
            Map.entry("json", "application/json; charset=UTF-8"),
            Map.entry("txt", "text/plain; charset=UTF-8"),
            Map.entry("svg", "image/svg+xml"),
            Map.entry("png", "image/png"),
            Map.entry("jpg", "image/jpeg"),
            Map.entry("jpeg", "image/jpeg"),
            Map.entry("gif", "image/gif"),
            Map.entry("ico", "image/x-icon"),
            Map.entry("webp", "image/webp"),
            Map.entry("woff", "font/woff"),
            Map.entry("woff2", "font/woff2"),
            Map.entry("ttf", "font/ttf"),
            Map.entry("otf", "font/otf"),
            Map.entry("map", "application/json")
    );

    /**
     * @param rootDirectory directory to serve
     * @param port 0 = random free port
     */
    public StaticFileHttpServer(Path rootDirectory, int port) throws IOException {
        this.root = rootDirectory.toRealPath();

        server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new StaticHandler());
        server.setExecutor(Executors.newCachedThreadPool());
    }

    public void start() {
        server.start();
    }

    @Override
    public void close() {
        server.stop(0);
    }

    public int getPort() {
        return server.getAddress().getPort();
    }

    public URI getBaseUri() {
        return URI.create("http://localhost:" + getPort() + "/");
    }

    public URI getUri(String relativePath) {

        System.out.println("requested " + relativePath);

        if (relativePath.startsWith("/")) {
            relativePath = relativePath.substring(1);
        }
        return URI.create(getBaseUri() + relativePath);
    }

    private class StaticHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            String method = exchange.getRequestMethod();

            if (!method.equals("GET") && !method.equals("HEAD")) {
                exchange.sendResponseHeaders(405, -1);
                exchange.close();
                return;
            }

            String requestPath = exchange.getRequestURI().getPath();

            if (requestPath.equals("/")) {
                requestPath = "/index.html";
            }

            if (requestPath.equals("/api/businessentities")) {
                Path file = root.resolve("timeline/businessEntity.json");

                byte[] content = Files.readAllBytes(file);

                exchange.getResponseHeaders()
                        .set("Content-Type", "application/json; charset=UTF-8");

                exchange.sendResponseHeaders(200, content.length);

                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(content);
                }

                return;
            }

            Path file = root.resolve(requestPath.substring(1)).normalize();

            // Prevent ../ attacks
            if (!file.startsWith(root)) {
                send404(exchange);
                return;
            }

            if (!Files.exists(file) || Files.isDirectory(file)) {
                send404(exchange);
                return;
            }

            Headers headers = exchange.getResponseHeaders();
            headers.set("Content-Type", detectMime(file));
            headers.set("Cache-Control", "no-cache");

            long length = Files.size(file);

            if (method.equals("HEAD")) {
                exchange.sendResponseHeaders(200, length);
                exchange.close();
                return;
            }

            exchange.sendResponseHeaders(200, length);

            try (InputStream in = Files.newInputStream(file); OutputStream out = exchange.getResponseBody()) {

                in.transferTo(out);
            }

            exchange.close();
        }

    }

    private static void send404(HttpExchange exchange) throws IOException {
        byte[] bytes = "404 Not Found".getBytes();

        exchange.getResponseHeaders().set("Content-Type", "text/plain");
        exchange.sendResponseHeaders(404, bytes.length);

        try (OutputStream out = exchange.getResponseBody()) {
            out.write(bytes);
        }

        exchange.close();
    }

    private static String detectMime(Path file) throws IOException {

        String mime = Files.probeContentType(file);

        if (mime != null) {
            return mime;
        }

        String name = file.getFileName().toString();

        int i = name.lastIndexOf('.');

        if (i >= 0) {
            String ext = name.substring(i + 1).toLowerCase();
            mime = MIME_TYPES.get(ext);
            if (mime != null) {
                return mime;
            }
        }

        return "application/octet-stream";
    }

    public void openBrowser(String path) throws IOException {

        if (!Desktop.isDesktopSupported()) {
            return;
        }

        Desktop.getDesktop().browse(getBaseUri().resolve(path));
    }

}
