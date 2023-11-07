package model;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class Toolkit {

    public static void copy(String copyText) {
        Clipboard clipboard = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection selection = new StringSelection(copyText);
        clipboard.setContents(selection, null);

    }
}
