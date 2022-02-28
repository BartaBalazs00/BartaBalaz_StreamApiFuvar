package hu.bartabalazs;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static List<Fuvar> fuvarok = beolvasas();
    public static void main(String[] args) {
        System.out.println(hanyUtazasKerultfeljegyzesre());
        System.out.println(azonosito6185TaxisBevetele());
    }

    public static long hanyUtazasKerultfeljegyzesre(){
        return fuvarok.stream().count();
    }
    public static double azonosito6185TaxisBevetele(){
        return  fuvarok.stream()
                .filter(fuvar -> fuvar.getTaxi_id() == 6185)
                .mapToDouble(bevetel -> bevetel.getViteldij() + bevetel.getBorravalo())
                .sum();
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
