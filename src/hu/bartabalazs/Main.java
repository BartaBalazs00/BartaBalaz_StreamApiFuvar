package hu.bartabalazs;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Fuvar> fuvarok;
        fuvarok = beolvasas();
        for (Fuvar fuvar : fuvarok){
            System.out.println(fuvar.toString());
        }
    }

    private static List<Fuvar> beolvasas() {
        List<Fuvar> fuvarok = new ArrayList<>();
        try
        {
            BufferedReader br = new BufferedReader(new FileReader("fuvar.csv"));
            String sor = br.readLine();
            sor = br.readLine();
            while (sor != null){
                Fuvar fuvar = new Fuvar(sor);
                fuvarok.add(fuvar);
                sor = br.readLine();
            }
            br.close();
        } catch (FileNotFoundException e)
        {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fuvarok;
    }

}
