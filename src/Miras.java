
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Miras {

    public static void main(String[] args) {
        ArrayList<String> nama = new ArrayList<String>();//untuk nama barang
        ArrayList<Integer> hrg = new ArrayList<Integer>();//untuk harga
        ArrayList<Integer> stok = new ArrayList<Integer>();//untuk stok
        
        //input list 1 dan 2, menggunakan add
        nama.add("Celana Jeans");
        nama.add("Kaos Polos");
        hrg.add(200000);
        hrg.add(60000);
        stok.add(100);
        stok.add(50);

        Scanner s = new Scanner(System.in);//scanner biasa
        Scanner str = new Scanner(System.in);//scanner khusus string

        int i, jml, stk = 0, pil = 0;
        String nm, sdh, pela, struk;
        do {
            System.out.println("===TOKO PAKAIAN===");
            System.out.println("Menu: ");
            System.out.println("1. List Barang");
            System.out.println("2. Tambah Barang Baru");
            System.out.println("3. Kasir/Transaksi");
            System.out.println("4. Keluar");
            System.out.print("Pilihan: ");
            pil = s.nextInt();
            switch (pil) {
                case 1://menu 1
                    System.out.printf("\n");
                    System.out.println("++ List Barang ++");
                    for (i = 0; i < nama.size(); i++) {
                        //diambil dari nama, hrg, dan stok sesuai index ke i sampai akhir
                        System.out.println((i + 1) + ". " + nama.get(i) + " | Rp." + hrg.get(i) + " | Stok: " + stok.get(i));
                    }
                    System.out.printf("\n");
                    break;
                case 2://menu 2
                    if (nama.size() < 6) {
                        System.out.printf("\n");
                        System.out.println("++ Tambah Barang ++");
                        System.out.print("Nama Barang: ");
                        nama.add(str.nextLine());//tambah data ke ArrayList nama
                        System.out.print("Harga Barang: ");
                        hrg.add(s.nextInt());//tambah data ke ArrayList hrg(harga)
                        System.out.print("Stok Barang: ");
                        stok.add(s.nextInt());//tambah data ke ArrayList stok
                        System.out.println("Sukses Tambah Barang");
                        System.out.printf("\n");
                    } else {
                        System.out.printf("\nMaksimal 6\n\n");//maksimal data cuma 6
                    }
                    break;
                case 3://menu 3
                    boolean cnm = false;
                    boolean cs = false;
                    System.out.printf("\n");
                    //nama pelanggan
                    System.out.print("Masukkan nama pelanggan: ");
                    pela = str.nextLine();
                    //check nama barang
                    do {
                        System.out.print("Nama Barang: ");
                        nm = str.nextLine();
                        for (i = 0; i < nama.size(); i++) {
                            if (nm.equalsIgnoreCase(nama.get(i))) {
                                cnm = true;//jika inputan nama barang sesuai dengan yang ada di Arraylist, maka true
                            }
                        }
                        if (!cnm) {//jika tidak maka print ini, dan akan loop dengan while
                            System.out.println("Nama Barang Tidak Ditemukan");
                        }
                    } while (cnm != true);//loop bila salah, keluar loop bila benar

                    //check lokasi
                    String[] b = new String[nama.size()];
                    for (i = 0; i < nama.size(); i++) {
                        b[i] = nama.get(i);//patokan untuk dapetin index dari nama
                    }

                    int x = 0;
                    for (i = 0; i < b.length; i++) {
                        if (nm.equalsIgnoreCase(b[i])) {
                            //nanti kalo sama dengan nama barang yang diinput, akan break dan dapat nilai x
                            //dengan x = i
                            x = i;
                            break;
                        }
                    }

                    //check stok (tadi udah dapet x, jadi udah dapet index yang mana)
                    do {
                        System.out.print("Jumlah: ");
                        jml = s.nextInt();
                        if (jml <= stok.get(x)) {
                            //kalao jumlah <= stok yang ada, maka true
                            cs = true;
                        } else {//jika salah maka akan print ini, dan loop lagi
                            System.out.println("Stok Barang Tidak Cukup!");
                        }
                    } while (cs != true);//loop jika salah (selain true)

                    //ok?
                    System.out.print("Sudah?: (ok)");
                    sdh = s.next();
                    if (sdh.equalsIgnoreCase("ok")) {//bila input ok, masuk ke ini
                        boolean uang = false;
                        int total = jml * hrg.get(x);//total = jumlah yang diinput x harga satuan
                        int jb;
                        
                        stk = stok.get(x);//untuk inisialisasi stok ke stok yang ada di ArrayList
                        stk = stk - jml;//untuk ngurangin stok
                        stok.set(x, stk);//untuk replace stok
                        
                        System.out.println("TOTAL: Rp." + total);
                        do {
                            System.out.print("Jumlah Bayar: Rp.");
                            jb = s.nextInt();
                            if (jb >= total) {//jika uang pas/lebih, dicari kembaliannya, dan statement bernilai true
                                System.out.println("KEMBALI: Rp." + (jb - total));
                                System.out.printf("\nTransaksi Sukses!\n\n");
                                uang = true;//true

                                struk//format cetak struk seperti ini
                                        = "Detail pembelian oleh " + pela + "\n"
                                        + nama.get(x) + " | " + jml + "pcs | Rp." + hrg.get(x) + " (Rp." + total + ")" + "\n\n"
                                        + "TOTAL   : Rp." + total + "\n"
                                        + "BAYAR   : Rp." + jb + "\n"
                                        + "KEMBALI : Rp." + (jb - total);
                                
                                try {//pake stream buffer yang output, dengan nama file nya NamaPelanggan.txt
                                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(pela+".txt"));
                                    bos.write(struk.getBytes());//memasukkan data ke file yang udah dibuat dan dibaca sebagai String
                                    bos.close();//menutup file
                                } catch (FileNotFoundException ex) {
                                    Logger.getLogger(Miras.class.getName()).log(Level.SEVERE, null, ex);//proteksi
                                } catch (IOException ex) {
                                    Logger.getLogger(Miras.class.getName()).log(Level.SEVERE, null, ex);//proteksi
                                }
                                
                            } else {//jika uang kurang maka !true dan print ini, dan akan loop lagi
                                System.out.println("Uang Anda Kurang");
                            }
                        } while (uang != true);//loop jika bukan true
                    } else {//bila input bukan ok, maka akan keluar dari menu 2 dan memanggil menu utama
                        System.out.println("");
                    }
                    break;
                case 4://menu 4 (exit)
                    System.out.printf("\nTerima Kasih!\n");
                    break;
                default://jika inputan <=0 atau >4
                    System.out.printf("Inputan Salah\n\n");
                    break;
            }
        } while (pil != 4);//jika pilihan bukan 4 akan loop, jika 4 maka selesai
    }
}
