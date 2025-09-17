import entities.Customer;
import entities.Order;
import entities.Product;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();

        //CREAZIONE DI PRODOTTI
        List<Product> products = List.of(
                new Product(random.nextLong(), "Il Signore degli Anelli", "Books", 150.0),
                new Product(random.nextLong(), "Harry Potter e la Pietra Filosofale", "Books", 99.5),
                new Product(random.nextLong(), "Pantalone", "Boys", 80.0),
                new Product(random.nextLong(), "Maglietta", "Boys", 50.0),
                new Product(random.nextLong(), "Java 8 in Action", "Books", 120.0),
                new Product(random.nextLong(), "La Divina Commedia", "Books", 80.0),
                new Product(random.nextLong(), "Pannolini", "Baby", 25.0),
                new Product(random.nextLong(), "Bavaglino", "Baby", 10.0),
                new Product(random.nextLong(), "Seggiolino", "Baby", 100.0)

        );

        // CREAZIONE CLIENTI
        Customer customer1 = new Customer(random.nextLong(), "Matteo", 1);
        Customer customer2 = new Customer(random.nextLong(), "Manuel", 2);


        // CREAZIONE DI ORDINI
        List<Order> orders = List.of(
                new Order(random.nextLong(), "Shipped", LocalDate.of(2021, 3, 25),
                        LocalDate.of(2021, 4, 1), List.of(products.get(5),
                        products.get(6)), customer1),
                new Order(random.nextLong(), "Pending", LocalDate.of(2021, 2, 20),
                        LocalDate.of(2021, 2, 25), List.of(products.get(0),
                        products.get(2)), customer2),
                new Order(random.nextLong(), "Pending", LocalDate.of(2021, 3, 10),
                        LocalDate.of(2021, 3, 18), List.of(products.get(7)), customer2)
        );
        //ESERCIZIO 1

        List<Product> expensiveBooks = products.stream().filter(product -> product.getCategory()
                .equalsIgnoreCase("Books") && product.getPrice() > 100).toList();

        expensiveBooks.forEach(product -> System.out.println("Prodotto : " + product.getName() + "Prezzo : " + product.getPrice()));

        //ESERCIZIO 2

        List<Order> babyOrders = orders.stream().filter(order -> order.getProducts().stream()
                .anyMatch(product -> product.getCategory().equalsIgnoreCase("Baby"))).toList();

        babyOrders.forEach(order -> {
            System.out.println("--- Dettagli Ordine ---");
            System.out.println("ID Ordine: " + order.getId());
            System.out.println("Prodotti 'Baby' in questo ordine:");

            order.getProducts().stream()
                    .filter(product -> product.getCategory().equalsIgnoreCase("Baby"))
                    .forEach(babyProduct ->
                            System.out.println("  - Prodotto: " + babyProduct.getName() + " (Prezzo: " + babyProduct.getPrice() + ")")
                    );
        });

        //ESERCIZIO 3

        List<Product> discountBoysProducts = products.stream()
                .filter(product -> product.getCategory().equalsIgnoreCase("Boys"))
                .map(product -> new Product(
                        product.getId(),
                        product.getName(),
                        product.getCategory(),
                        product.getPrice() * 0.9
                )).toList();

        discountBoysProducts.forEach(product ->
                System.out.println("Prodotto : " + product.getName() + " , Prezzo originale : " +
                        String.format("%.2f", product.getPrice() / 0.9) + ", Prezzo scontato : " +
                        String.format("%.2f", product.getPrice())));


        //ESERCIZIO 4

        List<Product> ordersOfTier2 = orders.stream().filter(order -> order.getCustomer().getTier() == 2)
                .filter(order -> order.getOrderDate().isAfter(LocalDate.of(2021, 2, 1)) &&
                        order.getOrderDate().isBefore(LocalDate.of(2021, 4, 1)))
                .flatMap(order -> order.getProducts().stream()).toList();

        System.out.println("Prodotti ordinati dal cliente tier 2");
        ordersOfTier2.forEach(product -> System.out.println("Prodotto: " + product.getName() +
                ", Prezzo: " + product.getPrice() + ", Categoria: " + product.getCategory()));
    }


}
