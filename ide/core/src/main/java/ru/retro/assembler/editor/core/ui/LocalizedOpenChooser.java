package ru.retro.assembler.editor.core.ui;

import ru.retro.assembler.editor.core.i18n.Messages;

import javax.swing.*;
import java.io.File;

/**
 * @Author: Maxim Gorin
 * Date: 25.02.2024
 */
public class LocalizedOpenChooser extends JFileChooser {
    static {
        UIManager.put("FileChooser.cancelButtonText", Messages.getInstance().get(Messages.CANCEL));
        UIManager.put("FileChooser.openButtonText", Messages.getInstance().get(Messages.OPEN2));
        UIManager.put("FileChooser.fileNameLabelText", Messages.getInstance().get(Messages.FILE_NAME));
        UIManager.put("FileChooser.filesOfTypeLabelText", Messages.getInstance().get(Messages.FILES_OF_TYPE));
        UIManager.put("FileChooser.lookInLabelText", Messages.getInstance().get(Messages.LOOK_IN));
        UIManager.put("FileChooser.listViewButtonToolTipText", Messages.getInstance().get(Messages.LIST));
        UIManager.put("FileChooser.listViewButtonAccessibleName", Messages.getInstance().get(Messages.LIST));
        UIManager.put("FileChooser.detailsViewButtonToolTipText", Messages.getInstance().get(Messages.DETAILS));
        UIManager.put("FileChooser.detailsViewButtonAccessibleName", Messages.getInstance().get(Messages.DETAILS));
        UIManager.put("FileChooser.homeFolderToolTipText", Messages.getInstance().get(Messages.HOME));
        UIManager.put("FileChooser.homeFolderAccessibleName", Messages.getInstance().get(Messages.HOME));
        UIManager.put("FileChooser.upFolderToolTipText", Messages.getInstance().get(Messages.UP_FOLDER));
        UIManager.put("FileChooser.upFolderAccessibleName", Messages.getInstance().get(Messages.UP_FOLDER));
        UIManager.put("FileChooser.newFolderToolTipText", Messages.getInstance().get(Messages.NEW_FOLDER));
        UIManager.put("FileChooser.acceptAllFileFilterText", Messages.getInstance().get(Messages.ALL_FILES));
    }

    public LocalizedOpenChooser() {
    }



    public LocalizedOpenChooser(String currentDirectoryPath) {
        super(currentDirectoryPath);
    }

    public LocalizedOpenChooser(File currentDirectory) {
        super(currentDirectory);
    }
}
