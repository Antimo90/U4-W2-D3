import com.github.javafaker.Faker;
import entities.User;
import functional_interfaces.StringModifier;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Main {
	public static void main(String[] args) {

		StringModifier dotsWrapper = str -> "..." + str + "...";
		StringModifier starsWrapper = str -> "***" + str + "***";
		StringModifier stringInverter = str -> {
			String[] splitted = str.split("");
			String inverted = "";

			for (int i = splitted.length - 1; i >= 0; i--) {
				inverted += splitted[i];
			}
			return inverted;
		};

		// Posso creare delle LAMBDA FUNCTIONS solo se c'è una FUNCTIONAL INTERFACE dietro
		// L'interfaccia StringModifier è l'interfaccia funzionale di riferimento
		// Ciò significa che la Lambda function che vado a creare implementa
		// esattamente l'unico metodo che c'è in quell'interfaccia e Java
		// può quindi controllare i parametri e tipi di ritorno
//		System.out.println(dotsWrapper.modify("CIAO"));
//		System.out.println(starsWrapper.modify("CIAO"));
//		System.out.println(stringInverter.modify("CIAO"));


		// *********************************************************** SUPPLIER *********************************************************************
//		Supplier<Integer> randomIntSupplier = () -> {
//			Random rndm = new Random();
//			return rndm.nextInt(1, 10000);
//		};
//
//		List<Integer> randomNumbers = new ArrayList<>();
//		for (int i = 0; i < 100; i++) {
//			randomNumbers.add(randomIntSupplier.get());
//		}
//
//		Supplier<User> randomUserSupplier = () -> {
//			Random rndm = new Random();
//			Faker faker = new Faker(Locale.ITALY);
//			return new User(faker.lordOfTheRings().character(), faker.name().lastName(), rndm.nextInt(0, 100));
//		};
//
//		List<User> randomUsers = new ArrayList<>();
//		for (int i = 0; i < 100; i++) {
//			randomUsers.add(randomUserSupplier.get());
//		}
//
//		Supplier<List<User>> randomListSupplier = () -> {
//			List<User> randomUsers2 = new ArrayList<>();
//			Random rndm = new Random();
//			Faker faker = new Faker(Locale.ITALY);
//			for (int i = 0; i < 100; i++) {
//				randomUsers2.add(new User(faker.lordOfTheRings().character(), faker.name().lastName(), rndm.nextInt(0, 100)));
//			}
//			return randomUsers2;
//		};
//
//		randomUsers.forEach(user -> System.out.println(user));
		// randomUsers.forEach(System.out::println); <-- Alternativa alla riga precedente ma più compatta


		// ********************************************************* PREDICATES ******************************************************************
		// Nelle parentesi angolari inserisco il TIPO del parametro della lambda

//		Predicate<Integer> isMoreThanZero = number -> number > 0;
//		Predicate<Integer> isLessThanHundred = number -> number < 100;
//		System.out.println(isMoreThanZero.test(-20));
//		System.out.println(isMoreThanZero.test(40));
//		System.out.println(isLessThanHundred.test(50));
//
//		System.out.println(isMoreThanZero.and(isLessThanHundred).test(30));
//		System.out.println(isMoreThanZero.negate().test(-40));
//
//		Predicate<User> isAdult = user -> user.getAge() >= 18;
//
//		randomUsers.forEach(user -> {
//			if (isAdult.test(user)) System.out.println("L'utente " + user + " è maggiorenne");
//			else System.out.println("L'utente " + user + " é' minorenne");
//		});


		// ****************************************************** STREAMS - FILTER **************************************************************************

		Supplier<Integer> randomIntSupplier = () -> {
			Random rndm = new Random();
			return rndm.nextInt(1, 10000);
		};

		List<Integer> randomNumbers = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			randomNumbers.add(randomIntSupplier.get());
		}

		Predicate<Integer> isLessThanHundred = number -> number < 100;
		Predicate<Integer> isMoreThanZero = number -> number > 0;

		// Nei filter posso usare o delle Lambda scritte al volo (number -> ) oppure dei Predicate definiti in precedenza
		Stream<Integer> stream = randomNumbers.stream().filter(isLessThanHundred.and(isMoreThanZero)); // N.B. Gli stream NON SONO LISTE!
		Stream<Integer> stream2 = randomNumbers.stream().filter(number -> number < 1000 && number > 500);

		// stream.forEach(number -> System.out.println(number)); // Neanche questa è una List
		stream2.forEach(number -> System.out.println(number));

		Supplier<List<User>> randomListSupplier = () -> {
			List<User> randomUsers2 = new ArrayList<>();
			Random rndm = new Random();
			Faker faker = new Faker(Locale.ITALY);
			for (int i = 0; i < 100; i++) {
				randomUsers2.add(new User(faker.lordOfTheRings().character(), faker.name().lastName(), rndm.nextInt(0, 100)));
			}
			return randomUsers2;
		};

		List<User> usersList = randomListSupplier.get();

		usersList.stream().filter(user -> user.getAge() < 18).forEach(user -> System.out.println(user));

		// ****************************************************** STREAMS - MAP **************************************************************************
		System.out.println("// ****************************************************** STREAMS - MAP **************************************************************************");

		usersList.stream().map(user -> user.getName() + " " + user.getSurname()).forEach(fullName -> System.out.println(fullName));
		usersList.stream().map(user -> user.getAge()).forEach(age -> System.out.println(age));
		// usersList.stream().map(User::getAge).forEach(System.out::println); // Alternativa ancora più compatta alla riga precedente

		// ****************************************************** STREAMS - FILTER & MAP **************************************************************************
		System.out.println("// ****************************************************** STREAMS - FILTER & MAP **************************************************************************");
		usersList.stream()
				.filter(user -> user.getAge() >= 18)
				.map(user -> user.getSurname())
				.forEach(surname -> System.out.println(surname));

		// ********************************************************* COME TERMINARE GLI STREAMS ******************************************************

		System.out.println("****************************************************** TERMINATORI - MATCHERS **************************************************************************");
		// .anyMatch() e .allMatch() (corrispondono a .some() e .every() in JavaScript)
		// Controllano se ALMENO UN elemento (.anyMatch e .some) della lista/stream rispetta una certa condizione e mi restituisce true in tal caso
		// Oppure controllano (.allMatch e .every) se TUTTI gli elementi della lista/stream rispettano una certa condizione e mi tornerà true
		// solo nel caso TUTTI rispettino la condizione
		// ENTRAMBI RITORNANO UN BOOLEANO

		if (usersList.stream().anyMatch(user -> user.getAge() > 17)) {
			System.out.println("C'è almeno un maggiorenne");
		} else {
			System.out.println("Sono tutti minorenni");
		}

		if (usersList.stream().allMatch(user -> user.getAge() > 17)) {
			System.out.println("Sono tutti maggiorenni");
		} else {
			System.out.println("C'è qualche minorenne");
		}

		System.out.println("****************************************************** TERMINATORI - REDUCE **************************************************************************");
		int total = usersList.stream()
				.filter(user -> user.getAge() < 18)
				.map(user -> user.getAge())
				.reduce(0, (partialSum, currentElement) -> partialSum + currentElement);
		System.out.println("La somma totale delle età é: " + total);

		System.out.println("****************************************************** TERMINATORI - COME OTTENERE UNA LISTA DA UNO STREAM **************************************************************************");
//		List<User> minorenni = usersList.stream()
//				.filter(user -> user.getAge() < 18)
//				.collect(Collectors.toList());

		List<User> minorenni = usersList.stream()
				.filter(user -> user.getAge() < 18)
				.toList(); // Come sopra però più compatto e pratico

		System.out.println(minorenni);

		System.out.println(" ------------------------------------------------------ DATE ----------------------------------------------------------");
		// Date <-- Per comodità in Java invece di usare Date per le date meglio usare LocalDate
		LocalDate today = LocalDate.now();
		System.out.println(today);

		LocalDate yesterday = today.minusDays(1);
		System.out.println("Ieri: " + yesterday);
		LocalDate tomorrow = today.plusDays(1);
		System.out.println("Domani: " + tomorrow);
		System.out.println("Il giorno di oggi lo scorso anno era " + today.minusYears(1));

		LocalDateTime currentTime = LocalDateTime.now();
		System.out.println(currentTime);

		LocalDate date = LocalDate.of(1990, 1, 2);
		System.out.println(date);

		System.out.println("date è prima di oggi? " + date.isBefore(today));
		System.out.println("date è dopo oggi? " + date.isAfter(today));

		date.isLeapYear();

		currentTime.getHour();

	}
}
