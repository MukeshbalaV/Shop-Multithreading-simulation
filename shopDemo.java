package com.olympictask;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
class Shop {
    private int riceStock;
    private int chocolateStock;

    //locks 
    private final ReentrantLock riceLock=new ReentrantLock();
    private final ReentrantLock chocolateLock=new ReentrantLock();
    public  void supplyRice(int qty) {
    	riceLock.lock();
    	try {
        System.out.println(Thread.currentThread().getName() +
                " supplying RICE: +" + qty +
                " , Before = " + riceStock);
        this.riceStock += qty;
    	}
    	finally
    	{
    		riceLock.unlock();
    	}
    }

    public  void supplyChocolate(int qty) {
    	chocolateLock.lock();
    	try {
        System.out.println(Thread.currentThread().getName() +
                " supplying Chocolate: +" + qty +
                " , Before = " + this.chocolateStock);
        this.chocolateStock += qty;
    	}
    	finally
    	{
    		chocolateLock.unlock();
    	}
    }

    public  void buyRice(int qty) {
    	riceLock.lock();
    	try {
        System.out.println(Thread.currentThread().getName() +
                " Wants to buy rice: " + qty +
                " , Before = " + this.riceStock);
        if (this.riceStock >= qty) {
            this.riceStock -= qty;
            System.out.println("Buy sucessfull , After = " + this.riceStock);
        } else {
            System.out.println("Buy Failed stock not enough");
        }
    	}
    	finally
    	{
    		riceLock.unlock();
    	}
    }

    public  void buyChocolate(int qty) {
    	chocolateLock.lock();
    	try {
        System.out.println(Thread.currentThread().getName() +
                " Wants to buy chocolate: " + qty +
                " , Before = " + this.chocolateStock);
        if (this.chocolateStock >= qty) {
            this.chocolateStock -= qty;
            System.out.println("Buy sucessfull , After = " + this.chocolateStock);
        } else {
            System.out.println("Buy Failed stock not enough");
        }
    	}
    	finally
    	{
    		chocolateLock.unlock();
    	}
    }

    // getters
    public  int getRiceStock() {
    	riceLock.lock();
    	try {
        return riceStock;
    	}
    	finally
    	{
    		riceLock.unlock();
    	}
    }

    public  int getChocolateStock() {
    	chocolateLock.lock();
    	try {
        return chocolateStock;
    	}
    	finally
    	{
    		chocolateLock.unlock();
    	}
    }
}

class Supplier implements Runnable {
    private Shop shop;
    private Random random = new Random();

    Supplier(Shop shop) {
        this.shop = shop;
    }

    @Override
    public void run() {
        while(true) {
            int qt = random.nextInt(100) + 1; // adding 1 so 0 supply will not be there
            int choice = random.nextInt(2);
            if (choice == 0) {
                shop.supplyRice(qt);
            } else {
                shop.supplyChocolate(qt);
            }

            try {
                Thread.sleep(100); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Customer implements Runnable {
    private Shop shop;
    private Random random = new Random();

    Customer(Shop shop) {
        this.shop = shop;
    }

    @Override
    public void run() {
       while(true) {
            int qt = random.nextInt(5) + 1;
            int choice = random.nextInt(2);
            if (choice == 0) {
                shop.buyRice(qt);
            } else {
                shop.buyChocolate(qt);
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class ShopDemo {
    public static void main(String[] args) {

        Shop shop = new Shop();

        Supplier supplier = new Supplier(shop);
        Thread supplierThread = new Thread(supplier, "Supplier-1");
        supplierThread.start();

        Thread[] customerThreads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            Customer customer = new Customer(shop);
            customerThreads[i] = new Thread(customer, "Customer-" + (i + 1));
            customerThreads[i].start();
        }
       
        // to get the final stock once the whole program is over
        try {
            supplierThread.join();
            for (int i = 0; i < 10; i++) {
                customerThreads[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n--- All Operations Finished ---\n");
        System.out.println("Final Rice Stock = " + shop.getRiceStock());
        System.out.println("Final Chocolate Stock = " + shop.getChocolateStock());
    }
}
