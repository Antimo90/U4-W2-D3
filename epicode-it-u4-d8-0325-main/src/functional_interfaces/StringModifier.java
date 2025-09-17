package functional_interfaces;

@FunctionalInterface
public interface StringModifier {
	public String modify(String str);
	// public void blabla(); //<-- Un'interfaccia è FUNZIONALE se e solo se possiede un unico metodo astratto
}
