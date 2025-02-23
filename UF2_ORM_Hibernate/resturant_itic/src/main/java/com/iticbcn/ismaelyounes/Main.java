package com.iticbcn.ismaelyounes;

import com.iticbcn.ismaelyounes.dao.ClientDAO;
import com.iticbcn.ismaelyounes.dao.EmpleatDAO;
import com.iticbcn.ismaelyounes.dao.RestaurantDAO;
import com.iticbcn.ismaelyounes.dao.ReservaDAO;
import com.iticbcn.ismaelyounes.model.Empleat;
import com.iticbcn.ismaelyounes.model.Client;
import com.iticbcn.ismaelyounes.model.Reserva;
import com.iticbcn.ismaelyounes.model.Restaurant;

import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Main {
    public static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    public static ClientDAO clientDAO = new ClientDAO(sessionFactory);
    public static EmpleatDAO empleatsDAO = new EmpleatDAO(sessionFactory);
    public static RestaurantDAO restaurantDAO = new RestaurantDAO(sessionFactory);
    public static ReservaDAO reservaDAO = new ReservaDAO(sessionFactory);
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static void main(String[] args) {

        mostraMenu();
        while (true) {
            String resposta = Utilitats.readLine();
            int seleccion = Utilitats.triaOpcio(resposta, 4);

            switch (seleccion) {
                case 1:
                    gestionarEmpleat(empleatsDAO);
                    break;
                case 2:
                    gestionarClient(clientDAO);
                    break;
                case 3:
                    gestionarRestaurant(restaurantDAO);
                    break;
                case 4:
                    gestionarReserva(reservaDAO);
                    break;
                default:
                    System.out.println("Opció no vàlida. Torna a intentar.");
            }

            mostraMenu();
        }
    }

    public static void mostraMenu() {
        System.out.println("\nSelecciona una opción a gestionar: ");
        System.out.println("1. Empleat");
        System.out.println("2. Client");
        System.out.println("3. Restaurant");
        System.out.println("4. Reserva");
        System.out.println();
    }

    public static void mostraMenu2(String tipus) {
        System.out.println("\nGestións per " + tipus + "s:");
        System.out.println("1. Crear " + tipus);
        System.out.println("2. Obtenir " + tipus);
        System.out.println("3. Obtenir tots els " + tipus + "s");
        System.out.println("4. Actualitzar " + tipus);
        System.out.println("5. Eliminar " + tipus);
        System.out.println("6. Tornar al menú principal");
        System.out.print("Selecciona una opció: ");
    }

    private static void gestionarEmpleat(EmpleatDAO empleatsDAO) {
        while (true) {
            mostraMenu2("Empleat");
            String resposta = Utilitats.readLine();
            int seleccion = Utilitats.triaOpcio(resposta, 5);
            System.out.println();

            switch (seleccion) {
                case 1:
                    try {
                        String nom = Utilitats.nom(1);
                        String telefon = Utilitats.telClient();
                        System.out.print("Introdueix el correu: ");
                        String correu = Utilitats.readLine();
                        Empleat empleatCreado = new Empleat(nom, telefon, correu);
                        empleatsDAO.save(empleatCreado);
                        System.out.println("Afegir Restaurant?");
                        String cadena = Utilitats.readLine();

                        if (Utilitats.confirmador(cadena)) {
                            Restaurant restaurantNou = DemanarRestaurant();
                            Restaurant prova = restaurantDAO.obtenirRestaurantPerNom(restaurantNou.getNom().strip());

                            if (prova != null) {
                                empleatCreado.setRestaurant(prova);
                            } else {
                                restaurantDAO.save(restaurantNou);
                                empleatCreado.setRestaurant(restaurantNou);
                            }
                        }

                        empleatsDAO.update(empleatCreado);
                        System.out.println("Empleat creat correctament amb ID: " + empleatCreado.getIdEmpleat());
                    } catch (Exception e) {
                        System.err.println("Error al crear o actualitzar l'empleat: " + e.getMessage());
                    }
                    break;

                case 2:
                    try {
                        System.out.print("Introdueix l'ID de l'Empleat: ");
                        String idResposta = Utilitats.readLine();
                        long idBuscar = Integer.parseInt(idResposta);

                        Empleat empleatObtenido = empleatsDAO.get(idBuscar);
                        if (empleatObtenido != null) {
                            System.out.println("\nEmpleat trobat: ");
                            System.out.println("Nom: " + empleatObtenido.getNom());
                            System.out.println("Email: " + empleatObtenido.getCorreo());
                            System.out.println("Teléfon: " + empleatObtenido.getTelefon());

                            Hibernate.initialize(empleatObtenido.getRestaurant());
                            if (empleatObtenido.getRestaurant() != null) {
                                System.out.println("Restaurant: " + empleatObtenido.getRestaurant().getNom());
                            } else {
                                System.out.println("Restaurant: NULL ");
                            }
                        } else {
                            System.out.println("No s'ha trobat un empleat amb aquest ID.");
                        }
                    } catch (Exception e) {
                        System.err.println("Error al obtenir l'empleat: " + e.getMessage());
                    }
                    break;

                case 3:
                    try {
                        System.out.println("Llistat de clients:");
                        List<Empleat> empleats = empleatsDAO.getAll();
                        if (empleats.isEmpty()) {
                            System.out.println("No s'ha trobat cap client.");
                        } else {
                            for (Empleat e : empleats) {
                                System.out.println("\nEmpleat trobat: ");
                                System.out.println("Nom: " + e.getNom());
                                System.out.println("Email: " + e.getCorreo());
                                System.out.println("Teléfon: " + e.getTelefon());
                                if (e.getRestaurant() != null) {
                                    System.out.println("Restaurant: " + e.getRestaurant().getNom());
                                } else {
                                    System.out.println("Restaurant: NULL ");
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.err.println("Error al obtenir tots els empleats: " + e.getMessage());
                    }
                    break;

                case 4:
                    try {
                        System.out.print("Introdueix l'ID de l'Empleat a actualitzar: ");
                        String idRespostaActualizar = Utilitats.readLine();
                        long idActualizar = Integer.parseInt(idRespostaActualizar);

                        Empleat empleatActualizar = empleatsDAO.get(idActualizar);

                        if (empleatActualizar != null) {
                            String nouNom = Utilitats.nom(1);
                            String nouTelefon = Utilitats.telClient();
                            System.out.print("Introdueix el nou correu: ");
                            String nouCorreu = Utilitats.readLine();

                            Restaurant restnou = DemanarRestaurant();
                            RestaurantDAO rest = new RestaurantDAO(empleatsDAO.getSessionFactory());
                            Boolean exiteix = rest.obtenirRestaurantPerNom(restnou.getNom()) != null;

                            empleatActualizar.setNom(nouNom);
                            empleatActualizar.setTelefon(nouTelefon);
                            empleatActualizar.setCorreo(nouCorreu);
                            if (exiteix) {
                                empleatActualizar.setRestaurant(rest.obtenirRestaurantPerNom(restnou.getNom()));
                            } else {
                                empleatActualizar.setRestaurant(restnou);
                            }
                            empleatsDAO.update(empleatActualizar);
                            System.out.println("Empleat actualitzat correctament!");
                        } else {
                            System.out.println("No s'ha trobat un empleat amb aquest ID.");
                        }
                    } catch (Exception e) {
                        System.err.println("Error al actualitzar l'empleat: " + e.getMessage());
                    }
                    break;

                case 5:
                    try {
                        System.out.print("Introdueix l'ID de l'Empleat a eliminar: ");
                        String idRespostaEliminar = Utilitats.readLine();
                        long idEliminar = Integer.parseInt(idRespostaEliminar);

                        Empleat empleatEliminar = empleatsDAO.get(idEliminar);
                        if (empleatEliminar != null) {
                            empleatsDAO.delete(empleatEliminar);
                            System.out.println("Empleat " + idEliminar + " eliminat correctament!");
                        } else {
                            System.out.println("No s'ha trobat un empleat amb aquest ID.");
                        }
                    } catch (Exception e) {
                        System.err.println("Error al eliminar l'empleat: " + e.getMessage());
                    }
                    break;

                case 6:
                    return;
            }
        }
    }

    private static void gestionarClient(ClientDAO clientDAO) {
        while (true) {
            mostraMenu2("Client");
            String resposta = Utilitats.readLine();
            int seleccion = Utilitats.triaOpcio(resposta, 5);
            System.out.println();

            switch (seleccion) {
                case 1:
                    try {
                        Client clientNuevo = DemanarClient();
                        clientDAO.save(clientNuevo);

                        System.out.println("Vols afegir una Reserva?");
                        String cadena = Utilitats.readLine();

                        if (Utilitats.confirmador(cadena)) {
                            Restaurant prova = DemanarRestaurant();
                            Restaurant restNou = restaurantDAO.obtenirRestaurantPerNom(prova.getNom().strip());

                            if (restNou == null) {
                                restaurantDAO.save(prova);
                                restNou = prova;
                            }

                            Reserva reserva = DemanarReserva();
                            reserva.setRestaurant(restNou);
                            reserva.setClient(clientNuevo);

                            reservaDAO.update(reserva);

                            clientNuevo.addReserva(reserva);
                        }

                        System.out.println("Client creat correctament!");
                    } catch (Exception e) {
                        System.err.println("Error al crear el client: " + e.getMessage());
                    }
                    break;

                case 2:
                    try {
                        System.out.print("Introdueix l'ID del Client: ");
                        String idResposta = Utilitats.readLine();
                        long idBuscar = Integer.parseInt(idResposta);

                        Client clientObtingut = clientDAO.get(idBuscar);
                        if (clientObtingut != null) {
                            System.out.println("\nClient trobat: ");
                            System.out.println("Nom: " + clientObtingut.getNom());
                            System.out.println("Email: " + clientObtingut.getCorreo());
                            System.out.println("Teléfon: " + clientObtingut.getTelefon());
                        } else {
                            System.out.println("No s'ha trobat un client amb aquest ID.");
                        }
                    } catch (Exception e) {
                        System.err.println("Error al obtenir el client: " + e.getMessage());
                    }
                    break;

                case 3:
                    try {
                        System.out.println("Llistat de clients:");
                        List<Client> clients = clientDAO.getAll();
                        if (clients.isEmpty()) {
                            System.out.println("No s'ha trobat cap client.");
                        } else {
                            for (Client c : clients) {
                                System.out.println("\nClient trobat: ");
                                System.out.println("Nom: " + c.getNom());
                                System.out.println("Email: " + c.getCorreo());
                                System.out.println("Teléfon: " + c.getTelefon());

                                // Mostrar les reserves del client
                                if (c.getReservas().isEmpty()) {
                                    System.out.println("Aquest client no té reserves.");
                                } else {
                                    System.out.println("Reserves:");
                                    for (Reserva r : c.getReservas()) {
                                        System.out.println("  - Hora: " + r.getFecha_hora());
                                    }
                                }
                                System.out.println(); // Separador entre clients
                            }
                        }
                    } catch (Exception e) {
                        System.err.println("Error al obtenir tots els clients: " + e.getMessage());
                    }
                    break;

                case 4:
                    try {
                        System.out.print("Introdueix l'ID del client a actualitzar: ");
                        long idClientActualizar = Integer.parseInt(Utilitats.readLine());
                        Client clientAActualizar = clientDAO.get(idClientActualizar);

                        if (clientAActualizar != null) {
                            String nouNomClient = Utilitats.nom(1);
                            String nouTelefonClient = Utilitats.telClient();
                            System.out.print("Introdueix el nou correu: ");
                            String nouCorreuClient = Utilitats.readLine();

                            clientAActualizar.setNom(nouNomClient);
                            clientAActualizar.setTelefon(nouTelefonClient);
                            clientAActualizar.setCorreo(nouCorreuClient);

                            clientDAO.update(clientAActualizar);
                            System.out.println("Client actualitzat correctament!");
                        } else {
                            System.out.println("No s'ha trobat un client amb aquest ID.");
                        }
                    } catch (Exception e) {
                        System.err.println("Error al actualitzar el client: " + e.getMessage());
                    }
                    break;

                case 5:
                    try {
                        System.out.print("Introdueix l'ID del client a eliminar: ");
                        long idClientEliminar = Integer.parseInt(Utilitats.readLine());

                        Client clientAEliminar = clientDAO.get(idClientEliminar);
                        if (clientAEliminar != null) {
                            clientDAO.delete(clientAEliminar);
                            System.out.println("Client eliminat correctament!");
                        } else {
                            System.out.println("No s'ha trobat un client amb aquest ID.");
                        }
                    } catch (Exception e) {
                        System.err.println("Error al eliminar el client: " + e.getMessage());
                    }
                    break;

                case 6:
                    return;
            }
        }
    }

    private static void gestionarReserva(ReservaDAO reservaDAO) {
        while (true) {
            mostraMenu2("Reserva");
            String resposta = Utilitats.readLine();
            int seleccion = Utilitats.triaOpcio(resposta, 5); // Ajustar el límite

            switch (seleccion) {
                case 1:
                    try {
                        restaurantDAO.save(DemanarRestaurant());
                        System.out.println("Restaurant creat correctament!");
                    } catch (Exception e) {
                        System.err.println("Error al crear el restaurant: " + e.getMessage());
                    }
                    break;

                case 2:
                    try {
                        System.out.print("Introdueix l'ID del Restaurant: ");
                        long idResposta = Integer.parseInt(Utilitats.readLine());

                        Restaurant restaurantObtingut = restaurantDAO.get(idResposta);
                        if (restaurantObtingut != null) {
                            System.out.println("\nRestaurant trobat:");
                            System.out.println("Nom: " + restaurantObtingut.getNom());
                            System.out.println("nº Taules: " + restaurantObtingut.getCapacitat());
                        } else {
                            System.out.println("No s'ha trobat un restaurant amb aquest ID.");
                        }
                    } catch (Exception e) {
                        System.err.println("Error al obtenir el restaurant: " + e.getMessage());
                    }
                    break;

                case 3:
                    try {
                        System.out.println("\nLlistat de restaurants:");
                        List<Restaurant> restaurants = restaurantDAO.getAll();
                        if (restaurants.isEmpty()) {
                            System.out.println("No s'ha trobat cap restaurant.");
                        } else {
                            for (Restaurant r : restaurants) {
                                System.out.println();
                                System.out.println("Nom: " + r.getNom());
                                System.out.println("nº Taules: " + r.getCapacitat());
                            }
                        }
                    } catch (Exception e) {
                        System.err.println("Error al obtenir tots els restaurants: " + e.getMessage());
                    }
                    break;

                case 4:
                    try {
                        String nouNomRestaurant = Utilitats.nom(1);
                        Restaurant restActualitzar = restaurantDAO.obtenirRestaurantPerNom(nouNomRestaurant);

                        if (restActualitzar != null) {
                            System.out.print("Introdueix la nova capacitat: ");
                            int novaCapacitat = Integer.parseInt(Utilitats.readLine());

                            restActualitzar.setNom(nouNomRestaurant);
                            restActualitzar.setCapacitat(novaCapacitat);

                            restaurantDAO.update(restActualitzar);
                            System.out.println("Restaurant actualitzat correctament!");
                        } else {
                            System.out.println("No s'ha trobat un restaurant.");
                        }
                    } catch (Exception e) {
                        System.err.println("Error al actualitzar el restaurant: " + e.getMessage());
                    }
                    break;

                case 5:
                    try {
                        System.out.print("Introdueix l'ID del restaurant a eliminar: ");
                        long idRestaurantEliminar = Integer.parseInt(Utilitats.readLine());

                        Restaurant restaurantAEliminar = restaurantDAO.get(idRestaurantEliminar);
                        if (restaurantAEliminar != null) {
                            restaurantDAO.delete(restaurantAEliminar);
                            System.out.println("Restaurant eliminat correctament!");
                        } else {
                            System.out.println("No s'ha trobat un restaurant amb aquest ID.");
                        }
                    } catch (Exception e) {
                        System.err.println("Error al eliminar el restaurant: " + e.getMessage());
                    }
                    break;

                case 6:
                    return;
            }
        }
    }

    private static void gestionarRestaurant(RestaurantDAO restaurantDAO) {
        while (true) {
            mostraMenu2("Restaurant");
            String resposta = Utilitats.readLine();
            int seleccion = Utilitats.triaOpcio(resposta, 5);
            System.out.println();

            switch (seleccion) {
                case 1:
                    try {
                        restaurantDAO.save(DemanarRestaurant());
                        System.out.println("Restaurant creat correctament!");
                    } catch (Exception e) {
                        System.err.println("Error al crear el restaurant: " + e.getMessage());
                    }
                    break;

                case 2:
                    try {
                        System.out.print("Introdueix l'ID del Restaurant: ");
                        long idResposta = Integer.parseInt(Utilitats.readLine());

                        Restaurant restaurantObtingut = restaurantDAO.get(idResposta);
                        if (restaurantObtingut != null) {
                            System.out.println("\nRestaurant trobat:");
                            System.out.println("Nom: " + restaurantObtingut.getNom());
                            System.out.println("nº Taules: " + restaurantObtingut.getCapacitat());
                        } else {
                            System.out.println("No s'ha trobat un restaurant amb aquest ID.");
                        }
                    } catch (Exception e) {
                        System.err.println("Error al obtenir el restaurant: " + e.getMessage());
                    }
                    break;

                case 3:
                    try {
                        System.out.println("\nLlistat de restaurants:");
                        List<Restaurant> restaurants = restaurantDAO.getAll();
                        if (restaurants.isEmpty()) {
                            System.out.println("No s'ha trobat cap restaurant.");
                        } else {
                            for (Restaurant r : restaurants) {
                                System.out.println();
                                System.out.println("Nom: " + r.getNom());
                                System.out.println("nº Taules: " + r.getCapacitat());
                            }
                        }
                    } catch (Exception e) {
                        System.err.println("Error al obtenir tots els restaurants: " + e.getMessage());
                    }
                    break;

                case 4:
                    try {
                        String nouNomRestaurant = Utilitats.nom(1);
                        Restaurant restActualitzar = restaurantDAO.obtenirRestaurantPerNom(nouNomRestaurant);

                        if (restActualitzar != null) {
                            System.out.print("Introdueix la nova capacitat: ");
                            int novaCapacitat = Integer.parseInt(Utilitats.readLine());

                            restActualitzar.setNom(nouNomRestaurant);
                            restActualitzar.setCapacitat(novaCapacitat);

                            restaurantDAO.update(restActualitzar);
                            System.out.println("Restaurant actualitzat correctament!");
                        } else {
                            System.out.println("No s'ha trobat un restaurant.");
                        }
                    } catch (Exception e) {
                        System.err.println("Error al actualitzar el restaurant: " + e.getMessage());
                    }
                    break;

                case 5:
                    try {
                        System.out.print("Introdueix l'ID del restaurant a eliminar: ");
                        long idRestaurantEliminar = Integer.parseInt(Utilitats.readLine());

                        Restaurant restaurantAEliminar = restaurantDAO.get(idRestaurantEliminar);
                        if (restaurantAEliminar != null) {
                            restaurantDAO.delete(restaurantAEliminar);
                            System.out.println("Restaurant eliminat correctament!");
                        } else {
                            System.out.println("No s'ha trobat un restaurant amb aquest ID.");
                        }
                    } catch (Exception e) {
                        System.err.println("Error al eliminar el restaurant: " + e.getMessage());
                    }
                    break;

                case 6:
                    return;
            }
        }
    }

    private static Restaurant DemanarRestaurant() {
        String nomRestaurant = Utilitats.nom(2);
        System.out.print("Introdueix la capacitat(nº Taules): ");
        int capacitatRestaurant = Integer.parseInt(Utilitats.readLine());

        Restaurant restaurantNou = new Restaurant(nomRestaurant, capacitatRestaurant);
        return restaurantNou;
    }

    private static Reserva DemanarReserva() {
        System.out.print("Introdueix el nº de la Taula: ");
        int idMesa = Integer.parseInt(Utilitats.readLine());
        System.out.print("Introdueix la data i hora (YYYY-MM-DD HH:MM): ");
        LocalDateTime fechaHora = LocalDateTime.parse(Utilitats.readLine(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        return new Reserva(idMesa, fechaHora);
    }

    public static Client DemanarClient() {
        String nomClient = Utilitats.nom(1);
        String telefonClient = Utilitats.telClient();
        System.out.print("Introdueix el correu: ");
        String correuClient = Utilitats.readLine();
        return new Client(nomClient, telefonClient, correuClient);

    }
}