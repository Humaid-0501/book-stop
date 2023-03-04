<script setup lang="ts">
import { useCartStore } from "@/stores/CartStore";
import { BookItem } from "@/types";
import { useCategoryStore } from "@/stores/CategoryStore";
import { asDollarsAndCents } from "@/utils";
import { bookImageFileName } from "@/utils";

const cartStore = useCartStore();
const categoryStore = useCategoryStore();

const updateCart = function (book: BookItem, quantity: number) {
  cartStore.updateBookQuantity(book, quantity);
};

function updateAmount(book: BookItem, quantity: number) {
  let amount = book.price * quantity;
  return amount;
}
</script>

<style scoped>
.cart-table {
  display: grid;
  grid-template-columns: max-content minmax(10em, 20em) repeat(3, max-content);
  row-gap: 1em;
  width: fit-content;
  margin: 0 auto;
  background-color: aliceblue;
  margin-bottom: 30px;
}

ul {
  display: contents;
}

ul > li {
  display: contents;
}

.table-heading {
  background-color: #333;
  color: white;
}

.table-heading > * {
  background-color: var(--secondary-background-color);
  padding: 1em;
}

.heading-book {
  grid-column: 1 / 3;
}

.heading-price {
  grid-column: 3 / 5;
  text-align: center;
}

.heading-subtotal {
  text-align: right;
  grid-column: -2 / -1;
}

.cart-book-image {
  padding: 0 1em;
}

.cart-book-image > * {
  margin-left: auto;
  margin-right: 0;
}

img {
  display: block;
  width: 100px;
  height: auto;
}

.cart-book-price {
  padding-left: 1em;
  text-align: right;
}

.cart-book-quantity {
  padding-left: 1em;
  padding-right: 1em;
  margin-top: -2px;
}

.cart-book-subtotal {
  text-align: right;
  padding-left: 1em;
  padding-right: 1em;
}

/* Row separators in the table */

.line-sep {
  display: block;
  height: 2px;
  background-color: #1325402e;
  grid-column: 1 / -1;
}

/* Increment/decrement buttons */
.icon-button {
  border: none;
  cursor: pointer;
  background: none;
  font-size: 1.51rem;
}

.quantity {
  padding: 0 5px;
}

.inc-button {
  font-size: 1.5rem;
  color: var(--secondary-background-color);
  margin-right: 0.25em;
}

.inc-button:hover {
  color: var(--primary-color);
}

.dec-button {
  font-size: 1.5rem;
  color: var(--secondary-background-color);
}

.dec-button:hover {
  color: var(--primary-color);
}

select {
  background-color: var(--primary-color);
  color: white;
  border: 2px solid var(--primary-color-dark);
  border-radius: 3px;
}

.cart-final-subtotal {
  text-align: center;
  grid-column: -2 / -1;
  width: 100%;
  padding-right: 1em;
  padding-top: 0;
}

.checkout-button {
  background: var(--primary-color);
  box-shadow: 0px 4px 4px rgb(0 0 0 / 25%), 0px 4px 4px rgb(0 0 0 / 25%);
  border-radius: 50px;
  border: 2px solid white;
  padding: 1rem 20px;
  font-style: normal;
  font-weight: 500;
  width: -moz-fit-content;
  width: fit-content;
  color: white;
  text-decoration: none;
  align-items: center;
  display: flex;
}

.checkout-button:hover {
  background: var(--secondary-background-color);
  border-color: transparent;
}

.clear-cart-row {
  grid-column: 1 / -1;
  width: 100%;
  justify-content: center;
  padding: 10px;
  display: flex;
}

.clear-cart {
  font-style: normal;
  font-weight: 400;
  font-size: 18px;
  width: fit-content;
  cursor: pointer;
  align-items: center;
  display: flex;
  color: #ef1e1e;
  border: none;
  text-decoration: underline;
  background: transparent;
}

.clear-cart:hover {
  color: var(--secondary-background-color);
}

.continue-shopping {
  background: #ffffff;
  border: 2px solid var(--primary-color);
  color: var(--primary-color);
  box-shadow: 0px 4px 4px rgb(0 0 0 / 25%);
  border-radius: 50px;
  padding: 1rem 20px;
  font-style: normal;
  font-weight: 400;
  font-size: 18px;
  width: -moz-fit-content;
  width: fit-content;
  text-decoration: none;
  cursor: pointer;
  align-items: center;
  display: flex;
}

.continue-shopping:hover {
  background: var(--secondary-background-color);
  color: white;
  border-color: transparent;
}

.cart-buttons {
  grid-column: 1 / -1;
  width: 100%;
  display: flex;
  justify-content: space-between;
  padding: 10px 1em;
}

.empty-cart-text {
  display: flex;
  justify-content: center;
  margin: 10px 0;
  font-weight: 400;
}

.empty-cart li {
  display: flex;
  justify-content: center;
  margin: 1em;
}

.empty-cart {
  min-height: calc(100vh - 450px);
}
</style>

<template>
  <div class="cart-table" v-if="cartStore.cart.numberOfItems != 0">
    <ul>
      <li class="table-heading">
        <div class="heading-book">Book</div>
        <div class="heading-price">Price / Quantity</div>
        <div class="heading-subtotal">Amount</div>
      </li>
      <template v-for="item in cartStore.cart.items" :key="item.book.bookId">
        <li>
          <div class="cart-book-image">
            <img
              :src="bookImageFileName(item.book.title)"
              :alt="item.book.title"
              width="100px"
              height="auto"
            />
          </div>
          <div class="cart-book-title">{{ item.book.title }}</div>
          <div class="cart-book-price">
            {{ asDollarsAndCents(item.book.price) }}
          </div>
          <div class="cart-book-quantity">
            <button
              class="dec-button icon-button"
              @click="updateCart(item.book, item.quantity - 1)"
            >
              <i class="fa-solid fa-circle-minus"></i>
            </button>
            <span class="quantity">{{ item.quantity }}</span>
            <button
              class="inc-button icon-button"
              @click="updateCart(item.book, item.quantity + 1)"
            >
              <i class="fa-solid fa-circle-plus"></i>
            </button>
          </div>
          <div class="cart-book-subtotal">
            {{ asDollarsAndCents(updateAmount(item.book, item.quantity)) }}
          </div>
        </li>
        <li class="line-sep"></li>
      </template>
      <li>
        <div class="heading-book"></div>
        <div class="heading-price">Total Books: {{ cartStore.count }}</div>
        <div class="cart-final-subtotal">
          <b>Subtotal : {{ asDollarsAndCents(cartStore.cart.subtotal) }}</b>
        </div>
      </li>
      <li class="cart-buttons">
        <router-link
          :to="'../category/' + categoryStore.prevCategory"
          class="continue-shopping"
          >Continue Shopping</router-link
        >
        <router-link :to="{ name: 'checkout-view' }" class="checkout-button"
          >Checkout!</router-link
        >
      </li>
      <li class="clear-cart-row">
        <button class="clear-cart" @click="cartStore.clearCart()">
          <i>Clear Cart</i>
        </button>
      </li>
    </ul>
  </div>
  <div v-else class="empty-cart">
    <h3 class="empty-cart-text">
      Your cart is empty. Please add items to your cart.
    </h3>
    <ul>
      <li class="cart-buttons">
        <router-link
          :to="'../category/' + categoryStore.prevCategory"
          class="continue-shopping"
          >Continue Shopping</router-link
        >
      </li>
    </ul>
  </div>
</template>
