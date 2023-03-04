<script setup lang="ts">
import HeaderDropdown from "@/components/HeaderDropdown.vue";
import { useCartStore } from "@/stores/CartStore";
const cartStore = useCartStore();
</script>

<style scoped>
.container {
  padding: 2rem 4rem;
  padding-bottom: 0px;
}

header {
  background: #ffffff;
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  padding: 1em;
  gap: 1em;
  align-items: center;
  font-size: 14px;
  font-family: "Montserrat";
  border-bottom: 1px solid lightgrey;
}

.logo-text {
  display: flex;
  cursor: pointer;
  text-decoration: none;
}

.logo-icon {
  padding-top: 19px;
  padding-left: 15px;
}

.logo-text:hover {
  color: #ee3f3f;
}

.text-logo {
  font-family: var(--title-font-family);
}

.bookstore-logo {
  padding-left: 15px;
  padding-right: 15px;
  padding-bottom: 25px;
  border-bottom: 3px solid #ee3f3f;
  color: #ee3f3f;
  font-weight: 700;
  font-size: 24px;
}

.title-and-search-bar {
  padding-bottom: 25px;
}

.title-and-search-bar {
  padding-bottom: 25px;
}

.search-bar-form input::placeholder {
  color: #132540;
  font-weight: 300;
}

.search-bar-form {
  border-radius: 50px;
  border: 1px solid #ee3f3f;
}

.search-bar {
  padding: 0.5em;
  border-radius: 50px;
  border: 1px solid #ee3f3f;
  border-style: none;
}

input[type="text"] {
  border: none;
  background: transparent;
  margin: 0;
  padding: 7px 8px;
  font-size: 14px;
  color: inherit;
  border: 1px solid transparent;
  border-radius: inherit;
}

input[type="text"]:focus {
  padding-left: 10px;
  outline: none;
}

.search-button {
  border: none;
}

.search-button:hover {
  border: 1px solid #ee3f3f;
}

.button {
  background-color: #ffffff;
  color: #ee3f3f;
  border: 1px solid #ee3f3f;
  font-size: 14px;
}

.button.search-button {
  border-color: transparent;
}

.button:visited {
  background-color: #ee3f3f;
}

.button:hover,
.button:active {
  background: var(--primary-color);
  color: white;
}

.header-dropdown-and-cart {
  display: flex;
  gap: 10px;
  padding-bottom: 25px;
}

@media (max-width: 600px) {
  header {
    flex-direction: column;
  }
}
</style>

<template>
  <header class="container">
    <section class="bookstore-logo">
      <router-link style="text-decoration: none" to="/">
        <a class="logo-text">
          <h1 class="text-logo">BookStop</h1>
          <i class="logo-icon fa-regular fa-circle-stop fa-xl"></i
        ></a>
      </router-link>
    </section>
    <section class="title-and-search-bar">
      <form class="search-bar-form" v-bind:action="'../category/Bestseller'">
        <input type="text" class="search-bar" placeholder="Search" />
        <button type="submit" class="button search-button" value="Search">
          <i class="fa fa-search"></i>
        </button>
      </form>
    </section>
    <section class="header-dropdown-and-cart">
      <router-link to="../">
        <button class="button categories-button">Home</button>
      </router-link>

      <HeaderDropdown></HeaderDropdown>
      <router-link to="/cart">
        <button class="button">
          <i class="fa-solid fa-bag-shopping" style="padding-right: 10px"></i
          >{{ cartStore.count }}
        </button>
      </router-link>

      <button class="button">
        <i
          class="profile-icon fa-solid fa-circle-user fa-lg"
          style="padding-right: 10px"
        ></i
        >Humaid
      </button>
    </section>
  </header>
</template>
