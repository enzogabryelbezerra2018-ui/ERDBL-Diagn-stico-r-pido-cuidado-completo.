// Classe base abstrata para elementos radioativos
public abstract class RadioactiveElement {
    protected String name;
    protected String symbol;
    protected int baseRadiation;   // radiação base
    protected int currentRadiation; // radiação atual (pode aumentar com tempo)
    protected String hazardLevel;  // Baixo, Médio, Alto

    public RadioactiveElement(String name, String symbol, int baseRadiation, String hazardLevel) {
        this.name = name;
        this.symbol = symbol;
        this.baseRadiation = baseRadiation;
        this.currentRadiation = baseRadiation;
        this.hazardLevel = hazardLevel;
    }

    // Método que aumenta a radiação conforme tempo de exposição (em horas)
    public void expose(int hours) {
        int increase = (int)(currentRadiation * 0.05 * hours);
        currentRadiation += increase;
        updateHazardLevel();
        showRadiationEffect();
    }

    // Atualiza nível de perigosidade conforme radiação atual
    protected void updateHazardLevel() {
        if (currentRadiation <= 20) {
            hazardLevel = "Baixo";
        } else if (currentRadiation <= 70) {
            hazardLevel = "Médio";
        } else {
            hazardLevel = "Alto";
        }
    }

    // Combina com outro elemento e retorna um novo elemento radioativo cumulativo
    public RadioactiveElement combine(RadioactiveElement other) {
        int combinedRadiation = this.currentRadiation + other.currentRadiation;
        String combinedName = this.name + "-" + other.name;
        String combinedSymbol = this.symbol + other.symbol;

        RadioactiveElement combinedElement;
        if (combinedRadiation <= 20) {
            combinedElement = new RadioactiveI(combinedName, combinedSymbol);
        } else if (combinedRadiation <= 70) {
            combinedElement = new RadioactiveII(combinedName, combinedSymbol);
        } else {
            combinedElement = new RadioactiveIII(combinedName, combinedSymbol);
        }

        combinedElement.currentRadiation = combinedRadiation;
        combinedElement.updateHazardLevel();
        combinedElement.showRadiationEffect();
        return combinedElement;
    }

    // Simula efeitos visuais de radiação no console
    protected void showRadiationEffect() {
        System.out.println("Simulando radiação de " + name + " (" + symbol + "):");
        int lines = currentRadiation / 10; // quantidade de linhas de efeito
        for (int i = 0; i < lines; i++) {
            System.out.println("~ ~ ~ Radiação ~ ~ ~");
        }
        if (hazardLevel.equals("Alto")) {
            System.out.println("⚠️ ALERTA: Nível de radiação ALTO! Proteja-se!");
        }
        System.out.println("-------------------------");
    }

    // Exibe informações detalhadas
    public void displayInfo() {
        System.out.println("Nome: " + name);
        System.out.println("Símbolo: " + symbol);
        System.out.println("Radiação Base: " + baseRadiation);
        System.out.println("Radiação Atual: " + currentRadiation);
        System.out.println("Perigosidade: " + hazardLevel);
        System.out.println("-------------------------");
    }
}

// RADIOACTIVE I - menos perigoso
class RadioactiveI extends RadioactiveElement {
    public RadioactiveI(String name, String symbol) {
        super(name, symbol, 10, "Baixo");
    }
}

// RADIOACTIVE II - perigo moderado
class RadioactiveII extends RadioactiveElement {
    public RadioactiveII(String name, String symbol) {
        super(name, symbol, 50, "Médio");
    }
}

// RADIOACTIVE III - altamente perigoso
class RadioactiveIII extends RadioactiveElement {
    public RadioactiveIII(String name, String symbol) {
        super(name, symbol, 100, "Alto");
    }
}

// Classe principal para teste
public class Main {
    public static void main(String[] args) {
        RadioactiveElement cesium = new RadioactiveI("Césio", "Cs");
        RadioactiveElement uranium = new RadioactiveII("Urânio", "U");
        RadioactiveElement plutonium = new RadioactiveIII("Plutônio", "Pu");

        System.out.println("=== Estados iniciais ===");
        cesium.displayInfo();
        uranium.displayInfo();
        plutonium.displayInfo();

        // Expondo elementos ao tempo
        cesium.expose(5);      // 5 horas
        uranium.expose(3);     // 3 horas
        plutonium.expose(2);   // 2 horas

        System.out.println("=== Após exposição ===");
        cesium.displayInfo();
        uranium.displayInfo();
        plutonium.displayInfo();

        // Combinando elementos
        RadioactiveElement combo1 = cesium.combine(uranium);
        RadioactiveElement combo2 = combo1.combine(plutonium);

        System.out.println("=== Após combinação ===");
        combo1.displayInfo();
        combo2.displayInfo();
    }
}
