<script setup lang="ts"></script>

<style scoped>
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

.search-button:hover {
  border: 1px solid #ee3f3f;
}
</style>

<template>
  <form class="search-bar-form" v-bind:action="'../category/Bestseller'">
    <input type="text" class="search-bar" placeholder="Search" />
    <button type="submit" class="button search-button" value="Search">
      <i class="fa fa-search"></i>
    </button>
  </form>
</template>
