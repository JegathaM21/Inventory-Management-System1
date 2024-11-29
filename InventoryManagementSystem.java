import java.util.*;

class InventoryManagementSystem {
    static class Item {
        String category;
        String unit;
        int stock;
        double price;

        Item(String category, String unit, int stock, double price) {
            this.category = category;
            this.unit = unit;
            this.stock = stock;
            this.price = price;
        }
    }

    private final Map<String, Item> inventory = new LinkedHashMap<>();

    public InventoryManagementSystem() {
        // Sample inventory data
        inventory.put("Apple", new Item("Fruits", "kg", 50, 150));
        inventory.put("Orange", new Item("Fruits", "kg", 40, 120));
        inventory.put("Potato", new Item("Vegetables", "kg", 100, 25));
        inventory.put("Rice", new Item("Grains", "kg", 200, 50));
        inventory.put("Milk", new Item("Dairy", "liter", 100, 60));
        inventory.put("Biscuits", new Item("Packaged Foods", "pack", 60, 30));
        inventory.put("Banana", new Item("Fruits", "dozen", 30, 50));
        inventory.put("Grapes", new Item("Fruits", "kg", 20, 180));
        inventory.put("Mango", new Item("Fruits", "piece", 25, 30));
        inventory.put("Onion", new Item("Vegetables", "kg", 80, 35));
        inventory.put("Carrot", new Item("Vegetables", "kg", 60, 60));
        inventory.put("Spinach", new Item("Vegetables", "bunch", 50, 20));
        inventory.put("Cucumber", new Item("Vegetables", "piece", 30, 15));
        inventory.put("Wheat Flour", new Item("Grains", "kg", 150, 40));
        inventory.put("Lentils", new Item("Grains", "kg", 100, 90));
        inventory.put("Chickpeas", new Item("Grains", "kg", 80, 100));
        inventory.put("Oats", new Item("Grains", "kg", 50, 150));
        inventory.put("Yogurt", new Item("Dairy", "cup", 75, 20));
        inventory.put("Cheese", new Item("Dairy", "kg", 20, 500));
        inventory.put("Butter", new Item("Dairy", "pack", 40, 200));
        inventory.put("Cream", new Item("Dairy", "liter", 30, 250));
        inventory.put("Chips", new Item("Packaged Foods", "pack", 50, 20));
        inventory.put("Pasta", new Item("Packaged Foods", "pack", 40, 80));
        inventory.put("Cereal", new Item("Packaged Foods", "box", 30, 200));
        inventory.put("Chocolate", new Item("Packaged Foods", "bar", 80, 20));

    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Item> entry : inventory.entrySet()) {
            String name = entry.getKey();
            Item item = entry.getValue();
            System.out.printf("%s: %d %s @ ₹%.2f/%s%n", name, item.stock, item.unit, item.price, item.unit);
        }
    }

    public void placeOrder() {
        Scanner scanner = new Scanner(System.in);
        double totalCost = 0;
        double totalWeight = 0;
        double totalVolume = 0;
        List<String> lowStockItems = new ArrayList<>();
        Map<String, Integer> orderSummary = new LinkedHashMap<>();

        while (true) {
            System.out.print("\nEnter item name (or type 'done' to finish): ");
            String itemName = scanner.nextLine().trim();
            if (itemName.equalsIgnoreCase("done")) {
                break;
            }

            if (!inventory.containsKey(itemName)) {
                System.out.println("Error: Item not available in the inventory.");
                continue;
            }

            Item item = inventory.get(itemName);
            System.out.printf("Enter quantity of %s (%s): ", itemName, item.unit);
            int quantity;
            try {
                quantity = Integer.parseInt(scanner.nextLine().trim());
                if (quantity <= 0) {
                    System.out.println("Error: Quantity must be a positive number.");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid quantity. Please enter a number.");
                continue;
            }

            if (quantity > item.stock) {
                System.out.printf("Warning: Only %d %s of %s is available.%n", item.stock, item.unit, itemName);
                quantity = item.stock;
            }

            double cost = quantity * item.price;
            totalCost += cost;
            if (item.unit.equalsIgnoreCase("kg")) {
                totalWeight += quantity;
            } else if (item.unit.equalsIgnoreCase("liter")) {
                totalVolume += quantity;
            }

            item.stock -= quantity;
            orderSummary.put(itemName, quantity);

            if (item.stock < 5) {
                lowStockItems.add(itemName);
            }
        }

        // Display order summary
        System.out.println("\nOrder Summary:");
        for (Map.Entry<String, Integer> entry : orderSummary.entrySet()) {
            String itemName = entry.getKey();
            int quantity = entry.getValue();
            Item item = inventory.get(itemName);
            double cost = quantity * item.price;
            System.out.printf("%s: %d %s @ ₹%.2f%n", itemName, quantity, item.unit, cost);
        }

        System.out.printf("\nTotal Weight (Kg): %.2f%n", totalWeight);
        System.out.printf("Total Volume (Liter): %.2f%n", totalVolume);
        System.out.printf("Total Cost: ₹%.2f%n", totalCost);

        // Display updated inventory
        System.out.println("\nUpdated Inventory:");
        for (Map.Entry<String, Integer> entry : orderSummary.entrySet()) {
            String itemName = entry.getKey();
            Item item = inventory.get(itemName);
            System.out.printf("%s: Remaining stock = %d %s%n", itemName, item.stock, item.unit);
        }

        // Display low-stock warnings
        if (!lowStockItems.isEmpty()) {
            System.out.println("\nLow Stock Warning:");
            for (String itemName : lowStockItems) {
                Item item = inventory.get(itemName);
                System.out.printf("%s: Remaining stock is below 5 %s.%n", itemName, item.unit);
            }
        }
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Grocery Shop Inventory Management System!");
        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Display Inventory");
            System.out.println("2. Place an Order");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    displayInventory();
                    break;
                case "2":
                    placeOrder();
                    break;
                case "3":
                    System.out.println("Thank you for using the system. Goodbye!");
                    return;
                default:
                    System.out.println("Error: Invalid choice. Please try again.");
            }
        }
    }

    public static void main(String[] args) {
        InventoryManagementSystem system = new InventoryManagementSystem();
        system.run();
    }
}