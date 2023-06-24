import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.collections.*;
import javafx.scene.*;
import javafx.util.Duration;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class AppFrame extends Application{
    public static String username = "", ip = "";
    public static Integer port = 6666;
    public static ShatterSender cons = null;
    public static ArrayList<String> running = new ArrayList<>();
    public static Localit loc = null;
    public static String lang = "en";
    public static TextField status = null;
    public static int ping_cycle(ShatterSender rec, String ip, int port, BufferedReader reader){
        String inp = "y";
        int fail = 0;

        while (true) {
            try {System.out.printf("No connection. Try and restore? (Y/N)\n"); inp = reader.readLine();}
            catch (Exception e){System.out.printf("Can't read the user reply.\n");}
            if (inp.contentEquals("y")) {
                try {rec.stopConnection();} catch (Exception e2) {}
                try {rec.startConnection(ip, port);}catch (Exception e){
                    fail = 1;
                }
                String pong = rec.speak("ping","");
                if (pong.contentEquals("pong\n")) {fail = 0;break;}
            } else {fail = 1;break;}
            if (fail == 0){break;}
        }
        return fail;
    }
    public static String fileread(ShatterSender rec, String name) throws IOException { //reads the commands form the specified file, can call itself if files reference other files
        boolean ex = false;
        int addup = 0;
        String inp = "";
        BufferedReader re = null;
        try {
            re = new BufferedReader(new InputStreamReader(new FileInputStream(name)));
            if(running.indexOf(name) != -1) {return "Script " + name + " is already being executed.\n";}
            else running.add(name);
        }
        catch (Exception e){
            return "The file " + name + " doesn't exist.\n";
        }
        try {
            inp = re.readLine();
            while (inp != null) {
                if (inp.contentEquals("add") || inp.contains("update")) addup = 1;
                String outp = "";
                if (addup < 2) {
                    try {
                        rec.startConnection(ip, port);
                    } catch (Exception e) {
                        int pong = ping_cycle(rec, ip, port, re);
                        if (pong == 1) break;
                        else continue;
                    }
                    if (addup == 1)
                        addup = 2;
                }
                outp = rec.speak(inp, name);
                if (outp.contentEquals("Element received.\n")) {addup = 0; continue;}
                //else
                //if (addup==2 && !outp.contentEquals("")) {addup = 0; continue;}
                if (outp.contentEquals("NOCON")){
                    int pong = ping_cycle(rec, ip, port, re);
                    if (pong == 1) break;
                    else continue;
                }
                if (inp.contains("execute_script")) {
                    if (!outp.contentEquals("-"))
                        outp = fileread(rec, outp);
                    else
                        outp = "No file name was provided.\n";
                }
                if (inp.contentEquals("exit")){System.out.printf("Exiting...\n"); rec.stopConnection(); return "exit\n";}
                else if (outp.contentEquals("ERRPARSE")) System.out.printf("The script " + name + " contains errors.\n");
                else System.out.printf(outp);
                //System.out.printf("file addup: " + String.valueOf(addup)+'\n');
                inp = re.readLine();
            }
            re.close();
        }
        catch (Exception e) {
            ex = true;
            return "The file " + name + " is not accessible.\n";
        }
        if (!ex) {
            running.remove(name);
            return "Script " + name + " executed successfully!\n";
        }
        else {return "Couldn't execute script " + name + "...\n";}
    }

    public Person spliter(String data){
        String[] split = data.split("\\s+");
        return new Person(
                Integer.parseInt(split[0]),
                split[1],
                Double.parseDouble(split[2]),
                Integer.parseInt(split[3]),
                split[4],
                split[5],
                Double.parseDouble(split[6]),
                Double.parseDouble(split[7]),
                Long.parseLong(split[8]),
                Long.parseLong(split[9]),
                split[10],split[11],split[12],split[13],split[14]
                );
    }
    public static void add(boolean addupd, Person p) throws Exception{
        Stage AddStage = new Stage();
        Label idlabel = new Label(loc.l("ID:"));
        idlabel.setPrefWidth(80);
        TextField idtxtbox = new TextField();
        idtxtbox.setPrefWidth(80);
        if (addupd == true) idtxtbox.setDisable(true);

        Label namelabel = new Label(loc.l("Name:"));
        namelabel.setPrefWidth(80);
        TextField nametxtbox = new TextField();
        idtxtbox.setPrefWidth(80);

        Label cxlabel = new Label(loc.l("X:"));
        cxlabel.setPrefWidth(80);
        TextField cxtxtbox = new TextField();
        cxtxtbox.setPrefWidth(80);

        Label cylabel = new Label(loc.l("Y:"));
        cylabel.setPrefWidth(80);
        TextField cytxtbox = new TextField();
        cytxtbox.setPrefWidth(80);

        Label hilabel = new Label(loc.l("Height:"));
        idlabel.setPrefWidth(80);
        TextField hitxtbox = new TextField();
        idtxtbox.setPrefWidth(80);

        Label lxlabel = new Label(loc.l("LocationX:"));
        lxlabel.setPrefWidth(80);
        TextField lxtxtbox = new TextField();
        lxtxtbox.setPrefWidth(80);
        Label lylabel = new Label(loc.l("LocationY:"));
        lylabel.setPrefWidth(80);
        TextField lytxtbox = new TextField();
        lytxtbox.setPrefWidth(80);
        Label lzlabel = new Label(loc.l("LocationZ:"));
        lzlabel.setPrefWidth(80);
        TextField lztxtbox = new TextField();
        lztxtbox.setPrefWidth(80);

        Label datelabel = new Label(loc.l("Date:"));
        datelabel.setPrefWidth(80);
        DatePicker datepick = new DatePicker();
        datepick.setPrefWidth(80);

        Label timelabel = new Label(loc.l("Time:"));
        timelabel.setPrefWidth(80);
        TextField timetxtbox = new TextField();
        timetxtbox.setPrefWidth(80);

        Label collabel = new Label(loc.l("Color:"));
        collabel.setPrefWidth(80);
        String colors[] = {"Blue", "Red", "Yellow", "Green", "Brown"};
        ComboBox clrbox = new ComboBox(FXCollections.observableArrayList(colors));
        clrbox.setPrefWidth(80);

        Label passlabel = new Label(loc.l("PassportID:"));
        passlabel.setPrefWidth(80);
        TextField passtxtbox = new TextField();
        passtxtbox.setPrefWidth(80);

        if (p != null){
            idtxtbox.setText(""+p.getId());
            nametxtbox.setText(""+p.getName());
            cxtxtbox.setText(""+p.getCx());
            cytxtbox.setText(""+p.getCy());
            hitxtbox.setText(""+p.getHei());
            lxtxtbox.setText(""+p.getLx());
            lytxtbox.setText(""+p.getLy());
            lztxtbox.setText(""+p.getLz());
            datepick.setValue(LocalDate.parse(p.getDate()));
            timetxtbox.setText(""+p.getTime());
            clrbox.getSelectionModel().select(p.getCol());
            passtxtbox.setText(""+p.getPass());
        }

        Button inpbutton = new Button();
        if (addupd) inpbutton.setText(loc.l("Add!")); else inpbutton.setText(loc.l("Update!"));
        inpbutton.setPrefWidth(80);
        Label inplabel = new Label(loc.l("<responsehere>"));
        inplabel.setPrefWidth(512);

        GridPane inputpanel = new GridPane();
        inputpanel.add(idlabel,0,0);
        inputpanel.add(idtxtbox,1,0);
        inputpanel.add(namelabel,0,1);
        inputpanel.add(nametxtbox,1,1);
        inputpanel.add(cxlabel,0,2);
        inputpanel.add(cxtxtbox,1,2);
        inputpanel.add(cylabel,0,3);
        inputpanel.add(cytxtbox,1,3);
        inputpanel.add(hilabel,0,4);
        inputpanel.add(hitxtbox,1,4);
        inputpanel.add(lxlabel,0,5);
        inputpanel.add(lxtxtbox,1,5);
        inputpanel.add(lylabel,0,6);
        inputpanel.add(lytxtbox,1,6);
        inputpanel.add(lzlabel,0,7);
        inputpanel.add(lztxtbox,1,7);
        inputpanel.add(datelabel,0,8);
        inputpanel.add(datepick,1,8);
        inputpanel.add(timelabel,0,9);
        inputpanel.add(timetxtbox,1,9);
        inputpanel.add(collabel,0,10);
        inputpanel.add(clrbox,1,10);
        inputpanel.add(passlabel,0,11);
        inputpanel.add(passtxtbox,1,11);

        for (int i=0;i<22;i++)
        GridPane.setHgrow(inputpanel.getChildren().get(i),Priority.ALWAYS);
        inpbutton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent e) {
                try {cons.startConnection(ip, port);}
                catch (Exception ce){inplabel.setText(loc.l("Noconnection:(")); return;}
                System.out.printf("add" + ' ' + " -1 " + nametxtbox.getText() + ' ' + cxtxtbox.getText() + ' ' + cytxtbox.getText() + ' ' + hitxtbox.getText() + ' ' + lxtxtbox.getText() + ' ' + lytxtbox.getText() + ' ' + lztxtbox.getText() + ' ' + datepick.getValue() + ' ' + timetxtbox.getText() + ' ' + clrbox.getValue() + ' ' + passtxtbox.getText());
                String tr;
                if (addupd)
                    tr = cons.speak("addfast -1" + ' ' + nametxtbox.getText() + ' ' + cxtxtbox.getText() + ' ' + cytxtbox.getText() + ' ' + hitxtbox.getText() + ' ' + lxtxtbox.getText() + ' ' + lytxtbox.getText() + ' ' + lztxtbox.getText() + ' ' + datepick.getValue() + ' ' + timetxtbox.getText() + ' ' + clrbox.getValue() + ' ' + passtxtbox.getText(), "");
                else
                    tr = cons.speak("updatefast " + idtxtbox.getText() +  ' ' + nametxtbox.getText() + ' ' + cxtxtbox.getText() + ' ' + cytxtbox.getText() + ' ' + hitxtbox.getText() + ' ' + lxtxtbox.getText() + ' ' + lytxtbox.getText() + ' ' + lztxtbox.getText() + ' ' + datepick.getValue() + ' ' + timetxtbox.getText() + ' ' + clrbox.getValue() + ' ' + passtxtbox.getText(), "");
                inplabel.setText(tr);
                if (tr.contains("added")){AddStage.close();}
            }
        });
        VBox lroot = new VBox(10, inputpanel, inpbutton, inplabel);
        lroot.setAlignment(Pos.TOP_CENTER);
        lroot.setPrefWidth(512);
        lroot.setMaxWidth(Double.MAX_VALUE);lroot.setMaxHeight(Double.MAX_VALUE);
        Scene lscene = new Scene(lroot);
        AddStage.setScene(lscene);
        AddStage.show();
        return;
    }
    public static void login() throws Exception{
        Stage loginStage = new Stage();
        Label loglabel = new Label(loc.l("Login:"));
        loglabel.setPrefWidth(80);
        TextField logtxtbox = new TextField();
        logtxtbox.setPrefWidth(80);
        Label passlabel = new Label(loc.l("Password:"));
        passlabel.setPrefWidth(80);
        PasswordField passtxtbox = new PasswordField();
        passtxtbox.setPrefWidth(80);
        Button logbutton = new Button(loc.l("Login"));
        logbutton.setPrefWidth(80);
        Button regbutton = new Button(loc.l("Register"));
        logbutton.setPrefWidth(80);
        Label replabel = new Label(loc.l("<responsehere>"));
        replabel.setPrefWidth(80);

        logbutton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent e) {
                try {cons.startConnection(ip, port);}
                catch (Exception ce){System.out.printf(loc.l("Noconnection:(")); return;}
                String tr = cons.speak("login " + logtxtbox.getText() + ' ' + passtxtbox.getText(), "");
                replabel.setText(tr);
                if (tr.contains("Logged in")){username = logtxtbox.getText(); cons.name = username; loginStage.close();}
            }
        });
        regbutton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent e) {
                try {cons.startConnection(ip, port);}
                catch (Exception ce){System.out.printf(loc.l("Noconnection:(")); return;}
                String tr = cons.speak("register " + logtxtbox.getText() + ' ' + passtxtbox.getText(), "");
                replabel.setText(tr);
                if (tr.contains("Registered")){username = logtxtbox.getText(); cons.name = username; loginStage.close();}
            }
        });

        GridPane inputpanel = new GridPane();
        inputpanel.add(loglabel,0,0);
        inputpanel.add(logtxtbox,1,0);
        inputpanel.add(passlabel,0,1);
        inputpanel.add(passtxtbox,1,1);
        inputpanel.add(logbutton,0,2);
        inputpanel.add(regbutton,1,2);
        for (int i=0;i<6;i++)
            GridPane.setHgrow(inputpanel.getChildren().get(i),Priority.ALWAYS);
        VBox lroot = new VBox(10, inputpanel, replabel);
        lroot.setPrefWidth(256);
        lroot.setMaxWidth(Double.MAX_VALUE);lroot.setMaxHeight(Double.MAX_VALUE);
        Scene lscene = new Scene(lroot);
        loginStage.setScene(lscene);
        loginStage.show();
        return;
    }
    public static void delete() throws Exception{
        Stage delStage = new Stage();
        Label idlabel = new Label("ID/PID:");
        idlabel.setPrefWidth(80);
        TextField idtxtbox = new TextField();
        idtxtbox.setPrefWidth(80);
        Button delbutton = new Button(loc.l("DeletebyID"));
        delbutton.setMaxWidth(Double.MAX_VALUE);
        Button pdelbutton = new Button(loc.l("DeletebyPID"));
        pdelbutton.setMaxWidth(Double.MAX_VALUE);
        Label replabel = new Label(loc.l("<responsehere>"));
        replabel.setPrefWidth(80);

        delbutton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent e) {
                try {cons.startConnection(ip, port);}
                catch (Exception ce){replabel.setText(loc.l("Noconnection:(")); return;}
                String tr = cons.speak("remove_by_id" + idtxtbox.getText(), "");
                System.out.printf("remove " + idtxtbox.getText());
                replabel.setText(loc.l(tr));
            }
        });

        pdelbutton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent e) {
                try {cons.startConnection(ip, port);}
                catch (Exception ce){replabel.setText(loc.l("Noconnection:(")); return;}
                String tr = cons.speak("remove_by_passport_id" + idtxtbox.getText(), "");
                System.out.printf("remove " + idtxtbox.getText());
                replabel.setText(loc.l(tr));
            }
        });
        GridPane inputpanel = new GridPane();
        inputpanel.add(idlabel,0,0);
        inputpanel.add(idtxtbox,1,0);
        inputpanel.add(delbutton,0,1);
        inputpanel.add(pdelbutton,1,1);
        for (int i=0;i<4;i++)
            GridPane.setHgrow(inputpanel.getChildren().get(i),Priority.ALWAYS);
        VBox droot = new VBox(10, inputpanel, replabel);
        droot.setPrefWidth(256);;
        droot.setMaxWidth(Double.MAX_VALUE);droot.setMaxHeight(Double.MAX_VALUE);
        Scene dscene = new Scene(droot);
        delStage.setScene(dscene);
        delStage.show();
        return;
    }
    public void start(Stage primaryStage) throws Exception{
        loc.setLang(lang);
        Parameters params = getParameters();
        List <String> args = params.getRaw();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String inp = "", password = ""; ip = args.get(0); port = Integer.parseInt(args.get(1));
        String exec = "";
        boolean log = false;
        for (int i = 0; i < args.size(); i++){
            if (args.get(i).contentEquals("-exec") && i+1 < args.size())
                exec = args.get(i+1);
            if (args.get(i).contentEquals("-login") && i+2 < args.size()) {
                username = args.get(i+1);
                password = args.get(i+2); log = true;
            }
        }

        cons = new ShatterSender();
        cons.startConnection(ip, port);
        int addup = 0;
        int loggedin = 0;
        HashMap<String, Color>colorMap = new HashMap<>(Map.of());

        // определяем таблицу и устанавливаем данные
        TableView<Person> table = new TableView<Person>();
        TableColumn<Person, Integer> idColumn = new TableColumn<Person, Integer>(loc.l("ID"));
        idColumn.setCellValueFactory(new PropertyValueFactory<Person, Integer>("id"));
        table.getColumns().add(idColumn);
        TableColumn<Person, String> nameColumn = new TableColumn<Person, String>(loc.l("Name"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
        table.getColumns().add(nameColumn);
        TableColumn<Person, String> cxColumn = new TableColumn<Person, String>(loc.l("X"));
        cxColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("cx"));
        table.getColumns().add(cxColumn);
        TableColumn<Person, Integer> cyColumn = new TableColumn<Person, Integer>(loc.l("Y"));
        cyColumn.setCellValueFactory(new PropertyValueFactory<Person, Integer>("cy"));
        table.getColumns().add(cyColumn);
        TableColumn<Person, String> cdateColumn = new TableColumn<Person, String>(loc.l("Creationdate"));
        cdateColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("cdate"));
        table.getColumns().add(cdateColumn);
        TableColumn<Person, String> ctimeColumn = new TableColumn<Person, String>(loc.l("Creationtime"));
        ctimeColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("ctime"));
        table.getColumns().add(ctimeColumn);
        TableColumn<Person, String> heiColumn = new TableColumn<Person, String>(loc.l("Height"));
        heiColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("hei"));
        table.getColumns().add(heiColumn);
        TableColumn<Person, String> lxColumn = new TableColumn<Person, String>(loc.l("LocationX"));
        lxColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("lx"));
        table.getColumns().add(lxColumn);
        TableColumn<Person, Long> lyColumn = new TableColumn<Person, Long>(loc.l("LocationY"));
        lyColumn.setCellValueFactory(new PropertyValueFactory<Person, Long>("ly"));
        table.getColumns().add(lyColumn);
        TableColumn<Person, Long> lzColumn = new TableColumn<Person, Long>(loc.l("LocationZ"));
        lzColumn.setCellValueFactory(new PropertyValueFactory<Person, Long>("lz"));
        table.getColumns().add(lzColumn);
        TableColumn<Person, String> dateColumn = new TableColumn<Person, String>(loc.l("Date"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("date"));
        table.getColumns().add(dateColumn);
        TableColumn<Person, String> timeColumn = new TableColumn<Person, String>(loc.l("Time"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("time"));
        table.getColumns().add(timeColumn);
        TableColumn<Person, String> clrColumn = new TableColumn<Person, String>(loc.l("Color"));
        clrColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("col"));
        table.getColumns().add(clrColumn);
        TableColumn<Person, String> passColumn = new TableColumn<Person, String>(loc.l("PassportID"));
        passColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("pass"));
        table.getColumns().add(passColumn);
        TableColumn<Person, String> userColumn = new TableColumn<Person, String>(loc.l("Username"));
        userColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("user"));
        table.getColumns().add(userColumn);
        table.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        Pane overlay = new Pane();
        overlay.setMaxWidth(Double.MAX_VALUE);
        overlay.setMaxHeight(Double.MAX_VALUE);
        overlay.setStyle("-fx-background-color: #FFFFFF");

        Label ctrlabel = new Label(loc.l("CONTROLS"));
        ctrlabel.setPrefWidth(120);
        Button loginbutton = new Button(loc.l("Login"));
        loginbutton.setPrefWidth(120);
        loginbutton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent e) {
                try{login();} catch (Exception d){System.out.printf(d.toString());}
            }
        });
        Button rfsbutton = new Button(loc.l("Refresh"));
        rfsbutton.setPrefWidth(120);
        Button shubutton = new Button(loc.l("Shuffle"));
        shubutton.setPrefWidth(120);

        status = new TextField("");
        status.setMaxWidth(Double.MAX_VALUE);
        status.setEditable(false);

        Button addbutton = new Button(loc.l("Add"));
        addbutton.setPrefWidth(120);
        addbutton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent e) {
                try{add(true,null);} catch (Exception d){System.out.printf(d.toString());}
            }
        });
        Button updbutton = new Button(loc.l("Update"));
        updbutton.setPrefWidth(120);
        updbutton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent e) {
                try{add(false,null);} catch (Exception d){System.out.printf(d.toString());}
            }
        });
        Button rmvbutton = new Button(loc.l("Remove"));
        rmvbutton.setPrefWidth(120);
        rmvbutton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent e) {
                try{delete();} catch (Exception d){System.out.printf(d.toString());}
            }
        });
        Button clrbutton = new Button(loc.l("Clear"));
        clrbutton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent e) {
                try{cons.startConnection(ip, port); cons.speak("clear","");} catch (Exception d){System.out.printf(d.toString());}
            }
        });

        Label langlabel = new Label(loc.l("LANGUAGE"));
        langlabel.setPrefWidth(120);
        String langs[] = {"English", "Русский", "Eesti", "Italiana", "Español"};
        ComboBox langbox = new ComboBox(FXCollections.observableArrayList(langs));
        langbox.setPrefWidth(120);

        clrbutton.setPrefWidth(120);
        Button tblbutton = new Button(loc.l("Table"));
        Button mapbutton = new Button(loc.l("Map"));
        tblbutton.setMaxWidth(Double.MAX_VALUE);
        mapbutton.setMaxWidth(Double.MAX_VALUE);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(loc.l("Open file"));
        Label filelabel = new Label(loc.l("FILE"));
        Button fileload = new Button(loc.l("Load"));
        fileload.setPrefWidth(120);

        GridPane tbpane = new GridPane();
        tbpane.add(table,0,0);
        tblbutton.setDisable(true);
        mapbutton.setDisable(false);
        GridPane.setHgrow(tbpane.getChildren().get(0),Priority.ALWAYS);
        GridPane.setVgrow(tbpane.getChildren().get(0),Priority.ALWAYS);
        ctrlabel.setAlignment(Pos.CENTER);
        langlabel.setAlignment(Pos.CENTER);
        VBox bpanel = new VBox(10, ctrlabel, loginbutton, rfsbutton, shubutton, addbutton, updbutton, rmvbutton, clrbutton, langlabel, langbox, filelabel, fileload);
        bpanel.setMinWidth(200); bpanel.setAlignment(Pos.CENTER);
        GridPane mswitch = new GridPane();
        mswitch.add(tblbutton,0,0);
        mswitch.add(mapbutton,1,0);
        tblbutton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent e) {
                tbpane.getChildren().remove(overlay);
                tbpane.add(table,0,0);
                GridPane.setHgrow(tbpane.getChildren().get(0),Priority.ALWAYS);
                GridPane.setVgrow(tbpane.getChildren().get(0),Priority.ALWAYS);
                tblbutton.setDisable(true);
                mapbutton.setDisable(false);
            }
        });
        mapbutton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent e) {
                tbpane.getChildren().remove(table);
                tbpane.add(overlay,0,0);
                GridPane.setHgrow(tbpane.getChildren().get(0),Priority.ALWAYS);
                GridPane.setVgrow(tbpane.getChildren().get(0),Priority.ALWAYS);
                mapbutton.setDisable(true);
                tblbutton.setDisable(false);
            }
        });


        VBox tpanel = new VBox(0, mswitch, tbpane, status);

        tpanel.setMaxWidth(Double.MAX_VALUE);tpanel.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(tpanel.getChildren().get(1), Priority.ALWAYS);
        HBox root = new HBox(10, bpanel, tpanel);
        HBox.setHgrow(root.getChildren().get(1), Priority.ALWAYS);
        GridPane.setHgrow(mswitch.getChildren().get(0), Priority.ALWAYS);
        GridPane.setHgrow(mswitch.getChildren().get(1), Priority.ALWAYS);
        root.setPrefWidth(1536);root.setPrefHeight(864);
        root.setMaxWidth(Double.MAX_VALUE);root.setMaxHeight(Double.MAX_VALUE);

        langbox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                switch (langbox.getValue().toString()){
                    case "English": lang = "en"; break;
                    case "Русский": lang = "ru"; break;
                    case "Eesti": lang = "es"; break;
                    case "Italiana": lang = "it"; break;
                    case "Español": lang = "sp"; break;
                }
                loc.setLang(lang);
                ctrlabel.setText(loc.l("CONTROLS"));
                loginbutton.setText(loc.l("Login"));
                rfsbutton.setText(loc.l("Refresh"));
                shubutton.setText(loc.l("Shuffle"));
                addbutton.setText(loc.l("Add"));
                updbutton.setText(loc.l("Update"));
                rmvbutton.setText(loc.l("Remove"));
                clrbutton.setText(loc.l("Clear"));
                langlabel.setText(loc.l("LANGUAGE"));
                tblbutton.setText(loc.l("Table"));
                mapbutton.setText(loc.l("Map"));
                idColumn.setText(loc.l("ID"));
                nameColumn.setText(loc.l("Name"));
                cxColumn.setText(loc.l("X"));
                cyColumn.setText(loc.l("Y"));
                cdateColumn.setText(loc.l("Creationdate"));
                ctimeColumn.setText(loc.l("Creationtime"));
                heiColumn.setText(loc.l("Height"));
                lxColumn.setText(loc.l("LocationX"));
                lyColumn.setText(loc.l("LocationY"));
                lzColumn.setText(loc.l("LocationZ"));
                dateColumn.setText(loc.l("Date"));
                timeColumn.setText(loc.l("Time"));
                clrColumn.setText(loc.l("Color"));
                passColumn.setText(loc.l("PassportID"));
                userColumn.setText(loc.l("Username"));
                filelabel.setText(loc.l("FILE"));
                fileload.setText(loc.l("Load"));
            }
        });

        addbutton.setDisable(true);
        rmvbutton.setDisable(true);
        clrbutton.setDisable(true);
        updbutton.setDisable(true);
        rfsbutton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent e) {
                if (!username.isEmpty()){
                    addbutton.setDisable(false);
                    rmvbutton.setDisable(false);
                    clrbutton.setDisable(false);
                    updbutton.setDisable(false);
                }
                try {cons.startConnection(ip, port);}
                catch (Exception ce){status.setText(loc.l("Noconnection:(")); return;}
                String resp = cons.speak("showall", "");
                System.out.printf(resp);
                if (resp.contentEquals("The list is empty.\n")){
                    status.setText(loc.l(resp));
                    return;
                }
                String[] splitn = resp.split("\\R+");
                ObservableList <Person> olist = FXCollections.observableArrayList();
                for (int i = 0;i<splitn.length;i++){
                    olist.add(spliter(splitn[i]));
                }
                table.setItems(olist);
                overlay.getChildren().clear();
                for (int i=0;i<olist.size();i++) {
                    Color clr = null;
                    if (colorMap.containsKey(olist.get(i).getUser())) clr = colorMap.get(olist.get(i).getUser());
                    else {
                        clr = new Color(Math.random(), Math.random(), Math.random(), 1.0);
                        colorMap.put(olist.get(i).getUser(), clr);
                    }
                    Color clrcopy = clr;
                    Circle guy = new Circle(Double.parseDouble(olist.get(i).getCx().replace(',', '.')), olist.get(i).getCy(), 15);
                    guy.setId(""+olist.get(i).getId());
                    guy.setFill(clr);
                    guy.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                        @Override public void handle(MouseEvent e) {
                            if (e.getButton() == MouseButton.PRIMARY) {
                                try {cons.startConnection(ip, port);}
                                catch (Exception ce){status.setText(loc.l("Noconnection:(")); return;}
                                System.out.printf("show " + guy.getId());
                                status.setText(cons.speak("show " + guy.getId(), "").replace(" ", "     ") + "");
                            }
                            if (e.getButton() == MouseButton.SECONDARY) {
                                ContextMenu contextMenu = new ContextMenu();
                                MenuItem item1 = new MenuItem(loc.l("Update"));
                                item1.setOnAction(new EventHandler<ActionEvent>() {
                                    public void handle(ActionEvent e) {
                                        Person theone = olist.stream().filter(person -> guy.getId().contentEquals(person.getId()+""))
                                                .findFirst().orElse(null);
                                        try{add(false, theone);}catch (Exception bruh){
                                            status.setText(loc.l("noaccess"));
                                            FillTransition fill1 = new FillTransition();
                                            FillTransition fill2 = new FillTransition();
                                            FillTransition fill3 = new FillTransition();
                                            FillTransition fill4 = new FillTransition();
                                            FillTransition fill5 = new FillTransition();
                                            FillTransition fill6 = new FillTransition();
                                            fill1.setDuration(Duration.millis(100)); fill1.setToValue(new Color(1,0,0,1));fill1.setAutoReverse(true);
                                            fill2.setDuration(Duration.millis(100)); fill2.setToValue(clrcopy);fill2.setAutoReverse(true);
                                            fill3.setDuration(Duration.millis(100)); fill3.setToValue(new Color(1,0,0,1));fill3.setAutoReverse(true);
                                            fill4.setDuration(Duration.millis(100)); fill4.setToValue(clrcopy);fill4.setAutoReverse(true);
                                            fill5.setDuration(Duration.millis(100)); fill5.setToValue(new Color(1,0,0,1));fill5.setAutoReverse(true);
                                            fill6.setDuration(Duration.millis(100)); fill6.setToValue(clrcopy);fill6.setAutoReverse(true);
                                            SequentialTransition seqT = new SequentialTransition(guy,fill1,fill2,fill3,fill4,fill5,fill6);
                                            seqT.play();
                                        }
                                    }
                                });
                                MenuItem item2 = new MenuItem(loc.l("Delete"));
                                item2.setOnAction(new EventHandler<ActionEvent>() {
                                    public void handle(ActionEvent e) {
                                        try {cons.startConnection(ip, port);}
                                        catch (Exception ces){status.setText(loc.l("Noconnection:(")); return;}
                                        String resp = cons.speak("remove_by_id " + guy.getId(),"");
                                        if (resp.contains("Success")){
                                            status.setText(loc.l("success"));
                                            ScaleTransition scale = new ScaleTransition();
                                            FadeTransition fade = new FadeTransition();
                                            scale.setDuration(Duration.millis(300));
                                            scale.setToX(3.0);
                                            scale.setToY(3.0);
                                            scale.setNode(guy);
                                            scale.play();
                                            fade.setToValue(0.0);
                                            fade.setDuration(Duration.millis(300));
                                            fade.setNode(guy);
                                            fade.play();
                                            guy.setDisable(true);
                                        }
                                        else{
                                            status.setText(loc.l("noaccess"));
                                            FillTransition fill1 = new FillTransition();
                                            FillTransition fill2 = new FillTransition();
                                            FillTransition fill3 = new FillTransition();
                                            FillTransition fill4 = new FillTransition();
                                            FillTransition fill5 = new FillTransition();
                                            FillTransition fill6 = new FillTransition();
                                            fill1.setDuration(Duration.millis(100)); fill1.setToValue(new Color(1,0,0,1));fill1.setAutoReverse(true);
                                            fill2.setDuration(Duration.millis(100)); fill2.setToValue(clrcopy);fill2.setAutoReverse(true);
                                            fill3.setDuration(Duration.millis(100)); fill3.setToValue(new Color(1,0,0,1));fill3.setAutoReverse(true);
                                            fill4.setDuration(Duration.millis(100)); fill4.setToValue(clrcopy);fill4.setAutoReverse(true);
                                            fill5.setDuration(Duration.millis(100)); fill5.setToValue(new Color(1,0,0,1));fill5.setAutoReverse(true);
                                            fill6.setDuration(Duration.millis(100)); fill6.setToValue(clrcopy);fill6.setAutoReverse(true);
                                            SequentialTransition seqT = new SequentialTransition(guy,fill1,fill2,fill3,fill4,fill5,fill6);
                                            seqT.play();
                                        }
                                    }
                                });
                                contextMenu.getItems().addAll(item1, item2);
                                contextMenu.show(guy, Side.RIGHT, 0, 0);
                            }
                        }
                    });
                    guy.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                        @Override public void handle(MouseEvent e) {
                            ScaleTransition scale = new ScaleTransition();
                            scale.setDuration(Duration.millis(100));
                            scale.setToX(1.25);
                            scale.setToY(1.25);
                            scale.setNode(guy);
                            scale.play();
                        }
                    });
                    guy.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                        @Override public void handle(MouseEvent e) {
                            ScaleTransition scale = new ScaleTransition();
                            scale.setDuration(Duration.millis(100));
                            scale.setToX(1.0);
                            scale.setToY(1.0);
                            scale.setNode(guy);
                            scale.play();
                        }
                    });
                    overlay.getChildren().add(guy);
                }
            }
        });
        shubutton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent e) {
                try {cons.startConnection(ip, port);}
                catch (Exception ce){status.setText(loc.l("Noconnection:(")); return;}
                String resp = cons.speak("shuffle", "");
                status.setText(loc.l(resp));
            }
        });
        fileload.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        File file = fileChooser.showOpenDialog(primaryStage);
                        if (file != null) {
                            try{fileread(cons, file.getName());}
                            catch(Exception fil){status.setText(loc.l("Noconnection:("));}
                        }
                    }
                });
        Scene scene = new Scene(root, 1536, 864);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] argz){
        loc = new Localit();
        loc.load();
        Application.launch(argz);
    }
}
