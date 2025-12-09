
---

# Shop Multithreading Simulation

This Java project simulates a **shop environment** where a supplier adds stock of products and multiple customers purchase products concurrently. It demonstrates the use of **multithreading** and **synchronization** in Java to manage shared resources safely.

---

## Features

* Simulates a shop with two products:

  * **Rice**
  * **Chocolate**
* One **Supplier** thread adds stock randomly.
* Multiple **Customer** threads buy products concurrently.
* Uses **synchronized methods** to ensure thread-safe operations on stock.
* Displays **real-time updates** of stock for each operation.
* Prints **final stock** after all operations are completed.

---

## Classes

### `Shop`

* Maintains stock for rice and chocolate.
* Methods:

  * `supplyRice(int qty)` – Adds rice stock.
  * `supplyChocolate(int qty)` – Adds chocolate stock.
  * `buyRice(int qty)` – Buys rice if stock is sufficient.
  * `buyChocolate(int qty)` – Buys chocolate if stock is sufficient.
  * Getters for rice and chocolate stock.
* All methods modifying stock are **synchronized** to prevent race conditions.

### `Supplier`

* Implements `Runnable`.
* Randomly supplies rice or chocolate to the shop in a loop.

### `Customer`

* Implements `Runnable`.
* Randomly buys rice or chocolate from the shop in a loop.

### `ShopDemo`

* Main class to start the simulation.
* Creates one `Shop` object.
* Starts one `Supplier` thread and multiple `Customer` threads.
* Waits for all threads to finish and prints the final stock.

---

## How to Run

1. Clone the repository:

```bash
git clone <repo-url>
```

2. Compile the project:

```bash
javac com/olympictask/*.java
```

3. Run the simulation:

```bash
java com.olympictask.ShopDemo
```

---

## Example Output

```
Supplier-1 supplying RICE: +50 , Before = 0
Customer-1 Wants to buy rice: 20 , Before = 50
Buy successful , After = 30
...
--- All Operations Finished ---
Final Rice Stock = 250
Final Chocolate Stock = 180
```

---

## Concepts Demonstrated

* Java Multithreading (`Thread`, `Runnable`)
* Synchronization (`synchronized` methods)
* Shared resource management
* Randomized simulation of real-world scenarios

---

## License

This project is open source and free to use.

---

