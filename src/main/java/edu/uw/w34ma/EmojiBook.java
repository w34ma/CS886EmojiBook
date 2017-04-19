package edu.uw.w34ma;

import culture.Identities;
import edu.uw.w34ma.models.BookCharacter;
import edu.uw.w34ma.models.EmotionResult;
import edu.uw.w34ma.models.ProcessResult;
import edu.uw.w34ma.models.TableEntry;
import edu.uw.w34ma.processing.Processor;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Weicong Ma
 *         Created by Weicong Ma on 29/03/17.
 */
public class EmojiBook extends Application {
    private final FileChooser bookSelector = new FileChooser();
    private File selectedBook = null;
    private final Processor processor = new Processor();

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        // top
        HBox top = new HBox(10);
        top.setAlignment(Pos.CENTER_LEFT);
        top.setPadding(new Insets(10, 10, 10, 10));

        Button btnSelectFile = new Button();
        btnSelectFile.setText("Select Book");
        Label lblSelectedFile = new Label();
        lblSelectedFile.setText("Selected Book: ");
        TextField txtSelectedFile = new TextField();
        txtSelectedFile.setPrefWidth(400);
        txtSelectedFile.setEditable(false);

        btnSelectFile.setOnAction(event -> {
            selectedBook = bookSelector.showOpenDialog(primaryStage);
            if (selectedBook != null) {
                txtSelectedFile.setText(selectedBook.getAbsolutePath());
            }
        });

        top.getChildren().addAll(btnSelectFile, lblSelectedFile, txtSelectedFile);
        root.setTop(top);

        // center

        HBox centerColumn = new HBox();
        centerColumn.setPadding(new Insets(5, 5, 5, 5));


        TableView<TableEntry> table = new TableView<>();
        table.setPadding(new Insets(5, 5, 5, 5));
        table.setMaxWidth(Double.MAX_VALUE);
        table.setMaxHeight(Double.MAX_VALUE);
        table.setEditable(false);

        TableColumn<TableEntry, Integer> numberColumn = new TableColumn<>("#");
        numberColumn.setCellValueFactory(e ->
            new ReadOnlyObjectWrapper<>(table.getItems().indexOf(e.getValue()) + 1)
        );
        numberColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.03));
        numberColumn.setSortable(false);

        TableColumn<TableEntry, String> sentenceColumn = new TableColumn<>("Sentence");
        sentenceColumn.setCellValueFactory(new PropertyValueFactory<TableEntry, String>("sentence"));
        sentenceColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.45));
        sentenceColumn.setSortable(false);

        TableColumn<TableEntry, String> eventColumn = new TableColumn<>("Event");
        eventColumn.setCellValueFactory(new PropertyValueFactory<TableEntry, String>("event"));
        eventColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.24));
        eventColumn.setSortable(false);

        TableColumn<TableEntry, String> emotionColumn = new TableColumn<>("Emotion");
        emotionColumn.setCellValueFactory(new PropertyValueFactory<TableEntry, String>("emotion"));
        emotionColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.28));
        emotionColumn.setSortable(false);

        //noinspection unchecked
        table.getColumns().addAll(numberColumn, sentenceColumn, eventColumn, emotionColumn);

        root.setCenter(table);


        // left
        VBox left = new VBox(10);
        left.setPadding(new Insets(10, 10, 10, 10));

        Label lblCharacters = new Label();
        lblCharacters.setText("Characters");

        ListView<BookCharacter> characterList = new ListView<>();
        ObservableList<BookCharacter> names = FXCollections.observableArrayList(new ArrayList<BookCharacter>());
        characterList.setItems(FXCollections.observableList(names));

        Label lblName = new Label();
        lblName.setText("Name");
        TextField txtName = new TextField();

        Label lblIdentity = new Label();
        lblIdentity.setText("Identity");
        ComboBox<String> comboIdentities = new ComboBox<>();
        comboIdentities.setMaxWidth(Double.MAX_VALUE);
        String[] identityData = (String[]) new Identities().getContents()[1][1];
        List<String> identityNames = Arrays.stream(identityData).filter(s -> !s.matches("[0-9\\.-]+"))
                .collect(Collectors.toList());
        comboIdentities.getItems().addAll(identityNames);
        comboIdentities.setValue(identityNames.get(0));

        Label lblGender = new Label();
        lblGender.setText("Gender");
        ComboBox<String> comboGenders = new ComboBox<>();
        comboGenders.setMaxWidth(Double.MAX_VALUE);
        comboGenders.getItems().addAll("MALE", "FEMALE");
        comboGenders.setValue("MALE");

        Button btnAddCharacter = new Button();
        btnAddCharacter.setMaxWidth(Double.MAX_VALUE);
        btnAddCharacter.setText("Add Character");
        btnAddCharacter.setOnAction(event -> {
            // validate
            String name = txtName.getText().trim();
            String identity = comboIdentities.getValue();
            String gender = comboGenders.getValue();
            if (name.length() == 0) {
                error("Name cannot be empty");
                return;
            }

            BookCharacter character = new BookCharacter(name, identity, BookCharacter.Gender.valueOf(gender));
            characterList.getItems().add(character);
        });

        Button btnDeleteSelected = new Button();
        btnDeleteSelected.setMaxWidth(Double.MAX_VALUE);
        btnDeleteSelected.setText("Delete Selected");
        btnDeleteSelected.setOnAction(event -> {
            characterList.getItems().remove(characterList.getSelectionModel().getSelectedIndex());
        });

        Button btnDeleteAll = new Button();
        btnDeleteAll.setMaxWidth(Double.MAX_VALUE);
        btnDeleteAll.setText("Delete All");
        btnDeleteAll.setOnAction(event -> {
            characterList.getItems().clear();
        });

        Button btnProcess = new Button();
        btnProcess.setMaxWidth(Double.MAX_VALUE);
        btnProcess.setText("Process");
        btnProcess.setOnAction(event -> {
            ProcessResult data;
            try {
                data = processor.process(selectedBook, characterList.getItems());
                output(data);
            } catch (Exception e) {
                error("Unable to process book: " + e.getMessage());
                e.printStackTrace();
                return;
            }
            table.setItems(FXCollections.observableList(data.entries));
            displayBook(data.entries, data.emojis);
        });

        left.getChildren().addAll(lblCharacters, characterList, lblName, txtName, lblIdentity, comboIdentities,
                lblGender, comboGenders, btnAddCharacter, btnDeleteSelected, btnDeleteAll, btnProcess);
        root.setLeft(left);




        Scene scene = new Scene(root, 1600, 800);
        primaryStage.setTitle("EmojiBook!");
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    private void output(ProcessResult result) {
        StringBuilder csv = new StringBuilder();
        csv.append("sentence | event | emotion\n");

        for (TableEntry entry : result.entries) {
            csv.append(entry.getSentence()).append(" | ");
            csv.append(entry.getEvent()).append(" | ");
            csv.append(entry.getEmotion()).append("\n");
        }

        try {
            Files.write(Paths.get("output.csv"), csv.toString().getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to write csv", e);
        }
    }

    private void displayBook(List<TableEntry> entries, List<Map<String, String>> emojis) {
        StringBuilder html = new StringBuilder().append("<html><body>");
        html.append("<style>");
        html.append("span {line-height: 50px;}");
        html.append("div {}");
        html.append("img {width: 30px; height: 30px; position: relative; top: 8px;}");

        html.append("</style>");

        for (int i = 0; i < entries.size(); i++) {
            String sentence = entries.get(i).getSentence().replace("\"", "&quot;");
            Map<String, String> emoji = emojis.get(i);
            Set<String> used = new HashSet<>();

            html.append("<div>");
            String[] tokens = sentence.split(" ");
            for (String token : tokens) {
                html.append("<span>").append(token).append("</span> ");
                for (String name : emoji.keySet()) {
                    if (token.toLowerCase().startsWith(name.toLowerCase()) && !used.contains(name)) {
                        html.append("&nbsp;&nbsp;<img src='emojis/").append(emoji.get(name)).append(".png' />&nbsp;&nbsp; ");
                        used.add(name);
                    }
                }
            }

            if (used.size() < emoji.size()) {
                html.append("&nbsp;&nbsp;&nbsp;[");
                for (String name : emoji.keySet()) {
                    if (!used.contains(name)) {
                        html.append(name).append(" ")
                                .append("&nbsp;&nbsp;<img src='emojis/").append(emoji.get(name)).append(".png'/>&nbsp;&nbsp; ");
                    }
                }
                html.append("]");
            }
            html.append("</div>");
        }

        html.append("</body></html>");

        Path htmlPath = Paths.get("book.html");

        try {
            Files.write(htmlPath, html.toString().getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Unable to write html", e);
        }

        WebView browser = new WebView();
        WebEngine webEngine = browser.getEngine();
        webEngine.load("file:///" + htmlPath.toAbsolutePath().toString());
        Stage stage = new Stage();
        Scene scene = new Scene(browser, 1000, 800);
        stage.setTitle("Generated Book");
        stage.setScene(scene);
        stage.show();
    }

    private String getLineNumber(int number) {
        StringBuilder sb = new StringBuilder();
        String lineNumber = String.valueOf(number);
        for (int i = 0; i < 3 - lineNumber.length(); i++) {
            sb.append("0");
        }
        sb.append(lineNumber).append(" ");
        return sb.toString();
    }



    private void error(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(msg);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
