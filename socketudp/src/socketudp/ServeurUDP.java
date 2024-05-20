package socketudp;
import socketudp.Voiture;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.Scanner;
public class ServeurUDP {
public static void main(String argv[]) {
int port = 0;
Scanner keyb = new Scanner(System.in);
try {
// on récupère le paramètre : port d'écoute
System.out.println("port d'écoute : ");
port = keyb.nextInt();
DatagramPacket packet;
// création d'une socket liée au port précisé en paramètre
DatagramSocket socket = new DatagramSocket(port);
ByteArrayOutputStream bos = new ByteArrayOutputStream();
ObjectOutputStream out = null;
byte [] data;
try {
  out = new ObjectOutputStream(bos);   
  out.writeObject(new Voiture("bmw", "X5"));
  out.flush();
  data = bos.toByteArray();
} finally {
  try {
    bos.close();
  } catch (IOException ex) {
    // ignore close exception
  }
}
packet = new DatagramPacket(data, data.length);



// attente de la réception d'un paquet. Le paquet reçu est placé
// dans packet et ses données dans data.

socket.receive(packet);

// récupération et affichage des données (une chaîne de caractères)

String chaine = new String(packet.getData(), 0,
packet.getLength());
System.out.println(" recu : " + chaine);
System.out.println(" ca vient de : " + packet.getAddress() + ":" +

packet.getPort());

// on met une nouvelle donnée dans le paquet
// (qui contient donc le couple @IP/port de la socket coté client)
byte[] reponse = (new String("set carburant")).getBytes();
packet.setData(reponse);
packet.setLength(reponse.length);
// on envoie le paquet au client
socket.send(packet);
} catch (Exception e) {
System.err.println("Erreur : " + e);
}
}
}