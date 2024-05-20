package socketudp;
import socketudp.Voiture;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.Scanner;
public class ClientUDP {public static void main(String argv[]) {
	int port = 0;
	String host = "";
	Scanner keyb = new Scanner(System.in);
	try {
	// on récupère les paramètres : nom de la machine serveur et
	// numéro de port
	System.out.println("Adress du serveur : ");
	host = keyb.next();
	System.out.println("port d'écoute du serveur : ");
	port = keyb.nextInt();
	InetAddress adr;
	DatagramPacket packet;
	DatagramSocket socket;
	// adr contient l'@IP de la partie serveur
	adr = InetAddress.getByName(host);
	// données à envoyer : chaîne de caractères
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

	
	
	
	// création du paquet avec les données et en précisant l'adresse
	// du serveur (@IP et port sur lequel il écoute)
	packet = new DatagramPacket(data, data.length, adr, port);
	// création d'une socket, sans la lier à un port particulier
	socket = new DatagramSocket();
	// envoi du paquet via la socket
	socket.send(packet);
	// création d'un tableau vide pour la réception
	byte[] reponse = new byte[15];
	packet.setData(reponse);
	packet.setLength(reponse.length);
	// attente paquet envoyé sur la socket du client
	socket.receive(packet);
	// récupération et affichage de la donnée contenue dans le paquet
	String chaine = new String(packet.getData(), 0,

	packet.getLength());

	System.out.println(" reçu du serveur : " + chaine);
	} catch (Exception e) {
	System.err.println("Erreur : " + e);
	}
	}

}
