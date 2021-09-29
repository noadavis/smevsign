package smevsign.api;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "reloadconfigServlet", urlPatterns = {"/api/reloadconfig"})
public class ReloadConfig extends HttpServlet {
    private final ConfigManager config = ConfigManager.getInstance();

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        config.updateConfig();
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, Content-Type");
        res.setHeader("Access-Control-Max-Age", "86400");
        res.setContentType("application/json; charset=UTF-8");
        res.setCharacterEncoding("UTF-8");
        res.setStatus(200);
        res.getWriter().print("{ \"result\": \"ok\" }");
    }
}