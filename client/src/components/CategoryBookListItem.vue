<script setup lang="ts">
import { defineProps } from "vue";
import { BookItem } from "@/types";
import { useCartStore } from "@/stores/CartStore";
import { asDollarsAndCents, bookImageFileName } from "@/utils";

const props = defineProps<{
  book: BookItem;
}>();
const cartStore = useCartStore();
</script>
<style scoped>
.book-box {
  display: flex;
  flex-direction: column;
  padding: 1em;
  gap: 0.25em;
  width: 174px;
}

.book-image img {
  height: 208px;
  width: 134px;
}

.book-title {
  font-weight: 600;
  font-size: 16px;
}

.book-author {
  font-style: italic;
  font-size: 14px;
}

.book-price {
  color: var(--primary-color);
  font-family: "Montserrat";
  font-weight: 500;
  font-size: 16px;
  font-variant-caps: small-caps;
}

.add-bag-button {
  width: 132px;
}

.button.read-now {
  transform: translate3d(1.5em, -16.5em, 0);
  color: #132540;
  border-color: #132540;
  background-color: #fff;
  width: 96px;
}

.button.read-now:hover {
  color: white;
  background-color: #132540;
}

.button {
  background-color: #ffffff;
  color: #ee3f3f;
  border: 1px solid #ee3f3f;
  font-size: 14px;
}

.button:hover,
.button:active {
  background: var(--primary-color);
  color: white;
}

.is-featured {
  background-color: #132540;
  color: white;
}

.is-featured .add-bag-button {
  background: none;
  color: white;
  border-color: white;
}

.is-featured .add-bag-button:hover {
  color: var(--primary-color);
  border-color: var(--primary-color);
}
</style>

<template>
  <li class="book-box" :class="{ 'is-featured': book.isFeatured }">
    <div class="book-image">
      <img :src="bookImageFileName(book.title)" :alt="book.title" />
    </div>
    <div class="book-title" v-if="book.title.length > 16">
      {{ book.title.substring(0, 12).concat("...") }}
    </div>
    <div class="book-title" v-else>
      {{ book.title }}
    </div>
    <div class="book-author">{{ book.author }}</div>
    <div class="book-price">{{ asDollarsAndCents(book.price) }}</div>
    <button class="button add-bag-button" @click="cartStore.addToCart(book)">
      <i class="fa-solid fa-bag-shopping" style="padding-right: 10px"></i>Add to
      Bag
    </button>
    <button class="button read-now" v-if="book.isPublic">Read Now</button>
  </li>
</template>
