package ru.yole.conflued.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import ru.yole.conflued.model.ConfServer;

import javax.swing.*;

/**
 * @author yole
 */
public class ConfigureServerDialog extends DialogWrapper {
    private JPanel myRootPanel;
    private JTextField myServerURLTextField;
    private JTextField myUsernameTextField;
    private JPasswordField myPasswordPasswordField;

    public ConfigureServerDialog(Project project) {
        super(project);
        init();
    }

    @Override
    protected JComponent createCenterPanel() {
        return myRootPanel;
    }

    public void updateFromServer(ConfServer server) {
        myServerURLTextField.setText(server.getURL());
        myUsernameTextField.setText(server.getUserName());
        myPasswordPasswordField.setText(server.getPassword());
    }

    public void updateServer(ConfServer server) {
        server.setURL(myServerURLTextField.getText());
        server.setUserName(myUsernameTextField.getText());
        server.setPassword(myPasswordPasswordField.getText());
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return myServerURLTextField;
    }
}
