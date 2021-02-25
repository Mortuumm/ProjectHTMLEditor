package sample;

import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Controller extends Main{

    public HTMLEditor HtmlEditor;
    public WebView webView;
    public Label stateLabel;

    public ProgressBar progressBar;
    public TextField UrlText;

    public void HtmlAction(ActionEvent actionEvent) {
        WebEngine webEngine = webView.getEngine();
        webEngine.loadContent(HtmlEditor.getHtmlText());
    }

    public void LoadHtml(ActionEvent actionEvent) throws IOException {
    
        JFileChooser fileopen = new JFileChooser();
        int ret = fileopen.showDialog(null, "Открыть файл");
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fileopen.getSelectedFile();


            WebEngine engine = webView.getEngine();
            File file1 = new File("sample1.html");
            URL url = file.toURI().toURL();
            String url1 = "https://betacode.net/11151/javafx-webview-webengine";
            Worker<Void> worker = engine.getLoadWorker();
            worker.stateProperty().addListener(new ChangeListener<State>() {

                @Override
                public void changed(ObservableValue<? extends State> observable, State oldValue, State newValue) {
                    stateLabel.setVisible(true);
                    stateLabel.setText("Loading state: " + newValue.toString());
                    if (newValue == Worker.State.SUCCEEDED) {

                        stateLabel.setText("Finish!");
                    }
                }
            });
            progressBar.progressProperty().bind(worker.progressProperty());
            engine.load(url.toString());

        }
    }
    public void SaveHtml(ActionEvent actionEvent) throws IOException {

            JFileChooser fc = new JFileChooser();

                FileNameExtensionFilter filter = new FileNameExtensionFilter(".html", "html");
                FileNameExtensionFilter filter1 = new FileNameExtensionFilter(".txt", "txt");

                fc.addChoosableFileFilter(filter);
                fc.addChoosableFileFilter(filter1);
                fc.setAcceptAllFileFilterUsed(false);
                fc.setFileFilter(fc.getFileFilter());



        if ( fc.showSaveDialog(fc) == JFileChooser.APPROVE_OPTION ) {
            try ( FileWriter fw = new FileWriter(fc.getSelectedFile() +fc.getFileFilter().getDescription()) ) {
                fw.write(HtmlEditor.getHtmlText());

            }
            catch ( IOException e ) {
                System.out.println("Всё погибло!");
            }

        }

        /*
        FileWriter fw = new FileWriter( "sample1.html" );
        fw.write(HtmlEditor.getHtmlText());
        fw.flush();
        fw.close();

         */
    }

    public void loadHTMLUrl(ActionEvent actionEvent) {
        WebEngine engine = webView.getEngine();
        String url1 = UrlText.getText();
        Worker<Void> worker = engine.getLoadWorker();
        worker.stateProperty().addListener(new ChangeListener<State>() {

            @Override
            public void changed(ObservableValue<? extends State> observable, State oldValue, State newValue) {
                stateLabel.setVisible(true);
                stateLabel.setText("Loading state: " + newValue.toString());
                if (newValue == State.SUCCEEDED) {

                    stateLabel.setText("Finish!");
                }
            }
        });
        progressBar.progressProperty().bind(worker.progressProperty());
        engine.load(url1);

    }

}
