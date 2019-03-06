package com.merenaas.resolver;

import com.sun.tools.classfile.Opcode;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyViewResolver extends InternalResourceViewResolver {
    private static final String  EXTN = ".jsp";
    @Override
    protected AbstractUrlBasedView buildView(String viewName) throws Exception {
        if(checkStatus(viewName)) {
            RedirectView redirectView = new RedirectView("/error");
            redirectView.setStatusCode(HttpStatus.resolve(Integer.parseInt(viewName)));
            return redirectView;
        }
        else {
            ServletContext context = getServletContext();
            File file = new File(context.getRealPath(context.getContextPath()));
            List<File> files = searchAllFiles(new ArrayList(), file);
            String pathTo = getPath(searchView(files, viewName));
            InternalResourceView view = (InternalResourceView)super.buildView(pathTo);
            view.setPreventDispatchLoop(true);
            return view;
        }
    }

    private static List searchAllFiles(List list, File file) {
        File[] folderFiles = file.listFiles();
        for (File entry : folderFiles) {
            if (entry.isDirectory()) {
                searchAllFiles(list, entry);
            } else list.add(entry);
        }
        return list;
    }

    private static String searchView(List<File> files, String name) {
        String viewName = "";
        for (File file : files) {
            String extension;
            int i = file.getName().lastIndexOf('.');
            if (i > 0) {
                extension = file.getName().substring(i);
                if (extension.equals(EXTN)) {
                    if ((name + extension).equals(file.getName())) {
                        viewName = file.getAbsolutePath();
                    }
                }
            }
        }
        return viewName;
    }

    private static String getPath(String absolutePAth) {
        Pattern pattern = Pattern.compile(".*/WEB-INF/views/(.*)\\.jsp");
        Matcher matcher = pattern.matcher(absolutePAth);
        if(matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private static boolean checkStatus(String viewName) {
        try {
            return Arrays.stream(HttpStatus.values()).mapToInt(s-> s.value()).anyMatch(s-> s==Integer.parseInt(viewName));
        }
        catch (NumberFormatException numberExc) {

        }
        return false;

    }
}
