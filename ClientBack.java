import java.io.*;
import java.util.ArrayList;
import java.net.*;
import java.util.Arrays;

/*
if (inp.contentEquals("save")) inp += ' ' + savefile;
                String outp = rec.speak(inp, name);
                if (inp.contains("execute_script")) {
                    if (!outp.contentEquals("-"))
                        outp = fileread(rec, outp, args);
                    else
                        outp = "No file name was provided.";
                }
                if (outp == "over") {
                    return "over";
                } else if (outp == "ERRPARSE") return "The script " + name + " contains errors.\n";
                else System.out.printf(outp);
*/

public class ClientBack {
    /*public static int ping_cycle(ShatterSender rec, String ip, int port, BufferedReader reader){
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
    }*/
}