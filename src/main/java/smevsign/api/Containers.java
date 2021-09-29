package smevsign.api;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import smevsign.support.ContainerConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "containersServlet", urlPatterns = {"/api/containers"})
public class Containers extends HttpServlet {
    private final ConfigManager config = ConfigManager.getInstance();

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        List<String> list = new ArrayList<String>();
        int index = 0;
        for (ContainerConfig container : config.getJsonConfigContainers()) {
            list.add(String.format("{ \"key\": \"%d\", \"value\": \"%s\" }", index, container.alias));
            index += 1;
        }
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, Content-Type");
        res.setHeader("Access-Control-Max-Age", "86400");
        res.setContentType("application/json; charset=UTF-8");
        res.setCharacterEncoding("UTF-8");
        res.setStatus(200);
        if (list.size() > 0) {
            res.getWriter().print(String.format("[ %s ]", String.join(", ", list)));
        } else {
            res.getWriter().print("[]");
        }
    }
}
