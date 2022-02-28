package hu.bartabalazs;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static List<Fuvar> fuvarok = beolvasas();
    public static void main(String[] args) {
       System.out.println(hanyUtazasKerultfeljegyzesre());
        azonosito6185TaxisBevetele();
        hanyMerfoldetTettekMegATaxisok();
        aLeghosszabbFuvarAdatai();
        aLegbokezubbBorravalojuFuvarAdatai();
        azonosito4261TaxisOsszKilometerei();
        hibasAdatok();
        letezikE1452AzonositojuTaxi();
        haromLegrovidebbUtazas();
        december24EiFuvarokSzama();
        szilveszteriBorravalokAranyossaga();
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

    public static void hibasAdatok(){
        int db = (int) fuvarok.stream()
                .filter(fuvar -> fuvar.getIdotartam() > 0 && fuvar.getViteldij() > 0 && fuvar.getTavolsag() == 0)
                .count();
        int idotartam = (int) fuvarok.stream()
                .filter(fuvar -> fuvar.getIdotartam() > 0 && fuvar.getViteldij() > 0 && fuvar.getTavolsag() == 0)
                .mapToDouble(ido -> ido.getIdotartam())
                .sum();
        int bevetel = (int) fuvarok.stream()
                .filter(fuvar -> fuvar.getIdotartam() > 0 && fuvar.getViteldij() > 0 && fuvar.getTavolsag() == 0)
                .mapToDouble(osszeg -> osszeg.getViteldij() + osszeg.getBorravalo())
                .sum();
        System.out.println("A hibás adatok száma: "+db+" db. Összes időtartalma: "+idotartam+" másodperc. Ezeknek a bevétele: "+bevetel+" pénz.");
    }

    public static void letezikE1452AzonositojuTaxi(){
        long taxis = fuvarok.stream()
                .filter(taxi -> taxi.getTaxi_id() == 1452)
                .count();
        if (taxis != 0)
        {
            System.out.println("létezik 1452 id-jú taxis");
        } else {
            System.out.println("nem létezik 1452 id-jú taxis");
        }
    }
    public static void haromLegrovidebbUtazas(){
        List<Fuvar> legrovidebbek = fuvarok.stream()
                .filter(ut -> ut.getIdotartam() != 0)
                .sorted(Comparator.comparingInt(Fuvar::getIdotartam))
                .limit(3)
                .collect(Collectors.toList());
        for (Fuvar fuvar: legrovidebbek)
        {
            System.out.println(fuvar);
        }
    }
    public static void december24EiFuvarokSzama(){
        int db = (int) fuvarok.stream()
                .filter(fuvar -> fuvar.getIndulas().substring(5, 10).equals("12-24"))
                .count();
        System.out.println("december 24-én "+db+" fuvar volt");
    }

    public static void szilveszteriBorravalokAranyossaga(){
        double fuvarokSzama = (double) fuvarok.stream()
                .filter(datum -> datum.getIndulas().substring(5,10).equals("12-31"))
                .count();
        double borravalokOsszege = (double) fuvarok.stream()
                .filter(datum -> datum.getIndulas().substring(5,10).equals("12-31"))
                .mapToDouble(borravalo -> borravalo.getBorravalo())
                .sum();
        System.out.println(fuvarokSzama);
        System.out.println(borravalokOsszege);
        System.out.println("1 út alatt arányosan "+borravalokOsszege/fuvarokSzama+ "pénz borravalót adtak. Azaz 1:"+borravalokOsszege/fuvarokSzama);
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
