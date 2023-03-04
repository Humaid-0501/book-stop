import { defineStore } from "pinia";
import { ShoppingCart } from "@/models/ShoppingCart";
import { BookItem, CustomerForm, OrderDetails } from "@/types";
import { apiUrl } from "@/services/ApiService";
import { useOrderDetailsStore } from "@/stores/OrderDetailsStore";

const CART_STORAGE_KEY = "ShoppingCart";

export const useCartStore = defineStore("CartStore", {
  state: () => {
    localStorage.setItem("category", "Bestseller");
    const initializeCart = new ShoppingCart();
    if (localStorage.getItem(CART_STORAGE_KEY) !== null) {
      const key = CART_STORAGE_KEY;
      const local = JSON.parse(
        localStorage.getItem(key) as string
      ) as ShoppingCart;
      Object.assign(initializeCart, local);
      console.log(local);
    }
    return {
      cart: initializeCart,
    };
  },

  getters: {
    count(): number {
      console.log(this.cart.numberOfItems);
      return this.cart.numberOfItems;
    },
    getCategory(): string {
      return <string>localStorage.getItem("category");
    },
  },

  actions: {
    clearCart() {
      this.cart.clear();
      localStorage.setItem(CART_STORAGE_KEY, JSON.stringify(this.cart));
    },
    addToCart(book: BookItem) {
      this.cart.addBook(book);
      localStorage.setItem(CART_STORAGE_KEY, JSON.stringify(this.cart));
    },
    updateBookQuantity(book: BookItem, quantity: number) {
      this.cart.update(book, quantity);
      localStorage.setItem(CART_STORAGE_KEY, JSON.stringify(this.cart));
    },
    async placeOrder(customerForm: CustomerForm) {
      const orderDetailsStore = useOrderDetailsStore();
      orderDetailsStore.clearOrderDetails();

      const order = { cart: this.cart, customerForm: customerForm };
      console.log(JSON.stringify(order));

      const url = apiUrl + "orders";
      const orderDetails: OrderDetails = await fetch(url, {
        mode: "cors",
        cache: "no-cache",
        credentials: "same-origin",
        headers: {
          "Content-Type": "application/json",
        },
        redirect: "follow",
        referrer: "client",
        method: "POST", // or 'PUT'
        body: JSON.stringify(order),
      }).then((response) => {
        if (response.ok) this.clearCart();
        return response.json();
      });
      orderDetailsStore.setOrderDetails(orderDetails);
    },
  },
});
