package hu.bartabalazs;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {
    private static List<Fuvar> fuvarok = beolvasas();
    public static void main(String[] args) {
        System.out.println(hanyUtazasKerultfeljegyzesre());
        azonosito6185TaxisBevetele();
        hanyMerfoldetTettekMegATaxisok();
        aLeghosszabbFuvarAdatai();
        aLegbokezubbBorravalojuFuvarAdatai();
        azonosito4261TaxisOsszKilometerei();
    }

    public static long hanyUtazasKerultfeljegyzesre(){
        return fuvarok.stream().count();
    }

    public static void azonosito6185TaxisBevetele(){
        double osszeg =  fuvarok.stream()
                .filter(fuvar -> fuvar.getTaxi_id() == 6185)
                .mapToDouble(bevetel -> bevetel.getViteldij() + bevetel.getBorravalo())
                .sum();
        int fuvarokSzama = (int) fuvarok.stream()
                .filter(fuvar -> fuvar.getTaxi_id() == 6185)
                .count();
        System.out.println("A 6185-os azonosítójú taxis "+osszeg+" pént kapott a "+fuvarokSzama+" db fuvarért");
    }

    public static void hanyMerfoldetTettekMegATaxisok(){
        double merfold = fuvarok.stream()
                .mapToDouble(hossz -> hossz.getTavolsag())
                .sum();
        System.out.println("összesen "+merfold+ " mérföldet tettek meg a taxisok");
    }

    public static void aLeghosszabbFuvarAdatai(){
        Fuvar lehosszabbFuvar = fuvarok.stream()
                .max(Comparator.comparing(hossz -> hossz.getIdotartam()))
                .get();
        System.out.println("A leghosszabb fuvar adatai: "+lehosszabbFuvar.toString());
    }

    public static void aLegbokezubbBorravalojuFuvarAdatai(){
        Fuvar legbokezubbFuvar = fuvarok.stream()
                .max(Comparator.comparing(hossz -> hossz.getBorravalo()))
                .get();
        System.out.println("A legbőkezübb borravalóju fuvar adatai: "+legbokezubbFuvar.toString());
    }

    public static void azonosito4261TaxisOsszKilometerei(){
        double tavolsag =  fuvarok.stream()
                .filter(fuvar -> fuvar.getTaxi_id() == 4261)
                .mapToDouble(km -> km.getTavolsag() *1.6)
                .sum();
        System.out.println("A 4261-os azonosítójú taxis osszesen "+ tavolsag +" km tett meg");
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
