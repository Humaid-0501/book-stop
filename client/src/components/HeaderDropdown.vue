<script setup lang="ts">
import { useCategoryStore } from "@/stores/CategoryStore";

const categoryStore = useCategoryStore();
</script>

<style scoped>
.header-dropdown {
  position: relative;
}

.categories-button {
  background-color: #fff;
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

.header-dropdown ul {
  background-color: white;
  color: white;
  position: absolute;
  min-width: 8em;
  z-index: 1;
  cursor: pointer;
  overflow: hidden;
  height: 0px;
  border: none;
  border-radius: 20px;
  transition: height 0.4s ease-in-out, border 0.4s ease-in;
}

.box-shadow {
  border: 1px solid lightgrey;
  box-shadow: 10px 5px 5px #0008;
}

.header-dropdown li {
  padding: 0.5em 0.75em;
}

.header-dropdown a {
  color: black;
  text-decoration: none;
}

.header-dropdown li:hover {
  background-color: lightgray;
  border-radius: inherit;
}

.header-dropdown:hover ul {
  height: 124px;
}
</style>

<template>
  <div class="header-dropdown">
    <button class="button categories-button">
      Categories <i class="fa-solid fa-angle-down"></i>
    </button>
    <ul class="box-shadow">
      <li
        v-for="category in categoryStore.categoryList"
        :key="category.categoryId"
      >
        <router-link :to="'../category/' + category.name"
          >{{ category.name }}
        </router-link>
      </li>
    </ul>
  </div>
</template>
