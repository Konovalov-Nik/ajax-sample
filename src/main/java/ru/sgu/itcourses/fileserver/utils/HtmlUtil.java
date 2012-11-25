package ru.sgu.itcourses.fileserver.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Nikita Konovalov
 */
public class HtmlUtil {
    private static final String head = "<html> <head> <title>File Server</title> </head> <body>";
    private static final String tail = "</body></html>";

    public static String getHtmlByPath(File path, String login) {
        if (!path.exists() || !path.canWrite()) {
            return null;
        }

        StringBuilder html = new StringBuilder();
        html.append(head);
        List<File> directories = getDirectories(path);
        html.append("<p>You have logged in as ");
        html.append(login);
        html.append(".&nbsp");
        html.append("<a href='/logout'>Logout</a>");
        html.append("</p>");
        html.append("<ul>");
        //parent
        html.append("<li>");
        File parentFile = path.getParentFile();
        if (parentFile != null) {
            html.append("<a href='/dir?path=").append(parentFile.getAbsolutePath()).append("'>");
            html.append("..");
            html.append("</a>");
            html.append("</li>");
        }
        for (File directory : directories) {
            html.append("<li>");
            html.append("<a href='/dir?path=").append(directory.getAbsolutePath()).append("'>");
            html.append(directory.getName());
            html.append("</a>");
            html.append("</li>");
        }

        List<File> files = getFiles(path);
        for (File file : files) {
            html.append("<li>");
            html.append(file.getName());
            html.append("</li>");
        }

        html.append("</ul>");
        html.append(tail);
        return html.toString();
    }

    private static List<File> getDirectories(File path) {
        List<File> dirList = Arrays.asList(path.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return new File(dir, name).isDirectory();
            }
        }));
        Collections.sort(dirList, FILE_COMPARATOR);
        return dirList;
    }

    private static List<File> getFiles(File path) {
        List<File> fileList = Arrays.asList(path.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return new File(dir, name).isFile();
            }
        }));
        Collections.sort(fileList, FILE_COMPARATOR);
        return fileList;
    }

    public static final Comparator<File> FILE_COMPARATOR = new Comparator<File>() {
        @Override
        public int compare(File o1, File o2) {
            return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
        }
    };
}
